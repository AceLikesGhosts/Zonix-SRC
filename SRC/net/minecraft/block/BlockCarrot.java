package net.minecraft.block;

import net.minecraft.util.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.client.renderer.texture.*;

public class BlockCarrot extends BlockCrops
{
    private IIcon[] field_149868_a;
    private static final String __OBFID = "CL_00000212";
    
    @Override
    public IIcon getIcon(final int p_149691_1_, int p_149691_2_) {
        if (p_149691_2_ < 7) {
            if (p_149691_2_ == 6) {
                p_149691_2_ = 5;
            }
            return this.field_149868_a[p_149691_2_ >> 1];
        }
        return this.field_149868_a[3];
    }
    
    @Override
    protected Item func_149866_i() {
        return Items.carrot;
    }
    
    @Override
    protected Item func_149865_P() {
        return Items.carrot;
    }
    
    @Override
    public void registerBlockIcons(final IIconRegister p_149651_1_) {
        this.field_149868_a = new IIcon[4];
        for (int var2 = 0; var2 < this.field_149868_a.length; ++var2) {
            this.field_149868_a[var2] = p_149651_1_.registerIcon(this.getTextureName() + "_stage_" + var2);
        }
    }
}
