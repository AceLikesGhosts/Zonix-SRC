package net.minecraft.world.biome;

import net.minecraft.world.*;
import java.util.*;
import net.minecraft.block.*;
import net.minecraft.world.gen.feature.*;

public class BiomeGenMutated extends BiomeGenBase
{
    protected BiomeGenBase field_150611_aD;
    private static final String __OBFID = "CL_00000178";
    
    public BiomeGenMutated(final int p_i45381_1_, final BiomeGenBase p_i45381_2_) {
        super(p_i45381_1_);
        this.field_150611_aD = p_i45381_2_;
        this.func_150557_a(p_i45381_2_.color, true);
        this.biomeName = p_i45381_2_.biomeName + " M";
        this.topBlock = p_i45381_2_.topBlock;
        this.fillerBlock = p_i45381_2_.fillerBlock;
        this.field_76754_C = p_i45381_2_.field_76754_C;
        this.minHeight = p_i45381_2_.minHeight;
        this.maxHeight = p_i45381_2_.maxHeight;
        this.temperature = p_i45381_2_.temperature;
        this.rainfall = p_i45381_2_.rainfall;
        this.waterColorMultiplier = p_i45381_2_.waterColorMultiplier;
        this.enableSnow = p_i45381_2_.enableSnow;
        this.enableRain = p_i45381_2_.enableRain;
        this.spawnableCreatureList = new ArrayList(p_i45381_2_.spawnableCreatureList);
        this.spawnableMonsterList = new ArrayList(p_i45381_2_.spawnableMonsterList);
        this.spawnableCaveCreatureList = new ArrayList(p_i45381_2_.spawnableCaveCreatureList);
        this.spawnableWaterCreatureList = new ArrayList(p_i45381_2_.spawnableWaterCreatureList);
        this.temperature = p_i45381_2_.temperature;
        this.rainfall = p_i45381_2_.rainfall;
        this.minHeight = p_i45381_2_.minHeight + 0.1f;
        this.maxHeight = p_i45381_2_.maxHeight + 0.2f;
    }
    
    @Override
    public void decorate(final World p_76728_1_, final Random p_76728_2_, final int p_76728_3_, final int p_76728_4_) {
        this.field_150611_aD.theBiomeDecorator.func_150512_a(p_76728_1_, p_76728_2_, this, p_76728_3_, p_76728_4_);
    }
    
    @Override
    public void func_150573_a(final World p_150573_1_, final Random p_150573_2_, final Block[] p_150573_3_, final byte[] p_150573_4_, final int p_150573_5_, final int p_150573_6_, final double p_150573_7_) {
        this.field_150611_aD.func_150573_a(p_150573_1_, p_150573_2_, p_150573_3_, p_150573_4_, p_150573_5_, p_150573_6_, p_150573_7_);
    }
    
    @Override
    public float getSpawningChance() {
        return this.field_150611_aD.getSpawningChance();
    }
    
    @Override
    public WorldGenAbstractTree func_150567_a(final Random p_150567_1_) {
        return this.field_150611_aD.func_150567_a(p_150567_1_);
    }
    
    @Override
    public int getBiomeFoliageColor(final int p_150571_1_, final int p_150571_2_, final int p_150571_3_) {
        return this.field_150611_aD.getBiomeFoliageColor(p_150571_1_, p_150571_2_, p_150571_2_);
    }
    
    @Override
    public int getBiomeGrassColor(final int p_150558_1_, final int p_150558_2_, final int p_150558_3_) {
        return this.field_150611_aD.getBiomeGrassColor(p_150558_1_, p_150558_2_, p_150558_2_);
    }
    
    @Override
    public Class func_150562_l() {
        return this.field_150611_aD.func_150562_l();
    }
    
    @Override
    public boolean func_150569_a(final BiomeGenBase p_150569_1_) {
        return this.field_150611_aD.func_150569_a(p_150569_1_);
    }
    
    @Override
    public TempCategory func_150561_m() {
        return this.field_150611_aD.func_150561_m();
    }
}
