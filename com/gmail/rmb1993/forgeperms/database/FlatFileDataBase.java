package com.gmail.rmb1993.forgeperms.database;

import com.gmail.rmb1993.forgeperms.ForgePermsContainer;
import com.gmail.rmb1993.forgeperms.config.Configuration;
import com.gmail.rmb1993.forgeperms.permissions.group.Group;
import com.gmail.rmb1993.forgeperms.permissions.group.Track;
import com.gmail.rmb1993.forgeperms.permissions.user.User;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.representer.Representer;

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
        Configuration config = ForgePermsContainer.instance.config;
        global = config.isGlobal();
    }

    @Override
    public void loadUsers() {
        File file = new File(location + "/users.yml");
        if (file.exists() == false) {
            try {
                file.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(FlatFileDataBase.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        DumperOptions options = new DumperOptions();
        options.setExplicitStart(true);


        Representer representer = new Representer();
        representer.addClassTag(User.class, new Tag("!user"));

        Yaml yaml = new Yaml(representer, options);

        InputStream input = null;
        try {
            input = new FileInputStream(file);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FlatFileDataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (Object obj : yaml.loadAll(input)) {
            User u = (User) obj;
            ForgePermsContainer.instance.users.put(u.getUserName(), u);
        }
        try {
            input.close();
        } catch (IOException ex) {
            Logger.getLogger(FlatFileDataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void createUser(String userName) {
        userName = userName.toLowerCase();
        System.out.println("Starting creating user");
        if (ForgePermsContainer.instance.users.containsKey(userName)) {
            System.out.println("User " + userName + " is already in file!");
            return;
        }

        User u = new User();
        u.setUserName(userName);


        Group g = new Group();
        g.setGroupName("default");
        u.setGroups(new ArrayList<String>());
        u.getGroups().add(g.getGroupName());

        u.setPermissions(new HashMap<String, List<String>>());

        u.setVars(new HashMap<String, String>());


        File file = new File(location + "/users.yml");
        if (file.exists() == false) {
            try {
                file.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(FlatFileDataBase.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        DumperOptions options = new DumperOptions();
        options.setExplicitStart(true);


        Representer representer = new Representer();
        representer.addClassTag(User.class, new Tag("!user"));

        Yaml yaml = new Yaml(representer, options);
        OutputStream output = null;
        try {
            output = new FileOutputStream(file);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FlatFileDataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        OutputStreamWriter osw = new OutputStreamWriter(output);
        System.out.println("Created User");
        ForgePermsContainer.instance.users.put(userName, u);
        yaml.dumpAll(ForgePermsContainer.instance.users.values().iterator(), osw);
        try {
            osw.close();
            output.close();
        } catch (IOException ex) {
            Logger.getLogger(FlatFileDataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public User loadUser(String userName) {
        userName = userName.toLowerCase();
        if (ForgePermsContainer.instance.users.containsKey(userName)) {
            return ForgePermsContainer.instance.users.get(userName);
        }
        if (userName.equalsIgnoreCase("server")) {
            User u = new User();
            u.setUserName(userName);
            Group g = new Group();
            g.setGroupName("default");
            u.setGroups(new ArrayList<String>());
            u.getGroups().add(g.getGroupName());
            u.setPermissions(new HashMap<String, List<String>>());
            u.setVars(new HashMap<String, String>());
            return u;
        }
        return null;
    }

    @Override
    public void createGroup(String groupName) {
        groupName = groupName.toLowerCase();
        if (ForgePermsContainer.instance.groups.containsKey(groupName)) {
            System.out.println("Group " + groupName + " is already in file!");
            return;
        }

        Group u = new Group();
        u.setGroupName(groupName);

        u.setGroups(new ArrayList<String>());

        u.setPermissions(new HashMap<String, String>());

        u.setVars(new HashMap<String, String>());
        u.setTrack("default");
        if (ForgePermsContainer.instance.tracks.containsKey("default") == false) {
            ForgePermsContainer.instance.tracks.put("default", new Track());
        }

        Track track = ForgePermsContainer.instance.tracks.get("default");
        track.addGroup(u);

        File file = new File(location + "/groups.yml");
        if (file.exists() == false) {
            try {
                file.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(FlatFileDataBase.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        DumperOptions options = new DumperOptions();
        options.setExplicitStart(true);


        Representer representer = new Representer();
        representer.addClassTag(Group.class, new Tag("!group"));

        Yaml yaml = new Yaml(representer, options);
        OutputStream output = null;
        try {
            output = new FileOutputStream(file);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FlatFileDataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        OutputStreamWriter osw = new OutputStreamWriter(output);
        ForgePermsContainer.instance.groups.put(groupName, u);
        yaml.dumpAll(ForgePermsContainer.instance.groups.values().iterator(), osw);
        try {
            osw.close();
            output.close();
        } catch (IOException ex) {
            Logger.getLogger(FlatFileDataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void loadGroups() {
        File file = new File(location + "/groups.yml");
        if (file.exists() == false) {
            try {
                file.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(FlatFileDataBase.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        DumperOptions options = new DumperOptions();
        options.setExplicitStart(true);


        Representer representer = new Representer();
        representer.addClassTag(Group.class, new Tag("!group"));

        Yaml yaml = new Yaml(representer, options);

        InputStream input = null;
        try {
            input = new FileInputStream(file);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FlatFileDataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (Object obj : yaml.loadAll(input)) {
            Group u = (Group) obj;
            if (ForgePermsContainer.instance.tracks.containsKey(u.getTrack()) == false) {
                ForgePermsContainer.instance.tracks.put(u.getTrack(), new Track());
            }

            Track track = ForgePermsContainer.instance.tracks.get(u.getTrack());
            track.addGroup(u);
            ForgePermsContainer.instance.groups.put(u.getGroupName(), u);
        }
        try {
            input.close();
        } catch (IOException ex) {
            Logger.getLogger(FlatFileDataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Group loadGroup(String groupName) {
        groupName = groupName.toLowerCase();
        if (ForgePermsContainer.instance.groups.containsKey(groupName)) {
            return ForgePermsContainer.instance.groups.get(groupName);
        }
        return null;
    }

    @Override
    public void removeGroup(String groupName) {
        groupName = groupName.toLowerCase();
        ForgePermsContainer.instance.groups.remove(groupName);

        for (User u : ForgePermsContainer.instance.users.values()) {
            u.getGroups().remove(groupName);
            if (u.getGroups().isEmpty()) {
                u.getGroups().add(ForgePermsContainer.instance.config.getDefaultGroup());
            }
        }
        saveUsers();
        for (Group g : ForgePermsContainer.instance.groups.values()) {
            g.getGroups().remove(groupName);
        }
        saveGroups();
    }

    @Override
    public void saveUsers() {
        File file = new File(location + "/users.yml");
        if (file.exists() == false) {
            try {
                file.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(FlatFileDataBase.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        DumperOptions options = new DumperOptions();
        options.setExplicitStart(true);


        Representer representer = new Representer();
        representer.addClassTag(User.class, new Tag("!user"));

        Yaml yaml = new Yaml(representer, options);
        OutputStream output = null;
        try {
            output = new FileOutputStream(file);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FlatFileDataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        OutputStreamWriter osw = new OutputStreamWriter(output);
        yaml.dumpAll(ForgePermsContainer.instance.users.values().iterator(), osw);
        try {
            osw.close();
            output.close();
        } catch (IOException ex) {
            Logger.getLogger(FlatFileDataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void saveGroups() {

        File file = new File(location + "/groups.yml");
        if (file.exists() == false) {
            try {
                file.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(FlatFileDataBase.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        DumperOptions options = new DumperOptions();
        options.setExplicitStart(true);


        Representer representer = new Representer();
        representer.addClassTag(Group.class, new Tag("!group"));

        Yaml yaml = new Yaml(representer, options);
        OutputStream output = null;
        try {
            output = new FileOutputStream(file);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FlatFileDataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        OutputStreamWriter osw = new OutputStreamWriter(output);
        yaml.dumpAll(ForgePermsContainer.instance.groups.values().iterator(), osw);
        try {
            osw.close();
            output.close();
        } catch (IOException ex) {
            Logger.getLogger(FlatFileDataBase.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
