package me.miccdev.miccgens.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

public class InventoryToBase64 {
    public static String toBase64(Inventory inventory) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);
       
            // Write the size of the inventory
            dataOutput.writeInt(inventory.getSize());
       
            // Save every element in the list
            for (int i = 0; i < inventory.getSize(); i++) {
                dataOutput.writeObject(inventory.getItem(i));
            }
       
            // Serialize that array
            dataOutput.close();
            return Base64Coder.encodeLines(outputStream.toByteArray());
        } catch (Exception e) {
            throw new IllegalStateException("Unable to save item stacks.", e);
        }    
    }

    public static Inventory fromBase64(String data) throws IOException {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
            BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);

            int inventorySize = 9;
            List<ItemStack> items = new ArrayList<>();
            int idx = 0;
            for(;;) {
            	try {
            		if(idx == 0) inventorySize = dataInput.readInt();
            		else items.add((ItemStack) dataInput.readObject());
            	} catch(EOFException e) {
            		break;
            	}
            	idx++;
            }
            
            Inventory inventory = Bukkit.getServer().createInventory(null, inventorySize);
            
            // Read the serialized inventory
            for (int i = 0; i < items.size(); i++) {
            	ItemStack item = items.get(i);
            	if(item == null) continue;
                inventory.setItem(i, item);
            }
            dataInput.close();
            return inventory;
        } catch (ClassNotFoundException e) {
            throw new IOException("Unable to decode class type.", e);
        }
    }
}