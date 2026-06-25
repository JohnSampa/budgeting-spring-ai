package br.com.jonathan.budgeting;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@EnabledIfEnvironmentVariable(named = "OPEN_AI_API_KEY",matches = ".+")
public class OpenAiChatClientIT {


    @Autowired
    OpenAiChatModel openAiChatModel;

    @Test
    void shouldExecuteSumWhenPrompt(){
        ChatClient client = ChatClient.builder(openAiChatModel)
                .defaultSystem("Você é um matemático")
                .build();

        String response = client
                .prompt("Quanto é 5 vezes 3 mais menos 5, responda somente o resultado ")
                .call()
                .content();

        System.out.println(response);

        assertThat(response).contains("10");
    }

}
