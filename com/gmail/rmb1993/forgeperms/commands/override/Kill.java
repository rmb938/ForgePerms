package com.gmail.rmb1993.forgeperms.commands.override;

import com.gmail.rmb1993.forgeperms.api.ForgePermsAPI;
import net.minecraft.command.CommandKill;
import net.minecraft.command.ICommandSender;

/**
 *
 * @author Ryan
 */
public class Kill extends CommandKill {
    
    @Override
    public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr) {
        super.processCommand(par1ICommandSender, par2ArrayOfStr);
    }
    
    @Override
    public boolean canCommandSenderUseCommand(ICommandSender par1ICommandSender) {
        if (ForgePermsAPI.playerHasPermission(par1ICommandSender.getCommandSenderName(), "minecraft.kill")) {
            return true;
        }
        return false;
    }

}
