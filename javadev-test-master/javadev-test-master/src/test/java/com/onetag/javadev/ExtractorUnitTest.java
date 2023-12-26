package com.onetag.javadev;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class ExtractorUnitTest {
    private static final List<String> extractionResult = List.of(
            "Katharine Hepburn",
            "Daniel Day-Lewis",
            "Hilary Swank",
            "Jodie Foster",
            "Sean Penn",
            "Frances McDormand",
            "Tom Hanks",
            "Meryl Streep",
            "Sally Field",
            "Jane Fonda",
            "Jack Nicholson",
            "Dustin Hoffman",
            "Glenda Jackson",
            "Elizabeth Taylor",
            "Marlon Brando",
            "Olivia de Havilland",
            "Ingrid Bergman",
            "Vivien Leigh",
            "Luise Rainer",
            "Bette Davis",
            "Gary Cooper",
            "Spencer Tracy",
            "Fredric March"
    );

    @Test
    public void checkResult() {
        Extractor extractor = ExtractorFactory.getExtractor();

        Assertions.assertNotNull(extractor);

        List<String> result = extractor.extract();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(extractionResult.size(), result.size());

        for (int i = 0; i < extractionResult.size(); ++i) {
            if (i < 8 || i > 10) {
                Assertions.assertEquals(extractionResult.get(i), result.get(i));
            } else {
                Assertions.assertTrue(extractionResult.get(i).equals(result.get(8))
                        || extractionResult.get(i).equals(result.get(9))
                        || extractionResult.get(i).equals(result.get(10)));
            }
        }
    }
}