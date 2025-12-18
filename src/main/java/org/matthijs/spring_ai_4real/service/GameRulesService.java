package org.matthijs.spring_ai_4real.service;

import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class GameRulesService {

    public String getRulesFor(String gameName, SimpleVectorStore vectorStore) {

        var similarDocs = vectorStore.similaritySearch(SearchRequest.builder()
                .query(gameName)
                .topK(1)
                .build());

        return similarDocs.stream()
                .map(Document::getText)
                .collect(Collectors.joining(System.lineSeparator()));
    }
}
