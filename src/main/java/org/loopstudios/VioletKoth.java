package org.loopstudios;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.loopstudios.koth.manager.KothManager;

import java.io.File;
import java.io.IOException;

public final class VioletKoth extends JavaPlugin {

    private KothManager kothManager;
    private final File kothData = new File(getDataFolder(), "kothdata.yml");
    private final FileConfiguration kothDataConfiguration = new YamlConfiguration();

    @Override
    public void onEnable() {
        this.configuration();
        this.command();
        this.listener();

        this.kothManager = new KothManager(this);
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

        // koth data

        if (!this.kothData.exists()) {
            this.kothData.getParentFile().mkdir();
            this.saveResource("kothdata.yml", false);
        }

        try {
            this.kothDataConfiguration.load(this.kothData);
        } catch (IOException | InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    public void relodData(){
        try {
            this.kothDataConfiguration.save(this.kothData);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            this.kothDataConfiguration.load(this.kothData);
        } catch (IOException | InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    public KothManager getKothManager() {
        return kothManager;
    }

    public FileConfiguration getKothDataConfiguration() {
        return kothDataConfiguration;
    }

    public File getKothData() {
        return kothData;
    }
}
