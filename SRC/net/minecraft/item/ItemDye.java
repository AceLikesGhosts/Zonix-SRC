package net.minecraft.item;

import net.minecraft.creativetab.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.init.*;
import net.minecraft.block.material.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.entity.passive.*;
import net.minecraft.block.*;
import java.util.*;
import net.minecraft.client.renderer.texture.*;

public class ItemDye extends Item
{
    public static final String[] field_150923_a;
    public static final String[] field_150921_b;
    public static final int[] field_150922_c;
    private IIcon[] field_150920_d;
    private static final String __OBFID = "CL_00000022";
    
    public ItemDye() {
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
        this.setCreativeTab(CreativeTabs.tabMaterials);
    }
    
    @Override
    public IIcon getIconFromDamage(final int p_77617_1_) {
        final int var2 = MathHelper.clamp_int(p_77617_1_, 0, 15);
        return this.field_150920_d[var2];
    }
    
    @Override
    public String getUnlocalizedName(final ItemStack p_77667_1_) {
        final int var2 = MathHelper.clamp_int(p_77667_1_.getItemDamage(), 0, 15);
        return super.getUnlocalizedName() + "." + ItemDye.field_150923_a[var2];
    }
    
    @Override
    public boolean onItemUse(final ItemStack p_77648_1_, final EntityPlayer p_77648_2_, final World p_77648_3_, int p_77648_4_, final int p_77648_5_, int p_77648_6_, final int p_77648_7_, final float p_77648_8_, final float p_77648_9_, final float p_77648_10_) {
        if (!p_77648_2_.canPlayerEdit(p_77648_4_, p_77648_5_, p_77648_6_, p_77648_7_, p_77648_1_)) {
            return false;
        }
        if (p_77648_1_.getItemDamage() == 15) {
            if (func_150919_a(p_77648_1_, p_77648_3_, p_77648_4_, p_77648_5_, p_77648_6_)) {
                if (!p_77648_3_.isClient) {
                    p_77648_3_.playAuxSFX(2005, p_77648_4_, p_77648_5_, p_77648_6_, 0);
                }
                return true;
            }
        }
        else if (p_77648_1_.getItemDamage() == 3) {
            final Block var11 = p_77648_3_.getBlock(p_77648_4_, p_77648_5_, p_77648_6_);
            final int var12 = p_77648_3_.getBlockMetadata(p_77648_4_, p_77648_5_, p_77648_6_);
            if (var11 == Blocks.log && BlockLog.func_150165_c(var12) == 3) {
                if (p_77648_7_ == 0) {
                    return false;
                }
                if (p_77648_7_ == 1) {
                    return false;
                }
                if (p_77648_7_ == 2) {
                    --p_77648_6_;
                }
                if (p_77648_7_ == 3) {
                    ++p_77648_6_;
                }
                if (p_77648_7_ == 4) {
                    --p_77648_4_;
                }
                if (p_77648_7_ == 5) {
                    ++p_77648_4_;
                }
                if (p_77648_3_.isAirBlock(p_77648_4_, p_77648_5_, p_77648_6_)) {
                    final int var13 = Blocks.cocoa.onBlockPlaced(p_77648_3_, p_77648_4_, p_77648_5_, p_77648_6_, p_77648_7_, p_77648_8_, p_77648_9_, p_77648_10_, 0);
                    p_77648_3_.setBlock(p_77648_4_, p_77648_5_, p_77648_6_, Blocks.cocoa, var13, 2);
                    if (!p_77648_2_.capabilities.isCreativeMode) {
                        --p_77648_1_.stackSize;
                    }
                }
                return true;
            }
        }
        return false;
    }
    
