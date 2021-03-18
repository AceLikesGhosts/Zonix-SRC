package net.minecraft.world.gen.layer;

import net.minecraft.world.*;
import net.minecraft.world.biome.*;
import java.util.concurrent.*;
import net.minecraft.util.*;
import net.minecraft.crash.*;

public abstract class GenLayer
{
    private long worldGenSeed;
    protected GenLayer parent;
    private long chunkSeed;
    protected long baseSeed;
    private static final String __OBFID = "CL_00000559";
    
    public static GenLayer[] initializeAllBiomeGenerators(final long p_75901_0_, final WorldType p_75901_2_) {
        final boolean var3 = false;
        final GenLayerIsland var4 = new GenLayerIsland(1L);
        final GenLayerFuzzyZoom var5 = new GenLayerFuzzyZoom(2000L, var4);
        GenLayerAddIsland var6 = new GenLayerAddIsland(1L, var5);
        GenLayerZoom var7 = new GenLayerZoom(2001L, var6);
        var6 = new GenLayerAddIsland(2L, var7);
        var6 = new GenLayerAddIsland(50L, var6);
        var6 = new GenLayerAddIsland(70L, var6);
        final GenLayerRemoveTooMuchOcean var8 = new GenLayerRemoveTooMuchOcean(2L, var6);
        final GenLayerAddSnow var9 = new GenLayerAddSnow(2L, var8);
        var6 = new GenLayerAddIsland(3L, var9);
        GenLayerEdge var10 = new GenLayerEdge(2L, var6, GenLayerEdge.Mode.COOL_WARM);
        var10 = new GenLayerEdge(2L, var10, GenLayerEdge.Mode.HEAT_ICE);
        var10 = new GenLayerEdge(3L, var10, GenLayerEdge.Mode.SPECIAL);
        var7 = new GenLayerZoom(2002L, var10);
        var7 = new GenLayerZoom(2003L, var7);
        var6 = new GenLayerAddIsland(4L, var7);
        final GenLayerAddMushroomIsland var11 = new GenLayerAddMushroomIsland(5L, var6);
        final GenLayerDeepOcean var12 = new GenLayerDeepOcean(4L, var11);
        final GenLayer var13 = GenLayerZoom.magnify(1000L, var12, 0);
        byte var14 = 4;
        if (p_75901_2_ == WorldType.LARGE_BIOMES) {
            var14 = 6;
        }
        if (var3) {
            var14 = 4;
        }
        GenLayer var15 = GenLayerZoom.magnify(1000L, var13, 0);
        final GenLayerRiverInit var16 = new GenLayerRiverInit(100L, var15);
        Object var17 = new GenLayerBiome(200L, var13, p_75901_2_);
        if (!var3) {
            final GenLayer var18 = GenLayerZoom.magnify(1000L, (GenLayer)var17, 2);
            var17 = new GenLayerBiomeEdge(1000L, var18);
        }
        final GenLayer var19 = GenLayerZoom.magnify(1000L, var16, 2);
        final GenLayerHills var20 = new GenLayerHills(1000L, (GenLayer)var17, var19);
        var15 = GenLayerZoom.magnify(1000L, var16, 2);
        var15 = GenLayerZoom.magnify(1000L, var15, var14);
        final GenLayerRiver var21 = new GenLayerRiver(1L, var15);
        final GenLayerSmooth var22 = new GenLayerSmooth(1000L, var21);
        var17 = new GenLayerRareBiome(1001L, var20);
        for (int var23 = 0; var23 < var14; ++var23) {
            var17 = new GenLayerZoom(1000 + var23, (GenLayer)var17);
            if (var23 == 0) {
                var17 = new GenLayerAddIsland(3L, (GenLayer)var17);
            }
            if (var23 == 1) {
                var17 = new GenLayerShore(1000L, (GenLayer)var17);
            }
        }
        final GenLayerSmooth var24 = new GenLayerSmooth(1000L, (GenLayer)var17);
        final GenLayerRiverMix var25 = new GenLayerRiverMix(100L, var24, var22);
        final GenLayerVoronoiZoom var26 = new GenLayerVoronoiZoom(10L, var25);
        var25.initWorldGenSeed(p_75901_0_);
        var26.initWorldGenSeed(p_75901_0_);
        return new GenLayer[] { var25, var26, var25 };
    }
    
    public GenLayer(final long p_i2125_1_) {
        this.baseSeed = p_i2125_1_;
        this.baseSeed *= this.baseSeed * 6364136223846793005L + 1442695040888963407L;
        this.baseSeed += p_i2125_1_;
        this.baseSeed *= this.baseSeed * 6364136223846793005L + 1442695040888963407L;
        this.baseSeed += p_i2125_1_;
        this.baseSeed *= this.baseSeed * 6364136223846793005L + 1442695040888963407L;
        this.baseSeed += p_i2125_1_;
    }
    
    public void initWorldGenSeed(final long p_75905_1_) {
        this.worldGenSeed = p_75905_1_;
        if (this.parent != null) {
            this.parent.initWorldGenSeed(p_75905_1_);
        }
        this.worldGenSeed *= this.worldGenSeed * 6364136223846793005L + 1442695040888963407L;
        this.worldGenSeed += this.baseSeed;
        this.worldGenSeed *= this.worldGenSeed * 6364136223846793005L + 1442695040888963407L;
        this.worldGenSeed += this.baseSeed;
        this.worldGenSeed *= this.worldGenSeed * 6364136223846793005L + 1442695040888963407L;
        this.worldGenSeed += this.baseSeed;
    }
    
