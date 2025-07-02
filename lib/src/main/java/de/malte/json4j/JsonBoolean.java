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

    @Override
    public JsonBoolean asBoolean() {
        return this;
    }

    @Override
    public void format(JsonSpeller speller) {
        speller.append(value ? "true" : "false");
    }
}
