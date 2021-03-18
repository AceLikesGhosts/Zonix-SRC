package net.minecraft.world.gen.structure;

import net.minecraft.entity.monster.*;
import net.minecraft.world.biome.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.world.*;

public class MapGenScatteredFeature extends MapGenStructure
{
    private static List biomelist;
    private List scatteredFeatureSpawnList;
    private int maxDistanceBetweenScatteredFeatures;
    private int minDistanceBetweenScatteredFeatures;
    private static final String __OBFID = "CL_00000471";
    
    public MapGenScatteredFeature() {
        this.scatteredFeatureSpawnList = new ArrayList();
        this.maxDistanceBetweenScatteredFeatures = 32;
        this.minDistanceBetweenScatteredFeatures = 8;
        this.scatteredFeatureSpawnList.add(new BiomeGenBase.SpawnListEntry(EntityWitch.class, 1, 1, 1));
    }
    
    public MapGenScatteredFeature(final Map p_i2061_1_) {
        this();
        for (final Map.Entry var3 : p_i2061_1_.entrySet()) {
            if (var3.getKey().equals("distance")) {
                this.maxDistanceBetweenScatteredFeatures = MathHelper.parseIntWithDefaultAndMax(var3.getValue(), this.maxDistanceBetweenScatteredFeatures, this.minDistanceBetweenScatteredFeatures + 1);
            }
        }
    }
    
    @Override
    public String func_143025_a() {
        return "Temple";
    }
    
    @Override
    protected boolean canSpawnStructureAtCoords(int p_75047_1_, int p_75047_2_) {
        final int var3 = p_75047_1_;
        final int var4 = p_75047_2_;
        if (p_75047_1_ < 0) {
            p_75047_1_ -= this.maxDistanceBetweenScatteredFeatures - 1;
        }
        if (p_75047_2_ < 0) {
            p_75047_2_ -= this.maxDistanceBetweenScatteredFeatures - 1;
        }
        int var5 = p_75047_1_ / this.maxDistanceBetweenScatteredFeatures;
        int var6 = p_75047_2_ / this.maxDistanceBetweenScatteredFeatures;
        final Random var7 = this.worldObj.setRandomSeed(var5, var6, 14357617);
        var5 *= this.maxDistanceBetweenScatteredFeatures;
        var6 *= this.maxDistanceBetweenScatteredFeatures;
        var5 += var7.nextInt(this.maxDistanceBetweenScatteredFeatures - this.minDistanceBetweenScatteredFeatures);
        var6 += var7.nextInt(this.maxDistanceBetweenScatteredFeatures - this.minDistanceBetweenScatteredFeatures);
        if (var3 == var5 && var4 == var6) {
            final BiomeGenBase var8 = this.worldObj.getWorldChunkManager().getBiomeGenAt(var3 * 16 + 8, var4 * 16 + 8);
            for (final BiomeGenBase var10 : MapGenScatteredFeature.biomelist) {
                if (var8 == var10) {
                    return true;
                }
            }
        }
        return false;
    }
    
    @Override
    protected StructureStart getStructureStart(final int p_75049_1_, final int p_75049_2_) {
        return new Start(this.worldObj, this.rand, p_75049_1_, p_75049_2_);
    }
    
    public boolean func_143030_a(final int p_143030_1_, final int p_143030_2_, final int p_143030_3_) {
        final StructureStart var4 = this.func_143028_c(p_143030_1_, p_143030_2_, p_143030_3_);
        if (var4 != null && var4 instanceof Start && !var4.components.isEmpty()) {
            final StructureComponent var5 = var4.components.getFirst();
            return var5 instanceof ComponentScatteredFeaturePieces.SwampHut;
        }
        return false;
    }
    
    public List getScatteredFeatureSpawnList() {
        return this.scatteredFeatureSpawnList;
    }
    
    static {
        MapGenScatteredFeature.biomelist = Arrays.asList(BiomeGenBase.desert, BiomeGenBase.desertHills, BiomeGenBase.jungle, BiomeGenBase.jungleHills, BiomeGenBase.swampland);
    }
    
    public static class Start extends StructureStart
    {
        private static final String __OBFID = "CL_00000472";
        
        public Start() {
        }
        
        public Start(final World p_i2060_1_, final Random p_i2060_2_, final int p_i2060_3_, final int p_i2060_4_) {
            super(p_i2060_3_, p_i2060_4_);
            final BiomeGenBase var5 = p_i2060_1_.getBiomeGenForCoords(p_i2060_3_ * 16 + 8, p_i2060_4_ * 16 + 8);
            if (var5 != BiomeGenBase.jungle && var5 != BiomeGenBase.jungleHills) {
                if (var5 == BiomeGenBase.swampland) {
                    final ComponentScatteredFeaturePieces.SwampHut var6 = new ComponentScatteredFeaturePieces.SwampHut(p_i2060_2_, p_i2060_3_ * 16, p_i2060_4_ * 16);
                    this.components.add(var6);
                }
                else {
                    final ComponentScatteredFeaturePieces.DesertPyramid var7 = new ComponentScatteredFeaturePieces.DesertPyramid(p_i2060_2_, p_i2060_3_ * 16, p_i2060_4_ * 16);
                    this.components.add(var7);
                }
            }
            else {
                final ComponentScatteredFeaturePieces.JunglePyramid var8 = new ComponentScatteredFeaturePieces.JunglePyramid(p_i2060_2_, p_i2060_3_ * 16, p_i2060_4_ * 16);
                this.components.add(var8);
            }
            this.updateBoundingBox();
        }
    }
}
