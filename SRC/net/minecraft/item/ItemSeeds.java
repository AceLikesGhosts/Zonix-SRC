package net.minecraft.item;

import net.minecraft.block.*;
import net.minecraft.creativetab.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;

public class ItemSeeds extends Item
{
    private Block field_150925_a;
    private Block soilBlockID;
    private static final String __OBFID = "CL_00000061";
    
    public ItemSeeds(final Block p_i45352_1_, final Block p_i45352_2_) {
        this.field_150925_a = p_i45352_1_;
        this.soilBlockID = p_i45352_2_;
        this.setCreativeTab(CreativeTabs.tabMaterials);
    }
    
    @Override
    public boolean onItemUse(final ItemStack p_77648_1_, final EntityPlayer p_77648_2_, final World p_77648_3_, final int p_77648_4_, final int p_77648_5_, final int p_77648_6_, final int p_77648_7_, final float p_77648_8_, final float p_77648_9_, final float p_77648_10_) {
        if (p_77648_7_ != 1) {
            return false;
        }
        if (!p_77648_2_.canPlayerEdit(p_77648_4_, p_77648_5_, p_77648_6_, p_77648_7_, p_77648_1_) || !p_77648_2_.canPlayerEdit(p_77648_4_, p_77648_5_ + 1, p_77648_6_, p_77648_7_, p_77648_1_)) {
            return false;
        }
        if (p_77648_3_.getBlock(p_77648_4_, p_77648_5_, p_77648_6_) == this.soilBlockID && p_77648_3_.isAirBlock(p_77648_4_, p_77648_5_ + 1, p_77648_6_)) {
            p_77648_3_.setBlock(p_77648_4_, p_77648_5_ + 1, p_77648_6_, this.field_150925_a);
            --p_77648_1_.stackSize;
            return true;
        }
        return false;
    }
}
