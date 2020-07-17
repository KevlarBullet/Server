package me.silver.server.minigame;

import me.silver.server.util.PlayerHolder;
import me.silver.server.util.Utils;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public abstract class BaseGame {

    public final World world;

    public final PlayerHolder holder;
    public final Player player;
    public final Location startLocation;

    public BaseGame(World world, Player player, Location startLocation) {
        this.world = world;
        this.player = player;
        this.holder = new PlayerHolder(player);

        this.startLocation = startLocation;

        Utils.clearPlayer(player);
    }

    public abstract void startGame();
    public abstract void endGame();

}
