package net.minecraft.util;

import net.minecraft.item.*;
import java.util.*;
import net.minecraft.inventory.*;
import net.minecraft.tileentity.*;

public class WeightedRandomChestContent extends WeightedRandom.Item
{
    private ItemStack theItemId;
    private int theMinimumChanceToGenerateItem;
    private int theMaximumChanceToGenerateItem;
    private static final String __OBFID = "CL_00001505";
    
    public WeightedRandomChestContent(final net.minecraft.item.Item p_i45311_1_, final int p_i45311_2_, final int p_i45311_3_, final int p_i45311_4_, final int p_i45311_5_) {
        super(p_i45311_5_);
        this.theItemId = new ItemStack(p_i45311_1_, 1, p_i45311_2_);
        this.theMinimumChanceToGenerateItem = p_i45311_3_;
        this.theMaximumChanceToGenerateItem = p_i45311_4_;
    }
    
    public WeightedRandomChestContent(final ItemStack p_i1558_1_, final int p_i1558_2_, final int p_i1558_3_, final int p_i1558_4_) {
        super(p_i1558_4_);
        this.theItemId = p_i1558_1_;
        this.theMinimumChanceToGenerateItem = p_i1558_2_;
        this.theMaximumChanceToGenerateItem = p_i1558_3_;
    }
    
    public static void generateChestContents(final Random p_76293_0_, final WeightedRandomChestContent[] p_76293_1_, final IInventory p_76293_2_, final int p_76293_3_) {
        for (int var4 = 0; var4 < p_76293_3_; ++var4) {
            final WeightedRandomChestContent var5 = (WeightedRandomChestContent)WeightedRandom.getRandomItem(p_76293_0_, p_76293_1_);
            final int var6 = var5.theMinimumChanceToGenerateItem + p_76293_0_.nextInt(var5.theMaximumChanceToGenerateItem - var5.theMinimumChanceToGenerateItem + 1);
            if (var5.theItemId.getMaxStackSize() >= var6) {
                final ItemStack var7 = var5.theItemId.copy();
                var7.stackSize = var6;
                p_76293_2_.setInventorySlotContents(p_76293_0_.nextInt(p_76293_2_.getSizeInventory()), var7);
            }
            else {
                for (int var8 = 0; var8 < var6; ++var8) {
                    final ItemStack var9 = var5.theItemId.copy();
                    var9.stackSize = 1;
                    p_76293_2_.setInventorySlotContents(p_76293_0_.nextInt(p_76293_2_.getSizeInventory()), var9);
                }
            }
        }
    }
    
    public static void func_150706_a(final Random p_150706_0_, final WeightedRandomChestContent[] p_150706_1_, final TileEntityDispenser p_150706_2_, final int p_150706_3_) {
        for (int var4 = 0; var4 < p_150706_3_; ++var4) {
            final WeightedRandomChestContent var5 = (WeightedRandomChestContent)WeightedRandom.getRandomItem(p_150706_0_, p_150706_1_);
            final int var6 = var5.theMinimumChanceToGenerateItem + p_150706_0_.nextInt(var5.theMaximumChanceToGenerateItem - var5.theMinimumChanceToGenerateItem + 1);
            if (var5.theItemId.getMaxStackSize() >= var6) {
                final ItemStack var7 = var5.theItemId.copy();
                var7.stackSize = var6;
                p_150706_2_.setInventorySlotContents(p_150706_0_.nextInt(p_150706_2_.getSizeInventory()), var7);
            }
            else {
                for (int var8 = 0; var8 < var6; ++var8) {
                    final ItemStack var9 = var5.theItemId.copy();
                    var9.stackSize = 1;
                    p_150706_2_.setInventorySlotContents(p_150706_0_.nextInt(p_150706_2_.getSizeInventory()), var9);
                }
            }
        }
    }
    
    public static WeightedRandomChestContent[] func_92080_a(final WeightedRandomChestContent[] p_92080_0_, final WeightedRandomChestContent... p_92080_1_) {
        final WeightedRandomChestContent[] var2 = new WeightedRandomChestContent[p_92080_0_.length + p_92080_1_.length];
        int var3 = 0;
        for (int var4 = 0; var4 < p_92080_0_.length; ++var4) {
            var2[var3++] = p_92080_0_[var4];
        }
        final WeightedRandomChestContent[] var5 = p_92080_1_;
        for (int var6 = p_92080_1_.length, var7 = 0; var7 < var6; ++var7) {
            final WeightedRandomChestContent var8 = var5[var7];
            var2[var3++] = var8;
        }
        return var2;
    }
}
