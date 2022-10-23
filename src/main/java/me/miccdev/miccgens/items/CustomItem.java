package me.miccdev.miccgens.items;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.attribute.AttributeModifier.Operation;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.miccdev.miccgens.Main;
import me.miccdev.miccgens.guis.GUI;
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
		
		CustomItem.config = Main.itemData.getConfig();
		initAllItems();
	}
	
	public static void initAllItems() {		
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
				float damage = config.contains(lastString + ".damage") ? (float) ((Double) config.getDouble(lastString + ".damage")).floatValue() : 1f;
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
		editAttribute(item, 
				Attribute.GENERIC_ATTACK_DAMAGE,
				new AttributeModifier("generic.attackDamage", damage, Operation.ADD_NUMBER),
				"&8Damage: &9" + damage
		);
		return item;
	}
	
	public static ItemStack createItem(Material material, String name, List<String> lore) {
		ItemStack item = new ItemStack(material, 1);
		editData(item, name, lore);
		return item;
	}
	
	public static CustomItem createCustomItem(String id, Material material, String name, List<String> lore) {
		return createCustomItem(id, material, name, lore, null, true);
	}
	
	public static CustomItem createCustomItem(String id, Material material, String name, List<String> lore, boolean hidden) {
		return createCustomItem(id, material, name, lore, null, hidden);
	}
	
	public static CustomItem createCustomItem(String id, Material material, String name, List<String> lore, ClickRunnable onClick) {
		CustomItem item = new CustomItem(id, material, true);
		item.setClickMethod(onClick);
		editData(item, name, lore);
		return item;
	}
	
	public static CustomItem createCustomItem(String id, Material material, String name, List<String> lore, ClickRunnable onClick, boolean hidden) {
		CustomItem item = new CustomItem(id, material, hidden);
		item.setClickMethod(onClick);
		editData(item, name, lore);
		return item;
	}
	
	private static void editAttribute(ItemStack item, Attribute attr, AttributeModifier attrMod, String lore) {
		ItemMeta meta = item.getItemMeta();
		
		meta.addAttributeModifier(attr, attrMod);
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		
		List<Component> lores = meta.lore();
		lores.add(Component.text(""));
		lores.add(Utils.toComponent(lore));
		
		item.setItemMeta(meta);
	}
	
	private static ItemMeta editData(ItemStack item, String name, List<String> lore) {
		ItemMeta meta = item.getItemMeta();
		
		meta.displayName(Utils.toComponent(name));
		meta.setUnbreakable(true);
		
		meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		
		List<Component> lores = new ArrayList<Component>();
		for(String line : lore) {
			lores.add(Utils.toComponent(line));
		}
		meta.lore(lores);
		
		item.setItemMeta(meta);
		return meta;
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
	public final boolean hidden;
	
	private ClickRunnable click;
	private ClickRunnable rightClick;
	
	public CustomItem(String id, Material material, boolean hidden) {
		this(id, material, CustomItemType.ITEM, hidden);
	}
	
	public CustomItem(String id, Material material, CustomItemType itemType, boolean hidden) {
		super(material);
		this.id = id;
		this.itemType = itemType;
		this.hidden = hidden;
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
		
		if(thisMeta == null) return false;
		if(otherMeta == null) return false;
		return thisMeta.displayName().equals(otherMeta.displayName()) && getType().equals(item.getType());
	}
	
}
