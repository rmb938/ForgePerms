package com.gmail.rmb1993.forgeperms.commands;

import com.gmail.rmb1993.forgeperms.commands.groups.*;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;

public class GroupCommand extends CommandBase {

    @Override
    public String getCommandName() {
        return "group";
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender par1ICommandSender) {
        return true;
    }
    
    @Override
    public void processCommand(ICommandSender var1, String[] var2) {
        if (var2.length >= 1) {
            String command = var2[0];
            if (command.equalsIgnoreCase("add")) {
                new Add(var1, var2);
            } else if (command.equalsIgnoreCase("remove")) {
                new Remove(var1, var2);
            } else if (command.equalsIgnoreCase("setRank")) {
                new SetRank(var1, var2);
            } else if (command.equalsIgnoreCase("setTrack")) {
                new SetTrack(var1, var2);
            } else if (command.equalsIgnoreCase("addPerm")) {
                new AddPerm(var1, var2).group();
            } else if (command.equalsIgnoreCase("removePerm")) {
                new RemovePerm(var1, var2).group();
            } else if (command.equalsIgnoreCase("setPrefix")) {
                new SetGroupPrefix(var1, var2);
            } else if (command.equalsIgnoreCase("removePrefix")) {
                new RemoveGroupPrefix(var1, var2);
            } else if (command.equalsIgnoreCase("setSuffix")) {
                new SetGroupSuffix(var1, var2);
            } else if (command.equalsIgnoreCase("removeSuffix")) {
                new RemoveGroupSuffix(var1, var2);
            } else if (command.equalsIgnoreCase("listGroups")) {
                new ListGroups(var1, var2);
            } else if (command.equalsIgnoreCase("listPerms")) {
                new ListGroupPerms(var1, var2);
            } else if (command.equalsIgnoreCase("addInherit")) {
                new AddInherit(var1, var2);
            } else if (command.equalsIgnoreCase("removeInherit")) {
                new RemoveInherit(var1, var2);
            } else if (command.equalsIgnoreCase("help")) {
                new GroupHelp(var1, var2);
            }
        } else {
            var1.sendChatToPlayer("Usage: /group help");
        }
    }

}
