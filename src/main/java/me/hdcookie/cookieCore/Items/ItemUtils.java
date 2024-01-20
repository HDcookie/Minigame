package me.hdcookie.cookieCore.Items;


import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemUtils {

    //Gets the inventory with the items from the database
    public Inventory getItemsInventory(MongoDatabase database, Player player){
        Inventory inventory = Bukkit.createInventory(player, 54, ChatColor.RED + "Custom Items menu");

        // Code to make the inventory with the items from the database
        List<String> list = getListId(database);
        for(String name : list){
            ItemStack item = getItemStack(database, name);
            inventory.addItem(item);
        }
        ItemStack background = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta backgroundMeta = background.getItemMeta();
        backgroundMeta.setDisplayName(" ");
        background.setItemMeta(backgroundMeta);

        for(int i = 0; i < 54; i++){
            if(inventory.getItem(i) == null || inventory.getItem(i).getType().equals(Material.AIR)) {
                inventory.setItem(i, background);
            }
        }



        return inventory;
    }

    //Gets the item from the database
    public ItemStack getItemStack(MongoDatabase database, String id){
        GetItem getItem = new GetItem();
        return getItem.getItem(database, id);
    }
    //Creates the item in the database.  Probably not needed and ill probably remove it
    public void createItem(MongoDatabase database, ItemStack item, Player player){
        MongoCollection<Document> items = database.getCollection("items");
        // Code to insert the item into the database\
        String name;
        String material;
        String lore;

        //Checks to see if the item is null
        if(item == null || item.getType().equals(Material.AIR) || item.getItemMeta() == null){
            player.sendMessage("You must be holding an item to insert it into the  DB");
            return;
        }
        //Checks to see if the item's name is null
        if(item.getItemMeta().getDisplayName() == null){
            name = "";
        }else{
            name = item.getItemMeta().getDisplayName();
        }

        //Checks to see if the item's material is null
        if(item.getType() == null){
            material = "null";
        }else{
            material = item.getType().toString();
        }

        //Checks to see if the item's lore is null
        if(item.getItemMeta().getLore() == null){
            lore = "";
        } else{
            lore = item.getItemMeta().getLore().toString();
            lore = lore.substring(1, lore.length()-1);
        }


        Document doc = new Document("name", name)
                .append("material", material)
                .append("lore", lore);
        items.insertOne(doc);



    }
    //Gets the list of items from the database
    public List<String > getList(MongoDatabase database){
        MongoCollection<Document> items = database.getCollection("items");
        ArrayList<String> list = new ArrayList<String>();
        // Code to get the list from the
        MongoCursor<Document> cursor = items.find().cursor();
        while(cursor.hasNext()){
            Document doc = cursor.next();
            list.add(doc.getString("name"));
        }
        cursor.close();

        return list;
    }
    public List<String > getListId(MongoDatabase database){
        MongoCollection<Document> items = database.getCollection("items");
        ArrayList<String> list = new ArrayList<String>();
        // Code to get the list from the
        MongoCursor<Document> cursor = items.find().cursor();
        while(cursor.hasNext()){
            Document doc = cursor.next();
            list.add(doc.getString("id"));
        }
        cursor.close();

        return list;
    }


}
