package com.gmail.rmb1993.forgeperms.commands;

import com.gmail.rmb1993.forgeperms.api.ForgePermsAPI;
import com.gmail.rmb1993.forgeperms.commands.user.*;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;

/**
 *
 * @author Ryan
 */
public class UserCommand extends CommandBase  {

    @Override
    public String getCommandName() {
        return "user";
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender par1ICommandSender) {
        return true;
    }
    
    @Override
    public void processCommand(ICommandSender var1, String[] var2) {
        if (var2.length >= 1) {
            String command = var2[0];
            if (command.equalsIgnoreCase("promote")) {
                new Promote(var1, var2);
            } else if (command.equalsIgnoreCase("demote")) {
                new Demote(var1, var2);
            } else if (command.equalsIgnoreCase("addGroup")) {
                new AddGroup(var1, var2);
            } else if (command.equalsIgnoreCase("removeGroup")) {
                new RemoveGroup(var1, var2);
            } else if (command.equalsIgnoreCase("addPerm")) {
                new AddPerm(var1, var2).user();
            } else if (command.equalsIgnoreCase("removePerm")) {
                new RemovePerm(var1, var2).user();
            } else if (command.equalsIgnoreCase("setSuffix")) {
                new SetUserSuffix(var1, var2);
            } else if (command.equalsIgnoreCase("removeSuffix")) {
                new RemoveUserSuffix(var1, var2);
            } else if (command.equalsIgnoreCase("setPrefix")) {
                new SetUserPrefix(var1, var2);
            } else if (command.equalsIgnoreCase("removePrefix")) {
                new RemoveUserPrefix(var1,var2);
            } else if (command.equalsIgnoreCase("listGroups")) {
                new ListUserGroups(var1, var2);
            } else if (command.equalsIgnoreCase("listPerms")) {
                new ListUserPerms(var1, var2);
            } else if (command.equalsIgnoreCase("listUsers")) {
                new ListUsers(var1, var2);
            } else if (command.equalsIgnoreCase("help")) {
                new UserHelp(var1, var2);
            }
        } else {
            var1.sendChatToPlayer("Usage: /user help");
        }
    }

}
