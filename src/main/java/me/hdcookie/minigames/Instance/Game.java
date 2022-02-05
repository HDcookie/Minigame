package me.hdcookie.minigames.Instance;

import me.hdcookie.minigames.GameState;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class Game {
    private Arena arena;
    private HashMap<UUID, Integer> points;

    public Game(Arena arena){
        this.arena = arena;
        points = new HashMap<>();
    }

    public void start(){
        arena.setState(GameState.LIVE);
        arena.sendMessage(ChatColor.RED + "GAME STARTED");

        for(UUID uuid : arena.getPlayers()){
            points.put(uuid, 0);
        }
    }

    public void addPoint(Player player){
        int playerPoints = points.get(player.getUniqueId()) + 1;
        if(playerPoints == 20){
            arena.sendMessage(ChatColor.GOLD + player.getName()+"HAS WON! Thanks for playing");
            arena.reset(true);

            return;
        }else{
            player.sendMessage(ChatColor.RED + "+1 point ");
            points.replace(player.getUniqueId(), playerPoints);
        }

    }

}
