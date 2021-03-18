package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;

public class BlockPressurePlateWeighted extends BlockBasePressurePlate
{
    private final int field_150068_a;
    private static final String __OBFID = "CL_00000334";
    
    protected BlockPressurePlateWeighted(final String p_i45436_1_, final Material p_i45436_2_, final int p_i45436_3_) {
        super(p_i45436_1_, p_i45436_2_);
        this.field_150068_a = p_i45436_3_;
    }
    
    @Override
    protected int func_150065_e(final World p_150065_1_, final int p_150065_2_, final int p_150065_3_, final int p_150065_4_) {
        final int var5 = Math.min(p_150065_1_.getEntitiesWithinAABB(Entity.class, this.func_150061_a(p_150065_2_, p_150065_3_, p_150065_4_)).size(), this.field_150068_a);
        if (var5 <= 0) {
            return 0;
        }
        final float var6 = Math.min(this.field_150068_a, var5) / (float)this.field_150068_a;
        return MathHelper.ceiling_float_int(var6 * 15.0f);
    }
    
    @Override
    protected int func_150060_c(final int p_150060_1_) {
        return p_150060_1_;
    }
    
    @Override
    protected int func_150066_d(final int p_150066_1_) {
        return p_150066_1_;
    }
    
    @Override
    public int func_149738_a(final World p_149738_1_) {
        return 10;
    }
}
