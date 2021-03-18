package net.minecraft.block;

import net.minecraft.util.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.world.*;

public class BlockRail extends BlockRailBase
{
    private IIcon field_150056_b;
    private static final String __OBFID = "CL_00000293";
    
    protected BlockRail() {
        super(false);
    }
    
    @Override
    public IIcon getIcon(final int p_149691_1_, final int p_149691_2_) {
        return (p_149691_2_ >= 6) ? this.field_150056_b : this.blockIcon;
    }
    
    @Override
    public void registerBlockIcons(final IIconRegister p_149651_1_) {
        super.registerBlockIcons(p_149651_1_);
        this.field_150056_b = p_149651_1_.registerIcon(this.getTextureName() + "_turned");
    }
    
    @Override
    protected void func_150048_a(final World p_150048_1_, final int p_150048_2_, final int p_150048_3_, final int p_150048_4_, final int p_150048_5_, final int p_150048_6_, final Block p_150048_7_) {
        if (p_150048_7_.canProvidePower() && new Rail(p_150048_1_, p_150048_2_, p_150048_3_, p_150048_4_).func_150650_a() == 3) {
            this.func_150052_a(p_150048_1_, p_150048_2_, p_150048_3_, p_150048_4_, false);
        }
    }
}
