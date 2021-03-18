package net.minecraft.entity;

import net.minecraft.inventory.*;
import net.minecraft.entity.player.*;
import net.minecraft.village.*;
import net.minecraft.item.*;

public class NpcMerchant implements IMerchant
{
    private InventoryMerchant theMerchantInventory;
    private EntityPlayer customer;
    private MerchantRecipeList recipeList;
    private static final String __OBFID = "CL_00001705";
    
    public NpcMerchant(final EntityPlayer p_i1746_1_) {
        this.customer = p_i1746_1_;
        this.theMerchantInventory = new InventoryMerchant(p_i1746_1_, this);
    }
    
    @Override
    public EntityPlayer getCustomer() {
        return this.customer;
    }
    
    @Override
    public void setCustomer(final EntityPlayer p_70932_1_) {
    }
    
    @Override
    public MerchantRecipeList getRecipes(final EntityPlayer p_70934_1_) {
        return this.recipeList;
    }
    
    @Override
    public void setRecipes(final MerchantRecipeList p_70930_1_) {
        this.recipeList = p_70930_1_;
    }
    
    @Override
    public void useRecipe(final MerchantRecipe p_70933_1_) {
    }
    
    @Override
    public void func_110297_a_(final ItemStack p_110297_1_) {
    }
}
