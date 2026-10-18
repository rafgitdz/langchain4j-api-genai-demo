package dev.rafik.genai.assistant;

import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.service.AiServices;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/** Step 2: system message and per-conversation memory. */
@RestController
class AssistantController {

    private final ApiCoach coach;

    AssistantController(ChatModel chatModel) {
        this.coach = AiServices.builder(ApiCoach.class)
                .chatModel(chatModel)
                .chatMemoryProvider(id -> MessageWindowChatMemory.withMaxMessages(10))
                .build();
    }

    @GetMapping("/assistant")
    String chat(@RequestParam(defaultValue = "demo") String conversation,
                @RequestParam String message) {
        return coach.chat(conversation, message);
    }
}
