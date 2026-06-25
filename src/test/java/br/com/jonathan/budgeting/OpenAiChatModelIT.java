package br.com.jonathan.budgeting;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@EnabledIfEnvironmentVariable(named = "OPEN_AI_API_KEY",matches = ".+")
public class OpenAiChatModelIT {

    @Value("${spring.ai.openai.api-key}")
    private String apiKey;

    @Test
    void shouldPrintAPi(){
        assertThat(apiKey).isNotEmpty();
    }

    @Test
    void shouldReceiveResponseWhenChatIsCalled() {

        OpenAiChatOptions options = OpenAiChatOptions.builder()
                .apiKey(apiKey)
                .model("gpt-4o-mini")
                .temperature(0.8)
                .build();

        OpenAiChatModel chatModel = OpenAiChatModel.builder()
                .options(options).build();

        String called = chatModel
                .call("Olá open ia essa é a primeira vem que " +
                        "me conecto com você em uma aplicação Java + Spring AI me manda uma resposta bem calorosa");

        System.out.println(called);

        assertThat(called).isNotEmpty();
    }

}
