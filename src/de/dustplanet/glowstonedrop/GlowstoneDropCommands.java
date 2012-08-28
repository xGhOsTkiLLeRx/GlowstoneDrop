package de.dustplanet.glowstonedrop;

import java.util.Arrays;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * GlowstoneDrop for CraftBukkit/Bukkit
 * Handles the commands!
 * Refer to the forum thread: 
 * http://bit.ly/oW6iR1
 * Refer to the dev.bukkit.org page: 
 * http://bit.ly/rcN2QB
 * 
 * @author  xGhOsTkiLLeRx
 * @thanks  to XxFuNxX for the original GlowstoneDrop plugin!
 */

public class GlowstoneDropCommands implements CommandExecutor {
	private GlowstoneDrop plugin;
	private String[] worlds = {"normal", "nether", "end"};
	private String[] values = {"block", "dust"};
	public GlowstoneDropCommands(GlowstoneDrop plugin) {
		this.plugin = plugin;
	}

	// Commands; always check for permissions!
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		// reload
		String message = "";
		if (args.length > 0 && args[0].equalsIgnoreCase("reload")) {
			if (sender.hasPermission("glowstonedrop.reload") || !plugin.config.getBoolean("configuration.permissions")) {
				GlowstoneDropReload(sender);
			}
			else {
				message = plugin.localization.getString("permission_denied");
				plugin.message(sender, null, message, null, null);
			}
			return true;
		}
		// help
		if (args.length > 0 && args[0].equalsIgnoreCase("help")) {
			if (sender.hasPermission("glowstonedrop.help") || !plugin.config.getBoolean("configuration.permissions")) {
				GlowstoneDropHelp(sender);
			}
			else {
				message = plugin.localization.getString("permission_denied");
				plugin.message(sender, null, message, null, null);
			}
			return true;
		}
		// set
		if (args.length > 2 && args[0].equalsIgnoreCase("set")) {
			String world = args[1], value = args[2];
			if (Arrays.asList(worlds).contains(args[1]) && Arrays.asList(values).contains(args[2])) {
				if (sender.hasPermission("glowstonedrop.set.normal") || !plugin.config.getBoolean("configuration.permissions")) {
					GlowstoneDropSet(sender, world, value);
				}
				else {
					message = plugin.localization.getString("permission_denied");
					plugin.message(sender, null, message, null, null);
				}
				return true;
			}
			// all
			if (args.length > 2 && args[1].equalsIgnoreCase("all")) {
				// block
				if (Arrays.asList(values).contains(args[2])) {
					if (sender.hasPermission("glowstonedrop.set.all") || !plugin.config.getBoolean("configuration.permissions")) {
						GlowstoneDropSetAll(sender, value);
					}
					else {
						message = plugin.localization.getString("permission_denied");
						plugin.message(sender, null, message, null, null);
					}
					return true;
				}
			}
		}
		// enable
		if (args.length > 0 && args[0].equalsIgnoreCase("enable"))   {
			if ((args[1].equalsIgnoreCase("messages")) || args[1].equalsIgnoreCase("permissions")) {
				// permissions
				String value = args[1];
				if (args.length > 1 && args[1].equalsIgnoreCase("permissions")) {
					if (sender.hasPermission("glowstonedrop.enable." + args[0]) || !plugin.config.getBoolean("configuration.permissions")) {
						GlowstoneDropEnable(sender, value);
					}
					else {
						message = plugin.localization.getString("permission_denied");
						plugin.message(sender, null, message, null, null);
					}
					return true;
				}
			}
		}
		// disable
		if (args.length > 0 && args[0].equalsIgnoreCase("disable"))   {
			if ((args[1].equalsIgnoreCase("messages")) || args[1].equalsIgnoreCase("permissions")) {
				// permissions
				String value = args[1];
				if (args.length > 1 && args[1].equalsIgnoreCase("permissions")) {
					if (sender.hasPermission("glowstonedrop.disable." + args[0]) || !plugin.config.getBoolean("configuration.permissions")) {
						GlowstoneDropDisable(sender, value);
					}
					else {
						message = plugin.localization.getString("permission_denied");
						plugin.message(sender, null, message, null, null);
					}
					return true;
				}
			}
		}
		return false;
	}

	// Set a value
	private void GlowstoneDropSet(CommandSender sender, String world, String value) {
		plugin.config.set("worlds." + world.toLowerCase(), value.toLowerCase());
		plugin.saveConfig();
		String message = plugin.localization.getString("set");
		plugin.message(sender, null, message, value, world);
	}

	// See the help with /glowstonedrop help or /glowdrop help
	private void GlowstoneDropHelp(CommandSender sender) {
		for (int i = 1; i <= 12; i++) {
			String message = plugin.localization.getString("help_" + Integer.toString(i));
			plugin.message(sender, null, message, null, null);
		}
	}

	// Reloads the config with /glowstonedrop reload or /glowdrop reload
	private void GlowstoneDropReload(CommandSender sender) {
		plugin.loadConfigsAgain();
		String message = plugin.localization.getString("reload");
		plugin.message(sender, null, message, null, null);
	}

	// Enables permissions with /glowstonedrop enable <value> or /glowdrop enable <value>
	private void GlowstoneDropEnable(CommandSender sender, String value) {
		plugin.config.set("configuration." + value, true);
		plugin.saveConfig();
		String message = "";
		if (value.equalsIgnoreCase("permissions")) {
			for (int i = 1; i <= 2; i++) {
				message = plugin.localization.getString("enable_permissions_" + Integer.toString(i));
				plugin.message(sender, null, message, null, null);
			}
		}
		else {
			message = plugin.localization.getString("enable_messages");
			plugin.message(sender, null, message, null, null);
		}
	}

	// Disables messages with /glowstonedrop disable <value> or /glowdrop disable <value>
	private void GlowstoneDropDisable(CommandSender sender, String value) {
		plugin.config.set("configuration." + value, false);
		plugin.saveConfig();
		String message = "";
		if (value.equalsIgnoreCase("permissions")) {
			for (int i = 1; i <= 2; i++) {
				message = plugin.localization.getString("disable_permissions_" + Integer.toString(i));
				plugin.message(sender, null, message, null, null);
			}
		}
		else {
			message = plugin.localization.getString("disable_messages");
			plugin.message(sender, null, message, null, null);
		}
	}


	// Sets the all drops to dust with /glowstonedrop set all dust or /glowdrop set all dust
	private void GlowstoneDropSetAll(CommandSender sender, String value) {
		plugin.config.set("worlds.normal", value.toLowerCase());
		plugin.config.set("worlds.nether", value.toLowerCase());
		plugin.config.set("worlds.end", value.toLowerCase());
		plugin.saveConfig();
		String message = plugin.localization.getString("set_all");
		plugin.message(sender, null, message, value, null);
	}
}
