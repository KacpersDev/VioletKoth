package org.loopstudios.koth.manager;

import org.bukkit.Location;
import org.loopstudios.VioletKoth;
import org.loopstudios.koth.Koth;
import org.loopstudios.koth.LocationType;

import java.util.*;

public class KothManager {

    private final VioletKoth plugin;
    private final Random random;

    public KothManager(VioletKoth plugin) {
        this.plugin = plugin;
        this.random = new Random();
    }

    public static List<Koth> activeKoths = new ArrayList<>();

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

    public Location getCuboid1(String kothName){
        return (Location) this.plugin.getKothDataConfiguration().get("koth." + kothName + ".corner-1");
    }

    public Location getCuboid2(String kothName){
        return (Location) this.plugin.getKothDataConfiguration().get("koth." + kothName + ".corner-2");
    }

    public Location getCapzone1(String kothName){
        return (Location) this.plugin.getKothDataConfiguration().get("koth." + kothName + ".capzone-1");
    }

    public Location getCapzone2(String kothName){
        return (Location) this.plugin.getKothDataConfiguration().get("koth." + kothName + ".capzone-2");
    }

    public String getRandomReward(){
        List<String> commands = new ArrayList<>();
        int randomNumber = random.nextInt(100);
        if (this.plugin.getConfig().getConfigurationSection("settings.reward") == null) return null;
        for (final String rewards : Objects.requireNonNull(this.plugin.getConfig().getConfigurationSection("settings.reward")).getKeys(false)) {
            if (randomNumber < this.plugin.getConfig().getInt("settings.reward." + rewards + ".chance")) {
                commands.add(this.plugin.getConfig().getString("settings.reward." + rewards + ".command"));
            }
        }
        int randomList = random.nextInt(commands.size());
        return commands.get(randomList);
    }
}
