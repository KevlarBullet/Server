package me.silver.server.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Subcommand;
import me.silver.server.GameManager;
import me.silver.server.Server;
import me.silver.server.minigame.OneVOneMinigame;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class OneVOne extends BaseCommand {

    private GameManager manager = Server.getInstance().gameManager;

    @Default
    @Subcommand("join")
    public void join(Player sender) {
        if (manager.queuedPlayers.contains(sender)) {
            sender.sendMessage("Error: You're already queued for a 1v1");
        } else {
            manager.queuedPlayers.add(sender);
            sender.sendMessage("a");
        }
    }

    @Subcommand("leave")
    public void leave(Player sender) {
        if (manager.queuedPlayers.contains(sender)) {
            manager.queuedPlayers.remove(sender);
            sender.sendMessage("You've left the queue or something");
        } else {
            sender.sendMessage("nah");
        }
    }

    @Subcommand("spec")
    public void spectate(Player sender) {

    }

}
