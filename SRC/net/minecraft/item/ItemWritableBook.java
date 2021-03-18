package net.minecraft.item;

import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.nbt.*;

public class ItemWritableBook extends Item
{
    private static final String __OBFID = "CL_00000076";
    
    public ItemWritableBook() {
        this.setMaxStackSize(1);
    }
    
    @Override
    public ItemStack onItemRightClick(final ItemStack p_77659_1_, final World p_77659_2_, final EntityPlayer p_77659_3_) {
        p_77659_3_.displayGUIBook(p_77659_1_);
        return p_77659_1_;
    }
    
    @Override
    public boolean getShareTag() {
        return true;
    }
    
    public static boolean func_150930_a(final NBTTagCompound p_150930_0_) {
        if (p_150930_0_ == null) {
            return false;
        }
        if (!p_150930_0_.func_150297_b("pages", 9)) {
            return false;
        }
        final NBTTagList var1 = p_150930_0_.getTagList("pages", 8);
        for (int var2 = 0; var2 < var1.tagCount(); ++var2) {
            final String var3 = var1.getStringTagAt(var2);
            if (var3 == null) {
                return false;
            }
            if (var3.length() > 256) {
                return false;
            }
        }
        return true;
    }
}
