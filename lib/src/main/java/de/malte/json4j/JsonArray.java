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

    @Override
    public void format(JsonSpeller speller) {
        if (array.isEmpty()) {
            speller.append("[]");
            return;
        }
        speller.append('[');
        speller.indent();
        speller.newLine();
        int i = 0;
        for (var element : array) {
            element.format(speller);
            if (i != array.size() - 1) {
                speller.append(',');
                speller.newLine();
            }
            i++;
        }
        speller.outdent();
        speller.newLine();
        speller.append(']');
    }
}
