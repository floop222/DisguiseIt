package de.TrustedCreeper.DisguiseIt;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.mcstats.metrics.Metrics;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;

public class DisguiseIt extends JavaPlugin {
	public void onEnable() {
		registerEvents();
		loadConfig();
		DisguiseItCommands command = new DisguiseItCommands(this);
		getCommand("di").setExecutor(command);
		
		if(!Bukkit.getPluginManager().isPluginEnabled("WorldEdit")) {
			Bukkit.getConsoleSender().sendMessage(getPrefix() + Messages.WORLDEDIT_IS_MISSING.get());
			Bukkit.getPluginManager().disablePlugin(this);
		} else { worldedit = true; }		
		
		if(getInstance().getConfig().getBoolean("config.use_metrics")) {
			try {
			    Metrics metrics = new Metrics(this);
			    metrics.start();
			    Bukkit.getConsoleSender().sendMessage(getPrefix() + "§2Plugin Metrics enabled!");
			} catch (IOException e) {
				Bukkit.getConsoleSender().sendMessage(getPrefix() + "§cError: Cant enable Plugin Metrics!");
				e.printStackTrace();
			}
		} else {
			Bukkit.getConsoleSender().sendMessage(getPrefix() + "§cPlugin Metrics are disabled!");
		}
		
		updateInSec = getConfig().getInt("config.update_blocks");
		
		if(Bukkit.getOnlinePlayers().length > 0) sendChanges();
	}
	
	private DisguiseItEventHandler events;
	public void registerEvents() {
		this.events = new DisguiseItEventHandler(this);
	}
	
	File config = new File("plugins/DisguiseIt/config.yml");
	public void loadConfig() {
		if(!config.exists()) {
			getConfig().set("config.use_metrics", true);
			getConfig().set("config.update_blocks", 1);
			getConfig().set("config.allow_break_disguised_blocks", false);
			for(Messages message : Messages.values()) 
				getConfig().set(message.getConfigPath(), message.getDefaultMessage());
			
			getConfig().options().copyDefaults(true);
			saveConfig();
		}
	}
	
	public static boolean worldedit = false;
	public int updateInSec = 1;
	public int taskid = 0;
	public static List<String> editmode = new ArrayList<String>();
	
	public static boolean existWorldEdit() { return worldedit; }
	
	
	
	public static WorldEditPlugin getWorldEdit() {
		if(existWorldEdit()) return (WorldEditPlugin) Bukkit.getPluginManager().getPlugin("WorldEdit");
		return null;
	}
	
	public static DisguiseIt getInstance() {
		return (DisguiseIt) Bukkit.getPluginManager().getPlugin("DisguiseIt");
	}
	
	public static String getPrefix() {
		return Messages.PREFIX.get() + " ";
	}
	
	public HashMap<Location, String> getDisguises() {
		HashMap<Location, String> disguises = new HashMap<Location, String>();
		List<String> raw = getConfig().getStringList("disguises");
		if(!raw.isEmpty()) {
			for(String i : raw) {
				disguises.put(MaterialSerializer.getLocationByRaw(i), MaterialSerializer.getMaterialSerializationByRaw(i));
			}
		}
		return disguises;
	} 
	
	public void cancelTask() { 
		if(taskid != 0) {
			Bukkit.getScheduler().cancelTask(taskid); 
			taskid = 0;
		}
	}
	
	public void sendChanges() {
		taskid = Bukkit.getScheduler().scheduleSyncRepeatingTask(getInstance(), new Runnable() {
			
			@Override
			public void run() {
				if(Bukkit.getOnlinePlayers().length > 0) {
					DisguiseIt.getInstance().sendAllChanges();
				} else {
					DisguiseIt.getInstance().cancelTask();
				}
			}
			
		}, updateInSec * 20, updateInSec * 20);
	}
	
	public boolean containsDisguises(Location loc) {
		if(getDisguises().size() == 0) return false;
		for(Location l : DisguiseIt.getInstance().getDisguises().keySet()) {
			if(l.equals(loc)) return true;
		}
		return false;
	}
	
	
	public void sendAllChanges() {
		if(getDisguises().size() == 0) return;
		for(Player p : Bukkit.getOnlinePlayers()) {
			for(Location l : DisguiseIt.getInstance().getDisguises().keySet()) {
				if(editmode.contains(p.getName())) {
					p.sendBlockChange(l, Material.DIAMOND_BLOCK.getId(), (byte) 0);
				} else {
					p.sendBlockChange(l, MaterialSerializer.stringToMaterial(DisguiseIt.getInstance().getDisguises().get(l)), MaterialSerializer.stringToData(DisguiseIt.getInstance().getDisguises().get(l)));
					
				}
				
			}
		}
	}
	
	
	
}



