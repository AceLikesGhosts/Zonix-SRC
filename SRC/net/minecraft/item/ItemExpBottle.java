package net.minecraft.item;

import net.minecraft.creativetab.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.*;

public class ItemExpBottle extends Item
{
    private static final String __OBFID = "CL_00000028";
    
    public ItemExpBottle() {
        this.setCreativeTab(CreativeTabs.tabMisc);
    }
    
    @Override
    public boolean hasEffect(final ItemStack p_77636_1_) {
        return true;
    }
    
    @Override
    public ItemStack onItemRightClick(final ItemStack p_77659_1_, final World p_77659_2_, final EntityPlayer p_77659_3_) {
        if (!p_77659_3_.capabilities.isCreativeMode) {
            --p_77659_1_.stackSize;
        }
        p_77659_2_.playSoundAtEntity(p_77659_3_, "random.bow", 0.5f, 0.4f / (ItemExpBottle.itemRand.nextFloat() * 0.4f + 0.8f));
        if (!p_77659_2_.isClient) {
            p_77659_2_.spawnEntityInWorld(new EntityExpBottle(p_77659_2_, p_77659_3_));
        }
        return p_77659_1_;
    }
}
