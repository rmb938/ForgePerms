package com.gmail.rmb1993.forgeperms.commands;

import com.gmail.rmb1993.forgeperms.ForgePermsPlugin;
import com.gmail.rmb1993.forgeperms.ForgePermsContainer;
import com.gmail.rmb1993.forgeperms.api.ForgePermsAPI;
import com.gmail.rmb1993.forgeperms.permissions.user.User;
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
            if (ForgePermsAPI.playerHasPermission(u.getUserName(), "permissions.adduserperm")) {
                User u1 = fpc.config.getDb().getUser(args[1]);
                ArrayList<String> world = new ArrayList();
                world.add("global");

                if (fpc.customNodes.containsKey(args[2])) {
                    u1.getCustomPermissions().put(args[2], world);
                } else {
                    u1.getPermissions().put(args[2], world);
                }
                sender.sendChatToPlayer("You added the permission " + args[2] + " to user " + args[1]);
                fpc.config.getDb().saveUsers();
            } else {
                sender.sendChatToPlayer("You do not have permission to use this command.");
            }
        }
    }

    void group() {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
