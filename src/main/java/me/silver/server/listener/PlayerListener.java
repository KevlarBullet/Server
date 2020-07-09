package me.silver.server.listener;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerListener implements Listener {

//    @EventHandler
//    public void onMove(PlayerMoveEvent event) {
//        Location to = event.getTo();
//        Location from = event.getFrom();
//
//        if (event.getPlayer().isOnGround()) {
//
//        }
//    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Block block = event.getBlock();
        Material type = block.getType();

        if (type == Material.COMMAND || type == Material.COMMAND_CHAIN || type == Material.COMMAND_REPEATING) {

        }
    }
}
