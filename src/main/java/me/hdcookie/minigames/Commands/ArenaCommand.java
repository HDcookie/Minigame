package me.hdcookie.minigames.Commands;

import me.hdcookie.minigames.GameState;
import me.hdcookie.minigames.Instance.Arena;
import me.hdcookie.minigames.Minigames;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerQuitEvent;
import org.checkerframework.checker.units.qual.C;

import javax.smartcardio.CardException;

public class ArenaCommand implements CommandExecutor {
    private Minigames minigames;

    public ArenaCommand(Minigames minigames){
        this.minigames = minigames;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;

            if/*List*/(args.length == 1 && args[0].equalsIgnoreCase("list")){
                player.sendMessage(ChatColor.GREEN+"Avalible arenas:");
                for(Arena arena : minigames.getArenaManager().getArenas()){
                    player.sendMessage(ChatColor.GREEN + "ID: "+arena.getId() + arena.getState().name());
                }

            }/*Leave*/else if(args.length == 1 && args[0].equalsIgnoreCase("leave")){
                Arena arena = minigames.getArenaManager().getArena(player);
                if(arena != null){
                    player.sendMessage(ChatColor.DARK_GREEN+"You left the arena!");
                    arena.removePlayer(player);
                }else {player.sendMessage(ChatColor.RED +"You're not in a minigame");}


            }else  if/*Join*/(args.length == 2 && args[0].equalsIgnoreCase("join")){
                if(minigames.getArenaManager().getArena(player) != null){
                    player.sendMessage(ChatColor.RED+"You're already playing in an arena");
                    return false;
                }
                int id;
                try {
                    id = Integer.parseInt(args[1]);

                }catch (NumberFormatException e){
                    player.sendMessage(ChatColor.RED+"Invalid id");
return false;
                }

                if(id >= 0 && id < minigames.getArenaManager().getArenas().size()){
                    Arena arena = minigames.getArenaManager().getArena(id);
                    if(arena.getState() != GameState.LIVE){
                         player.sendMessage(ChatColor.GREEN+"You're now in Arena "+id);
                         arena.addPlayer(player);
                    }else {
                        player.sendMessage(ChatColor.RED+"The game is already running");
                    }

                }else {
                    player.sendMessage(ChatColor.RED+"Invalid id");
                }

            }else {
                player.sendMessage(ChatColor.RED + "Invalid usage.  Example:");
                player.sendMessage(ChatColor.DARK_RED+"- /arena join 0");
                player.sendMessage(ChatColor.DARK_RED+"- /arena leave");
                player.sendMessage(ChatColor.DARK_RED+"- /arena list");
            }
        }

        return false;
    }
}
