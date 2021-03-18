package net.minecraft.block;

import java.util.*;
import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import net.minecraft.world.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.tileentity.*;
import net.minecraft.entity.item.*;
import net.minecraft.nbt.*;
import net.minecraft.entity.*;
import net.minecraft.inventory.*;
import net.minecraft.dispenser.*;
import net.minecraft.util.*;

public class BlockDispenser extends BlockContainer
{
    public static final IRegistry dispenseBehaviorRegistry;
    protected Random field_149942_b;
    protected IIcon field_149944_M;
    protected IIcon field_149945_N;
    protected IIcon field_149946_O;
    private static final String __OBFID = "CL_00000229";
    
    protected BlockDispenser() {
        super(Material.rock);
        this.field_149942_b = new Random();
        this.setCreativeTab(CreativeTabs.tabRedstone);
    }
    
    @Override
    public int func_149738_a(final World p_149738_1_) {
        return 4;
    }
    
    @Override
    public void onBlockAdded(final World p_149726_1_, final int p_149726_2_, final int p_149726_3_, final int p_149726_4_) {
        super.onBlockAdded(p_149726_1_, p_149726_2_, p_149726_3_, p_149726_4_);
        this.func_149938_m(p_149726_1_, p_149726_2_, p_149726_3_, p_149726_4_);
    }
    
    private void func_149938_m(final World p_149938_1_, final int p_149938_2_, final int p_149938_3_, final int p_149938_4_) {
        if (!p_149938_1_.isClient) {
            final Block var5 = p_149938_1_.getBlock(p_149938_2_, p_149938_3_, p_149938_4_ - 1);
            final Block var6 = p_149938_1_.getBlock(p_149938_2_, p_149938_3_, p_149938_4_ + 1);
            final Block var7 = p_149938_1_.getBlock(p_149938_2_ - 1, p_149938_3_, p_149938_4_);
            final Block var8 = p_149938_1_.getBlock(p_149938_2_ + 1, p_149938_3_, p_149938_4_);
            byte var9 = 3;
            if (var5.func_149730_j() && !var6.func_149730_j()) {
                var9 = 3;
            }
            if (var6.func_149730_j() && !var5.func_149730_j()) {
                var9 = 2;
            }
            if (var7.func_149730_j() && !var8.func_149730_j()) {
                var9 = 5;
            }
            if (var8.func_149730_j() && !var7.func_149730_j()) {
                var9 = 4;
            }
            p_149938_1_.setBlockMetadataWithNotify(p_149938_2_, p_149938_3_, p_149938_4_, var9, 2);
        }
    }
    
    @Override
    public IIcon getIcon(final int p_149691_1_, final int p_149691_2_) {
        final int var3 = p_149691_2_ & 0x7;
        return (p_149691_1_ == var3) ? ((var3 != 1 && var3 != 0) ? this.field_149945_N : this.field_149946_O) : ((var3 != 1 && var3 != 0) ? ((p_149691_1_ != 1 && p_149691_1_ != 0) ? this.blockIcon : this.field_149944_M) : this.field_149944_M);
    }
    
    @Override
    public void registerBlockIcons(final IIconRegister p_149651_1_) {
        this.blockIcon = p_149651_1_.registerIcon("furnace_side");
        this.field_149944_M = p_149651_1_.registerIcon("furnace_top");
        this.field_149945_N = p_149651_1_.registerIcon(this.getTextureName() + "_front_horizontal");
        this.field_149946_O = p_149651_1_.registerIcon(this.getTextureName() + "_front_vertical");
    }
    
    @Override
    public boolean onBlockActivated(final World p_149727_1_, final int p_149727_2_, final int p_149727_3_, final int p_149727_4_, final EntityPlayer p_149727_5_, final int p_149727_6_, final float p_149727_7_, final float p_149727_8_, final float p_149727_9_) {
        if (p_149727_1_.isClient) {
            return true;
        }
        final TileEntityDispenser var10 = (TileEntityDispenser)p_149727_1_.getTileEntity(p_149727_2_, p_149727_3_, p_149727_4_);
        if (var10 != null) {
            p_149727_5_.func_146102_a(var10);
        }
        return true;
    }
    
    protected void func_149941_e(final World p_149941_1_, final int p_149941_2_, final int p_149941_3_, final int p_149941_4_) {
        final BlockSourceImpl var5 = new BlockSourceImpl(p_149941_1_, p_149941_2_, p_149941_3_, p_149941_4_);
        final TileEntityDispenser var6 = (TileEntityDispenser)var5.getBlockTileEntity();
        if (var6 != null) {
            final int var7 = var6.func_146017_i();
            if (var7 < 0) {
                p_149941_1_.playAuxSFX(1001, p_149941_2_, p_149941_3_, p_149941_4_, 0);
            }
            else {
                final ItemStack var8 = var6.getStackInSlot(var7);
                final IBehaviorDispenseItem var9 = this.func_149940_a(var8);
                if (var9 != IBehaviorDispenseItem.itemDispenseBehaviorProvider) {
                    final ItemStack var10 = var9.dispense(var5, var8);
                    var6.setInventorySlotContents(var7, (var10.stackSize == 0) ? null : var10);
                }
            }
        }
    }
    
    protected IBehaviorDispenseItem func_149940_a(final ItemStack p_149940_1_) {
        return (IBehaviorDispenseItem)BlockDispenser.dispenseBehaviorRegistry.getObject(p_149940_1_.getItem());
    }
    
