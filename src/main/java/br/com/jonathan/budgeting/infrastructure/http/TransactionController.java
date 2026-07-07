package br.com.jonathan.budgeting.infrastructure.http;

import br.com.jonathan.budgeting.application.ListTransactionByCategoryUseCase;
import br.com.jonathan.budgeting.application.PersistTransactionUseCase;
import br.com.jonathan.budgeting.application.output.TransactionOutput;
import br.com.jonathan.budgeting.domain.Category;
import br.com.jonathan.budgeting.infrastructure.http.request.TransactionRequest;
import br.com.jonathan.budgeting.infrastructure.http.response.TransactionResponse;
import org.springframework.ai.audio.transcription.TranscriptionModel;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final PersistTransactionUseCase persistTransactionUseCase;

    private final ListTransactionByCategoryUseCase listTransactionByCategoryUseCase;

    private final TranscriptionModel transcriptionModel;

    private final ChatClient chatClient;

    public TransactionController(

            PersistTransactionUseCase persistTransactionUseCase,

            ListTransactionByCategoryUseCase listTransactionByCategoryUseCase,

            TranscriptionModel transcriptionModel,

            @Value("classpath:/prompts/system-message.st")
            Resource systemPrompt,

            ChatClient.Builder builder

    ) throws IOException {
        this.persistTransactionUseCase = persistTransactionUseCase;
        this.listTransactionByCategoryUseCase = listTransactionByCategoryUseCase;
        this.transcriptionModel = transcriptionModel;
        this.chatClient = builder
                .defaultSystem(systemPrompt.getContentAsString(Charset.defaultCharset()))
                .defaultTools(
                        persistTransactionUseCase,
                        listTransactionByCategoryUseCase
                )
                .build();
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

    @PostMapping(value = "/ai",consumes = MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> transcription(
            @RequestParam("file") MultipartFile file
    ) {
        Resource resource = file.getResource();

        String userMessage = transcriptionModel.transcribe(resource);

        String result = chatClient.prompt().user(userMessage).call().content();

        return ResponseEntity.ok(result);
    }
}
