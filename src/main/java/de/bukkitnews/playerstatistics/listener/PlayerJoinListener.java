package de.bukkitnews.playerstatistics.listener;

import de.bukkitnews.playerstatistics.stats.PlayerStatsManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    private final PlayerStatsManager playerStatsManager;

    public PlayerJoinListener(final PlayerStatsManager playerStatsManager){
        this.playerStatsManager = playerStatsManager;
    }

    @EventHandler
    public void handleJoinEvent(PlayerJoinEvent event){
        playerStatsManager.handlePlayerJoin(event.getPlayer());
    }
}
