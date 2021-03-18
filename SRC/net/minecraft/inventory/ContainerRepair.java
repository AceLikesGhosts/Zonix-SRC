package net.minecraft.inventory;

import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.enchantment.*;
import org.apache.commons.lang3.*;
import java.util.*;
import org.apache.logging.log4j.*;

public class ContainerRepair extends Container
{
    private static final Logger logger;
    private IInventory outputSlot;
    private IInventory inputSlots;
    private World theWorld;
    private int field_82861_i;
    private int field_82858_j;
    private int field_82859_k;
    public int maximumCost;
    private int stackSizeToBeUsedInRepair;
    private String repairedItemName;
    private final EntityPlayer thePlayer;
    private static final String __OBFID = "CL_00001732";
    
    public ContainerRepair(final InventoryPlayer p_i1800_1_, final World p_i1800_2_, final int p_i1800_3_, final int p_i1800_4_, final int p_i1800_5_, final EntityPlayer p_i1800_6_) {
        this.outputSlot = new InventoryCraftResult();
        this.inputSlots = new InventoryBasic("Repair", true, 2) {
            private static final String __OBFID = "CL_00001733";
            
            @Override
            public void onInventoryChanged() {
                super.onInventoryChanged();
                ContainerRepair.this.onCraftMatrixChanged(this);
            }
        };
        this.theWorld = p_i1800_2_;
        this.field_82861_i = p_i1800_3_;
        this.field_82858_j = p_i1800_4_;
        this.field_82859_k = p_i1800_5_;
        this.thePlayer = p_i1800_6_;
        this.addSlotToContainer(new Slot(this.inputSlots, 0, 27, 47));
        this.addSlotToContainer(new Slot(this.inputSlots, 1, 76, 47));
        this.addSlotToContainer(new Slot(this.outputSlot, 2, 134, 47) {
            private static final String __OBFID = "CL_00001734";
            
            @Override
            public boolean isItemValid(final ItemStack p_75214_1_) {
                return false;
            }
            
            @Override
            public boolean canTakeStack(final EntityPlayer p_82869_1_) {
                return (p_82869_1_.capabilities.isCreativeMode || p_82869_1_.experienceLevel >= ContainerRepair.this.maximumCost) && ContainerRepair.this.maximumCost > 0 && this.getHasStack();
            }
            
            @Override
            public void onPickupFromSlot(final EntityPlayer p_82870_1_, final ItemStack p_82870_2_) {
                if (!p_82870_1_.capabilities.isCreativeMode) {
                    p_82870_1_.addExperienceLevel(-ContainerRepair.this.maximumCost);
                }
                ContainerRepair.this.inputSlots.setInventorySlotContents(0, null);
                if (ContainerRepair.this.stackSizeToBeUsedInRepair > 0) {
                    final ItemStack var3 = ContainerRepair.this.inputSlots.getStackInSlot(1);
                    if (var3 != null && var3.stackSize > ContainerRepair.this.stackSizeToBeUsedInRepair) {
                        final ItemStack itemStack = var3;
                        itemStack.stackSize -= ContainerRepair.this.stackSizeToBeUsedInRepair;
                        ContainerRepair.this.inputSlots.setInventorySlotContents(1, var3);
                    }
                    else {
                        ContainerRepair.this.inputSlots.setInventorySlotContents(1, null);
                    }
                }
                else {
                    ContainerRepair.this.inputSlots.setInventorySlotContents(1, null);
                }
                ContainerRepair.this.maximumCost = 0;
                if (!p_82870_1_.capabilities.isCreativeMode && !p_i1800_2_.isClient && p_i1800_2_.getBlock(p_i1800_3_, p_i1800_4_, p_i1800_5_) == Blocks.anvil && p_82870_1_.getRNG().nextFloat() < 0.12f) {
                    final int var4 = p_i1800_2_.getBlockMetadata(p_i1800_3_, p_i1800_4_, p_i1800_5_);
                    final int var5 = var4 & 0x3;
                    int var6 = var4 >> 2;
                    if (++var6 > 2) {
                        p_i1800_2_.setBlockToAir(p_i1800_3_, p_i1800_4_, p_i1800_5_);
                        p_i1800_2_.playAuxSFX(1020, p_i1800_3_, p_i1800_4_, p_i1800_5_, 0);
                    }
                    else {
                        p_i1800_2_.setBlockMetadataWithNotify(p_i1800_3_, p_i1800_4_, p_i1800_5_, var5 | var6 << 2, 2);
                        p_i1800_2_.playAuxSFX(1021, p_i1800_3_, p_i1800_4_, p_i1800_5_, 0);
                    }
                }
                else if (!p_i1800_2_.isClient) {
                    p_i1800_2_.playAuxSFX(1021, p_i1800_3_, p_i1800_4_, p_i1800_5_, 0);
                }
            }
        });
        for (int var7 = 0; var7 < 3; ++var7) {
            for (int var8 = 0; var8 < 9; ++var8) {
                this.addSlotToContainer(new Slot(p_i1800_1_, var8 + var7 * 9 + 9, 8 + var8 * 18, 84 + var7 * 18));
            }
        }
        for (int var7 = 0; var7 < 9; ++var7) {
            this.addSlotToContainer(new Slot(p_i1800_1_, var7, 8 + var7 * 18, 142));
        }
    }
    
