package com.gmail.rmb1993.forgeperms.commands.override;

import com.gmail.rmb1993.forgeperms.api.ForgePermsAPI;
import net.minecraft.command.CommandEnchant;
import net.minecraft.command.ICommandSender;

/**
 *
 * @author Ryan
 */
public class Enchant extends CommandEnchant {
    
    @Override
    public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr) {
        super.processCommand(par1ICommandSender, par2ArrayOfStr);
    }
    
    @Override
    public boolean canCommandSenderUseCommand(ICommandSender par1ICommandSender) {
        if (ForgePermsAPI.playerHasPermission(par1ICommandSender.getCommandSenderName(), "minecraft.enchant")) {
            return true;
        }
        return false;
    }

}
