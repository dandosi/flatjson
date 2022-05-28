# README for a Flatten-the-JSON project

The project contains a Java program which 'flattens' the JSON

# Compiling
Import and use IntelliJ

# Running
The program accepts input from stdin. In the project directory, execute

> java -cp ./out/production/flatjson;./lib  org.example.Flatten < example.json

or, _(argument, not stdin)_

> java -cp ./out/production/flatjson;./lib  org.example.Flatten  example.json


Output:

>{
> "a": 1,
> "b": true,
> "c.d": 3,
> "c.e": "test"
> }

# Misc

Choosing the JSON.simple library:
https://code.google.com/archive/p/json-simple/

Download JAR from:
http://www.java2s.com/Code/Jar/j/Downloadjsonsimple11jar.htm


... but the library is included in the ./lib directory of the project... 