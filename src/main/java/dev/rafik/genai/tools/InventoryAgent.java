package dev.rafik.genai.tools;

import dev.langchain4j.service.SystemMessage;

public interface InventoryAgent {

    @SystemMessage("""
            You manage a fruit and vegetable inventory through the tools you are given.
            Never invent a product or a quantity: if a tool fails, say so.
            Today is {{current_date}}.
            """)
    String chat(String message);
}
