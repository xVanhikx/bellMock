package com.example.mock.IO;

import com.example.mock.model.User;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;

public class FileWorker {
    private static final String OUTPUT_FILE = "entities.txt";
    private static final String RANDOM_DATA_FILE = "random_data.txt";
    private static final Random random = new Random();

    public static void writeEntityToFile(User user) {
        String json = "{\n" +
                "\"login\":\"" + user.getLogin() + "\",\n" +
                "\"password\":\"" + user.getPassword() + "\",\n" +
                "\"email\":\"" + user.getEmail() + "\",\n" +
                "\"registrationDate\":\"" + user.getRegistrationDate() + "\"\n" +
                "}";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(OUTPUT_FILE, true))) {
            writer.write(json);
            writer.newLine();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void generateRandomDataFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(RANDOM_DATA_FILE))) {
            for (int i = 0; i < 5; i++) {
                writer.write("login: user" + random.nextInt(1000));
                writer.newLine();
                writer.write("password: pass" + random.nextInt(9999));
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static String readRandomLineFromFile() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(RANDOM_DATA_FILE));
        return lines.isEmpty() ? null : lines.get(random.nextInt(lines.size()));
    }
}
