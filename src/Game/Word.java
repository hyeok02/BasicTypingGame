package Game;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class Word {
    private int x, y;
    private String text;
    private int speed;
    private int width; // 단어의 폭
    private int height; // 단어의 높이

    private static ArrayList<String> englishWords = new ArrayList<>();
    private static final Random random = new Random();

    static {
        loadWords();
    }

    public Word(int difficultyLevel) {
        x = random.nextInt(700);
        y = 0;
        text = getRandomEnglishWord();
        speed = random.nextInt(3) + 2 + difficultyLevel;
        width = text.length() * 10; // 단어 길이에 따라 폭 설정
        height = 20; // 고정 높이 설정
    }

    public Word(String customWord, int difficultyLevel) {
        x = random.nextInt(700);
        y = 0;
        text = customWord;
        speed = random.nextInt(3) + 2 + difficultyLevel;
        width = text.length() * 10;
        height = 20;
    }

    private static void loadWords() {
        try (BufferedReader br = new BufferedReader(new FileReader("src/Words.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                englishWords.add(line.trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (englishWords.isEmpty()) {
            englishWords.add("java");
            englishWords.add("program");
            englishWords.add("keyboard");
            englishWords.add("multithread");
        }
    }

    private String getRandomEnglishWord() {
        return englishWords.get(random.nextInt(englishWords.size()));
    }

    public void move() {
        y += speed;
    }

    public boolean isOutOfBounds() {
        return y > 600;
    }

    public int getLength() {
        return text.length();
    }

    public String getText() {
        return text;
    }

    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString(text, x, y);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void shiftText() {
        if (text.length() > 1) {
            text = text.substring(1);
        } else {
            text = "";
        }
    }
}
