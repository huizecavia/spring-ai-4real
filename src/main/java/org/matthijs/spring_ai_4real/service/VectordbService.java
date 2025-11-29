package org.matthijs.spring_ai_4real.service;

import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VectordbService {

    private final EmbeddingModel embedding;

    public VectordbService(EmbeddingModel embedding) {
        this.embedding = embedding;
    }


//    https://docs.spring.io/spring-ai/reference/api/vectordbs.html
//    @Autowired
//    VectorStore vectorStore;
//
//    void load(String sourceFile) {
//        JsonReader jsonReader = new JsonReader(new FileSystemResource(sourceFile),
//                "price", "name", "shortDescription", "description", "tags");
//        List<Document> documents = jsonReader.get();
//        this.vectorStore.add(documents);
//    }


    public String getVectorRespons() {


        String root = System.getProperty("user.dir");
        String filepath = "/src/main/resources/365/";
        String filename = "1-71.txt";
        String abspath = root + filepath + filename;
        Document d = new Document(abspath);
        embedding.embed(d);


        SimpleVectorStore vs = SimpleVectorStore.builder(embedding).build();
        return "vectordb";
    }
}
