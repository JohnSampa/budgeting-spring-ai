package br.com.jonathan.budgeting.application.input;

import br.com.jonathan.budgeting.domain.Category;
import org.springframework.ai.tool.annotation.ToolParam;

public record PersistTransactionInput(
        @ToolParam(description = "Descrição de um gasto")
        String description,

        @ToolParam(description = "Valor do gasto em centavos")
        long amount,

        @ToolParam(description = "Categoria de uma transação")
        Category category
) {
}
