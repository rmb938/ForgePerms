package com.gmail.rmb1993.forgeperms.commands.user;

import com.gmail.rmb1993.forgeperms.ForgePermsPlugin;
import com.gmail.rmb1993.forgeperms.ForgePermsContainer;
import com.gmail.rmb1993.forgeperms.api.ForgePermsAPI;
import com.gmail.rmb1993.forgeperms.permissions.group.Group;
import com.gmail.rmb1993.forgeperms.permissions.user.User;
import com.gmail.rmb1993.forgeperms.utils.StringColors;
import net.minecraft.command.ICommandSender;

/**
 *
 * @author Ryan
 */
public class RemoveGroup {
    
    public RemoveGroup(ForgePermsContainer fpc, ICommandSender sender, String[] args) {
        if (args.length == 3) {
            User u = fpc.config.getDb().getUser(sender.getCommandSenderName());
            if (ForgePermsAPI.playerHasPermission(u.getUserName(), "permissions.removeUserGroup")) {
                User u1 = fpc.config.getDb().getUser(args[1]);
                if (u1 == null) {
                    sender.sendChatToPlayer(StringColors.EnumTextColor.RED.colorString("Sorry the user "+args[1]+" does not exist!"));
                    return;
                }
                Group g = fpc.config.getDb().getGroup(args[2]);
                if (g == null) {
                    sender.sendChatToPlayer(StringColors.EnumTextColor.RED.colorString("Sorry the group "+args[2]+" does not exist!"));
                    return;
                }
                if (u1.getGroups().contains(g.getGroupName()) == false) {
                    sender.sendChatToPlayer(StringColors.EnumTextColor.RED.colorString("User "+args[1]+" is not in group "+args[2]));
                    return;
                }
                u1.getGroups().remove(g.getGroupName());
                fpc.config.getDb().saveUsers();
                sender.sendChatToPlayer(StringColors.EnumTextColor.DARK_GREEN.colorString("You removed "+args[1]+" from group "+args[2]));
            } else {
                sender.sendChatToPlayer(StringColors.EnumTextColor.RED.colorString("You do not have permission to use this command."));
            }
        } else {
            sender.sendChatToPlayer(StringColors.EnumTextColor.RED.colorString("Usage: /user removeGroup [userName] [groupName]"));
        }
    }

}
