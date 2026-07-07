package br.com.jonathan.budgeting.application;

import br.com.jonathan.budgeting.application.input.PersistTransactionInput;
import br.com.jonathan.budgeting.application.output.TransactionOutput;
import br.com.jonathan.budgeting.domain.Transaction;
import br.com.jonathan.budgeting.domain.TransactionRepository;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

@Service
public class PersistTransactionUseCase {

    private final TransactionRepository transactionRepository;

    public PersistTransactionUseCase(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Tool(name = "list-transactions-by-category",description = "Persiste uma nova transação financeira")
    public TransactionOutput execute(PersistTransactionInput input) {
        Transaction transaction = transactionRepository.save(
                new Transaction(
                        input.description(),
                        input.amount(),
                        input.category()
                )
        );

        return TransactionOutput.from(transaction);
    }
}
