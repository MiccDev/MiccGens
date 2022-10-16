package me.miccdev.miccgens.utils;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.Inventory;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

import me.miccdev.miccgens.Main;
import net.md_5.bungee.api.ChatColor;

public class Utils {

	public static String Prefix = toColour("&e[&l&3MiccGens&r&e]:");
	public static String toColour(String txt) {
		return ChatColor.translateAlternateColorCodes('&', txt);
	}
	
	public static void savePlayerData(String uuid, Inventory inventory) {
		FileConfiguration config = Main.playerData.getConfig();
		String path = "players." + uuid + ".accessories";
		
		String encodedData = InventoryToBase64.toBase64(inventory);
		config.set(path, encodedData);
		Main.playerData.saveConfig();
	}
	
	public static Inventory loadPlayerData(String uuid) {
		FileConfiguration config = Main.playerData.getConfig();
		String path = "players." + uuid + ".accessories";

		if(!config.contains(path)) {
			config.set(path, "");
			Main.playerData.saveConfig();
			return null;
		}
		
		String data = config.getString(path);
		if(data.equals("")) return null;
		
		try {
			return InventoryToBase64.fromBase64(data);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static List<String> getFileData(String path, String file) {
		try {
			return Files.readLines(new File(path, file + ".txt"), Charsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static boolean isNumeric(String str) { 
		try {  
		    Double.parseDouble(str);
		    return true;
		} catch(NumberFormatException e){  
		    return false;  
		}  
	}

}
