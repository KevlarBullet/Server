package me.silver.server.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Subcommand;
import me.silver.server.minigame.GameManager;
import me.silver.server.Server;
import me.silver.server.util.PlayerHolder;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@CommandAlias("1v1")
public class DuelCommand extends BaseCommand {

    public List<Player> queuedPlayers = new ArrayList<>();

    private final GameManager manager = Server.getInstance().gameManager;
    private final HashMap<Player, PlayerHolder> holders = new HashMap<>();

    @Default
    @Subcommand("join")
    public void join(Player sender) {
        if (queuedPlayers.contains(sender)) {
            sender.sendMessage("Error: You're already queued for a 1v1");
        } else {
            queuedPlayers.add(sender);
            sender.sendMessage("a");
            holders.put(sender, new PlayerHolder(sender));
            clearPlayer(sender);
        }
    }

    @Subcommand("leave")
    public void leave(Player sender) {
        if (queuedPlayers.contains(sender)) {
            queuedPlayers.remove(sender);
            sender.sendMessage("You've left the queue or something");

            holders.remove(sender).setPlayer();
        } else {
            sender.sendMessage("nah");
        }
    }

    @Subcommand("spec")
    public void spectate(Player sender) {

    }

    private void clearPlayer(Player player) {
        player.getInventory().clear();
        player.setHealth(20);
        player.setGameMode(GameMode.ADVENTURE);
        player.setFoodLevel(20);
        player.setSaturation(10);

        for (PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }
    }

//    @Subcommand("save")
//    public void thisIsNotA1v1Command(Player sender) {
//        ItemStack[] items = sender.getInventory().getContents();
//        String inventoryString = Kit.InventorySerializer.serialize(items);
//
//        FileHandler.writeFile(Server.getInstance().getDataFolder().getAbsolutePath() + '/', sender.getName() + ".json", inventoryString);
//    }
//
//    @Subcommand("load")
//    public void thisIsntEither(Player sender) {
////        sender.sendMessage(sender.getInventory().getSize() + "");
//
//        String fileName = sender.getName() + ".json";
//        String filePath = Server.getInstance().getDataFolder().getAbsolutePath() + '/';
//
//        FileReader reader = FileHandler.readFile(filePath, fileName);
//
//        if (reader != null) {
//            ItemStack[] itemStacks = Kit.InventorySerializer.deserialize(reader);
//            sender.getInventory().setContents(itemStacks);
//        }
//
//    }

}
