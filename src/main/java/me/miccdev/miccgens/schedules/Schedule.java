package me.miccdev.miccgens.schedules;

import org.bukkit.Bukkit;

import me.miccdev.miccgens.Main;

public abstract class Schedule implements Runnable {

	private Main plugin;
	private long speed;
	private final int taskId;

	public Schedule(Main plugin, long speed) {
		this.plugin = plugin;
		this.speed = speed;
		this.taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, this, speed, speed);
	}

	public Main getPlugin() {
		return plugin;
	}

	public void setPlugin(Main plugin) {
		this.plugin = plugin;
	}

	public long getSpeed() {
		return speed;
	}

	public void setSpeed(long speed) {
		this.speed = speed;
	}
	
	public int getTaskId() {
		return taskId;
	}
	
}
