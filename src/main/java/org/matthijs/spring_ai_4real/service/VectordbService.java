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
import java.util.List;

@Service
public class VectordbService {

    private final EmbeddingModel embeddingModel;

    public VectordbService(EmbeddingModel embeddingModel) {
        this.embeddingModel = embeddingModel;
    }

    public SimpleVectorStore getVectorStore(String path) {

        SimpleVectorStore simpleVectorStore = SimpleVectorStore.builder(embeddingModel)
                .build();

        try {
            ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
            Resource[] resources = resolver.getResources(path);
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
