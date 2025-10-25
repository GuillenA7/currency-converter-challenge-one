package main.java;

import main.java.service.ExchangeRateClient;

public class Main {
    public static void main(String[] args) {
        try {
            ExchangeRateClient client = new ExchangeRateClient(); // uses EXCHANGE_RATE_API_KEY

            double rate = client.getConversionRate("USD", "MXN");
            System.out.println("Rate USD->MXN: " + rate);

            double converted = client.convert("USD", "MXN", 25.0);
            System.out.println("25 USD -> MXN: " + converted);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}