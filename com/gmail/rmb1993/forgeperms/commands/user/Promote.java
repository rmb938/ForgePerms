package com.gmail.rmb1993.forgeperms.commands.user;

import com.gmail.rmb1993.forgeperms.ForgePermsPlugin;
import com.gmail.rmb1993.forgeperms.ForgePermsContainer;
import com.gmail.rmb1993.forgeperms.api.ForgePermsAPI;
import com.gmail.rmb1993.forgeperms.permissions.group.Group;
import com.gmail.rmb1993.forgeperms.permissions.group.Track;
import com.gmail.rmb1993.forgeperms.permissions.user.User;
import com.gmail.rmb1993.forgeperms.utils.FontColour;
import net.minecraft.command.ICommandSender;

public class Promote {

    public Promote(ForgePermsContainer fpc, ICommandSender sender, String[] args) {
        if (args.length == 3) {
            User u = fpc.config.getDb().getUser(sender.getCommandSenderName());
            if (ForgePermsAPI.playerHasPermission(sender.getCommandSenderName(), "permissions.promote.all")) {
                User u1 = fpc.config.getDb().getUser(args[1]);
                if (u1 == null) {
                    sender.sendChatToPlayer(FontColour.RED + "Sorry the user "+args[1]+" does not exist!");
                    return;
                }
                Track track = fpc.tracks.get(args[2]);
                if (track == null) {
                    sender.sendChatToPlayer(FontColour.RED + "Sorry track " + args[2] + " does not exist!");
                    return;
                }
                Group groupInTrack = null;
                for (String gN : u1.getGroups()) {
                    Group g = fpc.config.getDb().getGroup(gN);
                    if (g.getTrack().equalsIgnoreCase(track.getTrackName())) {
                        groupInTrack = g;
                        break;
                    }
                }
                if (groupInTrack == null) {
                    sender.sendChatToPlayer(FontColour.RED + "Sorry " + args[1] + " is not part of a group in track " + args[2]);
                    return;
                }
                Group nextGroup = track.getNextGroup(groupInTrack);
                if (nextGroup == null) {
                    sender.sendChatToPlayer(FontColour.RED + "Sorry "+args[1]+" can not be promoted anymore!");
                    return;
                }
                u1.getGroups().remove(groupInTrack.getGroupName());
                u1.getGroups().add(nextGroup.getGroupName());
                fpc.config.getDb().saveUsers();
                sender.sendChatToPlayer(FontColour.DARK_GREEN + "You promoted " + args[1] + " to group " + nextGroup.getGroupName() + " in track " + args[2]);
            } else if (ForgePermsAPI.playerHasPermission(sender.getCommandSenderName(), "permissions.promote")) {
                User u1 = fpc.config.getDb().getUser(args[1]);
                if (u1 == null) {
                    sender.sendChatToPlayer(FontColour.RED + "Sorry the user "+args[1]+" does not exist!");
                    return;
                }
                Track track = fpc.tracks.get(args[2]);
                if (track == null) {
                    sender.sendChatToPlayer(FontColour.RED + "Sorry track " + args[2] + " does not exist!");
                    return;
                }
                boolean notInTrack = true;
                Group myGroup = null;
                for (String gN : u.getGroups()) {
                    Group g = fpc.config.getDb().getGroup(gN);
                    if (g.getTrack().equalsIgnoreCase(track.getTrackName())) {
                        notInTrack = false;
                        myGroup = g;
                        break;
                    }
                }
                if (notInTrack == true) {
                    sender.sendChatToPlayer(FontColour.RED + "You do not have permision to promote " + args[1] + " (Not in track " + args[2] + ")");
                    return;
                }
                Group groupInTrack = null;
                for (String gN: u1.getGroups()) {
                    Group g = fpc.config.getDb().getGroup(gN);
                    if (g.getTrack().equalsIgnoreCase(track.getTrackName())) {
                        groupInTrack = g;
                        break;
                    }
                }
                if (groupInTrack == null) {
                    sender.sendChatToPlayer(FontColour.RED + "Sorry " + args[1] + " is not part of a group in track " + args[2]);
                    return;
                }

                if (myGroup.getRank() > groupInTrack.getRank()) {
                    sender.sendChatToPlayer(FontColour.RED + "You are not high enough rank to promote " + args[1]);
                    return;
                }

                Group nextGroup = track.getNextGroup(groupInTrack);
                u1.getGroups().remove(groupInTrack.getGroupName());
                u1.getGroups().add(nextGroup.getGroupName());
                fpc.config.getDb().saveUsers();
                sender.sendChatToPlayer(FontColour.DARK_GREEN + "You promoted " + args[1] + " to group " + nextGroup.getGroupName() + " in track " + args[2]);
            } else {
                sender.sendChatToPlayer(FontColour.RED + "You do not have permission to use this command.");
            }
        } else {
            sender.sendChatToPlayer(FontColour.RED + "Usage: /user promote [userName] [trackName]");
        }
    }
}
