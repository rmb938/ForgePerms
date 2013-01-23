package com.gmail.rmb1993.forgeperms.commands.user;

import com.gmail.rmb1993.forgeperms.ForgePermsPlugin;
import com.gmail.rmb1993.forgeperms.ForgePermsContainer;
import com.gmail.rmb1993.forgeperms.api.ForgePermsAPI;
import com.gmail.rmb1993.forgeperms.permissions.group.Group;
import com.gmail.rmb1993.forgeperms.permissions.group.Track;
import com.gmail.rmb1993.forgeperms.permissions.user.User;
import com.gmail.rmb1993.forgeperms.utils.StringColors;
import net.minecraft.command.ICommandSender;

public class Demote {

    public Demote(ICommandSender sender, String[] args) {
        if (args.length == 3) {
            User u = ForgePermsContainer.instance.config.getDb().getUser(sender.getCommandSenderName());
            if (ForgePermsAPI.playerHasPermission(sender.getCommandSenderName(), "permissions.demote.all")) {
                User u1 = ForgePermsContainer.instance.config.getDb().getUser(args[1]);
                if (u1 == null) {
                    sender.sendChatToPlayer(StringColors.EnumTextColor.RED.colorString("Sorry the user "+args[1]+" does not exist!"));
                    return;
                }
                Track track = ForgePermsContainer.instance.tracks.get(args[2]);
                if (track == null) {
                    sender.sendChatToPlayer(StringColors.EnumTextColor.RED.colorString("Sorry track " + args[2] + " does not exist!"));
                    return;
                }
                Group groupInTrack = null;
                for (String gN : u1.getGroups()) {
                    Group g = ForgePermsContainer.instance.config.getDb().getGroup(gN);
                    if (g.getTrack().equalsIgnoreCase(track.getTrackName())) {
                        groupInTrack = g;
                        break;
                    }
                }
                if (groupInTrack == null) {
                    sender.sendChatToPlayer(StringColors.EnumTextColor.RED.colorString("Sorry " + args[1] + " is not part of a group in track " + args[2]));
                    return;
                }
                Group prevGroup = track.getPreviousGroup(groupInTrack);
                if (prevGroup == null) {
                    sender.sendChatToPlayer(StringColors.EnumTextColor.RED.colorString("Sorry " + args[1] + " can not be demoted anymore!"));
                    return;
                }
                u1.getGroups().remove(groupInTrack.getGroupName());
                u1.getGroups().add(prevGroup.getGroupName());
                ForgePermsContainer.instance.config.getDb().saveUsers();
                sender.sendChatToPlayer(StringColors.EnumTextColor.DARK_GREEN.colorString("You demoted " + args[1] + " to group " + prevGroup.getGroupName() + " in track " + args[2]));
            } else if (ForgePermsAPI.playerHasPermission(sender.getCommandSenderName(), "permissions.demote")) {
                User u1 = ForgePermsContainer.instance.config.getDb().getUser(args[1]);
                if (u1 == null) {
                    sender.sendChatToPlayer(StringColors.EnumTextColor.RED.colorString("Sorry the user "+args[1]+" does not exist!"));
                    return;
                }
                Track track = ForgePermsContainer.instance.tracks.get(args[2]);
                if (track == null) {
                    sender.sendChatToPlayer(StringColors.EnumTextColor.RED.colorString("Sorry track " + args[2] + " does not exist!"));
                    return;
                }
                boolean notInTrack = true;
                Group myGroup = null;
                for (String gN : u.getGroups()) {
                    Group g = ForgePermsContainer.instance.config.getDb().getGroup(gN);
                    if (g.getTrack().equalsIgnoreCase(track.getTrackName())) {
                        notInTrack = false;
                        myGroup = g;
                        break;
                    }
                }
                if (notInTrack == true) {
                    sender.sendChatToPlayer(StringColors.EnumTextColor.RED.colorString("You do not have permision to demote " + args[1] + " (Not in track " + args[2] + ")"));
                    return;
                }
                Group groupInTrack = null;
                for (String gN : u1.getGroups()) {
                    Group g = ForgePermsContainer.instance.config.getDb().getGroup(gN);
                    if (g.getTrack().equalsIgnoreCase(track.getTrackName())) {
                        groupInTrack = g;
                        break;
                    }
                }
                if (groupInTrack == null) {
                    sender.sendChatToPlayer(StringColors.EnumTextColor.RED.colorString("Sorry " + args[1] + " is not part of a group in track " + args[2]));
                    return;
                }

                if (myGroup.getRank() > groupInTrack.getRank()) {
                    sender.sendChatToPlayer(StringColors.EnumTextColor.RED.colorString("You are not high enough rank to demote " + args[1]));
                    return;
                }

                Group prevGroup = track.getPreviousGroup(groupInTrack);
                u1.getGroups().remove(groupInTrack.getGroupName());
                u1.getGroups().add(prevGroup.getGroupName());
                ForgePermsContainer.instance.config.getDb().saveUsers();
                sender.sendChatToPlayer(StringColors.EnumTextColor.DARK_GREEN.colorString("You demoted " + args[1] + " to group " + prevGroup.getGroupName() + " in track " + args[2]));
            } else {
                sender.sendChatToPlayer(StringColors.EnumTextColor.RED.colorString("You do not have permission to use this command."));
            }
        } else {
            sender.sendChatToPlayer(StringColors.EnumTextColor.RED.colorString("Usage: /user promote [userName] [trackName]"));
        }
    }
}
