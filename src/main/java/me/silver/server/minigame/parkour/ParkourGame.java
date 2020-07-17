package me.silver.server.minigame.parkour;

import me.silver.server.Server;
import me.silver.server.config.JumpConfig;
import me.silver.server.config.modules.Jump;
import me.silver.server.minigame.BaseGame;
import me.silver.server.util.Utils;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.List;
import java.util.Random;
import java.util.Stack;

public class ParkourGame extends BaseGame {

    private final Random random = new Random(System.currentTimeMillis());
    private final JumpConfig jumpConfig = Server.getInstance().configHandler.jumpConfig;

    private final Stack<Block> blocks = new Stack<>();
    private Block currentBlock; // What's this?
    private double lastAngle = 90;

    private int jumpCount = 0;
    private int tier = 1;
    private int blockHeight;

    public ParkourGame(World world, Player player, Location startLocation) {
        super(world, player, startLocation);
    }

    @Override
    public void startGame() {
        Block startingBlock = this.startLocation.getBlock();

        startingBlock.setType(Material.WOOL);
        startingBlock.setData(getRandomColor());

        currentBlock = this.blocks.push(startingBlock);
        blockHeight = startingBlock.getY();

        Location playerLocation = new Location(world, startLocation.getX() + 0.5, startLocation.getY() + 1, startLocation.getZ() + 0.5, 90, 30);
        this.player.teleport(playerLocation);

        genNextBlock();
    }

    @Override
    public void endGame() {
        this.holder.setPlayer();
        this.holder.teleportPlayer();

        for (Block block : blocks) {
            block.setType(Material.AIR);
        }

        player.sendMessage(ChatColor.YELLOW + "You fell after " + jumpCount + " jumps");
    }

    public void onMove(PlayerMoveEvent event) {
        if (event.getTo().getY() <= (blockHeight - 5)) {
            Server.getInstance().gameManager.endGame(player);
            return;
        }

//        if (!player.isOnGround()) return;

        Block[] groundBlocks = Utils.getStandingBlocks(event.getTo());

        for (Block block : groundBlocks) {
//            player.sendMessage(block.getType().toString());
            // TODO: Change block check to be based on leading/trailing block counts
//            if (block.getType() == Material.WOOL) {
//                player.sendMessage(block.getX() + " " + block.getY() + " " + block.getZ());
//                player.sendMessage(blocks.peek().getX() + " " + blocks.peek().getY() + " " + blocks.peek().getZ());
//            }

            if (block.getType() == blocks.peek().getType()) {
                if (block.getLocation().equals(blocks.peek().getLocation())) {
//                    player.sendMessage("It is this one");
                    deleteBottomBlock();
                    genNextBlock();
                    jumpCount++;
                    tier = Math.min(jumpCount / 10 + 1, 6);

                    break;
                }

            }
        }

    }

    private void genNextBlock() {
//        List<Jump> jumps = jumpConfig.getJumpsByTier(this.tier);
        List<Jump> jumps = jumpConfig.getJumpsNearAngle(tier, lastAngle);

        Jump jump = jumps.get(random.nextInt(jumps.size()));
        Location previous = blocks.peek().getLocation();
        Block nextBlock = this.world.getBlockAt(previous.getBlockX() + jump.xOffset,
                previous.getBlockY() + jump.yOffset, previous.getBlockZ() + jump.zOffset);

        nextBlock.setType(Material.WOOL);
        nextBlock.setData(getRandomColor());
        blocks.push(nextBlock);

        lastAngle = jump.angle.getAngle();
        blockHeight = nextBlock.getY();

        if (nextBlock.getLocation().equals(previous)) {
            player.sendMessage("Error: Failed to generate block. This shouldn't happen");
            Bukkit.getLogger().info("[Server] Error: Failed to generate block. This shouldn't happen");
            genNextBlock();
        }
    }

    private void deleteBottomBlock() {
        Block block = blocks.firstElement();
        block.setType(Material.AIR);
        blocks.remove(0);
    }

    private byte getRandomColor() {
        return (byte) random.nextInt(16);
    }

}
