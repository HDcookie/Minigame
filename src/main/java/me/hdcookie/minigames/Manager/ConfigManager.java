package me.hdcookie.minigames.Manager;

import me.hdcookie.minigames.Minigames;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigManager {

    private static FileConfiguration config;

    public static void setupConfig(Minigames minigames){
        ConfigManager.config = minigames.getConfig();
        minigames.saveDefaultConfig();

    }

    public static FileConfiguration getConfig() {
        return config;
    }

    public static int getRequiredPlayers() {return config.getInt("required-players");}

    public static int getCountdown() {return config.getInt("countdown-seconds");}

    public static Location getLobby() {
        Location loc = new Location(Bukkit.getWorld(config.getString("lobby-spawn")),
                config.getDouble("lobby-spawn.x"),
                config.getDouble("lobby-spawn.y"),
                config.getDouble("lobby-spawn.z"),
                (float) config.getDouble("lobby-spawn.yaw"),
                (float)config.getDouble("lobby-spawn.pitch"));
    return loc;
    }

}
