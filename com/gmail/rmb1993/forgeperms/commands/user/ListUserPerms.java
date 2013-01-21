package com.gmail.rmb1993.forgeperms.commands.user;

import com.gmail.rmb1993.forgeperms.ForgePerms;
import com.gmail.rmb1993.forgeperms.api.ForgePermsAPI;
import com.gmail.rmb1993.forgeperms.permissions.user.User;
import net.minecraft.command.ICommandSender;

public class ListUserPerms {

    public ListUserPerms(ICommandSender sender, String[] args) {
        if (args.length == 2) {
            User u = ForgePerms.instance.users.get(sender.getCommandSenderName());
            if (ForgePermsAPI.playerHasPermission(u.getUserName(), "permissions.listUserPerms")) {
                User u1 = ForgePerms.instance.users.get(args[1]);
                sender.sendChatToPlayer(args[1]+"'s Permissions: ");
                for (String perm : u1.getPermissions().keySet()) {
                    sender.sendChatToPlayer(perm);
                }
            } else {
                sender.sendChatToPlayer("You do not have permission to use this command.");
            }
        } else {
            sender.sendChatToPlayer("Usage: /user listUserGroups [userName]");
        }
    }
    
}
