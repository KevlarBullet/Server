package me.silver.server.config.modules;

import com.google.gson.Gson;
import me.silver.server.Server;
import me.silver.server.FileHandler;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.FileReader;
import java.util.*;

public class Kit implements ConfigurationSerializable {

    private static final Gson gson = new Gson();

    private final String kitName;
    private final String kitFile;

    private final ItemStack[] inventory;

    public Kit(String kitName, String kitFile) {
        this.kitName = kitName.toLowerCase().trim();
        this.kitFile = kitFile;
        this.inventory = loadInventory();
    }

    public Kit(String kitName, Inventory inventory) {
        this.kitName = kitName.toLowerCase().trim();
        this.kitFile = this.kitName + ".yml";
        this.inventory = inventory.getContents();
    }

    public String getKitName() {
        return this.kitName;
    }

    public String getKitFile() {
        return kitFile;
    }

    public ItemStack[] getInventory() {
        return inventory;
    }

    private ItemStack[] loadInventory() {
        FileReader reader = FileHandler.readFile(Server.getInstance().getDataFolder().getAbsolutePath() + "/kits/", this.kitFile);
        if (reader != null) return InventorySerializer.deserialize(reader);

        return null;
    }

    @Override
    public Map<String, Object> serialize() {
        HashMap<String, Object> kitMap = new HashMap<>();
        kitMap.put("name", kitName);
        kitMap.put("file", kitFile);

        return kitMap;
    }

    // This method is called reflectively when config is loaded, somehow
    @SuppressWarnings("unused")
    public static Kit deserialize(Map<String, Object> kit) {
        String name = (String) kit.get("name");
        String file = (String) kit.get("file");

        return new Kit(name, file);
    }

    public static void kitPlayer(Player player, Kit kit) {
        player.getInventory().clear();
        player.getInventory().setContents(kit.inventory);
    }

    public static class InventorySerializer {

        public static YamlConfiguration serialize(ItemStack[] itemStacks) {
            YamlConfiguration config = new YamlConfiguration();

            for (int i = 0; i < itemStacks.length; i++) {
                if (itemStacks[i] != null) config.set(String.valueOf(i), itemStacks[i]);
            }

//
//            for (int i = 0; i < itemStacks.length; i++) {
//                if (itemStacks[i] == null) continue;
//
//                inventory.put(i, itemStacks[i].serialize());
//            }
//
//            return gson.toJson(inventory);

//            JSONObject inventory = new JSONObject();
//
//            for (int i = 0; i < itemStacks.length; i++) {
//                if (itemStacks[i] == null) continue;
//                inventory.put(i, itemStacks[i].serialize());
//            }
//
//            return inventory.toJSONString();

            return config;
        }

        public static ItemStack[] deserialize(FileReader reader) {
            ItemStack[] inventory = new ItemStack[41];
            YamlConfiguration config = YamlConfiguration.loadConfiguration(reader);
            Set<String> itemMap = config.getKeys(false);

            for (String s : itemMap) {
                int slot = Integer.parseInt(s);
                ItemStack stack = (ItemStack) config.get(s);

                Bukkit.getLogger().info(slot + " " + stack.getType());

                inventory[slot] = stack;
            }

//            Type type = new TypeToken<HashMap<Integer, HashMap<String, Object>>>() {}.getType();
//            HashMap<Integer, Map<String, Object>>  items = gson.fromJson(reader, type);
//
//            for (Map.Entry<Integer,Map<String, Object>> entry : items.entrySet()) {
//                int index = entry.getKey();
//                ItemStack item = ItemStack.deserialize(entry.getValue());
//
//                if (entry.getValue().containsKey("meta")) {
//                    ItemMeta meta = (ItemMeta) (entry.getValue().get("meta"));
//                    item.setItemMeta(meta);
//                }
//
//                inventory[index] = item;
//            }

//            JSONParser parser = new JSONParser();
//
//            try {
//                JSONObject rawInventory = (JSONObject) parser.parse(reader);
//                Map<String, Map<String, Object>> inv = (Map<String, Map<String, Object>>) rawInventory;
//
//                for (Map.Entry<String, Map<String, Object>> entry : inv.entrySet()) {
//                    inventory[Integer.parseInt(entry.getKey())] = ItemStack.deserialize(entry.getValue());
//                }
//            } catch (IOException | ParseException e) {
//                Bukkit.getLogger().log(Level.SEVERE, "Error: failed to load kit inventory '" + kitName + "'", e);
//            }
//
//            return inventory;
            return inventory;
        }

    }

}
