package com.gmail.rmb1993.forgeperms.commands;

import com.gmail.rmb1993.forgeperms.ForgePerms;
import com.gmail.rmb1993.forgeperms.api.ForgePermsAPI;
import com.gmail.rmb1993.forgeperms.permissions.user.User;
import net.minecraft.command.ICommandSender;

public class RemovePerm {
    
    private ICommandSender sender;
    private String[] args;
    
    public RemovePerm(ICommandSender sender, String[] args) {
        this.sender = sender;
        this.args = args;
    }
    
    public void user() {
        if (args.length == 3) {
            User u = ForgePerms.instance.users.get(sender.getCommandSenderName());
            if (ForgePermsAPI.playerHasPermission(u.getUserName(), "permissions.adduserperm")) {
                User u1 = ForgePerms.instance.users.get(args[1]);
                //TODO setup multiworld
                u1.getPermissions().remove(args[2]);
                sender.sendChatToPlayer("You removed the permission "+args[2]+" from user "+args[1]);
            } else {
                sender.sendChatToPlayer("You do not have permission to use this command.");
            }
        }
    }

}
