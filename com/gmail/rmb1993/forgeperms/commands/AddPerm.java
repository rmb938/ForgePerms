package com.gmail.rmb1993.forgeperms.commands;

import com.gmail.rmb1993.forgeperms.ForgePermsPlugin;
import com.gmail.rmb1993.forgeperms.ForgePermsContainer;
import com.gmail.rmb1993.forgeperms.api.ForgePermsAPI;
import com.gmail.rmb1993.forgeperms.permissions.group.Group;
import com.gmail.rmb1993.forgeperms.permissions.user.User;
import com.gmail.rmb1993.forgeperms.utils.FontColour;
import java.util.ArrayList;
import net.minecraft.command.ICommandSender;

public class AddPerm {

    private ICommandSender sender;
    private String[] args;
    private ForgePermsContainer fpc;

    public AddPerm(ForgePermsContainer fpc, ICommandSender sender, String[] args) {
        this.fpc = fpc;
        this.sender = sender;
        this.args = args;
    }

    public void user() {
        if (args.length == 3) {
            User u = fpc.config.getDb().getUser(sender.getCommandSenderName());
            if (ForgePermsAPI.playerHasPermission(u.getUserName(), "permissions.addUserPerm")) {
                User u1 = fpc.config.getDb().getUser(args[1]);
                ArrayList<String> world = new ArrayList();
                world.add("global");

                if (fpc.customNodes.containsKey(args[2].toLowerCase())) {
                    u1.getCustomPermissions().put(args[2].toLowerCase(), world);
                } else {
                    u1.getPermissions().put(args[2].toLowerCase(), world);
                }
                sender.sendChatToPlayer(FontColour.DARK_GREEN + "You added the permission " + args[2] + " to user " + args[1]);
                fpc.config.getDb().saveUsers();
            } else {
                sender.sendChatToPlayer(FontColour.RED + "You do not have permission to use this command.");
            }
        }
    }

    public void group() {
        if (args.length == 3) {
            User u = fpc.config.getDb().getUser(sender.getCommandSenderName());
            if (ForgePermsAPI.playerHasPermission(u.getUserName(), "permission.addGroupPerm")) {
                Group g = fpc.config.getDb().getGroup(args[1]);
                ArrayList<String> world = new ArrayList();
                world.add("global");

                if (fpc.customNodes.containsKey(args[2].toLowerCase())) {
                    g.getCustomPermissions().put(args[2].toLowerCase(), world);
                } else {
                    g.getPermissions().put(args[2].toLowerCase(), world);
                }
                sender.sendChatToPlayer(FontColour.DARK_GREEN + "You added the permission " + args[2] + " to group " + args[1]);
                fpc.config.getDb().saveGroups();
            } else {
                sender.sendChatToPlayer(FontColour.RED + "You do not have permission to use this command.");
            }
        }
    }
}
