package com.gmail.rmb1993.forgeperms.database;

import com.gmail.rmb1993.forgeperms.permissions.group.Group;
import com.gmail.rmb1993.forgeperms.permissions.user.User;

/**
 *
 * @author Ryan
 */
public abstract class DataBase {

    public abstract void createUser(String userName);
    
    public abstract User loadUser(String userName);
    
    public abstract void saveUser(String userName);
    
    public abstract void createGroup(String groupName);
    
    public abstract Group loadGroup(String groupName);
    
    public abstract void saveGroup(String groupName);
    
    public abstract void removeGroup(String groupName);
}
