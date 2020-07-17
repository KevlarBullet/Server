package me.silver.server.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Subcommand;
import me.silver.server.minigame.GameManager;
import me.silver.server.Server;
import me.silver.server.config.JumpConfig;
import me.silver.server.config.modules.Jump;
import me.silver.server.util.PlayerHolder;
import me.silver.server.util.Utils;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Random;

@CommandAlias("parkour")
public class ParkourCommand extends BaseCommand {

    GameManager gameManager = Server.getInstance().gameManager;
    Random random = new Random(System.currentTimeMillis());

    Location cornerOne;
    Location cornerTwo;


    @Default
    @Subcommand("join")
    public void joinParkour(Player sender) {
        if (!gameManager.holders.containsKey(sender.getName())) {
            World parkourWorld = Server.getInstance().gameManager.getGamesWorld();

            Location location = new Location(parkourWorld, 8.5, 64, 8.5);
            PlayerHolder holder = new PlayerHolder(sender);

            gameManager.holders.put(sender.getName(), holder);
            Utils.clearPlayer(sender);
            sender.teleport(location);
        }

    }

    @Subcommand("leave")
    public void leaveParkour(Player sender) {
        if (gameManager.getGameMap().containsKey(sender)) gameManager.endGame(sender);

        if (gameManager.holders.containsKey(sender.getName())) {
            PlayerHolder player = gameManager.holders.remove(sender.getName());
            player.setPlayer();
            player.teleportPlayer();
        } else {
            Location location = new Location(Bukkit.getWorld("world"), 767, 112, 1218);
            sender.teleport(location);
        }

    }

    @Subcommand("start")
    public void start(Player sender) {
        if (gameManager.getGameMap().containsKey(sender)) {
            sender.sendMessage("Error: You're already in a game");
        } else {
            sender.sendMessage("Starting a new parkour game...");
            joinParkour(sender);
            gameManager.startGame(sender);
        }
    }

    @Subcommand("gen")
    public void generateJump(Player sender, int tier) {
        JumpConfig jumpConfig = Server.getInstance().configHandler.jumpConfig;
        List<Jump> jumps = jumpConfig.getJumpsByTier(tier);
        Location p = sender.getLocation();
        Jump jump = jumps.get(random.nextInt(jumps.size()));

        Block block = sender.getWorld().getBlockAt(p.getBlockX() + jump.xOffset, p.getBlockY() + jump.yOffset - 1, p.getBlockZ() + jump.zOffset);
        block.setType(Material.WOOL);
        block.setData((byte) random.nextInt(16));
    }

//    @Subcommand("createjumps")
    public void createJumps(Player sender) {
        World world = cornerOne.getWorld();

        int centerX = 8;
        int centerY = 65;
        int centerZ = 0;

        Block origin;

        if ((origin = world.getBlockAt(centerX, centerY, centerZ)).getType() != Material.WOOL || origin.getData() != 0) {
            sender.sendMessage("Error: Center block not found or is incorrect type");
            return;
        }

        for (int x = cornerOne.getBlockX(); x <= cornerTwo.getBlockX(); x++) {
            for (int y = cornerOne.getBlockY(); y <= cornerTwo.getBlockY(); y++) {
                for (int z = cornerOne.getBlockZ(); z <= cornerTwo.getBlockZ(); z++) {
                    byte data = world.getBlockAt(x, y, z).getData();

                    if (data == 0) continue;

                    int offsetX = x - origin.getX();
                    int offsetY = y - origin.getY();
                    int offsetZ = z - origin.getZ();
                    float angle = Utils.getAngle(0, 0, offsetX, offsetZ);

                    Jump jump = new Jump(offsetX, offsetY, offsetZ, angle);

                    Server.getInstance().configHandler.jumpConfig.addJump(data, jump);
                }
            }
        }

        sender.sendMessage("Successfully created jumps based on whatever");
    }

//    @Subcommand("c1")
    public void setCornerOne(Player sender) {
        cornerOne = sender.getLocation();
    }

//    @Subcommand("c2")
    public void setCornerTwo(Player sender) {
        cornerTwo = sender.getLocation();
    }

}
