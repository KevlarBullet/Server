package me.silver.server.util;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
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

    private List<PotionEffect> effectsList = new ArrayList<>();

    public PlayerHolder(Player player) {
        this.player = player;
        this.health = player.getHealth();
        this.hunger = player.getFoodLevel();
        this.inventory = player.getInventory().getContents();
        this.saturation = player.getSaturation();

        for (PotionEffect effect : player.getActivePotionEffects()) {
            effectsList.add(effect);
            player.removePotionEffect(effect.getType());
        }

    }

    public void resetPlayer() {
        player.setHealth(health);
        player.setFoodLevel(hunger);
        player.setSaturation(saturation);
        player.getInventory().setContents(inventory);
        player.addPotionEffects(effectsList);
    }

}
