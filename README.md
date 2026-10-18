<div align="center">

# APIs and generative AI with LangChain4j

**From one LLM call to an agent that drives your API, in four small steps of plain Java.**<br>
The live demo of the talk given at Apidays Paris and France API.

[![CI](https://github.com/rafgitdz/langchain4j-api-genai-demo/actions/workflows/ci.yml/badge.svg)](https://github.com/rafgitdz/langchain4j-api-genai-demo/actions/workflows/ci.yml)
![Java 21](https://img.shields.io/badge/Java-21-orange)
![LangChain4j](https://img.shields.io/badge/LangChain4j-1.22-blue)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.1-6db33f)
![license](https://img.shields.io/badge/license-MIT-green)

[Quick start](#quick-start) · [The four steps](#the-four-steps) · [Watch the talk](#the-talk) · [Book this talk](#book-this-talk)

</div>

---

## Quick start

Runs 100 % locally with [Ollama](https://ollama.com), no API key:

```bash
ollama pull qwen3:14b
mvn spring-boot:run
```

Or with OpenAI:

```bash
export DEMO_PROVIDER=openai OPENAI_API_KEY=...   # never commit it
mvn spring-boot:run
```

Embeddings are computed in-process (all-MiniLM-L6-v2), so the RAG step never
sends your documents to an embedding API.

## The four steps

| Step | Endpoint | Code | What it shows |
|---|---|---|---|
| 1. Call a model | `GET /model?message=...` | [`basic`](src/main/java/dev/rafik/genai/basic) | An LLM behind a REST endpoint, in ten lines |
| 2. An AI service | `GET /assistant?conversation=alice&message=...` | [`assistant`](src/main/java/dev/rafik/genai/assistant) | A Java interface, a system message, memory per conversation |
| 3. The LLM calls your API | `GET /tools?message=...` | [`tools`](src/main/java/dev/rafik/genai/tools) | `@Tool` methods: the model picks the call and its arguments |
| 4. Ground it in a document | `GET /rag?question=...` | [`rag`](src/main/java/dev/rafik/genai/rag) | Retrieval-augmented generation on a terms-of-use document |

Try them:

```bash
curl -G localhost:8081/model --data-urlencode "message=Explain what an API is in one sentence"
curl -G localhost:8081/assistant --data-urlencode "message=Should I version my API in the URL?"
curl -G localhost:8081/tools --data-urlencode "message=Add 50 bananas with id 5, then list the fruit"
curl localhost:8081/products        # what the inventory really contains
curl -G localhost:8081/rag --data-urlencode "question=How many requests per day on the Pro plan?"
```

## What the demo is really about

**Check what the agent claims.** `/products` returns the real state of the
inventory, outside the model. Run step 3 with a small local model and compare:
while testing this repository, `qwen3:14b` answered "we have 4 fruit products,
bananas included" without ever calling `add`. A tool call is a fact you can
verify; a sentence from the model is not.

**Choose what the model may see.** The tools throw `ToolErrorVisibleToLlm` for
errors written for the model ("No product with id 42"). Every other exception
fails the call instead of sending its message, and whatever internal detail it
carries, to the LLM provider.

**Test the tools without a model.** They are ordinary Java methods:
[`InventoryApiTest`](src/test/java/dev/rafik/genai/tools/InventoryApiTest.java)
tests them like any API, and CI needs neither a model nor a key.

| Model (Ollama) | Tool calling in this demo |
|---|---|
| `qwen3:14b` | Calls tools, sometimes claims an action it did not perform |
| `mistral:7b` | Calls tools, fast, weaker at summarizing the result |
| `qwen2.5-coder:7b` | Prints the tool call as text instead of making it: fine for steps 1, 2 and 4 only |
| `gpt-4o-mini` (OpenAI) | Reliable, needs a key |

## Layout

```text
config/     one switch (demo.provider) between Ollama and OpenAI
basic/      step 1
assistant/  step 2
tools/      step 3: InventoryApi (the tools), InventoryAgent (the AI service)
rag/        step 4, on src/main/resources/documents/acme-weather-api-terms.md (fictitious)
```

## The talk

| Year | Event | Title | Language | Replay |
|---|---|---|---|---|
| 2024 | Apidays Paris | APIs and GenAI: Your Perfect Combo with LLM-Powered Frameworks | EN | [YouTube](https://www.youtube.com/watch?v=b0_vGhaHN-c) |
| 2024 | France API | APIs and Generative AI: Your Perfect Combo with LangChain4j | FR | [YouTube](https://youtu.be/9jW6UzrQ_tM) |

This repository was rebuilt in 2026 on LangChain4j 1.x and Spring Boot 4: the
APIs shown in the 2024 recordings (`ChatLanguageModel`, `generate`) have been
renamed (`ChatModel`, `chat`) since.

## Book this talk

Available as a keynote, a 2-hour meetup or a half-day hands-on workshop, in
French or English.

**[Request a quote](https://tally.so/r/Y5LRev)** · [LinkedIn](https://www.linkedin.com/in/rafik-ferroukh) · [YouTube](https://www.youtube.com/@TheUnBullshIT)

## License

[MIT](./LICENSE)
