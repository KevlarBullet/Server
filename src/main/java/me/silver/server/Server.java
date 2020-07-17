package me.silver.server;

import co.aikar.commands.PaperCommandManager;
import me.silver.server.command.DuelCommand;
import me.silver.server.command.KitCommand;
import me.silver.server.command.ParkourCommand;
import me.silver.server.config.modules.Jump;
import me.silver.server.config.modules.Kit;
import me.silver.server.listener.PlayerListener;
import me.silver.server.minigame.GameManager;
import me.silver.server.util.PlayerHolder;
import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class Server extends JavaPlugin {

    private static Server instance;

    // Will possibly implement this later
    public GameManager gameManager;
    public ConfigHandler configHandler;

    @Override
    public void onEnable() {
        instance = this;

        ConfigurationSerialization.registerClass(Kit.class);
        ConfigurationSerialization.registerClass(Jump.class);

        gameManager = new GameManager();
        gameManager.loadGamesWorld();

        configHandler = new ConfigHandler(this);
        configHandler.loadConfigurations();

        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);

        PaperCommandManager manager = new PaperCommandManager(this);
        manager.registerCommand(new DuelCommand());
        manager.registerCommand(new KitCommand());
        manager.registerCommand(new ParkourCommand());
    }

//    private void

    @Override
    public void onDisable() {
        configHandler.saveConfigs();

        for (Player player : gameManager.getGameMap().keySet()) {
            gameManager.endGame(player);
        }

        for (PlayerHolder holder : gameManager.holders.values()) {
            holder.setPlayer();
            holder.teleportPlayer();
        }
    }


    public static Server getInstance() {
        return instance;
    }

}