    public void initChunkSeed(final long p_75903_1_, final long p_75903_3_) {
        this.chunkSeed = this.worldGenSeed;
        this.chunkSeed *= this.chunkSeed * 6364136223846793005L + 1442695040888963407L;
        this.chunkSeed += p_75903_1_;
        this.chunkSeed *= this.chunkSeed * 6364136223846793005L + 1442695040888963407L;
        this.chunkSeed += p_75903_3_;
        this.chunkSeed *= this.chunkSeed * 6364136223846793005L + 1442695040888963407L;
        this.chunkSeed += p_75903_1_;
        this.chunkSeed *= this.chunkSeed * 6364136223846793005L + 1442695040888963407L;
        this.chunkSeed += p_75903_3_;
    }
    
    protected int nextInt(final int p_75902_1_) {
        int var2 = (int)((this.chunkSeed >> 24) % p_75902_1_);
        if (var2 < 0) {
            var2 += p_75902_1_;
        }
        this.chunkSeed *= this.chunkSeed * 6364136223846793005L + 1442695040888963407L;
        this.chunkSeed += this.worldGenSeed;
        return var2;
    }
    
    public abstract int[] getInts(final int p0, final int p1, final int p2, final int p3);
    
    protected static boolean func_151616_a(final int p_151616_0_, final int p_151616_1_) {
        if (p_151616_0_ == p_151616_1_) {
            return true;
        }
        if (p_151616_0_ != BiomeGenBase.field_150607_aa.biomeID && p_151616_0_ != BiomeGenBase.field_150608_ab.biomeID) {
            try {
                return BiomeGenBase.func_150568_d(p_151616_0_) != null && BiomeGenBase.func_150568_d(p_151616_1_) != null && BiomeGenBase.func_150568_d(p_151616_0_).func_150569_a(BiomeGenBase.func_150568_d(p_151616_1_));
            }
            catch (Throwable var4) {
                final CrashReport var3 = CrashReport.makeCrashReport(var4, "Comparing biomes");
                final CrashReportCategory var5 = var3.makeCategory("Biomes being compared");
                var5.addCrashSection("Biome A ID", p_151616_0_);
                var5.addCrashSection("Biome B ID", p_151616_1_);
                var5.addCrashSectionCallable("Biome A", new Callable() {
                    private static final String __OBFID = "CL_00000560";
                    
                    @Override
                    public String call() {
                        return String.valueOf(BiomeGenBase.func_150568_d(p_151616_0_));
                    }
                });
                var5.addCrashSectionCallable("Biome B", new Callable() {
                    private static final String __OBFID = "CL_00000561";
                    
                    @Override
                    public String call() {
                        return String.valueOf(BiomeGenBase.func_150568_d(p_151616_1_));
                    }
                });
                throw new ReportedException(var3);
            }
        }
        return p_151616_1_ == BiomeGenBase.field_150607_aa.biomeID || p_151616_1_ == BiomeGenBase.field_150608_ab.biomeID;
    }
    
    protected static boolean func_151618_b(final int p_151618_0_) {
        return p_151618_0_ == BiomeGenBase.ocean.biomeID || p_151618_0_ == BiomeGenBase.field_150575_M.biomeID || p_151618_0_ == BiomeGenBase.frozenOcean.biomeID;
    }
    
    protected int func_151619_a(final int... p_151619_1_) {
        return p_151619_1_[this.nextInt(p_151619_1_.length)];
    }
    
    protected int func_151617_b(final int p_151617_1_, final int p_151617_2_, final int p_151617_3_, final int p_151617_4_) {
        return (p_151617_2_ == p_151617_3_ && p_151617_3_ == p_151617_4_) ? p_151617_2_ : ((p_151617_1_ == p_151617_2_ && p_151617_1_ == p_151617_3_) ? p_151617_1_ : ((p_151617_1_ == p_151617_2_ && p_151617_1_ == p_151617_4_) ? p_151617_1_ : ((p_151617_1_ == p_151617_3_ && p_151617_1_ == p_151617_4_) ? p_151617_1_ : ((p_151617_1_ == p_151617_2_ && p_151617_3_ != p_151617_4_) ? p_151617_1_ : ((p_151617_1_ == p_151617_3_ && p_151617_2_ != p_151617_4_) ? p_151617_1_ : ((p_151617_1_ == p_151617_4_ && p_151617_2_ != p_151617_3_) ? p_151617_1_ : ((p_151617_2_ == p_151617_3_ && p_151617_1_ != p_151617_4_) ? p_151617_2_ : ((p_151617_2_ == p_151617_4_ && p_151617_1_ != p_151617_3_) ? p_151617_2_ : ((p_151617_3_ == p_151617_4_ && p_151617_1_ != p_151617_2_) ? p_151617_3_ : this.func_151619_a(p_151617_1_, p_151617_2_, p_151617_3_, p_151617_4_))))))))));
    }
}
