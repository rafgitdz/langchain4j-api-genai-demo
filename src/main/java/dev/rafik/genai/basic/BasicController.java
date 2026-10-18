package dev.rafik.genai.basic;

import dev.langchain4j.model.chat.ChatModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/** Step 1: an LLM behind a REST endpoint, nothing more. */
@RestController
class BasicController {

    private final ChatModel chatModel;

    BasicController(ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @GetMapping("/model")
    String ask(@RequestParam(defaultValue = "Explain what an API is in one sentence.") String message) {
        return chatModel.chat(message);
    }
}
