package me.silver.server.util;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.List;

public class PlayerHolder {

    Player player;

    private final double health;
    private final int hunger;
    private final float saturation;
    private final ItemStack[] inventory;
    private final GameMode gameMode;
    private final Location location;

    private final List<PotionEffect> effectsList = new ArrayList<>();

    public PlayerHolder(Player player) {
        this.player = player;
        this.health = player.getHealth();
        this.hunger = player.getFoodLevel();
        this.gameMode = player.getGameMode();
        this.inventory = player.getInventory().getContents();
        this.saturation = player.getSaturation();
        this.location = player.getLocation();

        this.effectsList.addAll(player.getActivePotionEffects());
    }

    public void setPlayer() {
        clearPlayer();

        player.setHealth(health);
        player.setGameMode(gameMode);
        player.setFoodLevel(hunger);
        player.getInventory().setContents(inventory);
        player.setSaturation(saturation);
        player.addPotionEffects(effectsList);
    }

    public void teleportPlayer() {
        player.teleport(location);
    }

    private void clearPlayer() {
        player.getInventory().clear();

        for (PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }
    }

}
