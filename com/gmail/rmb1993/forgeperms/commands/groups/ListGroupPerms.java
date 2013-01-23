package com.gmail.rmb1993.forgeperms.commands.groups;

import com.gmail.rmb1993.forgeperms.ForgePermsContainer;
import com.gmail.rmb1993.forgeperms.api.ForgePermsAPI;
import com.gmail.rmb1993.forgeperms.permissions.group.Group;
import com.gmail.rmb1993.forgeperms.permissions.user.User;
import com.gmail.rmb1993.forgeperms.utils.StringColors;

import net.minecraft.command.ICommandSender;

public class ListGroupPerms {

    public ListGroupPerms(ForgePermsContainer fpc, ICommandSender sender, String[] args) {
        if (args.length == 2) {
            User u = fpc.config.getDb().getUser(sender.getCommandSenderName());
            if (ForgePermsAPI.playerHasPermission(u.getUserName(), "permissions.listGroupPerms")) {
                Group g = fpc.config.getDb().getGroup(args[1]);
                if (g == null) {
                    sender.sendChatToPlayer(StringColors.EnumTextColor.RED.colorString("Sorry the group " + args[1] + " does not exist!"));
                    return;
                }
                sender.sendChatToPlayer(args[1]+"'s Permissions: ");
                for (String perm : g.getPermissions().keySet()) {
                    sender.sendChatToPlayer(perm);
                }
            } else {
                sender.sendChatToPlayer(StringColors.EnumTextColor.RED.colorString("You do not have permission to use this command."));
            }
        } else {
            sender.sendChatToPlayer(StringColors.EnumTextColor.RED.colorString("Usage: /group listPerms [groupName]"));
        }
    }

}
