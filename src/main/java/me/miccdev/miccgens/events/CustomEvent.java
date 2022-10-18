package me.miccdev.miccgens.events;

import org.bukkit.event.Listener;

import me.miccdev.miccgens.Main;

public class CustomEvent implements Listener {

	public static void init(Main plugin) {
		new InventoryOpen(plugin);
		new InventoryClose(plugin);
		new InventoryClick(plugin);
		
		new PlayerJoin(plugin);
		new PlayerLeave(plugin);
		new PlayerInteract(plugin);
	}
	
	private Main plugin;
	
	public CustomEvent(Main plugin) {
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	public Main getPlugin() {
		return plugin;
	}

	public void setPlugin(Main plugin) {
		this.plugin = plugin;
	}
	
}
