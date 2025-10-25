package main.java.model;

public class ExchangeRateResponse {
    private String result;
    private String base_code;
    private String target_code;
    private double conversion_rate;
    private double conversion_result; // present when you pass an amount

    public String getResult() { return result; }
    public String getBaseCode() { return base_code; }
    public String getTargetCode() { return target_code; }
    public double getConversionRate() { return conversion_rate; }
    public double getConversionResult() { return conversion_result; }

    @Override
    public String toString() {
        return "ExchangeRateResponse{" +
                "result='" + result + '\'' +
                ", base_code='" + base_code + '\'' +
                ", target_code='" + target_code + '\'' +
                ", conversion_rate=" + conversion_rate +
                ", conversion_result=" + conversion_result +
                '}';
    }
}