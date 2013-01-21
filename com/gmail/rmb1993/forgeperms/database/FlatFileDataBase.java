package com.gmail.rmb1993.forgeperms.database;

import com.gmail.rmb1993.forgeperms.ForgePerms;
import com.gmail.rmb1993.forgeperms.config.Configuration;
import com.gmail.rmb1993.forgeperms.permissions.group.Group;
import com.gmail.rmb1993.forgeperms.permissions.group.Track;
import com.gmail.rmb1993.forgeperms.permissions.user.User;
import java.io.File;
import java.util.ArrayList;
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
            config.get(userName, "groups", new String[]{ForgePerms.instance.config.getDefaultGroup()}, "The groups for the user");
            config.get(userName, "permissions", new String[]{""}, "The permissions for the user");
            config.save();
        }
    }

    @Override
    public User loadUser(String userName) {
        userName = userName.toLowerCase();
        if (global == true) {
            if (ForgePerms.instance.users.containsKey(userName)) {
                return ForgePerms.instance.users.get(userName);
            }
            if (userName.equalsIgnoreCase("server")) {
                User u = new User();
                u.setUserName(userName);
                ForgePerms.instance.users.put(userName, u);
                return loadUser(userName);
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
                            u.getGroups().add(ForgePerms.instance.groups.get(group));
                        }
                    } else if (key.equalsIgnoreCase("permissions")) {
                        for (String perm : cc.get("permissions").valueList) {
                            ArrayList<String> world = new ArrayList();
                            world.add("global");
                            u.getPermissions().put(perm, world);
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
            config.categories.remove(userName);
            ConfigCategory cc = config.getCategory(userName);
            User u = loadUser(userName);

            String[] groups = (String[]) u.getGroups().toArray();
            cc.get("groups").valueList = groups;

            String[] perms = (String[]) u.getPermissions().keySet().toArray();
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
            config.get(groupName, "rank", "0", "The rank of the group");
            config.get(groupName, "track", "default", "The track of the group for promoting and demoting");
            config.get(groupName, "inherit", new String[]{""}, "Groups inherrited by this group");
            config.get(groupName, "permissions", new String[]{""}, "The permissions for the group");
            config.save();
        }
    }

    @Override
    public void loadGroups() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Group loadGroup(String groupName) {
        groupName = groupName.toLowerCase();
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
                        if (key.equalsIgnoreCase("inherit")) {
                            for (String group : cc.get("inherit").valueList) {
                                System.out.println("Added " + group + " to " + groupName);
                                g.getGroups().add(ForgePerms.instance.groups.get(group));
                            }
                        } else if (key.equalsIgnoreCase("permissions")) {
                            for (String perm : cc.get("permissions").valueList) {
                                g.getPermissions().put(perm, "global");
                            }
                        } else if (key.equalsIgnoreCase("rank")) {
                            g.setRank(cc.get("rank").getInt());
                        } else if (key.equalsIgnoreCase("track")) {
                            if (ForgePerms.instance.tracks.containsKey(cc.get("track").value) == false) {
                                Track track = new Track();
                                track.setTrackName(cc.get("track").value);
                                ForgePerms.instance.tracks.put(cc.get("track").value, track);
                            }
                            Track track = ForgePerms.instance.tracks.get(cc.get("track").value);
                            track.addGroup(g);
                            g.setTrack(track);
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

            cc.get("rank").value = "" + g.getRank();

            String[] groups = (String[]) g.getGroups().toArray();
            cc.get("inherit").valueList = groups;

            String[] perms = (String[]) g.getPermissions().keySet().toArray();
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
            if (g1.getGroups().contains(g)) {
                g1.getGroups().remove(g);
            }
            saveGroup(g1.getGroupName());
        }
        for (User u : ForgePerms.instance.users.values()) {
            if (u.getGroups().contains(g)) {
                u.getGroups().remove(g);
            }
            if (u.getGroups().isEmpty()) {
                u.getGroups().add(loadGroup(ForgePerms.instance.config.getDefaultGroup()));
            }
            saveUser(u.getUserName());
        }
        ForgePerms.instance.groups.remove(groupName);

        config.save();
    }
}
