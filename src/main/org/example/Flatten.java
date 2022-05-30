package org.example;

import java.io.*;
import java.util.*;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Flatten {
    /**
     * main function.
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        Reader reader;
        if (args.length > 0) {
            reader = new FileReader(args[0]);
        } else {
            reader = new InputStreamReader(System.in);
        }

        JSONObject jsonObject = parseReader(reader);

        List<Pair> resultList = flattenObject(jsonObject);

        System.out.println(makeSimpleJsonString(resultList));
    }

    /**
     * Receives a Reader as an input and returns you a JSONObject
     * @param reader
     * @return
     */
    protected static JSONObject parseReader(Reader reader) {
        JSONParser parser = new JSONParser();
        JSONObject jsonObject;
        try {
            jsonObject = (JSONObject) parser.parse(reader);
        } catch (ParseException | IOException e) {
            throw new RuntimeException(e);
        }
        return jsonObject;
    }

    /**
     * received JSONObject as an argument
     * the result is the flattened JSONObject.
     * This is a convenience wrapper.
     *
     * @param jsonObject a JSON
     */
    public static List<Pair> flattenObject(JSONObject jsonObject) {
        List<Pair> result = new LinkedList<>();
        Flatten.flattenToList(jsonObject, "", result);
        return  result;
    }

    protected static void flattenToList(JSONObject jsonObject, String prefix, List<Pair> list) {
        jsonObject.forEach((k,v) -> {
            if (v instanceof JSONObject)
                flattenToList((JSONObject)v, appendPrefix(prefix, k.toString()), list);
            else { // if not, v will be Long or Boolean or String, primitive types.
                list.add(new Pair(appendPrefix(prefix, k.toString()), v)); // inserting final value.
            }
        });
    }

    /**
     * makes a into "a"
     * and a, b into "a.b"
     * @param prefix
     * @param addition
     * @return a new string consisting of concatenated prefix and addition
     */
    public static String appendPrefix(String prefix, String addition) {
        if (prefix == null || "".equals(prefix))
            return addition;
        else
            return prefix + '.' + addition;
    }

    protected static String makeSimpleJsonString(List<Pair> resultList) {
        StringBuilder sb = new StringBuilder();
        sb.append("{\n");
        for (int i = 0; i < resultList.size(); i++) {
            Pair p = resultList.get(i);
            sb.append("    \"").append(p.key).append("\": ");
            // value output
            if (p.value instanceof String) { // as opposed to Long or Boolean primitive types
                sb.append('"').append(p.value).append('"');
            } else {
                sb.append(p.value);
            }
            // end of line
            if (i < resultList.size() - 1) // not last.
                sb.append(",\n");
            else
                sb.append("\n");
        }
        sb.append('}');
        return sb.toString();
    }
}