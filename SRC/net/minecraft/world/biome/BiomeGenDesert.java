package net.minecraft.world.biome;

import net.minecraft.init.*;
import net.minecraft.world.*;
import java.util.*;
import net.minecraft.world.gen.feature.*;

public class BiomeGenDesert extends BiomeGenBase
{
    private static final String __OBFID = "CL_00000167";
    
    public BiomeGenDesert(final int p_i1977_1_) {
        super(p_i1977_1_);
        this.spawnableCreatureList.clear();
        this.topBlock = Blocks.sand;
        this.fillerBlock = Blocks.sand;
        this.theBiomeDecorator.treesPerChunk = -999;
        this.theBiomeDecorator.deadBushPerChunk = 2;
        this.theBiomeDecorator.reedsPerChunk = 50;
        this.theBiomeDecorator.cactiPerChunk = 10;
        this.spawnableCreatureList.clear();
    }
    
    @Override
    public void decorate(final World p_76728_1_, final Random p_76728_2_, final int p_76728_3_, final int p_76728_4_) {
        super.decorate(p_76728_1_, p_76728_2_, p_76728_3_, p_76728_4_);
        if (p_76728_2_.nextInt(1000) == 0) {
            final int var5 = p_76728_3_ + p_76728_2_.nextInt(16) + 8;
            final int var6 = p_76728_4_ + p_76728_2_.nextInt(16) + 8;
            final WorldGenDesertWells var7 = new WorldGenDesertWells();
            var7.generate(p_76728_1_, p_76728_2_, var5, p_76728_1_.getHeightValue(var5, var6) + 1, var6);
        }
    }
}
