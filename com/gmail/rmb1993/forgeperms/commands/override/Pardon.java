package com.gmail.rmb1993.forgeperms.commands.override;

import com.gmail.rmb1993.forgeperms.api.ForgePermsAPI;
import net.minecraft.command.CommandServerPardon;
import net.minecraft.command.ICommandSender;

/**
 *
 * @author Ryan
 */
public class Pardon extends CommandServerPardon {

    @Override
    public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr) {
        super.processCommand(par1ICommandSender, par2ArrayOfStr);
    }
    
    @Override
    public boolean canCommandSenderUseCommand(ICommandSender par1ICommandSender) {
        if (ForgePermsAPI.playerHasPermission(par1ICommandSender.getCommandSenderName(), "minecraft.pardon")) {
            return true;
        }
        return false;
    }
    
}
