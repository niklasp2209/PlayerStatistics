package de.bukkitnews.playerstatistics.listener;

import de.bukkitnews.playerstatistics.stats.PlayerStatsManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    private final PlayerStatsManager playerStatsManager;

    public PlayerQuitListener(PlayerStatsManager playerStatistics){
        this.playerStatsManager = playerStatistics;
    }

    @EventHandler
    public void handleQuit(PlayerQuitEvent event){
        this.playerStatsManager.handlePlayerDisconnect(event.getPlayer());
    }
}
