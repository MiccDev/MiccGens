package me.miccdev.miccgens.generators;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class SpawnerData {
	
	public static SpawnerData coal = new SpawnerData(Material.COAL);
	
	private ItemStack spawns;
	private int count;
	private long speed;
	
	public SpawnerData(ItemStack spawns) {
		this(spawns, spawns.getAmount());
	}
	
	public SpawnerData(ItemStack spawns, int count) {
		this.spawns = spawns;
		this.spawns.setAmount(count);
	}
	
	public SpawnerData(Material spawns, int count) {
		this(spawns, count, 1L);
	}
	
	public SpawnerData(Material spawns, long speed) {
		this(spawns, 1, speed);
	}
	
	public SpawnerData(Material spawns) {
		this(spawns, 1, 1L);
	}
	
	public SpawnerData(Material spawns, int count, long speed) {
		this.spawns = new ItemStack(spawns, count);
		this.count = count;
		this.speed = speed;
	}

	/*
	 * 
	 *    GETTERS
	 *       &
	 *    SETTERS
	 *
	 */

	public ItemStack getSpawns() {
		return spawns;
	}

	public SpawnerData setSpawns(ItemStack spawns) {
		this.spawns = spawns;
		return this;
	}

	public int getCount() {
		return count;
	}

	public SpawnerData setCount(int count) {
		this.count = count;
		this.spawns.setAmount(count);
		return this;
	}
	
	public long getSpeed() {
		return speed;
	}

	public SpawnerData setSpeed(long speed) {
		this.speed = speed;
		return this;
	}
	
}
