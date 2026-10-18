package dev.rafik.genai.config;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.embedding.onnx.allminilml6v2.AllMiniLmL6V2EmbeddingModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import java.time.Duration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * One switch, {@code demo.provider}, chooses the model. The rest of the code
 * only sees {@link ChatModel}: changing provider changes no business code.
 */
@Configuration
class ModelConfiguration {

    @Bean
    ChatModel chatModel(
            @Value("${demo.provider}") String provider,
            @Value("${demo.ollama.base-url}") String ollamaBaseUrl,
            @Value("${demo.ollama.model}") String ollamaModel,
            @Value("${demo.openai.api-key}") String openAiApiKey,
            @Value("${demo.openai.model}") String openAiModel) {
        return switch (provider) {
            case "ollama" -> OllamaChatModel.builder()
                    .baseUrl(ollamaBaseUrl)
                    .modelName(ollamaModel)
                    .temperature(0.2)
                    .timeout(Duration.ofSeconds(120))
                    .build();
            case "openai" -> {
                if (openAiApiKey.isBlank()) {
                    throw new IllegalStateException("demo.provider=openai needs the OPENAI_API_KEY environment variable");
                }
                yield OpenAiChatModel.builder()
                        .apiKey(openAiApiKey)
                        .modelName(openAiModel)
                        .temperature(0.2)
                        .timeout(Duration.ofSeconds(60))
                        .build();
            }
            default -> throw new IllegalStateException("Unknown demo.provider: " + provider + " (expected ollama or openai)");
        };
    }

    @Bean
    EmbeddingModel embeddingModel() {
        return new AllMiniLmL6V2EmbeddingModel();
    }
}
