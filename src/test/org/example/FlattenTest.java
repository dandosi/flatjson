package org.example;

import org.json.simple.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.Reader;
import java.io.StringReader;
import java.util.Arrays;
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
    void testFlattenObjectBig() {
        Reader reader = new StringReader(JSON_ABC_D_E_S);
        List<Pair> expectedList = List.of(new Pair("a", 1),
                new Pair("b", Boolean.TRUE),  new Pair("S", "String-o-Rae"),  new Pair("c.d", 3),  new Pair("c.e", "test"));

        List<Pair> list = Flatten.flattenObject(Flatten.parseReader(reader));
        assertEquals(Arrays.deepToString(expectedList.toArray()), Arrays.deepToString(list.toArray()));
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
    void testFlattenToListBig() {
        Reader reader = new StringReader(JSON_ABC_D_E_S);
        List<Pair> expectedList = List.of(new Pair("a", 1),
                new Pair("b", Boolean.TRUE),  new Pair("S", "String-o-Rae"),  new Pair("c.d", 3),  new Pair("c.e", "test"));

        List<Pair> list = Flatten.flattenObject(Flatten.parseReader(reader));
        assertEquals(Arrays.deepToString(expectedList.toArray()), Arrays.deepToString(list.toArray()));
    }

    @Test
    void testFlattenToListTriple() {
        Reader reader = new StringReader(JSON_AC_DF_Z);
        List<Pair> expectedList = List.of(new Pair("a", 1), new Pair("c.d", 3),  new Pair("c.f.z", "6"));

        List<Pair> list = Flatten.flattenObject(Flatten.parseReader(reader));
        assertEquals(Arrays.deepToString(expectedList.toArray()), Arrays.deepToString(list.toArray()));
    }



    @Test
    void testAppendPrefix() {
        assertEquals("a", Flatten.appendPrefix("","a"));
        assertEquals("a.b", Flatten.appendPrefix("a","b"));
        assertEquals("a.b.c", Flatten.appendPrefix("a.b","c"));
    }
}