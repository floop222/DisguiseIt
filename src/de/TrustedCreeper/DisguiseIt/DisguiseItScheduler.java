package de.TrustedCreeper.DisguiseIt;

import org.bukkit.Bukkit;

public class DisguiseItScheduler implements Runnable {

	@Override
	public void run() {
		if(Bukkit.getOnlinePlayers().length > 0) {
			DisguiseIt.getInstance().sendAllChanges();
		} else {
			DisguiseIt.getInstance().cancelTask();
		}
	}
	
}
