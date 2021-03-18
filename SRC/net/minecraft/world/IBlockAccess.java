package net.minecraft.world;

import net.minecraft.block.*;
import net.minecraft.tileentity.*;
import net.minecraft.world.biome.*;

public interface IBlockAccess
{
    Block getBlock(final int p0, final int p1, final int p2);
    
    TileEntity getTileEntity(final int p0, final int p1, final int p2);
    
    int getLightBrightnessForSkyBlocks(final int p0, final int p1, final int p2, final int p3);
    
    int getBlockMetadata(final int p0, final int p1, final int p2);
    
    boolean isAirBlock(final int p0, final int p1, final int p2);
    
    BiomeGenBase getBiomeGenForCoords(final int p0, final int p1);
    
    int getHeight();
    
    boolean extendedLevelsInChunkCache();
    
    int isBlockProvidingPowerTo(final int p0, final int p1, final int p2, final int p3);
}
