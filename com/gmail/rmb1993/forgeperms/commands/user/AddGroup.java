package com.gmail.rmb1993.forgeperms.commands.user;

import java.util.ArrayList;

import com.gmail.rmb1993.forgeperms.ForgePermsContainer;
import com.gmail.rmb1993.forgeperms.api.ForgePermsAPI;
import com.gmail.rmb1993.forgeperms.permissions.group.Group;
import com.gmail.rmb1993.forgeperms.permissions.user.User;
import com.gmail.rmb1993.forgeperms.utils.FontColour;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.src.ModLoader;
import net.minecraft.world.WorldServer;

/**
 *
 * @author Ryan
 */
public class AddGroup {

    public AddGroup(ForgePermsContainer fpc, ICommandSender sender, String[] args) {
        if (args.length == 3) {
            User u = fpc.config.getDb().getUser(sender.getCommandSenderName());
            if (ForgePermsAPI.playerHasPermission(u.getUserName(), "permissions.addUserGroup")) {
                User u1 = fpc.config.getDb().getUser(args[1]);
                if (u1 == null) {
                    sender.sendChatToPlayer(FontColour.RED + "Sorry the user "+args[1]+" does not exist!");
                    return;
                }
                Group g = fpc.config.getDb().getGroup(args[2]);
                if (g == null) {
                    sender.sendChatToPlayer(FontColour.RED + "Sorry the group "+args[2]+" does not exist!");
                    return;
                }
                if (u1.getGroups().contains(g.getGroupName())) {
                    sender.sendChatToPlayer(FontColour.RED + "User "+args[1]+" is already in group "+args[2]);
                    return;
                }
                u1.getGroups().add(g.getGroupName());
                fpc.config.getDb().saveUsers();
                sender.sendChatToPlayer(FontColour.DARK_GREEN + "You added "+args[1]+" to group "+args[2]);
                
                if (fpc.config.isMessagePromote() == true) {
                	ArrayList<EntityPlayerMP> players = new ArrayList();
                	for (WorldServer ws : ModLoader.getMinecraftServerInstance().worldServers) {
                		players.addAll(ws.playerEntities);
                	}
            		for (EntityPlayerMP epmp : players) {
            			if (epmp.username.equalsIgnoreCase(u1.getUserName())) {
            				epmp.sendChatToPlayer("You have been added to group " + args[2]);
            			}
            		}
                }
            } else {
                sender.sendChatToPlayer(FontColour.RED + "You do not have permission to use this command.");
            }
        } else {
            sender.sendChatToPlayer(FontColour.RED + "Usage: /user addGroup [userName] [groupName]");
        }
    }
}
