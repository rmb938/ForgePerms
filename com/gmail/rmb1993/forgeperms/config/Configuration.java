package com.gmail.rmb1993.forgeperms.config;

import com.gmail.rmb1993.forgeperms.database.DataBase;
import com.gmail.rmb1993.forgeperms.database.FlatFileDataBase;
import com.gmail.rmb1993.forgeperms.database.MySQLDataBase;
import com.gmail.rmb1993.forgeperms.database.SQLLiteDataBase;
import cpw.mods.fml.relauncher.ReflectionHelper;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import net.minecraftforge.common.Property;

public class Configuration {

    private boolean global;
    private boolean messagePromote;
    private DataBase db;

    public DataBase getDb() {
        return db;
    }

    public void setDb(DataBase db) {
        this.db = db;
    }

    public boolean isMessagePromote() {
        return messagePromote;
    }

    public void setMessagePromote(boolean messagePromote) {
        this.messagePromote = messagePromote;
    }

    public boolean isGlobal() {
        return global;
    }
    
    public void setGlobal(boolean global) {
        this.global = global;
    }

    public void setUpConfig(File configPath) {
        File configFile = new File(configPath+"/ForgePerm/config.cfg");
        net.minecraftforge.common.Configuration config = new net.minecraftforge.common.Configuration(configFile);

        config.load();

        Property mysql = config.get("Database", "MySQL", false);
        Property sqlLite = config.get("Database", "SQLLite", false);
        Property flatFile = config.get("Database", "FlatFile", true);
        
        global = config.get("Permission Setup", "global", true).getBoolean(true);
        messagePromote = config.get("Permission Setup", "messageOnPromote", true).getBoolean(false);
        
        if (global == true) {
            System.out.println("Using global permissions");
        } else {
            System.out.println("Using per world permissions");
        }

        File mcDir = computeExistingClientHome();
        File libDir = null;

        if (mysql.getBoolean(false) == true) {
            System.out.println("Using MySQL");
            db = new MySQLDataBase();

            try {
                libDir = setupLibDir(mcDir);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            File libFile = new File(libDir, "commons-dbutils-1.5.jar");//MySQL Library
            if (libFile.exists() == false) {
                System.out.println("You do not have the mySQL library installed. Switching to flatFile");
                mysql.value = "false";
                flatFile.value = "true";
            }
        }
        if (sqlLite.getBoolean(false) == true) {
            System.out.println("Using SQL Lite");
            db = new SQLLiteDataBase();
        }

        if (flatFile.getBoolean(true) == true) {
            System.out.println("Using Flat File");
            db = new FlatFileDataBase(new File(configPath+"/ForgePerm/FlatFile"));
        }

        config.save();
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
    
}