    public static boolean func_150919_a(final ItemStack p_150919_0_, final World p_150919_1_, final int p_150919_2_, final int p_150919_3_, final int p_150919_4_) {
        final Block var5 = p_150919_1_.getBlock(p_150919_2_, p_150919_3_, p_150919_4_);
        if (var5 instanceof IGrowable) {
            final IGrowable var6 = (IGrowable)var5;
            if (var6.func_149851_a(p_150919_1_, p_150919_2_, p_150919_3_, p_150919_4_, p_150919_1_.isClient)) {
                if (!p_150919_1_.isClient) {
                    if (var6.func_149852_a(p_150919_1_, p_150919_1_.rand, p_150919_2_, p_150919_3_, p_150919_4_)) {
                        var6.func_149853_b(p_150919_1_, p_150919_1_.rand, p_150919_2_, p_150919_3_, p_150919_4_);
                    }
                    --p_150919_0_.stackSize;
                }
                return true;
            }
        }
        return false;
    }
    
    public static void func_150918_a(final World p_150918_0_, final int p_150918_1_, final int p_150918_2_, final int p_150918_3_, int p_150918_4_) {
        if (p_150918_4_ == 0) {
            p_150918_4_ = 15;
        }
        final Block var5 = p_150918_0_.getBlock(p_150918_1_, p_150918_2_, p_150918_3_);
        if (var5.getMaterial() != Material.air) {
            var5.setBlockBoundsBasedOnState(p_150918_0_, p_150918_1_, p_150918_2_, p_150918_3_);
            for (int var6 = 0; var6 < p_150918_4_; ++var6) {
                final double var7 = ItemDye.itemRand.nextGaussian() * 0.02;
                final double var8 = ItemDye.itemRand.nextGaussian() * 0.02;
                final double var9 = ItemDye.itemRand.nextGaussian() * 0.02;
                p_150918_0_.spawnParticle("happyVillager", p_150918_1_ + ItemDye.itemRand.nextFloat(), p_150918_2_ + ItemDye.itemRand.nextFloat() * var5.getBlockBoundsMaxY(), p_150918_3_ + ItemDye.itemRand.nextFloat(), var7, var8, var9);
            }
        }
    }
    
    @Override
    public boolean itemInteractionForEntity(final ItemStack p_111207_1_, final EntityPlayer p_111207_2_, final EntityLivingBase p_111207_3_) {
        if (p_111207_3_ instanceof EntitySheep) {
            final EntitySheep var4 = (EntitySheep)p_111207_3_;
            final int var5 = BlockColored.func_150032_b(p_111207_1_.getItemDamage());
            if (!var4.getSheared() && var4.getFleeceColor() != var5) {
                var4.setFleeceColor(var5);
                --p_111207_1_.stackSize;
            }
            return true;
        }
        return false;
    }
    
    @Override
    public void getSubItems(final Item p_150895_1_, final CreativeTabs p_150895_2_, final List p_150895_3_) {
        for (int var4 = 0; var4 < 16; ++var4) {
            p_150895_3_.add(new ItemStack(p_150895_1_, 1, var4));
        }
    }
    
    @Override
    public void registerIcons(final IIconRegister p_94581_1_) {
        this.field_150920_d = new IIcon[ItemDye.field_150921_b.length];
        for (int var2 = 0; var2 < ItemDye.field_150921_b.length; ++var2) {
            this.field_150920_d[var2] = p_94581_1_.registerIcon(this.getIconString() + "_" + ItemDye.field_150921_b[var2]);
        }
    }
    
    static {
        field_150923_a = new String[] { "black", "red", "green", "brown", "blue", "purple", "cyan", "silver", "gray", "pink", "lime", "yellow", "lightBlue", "magenta", "orange", "white" };
        field_150921_b = new String[] { "black", "red", "green", "brown", "blue", "purple", "cyan", "silver", "gray", "pink", "lime", "yellow", "light_blue", "magenta", "orange", "white" };
        field_150922_c = new int[] { 1973019, 11743532, 3887386, 5320730, 2437522, 8073150, 2651799, 11250603, 4408131, 14188952, 4312372, 14602026, 6719955, 12801229, 15435844, 15790320 };
    }
}
