package com.gmail.rmb1993.forgeperms.commands;

import com.gmail.rmb1993.forgeperms.ForgePermsContainer;
import com.gmail.rmb1993.forgeperms.api.ForgePermsAPI;
import com.gmail.rmb1993.forgeperms.permissions.group.Group;
import com.gmail.rmb1993.forgeperms.permissions.user.User;
import com.gmail.rmb1993.forgeperms.utils.FontColour;
import net.minecraft.command.ICommandSender;

public class RemovePerm {

    private ICommandSender sender;
    private String[] args;
    private ForgePermsContainer fpc;

    public RemovePerm(ForgePermsContainer fpc, ICommandSender sender, String[] args) {
        this.fpc = fpc;
        this.sender = sender;
        this.args = args;
    }

    public void user() {
        if (args.length == 3) {
            User u = fpc.config.getDb().getUser(sender.getCommandSenderName());
            if (ForgePermsAPI.playerHasPermission(u.getUserName(), "permissions.delUserPerm")) {
                User u1 = fpc.config.getDb().getUser(args[1]);
                //TODO setup multiworld

                if (fpc.customNodes.containsKey(args[2])) {
                    u1.getCustomPermissions().remove(args[2]);
                } else {
                    u1.getPermissions().remove(args[2]);
                }
                sender.sendChatToPlayer(FontColour.DARK_GREEN + "You removed the permission " + args[2] + " from user " + args[1]);
                fpc.config.getDb().saveUsers();
            } else {
                sender.sendChatToPlayer(FontColour.RED + "You do not have permission to use this command.");
            }
        }
    }

    public void group() {
        if (args.length == 3) {
            User u = fpc.config.getDb().getUser(sender.getCommandSenderName());
            if (ForgePermsAPI.playerHasPermission(u.getUserName(), "permission.delUserPerm")) {
                Group g = fpc.config.getDb().getGroup(args[1]);

                if (fpc.customNodes.containsKey(args[2])) {
                    g.getCustomPermissions().remove(args[2]);
                } else {
                    g.getPermissions().remove(args[2]);
                }
                sender.sendChatToPlayer(FontColour.DARK_GREEN + "You removed the permission " + args[2] + " from group " + args[1]);
                fpc.config.getDb().saveGroups();
            } else {
                sender.sendChatToPlayer(FontColour.RED + "You do not have permission to use this command.");
            }
        }
    }
}
