package de.malte.json4j;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The JSON null value.
 */
@EqualsAndHashCode(callSuper = false)
@Data
public class JsonNull extends JsonElement {
}
