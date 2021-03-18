package net.minecraft.item;

import net.minecraft.util.*;
import net.minecraft.creativetab.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.client.renderer.texture.*;

public class ItemFishingRod extends Item
{
    private IIcon theIcon;
    private static final String __OBFID = "CL_00000034";
    
    public ItemFishingRod() {
        this.setMaxDamage(64);
        this.setMaxStackSize(1);
        this.setCreativeTab(CreativeTabs.tabTools);
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
        if (p_77659_3_.fishEntity != null) {
            final int var4 = p_77659_3_.fishEntity.func_146034_e();
            p_77659_1_.damageItem(var4, p_77659_3_);
            p_77659_3_.swingItem();
        }
        else {
            p_77659_2_.playSoundAtEntity(p_77659_3_, "random.bow", 0.5f, 0.4f / (ItemFishingRod.itemRand.nextFloat() * 0.4f + 0.8f));
            if (!p_77659_2_.isClient) {
                p_77659_2_.spawnEntityInWorld(new EntityFishHook(p_77659_2_, p_77659_3_));
            }
            p_77659_3_.swingItem();
        }
        return p_77659_1_;
    }
    
    @Override
    public void registerIcons(final IIconRegister p_94581_1_) {
        this.itemIcon = p_94581_1_.registerIcon(this.getIconString() + "_uncast");
        this.theIcon = p_94581_1_.registerIcon(this.getIconString() + "_cast");
    }
    
    public IIcon func_94597_g() {
        return this.theIcon;
    }
    
    @Override
    public boolean isItemTool(final ItemStack p_77616_1_) {
        return super.isItemTool(p_77616_1_);
    }
    
    @Override
    public int getItemEnchantability() {
        return 1;
    }
}
