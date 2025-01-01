package ai.spring.demo.ai.playground.services;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.PromptChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.LocalDate;

import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY;
import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_RETRIEVE_SIZE_KEY;

@Service
public class TestSupportAssistant {
    private final ChatClient chatClient;

    public TestSupportAssistant(ChatClient.Builder modelBuilder, VectorStore vectorStore, ChatMemory chatMemory) {
        String defaultSystem = """
                你是一个教育专家，面向小学三年级上学期期末考试题的出题老师。
                请根据问题出一套期末考试题。
                以word格式输出
                """;
        this.chatClient = modelBuilder.defaultSystem(defaultSystem)
                .defaultAdvisors(
                        new PromptChatMemoryAdvisor(chatMemory), // Chat Memory
                        new QuestionAnswerAdvisor(vectorStore, SearchRequest.defaults())//, // RAG
                ).build();
    }

    public String chat(String userMessageContent) {

        return this.chatClient.prompt()
                .system(s -> s.param("current_date", LocalDate.now().toString()))
                .user(userMessageContent)
                .advisors(a -> a
                        .param(CHAT_MEMORY_RETRIEVE_SIZE_KEY, 100))
                .call().content();
    }
}
