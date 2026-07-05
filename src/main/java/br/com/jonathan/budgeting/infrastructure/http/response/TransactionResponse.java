package br.com.jonathan.budgeting.infrastructure.http.response;

import br.com.jonathan.budgeting.application.output.TransactionOutput;

import java.math.BigDecimal;

public record TransactionResponse(
        String id,
        String category,
        String description,
        double amount
) {
    public static TransactionResponse from(TransactionOutput output) {
        return new TransactionResponse(
                output.id(),
                output.category(),
                output.description(),
                output.value()
        );
    }
}
