package com.gmail.rmb1993.ForgePerms;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.Mod.ServerStarting;
import cpw.mods.fml.common.Mod.ServerStopping;
import cpw.mods.fml.common.event.*;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.relauncher.ReflectionHelper;
import cpw.mods.fml.relauncher.RelaunchClassLoader;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URLClassLoader;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;

//////////////////
// Date: 1/12/2013
//////////////////
@NetworkMod(clientSideRequired = false, serverSideRequired = false)
@Mod(modid = "ForgePerms", name = "Forge Permissions", version = "0.1")
public class ForgePerms {

    @Instance(value = "ForgePerms")
    public static ForgePerms instance;

    @PreInit
    public void preInit(FMLPreInitializationEvent event) {
        System.out.println("Forge Perms Loaded");
        
        Configuration config = new Configuration(event.getSuggestedConfigurationFile());
        
        config.load();
        
        config.addCustomCategoryComment("Database", "This is a database config :D");
        Property mysql = config.get("Database", "MySQL", true);
        Property sqlLite = config.get("Database", "SQLLite", false);
        Property flatFile = config.get("Database", "FlatFile", false);
        
        config.save();
        
        File mcDir = computeExistingClientHome();
        File libDir = null;
        try {
            libDir = setupLibDir(mcDir);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        File libFile = new File(libDir, "commons-dbutils-1.5.jar");

        URLClassLoader ucl = (URLClassLoader) getClass().getClassLoader();
        RelaunchClassLoader actualClassLoader = new RelaunchClassLoader(ucl.getURLs());
        try {
            actualClassLoader.addURL(libFile.toURI().toURL());
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * @return
     */
    private File computeExistingClientHome() {
        Class<? super Object> mcMaster = ReflectionHelper.getClass(getClass().getClassLoader(), "net.minecraft.client.Minecraft");
        // If we get the system property we inject into the old MC, setup the
        // dir, then pull the value
        String str = System.getProperty("minecraft.applet.TargetDirectory");
        if (str != null) {
            str = str.replace('/', File.separatorChar);
            ReflectionHelper.setPrivateValue(mcMaster, null, new File(str), "minecraftDir", "an", "minecraftDir");
        }
        // We force minecraft to setup it's homedir very early on so we can
        // inject stuff into it
        Method setupHome = ReflectionHelper.findMethod(mcMaster, null, new String[]{"getMinecraftDir", "getMinecraftDir", "b"});
        try {
            setupHome.invoke(null);
        } catch (Exception e) {
            // Hmmm
        }
        File minecraftHome = ReflectionHelper.getPrivateValue(mcMaster, null, "minecraftDir", "an", "minecraftDir");
        return minecraftHome;
    }

    /**
     * @param mcDir
     * @return
     */
    private static File setupLibDir(File mcDir) {
        File libDir = new File(mcDir, "mods");
        try {
            libDir = libDir.getCanonicalFile();
        } catch (IOException e) {
            throw new RuntimeException(String.format("Unable to canonicalize the mods dir at %s", mcDir.getName()), e);
        }
        if (!libDir.exists()) {
            libDir.mkdir();
        } else if (libDir.exists() && !libDir.isDirectory()) {
            throw new RuntimeException(String.format("Found a mods file in %s that's not a directory", mcDir.getName()));
        }
        return libDir;
    }

    @Init
    public void load(FMLInitializationEvent e) {
    }

    @PostInit
    public void postLoad(FMLPostInitializationEvent e) {
    }

    @ServerStarting
    public void serverStarting(FMLServerStartingEvent e) {
    }

    @ServerStopping
    public void serverStopping(FMLServerStoppingEvent e) {
    }
}