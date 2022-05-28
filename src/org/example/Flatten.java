package org.example;

import java.io.*;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Flatten {
    public static void main(String[] args) throws IOException {
        Reader reader;
        if (args.length > 0) {
            System.out.println("Hello world! " + args[0]);
            reader = new FileReader(args[0]);
        } else {
            reader = new InputStreamReader(System.in);
        }

        JSONParser parser = new JSONParser();
        JSONObject jsonObject;
        try {
            jsonObject = (JSONObject) parser.parse(reader);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

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
        System.out.println(Arrays.deepToString(resultList.toArray()));
    }

    /**
     * Not generating equals or hashcode because they are not yet needed in this small project.
     */
    private static class Pair {
        protected String key;
        protected Object value;

        protected Pair(String key, Object value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public String toString() {
            return "Pair{" +
                    "key='" + key + '\'' +
                    ", value=" + value +
                    '}';
        }
    }
}