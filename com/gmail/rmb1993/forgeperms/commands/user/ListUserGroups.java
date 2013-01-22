package com.gmail.rmb1993.forgeperms.commands.user;

import com.gmail.rmb1993.forgeperms.ForgePermsPlugin;
import com.gmail.rmb1993.forgeperms.ForgePermsContainer;
import com.gmail.rmb1993.forgeperms.api.ForgePermsAPI;
import com.gmail.rmb1993.forgeperms.permissions.group.Group;
import com.gmail.rmb1993.forgeperms.permissions.user.User;
import com.gmail.rmb1993.forgeperms.utils.StringColors;
import net.minecraft.command.ICommandSender;

public class ListUserGroups {

    public ListUserGroups(ICommandSender sender, String[] args) {
        if (args.length == 2) {
            User u = ForgePermsContainer.instance.config.getDb().loadUser(sender.getCommandSenderName());
            if (ForgePermsAPI.playerHasPermission(u.getUserName(), "permissions.listUserGroups")) {
                User u1 = ForgePermsContainer.instance.config.getDb().loadUser(args[1]);
                sender.sendChatToPlayer(args[1]+"'s Groups: ");
                for (String gN : u1.getGroups()) {
                    Group g = ForgePermsContainer.instance.config.getDb().loadGroup(gN);
                    sender.sendChatToPlayer(g.getGroupName()+" Track: "+g.getTrack());
                }
            } else {
                sender.sendChatToPlayer(StringColors.EnumTextColor.RED.colorString("You do not have permission to use this command."));
            }
        } else {
            sender.sendChatToPlayer(StringColors.EnumTextColor.RED.colorString("Usage: /user listUserGroups [userName]"));
        }
    }
    
}
