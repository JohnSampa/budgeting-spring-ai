package br.com.jonathan.budgeting.infrastructure.persistence.repository;

import br.com.jonathan.budgeting.domain.Category;
import br.com.jonathan.budgeting.infrastructure.persistence.entity.TransactionEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface TransactionEntityRepository extends CrudRepository<TransactionEntity, UUID> {

    List<TransactionEntity> findAllByCategory(Category category);
}
