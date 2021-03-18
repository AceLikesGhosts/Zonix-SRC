package net.minecraft.src;

import net.minecraft.block.*;
import net.minecraft.world.*;
import java.util.*;

public class ChunkCacheOF extends ChunkCache
{
    private IBlockAccess blockAccess;
    private BlockPos position;
    private int[] combinedLights;
    private Block[] blocks;
    private static ArrayCache cacheCombinedLights;
    private static ArrayCache cacheBlocks;
    private static final int ARRAY_SIZE = 8000;
    
    public ChunkCacheOF(final World world, final int xMin, final int yMin, final int zMin, final int xMax, final int yMax, final int zMax, final int subIn) {
        super(world, xMin, yMin, zMin, xMax, yMax, zMax, subIn);
        this.blockAccess = world;
        this.position = new BlockPos(xMin - subIn, yMin - subIn, zMin - subIn);
    }
    
    @Override
    public int getLightBrightnessForSkyBlocks(final int x, final int y, final int z, final int lightValue) {
        if (this.combinedLights == null) {
            int index = this.blockAccess.getLightBrightnessForSkyBlocks(x, y, z, lightValue);
            if (Config.isDynamicLights() && !this.getBlock(x, y, z).isOpaqueCube()) {
                index = DynamicLights.getCombinedLight(x, y, z, index);
            }
            return index;
        }
        int index = this.getPositionIndex(x, y, z);
        int light = this.combinedLights[index];
        if (light == -1) {
            light = this.blockAccess.getLightBrightnessForSkyBlocks(x, y, z, lightValue);
            if (Config.isDynamicLights() && !this.getBlock(x, y, z).isOpaqueCube()) {
                light = DynamicLights.getCombinedLight(x, y, z, light);
            }
            this.combinedLights[index] = light;
        }
        return light;
    }
    
    @Override
    public Block getBlock(final int x, final int y, final int z) {
        if (this.blocks == null) {
            return this.blockAccess.getBlock(x, y, z);
        }
        final int index = this.getPositionIndex(x, y, z);
        Block block = this.blocks[index];
        if (block == null) {
            block = this.blockAccess.getBlock(x, y, z);
            this.blocks[index] = block;
        }
        return block;
    }
    
    private int getPositionIndex(final int x, final int y, final int z) {
        final int i = x - this.position.getX();
        final int j = y - this.position.getY();
        final int k = z - this.position.getZ();
        return i * 400 + k * 20 + j;
    }
    
    public void renderStart() {
        if (this.combinedLights == null) {
            this.combinedLights = (int[])ChunkCacheOF.cacheCombinedLights.allocate(8000);
        }
        Arrays.fill(this.combinedLights, -1);
        if (this.blocks == null) {
            this.blocks = (Block[])ChunkCacheOF.cacheBlocks.allocate(8000);
        }
        Arrays.fill(this.blocks, null);
    }
    
    public void renderFinish() {
        ChunkCacheOF.cacheCombinedLights.free(this.combinedLights);
        this.combinedLights = null;
        ChunkCacheOF.cacheBlocks.free(this.blocks);
        this.blocks = null;
    }
    
    static {
        ChunkCacheOF.cacheCombinedLights = new ArrayCache(Integer.TYPE, 16);
        ChunkCacheOF.cacheBlocks = new ArrayCache(Block.class, 16);
    }
}
