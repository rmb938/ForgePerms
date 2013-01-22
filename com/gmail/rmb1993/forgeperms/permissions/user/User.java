package com.gmail.rmb1993.forgeperms.permissions.user;

import com.gmail.rmb1993.forgeperms.permissions.Permission;
import com.gmail.rmb1993.forgeperms.permissions.group.Group;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Ryan
 */
public class User {

    private String userName;
    
    //Permission, World(TBA)
    private HashMap<String, List<String>> permissions;

    public void setPermissions(HashMap<String, List<String>> permissions) {
        this.permissions = permissions;
    }

    public void setVars(HashMap<String, String> vars) {
        this.vars = vars;
    }
    
    //Group
    private ArrayList<String> groups;

    public void setGroups(ArrayList<String> groups) {
        this.groups = groups;
    }

    public ArrayList<String> getGroups() {
        return groups;
    }
    
    //Key, Value
    private HashMap<String, String> vars;

    public HashMap<String, String> getVars() {
        return vars;
    }

    public HashMap<String, List<String>> getPermissions() {
        return permissions;
    }
    
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    
}
