package com.gmail.rmb1993.forgeperms.commands.groups;

import com.gmail.rmb1993.forgeperms.ForgePermsContainer;
import com.gmail.rmb1993.forgeperms.api.ForgePermsAPI;
import com.gmail.rmb1993.forgeperms.permissions.group.Group;
import com.gmail.rmb1993.forgeperms.permissions.user.User;
import com.gmail.rmb1993.forgeperms.utils.FontColour;
import net.minecraft.command.ICommandSender;

/**
 *
 * @author Ryan
 * @author Favorlock
 */
public class RemoveInherit {

    public RemoveInherit(ForgePermsContainer fpc, ICommandSender sender, String[] args) {
        if (args.length == 3) {
            User u = fpc.config.getDb().getUser(sender.getCommandSenderName());
            if (ForgePermsAPI.playerHasPermission(u.getUserName(), "permission.removeInheritGroup")) {
                if (fpc.config.getDb().getGroup(args[1]) == null) {
                    sender.sendChatToPlayer(FontColour.RED + "Sorry the group " + args[1] + " does not exist!");
                    return;
                }
                Group g = fpc.config.getDb().getGroup(args[1]);
                if (g.getInheritance().contains(args[2]) == false) {
                    sender.sendChatToPlayer(FontColour.RED + "Sorry the group " + args[1]
                            + " does not inherit" + args[2]);
                    return;
                }
                g.getInheritance().remove(args[2]);
                fpc.config.getDb().saveGroups();
                sender.sendChatToPlayer(FontColour.DARK_GREEN + "Group " + args[1] + " no longer inherits"
                        + args[2] + "!");
            } else {
                sender.sendChatToPlayer(FontColour.RED + "You do not have permission to use this command.");
            }
        } else {
            sender.sendChatToPlayer(FontColour.RED + "Usage: /group removeInherit [groupName] [inheritGroup]");
        }
    }
}
