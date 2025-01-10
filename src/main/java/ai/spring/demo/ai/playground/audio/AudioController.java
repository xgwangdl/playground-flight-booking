package ai.spring.demo.ai.playground.audio;

import ai.spring.demo.ai.playground.audio.services.AudioService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class AudioController {
    private final AudioService audioService;

    @GetMapping(value = "/talk")
    public String talktalk(@RequestParam String title,@RequestParam String question) {
        return  audioService.getSpeech(title,question);
    }
}
