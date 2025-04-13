/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package snakeg;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author andre
 */

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/snake_game?zeroDateTimeBehavior=CONVERT_TO_NULL";
    private static final String USER = "root"; // Your MySQL username
    private static final String PASSWORD = ""; // Your MySQL password

    /**
     * Establishes a connection to the database using the provided URL, username, and password.
     * 
     * @return a {@link Connection} object representing the established connection to the database
     * @throws SQLException if an error occurs while establishing the connection
     */
    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASSWORD);
    }

    /**
     * Inserts a new high score into the high_scores table in the database.
     * The method takes the player's name, time spent in the game, and the number of food items eaten
     * during the game and stores them in the database.
     * 
     * @param name the name of the player
     * @param time the time the player took to finish the game (in seconds)
     * @param foodEaten the number of food items the player ate during the game
     */
    public static void insertHighscore(String name, float time, int foodEaten) {
        String query = "INSERT INTO high_scores (name, time, food_eaten) VALUES (?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, name);
            stmt.setFloat(2, time);
            stmt.setInt(3, foodEaten);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves the top 10 high scores from the database, ordered by the number of food items eaten.
     * The result is returned as a list of strings, where each string represents a player's high score
     * along with the time and the number of food items eaten.
     * 
     * @return a list of top 10 high scores as strings, each in the format "name - Time: Xs, Food Eaten: Y"
     */
    public static List<String> getTopScores() {
        List<String> highScoresList = new ArrayList<>();
        String query = "SELECT name, time, food_eaten FROM high_scores ORDER BY food_eaten DESC LIMIT 10";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                String name = rs.getString("name");
                float time = rs.getFloat("time");
                int foodEaten = rs.getInt("food_eaten");

                highScoresList.add(name + " - Time: " + time + "s, Food Eaten: " + foodEaten);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return highScoresList;
    }
}
