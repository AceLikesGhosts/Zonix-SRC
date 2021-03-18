package net.minecraft.block;

import net.minecraft.block.material.*;
import java.util.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.client.renderer.texture.*;

public class BlockCocoa extends BlockDirectional implements IGrowable
{
    private IIcon[] field_149989_a;
    private static final String __OBFID = "CL_00000216";
    
    public BlockCocoa() {
        super(Material.plants);
        this.setTickRandomly(true);
    }
    
    @Override
    public IIcon getIcon(final int p_149691_1_, final int p_149691_2_) {
        return this.field_149989_a[2];
    }
    
    public IIcon func_149988_b(int p_149988_1_) {
        if (p_149988_1_ < 0 || p_149988_1_ >= this.field_149989_a.length) {
            p_149988_1_ = this.field_149989_a.length - 1;
        }
        return this.field_149989_a[p_149988_1_];
    }
    
    @Override
    public void updateTick(final World p_149674_1_, final int p_149674_2_, final int p_149674_3_, final int p_149674_4_, final Random p_149674_5_) {
        if (!this.canBlockStay(p_149674_1_, p_149674_2_, p_149674_3_, p_149674_4_)) {
            this.dropBlockAsItem(p_149674_1_, p_149674_2_, p_149674_3_, p_149674_4_, p_149674_1_.getBlockMetadata(p_149674_2_, p_149674_3_, p_149674_4_), 0);
            p_149674_1_.setBlock(p_149674_2_, p_149674_3_, p_149674_4_, Block.getBlockById(0), 0, 2);
        }
        else if (p_149674_1_.rand.nextInt(5) == 0) {
            final int var6 = p_149674_1_.getBlockMetadata(p_149674_2_, p_149674_3_, p_149674_4_);
            int var7 = func_149987_c(var6);
            if (var7 < 2) {
                ++var7;
                p_149674_1_.setBlockMetadataWithNotify(p_149674_2_, p_149674_3_, p_149674_4_, var7 << 2 | BlockDirectional.func_149895_l(var6), 2);
            }
        }
    }
    
    @Override
    public boolean canBlockStay(final World p_149718_1_, int p_149718_2_, final int p_149718_3_, int p_149718_4_) {
        final int var5 = BlockDirectional.func_149895_l(p_149718_1_.getBlockMetadata(p_149718_2_, p_149718_3_, p_149718_4_));
        p_149718_2_ += Direction.offsetX[var5];
        p_149718_4_ += Direction.offsetZ[var5];
        final Block var6 = p_149718_1_.getBlock(p_149718_2_, p_149718_3_, p_149718_4_);
        return var6 == Blocks.log && BlockLog.func_150165_c(p_149718_1_.getBlockMetadata(p_149718_2_, p_149718_3_, p_149718_4_)) == 3;
    }
    
    @Override
    public int getRenderType() {
        return 28;
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
    public AxisAlignedBB getCollisionBoundingBoxFromPool(final World p_149668_1_, final int p_149668_2_, final int p_149668_3_, final int p_149668_4_) {
        this.setBlockBoundsBasedOnState(p_149668_1_, p_149668_2_, p_149668_3_, p_149668_4_);
        return super.getCollisionBoundingBoxFromPool(p_149668_1_, p_149668_2_, p_149668_3_, p_149668_4_);
    }
    
    @Override
    public AxisAlignedBB getSelectedBoundingBoxFromPool(final World p_149633_1_, final int p_149633_2_, final int p_149633_3_, final int p_149633_4_) {
        this.setBlockBoundsBasedOnState(p_149633_1_, p_149633_2_, p_149633_3_, p_149633_4_);
        return super.getSelectedBoundingBoxFromPool(p_149633_1_, p_149633_2_, p_149633_3_, p_149633_4_);
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess p_149719_1_, final int p_149719_2_, final int p_149719_3_, final int p_149719_4_) {
        final int var5 = p_149719_1_.getBlockMetadata(p_149719_2_, p_149719_3_, p_149719_4_);
        final int var6 = BlockDirectional.func_149895_l(var5);
        final int var7 = func_149987_c(var5);
        final int var8 = 4 + var7 * 2;
        final int var9 = 5 + var7 * 2;
        final float var10 = var8 / 2.0f;
        switch (var6) {
            case 0: {
                this.setBlockBounds((8.0f - var10) / 16.0f, (12.0f - var9) / 16.0f, (15.0f - var8) / 16.0f, (8.0f + var10) / 16.0f, 0.75f, 0.9375f);
                break;
            }
            case 1: {
                this.setBlockBounds(0.0625f, (12.0f - var9) / 16.0f, (8.0f - var10) / 16.0f, (1.0f + var8) / 16.0f, 0.75f, (8.0f + var10) / 16.0f);
                break;
            }
            case 2: {
                this.setBlockBounds((8.0f - var10) / 16.0f, (12.0f - var9) / 16.0f, 0.0625f, (8.0f + var10) / 16.0f, 0.75f, (1.0f + var8) / 16.0f);
                break;
            }
            case 3: {
                this.setBlockBounds((15.0f - var8) / 16.0f, (12.0f - var9) / 16.0f, (8.0f - var10) / 16.0f, 0.9375f, 0.75f, (8.0f + var10) / 16.0f);
                break;
            }
        }
    }
    
    @Override
    public void onBlockPlacedBy(final World p_149689_1_, final int p_149689_2_, final int p_149689_3_, final int p_149689_4_, final EntityLivingBase p_149689_5_, final ItemStack p_149689_6_) {
        final int var7 = ((MathHelper.floor_double(p_149689_5_.rotationYaw * 4.0f / 360.0f + 0.5) & 0x3) + 0) % 4;
        p_149689_1_.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_, var7, 2);
    }
    
