package net.minecraft.item;

import net.minecraft.nbt.*;
import net.minecraft.entity.player.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.texture.*;

public class ItemFireworkCharge extends Item
{
    private IIcon field_150904_a;
    private static final String __OBFID = "CL_00000030";
    
    @Override
    public IIcon getIconFromDamageForRenderPass(final int p_77618_1_, final int p_77618_2_) {
        return (p_77618_2_ > 0) ? this.field_150904_a : super.getIconFromDamageForRenderPass(p_77618_1_, p_77618_2_);
    }
    
    @Override
    public int getColorFromItemStack(final ItemStack p_82790_1_, final int p_82790_2_) {
        if (p_82790_2_ != 1) {
            return super.getColorFromItemStack(p_82790_1_, p_82790_2_);
        }
        final NBTBase var3 = func_150903_a(p_82790_1_, "Colors");
        if (var3 == null || !(var3 instanceof NBTTagIntArray)) {
            return 9079434;
        }
        final NBTTagIntArray var4 = (NBTTagIntArray)var3;
        final int[] var5 = var4.func_150302_c();
        if (var5.length == 1) {
            return var5[0];
        }
        int var6 = 0;
        int var7 = 0;
        int var8 = 0;
        final int[] var9 = var5;
        for (int var10 = var5.length, var11 = 0; var11 < var10; ++var11) {
            final int var12 = var9[var11];
            var6 += (var12 & 0xFF0000) >> 16;
            var7 += (var12 & 0xFF00) >> 8;
            var8 += (var12 & 0xFF) >> 0;
        }
        var6 /= var5.length;
        var7 /= var5.length;
        var8 /= var5.length;
        return var6 << 16 | var7 << 8 | var8;
    }
    
    @Override
    public boolean requiresMultipleRenderPasses() {
        return true;
    }
    
    public static NBTBase func_150903_a(final ItemStack p_150903_0_, final String p_150903_1_) {
        if (p_150903_0_.hasTagCompound()) {
            final NBTTagCompound var2 = p_150903_0_.getTagCompound().getCompoundTag("Explosion");
            if (var2 != null) {
                return var2.getTag(p_150903_1_);
            }
        }
        return null;
    }
    
    @Override
    public void addInformation(final ItemStack p_77624_1_, final EntityPlayer p_77624_2_, final List p_77624_3_, final boolean p_77624_4_) {
        if (p_77624_1_.hasTagCompound()) {
            final NBTTagCompound var5 = p_77624_1_.getTagCompound().getCompoundTag("Explosion");
            if (var5 != null) {
                func_150902_a(var5, p_77624_3_);
            }
        }
    }
    
    public static void func_150902_a(final NBTTagCompound p_150902_0_, final List p_150902_1_) {
        final byte var2 = p_150902_0_.getByte("Type");
        if (var2 >= 0 && var2 <= 4) {
            p_150902_1_.add(StatCollector.translateToLocal("item.fireworksCharge.type." + var2).trim());
        }
        else {
            p_150902_1_.add(StatCollector.translateToLocal("item.fireworksCharge.type").trim());
        }
        final int[] var3 = p_150902_0_.getIntArray("Colors");
        if (var3.length > 0) {
            boolean var4 = true;
            String var5 = "";
            final int[] var6 = var3;
            for (int var7 = var3.length, var8 = 0; var8 < var7; ++var8) {
                final int var9 = var6[var8];
                if (!var4) {
                    var5 += ", ";
                }
                var4 = false;
                boolean var10 = false;
                for (int var11 = 0; var11 < 16; ++var11) {
                    if (var9 == ItemDye.field_150922_c[var11]) {
                        var10 = true;
                        var5 += StatCollector.translateToLocal("item.fireworksCharge." + ItemDye.field_150923_a[var11]);
                        break;
                    }
                }
                if (!var10) {
                    var5 += StatCollector.translateToLocal("item.fireworksCharge.customColor");
                }
            }
            p_150902_1_.add(var5);
        }
        final int[] var12 = p_150902_0_.getIntArray("FadeColors");
        if (var12.length > 0) {
            boolean var13 = true;
            String var14 = StatCollector.translateToLocal("item.fireworksCharge.fadeTo") + " ";
            final int[] var15 = var12;
            for (int var8 = var12.length, var9 = 0; var9 < var8; ++var9) {
                final int var16 = var15[var9];
                if (!var13) {
                    var14 += ", ";
                }
                var13 = false;
                boolean var17 = false;
                for (int var18 = 0; var18 < 16; ++var18) {
                    if (var16 == ItemDye.field_150922_c[var18]) {
                        var17 = true;
                        var14 += StatCollector.translateToLocal("item.fireworksCharge." + ItemDye.field_150923_a[var18]);
                        break;
                    }
                }
                if (!var17) {
                    var14 += StatCollector.translateToLocal("item.fireworksCharge.customColor");
                }
            }
            p_150902_1_.add(var14);
        }
        boolean var13 = p_150902_0_.getBoolean("Trail");
        if (var13) {
            p_150902_1_.add(StatCollector.translateToLocal("item.fireworksCharge.trail"));
        }
        final boolean var19 = p_150902_0_.getBoolean("Flicker");
        if (var19) {
            p_150902_1_.add(StatCollector.translateToLocal("item.fireworksCharge.flicker"));
        }
    }
    
    @Override
    public void registerIcons(final IIconRegister p_94581_1_) {
        super.registerIcons(p_94581_1_);
        this.field_150904_a = p_94581_1_.registerIcon(this.getIconString() + "_overlay");
    }
}
