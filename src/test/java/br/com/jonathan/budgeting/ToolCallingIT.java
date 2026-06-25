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
public class ToolCallingIT {

    @Autowired
    OpenAiChatModel openAiChatModel;

    static class MathTools{

        @Tool(description = "Soma dois números inteiros, a e b")
        public int sum(int a, int b){
            return a+b;
        }

        @Tool(description = "Subtrai dois números inteiros, a e b ")
        public int subtract(int a, int b){
            return a-b;
        }
    }

    @Test
    void shouldExecuteSumWhenPrompt(){
        ChatClient client = ChatClient.builder(openAiChatModel)
                .defaultTools(new MathTools())
                .defaultSystem("Você é um matemático")
                .build();

        String response = client
                .prompt("Quanto é 5 mais 3 mais menos 5, responda somente o resultado")
                .call()
                .content();

        System.out.println(response);

        assertThat(response).contains("3");
    }
    
}
