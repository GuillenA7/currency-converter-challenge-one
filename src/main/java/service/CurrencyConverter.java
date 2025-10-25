package main.java.service;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CurrencyConverter {
    private static final int SCALE = 2;
    private static final RoundingMode RM = RoundingMode.HALF_UP;

    public BigDecimal convert(BigDecimal amount, BigDecimal rate) {
        if (amount == null || rate == null)
            throw new IllegalArgumentException("Amount and rate cannot be null");
        if (amount.signum() < 0)
            throw new IllegalArgumentException("Amount cannot be negative");
        if (rate.signum() <= 0)
            throw new IllegalArgumentException("Rate must be positive");
        return amount.multiply(rate).setScale(SCALE, RM);
    }

    public String format(BigDecimal value) {
        if (value == null) return "0.00";
        return value.setScale(SCALE, RM).toPlainString();
    }
}