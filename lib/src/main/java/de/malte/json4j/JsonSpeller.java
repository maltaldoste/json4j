package de.malte.json4j;

import lombok.EqualsAndHashCode;

/**
 * Abstract class for spelling JSON. Spelling means turning the parsed structure back into valid JSON.
 * The implementor of this class defines how the generated JSON will look like.
 * @see PrettyJsonSpeller
 * @see MinimalJsonSpeller
 */
@EqualsAndHashCode
public abstract class JsonSpeller {
    private StringBuilder buffer = new StringBuilder();

    /**
     * Asks the speller to insert a newline. A newline is not guaranteed to happen, as is the case with {@link MinimalJsonSpeller}.
     */
    abstract void newLine();
    /**
     * Asks the speller to indent. An indent is not guaranteed to happen, as is the case with {@link MinimalJsonSpeller}.
     */
    abstract void indent();
    /**
     * Asks the speller to outdent (i. e. reverse the last indent). An outdent is not guaranteed to happen, as is the case with {@link MinimalJsonSpeller}.
     */
    abstract void outdent();

    /**
     * Appends the given string as-is to the buffer.
     * @param s the string to append to the buffer
     */
    public void append(String s) {
        buffer.append(s);
    }

    /**
     * Appends the given string quoted and escaped to the buffer.
     * @param s the string to quote, escape and append to the buffer
     */
     public void appendStringLiteral(String s) {
        // FIXME: Do exactly the correct escapes
        append('"' + s
            .replace("\\", "\\\\")
            .replace("\r", "\\r")
            .replace("\n", "\\n")
            .replace("\t", "\\t") + '"');
    }

    /**
     * Like {@link #append(String)} but for a single char.
     * @param c the char to append to the buffer
     */
    public void append(char c) {
        buffer.append(c);
    }

    /**
     * Renders the buffer to a string.
     * @return the contents of the buffer
     */
    @Override
    public String toString() {
        return buffer.toString();
    }
}
