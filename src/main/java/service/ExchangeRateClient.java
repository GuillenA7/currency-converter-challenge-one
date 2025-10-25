package main.java.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ExchangeRateClient {
    private final String apiKey;
    private final HttpClient httpClient;

    public ExchangeRateClient() {
        // Read API key from environment variable
        this.apiKey = System.getenv("EXCHANGE_RATE_API_KEY");
        if (this.apiKey == null || this.apiKey.isBlank()) {
            throw new IllegalStateException("Missing environment variable: EXCHANGE_RATE_API_KEY");
        }

        // Create a reusable HttpClient instance
        this.httpClient = HttpClient.newHttpClient();
    }

    /**
     * Fetches the conversion rate between two currencies using ExchangeRate API.
     *
     * @param base   the base currency (e.g., "USD")
     * @param target the target currency (e.g., "EUR")
     * @return conversion rate as a double
     */
    public double getConversionRate(String base, String target) throws IOException, InterruptedException {
        String url = String.format("https://v6.exchangerate-api.com/v6/%s/pair/%s/%s",
                apiKey, base.toUpperCase(), target.toUpperCase());

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new RuntimeException("HTTP error: " + response.statusCode() + " | " + response.body());
        }

        JsonObject json = JsonParser.parseString(response.body()).getAsJsonObject();

        if (!"success".equalsIgnoreCase(json.get("result").getAsString())) {
            String error = json.has("error-type") ? json.get("error-type").getAsString() : "Unknown error";
            throw new RuntimeException("API error: " + error);
        }

        return json.get("conversion_rate").getAsDouble();
    }

    /**
     * Fetches the converted amount between two currencies.
     *
     * @param base   the base currency (e.g., "USD")
     * @param target the target currency (e.g., "EUR")
     * @param amount amount to convert
     * @return converted amount as a double
     */
    public double convert(String base, String target, double amount) throws IOException, InterruptedException {
        String url = String.format("https://v6.exchangerate-api.com/v6/%s/pair/%s/%s/%.4f",
                apiKey, base.toUpperCase(), target.toUpperCase(), amount);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new RuntimeException("HTTP error: " + response.statusCode() + " | " + response.body());
        }

        JsonObject json = JsonParser.parseString(response.body()).getAsJsonObject();

        if (!"success".equalsIgnoreCase(json.get("result").getAsString())) {
            String error = json.has("error-type") ? json.get("error-type").getAsString() : "Unknown error";
            throw new RuntimeException("API error: " + error);
        }

        return json.get("conversion_result").getAsDouble();
    }
}