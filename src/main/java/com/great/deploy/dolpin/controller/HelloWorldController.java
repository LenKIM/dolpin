package com.great.deploy.dolpin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Controller
public class HelloWorldController {

    @GetMapping("/")
    public String getCurrentUser() {
        return "hello world";
    }
}
