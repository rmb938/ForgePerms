package com.gmail.rmb1993.forgeperms.commands.groups;

import com.gmail.rmb1993.forgeperms.ForgePermsContainer;
import com.gmail.rmb1993.forgeperms.api.ForgePermsAPI;
import com.gmail.rmb1993.forgeperms.permissions.group.Group;
import com.gmail.rmb1993.forgeperms.permissions.user.User;
import com.gmail.rmb1993.forgeperms.utils.FontColour;
import net.minecraft.command.ICommandSender;

/**
 *
 * @author Favorlock
 *
 */
public class RemoveGroupPrefix {

    public RemoveGroupPrefix(ForgePermsContainer fpc, ICommandSender sender, String[] args) {
        if (args.length == 2) {
            User u = fpc.config.getDb().getUser(sender.getCommandSenderName());
            if (ForgePermsAPI.playerHasPermission(u.getUserName(), "permission.groupPrefix")) {
                Group g = fpc.config.getDb().getGroup(args[1]);
                if (g == null) {
                    sender.sendChatToPlayer(FontColour.RED + "Sorry the group " + args[1] + " does not exist!");
                    return;
                }
                g.getVars().remove("prefix");
                fpc.config.getDb().saveGroups();
                sender.sendChatToPlayer(FontColour.DARK_GREEN + "You removed the prefix from group " + args[1]);
            } else {
                sender.sendChatToPlayer(FontColour.RED + "You do not have permission to use this command.");
            }
        } else {
            sender.sendChatToPlayer(FontColour.RED + "Usage: /group removePrefix [groupName]");
        }
    }
}
