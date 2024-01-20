package me.hdcookie.cookieCore.MenuListener;

import me.hdcookie.cookieCore.CookieCore;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class WorldsCommandListener implements Listener {

    private CookieCore cookieCore;
    public WorldsCommandListener(CookieCore cookieCore){
        this.cookieCore = cookieCore;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        if(event.getView().getTitle().equals("Worlds")){
            event.setCancelled(true);
            if(event.getCurrentItem() == null) return;
            if(event.getCurrentItem().getItemMeta() == null) return;
            if(event.getCurrentItem().getItemMeta().getDisplayName() == null) return;
            if(event.getCurrentItem().getItemMeta().getDisplayName().equals(" ")) return;
            event.getWhoClicked().teleport(cookieCore.getServer().getWorld(event.getCurrentItem().getItemMeta().getDisplayName()).getSpawnLocation());
            event.getWhoClicked().closeInventory();
        }
    }


}
