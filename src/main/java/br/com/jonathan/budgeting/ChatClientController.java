package br.com.jonathan.budgeting;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ChatClientController {

    @Autowired
    private ChatClient client;

    @GetMapping("/chat")
    public ResponseEntity<String> sendMessage(
            @RequestParam("prompt") String prompt
    ){
        String result = client.prompt()
                .user(prompt)
                .call()
                .content();

        return ResponseEntity.ok(result);
    }
}

