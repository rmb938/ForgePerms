package com.gmail.rmb1993.forgeperms.database;

import com.gmail.rmb1993.forgeperms.permissions.group.Group;
import com.gmail.rmb1993.forgeperms.permissions.user.User;

/**
 *
 * @author Ryan
 */
public abstract class DataBase {

    public abstract void loadCustomNodes();
    
    public abstract void loadUsers();
    
    public abstract void createUser(String userName);
    
    public abstract User getUser(String userName);
    
    public abstract void saveUsers();
    
    public abstract void createGroup(String groupName);
    
    public abstract void loadGroups();
    
    public abstract Group getGroup(String groupName);
    
    public abstract void saveGroups();
    
    public abstract void removeGroup(String groupName);
}
