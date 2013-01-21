package com.gmail.rmb1993.forgeperms.api;

import com.gmail.rmb1993.forgeperms.ForgePerms;
import com.gmail.rmb1993.forgeperms.permissions.Permission;
import com.gmail.rmb1993.forgeperms.permissions.PermissionType;
import com.gmail.rmb1993.forgeperms.permissions.group.Group;
import com.gmail.rmb1993.forgeperms.permissions.user.User;
import java.util.ArrayList;
import net.minecraft.src.ModLoader;

public class ForgePermsAPI {

    public static boolean playerHasPermission(String playerName, String permission) {
        if (playerName.equalsIgnoreCase("server")) {
            return true;
        }
        Permission perm = ForgePerms.instance.permissions.get(permission);
        User u = ForgePerms.instance.config.getDb().loadUser(playerName);
        if (perm != null) {
            if (perm.getDefaultType() == PermissionType.CONSOLE) {
                return false;
            } else if (perm.getDefaultType() == PermissionType.OP) {
                if (ModLoader.getMinecraftServerInstance().getConfigurationManager().getOps().contains(playerName) == true) {
                    return true;
                }
            } else if (perm.getDefaultType() == PermissionType.USER) {
                if (u.getPermissions().containsKey("^" + permission) == true) {
                    return false;
                } else {
                    Group highestRank = u.getGroups().get(0);
                    for (Group g : u.getGroups()) {
                        if (g.getRank() > highestRank.getRank()) {
                            highestRank = g;
                        }
                    }
                    if (groupHasPermission(highestRank.getGroupName(), "^" + permission, false) == true) {
                        return false;
                    }
                }
                return true;
            }
        }
        if (u.getPermissions().containsKey(permission) == true) {
            return true;
        } else if (u.getPermissions().containsKey("^" + permission) == true) {
            return false;
        } else {
            for (Group g : u.getGroups()) {
                if (groupHasPermission(g.getGroupName(), permission, true) == true) {
                    return true;
                }
            }
        }
        return false;
    }

    public static ArrayList<Group> getPlayerGroups(String playerName) {
        if (playerName.equalsIgnoreCase("server")) {
            return null;
        }
        User u = ForgePerms.instance.config.getDb().loadUser(playerName);
        return u.getGroups();
    }

    public static boolean groupHasPermission(String groupName, String permission, boolean checkInherits) {
        Group g = ForgePerms.instance.config.getDb().loadGroup(groupName);
        if (g.getPermissions().containsKey(permission) == true) {
            return true;
        } else if (g.getPermissions().containsKey("^" + permission) == true) {
            return false;
        } else {
            if (checkInherits == true) {
                if (g.getGroups().size() > 0) {
                    for (Group g1 : g.getGroups()) {
                        if (g1 != null) {
                            if (groupHasPermission(g1.getGroupName(), permission, true) == true) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    public static ArrayList<Group> getGroupInheritted(String groupName) {
        Group g = ForgePerms.instance.config.getDb().loadGroup(groupName);
        return g.getGroups();
    }

    public static String getPlayerPrefix(String playerName) {
        if (playerName.equalsIgnoreCase("server")) {
            return null;
        }
        return getPlayerVar(playerName, "prefix");
    }

    public static String getPlayerSuffix(String playerName) {
        if (playerName.equalsIgnoreCase("server")) {
            return null;
        }
        return getPlayerVar(playerName, "suffix");
    }

    public static String getPlayerVar(String playerName, String varName) {
        User u = ForgePerms.instance.config.getDb().loadUser(playerName);
        if (u.getVars().get(varName) != null) {
            return u.getVars().get(varName);
        } else {
            Group highestRank = u.getGroups().get(0);
            for (Group g : u.getGroups()) {
                if (g.getRank() > highestRank.getRank()) {
                    highestRank = g;
                }
            }
            return getGroupVar(highestRank.getGroupName(), varName);
        }
    }

    public static String getGroupPrefix(String groupName) {
        return getGroupVar(groupName, "prefix");
    }

    public static String getGroupSuffix(String groupName) {
        return getGroupVar(groupName, "suffix");
    }

    public static String getGroupVar(String groupName, String varName) {
        Group g = ForgePerms.instance.config.getDb().loadGroup(groupName);
        return g.getVars().get(varName);
    }
}