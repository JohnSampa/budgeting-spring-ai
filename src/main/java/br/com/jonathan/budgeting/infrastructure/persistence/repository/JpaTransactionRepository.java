package br.com.jonathan.budgeting.infrastructure.persistence.repository;

import br.com.jonathan.budgeting.domain.Category;
import br.com.jonathan.budgeting.domain.Transaction;
import br.com.jonathan.budgeting.domain.TransactionRepository;
import br.com.jonathan.budgeting.infrastructure.persistence.entity.TransactionEntity;

import java.util.List;

public class JpaTransactionRepository implements TransactionRepository {

    private final TransactionEntityRepository transactionEntityRepository;

    public JpaTransactionRepository(TransactionEntityRepository transactionEntityRepository) {
        this.transactionEntityRepository = transactionEntityRepository;
    }

    @Override
    public Transaction save(Transaction transaction) {
        TransactionEntity transactionEntity = TransactionEntity.from(transaction);

        return transactionEntityRepository
                .save(transactionEntity)
                .toDomain();
    }

    @Override
    public List<Transaction> findAllByCategory(Category category) {
        return List.of();
    }
}
