package com.thevoxelbox.voxelmap.util;

import com.thevoxelbox.voxelmap.interfaces.*;
import net.minecraft.server.*;
import net.minecraft.command.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.chunk.*;
import java.util.*;
import net.minecraft.world.*;
import net.minecraft.block.material.*;
import net.minecraft.block.*;

public class CommandServerZanTp extends CommandBase
{
    private IWaypointManager waypointManager;
    
    public CommandServerZanTp(final IWaypointManager waypointManager) {
        this.waypointManager = waypointManager;
    }
    
    @Override
    public String getCommandName() {
        return "ztp";
    }
    
    @Override
    public String getCommandUsage(final ICommandSender par1ICommandSender) {
        return "/ztp [waypointName]";
    }
    
    @Override
    public void processCommand(final ICommandSender par1ICommandSender, final String[] par2ArrayOfStr) {
        if (par2ArrayOfStr.length < 1) {
            throw new WrongUsageException("/ztp [waypointName]", new Object[0]);
        }
        final MinecraftServer server = MinecraftServer.getServer();
        EntityPlayerMP player = null;
        if (player == null) {
            player = CommandBase.getCommandSenderAsPlayer(par1ICommandSender);
        }
        if (player == null) {
            throw new PlayerNotFoundException();
        }
        String waypointName = par2ArrayOfStr[0];
        for (int t = 1; t < par2ArrayOfStr.length; ++t) {
            waypointName += " ";
            waypointName += par2ArrayOfStr[t];
        }
        final ArrayList<Waypoint> waypoints = this.waypointManager.getWaypoints();
        Waypoint waypoint = null;
        for (final Waypoint wpt : waypoints) {
            if (wpt.name.equalsIgnoreCase(waypointName)) {
                waypoint = wpt;
            }
        }
        final boolean inNether = player.dimension == -1;
        if (waypoint != null && player.worldObj != null) {
            final int bound = 30000000;
            final int x = CommandBase.parseIntBounded(par1ICommandSender, "" + waypoint.getX(), -bound, bound);
            final int z = CommandBase.parseIntBounded(par1ICommandSender, "" + waypoint.getZ(), -bound, bound);
            int y = waypoint.getY();
            if (inNether) {
                final Chunk chunk = player.worldObj.getChunkFromBlockCoords(x, z);
                player.worldObj.getChunkProvider().loadChunk(x, z);
                int safeY = -1;
                for (int t2 = 0; t2 < 127; ++t2) {
                    if (y + t2 < 127 && this.isBlockStandable(player.worldObj, x, y + t2, z) && this.isBlockOpen(player.worldObj, x, y + t2 + 1, z) && this.isBlockOpen(player.worldObj, x, y + t2 + 2, z)) {
                        safeY = y + t2 + 1;
                        t2 = 128;
                    }
                    if (y - t2 > 0 && this.isBlockStandable(player.worldObj, x, y - t2, z) && this.isBlockOpen(player.worldObj, x, y - t2 + 1, z) && this.isBlockOpen(player.worldObj, x, y - t2 + 2, z)) {
                        safeY = y - t2 + 1;
                        t2 = 128;
                    }
                }
                if (safeY == -1) {
                    return;
                }
                y = safeY;
            }
            else {
                if (waypoint.getY() == -1) {
                    y = player.worldObj.getHeightValue(x, z);
                }
                if (y == 0) {
                    final Chunk chunk = player.worldObj.getChunkFromBlockCoords(x, z);
                    y = player.worldObj.getHeightValue(x, z);
                }
            }
            player.setPositionAndUpdate(x + 0.5f, y, z + 0.5f);
        }
    }
    
    @Override
    public List addTabCompletionOptions(final ICommandSender par1ICommandSender, final String[] par2ArrayOfStr) {
        return (par2ArrayOfStr.length != 1 && par2ArrayOfStr.length != 2) ? null : CommandBase.getListOfStringsMatchingLastWord(par2ArrayOfStr, MinecraftServer.getServer().getAllUsernames());
    }
    
    private boolean isBlockStandable(final World worldObj, final int par1, final int par2, final int par3) {
        final Block block = worldObj.getBlock(par1, par2, par3);
        if (block.getMaterial() == Material.air) {
            return block instanceof BlockFence;
        }
        return block != null && block.getMaterial().isOpaque();
    }
    
    private boolean isBlockOpen(final World worldObj, final int par1, final int par2, final int par3) {
        final Block block = worldObj.getBlock(par1, par2, par3);
        if (block.getMaterial() == Material.air) {
            return !(block instanceof BlockFence);
        }
        return block == null;
    }
}
