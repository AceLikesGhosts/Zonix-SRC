package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import net.minecraft.world.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.tileentity.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.item.*;
import net.minecraft.nbt.*;
import net.minecraft.inventory.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.init.*;

public class BlockHopper extends BlockContainer
{
    private final Random field_149922_a;
    private IIcon field_149921_b;
    private IIcon field_149923_M;
    private IIcon field_149924_N;
    private static final String __OBFID = "CL_00000257";
    
    public BlockHopper() {
        super(Material.iron);
        this.field_149922_a = new Random();
        this.setCreativeTab(CreativeTabs.tabRedstone);
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess p_149719_1_, final int p_149719_2_, final int p_149719_3_, final int p_149719_4_) {
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
    }
    
    @Override
    public void addCollisionBoxesToList(final World p_149743_1_, final int p_149743_2_, final int p_149743_3_, final int p_149743_4_, final AxisAlignedBB p_149743_5_, final List p_149743_6_, final Entity p_149743_7_) {
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.625f, 1.0f);
        super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_);
        final float var8 = 0.125f;
        this.setBlockBounds(0.0f, 0.0f, 0.0f, var8, 1.0f, 1.0f);
        super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_);
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, var8);
        super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_);
        this.setBlockBounds(1.0f - var8, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_);
        this.setBlockBounds(0.0f, 0.0f, 1.0f - var8, 1.0f, 1.0f, 1.0f);
        super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_);
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
    }
    
    @Override
    public int onBlockPlaced(final World p_149660_1_, final int p_149660_2_, final int p_149660_3_, final int p_149660_4_, final int p_149660_5_, final float p_149660_6_, final float p_149660_7_, final float p_149660_8_, final int p_149660_9_) {
        int var10 = Facing.oppositeSide[p_149660_5_];
        if (var10 == 1) {
            var10 = 0;
        }
        return var10;
    }
    
    @Override
    public TileEntity createNewTileEntity(final World p_149915_1_, final int p_149915_2_) {
        return new TileEntityHopper();
    }
    
    @Override
    public void onBlockPlacedBy(final World p_149689_1_, final int p_149689_2_, final int p_149689_3_, final int p_149689_4_, final EntityLivingBase p_149689_5_, final ItemStack p_149689_6_) {
        super.onBlockPlacedBy(p_149689_1_, p_149689_2_, p_149689_3_, p_149689_4_, p_149689_5_, p_149689_6_);
        if (p_149689_6_.hasDisplayName()) {
            final TileEntityHopper var7 = func_149920_e(p_149689_1_, p_149689_2_, p_149689_3_, p_149689_4_);
            var7.func_145886_a(p_149689_6_.getDisplayName());
        }
    }
    
    @Override
    public void onBlockAdded(final World p_149726_1_, final int p_149726_2_, final int p_149726_3_, final int p_149726_4_) {
        super.onBlockAdded(p_149726_1_, p_149726_2_, p_149726_3_, p_149726_4_);
        this.func_149919_e(p_149726_1_, p_149726_2_, p_149726_3_, p_149726_4_);
    }
    
    @Override
    public boolean onBlockActivated(final World p_149727_1_, final int p_149727_2_, final int p_149727_3_, final int p_149727_4_, final EntityPlayer p_149727_5_, final int p_149727_6_, final float p_149727_7_, final float p_149727_8_, final float p_149727_9_) {
        if (p_149727_1_.isClient) {
            return true;
        }
        final TileEntityHopper var10 = func_149920_e(p_149727_1_, p_149727_2_, p_149727_3_, p_149727_4_);
        if (var10 != null) {
            p_149727_5_.func_146093_a(var10);
        }
        return true;
    }
    
    @Override
    public void onNeighborBlockChange(final World p_149695_1_, final int p_149695_2_, final int p_149695_3_, final int p_149695_4_, final Block p_149695_5_) {
        this.func_149919_e(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_);
    }
    
    private void func_149919_e(final World p_149919_1_, final int p_149919_2_, final int p_149919_3_, final int p_149919_4_) {
        final int var5 = p_149919_1_.getBlockMetadata(p_149919_2_, p_149919_3_, p_149919_4_);
        final int var6 = func_149918_b(var5);
        final boolean var7 = !p_149919_1_.isBlockIndirectlyGettingPowered(p_149919_2_, p_149919_3_, p_149919_4_);
        final boolean var8 = func_149917_c(var5);
        if (var7 != var8) {
            p_149919_1_.setBlockMetadataWithNotify(p_149919_2_, p_149919_3_, p_149919_4_, var6 | (var7 ? 0 : 8), 4);
        }
    }
    
    @Override
    public void breakBlock(final World p_149749_1_, final int p_149749_2_, final int p_149749_3_, final int p_149749_4_, final Block p_149749_5_, final int p_149749_6_) {
        final TileEntityHopper var7 = (TileEntityHopper)p_149749_1_.getTileEntity(p_149749_2_, p_149749_3_, p_149749_4_);
        if (var7 != null) {
            for (int var8 = 0; var8 < var7.getSizeInventory(); ++var8) {
                final ItemStack var9 = var7.getStackInSlot(var8);
                if (var9 != null) {
                    final float var10 = this.field_149922_a.nextFloat() * 0.8f + 0.1f;
                    final float var11 = this.field_149922_a.nextFloat() * 0.8f + 0.1f;
                    final float var12 = this.field_149922_a.nextFloat() * 0.8f + 0.1f;
                    while (var9.stackSize > 0) {
                        int var13 = this.field_149922_a.nextInt(21) + 10;
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
                        var14.motionX = (float)this.field_149922_a.nextGaussian() * var15;
                        var14.motionY = (float)this.field_149922_a.nextGaussian() * var15 + 0.2f;
                        var14.motionZ = (float)this.field_149922_a.nextGaussian() * var15;
                        p_149749_1_.spawnEntityInWorld(var14);
                    }
                }
            }
            p_149749_1_.func_147453_f(p_149749_2_, p_149749_3_, p_149749_4_, p_149749_5_);
        }
        super.breakBlock(p_149749_1_, p_149749_2_, p_149749_3_, p_149749_4_, p_149749_5_, p_149749_6_);
    }
    
    @Override
    public int getRenderType() {
        return 38;
    }
    
    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }
    
    @Override
    public boolean isOpaqueCube() {
        return false;
    }
    
    @Override
    public boolean shouldSideBeRendered(final IBlockAccess p_149646_1_, final int p_149646_2_, final int p_149646_3_, final int p_149646_4_, final int p_149646_5_) {
        return true;
    }
    
    @Override
    public IIcon getIcon(final int p_149691_1_, final int p_149691_2_) {
        return (p_149691_1_ == 1) ? this.field_149923_M : this.field_149921_b;
    }
    
    public static int func_149918_b(final int p_149918_0_) {
        return p_149918_0_ & 0x7;
    }
    
    public static boolean func_149917_c(final int p_149917_0_) {
        return (p_149917_0_ & 0x8) != 0x8;
    }
    
    @Override
    public boolean hasComparatorInputOverride() {
        return true;
    }
    
    @Override
    public int getComparatorInputOverride(final World p_149736_1_, final int p_149736_2_, final int p_149736_3_, final int p_149736_4_, final int p_149736_5_) {
        return Container.calcRedstoneFromInventory(func_149920_e(p_149736_1_, p_149736_2_, p_149736_3_, p_149736_4_));
    }
    
    @Override
    public void registerBlockIcons(final IIconRegister p_149651_1_) {
        this.field_149921_b = p_149651_1_.registerIcon("hopper_outside");
        this.field_149923_M = p_149651_1_.registerIcon("hopper_top");
        this.field_149924_N = p_149651_1_.registerIcon("hopper_inside");
    }
    
    public static IIcon func_149916_e(final String p_149916_0_) {
        return p_149916_0_.equals("hopper_outside") ? Blocks.hopper.field_149921_b : (p_149916_0_.equals("hopper_inside") ? Blocks.hopper.field_149924_N : null);
    }
    
    @Override
    public String getItemIconName() {
        return "hopper";
    }
    
    public static TileEntityHopper func_149920_e(final IBlockAccess p_149920_0_, final int p_149920_1_, final int p_149920_2_, final int p_149920_3_) {
        return (TileEntityHopper)p_149920_0_.getTileEntity(p_149920_1_, p_149920_2_, p_149920_3_);
    }
}
