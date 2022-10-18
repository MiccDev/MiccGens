package me.miccdev.miccgens.items;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.miccdev.miccgens.Main;
import me.miccdev.miccgens.guis.GUI;
import me.miccdev.miccgens.utils.DataManager;
import me.miccdev.miccgens.utils.Utils;
import net.kyori.adventure.text.Component;

public class CustomItem extends ItemStack {
	
	private static FileConfiguration config;
	
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
		
		CustomItem.config = Main.itemData.getConfig();
		
		initAllItems();
		
		Accessory.init();
		Weapon.init();
	}
	
	public static void initAllItems() {
		DataManager itemData = Main.itemData;
		FileConfiguration config = itemData.getConfig();
		
		Set<String> itemIds = config.getKeys(false);
		
		for(String id : itemIds) {
			initItem(id);
		}
	}
	
	public static void initItem(String lastString) {
		String itemType = config.getString(lastString + ".type").toUpperCase();
		String matId = config.getString(lastString + ".material");
		String displayName = Utils.toColour(config.getString(lastString + ".display_name"));
		
		List<String> lore = config.contains(lastString + ".lore") ? config.getStringList(lastString + ".lore") : new ArrayList<>();

		Material material = Material.matchMaterial(matId);		
		CustomItemType type = CustomItemType.valueOf(itemType);

		switch(type) {
			case ACCESSORY:
				createAccessory(lastString, material, displayName, lore);
				break;
			case WEAPON:
				float damage = config.contains(lastString + ".damage") ? (float) config.get(lastString + ".damage") : 1f;
				createWeapon(lastString, material, displayName, lore, damage);
				break;
			case ITEM:
				createCustomItem(lastString, material, displayName, lore);
				break;
		}
	}
	
	public static Accessory createAccessory(String id, Material material, String name, List<String> lore) {
		Accessory item = new Accessory(id, material);
		editData(item, name, lore);
		return item;
	}
	
	public static Weapon createWeapon(String id, Material material, String name, List<String> lore, float damage) {
		return createWeapon(id, material, name, lore, damage, null);
	}
	
	public static Weapon createWeapon(String id, Material material, String name, List<String> lore, float damage, ClickRunnable onClick) {
		Weapon item = new Weapon(id, material, damage);
		editData(item, name, lore);
		return item;
	}
	
	public static CustomItem createCustomItem(String id, Material material, String name, List<String> lore) {
		return createCustomItem(id, material, name, lore, null);
	}
	
	public static CustomItem createCustomItem(String id, Material material, String name, List<String> lore, ClickRunnable onClick) {
		CustomItem item = new CustomItem(id, material);
		item.setClickMethod(onClick);
		editData(item, name, lore);
		return item;
	}
	
	private static ItemStack editData(ItemStack item, String name, List<String> lore) {
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
	
	public static boolean isWeapon(ItemStack item) {
		if(item == null) return false;
		if(!isCustomItem(item)) return false;
		CustomItem i = (CustomItem) item;
		return i.itemType == CustomItemType.WEAPON;
	}
	
	public static boolean isAccessory(ItemStack item) {
		if(item == null) return false;
		if(!isCustomItem(item)) return false;
		CustomItem i = (CustomItem) item;
		return i.itemType == CustomItemType.ACCESSORY;
	}
	
	
	public static Map<String, CustomItem> allItems = new HashMap<String, CustomItem>();
	
	public final String id;
	public final CustomItemType itemType;
	
	private ClickRunnable click;
	private ClickRunnable rightClick;
	
	public CustomItem(String id, Material material) {
		this(id, material, CustomItemType.ITEM);
	}
	
	public CustomItem(String id, Material material, CustomItemType itemType) {
		super(material);
		this.id = id;
		this.itemType = itemType;
		CustomItem.allItems.put(id, this);
	}
	
	public void onItemClick(Player holder) {
		if(click != null) click.run(holder, this);
	}
	
	public void onItemRightClick(Player holder) {
		if(rightClick != null) rightClick.run(holder, this);
	}
	
	public void setClickMethod(ClickRunnable onClick) {
		this.click = onClick;
	}
	
	public void setRightClickMethod(ClickRunnable onRightClick) {
		this.rightClick = onRightClick;
	}
	
	public boolean equals(ItemStack item) {
		ItemMeta thisMeta = getItemMeta();
		ItemMeta otherMeta = item.getItemMeta();
		
		if(otherMeta == null) return false;
		return thisMeta.displayName().equals(otherMeta.displayName()) && getType().equals(item.getType());
	}
	
}
