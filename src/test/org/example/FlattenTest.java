package org.example;

import org.json.simple.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FlattenTest {

    /**
     * using a concatenated string, to be compatible with older Java
     */
    public static final String JSON_A = "{\n" +
            "  \"a\": 1\n" +
            "}";
    /**
     *  triple
     */
    public static final String JSON_AC_DF_Z = "{\n" +
            "    \"a\": 1,\n" +
            "    \"c\": {\n" +
            "        \"d\": 3\n" +
            "\t\t\"f\": {\n" +
            "\t\t\t\"z\": 6\n" +
            "\t\t}\n" +
            "    }\n" +
            "}\n";
    public static final String JSON_ABC_D_E_S = "{\n" +
            "  \"a\": 1,\n" +
            "  \"b\": true,\n" +
            "  \"c\": {\n" +
            "    \"d\": 3,\n" +
            "    \"e\": \"test\"\n" +
            "  },\n" +
            "  \"S\": \"String-o-Rae\"\n" +
            "}";

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testFlattenToListStart() {
        Reader reader = new StringReader(JSON_A);
        List<Pair> expectedList = List.of(new Pair("a", 1));

        JSONObject jo = Flatten.parseReader(reader);
        List<Pair> result = new LinkedList<>();
        Flatten.flattenToList(jo, "", result);
        assertEquals(Arrays.deepToString(expectedList.toArray()), Arrays.deepToString(result.toArray()));
    }

    /**
     * Note, this tests a value-in-a-list already; testing having a prefix..
     */
    @Test
    void testFlattenToListMidValue() {
        Reader reader = new StringReader(JSON_A);
        List<Pair> expectedList = List.of(new Pair("encompassing", 101), new Pair("encompassing.a", 1));

        JSONObject joMidValue = Flatten.parseReader(reader);
        List<Pair> result = new LinkedList<>();
        result.add(new Pair("encompassing", 101)); // preparing to have a value in the list already.

        Flatten.flattenToList(joMidValue, "encompassing", result);

        assertEquals(Arrays.deepToString(expectedList.toArray()), Arrays.deepToString(result.toArray()));
    }

    @Test
    void testFlattenObjectThreeLevel() {
        Reader reader = new StringReader(JSON_AC_DF_Z);
        List<Pair> expectedList = List.of(new Pair("a", 1), new Pair("c.d", 3),  new Pair("c.f.z", "6"));

        List<Pair> list = Flatten.flattenObject(Flatten.parseReader(reader));
        assertEquals(Arrays.deepToString(expectedList.toArray()), Arrays.deepToString(list.toArray()));
    }

    @Test
    void testFlattenObjectBig() {
        Reader reader = new StringReader(JSON_ABC_D_E_S);
        List<Pair> expectedList = List.of(new Pair("a", 1),
                new Pair("b", Boolean.TRUE), new Pair("c.d", 3), new Pair("c.e", "test"), new Pair("S", "String-o-Rae"));

        List<Pair> list = Flatten.flattenObject(Flatten.parseReader(reader));
        assertEquals(Arrays.deepToString(expectedList.toArray()), Arrays.deepToString(list.toArray()));
    }


    @Test
    void testAppendPrefix() {
        assertEquals("a", Flatten.appendPrefix("","a"));
        assertEquals("a.b", Flatten.appendPrefix("a","b"));
        assertEquals("a.b.c", Flatten.appendPrefix("a.b","c"));
    }

    @Test
    void testParseReader() {
        Reader sw = new StringReader("{ \"a\": \"true\" }");

        JSONObject jsonObject = Flatten.parseReader(sw);
        assertEquals(1, jsonObject.size()); // the jsonObject contains all the elements

        Object[] objects = jsonObject.values().toArray(); // when you get the values in the array, they are primitive types
        Object oneEntry = objects[0]; // getting just one, the index is OK per the above assertion

        assertEquals(String.class, oneEntry.getClass());
        assertEquals("true", oneEntry);
    }

    @Test
    void testMakeSimpleJsonString() {
        List<Pair> inputList = List.of(new Pair("a", 1),
                new Pair("b", Boolean.TRUE), new Pair("c.d", 3), new Pair("c.e", "test"), new Pair("S", "String-o-Rae"));
        String expected_flat_json = "{\n" +
                "    \"a\": 1,\n" +
                "    \"b\": true,\n" +
                "    \"c.d\": 3,\n" +
                "    \"c.e\": \"test\",\n" +
                "    \"S\": \"String-o-Rae\"\n" +
                "}";
        assertEquals(expected_flat_json, Flatten.makeSimpleJsonString(inputList));
    }
}