package com.gmail.rmb1993.forgeperms.event;

import com.gmail.rmb1993.forgeperms.ForgePermsContainer;
import com.gmail.rmb1993.forgeperms.permissions.user.User;
import cpw.mods.fml.common.IPlayerTracker;
import net.minecraft.entity.player.EntityPlayer;

/**
 *
 * @author Ryan
 */
public class PlayerTrackerHook implements IPlayerTracker {

    @Override
    public void onPlayerLogin(EntityPlayer player) {
        User u = ForgePermsContainer.instance.config.getDb().getUser(player.getEntityName());
        if (u == null) {
            ForgePermsContainer.instance.config.getDb().createUser(player.getEntityName());
            
        }
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
