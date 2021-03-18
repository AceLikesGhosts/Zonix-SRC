package net.minecraft.world.biome;

import net.minecraft.init.*;
import java.util.*;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.*;
import net.minecraft.block.*;

public class BiomeGenHills extends BiomeGenBase
{
    private WorldGenerator theWorldGenerator;
    private WorldGenTaiga2 field_150634_aD;
    private int field_150635_aE;
    private int field_150636_aF;
    private int field_150637_aG;
    private int field_150638_aH;
    private static final String __OBFID = "CL_00000168";
    
    protected BiomeGenHills(final int p_i45373_1_, final boolean p_i45373_2_) {
        super(p_i45373_1_);
        this.theWorldGenerator = new WorldGenMinable(Blocks.monster_egg, 8);
        this.field_150634_aD = new WorldGenTaiga2(false);
        this.field_150635_aE = 0;
        this.field_150636_aF = 1;
        this.field_150637_aG = 2;
        this.field_150638_aH = this.field_150635_aE;
        if (p_i45373_2_) {
            this.theBiomeDecorator.treesPerChunk = 3;
            this.field_150638_aH = this.field_150636_aF;
        }
    }
    
    @Override
    public WorldGenAbstractTree func_150567_a(final Random p_150567_1_) {
        return (p_150567_1_.nextInt(3) > 0) ? this.field_150634_aD : super.func_150567_a(p_150567_1_);
    }
    
    @Override
    public void decorate(final World p_76728_1_, final Random p_76728_2_, final int p_76728_3_, final int p_76728_4_) {
        super.decorate(p_76728_1_, p_76728_2_, p_76728_3_, p_76728_4_);
        for (int var5 = 3 + p_76728_2_.nextInt(6), var6 = 0; var6 < var5; ++var6) {
            final int var7 = p_76728_3_ + p_76728_2_.nextInt(16);
            final int var8 = p_76728_2_.nextInt(28) + 4;
            final int var9 = p_76728_4_ + p_76728_2_.nextInt(16);
            if (p_76728_1_.getBlock(var7, var8, var9) == Blocks.stone) {
                p_76728_1_.setBlock(var7, var8, var9, Blocks.emerald_ore, 0, 2);
            }
        }
        for (int var5 = 0; var5 < 7; ++var5) {
            final int var6 = p_76728_3_ + p_76728_2_.nextInt(16);
            final int var7 = p_76728_2_.nextInt(64);
            final int var8 = p_76728_4_ + p_76728_2_.nextInt(16);
            this.theWorldGenerator.generate(p_76728_1_, p_76728_2_, var6, var7, var8);
        }
    }
    
    @Override
    public void func_150573_a(final World p_150573_1_, final Random p_150573_2_, final Block[] p_150573_3_, final byte[] p_150573_4_, final int p_150573_5_, final int p_150573_6_, final double p_150573_7_) {
        this.topBlock = Blocks.grass;
        this.field_150604_aj = 0;
        this.fillerBlock = Blocks.dirt;
        if ((p_150573_7_ < -1.0 || p_150573_7_ > 2.0) && this.field_150638_aH == this.field_150637_aG) {
            this.topBlock = Blocks.gravel;
            this.fillerBlock = Blocks.gravel;
        }
        else if (p_150573_7_ > 1.0 && this.field_150638_aH != this.field_150636_aF) {
            this.topBlock = Blocks.stone;
            this.fillerBlock = Blocks.stone;
        }
        this.func_150560_b(p_150573_1_, p_150573_2_, p_150573_3_, p_150573_4_, p_150573_5_, p_150573_6_, p_150573_7_);
    }
    
    private BiomeGenHills func_150633_b(final BiomeGenBase p_150633_1_) {
        this.field_150638_aH = this.field_150637_aG;
        this.func_150557_a(p_150633_1_.color, true);
        this.setBiomeName(p_150633_1_.biomeName + " M");
        this.func_150570_a(new Height(p_150633_1_.minHeight, p_150633_1_.maxHeight));
        this.setTemperatureRainfall(p_150633_1_.temperature, p_150633_1_.rainfall);
        return this;
    }
    
    @Override
    protected BiomeGenBase func_150566_k() {
        return new BiomeGenHills(this.biomeID + 128, false).func_150633_b(this);
    }
}
