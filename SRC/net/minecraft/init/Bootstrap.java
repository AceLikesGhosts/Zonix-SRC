package net.minecraft.init;

import net.minecraft.world.*;
import net.minecraft.dispenser.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import net.minecraft.entity.projectile.*;
import java.util.*;
import net.minecraft.block.material.*;
import net.minecraft.tileentity.*;
import net.minecraft.item.*;
import net.minecraft.entity.item.*;
import net.minecraft.block.*;
import net.minecraft.stats.*;

public class Bootstrap
{
    private static boolean field_151355_a;
    private static final String __OBFID = "CL_00001397";
    
    static void func_151353_a() {
        BlockDispenser.dispenseBehaviorRegistry.putObject(Items.arrow, new BehaviorProjectileDispense() {
            private static final String __OBFID = "CL_00001398";
            
            @Override
            protected IProjectile getProjectileEntity(final World p_82499_1_, final IPosition p_82499_2_) {
                final EntityArrow var3 = new EntityArrow(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
                var3.canBePickedUp = 1;
                return var3;
            }
        });
        BlockDispenser.dispenseBehaviorRegistry.putObject(Items.egg, new BehaviorProjectileDispense() {
            private static final String __OBFID = "CL_00001404";
            
            @Override
            protected IProjectile getProjectileEntity(final World p_82499_1_, final IPosition p_82499_2_) {
                return new EntityEgg(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
            }
        });
        BlockDispenser.dispenseBehaviorRegistry.putObject(Items.snowball, new BehaviorProjectileDispense() {
            private static final String __OBFID = "CL_00001405";
            
            @Override
            protected IProjectile getProjectileEntity(final World p_82499_1_, final IPosition p_82499_2_) {
                return new EntitySnowball(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
            }
        });
        BlockDispenser.dispenseBehaviorRegistry.putObject(Items.experience_bottle, new BehaviorProjectileDispense() {
            private static final String __OBFID = "CL_00001406";
            
            @Override
            protected IProjectile getProjectileEntity(final World p_82499_1_, final IPosition p_82499_2_) {
                return new EntityExpBottle(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
            }
            
            @Override
            protected float func_82498_a() {
                return super.func_82498_a() * 0.5f;
            }
            
            @Override
            protected float func_82500_b() {
                return super.func_82500_b() * 1.25f;
            }
        });
        BlockDispenser.dispenseBehaviorRegistry.putObject(Items.potionitem, new IBehaviorDispenseItem() {
            private final BehaviorDefaultDispenseItem field_150843_b = new BehaviorDefaultDispenseItem();
            private static final String __OBFID = "CL_00001407";
            
            @Override
            public ItemStack dispense(final IBlockSource p_82482_1_, final ItemStack p_82482_2_) {
                return ItemPotion.isSplash(p_82482_2_.getItemDamage()) ? new BehaviorProjectileDispense() {
                    private static final String __OBFID = "CL_00001408";
                    
                    @Override
                    protected IProjectile getProjectileEntity(final World p_82499_1_, final IPosition p_82499_2_) {
                        return new EntityPotion(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ(), p_82482_2_.copy());
                    }
                    
                    @Override
                    protected float func_82498_a() {
                        return super.func_82498_a() * 0.5f;
                    }
                    
                    @Override
                    protected float func_82500_b() {
                        return super.func_82500_b() * 1.25f;
                    }
                }.dispense(p_82482_1_, p_82482_2_) : this.field_150843_b.dispense(p_82482_1_, p_82482_2_);
            }
        });
        BlockDispenser.dispenseBehaviorRegistry.putObject(Items.spawn_egg, new BehaviorDefaultDispenseItem() {
            private static final String __OBFID = "CL_00001410";
            
            public ItemStack dispenseStack(final IBlockSource p_82487_1_, final ItemStack p_82487_2_) {
                final EnumFacing var3 = BlockDispenser.func_149937_b(p_82487_1_.getBlockMetadata());
                final double var4 = p_82487_1_.getX() + var3.getFrontOffsetX();
                final double var5 = p_82487_1_.getYInt() + 0.2f;
                final double var6 = p_82487_1_.getZ() + var3.getFrontOffsetZ();
                final Entity var7 = ItemMonsterPlacer.spawnCreature(p_82487_1_.getWorld(), p_82487_2_.getItemDamage(), var4, var5, var6);
                if (var7 instanceof EntityLivingBase && p_82487_2_.hasDisplayName()) {
                    ((EntityLiving)var7).setCustomNameTag(p_82487_2_.getDisplayName());
                }
                p_82487_2_.splitStack(1);
                return p_82487_2_;
            }
        });
        BlockDispenser.dispenseBehaviorRegistry.putObject(Items.fireworks, new BehaviorDefaultDispenseItem() {
            private static final String __OBFID = "CL_00001411";
            
            public ItemStack dispenseStack(final IBlockSource p_82487_1_, final ItemStack p_82487_2_) {
                final EnumFacing var3 = BlockDispenser.func_149937_b(p_82487_1_.getBlockMetadata());
                final double var4 = p_82487_1_.getX() + var3.getFrontOffsetX();
                final double var5 = p_82487_1_.getYInt() + 0.2f;
                final double var6 = p_82487_1_.getZ() + var3.getFrontOffsetZ();
                final EntityFireworkRocket var7 = new EntityFireworkRocket(p_82487_1_.getWorld(), var4, var5, var6, p_82487_2_);
                p_82487_1_.getWorld().spawnEntityInWorld(var7);
                p_82487_2_.splitStack(1);
                return p_82487_2_;
            }
            
            @Override
            protected void playDispenseSound(final IBlockSource p_82485_1_) {
                p_82485_1_.getWorld().playAuxSFX(1002, p_82485_1_.getXInt(), p_82485_1_.getYInt(), p_82485_1_.getZInt(), 0);
            }
        });
        BlockDispenser.dispenseBehaviorRegistry.putObject(Items.fire_charge, new BehaviorDefaultDispenseItem() {
            private static final String __OBFID = "CL_00001412";
            
            public ItemStack dispenseStack(final IBlockSource p_82487_1_, final ItemStack p_82487_2_) {
                final EnumFacing var3 = BlockDispenser.func_149937_b(p_82487_1_.getBlockMetadata());
                final IPosition var4 = BlockDispenser.func_149939_a(p_82487_1_);
                final double var5 = var4.getX() + var3.getFrontOffsetX() * 0.3f;
                final double var6 = var4.getY() + var3.getFrontOffsetX() * 0.3f;
                final double var7 = var4.getZ() + var3.getFrontOffsetZ() * 0.3f;
                final World var8 = p_82487_1_.getWorld();
                final Random var9 = var8.rand;
                final double var10 = var9.nextGaussian() * 0.05 + var3.getFrontOffsetX();
                final double var11 = var9.nextGaussian() * 0.05 + var3.getFrontOffsetY();
                final double var12 = var9.nextGaussian() * 0.05 + var3.getFrontOffsetZ();
                var8.spawnEntityInWorld(new EntitySmallFireball(var8, var5, var6, var7, var10, var11, var12));
                p_82487_2_.splitStack(1);
                return p_82487_2_;
            }
            
            @Override
            protected void playDispenseSound(final IBlockSource p_82485_1_) {
                p_82485_1_.getWorld().playAuxSFX(1009, p_82485_1_.getXInt(), p_82485_1_.getYInt(), p_82485_1_.getZInt(), 0);
            }
        });
        BlockDispenser.dispenseBehaviorRegistry.putObject(Items.boat, new BehaviorDefaultDispenseItem() {
            private final BehaviorDefaultDispenseItem field_150842_b = new BehaviorDefaultDispenseItem();
            private static final String __OBFID = "CL_00001413";
            
            public ItemStack dispenseStack(final IBlockSource p_82487_1_, final ItemStack p_82487_2_) {
                final EnumFacing var3 = BlockDispenser.func_149937_b(p_82487_1_.getBlockMetadata());
                final World var4 = p_82487_1_.getWorld();
                final double var5 = p_82487_1_.getX() + var3.getFrontOffsetX() * 1.125f;
                final double var6 = p_82487_1_.getY() + var3.getFrontOffsetY() * 1.125f;
                final double var7 = p_82487_1_.getZ() + var3.getFrontOffsetZ() * 1.125f;
                final int var8 = p_82487_1_.getXInt() + var3.getFrontOffsetX();
                final int var9 = p_82487_1_.getYInt() + var3.getFrontOffsetY();
                final int var10 = p_82487_1_.getZInt() + var3.getFrontOffsetZ();
                final Material var11 = var4.getBlock(var8, var9, var10).getMaterial();
                double var12;
                if (Material.water.equals(var11)) {
                    var12 = 1.0;
                }
                else {
                    if (!Material.air.equals(var11) || !Material.water.equals(var4.getBlock(var8, var9 - 1, var10).getMaterial())) {
                        return this.field_150842_b.dispense(p_82487_1_, p_82487_2_);
                    }
                    var12 = 0.0;
                }
                final EntityBoat var13 = new EntityBoat(var4, var5, var6 + var12, var7);
                var4.spawnEntityInWorld(var13);
                p_82487_2_.splitStack(1);
                return p_82487_2_;
            }
            
            @Override
            protected void playDispenseSound(final IBlockSource p_82485_1_) {
                p_82485_1_.getWorld().playAuxSFX(1000, p_82485_1_.getXInt(), p_82485_1_.getYInt(), p_82485_1_.getZInt(), 0);
            }
        });
        final BehaviorDefaultDispenseItem var0 = new BehaviorDefaultDispenseItem() {
            private final BehaviorDefaultDispenseItem field_150841_b = new BehaviorDefaultDispenseItem();
            private static final String __OBFID = "CL_00001399";
            
            public ItemStack dispenseStack(final IBlockSource p_82487_1_, final ItemStack p_82487_2_) {
                final ItemBucket var3 = (ItemBucket)p_82487_2_.getItem();
                final int var4 = p_82487_1_.getXInt();
                final int var5 = p_82487_1_.getYInt();
                final int var6 = p_82487_1_.getZInt();
                final EnumFacing var7 = BlockDispenser.func_149937_b(p_82487_1_.getBlockMetadata());
                if (var3.tryPlaceContainedLiquid(p_82487_1_.getWorld(), var4 + var7.getFrontOffsetX(), var5 + var7.getFrontOffsetY(), var6 + var7.getFrontOffsetZ())) {
                    p_82487_2_.func_150996_a(Items.bucket);
                    p_82487_2_.stackSize = 1;
                    return p_82487_2_;
                }
                return this.field_150841_b.dispense(p_82487_1_, p_82487_2_);
            }
        };
        BlockDispenser.dispenseBehaviorRegistry.putObject(Items.lava_bucket, var0);
        BlockDispenser.dispenseBehaviorRegistry.putObject(Items.water_bucket, var0);
        BlockDispenser.dispenseBehaviorRegistry.putObject(Items.bucket, new BehaviorDefaultDispenseItem() {
            private final BehaviorDefaultDispenseItem field_150840_b = new BehaviorDefaultDispenseItem();
            private static final String __OBFID = "CL_00001400";
            
            public ItemStack dispenseStack(final IBlockSource p_82487_1_, final ItemStack p_82487_2_) {
                final EnumFacing var3 = BlockDispenser.func_149937_b(p_82487_1_.getBlockMetadata());
                final World var4 = p_82487_1_.getWorld();
                final int var5 = p_82487_1_.getXInt() + var3.getFrontOffsetX();
                final int var6 = p_82487_1_.getYInt() + var3.getFrontOffsetY();
                final int var7 = p_82487_1_.getZInt() + var3.getFrontOffsetZ();
                final Material var8 = var4.getBlock(var5, var6, var7).getMaterial();
                final int var9 = var4.getBlockMetadata(var5, var6, var7);
                Item var10;
                if (Material.water.equals(var8) && var9 == 0) {
                    var10 = Items.water_bucket;
                }
                else {
                    if (!Material.lava.equals(var8) || var9 != 0) {
                        return super.dispenseStack(p_82487_1_, p_82487_2_);
                    }
                    var10 = Items.lava_bucket;
                }
                var4.setBlockToAir(var5, var6, var7);
                final int stackSize = p_82487_2_.stackSize - 1;
                p_82487_2_.stackSize = stackSize;
                if (stackSize == 0) {
                    p_82487_2_.func_150996_a(var10);
                    p_82487_2_.stackSize = 1;
                }
                else if (((TileEntityDispenser)p_82487_1_.getBlockTileEntity()).func_146019_a(new ItemStack(var10)) < 0) {
                    this.field_150840_b.dispense(p_82487_1_, new ItemStack(var10));
                }
                return p_82487_2_;
            }
        });
        BlockDispenser.dispenseBehaviorRegistry.putObject(Items.flint_and_steel, new BehaviorDefaultDispenseItem() {
            private boolean field_150839_b = true;
            private static final String __OBFID = "CL_00001401";
            
            @Override
            protected ItemStack dispenseStack(final IBlockSource p_82487_1_, final ItemStack p_82487_2_) {
                final EnumFacing var3 = BlockDispenser.func_149937_b(p_82487_1_.getBlockMetadata());
                final World var4 = p_82487_1_.getWorld();
                final int var5 = p_82487_1_.getXInt() + var3.getFrontOffsetX();
                final int var6 = p_82487_1_.getYInt() + var3.getFrontOffsetY();
                final int var7 = p_82487_1_.getZInt() + var3.getFrontOffsetZ();
                if (var4.isAirBlock(var5, var6, var7)) {
                    var4.setBlock(var5, var6, var7, Blocks.fire);
                    if (p_82487_2_.attemptDamageItem(1, var4.rand)) {
                        p_82487_2_.stackSize = 0;
                    }
                }
                else if (var4.getBlock(var5, var6, var7) == Blocks.tnt) {
                    Blocks.tnt.onBlockDestroyedByPlayer(var4, var5, var6, var7, 1);
                    var4.setBlockToAir(var5, var6, var7);
                }
                else {
                    this.field_150839_b = false;
                }
                return p_82487_2_;
            }
            
            @Override
            protected void playDispenseSound(final IBlockSource p_82485_1_) {
                if (this.field_150839_b) {
                    p_82485_1_.getWorld().playAuxSFX(1000, p_82485_1_.getXInt(), p_82485_1_.getYInt(), p_82485_1_.getZInt(), 0);
                }
                else {
                    p_82485_1_.getWorld().playAuxSFX(1001, p_82485_1_.getXInt(), p_82485_1_.getYInt(), p_82485_1_.getZInt(), 0);
                }
            }
        });
        BlockDispenser.dispenseBehaviorRegistry.putObject(Items.dye, new BehaviorDefaultDispenseItem() {
            private boolean field_150838_b = true;
            private static final String __OBFID = "CL_00001402";
            
            @Override
            protected ItemStack dispenseStack(final IBlockSource p_82487_1_, final ItemStack p_82487_2_) {
                if (p_82487_2_.getItemDamage() == 15) {
                    final EnumFacing var3 = BlockDispenser.func_149937_b(p_82487_1_.getBlockMetadata());
                    final World var4 = p_82487_1_.getWorld();
                    final int var5 = p_82487_1_.getXInt() + var3.getFrontOffsetX();
                    final int var6 = p_82487_1_.getYInt() + var3.getFrontOffsetY();
                    final int var7 = p_82487_1_.getZInt() + var3.getFrontOffsetZ();
                    if (ItemDye.func_150919_a(p_82487_2_, var4, var5, var6, var7)) {
                        if (!var4.isClient) {
                            var4.playAuxSFX(2005, var5, var6, var7, 0);
                        }
                    }
                    else {
                        this.field_150838_b = false;
                    }
                    return p_82487_2_;
                }
                return super.dispenseStack(p_82487_1_, p_82487_2_);
            }
            
            @Override
            protected void playDispenseSound(final IBlockSource p_82485_1_) {
                if (this.field_150838_b) {
                    p_82485_1_.getWorld().playAuxSFX(1000, p_82485_1_.getXInt(), p_82485_1_.getYInt(), p_82485_1_.getZInt(), 0);
                }
                else {
                    p_82485_1_.getWorld().playAuxSFX(1001, p_82485_1_.getXInt(), p_82485_1_.getYInt(), p_82485_1_.getZInt(), 0);
                }
            }
        });
        BlockDispenser.dispenseBehaviorRegistry.putObject(Item.getItemFromBlock(Blocks.tnt), new BehaviorDefaultDispenseItem() {
            private static final String __OBFID = "CL_00001403";
            
            @Override
            protected ItemStack dispenseStack(final IBlockSource p_82487_1_, final ItemStack p_82487_2_) {
                final EnumFacing var3 = BlockDispenser.func_149937_b(p_82487_1_.getBlockMetadata());
                final World var4 = p_82487_1_.getWorld();
                final int var5 = p_82487_1_.getXInt() + var3.getFrontOffsetX();
                final int var6 = p_82487_1_.getYInt() + var3.getFrontOffsetY();
                final int var7 = p_82487_1_.getZInt() + var3.getFrontOffsetZ();
                final EntityTNTPrimed var8 = new EntityTNTPrimed(var4, var5 + 0.5f, var6 + 0.5f, var7 + 0.5f, null);
                var4.spawnEntityInWorld(var8);
                --p_82487_2_.stackSize;
                return p_82487_2_;
            }
        });
    }
    
    public static void func_151354_b() {
        if (!Bootstrap.field_151355_a) {
            Bootstrap.field_151355_a = true;
            Block.registerBlocks();
            BlockFire.func_149843_e();
            Item.registerItems();
            StatList.func_151178_a();
            func_151353_a();
        }
    }
    
    static {
        Bootstrap.field_151355_a = false;
    }
}
