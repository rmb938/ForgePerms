package com.gmail.rmb1993.forgeperms.commands.user;

import net.minecraft.command.ICommandSender;

import com.gmail.rmb1993.forgeperms.ForgePermsContainer;
import com.gmail.rmb1993.forgeperms.api.ForgePermsAPI;
import com.gmail.rmb1993.forgeperms.permissions.group.Group;
import com.gmail.rmb1993.forgeperms.permissions.user.User;
import com.gmail.rmb1993.forgeperms.utils.FontColour;

public class SetGroup {
	
	public SetGroup(ForgePermsContainer fpc, ICommandSender sender, String[] args) {
		if (args.length == 3) {
			User u = fpc.config.getDb().getUser(sender.getCommandSenderName());
			if (ForgePermsAPI.playerHasPermission(u.getUserName(), "permissions.setUserGroup")) {
				User u1 = fpc.config.getDb().getUser(args[1]);
				if (u1 == null) {
					sender.sendChatToPlayer(FontColour.RED + "Sorry the user " + args[1] + " does not exist!");
					return;
				}
				Group g = fpc.config.getDb().getGroup(args[2]);
				if (g == null) {
					sender.sendChatToPlayer(FontColour.RED + "Sorry the group " + args[2] + " does not exist!");
					return;
				}
				if (!u1.getGroups().isEmpty()) {
					u1.getGroups().clear();
					u1.getGroups().add(g.getGroupName());
					fpc.config.getDb().saveUsers();
					sender.sendChatToPlayer(FontColour.DARK_GREEN + "You set " + args[1] + "'s group to " + args[2]);
				}
			} else {
				sender.sendChatToPlayer(FontColour.RED + "You do not have permission to use this command.");
			}
		} else {
			sender.sendChatToPlayer(FontColour.RED + "Usage: /user setGroup [userName] [groupName]");
		}
	}

}
