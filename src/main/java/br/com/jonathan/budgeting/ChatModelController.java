package br.com.jonathan.budgeting;

import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ChatModelController {

    @Autowired
    private OpenAiChatModel openAiChatModel;

    @GetMapping("/chat-model")
    public ResponseEntity<String> sendMessage(
            @RequestParam("prompt") String prompt
    ){
        String result = openAiChatModel.call(prompt);

        return ResponseEntity.ok(result);
    }
}

