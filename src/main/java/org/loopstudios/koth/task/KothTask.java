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
    public static int timer;
    private final Koth koth;
    private final Cuboid cuboid;
    public static boolean underControl = false;
    public static UUID capper = null;

    public KothTask(VioletKoth plugin) {
        this.plugin = plugin;
        timer = this.plugin.getConfig().getInt("koth.captime");
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

                        if (!underControl) {
                            timer = plugin.getConfig().getInt("koth.captime");
                        }

                        if (cuboid.contains(player.getLocation()) && !underControl) {
                            Bukkit.broadcastMessage(CC.translate(plugin.getConfig().getString("messages.koth-capping")));
                            underControl = true;
                            capper = player.getUniqueId();
                        }
                        timer--;

                        if (!cuboid.contains(player.getLocation()) && capper != null) {
                            capper = null;
                            timer = plugin.getConfig().getInt("koth.captime");
                        }

                        if (capper == null && underControl) {
                            Bukkit.broadcastMessage(CC.translate(plugin.getConfig().getString("messages.koth-lost")));
                            underControl = false;
                            timer = plugin.getConfig().getInt("koth.captime");
                        }

                        if (underControl && capper != null && timer <= 0) {
                            Player p = Bukkit.getPlayer(capper);
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), reward().replace("%player%", p.getName()));
                            Bukkit.broadcastMessage(CC.translate(plugin.getConfig().getString("messages.koth-capped")));
                            stop();
                        }
                    }

                    Bukkit.broadcastMessage("debug so you can see, it works " + timer);
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
