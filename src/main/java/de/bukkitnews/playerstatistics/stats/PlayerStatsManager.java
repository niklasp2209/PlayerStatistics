package de.bukkitnews.playerstatistics.stats;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import de.bukkitnews.playerstatistics.type.StatType;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.PropertyPermission;

public class PlayerStatsManager {

    private final Cache<Player, PlayerStats> playerStatsCache;
    private final FileConfiguration fileConfiguration;
    private final File file;

    public PlayerStatsManager (FileConfiguration fileConfiguration, File file) {
        this.fileConfiguration = fileConfiguration;
        this.file = file;
        //Guava Cache für Spielerstatistiken erstellen
        this.playerStatsCache = CacheBuilder.newBuilder()
                .maximumSize(1000)
                .build();
    }

    public void recordPlayerStats (Player player, StatType statType, int value) {
        PlayerStats playerStats = getPlayerStats(player);
        if (playerStats != null) {
            //Statistik und Cache aktualisieren
            playerStats.updateStat(statType, value);
            playerStatsCache.put(player, playerStats);
        }
    }

    public PlayerStats getPlayerStats (Player player) {
        PlayerStats playerStats = playerStatsCache.getIfPresent(player);
        if (playerStats == null) {
            //Statistiken laden aus YAML
            playerStats = loadPlayerStatsFromConfig(player);
            if(playerStats != null) {
                playerStatsCache.put(player, playerStats);
            }
        }
        return playerStats;
    }

    public void savePlayerStatsToFile (Player player) {
        PlayerStats playerStats = getPlayerStats(player);
        if (playerStats != null) {
            //Statistik in YAML speichern
            fileConfiguration.set(player.getUniqueId().toString(), playerStats.serialize());
            try {
                fileConfiguration.save(file);
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
    }

    public void handlePlayerDisconnect (Player player) {
        savePlayerStatsToFile(player);
        playerStatsCache.invalidate(player);
    }

    public void handlePlayerJoin (Player player) {
        PlayerStats playerStats = getPlayerStats(player);

    }


    private PlayerStats loadPlayerStatsFromConfig (Player player) {
        if (fileConfiguration.contains(player.getUniqueId().toString())) {
            return PlayerStats.deserialize(Objects.requireNonNull(fileConfiguration.getConfigurationSection(player.getUniqueId().toString())));
        }
        return null;
    }
}
