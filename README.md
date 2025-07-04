JSON4J is a parser for the [JSON](https://www.json.org/json-en.html) data
format.

# Usage

JSON4J uses [Gradle](https://gradle.org/) to build. In order to include JSON4J
as a dependency, the project needs to be built locally (`gradle install`) and
can then be added to a Gradle project like so:

```kotlin
repositories {
    mavenLocal()
}

dependencies {
    implementation("de.malte.json4j:json4j:0.1")
}
```

A minimal example of usage looks as follows (see also
example/src/main/java/de/malte/json4j/example/Main.java):

```java
import de.malte.json4j.JsonParser;
import de.malte.json4j.JsonParserConfig;
import de.malte.json4j.MinimalJsonSpeller;
import de.malte.json4j.PrettyJsonSpeller;

// ...
var parser = new JsonParser(new JsonParserConfig());
var jsonSource = """{
  "project-name": "json4j",
  "author": "Malte",
  "year": 2025,
  "tools": ["java", "gradle", "git", "vim", "Intellij Idea"]
}""";

// Parse the JSON and cast to JsonObject
var rootJsonElement = parser.parse(jsonSource).asObject();

// Access the object members
System.out.println("Project name:\t" + rootJsonElement.get("project-name").asString().getValue());
System.out.println("Project author:\t" + rootJsonElement.get("author").asString().getValue());
System.out.println("Project year:\t" + rootJsonElement.get("year").asNumber().getValue());
System.out.println("Tools used:");
for (var tool : rootJsonElement.get("tools").asArray()) {
    System.out.println("\t- " + tool.asString().getValue());
}

// Spell the JSON
System.out.println("\n-----\n");
System.out.println("Pretty Json:");
System.out.println(rootJsonElement.spell(new PrettyJsonSpeller()));
System.out.println("\n-----\n");
System.out.println("Minimal Json:");
System.out.println(rootJsonElement.spell(new MinimalJsonSpeller()));
}
```
