package com.example.mock.controller;

import com.example.mock.connection.DataBaseWorker;
import com.example.mock.exceptions.InvalidRequestException;
import com.example.mock.exceptions.UserNotFoundException;
import com.example.mock.model.User;
import io.micrometer.prometheusmetrics.PrometheusConfig;
import io.micrometer.prometheusmetrics.PrometheusMeterRegistry;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<String> getStatus(@PathVariable String login) {
        timer(100);
        try {
            User user = dbWorker.getUserByLogin(login);
            return ResponseEntity.ok(user.toString());
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage()); //
        }
    }

    @PostMapping("/login")
    public ResponseEntity<User> postLogin(@Valid @RequestBody User user) {

        timer(100);
        int answer = dbWorker.insertUser(user);
        if (answer > 0) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(user); //
        }

    }

    @GetMapping("/prometheus")
    public String prometheusEndpoint() {
        return "redirect:/actuator/prometheus";
    }

    @Bean
    public MeterRegistryCustomizer<PrometheusMeterRegistry> metricsCommonTags() {
        return registry -> registry.config().commonTags("application", "bell-mock");
    }

    @Bean
    public PrometheusMeterRegistry prometheusMeterRegistry() {
        return new PrometheusMeterRegistry(PrometheusConfig.DEFAULT);
    }

    public void timer(int time) {
        try {
            Thread.sleep(time + (long) (Math.random() * time));
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }
}
