package net.minecraft.item;

import net.minecraft.creativetab.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.block.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import java.util.*;

public class ItemLead extends Item
{
    private static final String __OBFID = "CL_00000045";
    
    public ItemLead() {
        this.setCreativeTab(CreativeTabs.tabTools);
    }
    
    @Override
    public boolean onItemUse(final ItemStack p_77648_1_, final EntityPlayer p_77648_2_, final World p_77648_3_, final int p_77648_4_, final int p_77648_5_, final int p_77648_6_, final int p_77648_7_, final float p_77648_8_, final float p_77648_9_, final float p_77648_10_) {
        final Block var11 = p_77648_3_.getBlock(p_77648_4_, p_77648_5_, p_77648_6_);
        if (var11.getRenderType() != 11) {
            return false;
        }
        if (p_77648_3_.isClient) {
            return true;
        }
        func_150909_a(p_77648_2_, p_77648_3_, p_77648_4_, p_77648_5_, p_77648_6_);
        return true;
    }
    
    public static boolean func_150909_a(final EntityPlayer p_150909_0_, final World p_150909_1_, final int p_150909_2_, final int p_150909_3_, final int p_150909_4_) {
        EntityLeashKnot var5 = EntityLeashKnot.getKnotForBlock(p_150909_1_, p_150909_2_, p_150909_3_, p_150909_4_);
        boolean var6 = false;
        final double var7 = 7.0;
        final List var8 = p_150909_1_.getEntitiesWithinAABB(EntityLiving.class, AxisAlignedBB.getBoundingBox(p_150909_2_ - var7, p_150909_3_ - var7, p_150909_4_ - var7, p_150909_2_ + var7, p_150909_3_ + var7, p_150909_4_ + var7));
        if (var8 != null) {
            for (final EntityLiving var10 : var8) {
                if (var10.getLeashed() && var10.getLeashedToEntity() == p_150909_0_) {
                    if (var5 == null) {
                        var5 = EntityLeashKnot.func_110129_a(p_150909_1_, p_150909_2_, p_150909_3_, p_150909_4_);
                    }
                    var10.setLeashedToEntity(var5, true);
                    var6 = true;
                }
            }
        }
        return var6;
    }
}
