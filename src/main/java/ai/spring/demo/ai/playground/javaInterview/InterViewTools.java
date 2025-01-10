package ai.spring.demo.ai.playground.javaInterview;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;

import java.util.function.Function;

@Configuration
public class InterViewTools {
    @Autowired
    private InterViewService interViewService;

    public record InterViewRequest(String number,String name, int score, String evaluate){}

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public record InterViewRecord(String number, String name, int score, String interViewStatus,
                                  String evaluate,String email,String mp3path) {}

    @Bean
    @Description("更改面试结果")
    public Function<InterViewTools.InterViewRequest, String> changeInterView() {
        return request -> {
            interViewService.changeInterView(request.number(), request.name(), request.score(), request.evaluate());
            return "";
        };
    }

    @Bean
    @Description("获取面试结果")
    public Function<InterViewTools.InterViewRequest, InterViewTools.InterViewRecord> getInterviewDetails() {
        return request -> {
            try {
                return interViewService.getInterViewDetails(request.number(), request.name());
            }
            catch (Exception e) {
                return null;
            }
        };
    }
}
