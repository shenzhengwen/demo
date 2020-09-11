package com.example.demo.controller;

import org.springframework.web.bind.annotation.*;


@RestController
public class HelloController {

    @RequestMapping("/hello")
    public String index(@RequestParam String name) {
        return "hello "+name+"，this is first messge";
    }
}
