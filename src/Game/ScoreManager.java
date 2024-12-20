package Game;

import java.io.*;
import java.util.*;

public class ScoreManager {
    private static final String SCORE_FILE = "scores.txt";

    public void saveScore(String playerName, int score) {  // 중복된 'public' 제거
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(SCORE_FILE, true))) {
            writer.write(playerName + "," + score);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getTopScores() {
        List<String> scores = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(SCORE_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                scores.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        scores.sort((a, b) -> {
            int scoreA = Integer.parseInt(a.split(",")[1]);
            int scoreB = Integer.parseInt(b.split(",")[1]);
            return Integer.compare(scoreB, scoreA);
        });

        StringBuilder ranking = new StringBuilder();
        for (int i = 0; i < Math.min(10, scores.size()); i++) {
            String[] parts = scores.get(i).split(",");
            ranking.append(i + 1).append(". ").append(parts[0]).append(": ").append(parts[1]).append(" points\n");
        }
        return ranking.toString();
    }
}
