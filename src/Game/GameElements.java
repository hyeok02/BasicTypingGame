package Game;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Iterator;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class GameElements implements KeyListener {
    private int score;
    private int playerHealth;
    private Soldier soldier;
    private Background background;
    private ArrayList<Word> words;

    public GameElements() {
        this.soldier = new Soldier(0, 400);  
        this.background = new Background(); 
        this.words = new ArrayList<>();
        this.score = 0;
        this.playerHealth = 100;
    }

    public void move() {
        soldier.move();  
        background.move(); 
    }

    public void addWord(Word word) {
        words.add(word);  
    }

    public void clearWords() {
        words.clear();  
    }

    public void draw(Graphics g) {
        int panelHeight = g.getClipBounds().height;  
        background.draw(g, panelHeight);  
        soldier.draw(g);  
        for (Word word : words) {
            word.draw(g);  
        }
        for (Bullet bullet : bullets) {
            bullet.draw(g);  
        }
    }

    private class Soldier {
        private int x, y;
        private final int speed = 2;

        public Soldier(int startX, int startY) {
            this.x = startX;
            this.y = startY;
        }
        
        public int getY() {
            return y;
        }

        public void move() {
            x += speed;  
        }

        public void draw(Graphics g) {
            g.setColor(new Color(0, 128, 255)); // 진한 파란색 군복
            g.fillRect(x, y, 50, 100);  // 군인 몸통

            g.setColor(new Color(255, 220, 185)); // 피부색
            g.fillOval(x + 10, y - 20, 30, 30);  // 얼굴

            g.setColor(new Color(34, 139, 34)); // 녹색 헬멧
            g.fillArc(x + 5, y - 25, 40, 20, 0, 180);

            g.setColor(Color.BLACK);
            g.fillRect(x + 12, y - 10, 25, 5);  // 선글라스
            g.setColor(Color.GRAY);
            g.drawLine(x + 15, y - 7, x + 20, y - 7); // 선글라스 디테일

            g.setColor(Color.DARK_GRAY);
            g.fillRect(x + 45, y + 20, 30, 5); // 총 본체
            g.fillRect(x + 70, y + 18, 5, 10); // 총구

            g.setColor(new Color(139, 69, 19)); // 갈색 벨트
            g.fillRect(x + 5, y + 70, 40, 5);

            g.setColor(new Color(0, 128, 255)); // 군복 색
            g.fillRect(x + 10, y + 80, 10, 20); // 왼쪽 다리
            g.fillRect(x + 30, y + 80, 10, 20); // 오른쪽 다리

            g.setColor(Color.BLACK);
            g.fillRect(x + 10, y + 100, 10, 5); // 왼쪽 신발
            g.fillRect(x + 30, y + 100, 10, 5); // 오른쪽 신발

            g.setColor(new Color(0, 128, 255)); // 군복 색
            g.fillRect(x - 10, y + 20, 10, 30); // 왼쪽 팔
            g.fillRect(x + 50, y + 20, 10, 30); // 오른쪽 팔

            g.setColor(Color.BLACK);
            g.fillRect(x - 10, y + 40, 10, 5); // 왼쪽 장갑
            g.fillRect(x + 50, y + 40, 10, 5); // 오른쪽 장갑

            g.setColor(Color.DARK_GRAY);
            g.fillRect(x + 10, y + 20, 30, 40); // 방탄 조끼
            g.setColor(Color.LIGHT_GRAY);
            g.drawRect(x + 10, y + 20, 30, 40); // 조끼 테두리
            g.drawLine(x + 10, y + 40, x + 40, y + 40); // 중간 디테일
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }
    }


    private class Background {
        private int x1, x2;
        private final int width = 800;
        private int currentTheme; 
        private long lastThemeChangeTime;
        private Random random = new Random();

        public Background() {
            this.x1 = 0;
            this.x2 = width;
            this.currentTheme = 0;
            this.lastThemeChangeTime = System.currentTimeMillis();
        }

        public void move() {
            x1 -= 2;
            x2 -= 2;

            if (x1 <= -width) {
                x1 = width;
            }
            if (x2 <= -width) {
                x2 = width;
            }

            if (System.currentTimeMillis() - lastThemeChangeTime > 10000) {
                currentTheme = (currentTheme + 1) % 3;
                lastThemeChangeTime = System.currentTimeMillis();
            }
        }

        public void draw(Graphics g, int panelHeight) {
            switch (currentTheme) {
                case 0 -> drawDesert(g, panelHeight);
                case 1 -> drawSwamp(g, panelHeight);
                case 2 -> drawSnowfield(g, panelHeight);
            }
        }

        private void drawDesert(Graphics g, int panelHeight) {
            g.setColor(new Color(255, 223, 100));
            g.fillRect(x1, 0, width, panelHeight);
            g.fillRect(x2, 0, width, panelHeight);

            g.setColor(new Color(255, 204, 0));
            g.fillOval(x1 + 600, 50, 100, 100);

            g.setColor(new Color(34, 139, 34));
            g.fillRect(x1 + 200, panelHeight - 120, 20, 60);
            g.fillRect(x1 + 190, panelHeight - 100, 10, 30);
            g.fillRect(x1 + 230, panelHeight - 100, 10, 30);

            g.setColor(new Color(210, 180, 140));
            g.fillOval(x1 + 50, panelHeight - 50, 200, 30);
            g.fillOval(x2 + 300, panelHeight - 70, 250, 40);

            g.setColor(new Color(255, 255, 224, 128)); // 반투명
            g.fillArc(x1 + 100, panelHeight - 200, 300, 100, 0, 180);
        }

        private void drawSwamp(Graphics g, int panelHeight) {
            g.setColor(new Color(85, 107, 47));
            g.fillRect(x1, 0, width, panelHeight);
            g.fillRect(x2, 0, width, panelHeight);

            g.setColor(new Color(34, 139, 34));
            g.fillRect(x1 + 100, panelHeight - 200, 50, 100);
            g.fillOval(x1 + 75, panelHeight - 250, 100, 100);
            g.fillRect(x2 + 300, panelHeight - 180, 60, 90);
            g.fillOval(x2 + 270, panelHeight - 230, 120, 120);

            g.setColor(new Color(0, 100, 0));
            g.fillOval(x1 + 50, panelHeight - 80, 200, 40);
            g.fillOval(x2 + 300, panelHeight - 100, 250, 50);

            g.setColor(new Color(0, 128, 0));
            g.fillRect(x1 + 300, panelHeight - 60, 50, 30);
            g.fillRect(x2 + 100, panelHeight - 50, 60, 40);

            g.setColor(new Color(0, 255, 0));
            g.fillOval(x1 + 150, panelHeight - 90, 30, 20); // 몸통
            g.fillOval(x1 + 140, panelHeight - 100, 10, 10); // 왼쪽 눈
            g.fillOval(x1 + 180, panelHeight - 100, 10, 10); // 오른쪽 눈
            g.setColor(Color.BLACK);
            g.fillOval(x1 + 145, panelHeight - 95, 5, 5); // 왼쪽 동공
            g.fillOval(x1 + 185, panelHeight - 95, 5, 5); // 오른쪽 동공

            g.setColor(Color.RED);
            g.fillOval(x1 + 400, panelHeight - 80, 20, 20);
            g.setColor(Color.YELLOW);
            g.fillOval(x1 + 405, panelHeight - 75, 10, 10);

            g.setColor(Color.PINK);
            g.fillOval(x2 + 150, panelHeight - 100, 20, 20);
            g.setColor(Color.WHITE);
            g.fillOval(x2 + 155, panelHeight - 95, 10, 10);

            g.setColor(Color.BLACK);
            g.drawArc(x1 + 50, panelHeight - 250, 20, 10, 0, 180);
            g.drawArc(x1 + 70, panelHeight - 250, 20, 10, 0, 180);
        }

        private void drawSnowfield(Graphics g, int panelHeight) {
            g.setColor(new Color(240, 248, 255));
            g.fillRect(x1, 0, width, panelHeight);
            g.fillRect(x2, 0, width, panelHeight);

            g.setColor(Color.WHITE);
            for (int i = 0; i < 5; i++) {
                int xPos = random.nextInt(width);
                int yPos = panelHeight - 140 - random.nextInt(100);
                g.fillOval(xPos, yPos, 50, 50);
                g.fillOval(xPos + 10, yPos - 40, 30, 30);
                g.setColor(Color.BLACK);
                g.fillOval(xPos + 15, yPos - 35, 5, 5);
                g.fillOval(xPos + 30, yPos - 35, 5, 5);
                g.setColor(Color.ORANGE);
                g.fillPolygon(new int[]{xPos + 25, xPos + 30, xPos + 20}, new int[]{yPos - 20, yPos - 25, yPos - 25}, 3);
                g.setColor(Color.WHITE);
            }

            for (int i = 0; i < 3; i++) {
                int xPos = x1 + random.nextInt(width);
                int yPos = panelHeight - 100 - random.nextInt(50);
                g.setColor(new Color(255, 250, 250));
                g.fillOval(xPos, yPos, 40, 20);
                g.fillOval(xPos + 30, yPos - 10, 20, 20);
                g.setColor(Color.BLACK);
                g.fillOval(xPos + 35, yPos - 5, 5, 5);
                g.fillOval(xPos + 45, yPos - 5, 5, 5);
                g.setColor(new Color(255, 250, 250));
                g.fillPolygon(new int[]{xPos + 30, xPos + 35, xPos + 25}, new int[]{yPos - 10, yPos - 20, yPos - 20}, 3);
                g.fillPolygon(new int[]{xPos + 50, xPos + 55, xPos + 45}, new int[]{yPos - 10, yPos - 20, yPos - 20}, 3);
            }

            g.setColor(Color.WHITE);
            for (int i = 0; i < 20; i++) {
                int xPos = random.nextInt(width);
                int yPos = random.nextInt(panelHeight);
                g.fillOval(xPos, yPos, 5, 5);
            }
        }
    }

    public void incrementScore(int points) {
        score += points;
    }

    public void decrementHealth(int damage) {
        playerHealth -= damage;
    }

    public int getScore() {
        return score;
    }

    public int getPlayerHealth() {
        return playerHealth;
    }

    public ArrayList<Word> getWords() {
        return words;
    }
    
    private class Bullet {
        private int x, y;
        private final int speed = 10;

        public Bullet(int startX, int startY) {
            this.x = startX;
            this.y = startY;
        }
        
        public int getX() {
            return x;
        }

        public void move() {
            x += speed;  
        }

        public void draw(Graphics g) {
            g.setColor(Color.YELLOW);
            g.fillRect(x, y, 10, 5); 
        }

        public boolean hit(Word word) {
            return word.getX() < x && x < word.getX() + word.getWidth() &&
                   word.getY() < y && y < word.getY() + word.getHeight();
        }
    }
    
    private void moveBullets() {
        Iterator<Bullet> iterator = bullets.iterator();
        while (iterator.hasNext()) {
            Bullet bullet = iterator.next();
            bullet.move();
            if (bullet.getX() > 800) { 
                iterator.remove();
            } else {
                for (Iterator<Word> wordIterator = words.iterator(); wordIterator.hasNext(); ) {
                    Word word = wordIterator.next();
                    if (bullet.hit(word)) { 
                        wordIterator.remove();
                        iterator.remove(); 
                        incrementScore(10); 
                        break;
                    }
                }
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        char key = e.getKeyChar();
        Iterator<Word> iterator = words.iterator();
        while (iterator.hasNext()) {
            Word word = iterator.next();
            if (word.getText().charAt(0) == key) {
                bullets.add(new Bullet(soldier.getX() + 50, soldier.getY() + 50));
                word.shiftText();
                if (word.getText().isEmpty()) {
                    iterator.remove();
                }
                break;
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }


    private ArrayList<Bullet> bullets = new ArrayList<>();
    
    
}


