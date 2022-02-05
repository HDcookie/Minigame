package me.hdcookie.minigames.Listener;

import me.hdcookie.minigames.GameState;
import me.hdcookie.minigames.Instance.Arena;
import me.hdcookie.minigames.Manager.ConfigManager;
import me.hdcookie.minigames.Minigames;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ConnectListener implements Listener {

    private Minigames minigames;

    public ConnectListener(Minigames minigames){
this.minigames = minigames;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        event.getPlayer().teleport(ConfigManager.getLobby());
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event){
        Arena arena = minigames.getArenaManager().getArena(event.getPlayer());
        if(arena != null){
            arena.removePlayer(event.getPlayer());



        }
    }

}
