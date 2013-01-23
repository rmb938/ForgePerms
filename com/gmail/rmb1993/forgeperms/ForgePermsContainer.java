package com.gmail.rmb1993.forgeperms;

import com.gmail.rmb1993.forgeperms.commands.GroupCommand;
import com.gmail.rmb1993.forgeperms.commands.UserCommand;
import com.gmail.rmb1993.forgeperms.commands.override.*;
import com.gmail.rmb1993.forgeperms.config.Configuration;
import com.gmail.rmb1993.forgeperms.event.PlayerTrackerHook;
import com.gmail.rmb1993.forgeperms.permissions.Permission;
import com.gmail.rmb1993.forgeperms.permissions.PermissionType;
import com.gmail.rmb1993.forgeperms.permissions.group.Group;
import com.gmail.rmb1993.forgeperms.permissions.group.Track;
import com.gmail.rmb1993.forgeperms.permissions.user.User;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.LoadController;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.event.*;
import cpw.mods.fml.common.registry.GameRegistry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import net.minecraft.command.ICommandManager;
import net.minecraft.command.ServerCommandManager;
import net.minecraft.server.MinecraftServer;

/**
 *
 * @author Ryan
 */
public class ForgePermsContainer extends DummyModContainer {

    public ForgePermsContainer() {
        super(new ModMetadata());
        ModMetadata myMeta = super.getMetadata();
        myMeta.authorList = Arrays.asList(new String[]{"Rmb938", "Favorlock"});
        myMeta.description = "Permissions system for Forge";
        myMeta.modId = "ForgePerms";
        myMeta.version = "0.1";
        myMeta.name = "Forge Permissions";
        myMeta.url = "";
    }

    @Override
    public boolean registerBus(EventBus bus, LoadController controller) {
        bus.register(this);
        return true;
    }
    public Configuration config;
    public HashMap<String, User> users = new HashMap();
    public HashMap<String, Group> groups = new HashMap();
    public HashMap<String, Permission> permissions = new HashMap();
    public HashMap<String, ArrayList<String>> customNodes = new HashMap();
    public HashMap<String, Track> tracks = new HashMap();

    @Subscribe
    public void preInit(FMLPreInitializationEvent event) {
        config = new Configuration(this);

        config.setUpConfig(event.getModConfigurationDirectory());

        loadVanillaPerms();
        loadPermissionPerms();

        GameRegistry.registerPlayerTracker(new PlayerTrackerHook(this));
    }

    @Subscribe
    public void load(FMLInitializationEvent e) {
        System.out.println("Forge Perms Loaded");
        
        config.getDb().loadCustomNodes();
        System.out.println("Forge Perms Loaded Custom Nodes");
        config.getDb().loadGroups();
        if (config.getDb().getGroup(config.getDefaultGroup()) == null) {
            System.out.println("Creating Default group");
            config.getDb().createGroup(config.getDefaultGroup());
        }
        System.out.println("Forge Perms Loaded Groups");
        config.getDb().loadUsers();
        System.out.println("Forge Perms Loaded Users");
        
    }

    @Subscribe
    public void postLoad(FMLPostInitializationEvent e) {
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
        /*User Perms*/
        permissions.put("permissions.addUserGroup", new Permission("permissions.addUserGroup", PermissionType.OP));
        permissions.put("permissions.demote", new Permission("permissions.demote", PermissionType.OP));
        permissions.put("permissions.demote.all", new Permission("permissions.demote.all", PermissionType.OP));
        permissions.put("permissions.listUserGroups", new Permission("permissions.listUserGroups", PermissionType.OP));
        permissions.put("permissions.listUserPerms", new Permission("permissions.listUserPerms", PermissionType.OP));
        permissions.put("permissions.listUsers", new Permission("permissions.listUsers", PermissionType.OP));
        permissions.put("permissions.promote", new Permission("permissions.promote", PermissionType.OP));
        permissions.put("permissions.promote.all", new Permission("permissions.promote.all", PermissionType.OP));
        permissions.put("permissions.removeUserGroup", new Permission("permissions.removeUserGroup", PermissionType.OP));
        permissions.put("permissions.userSuffix", new Permission("permissions.userSuffix", PermissionType.OP));
        permissions.put("permissions.userPrefix", new Permission("permissions.userPrefix", PermissionType.OP));
        /*Group Perms*/
    }

    @Subscribe
    public void serverStarting(FMLServerStartingEvent e) {
        MinecraftServer server = e.getServer();
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
        serverCommandManager.registerCommand(new UserCommand(this));
        serverCommandManager.registerCommand(new GroupCommand(this));
    }

    @Subscribe
    public void serverStopping(FMLServerStoppingEvent e) {
        config.getDb().saveGroups();
        config.getDb().saveUsers();
    }
}
