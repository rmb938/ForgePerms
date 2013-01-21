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
    private Track track;

    public Track getTrack() {
        return track;
    }

    public void setTrack(Track track) {
        this.track = track;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
    
    //Permission, World(TBA)
    private HashMap<String, String> permissions = new HashMap();
    
    //Key, Value
    private HashMap<String, String> vars = new HashMap();

    public HashMap<String, String> getVars() {
        return vars;
    }
    
    //Group
    private ArrayList<Group> groups = new ArrayList();

    public ArrayList<Group> getGroups() {
        return groups;
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
