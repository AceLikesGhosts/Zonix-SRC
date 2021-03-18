package net.minecraft.item;

import net.minecraft.nbt.*;
import net.minecraft.entity.player.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.world.*;

public class ItemEditableBook extends Item
{
    private static final String __OBFID = "CL_00000077";
    
    public ItemEditableBook() {
        this.setMaxStackSize(1);
    }
    
    public static boolean validBookTagContents(final NBTTagCompound p_77828_0_) {
        if (!ItemWritableBook.func_150930_a(p_77828_0_)) {
            return false;
        }
        if (!p_77828_0_.func_150297_b("title", 8)) {
            return false;
        }
        final String var1 = p_77828_0_.getString("title");
        return var1 != null && var1.length() <= 16 && p_77828_0_.func_150297_b("author", 8);
    }
    
    @Override
    public String getItemStackDisplayName(final ItemStack p_77653_1_) {
        if (p_77653_1_.hasTagCompound()) {
            final NBTTagCompound var2 = p_77653_1_.getTagCompound();
            final String var3 = var2.getString("title");
            if (!StringUtils.isNullOrEmpty(var3)) {
                return var3;
            }
        }
        return super.getItemStackDisplayName(p_77653_1_);
    }
    
    @Override
    public void addInformation(final ItemStack p_77624_1_, final EntityPlayer p_77624_2_, final List p_77624_3_, final boolean p_77624_4_) {
        if (p_77624_1_.hasTagCompound()) {
            final NBTTagCompound var5 = p_77624_1_.getTagCompound();
            final String var6 = var5.getString("author");
            if (!StringUtils.isNullOrEmpty(var6)) {
                p_77624_3_.add(EnumChatFormatting.GRAY + StatCollector.translateToLocalFormatted("book.byAuthor", var6));
            }
        }
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
    
    @Override
    public boolean hasEffect(final ItemStack p_77636_1_) {
        return true;
    }
}
