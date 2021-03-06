package com.gmail.rmb1993.forgeperms.commands.user;

import com.gmail.rmb1993.forgeperms.ForgePermsPlugin;
import com.gmail.rmb1993.forgeperms.ForgePermsContainer;
import com.gmail.rmb1993.forgeperms.api.ForgePermsAPI;
import com.gmail.rmb1993.forgeperms.permissions.user.User;
import com.gmail.rmb1993.forgeperms.utils.FontColour;
import net.minecraft.command.ICommandSender;

public class ListUserPerms {

    public ListUserPerms(ForgePermsContainer fpc, ICommandSender sender, String[] args) {
        if (args.length == 2) {
            User u = fpc.config.getDb().getUser(sender.getCommandSenderName());
            if (ForgePermsAPI.playerHasPermission(u.getUserName(), "permissions.listUserPerms")) {
                User u1 = fpc.config.getDb().getUser(args[1]);
                if (u1 == null) {
                    sender.sendChatToPlayer(FontColour.RED + "Sorry the user "+args[1]+" does not exist!");
                    return;
                }
                sender.sendChatToPlayer(args[1]+"'s Permissions: ");
                for (String perm : u1.getPermissions().keySet()) {
                    sender.sendChatToPlayer(perm);
                }
            } else {
                sender.sendChatToPlayer(FontColour.RED + "You do not have permission to use this command.");
            }
        } else {
            sender.sendChatToPlayer(FontColour.RED + "Usage: /user listPerms [userName]");
        }
    }
    
}
