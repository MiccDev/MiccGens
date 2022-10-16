package me.miccdev.miccgens.items;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.miccdev.miccgens.guis.GUI;
import me.miccdev.miccgens.items.weapons.CustomWeapon;
import me.miccdev.miccgens.utils.Utils;
import net.kyori.adventure.text.Component;

public class CustomItem extends ItemStack {
	
	public static void init() {
		createCustomItem("menu", Material.ECHO_SHARD, "&bMenu", Arrays.asList("&bOpens the main menu."), new ClickRunnable() {
			@Override
			public void run(Player player, CustomItem item) {
				player.openInventory(GUI.getGui("mainMenu").getInventory());
			}
		});
		
		createCustomItem("accessories", Material.NETHER_STAR, "&3&lAccessories", Arrays.asList("&bOpens the accessory menu."));
		createCustomItem("shop", Material.EMERALD, "&aShop", Arrays.asList("&bOpens the shop menu in the current world"));
		
		createCustomItem("azalea_flower", Material.ALLIUM, "&r&7Azalea Flower", Arrays.asList("&d&oA flower harvested from the finest Azalea."));
		
		Accessory.init();
		CustomWeapon.init();
	}
	
	public static CustomItem createCustomItem(String id, Material material, String name, List<String> lore) {
		return createCustomItem(id, material, name, lore, null);
	}
	
	public static CustomItem createCustomItem(String id, Material material, String name, List<String> lore, ClickRunnable onClick) {
		CustomItem item = new CustomItem(id, material);
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
	
	public static CustomItem getItem(String id) {
		return CustomItem.allItems.containsKey(id) ? CustomItem.allItems.get(id) : null;
	}
	
	public static boolean hasItem(String id) {
		return CustomItem.allItems.containsKey(id);
	}
	
	public static boolean isCustomItem(ItemStack item) {
		if(item == null) return false;
		return CustomItem.allItems.values().stream().filter(i -> {
			ItemMeta thisMeta = item.getItemMeta();
			ItemMeta otherMeta = i.getItemMeta();
			
			if(thisMeta == null) return false;
			if(otherMeta == null) return false;
			return thisMeta.displayName().equals(otherMeta.displayName()) && item.getType().equals(item.getType());
		}).collect(Collectors.toList()).size() >= 1;
	}
	
	public static Map<String, CustomItem> allItems = new HashMap<String, CustomItem>();
	
	public final String id;
	
	private ClickRunnable click;
	
	public CustomItem(String id, Material material) {
		super(material);
		this.id = id;
		CustomItem.allItems.put(id, this);
	}
	
	public void onItemClick(Player holder) {
		if(click != null) click.run(holder, this);
	}
	
	public void setClickMethod(ClickRunnable onClick) {
		this.click = onClick;
	}
	
	public boolean equals(ItemStack item) {
		ItemMeta thisMeta = getItemMeta();
		ItemMeta otherMeta = item.getItemMeta();
		
		if(otherMeta == null) return false;
		return thisMeta.displayName().equals(otherMeta.displayName()) && getType().equals(item.getType());
	}
	
}
