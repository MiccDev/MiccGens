package me.miccdev.miccgens.items;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.attribute.AttributeModifier.Operation;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;

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
		
		overworldItems();
		
		CustomItem.config = Main.itemData.getConfig();
		initAllItems();
	}
	
	public static void overworldItems() {
		/*************************
		*  RESOURCES
		*************************/
		
		createCustomItem(
				"azalea_flower",
				Material.ALLIUM,
				"&5Azalea Flower",
				Arrays.asList(
						"&dA flower picked right from a magical forest floor.",
						"&dDo you smell that?"
				),
				false
		);
		
		/*************************
		*  WEAPONS
		*************************/
		
		createWeapon(
				"charcoal_sword", 
				Material.NETHERITE_SWORD, 
				"&8Charcoal Sword", 
				Arrays.asList(
					"&8Forged from deep within the 'Dark Forest'."
				),
				3f
		);
		
		Weapon charcoalSpear = createWeapon(
				"charcoal_spear", 
				Material.TIPPED_ARROW, 
				"&8Charcoal Spear", 
				Arrays.asList(
					"&7Throw them at your enemies."
				),
				2f
		);
		editDataPotion(charcoalSpear, PotionType.WEAKNESS);
		
		createWeapon(
				"wheat_spear",
				Material.SPECTRAL_ARROW,
				"&6Wheat Spear",
				Arrays.asList(
						"&eThe farmers are gonna have your hat for this one."
				),
				1.5f
		);
		
		createWeapon(
				"kelp_rod",
				Material.BAMBOO,
				"&2Kelp Rod",
				Arrays.asList(
						"&aBONK"
				),
				1f,
				2f
		);
		
		
		/*************************
		*  ARMOURS
		*************************/
		
		Armour kelpHelmet = createArmour(
				"kelp_helmet",
				Material.PLAYER_HEAD,
				"&2Kelp Helemt",
				Arrays.asList(
						"&aNice hair."
				),
				2f
		);
		editDataHead(kelpHelmet, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTEzNmVlZjgwMWIzMGY4Y2I2Y2Q2ZWQ1MzI1MDUyN2M5MjIwNWFkZWQxNDc3NjIwYTI3ZjVlNjJiYjg4YjA2MCJ9fX0=");
		
		Armour sweetBerryChestplate = createArmour(
				"sweet_berry_chestplate",
				Material.LEATHER_CHESTPLATE,
				"&4Sweet Berry Chestplate",
				Arrays.asList(
						"&cThorn-away"
				),
				4f
		);
		editDataDye(sweetBerryChestplate, DyeColor.RED.getColor());
		
		Armour sweetBerryBoots = createArmour(
				"sweet_berry_boots",
				Material.LEATHER_BOOTS,
				"&4Sweet Berry Boots",
				Arrays.asList(
						"&cThorn-away",
						"&cNo more pesky spikes."
				),
				3f
		);
		editDataDye(sweetBerryBoots, DyeColor.RED.getColor());
		
		createArmour(
				"wheat_leggings",
				Material.GOLDEN_LEGGINGS,
				"&6Wheat Leggings",
				Arrays.asList(
						"&eReally faught them farmers off..."
				),
				3f,
				1f
		);
		
		Armour azaleaHelmet = createArmour(
				"azalea_helmet",
				Material.PLAYER_HEAD,
				"&5Azalea Helemt",
				Arrays.asList(
						"&dA moving bush?..."
				),
				2f
		);
		editDataHead(azaleaHelmet, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmI1Y2UwOWIyY2ZlMjM2MWZjN2VkMDEwZjYyMmE3OGNjZTJhODY5Y2YyYTFjNzNkNDE4NWZjYTU3MzBlYWU4YSJ9fX0=");
		
		Armour azaleaChestplate = createArmour(
				"azalea_chestplate",
				Material.LEATHER_CHESTPLATE,
				"&5Azalea Chestplate",
				Arrays.asList(
						"&dSewed together with grass"
				),
				2f
		);
		editDataDye(azaleaChestplate, DyeColor.PINK.getColor());
		
		Armour azaleaLeggings = createArmour(
				"azalea_leggings",
				Material.LEATHER_LEGGINGS,
				"&5Azalea Leggings",
				Arrays.asList(
						"&dGreat smelling flowers!"
				),
				2f
		);
		editDataDye(azaleaLeggings, DyeColor.PINK.getColor());
		
		Armour azaleaBoots = createArmour(
				"azalea_boots",
				Material.LEATHER_BOOTS,
				"&5Azalea Boots",
				Arrays.asList(
						"&dThe power of the forest rebuilds you."
				),
				2f
		);
		editDataDye(azaleaBoots, DyeColor.PINK.getColor());
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
			case ARMOUR:
				float def = config.contains(lastString + ".def") ? (float) ((Double) config.getDouble(lastString + ".def")).floatValue() : 1f;
				createArmour(lastString, material, displayName, lore, def);
				break;
		}
	}
	
	public static Accessory createAccessory(String id, Material material, String name, List<String> lore) {
		Accessory item = new Accessory(id, material);
		editData(item, name, lore);
		return item;
	}
	
	public static Weapon createWeapon(String id, Material material, String name, List<String> lore, float damage) {
		return createWeapon(id, material, name, lore, damage, 0f, 0f);
	}
	
	public static Weapon createWeapon(String id, Material material, String name, List<String> lore, float damage, float speed) {
		return createWeapon(id, material, name, lore, damage, speed, 0f);
	}
	
	public static Weapon createWeapon(String id, Material material, String name, List<String> lore, float damage, float speed, float kb) {
		Weapon item = new Weapon(id, material, damage);
		editData(item, name, lore);
		editAttribute(item, 
				Attribute.GENERIC_ATTACK_DAMAGE,
				new AttributeModifier("generic.attackDamage", damage, Operation.ADD_NUMBER),
				"&7Damage: &2" + damage
		);
		if(speed > 0f) {
			editAttribute(item, 
					Attribute.GENERIC_ATTACK_SPEED,
					new AttributeModifier("generic.attackSpeed", speed, Operation.ADD_NUMBER),
					"&7Attack speed: &2" + damage
			);
		}
		
		if(kb > 0f) {
			editAttribute(item, 
					Attribute.GENERIC_ATTACK_KNOCKBACK,
					new AttributeModifier("generic.attackKnockback", kb, Operation.ADD_NUMBER),
					"&7Knockback: &2" + damage
			);
		}
		
		return item;
	}
	
	public static Armour createArmour(String id, Material material, String name, List<String> lore, float def) {
		return createArmour(id, material, name, lore, def, 0f);
	}
	
	public static Armour createArmour(String id, Material material, String name, List<String> lore, float def, float toughness) {
		Armour item = new Armour(id, material, def, toughness);
		editData(item, name, lore);
		editAttribute(item, 
				Attribute.GENERIC_ARMOR,
				new AttributeModifier("generic.armor", def, Operation.ADD_NUMBER),
				"&7Defense: &2" + def
		);
		if(toughness > 0f) {
			editAttribute(item, 
					Attribute.GENERIC_ARMOR_TOUGHNESS,
					new AttributeModifier("generic.armorToughness", toughness, Operation.ADD_NUMBER),
					"&7Toughness: &2" + def
			);
		}
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
		
		List<Component> lores = meta.lore();
		lores.add(Component.text(""));
		lores.add(Utils.toComponent(lore));
		meta.lore(lores);
		
		item.setItemMeta(meta);
	}
	
	private static ItemMeta editData(ItemStack item, String name, List<String> lore) {
		ItemMeta meta = item.getItemMeta();
		
		meta.displayName(Utils.toComponent(name));
		meta.setUnbreakable(true);
		
		/**********************
		* Hide all item flags
		**********************/
		meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		meta.addItemFlags(ItemFlag.HIDE_DYE);
		meta.addItemFlags(ItemFlag.HIDE_PLACED_ON);
		
		List<Component> lores = new ArrayList<Component>();
		for(String line : lore) {
			lores.add(Utils.toComponent(line));
		}
		meta.lore(lores);
		
		item.setItemMeta(meta);
		return meta;
	}
	
	private static ItemMeta editDataPotion(ItemStack item, PotionType type) {
		PotionMeta meta = (PotionMeta) item.getItemMeta();
		
		meta.setBasePotionData(new PotionData(type));
		
		item.setItemMeta(meta);
		return meta;
	}
	
	private static ItemMeta editDataDye(ItemStack item, Color c) {
		LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
		
		meta.setColor(c);
		
		item.setItemMeta(meta);
		return meta;
	}
	
	private static ItemMeta editDataHead(ItemStack item, String texture) {
		SkullMeta meta = (SkullMeta) item.getItemMeta();
		
		PlayerProfile plrProf = Bukkit.createProfile(UUID.randomUUID(), null);
		plrProf.setProperty(new ProfileProperty("textures", texture));
		meta.setPlayerProfile(plrProf);
		
		item.setItemMeta(meta);
		return meta;
	}
	
	
	public static boolean equals(ItemStack self, ItemStack other) {
		if(self == null) return false;
		if(other == null) return false;
		return self.getType().equals(other.getType()) && self.getItemMeta().displayName().equals(other.getItemMeta().displayName());
	}
	
	public static CustomItem getItem(String id) {
		return CustomItem.allItems.containsKey(id) ? CustomItem.allItems.get(id) : null;
	}
	
	public static boolean hasItem(String id) {
		return CustomItem.allItems.containsKey(id);
	}
	
	public static ItemStack getAnyItem(String id) {
		return hasItem(id) ? getItem(id) : Material.getMaterial(id) != null ? new ItemStack(Material.getMaterial(id)) : null;
	}
	
	public static boolean hasAnyItem(String id) {
		return getAnyItem(id) != null;
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
		if(item == null) return false;
		ItemMeta thisMeta = getItemMeta();
		ItemMeta otherMeta = item.getItemMeta();
		
		if(thisMeta == null) return false;
		if(otherMeta == null) return false;
		return thisMeta.displayName().equals(otherMeta.displayName()) && getType().equals(item.getType());
	}
	
}
