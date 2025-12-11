import java.io.*;
import java.util.*;

public class Coach {
    private int coachId;
    private String coachName;
    private String coachEmail;
    private String specialization;
    private List<Integer> assignedPlayers;
    private Map<String, String> trainingPlans;
    public static final String COACHES_FILE = "coaches.dat";

    public Coach(int coachId, String coachName, String coachEmail, String specialization) {
        this.coachId = coachId;
        this.coachName = coachName;
        this.coachEmail = coachEmail;
        this.specialization = specialization;
        this.assignedPlayers = new ArrayList<>();
        this.trainingPlans = new HashMap<>();
        
        trainingPlans.put("Batting", "Daily 2-hour practice with focus on technique");
        trainingPlans.put("Bowling", "Alternate days with focus on speed and accuracy");
    }

    // Getters
    public int getCoachId() { return coachId; }
    public String getCoachName() { return coachName; }
    public String getCoachEmail() { return coachEmail; }
    public String getSpecialization() { return specialization; }
    public List<Integer> getAssignedPlayers() { return assignedPlayers; }

    public void viewAssignedPlayers() {
        System.out.println("Players assigned to Coach " + coachName + ":");
        try {
            List<Player> allPlayers = Player.loadAllPlayers();
            for (Integer playerId : assignedPlayers) {
                for (Player player : allPlayers) {
                    if (player.getPlayerId() == playerId) {
                        System.out.println("- " + player.getPlayerName());
                        break;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading player data: " + e.getMessage());
        }
    }

    public void createTrainingPlan(String trainingType, String planDetails) {
        trainingPlans.put(trainingType, planDetails);
        System.out.println("Training plan created for " + trainingType);
    }

    public void viewTrainingPlans() {
        System.out.println("Training Plans by " + coachName + ":");
        trainingPlans.forEach((type, plan) -> 
            System.out.println(type + ": " + plan));
    }

    public void assignPlayer(int playerId) {
        if (!assignedPlayers.contains(playerId)) {
            assignedPlayers.add(playerId);
            System.out.println("Player " + playerId + " assigned to coach " + coachName);
        } else {
            System.out.println("Player is already assigned to this coach");
        }
    }

    public void saveCoachToFile() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(COACHES_FILE, true))) {
            writer.write(coachId + "," + coachName + "," + coachEmail + "," + specialization);
            if (!assignedPlayers.isEmpty()) {
                writer.write("," + String.join(";", assignedPlayers.stream()
                    .map(String::valueOf)
                    .toArray(String[]::new)));
            }
            writer.newLine();
        }
    }

    public static List<Coach> loadAllCoaches() throws IOException {
        List<Coach> coaches = new ArrayList<>();
        File file = new File(COACHES_FILE);
        if (!file.exists()) {
            return coaches;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 4) {
                    Coach coach = new Coach(Integer.parseInt(parts[0]), parts[1], parts[2], parts[3]);
                    if (parts.length >= 5) {
                        String[] playerIds = parts[4].split(";");
                        for (String id : playerIds) {
                            if (!id.isEmpty()) {
                                coach.getAssignedPlayers().add(Integer.parseInt(id));
                            }
                        }
                    }
                    coaches.add(coach);
                }
            }
        }
        return coaches;
    }

    public static Coach coachLogin(String email) throws IOException {
        List<Coach> coaches = loadAllCoaches();
        for (Coach coach : coaches) {
            if (coach.getCoachEmail().equals(email)) {
                return coach;
            }
        }
        return null;
    }
}