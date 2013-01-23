package com.gmail.rmb1993.forgeperms.commands.user;

import com.gmail.rmb1993.forgeperms.ForgePermsPlugin;
import com.gmail.rmb1993.forgeperms.ForgePermsContainer;
import com.gmail.rmb1993.forgeperms.api.ForgePermsAPI;
import com.gmail.rmb1993.forgeperms.permissions.user.User;
import com.gmail.rmb1993.forgeperms.utils.StringColors;
import net.minecraft.command.ICommandSender;

public class RemoveUserPrefix {
    
    public RemoveUserPrefix(ForgePermsContainer fpc, ICommandSender sender, String[] args) {
        if (args.length == 2) {
            User u = fpc.config.getDb().getUser(sender.getCommandSenderName());
            if (ForgePermsAPI.playerHasPermission(u.getUserName(), "permissions.userPrefix")) {
                User u1 = fpc.config.getDb().getUser(args[1]);
                if (u1 == null) {
                    sender.sendChatToPlayer(StringColors.EnumTextColor.RED.colorString("Sorry the user "+args[1]+" does not exist!"));
                    return;
                }
                u1.getVars().remove("prefix");
                fpc.config.getDb().saveUsers();
                sender.sendChatToPlayer(StringColors.EnumTextColor.DARK_GREEN.colorString("You removed "+args[1]+"'s prefix"));
                fpc.config.getDb().saveUsers();
            } else {
                sender.sendChatToPlayer(StringColors.EnumTextColor.RED.colorString("You do not have permission to use this command."));
            }
        } else {
            sender.sendChatToPlayer(StringColors.EnumTextColor.RED.colorString("Usage: /user removePrefix [userName]"));
        }
    }

}
