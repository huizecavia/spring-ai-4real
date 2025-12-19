package org.matthijs.spring_ai_4real.service;

import org.springframework.ai.content.Media;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.stereotype.Service;

@Service
public class GameRulesService {

    public String getRulesFor(String gameName, SimpleVectorStore vectorStore) {

        var searchRequest = SearchRequest
                .builder()
                .query(gameName)
                .topK(1)
                .build();

        Document doc = vectorStore.similaritySearch(searchRequest).getFirst();

        if (doc.isText()) {
            String textContent = doc.getText();
            // Process text content
        } else {
            Media mediaContent = doc.getMedia();
            // Process media content
        }

        return ".....";

    }
}
