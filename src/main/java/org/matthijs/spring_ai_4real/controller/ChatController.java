package org.matthijs.spring_ai_4real.controller;

//import org.matthijs.spring_ai_4real.dto.UserInput;
import org.matthijs.spring_ai_4real.dto.UserInput;
import org.matthijs.spring_ai_4real.service.VectordbService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {

    private static final Logger log = LoggerFactory.getLogger(ChatController.class);

    private final ChatClient chatClient;

    private final VectordbService vectordbService;

    public ChatController(ChatClient.Builder chatClientBuilder, VectordbService vectordbService) {
        this.chatClient = chatClientBuilder
                .build();
        this.vectordbService = vectordbService;
    }

//    https://docs.spring.io/spring-ai/reference/api/retrieval-augmented-generation.html
//    https://github.com/ollama/ollama/blob/main/docs/api.md#parameters-1
    @PostMapping("/v2/chats")
    public Object chatV2(@RequestBody UserInput userInput ) {

        log.info("userInput message : {} ", userInput);

        var systemMessage = "You are a helpful assistant. Answer the question in German.";

        var requestSpec = chatClient.prompt()
                .user(userInput.prompt())
                .system(systemMessage);

        log.info("requestSpec: {}", requestSpec);

        var responseSpec = requestSpec.call();
        log.info("responseSpec: {}", responseSpec);
        var content = responseSpec.content();
        log.info("content: {}", content);

        return content;
    }

    @GetMapping("/vectordb")
    public String vectordb() {

        return vectordbService.getVectorRespons();
    }
}
