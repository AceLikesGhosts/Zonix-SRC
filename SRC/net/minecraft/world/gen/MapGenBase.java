package net.minecraft.world.gen;

import java.util.*;
import net.minecraft.world.*;
import net.minecraft.world.chunk.*;
import net.minecraft.block.*;

public class MapGenBase
{
    protected int range;
    protected Random rand;
    protected World worldObj;
    private static final String __OBFID = "CL_00000394";
    
    public MapGenBase() {
        this.range = 8;
        this.rand = new Random();
    }
    
    public void func_151539_a(final IChunkProvider p_151539_1_, final World p_151539_2_, final int p_151539_3_, final int p_151539_4_, final Block[] p_151539_5_) {
        final int var6 = this.range;
        this.worldObj = p_151539_2_;
        this.rand.setSeed(p_151539_2_.getSeed());
        final long var7 = this.rand.nextLong();
        final long var8 = this.rand.nextLong();
        for (int var9 = p_151539_3_ - var6; var9 <= p_151539_3_ + var6; ++var9) {
            for (int var10 = p_151539_4_ - var6; var10 <= p_151539_4_ + var6; ++var10) {
                final long var11 = var9 * var7;
                final long var12 = var10 * var8;
                this.rand.setSeed(var11 ^ var12 ^ p_151539_2_.getSeed());
                this.func_151538_a(p_151539_2_, var9, var10, p_151539_3_, p_151539_4_, p_151539_5_);
            }
        }
    }
    
    protected void func_151538_a(final World p_151538_1_, final int p_151538_2_, final int p_151538_3_, final int p_151538_4_, final int p_151538_5_, final Block[] p_151538_6_) {
    }
}
