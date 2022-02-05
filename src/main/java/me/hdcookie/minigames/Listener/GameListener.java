package me.hdcookie.minigames.Listener;

import me.hdcookie.minigames.GameState;
import me.hdcookie.minigames.Instance.Arena;
import me.hdcookie.minigames.Minigames;
import org.bukkit.entity.Painting;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class GameListener implements Listener {

    private Minigames minigames;

    public GameListener(Minigames minigames){
this.minigames = minigames;
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event){
        Arena arena = minigames.getArenaManager().getArena(event.getPlayer());

        if(arena != null && arena.getState() == GameState.LIVE){
            arena.getGame().addPoint(event.getPlayer());
        }

    }
}
