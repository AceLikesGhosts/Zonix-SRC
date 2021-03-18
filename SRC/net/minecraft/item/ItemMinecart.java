package net.minecraft.item;

import net.minecraft.creativetab.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.*;
import net.minecraft.dispenser.*;
import net.minecraft.block.material.*;
import net.minecraft.util.*;
import net.minecraft.block.*;

public class ItemMinecart extends Item
{
    private static final IBehaviorDispenseItem dispenserMinecartBehavior;
    public int minecartType;
    private static final String __OBFID = "CL_00000049";
    
    public ItemMinecart(final int p_i45345_1_) {
        this.maxStackSize = 1;
        this.minecartType = p_i45345_1_;
        this.setCreativeTab(CreativeTabs.tabTransport);
        BlockDispenser.dispenseBehaviorRegistry.putObject(this, ItemMinecart.dispenserMinecartBehavior);
    }
    
    @Override
    public boolean onItemUse(final ItemStack p_77648_1_, final EntityPlayer p_77648_2_, final World p_77648_3_, final int p_77648_4_, final int p_77648_5_, final int p_77648_6_, final int p_77648_7_, final float p_77648_8_, final float p_77648_9_, final float p_77648_10_) {
        if (BlockRailBase.func_150051_a(p_77648_3_.getBlock(p_77648_4_, p_77648_5_, p_77648_6_))) {
            if (!p_77648_3_.isClient) {
                final EntityMinecart var11 = EntityMinecart.createMinecart(p_77648_3_, p_77648_4_ + 0.5f, p_77648_5_ + 0.5f, p_77648_6_ + 0.5f, this.minecartType);
                if (p_77648_1_.hasDisplayName()) {
                    var11.setMinecartName(p_77648_1_.getDisplayName());
                }
                p_77648_3_.spawnEntityInWorld(var11);
            }
            --p_77648_1_.stackSize;
            return true;
        }
        return false;
    }
    
    static {
        dispenserMinecartBehavior = new BehaviorDefaultDispenseItem() {
            private final BehaviorDefaultDispenseItem behaviourDefaultDispenseItem = new BehaviorDefaultDispenseItem();
            private static final String __OBFID = "CL_00000050";
            
            public ItemStack dispenseStack(final IBlockSource p_82487_1_, final ItemStack p_82487_2_) {
                final EnumFacing var3 = BlockDispenser.func_149937_b(p_82487_1_.getBlockMetadata());
                final World var4 = p_82487_1_.getWorld();
                final double var5 = p_82487_1_.getX() + var3.getFrontOffsetX() * 1.125f;
                final double var6 = p_82487_1_.getY() + var3.getFrontOffsetY() * 1.125f;
                final double var7 = p_82487_1_.getZ() + var3.getFrontOffsetZ() * 1.125f;
                final int var8 = p_82487_1_.getXInt() + var3.getFrontOffsetX();
                final int var9 = p_82487_1_.getYInt() + var3.getFrontOffsetY();
                final int var10 = p_82487_1_.getZInt() + var3.getFrontOffsetZ();
                final Block var11 = var4.getBlock(var8, var9, var10);
                double var12;
                if (BlockRailBase.func_150051_a(var11)) {
                    var12 = 0.0;
                }
                else {
                    if (var11.getMaterial() != Material.air || !BlockRailBase.func_150051_a(var4.getBlock(var8, var9 - 1, var10))) {
                        return this.behaviourDefaultDispenseItem.dispense(p_82487_1_, p_82487_2_);
                    }
                    var12 = -1.0;
                }
                final EntityMinecart var13 = EntityMinecart.createMinecart(var4, var5, var6 + var12, var7, ((ItemMinecart)p_82487_2_.getItem()).minecartType);
                if (p_82487_2_.hasDisplayName()) {
                    var13.setMinecartName(p_82487_2_.getDisplayName());
                }
                var4.spawnEntityInWorld(var13);
                p_82487_2_.splitStack(1);
                return p_82487_2_;
            }
            
            @Override
            protected void playDispenseSound(final IBlockSource p_82485_1_) {
                p_82485_1_.getWorld().playAuxSFX(1000, p_82485_1_.getXInt(), p_82485_1_.getYInt(), p_82485_1_.getZInt(), 0);
            }
        };
    }
}
