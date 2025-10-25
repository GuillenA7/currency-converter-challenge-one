package main.java.util;

import main.java.model.Currency;

import java.util.EnumSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public final class CurrencyFilter {

    private static final Set<Currency> ALLOWED = EnumSet.of(
            Currency.USD, Currency.ARS, Currency.BRL, Currency.CLP, Currency.COP, Currency.BOB
    );

    private CurrencyFilter() {}

    public static boolean isAllowed(String code) {
        try {
            return ALLOWED.contains(Currency.valueOf(code.toUpperCase()));
        } catch (IllegalArgumentException ex) {
            return false;
        }
    }

    /**
     * Returns a filtered map containing only allowed currency codes.
     */
    public static Map<String, Double> filterAllowed(Map<String, Double> rates) {
        return rates.entrySet().stream()
                .filter(e -> isAllowed(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}