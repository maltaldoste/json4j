package de.malte.json4j;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * A JSON string value.
 */
@EqualsAndHashCode
@Data
@AllArgsConstructor
public class JsonString extends JsonElement {
    /**
     * The value.
     */
    private String value;

    @Override
    public void format(JsonSpeller speller) {
        speller.appendStringLiteral(value);
    }
}
