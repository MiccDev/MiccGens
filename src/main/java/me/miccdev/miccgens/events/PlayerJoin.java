package me.miccdev.miccgens.events;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.miccdev.miccgens.Main;
import me.miccdev.miccgens.guis.Accessories;
import me.miccdev.miccgens.items.CustomItem;
import me.miccdev.miccgens.schedules.PlayerStats;
import me.miccdev.miccgens.utils.Utils;
import net.kyori.adventure.text.Component;

public class PlayerJoin extends CustomEvent {
	
	public PlayerJoin(Main plugin) {
		super(plugin);
	}

	@EventHandler	
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		Location position = new Location(Bukkit.getWorld("world"), 53.5, -43, -19.5, -88.8f, 0.9f);
		player.teleport(position);
		
		player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, Integer.MAX_VALUE, 255, false, false));
		event.joinMessage(Component.text(Utils.toColour("&e[&3&lMiccGens&r&e]: &r&bWelcome, " + player.getName() + " &bto the server!")));
		
		player.getInventory().setItem(8, CustomItem.getItem("menu"));
		
		Accessories.getAccessoryInventory(player.getUniqueId().toString());
		PlayerStats.registerNewStats(player, new PlayerStats(getPlugin()));
	}
	
}
