package ai.spring.demo.ai.playground.client;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RequestMapping("demo/message")
@RestController
@AllArgsConstructor
public class MessageController {

    AssistantService service;

    @GetMapping(value = "chat/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> chatStream(@RequestParam String prompt) {
        return service.chat("1",prompt);
    }
}
