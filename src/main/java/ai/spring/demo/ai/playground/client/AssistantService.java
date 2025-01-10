package ai.spring.demo.ai.playground.client;

import ai.spring.demo.ai.playground.javaInterview.JavaAssistant;
import ai.spring.demo.ai.playground.services.CustomerSupportAssistant;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;

import reactor.core.publisher.Flux;

@BrowserCallable
@AnonymousAllowed
public class AssistantService {

    private final CustomerSupportAssistant agent;
    private final JavaAssistant interViewAgent;

    public AssistantService(CustomerSupportAssistant agent,JavaAssistant interViewAgent) {

        this.agent = agent;
        this.interViewAgent = interViewAgent;
    }

    public Flux<String> chat(String chatId, String userMessage) {
        return agent.chat(chatId, userMessage);
    }

    public Flux<String> interViewChat(String chatId, String userMessage)  {
        return interViewAgent.chat(chatId, userMessage);
    }

}
