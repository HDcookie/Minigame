package me.hdcookie.cookieCore.Items;

import com.mongodb.client.MongoCollection;
import me.hdcookie.cookieCore.CookieCore;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class ItemsCommand implements CommandExecutor {
    private CookieCore cookieCore;
    private ItemUtils itemUtils;


    public ItemsCommand(CookieCore cookieCore) {
        this.cookieCore = cookieCore;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        //Has several sub-commands.  One is list, one is menu, one is give, one is insert into DB
        String helpMenu = "Help menu \n" +
                "/items list - List all the items \n" +
                "/items menu - Show the menu \n" +
                "/items give - Give the player the item you specify (not implimented) \n" +
                "/items insert - Insert the currently held item into the DB \n";

        //checks to see if sender is a player
        if(!(sender instanceof org.bukkit.entity.Player)){
            sender.sendMessage("You must be a player to use this command");
            return false;
        }else if(!sender.isOp()){
            sender.sendMessage("You do not have permission to use this command");
            return false;
        }
        Player player = (Player) sender;
        ItemUtils itemUtils = new ItemUtils();

        MongoCollection items = cookieCore.getDatabaseManager().getDatabase().getCollection("items");




        if(args.length == 0){
            //Show the help menu
            player.sendMessage(helpMenu);
        }
        else if(args[0].equalsIgnoreCase("list")) {
            //Sends the list of the names of every item in the DB
            List list = itemUtils.getList(cookieCore.getDatabaseManager().getDatabase());
            for(String name : (List<String>) list){
                player.sendMessage(name);
            }



        }
        else if(args[0].equalsIgnoreCase("menu")){
            //Show the menu
            player.openInventory(itemUtils.getItemsInventory(cookieCore.getDatabaseManager().getDatabase(), player));
            player.sendMessage("Showing the menu");
        }
        else if(args[0].equalsIgnoreCase("give")){
            //Give the player the item

            if(args.length == 1){
                player.sendMessage("You must specify an item");
                return false;
            }

            //gets the rest of the args, and puts them together
            String name = "";
            for(int i = 1; i < args.length; i++){
                name += args[i];
            }

            ItemStack item = itemUtils.getItemStack(cookieCore.getDatabaseManager().getDatabase(), name);
            if(item == null){
                player.sendMessage("Invalid item");
                return false;
            }

            player.getInventory().addItem(item);


            player.sendMessage("Given :" + args[1] + " to " + player.getName() + "!");

        }
        else if(args[0].equalsIgnoreCase("insert"))
        {
            //Insert the item into the DB
            ItemStack item = player.getInventory().getItemInMainHand();
            itemUtils.createItem(cookieCore.getDatabaseManager().getDatabase(), item, player);


            player.sendMessage("Inserted item into DB");
        } else {
            //Show the help menu
            player.sendMessage(helpMenu);
        }





        return false;
    }
}
