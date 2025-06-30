package de.malte.json4j;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * A JSON string value.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class JsonString extends JsonElement {
    /**
     * The value.
     */
    private String value;
}
