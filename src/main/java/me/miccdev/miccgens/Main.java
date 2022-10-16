package me.miccdev.miccgens;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.miccdev.miccgens.commands.CustomCommand;
import me.miccdev.miccgens.events.CustomEvent;
import me.miccdev.miccgens.files.DataManager;
import me.miccdev.miccgens.guis.Accessories;
import me.miccdev.miccgens.guis.GUI;
import me.miccdev.miccgens.items.CustomItem;
import me.miccdev.miccgens.schedules.PlayerStats;
import me.miccdev.miccgens.schedules.Spawner;
import me.miccdev.miccgens.utils.Utils;

public class Main extends JavaPlugin {

	public static DataManager playerData;
	
	public List<String> itemList;
	
	@Override
	public void onEnable() {
		getLogger().info("Enabling main module");
		getLogger().info("Obtaining player data.");
		Main.playerData = new DataManager(this, "player-data");
		saveDefaultConfig();
		
		getLogger().info("Enabling 'items' module");
		itemList = Utils.getFileData(getDataFolder().getAbsolutePath(), "items");
		CustomItem.init();
		
		getLogger().info("Enabling 'spawners' module");
		Spawner.init(this, getConfig());
		
		getLogger().info("Enabling 'guis' module");
		GUI.init();
		
		getLogger().info("Enabling 'commands' module");
		CustomCommand.init(this);
		
		getLogger().info("Enabling 'events' module");
		CustomEvent.init(this);
		
		for(Player plr : Bukkit.getOnlinePlayers()) {
			Accessories.getAccessoryInventory(plr.getUniqueId().toString());
			PlayerStats.registerNewStats(plr, new PlayerStats(this));
			
			plr.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, Integer.MAX_VALUE, 255, false, false));
		}
		
		getLogger().info("Done!");
	}
	
	@Override
	public void onDisable() {
		for(Player plr : Bukkit.getOnlinePlayers()) {
			String uuid = plr.getUniqueId().toString();
			Utils.savePlayerData(uuid, Accessories.getAccessoryInventory(uuid).getInventory());
		}
	}
	
}
