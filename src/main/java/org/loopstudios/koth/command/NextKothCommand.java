package org.loopstudios.koth.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.loopstudios.VioletKoth;
import org.loopstudios.utils.CC;

import java.util.Objects;

public class NextKothCommand implements CommandExecutor {

    private final VioletKoth plugin;

    public NextKothCommand(VioletKoth plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if (!(sender.hasPermission("koth.capture"))) {
            sender.sendMessage(CC.translate(this.plugin.getConfig()
                    .getString("messages.no-permissions")));
            return false;
        }

        int nextKoth = this.plugin.getKothScheduler().getTimer();
        sender.sendMessage(CC.translate(Objects.requireNonNull(this.plugin.getConfig().getString("messages.koth-next")).replace("%timer%", String.valueOf(nextKoth))));

        return true;
    }
}
