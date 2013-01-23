package com.gmail.rmb1993.forgeperms.commands.user;

import com.gmail.rmb1993.forgeperms.ForgePermsContainer;
import net.minecraft.command.ICommandSender;

/**
 *
 * @author Ryan
 */
public class UserHelp {

    public UserHelp(ForgePermsContainer fpc, ICommandSender sender, String[] args) {
        
        sender.sendChatToPlayer("ForgePerms User Command Help");
        sender.sendChatToPlayer("----------");
        
        sender.sendChatToPlayer("/user promote [userName] [track] - promotes a user along the selected track");
        sender.sendChatToPlayer("/user demote [userName] [track] - demotes a user along the selected track");
        sender.sendChatToPlayer("/user addGroup [userName] [groupName] - adds a user to the selected group");
        sender.sendChatToPlayer("/user removeGroup [userName] [groupName] - removes a user from the selected group");
        sender.sendChatToPlayer("/user addPerm [userName] [permission] - adds the selected permission to the user");
        sender.sendChatToPlayer("/user removePerm [userName] [permission] - removed the selected permission from the user");
        sender.sendChatToPlayer("/user setSuffix [userName] [suffix] - sets the suffix for the user");
        sender.sendChatToPlayer("/user removeSuffix [userName] - removes the suffix from the user");
        sender.sendChatToPlayer("/user setPrefix [userName] [prefix] - sets the prefix for the user");
        sender.sendChatToPlayer("/user removePrefix [userName] - removes the prefix from the user");
        sender.sendChatToPlayer("/user listGroups [userName] - lists the groups the user is in");
        sender.sendChatToPlayer("/user listPerms [userName] - lists the user's permissions");
        sender.sendChatToPlayer("/user listUsers - lists all the users");
    }
}
