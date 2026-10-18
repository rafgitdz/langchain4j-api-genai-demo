package dev.rafik.genai.tools;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import dev.langchain4j.exception.ToolErrorVisibleToLlm;
import org.junit.jupiter.api.Test;

/** The tools are plain Java: test them like any API, without a model. */
class InventoryApiTest {

    private final InventoryApi inventory = new InventoryApi();

    @Test
    void starts_with_four_products_sorted_by_id() {
        assertThat(inventory.all()).extracting(Product::id).containsExactly(1, 2, 3, 4);
    }

    @Test
    void adds_a_product() {
        inventory.add(5, "Banana", "Fruit", 50);

        assertThat(inventory.byId(5)).isEqualTo(new Product(5, "Banana", "Fruit", 50));
    }

    @Test
    void refuses_a_duplicate_id() {
        assertThatThrownBy(() -> inventory.add(1, "Kiwi", "Fruit", 10))
                .isInstanceOf(ToolErrorVisibleToLlm.class);
    }

    @Test
    void updates_the_quantity_only() {
        inventory.updateQuantity(2, 10);

        assertThat(inventory.byId(2)).isEqualTo(new Product(2, "Tomato", "Vegetable", 10));
    }

    @Test
    void fails_clearly_on_an_unknown_product() {
        assertThatThrownBy(() -> inventory.byId(42))
                .isInstanceOf(ToolErrorVisibleToLlm.class)
                .hasMessageContaining("42");
    }
}
