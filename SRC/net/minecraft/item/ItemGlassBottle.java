package net.minecraft.item;

import net.minecraft.creativetab.*;
import net.minecraft.init.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import net.minecraft.block.material.*;
import net.minecraft.client.renderer.texture.*;

public class ItemGlassBottle extends Item
{
    private static final String __OBFID = "CL_00001776";
    
    public ItemGlassBottle() {
        this.setCreativeTab(CreativeTabs.tabBrewing);
    }
    
    @Override
    public IIcon getIconFromDamage(final int p_77617_1_) {
        return Items.potionitem.getIconFromDamage(0);
    }
    
    @Override
    public ItemStack onItemRightClick(final ItemStack p_77659_1_, final World p_77659_2_, final EntityPlayer p_77659_3_) {
        final MovingObjectPosition var4 = this.getMovingObjectPositionFromPlayer(p_77659_2_, p_77659_3_, true);
        if (var4 == null) {
            return p_77659_1_;
        }
        if (var4.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
            final int var5 = var4.blockX;
            final int var6 = var4.blockY;
            final int var7 = var4.blockZ;
            if (!p_77659_2_.canMineBlock(p_77659_3_, var5, var6, var7)) {
                return p_77659_1_;
            }
            if (!p_77659_3_.canPlayerEdit(var5, var6, var7, var4.sideHit, p_77659_1_)) {
                return p_77659_1_;
            }
            if (p_77659_2_.getBlock(var5, var6, var7).getMaterial() == Material.water) {
                --p_77659_1_.stackSize;
                if (p_77659_1_.stackSize <= 0) {
                    return new ItemStack(Items.potionitem);
                }
                if (!p_77659_3_.inventory.addItemStackToInventory(new ItemStack(Items.potionitem))) {
                    p_77659_3_.dropPlayerItemWithRandomChoice(new ItemStack(Items.potionitem, 1, 0), false);
                }
            }
        }
        return p_77659_1_;
    }
    
    @Override
    public void registerIcons(final IIconRegister p_94581_1_) {
    }
}
