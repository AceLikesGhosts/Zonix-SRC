package net.minecraft.item;

import net.minecraft.creativetab.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.block.material.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import net.minecraft.entity.*;

public class ItemFlintAndSteel extends Item
{
    private static final String __OBFID = "CL_00000035";
    
    public ItemFlintAndSteel() {
        this.maxStackSize = 1;
        this.setMaxDamage(64);
        this.setCreativeTab(CreativeTabs.tabTools);
    }
    
    @Override
    public boolean onItemUse(final ItemStack p_77648_1_, final EntityPlayer p_77648_2_, final World p_77648_3_, int p_77648_4_, int p_77648_5_, int p_77648_6_, final int p_77648_7_, final float p_77648_8_, final float p_77648_9_, final float p_77648_10_) {
        if (p_77648_7_ == 0) {
            --p_77648_5_;
        }
        if (p_77648_7_ == 1) {
            ++p_77648_5_;
        }
        if (p_77648_7_ == 2) {
            --p_77648_6_;
        }
        if (p_77648_7_ == 3) {
            ++p_77648_6_;
        }
        if (p_77648_7_ == 4) {
            --p_77648_4_;
        }
        if (p_77648_7_ == 5) {
            ++p_77648_4_;
        }
        if (!p_77648_2_.canPlayerEdit(p_77648_4_, p_77648_5_, p_77648_6_, p_77648_7_, p_77648_1_)) {
            return false;
        }
        if (p_77648_3_.getBlock(p_77648_4_, p_77648_5_, p_77648_6_).getMaterial() == Material.air) {
            p_77648_3_.playSoundEffect(p_77648_4_ + 0.5, p_77648_5_ + 0.5, p_77648_6_ + 0.5, "fire.ignite", 1.0f, ItemFlintAndSteel.itemRand.nextFloat() * 0.4f + 0.8f);
            p_77648_3_.setBlock(p_77648_4_, p_77648_5_, p_77648_6_, Blocks.fire);
        }
        p_77648_1_.damageItem(1, p_77648_2_);
        return true;
    }
}
