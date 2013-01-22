package com.gmail.rmb1993.forgeperms.permissions.group;

import com.gmail.rmb1993.forgeperms.permissions.Permission;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Ryan
 */
public class Group {

    private String groupName;
    private int rank;
    private String track;

    public String getTrack() {
        return track;
    }

    public void setTrack(String track) {
        this.track = track;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
    
    //Permission, World(TBA)
    private HashMap<String, String> permissions;
    
    //Key, Value
    private HashMap<String, String> vars;

    public HashMap<String, String> getVars() {
        return vars;
    }

    public void setGroups(ArrayList<String> groups) {
        this.inheritance = groups;
    }

    public void setPermissions(HashMap<String, String> permissions) {
        this.permissions = permissions;
    }

    public void setVars(HashMap<String, String> vars) {
        this.vars = vars;
    }
    
    //Group
    private ArrayList<String> inheritance;

    public ArrayList<String> getGroups() {
        return inheritance;
    }

    public HashMap<String, String> getPermissions() {
        return permissions;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
    
}
