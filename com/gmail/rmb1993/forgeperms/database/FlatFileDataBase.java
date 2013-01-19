package com.gmail.rmb1993.forgeperms.database;

import com.gmail.rmb1993.forgeperms.ForgePerms;
import com.gmail.rmb1993.forgeperms.config.Configuration;
import com.gmail.rmb1993.forgeperms.permissions.group.Group;
import com.gmail.rmb1993.forgeperms.permissions.user.User;
import java.io.File;
import java.util.Map;
import net.minecraftforge.common.ConfigCategory;
import net.minecraftforge.common.DimensionManager;

/**
 *
 * @author Ryan
 */
public class FlatFileDataBase extends DataBase {

    private File location;
    private boolean global;

    public FlatFileDataBase(File location) {
        super();
        this.location = location;
        Configuration config = ForgePerms.instance.config;
        global = config.isGlobal();
    }

    @Override
    public void createUser(String userName) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public User loadUser(String userName) {
        if (global == true) {
            net.minecraftforge.common.Configuration config = new net.minecraftforge.common.Configuration(new File(location+"/users.cfg"));

            config.load();
            Map<String, ConfigCategory> users = config.categories;
            ConfigCategory cc = users.get(userName);
            User u = new User();
            u.setUserName(userName);
            cc.get("permissions");
            config.save();
        } else {
        }
        return null;
    }

    @Override
    public void saveUser(String userName) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void createGroup(String groupName) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Group loadGroup(String groupName) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void saveGroup(String groupName) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void removeGroup(String groupName) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
