package de.TrustedCreeper.DisguiseIt;

import org.bukkit.Location;
import org.bukkit.Material;

public class MaterialSerializer {
	public static String materialToString(Material mat) {
		return mat.getId() + ":" + mat.getData();
	}
	public static Material stringToMaterial(String str) {
		String[] splitted = str.split(":");
		return Material.getMaterial(Integer.parseInt(splitted[0]));
	}
	public static byte stringToData(String str) {
		String[] splitted = str.split(":");
		return Byte.parseByte(splitted[1]);
	}
	public static Location getLocationByRaw(String raw) {
		String[] locblock = raw.split("/");
		return LocationSerialiser.stringToLocationEntity(locblock[0]);
	}
	public static String getMaterialSerializationByRaw(String raw) {
		String[] locblock = raw.split("/");
		return locblock[1];
	}
	public static String getMaterialDataByLocation(Location loc) {
		if(DisguiseIt.getInstance().containsDisguises(loc)) {
			for(Location l : DisguiseIt.getInstance().getDisguises().keySet()) {
				return DisguiseIt.getInstance().getDisguises().get(l);
			}
		}
		return "";
	}
}
