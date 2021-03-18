package net.minecraft.dispenser;

import net.minecraft.item.*;
import net.minecraft.block.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.*;

public class BehaviorDefaultDispenseItem implements IBehaviorDispenseItem
{
    private static final String __OBFID = "CL_00001195";
    
    @Override
    public final ItemStack dispense(final IBlockSource p_82482_1_, final ItemStack p_82482_2_) {
        final ItemStack var3 = this.dispenseStack(p_82482_1_, p_82482_2_);
        this.playDispenseSound(p_82482_1_);
        this.spawnDispenseParticles(p_82482_1_, BlockDispenser.func_149937_b(p_82482_1_.getBlockMetadata()));
        return var3;
    }
    
    protected ItemStack dispenseStack(final IBlockSource p_82487_1_, final ItemStack p_82487_2_) {
        final EnumFacing var3 = BlockDispenser.func_149937_b(p_82487_1_.getBlockMetadata());
        final IPosition var4 = BlockDispenser.func_149939_a(p_82487_1_);
        final ItemStack var5 = p_82487_2_.splitStack(1);
        doDispense(p_82487_1_.getWorld(), var5, 6, var3, var4);
        return p_82487_2_;
    }
    
    public static void doDispense(final World p_82486_0_, final ItemStack p_82486_1_, final int p_82486_2_, final EnumFacing p_82486_3_, final IPosition p_82486_4_) {
        final double var5 = p_82486_4_.getX();
        final double var6 = p_82486_4_.getY();
        final double var7 = p_82486_4_.getZ();
        final EntityItem var8 = new EntityItem(p_82486_0_, var5, var6 - 0.3, var7, p_82486_1_);
        final double var9 = p_82486_0_.rand.nextDouble() * 0.1 + 0.2;
        var8.motionX = p_82486_3_.getFrontOffsetX() * var9;
        var8.motionY = 0.20000000298023224;
        var8.motionZ = p_82486_3_.getFrontOffsetZ() * var9;
        final EntityItem entityItem = var8;
        entityItem.motionX += p_82486_0_.rand.nextGaussian() * 0.007499999832361937 * p_82486_2_;
        final EntityItem entityItem2 = var8;
        entityItem2.motionY += p_82486_0_.rand.nextGaussian() * 0.007499999832361937 * p_82486_2_;
        final EntityItem entityItem3 = var8;
        entityItem3.motionZ += p_82486_0_.rand.nextGaussian() * 0.007499999832361937 * p_82486_2_;
        p_82486_0_.spawnEntityInWorld(var8);
    }
    
    protected void playDispenseSound(final IBlockSource p_82485_1_) {
        p_82485_1_.getWorld().playAuxSFX(1000, p_82485_1_.getXInt(), p_82485_1_.getYInt(), p_82485_1_.getZInt(), 0);
    }
    
    protected void spawnDispenseParticles(final IBlockSource p_82489_1_, final EnumFacing p_82489_2_) {
        p_82489_1_.getWorld().playAuxSFX(2000, p_82489_1_.getXInt(), p_82489_1_.getYInt(), p_82489_1_.getZInt(), this.func_82488_a(p_82489_2_));
    }
    
    private int func_82488_a(final EnumFacing p_82488_1_) {
        return p_82488_1_.getFrontOffsetX() + 1 + (p_82488_1_.getFrontOffsetZ() + 1) * 3;
    }
}
