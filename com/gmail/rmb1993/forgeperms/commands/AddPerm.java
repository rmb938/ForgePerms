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
    
    public AddPerm(ICommandSender sender, String[] args) {
        this.sender = sender;
        this.args = args;
    }
    
    public void user() {
        if (args.length == 3) {
            User u = ForgePermsContainer.instance.config.getDb().loadUser(sender.getCommandSenderName());
            if (ForgePermsAPI.playerHasPermission(u.getUserName(), "permissions.adduserperm")) {
                User u1 = ForgePermsContainer.instance.config.getDb().loadUser(args[1]);
                ArrayList<String> world = new ArrayList();
                world.add("global");
                u1.getPermissions().put(args[2], world);
                sender.sendChatToPlayer("You added the permission "+args[2]+" to user "+args[1]);
            } else {
                sender.sendChatToPlayer("You do not have permission to use this command.");
            }
        }
    }
    
}
