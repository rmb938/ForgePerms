package com.gmail.rmb1993.forgeperms.permissions.group;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 *
 * @author Ryan
 */
public class Track {

    private String trackName;
    private ArrayList<Group> groups = new ArrayList();

    public void addGroup(Group g) {
        groups.add(g);
        Collections.sort(groups, new Comparator<Group>() {

            @Override
            public int compare(Group g1, Group g2) {
                if (g1.getRank() > g2.getRank()) {
                    return 1;
                }
                if (g1.getRank() < g2.getRank()) {
                    return -1;
                }
                return 0;
            }
        });
    }

    public String getTrackName() {
        return trackName;
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

    public Group getNextGroup(Group currentGroup) {
        Group newGroup = groups.get(0);
        for (Group g : groups) {
            if (g.getRank() <= currentGroup.getRank()) {
                newGroup = g;
                continue;
            }
            return newGroup;
        }
        return null;
    }
    
    public Group getPreviousGroup(Group currentGroup) {
        Group newGroup = groups.get(groups.size() - 1);
        for (int i = groups.size() - 1; i >= 0; i--) {
            Group g = groups.get(i);
            if (g.getRank() >= newGroup.getRank()) {
                newGroup = g;
                continue;
            }
            return newGroup;
        }
        return null;
    }
}
