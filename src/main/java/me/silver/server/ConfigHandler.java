package me.silver.server;

import me.silver.server.Server;
import me.silver.server.config.JumpConfig;
import me.silver.server.config.KitConfig;

public class ConfigHandler {

    public final String DATA_PATH;

    public KitConfig kitConfig;
    public JumpConfig jumpConfig;

    public ConfigHandler(Server instance) {
        this.DATA_PATH = instance.getDataFolder().getAbsolutePath();
    }

    public void loadConfigurations() {
        kitConfig = new KitConfig(DATA_PATH + "/", "kits.yml");
        kitConfig.loadConfig();
        jumpConfig = new JumpConfig(DATA_PATH + "/", "jumps.yml");
        jumpConfig.loadConfig();
    }

    public void saveConfigs() {
        kitConfig.saveConfig();
//        jumpConfig.saveConfig();
    }

}
