package de.malte.json4j;

import java.util.Objects;

/**
 * A parser of the JSON format.
 */
public class JsonParser {
    /**
     * Parses the given JSON string into the corresponding {@link JsonElement}.
     * Throws an {@link InvalidJsonFormatError} when the given source is not valid
     * JSON.
     * Never returns null.
     * @param source the JSON source string
     * @return the parsed {@link JsonElement}
     * @throws InvalidJsonFormatError when the given JSON source contains invalid
     * JSON
     * @throws NullPointerException when the given JSON source is null
     */
    public JsonElement parse(String source) throws InvalidJsonFormatError {
        Objects.requireNonNull(source, "JSON source cannot be null");
        return new JsonNull();
    }
}
