package com.gmail.rmb1993.forgeperms;

import com.gmail.rmb1993.forgeperms.commands.override.*;
import com.gmail.rmb1993.forgeperms.config.Configuration;
import com.gmail.rmb1993.forgeperms.permissions.Permission;
import com.gmail.rmb1993.forgeperms.permissions.PermissionType;
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
import java.util.ArrayList;
import java.util.HashMap;
import net.minecraft.command.ICommandManager;
import net.minecraft.command.ServerCommandManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.src.ModLoader;

@Mod(modid = "ForgePerms", name = "Forge Permissions", version = "0.1")
public class ForgePerms {

    @Instance(value = "ForgePerms")
    public static ForgePerms instance;
    
    public Configuration config;
    
    public HashMap<String, User> users = new HashMap();
    public HashMap<String, Group> groups = new HashMap();
    public HashMap<String, Permission> permissions = new HashMap();
    public HashMap<String, Track> tracks = new HashMap();
    
    @PreInit
    public void preInit(FMLPreInitializationEvent event) {
        instance = this;
        config = new Configuration();
        
        config.setUpConfig(event.getModConfigurationDirectory());
        
        loadVanillaPerms();
        loadPermissionPerms();
    }
    @Init
    public void load(FMLInitializationEvent e) {
        System.out.println("Forge Perms Loaded");
    }

    @PostInit
    public void postLoad(FMLPostInitializationEvent e) {
        //post load things?
    }

    public void loadVanillaPerms() {
        permissions.put("minecraft.ban", new Permission("minecraft.ban", PermissionType.OP));
        permissions.put("minecraft.banip", new Permission("minecraft.banip", PermissionType.OP));
        permissions.put("minecraft.banlist", new Permission("minecraft.banlist", PermissionType.OP));
        permissions.put("minecraft.clear", new Permission("minecraft.clear", PermissionType.OP));
        permissions.put("minecraft.deop", new Permission("minecraft.deop", PermissionType.OP));
        permissions.put("minecraft.debug", new Permission("minecraft.debug", PermissionType.OP));
        permissions.put("minecraft.defaultgamemode", new Permission("minecraft.defaultgamemode", PermissionType.OP));
        permissions.put("minecraft.difficulty", new Permission("minecraft.difficulty", PermissionType.OP));
        permissions.put("minecraft.enchant", new Permission("minecraft.enchant", PermissionType.OP));
        permissions.put("minecraft.gamemode", new Permission("minecraft.gamemode", PermissionType.OP));
        permissions.put("minecraft.give", new Permission("minecraft.give", PermissionType.OP));
        permissions.put("minecraft.kick", new Permission("minecraft.kick", PermissionType.OP));
        permissions.put("minecraft.kill", new Permission("minecraft.kill", PermissionType.OP));
        permissions.put("minecraft.list", new Permission("minecraft.list", PermissionType.OP));
        permissions.put("minecraft.me", new Permission("minecraft.me", PermissionType.OP));
        permissions.put("minecraft.op", new Permission("minecraft.op", PermissionType.OP));
        permissions.put("minecraft.pardon", new Permission("minecraft.pardon", PermissionType.OP));
        permissions.put("minecraft.pardonip", new Permission("minecraft.pardonip", PermissionType.OP));
        permissions.put("minecraft.saveall", new Permission("minecraft.saveall", PermissionType.OP));
        permissions.put("minecraft.saveoff", new Permission("minecraft.saveoff", PermissionType.OP));
        permissions.put("minecraft.saveon", new Permission("minecraft.saveon", PermissionType.OP));
        permissions.put("minecraft.say", new Permission("minecraft.say", PermissionType.OP));
        permissions.put("minecraft.seed", new Permission("minecraft.seed", PermissionType.OP));
        permissions.put("minecraft.spawnpoint", new Permission("minecraft.spawnpoint", PermissionType.OP));
        permissions.put("minecraft.stop", new Permission("minecraft.stop", PermissionType.OP));
        permissions.put("minecraft.tell", new Permission("minecraft.tell", PermissionType.OP));
        permissions.put("minecraft.time", new Permission("minecraft.time", PermissionType.OP));
        permissions.put("minecraft.toggledownfall", new Permission("minecraft.toggledownfall", PermissionType.OP));
        permissions.put("minecraft.tp", new Permission("minecraft.tp", PermissionType.OP));
        permissions.put("minecraft.weather", new Permission("minecraft.weather", PermissionType.OP));
        permissions.put("minecraft.whitelist", new Permission("minecraft.whitelist", PermissionType.OP));
        permissions.put("minecraft.xp", new Permission("minecraft.xp", PermissionType.OP));
    }
    
    public void loadPermissionPerms() {
        
    }
    
    @ServerStarting
    public void serverStarting(FMLServerStartingEvent e) {
        MinecraftServer server = ModLoader.getMinecraftServerInstance();
        ICommandManager commandManager = server.getCommandManager();
        ServerCommandManager serverCommandManager = ((ServerCommandManager) commandManager); 
        
        loadVanillaCommands(serverCommandManager);
        loadPermissionCommands(serverCommandManager);
    }

    public void loadVanillaCommands(ServerCommandManager serverCommandManager) {
        serverCommandManager.registerCommand(new Ban());
        serverCommandManager.registerCommand(new BanIp());
        serverCommandManager.registerCommand(new BanList());
        serverCommandManager.registerCommand(new Clear());
        serverCommandManager.registerCommand(new DeOp());
        serverCommandManager.registerCommand(new DefaultGameMode());
        serverCommandManager.registerCommand(new Difficulty());
        serverCommandManager.registerCommand(new Enchant());
        serverCommandManager.registerCommand(new GameMode());
        serverCommandManager.registerCommand(new GameRule());
        serverCommandManager.registerCommand(new Give());
        serverCommandManager.registerCommand(new Kick());
        serverCommandManager.registerCommand(new Kill());
        serverCommandManager.registerCommand(new List());
        serverCommandManager.registerCommand(new Me());
        serverCommandManager.registerCommand(new Op());
        serverCommandManager.registerCommand(new Pardon());
        serverCommandManager.registerCommand(new PardonIp());
        serverCommandManager.registerCommand(new SaveAll());
        serverCommandManager.registerCommand(new SaveOff());
        serverCommandManager.registerCommand(new SaveOn());
        serverCommandManager.registerCommand(new Say());
        serverCommandManager.registerCommand(new Seed());
        serverCommandManager.registerCommand(new SpawnPoint());
        serverCommandManager.registerCommand(new Stop());
        serverCommandManager.registerCommand(new Tell());
        serverCommandManager.registerCommand(new Time());
        serverCommandManager.registerCommand(new ToggleDownFall());
        serverCommandManager.registerCommand(new Tp());
        serverCommandManager.registerCommand(new Weather());
        serverCommandManager.registerCommand(new Whitelist());
        serverCommandManager.registerCommand(new Xp());
    }
    
    public void loadPermissionCommands(ServerCommandManager serverCommandManager) {
        
    }
    
    @ServerStopping
    public void serverStopping(FMLServerStoppingEvent e) {
        //save! save! save!
    }
}