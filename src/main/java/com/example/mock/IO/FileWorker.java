package com.example.mock.IO;

import com.example.mock.model.User;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;

@Component
public class FileWorker {
    private static final String OUTPUT_FILE = "entities.txt";
    private static final String RANDOM_DATA_FILE = "random_data.txt";
    private static final Random random = new Random();

    public void writeEntityToFile(User user) {
        String json = "{" +
                "\"login\":\"" + user.getLogin() + "\"," +
                "\"password\":\"" + user.getPassword() + "\"," +
                "\"email\":\"" + user.getEmail() + "\"," +
                "\"registrationDate\":\"" + user.getRegistrationDate() + "\"" + ///
                "}";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(OUTPUT_FILE, true))) {
            writer.write(json);
            writer.newLine();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public String readRandomLineFromFile() throws IOException {
        String str = null;
        try (BufferedReader reader = new BufferedReader(new FileReader(RANDOM_DATA_FILE))) {
            for (int i = 0; i <= (random.nextInt(10) + 1); i++) {
                str = reader.readLine();
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return str;
    }
}
