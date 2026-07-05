package br.com.jonathan.budgeting.infrastructure.http;

import br.com.jonathan.budgeting.application.ListTransactionByCategoryUseCase;
import br.com.jonathan.budgeting.application.PersistTransactionUseCase;
import br.com.jonathan.budgeting.application.output.TransactionOutput;
import br.com.jonathan.budgeting.domain.Category;
import br.com.jonathan.budgeting.infrastructure.http.request.TransactionRequest;
import br.com.jonathan.budgeting.infrastructure.http.response.TransactionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final PersistTransactionUseCase persistTransactionUseCase;

    private final ListTransactionByCategoryUseCase listTransactionByCategoryUseCase;

    public TransactionController(PersistTransactionUseCase persistTransactionUseCase, ListTransactionByCategoryUseCase listTransactionByCategoryUseCase) {
        this.persistTransactionUseCase = persistTransactionUseCase;
        this.listTransactionByCategoryUseCase = listTransactionByCategoryUseCase;
    }

    @PostMapping
    public ResponseEntity<TransactionResponse> createTransaction(
            @RequestBody TransactionRequest request
    ) {
        TransactionOutput output = persistTransactionUseCase.execute(request.toInput());

        TransactionResponse response = TransactionResponse.from(output);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{category}")
    public ResponseEntity<List<TransactionResponse>> findAllByCategory(
            @PathVariable Category category
    ) {
        List<TransactionResponse> response = listTransactionByCategoryUseCase
                .execute(category)
                .stream()
                .map(TransactionResponse::from)
                .toList();

        return ResponseEntity.ok(response);
    }
}
