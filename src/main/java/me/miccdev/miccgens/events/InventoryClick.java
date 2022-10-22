package me.miccdev.miccgens.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.ItemStack;

import me.miccdev.miccgens.Main;
import me.miccdev.miccgens.guis.GUI;
import me.miccdev.miccgens.items.CustomItem;

public class InventoryClick extends CustomEvent {

	public InventoryClick(Main plugin) {
		super(plugin);
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		ItemStack currentItem = e.getCurrentItem();
		if(CustomItem.getItem("menu").equals(currentItem)) {
			e.setCancelled(true);
		}
		
		for(GUI gui : GUI.allGuis.values()) {
			if(!gui.isClickable()) continue;
			if(e.getInventory().equals(gui.getInventory())) {
				e.setCancelled(true);
				gui.onItemClick((Player) e.getWhoClicked(), currentItem);
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
