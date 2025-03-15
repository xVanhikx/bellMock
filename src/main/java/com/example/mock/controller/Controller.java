package com.example.mock.controller;

import com.example.mock.model.User;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/api")
public class Controller {
    @GetMapping("/status")
    public String getStatus() {
        //    Thread.sleep(1000 + (long) (Math.random() * 1000));
        return "{\"login\":\"Login1\",\"status\":\"ok\"}";
    }

    @PostMapping("/login")
    public ResponseEntity<User> postLogin(@Valid @RequestBody User request) throws InterruptedException {

    //    Thread.sleep(1000 + (long) (Math.random() * 1000));



        return ResponseEntity.ok(new User(request.getLogin(), request.getPassword()));
    }
}
