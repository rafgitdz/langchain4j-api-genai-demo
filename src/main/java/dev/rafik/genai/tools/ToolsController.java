package dev.rafik.genai.tools;

import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.tool.ToolArgumentsErrorHandler;
import dev.langchain4j.service.tool.ToolExecutionErrorHandler;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/** Step 3: the LLM calls your API. Try "Add 50 bananas, then list the fruit". */
@RestController
class ToolsController {

    private final InventoryAgent agent;
    private final InventoryApi inventory;

    ToolsController(ChatModel chatModel, InventoryApi inventory) {
        this.inventory = inventory;
        this.agent = AiServices.builder(InventoryAgent.class)
                .chatModel(chatModel)
                .chatMemory(MessageWindowChatMemory.withMaxMessages(20))
                .tools(inventory)
                // The model sees bad arguments and can retry with better ones.
                .toolArgumentsErrorHandler(ToolArgumentsErrorHandler.sendExceptionMessageToLlm())
                // Only errors written for the model reach it: no stack trace, no internal detail.
                .toolExecutionErrorHandler(ToolExecutionErrorHandler.failInvocationUnlessVisibleToLlm())
                .build();
    }

    @GetMapping("/tools")
    String chat(@RequestParam String message) {
        return agent.chat(message);
    }

    /** The real state, to check what the agent claims. */
    @GetMapping("/products")
    List<Product> products() {
        return inventory.all();
    }
}
