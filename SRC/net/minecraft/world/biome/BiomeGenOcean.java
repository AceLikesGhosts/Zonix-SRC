package net.minecraft.world.biome;

import net.minecraft.world.*;
import java.util.*;
import net.minecraft.block.*;

public class BiomeGenOcean extends BiomeGenBase
{
    private static final String __OBFID = "CL_00000179";
    
    public BiomeGenOcean(final int p_i1985_1_) {
        super(p_i1985_1_);
        this.spawnableCreatureList.clear();
    }
    
    @Override
    public TempCategory func_150561_m() {
        return TempCategory.OCEAN;
    }
    
    @Override
    public void func_150573_a(final World p_150573_1_, final Random p_150573_2_, final Block[] p_150573_3_, final byte[] p_150573_4_, final int p_150573_5_, final int p_150573_6_, final double p_150573_7_) {
        super.func_150573_a(p_150573_1_, p_150573_2_, p_150573_3_, p_150573_4_, p_150573_5_, p_150573_6_, p_150573_7_);
    }
}
