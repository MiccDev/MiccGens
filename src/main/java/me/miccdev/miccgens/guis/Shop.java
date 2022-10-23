package me.miccdev.miccgens.guis;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.miccdev.miccgens.items.CustomItem;

public class Shop extends GUI {

	private ItemStack weapons;
	private ItemStack armour;
	private ItemStack accessories;
	
	public Shop() {
		super("shop", "&e[&3&lShop&r&3]", 3 * 9);
		
		ItemStack empty = getEmptyItem();
		weapons = CustomItem.createItem(Material.IRON_SWORD, "&8Weapons", Arrays.asList("&bOpens the weapons shop."));
		armour = CustomItem.createItem(Material.IRON_CHESTPLATE, "&7Armour", Arrays.asList("&bOpens the armour shop."));
		accessories = CustomItem.createItem(Material.TOTEM_OF_UNDYING, "&7Accessories", Arrays.asList("&bOpens the accessories shop."));
		
		setContents(new ItemStack[] {
			empty, empty, empty, empty, empty, empty, empty, empty, empty,
			empty, empty, weapons, empty, armour, empty, accessories, empty, empty,
			empty, empty, empty, empty, empty, empty, empty, empty, empty,
		});
		
		createPage(new ItemStack[] {
			
		}); // WEAPONS
		
		createPage(new ItemStack[] {
				
		}); // ARMOUR
		
		createPage(new ItemStack[] {
				
		}); // ACCESSORIES
	}

	@Override
	public void onItemClick(Player player, ItemStack item) {
		if(weapons.equals(item)) {
			switchPage(1);
		}
	}
}
