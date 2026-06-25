package br.com.jonathan.budgeting;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BudgetingApplication {

    @Bean
    public ChatClient chatClient(ChatClient.Builder builder) {
        return  builder.defaultSystem("Você é um matemático")
                .build();
    }

    public static void main(String[] args) {
        SpringApplication.run(BudgetingApplication.class, args);
    }

}
