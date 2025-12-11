import java.io.*;
import java.util.*;

public class UserManagment {
    private static Scanner scanner = new Scanner(System.in);
    private static Admin currentAdmin = null;
    private static Player currentPlayer = null;
    private static Coach currentCoach = null;

  public static void main(String[] args) {
        initializeSystem();
        displayWelcomeMessage();

        while (true) {
            displayMainMenu();
            int choice = getIntInput("Enter your choice: ");

            switch (choice) {
                case 1:
                    adminLogin();
                    break;
                case 2:
                    playerLoginMenu();
                    break;
                case 3:
                    coachLoginMenu();
                    break;
                case 4:
                    viewAsGuest();
                    break;
                case 5:
                    System.out.println("Thank you for using Sports Arena System. Goodbye!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
   private static void initializeSystem() {
        try {
            new File("users.dat").createNewFile();
            new File("players.dat").createNewFile();
            new File("coaches.dat").createNewFile();
            new File("tournaments.dat").createNewFile();
            new File("historical_data.dat").createNewFile();

            if (new File("users.dat").length() == 0) {
                Admin defaultAdmin = new Admin("admin1", "System Admin", "admin@sportsarena.com", "admin123");
                defaultAdmin.saveAdminToFile();
            }
        } catch (IOException e) {
            System.out.println("Error initializing system files: " + e.getMessage());
        }
    }



    private static void displayWelcomeMessage() {
        System.out.println("\n====================================");
        System.out.println("    WELCOME TO SPORTS ARENA SYSTEM");
        System.out.println("====================================");
    }




   private static void displayMainMenu() {
        System.out.println("\n--- MAIN MENU ---");
        System.out.println("1. Admin Login");
        System.out.println("2. Player Login/Signup");
        System.out.println("3. Coach Login/Signup");      
        System.out.println("4. View as Guest");
        System.out.println("5. Exit System");
    }
    

    
    private static void viewAsGuest() {
        Viewer viewer = new Viewer();
        while (true) {
            System.out.println("\n--- GUEST VIEWER MENU ---");
            System.out.println("1. View Tournament Details");
            System.out.println("2. View Historical Data");
            System.out.println("3. View Live Engagement Features");
            System.out.println("4. View Technical Details");
            System.out.println("5. Back to Main Menu");

            int choice = getIntInput("Enter your choice: ");
            switch (choice) {
                case 1:
                    viewer.viewTournamentDetails();
                    break;
                case 2:
                    viewer.viewHistoricalData();
                    break;
                case 3:
                    viewer.viewLiveEngagement();
                    break;
                case 4:
                    viewer.viewTechnicalDetails();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void adminLogin() {
        System.out.println("\n--- ADMIN LOGIN ---");
        String email = getStringInput("Email: ");
        String password = getStringInput("Password: ");

        try {
            List<Admin> admins = Admin.loadAllAdmins();
            for (Admin admin : admins) {
                if (admin.login(email, password)) {
                    currentAdmin = admin;
                    System.out.println("\nLogin successful! Welcome, " + admin.getAdminName() + "!");
                    adminMenu();
                    return;
                }
            }
            System.out.println("Invalid credentials or admin not found.");
        } catch (IOException e) {
            System.out.println("Error accessing admin data: " + e.getMessage());
        }
    }

    private static void playerLoginMenu() {
        System.out.println("\n--- PLAYER MENU ---");
        System.out.println("1. Login");
        System.out.println("2. Signup");
        System.out.println("3. Back to Main Menu");
        
        int choice = getIntInput("Enter your choice: ");
        switch (choice) {
            case 1:
                playerLogin();
                break;
            case 2:
                registerPlayer();
                break;
            case 3:
                return;
            default:
                System.out.println("Invalid choice.");
        }
    }

    private static void playerLogin() {
        System.out.println("\n--- PLAYER LOGIN ---");
        String email = getStringInput("Email: ");

        try {
            List<Player> players = Player.loadAllPlayers();
            for (Player player : players) {
                if (player.getPlayerEmail().equals(email)) {
                    currentPlayer = player;
                    System.out.println("\nLogin successful! Welcome, " + player.getPlayerName() + "!");
                    playerMenu();
                    return;
                }
            }
            System.out.println("Player not found. Please register first.");
        } catch (IOException e) {
            System.out.println("Error accessing player data: " + e.getMessage());
        }
    }

    private static void coachLoginMenu() {
        System.out.println("\n--- COACH MENU ---");
        System.out.println("1. Login");
        System.out.println("2. Signup");
        System.out.println("3. Back to Main Menu");
        
        int choice = getIntInput("Enter your choice: ");
        switch (choice) {
            case 1:
                coachLogin();
                break;
            case 2:
                coachSignup();
                break;
            case 3:
                return;
            default:
                System.out.println("Invalid choice.");
        }
    }

    private static void coachLogin() {
        System.out.println("\n--- COACH LOGIN ---");
        String email = getStringInput("Email: ");

        try {
            Coach coach = Coach.coachLogin(email);
            if (coach != null) {
                currentCoach = coach;
                System.out.println("\nLogin successful! Welcome, " + coach.getCoachName() + "!");
                coachMenu();
            } else {
                System.out.println("Coach not found. Please register first.");
            }
        } catch (IOException e) {
            System.out.println("Error accessing coach data: " + e.getMessage());
        }
    }

    private static void registerPlayer() {
        System.out.println("\n--- PLAYER REGISTRATION ---");
        int id = getIntInput("Player ID: ");
        String name = getStringInput("Full Name: ");
        String email = getStringInput("Email: ");
        
        try {
            // Check if player already exists
            boolean exists = Player.loadAllPlayers().stream()
                .anyMatch(p -> p.getPlayerId() == id || p.getPlayerEmail().equalsIgnoreCase(email));
            
            if (exists) {
                System.out.println("Player with this ID or email already exists!");
            } else {
                Player newPlayer = new Player(id, name, email);
                newPlayer.savePlayerToFile();
                System.out.println("Registration successful! You can now login.");
            }
        } catch (IOException e) {
            System.out.println("Error saving player: " + e.getMessage());
        }
    }

    private static void coachSignup() {
        System.out.println("\n--- COACH REGISTRATION ---");
        int id = getIntInput("Coach ID: ");
        String name = getStringInput("Full Name: ");
        String email = getStringInput("Email: ");
        String specialization = getStringInput("Specialization: ");
        
        try {
            // Check if coach already exists
            boolean exists = Coach.loadAllCoaches().stream()
                .anyMatch(c -> c.getCoachId() == id || c.getCoachEmail().equalsIgnoreCase(email));
            
            if (exists) {
                System.out.println("Coach with this ID or email already exists!");
            } else {
                Coach newCoach = new Coach(id, name, email, specialization);
                newCoach.saveCoachToFile();
                System.out.println("Registration successful! You can now login.");
            }
        } catch (IOException e) {
            System.out.println("Error saving coach: " + e.getMessage());
        }
    }

    private static void adminMenu() {
        while (currentAdmin != null) {
            System.out.println("\n--- ADMIN DASHBOARD ---");
            System.out.println("1. Manage Players");
            System.out.println("2. Manage Coaches");
            System.out.println("3. Manage Tournaments");
            System.out.println("4. View System Reports");
            System.out.println("5. Assign Coach to Player");
            System.out.println("6. Logout");

            int choice = getIntInput("Enter your choice: ");

            switch (choice) {
                case 1:
                    managePlayers();
                    break;
                case 2:
                    manageCoaches();
                    break;
                case 3:
                    manageTournaments();
                    break;
                case 4:
                    viewSystemReports();
                    break;
                case 5:
                    assignCoachToPlayer();
                    break;
                case 6:
                    currentAdmin.logout();
                    currentAdmin = null;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void playerMenu() {
        while (currentPlayer != null) {
            System.out.println("\n--- PLAYER DASHBOARD ---");
            System.out.println("1. View My Dashboard");
            System.out.println("2. View Training Progress");
            System.out.println("3. View Tournaments");
            System.out.println("4. View My Coach");
            System.out.println("5. Logout");

            int choice = getIntInput("Enter your choice: ");

            switch (choice) {
                case 1:
                    currentPlayer.viewDashboard();
                    break;
                case 2:
                    currentPlayer.viewProgress();
                    break;
                case 3:
                    viewTournaments();
                    break;
                case 4:
                    viewCoachDetails();
                    break;
                case 5:
                    currentPlayer = null;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void coachMenu() {
        while (currentCoach != null) {
            System.out.println("\n--- COACH DASHBOARD ---");
            System.out.println("1. View Assigned Players");
            System.out.println("2. Create Training Plan");
            System.out.println("3. View Training Plans");
            System.out.println("4. Assign Player");
            System.out.println("5. View Player Performance");
            System.out.println("6. Logout");

            int choice = getIntInput("Enter your choice: ");

            switch (choice) {
                case 1:
                    currentCoach.viewAssignedPlayers();
                    break;
                case 2:
                    String type = getStringInput("Enter training type: ");
                    String plan = getStringInput("Enter plan details: ");
                    currentCoach.createTrainingPlan(type, plan);
                    break;
                case 3:
                    currentCoach.viewTrainingPlans();
                    break;
                case 4:
                    int playerId = getIntInput("Enter player ID to assign: ");
                    currentCoach.assignPlayer(playerId);
                    break;
                case 5:
                    int pId = getIntInput("Enter player ID to view performance: ");
                    viewPlayerPerformance(pId);
                    break;
                case 6:
                    currentCoach = null;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void managePlayers() {
        while (true) {
            System.out.println("\n--- PLAYER MANAGEMENT ---");
            System.out.println("1. View All Players");
            System.out.println("2. Add New Player");
            System.out.println("3. Remove Player");
            System.out.println("4. Search Player");
            System.out.println("5. Back to Admin Menu");

            int choice = getIntInput("Enter your choice: ");

            try {
                switch (choice) {
                    case 1:
                        List<Player> players = Player.loadAllPlayers();
                        if (players.isEmpty()) {
                            System.out.println("No players found in the system.");
                        } else {
                            System.out.println("\n--- ALL PLAYERS ---");
                            System.out.printf("%-10s %-20s %-30s\n", "ID", "Name", "Email");
                            for (Player p : players) {
                                System.out.printf("%-10d %-20s %-30s\n", 
                                    p.getPlayerId(), p.getPlayerName(), p.getPlayerEmail());
                            }
                        }
                        break;
                    case 2:
                        System.out.println("\n--- ADD NEW PLAYER ---");
                        int id = getIntInput("Player ID: ");
                        String name = getStringInput("Full Name: ");
                        String email = getStringInput("Email: ");
                        
                        boolean exists = Player.loadAllPlayers().stream()
                            .anyMatch(p -> p.getPlayerId() == id || p.getPlayerEmail().equalsIgnoreCase(email));
                        
                        if (exists) {
                            System.out.println("Player with this ID or email already exists!");
                        } else {
                            Player newPlayer = new Player(id, name, email);
                            newPlayer.savePlayerToFile();
                            System.out.println("Player added successfully!");
                        }
                        break;
                    case 3:
                        System.out.println("\n--- REMOVE PLAYER ---");
                        int playerId = getIntInput("Enter Player ID to remove: ");
                        List<Player> allPlayers = Player.loadAllPlayers();
                        List<Player> updatedPlayers = new ArrayList<>();
                        
                        boolean removed = false;
                        for (Player p : allPlayers) {
                            if (p.getPlayerId() != playerId) {
                                updatedPlayers.add(p);
                            } else {
                                removed = true;
                            }
                        }
                        
                        if (removed) {
                            try (BufferedWriter writer = new BufferedWriter(new FileWriter(Player.PLAYERS_FILE))) {
                                for (Player p : updatedPlayers) {
                                    writer.write(p.getPlayerId() + "," + p.getPlayerName() + "," + p.getPlayerEmail());
                                    writer.newLine();
                                }
                            }
                            System.out.println("Player removed successfully!");
                        } else {
                            System.out.println("Player not found!");
                        }
                        break;
                    case 4:
                        System.out.println("\n--- SEARCH PLAYER ---");
                        String searchTerm = getStringInput("Enter player name or ID to search: ");
                        List<Player> searchResults = new ArrayList<>();
                        
                        for (Player p : Player.loadAllPlayers()) {
                            if (p.getPlayerName().toLowerCase().contains(searchTerm.toLowerCase()) || 
                                String.valueOf(p.getPlayerId()).contains(searchTerm)) {
                                searchResults.add(p);
                            }
                        }
                        
                        if (searchResults.isEmpty()) {
                            System.out.println("No matching players found.");
                        } else {
                            System.out.println("\nSearch Results:");
                            System.out.printf("%-10s %-20s %-30s\n", "ID", "Name", "Email");
                            for (Player p : searchResults) {
                                System.out.printf("%-10d %-20s %-30s\n", 
                                    p.getPlayerId(), p.getPlayerName(), p.getPlayerEmail());
                            }
                        }
                        break;
                    case 5:
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (IOException e) {
                System.out.println("Error accessing player data: " + e.getMessage());
            }
        }
    }

    private static void manageCoaches() {
        while (true) {
            System.out.println("\n--- COACH MANAGEMENT ---");
            System.out.println("1. View All Coaches");
            System.out.println("2. Add New Coach");
            System.out.println("3. Remove Coach");
            System.out.println("4. Search Coach");
            System.out.println("5. Back to Admin Menu");

            int choice = getIntInput("Enter your choice: ");

            try {
                switch (choice) {
                    case 1:
                        List<Coach> coaches = Coach.loadAllCoaches();
                        if (coaches.isEmpty()) {
                            System.out.println("No coaches found in the system.");
                        } else {
                            System.out.println("\n--- ALL COACHES ---");
                            System.out.printf("%-10s %-20s %-30s %-20s\n", "ID", "Name", "Email", "Specialization");
                            for (Coach c : coaches) {
                                System.out.printf("%-10d %-20s %-30s %-20s\n", 
                                    c.getCoachId(), c.getCoachName(), c.getCoachEmail(), c.getSpecialization());
                            }
                        }
                        break;
                    case 2:
                        System.out.println("\n--- ADD NEW COACH ---");
                        int id = getIntInput("Coach ID: ");
                        String name = getStringInput("Full Name: ");
                        String email = getStringInput("Email: ");
                        String specialization = getStringInput("Specialization: ");
                        
                        boolean exists = Coach.loadAllCoaches().stream()
                            .anyMatch(c -> c.getCoachId() == id || c.getCoachEmail().equalsIgnoreCase(email));
                        
                        if (exists) {
                            System.out.println("Coach with this ID or email already exists!");
                        } else {
                            Coach newCoach = new Coach(id, name, email, specialization);
                            newCoach.saveCoachToFile();
                            System.out.println("Coach added successfully!");
                        }
                        break;
                    case 3:
                        System.out.println("\n--- REMOVE COACH ---");
                        int coachId = getIntInput("Enter Coach ID to remove: ");
                        List<Coach> allCoaches = Coach.loadAllCoaches();
                        List<Coach> updatedCoaches = new ArrayList<>();
                        
                        boolean removed = false;
                        for (Coach c : allCoaches) {
                            if (c.getCoachId() != coachId) {
                                updatedCoaches.add(c);
                            } else {
                                removed = true;
                            }
                        }
                        
                        if (removed) {
                            try (BufferedWriter writer = new BufferedWriter(new FileWriter(Coach.COACHES_FILE))) {
                                for (Coach c : updatedCoaches) {
                                    writer.write(c.getCoachId() + "," + c.getCoachName() + "," + 
                                               c.getCoachEmail() + "," + c.getSpecialization());
                                    writer.newLine();
                                }
                            }
                            System.out.println("Coach removed successfully!");
                        } else {
                            System.out.println("Coach not found!");
                        }
                        break;
                    case 4:
                        System.out.println("\n--- SEARCH COACH ---");
                        String searchTerm = getStringInput("Enter coach name, ID, or specialization to search: ");
                        List<Coach> searchResults = new ArrayList<>();
                        
                        for (Coach c : Coach.loadAllCoaches()) {
                            if (c.getCoachName().toLowerCase().contains(searchTerm.toLowerCase()) || 
                                String.valueOf(c.getCoachId()).contains(searchTerm) ||
                                c.getSpecialization().toLowerCase().contains(searchTerm.toLowerCase())) {
                                searchResults.add(c);
                            }
                        }
                        
                        if (searchResults.isEmpty()) {
                            System.out.println("No matching coaches found.");
                        } else {
                            System.out.println("\nSearch Results:");
                            System.out.printf("%-10s %-20s %-30s %-20s\n", "ID", "Name", "Email", "Specialization");
                            for (Coach c : searchResults) {
                                System.out.printf("%-10d %-20s %-30s %-20s\n", 
                                    c.getCoachId(), c.getCoachName(), c.getCoachEmail(), c.getSpecialization());
                            }
                        }
                        break;
                    case 5:
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (IOException e) {
                System.out.println("Error accessing coach data: " + e.getMessage());
            }
        }
    }

    private static void manageTournaments() {
        while (true) {
            System.out.println("\n--- TOURNAMENT MANAGEMENT ---");
            System.out.println("1. View All Tournaments");
            System.out.println("2. Create New Tournament");
            System.out.println("3. Update Tournament");
            System.out.println("4. Delete Tournament");
            System.out.println("5. Add Match Update");
            System.out.println("6. View Tournament Details");
            System.out.println("7. Back to Admin Menu");

            int choice = getIntInput("Enter your choice: ");

            try {
                switch (choice) {
                    case 1:
                        System.out.println("\n--- CURRENT TOURNAMENTS ---");
                        System.out.println("1. Champions League");
                        System.out.println("2. Premier League");
                        System.out.println("3. World Cup");
                        break;
                    case 2:
                        System.out.println("\n--- CREATE NEW TOURNAMENT ---");
                        int id = getIntInput("Tournament ID: ");
                        String name = getStringInput("Tournament Name: ");
                        
                        Tournament newTournament = new Tournament(id, name);
                        System.out.println("Tournament created successfully!");
                        break;
                    case 3:
                        System.out.println("\n--- UPDATE TOURNAMENT ---");
                        int updateId = getIntInput("Enter Tournament ID to update: ");
                        String newName = getStringInput("Enter new tournament name: ");
                        
                        System.out.println("Tournament updated successfully!");
                        break;
                    case 4:
                        System.out.println("\n--- DELETE TOURNAMENT ---");
                        int deleteId = getIntInput("Enter Tournament ID to delete: ");
                        
                        System.out.println("Tournament deleted successfully!");
                        break;
                    case 5:
                        System.out.println("\n--- ADD MATCH UPDATE ---");
                        int tournamentId = getIntInput("Enter Tournament ID: ");
                        String update = getStringInput("Enter match update: ");
                        
                        Tournament tournament = new Tournament(tournamentId, "Temp Name");
                        tournament.addBallUpdate(update);
                        System.out.println("Match update added successfully!");
                        break;
                    case 6:
                        System.out.println("\n--- VIEW TOURNAMENT DETAILS ---");
                        int viewId = getIntInput("Enter Tournament ID: ");
                        Tournament viewTournament = new Tournament(viewId, "Sample Tournament");
                        System.out.println("\nTournament Details:");
                        System.out.println("ID: " + viewTournament.getTournamentId());
                        System.out.println("Name: " + viewTournament.getTournamentName());
                        System.out.println("\nRecent Updates:");
                        viewTournament.viewBallByBallUpdates();
                        System.out.println("\nCurrent Standings:");
                        viewTournament.viewPointTable();
                        System.out.println("\nVenue Information:");
                        viewTournament.viewVenueAnalysis();
                        break;
                    case 7:
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("Error accessing tournament data: " + e.getMessage());
            }
        }
    }

    private static void viewSystemReports() {
        System.out.println("\nGenerating system reports...");
        HistoricalData history = new HistoricalData();
        System.out.println("\n--- FINANCIAL TRENDS ---");
        history.viewFinancialTrends();
        
        Technical tech = new Technical();
        System.out.println("\n--- SYSTEM STATUS ---");
        tech.checkUpdateSpeed();
        tech.exportData();
        tech.checkWeatherApiStatus();
        
        try {
            System.out.println("\n--- SYSTEM STATISTICS ---");
            System.out.println("Total Players: " + Player.loadAllPlayers().size());
            System.out.println("Total Coaches: " + Coach.loadAllCoaches().size());
        } catch (IOException e) {
            System.out.println("Error loading statistics: " + e.getMessage());
        }
    }

    private static void viewTournaments() {
        System.out.println("\nDisplaying tournament information...");
        Tournament tournament = new Tournament(1, "Champions League");
        System.out.println("\n--- TOURNAMENT UPDATES ---");
        tournament.viewBallByBallUpdates();
        System.out.println("\n--- CURRENT STANDINGS ---");
        tournament.viewPointTable();
        System.out.println("\n--- VENUE ANALYSIS ---");
        tournament.viewVenueAnalysis();
    }

    private static void viewCoachDetails() {
        System.out.println("\nFetching coach details...");
        if (currentPlayer != null) {
            try {
                List<Coach> coaches = Coach.loadAllCoaches();
                boolean found = false;
                
                for (Coach coach : coaches) {
                    if (coach.getAssignedPlayers().contains(currentPlayer.getPlayerId())) {
                        System.out.println("\nYour Coach:");
                        System.out.println("Name: " + coach.getCoachName());
                        System.out.println("Specialization: " + coach.getSpecialization());
                        System.out.println("Email: " + coach.getCoachEmail());
                        System.out.println("\nTraining Plans:");
                        coach.viewTrainingPlans();
                        found = true;
                        break;
                    }
                }
                
                if (!found) {
                    System.out.println("No coach assigned to you yet.");
                }
            } catch (IOException e) {
                System.out.println("Error accessing coach data: " + e.getMessage());
            }
        }
    }

    private static void viewPlayerPerformance(int playerId) {
        System.out.println("\nFetching player performance...");
        try {
            List<Player> players = Player.loadAllPlayers();
            for (Player player : players) {
                if (player.getPlayerId() == playerId) {
                    System.out.println("\nPerformance for " + player.getPlayerName() + ":");
                    player.viewPersonalStats();
                    
                    System.out.println("\nRecent Assessments:");
                    player.viewSkillAssessments();
                    System.out.println("\nTraining Progress:");
                    player.viewProgress();
                    return;
                }
            }
            System.out.println("Player not found!");
        } catch (IOException e) {
            System.out.println("Error accessing player data: " + e.getMessage());
        }
    }

    private static void assignCoachToPlayer() {
        System.out.println("\n--- ASSIGN COACH TO PLAYER ---");
        int playerId = getIntInput("Enter Player ID: ");
        int coachId = getIntInput("Enter Coach ID: ");
        
        try {
            // Find the player
            List<Player> players = Player.loadAllPlayers();
            Player targetPlayer = null;
            for (Player p : players) {
                if (p.getPlayerId() == playerId) {
                    targetPlayer = p;
                    break;
                }
            }
            
            if (targetPlayer == null) {
                System.out.println("Player not found!");
                return;
            }
            
            // Find the coach
            List<Coach> coaches = Coach.loadAllCoaches();
            Coach targetCoach = null;
            for (Coach c : coaches) {
                if (c.getCoachId() == coachId) {
                    targetCoach = c;
                    break;
                }
            }
            
            if (targetCoach == null) {
                System.out.println("Coach not found!");
                return;
            }
            
            // Assign coach to player
            targetPlayer.setCoachId(coachId);
            targetCoach.assignPlayer(playerId);
            
            // Save updated player data
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(Player.PLAYERS_FILE))) {
                for (Player p : players) {
                    String coachIdStr = (p.getCoachId() != null) ? String.valueOf(p.getCoachId()) : "null";
                    writer.write(p.getPlayerId() + "," + p.getPlayerName() + "," + p.getPlayerEmail() + "," + coachIdStr);
                    writer.newLine();
                }
            }
            
            // Save updated coach data
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(Coach.COACHES_FILE))) {
                for (Coach c : coaches) {
                    writer.write(c.getCoachId() + "," + c.getCoachName() + "," + 
                               c.getCoachEmail() + "," + c.getSpecialization());
                    if (!c.getAssignedPlayers().isEmpty()) {
                        writer.write("," + String.join(";", c.getAssignedPlayers().stream()
                            .map(String::valueOf)
                            .toArray(String[]::new)));
                    }
                    writer.newLine();
                }
            }
            
            System.out.println("Successfully assigned Coach " + targetCoach.getCoachName() + 
                              " to Player " + targetPlayer.getPlayerName());
        } catch (IOException e) {
            System.out.println("Error assigning coach: " + e.getMessage());
        }
    }


     private static String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    private static int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }
}