    @Override
    public int onBlockPlaced(final World p_149660_1_, final int p_149660_2_, final int p_149660_3_, final int p_149660_4_, int p_149660_5_, final float p_149660_6_, final float p_149660_7_, final float p_149660_8_, final int p_149660_9_) {
        if (p_149660_5_ == 1 || p_149660_5_ == 0) {
            p_149660_5_ = 2;
        }
        return Direction.rotateOpposite[Direction.facingToDirection[p_149660_5_]];
    }
    
    @Override
    public void onNeighborBlockChange(final World p_149695_1_, final int p_149695_2_, final int p_149695_3_, final int p_149695_4_, final Block p_149695_5_) {
        if (!this.canBlockStay(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_)) {
            this.dropBlockAsItem(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_, p_149695_1_.getBlockMetadata(p_149695_2_, p_149695_3_, p_149695_4_), 0);
            p_149695_1_.setBlock(p_149695_2_, p_149695_3_, p_149695_4_, Block.getBlockById(0), 0, 2);
        }
    }
    
    public static int func_149987_c(final int p_149987_0_) {
        return (p_149987_0_ & 0xC) >> 2;
    }
    
    @Override
    public void dropBlockAsItemWithChance(final World p_149690_1_, final int p_149690_2_, final int p_149690_3_, final int p_149690_4_, final int p_149690_5_, final float p_149690_6_, final int p_149690_7_) {
        final int var8 = func_149987_c(p_149690_5_);
        byte var9 = 1;
        if (var8 >= 2) {
            var9 = 3;
        }
        for (int var10 = 0; var10 < var9; ++var10) {
            this.dropBlockAsItem_do(p_149690_1_, p_149690_2_, p_149690_3_, p_149690_4_, new ItemStack(Items.dye, 1, 3));
        }
    }
    
    @Override
    public Item getItem(final World p_149694_1_, final int p_149694_2_, final int p_149694_3_, final int p_149694_4_) {
        return Items.dye;
    }
    
    @Override
    public int getDamageValue(final World p_149643_1_, final int p_149643_2_, final int p_149643_3_, final int p_149643_4_) {
        return 3;
    }
    
    @Override
    public void registerBlockIcons(final IIconRegister p_149651_1_) {
        this.field_149989_a = new IIcon[3];
        for (int var2 = 0; var2 < this.field_149989_a.length; ++var2) {
            this.field_149989_a[var2] = p_149651_1_.registerIcon(this.getTextureName() + "_stage_" + var2);
        }
    }
    
    @Override
    public boolean func_149851_a(final World p_149851_1_, final int p_149851_2_, final int p_149851_3_, final int p_149851_4_, final boolean p_149851_5_) {
        final int var6 = p_149851_1_.getBlockMetadata(p_149851_2_, p_149851_3_, p_149851_4_);
        final int var7 = func_149987_c(var6);
        return var7 < 2;
    }
    
    @Override
    public boolean func_149852_a(final World p_149852_1_, final Random p_149852_2_, final int p_149852_3_, final int p_149852_4_, final int p_149852_5_) {
        return true;
    }
    
    @Override
    public void func_149853_b(final World p_149853_1_, final Random p_149853_2_, final int p_149853_3_, final int p_149853_4_, final int p_149853_5_) {
        final int var6 = p_149853_1_.getBlockMetadata(p_149853_3_, p_149853_4_, p_149853_5_);
        final int var7 = BlockDirectional.func_149895_l(var6);
        int var8 = func_149987_c(var6);
        ++var8;
        p_149853_1_.setBlockMetadataWithNotify(p_149853_3_, p_149853_4_, p_149853_5_, var8 << 2 | var7, 2);
    }
}
