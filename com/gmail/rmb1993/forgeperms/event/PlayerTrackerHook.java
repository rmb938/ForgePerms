package com.gmail.rmb1993.forgeperms.event;

import cpw.mods.fml.common.IPlayerTracker;
import net.minecraft.entity.player.EntityPlayer;

/**
 *
 * @author Ryan
 */
public class PlayerTrackerHook implements IPlayerTracker {

    @Override
    public void onPlayerLogin(EntityPlayer player) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void onPlayerLogout(EntityPlayer player) {
       // throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void onPlayerChangedDimension(EntityPlayer player) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void onPlayerRespawn(EntityPlayer player) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

}
