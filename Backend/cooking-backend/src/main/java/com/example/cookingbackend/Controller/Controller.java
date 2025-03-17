package com.example.cookingbackend.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class Controller {

    @GetMapping
    public ResponseEntity<String> get() {
        return ResponseEntity.ok().body("Hello World");
    }
    @GetMapping("/api")
    public ResponseEntity<String> get1() {
        return ResponseEntity.ok().body("Lalalaaa");
    }

}
