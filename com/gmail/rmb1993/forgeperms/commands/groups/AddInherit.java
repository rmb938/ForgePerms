package com.gmail.rmb1993.forgeperms.commands.groups;

import com.gmail.rmb1993.forgeperms.ForgePermsContainer;
import com.gmail.rmb1993.forgeperms.api.ForgePermsAPI;
import com.gmail.rmb1993.forgeperms.permissions.group.Group;
import com.gmail.rmb1993.forgeperms.permissions.user.User;
import com.gmail.rmb1993.forgeperms.utils.StringColors;

import net.minecraft.command.ICommandSender;

/**
 *
 * @author Ryan
 */
public class AddInherit {

    public AddInherit(ForgePermsContainer fpc, ICommandSender sender, String[] args) {
        if (args.length == 3) {
        	User u = fpc.config.getDb().getUser(sender.getCommandSenderName());
        	if (ForgePermsAPI.playerHasPermission(u.getUserName(), "permission.addInheritGroup")) {
            	if (fpc.config.getDb().getGroup(args[1]) == null) {
            		sender.sendChatToPlayer(StringColors.EnumTextColor.RED.colorString("Sorry the group " + args[1] + " does not exist!"));
            		return;
            	} else {
            		if (fpc.config.getDb().getGroup(args[2]) == null) {
            			sender.sendChatToPlayer(StringColors.EnumTextColor.RED.colorString("Sorry the group " + args[2] + " does not exist!"));
            			return;
            		}
            		Group g = fpc.config.getDb().getGroup(args[1]);
            		g.getInheritance().add(args[2]);
            	}
        	} else {
        		sender.sendChatToPlayer(StringColors.EnumTextColor.RED.colorString("You do not have permission to use this command."));
        	}
        } else {
        	sender.sendChatToPlayer(StringColors.EnumTextColor.RED.colorString("Usage: /group addInherit [Group] [inheritedGroup]"));
        }
    }

}
