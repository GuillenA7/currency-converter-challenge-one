public record ExchangeResponse(
        String result,
        String base_code,
        String target_code,
        Double conversion_rate,
        Double conversion_result,
        String error_type
) {}