package de.malte.json4j.example;

import de.malte.json4j.JsonParser;
import de.malte.json4j.JsonParserConfig;
import de.malte.json4j.MinimalJsonSpeller;
import de.malte.json4j.PrettyJsonSpeller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        var parser = new JsonParser(new JsonParserConfig());
        var jsonSource = "";
        try (
            var inputStream = Main.class.getClassLoader().getResourceAsStream("example.json");
            var reader = new BufferedReader(new InputStreamReader(inputStream))) {
            jsonSource = reader.lines().reduce("", String::concat);
        }

        var rootJsonElement = parser.parse(jsonSource).asObject();
        System.out.println("Project name:\t" + rootJsonElement.get("project-name").asString().getValue());
        System.out.println("Project author:\t" + rootJsonElement.get("author").asString().getValue());
        System.out.println("Project year:\t" + rootJsonElement.get("year").asNumber().getValue());
        System.out.println("Tools used:");
        for (var tool : rootJsonElement.get("tools").asArray()) {
            System.out.println("\t- " + tool.asString().getValue());
        }

        System.out.println("\n-----\n");
        System.out.println("Pretty Json:");
        System.out.println(rootJsonElement.spell(new PrettyJsonSpeller()));
        System.out.println("\n-----\n");
        System.out.println("Minimal Json:");
        System.out.println(rootJsonElement.spell(new MinimalJsonSpeller()));
    }
}