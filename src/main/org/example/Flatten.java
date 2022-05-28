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
            if (p.value instanceof JSONObject) {
                JSONObject jo =  (JSONObject)p.value;
                jo.forEach((k,v) -> queue.add(new Pair(p.key + '.' + k.toString(), v)));
            } else {
                resultList.add(p);
            }
        }
        return resultList;
    }

}