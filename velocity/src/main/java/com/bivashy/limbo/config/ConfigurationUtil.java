package com.bivashy.limbo.config;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

import com.bivashy.limbo.NanoLimboVelocity;

public class ConfigurationUtil {
    private ConfigurationUtil() {
    }

    public static File saveDefaultConfig(NanoLimboVelocity plugin, String configurationName) {
        return saveDefaultConfig(plugin.getClass().getClassLoader(), plugin.getDataFolder().toFile(), configurationName);
    }

    public static File saveDefaultConfig(ClassLoader classLoader, File configurationFolder, String configurationName) {
        try {
            configurationFolder.mkdirs();
            File configurationFile = new File(configurationFolder, configurationName);
            if (!configurationFile.exists()) {
                try (InputStream inputStream = classLoader.getResourceAsStream(configurationName)) {
                    if (inputStream == null)
                        throw new IllegalArgumentException("Configuration with name " + configurationName + " not found in resources!");
                    Files.copy(inputStream, configurationFile.toPath());
                }
            }
            return configurationFile;
        } catch(IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
