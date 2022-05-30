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
            System.out.println("Hello world! " + args[0]);
            reader = new FileReader(args[0]);
        } else {
            reader = new InputStreamReader(System.in);
        }

        JSONObject jsonObject = parseReader(reader);

        List<Pair> resultList = flattenObject(jsonObject);
        System.out.println(Arrays.deepToString(resultList.toArray()));
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
     * the result is the flattened JSONObject, which was passed as an argument.
     * @param jsonObject
     */
    protected static List<Pair> flattenObject(JSONObject jsonObject) {
        Queue<Pair> queue = new LinkedList<>();
        jsonObject.forEach((k,v) -> {
            if (v instanceof JSONObject) // burrow down
                System.out.println(k);
            else { // the rest will be Long or Boolean or String
                System.out.println(k + " is an object " + v.getClass().getSimpleName() + ", value: " + v);
            }
            queue.add(new Pair(k.toString(), v));
        });

        List<Pair> resultList = new ArrayList<>();
        while (!queue.isEmpty()) {
            Pair p = queue.poll();
            if (p.value instanceof JSONObject jo) {  // newest Java 17 feature, can be replaced with a cast below
                jo.forEach((k,v) -> queue.add(new Pair(p.key + '.' + k.toString(), v)));
            } else {
                resultList.add(p);
            }
        }
        return resultList;
    }

    protected static void flattenToList(JSONObject jsonObject, String prefix, List<Pair> list) {
        jsonObject.forEach((k,v) -> {
            if (v instanceof JSONObject)
                flattenToList((JSONObject)v, appendPrefix(prefix, k.toString()), list);
            else { // if not, it will be Long or Boolean or String
                list.add(new Pair(appendPrefix(prefix, k.toString()), v)); // inserting final value.
            }
        });
    }

    /**
     * makes a into "a"
     * and a, b into "a.b"
     * @param prefix
     * @param addition
     * @return
     */
    public static String appendPrefix(String prefix, String addition) {
        if (prefix == null || "".equals(prefix))
            return addition;
        else
            return prefix + '.' + addition;
    }
}