package com.gmail.rmb1993.forgeperms.api;

import com.gmail.rmb1993.forgeperms.ForgePerms;
import com.gmail.rmb1993.forgeperms.permissions.group.Group;
import com.gmail.rmb1993.forgeperms.permissions.user.User;

public class ForgePermsAPI {

    public static boolean hasPermission(String userName, String permission) {
        User u = ForgePerms.instance.config.getDb().loadUser(userName);
        if (u.getPermissions().containsValue(permission) == false) {
            for (Group g : u.getGroups().keySet()) {
                if (g.getPermissions().containsValue(permission)) {
                    return true;
                }
            }
        } else {
            return true;
        }
        return false;
    }
    
}