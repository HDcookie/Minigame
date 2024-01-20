package me.hdcookie.cookieCore.Commands;

import me.hdcookie.cookieCore.CookieCore;
import me.hdcookie.cookieCore.Utilities.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class WorldsCommand implements CommandExecutor {
    private CookieCore cookieCore;

    public WorldsCommand(CookieCore cookieCore) {
        this.cookieCore = cookieCore;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;

        // Simple command that shows the player a gui, which contains all the worlds.
        // Clicking on them TP's the player to that world.


        //Create the menu
        Inventory inv = Bukkit.createInventory(player, 54, "Worlds");

        //Creating the itemstacks
        ItemStack background = new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE)
                .setName(" ").toItemStack();

        ArrayList<ItemStack> worlds = new ArrayList<>();//Arraylist of itemstacks for the worlds

        //Looping through each world, creating an itemstack and adding it to the arraylist
        for (World world : Bukkit.getWorlds()) {
            ItemStack worldItem = new ItemStack(Material.GRASS_BLOCK);
            ItemMeta meta = worldItem.getItemMeta();

            //Checking the config to see if the world has a custom material
            if (cookieCore.getConfig().contains("worlds." + world.getName() + ".material")) {
                System.out.println("Found custom material for " + world.getName() + " : " + cookieCore.getConfig().getString("worlds." + world.getName() + ".material"));
                worldItem.setType(Material.getMaterial(cookieCore.getConfig().getString("worlds." + world.getName() + ".material")));
            }else if (world.getEnvironment().equals(World.Environment.NORMAL)) {
                worldItem.setType(Material.GRASS_BLOCK);
            } else if (world.getEnvironment().equals(World.Environment.NETHER)) {
                worldItem.setType(Material.NETHERRACK);
            } else if (world.getEnvironment().equals(World.Environment.THE_END)) {
                worldItem.setType(Material.END_STONE);
            }

            //adding player names to the lore
            List<String> lore = new ArrayList<>();
            for (Player p : world.getPlayers()) {
                lore.add(p.getName());
            }
            meta.setLore(lore);

            //Setting the name of the itemstack
            meta.setDisplayName(world.getName());


            worldItem.setItemMeta(meta);

            //Adding the itemstack to the arraylist
            worlds.add(worldItem);
        }


        //Adding the items to the inventory
        for (int i = 0; i < 54; i++) {
            if (i < worlds.size()) {
                inv.setItem(i, worlds.get(i));
            } else {
                inv.setItem(i, background);
            }
        }

        //Opening the inventory
        player.openInventory(inv);
        //Sending a message to the player
        player.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "Opening the worlds menu...");

        return false;
        }

}
