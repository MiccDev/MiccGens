package me.miccdev.miccgens.schedules;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import me.miccdev.miccgens.Main;
import me.miccdev.miccgens.files.DataManager;
import me.miccdev.miccgens.utils.Utils;
import net.kyori.adventure.text.Component;

public class PlayerStats extends Schedule {

	private static Map<String, PlayerStats> stats = new HashMap<String, PlayerStats>();
	
	public static void registerNewStats(Player player, PlayerStats stats) {
		stats.setOwner(player);
		String uuid = player.getUniqueId().toString();
		PlayerStats.stats.put(uuid, stats);
	}
	
	public static void deleteStat(Player player) {
		String uuid = player.getUniqueId().toString();
		if(PlayerStats.stats.get(uuid) == null) return;
		
		int taskId = PlayerStats.stats.get(uuid).getTaskId();
		Bukkit.getScheduler().cancelTask(taskId);
		PlayerStats.stats.remove(uuid);
	}
	
	public static int getMaxHealth(String uuid) {
		DataManager playerData = Main.playerData;
		FileConfiguration config = playerData.getConfig();
		String path = "players." + uuid + ".maxHp";
		
		if(!config.contains(path)) {
			config.set(path, 10);
			playerData.saveConfig();
		}
		
		return config.getInt(path);
	}
	
	public static PlayerStats getPlayerStats(String uuid) {
		return stats.get(uuid);
	}
	
	private Player owner;
	private float health;
	
	private int maxHealth;
	
	public PlayerStats(Main plugin) {
		this(plugin, null);
	}
	
	public PlayerStats(Main plugin, Player owner) {
		super(plugin, 15L);
		this.owner = owner;
		
		if(owner != null) {
			String uuid = owner.getUniqueId().toString();
			maxHealth = PlayerStats.getMaxHealth(uuid);
			health = maxHealth;
		}
	}
	
	@Override
	public void run() {
//		String uuid = owner.getUniqueId().toString();
		
		String formattedHealth = new DecimalFormat("#").format(health);
		owner.sendActionBar(Component.text(Utils.toColour("&cHealth: " + formattedHealth + "/" + maxHealth)));
	}

	public float getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
		if(this.health > maxHealth) this.health = maxHealth;
	}
	
	public void damage(double amount) {
		this.health -= amount;
		if(this.health <= 0) {
			this.owner.setHealth(0);
			this.health = maxHealth;
			this.owner.teleport(this.owner.getWorld().getSpawnLocation());
		}
	}
	
	public void heal(double amount) {
		this.health += amount;
		if(this.health > maxHealth) this.health = maxHealth;
	}

	public Player getOwner() {
		return owner;
	}

	public void setOwner(Player owner) {
		this.owner = owner;
		maxHealth = PlayerStats.getMaxHealth(owner.getUniqueId().toString());
		heal(maxHealth);
	}
	
}
