package me.silver.server.minigame.duel;

import me.silver.server.util.PlayerHolder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class DuelMinigame /*extends BaseGame*/ {

    private static final Location CHANGE_THIS_TO_A_CONFIG = new Location(Bukkit.getWorld("world"), 924, 112, 1222, 0, 20);
    private static final Location THIS_TOO = new Location(Bukkit.getWorld("world"), 924, 112, 1264, 180, 20);

    private final Player playerOne;
    private final Player playerTwo;

    private final PlayerHolder p1Holder;
    private final PlayerHolder p2Holder;

    public DuelMinigame(Player playerOne, Player playerTwo) {
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;

        this.p1Holder = new PlayerHolder(playerOne);
        this.p2Holder = new PlayerHolder(playerTwo);
    }

    public void spawnPlayers() {
        playerOne.teleport(CHANGE_THIS_TO_A_CONFIG);
        playerTwo.teleport(THIS_TOO);

        // TODO: Set inventories
    }
}
