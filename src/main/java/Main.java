package main.java;

import main.java.service.CurrencyConverter;
import main.java.service.ExchangeRateClient;
import main.java.util.CurrencyFilter;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Scanner;

public class Main {

    private static final Scanner SC = new Scanner(System.in);

    public static void main(String[] args) {
        ExchangeRateClient client = new ExchangeRateClient();      // Usa EXCHANGE_RATE_API_KEY
        CurrencyConverter converter = new CurrencyConverter();

        System.out.println("========================================");
        System.out.println("        Currency Converter (Console)    ");
        System.out.println("========================================");
        System.out.println("Tip: Allowed codes: USD, ARS, BRL, CLP, COP, BOB\n");

        while (true) {
            try {
                String base = prompt("Enter BASE currency code (3 letters, e.g., USD) or 'X' to exit: ").toUpperCase();
                if ("X".equals(base)) break;

                String target = prompt("Enter TARGET currency code (e.g., MXN): ").toUpperCase();
                String amountStr = prompt("Enter amount to convert: ");
                BigDecimal amount = new BigDecimal(amountStr);

                // Validación simple (opcional: usa tu enum/ filtro)
                validateCode(base);
                validateCode(target);

                // Obtener tasa desde API
                double rateDouble = client.getConversionRate(base, target);
                BigDecimal rate = BigDecimal.valueOf(rateDouble);

                // Calcular y mostrar
                BigDecimal result = converter.convert(amount, rate);

                System.out.println("----------------------------------------");
                System.out.println(converter.format(amount) + " " + base + " @ rate " + converter.format(rate) +
                        " = " + converter.format(result) + " " + target);
                System.out.println("----------------------------------------\n");

            } catch (Exception e) {
                System.out.println("[Error] " + e.getMessage() + "\n");
            }
        }
        System.out.println("Goodbye!");
    }

    private static String prompt(String msg) {
        System.out.print(msg);
        return SC.nextLine().trim();
    }

    private static void validateCode(String code) {
        if (code == null || code.length() != 3) {
            throw new IllegalArgumentException("Invalid currency code: " + code);
        }
    }
}