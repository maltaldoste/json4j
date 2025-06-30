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
}
