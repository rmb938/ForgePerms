package com.gmail.rmb1993.forgeperms.permissions;

/**
 *
 * @author Ryan
 */
public class Permission {

    private String permission;
    private PermissionType defaultType = PermissionType.OP;
    
    public Permission(String permission, PermissionType defaultType) {
        this.permission = permission.toLowerCase();
        this.defaultType = defaultType;
    }

    public PermissionType getDefaultType() {
        return defaultType;
    }

    public void setDefaultType(PermissionType defaultType) {
        this.defaultType = defaultType;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }
    
}
