package org.matthijs.spring_ai_4real.service;

import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class VectordbService {

    private final EmbeddingModel embeddingModel;

    private final OllamaService os;

    public VectordbService(EmbeddingModel embeddingModel, OllamaService os) {
        this.embeddingModel = embeddingModel;
        this.os = os;
    }

    public SimpleVectorStore getVectorStore() {
        String root = System.getProperty("user.dir");
        String filepath = "/src/main/resources/gameRules/"; // directory containing files
        File abspath = new File(root + filepath);

        SimpleVectorStore simpleVectorStore = SimpleVectorStore.builder(embeddingModel)
                .build();

        try {
            ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
            Resource[] resources = resolver.getResources("classpath*:/gameRules/*.txt");
            for(Resource resource : resources) {
                TextReader textReader = new TextReader(resource);
                List<Document> documents = textReader.get();
                simpleVectorStore.add(documents);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        simpleVectorStore.save(new File("vectordb.db"));
        return simpleVectorStore;
    }

}
