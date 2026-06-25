package br.com.jonathan.budgeting;

import org.springframework.ai.audio.transcription.TranscriptionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@RestController
@RequestMapping("/api")
public class TranscriptionController {

    @Autowired
    private TranscriptionModel transcriptionModel;

        @PostMapping(value = "/transcription",consumes = MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> transcription(
            @RequestParam("file") MultipartFile file
    ) {
        Resource resource = file.getResource();

        String result = transcriptionModel.transcribe(resource);

        return ResponseEntity.ok(result);
    }
}
