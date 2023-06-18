package org.loopstudios;

import org.bukkit.plugin.java.JavaPlugin;

public final class VioletKoth extends JavaPlugin {

    @Override
    public void onEnable() {
        this.configuration();
        this.command();
        this.listener();
    }

    @Override
    public void onDisable() {

    }

    private void listener(){}

    private void command(){
    }

    private void configuration(){
        getConfig().options().copyDefaults();
        saveDefaultConfig();
    }
}
