package me.silver.server;

import me.silver.server.minigame.BaseGame;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public final class Server extends JavaPlugin {

    private static Server instance;

    // Will possibly implement this later
    public HashMap<Integer, BaseGame> runningGames = new HashMap<>();
    public GameManager gameManager;

    @Override
    public void onEnable() {
        instance = this;
        // Plugin startup logic
        gameManager = new GameManager();
    }

//    @Override
//    public void onDisable() {
//        // Plugin shutdown logic
//    }


    public static Server getInstance() {
        return instance;
    }

}
