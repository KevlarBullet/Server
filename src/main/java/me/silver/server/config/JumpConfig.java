package me.silver.server.config;

import me.silver.server.config.modules.Jump;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;

public class JumpConfig {

    private final String filePath;
    private final String fileName;

    private final Map<Integer, List<Jump>> jumps = new HashMap<>();

    public JumpConfig(String filePath, String fileName) {
        this.filePath = filePath;
        this.fileName = fileName;
    }

    public List<Jump> getJumpsByTier(int tier) {
        return jumps.get(tier);
    }

    // TODO: Get jumps within range of an angle

    public List<Jump> getJumpsNearAngle(int tier, double angle) {
        List<Jump> nearbyJumps = new ArrayList<>();

        for (Jump jump : jumps.get(tier)) {
            double offset = jump.angle.getOffset(angle);

            if (offset >= -45 && offset <= 45) {
                nearbyJumps.add(jump);
            }
        }

        return nearbyJumps;
    }

    private Jump binarySearchAngle(List<Jump> jumpList, int left, int right, double angle) {
        if (right >= left) {
            int middle = left + (right - left) / 2;
            double offset = jumpList.get(middle).angle.getOffset(angle);

            if (offset > 45) {
                return binarySearchAngle(jumpList, left, middle - 1, angle);
            }

            if (offset < -45) {
                return binarySearchAngle(jumpList, middle + 1, right, angle);
            }

            return jumpList.get(middle);
        }

        return null;
    }

    public void addJump(int tier, Jump jump) {
        List<Jump> jumpList = jumps.computeIfAbsent(tier, k -> new ArrayList<>());
        jumpList.add(jump);
    }

    @SuppressWarnings("unchecked")
    public void loadConfig() {
        File file = new File(filePath, fileName);
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

        Set<String> tierList = config.getKeys(false);

        for (String rawTier : tierList) {
            int tier = Integer.parseInt(rawTier);
            List<Jump> jumpList = (List<Jump>) config.getList(rawTier);

            Collections.sort(jumpList);

            jumps.put(tier, jumpList);
        }
    }

    public void saveConfig() {
        if (jumps.isEmpty()) return;

        File file = new File(filePath, fileName);
        YamlConfiguration config = new YamlConfiguration();

        for (Map.Entry<Integer, List<Jump>> entry : jumps.entrySet()) {
            config.set(String.valueOf(entry.getKey()), entry.getValue() );
        }

        try {
            config.save(file);
        } catch (IOException e) {
            Bukkit.getLogger().log(Level.SEVERE, "Error saving file '" + fileName + "'", e);
        }
    }
}
