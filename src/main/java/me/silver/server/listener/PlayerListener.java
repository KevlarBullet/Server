package me.silver.server.listener;

import me.silver.server.minigame.GameManager;
import me.silver.server.Server;
import me.silver.server.minigame.BaseGame;
import me.silver.server.minigame.parkour.ParkourGame;
import me.silver.server.util.PlayerHolder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {

    private GameManager gameManager = Server.getInstance().gameManager;

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event) {
        Player player;

        if (event.getEntity() instanceof Player) {
            player = (Player) event.getEntity();
        } else {
            return;
        }

        if (player.getWorld().getName().equalsIgnoreCase("parkour")) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onHungerDrain(FoodLevelChangeEvent event) {
        Player player;

        if (event.getEntity() instanceof Player) {
            player = (Player) event.getEntity();
        } else {
            return;
        }

        if (player.getWorld().getName().equalsIgnoreCase("parkour")) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Location to = event.getTo();
        Location from = event.getFrom();

        if (to.getBlockX() == from.getBlockX()
                && to.getBlockY() == from.getBlockY()
                && to.getBlockZ() == from.getBlockZ()) {
            // Do nothing if the player hasn't moved 1 block
            return;
        }

        if (gameManager.getGameMap().containsKey(player)) {
            BaseGame game = gameManager.getGameMap().get(player);

            if (game instanceof ParkourGame) {
                ((ParkourGame)game).onMove(event);
                return;
            }
        }

        if (event.getPlayer().isOnGround()) {

        } else {
            if (player.getWorld().getName().equalsIgnoreCase("parkour") && event.getTo().getY() <= 0) {
                event.getPlayer().teleport(new Location(gameManager.getGamesWorld(), 8.5, 65, 8.5));
            }
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Block block = event.getBlock();
        Material type = block.getType();

        if (type == Material.COMMAND || type == Material.COMMAND_CHAIN || type == Material.COMMAND_REPEATING) {

        }
    }

    @EventHandler
    public void onPlayerDisconnect(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        String name = player.getName();
        GameManager gameManager = Server.getInstance().gameManager;
        PlayerHolder holder;

        if (gameManager.getGameMap().containsKey(player)) gameManager.endGame(player);

        if ((holder = gameManager.holders.get(name)) != null) {
            holder.teleportPlayer();
            holder.setPlayer();
            gameManager.holders.remove(name);
        }

        if (player.getWorld().getName().equals("parkour")) {
            Location location = new Location(Bukkit.getWorld("world"), 767, 112, 1218);
            player.teleport(location);
        }
    }

}
