import java.io.IOException;
import java.util.List;

public class Viewer {
    private Tournament tournament;
    private HistoricalData historicalData;
    private LiveEngagement liveEngagement;
    private Technical technical;

    public Viewer() {
        this.tournament = new Tournament(1, "Champions League");
        this.historicalData = new HistoricalData();
        this.liveEngagement = new LiveEngagement(1);
        this.technical = new Technical();
    }

    public void viewTournamentDetails() {
        System.out.println("\n--- TOURNAMENT DETAILS ---");
        System.out.println("Tournament: " + tournament.getTournamentName());
        System.out.println("\n--- BALL-BY-BALL UPDATES (FR3) ---");
        tournament.viewBallByBallUpdates();
        System.out.println("\n--- POINTS TABLE (FR4) ---");
        tournament.viewPointTable();
        System.out.println("\n--- VENUE ANALYTICS (FR5) ---");
        tournament.viewVenueAnalysis();
    }

    public void viewHistoricalData() {
        System.out.println("\n--- HISTORICAL DATA ---");
        System.out.println("\n--- ARCHIVE ACCESS (FR13) ---");
        System.out.println("2022 Winner: " + historicalData.getTournamentWinner(2022));
        System.out.println("\n--- PLAYER TIMELINES (FR14) ---");
        System.out.println("Player 101: " + historicalData.getPlayerTimeline(101));
        System.out.println("\n--- FINANCIAL TRENDS (FR15) ---");
        historicalData.viewFinancialTrends();
    }

    public void viewLiveEngagement() {
        System.out.println("\n--- LIVE ENGAGEMENT ---");
        System.out.println("\n--- HD STREAMING (FR16) ---");
        liveEngagement.startHDStreaming();
        System.out.println("\n--- WIN PROBABILITY (FR17) ---");
        liveEngagement.viewWinProbability();
        System.out.println("\n--- LIVE COMMENTARY (FR18) ---");
        liveEngagement.viewLiveCommentary();
    }

    public void viewTechnicalDetails() {
        System.out.println("\n--- TECHNICAL DETAILS ---");
        System.out.println("\n--- LIVE UPDATE SPEED (FR19) ---");
        technical.checkUpdateSpeed();
        System.out.println("\n--- DATA EXPORT (FR20) ---");
        technical.exportData();
    }
}