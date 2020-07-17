package me.silver.server.minigame;

import me.silver.server.minigame.BaseGame;
import me.silver.server.minigame.parkour.ParkourGame;
import me.silver.server.util.PlayerHolder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class GameManager {

    World gamesWorld;

    // TODO: Make this less stupid (currently only allows for single-player games)
    private final HashMap<Player, BaseGame> gameMap = new HashMap<>();
    public final HashMap<String, PlayerHolder> holders = new HashMap<>();

    private int lastZ = 500;

    // TODO: This is part of that
    public void startGame(/*Class<? extends BaseGame> gameMode, */ Player player) {
        if (!gameMap.containsKey(player)) {
            ParkourGame game = new ParkourGame(gamesWorld, player, assignLocation());

            game.startGame();
            Bukkit.getLogger().info("[Server] Starting new Parkour instance for player '" + player.getName() + "'");
            gameMap.put(player, game);
        } else {
            Bukkit.getLogger().info("[Server] Error: Player '" + player.getName() + "' is already in a game");
        }
    }

    // TODO: This, too
    public void endGame(Player player) {
        if (gameMap.containsKey(player)) {
            Bukkit.getLogger().info("[Server] Ending parkour game for player '" + player.getName() + "'");
            BaseGame game = gameMap.get(player);
            game.endGame();
            gameMap.remove(player);
        } else {
            Bukkit.getLogger().info("[Server] Error stopping game: no game found for player '" + player.getName() + "'");
        }

        if (gameMap.isEmpty()) {
            lastZ = 500;
        }
    }

    private Location assignLocation() {
        return new Location(gamesWorld, 0, 65, lastZ += 500);
    }

    public void loadGamesWorld() {
        World parkourWorld = Bukkit.getWorld("parkour");

        if (parkourWorld == null) {
            parkourWorld = Bukkit.createWorld(new WorldCreator("parkour"));
        }

        this.gamesWorld = parkourWorld;
    }

    public HashMap<Player, BaseGame> getGameMap() {
        return gameMap;
    }

    public World getGamesWorld() {
        return gamesWorld;
    }
}
