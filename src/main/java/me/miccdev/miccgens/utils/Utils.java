package me.miccdev.miccgens.utils;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

import net.md_5.bungee.api.ChatColor;

public class Utils {

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

}
