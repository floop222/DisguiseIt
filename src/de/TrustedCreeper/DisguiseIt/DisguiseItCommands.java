package de.TrustedCreeper.DisguiseIt;

import java.util.List;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.bukkit.selections.Selection;
public class DisguiseItCommands implements CommandExecutor {

	private DisguiseIt plugin;

	public DisguiseItCommands(DisguiseIt plugin) {
		this.plugin = plugin;
	}


	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if(command.getName().equalsIgnoreCase("di")) {
			if(!(sender instanceof Player)) return false;
			Player p = (Player) sender;
			if(DisguiseIt.worldedit == false) {
				p.sendMessage(DisguiseIt.getPrefix() + Messages.WORLDEDIT_IS_MISSING.get());
				return true;
			}
			
			if(args.length == 0) {
				if(!p.hasPermission("di.info")) {
					p.sendMessage(DisguiseIt.getPrefix() + Messages.ADD_USAGE.get() + "§7 - §r" + Messages.NO_PERMISSION.get());
					return true;
				}
				p.sendMessage(DisguiseIt.getPrefix() + Messages.ADD_USAGE.get() + "§7 - §r" + Messages.ADD_INFO.get());
				p.sendMessage(DisguiseIt.getPrefix() + Messages.EDIT_USAGE.get() + "§7 - §r" + Messages.EDIT_INFO.get());
				p.sendMessage(DisguiseIt.getPrefix() + Messages.REMOVE_USAGE.get() + "§7 - §r" + Messages.REMOVE_INFO.get());
				return true;
			}
			
			if(args.length == 1) {
				if(args[0].equalsIgnoreCase("edit")) {
					if(!p.hasPermission("di.edit")) {
						p.sendMessage(DisguiseIt.getPrefix() + Messages.ADD_USAGE.get() + "§7 - §r" + Messages.NO_PERMISSION.get());
						return true;
					}
					if(DisguiseIt.editmode.contains(p.getName())) {
						DisguiseIt.editmode.remove(p.getName());
						p.sendMessage(DisguiseIt.getPrefix() + Messages.REMOVED_EDITMODE.get());
					} else {
						DisguiseIt.editmode.add(p.getName());
						p.sendMessage(DisguiseIt.getPrefix() + Messages.ADDED_EDITMODE.get());
					}
				}else if(args[0].equalsIgnoreCase("remove")) {
					if(!p.hasPermission("di.remove")) {
						p.sendMessage(DisguiseIt.getPrefix() + Messages.ADD_USAGE.get() + "§7 - §r" + Messages.NO_PERMISSION.get());
						return true;
					}
					if(DisguiseIt.editmode.contains(p.getName())) {
						Selection selection = DisguiseIt.getWorldEdit().getSelection(p);
						if (selection != null) {
							boolean removed = false;
						    World world = selection.getWorld();
						    Vector min = selection.getNativeMinimumPoint();
		                    Vector max = selection.getNativeMaximumPoint();
		                    for(int x = min.getBlockX();x <= max.getBlockX(); x=x+1){
		                        for(int y = min.getBlockY();y <= max.getBlockY(); y=y+1){
		                            for(int z = min.getBlockZ();z <= max.getBlockZ(); z=z+1){
		                                Location loc = new Location(world, x, y, z);
		                                if(DisguiseIt.getInstance().containsDisguises(loc)) {
		                                	removed = true;
		                                	List<String> disguises = DisguiseIt.getInstance().getConfig().getStringList("disguises");
			                                disguises.remove(LocationSerialiser.locationEntityToString(loc) + "/" + MaterialSerializer.getMaterialDataByLocation(loc));
			                                DisguiseIt.getInstance().getConfig().set("disguises", disguises);
			                                Chunk c = loc.getChunk();
			                                loc.getWorld().refreshChunk(c.getX(), c.getZ());
		                                }
		                            }
		                        }
		                    }
		                    if(removed) {
		                    	DisguiseIt.getInstance().saveConfig();
			                    p.sendMessage(DisguiseIt.getPrefix() + Messages.REMOVED_BLOCKS.get());
		                    } else {
		                    	p.sendMessage(DisguiseIt.getPrefix() + Messages.NO_BLOCKS_FOUND.get());
		                    }
		                    
						} else {
							p.sendMessage(DisguiseIt.getPrefix() + Messages.NO_SELECTION.get());
						}
					} else {
						p.sendMessage(DisguiseIt.getPrefix() + Messages.ENTER_EDITMODE.get());
					}
				} else {
					p.sendMessage(DisguiseIt.getPrefix() + Messages.COMMAND_NOT_FOUND.get());
				}
			}
			
			if(args.length == 2) {
				if(args[0].equalsIgnoreCase("add")) {
					if(!p.hasPermission("di.add")) {
						p.sendMessage(DisguiseIt.getPrefix() + Messages.ADD_USAGE.get() + "§7 - §r" + Messages.NO_PERMISSION.get());
						return true;
					}
					if(!args[1].contains(":")) {
						p.sendMessage(DisguiseIt.getPrefix() + Messages.ADD_USAGE.get());
						return true;
					}
					
					String[] split = args[1].split(":");
					try {
						Integer.parseInt(split[0]);
						Integer.parseInt(split[1]);
					} catch (Exception e) {
						p.sendMessage(DisguiseIt.getPrefix() + Messages.NO_NUMBER_FOUND.get());
						return true;
					}
					Selection selection = DisguiseIt.getWorldEdit().getSelection(p);
					if (selection != null) {
					    World world = selection.getWorld();
					    Vector min = selection.getNativeMinimumPoint();
	                    Vector max = selection.getNativeMaximumPoint();
	                    for(int x = min.getBlockX();x <= max.getBlockX(); x=x+1){
	                        for(int y = min.getBlockY();y <= max.getBlockY(); y=y+1){
	                            for(int z = min.getBlockZ();z <= max.getBlockZ(); z=z+1){
	                                Location loc = new Location(world, x, y, z);
	                                if(!DisguiseIt.getInstance().containsDisguises(loc)) {
	                                	List<String> disguises = DisguiseIt.getInstance().getConfig().getStringList("disguises");
		                                disguises.add(LocationSerialiser.locationEntityToString(loc) + "/" + args[1]);
		                                DisguiseIt.getInstance().getConfig().set("disguises", disguises);
	                                }
	                            }
	                        }
	                    }
	                    DisguiseIt.getInstance().saveConfig();
	                    p.sendMessage(DisguiseIt.getPrefix() + Messages.ADDED_BLOCKS.get());
					} else {
						p.sendMessage(DisguiseIt.getPrefix() + Messages.NO_SELECTION.get());
					}
					
				} else {
					p.sendMessage(DisguiseIt.getPrefix() + Messages.COMMAND_NOT_FOUND.get());
				}
			}
			
			if(args.length > 2) {
				if(!p.hasPermission("di.info")) {
					p.sendMessage(DisguiseIt.getPrefix() + Messages.ADD_USAGE.get() + "§7 - §r" + Messages.NO_PERMISSION.get());
					return true;
				}
				p.sendMessage(DisguiseIt.getPrefix() + Messages.COMMAND_NOT_FOUND.get());
			}
			return true;
		}
		return false;
	}
}