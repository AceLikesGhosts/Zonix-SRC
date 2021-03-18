package net.minecraft.world.biome;

import net.minecraft.init.*;
import net.minecraft.world.*;
import java.util.*;
import net.minecraft.world.gen.feature.*;

public class BiomeGenSnow extends BiomeGenBase
{
    private boolean field_150615_aC;
    private WorldGenIceSpike field_150616_aD;
    private WorldGenIcePath field_150617_aE;
    private static final String __OBFID = "CL_00000174";
    
    public BiomeGenSnow(final int p_i45378_1_, final boolean p_i45378_2_) {
        super(p_i45378_1_);
        this.field_150616_aD = new WorldGenIceSpike();
        this.field_150617_aE = new WorldGenIcePath(4);
        this.field_150615_aC = p_i45378_2_;
        if (p_i45378_2_) {
            this.topBlock = Blocks.snow;
        }
        this.spawnableCreatureList.clear();
    }
    
    @Override
    public void decorate(final World p_76728_1_, final Random p_76728_2_, final int p_76728_3_, final int p_76728_4_) {
        if (this.field_150615_aC) {
            for (int var5 = 0; var5 < 3; ++var5) {
                final int var6 = p_76728_3_ + p_76728_2_.nextInt(16) + 8;
                final int var7 = p_76728_4_ + p_76728_2_.nextInt(16) + 8;
                this.field_150616_aD.generate(p_76728_1_, p_76728_2_, var6, p_76728_1_.getHeightValue(var6, var7), var7);
            }
            for (int var5 = 0; var5 < 2; ++var5) {
                final int var6 = p_76728_3_ + p_76728_2_.nextInt(16) + 8;
                final int var7 = p_76728_4_ + p_76728_2_.nextInt(16) + 8;
                this.field_150617_aE.generate(p_76728_1_, p_76728_2_, var6, p_76728_1_.getHeightValue(var6, var7), var7);
            }
        }
        super.decorate(p_76728_1_, p_76728_2_, p_76728_3_, p_76728_4_);
    }
    
    @Override
    public WorldGenAbstractTree func_150567_a(final Random p_150567_1_) {
        return new WorldGenTaiga2(false);
    }
    
    @Override
    protected BiomeGenBase func_150566_k() {
        final BiomeGenBase var1 = new BiomeGenSnow(this.biomeID + 128, true).func_150557_a(13828095, true).setBiomeName(this.biomeName + " Spikes").setEnableSnow().setTemperatureRainfall(0.0f, 0.5f).func_150570_a(new Height(this.minHeight + 0.1f, this.maxHeight + 0.1f));
        var1.minHeight = this.minHeight + 0.3f;
        var1.maxHeight = this.maxHeight + 0.4f;
        return var1;
    }
}
