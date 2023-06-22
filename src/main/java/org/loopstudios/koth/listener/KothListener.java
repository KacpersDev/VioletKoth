package org.loopstudios.koth.listener;

import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.loopstudios.VioletKoth;
import org.loopstudios.utils.CC;
import org.loopstudios.utils.Cuboid;

import java.util.Objects;

public class KothListener implements Listener {

    private final VioletKoth plugin;

    public KothListener(VioletKoth plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onMine(BlockBreakEvent event) {
        if (event.getPlayer().getGameMode() != GameMode.CREATIVE) {
            for (final String koth : Objects.requireNonNull(this.plugin.getKothDataConfiguration().getConfigurationSection("koth")).getKeys(false)) {
                String name = this.plugin.getKothDataConfiguration().getString("koth." + koth + ".name");
                Cuboid cuboid = new Cuboid(this.plugin.getKothManager().getCuboid1(name),
                        this.plugin.getKothManager().getCuboid2(name));
                if (cuboid.contains(event.getBlock().getLocation())) {
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        if (event.getPlayer().getGameMode() != GameMode.CREATIVE) {
            for (final String koth : Objects.requireNonNull(this.plugin.getKothDataConfiguration().getConfigurationSection("koth")).getKeys(false)) {
                String name = this.plugin.getKothDataConfiguration().getString("koth." + koth + ".name");
                Cuboid cuboid = new Cuboid(this.plugin.getKothManager().getCuboid1(name),
                        this.plugin.getKothManager().getCuboid2(name));
                if (cuboid.contains(event.getBlock().getLocation())) {
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler @Deprecated
    public void onInventory(InventoryClickEvent event) {
        if (event.getView().getTitle().equalsIgnoreCase(CC.translate(this.plugin.getConfig().getString("inventory.title")))) {
            event.setCancelled(true);
        }
    }
}
