package br.com.jonathan.budgeting.application;

import br.com.jonathan.budgeting.application.output.TransactionOutput;
import br.com.jonathan.budgeting.domain.Category;
import br.com.jonathan.budgeting.domain.TransactionRepository;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListTransactionByCategoryUseCase {

    private final TransactionRepository transactionRepository;

    public ListTransactionByCategoryUseCase(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Tool(name = "persist-transaction",description = "Lista transações financeiras por categoria ")
    public List<TransactionOutput> execute(
            @ToolParam(description = "Categoria de uma transação")
            Category category
    ) {
        return transactionRepository.findAllByCategory(category)
                .stream()
                .map(TransactionOutput::from)
                .toList();
    }
}
