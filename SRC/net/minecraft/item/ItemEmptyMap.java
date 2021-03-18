package net.minecraft.item;

import net.minecraft.creativetab.*;
import net.minecraft.entity.player.*;
import net.minecraft.init.*;
import net.minecraft.world.storage.*;
import net.minecraft.world.*;

public class ItemEmptyMap extends ItemMapBase
{
    private static final String __OBFID = "CL_00000024";
    
    protected ItemEmptyMap() {
        this.setCreativeTab(CreativeTabs.tabMisc);
    }
    
    @Override
    public ItemStack onItemRightClick(final ItemStack p_77659_1_, final World p_77659_2_, final EntityPlayer p_77659_3_) {
        final ItemStack var4 = new ItemStack(Items.filled_map, 1, p_77659_2_.getUniqueDataId("map"));
        final String var5 = "map_" + var4.getItemDamage();
        final MapData var6 = new MapData(var5);
        p_77659_2_.setItemData(var5, var6);
        var6.scale = 0;
        final int var7 = 128 * (1 << var6.scale);
        var6.xCenter = (int)(Math.round(p_77659_3_.posX / var7) * var7);
        var6.zCenter = (int)(Math.round(p_77659_3_.posZ / var7) * var7);
        var6.dimension = (byte)p_77659_2_.provider.dimensionId;
        var6.markDirty();
        --p_77659_1_.stackSize;
        if (p_77659_1_.stackSize <= 0) {
            return var4;
        }
        if (!p_77659_3_.inventory.addItemStackToInventory(var4.copy())) {
            p_77659_3_.dropPlayerItemWithRandomChoice(var4, false);
        }
        return p_77659_1_;
    }
}
