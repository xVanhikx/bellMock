package com.example.mock;

import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/api")
public class LoginController {
    @GetMapping("/status")
    public String getStatus() {
        return "{\"login\":\"Login1\",\"status\":\"ok\"}";
    }

    @PostMapping("/login")
    public LoginResponse postLogin(@RequestBody LoginRequest request) throws InterruptedException {

    //    Thread.sleep(1000 + (long) (Math.random() * 1000));

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentDate = dateFormat.format(new Date());

        return new LoginResponse(request.getLogin(), request.getPassword(), currentDate);
    }
}
