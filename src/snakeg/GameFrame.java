/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package snakeg;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author andre
 */

public class GameFrame extends JFrame {
    private MainMenu mainMenu;
    private GamePanel gamePanel;

    /**
     * Constructs the GameFrame object, initializes the main menu, and sets up the window properties.
     * The window will have a fixed size, a title, and it will be centered on the screen.
     */
    public GameFrame() {
        mainMenu = new MainMenu(this);
        add(mainMenu);

        setTitle("The Snake Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false); 
        pack(); 
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Switches the current view to the game panel, which shows the game in progress.
     * This method removes the main menu and replaces it with the game panel.
     */
    public void showGamePanel() {
        remove(mainMenu);
        gamePanel = new GamePanel();
        
        setSize(GamePanel.SCREEN_WIDTH, GamePanel.SCREEN_HEIGHT);
        setContentPane(gamePanel);
        validate(); 
        gamePanel.requestFocusInWindow(); 
        setLocationRelativeTo(null);
    }
    
    /**
     * Displays the highscore table by removing the main menu and showing a panel with top players' scores.
     * The scores are fetched from the database and displayed in a vertically aligned list.
     */
    public void showHighscores() {
    if (mainMenu != null) {
        remove(mainMenu);
    }

    JPanel bestPlayersPanel = new JPanel();
    bestPlayersPanel.setLayout(new BoxLayout(bestPlayersPanel, BoxLayout.Y_AXIS));
    bestPlayersPanel.setBackground(new Color(255, 239, 185));
    bestPlayersPanel.setBorder(BorderFactory.createTitledBorder("Best Players"));

    List<String> entries = DatabaseHandler.getTopScores();

    for (String entry : entries) {
        JLabel playerLabel = new JLabel(entry, SwingConstants.CENTER);
        playerLabel.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        playerLabel.setAlignmentX(Component.CENTER_ALIGNMENT); 
        playerLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        bestPlayersPanel.add(playerLabel);
    }

    JButton backButton = new JButton("Back to Main Menu");
    backButton.setAlignmentX(Component.CENTER_ALIGNMENT); 
    backButton.addActionListener(e -> showMainMenu()); 

    bestPlayersPanel.add(Box.createVerticalStrut(10)); 
    bestPlayersPanel.add(backButton); 
    setSize(500, 400);
    setContentPane(bestPlayersPanel);
    validate(); 
    setLocationRelativeTo(null); 
    }
    
    /**
    * Switches the current view to the main menu screen.
    * This method removes any existing panels (such as the game panel or high score panel) 
    * and adds the main menu panel back to the window. It is used to return to the main menu 
    * after a game session or when viewing the high scores.
    */
    private void showMainMenu() {
    
    if (gamePanel != null) {
        remove(gamePanel);
    }

    if (mainMenu == null) {
        mainMenu = new MainMenu(this); 
    }

    setContentPane(mainMenu);
    validate();
    setLocationRelativeTo(null);
    }
    
    /**
     * Restarts the game by showing the game panel again. This can be used to start a new game session.
     */
    public void restartGame() {
        showGamePanel();
    }
}
