package de.malte.json4j;

import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class RealWorldParserTest {
    private static JsonParser parser;

    @BeforeClass
    public static void initParser() {
        parser = new JsonParser(new JsonParserConfig());
    }

    private String readResource(String name) throws IOException {
        return new String(getClass().getClassLoader().getResourceAsStream(name).readAllBytes());
    }

    @Test
    public void glossaryExample() throws IOException {
        // The test data is from https://json.org/example.html
        var jsonString = readResource("glossary.json");
        var expected = generateExpectedGlossary();
        assertEquals(expected, parser.parse(jsonString));
    }

    private JsonElement generateExpectedGlossary() {
        return
            new JsonObject(
                Map.of(
                    "glossary", new JsonObject(
                        Map.of(
                            "title", new JsonString("example glossary"),
                            "GlossDiv", new JsonObject(
                                Map.of(
                                    "title", new JsonString("S"),
                                    "GlossList", new JsonObject(
                                        Map.of(
                                            "GlossEntry", new JsonObject(
                                                Map.of(
                                                    "ID", new JsonString("SGML"),
                                                    "SortAs", new JsonString("SGML"),
                                                    "GlossTerm", new JsonString("Standard Generalized Markup Language"),
                                                    "Acronym", new JsonString("SGML"),
                                                    "Abbrev", new JsonString("ISO 8879:1986"),
                                                    "GlossDef", new JsonObject(
                                                        Map.of(
                                                            "para", new JsonString("A meta-markup language, used to create markup languages such as DocBook."),
                                                            "GlossSeeAlso", new JsonArray(
                                                                Arrays.asList(
                                                                    new JsonString("GML"),
                                                                    new JsonString("XML")
                                                                )
                                                            )
                                                        )
                                                    ),
                                                    "GlossSee", new JsonString("markup")
                                                )
                                            )
                                        )
                                    )
                                )
                            )
                        )
                    )
                )
            );
    }
}
