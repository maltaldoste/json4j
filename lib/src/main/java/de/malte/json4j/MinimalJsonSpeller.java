package de.malte.json4j;

public class MinimalJsonSpeller extends JsonSpeller {
    @Override
    void newLine() {
        // no op
    }

    @Override
    void indent() {
        // no op
    }

    @Override
    void outdent() {
        // no op
    }

    @Override
    void keyValueSeparator() {
        // no op
    }
}
