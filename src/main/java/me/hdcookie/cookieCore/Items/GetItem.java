package me.hdcookie.cookieCore.Items;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class GetItem {

    public ItemStack getItem(MongoDatabase database, String id){
        ItemStack itemStack = new ItemStack(Material.BARRIER);
        ItemMeta itemmetasaas = itemStack.getItemMeta();
        itemmetasaas.setDisplayName("Error");
        itemStack.setItemMeta(itemmetasaas);

        //--------------------------------------------------------------------------------------------------------------
        // This gets the item document from database
        //--------------------------------------------------------------------------------------------------------------


        //first, get the collection
        MongoCollection items = database.getCollection("items");
        //then get the specific item document with cursor
        Document query;

        try{
            query = new Document("id", Pattern.compile(id, Pattern.CASE_INSENSITIVE));
        }catch (NullPointerException e){
            System.out.println("Error with item: " + id + " no id found.  Either delete this, or add an id.  Cannot continue");
            Bukkit.broadcast("Error with item: " + id + " no id found.  Either delete this, or add an id.  Cannot continue", "*)");
            return itemStack;
        }


        Document doc = new Document();
        MongoCursor<Document> cursor = items.find(query).cursor();

        try {
            if(cursor.hasNext()) {
                doc = cursor.next();
            }else{
                return null;
            }
        }catch (Exception e){
            e.printStackTrace();
        }


        //--------------------------------------------------------------------------------------------------------------
        //This is to get the itemStack from the document
        //
        //At this point, the document has been found, so we can get the item.
        //We need to check every variable to see if it exists, if so, add it to the itemStack
        //--------------------------------------------------------------------------------------------------------------

        //first, create all the variables
        String itemName; // this should never be null
        String itemMaterial; // this should never be null
        String itemLore; // this should turn out to be null, and unused, unless it's using old system
        boolean isEnchanted; //can be null
        int itemAmount; // can be null
        String itemDescription; // this is the new system, and should be used if it exists
        String itemRarity; //

        //Checks, then adds the data from the document to the variables above
        if(!doc.containsKey("id")){
            System.out.println("No id found.  lowkey don't know how that happened");
            return itemStack;
        }
        itemName = doc.getString("id");

        if (!doc.containsKey("material")){
            System.out.println("Error with item: "+doc.getString("id")+" no material found.  Either delete this, or add a material");
            return itemStack;
        }
        itemMaterial = doc.getString("material");

        //Checks to see if it's using the old lore variable, or the new description variable
        if (!doc.containsKey("lore")){
            //This is good, because it should be using the description variable.  If there is lore, we can revert to old system
            itemLore = null;
        }else {
            //This is bad, but we can still use it
            itemLore = doc.getString("lore");
        }

        if(!doc.containsKey("description")){
            //This is bad, because there is no lore or description.  We can just set it to null
            itemDescription = null;
        }else{
            itemDescription = doc.getString("description");
        }

        if(!doc.containsKey("rarity")){
            //This is fine, well set it to common
            itemRarity = "Common";
        }else {
            itemRarity = doc.getString("rarity");
        }

        if(!doc.containsKey("isEnchanted")){
            //This is fine, we'll set it to false
            isEnchanted = false;
        }else {
            isEnchanted = doc.getBoolean("isEnchanted");
        }

        if(!doc.containsKey("amount")){
            //This is fine, we'll set it to 1
            itemAmount = 1;
        }else {
            itemAmount = doc.getInteger("amount");
        }

        //--------------------------------------------------------------------------------------------------------------
        //This is to create the itemStack
        //--------------------------------------------------------------------------------------------------------------
        itemStack = new ItemStack(Material.getMaterial(itemMaterial), itemAmount);

        ItemMeta itemMeta = itemStack.getItemMeta();

        itemMeta.setDisplayName(itemName);

        List<String> lore = new ArrayList<>();
        if(itemDescription != null){

            lore.add(ChatColor.GOLD + "Description: ");
            lore.add(ChatColor.DARK_GRAY + itemDescription);

            if(itemRarity != null){
                lore.add(ChatColor.GOLD + "Rarity: ");
                lore.add(ChatColor.DARK_GRAY + itemRarity);
            }

        }else if(itemLore != null){
            lore.add(itemLore);
        }

        if(isEnchanted){
            itemMeta.addEnchant(Enchantment.DURABILITY, 1, true);
            itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        itemMeta.setLore(lore);

        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

}
