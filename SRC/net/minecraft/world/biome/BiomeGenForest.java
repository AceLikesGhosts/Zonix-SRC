package net.minecraft.world.biome;

import net.minecraft.entity.passive.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.block.*;
import net.minecraft.world.*;
import net.minecraft.world.gen.feature.*;

public class BiomeGenForest extends BiomeGenBase
{
    private int field_150632_aF;
    protected static final WorldGenForest field_150629_aC;
    protected static final WorldGenForest field_150630_aD;
    protected static final WorldGenCanopyTree field_150631_aE;
    private static final String __OBFID = "CL_00000170";
    
    public BiomeGenForest(final int p_i45377_1_, final int p_i45377_2_) {
        super(p_i45377_1_);
        this.field_150632_aF = p_i45377_2_;
        this.theBiomeDecorator.treesPerChunk = 10;
        this.theBiomeDecorator.grassPerChunk = 2;
        if (this.field_150632_aF == 1) {
            this.theBiomeDecorator.treesPerChunk = 6;
            this.theBiomeDecorator.flowersPerChunk = 100;
            this.theBiomeDecorator.grassPerChunk = 1;
        }
        this.func_76733_a(5159473);
        this.setTemperatureRainfall(0.7f, 0.8f);
        if (this.field_150632_aF == 2) {
            this.field_150609_ah = 353825;
            this.color = 3175492;
            this.setTemperatureRainfall(0.6f, 0.6f);
        }
        if (this.field_150632_aF == 0) {
            this.spawnableCreatureList.add(new SpawnListEntry(EntityWolf.class, 5, 4, 4));
        }
        if (this.field_150632_aF == 3) {
            this.theBiomeDecorator.treesPerChunk = -999;
        }
    }
    
    @Override
    protected BiomeGenBase func_150557_a(final int p_150557_1_, final boolean p_150557_2_) {
        if (this.field_150632_aF == 2) {
            this.field_150609_ah = 353825;
            this.color = p_150557_1_;
            if (p_150557_2_) {
                this.field_150609_ah = (this.field_150609_ah & 0xFEFEFE) >> 1;
            }
            return this;
        }
        return super.func_150557_a(p_150557_1_, p_150557_2_);
    }
    
    @Override
    public WorldGenAbstractTree func_150567_a(final Random p_150567_1_) {
        return (this.field_150632_aF == 3 && p_150567_1_.nextInt(3) > 0) ? BiomeGenForest.field_150631_aE : ((this.field_150632_aF != 2 && p_150567_1_.nextInt(5) != 0) ? this.worldGeneratorTrees : BiomeGenForest.field_150630_aD);
    }
    
    @Override
    public String func_150572_a(final Random p_150572_1_, final int p_150572_2_, final int p_150572_3_, final int p_150572_4_) {
        if (this.field_150632_aF == 1) {
            final double var5 = MathHelper.clamp_double((1.0 + BiomeGenForest.field_150606_ad.func_151601_a(p_150572_2_ / 48.0, p_150572_4_ / 48.0)) / 2.0, 0.0, 0.9999);
            int var6 = (int)(var5 * BlockFlower.field_149859_a.length);
            if (var6 == 1) {
                var6 = 0;
            }
            return BlockFlower.field_149859_a[var6];
        }
        return super.func_150572_a(p_150572_1_, p_150572_2_, p_150572_3_, p_150572_4_);
    }
    
