package me.silver.server.config;

import me.silver.server.config.modules.Kit;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

public class KitConfig {

    private final String filePath;
    private final String fileName;

    private final Map<String, Kit> kits = new HashMap<>();

    public KitConfig(String filePath, String fileName) {
        this.filePath = filePath;
        this.fileName = fileName;
    }

    @SuppressWarnings("unchecked")
    public void loadConfig() {
        File file = new File(filePath, fileName);
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

        List<Kit> loadedKits = (List<Kit>) config.getList("kits");

        if (loadedKits != null) {
            for (Kit kit : loadedKits) {
                if (kit != null) {
                    kits.put(kit.getKitName(), kit);
                    Bukkit.getLogger().info("[Server] Successfully loaded kit '" + kit.getKitName() + "'");
                }
            }
        }
    }

    public void saveConfig() {
        if (kits.isEmpty()) return;

        File configFile = new File(this.filePath, this.fileName);
        YamlConfiguration config = new YamlConfiguration();
        List<Kit> kitList = new ArrayList<>(kits.values());

        config.set("kits", kitList);

        try {
            config.save(configFile);
        } catch (IOException e) {
            Bukkit.getLogger().log(Level.SEVERE, "Error saving file '" + fileName + "'", e);
        }

        for (Kit kit : kitList) {
            File kitFile = new File(this.filePath + "kits/", kit.getKitFile());
            config = Kit.InventorySerializer.serialize(kit.getInventory());

            try {
                config.save(kitFile);
            } catch (IOException e) {
                Bukkit.getLogger().log(Level.SEVERE, "Error saving file '" + kit.getKitFile() + "'", e);
            }
        }
    }

    public Kit getKit(String kitName) {
        return kits.get(kitName);
    }

    public void addKit(String kitName, Kit kit) {
        this.kits.put(kitName, kit);
    }

}
