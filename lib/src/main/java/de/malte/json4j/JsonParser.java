package de.malte.json4j;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.NoSuchElementException;
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
        var startingRow = sourceIterator.getRow();
        var startingCol = sourceIterator.getColumn();
        var elements = new ArrayList<JsonElement>();
        sourceIterator.skipWhitespace();
        if (sourceIterator.peek() == ']') {
            sourceIterator.next();
            return new JsonArray(elements);
        }
        while (sourceIterator.hasNext()) {
            elements.add(nextElement(sourceIterator));
            sourceIterator.skipWhitespace();
            var next = sourceIterator.next();
            if (next == ']') {
                return new JsonArray(elements);
            } else if (next != ',') {
                throw new InvalidJsonFormatError("expected comma here between array elements", sourceIterator.getRow(), sourceIterator.getColumn());
            }
            sourceIterator.skipWhitespace();
        }
        throw new InvalidJsonFormatError("unclosed array starting here", startingRow, startingCol);
    }

    // The opening { is already consumed
    private JsonElement consumeObject(SourceIterator sourceIterator) throws InvalidJsonFormatError {
        var startingRow = sourceIterator.getRow();
        var startingCol = sourceIterator.getColumn();
        var map = new LinkedHashMap<String, JsonElement>();
        sourceIterator.skipWhitespace();
        if (sourceIterator.peek() == '}') {
            sourceIterator.next();
            return new JsonObject(map);
        }
        while (sourceIterator.hasNext()) {
            if (sourceIterator.next() != '"')
                throw new InvalidJsonFormatError("expected quoted key in object", sourceIterator.getRow(), sourceIterator.getColumn());
            var key = consumeString(sourceIterator);
            sourceIterator.skipWhitespace();
            if (sourceIterator.next() != ':')
                throw new InvalidJsonFormatError("expected colon : after object member key", sourceIterator.getRow(), sourceIterator.getColumn());
            map.put(key, nextElement(sourceIterator));
            sourceIterator.skipWhitespace();
            var next = sourceIterator.next();
            if (next == '}') {
                return new JsonObject(map);
            } else if (next != ',') {
                throw new InvalidJsonFormatError("expected comma here between object members", sourceIterator.getRow(), sourceIterator.getColumn());
            }
            sourceIterator.skipWhitespace();
        }
        throw new InvalidJsonFormatError("unclosed object starting here", startingRow, startingCol);
    }

    private JsonElement consumeBooleanOrNumber(SourceIterator sourceIterator, char startingChar) throws InvalidJsonFormatError {
        var startingRow = sourceIterator.getRow();
        var startingCol = sourceIterator.getColumn();
        if ((startingChar >= '0' && startingChar <= '9') || startingChar == '-' || startingChar == '+') {
            return consumeNumber(sourceIterator, startingChar);
        // TODO: The following code looks ugly and isn't DRY
        } else if (startingChar == 'n') {
            try {
                var maybeNull = sourceIterator.nextN(3);
                if (maybeNull.equals("ull")) {
                    return new JsonNull();
                } else {
                    throw new InvalidJsonFormatError("invalid element starting here", startingRow, startingCol);
                }
            } catch (NoSuchElementException e) {
                throw new InvalidJsonFormatError("invalid element starting here", startingRow, startingCol);
            }
        } else if (startingChar == 't') {
            try {
                var maybeNull = sourceIterator.nextN(3);
                if (maybeNull.equals("rue")) {
                    return new JsonBoolean(true);
                } else {
                    throw new InvalidJsonFormatError("invalid element starting here", startingRow, startingCol);
                }
            } catch (NoSuchElementException e) {
                throw new InvalidJsonFormatError("invalid element starting here", startingRow, startingCol);
            }
        } else if (startingChar == 'f') {
            try {
                var maybeNull = sourceIterator.nextN(4);
                if (maybeNull.equals("alse")) {
                    return new JsonBoolean(false);
                } else {
                    throw new InvalidJsonFormatError("invalid element starting here", startingRow, startingCol);
                }
            } catch (NoSuchElementException e) {
                throw new InvalidJsonFormatError("invalid element starting here", startingRow, startingCol);
            }
        } else {
            throw new InvalidJsonFormatError("invalid start of element: '" + startingChar + "'", startingRow, startingCol);
        }
    }

    private JsonElement consumeNumber(SourceIterator sourceIterator, char startingChar) throws InvalidJsonFormatError {
        var startingRow = sourceIterator.getRow();
        var startingCol = sourceIterator.getColumn();
        var buffer = new StringBuilder();
        buffer.append(startingChar);
        while (sourceIterator.hasNext() && isNumberChar(sourceIterator.peek())) {
            buffer.append(sourceIterator.next());
        }
        try {
            return new JsonNumber(new BigDecimal(buffer.toString()));
        } catch (NumberFormatException e) {
            throw new InvalidJsonFormatError("invalid number literal starting here", startingRow, startingCol);
        }
    }

    private boolean isNumberChar(char c) {
        return ('0' <= c && c <= '9')
            || c == '+' || c == '-'
            || c == '.'
            || c == 'e' || c == 'E';
    }
}
