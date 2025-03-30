package com.example.mock.controller;

import com.example.mock.connection.DataBaseWorker;
import com.example.mock.exceptions.InvalidRequestException;
import com.example.mock.exceptions.UserNotFoundException;
import com.example.mock.model.User;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/api")
public class Controller {
    @Autowired
    private DataBaseWorker dbWorker;
    @GetMapping("/status")
    public ResponseEntity<User> getStatus(@PathVariable String login) {
        //    Thread.sleep(1000 + (long) (Math.random() * 1000));
        try {
            User user = dbWorker.getUserByLogin(login);
            return ResponseEntity.ok(user);
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<User> postLogin(@Valid @RequestBody User user) throws InterruptedException {

    //    Thread.sleep(1000 + (long) (Math.random() * 1000));
        int answer = dbWorker.insertUser(user);
        if (answer > 0) {
            return ResponseEntity.ok(user);
        } else {
            throw new InvalidRequestException();
        }



    }
}
