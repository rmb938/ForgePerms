package com.gmail.rmb1993.forgeperms.commands.user;

import com.gmail.rmb1993.forgeperms.ForgePerms;
import com.gmail.rmb1993.forgeperms.api.ForgePermsAPI;
import com.gmail.rmb1993.forgeperms.permissions.group.Group;
import com.gmail.rmb1993.forgeperms.permissions.group.Track;
import com.gmail.rmb1993.forgeperms.permissions.user.User;
import net.minecraft.command.ICommandSender;

public class Demote {

    public Demote(ICommandSender sender, String[] args) {
        if (args.length == 3) {
            User u = ForgePerms.instance.users.get(sender.getCommandSenderName());
            if (u == null) {
            } else {
                if (ForgePermsAPI.playerHasPermission(sender.getCommandSenderName(), "permissions.promote.all")) {
                    User u1 = ForgePerms.instance.users.get(args[1]);
                    Track track = ForgePerms.instance.tracks.get(args[2]);
                    if (track == null) {
                        sender.sendChatToPlayer("Sorry track " + args[2] + " does not exist!");
                        return;
                    }
                    Group groupInTrack = null;
                    for (Group g : u1.getGroups()) {
                        if (g.getTrack() == track) {
                            groupInTrack = g;
                            break;
                        }
                    }
                    if (groupInTrack == null) {
                        sender.sendChatToPlayer("Sorry " + args[1] + " is not part of a group in track " + args[2]);
                        return;
                    }
                    Group prevGroup = track.getPreviousGroup(groupInTrack);
                    u1.getGroups().remove(groupInTrack);
                    u1.getGroups().add(prevGroup);
                    sender.sendChatToPlayer("You demoted " + args[1] + " to group " + prevGroup.getGroupName() + " in track " + args[2]);
                } else if (ForgePermsAPI.playerHasPermission(sender.getCommandSenderName(), "permissions.promote")) {
                    User u1 = ForgePerms.instance.users.get(args[1]);
                    Track track = ForgePerms.instance.tracks.get(args[2]);
                    if (track == null) {
                        sender.sendChatToPlayer("Sorry track " + args[2] + " does not exist!");
                        return;
                    }
                    boolean notInTrack = true;
                    Group myGroup = null;
                    for (Group g : u.getGroups()) {
                        if (g.getTrack() == track) {
                            notInTrack = false;
                            myGroup = g;
                            break;
                        }
                    }
                    if (notInTrack == true) {
                        sender.sendChatToPlayer("You do not have permision to demote " + args[1] + " (Not in track " + args[2] + ")");
                        return;
                    }
                    Group groupInTrack = null;
                    for (Group g : u1.getGroups()) {
                        if (g.getTrack() == track) {
                            groupInTrack = g;
                            break;
                        }
                    }
                    if (groupInTrack == null) {
                        sender.sendChatToPlayer("Sorry " + args[1] + " is not part of a group in track " + args[2]);
                        return;
                    }

                    if (myGroup.getRank() > groupInTrack.getRank()) {
                        sender.sendChatToPlayer("You are not high enough rank to demote " + args[1]);
                        return;
                    }

                    Group prevGroup = track.getPreviousGroup(groupInTrack);
                    u1.getGroups().remove(groupInTrack);
                    u1.getGroups().add(prevGroup);
                    sender.sendChatToPlayer("You demoted " + args[1] + " to group " + prevGroup.getGroupName() + " in track " + args[2]);
                } else {
                    sender.sendChatToPlayer("You do not have permission to use this command.");
                }
            }
        } else {
            sender.sendChatToPlayer("Usage: /user promote [userName] [trackName]");
        }
    }
}
