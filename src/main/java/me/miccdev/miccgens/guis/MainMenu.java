package me.miccdev.miccgens.guis;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.miccdev.miccgens.items.CustomItem;

public class MainMenu extends GUI {

	private CustomItem accessories;
	private CustomItem shop;
	
	public MainMenu() {
		super("mainMenu", "&e[&3&lMenu&r&e]", 9 * 3, true);
		ItemStack empty = getEmptyItem();
		accessories = CustomItem.getItem("accessories");
		shop = CustomItem.getItem("shop");
		
		getInventory().setContents(new ItemStack[] {
				empty, empty, empty, empty, empty, empty, empty, empty, empty,
				empty, empty, empty, shop, empty, accessories, empty, empty, empty,
				empty, empty, empty, empty, empty, empty, empty, empty, empty,
		});
	}

	@Override
	public void onItemClick(Player player, ItemStack item) {
		if(accessories.equals(item)) {
			
		} else if(shop.equals(item)) {
			player.openInventory(GUI.getGui("shop").getInventory());
		}
	}
	
}
