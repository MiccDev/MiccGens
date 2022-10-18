package me.miccdev.miccgens;

import java.util.List;

import org.bukkit.plugin.java.JavaPlugin;

import me.miccdev.miccgens.commands.CustomCommand;
import me.miccdev.miccgens.events.CustomEvent;
import me.miccdev.miccgens.guis.GUI;
import me.miccdev.miccgens.items.CustomItem;
import me.miccdev.miccgens.schedules.Spawner;
import me.miccdev.miccgens.utils.DataManager;

public class Main extends JavaPlugin {

	public static DataManager playerData;
	public static DataManager itemData;
	
	public List<String> itemList;
	
	@Override
	public void onEnable() {
		getLogger().info("Enabling main module");
		
		getLogger().info("Obtaining config data.");
		Main.itemData = new DataManager(this, "items");
		Main.playerData = new DataManager(this, "player-data");
		saveDefaultConfig();
		getLogger().info("Finished obtaining config data.");
		
		getLogger().info("Enabling 'items' module");
		CustomItem.init();
		
		getLogger().info("Enabling 'spawners' module");
		Spawner.init(this, getConfig());
		
		getLogger().info("Enabling 'guis' module");
		GUI.init();
		
		getLogger().info("Enabling 'commands' module");
		CustomCommand.init(this);
		
		getLogger().info("Enabling 'events' module");
		CustomEvent.init(this);
		
		getLogger().info("Done!");
	}
	
	@Override
	public void onDisable() {
		getLogger().info("Disabling all modules!");
	}
	
}
