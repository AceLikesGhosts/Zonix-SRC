package net.minecraft.item.crafting;

import net.minecraft.item.*;
import net.minecraft.inventory.*;
import net.minecraft.world.*;
import java.util.*;

public class ShapelessRecipes implements IRecipe
{
    private final ItemStack recipeOutput;
    private final List recipeItems;
    private static final String __OBFID = "CL_00000094";
    
    public ShapelessRecipes(final ItemStack p_i1918_1_, final List p_i1918_2_) {
        this.recipeOutput = p_i1918_1_;
        this.recipeItems = p_i1918_2_;
    }
    
    @Override
    public ItemStack getRecipeOutput() {
        return this.recipeOutput;
    }
    
    @Override
    public boolean matches(final InventoryCrafting p_77569_1_, final World p_77569_2_) {
        final ArrayList var3 = new ArrayList(this.recipeItems);
        for (int var4 = 0; var4 < 3; ++var4) {
            for (int var5 = 0; var5 < 3; ++var5) {
                final ItemStack var6 = p_77569_1_.getStackInRowAndColumn(var5, var4);
                if (var6 != null) {
                    boolean var7 = false;
                    for (final ItemStack var9 : var3) {
                        if (var6.getItem() == var9.getItem() && (var9.getItemDamage() == 32767 || var6.getItemDamage() == var9.getItemDamage())) {
                            var7 = true;
                            var3.remove(var9);
                            break;
                        }
                    }
                    if (!var7) {
                        return false;
                    }
                }
            }
        }
        return var3.isEmpty();
    }
    
    @Override
    public ItemStack getCraftingResult(final InventoryCrafting p_77572_1_) {
        return this.recipeOutput.copy();
    }
    
    @Override
    public int getRecipeSize() {
        return this.recipeItems.size();
    }
}
