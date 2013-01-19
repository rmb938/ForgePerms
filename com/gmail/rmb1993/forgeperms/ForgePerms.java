package com.gmail.rmb1993.forgeperms;

import com.gmail.rmb1993.forgeperms.commands.override.Kick;
import com.gmail.rmb1993.forgeperms.config.Configuration;
import com.gmail.rmb1993.forgeperms.permissions.Permission;
import com.gmail.rmb1993.forgeperms.permissions.group.Group;
import com.gmail.rmb1993.forgeperms.permissions.group.Track;
import com.gmail.rmb1993.forgeperms.permissions.user.User;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.Mod.ServerStarting;
import cpw.mods.fml.common.Mod.ServerStopping;
import cpw.mods.fml.common.event.*;
import cpw.mods.fml.common.network.NetworkMod;
import java.util.ArrayList;
import java.util.HashMap;
import net.minecraft.command.ICommandManager;
import net.minecraft.command.ServerCommandManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.src.ModLoader;

//////////////////
// Date: 1/12/2013
//////////////////
@NetworkMod(clientSideRequired = false, serverSideRequired = false)
@Mod(modid = "ForgePerms", name = "Forge Permissions", version = "0.1")
public class ForgePerms {

    @Instance(value = "ForgePerms")
    public static ForgePerms instance;
    
    public Configuration config;
    
    public HashMap<String, User> users = new HashMap();
    public HashMap<String, Group> groups = new HashMap();
    public HashMap<String, Permission> permissions = new HashMap();
    public ArrayList<Track> tracks = new ArrayList();
    
    @PreInit
    public void preInit(FMLPreInitializationEvent event) {
        instance = this;
        System.out.println("Forge Perms Loaded");
        config = new Configuration();
        
        Permission perm = new Permission();
        perm.setPermission("test.permission");
        permissions.put("test.permission", perm);
        
        config.setUpConfig(event.getModConfigurationDirectory());
        config.getDb().loadUser("rmb");
    }
    @Init
    public void load(FMLInitializationEvent e) {
    }

    @PostInit
    public void postLoad(FMLPostInitializationEvent e) {
    }

    @ServerStarting
    public void serverStarting(FMLServerStartingEvent e) {
        MinecraftServer server = ModLoader.getMinecraftServerInstance();
        ICommandManager commandManager = server.getCommandManager();
        ServerCommandManager serverCommandManager = ((ServerCommandManager) commandManager); 
        serverCommandManager.registerCommand(new Kick());
    }

    @ServerStopping
    public void serverStopping(FMLServerStoppingEvent e) {
    }
}