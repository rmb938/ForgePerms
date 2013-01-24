package com.gmail.rmb1993.forgeperms.commands.override;

import com.gmail.rmb1993.forgeperms.api.ForgePermsAPI;
import net.minecraft.command.CommandServerKick;
import net.minecraft.command.ICommandSender;

public class Kick extends CommandServerKick {
	
    @Override
    public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr) {
        super.processCommand(par1ICommandSender, par2ArrayOfStr);
    }
    
    @Override
    public boolean canCommandSenderUseCommand(ICommandSender par1ICommandSender) {
        if (ForgePermsAPI.playerHasPermission(par1ICommandSender.getCommandSenderName(), "minecraft.kick")) {
            return true;
        }
        return false;
    }

}
