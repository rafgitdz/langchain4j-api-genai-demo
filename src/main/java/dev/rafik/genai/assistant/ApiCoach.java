package dev.rafik.genai.assistant;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;

/**
 * Step 2: an AI service. A Java interface, a system message, and LangChain4j
 * writes the implementation.
 */
public interface ApiCoach {

    @SystemMessage("""
            You are an API design coach. You answer in the language of the question,
            in five sentences at most, and you always end with one concrete next step.
            """)
    String chat(@MemoryId String conversationId, @UserMessage String message);
}
