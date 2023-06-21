package org.loopstudios.koth.task;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;
import org.loopstudios.VioletKoth;

public class KothScheduler {

    private VioletKoth plugin;
    private int timer;

    public KothScheduler(VioletKoth plugin) {
        this.plugin = plugin;
        this.timer = this.plugin.getConfig().getInt("koth.schedule");
    }

    public void run(){
        Bukkit.getScheduler().runTaskTimer(this.plugin, new Runnable() {
            @Override
            public void run() {

                if (timer <= 0) {
                    //start koth
                    timer = plugin.getConfig().getInt("koth.schedule");
                }
            }
        }, 0L ,20L);
    }
}
