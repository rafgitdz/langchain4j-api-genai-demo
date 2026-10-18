package dev.rafik.genai;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Starts the whole application: model wiring, AI services, and RAG ingestion
 * with in-process embeddings. Needs no API key and no running model, because
 * no question is asked.
 */
@SpringBootTest
class GenAiDemoApplicationTest {

    @Test
    void context_loads() {
    }
}
