package com.gmail.rmb1993.forgeperms.permissions.user;

import com.gmail.rmb1993.forgeperms.permissions.Permission;
import java.util.HashMap;

/**
 *
 * @author Ryan
 */
public class User {

    private String userName;
    
    //Permission, World
    private HashMap<Permission, String> permissions = new HashMap();

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
