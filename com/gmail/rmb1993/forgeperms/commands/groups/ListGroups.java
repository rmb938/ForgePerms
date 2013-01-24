package com.gmail.rmb1993.forgeperms.commands.groups;

import com.gmail.rmb1993.forgeperms.ForgePermsContainer;
import com.gmail.rmb1993.forgeperms.api.ForgePermsAPI;
import com.gmail.rmb1993.forgeperms.permissions.group.Group;
import com.gmail.rmb1993.forgeperms.permissions.user.User;
import com.gmail.rmb1993.forgeperms.utils.StringColors;

import net.minecraft.command.ICommandSender;

/**
 *
 * @author Favorlock
 *
 */
public class ListGroups {

    public ListGroups(ForgePermsContainer fpc, ICommandSender sender, String[] args) {
        User u = fpc.config.getDb().getUser(sender.getCommandSenderName());
        if (ForgePermsAPI.playerHasPermission(u.getUserName(), "permissions.listGroups")) {
            sender.sendChatToPlayer("Groups: ");
            for (Group g : fpc.groups.values()) {
                sender.sendChatToPlayer(g.getGroupName());
            }
        } else {
            sender.sendChatToPlayer(StringColors.EnumTextColor.RED.colorString("You do not have permission to use this command."));
        }
    }
}
