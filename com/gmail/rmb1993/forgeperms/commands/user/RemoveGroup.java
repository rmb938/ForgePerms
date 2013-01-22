package com.gmail.rmb1993.forgeperms.commands.user;

import com.gmail.rmb1993.forgeperms.ForgePermsPlugin;
import com.gmail.rmb1993.forgeperms.ForgePermsContainer;
import com.gmail.rmb1993.forgeperms.api.ForgePermsAPI;
import com.gmail.rmb1993.forgeperms.permissions.group.Group;
import com.gmail.rmb1993.forgeperms.permissions.user.User;
import net.minecraft.command.ICommandSender;

/**
 *
 * @author Ryan
 */
public class RemoveGroup {
    
    public RemoveGroup(ICommandSender sender, String[] args) {
        if (args.length == 3) {
            User u = ForgePermsContainer.instance.config.getDb().loadUser(sender.getCommandSenderName());
            if (ForgePermsAPI.playerHasPermission(u.getUserName(), "permissions.removeGroup")) {
                User u1 = ForgePermsContainer.instance.config.getDb().loadUser(args[1]);
                Group g = ForgePermsContainer.instance.config.getDb().loadGroup(args[2]);
                if (g == null) {
                    sender.sendChatToPlayer("Sorry the group "+args[2]+" does not exist!");
                    return;
                }
                if (u1.getGroups().contains(g.getGroupName()) == false) {
                    sender.sendChatToPlayer("User "+args[1]+" is not in group "+args[2]);
                    return;
                }
                u1.getGroups().remove(g.getGroupName());
                ForgePermsContainer.instance.config.getDb().saveUsers();
                sender.sendChatToPlayer("You removed "+args[1]+" from group "+args[2]);
            } else {
                sender.sendChatToPlayer("You do not have permission to use this command.");
            }
        } else {
            sender.sendChatToPlayer("Usage: /user removeGroup [userName] [groupName]");
        }
    }

}
