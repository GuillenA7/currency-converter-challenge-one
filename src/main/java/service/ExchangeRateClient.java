package main.java.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import main.java.model.ExchangeRateResponse;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Simple HTTP client for ExchangeRate-API.
 * - Reads API key from env var: EXCHANGE_RATE_API_KEY
 * - Provides methods to fetch a pair conversion rate or a converted amount
 */
public class ExchangeRateClient {

    private static final String BASE_URL = "https://v6.exchangerate-api.com/v6";
    private static final Duration CONNECT_TIMEOUT = Duration.ofSeconds(10);
    private static final Duration READ_TIMEOUT = Duration.ofSeconds(20);

    private final String apiKey;
    private final HttpClient httpClient;
    private final Gson gson;

    public ExchangeRateClient() {
        this(System.getenv("EXCHANGE_RATE_API_KEY"));
    }

    public ExchangeRateClient(String apiKey) {
        if (apiKey == null || apiKey.isBlank()) {
            throw new IllegalStateException("Missing environment variable: EXCHANGE_RATE_API_KEY");
        }
        this.apiKey = apiKey;
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(CONNECT_TIMEOUT)
                .build();
        this.gson = new Gson();
    }

    /**
     * Returns only the conversion rate for BASE -> TARGET (e.g., USD -> MXN).
     */
    public double getConversionRate(String base, String target) throws IOException, InterruptedException {
        String url = String.format("%s/%s/pair/%s/%s",
                BASE_URL, apiKey, normalize(base), normalize(target));

        ExchangeRateResponse data = sendAndParse(url);
        ensureSuccess(data);
        return data.getConversionRate();
    }

    /**
     * Returns the converted amount for BASE -> TARGET with the given amount.
     * (This hits the /pair/.../{amount} endpoint, which returns conversion_result.)
     */
    public double convert(String base, String target, double amount) throws IOException, InterruptedException {
        String url = String.format("%s/%s/pair/%s/%s/%.8f",
                BASE_URL, apiKey, normalize(base), normalize(target), amount);

        ExchangeRateResponse data = sendAndParse(url);
        ensureSuccess(data);

        // If conversion_result is missing (when API doesn't include it), compute fallback:
        if (data.getConversionResult() == 0.0 && data.getConversionRate() != 0.0) {
            return amount * data.getConversionRate();
        }
        return data.getConversionResult();
    }

    // --------------------- helpers ---------------------

    private ExchangeRateResponse sendAndParse(String url) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(READ_TIMEOUT)
                .GET()
                .header("Accept", "application/json")
                .build();

        HttpResponse<String> response =
                httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        // Basic HTTP validation first
        if (response.statusCode() != 200) {
            throw new RuntimeException("HTTP " + response.statusCode() + " | " + response.body());
        }

        // Optional quick sanity check before full mapping
        JsonObject root = JsonParser.parseString(response.body()).getAsJsonObject();
        if (!root.has("result")) {
            throw new RuntimeException("Malformed JSON: missing 'result'");
        }

        // Map to our model
        return gson.fromJson(response.body(), ExchangeRateResponse.class);
    }

    private void ensureSuccess(ExchangeRateResponse data) {
        if (!"success".equalsIgnoreCase(data.getResult())) {
            // The API often returns an "error-type" when result != success.
            // If you want, you can parse it from the raw JSON too.
            throw new RuntimeException("API error: result=" + data.getResult());
        }
    }

    private String normalize(String code) {
        if (code == null) throw new IllegalArgumentException("Currency code cannot be null");
        String c = code.trim().toUpperCase();
        if (c.length() != 3) throw new IllegalArgumentException("Invalid currency code: " + code);
        return c;
    }

    /**
     * Fetches a map of conversion rates for a given base (e.g., latest/USD).
     * Returns a Map like { "MXN": 18.52, "BRL": 5.12, ... }
     */
    public Map<String, Double> getLatestRates(String base) throws IOException, InterruptedException {
        String url = String.format("%s/%s/latest/%s", BASE_URL, apiKey, normalize(base));

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(READ_TIMEOUT)
                .GET()
                .header("Accept", "application/json")
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new RuntimeException("HTTP " + response.statusCode() + " | " + response.body());
        }

        // Parse once to check result and extract conversion_rates object
        JsonObject root = JsonParser.parseString(response.body()).getAsJsonObject();
        if (!root.has("result") || !"success".equalsIgnoreCase(root.get("result").getAsString())) {
            String error = root.has("error-type") ? root.get("error-type").getAsString() : "Unknown";
            throw new RuntimeException("API error: " + error);
        }

        if (!root.has("conversion_rates")) {
            return Collections.emptyMap();
        }

        JsonObject ratesObj = root.getAsJsonObject("conversion_rates");

        // Convert JsonObject to Map<String, Double>
        Type type = new TypeToken<HashMap<String, Double>>(){}.getType();
        Map<String, Double> rates = new Gson().fromJson(ratesObj, type);
        return rates != null ? rates : Collections.emptyMap();
    }
}