    @Override
    public void onCraftMatrixChanged(final IInventory p_75130_1_) {
        super.onCraftMatrixChanged(p_75130_1_);
        if (p_75130_1_ == this.inputSlots) {
            this.updateRepairOutput();
        }
    }
    
    public void updateRepairOutput() {
        final ItemStack var1 = this.inputSlots.getStackInSlot(0);
        this.maximumCost = 0;
        int var2 = 0;
        final byte var3 = 0;
        int var4 = 0;
        if (var1 == null) {
            this.outputSlot.setInventorySlotContents(0, null);
            this.maximumCost = 0;
        }
        else {
            ItemStack var5 = var1.copy();
            final ItemStack var6 = this.inputSlots.getStackInSlot(1);
            final Map var7 = EnchantmentHelper.getEnchantments(var5);
            boolean var8 = false;
            int var9 = var3 + var1.getRepairCost() + ((var6 == null) ? 0 : var6.getRepairCost());
            this.stackSizeToBeUsedInRepair = 0;
            if (var6 != null) {
                var8 = (var6.getItem() == Items.enchanted_book && Items.enchanted_book.func_92110_g(var6).tagCount() > 0);
                if (var5.isItemStackDamageable() && var5.getItem().getIsRepairable(var1, var6)) {
                    int var10 = Math.min(var5.getItemDamageForDisplay(), var5.getMaxDamage() / 4);
                    if (var10 <= 0) {
                        this.outputSlot.setInventorySlotContents(0, null);
                        this.maximumCost = 0;
                        return;
                    }
                    int var11;
                    for (var11 = 0; var10 > 0 && var11 < var6.stackSize; var10 = Math.min(var5.getItemDamageForDisplay(), var5.getMaxDamage() / 4), ++var11) {
                        final int var12 = var5.getItemDamageForDisplay() - var10;
                        var5.setItemDamage(var12);
                        var2 += Math.max(1, var10 / 100) + var7.size();
                    }
                    this.stackSizeToBeUsedInRepair = var11;
                }
                else {
                    if (!var8 && (var5.getItem() != var6.getItem() || !var5.isItemStackDamageable())) {
                        this.outputSlot.setInventorySlotContents(0, null);
                        this.maximumCost = 0;
                        return;
                    }
                    if (var5.isItemStackDamageable() && !var8) {
                        final int var10 = var1.getMaxDamage() - var1.getItemDamageForDisplay();
                        final int var11 = var6.getMaxDamage() - var6.getItemDamageForDisplay();
                        final int var12 = var11 + var5.getMaxDamage() * 12 / 100;
                        final int var13 = var10 + var12;
                        int var14 = var5.getMaxDamage() - var13;
                        if (var14 < 0) {
                            var14 = 0;
                        }
                        if (var14 < var5.getItemDamage()) {
                            var5.setItemDamage(var14);
                            var2 += Math.max(1, var12 / 100);
                        }
                    }
                    final Map var15 = EnchantmentHelper.getEnchantments(var6);
                    final Iterator var16 = var15.keySet().iterator();
                    while (var16.hasNext()) {
                        final int var12 = var16.next();
                        final Enchantment var17 = Enchantment.enchantmentsList[var12];
                        final int var14 = var7.containsKey(var12) ? var7.get(var12) : 0;
                        int var18 = var15.get(var12);
                        int var19;
                        if (var14 == var18) {
                            var19 = ++var18;
                        }
                        else {
                            var19 = Math.max(var18, var14);
                        }
                        var18 = var19;
                        final int var20 = var18 - var14;
                        boolean var21 = var17.canApply(var1);
                        if (this.thePlayer.capabilities.isCreativeMode || var1.getItem() == Items.enchanted_book) {
                            var21 = true;
                        }
                        for (final int var23 : var7.keySet()) {
                            if (var23 != var12 && !var17.canApplyTogether(Enchantment.enchantmentsList[var23])) {
                                var21 = false;
                                var2 += var20;
                            }
                        }
                        if (var21) {
                            if (var18 > var17.getMaxLevel()) {
                                var18 = var17.getMaxLevel();
                            }
                            var7.put(var12, var18);
                            int var24 = 0;
                            switch (var17.getWeight()) {
                                case 1: {
                                    var24 = 8;
                                    break;
                                }
                                case 2: {
                                    var24 = 4;
                                    break;
                                }
                                case 5: {
                                    var24 = 2;
                                    break;
                                }
                                case 10: {
                                    var24 = 1;
                                    break;
                                }
                            }
                            if (var8) {
                                var24 = Math.max(1, var24 / 2);
                            }
                            var2 += var24 * var20;
                        }
                    }
                }
            }
            if (StringUtils.isBlank((CharSequence)this.repairedItemName)) {
                if (var1.hasDisplayName()) {
                    var4 = (var1.isItemStackDamageable() ? 7 : (var1.stackSize * 5));
                    var2 += var4;
                    var5.func_135074_t();
                }
            }
            else if (!this.repairedItemName.equals(var1.getDisplayName())) {
                var4 = (var1.isItemStackDamageable() ? 7 : (var1.stackSize * 5));
                var2 += var4;
                if (var1.hasDisplayName()) {
                    var9 += var4 / 2;
                }
                var5.setStackDisplayName(this.repairedItemName);
            }
            int var10 = 0;
            final Iterator var16 = var7.keySet().iterator();
            while (var16.hasNext()) {
                final int var12 = var16.next();
                final Enchantment var17 = Enchantment.enchantmentsList[var12];
                final int var14 = var7.get(var12);
                int var18 = 0;
                ++var10;
                switch (var17.getWeight()) {
                    case 1: {
                        var18 = 8;
                        break;
                    }
                    case 2: {
                        var18 = 4;
                        break;
                    }
                    case 5: {
                        var18 = 2;
                        break;
                    }
                    case 10: {
                        var18 = 1;
                        break;
                    }
                }
                if (var8) {
                    var18 = Math.max(1, var18 / 2);
                }
                var9 += var10 + var14 * var18;
            }
            if (var8) {
                var9 = Math.max(1, var9 / 2);
            }
            this.maximumCost = var9 + var2;
            if (var2 <= 0) {
                var5 = null;
            }
            if (var4 == var2 && var4 > 0 && this.maximumCost >= 40) {
                this.maximumCost = 39;
            }
            if (this.maximumCost >= 40 && !this.thePlayer.capabilities.isCreativeMode) {
                var5 = null;
            }
            if (var5 != null) {
                int var11 = var5.getRepairCost();
                if (var6 != null && var11 < var6.getRepairCost()) {
                    var11 = var6.getRepairCost();
                }
                if (var5.hasDisplayName()) {
                    var11 -= 9;
                }
                if (var11 < 0) {
                    var11 = 0;
                }
                var11 += 2;
                var5.setRepairCost(var11);
                EnchantmentHelper.setEnchantments(var7, var5);
            }
            this.outputSlot.setInventorySlotContents(0, var5);
            this.detectAndSendChanges();
        }
    }
    
