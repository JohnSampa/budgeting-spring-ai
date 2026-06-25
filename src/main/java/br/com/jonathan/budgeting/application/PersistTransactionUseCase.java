package br.com.jonathan.budgeting.application;

import br.com.jonathan.budgeting.application.input.PersistTransactionInput;
import br.com.jonathan.budgeting.application.output.TransactionOutput;
import br.com.jonathan.budgeting.domain.Transaction;
import br.com.jonathan.budgeting.domain.TransactionRepository;

public class PersistTransactionUseCase {

    private final TransactionRepository transactionRepository;

    public PersistTransactionUseCase(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

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
