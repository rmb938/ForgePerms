package com.gmail.rmb1993.forgeperms.commands.override;

import com.gmail.rmb1993.forgeperms.api.ForgePermsAPI;
import net.minecraft.command.CommandServerKick;
import net.minecraft.command.ICommandSender;

public class Kick extends CommandServerKick {
	
    @Override
    public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr) {
        if (ForgePermsAPI.hasPermission("rmb", "test.permission")) {
            System.out.println("Good!");
        } else {
            System.out.println("Bad!");
        }
        super.processCommand(par1ICommandSender, par2ArrayOfStr);
    }

}
