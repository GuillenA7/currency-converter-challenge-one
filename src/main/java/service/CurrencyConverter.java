package main.java.service;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CurrencyConverter {

    private static final int SCALE = 2; // 2 decimales para formato monetario
    private static final RoundingMode RM = RoundingMode.HALF_UP;

    /**
     * Converts an amount using a given rate with monetary precision.
     * amount and rate must be >= 0
     */
    public BigDecimal convert(BigDecimal amount, BigDecimal rate) {
        if (amount == null || rate == null) {
            throw new IllegalArgumentException("Amount and rate cannot be null");
        }
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }
        if (rate.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Rate must be positive");
        }
        return amount.multiply(rate).setScale(SCALE, RM);
    }

    /**
     * Utility to format a BigDecimal as string with 2 decimals.
     */
    public String format(BigDecimal value) {
        if (value == null) return "0.00";
        return value.setScale(SCALE, RM).toPlainString();
    }
}