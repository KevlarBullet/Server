package me.silver.server.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Subcommand;
import me.silver.server.Server;
import me.silver.server.config.KitConfig;
import me.silver.server.config.modules.Kit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

@CommandAlias("kit")
public class KitCommand extends BaseCommand {

    private final Server instance = Server.getInstance();
    private final KitConfig kitConfig = Server.getInstance().configHandler.kitConfig;

    // TODO: Add utility method to resolve players from entity selectors (@p, @a[r=10], etc)
    // Also fix target player thingy
    @Subcommand("give")
    public void giveKit(Player sender, Player target, String kitName) {
        if (kitConfig.getKit(kitName) != null) {
            Kit kit = kitConfig.getKit(kitName);
            Kit.kitPlayer(target, kit);
            sender.sendMessage(ChatColor.YELLOW + "Successfully gave kit '" + kitName + "' to player '" + target.getName() + "'");
        } else {
            sender.sendMessage(ChatColor.RED + "Error: no kit found with name '" + kitName + "'");
        }
    }

    @Subcommand("create")
    public void saveKit(Player sender, String kitName) {
        if (kitConfig.getKit(kitName) != null) {
            sender.sendMessage(ChatColor.RED + "Error: kit with this name already exists");
        } else {
            Kit kit = new Kit(kitName, sender.getInventory());
            kitConfig.addKit(kitName, kit);
            sender.sendMessage(ChatColor.YELLOW + "Successfully created kit '" + kitName + "'");
        }
    }

}
