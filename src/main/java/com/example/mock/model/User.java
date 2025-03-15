package com.example.mock.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class User {
    @NotNull(message = "Login cannot be null")
    @Size(min = 3, max = 20, message = "Login must be between 3 and 20 characters")
    private String login;

    @NotNull(message = "Password cannot be null")
    @Size(min = 6, max = 30, message = "Password must be between 6 and 30 characters")
    private String password;
    private String date;

    public User(String login, String password) {
        this.login = login;
        this.password = password;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.date = dateFormat.format(new Date());
    }

    @Override
    public String toString() {
        return "{" +
                "\"login\": \"" + getLogin() + "\"," +
                "\"password\": \"" + getPassword() + "\"," +
                "\"date\": \"" + getDate() + "\"" +
                "}";
    }
}
