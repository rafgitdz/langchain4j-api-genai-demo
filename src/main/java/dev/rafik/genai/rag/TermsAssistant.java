package dev.rafik.genai.rag;

import dev.langchain4j.service.SystemMessage;

public interface TermsAssistant {

    @SystemMessage("""
            You answer questions about the terms of use of the Acme Weather API.
            Use only the excerpts you are given. If the answer is not in them,
            say that you do not know.
            """)
    String chat(String question);
}
