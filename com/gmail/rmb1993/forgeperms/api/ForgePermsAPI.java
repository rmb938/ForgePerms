package com.gmail.rmb1993.forgeperms.api;

import com.gmail.rmb1993.forgeperms.ForgePermsContainer;
import com.gmail.rmb1993.forgeperms.permissions.Permission;
import com.gmail.rmb1993.forgeperms.permissions.PermissionType;
import com.gmail.rmb1993.forgeperms.permissions.group.Group;
import com.gmail.rmb1993.forgeperms.permissions.user.User;
import java.util.ArrayList;
import net.minecraft.src.ModLoader;

public class ForgePermsAPI {
    
    public static ForgePermsContainer fpc;
    
    public static boolean playerHasPermission(String playerName, String permission) {
        permission = permission.toLowerCase();
        if (playerName.equalsIgnoreCase("server")) {
            return true;
        }
        
        User u = fpc.config.getDb().getUser(playerName);
        Permission perm = fpc.permissions.get(permission);
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
                    Group highestRank = fpc.config.getDb().getGroup(u.getGroups().get(0));
                    for (String gN : u.getGroups()) {
                        Group g = fpc.config.getDb().getGroup(gN);
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
        
        for (String cNode : u.getCustomPermissions().keySet()) {
            ArrayList<String> nodes = fpc.customNodes.get(cNode);
            for (String node : nodes) {
                if (node.equalsIgnoreCase(permission)) {
                    return true;
                }
            }
        }
        
        if (u.getPermissions().containsKey(permission) == true) {
            return true;
        } else if (u.getPermissions().containsKey("^" + permission) == true) {
            return false;
        } else {
            for (String gN : u.getGroups()) {
                Group g = fpc.config.getDb().getGroup(gN);
                if (groupHasPermission(g.getGroupName(), permission, true) == true) {
                    return true;
                }
            }
        }
        return false;
    }

    public static ArrayList<String> getPlayerGroups(String playerName) {
        if (playerName.equalsIgnoreCase("server")) {
            return null;
        }
        User u = fpc.config.getDb().getUser(playerName);
        return u.getGroups();
    }

    private static boolean groupHasPermission(String groupName, String permission, boolean checkInherits) {
        permission = permission.toLowerCase();
        Group g = fpc.config.getDb().getGroup(groupName);
        
        for (String cNode : g.getCustomPermissions().keySet()) {
            ArrayList<String> nodes = fpc.customNodes.get(cNode);
            for (String node : nodes) {
                if (node.equalsIgnoreCase(permission)) {
                    return true;
                }
            }
        }
        
        if (g.getPermissions().containsKey(permission) == true) {
            return true;
        } else if (g.getPermissions().containsKey("^" + permission) == true) {
            return false;
        } else {
            if (checkInherits == true) {
                if (g.getInheritance().size() > 0) {
                    for (String g1N : g.getInheritance()) {
                        Group g1 = fpc.config.getDb().getGroup(g1N);
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

    public static ArrayList<String> getGroupInheritted(String groupName) {
        Group g = fpc.config.getDb().getGroup(groupName);
        return g.getInheritance();
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

    public static void setPlayerVar(String playerName, String varName, String varValue) {
        User u = fpc.config.getDb().getUser(playerName);
        u.getVars().put(varName, varValue);
        fpc.config.getDb().saveUsers();
    }
    
    public static String getPlayerVar(String playerName, String varName) {
        User u = fpc.config.getDb().getUser(playerName);
        if (u.getVars().get(varName) != null) {
            return u.getVars().get(varName);
        } else {
            Group highestRank = fpc.config.getDb().getGroup(u.getGroups().get(0));
            for (String gN : u.getGroups()) {
                Group g = fpc.config.getDb().getGroup(gN);
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

    public static void setGroupVar(String groupName, String varName, String varValue) {
        Group g = fpc.config.getDb().getGroup(groupName);
        g.getVars().put(varName, varValue);
        fpc.config.getDb().saveGroups();
    }
    
    public static String getGroupVar(String groupName, String varName) {
        Group g = fpc.config.getDb().getGroup(groupName);
        return g.getVars().get(varName);
    }
}