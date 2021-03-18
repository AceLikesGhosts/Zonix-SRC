package net.minecraft.item.crafting;

import net.minecraft.inventory.*;
import net.minecraft.world.*;
import net.minecraft.init.*;
import net.minecraft.nbt.*;
import java.util.*;
import net.minecraft.item.*;

public class RecipeFireworks implements IRecipe
{
    private ItemStack field_92102_a;
    private static final String __OBFID = "CL_00000083";
    
    @Override
    public boolean matches(final InventoryCrafting p_77569_1_, final World p_77569_2_) {
        this.field_92102_a = null;
        int var3 = 0;
        int var4 = 0;
        int var5 = 0;
        int var6 = 0;
        int var7 = 0;
        int var8 = 0;
        for (int var9 = 0; var9 < p_77569_1_.getSizeInventory(); ++var9) {
            final ItemStack var10 = p_77569_1_.getStackInSlot(var9);
            if (var10 != null) {
                if (var10.getItem() == Items.gunpowder) {
                    ++var4;
                }
                else if (var10.getItem() == Items.firework_charge) {
                    ++var6;
                }
                else if (var10.getItem() == Items.dye) {
                    ++var5;
                }
                else if (var10.getItem() == Items.paper) {
                    ++var3;
                }
                else if (var10.getItem() == Items.glowstone_dust) {
                    ++var7;
                }
                else if (var10.getItem() == Items.diamond) {
                    ++var7;
                }
                else if (var10.getItem() == Items.fire_charge) {
                    ++var8;
                }
                else if (var10.getItem() == Items.feather) {
                    ++var8;
                }
                else if (var10.getItem() == Items.gold_nugget) {
                    ++var8;
                }
                else {
                    if (var10.getItem() != Items.skull) {
                        return false;
                    }
                    ++var8;
                }
            }
        }
        var7 += var5 + var8;
        if (var4 > 3 || var3 > 1) {
            return false;
        }
        if (var4 >= 1 && var3 == 1 && var7 == 0) {
            this.field_92102_a = new ItemStack(Items.fireworks);
            if (var6 > 0) {
                final NBTTagCompound var11 = new NBTTagCompound();
                final NBTTagCompound var12 = new NBTTagCompound();
                final NBTTagList var13 = new NBTTagList();
                for (int var14 = 0; var14 < p_77569_1_.getSizeInventory(); ++var14) {
                    final ItemStack var15 = p_77569_1_.getStackInSlot(var14);
                    if (var15 != null && var15.getItem() == Items.firework_charge && var15.hasTagCompound() && var15.getTagCompound().func_150297_b("Explosion", 10)) {
                        var13.appendTag(var15.getTagCompound().getCompoundTag("Explosion"));
                    }
                }
                var12.setTag("Explosions", var13);
                var12.setByte("Flight", (byte)var4);
                var11.setTag("Fireworks", var12);
                this.field_92102_a.setTagCompound(var11);
            }
            return true;
        }
        if (var4 == 1 && var3 == 0 && var6 == 0 && var5 > 0 && var8 <= 1) {
            this.field_92102_a = new ItemStack(Items.firework_charge);
            final NBTTagCompound var11 = new NBTTagCompound();
            final NBTTagCompound var12 = new NBTTagCompound();
            byte var16 = 0;
            final ArrayList var17 = new ArrayList();
            for (int var18 = 0; var18 < p_77569_1_.getSizeInventory(); ++var18) {
                final ItemStack var19 = p_77569_1_.getStackInSlot(var18);
                if (var19 != null) {
                    if (var19.getItem() == Items.dye) {
                        var17.add(ItemDye.field_150922_c[var19.getItemDamage()]);
                    }
                    else if (var19.getItem() == Items.glowstone_dust) {
                        var12.setBoolean("Flicker", true);
                    }
                    else if (var19.getItem() == Items.diamond) {
                        var12.setBoolean("Trail", true);
                    }
                    else if (var19.getItem() == Items.fire_charge) {
                        var16 = 1;
                    }
                    else if (var19.getItem() == Items.feather) {
                        var16 = 4;
                    }
                    else if (var19.getItem() == Items.gold_nugget) {
                        var16 = 2;
                    }
                    else if (var19.getItem() == Items.skull) {
                        var16 = 3;
                    }
                }
            }
            final int[] var20 = new int[var17.size()];
            for (int var21 = 0; var21 < var20.length; ++var21) {
                var20[var21] = var17.get(var21);
            }
            var12.setIntArray("Colors", var20);
            var12.setByte("Type", var16);
            var11.setTag("Explosion", var12);
            this.field_92102_a.setTagCompound(var11);
            return true;
        }
        if (var4 != 0 || var3 != 0 || var6 != 1 || var5 <= 0 || var5 != var7) {
            return false;
        }
        final ArrayList var22 = new ArrayList();
        for (int var23 = 0; var23 < p_77569_1_.getSizeInventory(); ++var23) {
            final ItemStack var24 = p_77569_1_.getStackInSlot(var23);
            if (var24 != null) {
                if (var24.getItem() == Items.dye) {
                    var22.add(ItemDye.field_150922_c[var24.getItemDamage()]);
                }
                else if (var24.getItem() == Items.firework_charge) {
                    this.field_92102_a = var24.copy();
                    this.field_92102_a.stackSize = 1;
                }
            }
        }
        final int[] var25 = new int[var22.size()];
        for (int var26 = 0; var26 < var25.length; ++var26) {
            var25[var26] = var22.get(var26);
        }
        if (this.field_92102_a == null || !this.field_92102_a.hasTagCompound()) {
            return false;
        }
        final NBTTagCompound var27 = this.field_92102_a.getTagCompound().getCompoundTag("Explosion");
        if (var27 == null) {
            return false;
        }
        var27.setIntArray("FadeColors", var25);
        return true;
    }
    
    @Override
    public ItemStack getCraftingResult(final InventoryCrafting p_77572_1_) {
        return this.field_92102_a.copy();
    }
    
    @Override
    public int getRecipeSize() {
        return 10;
    }
    
    @Override
    public ItemStack getRecipeOutput() {
        return this.field_92102_a;
    }
}
