package me.hdcookie.minigames.Commands;

import me.hdcookie.minigames.Instance.Arena;
import me.hdcookie.minigames.Minigames;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerQuitEvent;
import org.checkerframework.checker.units.qual.C;

public class ArenaCommand implements CommandExecutor {
    private Minigames minigames;

    private ArenaCommand(Minigames minigames){
        this.minigames = minigames;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;

            if/*List*/(args.length == 1 && args[0].equalsIgnoreCase("list")){
                player.sendMessage(ChatColor.GREEN+"Avalible arenas:");
                player.sendMessage();
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
