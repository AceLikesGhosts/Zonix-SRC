package com.thevoxelbox.voxelmap.util;

import net.minecraft.client.*;
import net.minecraft.world.*;
import java.io.*;

public class GameVariableAccessShim
{
    private static Minecraft minecraft;
    
    public static Minecraft getMinecraft() {
        return GameVariableAccessShim.minecraft;
    }
    
    public static World getWorld() {
        return GameVariableAccessShim.minecraft.theWorld;
    }
    
    public static File getDataDir() {
        return GameVariableAccessShim.minecraft.mcDataDir;
    }
    
    public static int xCoord() {
        return (int)((GameVariableAccessShim.minecraft.thePlayer.posX < 0.0) ? (GameVariableAccessShim.minecraft.thePlayer.posX - 1.0) : GameVariableAccessShim.minecraft.thePlayer.posX);
    }
    
    public static int zCoord() {
        return (int)((GameVariableAccessShim.minecraft.thePlayer.posZ < 0.0) ? (GameVariableAccessShim.minecraft.thePlayer.posZ - 1.0) : GameVariableAccessShim.minecraft.thePlayer.posZ);
    }
    
    public static int yCoord() {
        return (int)GameVariableAccessShim.minecraft.thePlayer.posY;
    }
    
    public static double xCoordDouble() {
        return GameVariableAccessShim.minecraft.thePlayer.posX;
    }
    
    public static double zCoordDouble() {
        return GameVariableAccessShim.minecraft.thePlayer.posZ;
    }
    
    public static float rotationYaw() {
        return GameVariableAccessShim.minecraft.thePlayer.rotationYaw;
    }
    
    static {
        GameVariableAccessShim.minecraft = Minecraft.getMinecraft();
    }
}
