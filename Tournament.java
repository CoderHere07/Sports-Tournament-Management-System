import java.io.*;
import java.util.*;

public class Tournament {
    private int tournamentId;
    private String tournamentName;
    private List<String> ballByBallUpdates;
    private Map<String, Integer> pointTable;
    private String venueAnalysis;
    private static final String TOURNAMENT_FILE = "tournaments.dat";

    public Tournament(int tournamentId, String tournamentName) {
        this.tournamentId = tournamentId;
        this.tournamentName = tournamentName;
        this.ballByBallUpdates = new ArrayList<>();
        this.pointTable = new HashMap<>();
        this.venueAnalysis = "";
        loadTournamentData();
    }

    private void loadTournamentData() {
        File file = new File(TOURNAMENT_FILE);
        if (!file.exists()) {
            ballByBallUpdates.add("Over 1: 4 runs");
            pointTable.put("Team A", 12);
            venueAnalysis = "Pitch: Dry, Weather: Sunny";
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith(tournamentId + ",")) {
                    String[] parts = line.split(",");
                    if (parts.length >= 4) {
                        ballByBallUpdates.add(parts[2]);
                        pointTable.put(parts[3], Integer.parseInt(parts[4]));
                    }
                }
            }
        } catch (IOException e) {
            ballByBallUpdates.add("Over 1: 4 runs");
            pointTable.put("Team A", 12);
            venueAnalysis = "Pitch: Dry, Weather: Sunny";
        }
    }

    public void addBallUpdate(String update) {
        ballByBallUpdates.add(update);
        saveTournamentData();
    }

    private void saveTournamentData() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TOURNAMENT_FILE, true))) {
            for (String update : ballByBallUpdates) {
                writer.write(tournamentId + "," + tournamentName + "," + update);
                for (Map.Entry<String, Integer> entry : pointTable.entrySet()) {
                    writer.write("," + entry.getKey() + "," + entry.getValue());
                }
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving tournament data: " + e.getMessage());
        }
    }

    public void viewBallByBallUpdates() {
        System.out.println("Ball-by-ball updates:");
        ballByBallUpdates.forEach(System.out::println);
    }

    public void viewPointTable() {
        System.out.println("Point Table:");
        pointTable.forEach((team, points) -> 
            System.out.println(team + ": " + points + " points"));
    }

    public void viewVenueAnalysis() {
        System.out.println("Venue Analysis: " + venueAnalysis);
    }

    public int getTournamentId() {
        return tournamentId;
    }

    public String getTournamentName() {
        return tournamentName;
    }

}