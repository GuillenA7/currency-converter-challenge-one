package main;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ClienteHttp {
    public static void main(String[] args) throws IOException, InterruptedException {
        // 1️⃣ Crear el cliente
        HttpClient cliente = HttpClient.newHttpClient();

        // 2️⃣ Definir la URL con tu API Key y monedas
        String apiKey = "c75535408358a1ce70d7f107";  // ⚠️ reemplázala o carga desde archivo .properties
        String url = "https://v6.exchangerate-api.com/v6/" + apiKey + "/pair/USD/ARS";

        // 3️⃣ Crear la solicitud HTTP
        HttpRequest solicitud = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET() // tipo de solicitud
                .build();

        // 4️⃣ Enviar la solicitud y recibir la respuesta
        HttpResponse<String> respuesta = cliente.send(solicitud, HttpResponse.BodyHandlers.ofString());

        // 5️⃣ Imprimir el cuerpo de la respuesta (JSON)
        System.out.println("Código de estado: " + respuesta.statusCode());
        System.out.println("Respuesta JSON:\n" + respuesta.body());
    }
}