    @Override
    public void onNeighborBlockChange(final World p_149695_1_, final int p_149695_2_, final int p_149695_3_, final int p_149695_4_, final Block p_149695_5_) {
        final boolean var6 = p_149695_1_.isBlockIndirectlyGettingPowered(p_149695_2_, p_149695_3_, p_149695_4_) || p_149695_1_.isBlockIndirectlyGettingPowered(p_149695_2_, p_149695_3_ + 1, p_149695_4_);
        final int var7 = p_149695_1_.getBlockMetadata(p_149695_2_, p_149695_3_, p_149695_4_);
        final boolean var8 = (var7 & 0x8) != 0x0;
        if (var6 && !var8) {
            p_149695_1_.scheduleBlockUpdate(p_149695_2_, p_149695_3_, p_149695_4_, this, this.func_149738_a(p_149695_1_));
            p_149695_1_.setBlockMetadataWithNotify(p_149695_2_, p_149695_3_, p_149695_4_, var7 | 0x8, 4);
        }
        else if (!var6 && var8) {
            p_149695_1_.setBlockMetadataWithNotify(p_149695_2_, p_149695_3_, p_149695_4_, var7 & 0xFFFFFFF7, 4);
        }
    }
    
    @Override
    public void updateTick(final World p_149674_1_, final int p_149674_2_, final int p_149674_3_, final int p_149674_4_, final Random p_149674_5_) {
        if (!p_149674_1_.isClient) {
            this.func_149941_e(p_149674_1_, p_149674_2_, p_149674_3_, p_149674_4_);
        }
    }
    
    @Override
    public TileEntity createNewTileEntity(final World p_149915_1_, final int p_149915_2_) {
        return new TileEntityDispenser();
    }
    
    @Override
    public void onBlockPlacedBy(final World p_149689_1_, final int p_149689_2_, final int p_149689_3_, final int p_149689_4_, final EntityLivingBase p_149689_5_, final ItemStack p_149689_6_) {
        final int var7 = BlockPistonBase.func_150071_a(p_149689_1_, p_149689_2_, p_149689_3_, p_149689_4_, p_149689_5_);
        p_149689_1_.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_, var7, 2);
        if (p_149689_6_.hasDisplayName()) {
            ((TileEntityDispenser)p_149689_1_.getTileEntity(p_149689_2_, p_149689_3_, p_149689_4_)).func_146018_a(p_149689_6_.getDisplayName());
        }
    }
    
    @Override
    public void breakBlock(final World p_149749_1_, final int p_149749_2_, final int p_149749_3_, final int p_149749_4_, final Block p_149749_5_, final int p_149749_6_) {
        final TileEntityDispenser var7 = (TileEntityDispenser)p_149749_1_.getTileEntity(p_149749_2_, p_149749_3_, p_149749_4_);
        if (var7 != null) {
            for (int var8 = 0; var8 < var7.getSizeInventory(); ++var8) {
                final ItemStack var9 = var7.getStackInSlot(var8);
                if (var9 != null) {
                    final float var10 = this.field_149942_b.nextFloat() * 0.8f + 0.1f;
                    final float var11 = this.field_149942_b.nextFloat() * 0.8f + 0.1f;
                    final float var12 = this.field_149942_b.nextFloat() * 0.8f + 0.1f;
                    while (var9.stackSize > 0) {
                        int var13 = this.field_149942_b.nextInt(21) + 10;
                        if (var13 > var9.stackSize) {
                            var13 = var9.stackSize;
                        }
                        final ItemStack itemStack = var9;
                        itemStack.stackSize -= var13;
                        final EntityItem var14 = new EntityItem(p_149749_1_, p_149749_2_ + var10, p_149749_3_ + var11, p_149749_4_ + var12, new ItemStack(var9.getItem(), var13, var9.getItemDamage()));
                        if (var9.hasTagCompound()) {
                            var14.getEntityItem().setTagCompound((NBTTagCompound)var9.getTagCompound().copy());
                        }
                        final float var15 = 0.05f;
                        var14.motionX = (float)this.field_149942_b.nextGaussian() * var15;
                        var14.motionY = (float)this.field_149942_b.nextGaussian() * var15 + 0.2f;
                        var14.motionZ = (float)this.field_149942_b.nextGaussian() * var15;
                        p_149749_1_.spawnEntityInWorld(var14);
                    }
                }
            }
            p_149749_1_.func_147453_f(p_149749_2_, p_149749_3_, p_149749_4_, p_149749_5_);
        }
        super.breakBlock(p_149749_1_, p_149749_2_, p_149749_3_, p_149749_4_, p_149749_5_, p_149749_6_);
    }
    
    public static IPosition func_149939_a(final IBlockSource p_149939_0_) {
        final EnumFacing var1 = func_149937_b(p_149939_0_.getBlockMetadata());
        final double var2 = p_149939_0_.getX() + 0.7 * var1.getFrontOffsetX();
        final double var3 = p_149939_0_.getY() + 0.7 * var1.getFrontOffsetY();
        final double var4 = p_149939_0_.getZ() + 0.7 * var1.getFrontOffsetZ();
        return new PositionImpl(var2, var3, var4);
    }
    
    public static EnumFacing func_149937_b(final int p_149937_0_) {
        return EnumFacing.getFront(p_149937_0_ & 0x7);
    }
    
    @Override
    public boolean hasComparatorInputOverride() {
        return true;
    }
    
    @Override
    public int getComparatorInputOverride(final World p_149736_1_, final int p_149736_2_, final int p_149736_3_, final int p_149736_4_, final int p_149736_5_) {
        return Container.calcRedstoneFromInventory((IInventory)p_149736_1_.getTileEntity(p_149736_2_, p_149736_3_, p_149736_4_));
    }
    
    static {
        dispenseBehaviorRegistry = new RegistryDefaulted(new BehaviorDefaultDispenseItem());
    }
}
