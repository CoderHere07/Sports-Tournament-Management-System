import java.util.ArrayList;
import java.util.List;

public class Training {
    private int validate;
    private int messageId;
    private int assessmentId;
    private List<String> uploadedVideos;
    private List<String> messages; 

    public Training() {
        this.uploadedVideos = new ArrayList<>();
        this.messages = new ArrayList<>();
    }
    public boolean uploadVideo(String filename) {
        uploadedVideos.add(filename);
        System.out.println("Video uploaded: " + filename);
        return true;
    }

    public void sendMessage(String message) {
        messages.add(message);
        System.out.println("Message sent: " + message);
    }

    public void viewMessages() {
        System.out.println("Messages:");
        messages.forEach(System.out::println);
    }
}