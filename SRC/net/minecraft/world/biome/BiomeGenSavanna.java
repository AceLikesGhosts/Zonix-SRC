package net.minecraft.world.biome;

import net.minecraft.entity.passive.*;
import java.util.*;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.*;
import net.minecraft.block.*;
import net.minecraft.init.*;

public class BiomeGenSavanna extends BiomeGenBase
{
    private static final WorldGenSavannaTree field_150627_aC;
    private static final String __OBFID = "CL_00000182";
    
    protected BiomeGenSavanna(final int p_i45383_1_) {
        super(p_i45383_1_);
        this.spawnableCreatureList.add(new SpawnListEntry(EntityHorse.class, 1, 2, 6));
        this.theBiomeDecorator.treesPerChunk = 1;
        this.theBiomeDecorator.flowersPerChunk = 4;
        this.theBiomeDecorator.grassPerChunk = 20;
    }
    
    @Override
    public WorldGenAbstractTree func_150567_a(final Random p_150567_1_) {
        return (p_150567_1_.nextInt(5) > 0) ? BiomeGenSavanna.field_150627_aC : this.worldGeneratorTrees;
    }
    
    @Override
    protected BiomeGenBase func_150566_k() {
        final Mutated var1 = new Mutated(this.biomeID + 128, this);
        var1.temperature = (this.temperature + 1.0f) * 0.5f;
        var1.minHeight = this.minHeight * 0.5f + 0.3f;
        var1.maxHeight = this.maxHeight * 0.5f + 1.2f;
        return var1;
    }
    
    @Override
    public void decorate(final World p_76728_1_, final Random p_76728_2_, final int p_76728_3_, final int p_76728_4_) {
        BiomeGenSavanna.field_150610_ae.func_150548_a(2);
        for (int var5 = 0; var5 < 7; ++var5) {
            final int var6 = p_76728_3_ + p_76728_2_.nextInt(16) + 8;
            final int var7 = p_76728_4_ + p_76728_2_.nextInt(16) + 8;
            final int var8 = p_76728_2_.nextInt(p_76728_1_.getHeightValue(var6, var7) + 32);
            BiomeGenSavanna.field_150610_ae.generate(p_76728_1_, p_76728_2_, var6, var8, var7);
        }
        super.decorate(p_76728_1_, p_76728_2_, p_76728_3_, p_76728_4_);
    }
    
    static {
        field_150627_aC = new WorldGenSavannaTree(false);
    }
    
    public static class Mutated extends BiomeGenMutated
    {
        private static final String __OBFID = "CL_00000183";
        
        public Mutated(final int p_i45382_1_, final BiomeGenBase p_i45382_2_) {
            super(p_i45382_1_, p_i45382_2_);
            this.theBiomeDecorator.treesPerChunk = 2;
            this.theBiomeDecorator.flowersPerChunk = 2;
            this.theBiomeDecorator.grassPerChunk = 5;
        }
        
        @Override
        public void func_150573_a(final World p_150573_1_, final Random p_150573_2_, final Block[] p_150573_3_, final byte[] p_150573_4_, final int p_150573_5_, final int p_150573_6_, final double p_150573_7_) {
            this.topBlock = Blocks.grass;
            this.field_150604_aj = 0;
            this.fillerBlock = Blocks.dirt;
            if (p_150573_7_ > 1.75) {
                this.topBlock = Blocks.stone;
                this.fillerBlock = Blocks.stone;
            }
            else if (p_150573_7_ > -0.5) {
                this.topBlock = Blocks.dirt;
                this.field_150604_aj = 1;
            }
            this.func_150560_b(p_150573_1_, p_150573_2_, p_150573_3_, p_150573_4_, p_150573_5_, p_150573_6_, p_150573_7_);
        }
        
        @Override
        public void decorate(final World p_76728_1_, final Random p_76728_2_, final int p_76728_3_, final int p_76728_4_) {
            this.theBiomeDecorator.func_150512_a(p_76728_1_, p_76728_2_, this, p_76728_3_, p_76728_4_);
        }
    }
}
