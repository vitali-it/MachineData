package org.example.parser;

import org.example.FluorescenceModel;

import java.io.*;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CsvParser implements MachineParser {

    private static final int TIME_INTERVAL_SECONDS = 94;

    @Override
    public List<FluorescenceModel> parse(File csvFile) throws IOException {
        List<FluorescenceModel> fluorescenceDataList = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(csvFile.toPath())) {
            String line = reader.readLine();
            String[] header = line.split(",");

            int cycle = 0;
            while ((line = reader.readLine()) != null) {
                cycle++;
                LocalDateTime timestamp = LocalDateTime.now().plusSeconds((long) TIME_INTERVAL_SECONDS * (cycle - 1));

                String[] values = line.split(",");
                for (int i = 1; i < values.length; i++) {
                    String wellId = header[i];
                    double fluorescenceValue = Double.parseDouble(values[i]);
                    FluorescenceModel data = new FluorescenceModel(wellId, cycle, timestamp, fluorescenceValue);
                    fluorescenceDataList.add(data);
                }
            }
        }
        return fluorescenceDataList;
    }
}