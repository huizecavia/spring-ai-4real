package org.matthijs.spring_ai_4real.service;

import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VectordbService {

    private final EmbeddingModel embeddingModel;

    public VectordbService(EmbeddingModel embeddingModel) {
        this.embeddingModel = embeddingModel;
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


//        String root = System.getProperty("user.dir");
//        String filepath = "/src/main/resources/365/";
//        String abspath = root + filepath;
//        Document document = new Document("Hello World!");
//
        embeddingModel.embed("Tekst kan ook");

//        List<Document> documents = FileSystemDocumentLoader.loadDocuments(abspath);
//        InMemoryEmbeddingStore<TextSegment> embeddingStore = new InMemoryEmbeddingStore<>();
//        EmbeddingStoreIngestor.ingest(documents, embeddingStore);
//        return embeddingStore.serializeToJson();


        return "vectordb";
    }
}
