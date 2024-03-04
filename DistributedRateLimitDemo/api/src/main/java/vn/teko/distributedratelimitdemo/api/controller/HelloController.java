package vn.teko.distributedratelimitdemo.api.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/hello")
@Slf4j
public class HelloController {
    @GetMapping
    public String hello() {
        log.info("Received request from gateway to /api/v1/hello");
        return "Hello, world!";
    }
}
