package com.gmail.rmb1993.forgeperms.config;

import com.gmail.rmb1993.forgeperms.ForgePermsContainer;
import com.gmail.rmb1993.forgeperms.database.DataBase;
import com.gmail.rmb1993.forgeperms.database.FlatFileDataBase;
import java.io.File;
import net.minecraftforge.common.Property;

public class Configuration {

    private boolean global;
    private boolean messagePromote;
    private DataBase db;
    private String defaultGroup;
    private ForgePermsContainer fpc;
    
    public Configuration(ForgePermsContainer fpc) {
        this.fpc = fpc;
    }

    public String getDefaultGroup() {
        return defaultGroup;
    }

    public void setDefaultGroup(String defaultGroup) {
        this.defaultGroup = defaultGroup;
    }

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

        //Property mysql = config.get("Database", "MySQL", false);
        //Property sqlLite = config.get("Database", "SQLLite", false);
        Property flatFile = config.get("Database", "FlatFile", true);
        
        //global = config.get("Permission Setup", "global", true).getBoolean(true);
        global = true;
        messagePromote = config.get("Permission Setup", "messageOnPromote", true).getBoolean(false);
        defaultGroup = config.get("Permissions Setup", "defaultGroup", "default").value;
        
        if (global == true) {
            System.out.println("Using global permissions");
        } else {
            System.out.println("Using per world permissions");
        }

        /*if (mysql.getBoolean(false) == true) {
            System.out.println("Using MySQL");
            db = new MySQLDataBase();
        }
        if (sqlLite.getBoolean(false) == true) {
            System.out.println("Using SQL Lite");
            db = new SQLLiteDataBase();
        }*/

        //if (flatFile.getBoolean(true) == true) {
            flatFile.value = "true";
            System.out.println("Using Flat File");
            db = new FlatFileDataBase(fpc, new File(configPath+"/ForgePerm/FlatFile"));
        //}

        config.save();
    }
    
}
