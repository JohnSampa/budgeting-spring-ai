package br.com.jonathan.budgeting;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.springframework.ai.openai.OpenAiAudioSpeechModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@EnabledIfEnvironmentVariable(named = "OPEN_AI_API_KEY",matches = ".+")
public class OpenAiSpeechModelIT {

    @Autowired
    OpenAiAudioSpeechModel openAiSpeechModel;

    @Test
    void shouldProduceAudioWhenTextIsProvided() throws IOException {

        byte[] response = openAiSpeechModel
                .call("O valor total do serviço ficou 90, deseja confirmar o pagamento");

        assertThat(response).hasSizeGreaterThan(1024);

        Path tempFile = Files.createTempFile("OPENAI_AUDIO_", ".mp3");

        Files.write(tempFile, response);

        System.out.println(tempFile.toAbsolutePath());
    }
}
