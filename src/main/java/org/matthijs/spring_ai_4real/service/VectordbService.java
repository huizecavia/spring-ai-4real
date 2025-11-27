package org.matthijs.spring_ai_4real.service;

import org.springframework.ai.document.Document;
import org.springframework.ai.reader.JsonReader;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VectordbService {

//    https://docs.spring.io/spring-ai/reference/api/vectordbs.html

    @Autowired
    VectorStore vectorStore;

    void load(String sourceFile) {
        JsonReader jsonReader = new JsonReader(new FileSystemResource(sourceFile),
                "price", "name", "shortDescription", "description", "tags");
        List<Document> documents = jsonReader.get();
        this.vectorStore.add(documents);
    }
}
