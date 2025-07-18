package org.example.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

    public static MachineConfig readConfig(String filePath) throws IOException {
        final Properties properties = new Properties();
        try (FileInputStream inputStream = new FileInputStream(filePath)) {
            properties.load(inputStream);
        }

        final MachineConfig config = new MachineConfig();
        config.setFileFormat(properties.getProperty("machine.file_format"));
        config.setTimeIntervalSeconds(Integer.parseInt(properties.getProperty("machine.time_interval_seconds")));
        config.setFilePath(properties.getProperty("machine.file_path"));
        return config;
    }
}
