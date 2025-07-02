package de.malte.json4j;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * A JSON string value.
 */
@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor
public class JsonString extends JsonElement {
    /**
     * The value.
     */
    private String value;

    @Override
    public JsonString asString() {
        return this;
    }


    @Override
    public void format(JsonSpeller speller) {
        speller.appendStringLiteral(value);
    }
}
