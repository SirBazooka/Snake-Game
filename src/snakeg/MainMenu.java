/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package snakeg;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author andre
 */
public class MainMenu extends JPanel {
    protected GameFrame gameFrame;

    
    /**
     * Constructs the MainMenu panel with a reference to the GameFrame.
     * Sets up the layout, background color, title, and buttons for the main menu.
     * 
     * @param gameFrame The GameFrame instance that allows interaction with the main game screen
     */
    public MainMenu(GameFrame gameFrame) {
        this.gameFrame = gameFrame;

        setLayout(new BorderLayout());
        setBackground(new Color(255, 239, 185)); // Pastel yellow, desert sand

        JLabel titleLabel = new JLabel("The Snake Game", JLabel.CENTER);
        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 50));
        titleLabel.setForeground(new Color(47, 79, 79)); // Dark green color for title
        add(titleLabel, BorderLayout.NORTH);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(3, 1, 10, 10));
        buttonsPanel.setBackground(new Color(255, 239, 185));

        JButton startButton = new JButton("Start Game");
        startButton.setFont(new Font("Arial", Font.PLAIN, 30));
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gameFrame.showGamePanel(); // Show the game panel when clicked
            }
        });

        JButton highscoreButton = new JButton("View Highscores");
        highscoreButton.setFont(new Font("Arial", Font.PLAIN, 30));
        highscoreButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gameFrame.showHighscores(); // Show the highscore table
            }
        });

        JButton exitButton = new JButton("Exit");
        exitButton.setFont(new Font("Arial", Font.PLAIN, 30));
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0); 
            }
        });

        buttonsPanel.add(startButton);
        buttonsPanel.add(highscoreButton);
        buttonsPanel.add(exitButton);

        add(buttonsPanel, BorderLayout.CENTER);
    }
}
