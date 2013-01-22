package com.gmail.rmb1993.forgeperms.commands.user;

import com.gmail.rmb1993.forgeperms.ForgePermsPlugin;
import com.gmail.rmb1993.forgeperms.ForgePermsContainer;
import com.gmail.rmb1993.forgeperms.api.ForgePermsAPI;
import com.gmail.rmb1993.forgeperms.permissions.user.User;
import net.minecraft.command.ICommandSender;

public class RemoveUserSuffix {
    
    public RemoveUserSuffix(ICommandSender sender, String[] args) {
        if (args.length == 2) {
            User u = ForgePermsContainer.instance.config.getDb().loadUser(sender.getCommandSenderName());
            if (ForgePermsAPI.playerHasPermission(u.getUserName(), "permissions.suffix")) {
                User u1 = ForgePermsContainer.instance.config.getDb().loadUser(args[1]);
                u1.getVars().remove("suffix");
                ForgePermsContainer.instance.config.getDb().saveUsers();
                sender.sendChatToPlayer("You removed "+args[1]+"'s suffix");
            } else {
                sender.sendChatToPlayer("You do not have permission to use this command.");
            }
        } else {
            sender.sendChatToPlayer("Usage: /user removeSuffix [userName]");
        }
    }

}
