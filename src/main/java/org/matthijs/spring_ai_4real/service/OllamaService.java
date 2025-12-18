package org.matthijs.spring_ai_4real.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.document.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
public class OllamaService {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(OllamaService.class);

    private final ChatModel chatModel;

    private final ChatClient chatClient;

    public OllamaService(ChatClient.Builder chatClientBuilder, ChatModel chatModel) {
        this.chatModel = chatModel;
        this.chatClient = chatClientBuilder.build();
    }

    @Value("classpath:/promptTemplates/nameOfTheGame.st")
    Resource nameOfTheGameTemplateResource;

    public String getTitle(Document doc) {

        var gameTitle = chatClient.prompt()
                .user(userSpec -> userSpec
                        .text(nameOfTheGameTemplateResource)
                        .param("document", doc.getText()))
                .call()
                .content();

        LOGGER.info("We hebben een titel gevonden: {}", gameTitle);
        return gameTitle; }
}
