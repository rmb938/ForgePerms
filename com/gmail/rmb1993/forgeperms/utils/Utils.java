package com.gmail.rmb1993.forgeperms.utils;

import java.util.ArrayList;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.src.ModLoader;
import net.minecraft.world.WorldServer;

/**
 *
 * @author Ryan
 */
public class Utils {

    public static EntityPlayerMP getPlayer(String name) {
        ArrayList<EntityPlayerMP> players = new ArrayList();
        for (WorldServer ws : ModLoader.getMinecraftServerInstance().worldServers) {
            players.addAll(ws.playerEntities);
        }
        for (EntityPlayerMP epmp : players) {
            if (epmp.username.equalsIgnoreCase(name)) {
                return epmp;
            }
        }
        return null;
    }
}
