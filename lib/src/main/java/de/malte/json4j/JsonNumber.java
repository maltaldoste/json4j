package de.malte.json4j;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * A JSON number value with arbitrary precision.
 * @see BigDecimal
 */
@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor
public class JsonNumber extends JsonElement {
    /**
     * The value.
     */
    private BigDecimal value;

    @Override
    public void format(JsonSpeller speller) {
        // FIXME: Does this always produce the correct result?
        speller.append(value.toString());
    }
}
