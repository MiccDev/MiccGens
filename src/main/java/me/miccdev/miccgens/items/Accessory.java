package me.miccdev.miccgens.items;

import org.bukkit.Material;
import org.bukkit.entity.Player;

public class Accessory extends CustomItem {

	public Accessory(String id, Material material) {
		super(id, material, CustomItemType.ACCESSORY, false);
	}
	
	public boolean accessoryUsed(Player user) { return false; };
	
}
