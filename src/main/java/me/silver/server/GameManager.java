package me.silver.server;

import me.silver.server.minigame.OneVOneMinigame;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class GameManager {

    public List<Player> queuedPlayers = new ArrayList<>();
    private OneVOneMinigame currentGame = null;

}
