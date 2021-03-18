package net.minecraft.world.gen.feature;

import net.minecraft.world.*;
import java.util.*;
import net.minecraft.block.*;

public abstract class WorldGenerator
{
    private final boolean doBlockNotify;
    private static final String __OBFID = "CL_00000409";
    
    public WorldGenerator() {
        this.doBlockNotify = false;
    }
    
    public WorldGenerator(final boolean p_i2013_1_) {
        this.doBlockNotify = p_i2013_1_;
    }
    
    public abstract boolean generate(final World p0, final Random p1, final int p2, final int p3, final int p4);
    
    public void setScale(final double p_76487_1_, final double p_76487_3_, final double p_76487_5_) {
    }
    
    protected void func_150515_a(final World p_150515_1_, final int p_150515_2_, final int p_150515_3_, final int p_150515_4_, final Block p_150515_5_) {
        this.func_150516_a(p_150515_1_, p_150515_2_, p_150515_3_, p_150515_4_, p_150515_5_, 0);
    }
    
    protected void func_150516_a(final World p_150516_1_, final int p_150516_2_, final int p_150516_3_, final int p_150516_4_, final Block p_150516_5_, final int p_150516_6_) {
        if (this.doBlockNotify) {
            p_150516_1_.setBlock(p_150516_2_, p_150516_3_, p_150516_4_, p_150516_5_, p_150516_6_, 3);
        }
        else {
            p_150516_1_.setBlock(p_150516_2_, p_150516_3_, p_150516_4_, p_150516_5_, p_150516_6_, 2);
        }
    }
}
