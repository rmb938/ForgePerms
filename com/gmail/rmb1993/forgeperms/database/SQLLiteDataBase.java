package com.gmail.rmb1993.forgeperms.database;

import com.gmail.rmb1993.forgeperms.ForgePermsContainer;
import com.gmail.rmb1993.forgeperms.permissions.group.Group;
import com.gmail.rmb1993.forgeperms.permissions.user.User;
/**
 *
 * @author Ryan
 */
public class SQLLiteDataBase extends DataBase {
    
    public SQLLiteDataBase(ForgePermsContainer fpc) {
        super(fpc);
    }

    @Override
    public void loadUsers() {
        
    }
    
    @Override
    public void loadCustomNodes() {
        
    }
    
    @Override
    public void createUser(String userName) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public User getUser(String userName) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void createGroup(String groupName) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public void loadGroups() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public Group getGroup(String groupName) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    @Override
    public void removeGroup(String groupName) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void saveUsers() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void saveGroups() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
