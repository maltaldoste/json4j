package de.malte.json4j;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JsonParserConfig {
    /**
     * Should objects retain their order?
     */
    @Builder.Default
    private boolean objectsInOrder = true;
}
