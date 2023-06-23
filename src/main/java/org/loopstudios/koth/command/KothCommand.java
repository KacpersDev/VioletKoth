package org.loopstudios.koth.command;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;
import org.loopstudios.VioletKoth;
import org.loopstudios.koth.Koth;
import org.loopstudios.koth.LocationType;
import org.loopstudios.koth.manager.KothManager;
import org.loopstudios.koth.task.KothTask;
import org.loopstudios.utils.CC;

import java.util.ArrayList;

public class KothCommand implements CommandExecutor {

    private final VioletKoth plugin;

    public KothCommand(VioletKoth plugin) {
        this.plugin = plugin;
    }

    @Override @SuppressWarnings("ALL")
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String s, @NotNull String[] args) {

        if (args.length == 0) {
            wrongUsage(sender);
            return false;
        } else if (args[0].equalsIgnoreCase("create")) {
            if (!sender.hasPermission("koth.admin")) {
                sender.sendMessage(CC.translate(this.plugin.getConfig()
                        .getString("messages.no-permissions")));
                return false;
            }

            if (args.length == 1) {
                wrongUsage(sender);
                return false;
            } else {
                String kothName = args[1];
                if (this.plugin.getKothManager().exists(kothName)) {
                    sender.sendMessage(CC.translate(this.plugin
                            .getConfig().getString("messages.koth-exists")));
                    return false;
                }

                this.plugin.getKothManager().create(kothName);
                sender.sendMessage(CC.translate(this.plugin.getConfig()
                        .getString("messages.koth-created")));
            }
        } else if (args[0].equalsIgnoreCase("delete")) {
            if (!sender.hasPermission("koth.admin")) {
                sender.sendMessage(CC.translate(this.plugin.getConfig()
                        .getString("messages.no-permissions")));
                return false;
            }

            if (args.length == 1) {
                wrongUsage(sender);
                return false;
            } else {
                String kothName = args[1];
                if (!this.plugin.getKothManager().exists(kothName)) {
                    sender.sendMessage(CC.translate(this.plugin
                            .getConfig().getString("messages.not-koth-exists")));
                    return false;
                }

                this.plugin.getKothManager().remove(kothName);
                sender.sendMessage(CC.translate(this.plugin.getConfig()
                        .getString("messages.koth-removed")));
            }
        } else if (args[0].equalsIgnoreCase("setcapzone")) {
            if (!(sender instanceof Player)) return false;
            Player player = (Player) sender;
            if (!sender.hasPermission("koth.admin")) {
                sender.sendMessage(CC.translate(this.plugin.getConfig()
                        .getString("messages.no-permissions")));
                return false;
            }

            if (args.length == 1) {
                wrongUsage(sender);
                return false;
            } else {
                String kothName = args[1];

                if (!this.plugin.getKothManager().exists(kothName)) {
                    sender.sendMessage(CC.translate(this.plugin
                            .getConfig().getString("messages.not-koth-exists")));
                    return false;
                }

                if (args.length == 2) {
                    wrongUsage(sender);
                    return false;
                } else {
                    String type = args[2];
                    if (type.equalsIgnoreCase("first")) {
                        this.plugin.getKothManager().setCapzone(kothName, LocationType.FIRST, player.getLocation());
                        player.sendMessage(CC.translate(this.plugin.getConfig().getString("messages.capzone-claimed")));
                    } else if (type.equalsIgnoreCase("second")) {
                        this.plugin.getKothManager().setCapzone(kothName, LocationType.SECOND, player.getLocation());
                        player.sendMessage(CC.translate(this.plugin.getConfig().getString("messages.capzone-claimed")));
                    }
                }
            }
        } else if (args[0].equalsIgnoreCase("setcuboid")) {
            if (!(sender instanceof Player)) return false;
            Player player = (Player) sender;
            if (!sender.hasPermission("koth.admin")) {
                sender.sendMessage(CC.translate(this.plugin.getConfig()
                        .getString("messages.no-permissions")));
                return false;
            }

            if (args.length == 1) {
                wrongUsage(sender);
                return false;
            } else {
                String kothName = args[1];

                if (!this.plugin.getKothManager().exists(kothName)) {
                    sender.sendMessage(CC.translate(this.plugin
                            .getConfig().getString("messages.not-koth-exists")));
                    return false;
                }

                if (args.length == 2) {
                    wrongUsage(sender);
                    return false;
                } else {
                    String type = args[2];
                    if (type.equalsIgnoreCase("first")) {
                        Location location = player.getLocation();
                        location.setY(256);
                        this.plugin.getKothManager().setCuboid(kothName, LocationType.FIRST, location);
                        player.sendMessage(CC.translate(this.plugin.getConfig().getString("messages.cuboid-claimed")));
                    } else if (type.equalsIgnoreCase("second")) {
                        Location location = player.getLocation();
                        location.setY(-256);
                        this.plugin.getKothManager().setCuboid(kothName, LocationType.SECOND, location);
                        player.sendMessage(CC.translate(this.plugin.getConfig().getString("messages.cuboid-claimed")));
                    }
                }
            }
        } else if (args[0].equalsIgnoreCase("start")) {
            if (!sender.hasPermission("koth.admin")) {
                sender.sendMessage(CC.translate(this.plugin.getConfig()
                        .getString("messages.no-permissions")));
                return false;
            }

            if (args.length == 1) {
                wrongUsage(sender);
            } else {
                String kothName = args[1];

                if (!this.plugin.getKothManager().exists(kothName)) {
                    sender.sendMessage(CC.translate(this.plugin
                            .getConfig().getString("messages.koth-not-exists")));
                    return false;
                }


                Bukkit.broadcastMessage(String.valueOf(KothManager.activeKoths));
                if (KothManager.activeKoths.size() >= 1) {
                    sender.sendMessage(CC.translate(this.plugin.getConfig().getString("messages.koth-running")));
                    return false;
                }
                Koth koth = new Koth(kothName,
                        this.plugin.getConfig().getInt("koth.captime"),
                        this.plugin.getKothManager().getCuboid1(kothName),
                        this.plugin.getKothManager().getCuboid2(kothName),
                        this.plugin.getKothManager().getCapzone1(kothName),
                        this.plugin.getKothManager().getCapzone2(kothName));
                KothManager.activeKoths.add(koth);

                KothTask kothTask = new KothTask(this.plugin);
                kothTask.run(this.plugin);
                KothTask.activeTasks.put(koth, kothTask.getBukkitTask());
                Bukkit.broadcastMessage(CC.translate(this.plugin.getConfig().getString("messages.koth-started")));
            }
        } else if (args[0].equalsIgnoreCase("stop")) {
            if (!sender.hasPermission("koth.admin")) {
                sender.sendMessage(CC.translate(this.plugin.getConfig()
                        .getString("messages.no-permissions")));
                return false;
            }

            if (args.length == 1) {
                wrongUsage(sender);
            } else {
                String kothName = args[1];

                if (!this.plugin.getKothManager().exists(kothName)) {
                    sender.sendMessage(CC.translate(this.plugin
                            .getConfig().getString("messages.not-koth-exists")));
                    return false;
                }

                if (KothManager.activeKoths.get(0) == null) {
                    sender.sendMessage(CC.translate(this.plugin.getConfig().getString("messages.koth-not-running")));
                    return false;
                }

                Koth koth = new Koth(kothName,
                        this.plugin.getConfig().getInt("koth.captime"),
                        this.plugin.getKothManager().getCuboid1(kothName),
                        this.plugin.getKothManager().getCuboid2(kothName),
                        this.plugin.getKothManager().getCapzone1(kothName),
                        this.plugin.getKothManager().getCapzone2(kothName));
                KothManager.activeKoths.add(koth);

                new KothTask(this.plugin).stop();
                BukkitTask bukkitTask = KothTask.activeTasks.get(koth);
                bukkitTask.cancel();
                bukkitTask = null;
                KothTask.activeTasks.remove(koth);
                Bukkit.broadcastMessage(CC.translate(this.plugin.getConfig().getString("messages.koth-stopped")));
            }
        } else if (args[0].equalsIgnoreCase("reset")) {
            if (!sender.hasPermission("koth.admin")) {
                sender.sendMessage(CC.translate(this.plugin.getConfig()
                        .getString("messages.no-permissions")));
                return false;
            }

            if (args.length == 1) {
                wrongUsage(sender);
                return false;
            } else {
                String kothName = args[1];

                if (!KothManager.activeKoths.get(0).getName().equalsIgnoreCase(kothName)) {
                    sender.sendMessage(CC.translate(this.plugin.getConfig().getString("messages.koth-not-running")));
                    return false;
                }

                Koth koth = new Koth(kothName,
                        this.plugin.getConfig().getInt("koth.captime"),
                        this.plugin.getKothManager().getCuboid1(kothName),
                        this.plugin.getKothManager().getCuboid2(kothName),
                        this.plugin.getKothManager().getCapzone1(kothName),
                        this.plugin.getKothManager().getCapzone2(kothName));
                BukkitTask bukkitTask = KothTask.activeTasks.get(koth);
                bukkitTask.cancel();
                bukkitTask = null;
                KothTask.activeTasks.remove(koth);

                KothManager.activeKoths.add(koth);
                KothTask kothTask = new KothTask(this.plugin);
                kothTask.run(this.plugin);
                KothTask.activeTasks.put(koth, kothTask.getBukkitTask());
                sender.sendMessage(CC.translate(this.plugin.getConfig().getString("messages.koth-reset")));
            }
        } else if (args[0].equalsIgnoreCase("capture")) {
            if (!sender.hasPermission("koth.admin")) {
                sender.sendMessage(CC.translate(this.plugin.getConfig()
                        .getString("messages.no-permissions")));
                return false;
            }

            if (args.length == 1) {
                wrongUsage(sender);
                return false;
            } else {
                Player player = Bukkit.getPlayer(args[1]);

                if (args.length == 2) {
                    wrongUsage(sender);
                    return false;
                } else {
                    String kothName = args[2];
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), this.plugin.getKothManager().getRandomReward().replace("%player%", player.getName()));
                    sender.sendMessage(CC.translate(this.plugin.getConfig().getString("messages.koth-capture")));
                }
            }
        } else {
            if (!(sender instanceof Player)) return false;
            Player player = (Player) sender;
            if (!sender.hasPermission("koth.gui")) {
                sender.sendMessage(CC.translate(this.plugin.getConfig()
                        .getString("messages.no-permissions")));
                return false;
            }

            String kothName = args[1];
            if (!this.plugin.getKothManager().exists(kothName)) {
                sender.sendMessage(CC.translate(this.plugin
                        .getConfig().getString("messages.not-koth-exists")));
                return false;
            }

            Inventory inventory = Bukkit.createInventory(player,
                    this.plugin.getConfig().getInt("inventory.size"),
                    CC.translate(this.plugin.getConfig().getString("inventory.title")));

            ItemStack itemStack = new ItemStack(Material.valueOf(this.plugin.getConfig().getString("inventory.item.item")));
            ItemMeta meta = itemStack.getItemMeta();
            meta.setDisplayName(CC.translate(this.plugin.getConfig().getString("inventory.item.name")));
            ArrayList<String> lore = new ArrayList<>();
            if (KothTask.activeTasks.get(0) == null) {
                for (final String string : this.plugin.getConfig().getStringList("inventory.item.lore-none")) {
                    lore.add(CC.translate(string).replace("%koth%", kothName));
                }
            } else {
                for (final String string : this.plugin.getConfig().getStringList("inventory.item.lore")) {
                    lore.add(CC.translate(string)
                            .replace("%koth%", kothName)
                            .replace("%timer%", String.valueOf(KothTask.timer))
                            .replace("%capper%", String.valueOf(Bukkit.getPlayer(KothTask.capper)))
                            .replace("%control%", String.valueOf(KothTask.underControl)));
                }
            }

            meta.setLore(lore);
            itemStack.setItemMeta(meta);
            inventory.setItem(this.plugin.getConfig().getInt("inventory.item.slot"), itemStack);
            player.openInventory(inventory);
        }

        return true;
    }

    private void wrongUsage(CommandSender sender){
        for (final String s : this.plugin.getConfig().getStringList("messages.wrong-usage")) {
            sender.sendMessage(CC.translate(s));
        }
    }
}
