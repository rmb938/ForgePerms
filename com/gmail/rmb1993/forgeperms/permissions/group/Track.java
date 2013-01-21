package com.gmail.rmb1993.forgeperms.permissions.group;

import java.util.ArrayList;

/**
 *
 * @author Ryan
 */
public class Track {
    
    private String trackName;
    
    private ArrayList<Group> groups = new ArrayList();

    public ArrayList<Group> getGroups() {
        return groups;
    }

    public String getTrackName() {
        return trackName;
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }
    
    public Group getNextGroup(int currentRank) {
        Group lowestRank = null;
        for (Group g : groups) {
            if (g.getRank() <= currentRank) {
                continue;
            }
            if (lowestRank != null) {
                if (lowestRank.getRank() > g.getRank()) {
                    lowestRank = g;
                }
            } else {
                lowestRank = g;
            }
        }
        return lowestRank;
    }

}
