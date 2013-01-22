package com.gmail.rmb1993.forgeperms.commands.user;

import com.gmail.rmb1993.forgeperms.ForgePermsContainer;
import com.gmail.rmb1993.forgeperms.api.ForgePermsAPI;
import com.gmail.rmb1993.forgeperms.permissions.user.User;
import net.minecraft.command.ICommandSender;

public class SetUserSuffix {

    public SetUserSuffix(ICommandSender sender, String[] args) {
        if (args.length == 3) {
            User u = ForgePermsContainer.instance.config.getDb().loadUser(sender.getCommandSenderName());
            if (ForgePermsAPI.playerHasPermission(u.getUserName(), "permissions.suffix")) {
                User u1 = ForgePermsContainer.instance.config.getDb().loadUser(args[1]);
                u1.getVars().put("suffix", args[2]);
                ForgePermsContainer.instance.config.getDb().saveUsers();
                sender.sendChatToPlayer("You set "+args[1]+"'s suffix to "+args[2]);
            } else {
                sender.sendChatToPlayer("You do not have permission to use this command.");
            }
        } else {
            sender.sendChatToPlayer("Usage: /user setSuffix [userName] [suffix]");
        }
    }
    
}
