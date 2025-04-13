/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package snakeg;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

/**
 *
 * @author andre
 */
public class GamePanel extends JPanel implements ActionListener {

        static final int SCREEN_WIDTH = 800+12;
        static final int SCREEN_HEIGHT = 800+12;
        static final int UNIT_SIZE = 25;
        static final int GAME_UNITS = (SCREEN_WIDTH-12) * (SCREEN_HEIGHT-12) / UNIT_SIZE ;
        static final int DELAY = 175;
        static final int NUM_ROCKS = 10; 
        final int x[] = new int[GAME_UNITS];
        final int y[] = new int[GAME_UNITS];
        int bodyParts = 2;
        int applesEaten = 0;
        int appleX;
        int appleY;
        char direction = 'R';
        boolean running = false;
        boolean paused = false;

        int rockX[] = new int[NUM_ROCKS];
        int rockY[] = new int[NUM_ROCKS];

        Timer timer;
        Random random;
        
        long startingTime = 0;
        long endingTime = 0; 

        
        JButton mainMenuButton;

        /**
        * Constructs a new {@code GamePanel} and initializes the game settings.
        * Sets up the panel's preferred size, background color, key listener, 
        * and border. Then, starts the game.
        */
        GamePanel() {
            random = new Random();
            this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_WIDTH));
            this.setBackground(Color.getHSBColor(36, 42, 99));
            this.setFocusable(true);
            this.addKeyListener(new MyKeyAdapter());
            this.setBorder(BorderFactory.createLineBorder(Color.getHSBColor(46, 100, 81), 2));
            startGame();
        }

        /**
        * Starts the game by spawning a new apple, generating rocks, and initializing the timer.
        */

        public void startGame() {
            newApple();
            generateRocks(); 
            running = true;
            timer = new Timer(DELAY, this);
            timer.start();
              startingTime = System.nanoTime();
        }

        /**
        * Paints the game components onto the panel. This method is automatically
        * invoked by the {@link javax.swing.Timer} when the screen needs to be redrawn.
        * 
        * @param g the {@code Graphics} object used to draw on the panel
        */
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            draw(g);
        }
        
        /**
        * Draws the grid of the game field.
        * 
        * @param g the {@code Graphics} object used to draw the grid
        */
        public void drawGrid(Graphics g) {
            for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
                g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
                g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
            }
        }

        /**
        * Draws the game objects (snake, apple, rocks, score) on the screen.
        * 
        * @param g the {@code Graphics} object used to draw the game components
        */
        public void draw(Graphics g) {
            if (running) {

                g.setColor(Color.getHSBColor(5, 79, 87));
                g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

                for (int i = 0; i < bodyParts; i++) {
                    if (i == 0) {
                        g.setColor(Color.green);
                        g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                    } else {
                        g.setColor(new Color(75, 180, 0));
                        g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                    }
                }

                g.setColor(Color.GRAY);
                for (int i = 0; i < NUM_ROCKS; i++) {
                    g.fillRect(rockX[i], rockY[i], UNIT_SIZE, UNIT_SIZE);
                }

                g.setColor(Color.RED);
                g.setFont(new Font("Times New Roman", Font.BOLD, 30));
                FontMetrics metrics = getFontMetrics(g.getFont());
                //g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: " + applesEaten)) / 2, g.getFont().getSize());
                g.drawString("Score: " + applesEaten, 10, g.getFont().getSize());
                
                long elapsedTime_timer = System.nanoTime() - startingTime; 
                long seconds = elapsedTime_timer / 1000000000; 
                long minutes = seconds / 60; 
                seconds = seconds % 60;

                String timeString = String.format("%02d:%02d", minutes, seconds);
                g.drawString("Time: " + timeString, SCREEN_WIDTH - 180, g.getFont().getSize()); // Right aligned (10 pixels from the right)
            } else {
                gameOver(g);
            }
        }

        /**
        * Generates a new apple at a random position on the game field.
        */
        public void newApple() {
            appleX = random.nextInt((int)(SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
            appleY = random.nextInt((int)(SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
        }

        /**
        * Generates rocks at random positions on the game field.
        */
        public void generateRocks() {
            for (int i = 0; i < NUM_ROCKS; i++) {
                rockX[i] = random.nextInt((int)(SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
                rockY[i] = random.nextInt((int)(SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
            }
        }

        /**
        * Moves the snake based on the current direction.
        * Updates the coordinates of the snake's body parts.
        */
        public void move() {
            for (int i = bodyParts; i > 0; i--) {
                x[i] = x[i-1];
                y[i] = y[i-1];
            }

            switch(direction) {
                case 'U':
                    y[0] = y[0] - UNIT_SIZE;
                    break;
                case 'D':
                    y[0] = y[0] + UNIT_SIZE;
                    break;
                case 'L':
                    x[0] = x[0] - UNIT_SIZE;
                    break;
                case 'R':
                    x[0] = x[0] + UNIT_SIZE;
                    break;
            }
        }
        
        
        /**
        * Checks if the snake has eaten an apple. If true, the snake grows and a new apple is spawned.
        */
        public void checkApple() {
            if ((x[0] == appleX) && (y[0] == appleY)) {
                bodyParts++;
                applesEaten++;
                newApple();
            }
        }

        
        
        /**
         * Checks if the snake collides with itself, the boundaries, or rocks.
         * If any collision occurs, the game ends.
         */
        public void checkCollisions() {
            for (int i = bodyParts; i > 0; i--) {
                if ((x[0] == x[i]) && (y[0] == y[i])) {
                    running = false;
                }
            }

            if (x[0] < 0 || x[0] > SCREEN_WIDTH || y[0] < 0 || y[0] > SCREEN_HEIGHT) {
                running = false;
            }

            for (int i = 0; i < NUM_ROCKS; i++) {
                if (x[0] == rockX[i] && y[0] == rockY[i]) {
                    running = false;
                }
            }

            if (!running) {
                timer.stop();
                   endingTime = (System.nanoTime() - startingTime) / 1000000000;
            }
        }

        /**
        * Displays the game over screen and prompts the player to enter their name to save their score.
        * If a valid name is entered, the score is saved to the database.
        * 
        * @param g the {@code Graphics} object used to draw the game over screen
        */
        public void gameOver(Graphics g) {
            String name = JOptionPane.showInputDialog(this, "You lost! Enter your name to save your score:");
            if (name != null && !name.isEmpty()) {
                DatabaseHandler.insertHighscore(name, endingTime, applesEaten);
                JOptionPane.showMessageDialog(this, "Score saved successfully.");
                showMainMenu();
            } else {
                JOptionPane.showMessageDialog(this, "No name entered. Score will not be saved.");
                showMainMenu();
            }
        }

        /**
        * Displays the main menu by removing the game panel and adding the main menu screen.
        */
        private void showMainMenu() {
            JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);

            parentFrame.getContentPane().removeAll();

            MainMenu mainmenu = new MainMenu(new GameFrame());
            parentFrame.getContentPane().add(mainmenu);

            parentFrame.setVisible(false);
            parentFrame.dispose();
            parentFrame.revalidate();
            parentFrame.repaint();
        }

        /**
        * This method is called when the timer ticks. It is responsible for updating the game state and redrawing the screen.
        * 
        * @param e the event triggered by the timer
        */
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!paused) {
                if (running) {
                    move();
                    checkApple();
                    checkCollisions();
                }
                repaint();
            }
        }

        /**
        * Key adapter to handle player input from the keyboard (arrow keys and WASD keys).
        */
        public class MyKeyAdapter extends KeyAdapter {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                    case KeyEvent.VK_A: 
                        if (direction != 'R') {
                            direction = 'L';
                        }
                        break;
                    case KeyEvent.VK_RIGHT:
                    case KeyEvent.VK_D: 
                        if (direction != 'L') {
                            direction = 'R';
                        }
                        break;
                    case KeyEvent.VK_UP:
                    case KeyEvent.VK_W: 
                        if (direction != 'D') {
                            direction = 'U';
                        }
                        break;
                    case KeyEvent.VK_DOWN:
                    case KeyEvent.VK_S: 
                        if (direction != 'U') {
                            direction = 'D';
                        }
                        break;
                    case KeyEvent.VK_SPACE:
                        paused = !paused;
                        break;
                }
            }
        }
    }
