import java.net.URI;
import java.net.http.*;
import com.google.gson.Gson;

public class ExchangeClient {
    private final String apiKey;
    private final HttpClient http = HttpClient.newHttpClient();
    private final Gson gson = new Gson();

    public ExchangeClient(String apiKey) {
        this.apiKey = apiKey;
    }

    public ExchangeResponse pair(String base, String target, Double amount) {
        String baseUrl = "https://v6.exchangerate-api.com/v6/" + apiKey + "/pair/";
        String url = amount == null
                ? baseUrl + base + "/" + target
                : baseUrl + base + "/" + target + "/" + amount;

        try {
            HttpRequest req = HttpRequest.newBuilder().uri(URI.create(url)).build();
            HttpResponse<String> res = http.send(req, HttpResponse.BodyHandlers.ofString());
            ExchangeResponse er = gson.fromJson(res.body(), ExchangeResponse.class);

            if (!"success".equalsIgnoreCase(er.result())) {
                throw new RuntimeException("API error: " + er.error_type());
            }
            return er;
        } catch (Exception e) {
            throw new RuntimeException("Failed request: " + e.getMessage(), e);
        }
    }
}
