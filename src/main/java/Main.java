package main.java;

import main.java.service.ExchangeRateClient;

public class Main {
    public static void main(String[] args) {
        try {
            ExchangeRateClient client = new ExchangeRateClient();
            double rate = client.getConversionRate("USD", "MXN");
            System.out.println("1 USD = " + rate + " MXN");

            double result = client.convert("USD", "MXN", 25.0);
            System.out.println("25 USD = " + result + " MXN");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
