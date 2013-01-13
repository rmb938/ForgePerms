package com.gmail.rmb1993.forgeperms.permissions.group;

import java.util.ArrayList;

/**
 *
 * @author Ryan
 */
public class Track {
    
    private String trackName;
    
    private ArrayList<Group> groups = new ArrayList();

    public String getTrackName() {
        return trackName;
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

}
