package me.hdcookie.cookieCore.Instance;

import me.hdcookie.cookieCore.GameState;
import me.hdcookie.cookieCore.Manager.ConfigManager;
import me.hdcookie.cookieCore.CookieCore;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

public class Countdown extends BukkitRunnable {

    private CookieCore cookieCore;
    private Arena arena;
    private int countdown;

    public Countdown(CookieCore cookieCore, Arena arena){
        this.arena = arena;
        this.cookieCore = cookieCore;
        this.countdown = ConfigManager.getCountdown();

    }

    public void start(){
        arena.setState(GameState.COUNTDOWN);
        runTaskTimer(cookieCore, 0, 20);
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
