package com.gmail.rmb1993.forgeperms.permissions.user;

import com.gmail.rmb1993.forgeperms.permissions.Permission;
import com.gmail.rmb1993.forgeperms.permissions.group.Group;
import java.util.HashMap;

/**
 *
 * @author Ryan
 */
public class User {

    private String userName;
    
    //Permission, World
    private HashMap<Permission, String> permissions = new HashMap();
    
    //Group, GroupName
    private HashMap<Group, String> groups = new HashMap();

    public HashMap<Group, String> getGroups() {
        return groups;
    }
    
    //Key, Value
    private HashMap<String, String> vars = new HashMap();

    public HashMap<String, String> getVars() {
        return vars;
    }

    public HashMap<Permission, String> getPermissions() {
        return permissions;
    }
    
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    
}
