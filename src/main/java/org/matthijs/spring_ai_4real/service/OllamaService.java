package org.matthijs.spring_ai_4real.service;

import org.matthijs.spring_ai_4real.model.Answer;
import org.matthijs.spring_ai_4real.model.Question;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
public class OllamaService {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(OllamaService.class);

    private final ChatModel chatModel;
    private final ChatClient chatClient;
    private final GameRulesService gameRulesService;

    public OllamaService(ChatClient.Builder chatClientBuilder, ChatModel chatModel, GameRulesService gameRulesService) {
        this.chatModel = chatModel;
        this.chatClient = chatClientBuilder.build();
        this.gameRulesService = gameRulesService;
    }

    @Value("classpath:/promptTemplates/nameOfTheGame.st")
    Resource nameOfTheGameTemplateResource;

    @Value("classpath:/promptTemplates/systemPromptTemplate.st")
    Resource promptTemplate;

    public String getTitle(Document doc) {

        var gameTitle = chatClient.prompt()
                .user(userSpec -> userSpec
                        .text(nameOfTheGameTemplateResource)
                        .param("document", doc.getText()))
                .call()
                .content();

        LOGGER.info("We hebben een titel gevonden: {}", gameTitle);
        return gameTitle;
    }

    public Answer askQuestion(Question question, SimpleVectorStore vectorStore) {
        var gameRules = gameRulesService.getRulesFor(
                question.gameTitle(), vectorStore);

        var answer = chatClient.prompt()
                .system(systemSpec -> systemSpec
                        .text(promptTemplate)
                        .param("gameTitle", question.gameTitle())
                        .param("rules", gameRules))
                .user(question.question())
                .call()
                .content();

        return new Answer(question.gameTitle(), answer);
    }


}
