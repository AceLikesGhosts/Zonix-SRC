package net.minecraft.item;

import net.minecraft.creativetab.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.block.material.*;
import net.minecraft.init.*;
import net.minecraft.entity.*;
import net.minecraft.block.*;

public class ItemHoe extends Item
{
    protected ToolMaterial theToolMaterial;
    private static final String __OBFID = "CL_00000039";
    
    public ItemHoe(final ToolMaterial p_i45343_1_) {
        this.theToolMaterial = p_i45343_1_;
        this.maxStackSize = 1;
        this.setMaxDamage(p_i45343_1_.getMaxUses());
        this.setCreativeTab(CreativeTabs.tabTools);
    }
    
    @Override
    public boolean onItemUse(final ItemStack p_77648_1_, final EntityPlayer p_77648_2_, final World p_77648_3_, final int p_77648_4_, final int p_77648_5_, final int p_77648_6_, final int p_77648_7_, final float p_77648_8_, final float p_77648_9_, final float p_77648_10_) {
        if (!p_77648_2_.canPlayerEdit(p_77648_4_, p_77648_5_, p_77648_6_, p_77648_7_, p_77648_1_)) {
            return false;
        }
        final Block var11 = p_77648_3_.getBlock(p_77648_4_, p_77648_5_, p_77648_6_);
        if (p_77648_7_ == 0 || p_77648_3_.getBlock(p_77648_4_, p_77648_5_ + 1, p_77648_6_).getMaterial() != Material.air || (var11 != Blocks.grass && var11 != Blocks.dirt)) {
            return false;
        }
        final Block var12 = Blocks.farmland;
        p_77648_3_.playSoundEffect(p_77648_4_ + 0.5f, p_77648_5_ + 0.5f, p_77648_6_ + 0.5f, var12.stepSound.func_150498_e(), (var12.stepSound.func_150497_c() + 1.0f) / 2.0f, var12.stepSound.func_150494_d() * 0.8f);
        if (p_77648_3_.isClient) {
            return true;
        }
        p_77648_3_.setBlock(p_77648_4_, p_77648_5_, p_77648_6_, var12);
        p_77648_1_.damageItem(1, p_77648_2_);
        return true;
    }
    
    @Override
    public boolean isFull3D() {
        return true;
    }
    
    public String getMaterialName() {
        return this.theToolMaterial.toString();
    }
}
