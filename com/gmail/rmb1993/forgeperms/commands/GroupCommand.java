package com.gmail.rmb1993.forgeperms.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;

/**
 *
 * @author Ryan
 */
public class GroupCommand extends CommandBase {

    @Override
    public String getCommandName() {
        return "group";
    }

    @Override
    public void processCommand(ICommandSender var1, String[] var2) {
        if (var2.length >= 1) {
            String command = var2[0];
            if (command.equalsIgnoreCase("add")) {
                
            } else if (command.equalsIgnoreCase("remove")) {
                
            } else if (command.equalsIgnoreCase("setRank")) {
                
            } else if (command.equalsIgnoreCase("setTrack")) {
                
            } else if (command.equalsIgnoreCase("addPerm")) {
                
            } else if (command.equalsIgnoreCase("removePerm")) {
                
            } else if (command.equalsIgnoreCase("setPrefix")) {
                
            } else if (command.equalsIgnoreCase("removePrefix")) {
                
            } else if (command.equalsIgnoreCase("setSuffix")) {
                
            } else if (command.equalsIgnoreCase("removeSuffix")) {
                
            } else if (command.equalsIgnoreCase("listGroups")) {
                
            } else if (command.equalsIgnoreCase("listPerms")) {
                
            } else if (command.equalsIgnoreCase("addInherit")) {
                
            } else if (command.equalsIgnoreCase("removeInherit")) {
                
            }
        } else {
            var1.sendChatToPlayer("Usage: /group help");
        }
    }

}
