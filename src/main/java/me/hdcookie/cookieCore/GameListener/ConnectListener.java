package me.hdcookie.cookieCore.GameListener;

import me.hdcookie.cookieCore.Instance.Arena;
import me.hdcookie.cookieCore.Manager.ConfigManager;
import me.hdcookie.cookieCore.CookieCore;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ConnectListener implements Listener {

    private CookieCore cookieCore;

    public ConnectListener(CookieCore cookieCore){
this.cookieCore = cookieCore;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        //checks if tp on join is enabled
        if(ConfigManager.getConfig().contains("tp-on-join") && ConfigManager.getConfig().getBoolean("tp-on-join")){
            event.getPlayer().teleport(ConfigManager.getLobby());
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event){
        Arena arena = cookieCore.getArenaManager().getArena(event.getPlayer());
        if(arena != null){
            arena.removePlayer(event.getPlayer());



        }
    }

}
