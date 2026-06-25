package br.com.jonathan.budgeting.application.input;

import br.com.jonathan.budgeting.domain.Category;

public record PersistTransactionInput(
        String description,

        long amount,

        Category category
) {
}
