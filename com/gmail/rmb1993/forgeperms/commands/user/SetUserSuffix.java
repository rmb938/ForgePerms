package com.gmail.rmb1993.forgeperms.commands.user;

import com.gmail.rmb1993.forgeperms.ForgePermsContainer;
import com.gmail.rmb1993.forgeperms.api.ForgePermsAPI;
import com.gmail.rmb1993.forgeperms.permissions.user.User;
import com.gmail.rmb1993.forgeperms.utils.StringColors;
import net.minecraft.command.ICommandSender;

public class SetUserSuffix {

    public SetUserSuffix(ICommandSender sender, String[] args) {
        if (args.length == 3) {
            User u = ForgePermsContainer.instance.config.getDb().loadUser(sender.getCommandSenderName());
            if (ForgePermsAPI.playerHasPermission(u.getUserName(), "permissions.suffix")) {
                User u1 = ForgePermsContainer.instance.config.getDb().loadUser(args[1]);
                u1.getVars().put("suffix", args[2]);
                ForgePermsContainer.instance.config.getDb().saveUsers();
                sender.sendChatToPlayer(StringColors.EnumTextColor.DARK_GREEN.colorString("You set "+args[1]+"'s suffix to "+args[2]));
            } else {
                sender.sendChatToPlayer(StringColors.EnumTextColor.RED.colorString("You do not have permission to use this command."));
            }
        } else {
            sender.sendChatToPlayer(StringColors.EnumTextColor.RED.colorString("Usage: /user setSuffix [userName] [suffix]"));
        }
    }
    
}
