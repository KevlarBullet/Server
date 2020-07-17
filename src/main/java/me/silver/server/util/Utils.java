package me.silver.server.util;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

import java.util.List;

public class Utils {

    private static final double PLAYER_APOTHEM = 0.3;

    public static List<Player> getPlayersFromString(String playerString) {

        return null;
    }

    public static float getAngle(int x1, int z1, int x2, int z2) {
        float angle = (float) Math.toDegrees(Math.atan2(z2 - z1, x2 - x1)) - 90;
        if (angle < 0) angle = (angle % 360) + 360;

        return angle;
    }

    public static Block[] getStandingBlocks(Location location) {
        int x1 = (int) Math.floor(location.getX() - PLAYER_APOTHEM);
        int x2 = (int) Math.floor(location.getX() + PLAYER_APOTHEM);
        int z1 = (int) Math.floor(location.getZ() - PLAYER_APOTHEM);
        int z2 = (int) Math.floor(location.getZ() + PLAYER_APOTHEM);

        int y = (int) location.getY() - 1;

        int count = (1 + x2 - x1) * (1 + z2 - z1);
        int currentIndex = 0;

        Block[] blocks = new Block[count];

        for (int x = x1; x <= x2; x++) {
            for (int z = z1; z <= z2; z++) {
                Block block = location.getWorld().getBlockAt(x, y, z);
                blocks[currentIndex++] = block;
            }
        }

        return blocks;
    }

    public static void clearPlayer(Player player) {
        player.getInventory().clear();

        for (PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }

        player.setHealth(20);
        player.setGameMode(GameMode.ADVENTURE);
        player.setFoodLevel(20);
        player.setSaturation(10);
    }
}
