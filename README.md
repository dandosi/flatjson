# README for a Flatten-the-JSON project

The project contains a Java program which 'flattens' the JSON

# Compiling
Import into IntelliJ and use it that way. The project file (.iml) should be included. 
Only one library is necessary, **json-simple**, is included in the `./lib` directory.

# Running
The program accepts input from `stdin`. In the project directory, execute (MingW):

    java -classpath "C:\Code\flatjson\out\production\flatjson;C:\Code\flatjson\lib\json-simple-1.1.1.jar" org.example.Flatten < example.json

or, you can use it with an **argument**, not `stdin`, simply provide the filename _(This is an enhancement.)_

    java -classpath "C:\Code\flatjson\out\production\flatjson;C:\Code\flatjson\lib\json-simple-1.1.1.jar" org.example.Flatten example.json

Output:

    {
        "a": 1,
        "b": true,
        "c.d": 3,
        "c.e": "test"
    }

# Misc

Choosing the JSON.simple library:
https://code.google.com/archive/p/json-simple/

Download JAR from:
http://www.java2s.com/Code/Jar/j/Downloadjsonsimple11jar.htm


... but the library is included in the ./lib directory of the project... 