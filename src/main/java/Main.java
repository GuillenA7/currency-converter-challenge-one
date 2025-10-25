package main.java;

import main.java.service.CurrencyConverter;
import main.java.service.ExchangeRateClient;

import java.math.BigDecimal;
import java.util.Scanner;

public class Main {

    private static final Scanner SC = new Scanner(System.in);

    public static void main(String[] args) {
        ExchangeRateClient client = new ExchangeRateClient();   // reads EXCHANGE_RATE_API_KEY
        CurrencyConverter converter = new CurrencyConverter();

        while (true) {
            printMenu();
            int option = readInt("Choose a valid option: ");

            try {
                switch (option) {
                    case 1 -> convert(client, converter, "USD", "ARS");
                    case 2 -> convert(client, converter, "ARS", "USD");
                    case 3 -> convert(client, converter, "USD", "BRL");
                    case 4 -> convert(client, converter, "BRL", "USD");
                    case 5 -> convert(client, converter, "USD", "COP");
                    case 6 -> convert(client, converter, "COP", "USD");
                    case 7 -> {
                        System.out.println("\nThanks for using the Currency Converter. Bye!");
                        return;
                    }
                    default -> System.out.println("[!] Invalid option. Please try again.\n");
                }
            } catch (Exception e) {
                System.out.println("[Error] " + e.getMessage() + "\n");
            }
        }
    }

    private static void convert(ExchangeRateClient client, CurrencyConverter converter,
                                String base, String target) throws Exception {
        BigDecimal amount = readAmount("Enter the amount to convert (" + base + "): ");
        double rateDouble = client.getConversionRate(base, target);
        BigDecimal rate = BigDecimal.valueOf(rateDouble);

        BigDecimal result = converter.convert(amount, rate);

        System.out.println("-----------------------------------------------");
        System.out.println(converter.format(amount) + " " + base +
                " @ rate " + converter.format(rate) +
                " = " + converter.format(result) + " " + target);
        System.out.println("-----------------------------------------------\n");
    }

    private static void printMenu() {
        System.out.println("""
                ****************************************************
                *        Welcome to the Currency Converter         *
                ****************************************************
                1) US Dollar  =>  Argentine Peso   (USD -> ARS)
                2) Argentine Peso  =>  US Dollar   (ARS -> USD)
                3) US Dollar  =>  Brazilian Real   (USD -> BRL)
                4) Brazilian Real  =>  US Dollar   (BRL -> USD)
                5) US Dollar  =>  Colombian Peso   (USD -> COP)
                6) Colombian Peso  =>  US Dollar   (COP -> USD)
                7) Exit
                """);
    }

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = SC.nextLine().trim();
            try {
                return Integer.parseInt(s);
            } catch (NumberFormatException e) {
                System.out.println("[!] Please enter a number.\n");
            }
        }
    }

    private static BigDecimal readAmount(String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = SC.nextLine().trim().replace(",", ".");
            try {
                BigDecimal value = new BigDecimal(s);
                if (value.signum() < 0) {
                    System.out.println("[!] Amount cannot be negative.\n");
                    continue;
                }
                return value;
            } catch (NumberFormatException e) {
                System.out.println("[!] Invalid amount. Try again.\n");
            }
        }
    }
}