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
        var sourceIterator = new SourceIterator(source);
        return nextElement(sourceIterator);
    }

    private JsonElement nextElement(SourceIterator sourceIterator) throws InvalidJsonFormatError {
        sourceIterator.skipWhitespace();
        var startingChar = sourceIterator.next();
        return switch (startingChar) {
            case '"' -> new JsonString(consumeString(sourceIterator));
            case '[' -> consumeArray(sourceIterator);
            case '{' -> consumeObject(sourceIterator);
            default -> consumeBooleanOrNumber(sourceIterator, startingChar);
        };
    }

    // The opening " is already consumed
    private String consumeString(SourceIterator sourceIterator) throws InvalidJsonFormatError {
        var startingRow = sourceIterator.getRow();
        var startingCol = sourceIterator.getColumn();
        var buffer = new StringBuilder();
        boolean escaped = false;
        for (var character : sourceIterator) {
            if (character == '"' && !escaped) {
                return buffer.toString();
            }
            if (character == '\\' && !escaped) {
                escaped = true;
                continue;
            }
            if (escaped) {
                // TODO: implement escape sequences
                buffer.append(switch (character) {
                    case '"' -> '"';
                    case '\\' -> '\\';
                    default -> throw new UnsupportedOperationException("escape sequences not yet implemented");
                });
                escaped = false;
            } else {
                buffer.append(character);
            }
        }
        throw new InvalidJsonFormatError("unclosed string literal or key starts here", startingRow, startingCol);
    }

    // The opening [ is already consumed
    private JsonElement consumeArray(SourceIterator sourceIterator) throws InvalidJsonFormatError {
        return new JsonNull();
    }

    // The opening { is already consumed
    private JsonElement consumeObject(SourceIterator sourceIterator) throws InvalidJsonFormatError {
        return new JsonNull();
    }

    // The opening { is already consumed
    private JsonElement consumeBooleanOrNumber(SourceIterator sourceIterator, char startingChar) throws InvalidJsonFormatError {
        return new JsonNull();
    }
}
