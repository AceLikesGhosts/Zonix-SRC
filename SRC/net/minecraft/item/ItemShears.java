package net.minecraft.item;

import net.minecraft.creativetab.*;
import net.minecraft.world.*;
import net.minecraft.block.*;
import net.minecraft.entity.*;
import net.minecraft.block.material.*;
import net.minecraft.init.*;

public class ItemShears extends Item
{
    private static final String __OBFID = "CL_00000062";
    
    public ItemShears() {
        this.setMaxStackSize(1);
        this.setMaxDamage(238);
        this.setCreativeTab(CreativeTabs.tabTools);
    }
    
    @Override
    public boolean onBlockDestroyed(final ItemStack p_150894_1_, final World p_150894_2_, final Block p_150894_3_, final int p_150894_4_, final int p_150894_5_, final int p_150894_6_, final EntityLivingBase p_150894_7_) {
        if (p_150894_3_.getMaterial() != Material.leaves && p_150894_3_ != Blocks.web && p_150894_3_ != Blocks.tallgrass && p_150894_3_ != Blocks.vine && p_150894_3_ != Blocks.tripwire) {
            return super.onBlockDestroyed(p_150894_1_, p_150894_2_, p_150894_3_, p_150894_4_, p_150894_5_, p_150894_6_, p_150894_7_);
        }
        p_150894_1_.damageItem(1, p_150894_7_);
        return true;
    }
    
    @Override
    public boolean func_150897_b(final Block p_150897_1_) {
        return p_150897_1_ == Blocks.web || p_150897_1_ == Blocks.redstone_wire || p_150897_1_ == Blocks.tripwire;
    }
    
    @Override
    public float func_150893_a(final ItemStack p_150893_1_, final Block p_150893_2_) {
        return (p_150893_2_ != Blocks.web && p_150893_2_.getMaterial() != Material.leaves) ? ((p_150893_2_ == Blocks.wool) ? 5.0f : super.func_150893_a(p_150893_1_, p_150893_2_)) : 15.0f;
    }
}