    @Override
    public void addCraftingToCrafters(final ICrafting p_75132_1_) {
        super.addCraftingToCrafters(p_75132_1_);
        p_75132_1_.sendProgressBarUpdate(this, 0, this.maximumCost);
    }
    
    @Override
    public void updateProgressBar(final int p_75137_1_, final int p_75137_2_) {
        if (p_75137_1_ == 0) {
            this.maximumCost = p_75137_2_;
        }
    }
    
    @Override
    public void onContainerClosed(final EntityPlayer p_75134_1_) {
        super.onContainerClosed(p_75134_1_);
        if (!this.theWorld.isClient) {
            for (int var2 = 0; var2 < this.inputSlots.getSizeInventory(); ++var2) {
                final ItemStack var3 = this.inputSlots.getStackInSlotOnClosing(var2);
                if (var3 != null) {
                    p_75134_1_.dropPlayerItemWithRandomChoice(var3, false);
                }
            }
        }
    }
    
    @Override
    public boolean canInteractWith(final EntityPlayer p_75145_1_) {
        return this.theWorld.getBlock(this.field_82861_i, this.field_82858_j, this.field_82859_k) == Blocks.anvil && p_75145_1_.getDistanceSq(this.field_82861_i + 0.5, this.field_82858_j + 0.5, this.field_82859_k + 0.5) <= 64.0;
    }
    
