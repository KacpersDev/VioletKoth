package org.loopstudios.koth.task;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;
import org.loopstudios.VioletKoth;
import org.loopstudios.koth.Koth;
import org.loopstudios.koth.manager.KothManager;
import org.loopstudios.utils.CC;
import org.loopstudios.utils.Cuboid;

import java.util.HashMap;
import java.util.UUID;

public class KothTask {

    private final VioletKoth plugin;
    public static HashMap<Koth, BukkitTask> activeTasks = new HashMap<>();
    private BukkitTask bukkitTask;
    private int timer;
    private final Koth koth;
    private final Cuboid cuboid;
    private boolean underControl = false;
    private UUID capper = null;

    public KothTask(VioletKoth plugin) {
        this.plugin = plugin;
        this.timer = this.plugin.getConfig().getInt("koth.captime");
        this.koth = KothManager.activeKoths.get(0);
        this.cuboid = new Cuboid(this.koth.getCapCorner1(), this.koth.getCapCorner2());
    }

    @SuppressWarnings("ALL")
    public void run(Plugin plugin){
        bukkitTask = Bukkit.getScheduler().runTaskTimer(plugin, new Runnable() {

            @Override
            public void run() {

                if (bukkitTask != null) {
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        if (cuboid.contains(player.getLocation()) && !underControl) {
                            Bukkit.broadcastMessage(CC.translate(plugin.getConfig().getString("koth-capping")));
                            underControl = true;
                            capper = player.getUniqueId();
                        }
                        timer--;

                        if (!cuboid.contains(player.getLocation()) && capper != null) {
                            capper = null;
                            timer = plugin.getConfig().getInt("koth.captime");
                        }

                        if (capper == null && underControl) {
                            Bukkit.broadcastMessage(plugin.getConfig().getString("koth-lost"));
                            underControl = false;
                            timer = plugin.getConfig().getInt("koth.captime");
                        }

                        if (underControl && capper != null && timer <= 0) {
                            Player p = Bukkit.getPlayer(capper);
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), reward().replace("%player%", p.getName()));
                            Bukkit.broadcastMessage(plugin.getConfig().getString("koth-capped"));
                            stop();
                        }
                    }
                }
            }
        }, 0L, 20L);
    }

    private String reward(){
        return this.plugin.getKothManager().getRandomReward();
    }

    public void stop(){
        bukkitTask.cancel();
        bukkitTask = null;
        activeTasks.remove(this.koth);
    }

    public BukkitTask getBukkitTask() {
        return bukkitTask;
    }
}
