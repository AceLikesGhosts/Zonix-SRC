package net.minecraft.item;

import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.network.*;

public class ItemMapBase extends Item
{
    private static final String __OBFID = "CL_00000004";
    
    @Override
    public boolean isMap() {
        return true;
    }
    
    public Packet func_150911_c(final ItemStack p_150911_1_, final World p_150911_2_, final EntityPlayer p_150911_3_) {
        return null;
    }
}
