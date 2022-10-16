package me.miccdev.miccgens.items.weapons;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.meta.ItemMeta;

import me.miccdev.miccgens.items.CustomItem;
import me.miccdev.miccgens.utils.Utils;
import net.kyori.adventure.text.Component;

public class CustomWeapon extends CustomItem {

	public static void init() {
		createCustomItem("charcoal_sword", 
				Material.NETHERITE_SWORD, 
				"&0Charcoal Sword", 
				Arrays.asList(
						"&8A weapon forged from the forests.", 
						"&8Difficult to obtain, but worth it."
				), 
				5f
		);
		createCustomItem("wheat_spear", 
				Material.SPECTRAL_ARROW, 
				"&6Wheat Spear", 
				Arrays.asList(
						"&eThrow this at your enemies."
				),
				3f
		);
	}
	
	public static CustomWeapon createCustomItem(String id, Material material, String name, List<String> lore) {
		return createCustomItem(id, material, name, lore, 10f);
	}
	
	public static CustomWeapon createCustomItem(String id, Material material, String name, List<String> lore, float damage) {
		CustomWeapon item = new CustomWeapon(id, material, damage);
		ItemMeta meta = item.getItemMeta();
		
		meta.displayName(Component.text(Utils.toColour(name)));
		
		List<Component> lores = new ArrayList<Component>();
		for(String line : lore) {
			lores.add(Component.text(Utils.toColour(line)));
		}
		meta.lore(lores);
		
		item.setItemMeta(meta);
		return item;
	}
	
	private float damage;
	
	public CustomWeapon(String id, Material material) {
		this(id, material, 10f);
	}
	
	public CustomWeapon(String id, Material material, float damage) {
		super(id, material);
		this.damage = damage;
	}

	
	
	public float getDamage() {
		return damage;
	}

	public void setDamage(float damage) {
		this.damage = damage;
	}
	
}
