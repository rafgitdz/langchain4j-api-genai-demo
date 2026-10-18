package dev.rafik.genai.rag;

import static dev.langchain4j.data.document.loader.ClassPathDocumentLoader.loadDocument;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Step 4: retrieval-augmented generation. The document is split, embedded
 * in-process and stored in memory; each question retrieves the closest
 * excerpts and sends only those to the model.
 */
@RestController
class RagController {

    private final TermsAssistant assistant;

    RagController(ChatModel chatModel, EmbeddingModel embeddingModel) {
        Document terms = loadDocument("documents/acme-weather-api-terms.md");
        var store = new InMemoryEmbeddingStore<TextSegment>();
        EmbeddingStoreIngestor.builder()
                .documentSplitter(DocumentSplitters.recursive(300, 30))
                .embeddingModel(embeddingModel)
                .embeddingStore(store)
                .build()
                .ingest(terms);

        this.assistant = AiServices.builder(TermsAssistant.class)
                .chatModel(chatModel)
                .contentRetriever(EmbeddingStoreContentRetriever.builder()
                        .embeddingStore(store)
                        .embeddingModel(embeddingModel)
                        .maxResults(3)
                        .minScore(0.5)
                        .build())
                .build();
    }

    @GetMapping("/rag")
    String ask(@RequestParam String question) {
        return assistant.chat(question);
    }
}
