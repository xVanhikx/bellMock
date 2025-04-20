package com.example.mock.controller;

import com.example.mock.workers.FileWorker;
import com.example.mock.workers.DataBaseWorker;
import com.example.mock.exceptions.UserNotFoundException;
import com.example.mock.model.User;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class Controller {
    @Autowired
    private DataBaseWorker dbWorker;
    @Autowired
    FileWorker fileWorker;


    @GetMapping("/status/{login}")
    public ResponseEntity<String> getStatus(@PathVariable String login) {
        timer(100);
        try {
            User user = dbWorker.getUserByLogin(login);
            fileWorker.writeEntityToFile(user);
            return ResponseEntity.ok(user.toString());
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<User> postLogin(@Valid @RequestBody User user) {

        timer(100);
        int answer = dbWorker.insertUser(user);
        if (answer > 0) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(user);
        }

    }

    @GetMapping("/random")
    public String getRandomLine() {
        String str = null;
        try {
            str = fileWorker.readRandomLineFromFile();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return str;
    }

    @GetMapping("/prometheus")
    public String prometheusEndpoint() {
        return "redirect:/actuator/prometheus";
    }



    public void timer(int time) {
        try {
            Thread.sleep(time + (long) (Math.random() * time));
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }
}
