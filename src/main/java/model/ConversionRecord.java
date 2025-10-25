package main.java.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ConversionRecord {
    private final String fromCurrency;
    private final String toCurrency;
    private final BigDecimal amount;
    private final BigDecimal convertedAmount;
    private final BigDecimal rate;
    private final LocalDateTime timestamp;

    public ConversionRecord(String fromCurrency, String toCurrency,
                            BigDecimal amount, BigDecimal convertedAmount, BigDecimal rate) {
        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;
        this.amount = amount;
        this.convertedAmount = convertedAmount;
        this.rate = rate;
        this.timestamp = LocalDateTime.now();
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return String.format("[%s] %s %.2f -> %s %.2f (rate: %.4f)",
                timestamp.format(formatter),
                fromCurrency, amount,
                toCurrency, convertedAmount,
                rate);
    }
}