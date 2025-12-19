package org.matthijs.spring_ai_4real.service;

import org.matthijs.spring_ai_4real.model.Answer;
import org.matthijs.spring_ai_4real.model.GipGptQuestion;
import org.matthijs.spring_ai_4real.model.Question;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
public class GipGptService {
    private static final Logger LOGGER =
            LoggerFactory.getLogger(GameRulesService.class);

    private final ChatClient chatClient;

    public GipGptService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @Value("classpath:/promptTemplates/gipGptTemplate.st")
    Resource gipGptTemplate;

    public Answer askGipGpt(GipGptQuestion question, SimpleVectorStore vectorStore) {
        var articles = getArticles(
                question.question(), vectorStore);

        var answer = chatClient.prompt()
                .system(systemSpec -> systemSpec
                        .text(gipGptTemplate)
                        .param("articles", articles))
                .user(question.question())
                .call()
                .content();

        return new Answer(question.question(), answer);
    }

    public String getArticles(String question, SimpleVectorStore vectorStore) {

        var searchRequest = SearchRequest
                .builder()
                .query(question)
                .topK(1)
                .build();

        Document doc = vectorStore.similaritySearch(searchRequest).getFirst();

        return doc.getText();
    }
}
