package com.gmail.rmb1993.forgeperms.commands;

import com.gmail.rmb1993.forgeperms.ForgePermsContainer;
import com.gmail.rmb1993.forgeperms.commands.groups.*;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;

public class GroupCommand extends CommandBase {

    private ForgePermsContainer fpc;
    
    public GroupCommand(ForgePermsContainer fpc) {
        super();
        this.fpc = fpc;
    }
    
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
                new Add(fpc, var1, var2);
            } else if (command.equalsIgnoreCase("remove")) {
                new Remove(fpc, var1, var2);
            } else if (command.equalsIgnoreCase("setRank")) {
                new SetRank(fpc, var1, var2);
            } else if (command.equalsIgnoreCase("setTrack")) {
                new SetTrack(fpc, var1, var2);
            } else if (command.equalsIgnoreCase("addPerm")) {
                new AddPerm(fpc, var1, var2).group();
            } else if (command.equalsIgnoreCase("removePerm")) {
                new RemovePerm(fpc, var1, var2).group();
            } else if (command.equalsIgnoreCase("setPrefix")) {
                new SetGroupPrefix(fpc, var1, var2);
            } else if (command.equalsIgnoreCase("removePrefix")) {
                new RemoveGroupPrefix(fpc, var1, var2);
            } else if (command.equalsIgnoreCase("setSuffix")) {
                new SetGroupSuffix(fpc, var1, var2);
            } else if (command.equalsIgnoreCase("removeSuffix")) {
                new RemoveGroupSuffix(fpc, var1, var2);
            } else if (command.equalsIgnoreCase("listGroups")) {
                new ListGroups(fpc, var1, var2);
            } else if (command.equalsIgnoreCase("listPerms")) {
                new ListGroupPerms(fpc, var1, var2);
            } else if (command.equalsIgnoreCase("addInherit")) {
                new AddInherit(fpc, var1, var2);
            } else if (command.equalsIgnoreCase("removeInherit")) {
                new RemoveInherit(fpc, var1, var2);
            } else if (command.equalsIgnoreCase("help")) {
                new GroupHelp(fpc, var1, var2);
            } else {
                var1.sendChatToPlayer("Usage: /group help");
            }
        } else {
            var1.sendChatToPlayer("Usage: /group help");
        }
    }

}
