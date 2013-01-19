package com.gmail.rmb1993.forgeperms.database;

import com.gmail.rmb1993.forgeperms.ForgePerms;
import com.gmail.rmb1993.forgeperms.config.Configuration;
import com.gmail.rmb1993.forgeperms.permissions.group.Group;
import com.gmail.rmb1993.forgeperms.permissions.user.User;
import java.io.File;
import java.util.Map;
import net.minecraftforge.common.ConfigCategory;

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
        if (global == true) {
            net.minecraftforge.common.Configuration config = new net.minecraftforge.common.Configuration(new File(location + "/users.cfg"));
            config.load();
            config.get(userName, "groups", new String[]{"testGroup"}, "The groups for the user");
            config.get(userName, "permissions", new String[]{"test.permission"}, "The permissions for the user");
            config.save();
        }
    }

    @Override
    public User loadUser(String userName) {
        if (global == true) {
            if (ForgePerms.instance.users.containsKey(userName)) {
                return ForgePerms.instance.users.get(userName);
            }
            net.minecraftforge.common.Configuration config = new net.minecraftforge.common.Configuration(new File(location + "/users.cfg"));

            config.load();
            Map<String, ConfigCategory> users = config.categories;
            ConfigCategory cc = users.get(userName);
            if (cc != null) {
                User u = new User();
                u.setUserName(userName);
                for (String key : cc.keySet()) {
                    if (key.equalsIgnoreCase("groups")) {
                        for (String group : cc.get("groups").valueList) {
                            System.out.println("Added " + group + " to " + userName);
                            u.getGroups().put(ForgePerms.instance.groups.get(group), group);
                        }
                    } else if (key.equalsIgnoreCase("permissions")) {
                        for (String perm : cc.get("permissions").valueList) {
                            System.out.println("Added " + perm + " to " + userName);
                            u.getPermissions().put(ForgePerms.instance.permissions.get(perm), "global");
                        }
                    } else {
                        u.getVars().put(key, cc.get(key).value);
                    }
                }
                ForgePerms.instance.users.put(userName, u);
                return u;
            } else {
                createUser(userName);
                return loadUser(userName);
            }
        }
        return null;
    }

    @Override
    public void saveUser(String userName) {
        if (global == true) {
            net.minecraftforge.common.Configuration config = new net.minecraftforge.common.Configuration(new File(location + "/users.cfg"));

            config.load();
            
            ConfigCategory cc = config.getCategory(userName);
            User u = loadUser(userName);
            
            String[] groups = (String[]) u.getGroups().values().toArray();
            cc.get("groups").valueList = groups;
            
            String[] perms = (String[]) u.getPermissions().values().toArray();
            cc.get("permissions").valueList = perms;
            
            for (String key : u.getVars().keySet()) {
                String value = u.getVars().get(key);
                cc.get(key).value = value;
            }
            
            config.save();
        }
    }

    @Override
    public void createGroup(String groupName) {
        if (global == true) {
            net.minecraftforge.common.Configuration config = new net.minecraftforge.common.Configuration(new File(location + "/groups.cfg"));
            config.load();
            config.get(groupName, "groups", new String[]{"testGroup"}, "Groups inherrited by this group");
            config.get(groupName, "permissions", new String[]{"test.permission"}, "The permissions for the group");
            config.save();
        }
    }

    @Override
    public Group loadGroup(String groupName) {
        if (global == true) {
            if (global == true) {
                if (ForgePerms.instance.groups.containsKey(groupName)) {
                    return ForgePerms.instance.groups.get(groupName);
                }
                net.minecraftforge.common.Configuration config = new net.minecraftforge.common.Configuration(new File(location + "/groups.cfg"));

                config.load();
                Map<String, ConfigCategory> users = config.categories;
                ConfigCategory cc = users.get(groupName);
                if (cc != null) {
                    Group g = new Group();
                    g.setGroupName(groupName);
                    for (String key : cc.keySet()) {
                        if (key.equalsIgnoreCase("permissions")) {
                            for (String perm : cc.get("permissions").valueList) {
                                System.out.println("Added " + perm + " to " + groupName);
                                g.getPermissions().put(ForgePerms.instance.permissions.get(perm), "global");
                            }
                        } else {
                            g.getVars().put(key, cc.get(key).value);
                        }
                    }
                    ForgePerms.instance.groups.put(groupName, g);
                    return g;
                }
            }
        }
        return null;
    }

    @Override
    public void saveGroup(String groupName) {
        if (global == true) {
            net.minecraftforge.common.Configuration config = new net.minecraftforge.common.Configuration(new File(location + "/groups.cfg"));

            config.load();
            
            ConfigCategory cc = config.getCategory(groupName);
            Group g = loadGroup(groupName);
            
            String[] groups = (String[]) g.getGroups().values().toArray();
            cc.get("groups").valueList = groups;
            
            String[] perms = (String[]) g.getPermissions().values().toArray();
            cc.get("permissions").valueList = perms;
            
            for (String key : g.getVars().keySet()) {
                String value = g.getVars().get(key);
                cc.get(key).value = value;
            }
            
            config.save();
        }
    }

    @Override
    public void removeGroup(String groupName) {
        net.minecraftforge.common.Configuration config = new net.minecraftforge.common.Configuration(new File(location + "/groups.cfg"));

            config.load();
            
            config.categories.remove(groupName);
            Group g = loadGroup(groupName);
            for (Group g1 : ForgePerms.instance.groups.values()) {
                if (g1.getGroups().containsKey(g)) {
                    g1.getGroups().remove(g);
                }
            }
            for (User u : ForgePerms.instance.users.values()) {
                if (u.getGroups().containsKey(g)) {
                    u.getGroups().remove(g);
                }
                if (u.getGroups().size() == 0) {
                    u.getGroups().put(loadGroup(ForgePerms.instance.config.getDefaultGroup()), ForgePerms.instance.config.getDefaultGroup());
                }
            }
            ForgePerms.instance.groups.remove(groupName);
            
            config.save();
    }
}
