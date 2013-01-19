package com.gmail.rmb1993.forgeperms.permissions.group;

import com.gmail.rmb1993.forgeperms.permissions.Permission;
import java.util.HashMap;

/**
 *
 * @author Ryan
 */
public class Group {

    private String groupName;
    private int rank;
    private Track track;
    
    //Permission, World
    private HashMap<Permission, String> permissions = new HashMap();
    
    //Key, Value
    private HashMap<String, String> vars = new HashMap();

    public HashMap<String, String> getVars() {
        return vars;
    }
    
    //Group, GroupName
    private HashMap<Group, String> groups = new HashMap();

    public HashMap<Group, String> getGroups() {
        return groups;
    }

    public HashMap<Permission, String> getPermissions() {
        return permissions;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
    
}
