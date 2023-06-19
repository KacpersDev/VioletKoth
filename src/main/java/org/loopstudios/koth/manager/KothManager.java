package org.loopstudios.koth.manager;

import org.bukkit.Location;
import org.loopstudios.VioletKoth;
import org.loopstudios.koth.Koth;
import org.loopstudios.koth.LocationType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class KothManager {

    private final VioletKoth plugin;

    public KothManager(VioletKoth plugin) {
        this.plugin = plugin;
    }

    public static List<Koth> activeKoths = new ArrayList<>();
    public static HashMap<Koth, Integer> activeKothTimers = new HashMap<>();

    public boolean exists(String kothName) {
        return this.plugin.getKothDataConfiguration().contains("koth." + kothName);
    }

    public void create(String kothName){
        this.plugin.getKothDataConfiguration().set("koth." + kothName + ".name", kothName);
        this.plugin.relodData();
    }

    public void remove(String kothName) {
        this.plugin.getKothDataConfiguration().set("koth." + kothName, null);
        this.plugin.relodData();
    }

    public void setCapTime(String koth, int timer) {
        this.plugin.getKothDataConfiguration().set("koth." + koth + ".captime", timer);
        this.plugin.relodData();
    }

    public void setCapzone(String koth, LocationType type, Location location) {
        if (type.equals(LocationType.FIRST)) {
            this.plugin.getKothDataConfiguration().set("koth." + koth + ".capzone-1", location);
            this.plugin.relodData();
        } else if (type.equals(LocationType.SECOND)) {
            this.plugin.getKothDataConfiguration().set("koth." + koth + ".capzone-2", location);
            this.plugin.relodData();
        }
    }

    public void setCuboid(String koth, LocationType type, Location location) {
        if (type.equals(LocationType.FIRST)) {
            this.plugin.getKothDataConfiguration().set("koth." + koth + ".corner-1", location);
            this.plugin.relodData();
        } else if (type.equals(LocationType.SECOND)) {
            this.plugin.getKothDataConfiguration().set("koth." + koth + ".corner-2", location);
            this.plugin.relodData();
        }
    }
}
