package org.example;

import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        SQLConnect sqlData = new SQLConnect();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nEnter a command\n");
            switch (scanner.nextLine()) {
                case "get":
                    get(sqlData);
                    break;
                case "post":
                    post(sqlData);
                    break;
                case "update":
                    update(sqlData);
                    break;
                case "delete":
                    delete(sqlData);
                    break;
                case "exit", "^C":
                    System.out.println("Exiting...");
                    return;
                default:
                    System.err.println("Wrong command");
                    break;
            }
        }
    }

    public static void get(SQLConnect sqlData) {
        try (Connection connection = DriverManager.getConnection(
                sqlData.getUrl(),
                sqlData.getUser(),
                sqlData.getPassword())) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM users");
            ResultSet resultSet = statement.executeQuery();

            System.out.printf("%-10s | %-20s%n", "ID", "Name");
            System.out.println("-------------------------------");

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                System.out.printf("%-10d | %-20s%n", id, name);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void post(SQLConnect sqlData) {
        Scanner nextString = new Scanner(System.in);
        try (Connection connection = DriverManager.getConnection(
                sqlData.getUrl(),
                sqlData.getUser(),
                sqlData.getPassword())) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO users (name) VALUES (?)");
            System.out.println("Enter your name");
/*
            This part of code used to insert id  automatically
            statement.setInt(1, nextInt.nextInt());
            int rowsInserted = statement.executeUpdate();
            System.out.println("Rows inserted: " + rowsInserted);
*/
            statement.setString(1, nextString.nextLine());
            int rowsInserted = statement.executeUpdate();
            System.out.println("Rows inserted: " + rowsInserted);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void update(SQLConnect sqlData) {
        Scanner nextString = new Scanner(System.in);
        try (Connection connection = DriverManager.getConnection(
                sqlData.getUrl(),
                sqlData.getUser(),
                sqlData.getPassword())) {
            PreparedStatement statement = connection.prepareStatement("UPDATE users SET name = ? WHERE id = ?");
            System.out.println("Enter your new name");
            statement.setString(1, nextString.nextLine());
            System.out.println("Enter your id");
            statement.setInt(2, nextString.nextInt());
            int rowsUpdated = statement.executeUpdate();
            System.out.println("Rows updated: " + rowsUpdated);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void delete(SQLConnect sqlData) {
        Scanner nextString = new Scanner(System.in);
        try (Connection connection = DriverManager.getConnection(
                sqlData.getUrl(),
                sqlData.getUser(),
                sqlData.getPassword())) {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM users WHERE id = ?");
            System.out.println("Enter your id");
            statement.setInt(1, nextString.nextInt());
            int rowsDeleted = statement.executeUpdate();
            System.out.println("Rows deleted: " + rowsDeleted);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
