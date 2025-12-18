package org.matthijs.spring_ai_4real.service;

import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
public class VectordbService {

    private final EmbeddingModel embedding;

    private final OllamaService os;

    public VectordbService(EmbeddingModel embedding, OllamaService os) {
        this.embedding = embedding;
        this.os = os;
    }

    public SimpleVectorStore getVectorStore() {
        String root = System.getProperty("user.dir");
        String filepath = "/src/main/resources/gameRules/"; // directory containing files
        File dir = new File(root + filepath);

        if (!dir.exists() || !dir.isDirectory()) {
            throw new IllegalStateException("Directory not found: " + dir.getAbsolutePath());
        }

        File[] files = dir.listFiles();
        List<Document> dlist = new ArrayList<>();
        if (files != null) {
            for (File f : files) {
                if (f.isFile()) {
                    dlist.add(new Document(f.getAbsolutePath()));
                }
            }
        }

        for (Document doc : dlist) {

            String title = os.getTitle(doc);
            doc.getMetadata().put("title", title);
            embedding.embed(doc);
        }

        SimpleVectorStore vs = SimpleVectorStore.builder(embedding).build();
        vs.add(dlist);
        vs.save(new File("vectordb.db"));

//        var similarDocs = vs.similaritySearch("identiteitsbewijs");
        return vs;
    }

}
