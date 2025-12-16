package org.matthijs.spring_ai_4real.service;

import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
public class VectordbService {

    private final EmbeddingModel embedding;

    public VectordbService(EmbeddingModel embedding) {
        this.embedding = embedding;
    }




    public SimpleVectorStore getVectorRespons() {
        String root = System.getProperty("user.dir");
        String filepath = "/src/main/resources/rag-it/";
        String filename = "pan.txt";
        String abspath = root + filepath + filename;
        Document d = new Document(abspath);
        List<Document> dlist = List.of(d);

        var b = embedding.embed(d);
        SimpleVectorStore vs = SimpleVectorStore.builder(embedding).build();
        vs.add(dlist);
        vs.save(new File("vectordb.db"));
        vs.similaritySearch("vraag maar wat");
        return vs;
    }
}
