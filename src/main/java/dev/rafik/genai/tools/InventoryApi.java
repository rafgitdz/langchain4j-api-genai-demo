package dev.rafik.genai.tools;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import dev.langchain4j.exception.ToolErrorVisibleToLlm;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;

/**
 * Step 3: an ordinary API. The {@link Tool} descriptions are all the model
 * knows about it: it decides on its own which method to call, and with what.
 *
 * Errors meant for the model are thrown as {@link ToolErrorVisibleToLlm}: any
 * other exception fails the call instead of leaking its message to the LLM.
 */
@Service
public class InventoryApi {

    private final Map<Integer, Product> products = new ConcurrentHashMap<>();

    public InventoryApi() {
        add(1, "Apple", "Fruit", 322);
        add(2, "Tomato", "Vegetable", 449);
        add(3, "Strawberry", "Fruit", 122);
        add(4, "Pear", "Fruit", 113);
    }

    @Tool("Lists every product in stock")
    public List<Product> all() {
        return products.values().stream().sorted((a, b) -> Integer.compare(a.id(), b.id())).toList();
    }

    @Tool("Finds a product by its identifier")
    public Product byId(@P("product identifier") int id) {
        Product product = products.get(id);
        if (product == null) {
            throw ToolErrorVisibleToLlm.from("No product with id " + id);
        }
        return product;
    }

    @Tool("Adds a product. If the user gives no quantity, use 100")
    public Product add(int id, String name, @P("Fruit or Vegetable") String category, int quantity) {
        if (products.containsKey(id)) {
            throw ToolErrorVisibleToLlm.from("A product with id " + id + " already exists");
        }
        Product product = new Product(id, name, category, quantity);
        products.put(id, product);
        return product;
    }

    @Tool("Changes the quantity in stock of a product")
    public Product updateQuantity(int id, int quantity) {
        Product current = byId(id);
        Product updated = new Product(id, current.name(), current.category(), quantity);
        products.put(id, updated);
        return updated;
    }

    @Tool("Deletes a product by its identifier")
    public void delete(int id) {
        if (products.remove(id) == null) {
            throw ToolErrorVisibleToLlm.from("No product with id " + id);
        }
    }
}
