package com.gmail.rmb1993.forgeperms.commands.user;

import com.gmail.rmb1993.forgeperms.ForgePermsPlugin;
import com.gmail.rmb1993.forgeperms.ForgePermsContainer;
import com.gmail.rmb1993.forgeperms.api.ForgePermsAPI;
import com.gmail.rmb1993.forgeperms.permissions.group.Group;
import com.gmail.rmb1993.forgeperms.permissions.group.Track;
import com.gmail.rmb1993.forgeperms.permissions.user.User;
import com.gmail.rmb1993.forgeperms.utils.StringColors;
import net.minecraft.command.ICommandSender;

public class Promote {

    public Promote(ICommandSender sender, String[] args) {
        if (args.length == 3) {
            User u = ForgePermsContainer.instance.config.getDb().loadUser(sender.getCommandSenderName());
            if (ForgePermsAPI.playerHasPermission(sender.getCommandSenderName(), "permissions.promote.all")) {
                User u1 = ForgePermsContainer.instance.config.getDb().loadUser(args[1]);
                Track track = ForgePermsContainer.instance.tracks.get(args[2]);
                if (track == null) {
                    sender.sendChatToPlayer(StringColors.EnumTextColor.RED.colorString("Sorry track " + args[2] + " does not exist!"));
                    return;
                }
                Group groupInTrack = null;
                for (String gN : u1.getGroups()) {
                    Group g = ForgePermsContainer.instance.config.getDb().loadGroup(gN);
                    if (g.getTrack().equalsIgnoreCase(track.getTrackName())) {
                        groupInTrack = g;
                        break;
                    }
                }
                if (groupInTrack == null) {
                    sender.sendChatToPlayer(StringColors.EnumTextColor.RED.colorString("Sorry " + args[1] + " is not part of a group in track " + args[2]));
                    return;
                }
                Group nextGroup = track.getNextGroup(groupInTrack);
                if (nextGroup == null) {
                    sender.sendChatToPlayer(StringColors.EnumTextColor.RED.colorString("Sorry "+args[1]+" can not be promoted anymore!"));
                    return;
                }
                u1.getGroups().remove(groupInTrack.getGroupName());
                u1.getGroups().add(nextGroup.getGroupName());
                ForgePermsContainer.instance.config.getDb().saveUsers();
                sender.sendChatToPlayer(StringColors.EnumTextColor.DARK_GREEN.colorString("You promoted " + args[1] + " to group " + nextGroup.getGroupName() + " in track " + args[2]));
            } else if (ForgePermsAPI.playerHasPermission(sender.getCommandSenderName(), "permissions.promote")) {
                User u1 = ForgePermsContainer.instance.config.getDb().loadUser(args[1]);
                Track track = ForgePermsContainer.instance.tracks.get(args[2]);
                if (track == null) {
                    sender.sendChatToPlayer(StringColors.EnumTextColor.RED.colorString("Sorry track " + args[2] + " does not exist!"));
                    return;
                }
                boolean notInTrack = true;
                Group myGroup = null;
                for (String gN : u.getGroups()) {
                    Group g = ForgePermsContainer.instance.config.getDb().loadGroup(gN);
                    if (g.getTrack().equalsIgnoreCase(track.getTrackName())) {
                        notInTrack = false;
                        myGroup = g;
                        break;
                    }
                }
                if (notInTrack == true) {
                    sender.sendChatToPlayer(StringColors.EnumTextColor.RED.colorString("You do not have permision to promote " + args[1] + " (Not in track " + args[2] + ")"));
                    return;
                }
                Group groupInTrack = null;
                for (String gN: u1.getGroups()) {
                    Group g = ForgePermsContainer.instance.config.getDb().loadGroup(gN);
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
                    sender.sendChatToPlayer(StringColors.EnumTextColor.RED.colorString("You are not high enough rank to promote " + args[1]));
                    return;
                }

                Group nextGroup = track.getNextGroup(groupInTrack);
                u1.getGroups().remove(groupInTrack.getGroupName());
                u1.getGroups().add(nextGroup.getGroupName());
                ForgePermsContainer.instance.config.getDb().saveUsers();
                sender.sendChatToPlayer(StringColors.EnumTextColor.DARK_GREEN.colorString("You promoted " + args[1] + " to group " + nextGroup.getGroupName() + " in track " + args[2]));
            } else {
                sender.sendChatToPlayer(StringColors.EnumTextColor.RED.colorString("You do not have permission to use this command."));
            }
        } else {
            sender.sendChatToPlayer(StringColors.EnumTextColor.RED.colorString("Usage: /user promote [userName] [trackName]"));
        }
    }
}
