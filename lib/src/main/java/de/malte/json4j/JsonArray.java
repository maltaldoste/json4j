package de.malte.json4j;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Delegate;

import java.util.List;

/**
 * A JSON array which contains a linear sequence of elements.
 */
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
public class JsonArray extends JsonElement {
    /**
     * The array of elements.
     */
    @Getter
    private @Delegate List<JsonElement> array;
}
