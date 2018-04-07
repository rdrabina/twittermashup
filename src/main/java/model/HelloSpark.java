package model;

import static spark.Spark.*;

public class HelloSpark {
    public static void main(String[] args) {
        port(8080);
        get("/", (request, response) -> "Hello Spark!");
    }
}
