package org.matthijs.spring_ai_4real.controller;

//import org.matthijs.spring_ai_4real.dto.UserInput;
import jakarta.validation.Valid;
import org.matthijs.spring_ai_4real.dto.UserInput;
import org.matthijs.spring_ai_4real.model.Answer;
import org.matthijs.spring_ai_4real.model.Question;
import org.matthijs.spring_ai_4real.service.GameRulesService;
import org.matthijs.spring_ai_4real.service.OllamaService;
import org.matthijs.spring_ai_4real.service.VectordbService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.web.bind.annotation.*;

@RestController
public class ChatController {

    private static final Logger log = LoggerFactory.getLogger(ChatController.class);

    private final ChatClient chatClient;
    private final ChatModel chatModel;
    private final VectordbService vectordbService;
    private final GameRulesService gs;
    private final OllamaService os;

    public ChatController(ChatClient.Builder chatClientBuilder, ChatModel chatModel, VectordbService vectordbService, GameRulesService gs, OllamaService os) {
        this.gs = gs;
        this.chatClient = chatClientBuilder
                .build();
        this.chatModel = chatModel;
        this.vectordbService = vectordbService;
        this.os = os;
    }

    @PostMapping("/v2/chats")
    public Object chatV2(@RequestBody UserInput userInput ) {

        log.info("userInput message : {} ", userInput);
        return ChatClient.builder(chatModel)
                .build().prompt()
                .user(userInput.prompt())
                .advisors(
                        QuestionAnswerAdvisor.builder(vectordbService.getVectorStore()).build())
                .call()
                .chatResponse();
    }

    @GetMapping("/gamerules")
    public String getGameRules(@RequestParam String gameName) {
        return gs.getRulesFor(gameName, vectordbService.getVectorStore());
    }

    @PostMapping(path="/ask", produces="application/json")
    public Answer ask(@RequestBody @Valid Question question) {
        return os.askQuestion(question, vectordbService.getVectorStore());
    }
}
