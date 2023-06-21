package org.loopstudios.koth.task;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.loopstudios.VioletKoth;
import org.loopstudios.koth.Koth;
import org.loopstudios.koth.manager.KothManager;
import org.loopstudios.utils.CC;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class KothScheduler {

    private final VioletKoth plugin;
    private int timer;
    private final List<Koth> kothList = new ArrayList<>();
    private final Random random = new Random();

    public KothScheduler(VioletKoth plugin) {
        this.plugin = plugin;
        this.timer = this.plugin.getConfig().getInt("koth.schedule");
    }

    @SuppressWarnings("ALL")
    public void run(){
        Bukkit.getScheduler().runTaskTimer(this.plugin, new Runnable() {
            @Override
            public void run() {

                if (timer <= 0) {
                    for (final String koths : Objects.requireNonNull(plugin.getKothDataConfiguration().getConfigurationSection("koth")).getKeys(false)) {
                        kothList.add(new Koth(
                                plugin.getKothDataConfiguration().getString("koth." + koths + ".name"),
                                plugin.getConfig().getInt("koth.captime"),
                                (Location) plugin.getKothDataConfiguration().get("koth." + koths + ".corner-1"),
                                (Location) plugin.getKothDataConfiguration().get("koth." + koths + ".corner-2"),
                                (Location) plugin.getKothDataConfiguration().get("koth." + koths + ".capzone-1"),
                                (Location) plugin.getKothDataConfiguration().get("koth." + koths + ".capzone-2")));
                    }
                    Koth randomKoth = kothList.get(random.nextInt(kothList.size()));
                    KothManager.activeKoths.add(randomKoth);
                    KothTask kothTask = new KothTask(plugin);
                    kothTask.run(plugin);
                    KothTask.activeTasks.put(randomKoth, kothTask.getBukkitTask());
                    timer = plugin.getConfig().getInt("koth.schedule");
                    Bukkit.broadcastMessage(CC.translate(plugin.getConfig().getString("messages.koth-started")));
                }
            }
        }, 0L ,20L);
    }
}
