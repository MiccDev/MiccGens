package me.miccdev.miccgens.events;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import me.miccdev.miccgens.Main;
import me.miccdev.miccgens.items.CustomItem;
import me.miccdev.miccgens.items.weapons.CustomWeapon;
import me.miccdev.miccgens.schedules.PlayerStats;

public class EntityDamageEntity extends CustomEvent {

	public EntityDamageEntity(Main plugin) {
		super(plugin);
	}
/*
	@EventHandler
	public void onEntityDamage(EntityDamageEvent e) {
		Entity entity = e.getEntity();

		if(e.getCause().equals(DamageCause.CONTACT)) {
			e.setCancelled(true);
			return;
		}

		if(e.getCause().equals(DamageCause.ENTITY_ATTACK)) return;
		if(entity instanceof Player) {
			handlePlayerFunctions((Player) entity, e.getDamage());
		}
	}
*/
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onEntityDamageEntity(EntityDamageByEntityEvent e) {
		Entity entity = e.getEntity();
		Entity damagerEntity = e.getDamager();
		
		if(!(entity instanceof Player)) return;
		if(!(damagerEntity instanceof Player)) return;
		Player damager = (Player) damagerEntity;
		
		ItemStack damagersItem = damager.getItemInHand();
		
		if(!CustomItem.isCustomItem(damagersItem)) return;
		if(!(((CustomItem) damagersItem) instanceof CustomWeapon)) return;
		CustomWeapon weapon = (CustomWeapon) damagersItem;
		
		handlePlayerFunctions((Player) entity, weapon.getDamage());
	}
	
	public void handlePlayerFunctions(Player plr, double damage) {
		String uuid = plr.getUniqueId().toString();
		PlayerStats stats = PlayerStats.getPlayerStats(uuid);
		
		stats.damage(damage);
	}
	
}
