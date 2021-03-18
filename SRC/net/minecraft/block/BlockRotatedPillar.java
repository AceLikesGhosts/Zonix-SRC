package net.minecraft.block;

import net.minecraft.util.*;
import net.minecraft.block.material.*;
import net.minecraft.world.*;
import net.minecraft.item.*;

public abstract class BlockRotatedPillar extends Block
{
    protected IIcon field_150164_N;
    private static final String __OBFID = "CL_00000302";
    
    protected BlockRotatedPillar(final Material p_i45425_1_) {
        super(p_i45425_1_);
    }
    
    @Override
    public int getRenderType() {
        return 31;
    }
    
    @Override
    public int onBlockPlaced(final World p_149660_1_, final int p_149660_2_, final int p_149660_3_, final int p_149660_4_, final int p_149660_5_, final float p_149660_6_, final float p_149660_7_, final float p_149660_8_, final int p_149660_9_) {
        final int var10 = p_149660_9_ & 0x3;
        byte var11 = 0;
        switch (p_149660_5_) {
            case 0:
            case 1: {
                var11 = 0;
                break;
            }
            case 2:
            case 3: {
                var11 = 8;
                break;
            }
            case 4:
            case 5: {
                var11 = 4;
                break;
            }
        }
        return var10 | var11;
    }
    
    @Override
    public IIcon getIcon(final int p_149691_1_, final int p_149691_2_) {
        final int var3 = p_149691_2_ & 0xC;
        final int var4 = p_149691_2_ & 0x3;
        return (var3 == 0 && (p_149691_1_ == 1 || p_149691_1_ == 0)) ? this.func_150161_d(var4) : ((var3 == 4 && (p_149691_1_ == 5 || p_149691_1_ == 4)) ? this.func_150161_d(var4) : ((var3 == 8 && (p_149691_1_ == 2 || p_149691_1_ == 3)) ? this.func_150161_d(var4) : this.func_150163_b(var4)));
    }
    
    protected abstract IIcon func_150163_b(final int p0);
    
    protected IIcon func_150161_d(final int p_150161_1_) {
        return this.field_150164_N;
    }
    
    @Override
    public int damageDropped(final int p_149692_1_) {
        return p_149692_1_ & 0x3;
    }
    
    public int func_150162_k(final int p_150162_1_) {
        return p_150162_1_ & 0x3;
    }
    
    @Override
    protected ItemStack createStackedBlock(final int p_149644_1_) {
        return new ItemStack(Item.getItemFromBlock(this), 1, this.func_150162_k(p_149644_1_));
    }
}
