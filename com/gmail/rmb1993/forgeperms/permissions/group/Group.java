package com.gmail.rmb1993.forgeperms.permissions.group;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
    
    //Custom Perm, World(TBA)
    private HashMap<String, List<String>> customPermissions;

    public HashMap<String, List<String>> getCustomPermissions() {
        return customPermissions;
    }

    public void setCustomPermissions(HashMap<String, List<String>> customPermissions) {
        this.customPermissions = customPermissions;
    }
    
    //Permission, World(TBA)
    private HashMap<String, List<String>> permissions;
    
    //Key, Value
    private HashMap<String, String> vars;

    public HashMap<String, String> getVars() {
        return vars;
    }

    public void setInheritance(ArrayList<String> groups) {
        this.inheritance = groups;
    }

    public void setPermissions(HashMap<String, List<String>> permissions) {
        this.permissions = permissions;
    }

    public void setVars(HashMap<String, String> vars) {
        this.vars = vars;
    }
    
    //Group
    private ArrayList<String> inheritance;

    public ArrayList<String> getInheritance() {
        return inheritance;
    }

    public HashMap<String, List<String>> getPermissions() {
        return permissions;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
    
}
