package com.gmail.rmb1993.forgeperms.permissions;

/**
 *
 * @author Ryan
 */
public enum PermissionType {

    CONSOLE(0),OP(1),USER(2);
    
    int type;
    
    private PermissionType(int type) {
        this.type = type;
    }
    
}