    @Override
    public ItemStack transferStackInSlot(final EntityPlayer p_82846_1_, final int p_82846_2_) {
        ItemStack var3 = null;
        final Slot var4 = this.inventorySlots.get(p_82846_2_);
        if (var4 != null && var4.getHasStack()) {
            final ItemStack var5 = var4.getStack();
            var3 = var5.copy();
            if (p_82846_2_ == 2) {
                if (!this.mergeItemStack(var5, 3, 39, true)) {
                    return null;
                }
                var4.onSlotChange(var5, var3);
            }
            else if (p_82846_2_ != 0 && p_82846_2_ != 1) {
                if (p_82846_2_ >= 3 && p_82846_2_ < 39 && !this.mergeItemStack(var5, 0, 2, false)) {
                    return null;
                }
            }
            else if (!this.mergeItemStack(var5, 3, 39, false)) {
                return null;
            }
            if (var5.stackSize == 0) {
                var4.putStack(null);
            }
            else {
                var4.onSlotChanged();
            }
            if (var5.stackSize == var3.stackSize) {
                return null;
            }
            var4.onPickupFromSlot(p_82846_1_, var5);
        }
        return var3;
    }
    
    public void updateItemName(final String p_82850_1_) {
        this.repairedItemName = p_82850_1_;
        if (this.getSlot(2).getHasStack()) {
            final ItemStack var2 = this.getSlot(2).getStack();
            if (StringUtils.isBlank((CharSequence)p_82850_1_)) {
                var2.func_135074_t();
            }
            else {
                var2.setStackDisplayName(this.repairedItemName);
            }
        }
        this.updateRepairOutput();
    }
    
    static {
        logger = LogManager.getLogger();
    }
}
