public class Technical {
    private static final int UPDATE_SPEED_MS = 2500;    private String dataExportFormat;
    private String weatherApiStatus;

    public Technical() {
        this.dataExportFormat = "CSV";
        this.weatherApiStatus = "Connected";
    }

    public void checkUpdateSpeed() {
        System.out.println("System update speed: " + UPDATE_SPEED_MS + "ms");
    }

    public void exportData() {
        System.out.println("Exporting data in " + dataExportFormat + " format");
    }

    public void checkWeatherApiStatus() {
        System.out.println("Weather API Status: " + weatherApiStatus);
    }
}