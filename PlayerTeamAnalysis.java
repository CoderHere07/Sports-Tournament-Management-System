import java.util.HashMap;
import java.util.Map;

public class PlayerTeamAnalysis {
    private int playerId;
    private Map<String, String> headToHeadReports; 
    private Map<String, String> strengthWeaknessAnalysis;
    private Map<String, String> aiPerformanceAnalysis;

    public PlayerTeamAnalysis() {
        this.headToHeadReports = new HashMap<>();
        this.strengthWeaknessAnalysis = new HashMap<>();
        this.aiPerformanceAnalysis = new HashMap<>();

        headToHeadReports.put("TeamA vs TeamB", "TeamA: 5 wins, TeamB: 3 wins");
        strengthWeaknessAnalysis.put("TeamA", "Strength: Batting, Weakness: Bowling");
        aiPerformanceAnalysis.put("PlayerX", "Predicted performance: 85%");
    }

    public void viewHeadToHeadReport(String teams) {
        System.out.println("Head-to-head: " + teams);
        System.out.println(headToHeadReports.get(teams));
    }

    public void viewStrengthWeakness(String team) {
        System.out.println("Analysis for " + team + ":");
        System.out.println(strengthWeaknessAnalysis.get(team));
    }
    public void viewAIPerformance(String player) {
        System.out.println("AI Analysis for " + player + ":");
        System.out.println(aiPerformanceAnalysis.get(player));
    }
}