package org.loopstudios.koth.task;

import org.bukkit.scheduler.BukkitTask;

public class KothTask {

    private BukkitTask bukkitTask;

    public void run(){}

    public void stop(){
        bukkitTask.cancel();
        bukkitTask = null;
    }
}
