package br.com.jonathan.budgeting;

import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.ai.openai.OpenAiAudioTranscriptionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@EnabledIfEnvironmentVariable(named = "OPEN_AI_API_KEY",matches = ".+")
public class OpenAiTranscriptionModelIT {

    @Autowired
    OpenAiAudioTranscriptionModel openAiTranscriptionModel;

    @ParameterizedTest
    @CsvSource({
            "audio-1.mp3, 25",
            "audio-2.mp3, 93",
            "audio-3.mp3, 18 reais",
    })
    void shouldContainsExpectedKeyWordsWhenAudioFilesAreProcess(
            String audioFileName,
            String expectedKeyWords
    ){
        ClassPathResource audioFile = new ClassPathResource("audio/" + audioFileName);

        String response = openAiTranscriptionModel.transcribe(audioFile);

        assertThat(response).contains(expectedKeyWords);
        System.out.println(response);
    }
}
