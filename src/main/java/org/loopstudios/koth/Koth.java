package org.loopstudios.koth;

import org.bukkit.Location;

public class Koth {

    private final String name;
    private final int capTime;
    private final Location corner1, corner2, capCorner1, capCorner2;

    public Koth(String name, int capTime, Location corner1, Location corner2, Location capCorner1, Location capCorner2) {
        this.name = name;
        this.capTime = capTime;
        this.corner1 = corner1;
        this.corner2 = corner2;
        this.capCorner1 = capCorner1;
        this.capCorner2 = capCorner2;
    }

    public String getName() {
        return name;
    }

    public int getCapTime() {
        return capTime;
    }

    public Location getCorner1() {
        return corner1;
    }

    public Location getCorner2() {
        return corner2;
    }

    public Location getCapCorner1() {
        return capCorner1;
    }

    public Location getCapCorner2() {
        return capCorner2;
    }
}
