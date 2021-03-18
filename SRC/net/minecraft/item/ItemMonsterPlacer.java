package net.minecraft.item;

import net.minecraft.creativetab.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.block.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import java.util.*;
import net.minecraft.client.renderer.texture.*;

public class ItemMonsterPlacer extends Item
{
    private IIcon theIcon;
    private static final String __OBFID = "CL_00000070";
    
    public ItemMonsterPlacer() {
        this.setHasSubtypes(true);
        this.setCreativeTab(CreativeTabs.tabMisc);
    }
    
    @Override
    public String getItemStackDisplayName(final ItemStack p_77653_1_) {
        String var2 = ("" + StatCollector.translateToLocal(this.getUnlocalizedName() + ".name")).trim();
        final String var3 = EntityList.getStringFromID(p_77653_1_.getItemDamage());
        if (var3 != null) {
            var2 = var2 + " " + StatCollector.translateToLocal("entity." + var3 + ".name");
        }
        return var2;
    }
    
    @Override
    public int getColorFromItemStack(final ItemStack p_82790_1_, final int p_82790_2_) {
        final EntityList.EntityEggInfo var3 = EntityList.entityEggs.get(p_82790_1_.getItemDamage());
        return (var3 != null) ? ((p_82790_2_ == 0) ? var3.primaryColor : var3.secondaryColor) : 16777215;
    }
    
    @Override
    public boolean requiresMultipleRenderPasses() {
        return true;
    }
    
    @Override
    public IIcon getIconFromDamageForRenderPass(final int p_77618_1_, final int p_77618_2_) {
        return (p_77618_2_ > 0) ? this.theIcon : super.getIconFromDamageForRenderPass(p_77618_1_, p_77618_2_);
    }
    
    @Override
    public boolean onItemUse(final ItemStack p_77648_1_, final EntityPlayer p_77648_2_, final World p_77648_3_, int p_77648_4_, int p_77648_5_, int p_77648_6_, final int p_77648_7_, final float p_77648_8_, final float p_77648_9_, final float p_77648_10_) {
        if (p_77648_3_.isClient) {
            return true;
        }
        final Block var11 = p_77648_3_.getBlock(p_77648_4_, p_77648_5_, p_77648_6_);
        p_77648_4_ += Facing.offsetsXForSide[p_77648_7_];
        p_77648_5_ += Facing.offsetsYForSide[p_77648_7_];
        p_77648_6_ += Facing.offsetsZForSide[p_77648_7_];
        double var12 = 0.0;
        if (p_77648_7_ == 1 && var11.getRenderType() == 11) {
            var12 = 0.5;
        }
        final Entity var13 = spawnCreature(p_77648_3_, p_77648_1_.getItemDamage(), p_77648_4_ + 0.5, p_77648_5_ + var12, p_77648_6_ + 0.5);
        if (var13 != null) {
            if (var13 instanceof EntityLivingBase && p_77648_1_.hasDisplayName()) {
                ((EntityLiving)var13).setCustomNameTag(p_77648_1_.getDisplayName());
            }
            if (!p_77648_2_.capabilities.isCreativeMode) {
                --p_77648_1_.stackSize;
            }
        }
        return true;
    }
    
    @Override
    public ItemStack onItemRightClick(final ItemStack p_77659_1_, final World p_77659_2_, final EntityPlayer p_77659_3_) {
        if (p_77659_2_.isClient) {
            return p_77659_1_;
        }
        final MovingObjectPosition var4 = this.getMovingObjectPositionFromPlayer(p_77659_2_, p_77659_3_, true);
        if (var4 == null) {
            return p_77659_1_;
        }
        if (var4.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
            final int var5 = var4.blockX;
            final int var6 = var4.blockY;
            final int var7 = var4.blockZ;
            if (!p_77659_2_.canMineBlock(p_77659_3_, var5, var6, var7)) {
                return p_77659_1_;
            }
            if (!p_77659_3_.canPlayerEdit(var5, var6, var7, var4.sideHit, p_77659_1_)) {
                return p_77659_1_;
            }
            if (p_77659_2_.getBlock(var5, var6, var7) instanceof BlockLiquid) {
                final Entity var8 = spawnCreature(p_77659_2_, p_77659_1_.getItemDamage(), var5, var6, var7);
                if (var8 != null) {
                    if (var8 instanceof EntityLivingBase && p_77659_1_.hasDisplayName()) {
                        ((EntityLiving)var8).setCustomNameTag(p_77659_1_.getDisplayName());
                    }
                    if (!p_77659_3_.capabilities.isCreativeMode) {
                        --p_77659_1_.stackSize;
                    }
                }
            }
        }
        return p_77659_1_;
    }
    
    public static Entity spawnCreature(final World p_77840_0_, final int p_77840_1_, final double p_77840_2_, final double p_77840_4_, final double p_77840_6_) {
        if (!EntityList.entityEggs.containsKey(p_77840_1_)) {
            return null;
        }
        Entity var8 = null;
        for (int var9 = 0; var9 < 1; ++var9) {
            var8 = EntityList.createEntityByID(p_77840_1_, p_77840_0_);
            if (var8 != null && var8 instanceof EntityLivingBase) {
                final EntityLiving var10 = (EntityLiving)var8;
                var8.setLocationAndAngles(p_77840_2_, p_77840_4_, p_77840_6_, MathHelper.wrapAngleTo180_float(p_77840_0_.rand.nextFloat() * 360.0f), 0.0f);
                var10.rotationYawHead = var10.rotationYaw;
                var10.renderYawOffset = var10.rotationYaw;
                var10.onSpawnWithEgg(null);
                p_77840_0_.spawnEntityInWorld(var8);
                var10.playLivingSound();
            }
        }
        return var8;
    }
    
    @Override
    public void getSubItems(final Item p_150895_1_, final CreativeTabs p_150895_2_, final List p_150895_3_) {
        for (final EntityList.EntityEggInfo var5 : EntityList.entityEggs.values()) {
            p_150895_3_.add(new ItemStack(p_150895_1_, 1, var5.spawnedID));
        }
    }
    
    @Override
    public void registerIcons(final IIconRegister p_94581_1_) {
        super.registerIcons(p_94581_1_);
        this.theIcon = p_94581_1_.registerIcon(this.getIconString() + "_overlay");
    }
}
