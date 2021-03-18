package net.minecraft.util;

public class Facing
{
    public static final int[] oppositeSide;
    public static final int[] offsetsXForSide;
    public static final int[] offsetsYForSide;
    public static final int[] offsetsZForSide;
    public static final String[] facings;
    private static final String __OBFID = "CL_00001532";
    
    static {
        oppositeSide = new int[] { 1, 0, 3, 2, 5, 4 };
        offsetsXForSide = new int[] { 0, 0, 0, 0, -1, 1 };
        offsetsYForSide = new int[] { -1, 1, 0, 0, 0, 0 };
        offsetsZForSide = new int[] { 0, 0, -1, 1, 0, 0 };
        facings = new String[] { "DOWN", "UP", "NORTH", "SOUTH", "WEST", "EAST" };
    }
}
