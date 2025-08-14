package com.moneybags.tempfly;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

public class TeleportListener implements Listener {

    private final TempFly plugin;

    public TeleportListener(TempFly plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent event) {
        Player player = event.getPlayer();

        // Check if player is actively using TempFly
        if (!plugin.getFlightManager().isTempFlying(player)) return;

        // Check if destination world allows TempFly
        if (!plugin.getFlightManager().worldHasTempFly(event.getTo().getWorld())) return;

        // After teleport, stop flying but keep ability to start again
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            if (player.isFlying()) {
                player.setFlying(false);
                player.setAllowFlight(true);
            }
        }, 1L);
    }
}
