package com.example.demo.conreoller;

import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/demo")
public class HelloController {


    @GetMapping(value = "/say")
    @ResponseBody
    public String hello() {
        try {

        } catch (Exception e) {
        }
        return "hello" ;
    }


}
