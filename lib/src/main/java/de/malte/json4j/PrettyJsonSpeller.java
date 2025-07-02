package de.malte.json4j;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * An implementation of {@link JsonSpeller} that spells pretty, human-readably JSON.
 * Indent amount, indent character and initial indent level can be customized via their setters
 * of the constructor.
 */
@EqualsAndHashCode(callSuper = true)
public class PrettyJsonSpeller extends JsonSpeller {
    /**
     * The amount of {@link #indentChar}s to indent each level by.
     */
    @Getter
    @Setter
    private int indentAmount;
    /**
     * The character use to indent.
     */
    @Getter
    @Setter
    private char indentChar;
    /**
     * The current indent level.
     */
    @Getter
    @Setter
    private int indentLevel;

    /**
     * Sets the {@link #getIndentLevel() indent level} to 0.
     * @param indentAmount the initial value for {@link #getIndentAmount() the indent amount}
     * @param indentChar the initial value for {@link #getIndentChar() the indent char}
     */
    public PrettyJsonSpeller(int indentAmount, char indentChar) {
        this.indentAmount = indentAmount;
        this.indentChar = indentChar;
        this.indentLevel = 0;
    }

    /**
     * Default constructor, uses 2 as the {@link #getIndentAmount() indent amount}
     * and the space for {@link #getIndentChar() indent char}.
     */
    public PrettyJsonSpeller() {
        this(2, ' ');
    }

    /**
     * Actually adds a newline.
     */
    @Override
    public void newLine() {
        append('\n');
        append(getIndentString());
    }

    private String getIndentString() {
        return String.valueOf(indentChar).repeat(indentLevel * indentAmount);
    }

    /**
     * Increase indent level by one.
     */
    @Override
    public void indent() {
        indentLevel += 1;
    }

    /**
     * Decrease indent level by one.
     */
    @Override
    public void outdent() {
        indentLevel -= 1;
    }

    @Override
    void keyValueSeparator() {
        append(' ');
    }
}
