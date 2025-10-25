package main.java;

import main.java.service.ExchangeRateClient;
import main.java.service.CurrencyConverter;
import main.java.model.ConversionRecord;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner SC = new Scanner(System.in);

    public static void main(String[] args) {
        ExchangeRateClient client = new ExchangeRateClient();   // uses EXCHANGE_RATE_API_KEY
        CurrencyConverter converter = new CurrencyConverter();
        List<ConversionRecord> history = new ArrayList<>();

        System.out.println("""
                ****************************************************
                *        Welcome to the Currency Converter         *
                ****************************************************""");

        while (true) {
            printMenu();
            int option = readInt("Choose a valid option: ");

            try {
                switch (option) {
                    case 1 -> convert(client, converter, history, "USD", "ARS");
                    case 2 -> convert(client, converter, history, "ARS", "USD");
                    case 3 -> convert(client, converter, history, "USD", "BRL");
                    case 4 -> convert(client, converter, history, "BRL", "USD");
                    case 5 -> convert(client, converter, history, "USD", "COP");
                    case 6 -> convert(client, converter, history, "COP", "USD");
                    case 7 -> {
                        System.out.println("\nThanks for using the Currency Converter. Bye!");
                        return;
                    }
                    case 8 -> showHistory(history);
                    case 9 -> convertCustom(client, converter, history);
                    default -> System.out.println("[!] Invalid option. Please try again.\n");
                }
            } catch (Exception e) {
                System.out.println("[Error] " + e.getMessage() + "\n");
            }
        }
    }

    // ---------- Actions ----------

    private static void convert(ExchangeRateClient client,
                                CurrencyConverter converter,
                                List<ConversionRecord> history,
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

        history.add(new ConversionRecord(base, target, amount, result, rate));
    }

    private static void convertCustom(ExchangeRateClient client,
                                      CurrencyConverter converter,
                                      List<ConversionRecord> history) throws Exception {
        String base = readCurrency("Enter base currency (e.g., USD): ");
        String target = readCurrency("Enter target currency (e.g., EUR): ");
        BigDecimal amount = readAmount("Enter amount to convert (" + base + "): ");

        double rateDouble = client.getConversionRate(base, target);
        BigDecimal rate = BigDecimal.valueOf(rateDouble);

        BigDecimal result = converter.convert(amount, rate);

        System.out.println("-----------------------------------------------");
        System.out.println(converter.format(amount) + " " + base +
                " @ rate " + converter.format(rate) +
                " = " + converter.format(result) + " " + target);
        System.out.println("-----------------------------------------------\n");

        history.add(new ConversionRecord(base, target, amount, result, rate));
    }

    private static void showHistory(List<ConversionRecord> history) {
        if (history.isEmpty()) {
            System.out.println("No conversions yet.\n");
            return;
        }
        System.out.println("=========== Conversion History ===========");
        history.forEach(System.out::println);
        System.out.println("==========================================\n");
    }

    // ---------- Input helpers ----------

    private static void printMenu() {
        System.out.println("""
                1) US Dollar       =>  Argentine Peso   (USD -> ARS)
                2) Argentine Peso  =>  US Dollar        (ARS -> USD)
                3) US Dollar       =>  Brazilian Real   (USD -> BRL)
                4) Brazilian Real  =>  US Dollar        (BRL -> USD)
                5) US Dollar       =>  Colombian Peso   (USD -> COP)
                6) Colombian Peso  =>  US Dollar        (COP -> USD)
                7) Exit
                8) View conversion history
                9) Convert custom currencies (choose any pair)
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
            String s = SC.nextLine().trim().replace(',', '.');
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

    private static String readCurrency(String prompt) {
        while (true) {
            System.out.print(prompt);
            String code = SC.nextLine().trim().toUpperCase();
            if (code.length() == 3 && code.chars().allMatch(Character::isLetter)) {
                return code;
            }
            System.out.println("[!] Invalid currency code. Use 3 letters (e.g., USD, EUR, ARS).\n");
        }
    }
}
