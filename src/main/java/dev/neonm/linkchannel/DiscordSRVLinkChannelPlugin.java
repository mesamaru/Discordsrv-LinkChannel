package dev.neonm.linkchannel;

import github.scarsz.discordsrv.DiscordSRV;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public final class DiscordSRVLinkChannelPlugin extends JavaPlugin {

    private LinkChannelListener listener;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        Plugin discordSrv = getServer().getPluginManager().getPlugin("DiscordSRV");
        if (discordSrv == null || !discordSrv.isEnabled()) {
            getLogger().severe("DiscordSRV is required but not enabled. Disabling plugin.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        listener = new LinkChannelListener(this);
        DiscordSRV.api.subscribe(listener);
        getLogger().info("DiscordSRV-LinkChannel-Latest enabled.");
    }

    @Override
    public void onDisable() {
        if (listener != null) {
            try {
                DiscordSRV.api.unsubscribe(listener);
            } catch (Throwable ignored) {
                // DiscordSRV may already be shutting down.
            }
        }
    }

    @Override
    public boolean onCommand(
            @NotNull CommandSender sender,
            @NotNull Command command,
            @NotNull String label,
            @NotNull String[] args
    ) {
        if (command.getName().equalsIgnoreCase("lcreload")) {
            if (!sender.hasPermission("linkchannel.reload")) {
                sender.sendMessage("You do not have permission.");
                return true;
            }

            reloadConfig();
            sender.sendMessage("DiscordSRV-LinkChannel-Latest config reloaded.");
            return true;
        }
        return false;
    }
}
