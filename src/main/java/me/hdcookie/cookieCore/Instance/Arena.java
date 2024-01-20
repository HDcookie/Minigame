package me.hdcookie.cookieCore.Instance;

import me.hdcookie.cookieCore.GameState;
import me.hdcookie.cookieCore.Manager.ConfigManager;
import me.hdcookie.cookieCore.CookieCore;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Arena {

    //Class lvl variables
    private CookieCore cookieCore;
    private int id;
    private Location spawn;
    private GameState state;
    private List<UUID> players;
    private Countdown countdown;
    private Game game;

    //Constructor
    public Arena(CookieCore cookieCore, int id, Location spawn){
        this.cookieCore = cookieCore;

        this.id = id;
        this.spawn = spawn;

        this.state = GameState.RECRUITING;
        this.players = new ArrayList<>();
        this.countdown = new Countdown(cookieCore, this);
        this.game = new Game(this);
    }
    //Players
    public void addPlayer(Player player){
        players.add(player.getUniqueId());
        player.teleport(spawn);
        if(state.equals(GameState.RECRUITING) && players.size() >= ConfigManager.getRequiredPlayers()){
            countdown.start();
        }
    }
    public void removePlayer(Player player){
        players.remove(player.getUniqueId());
        player.teleport(ConfigManager.getLobby());
        player.sendTitle("","");

        if(state == GameState.COUNTDOWN && players.size() < ConfigManager.getRequiredPlayers()){
            reset(false);
            sendMessage(ChatColor.GOLD + "There isnt enough players, countdown stopped");
            return;
        }
        if(state == GameState.LIVE && players.size() < ConfigManager.getRequiredPlayers()){
            reset(false);
            sendMessage(ChatColor.GOLD + "Game ended because too many players left");
        }

    }


    //Tools
    public void setState(GameState state){this.state = state;}
    public void sendMessage(String message){
        for(UUID uuid : players){
            Bukkit.getPlayer(uuid).sendMessage(message);
        }
    }
    public void sendTitle(String title, String subTitle){
        for(UUID uuid : players){
            Bukkit.getPlayer(uuid).sendTitle(title, subTitle);
        }
    }

    //Getters
    public int getId() {return id;}
    public GameState getState() {return state;}
    public List<UUID> getPlayers() {return players;}
    public Game getGame() {return game;}

    //Game
    public void start(){game.start();}

    public void reset(boolean kickPlayers){
        if(kickPlayers){
            Location location = ConfigManager.getLobby();
            for(UUID uuid : players){
                Bukkit.getPlayer(uuid).teleport(location);
            }
            players.clear();
        }
        sendTitle("","");
        state = GameState.RECRUITING;
        countdown.cancel();
        countdown = new Countdown(cookieCore, this);
        game = new Game(this);
    }

}
