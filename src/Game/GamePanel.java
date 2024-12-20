package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;

public class GamePanel extends JPanel {
    private GameElements gameElements;  
    private int score;
    private int playerHealth;
    private boolean gameRunning;
    private ScoreManager scoreManager;
    private String playerName;
    private JTextField inputField; 
    private JButton startButton;  
    private JButton restartButton; 
    private JButton saveWordsButton; 
    private JTextField customWordInput;
    private JComboBox<String> difficultyComboBox;
    private boolean gameStarted = false; 
    private int difficultyLevel; 
    private long startTime; 
    private boolean customWordMode = false;

    public GamePanel() {
        gameElements = new GameElements();  
        score = 0;
        playerHealth = 100;
        gameRunning = false;
        difficultyLevel = 1;

        inputField = new JTextField();
        inputField.setFont(new Font("Arial", Font.PLAIN, 18));
        inputField.addActionListener(e -> checkInput());
        inputField.setEnabled(false); // 게임이 시작되기 전에는 비활성화
        inputField.setVisible(false); // 게임 시작 전에는 텍스트란 숨기기

        startButton = new JButton("Game Start");
        startButton.setFont(new Font("Arial", Font.BOLD, 18));
        startButton.addActionListener(e -> startGame());

        restartButton = new JButton("Restart Game");
        restartButton.setFont(new Font("Arial", Font.BOLD, 18));
        restartButton.addActionListener(e -> restartGame());
        restartButton.setVisible(false); // 게임 종료 후에만 보이도록 설정

        saveWordsButton = new JButton("Save Words");
        saveWordsButton.setFont(new Font("Arial", Font.BOLD, 18));
        saveWordsButton.addActionListener(e -> toggleCustomWordInput());

        customWordInput = new JTextField();
        customWordInput.setFont(new Font("Arial", Font.PLAIN, 18));
        customWordInput.setEnabled(false);
        customWordInput.setVisible(false);
        customWordInput.addActionListener(e -> addCustomWord());

        String[] difficultyOptions = {"LV1 (Easy)", "LV2 (Medium)", "LV3 (Hard)"};
        difficultyComboBox = new JComboBox<>(difficultyOptions);
        difficultyComboBox.setFont(new Font("Arial", Font.PLAIN, 18));
        difficultyComboBox.setSelectedIndex(0); // 기본값: LV1
        difficultyComboBox.setBounds(300, 180, 200, 30);

        setLayout(null);

        int inputFieldY = 500;
        inputField.setBounds(200, inputFieldY, 400, 30); 
        startButton.setBounds(300, 250, 200, 50);
        saveWordsButton.setBounds(300, 320, 200, 50); 
        restartButton.setBounds(300, 250, 200, 50);
        customWordInput.setBounds(200, 400, 400, 30); 

        add(inputField);
        add(startButton);
        add(restartButton);
        add(saveWordsButton); 
        add(customWordInput); 
        add(difficultyComboBox); 

        scoreManager = new ScoreManager();
        setFocusable(true);
    }

    private void checkInput() {
        String input = inputField.getText();
        inputField.setText(""); 
        Iterator<Word> iterator = gameElements.getWords().iterator();

        while (iterator.hasNext()) {
            Word word = iterator.next();
            if (word.getText().equals(input)) {
                score += word.getLength() * 10; 
                iterator.remove();
                repaint();
                return;
            }
        }
    }

    private void startGame() {
        // 난이도 설정
        difficultyLevel = difficultyComboBox.getSelectedIndex() + 1; 
        startButton.setVisible(false);
        saveWordsButton.setVisible(false); 
        difficultyComboBox.setVisible(false); 
        inputField.setEnabled(true);
        inputField.setVisible(true); 
        gameRunning = true;
        gameStarted = true;

        playerName = JOptionPane.showInputDialog(this, "Enter your name:", "Player");
        if (playerName == null || playerName.isEmpty()) {
            playerName = "Unknown";
        }

        startTime = System.currentTimeMillis();
        new Thread(this::gameLoop).start();
    }

