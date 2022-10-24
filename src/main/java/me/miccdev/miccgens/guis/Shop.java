package me.miccdev.miccgens.guis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.miccdev.miccgens.items.CustomItem;
import me.miccdev.miccgens.utils.Utils;
import net.kyori.adventure.text.Component;

public class Shop extends GUI {
	
	private static class ShopCost {
		public static ShopCost builder(String itemId) {
			return builder(itemId, 1);
		}
		
		public static ShopCost builder(String itemId, int count) {
			ShopCost r = new ShopCost();
			r.id = itemId;
			r.amount = count;
			return r;
		}
		
		@SuppressWarnings("unused")
		public static ShopCost builder(Material m) {
			return builder(m, 1);
		}
		
		public static ShopCost builder(Material m, int count) {
			ShopCost r = new ShopCost();
			r.id = m.name();
			r.amount = count;
			return r;
		}
		
		public String id;
		public int amount = 1;
	}

	private static class ShopResult {
		public static ShopResult builder(String itemId, int amount, ShopCost... cost) {
			ShopResult r = new ShopResult();
			r.resultId = itemId;
			r.amount = amount;
			r.cost = Arrays.asList(cost);
			return r;
		}
		
		public List<ShopCost> cost;
		public String resultId;
		public int amount = 1;
		
	}
	
	private class ShopHandler extends GUI {
		
		private ItemStack back;
		private List<ShopResult> items;
		
		public ShopHandler(String id, String title, List<ShopResult> items) {
			super(id, title, 5 * 9, true);
			this.items = items;
			
			back = CustomItem.createItem(Material.BARRIER, "&cBack", Arrays.asList());
			
			ItemStack empty = getEmptyItem();
			setContents(new ItemStack[] {
					empty, empty, empty, empty, empty, empty, empty, empty, empty,
					empty, null, null, null, null, null, null, null, empty,
					empty, null, null, null, null, null, null, null, empty,
					empty, null, null, null, null, null, null, null, empty,
					empty, empty, empty, empty, back, empty, empty, empty, empty,
			});
			
			addItems(this.items);
		}
		
		@Override
		public void onItemClick(Player player, ItemStack item) {
			if(back.equals(item)) {
				player.openInventory(GUI.getGui("shop").getInventory());
			}
			
			buy(player, item);
		}
		
		private void buy(Player player, ItemStack item) {
			Inventory pInv = player.getInventory();
			for(ShopResult res : items) {
				ItemStack r = CustomItem.getAnyItem(res.resultId).clone();
				r.setAmount(res.amount);
				
				if(CustomItem.equals(r, item)) {
					List<ItemStack> costItems = res.cost.stream().map(c -> {
						ItemStack clone = CustomItem.getAnyItem(c.id).clone();
						clone.setAmount(c.amount);
						return clone;
					}).collect(Collectors.toList());
					List<ItemStack> contains = costItems.stream().filter(i -> {
						return pInv.containsAtLeast(i, i.getAmount());
					}).collect(Collectors.toList());
					
//					Bukkit.getLogger().info("" + costItems + " " + contains);
					
					if(contains.size() >= res.cost.size()) {
						ItemStack[] remove = new ItemStack[costItems.size()];
						costItems.toArray(remove);
						pInv.removeItem(remove);
						
						pInv.addItem(r);
						player.sendMessage(Utils.toComponent(Utils.Prefix + " &aSuccessfully bought item!"));
						player.playSound(player, Sound.ENTITY_PLAYER_LEVELUP, 0.5f, 1);
					} else {
						player.sendMessage(Utils.toComponent(Utils.Prefix + " &cYou do not have the resources to buy this item!"));
						player.playSound(player, Sound.ENTITY_VILLAGER_NO, 0.5f, 1);
					}
					break;
				}
			}
		}
		
		public ShopHandler addItems(List<ShopResult> results) {
			if(results == null) return this;
			for(ShopResult res : results) {
				if(res == null) continue;
				addItem(res);
			}
			
			return this;
		}
		
		@SuppressWarnings("unused")
		public ShopHandler addItem(String itemId, int amount, ShopCost... cost) {
			ShopResult res = new ShopResult();
			res.resultId = itemId;
			res.amount = amount;
			
			res.cost = Arrays.asList(cost);
			
			return addItem(res);
		}
		
		@SuppressWarnings("deprecation")
		public ShopHandler addItem(ShopResult res) {
			ItemStack i = CustomItem.getAnyItem(res.resultId).clone();
			i.setAmount(1);
			
			ItemMeta meta = i.getItemMeta();
			
			List<Component> lores = meta.lore();
			lores.add(Utils.toComponent(""));
			lores.add(Utils.toComponent("&7Cost:"));
			for(ShopCost c : res.cost) {
				if(!CustomItem.hasAnyItem(c.id)) continue;
				ItemStack item = CustomItem.getAnyItem(c.id);
				ItemMeta iMeta = item.getItemMeta();
				String name = !iMeta.getDisplayName().equals("") ? iMeta.getDisplayName() : Utils.decapitalize(item.getType().toString());
				lores.add(Utils.toComponent("  &7" + c.amount + " " + name));
			}
			meta.lore(lores);
			i.setItemMeta(meta);
			
			getInventory().addItem(i);
			return this;
		}
		
	}
	
