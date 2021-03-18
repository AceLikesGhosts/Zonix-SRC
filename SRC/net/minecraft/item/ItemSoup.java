package net.minecraft.item;

import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.init.*;

public class ItemSoup extends ItemFood
{
    private static final String __OBFID = "CL_00001778";
    
    public ItemSoup(final int p_i45330_1_) {
        super(p_i45330_1_, false);
        this.setMaxStackSize(1);
    }
    
    @Override
    public ItemStack onEaten(final ItemStack p_77654_1_, final World p_77654_2_, final EntityPlayer p_77654_3_) {
        super.onEaten(p_77654_1_, p_77654_2_, p_77654_3_);
        return new ItemStack(Items.bowl);
    }
}
