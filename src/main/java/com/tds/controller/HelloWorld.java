package com.tds.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorld {
    @GetMapping
    public String helloWorld() {
        return "Hello World";
    }

    @GetMapping("crash")
    public void crash() {
        Runtime.getRuntime().halt(55);
    }

}