    private void gameLoop() {
        while (gameRunning) {
            try {
                Thread.sleep(100);
                gameElements.move();  

                Iterator<Word> iterator = gameElements.getWords().iterator();

                while (iterator.hasNext()) {
                    Word word = iterator.next();
                    word.move(); 
                    if (word.isOutOfBounds()) {
                        playerHealth -= 10;
                        iterator.remove();
                    }
                }

                if (Math.random() < 0.1) {
                    gameElements.addWord(new Word(difficultyLevel)); 
                }

                if (playerHealth <= 0) {
                    gameRunning = false;
                    inputField.setEnabled(false); 
                    showGameOverDialog();
                }
                repaint();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void toggleCustomWordInput() {
        if (customWordMode) {
            customWordInput.setVisible(false);
            customWordMode = false;
        } else {
            customWordInput.setVisible(true); 
            customWordInput.setEnabled(true);
            customWordMode = true;
        }
    }

    private void addCustomWord() {
        String customWord = customWordInput.getText();
        if (!customWord.isEmpty()) {
            gameElements.addWord(new Word(customWord, difficultyLevel));
            JOptionPane.showMessageDialog(this, "Word added successfully!");
            customWordInput.setText(""); 
            customWordInput.setVisible(false); 
            customWordMode = false; 
        }
    }

    private void showGameOverDialog() {
        SwingUtilities.invokeLater(() -> {
            scoreManager.saveScore(playerName, score);
            String ranking = scoreManager.getTopScores();
            JOptionPane.showMessageDialog(this, "Game Over! Your Score: " + score + "\n\nTop Scores:\n" + ranking);
            restartButton.setVisible(true);
        });
    }

    private void restartGame() {
        score = 0;
        playerHealth = 100;
        difficultyLevel = 1;
        gameElements.clearWords();
        inputField.setEnabled(true);
        inputField.setVisible(true); 
        gameRunning = true;
        restartButton.setVisible(false); 
        new Thread(this::gameLoop).start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        
        super.paintComponent(g);

g.setFont(new Font("Arial", Font.BOLD, 16));
g.setColor(Color.BLACK);
g.drawString("Score: " + score, 10, 20);
g.drawString("Health: " + playerHealth, 10, 40);

        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.setColor(Color.BLACK);
        g.drawString("Score: " + score, 10, 20);
        g.drawString("Health: " + playerHealth, 10, 40);
        

        // 배경 색상 설정
        g.setColor(new Color(200, 230, 255));
        g.fillRect(0, 0, getWidth(), getHeight()); 
        g.fillRect(0, 0, getWidth(), getHeight());

        if (gameStarted) {
            g.setFont(new Font("Arial", Font.BOLD, 16));
            g.setColor(Color.BLACK);
            g.drawString("Score: " + score, 10, 20);
            g.drawString("Health: " + playerHealth, 10, 40);

            gameElements.draw(g);  
        }

        if (gameStarted) {
            g.setColor(Color.BLACK);
            int rankingBoxWidth = 250;
            int lineHeight = 20; 
            int yOffset = 70; 

            String ranking = scoreManager.getTopScores();
            String[] rankings = ranking.split("\n");

            int rankingBoxHeight = 50 + (rankings.length * lineHeight);

            g.fillRect(500, 50, rankingBoxWidth, rankingBoxHeight);
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.PLAIN, 14));

            g.drawString("Ranking", 510, yOffset);
            g.drawString("Current Score: " + score, 510, yOffset + lineHeight);

            int yPosition = yOffset + 2 * lineHeight; // 랭킹 텍스트 시작점
            for (String rank : rankings) {
                g.drawString(rank, 510, yPosition);
                yPosition += lineHeight; 
            }
        } 

    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Typing Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.add(new GamePanel());
        frame.setVisible(true);
    }
}
