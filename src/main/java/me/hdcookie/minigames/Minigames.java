package me.hdcookie.minigames;

import me.hdcookie.minigames.Commands.ArenaCommand;
import me.hdcookie.minigames.Listener.ConnectListener;
import me.hdcookie.minigames.Listener.GameListener;
import me.hdcookie.minigames.Manager.ArenaManager;
import me.hdcookie.minigames.Manager.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Minigames extends JavaPlugin {

    private ArenaManager arenaManager;

    @Override
    public void onEnable() {
        Bukkit.getConsoleSender().sendMessage("New minigame loaded");

        ConfigManager.setupConfig(this);
        arenaManager = new ArenaManager(this);
        System.out.println("Hi ");

        Bukkit.getPluginManager().registerEvents(new ConnectListener(this), this);
        Bukkit.getPluginManager().registerEvents(new GameListener(this), this);
        getCommand("arena").setExecutor(new ArenaCommand(this));
    }

    public ArenaManager getArenaManager(){
        return  arenaManager;
    }
}
