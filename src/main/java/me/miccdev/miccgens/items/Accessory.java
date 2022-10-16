package me.miccdev.miccgens.items;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ItemMeta;

import me.miccdev.miccgens.utils.Utils;
import net.kyori.adventure.text.Component;

public class Accessory extends CustomItem {
	
	public static void init() {
		createCustomItem("test", Material.BLUE_WOOL, "Test Accessory", Arrays.asList("Does testing well!"));
	}
	
	public static Accessory createCustomItem(String id, Material material, String name, List<String> lore) {
		return createCustomItem(id, material, name, lore, null);
	}
	
	public static Accessory createCustomItem(String id, Material material, String name, List<String> lore, ClickRunnable onClick) {
		Accessory item = new Accessory(id, material);
		ItemMeta meta = item.getItemMeta();
		
		meta.displayName(Component.text(Utils.toColour(name)));
		
		List<Component> lores = new ArrayList<Component>();
		for(String line : lore) {
			lores.add(Component.text(Utils.toColour(line)));
		}
		meta.lore(lores);
		
		item.setItemMeta(meta);
		item.setClickMethod(onClick);
		return item;
	}

	public Accessory(String id, Material material) {
		super(id, material);
	}
	
	public boolean accessoryUsed(Player user) { return false; };
	
}
