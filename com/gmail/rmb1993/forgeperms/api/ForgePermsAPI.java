package com.gmail.rmb1993.forgeperms.api;

import com.gmail.rmb1993.forgeperms.ForgePerms;
import com.gmail.rmb1993.forgeperms.permissions.Permission;
import com.gmail.rmb1993.forgeperms.permissions.group.Group;
import com.gmail.rmb1993.forgeperms.permissions.user.User;

public class ForgePermsAPI {

    public static boolean hasPermission(String userName, String permission) {
        User u = ForgePerms.instance.config.getDb().loadUser(userName);
        for (Permission perm : u.getPermissions().keySet()) {
            if (perm.getPermission().equalsIgnoreCase(permission)) {
                return true;
            }
        }
        for (Group g : u.getGroups().keySet()) {
            for (Permission perm : g.getPermissions().keySet()) {
                if (perm.getPermission().equalsIgnoreCase(permission)) {
                    return true;
                }
            }
        }
        return false;
    }
}