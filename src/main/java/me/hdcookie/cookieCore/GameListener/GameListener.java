package me.hdcookie.cookieCore.GameListener;

import me.hdcookie.cookieCore.GameState;
import me.hdcookie.cookieCore.Instance.Arena;
import me.hdcookie.cookieCore.CookieCore;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class GameListener implements Listener {

    private CookieCore cookieCore;

    public GameListener(CookieCore cookieCore){
this.cookieCore = cookieCore;
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event){
        Arena arena = cookieCore.getArenaManager().getArena(event.getPlayer());

        if(arena != null && arena.getState() == GameState.LIVE){
            arena.getGame().addPoint(event.getPlayer());
        }else if (arena != null && arena.getState() == GameState.RECRUITING) {
            event.setCancelled(true);
        }

    }
}
