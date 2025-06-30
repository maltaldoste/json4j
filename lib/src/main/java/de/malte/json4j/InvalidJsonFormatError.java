package de.malte.json4j;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * A runtime exception thrown when the parser encountered invalid JSON.
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class InvalidJsonFormatError extends RuntimeException {
    /**
     * Construct a new instance with an error message consisting of the given
     * message and the row and column it was encountered.
     * {@code message} should be short, starting with lowercase and should have
     * no trailing punctuation.
     *
     * @param message a short message about the error
     * @param row the row in which the invalid format was found
     * @param column the column in which the invalid format was found
     */
    public InvalidJsonFormatError(String message, int row, int column) {
        super(String.format("invalid JSON format in line %d:%d: '%s'", row, column, message));
    }
}
