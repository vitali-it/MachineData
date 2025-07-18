package org.example;

import org.example.config.ConfigReader;
import org.example.config.MachineConfig;
import org.example.parser.CsvParser;
import org.example.parser.MachineParser;
import org.example.parser.TxtParser;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        try {
            final MachineConfig config = ConfigReader.readConfig("src/main/resources/machine-config.properties");
            final File dataFile = new File(config.getFilePath());

            final MachineParser parser = createParser(config);

            final List<FluorescenceModel> data = parser.parse(dataFile);

            final LocalDateTime initialTimestamp = LocalDateTime.now();
            final int intervalSeconds = config.getTimeIntervalSeconds();

            data.stream()
                    .filter(d -> d.cycle() <= 3)
                    .forEach(d -> {
                        LocalDateTime adjustedTimestamp = initialTimestamp.plusSeconds((long) intervalSeconds * (d.cycle() - 1));

                        FluorescenceModel updatedRecord = d.withTimestamp(adjustedTimestamp);

                        System.out.printf("Well: %s, Cycle: %d, Timestamp: %s, Fluorescence: %.3f\n",
                                updatedRecord.wellId(),
                                updatedRecord.cycle(),
                                updatedRecord.getFormattedTimestamp(),
                                updatedRecord.value());
                    });

        } catch (IOException e) {
            System.err.println("Error reading configuration or data file: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
        }
    }

    private static MachineParser createParser(MachineConfig config) {
        final String fileFormat = config.getFileFormat();
        return switch (fileFormat.toUpperCase()) {
            case "CSV" -> new CsvParser();
            case "TXT" -> new TxtParser();
            default -> throw new IllegalArgumentException("Unsupported file format: " + fileFormat);
        };
    }
}
