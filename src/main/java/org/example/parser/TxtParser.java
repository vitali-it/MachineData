package org.example.parser;

import org.example.FluorescenceModel;

import java.io.*;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TxtParser implements MachineParser {

    private static final int WELLS_PER_CYCLE = 384;
    private static final int TIME_INTERVAL_SECONDS = 60; // Machine 2 interval

    @Override
    public List<FluorescenceModel> parse(File txtFile) throws IOException {
        List<FluorescenceModel> fluorescenceDataList = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(txtFile.toPath())) {
            String line;
            int lineCounter = 0;

            while ((line = reader.readLine()) != null) {
                final String[] parts = line.split("\t");

                if (parts.length < 4) continue;

                final String wellId = parts[1];
                double fluorescenceValue;
                try {
                    fluorescenceValue = Double.parseDouble(parts[3].replace(",", ".")); // handle comma decimal
                } catch (NumberFormatException e) {
                    continue;
                }

                final int cycle = (lineCounter / WELLS_PER_CYCLE) + 1;
                final LocalDateTime timestamp = LocalDateTime.now().plusSeconds((long) TIME_INTERVAL_SECONDS * (cycle - 1));

                final FluorescenceModel record = new FluorescenceModel(wellId, cycle, timestamp, fluorescenceValue);
                fluorescenceDataList.add(record);

                lineCounter++;
            }
        }

        return fluorescenceDataList;
    }
}
