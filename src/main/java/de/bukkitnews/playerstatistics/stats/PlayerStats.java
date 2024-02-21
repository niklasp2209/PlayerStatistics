package de.bukkitnews.playerstatistics.stats;

import de.bukkitnews.playerstatistics.type.StatType;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.MemoryConfiguration;

@Getter
public class PlayerStats {

    private int mobsKilled;
    private int blocksMined;

    public PlayerStats (int mobsKilled, int blocksMined) {
        this.mobsKilled = mobsKilled;
        this.blocksMined = blocksMined;
    }

    public void updateStat (StatType statType, int value) {
        switch (statType) {
            case MOBS_KILLED:
                mobsKilled += value;
                break;
            case BLOCKS_MINED:
                blocksMined += value;
                break;
        }
    }

    public ConfigurationSection serialize() {
        ConfigurationSection configurationSection = new MemoryConfiguration();
        configurationSection.set("mobs_killed", mobsKilled);
        configurationSection.set("blocks_mined", blocksMined);

        return configurationSection;
    }

    public static PlayerStats deserialize(ConfigurationSection configurationSection) {
        int mobsKiled = configurationSection.getInt("mobs_killed");
        int blocksMined = configurationSection.getInt("blocks_mied");

        return new PlayerStats(mobsKiled, blocksMined);
    }
}
