package br.com.jonathan.budgeting.infrastructure.http.request;

import br.com.jonathan.budgeting.application.input.PersistTransactionInput;
import br.com.jonathan.budgeting.domain.Category;

public record TransactionRequest(
        String description,
        Category category,
        long amount
) {
    public PersistTransactionInput toInput() {
        return new PersistTransactionInput(
                description,
                amount,
                category
        );
    }
}
