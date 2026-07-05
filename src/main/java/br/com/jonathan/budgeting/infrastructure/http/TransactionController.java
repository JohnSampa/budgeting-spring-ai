package br.com.jonathan.budgeting.infrastructure.http;

import br.com.jonathan.budgeting.application.PersistTransactionUseCase;
import br.com.jonathan.budgeting.application.output.TransactionOutput;
import br.com.jonathan.budgeting.infrastructure.http.request.TransactionRequest;
import br.com.jonathan.budgeting.infrastructure.http.response.TransactionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final PersistTransactionUseCase persistTransactionUseCase;

    public TransactionController(PersistTransactionUseCase persistTransactionUseCase) {
        this.persistTransactionUseCase = persistTransactionUseCase;
    }

    @PostMapping
    public ResponseEntity<TransactionResponse> createTransaction(
            @RequestBody TransactionRequest request
    ) {
        TransactionOutput output = persistTransactionUseCase.execute(request.toInput());

        TransactionResponse response = TransactionResponse.from(output);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
