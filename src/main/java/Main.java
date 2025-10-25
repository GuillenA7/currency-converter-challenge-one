package main.java;

import main.java.service.ExchangeRateClient;
import main.java.util.CurrencyFilter;

import java.util.Map;

public class Main {
    public static void main(String[] args) {
        try {
            ExchangeRateClient client = new ExchangeRateClient();

            // Choose your base (commonly USD)
            Map<String, Double> all = client.getLatestRates("USD");
            Map<String, Double> filtered = CurrencyFilter.filterAllowed(all);

            System.out.println("All rates count: " + all.size());
            System.out.println("Filtered (allowed) rates:");
            filtered.forEach((code, rate) ->
                    System.out.println("USD -> " + code + " : " + rate)
            );

            // Example: pick specific three for the challenge
            double ars = filtered.getOrDefault("ARS", 0.0);
            double brl = filtered.getOrDefault("BRL", 0.0);
            double cop = filtered.getOrDefault("COP", 0.0);

            System.out.println("\nSelected sample:");
            System.out.println("USD -> ARS: " + ars);
            System.out.println("USD -> BRL: " + brl);
            System.out.println("USD -> COP: " + cop);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}