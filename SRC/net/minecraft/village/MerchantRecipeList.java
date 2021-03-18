package net.minecraft.village;

import java.util.*;
import net.minecraft.item.*;
import net.minecraft.network.*;
import java.io.*;
import net.minecraft.nbt.*;

public class MerchantRecipeList extends ArrayList
{
    private static final String __OBFID = "CL_00000127";
    
    public MerchantRecipeList() {
    }
    
    public MerchantRecipeList(final NBTTagCompound p_i1944_1_) {
        this.readRecipiesFromTags(p_i1944_1_);
    }
    
    public MerchantRecipe canRecipeBeUsed(final ItemStack p_77203_1_, final ItemStack p_77203_2_, final int p_77203_3_) {
        if (p_77203_3_ > 0 && p_77203_3_ < this.size()) {
            final MerchantRecipe var6 = this.get(p_77203_3_);
            return (p_77203_1_.getItem() == var6.getItemToBuy().getItem() && ((p_77203_2_ == null && !var6.hasSecondItemToBuy()) || (var6.hasSecondItemToBuy() && p_77203_2_ != null && var6.getSecondItemToBuy().getItem() == p_77203_2_.getItem())) && p_77203_1_.stackSize >= var6.getItemToBuy().stackSize && (!var6.hasSecondItemToBuy() || p_77203_2_.stackSize >= var6.getSecondItemToBuy().stackSize)) ? var6 : null;
        }
        for (int var7 = 0; var7 < this.size(); ++var7) {
            final MerchantRecipe var8 = this.get(var7);
            if (p_77203_1_.getItem() == var8.getItemToBuy().getItem() && p_77203_1_.stackSize >= var8.getItemToBuy().stackSize && ((!var8.hasSecondItemToBuy() && p_77203_2_ == null) || (var8.hasSecondItemToBuy() && p_77203_2_ != null && var8.getSecondItemToBuy().getItem() == p_77203_2_.getItem() && p_77203_2_.stackSize >= var8.getSecondItemToBuy().stackSize))) {
                return var8;
            }
        }
        return null;
    }
    
    public void addToListWithCheck(final MerchantRecipe p_77205_1_) {
        for (int var2 = 0; var2 < this.size(); ++var2) {
            final MerchantRecipe var3 = this.get(var2);
            if (p_77205_1_.hasSameIDsAs(var3)) {
                if (p_77205_1_.hasSameItemsAs(var3)) {
                    this.set(var2, p_77205_1_);
                }
                return;
            }
        }
        this.add(p_77205_1_);
    }
    
    public void func_151391_a(final PacketBuffer p_151391_1_) throws IOException {
        p_151391_1_.writeByte((byte)(this.size() & 0xFF));
        for (int var2 = 0; var2 < this.size(); ++var2) {
            final MerchantRecipe var3 = this.get(var2);
            p_151391_1_.writeItemStackToBuffer(var3.getItemToBuy());
            p_151391_1_.writeItemStackToBuffer(var3.getItemToSell());
            final ItemStack var4 = var3.getSecondItemToBuy();
            p_151391_1_.writeBoolean(var4 != null);
            if (var4 != null) {
                p_151391_1_.writeItemStackToBuffer(var4);
            }
            p_151391_1_.writeBoolean(var3.isRecipeDisabled());
        }
    }
    
    public static MerchantRecipeList func_151390_b(final PacketBuffer p_151390_0_) throws IOException {
        final MerchantRecipeList var1 = new MerchantRecipeList();
        for (int var2 = p_151390_0_.readByte() & 0xFF, var3 = 0; var3 < var2; ++var3) {
            final ItemStack var4 = p_151390_0_.readItemStackFromBuffer();
            final ItemStack var5 = p_151390_0_.readItemStackFromBuffer();
            ItemStack var6 = null;
            if (p_151390_0_.readBoolean()) {
                var6 = p_151390_0_.readItemStackFromBuffer();
            }
            final boolean var7 = p_151390_0_.readBoolean();
            final MerchantRecipe var8 = new MerchantRecipe(var4, var6, var5);
            if (var7) {
                var8.func_82785_h();
            }
            var1.add(var8);
        }
        return var1;
    }
    
    public void readRecipiesFromTags(final NBTTagCompound p_77201_1_) {
        final NBTTagList var2 = p_77201_1_.getTagList("Recipes", 10);
        for (int var3 = 0; var3 < var2.tagCount(); ++var3) {
            final NBTTagCompound var4 = var2.getCompoundTagAt(var3);
            this.add(new MerchantRecipe(var4));
        }
    }
    
    public NBTTagCompound getRecipiesAsTags() {
        final NBTTagCompound var1 = new NBTTagCompound();
        final NBTTagList var2 = new NBTTagList();
        for (int var3 = 0; var3 < this.size(); ++var3) {
            final MerchantRecipe var4 = this.get(var3);
            var2.appendTag(var4.writeToTags());
        }
        var1.setTag("Recipes", var2);
        return var1;
    }
}
