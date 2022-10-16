package me.miccdev.miccgens.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.InventoryView;

import me.miccdev.miccgens.Main;
import me.miccdev.miccgens.guis.GUI;

public class InventoryClose extends CustomEvent {

	public InventoryClose(Main plugin) {
		super(plugin);
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onInventoryClose(InventoryCloseEvent event) {
		InventoryView view = event.getView();
		String viewTitle = view.getTitle().replaceAll("§", "&");
		
		for(GUI gui : GUI.allGuis.values()) {
			if(viewTitle.equals(gui.getTitle())) {
				gui.onClose(getPlugin(), (Player) event.getPlayer());
			}
		}
	}
	
}
