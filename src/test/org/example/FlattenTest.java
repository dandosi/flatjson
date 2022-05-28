package org.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.Reader;
import java.io.StringReader;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FlattenTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testFlattenObjectBig() {
        // I am leaving this to be a concatenated string, to be compatible with older Java
        String inputString = "{\n" +
                "  \"a\": 1,\n" +
                "  \"b\": true,\n" +
                "  \"c\": {\n" +
                "    \"d\": 3,\n" +
                "    \"e\": \"test\"\n" +
                "  },\n" +
                "  \"d\": \"String-o-Rae\"\n" +
                "}";
        Reader reader = new StringReader(inputString);
        List<Pair> expectedList = List.of(new Pair("a", 1),
                new Pair("b", Boolean.TRUE),  new Pair("d", "String-o-Rae"),  new Pair("c.d", 3),  new Pair("c.e", "test"));

        List<Pair> list = Flatten.flattenObject(Flatten.parseReader(reader));
        assertEquals(Arrays.deepToString(expectedList.toArray()), Arrays.deepToString(list.toArray()));
    }
}