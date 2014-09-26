package de.TrustedCreeper.DisguiseIt;

import org.bukkit.ChatColor;

public enum Messages {
	PREFIX("messages.prefix", "&6[&3DisguiseIt&6]"),
	WORLDEDIT_IS_MISSING("messages.worldedit_is_missing","&cPlease install WorldEdit to use this plugin!"),
	ADDED_BLOCKS("messages.added_blocks","&2Added blocks successfully!"),
	REMOVED_BLOCKS("messages.removed_blocks","&2Removed blocks successfully!"),
	NO_BLOCKS_FOUND("messages.no_blocks_found", "&cNo disguised blocks found in your current selection!"),
	NO_SELECTION("messages.no_selection","&cPlease select a region with WorldEdit first!"),
	ADD_USAGE("messages.add_usage", "&cUsage: /di add <X>:<X>"),
	EDIT_USAGE("messages.edit_usage", "&cUsage: /di edit"),
	REMOVE_USAGE("messages.remove_usage", "&cUsage: /di remove"),
	ADD_INFO("messages.add_info", "&5Disguise all blocks in selection into <X>:<X>! &6Example: /di add 5:2"),
	EDIT_INFO("messages.edit_info", "&5Enter / leave editmode to remove disguises!"),
	REMOVE_INFO("messages.remove_info", "&5Remove all disguises in selection! Enter editmode to use this."),
	COMMAND_NOT_FOUND("messages.command_not_found", "&cCommand not found! &6See help: /di"),
	ADDED_EDITMODE("messages.added_editmode","&2You are now in editmode! All disguised blocks are now diamondblocks!"),
	REMOVED_EDITMODE("messages.removed_editmode","&cYou are no longer in editmode!"),
	ENTER_EDITMODE("messages.enter_editmode","&cPlease enter editmode first! &6Use: /di edit"),
	NO_NUMBER_FOUND("messages.no_number_found", "&cUsage: /di add <X>:<X> &7- &6<X> has to be a number!"),
	NO_PERMISSION("messages.no_permission", "&cYou dont have permission to use this command!");
	
	String configpath;
	String defaultmessage;
	
	Messages(String configpath, String defaultmessage) {
		this.configpath = configpath;
		this.defaultmessage = defaultmessage;
	}
	
	public String get() {
		return ChatColor.translateAlternateColorCodes('&', DisguiseIt.getInstance().getConfig().getString(configpath));
	}
	
	public String getConfigPath() { return configpath; } 
	public String getDefaultMessage() { return defaultmessage; } 
}
