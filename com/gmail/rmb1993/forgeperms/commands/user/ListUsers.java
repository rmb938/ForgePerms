package com.gmail.rmb1993.forgeperms.commands.user;

import com.gmail.rmb1993.forgeperms.ForgePerms;
import com.gmail.rmb1993.forgeperms.api.ForgePermsAPI;
import com.gmail.rmb1993.forgeperms.permissions.user.User;
import net.minecraft.command.ICommandSender;

public class ListUsers {

    public ListUsers(ICommandSender sender, String[] args) {
        User u = ForgePerms.instance.users.get(sender.getCommandSenderName());
        if (ForgePermsAPI.playerHasPermission(u.getUserName(), "permissions.listUsers")) {
            sender.sendChatToPlayer("Users: ");
            for (User u1 : ForgePerms.instance.users.values()) {
                sender.sendChatToPlayer(u1.getUserName());
            }
        } else {
            sender.sendChatToPlayer("You do not have permission to use this command.");
        }
    }
}
