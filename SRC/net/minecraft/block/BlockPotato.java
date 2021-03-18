package net.minecraft.block;

import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.world.*;
import net.minecraft.item.*;
import net.minecraft.client.renderer.texture.*;

public class BlockPotato extends BlockCrops
{
    private IIcon[] field_149869_a;
    private static final String __OBFID = "CL_00000286";
    
    @Override
    public IIcon getIcon(final int p_149691_1_, int p_149691_2_) {
        if (p_149691_2_ < 7) {
            if (p_149691_2_ == 6) {
                p_149691_2_ = 5;
            }
            return this.field_149869_a[p_149691_2_ >> 1];
        }
        return this.field_149869_a[3];
    }
    
    @Override
    protected Item func_149866_i() {
        return Items.potato;
    }
    
    @Override
    protected Item func_149865_P() {
        return Items.potato;
    }
    
    @Override
    public void dropBlockAsItemWithChance(final World p_149690_1_, final int p_149690_2_, final int p_149690_3_, final int p_149690_4_, final int p_149690_5_, final float p_149690_6_, final int p_149690_7_) {
        super.dropBlockAsItemWithChance(p_149690_1_, p_149690_2_, p_149690_3_, p_149690_4_, p_149690_5_, p_149690_6_, p_149690_7_);
        if (!p_149690_1_.isClient && p_149690_5_ >= 7 && p_149690_1_.rand.nextInt(50) == 0) {
            this.dropBlockAsItem_do(p_149690_1_, p_149690_2_, p_149690_3_, p_149690_4_, new ItemStack(Items.poisonous_potato));
        }
    }
    
    @Override
    public void registerBlockIcons(final IIconRegister p_149651_1_) {
        this.field_149869_a = new IIcon[4];
        for (int var2 = 0; var2 < this.field_149869_a.length; ++var2) {
            this.field_149869_a[var2] = p_149651_1_.registerIcon(this.getTextureName() + "_stage_" + var2);
        }
    }
}
