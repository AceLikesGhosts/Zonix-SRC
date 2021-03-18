package net.minecraft.item;

import net.minecraft.creativetab.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.*;

public class ItemSaddle extends Item
{
    private static final String __OBFID = "CL_00000059";
    
    public ItemSaddle() {
        this.maxStackSize = 1;
        this.setCreativeTab(CreativeTabs.tabTransport);
    }
    
    @Override
    public boolean itemInteractionForEntity(final ItemStack p_111207_1_, final EntityPlayer p_111207_2_, final EntityLivingBase p_111207_3_) {
        if (p_111207_3_ instanceof EntityPig) {
            final EntityPig var4 = (EntityPig)p_111207_3_;
            if (!var4.getSaddled() && !var4.isChild()) {
                var4.setSaddled(true);
                var4.worldObj.playSoundAtEntity(var4, "mob.horse.leather", 0.5f, 1.0f);
                --p_111207_1_.stackSize;
            }
            return true;
        }
        return false;
    }
    
    @Override
    public boolean hitEntity(final ItemStack p_77644_1_, final EntityLivingBase p_77644_2_, final EntityLivingBase p_77644_3_) {
        this.itemInteractionForEntity(p_77644_1_, null, p_77644_2_);
        return true;
    }
}
