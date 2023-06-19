package org.loopstudios.koth.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.loopstudios.VioletKoth;
import org.loopstudios.koth.LocationType;
import org.loopstudios.utils.CC;

import java.time.LocalTime;

public class KothCommand implements CommandExecutor {

    private VioletKoth plugin;

    public KothCommand(VioletKoth plugin) {
        this.plugin = plugin;
    }

    @Override
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
        } else if (args[0].equalsIgnoreCase("setcaptime")) {
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
                    int capTime = Integer.parseInt(args[2]);

                    this.plugin.getKothManager().setCapTime(kothName, capTime);
                    sender.sendMessage(CC.translate(this.plugin.getConfig().getString("messages.captime-set")));
                }
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
                if (args.length == 2) {
                    wrongUsage(sender);
                    return false;
                } else {
                    String type = args[2];
                    LocationType m = this.plugin.getKothManager().getLocationByString(type);
                    if (type.equalsIgnoreCase("first")) {
                        this.plugin.getKothManager().setCapzone(kothName, LocationType.FIRST, player.getLocation());
                        player.sendMessage(CC.translate(this.plugin.getConfig().getString("messages.capzone-claimed")));
                    } else if (type.equalsIgnoreCase("second")) {
                        this.plugin.getKothManager().setCapzone(kothName, LocationType.SECOND, player.getLocation());
                        player.sendMessage(CC.translate(this.plugin.getConfig().getString("messages.capzone-claimed")));
                    }
                }
            }
        }

        return true;

    }

    private void wrongUsage(CommandSender sender){
        for (final String s : this.plugin.getConfig().getStringList("messages.wrong-usage")) {
            sender.sendMessage(CC.translate(s));
        }
    }
}
