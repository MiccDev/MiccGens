package me.miccdev.miccgens.utils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

import net.kyori.adventure.text.Component;
import net.md_5.bungee.api.ChatColor;

public class Utils {

	public static String TITLE = toColour("&3&lMiccGens");
	public static String Prefix = toColour("&e[" + TITLE + "&e]:");
	
	@SuppressWarnings("serial")
	public static Map<String, String> WORLDS = new HashMap<String, String>() {
		{ put("world", "Serenity Valley"); }
		{ put("grim_winds", "Grim Winds"); }
	};

	public static String toColour(String txt) {
		return ChatColor.translateAlternateColorCodes('&', txt);
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
	
	public static Component toComponent(String text) {
		return Component.text(toColour(text));
	}
	
	public static double round(double value, int precision) {
	    int scale = (int) Math.pow(10, precision);
	    return (double) Math.round(value * scale) / scale;
	}

}