	private ItemStack weapons;
	private ItemStack armour;
	private ItemStack accessories;
	
	@SuppressWarnings({ "serial", "unused" })
	public Shop() {
		super("shop", "&e[&3&lShop&r&e]", 3 * 9, true);
		
		ItemStack empty = getEmptyItem();
		weapons = CustomItem.createItem(Material.IRON_SWORD, "&8Weapons", Arrays.asList("&bOpens the weapons shop."));
		armour = CustomItem.createItem(Material.IRON_CHESTPLATE, "&7Armour", Arrays.asList("&bOpens the armour shop."));
		accessories = CustomItem.createItem(Material.TOTEM_OF_UNDYING, "&7Accessories", Arrays.asList("&bOpens the accessories shop.", "&c&lCOMING SOON!!!"));
		
		setContents(new ItemStack[] {
			empty, empty, empty, empty, empty, empty, empty, empty, empty,
			empty, empty, weapons, empty, armour, empty, accessories, empty, empty,
			empty, empty, empty, empty, empty, empty, empty, empty, empty,
		});
		
		String weaponsTitle = "&e[&3&lWeapons&r&e]";
		String armourTitle = "&e[&3&lArmour&r&e]";
		String accessoriesTitle = "&e[&3&lAccessories&r&e]";
		
		new ShopHandler("shop-weapons-world", weaponsTitle, new ArrayList<ShopResult>() {{
			add(ShopResult
					.builder("charcoal_sword", 1, 
							ShopCost.builder(Material.CHARCOAL, 64),
							ShopCost.builder("charcoal_spear", 3)
					)
			);
			add(ShopResult
					.builder("charcoal_spear", 1, 
							ShopCost.builder(Material.CHARCOAL, 64),
							ShopCost.builder("wheat_spear")
					)
			);
			add(ShopResult
					.builder("wheat_spear", 1, 
							ShopCost.builder(Material.WHEAT, 80)
					)
			);
			add(ShopResult
					.builder("kelp_rod", 1, 
							ShopCost.builder(Material.KELP, 64)
					)
			);
//			add(ShopResult
//					.builder("kelp_bow", 1, 
//							ShopCost.builder("kelp", 64),
//							ShopCost.builder("kelp_rod", 4)
//					)
//			);
		}});
		new ShopHandler("shop-armour-world", armourTitle, new ArrayList<ShopResult>() {{
			add(ShopResult
					.builder("kelp_helmet", 1, 
							ShopCost.builder(Material.KELP, 64)
					)
			);
			add(ShopResult
					.builder("sweet_berry_chestplate", 1, 
							ShopCost.builder(Material.SWEET_BERRIES, 128)
					)
			);
			add(ShopResult
					.builder("sweet_berry_boots", 1, 
							ShopCost.builder(Material.SWEET_BERRIES, 128)
					)
			);
			add(ShopResult
					.builder("wheat_leggings", 1, 
							ShopCost.builder(Material.WHEAT, 128)
					)
			);
			add(ShopResult
					.builder("azalea_helmet", 1, 
							ShopCost.builder("azalea_flower", 80)
					)
			);
			add(ShopResult
					.builder("azalea_chestplate", 1, 
							ShopCost.builder("azalea_flower", 80)
					)
			);
			add(ShopResult
					.builder("azalea_leggings", 1, 
							ShopCost.builder("azalea_flower", 80)
					)
			);
			add(ShopResult
					.builder("azalea_boots", 1, 
							ShopCost.builder("azalea_flower", 80)
					)
			);
		}});
	}

	@Override
	public void onItemClick(Player player, ItemStack item) {
		String worldName = player.getWorld().getName();
		String displayName = Utils.WORLDS.get(worldName);
		if(weapons.equals(item)) {
			if(GUI.getGui("shop-weapons-" + worldName) == null) {
				player.sendMessage(Utils.toComponent("&c&lTHERE IS NO WEAPONS SHOP FOR " + displayName.toUpperCase()));
				return;
			}
			player.openInventory(GUI.getGui("shop-weapons-" + worldName).getInventoryClone());
		} else if(armour.equals(item)) {
			if(GUI.getGui("shop-armour-" + worldName) == null) {
				player.sendMessage(Utils.toComponent("&c&lTHERE IS NO ARMOUR SHOP FOR " + displayName.toUpperCase()));
				return;
			}
			player.openInventory(GUI.getGui("shop-armour-" + worldName).getInventoryClone());
		} else if(accessories.equals(item)) {
			player.sendMessage(Utils.toComponent("&c&lACESSORIES SHOP COMING SOON."));
//			player.openInventory(GUI.getGui("shop-weapons").getInventory());
		}
	}
}
