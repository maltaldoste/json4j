package de.malte.json4j;

import lombok.Getter;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Internal utility class for iterating a string while keeping track of where we are.
 */
public class SourceIterator implements Iterator<Character>, Iterable<Character> {
    /**
     * The source this iterates.
     */
    @Getter
    private String source;
    /**
     * The row (= line) this iterator is currently at in the source.
     */
    @Getter
    private int row;
    /**
     * The column (= character in line) this iterator is currently as in the source.
     */
    @Getter
    private int column;
    private int index;

    /**
     * Construct a new {@code SourceIterator} over the given source.
     * @param source the nun-null source to iterate
     * @throws NullPointerException when the given source is null
     */
    public SourceIterator(String source) {
        this.source = Objects.requireNonNull(source);
        this.row = 1;
        this.column = 1;
        this.index = 0;
    }

    public void skipWhitespace() {
        while (Character.isWhitespace(peek())) {
            next();
        }
    }

    public String nextN(int n) throws NoSuchElementException {
        var buffer = new StringBuilder();
        for (int i = 0; i < n; i++) {
            buffer.append(next());
        }
        return buffer.toString();
    }

    @Override
    public boolean hasNext() {
        return index < source.length();
    }

    @Override
    public Character next() {
        var c = source.charAt(index);
        index += 1;
        if (c == '\n') {
            row += 1;
            column = 1;
        } else {
            column += 1;
        }
        return c;
    }

    /**
     * Like {@link #next()} but doesn't advance the iterator.
     * @return the character at the current position of this iterator
     */
    public char peek() {
        return source.charAt(index);
    }

    @Override
    public Iterator<Character> iterator() {
        return this;
    }
}
