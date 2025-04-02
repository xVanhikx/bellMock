package com.example.mock.connection;


import com.example.mock.exceptions.UserNotFoundException;
import com.example.mock.model.User;
import org.springframework.stereotype.Component;

import java.sql.*;

@Component
public class DataBaseWorker {
    private static final String URL = "jdbc:postgresql://127.0.0.1:5432/mydb";
    private static final String USER = "admin";
    private static final String PASSWORD = "admin";

    public User getUserByLogin(String login) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            statement = connection.createStatement();
            String query = "SELECT * FROM users u JOIN emails e ON u.login = e.login WHERE u.login = '" + login + "'";
            resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                return new User(
                        resultSet.getString("login"),
                        resultSet.getString("password"),
                        resultSet.getString("email"),
                        resultSet.getDate("date")
                );
            } else {
                throw new UserNotFoundException();
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при выполнении запроса: " + e.getMessage());
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.err.println("Ошибка при закрытии ресурсов: " + e.getMessage());
            }
        }
        return null;
    }

    public Integer insertUser(User user) {
        String insertUserSQL = "INSERT INTO users (login, password, date) VALUES (?, ?, ?)\n" +
                                "INSERT INTO emails (login, email) VALUES (?, ?)";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement userStatement = connection.prepareStatement(insertUserSQL)) {

            connection.setAutoCommit(false);

            userStatement.setString(1, user.getLogin());
            userStatement.setString(2, user.getPassword());
            userStatement.setDate(3, new Date(user.getRegistrationDate().getTime()));
            userStatement.setString(4, user.getLogin());
            userStatement.setString(5, user.getEmail());
            int userRows = userStatement.executeUpdate();



            connection.commit();

            return userRows;
        } catch (SQLException e) {
            System.err.println("Ошибка при вставке пользователя: " + e.getMessage());
            return null;
        }
    }
}
