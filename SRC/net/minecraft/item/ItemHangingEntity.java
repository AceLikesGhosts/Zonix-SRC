package net.minecraft.item;

import net.minecraft.creativetab.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import net.minecraft.entity.item.*;

public class ItemHangingEntity extends Item
{
    private final Class hangingEntityClass;
    private static final String __OBFID = "CL_00000038";
    
    public ItemHangingEntity(final Class p_i45342_1_) {
        this.hangingEntityClass = p_i45342_1_;
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }
    
    @Override
    public boolean onItemUse(final ItemStack p_77648_1_, final EntityPlayer p_77648_2_, final World p_77648_3_, final int p_77648_4_, final int p_77648_5_, final int p_77648_6_, final int p_77648_7_, final float p_77648_8_, final float p_77648_9_, final float p_77648_10_) {
        if (p_77648_7_ == 0) {
            return false;
        }
        if (p_77648_7_ == 1) {
            return false;
        }
        final int var11 = Direction.facingToDirection[p_77648_7_];
        final EntityHanging var12 = this.createHangingEntity(p_77648_3_, p_77648_4_, p_77648_5_, p_77648_6_, var11);
        if (!p_77648_2_.canPlayerEdit(p_77648_4_, p_77648_5_, p_77648_6_, p_77648_7_, p_77648_1_)) {
            return false;
        }
        if (var12 != null && var12.onValidSurface()) {
            if (!p_77648_3_.isClient) {
                p_77648_3_.spawnEntityInWorld(var12);
            }
            --p_77648_1_.stackSize;
        }
        return true;
    }
    
    private EntityHanging createHangingEntity(final World p_82810_1_, final int p_82810_2_, final int p_82810_3_, final int p_82810_4_, final int p_82810_5_) {
        return (this.hangingEntityClass == EntityPainting.class) ? new EntityPainting(p_82810_1_, p_82810_2_, p_82810_3_, p_82810_4_, p_82810_5_) : ((this.hangingEntityClass == EntityItemFrame.class) ? new EntityItemFrame(p_82810_1_, p_82810_2_, p_82810_3_, p_82810_4_, p_82810_5_) : null);
    }
}