    @Override
    public void decorate(final World p_76728_1_, final Random p_76728_2_, final int p_76728_3_, final int p_76728_4_) {
        if (this.field_150632_aF == 3) {
            for (int var5 = 0; var5 < 4; ++var5) {
                for (int var6 = 0; var6 < 4; ++var6) {
                    final int var7 = p_76728_3_ + var5 * 4 + 1 + 8 + p_76728_2_.nextInt(3);
                    final int var8 = p_76728_4_ + var6 * 4 + 1 + 8 + p_76728_2_.nextInt(3);
                    final int var9 = p_76728_1_.getHeightValue(var7, var8);
                    if (p_76728_2_.nextInt(20) == 0) {
                        final WorldGenBigMushroom var10 = new WorldGenBigMushroom();
                        var10.generate(p_76728_1_, p_76728_2_, var7, var9, var8);
                    }
                    else {
                        final WorldGenAbstractTree var11 = this.func_150567_a(p_76728_2_);
                        var11.setScale(1.0, 1.0, 1.0);
                        if (var11.generate(p_76728_1_, p_76728_2_, var7, var9, var8)) {
                            var11.func_150524_b(p_76728_1_, p_76728_2_, var7, var9, var8);
                        }
                    }
                }
            }
        }
        int var5 = p_76728_2_.nextInt(5) - 3;
        if (this.field_150632_aF == 1) {
            var5 += 2;
        }
        for (int var6 = 0; var6 < var5; ++var6) {
            final int var7 = p_76728_2_.nextInt(3);
            if (var7 == 0) {
                BiomeGenForest.field_150610_ae.func_150548_a(1);
            }
            else if (var7 == 1) {
                BiomeGenForest.field_150610_ae.func_150548_a(4);
            }
            else if (var7 == 2) {
                BiomeGenForest.field_150610_ae.func_150548_a(5);
            }
            for (int var8 = 0; var8 < 5; ++var8) {
                final int var9 = p_76728_3_ + p_76728_2_.nextInt(16) + 8;
                final int var12 = p_76728_4_ + p_76728_2_.nextInt(16) + 8;
                final int var13 = p_76728_2_.nextInt(p_76728_1_.getHeightValue(var9, var12) + 32);
                if (BiomeGenForest.field_150610_ae.generate(p_76728_1_, p_76728_2_, var9, var13, var12)) {
                    break;
                }
            }
        }
        super.decorate(p_76728_1_, p_76728_2_, p_76728_3_, p_76728_4_);
    }
    
    @Override
    public int getBiomeGrassColor(final int p_150558_1_, final int p_150558_2_, final int p_150558_3_) {
        final int var4 = super.getBiomeGrassColor(p_150558_1_, p_150558_2_, p_150558_3_);
        return (this.field_150632_aF == 3) ? ((var4 & 0xFEFEFE) + 2634762 >> 1) : var4;
    }
    
    @Override
    protected BiomeGenBase func_150566_k() {
        if (this.biomeID == BiomeGenBase.forest.biomeID) {
            final BiomeGenForest var1 = new BiomeGenForest(this.biomeID + 128, 1);
            var1.func_150570_a(new Height(this.minHeight, this.maxHeight + 0.2f));
            var1.setBiomeName("Flower Forest");
            var1.func_150557_a(6976549, true);
            var1.func_76733_a(8233509);
            return var1;
        }
        return (this.biomeID != BiomeGenBase.field_150583_P.biomeID && this.biomeID != BiomeGenBase.field_150582_Q.biomeID) ? new BiomeGenMutated(this.biomeID + 128, this) {
            private static final String __OBFID = "CL_00000172";
            
            @Override
            public void decorate(final World p_76728_1_, final Random p_76728_2_, final int p_76728_3_, final int p_76728_4_) {
                this.field_150611_aD.decorate(p_76728_1_, p_76728_2_, p_76728_3_, p_76728_4_);
            }
        } : new BiomeGenMutated(this.biomeID + 128, this) {
            private static final String __OBFID = "CL_00001861";
            
            @Override
            public WorldGenAbstractTree func_150567_a(final Random p_150567_1_) {
                return p_150567_1_.nextBoolean() ? BiomeGenForest.field_150629_aC : BiomeGenForest.field_150630_aD;
            }
        };
    }
    
    static {
        field_150629_aC = new WorldGenForest(false, true);
        field_150630_aD = new WorldGenForest(false, false);
        field_150631_aE = new WorldGenCanopyTree(false);
    }
}
