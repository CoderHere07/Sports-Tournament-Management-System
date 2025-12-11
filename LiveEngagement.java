public class LiveEngagement {
    private int mustInId;
    private boolean hdStreaming;
    private double winProbability;
    private String liveCommentary;

    public LiveEngagement(int mustInId) {
        this.mustInId = mustInId;
        this.hdStreaming = true;
        this.winProbability = 65.5;
        this.liveCommentary = "Great shot by the batsman!";
    }

    public void startHDStreaming() {
        this.hdStreaming = true;
        System.out.println("HD streaming started for match " + mustInId);
    }

    public void viewWinProbability() {
        System.out.println("Current win probability: " + winProbability + "%");
    }

    public void viewLiveCommentary() {
        System.out.println("Live Commentary: " + liveCommentary);
    }
}