package de.TrustedCreeper.DisguiseIt;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;


public class DisguiseItEventHandler implements Listener {

	
	private DisguiseIt plugin;

	public DisguiseItEventHandler(DisguiseIt plugin) {
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		if(e.getPlayer() == null) return;
		if(DisguiseIt.getInstance().taskid == 0) DisguiseIt.getInstance().sendChanges();
		DisguiseIt.getInstance().sendAllChanges();
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e)  {
		if(e.isCancelled()) return;
		if(e.getAction() == null) return;
		if(e.getClickedBlock() == null) return;
		DisguiseIt.getInstance().sendAllChanges();
	}
	
	@EventHandler
	public void onPlayerBreakBlock(BlockBreakEvent e) {
		if(e.isCancelled()) return;
		if(e.getPlayer() == null) return;
		if(e.getBlock() == null) return;
		if(DisguiseIt.getInstance().containsDisguises(e.getBlock().getLocation())) {
			if(!DisguiseIt.getInstance().getConfig().getBoolean("config.allow_break_disguised_blocks")) {
				e.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onBlockPhysics(BlockPhysicsEvent e) {
		if(e.getBlock() == null) return;
		if(e.getChangedType() == null) return;
		if(DisguiseIt.getInstance().containsDisguises(e.getBlock().getLocation())) DisguiseIt.getInstance().sendAllChanges();
	}
}
