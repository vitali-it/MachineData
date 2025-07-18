package org.example.config;

public class MachineConfig {

    private String fileFormat;
    private int timeIntervalSeconds;
    private String filePath;

    public String getFileFormat() {
        return fileFormat;
    }

    public void setFileFormat(String fileFormat) {
        this.fileFormat = fileFormat;
    }

    public int getTimeIntervalSeconds() {
        return timeIntervalSeconds;
    }

    public void setTimeIntervalSeconds(int timeIntervalSeconds) {
        this.timeIntervalSeconds = timeIntervalSeconds;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
