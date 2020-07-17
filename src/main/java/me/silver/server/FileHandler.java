package me.silver.server;

import org.bukkit.Bukkit;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;

public class FileHandler {

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static FileReader readFile(String filePath, String fileName) {
        File file = new File(filePath + fileName);

        try {
            if (!file.exists()) {
                Bukkit.getLogger().info("File '" + fileName + "' does not exist. Creating new one...");
                file.getParentFile().mkdirs();
            }

            return new FileReader(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    // Result of mkdirs() call is unneeded
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void writeFile(String filePath, String fileName, String jsonObject) {
        File file = new File(filePath + fileName);

        try {
            if (!file.exists()) file.getParentFile().mkdirs();

            FileWriter writer = new FileWriter(file);

            writer.write(jsonObject);
            writer.flush();
        } catch (IOException e) {
            Bukkit.getLogger().log(Level.SEVERE, "Error saving file '" + fileName + "'", e);
        }
    }
}

