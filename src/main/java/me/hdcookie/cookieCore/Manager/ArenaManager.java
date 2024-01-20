package me.hdcookie.cookieCore.Manager;

import me.hdcookie.cookieCore.Instance.Arena;
import me.hdcookie.cookieCore.CookieCore;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ArenaManager {

    private List<Arena> arenas = new ArrayList<>();

    public ArenaManager (CookieCore cookieCore){
        FileConfiguration config = ConfigManager.getConfig();
        //Looping through each arena, string being the arena name
        for(String string : config.getConfigurationSection("arenas").getKeys(false)){
            //Adds it, just a ton of location adding stuff
            arenas.add(new Arena(cookieCore,Integer.parseInt(string), new Location(Bukkit.getWorld(config.getString("arenas."+string+".world")),
                    config.getDouble("arenas."+string+".x"),
                    config.getDouble("arenas."+string+".y"),
                    config.getDouble("arenas."+string+".z"),
                    (float) config.getDouble("arenas."+string+".yaw"),
                    (float)config.getDouble("arenas."+string+".pitch"))));
        }
    }

    public List<Arena> getArenas() {return arenas;}

    public Arena getArena(Player player){
        for(Arena arena : arenas){
            if(arena.getPlayers().contains(player.getUniqueId())){
                return arena;
            }
        }
        return null;
    }

    public Arena getArena(int id){
        for(Arena arena : arenas){

            if(arena.getId() == id){
                return arena;
            }
        }
        return null;
    }
}
