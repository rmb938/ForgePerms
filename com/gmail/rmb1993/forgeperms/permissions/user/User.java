package com.gmail.rmb1993.forgeperms.permissions.user;

import com.gmail.rmb1993.forgeperms.permissions.Permission;
import com.gmail.rmb1993.forgeperms.permissions.group.Group;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Ryan
 */
public class User {

    private String userName;
    
    //Permission, World(TBA)
    private HashMap<String, String> permissions = new HashMap();
    
    //Group
    private ArrayList<Group> groups = new ArrayList();

    public ArrayList<Group> getGroups() {
        return groups;
    }
    
    //Key, Value
    private HashMap<String, String> vars = new HashMap();

    public HashMap<String, String> getVars() {
        return vars;
    }

    public HashMap<String, String> getPermissions() {
        return permissions;
    }
    
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    
}
