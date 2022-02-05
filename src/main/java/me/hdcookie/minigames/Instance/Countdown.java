package me.hdcookie.minigames.Instance;

import me.hdcookie.minigames.GameState;
import me.hdcookie.minigames.Manager.ConfigManager;
import me.hdcookie.minigames.Minigames;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

public class Countdown extends BukkitRunnable {

    private Minigames minigames;
    private Arena arena;
    private int countdown;

    public Countdown(Minigames minigames, Arena arena){
        this.arena = arena;
        this.minigames = minigames;
        this.countdown = ConfigManager.getCountdown();

    }

    public void start(){
        arena.setState(GameState.COUNTDOWN);
        runTaskTimer(minigames, 0, 20);
    }

    @Override
    public void run() {
        if(countdown == 0){
            cancel();
            arena.start();
            return;
        }
        if(countdown <= 10 || countdown % 15 == 0){
            arena.sendMessage(ChatColor.DARK_GREEN+ "Game will start in " + countdown + "second" + (countdown == 1 ? "" : "s") + ".");
            arena.sendTitle(ChatColor.DARK_GREEN+ "Game starting in "+ countdown, ChatColor.DARK_GRAY+"second" + (countdown == 1 ? "" : "s") + ".");
        }

        countdown--;
    }
}
