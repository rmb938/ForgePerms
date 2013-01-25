package com.gmail.rmb1993.forgeperms.commands.groups;

import com.gmail.rmb1993.forgeperms.ForgePermsContainer;
import com.gmail.rmb1993.forgeperms.api.ForgePermsAPI;
import com.gmail.rmb1993.forgeperms.permissions.user.User;
import com.gmail.rmb1993.forgeperms.utils.FontColour;
import net.minecraft.command.ICommandSender;

/**
 *
 * @author Ryan
 * @author Favorlock
 */
public class Remove {

    public Remove(ForgePermsContainer fpc, ICommandSender sender, String[] args) {
        if (args.length == 2) {
            User u = fpc.config.getDb().getUser(sender.getCommandSenderName());
            if (ForgePermsAPI.playerHasPermission(u.getUserName(), "permissions.removeGroup")) {
                if (fpc.config.getDb().getGroup(args[1]) == null) {
                    sender.sendChatToPlayer(FontColour.RED + "Sorry the group " + args[1] + " does not exist!");
                    return;
                }
                fpc.config.getDb().removeGroup(args[1]);
                sender.sendChatToPlayer(FontColour.DARK_GREEN + "You removed the group " + args[1]);
            } else {
                sender.sendChatToPlayer(FontColour.RED + "You do not have permission to use this command.");
            }
        } else {
            sender.sendChatToPlayer(FontColour.RED + "Usage: /group remove [groupName]");
        }
    }
}
