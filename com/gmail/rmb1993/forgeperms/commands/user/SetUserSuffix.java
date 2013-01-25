package com.gmail.rmb1993.forgeperms.commands.user;

import com.gmail.rmb1993.forgeperms.ForgePermsContainer;
import com.gmail.rmb1993.forgeperms.api.ForgePermsAPI;
import com.gmail.rmb1993.forgeperms.permissions.user.User;
import com.gmail.rmb1993.forgeperms.utils.FontColour;
import net.minecraft.command.ICommandSender;

public class SetUserSuffix {

    public SetUserSuffix(ForgePermsContainer fpc, ICommandSender sender, String[] args) {
        if (args.length == 3) {
            User u = fpc.config.getDb().getUser(sender.getCommandSenderName());
            if (ForgePermsAPI.playerHasPermission(u.getUserName(), "permissions.userSuffix")) {
                User u1 = fpc.config.getDb().getUser(args[1]);
                if (u1 == null) {
                    sender.sendChatToPlayer(FontColour.RED + "Sorry the user "+args[1]+" does not exist!");
                    return;
                }
                u1.getVars().put("suffix", args[2]);
                fpc.config.getDb().saveUsers();
                sender.sendChatToPlayer(FontColour.DARK_GREEN + "You set "+args[1]+"'s suffix to "+args[2]);
            } else {
                sender.sendChatToPlayer(FontColour.RED + "You do not have permission to use this command.");
            }
        } else {
            sender.sendChatToPlayer(FontColour.RED + "Usage: /user setSuffix [userName] [suffix]");
        }
    }
    
}
