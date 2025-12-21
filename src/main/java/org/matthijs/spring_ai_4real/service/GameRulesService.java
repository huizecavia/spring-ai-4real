package org.matthijs.spring_ai_4real.service;

import org.matthijs.spring_ai_4real.model.Answer;
import org.matthijs.spring_ai_4real.model.Question;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
public class GameRulesService {

    private final ChatClient chatClient;

     public GameRulesService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @Value("classpath:/promptTemplates/systemPromptTemplate.st")
    Resource promptTemplate;

    public Answer askQuestion(Question question, SimpleVectorStore vectorStore) {
        var gameRules = getRulesFor(
                question.gameTitle(), vectorStore);

        var answer = chatClient.prompt()
                .system(systemSpec -> systemSpec
                        .text(promptTemplate)
                        .param("gameTitle", question.gameTitle())
                        .param("rules", gameRules))
                .user(question.question())
                .call()
                .content();

//        vervolgstap: gebruik QuestionAnswerAdvisor ipv parameters
//        https://learning.oreilly.com/library/view/spring-ai-in/9781633436114/Text/chapter-4.html#p225

        return new Answer(question.gameTitle(), answer);
    }

    public String getRulesFor(String gameName, SimpleVectorStore vectorStore) {

        var searchRequest = SearchRequest
                .builder()
                .query(gameName)
                .topK(3)
                .build();

        Document doc = vectorStore.similaritySearch(searchRequest).getFirst();

        return doc.getText();
    }
}
