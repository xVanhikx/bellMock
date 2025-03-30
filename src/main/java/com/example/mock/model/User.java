package com.example.mock.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class User {
    @NotNull(message = "Login cannot be null")
    @Size(min = 3, max = 20, message = "Login must be between 3 and 20 characters")
    private String login;

    @NotNull(message = "Password cannot be null")
    @Size(min = 6, max = 30, message = "Password must be between 6 and 30 characters")
    private String password;
    private String email;
    private Date registrationDate;


    @Override
    public String toString() {
        return "{" +
                "\"login\": \"" + getLogin() + "\"," +
                "\"password\": \"" + getPassword() + "\"," +
                "\"date\": \"" + getRegistrationDate()   + "\"" +
                "}";
    }
}
