package me.miccdev.miccgens.guis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.miccdev.miccgens.Main;
import me.miccdev.miccgens.utils.Utils;
import net.kyori.adventure.text.Component;

public class GUI {
	
	public static Map<String, GUI> allGuis = new HashMap<String, GUI>();
	
	public static void init() {
		new MainMenu();
		new Shop();
	}
	
	public static GUI getGui(String id) {
		return GUI.allGuis.containsKey(id) ? GUI.allGuis.get(id) : null;
	}
	
	private final String id;
	private final Inventory inventory;
	
	private List<GUI> pages;
	private String title;
	private int slots;
	private final boolean clickable;
	
	private ItemStack[] contents;
	
	public GUI(String id, String title, int slots) {
		this(id, title, slots, false, true);
	}
	
	public GUI(String id, String title, int slots, boolean clickable) {
		this(id, title, slots, clickable, true);
	}
	
	public GUI(String id, String title, int slots, boolean clickable, boolean addToList) {
		this.id = id;
		this.title = title;
		this.slots = slots;
		this.clickable = clickable;
		this.pages = new ArrayList<GUI>();
		this.contents = new ItemStack[slots];
		this.inventory = Bukkit.createInventory(null, slots, Utils.toComponent(title));
		
		if(addToList)
			GUI.allGuis.put(id, this);
	}
	
	public GUI createPage(ItemStack[] items) {
		GUI page = new GUI(id + (pages.size() + 1), title, slots);
		page.setContents(items);
		pages.add(page);
		return page;
	}
	
	public void switchPage(int page) {
		GUI selectedPage = pages.get(page);
		inventory.setContents(selectedPage.getContents());
	}
	
	public void back() {
		inventory.setContents(getContents());
	}
	
	public ItemStack getEmptyItem() {
		return createInventoryItem(Material.GRAY_STAINED_GLASS_PANE, "", "");
	}
	
	public ItemStack createInventoryItem(Material material, String name, String... lore) {
		ItemStack item = new ItemStack(material);
		ItemMeta meta = item.getItemMeta();
		
		meta.displayName(Utils.toComponent(name));
		
		List<Component> lores = new ArrayList<Component>();
		for(String line : lore) {
			lores.add(Utils.toComponent(line));
		}
		meta.lore(lores);
		
		item.setItemMeta(meta);
		return item;
	}
	
	public void onItemClick(Player player, ItemStack item) {};
	public void onOpen(Main plugin, Player player) {};
	public void onClose(Main plugin, Player player) {};

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getSlots() {
		return slots;
	}

	public void setSlots(int slots) {
		this.slots = slots;
	}
	
	public void setContents(ItemStack[] contents) {
		this.contents = contents;
		inventory.setContents(this.contents);
	}
	
	public ItemStack[]  getContents() {
		return contents;
	}

	public Inventory getInventory() {
		return inventory;
	}
	
	public Inventory getInventoryClone() {
		return getInventoryClone(this.inventory.getContents());
	}
	
	public Inventory getInventoryClone(ItemStack[] items) {
		return getInventoryClone(null, items);
	}
	
	public Inventory getInventoryClone(Player player) {
		return getInventoryClone(player, this.inventory.getContents());
	}
	
	public Inventory getInventoryClone(Player player, ItemStack[] items) {
		Inventory inv = Bukkit.createInventory(player, slots, Utils.toComponent(title));
		inv.setContents(items);
		return inv;
	}
	
	public boolean isClickable() {
		return this.clickable;
	}

}
