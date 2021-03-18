package net.minecraft.dispenser;

import net.minecraft.item.*;
import net.minecraft.block.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;

public abstract class BehaviorProjectileDispense extends BehaviorDefaultDispenseItem
{
    private static final String __OBFID = "CL_00001394";
    
    public ItemStack dispenseStack(final IBlockSource p_82487_1_, final ItemStack p_82487_2_) {
        final World var3 = p_82487_1_.getWorld();
        final IPosition var4 = BlockDispenser.func_149939_a(p_82487_1_);
        final EnumFacing var5 = BlockDispenser.func_149937_b(p_82487_1_.getBlockMetadata());
        final IProjectile var6 = this.getProjectileEntity(var3, var4);
        var6.setThrowableHeading(var5.getFrontOffsetX(), var5.getFrontOffsetY() + 0.1f, var5.getFrontOffsetZ(), this.func_82500_b(), this.func_82498_a());
        var3.spawnEntityInWorld((Entity)var6);
        p_82487_2_.splitStack(1);
        return p_82487_2_;
    }
    
    @Override
    protected void playDispenseSound(final IBlockSource p_82485_1_) {
        p_82485_1_.getWorld().playAuxSFX(1002, p_82485_1_.getXInt(), p_82485_1_.getYInt(), p_82485_1_.getZInt(), 0);
    }
    
    protected abstract IProjectile getProjectileEntity(final World p0, final IPosition p1);
    
    protected float func_82498_a() {
        return 6.0f;
    }
    
    protected float func_82500_b() {
        return 1.1f;
    }
}
