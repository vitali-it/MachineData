package org.example;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public record FluorescenceModel(String wellId, int cycle, LocalDateTime timestamp, double value) {

    public FluorescenceModel withTimestamp(LocalDateTime timestamp) {
        return new FluorescenceModel(this.wellId, this.cycle, timestamp, this.value);
    }

    public String getFormattedTimestamp() {
        return timestamp.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
    }

    public String getFormattedFluorescence() {
        return String.format("%.3f", value);
    }

    @Override
    public String toString() {
        return "Well: " + wellId + ", Cycle: " + cycle + ", Timestamp: " + getFormattedTimestamp() + ", Fluorescence: " + getFormattedFluorescence();
    }
}
