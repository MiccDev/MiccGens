package me.miccdev.miccgens.guis;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import me.miccdev.miccgens.Main;
import me.miccdev.miccgens.utils.Utils;

public class Accessories extends GUI {

	public static Map<String, Accessories> accessoryInventories = new HashMap<String, Accessories>();
	
	public Accessories() {
		super("accessories", "&e[&3&lAccessories&r&e]", 9);
	}
	
	@Override
	public void onOpen(Main plugin, Player player) {
		Inventory savedData = Utils.loadPlayerData(player.getUniqueId().toString());
		if(savedData != null) {
			getInventory().setContents(savedData.getContents());
		}
	}
	
	@Override
	public void onClose(Main plugin, Player player) {
		Utils.savePlayerData(player.getUniqueId().toString(), getInventory());
	}
	
	public static Accessories getAccessoryInventory(String uuid) {
		Accessories a = accessoryInventories.get(uuid);
		
		if(a == null) {
			a = new Accessories();
			accessoryInventories.put(uuid, a);
		}
		
		return a;
	}
	
}
