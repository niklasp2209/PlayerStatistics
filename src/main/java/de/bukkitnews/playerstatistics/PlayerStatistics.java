package de.bukkitnews.playerstatistics;

import de.bukkitnews.playerstatistics.listener.PlayerJoinListener;
import de.bukkitnews.playerstatistics.stats.PlayerStatsManager;
import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

@Getter
public class PlayerStatistics extends JavaPlugin {

    private PlayerStatsManager playerStatsManager;

    @Override
    public void onEnable(){
        initializeConfig();
        initializePlayerStatsManager();

        registerEventListener();

        System.out.println("[PlayerStatistics] Plugin loaded successfully");
    }

    @Override
    public void onDisable(){
        savePlayerStatsOnShutdown();

        System.out.println("[PlayerStatistics] Plugin shutdown");
    }

    private void initializeConfig(){
        File file = new File(getDataFolder(), "player_stats.yml");
        if(!file.exists()){
            saveResource("player_stats.yml", false);
        }
        FileConfiguration fileConfiguration = YamlConfiguration.loadConfiguration(file);
    }

    private void initializePlayerStatsManager(){
        File file = new File(getDataFolder(), "player_stats.yml");
        FileConfiguration fileConfiguration = YamlConfiguration.loadConfiguration(file);
        playerStatsManager = new PlayerStatsManager(fileConfiguration, file);
    }

    private void registerEventListener(){
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(playerStatsManager), this);
    }

    private void savePlayerStatsOnShutdown(){
        for(Player player : getServer().getOnlinePlayers()){
            playerStatsManager.savePlayerStatsToFile(player);
        }
    }
}