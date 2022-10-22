package me.miccdev.miccgens.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;

import me.miccdev.miccgens.Main;

public class AnimatedBoard extends ScoreboardUtils {
	
	private static Map<UUID, Integer> TASKS = new HashMap<UUID, Integer>();
	
	public static int getID(UUID uuid) {
		return TASKS.get(uuid);
	}
	
	public static boolean hasID(UUID uuid) {
		return TASKS.containsKey(uuid);
	}
	
	public static void stopTask(UUID uuid) {
		Bukkit.getScheduler().cancelTask(TASKS.get(uuid));
		TASKS.remove(uuid);
	}
	
	private final Main plugin;
	private final UUID uuid;
	
	public AnimatedBoard(Main plugin, UUID uuid, String id, String displayName) {
		super(id, displayName);
		this.plugin = plugin;
		this.uuid = uuid;
	}
	
	public void setUpdate(Runnable onUpdate) {
		int taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, onUpdate, 0, 10);
		setID(taskID);
	}
	
	public void setID(int id) {
		TASKS.put(uuid, id);
	}
	
	public int getID() {
		return TASKS.get(uuid);
	}
	
	public boolean hasID() {
		return TASKS.containsKey(uuid);
	}
	
	public void stop() {
		Bukkit.getScheduler().cancelTask(getID());
		TASKS.remove(uuid);
	}

}
