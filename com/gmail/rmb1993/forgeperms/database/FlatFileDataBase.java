package com.gmail.rmb1993.forgeperms.database;

import com.gmail.rmb1993.forgeperms.ForgePermsContainer;
import com.gmail.rmb1993.forgeperms.config.Configuration;
import com.gmail.rmb1993.forgeperms.permissions.group.Group;
import com.gmail.rmb1993.forgeperms.permissions.group.Track;
import com.gmail.rmb1993.forgeperms.permissions.user.User;
import cpw.mods.fml.common.FMLLog;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.yaml.snakeyaml.Yaml;

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
    public void loadCustomNodes() {
        File file = new File(location + "/customNodes.yml");
        if (file.exists() == false) {
            try {
                System.out.println("Creating Example Custom Nodes");
                
                file.createNewFile();

                ArrayList<String> nodes = new ArrayList();

                nodes.add("node.example.1");
                nodes.add("node.example.2");
                nodes.add("node.example.3");

                ForgePermsContainer.instance.customNodes.put("customNode.example", nodes);

                Yaml yaml = new Yaml();
                OutputStream output = null;
                try {
                    output = new FileOutputStream(file);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(FlatFileDataBase.class.getName()).log(Level.SEVERE, null, ex);
                }
                OutputStreamWriter osw = new OutputStreamWriter(output);
                yaml.dump(ForgePermsContainer.instance.customNodes, osw);
                try {
                    osw.close();
                    output.close();
                } catch (IOException ex) {
                    Logger.getLogger(FlatFileDataBase.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (IOException ex) {
                Logger.getLogger(FlatFileDataBase.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        Yaml yaml = new Yaml();
        InputStream input = null;
        try {
            input = new FileInputStream(file);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FlatFileDataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            ForgePermsContainer.instance.customNodes = (HashMap<String, ArrayList<String>>) yaml.load(input);
        } catch (Exception e) {
            FMLLog.log(Level.SEVERE, e, "Exception encountered attempting YAML parsing of %s", file);
        }
        try {
            input.close();
        } catch (IOException ex) {
            Logger.getLogger(FlatFileDataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
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

        Yaml yaml = new Yaml();

        InputStream input = null;
        try {
            input = new FileInputStream(file);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FlatFileDataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            for (Object obj : yaml.loadAll(input)) {
                User u = (User) obj;
                System.out.println("Loaded User: " + u.getUserName());
                ForgePermsContainer.instance.users.put(u.getUserName(), u);
            }
        } catch (Exception e) {
            FMLLog.log(Level.SEVERE, e, "Exception encountered attempting YAML parsing of %s", file);
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
        if (ForgePermsContainer.instance.users.containsKey(userName)) {
            System.out.println("User " + userName + " is already in file!");
            return;
        }

        User u = new User();
        u.setUserName(userName);

        u.setCustomPermissions(new HashMap<String, List<String>>());
        
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

        Yaml yaml = new Yaml();
        OutputStream output = null;
        try {
            output = new FileOutputStream(file);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FlatFileDataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        OutputStreamWriter osw = new OutputStreamWriter(output);
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
    public User getUser(String userName) {
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
        
        u.setCustomPermissions(new HashMap<String, List<String>>());

        u.setInheritance(new ArrayList<String>());

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

        Yaml yaml = new Yaml();
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
        Yaml yaml = new Yaml();
        InputStream input = null;
        try {
            input = new FileInputStream(file);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FlatFileDataBase.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            for (Object obj : yaml.loadAll(input)) {
                Group u = (Group) obj;
                if (ForgePermsContainer.instance.tracks.containsKey(u.getTrack()) == false) {
                    ForgePermsContainer.instance.tracks.put(u.getTrack(), new Track());
                }

                Track track = ForgePermsContainer.instance.tracks.get(u.getTrack());
                track.addGroup(u);
                ForgePermsContainer.instance.groups.put(u.getGroupName(), u);
            }
        } catch (Exception e) {
            FMLLog.log(Level.SEVERE, e, "Exception encountered attempting YAML parsing of %s", file);
            //System.exit(0);
        }
        try {
            input.close();
        } catch (IOException ex) {
            Logger.getLogger(FlatFileDataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Group getGroup(String groupName) {
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
            g.getInheritance().remove(groupName);
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

        Yaml yaml = new Yaml();
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

        Yaml yaml = new Yaml();
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
