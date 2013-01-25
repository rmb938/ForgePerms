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
public class GroupHelp {

    public GroupHelp(ForgePermsContainer fpc, ICommandSender sender, String[] args) {
        User u = fpc.config.getDb().getUser(sender.getCommandSenderName());
        if (ForgePermsAPI.playerHasPermission(u.getUserName(), "permission.helpGroup")
                || ForgePermsAPI.playerHasPermission(u.getUserName(), "permission.help")) {
            sender.sendChatToPlayer("ForgePerms Group Command Help");
            sender.sendChatToPlayer("----------");

            sender.sendChatToPlayer("/group add [groupName] - add a group");
            sender.sendChatToPlayer("/group addPerm [groupName] [permission]");
            sender.sendChatToPlayer("/group addInherit [groupName] [inheritGroup] - add group inheritance");
            sender.sendChatToPlayer("/group listPerms [groupName] - list the group's permissions");
            sender.sendChatToPlayer("/group listGroups - list all the groups");
            sender.sendChatToPlayer("/group remove [groupName] - remove a group");
            sender.sendChatToPlayer("/group removePrefix [groupName] - remove a group's prefix");
            sender.sendChatToPlayer("/group removeSuffix [groupName] - remove a group's suffix");
            sender.sendChatToPlayer("/group removeInherit [groupName] [inheritGroup] - remove group inheritance");
            sender.sendChatToPlayer("/group setPrefix [groupName] [prefix] - set a group's prefix");
            sender.sendChatToPlayer("/group setSuffix [groupName] [prefix] - set a group's suffix");
            sender.sendChatToPlayer("/group setRank [groupName] [rank] - set a group's rank");
            sender.sendChatToPlayer("/group setTrack [groupName] [track] - set a group's track");
        } else {
            sender.sendChatToPlayer(FontColour.RED + "You do not have permission to use this command.");
        }
    }
}
