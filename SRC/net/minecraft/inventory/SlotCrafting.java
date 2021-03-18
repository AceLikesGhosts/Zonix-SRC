package net.minecraft.inventory;

import net.minecraft.entity.player.*;
import net.minecraft.stats.*;
import net.minecraft.init.*;
import net.minecraft.item.*;

public class SlotCrafting extends Slot
{
    private final IInventory craftMatrix;
    private EntityPlayer thePlayer;
    private int amountCrafted;
    private static final String __OBFID = "CL_00001761";
    
    public SlotCrafting(final EntityPlayer p_i1823_1_, final IInventory p_i1823_2_, final IInventory p_i1823_3_, final int p_i1823_4_, final int p_i1823_5_, final int p_i1823_6_) {
        super(p_i1823_3_, p_i1823_4_, p_i1823_5_, p_i1823_6_);
        this.thePlayer = p_i1823_1_;
        this.craftMatrix = p_i1823_2_;
    }
    
    @Override
    public boolean isItemValid(final ItemStack p_75214_1_) {
        return false;
    }
    
    @Override
    public ItemStack decrStackSize(final int p_75209_1_) {
        if (this.getHasStack()) {
            this.amountCrafted += Math.min(p_75209_1_, this.getStack().stackSize);
        }
        return super.decrStackSize(p_75209_1_);
    }
    
    @Override
    protected void onCrafting(final ItemStack p_75210_1_, final int p_75210_2_) {
        this.amountCrafted += p_75210_2_;
        this.onCrafting(p_75210_1_);
    }
    
    @Override
    protected void onCrafting(final ItemStack p_75208_1_) {
        p_75208_1_.onCrafting(this.thePlayer.worldObj, this.thePlayer, this.amountCrafted);
        this.amountCrafted = 0;
        if (p_75208_1_.getItem() == Item.getItemFromBlock(Blocks.crafting_table)) {
            this.thePlayer.addStat(AchievementList.buildWorkBench, 1);
        }
        if (p_75208_1_.getItem() instanceof ItemPickaxe) {
            this.thePlayer.addStat(AchievementList.buildPickaxe, 1);
        }
        if (p_75208_1_.getItem() == Item.getItemFromBlock(Blocks.furnace)) {
            this.thePlayer.addStat(AchievementList.buildFurnace, 1);
        }
        if (p_75208_1_.getItem() instanceof ItemHoe) {
            this.thePlayer.addStat(AchievementList.buildHoe, 1);
        }
        if (p_75208_1_.getItem() == Items.bread) {
            this.thePlayer.addStat(AchievementList.makeBread, 1);
        }
        if (p_75208_1_.getItem() == Items.cake) {
            this.thePlayer.addStat(AchievementList.bakeCake, 1);
        }
        if (p_75208_1_.getItem() instanceof ItemPickaxe && ((ItemPickaxe)p_75208_1_.getItem()).func_150913_i() != Item.ToolMaterial.WOOD) {
            this.thePlayer.addStat(AchievementList.buildBetterPickaxe, 1);
        }
        if (p_75208_1_.getItem() instanceof ItemSword) {
            this.thePlayer.addStat(AchievementList.buildSword, 1);
        }
        if (p_75208_1_.getItem() == Item.getItemFromBlock(Blocks.enchanting_table)) {
            this.thePlayer.addStat(AchievementList.enchantments, 1);
        }
        if (p_75208_1_.getItem() == Item.getItemFromBlock(Blocks.bookshelf)) {
            this.thePlayer.addStat(AchievementList.bookcase, 1);
        }
    }
    
    @Override
    public void onPickupFromSlot(final EntityPlayer p_82870_1_, final ItemStack p_82870_2_) {
        this.onCrafting(p_82870_2_);
        for (int var3 = 0; var3 < this.craftMatrix.getSizeInventory(); ++var3) {
            final ItemStack var4 = this.craftMatrix.getStackInSlot(var3);
            if (var4 != null) {
                this.craftMatrix.decrStackSize(var3, 1);
                if (var4.getItem().hasContainerItem()) {
                    final ItemStack var5 = new ItemStack(var4.getItem().getContainerItem());
                    if (!var4.getItem().doesContainerItemLeaveCraftingGrid(var4) || !this.thePlayer.inventory.addItemStackToInventory(var5)) {
                        if (this.craftMatrix.getStackInSlot(var3) == null) {
                            this.craftMatrix.setInventorySlotContents(var3, var5);
                        }
                        else {
                            this.thePlayer.dropPlayerItemWithRandomChoice(var5, false);
                        }
                    }
                }
            }
        }
    }
}
