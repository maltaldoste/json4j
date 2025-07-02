package de.malte.json4j;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Delegate;

import java.util.Map;

/**
 * A JSON object that contains key/value pairs.
 */
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
public class JsonObject extends JsonElement {
    /**
     * The map that contains all the key/value pairs.
     */
    @Getter
    private @Delegate Map<String, JsonElement> map;

    @Override
    public JsonObject asObject() {
        return this;
    }


    @Override
    public void format(JsonSpeller speller) {
        if (map.isEmpty()) {
            speller.append("{}");
            return;
        }
        speller.append('{');
        speller.indent();
        speller.newLine();
        int i = 0;
        for (var member : map.entrySet()) {
            speller.append(member.getKey());
            speller.append(": ");
            member.getValue().format(speller);
            if (i != map.size() - 1) {
                speller.append(',');
                speller.newLine();
            }
            i++;
        }
        speller.outdent();
        speller.newLine();
        speller.append('}');
    }
}
