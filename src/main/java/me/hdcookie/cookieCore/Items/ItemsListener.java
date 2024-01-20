package me.hdcookie.cookieCore.Items;

import me.hdcookie.cookieCore.CookieCore;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class ItemsListener implements Listener {
    private CookieCore cookieCore;

    public ItemsListener(CookieCore cookieCore) {
        this.cookieCore = cookieCore;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getView().getTitle().contains("Custom Items menu")) {
            event.setCancelled(true);

            if(event.getCurrentItem().getItemMeta().getDisplayName().contains("Error")){
                event.getWhoClicked().sendMessage("Error with item.  Please contact an admin");
                event.getWhoClicked().sendMessage("if you are an admin, why don't you go add an id to that item buddy");
                return;
            }


            if (event.getCurrentItem() != null && !event.getCurrentItem().getType().equals(Material.GRAY_STAINED_GLASS_PANE)) {
                Player player = (Player) event.getWhoClicked();
                player.getInventory().addItem(event.getCurrentItem());
            }
        }
    }

    //This is for the custom functionality of the items.
    // Each one should reference a different class to handle the functionality, unless it's simple
    @EventHandler
    public void onPlayerInteraction(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if(event.getItem() == null){
            return;
        }else if(event.getItem().getType() == Material.AIR){
            return;
        }

        if(event.getItem() == null || event.getItem().getItemMeta().getDisplayName().equals("")) {
            return;
        }

        if (event.getItem().getItemMeta().getDisplayName().contains("heal_wand")) {
            if(event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                player.setHealth(20);
                player.sendMessage("You have been healed");
            }
        }


    }

}
