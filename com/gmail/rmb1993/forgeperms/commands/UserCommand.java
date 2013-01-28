package com.gmail.rmb1993.forgeperms.commands;

import com.gmail.rmb1993.forgeperms.ForgePermsContainer;
import com.gmail.rmb1993.forgeperms.api.ForgePermsAPI;
import com.gmail.rmb1993.forgeperms.commands.user.*;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;

/**
 *
 * @author Ryan
 */
public class UserCommand extends CommandBase  {

    private ForgePermsContainer fpc;
    
    public UserCommand(ForgePermsContainer fpc) {
        super();
        this.fpc = fpc;
    }
    
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
                new Promote(fpc, var1, var2);
            } else if (command.equalsIgnoreCase("demote")) {
                new Demote(fpc, var1, var2);
            } else if (command.equalsIgnoreCase("addGroup")) {
                new AddGroup(fpc, var1, var2);
            } else if (command.equalsIgnoreCase("removeGroup")) {
                new RemoveGroup(fpc, var1, var2);
            } else if (command.equalsIgnoreCase("setGroup")) {
            	new SetGroup(fpc, var1, var2);
            } else if (command.equalsIgnoreCase("addPerm")) {
                new AddPerm(fpc, var1, var2).user();
            } else if (command.equalsIgnoreCase("removePerm")) {
                new RemovePerm(fpc, var1, var2).user();
            } else if (command.equalsIgnoreCase("setSuffix")) {
                new SetUserSuffix(fpc, var1, var2);
            } else if (command.equalsIgnoreCase("removeSuffix")) {
                new RemoveUserSuffix(fpc, var1, var2);
            } else if (command.equalsIgnoreCase("setPrefix")) {
                new SetUserPrefix(fpc, var1, var2);
            } else if (command.equalsIgnoreCase("removePrefix")) {
                new RemoveUserPrefix(fpc, var1,var2);
            } else if (command.equalsIgnoreCase("listGroups")) {
                new ListUserGroups(fpc, var1, var2);
            } else if (command.equalsIgnoreCase("listPerms")) {
                new ListUserPerms(fpc, var1, var2);
            } else if (command.equalsIgnoreCase("listUsers")) {
                new ListUsers(fpc, var1, var2);
            } else if (command.equalsIgnoreCase("help")) {
                new UserHelp(fpc, var1, var2);
            } else {
                var1.sendChatToPlayer("Usage: /user help");
            }
        } else {
            var1.sendChatToPlayer("Usage: /user help");
        }
    }

}
