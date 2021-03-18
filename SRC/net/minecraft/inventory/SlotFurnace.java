package net.minecraft.inventory;

import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.item.crafting.*;
import net.minecraft.util.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.*;
import net.minecraft.init.*;
import net.minecraft.stats.*;

public class SlotFurnace extends Slot
{
    private EntityPlayer thePlayer;
    private int field_75228_b;
    private static final String __OBFID = "CL_00001749";
    
    public SlotFurnace(final EntityPlayer p_i1813_1_, final IInventory p_i1813_2_, final int p_i1813_3_, final int p_i1813_4_, final int p_i1813_5_) {
        super(p_i1813_2_, p_i1813_3_, p_i1813_4_, p_i1813_5_);
        this.thePlayer = p_i1813_1_;
    }
    
    @Override
    public boolean isItemValid(final ItemStack p_75214_1_) {
        return false;
    }
    
    @Override
    public ItemStack decrStackSize(final int p_75209_1_) {
        if (this.getHasStack()) {
            this.field_75228_b += Math.min(p_75209_1_, this.getStack().stackSize);
        }
        return super.decrStackSize(p_75209_1_);
    }
    
    @Override
    public void onPickupFromSlot(final EntityPlayer p_82870_1_, final ItemStack p_82870_2_) {
        this.onCrafting(p_82870_2_);
        super.onPickupFromSlot(p_82870_1_, p_82870_2_);
    }
    
    @Override
    protected void onCrafting(final ItemStack p_75210_1_, final int p_75210_2_) {
        this.field_75228_b += p_75210_2_;
        this.onCrafting(p_75210_1_);
    }
    
    @Override
    protected void onCrafting(final ItemStack p_75208_1_) {
        p_75208_1_.onCrafting(this.thePlayer.worldObj, this.thePlayer, this.field_75228_b);
        if (!this.thePlayer.worldObj.isClient) {
            int var2 = this.field_75228_b;
            final float var3 = FurnaceRecipes.smelting().func_151398_b(p_75208_1_);
            if (var3 == 0.0f) {
                var2 = 0;
            }
            else if (var3 < 1.0f) {
                int var4 = MathHelper.floor_float(var2 * var3);
                if (var4 < MathHelper.ceiling_float_int(var2 * var3) && (float)Math.random() < var2 * var3 - var4) {
                    ++var4;
                }
                var2 = var4;
            }
            while (var2 > 0) {
                final int var4 = EntityXPOrb.getXPSplit(var2);
                var2 -= var4;
                this.thePlayer.worldObj.spawnEntityInWorld(new EntityXPOrb(this.thePlayer.worldObj, this.thePlayer.posX, this.thePlayer.posY + 0.5, this.thePlayer.posZ + 0.5, var4));
            }
        }
        this.field_75228_b = 0;
        if (p_75208_1_.getItem() == Items.iron_ingot) {
            this.thePlayer.addStat(AchievementList.acquireIron, 1);
        }
        if (p_75208_1_.getItem() == Items.cooked_fished) {
            this.thePlayer.addStat(AchievementList.cookFish, 1);
        }
    }
}
