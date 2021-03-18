package net.minecraft.world;

import net.minecraft.world.biome.*;
import net.minecraft.world.chunk.*;
import net.minecraft.world.gen.*;
import net.minecraft.util.*;

public class WorldProviderEnd extends WorldProvider
{
    private static final String __OBFID = "CL_00000389";
    
    public void registerWorldChunkManager() {
        this.worldChunkMgr = new WorldChunkManagerHell(BiomeGenBase.sky, 0.0f);
        this.dimensionId = 1;
        this.hasNoSky = true;
    }
    
    @Override
    public IChunkProvider createChunkGenerator() {
        return new ChunkProviderEnd(this.worldObj, this.worldObj.getSeed());
    }
    
    @Override
    public float calculateCelestialAngle(final long p_76563_1_, final float p_76563_3_) {
        return 0.0f;
    }
    
    @Override
    public float[] calcSunriseSunsetColors(final float p_76560_1_, final float p_76560_2_) {
        return null;
    }
    
    @Override
    public Vec3 getFogColor(final float p_76562_1_, final float p_76562_2_) {
        final int var3 = 10518688;
        float var4 = MathHelper.cos(p_76562_1_ * 3.1415927f * 2.0f) * 2.0f + 0.5f;
        if (var4 < 0.0f) {
            var4 = 0.0f;
        }
        if (var4 > 1.0f) {
            var4 = 1.0f;
        }
        float var5 = (var3 >> 16 & 0xFF) / 255.0f;
        float var6 = (var3 >> 8 & 0xFF) / 255.0f;
        float var7 = (var3 & 0xFF) / 255.0f;
        var5 *= var4 * 0.0f + 0.15f;
        var6 *= var4 * 0.0f + 0.15f;
        var7 *= var4 * 0.0f + 0.15f;
        return Vec3.createVectorHelper(var5, var6, var7);
    }
    
    @Override
    public boolean isSkyColored() {
        return false;
    }
    
    @Override
    public boolean canRespawnHere() {
        return false;
    }
    
    @Override
    public boolean isSurfaceWorld() {
        return false;
    }
    
    @Override
    public float getCloudHeight() {
        return 8.0f;
    }
    
    @Override
    public boolean canCoordinateBeSpawn(final int p_76566_1_, final int p_76566_2_) {
        return this.worldObj.getTopBlock(p_76566_1_, p_76566_2_).getMaterial().blocksMovement();
    }
    
    @Override
    public ChunkCoordinates getEntrancePortalLocation() {
        return new ChunkCoordinates(100, 50, 0);
    }
    
    @Override
    public int getAverageGroundLevel() {
        return 50;
    }
    
    @Override
    public boolean doesXZShowFog(final int p_76568_1_, final int p_76568_2_) {
        return true;
    }
    
    @Override
    public String getDimensionName() {
        return "The End";
    }
}
