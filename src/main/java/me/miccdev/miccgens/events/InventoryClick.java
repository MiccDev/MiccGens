package me.miccdev.miccgens.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

import me.miccdev.miccgens.Main;
import me.miccdev.miccgens.guis.GUI;
import me.miccdev.miccgens.items.CustomItem;
import me.miccdev.miccgens.utils.Utils;

public class InventoryClick extends CustomEvent {

	public InventoryClick(Main plugin) {
		super(plugin);
	}
	
	@EventHandler
	public void onItemDrop(PlayerDropItemEvent e) {
		ItemStack currentItem = e.getItemDrop().getItemStack();
		if(CustomItem.getItem("menu").equals(currentItem)) {
			e.setCancelled(true);
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		ItemStack currentItem = e.getCurrentItem();
		if(CustomItem.getItem("menu").equals(currentItem)) {
			e.setCancelled(true);
		}
		
		InventoryView view = e.getView();
		String viewTitle = view.getTitle().replaceAll("§", "&");
		Player player = (Player) e.getWhoClicked();
		
        if (e.getInventory().getType().equals(InventoryType.WORKBENCH)) {
            // cancels the event, which makes it so when the player tries to take an item from crafting it, it won't let the player take it.
            e.setCancelled(true);
            player.sendMessage(Utils.toComponent("&c&lCrafting is disabled on this server!"));
        }
		
		for(GUI gui : GUI.allGuis.values()) {
			if(!gui.isClickable()) continue;
			if(gui.getTitle().equals(viewTitle)) {
				e.setCancelled(true);
				gui.onItemClick(player, currentItem);
			}
		}
	}
	
	@EventHandler
	public void onInventoryClick(InventoryDragEvent e) {
		for(GUI gui : GUI.allGuis.values()) {
			if(!gui.isClickable()) continue;
			if(e.getInventory().equals(gui.getInventory())) {
				e.setCancelled(true);
			}
		}
	}
	
}
