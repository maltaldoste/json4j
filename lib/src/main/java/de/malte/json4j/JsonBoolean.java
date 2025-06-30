package de.malte.json4j;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * A JSON boolean value.
 */
@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor
public class JsonBoolean extends JsonElement {
    /**
     * The value.
     */
    private boolean value;
}
