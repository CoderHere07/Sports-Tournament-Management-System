import java.io.*;
import java.util.*;

public class Player {
    private int playerId;
    private String playerName;
    private String playerEmail;
    private Integer coachId;
    private Map<String, Double> stats;
    private Map<String, String> assessments;
    private Map<String, Integer> progress;
    public static final String PLAYERS_FILE = "players.dat";

    public Player(int playerId, String playerName, String playerEmail) {
        this.playerId = playerId;
        this.playerName = playerName;
        this.playerEmail = playerEmail;
        this.coachId = null;
        this.stats = new HashMap<>();
        this.assessments = new HashMap<>();
        this.progress = new HashMap<>();
        
        stats.put("batting_average", 45.2);
        stats.put("strike_rate", 132.5);
        assessments.put("skill_level", "Advanced");
        progress.put("training_hours", 120);
    }

    // Getters and Setters
    public int getPlayerId() { return playerId; }
    public String getPlayerName() { return playerName; }
    public String getPlayerEmail() { return playerEmail; }
    public Integer getCoachId() { return coachId; }
    public void setCoachId(Integer coachId) { this.coachId = coachId; }

    public void viewPersonalStats() {
        System.out.println("Player Stats for " + playerName + ":");
        stats.forEach((key, value) -> System.out.println(key + ": " + value));
    }

    public void viewDashboard() {
        System.out.println("Player Dashboard:");
        viewPersonalStats();
        viewSkillAssessments();
        viewProgress();
    }

    public void viewSkillAssessments() {
        System.out.println("Skill Assessments:");
        assessments.forEach((key, value) -> System.out.println(key + ": " + value));
    }

    public void viewProgress() {
        System.out.println("Training Progress:");
        progress.forEach((key, value) -> System.out.println(key + ": " + value));
    }

    public void savePlayerToFile() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PLAYERS_FILE, true))) {
            String coachIdStr = (coachId != null) ? String.valueOf(coachId) : "null";
            writer.write(playerId + "," + playerName + "," + playerEmail + "," + coachIdStr);
            writer.newLine();
        }
    }

    public static List<Player> loadAllPlayers() throws IOException {
        List<Player> players = new ArrayList<>();
        File file = new File(PLAYERS_FILE);
        if (!file.exists()) {
            return players;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    Player player = new Player(Integer.parseInt(parts[0]), parts[1], parts[2]);
                    if (parts.length >= 4 && !parts[3].equals("null")) {
                        player.setCoachId(Integer.parseInt(parts[3]));
                    }
                    players.add(player);
                }
            }
        }
        return players;
    }
}