package de.malte.json4j;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * A JSON array which contains a linear sequence of elements.
 */
@AllArgsConstructor
public class JsonArray extends JsonElement {
    /**
     * The array of elements.
     */
    @Getter
    private List<JsonElement> array;
}
