import java.io.*;
import java.util.*;

public class Admin {
    private String adminId;
    private String adminName;
    private String adminEmail;
    private String adminPassword;
    private static final String USERS_FILE = "users.dat";

    public Admin(String adminId, String adminName, String adminEmail, String adminPassword) {
        this.adminId = adminId;
        this.adminName = adminName;
        this.adminEmail = adminEmail;
        this.adminPassword = adminPassword;
    }

    public boolean login(String email, String password) {
        return this.adminEmail.equals(email) && this.adminPassword.equals(password);
    }

    public void logout() {
        System.out.println("Admin " + adminName + " logged out successfully.");
    }

    public boolean verifyUser(String userId) {
        try {
            List<String> users = readAllUsers();
            return users.contains(userId);
        } catch (IOException e) {
            System.out.println("Error verifying user: " + e.getMessage());
            return false;
        }
    }

    private List<String> readAllUsers() throws IOException {
        List<String> users = new ArrayList<>();
        File file = new File(USERS_FILE);
        if (!file.exists()) {
            return users;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                users.add(line);
            }
        }
        return users;
    }

    public void saveAdminToFile() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USERS_FILE, true))) {
            writer.write(adminId + "," + adminName + "," + adminEmail + "," + adminPassword);
            writer.newLine();
        }
    }

    public static List<Admin> loadAllAdmins() throws IOException {
        List<Admin> admins = new ArrayList<>();
        File file = new File(USERS_FILE);
        if (!file.exists()) {
            return admins;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 4) {
                    admins.add(new Admin(parts[0], parts[1], parts[2], parts[3]));
                }
            }
        }
        return admins;
    }

    // Getters
    public String getAdminId() { return adminId; }
    public String getAdminName() { return adminName; }
    public String getAdminEmail() { return adminEmail; }
}