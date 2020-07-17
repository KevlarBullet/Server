package me.silver.server.config.modules;

import me.silver.server.util.Angle;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.HashMap;
import java.util.Map;

public class Jump implements ConfigurationSerializable, Comparable<Jump> {

    public final int xOffset;
    public final int yOffset;
    public final int zOffset;
    public final Angle angle;

    public Jump(int xOffset, int yOffset, int zOffset, double angle) {
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.zOffset = zOffset;
        this.angle = new Angle(angle);
    }

    @Override
    public Map<String, Object> serialize() {
        HashMap<String, Object> jump = new HashMap<>();

        jump.put("xOffset", xOffset);
        jump.put("yOffset", yOffset);
        jump.put("zOffset", zOffset);
        jump.put("angle", angle.getAngle());

        return jump;
    }

    @SuppressWarnings("unused")
    public static Jump deserialize(Map<String, Object> jump) {
        int xOffset = (int) jump.get("xOffset");
        int yOffset = (int) jump.get("yOffset");
        int zOffset = (int) jump.get("zOffset");
        double angle = (double) jump.get("angle");

        return new Jump(xOffset, yOffset, zOffset, angle);
    }

    @Override
    public int compareTo(Jump jump) {
        return Double.compare(this.angle.getAngle(), jump.angle.getAngle());
    }
}
