package com.gmail.rmb1993.forgeperms.commands.user;

import com.gmail.rmb1993.forgeperms.ForgePermsPlugin;
import com.gmail.rmb1993.forgeperms.ForgePermsContainer;
import com.gmail.rmb1993.forgeperms.api.ForgePermsAPI;
import com.gmail.rmb1993.forgeperms.permissions.user.User;
import com.gmail.rmb1993.forgeperms.utils.FontColour;
import net.minecraft.command.ICommandSender;

public class ListUsers {

    public ListUsers(ForgePermsContainer fpc, ICommandSender sender, String[] args) {
        User u = fpc.config.getDb().getUser(sender.getCommandSenderName());
        if (ForgePermsAPI.playerHasPermission(u.getUserName(), "permissions.listUsers")) {
            sender.sendChatToPlayer("Users: ");
            for (User u1 : fpc.users.values()) {
                sender.sendChatToPlayer(u1.getUserName());
            }
        } else {
            sender.sendChatToPlayer(FontColour.RED + "You do not have permission to use this command.");
        }
    }
}
