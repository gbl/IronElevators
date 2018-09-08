package me.ScarleTomato.IronElevators;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.plugin.java.JavaPlugin;

public class IronElevators extends JavaPlugin {

    //default config values
    public int maxElevation = 14, minElevation = 3;
    public Material elevatorMaterial = Material.IRON_BLOCK;
    public Sound elevatorWhoosh = Sound.ENTITY_IRON_GOLEM_ATTACK;
    public TeleportCause cause = TeleportCause.UNKNOWN;

    EventListener listener;
    FileConfiguration config;
    File configFile;

    @Override
    public void onEnable() {
        loadConfig();
        getConfigValues();
        listener = new EventListener(this);
        getServer().getPluginManager().registerEvents(this.listener, this);
    }

    void getConfigValues() {
        maxElevation = config.getInt("maxElevation");
        minElevation = config.getInt("minElevation");
        try {
            elevatorMaterial = Material.valueOf(config.getString("elevatorMaterial").toUpperCase());
        } catch (IllegalArgumentException ex) {
            this.getLogger().log(Level.WARNING, "Elevator Material not found: {0}, using iron block instead", ex.getMessage());
            elevatorMaterial = Material.IRON_BLOCK;
        }
        try {
            elevatorWhoosh = Sound.valueOf(config.getString("elevatorWhoosh").toUpperCase());
        } catch (IllegalArgumentException ex) {
            this.getLogger().log(Level.WARNING, "Elevator whoosh sound not found: {0}, using iron golem instead", ex.getMessage());
            elevatorWhoosh = Sound.ENTITY_IRON_GOLEM_ATTACK;
        }
        cause = TeleportCause.valueOf(config.getString("teleportcause", "UNKNOWN"));
    }

    void loadConfig() {
        try {
            config = new YamlConfiguration();
            configFile = new File(getDataFolder(), "config.yml");
            if (!configFile.exists()) {
                //build config from defaults
                configFile.getParentFile().mkdirs();
                config.set("minElevation", minElevation);
                config.set("maxElevation", maxElevation);
                config.set("elevatorMaterial", elevatorMaterial.toString());
                config.set("elevatorWhoosh", elevatorWhoosh.toString());
                config.set("teleportcause", TeleportCause.UNKNOWN.name());
                config.save(configFile);
            }
            config.load(configFile);
        } catch(IOException | InvalidConfigurationException e) {
            this.getLogger().log(Level.WARNING, "{0}Exception {1}when loading configuration file.\n{2}", new Object[]{ChatColor.RED, Color.white, e.getMessage()});
        }
    }
}
