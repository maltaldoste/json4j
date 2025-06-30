package de.malte.json4j;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.Delegate;

import java.util.Map;

/**
 * A JSON object that contains key/value pairs.
 */
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class JsonObject extends JsonElement {
    /**
     * The map that contains all the key/value pairs.
     */
    @Getter
    private @Delegate Map<String, JsonElement> map;
}
