package me.hdcookie.cookieCore;

import com.mongodb.client.MongoDatabase;
import me.hdcookie.cookieCore.Commands.ArenaCommand;
import me.hdcookie.cookieCore.Items.ItemsCommand;
import me.hdcookie.cookieCore.Commands.WorldsCommand;
import me.hdcookie.cookieCore.GameListener.ConnectListener;
import me.hdcookie.cookieCore.GameListener.GameListener;
import me.hdcookie.cookieCore.Items.ItemsListener;
import me.hdcookie.cookieCore.Manager.ArenaManager;
import me.hdcookie.cookieCore.Manager.ConfigManager;
import me.hdcookie.cookieCore.MenuListener.WorldsCommandListener;
import me.hdcookie.cookieCore.Manager.DatabaseManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class CookieCore extends JavaPlugin {

    private ArenaManager arenaManager;
    private DatabaseManager databaseManager;


    @Override
    public void onEnable() {
        // Plugin startup logic
        ConfigManager.setupConfig(this);
        databaseManager = new DatabaseManager(this);
        arenaManager = new ArenaManager(this);

        // Print out a message to the console
        Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "-------------------------------------");
        Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "CookieCore has been enabled");
        Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "-------------------------------------");

        // Register the listeners
        Bukkit.getPluginManager().registerEvents(new ConnectListener(this), this);
        Bukkit.getPluginManager().registerEvents(new GameListener(this), this);
        Bukkit.getPluginManager().registerEvents(new WorldsCommandListener(this), this);
        Bukkit.getPluginManager().registerEvents(new ItemsListener(this), this);

        // Register the commands
        getCommand("arena").setExecutor(new ArenaCommand(this));
        getCommand("worlds").setExecutor(new WorldsCommand(this));
        getCommand("items").setExecutor(new ItemsCommand(this));
    }



    public ArenaManager getArenaManager(){
        return  arenaManager;
    }
    public DatabaseManager getDatabaseManager(){
        return databaseManager;
    }



    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "-------------------------------------");
        Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "CookieCore has been disabled");
        Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "-------------------------------------");
    }
}
