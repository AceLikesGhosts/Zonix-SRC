package net.minecraft.item;

import net.minecraft.creativetab.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;

public class ItemNameTag extends Item
{
    private static final String __OBFID = "CL_00000052";
    
    public ItemNameTag() {
        this.setCreativeTab(CreativeTabs.tabTools);
    }
    
    @Override
    public boolean itemInteractionForEntity(final ItemStack p_111207_1_, final EntityPlayer p_111207_2_, final EntityLivingBase p_111207_3_) {
        if (!p_111207_1_.hasDisplayName()) {
            return false;
        }
        if (p_111207_3_ instanceof EntityLiving) {
            final EntityLiving var4 = (EntityLiving)p_111207_3_;
            var4.setCustomNameTag(p_111207_1_.getDisplayName());
            var4.func_110163_bv();
            --p_111207_1_.stackSize;
            return true;
        }
        return super.itemInteractionForEntity(p_111207_1_, p_111207_2_, p_111207_3_);
    }
}
