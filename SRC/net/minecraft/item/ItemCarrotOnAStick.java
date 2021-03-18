package net.minecraft.item;

import net.minecraft.creativetab.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.*;
import net.minecraft.init.*;

public class ItemCarrotOnAStick extends Item
{
    private static final String __OBFID = "CL_00000001";
    
    public ItemCarrotOnAStick() {
        this.setCreativeTab(CreativeTabs.tabTransport);
        this.setMaxStackSize(1);
        this.setMaxDamage(25);
    }
    
    @Override
    public boolean isFull3D() {
        return true;
    }
    
    @Override
    public boolean shouldRotateAroundWhenRendering() {
        return true;
    }
    
    @Override
    public ItemStack onItemRightClick(final ItemStack p_77659_1_, final World p_77659_2_, final EntityPlayer p_77659_3_) {
        if (p_77659_3_.isRiding() && p_77659_3_.ridingEntity instanceof EntityPig) {
            final EntityPig var4 = (EntityPig)p_77659_3_.ridingEntity;
            if (var4.getAIControlledByPlayer().isControlledByPlayer() && p_77659_1_.getMaxDamage() - p_77659_1_.getItemDamage() >= 7) {
                var4.getAIControlledByPlayer().boostSpeed();
                p_77659_1_.damageItem(7, p_77659_3_);
                if (p_77659_1_.stackSize == 0) {
                    final ItemStack var5 = new ItemStack(Items.fishing_rod);
                    var5.setTagCompound(p_77659_1_.stackTagCompound);
                    return var5;
                }
            }
        }
        return p_77659_1_;
    }
}
