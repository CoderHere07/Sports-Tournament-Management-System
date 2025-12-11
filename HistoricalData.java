import java.io.*;
import java.util.*;

public class HistoricalData {
    private int recordId;
    private Map<Integer, String> tournamentWinners;
    private Map<Integer, String> playerTimelines;
    private Map<String, Double> financialTrends;
    private static final String DATA_FILE = "historical_data.dat";

    public HistoricalData() {
        this.tournamentWinners = new HashMap<>();
        this.playerTimelines = new HashMap<>();
        this.financialTrends = new HashMap<>();
        loadData();
    }

    private void loadData() {
        File file = new File(DATA_FILE);
        if (!file.exists()) {
            initializeDefaultData();
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 4) {
                    tournamentWinners.put(Integer.parseInt(parts[0]), parts[1]);
                    playerTimelines.put(Integer.parseInt(parts[0]), parts[2]);
                    financialTrends.put(parts[0], Double.parseDouble(parts[3]));
                }
            }
        } catch (IOException e) {
            initializeDefaultData();
        }
    }

    private void initializeDefaultData() {
        tournamentWinners.put(2022, "Team A");
        playerTimelines.put(101, "2000-2010: Club Player, 2011-present: Professional");
        financialTrends.put("2022", 4500000.0);
        saveData();
    }

    private void saveData() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DATA_FILE))) {
            for (Map.Entry<Integer, String> entry : tournamentWinners.entrySet()) {
                writer.write(entry.getKey() + "," + entry.getValue() + "," + 
                    playerTimelines.get(entry.getKey()) + "," + 
                    financialTrends.get(entry.getKey().toString()));
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving historical data: " + e.getMessage());
        }
    }

    public String getTournamentWinner(int year) {
        return tournamentWinners.get(year);
    }

    public String getPlayerTimeline(int playerId) {
        return playerTimelines.get(playerId);
    }

    public void viewFinancialTrends() {
        System.out.println("Financial Trends:");
        financialTrends.forEach((year, amount) -> 
            System.out.println(year + ": $" + amount));
    }
}