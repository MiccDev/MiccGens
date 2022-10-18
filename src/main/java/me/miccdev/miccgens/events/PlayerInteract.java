package me.miccdev.miccgens.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import me.miccdev.miccgens.Main;
import me.miccdev.miccgens.items.CustomItem;

public class PlayerInteract extends CustomEvent {

	public PlayerInteract(Main plugin) {
		super(plugin);
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		Action a = event.getAction();
		ItemStack heldItem = player.getInventory().getItemInMainHand();
		
		if(a == Action.PHYSICAL) return;
		if(heldItem == null) return;
		
		for(CustomItem i : CustomItem.allItems.values()) {
			if(!i.equals(heldItem)) continue;
			i.onItemClick(player);
			
			if(a.isRightClick())
				i.onItemRightClick(player);
		}
	}
	
}
