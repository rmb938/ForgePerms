package com.gmail.rmb1993.forgeperms.commands.user;

import com.gmail.rmb1993.forgeperms.ForgePermsPlugin;
import com.gmail.rmb1993.forgeperms.ForgePermsContainer;
import com.gmail.rmb1993.forgeperms.api.ForgePermsAPI;
import com.gmail.rmb1993.forgeperms.permissions.group.Group;
import com.gmail.rmb1993.forgeperms.permissions.user.User;
import com.gmail.rmb1993.forgeperms.utils.StringColors;
import net.minecraft.command.ICommandSender;

public class ListUserGroups {

    public ListUserGroups(ForgePermsContainer fpc, ICommandSender sender, String[] args) {
        if (args.length == 2) {
            User u = fpc.config.getDb().getUser(sender.getCommandSenderName());
            if (ForgePermsAPI.playerHasPermission(u.getUserName(), "permissions.listUserGroups")) {
                User u1 = fpc.config.getDb().getUser(args[1]);
                if (u1 == null) {
                    sender.sendChatToPlayer(StringColors.EnumTextColor.RED.colorString("Sorry the user "+args[1]+" does not exist!"));
                    return;
                }
                sender.sendChatToPlayer(args[1]+"'s Groups: ");
                for (String gN : u1.getGroups()) {
                    Group g = fpc.config.getDb().getGroup(gN);
                    sender.sendChatToPlayer(g.getGroupName()+" Track: "+g.getTrack());
                }
            } else {
                sender.sendChatToPlayer(StringColors.EnumTextColor.RED.colorString("You do not have permission to use this command."));
            }
        } else {
            sender.sendChatToPlayer(StringColors.EnumTextColor.RED.colorString("Usage: /user listGroups [userName]"));
        }
    }
    
}
