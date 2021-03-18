package net.minecraft.block;

import net.minecraft.creativetab.*;
import java.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.init.*;
import net.minecraft.world.*;
import net.minecraft.block.material.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.texture.*;

public class BlockStairs extends Block
{
    private static final int[][] field_150150_a;
    private final Block field_150149_b;
    private final int field_150151_M;
    private boolean field_150152_N;
    private int field_150153_O;
    private static final String __OBFID = "CL_00000314";
    
    protected BlockStairs(final Block p_i45428_1_, final int p_i45428_2_) {
        super(p_i45428_1_.blockMaterial);
        this.field_150149_b = p_i45428_1_;
        this.field_150151_M = p_i45428_2_;
        this.setHardness(p_i45428_1_.blockHardness);
        this.setResistance(p_i45428_1_.blockResistance / 3.0f);
        this.setStepSound(p_i45428_1_.stepSound);
        this.setLightOpacity(255);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess p_149719_1_, final int p_149719_2_, final int p_149719_3_, final int p_149719_4_) {
        if (this.field_150152_N) {
            this.setBlockBounds(0.5f * (this.field_150153_O % 2), 0.5f * (this.field_150153_O / 2 % 2), 0.5f * (this.field_150153_O / 4 % 2), 0.5f + 0.5f * (this.field_150153_O % 2), 0.5f + 0.5f * (this.field_150153_O / 2 % 2), 0.5f + 0.5f * (this.field_150153_O / 4 % 2));
        }
        else {
            this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        }
    }
    
    @Override
    public boolean isOpaqueCube() {
        return false;
    }
    
    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }
    
    @Override
    public int getRenderType() {
        return 10;
    }
    
    public void func_150147_e(final IBlockAccess p_150147_1_, final int p_150147_2_, final int p_150147_3_, final int p_150147_4_) {
        final int var5 = p_150147_1_.getBlockMetadata(p_150147_2_, p_150147_3_, p_150147_4_);
        if ((var5 & 0x4) != 0x0) {
            this.setBlockBounds(0.0f, 0.5f, 0.0f, 1.0f, 1.0f, 1.0f);
        }
        else {
            this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.5f, 1.0f);
        }
    }
    
    public static boolean func_150148_a(final Block p_150148_0_) {
        return p_150148_0_ instanceof BlockStairs;
    }
    
    private boolean func_150146_f(final IBlockAccess p_150146_1_, final int p_150146_2_, final int p_150146_3_, final int p_150146_4_, final int p_150146_5_) {
        final Block var6 = p_150146_1_.getBlock(p_150146_2_, p_150146_3_, p_150146_4_);
        return func_150148_a(var6) && p_150146_1_.getBlockMetadata(p_150146_2_, p_150146_3_, p_150146_4_) == p_150146_5_;
    }
    
    public boolean func_150145_f(final IBlockAccess p_150145_1_, final int p_150145_2_, final int p_150145_3_, final int p_150145_4_) {
        final int var5 = p_150145_1_.getBlockMetadata(p_150145_2_, p_150145_3_, p_150145_4_);
        final int var6 = var5 & 0x3;
        float var7 = 0.5f;
        float var8 = 1.0f;
        if ((var5 & 0x4) != 0x0) {
            var7 = 0.0f;
            var8 = 0.5f;
        }
        float var9 = 0.0f;
        float var10 = 1.0f;
        float var11 = 0.0f;
        float var12 = 0.5f;
        boolean var13 = true;
        if (var6 == 0) {
            var9 = 0.5f;
            var12 = 1.0f;
            final Block var14 = p_150145_1_.getBlock(p_150145_2_ + 1, p_150145_3_, p_150145_4_);
            final int var15 = p_150145_1_.getBlockMetadata(p_150145_2_ + 1, p_150145_3_, p_150145_4_);
            if (func_150148_a(var14) && (var5 & 0x4) == (var15 & 0x4)) {
                final int var16 = var15 & 0x3;
                if (var16 == 3 && !this.func_150146_f(p_150145_1_, p_150145_2_, p_150145_3_, p_150145_4_ + 1, var5)) {
                    var12 = 0.5f;
                    var13 = false;
                }
                else if (var16 == 2 && !this.func_150146_f(p_150145_1_, p_150145_2_, p_150145_3_, p_150145_4_ - 1, var5)) {
                    var11 = 0.5f;
                    var13 = false;
                }
            }
        }
        else if (var6 == 1) {
            var10 = 0.5f;
            var12 = 1.0f;
            final Block var14 = p_150145_1_.getBlock(p_150145_2_ - 1, p_150145_3_, p_150145_4_);
            final int var15 = p_150145_1_.getBlockMetadata(p_150145_2_ - 1, p_150145_3_, p_150145_4_);
            if (func_150148_a(var14) && (var5 & 0x4) == (var15 & 0x4)) {
                final int var16 = var15 & 0x3;
                if (var16 == 3 && !this.func_150146_f(p_150145_1_, p_150145_2_, p_150145_3_, p_150145_4_ + 1, var5)) {
                    var12 = 0.5f;
                    var13 = false;
                }
                else if (var16 == 2 && !this.func_150146_f(p_150145_1_, p_150145_2_, p_150145_3_, p_150145_4_ - 1, var5)) {
                    var11 = 0.5f;
                    var13 = false;
                }
            }
        }
        else if (var6 == 2) {
            var11 = 0.5f;
            var12 = 1.0f;
            final Block var14 = p_150145_1_.getBlock(p_150145_2_, p_150145_3_, p_150145_4_ + 1);
            final int var15 = p_150145_1_.getBlockMetadata(p_150145_2_, p_150145_3_, p_150145_4_ + 1);
            if (func_150148_a(var14) && (var5 & 0x4) == (var15 & 0x4)) {
                final int var16 = var15 & 0x3;
                if (var16 == 1 && !this.func_150146_f(p_150145_1_, p_150145_2_ + 1, p_150145_3_, p_150145_4_, var5)) {
                    var10 = 0.5f;
                    var13 = false;
                }
                else if (var16 == 0 && !this.func_150146_f(p_150145_1_, p_150145_2_ - 1, p_150145_3_, p_150145_4_, var5)) {
                    var9 = 0.5f;
                    var13 = false;
                }
            }
        }
        else if (var6 == 3) {
            final Block var14 = p_150145_1_.getBlock(p_150145_2_, p_150145_3_, p_150145_4_ - 1);
            final int var15 = p_150145_1_.getBlockMetadata(p_150145_2_, p_150145_3_, p_150145_4_ - 1);
            if (func_150148_a(var14) && (var5 & 0x4) == (var15 & 0x4)) {
                final int var16 = var15 & 0x3;
                if (var16 == 1 && !this.func_150146_f(p_150145_1_, p_150145_2_ + 1, p_150145_3_, p_150145_4_, var5)) {
                    var10 = 0.5f;
                    var13 = false;
                }
                else if (var16 == 0 && !this.func_150146_f(p_150145_1_, p_150145_2_ - 1, p_150145_3_, p_150145_4_, var5)) {
                    var9 = 0.5f;
                    var13 = false;
                }
            }
        }
        this.setBlockBounds(var9, var7, var11, var10, var8, var12);
        return var13;
    }
    
    public boolean func_150144_g(final IBlockAccess p_150144_1_, final int p_150144_2_, final int p_150144_3_, final int p_150144_4_) {
        final int var5 = p_150144_1_.getBlockMetadata(p_150144_2_, p_150144_3_, p_150144_4_);
        final int var6 = var5 & 0x3;
        float var7 = 0.5f;
        float var8 = 1.0f;
        if ((var5 & 0x4) != 0x0) {
            var7 = 0.0f;
            var8 = 0.5f;
        }
        float var9 = 0.0f;
        float var10 = 0.5f;
        float var11 = 0.5f;
        float var12 = 1.0f;
        boolean var13 = false;
        if (var6 == 0) {
            final Block var14 = p_150144_1_.getBlock(p_150144_2_ - 1, p_150144_3_, p_150144_4_);
            final int var15 = p_150144_1_.getBlockMetadata(p_150144_2_ - 1, p_150144_3_, p_150144_4_);
            if (func_150148_a(var14) && (var5 & 0x4) == (var15 & 0x4)) {
                final int var16 = var15 & 0x3;
                if (var16 == 3 && !this.func_150146_f(p_150144_1_, p_150144_2_, p_150144_3_, p_150144_4_ - 1, var5)) {
                    var11 = 0.0f;
                    var12 = 0.5f;
                    var13 = true;
                }
                else if (var16 == 2 && !this.func_150146_f(p_150144_1_, p_150144_2_, p_150144_3_, p_150144_4_ + 1, var5)) {
                    var11 = 0.5f;
                    var12 = 1.0f;
                    var13 = true;
                }
            }
        }
        else if (var6 == 1) {
            final Block var14 = p_150144_1_.getBlock(p_150144_2_ + 1, p_150144_3_, p_150144_4_);
            final int var15 = p_150144_1_.getBlockMetadata(p_150144_2_ + 1, p_150144_3_, p_150144_4_);
            if (func_150148_a(var14) && (var5 & 0x4) == (var15 & 0x4)) {
                var9 = 0.5f;
                var10 = 1.0f;
                final int var16 = var15 & 0x3;
                if (var16 == 3 && !this.func_150146_f(p_150144_1_, p_150144_2_, p_150144_3_, p_150144_4_ - 1, var5)) {
                    var11 = 0.0f;
                    var12 = 0.5f;
                    var13 = true;
                }
                else if (var16 == 2 && !this.func_150146_f(p_150144_1_, p_150144_2_, p_150144_3_, p_150144_4_ + 1, var5)) {
                    var11 = 0.5f;
                    var12 = 1.0f;
                    var13 = true;
                }
            }
        }
        else if (var6 == 2) {
            final Block var14 = p_150144_1_.getBlock(p_150144_2_, p_150144_3_, p_150144_4_ - 1);
            final int var15 = p_150144_1_.getBlockMetadata(p_150144_2_, p_150144_3_, p_150144_4_ - 1);
            if (func_150148_a(var14) && (var5 & 0x4) == (var15 & 0x4)) {
                var11 = 0.0f;
                var12 = 0.5f;
                final int var16 = var15 & 0x3;
                if (var16 == 1 && !this.func_150146_f(p_150144_1_, p_150144_2_ - 1, p_150144_3_, p_150144_4_, var5)) {
                    var13 = true;
                }
                else if (var16 == 0 && !this.func_150146_f(p_150144_1_, p_150144_2_ + 1, p_150144_3_, p_150144_4_, var5)) {
                    var9 = 0.5f;
                    var10 = 1.0f;
                    var13 = true;
                }
            }
        }
        else if (var6 == 3) {
            final Block var14 = p_150144_1_.getBlock(p_150144_2_, p_150144_3_, p_150144_4_ + 1);
            final int var15 = p_150144_1_.getBlockMetadata(p_150144_2_, p_150144_3_, p_150144_4_ + 1);
            if (func_150148_a(var14) && (var5 & 0x4) == (var15 & 0x4)) {
                final int var16 = var15 & 0x3;
                if (var16 == 1 && !this.func_150146_f(p_150144_1_, p_150144_2_ - 1, p_150144_3_, p_150144_4_, var5)) {
                    var13 = true;
                }
                else if (var16 == 0 && !this.func_150146_f(p_150144_1_, p_150144_2_ + 1, p_150144_3_, p_150144_4_, var5)) {
                    var9 = 0.5f;
                    var10 = 1.0f;
                    var13 = true;
                }
            }
        }
        if (var13) {
            this.setBlockBounds(var9, var7, var11, var10, var8, var12);
        }
        return var13;
    }
    
    @Override
    public void addCollisionBoxesToList(final World p_149743_1_, final int p_149743_2_, final int p_149743_3_, final int p_149743_4_, final AxisAlignedBB p_149743_5_, final List p_149743_6_, final Entity p_149743_7_) {
        this.func_150147_e(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_);
        super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_);
        final boolean var8 = this.func_150145_f(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_);
        super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_);
        if (var8 && this.func_150144_g(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_)) {
            super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_);
        }
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
    }
    
    @Override
    public void randomDisplayTick(final World p_149734_1_, final int p_149734_2_, final int p_149734_3_, final int p_149734_4_, final Random p_149734_5_) {
        this.field_150149_b.randomDisplayTick(p_149734_1_, p_149734_2_, p_149734_3_, p_149734_4_, p_149734_5_);
    }
    
    @Override
    public void onBlockClicked(final World p_149699_1_, final int p_149699_2_, final int p_149699_3_, final int p_149699_4_, final EntityPlayer p_149699_5_) {
        this.field_150149_b.onBlockClicked(p_149699_1_, p_149699_2_, p_149699_3_, p_149699_4_, p_149699_5_);
    }
    
    @Override
    public void onBlockDestroyedByPlayer(final World p_149664_1_, final int p_149664_2_, final int p_149664_3_, final int p_149664_4_, final int p_149664_5_) {
        this.field_150149_b.onBlockDestroyedByPlayer(p_149664_1_, p_149664_2_, p_149664_3_, p_149664_4_, p_149664_5_);
    }
    
    @Override
    public int getBlockBrightness(final IBlockAccess p_149677_1_, final int p_149677_2_, final int p_149677_3_, final int p_149677_4_) {
        return this.field_150149_b.getBlockBrightness(p_149677_1_, p_149677_2_, p_149677_3_, p_149677_4_);
    }
    
    @Override
    public float getExplosionResistance(final Entity p_149638_1_) {
        return this.field_150149_b.getExplosionResistance(p_149638_1_);
    }
    
    @Override
    public int getRenderBlockPass() {
        return this.field_150149_b.getRenderBlockPass();
    }
    
    @Override
    public IIcon getIcon(final int p_149691_1_, final int p_149691_2_) {
        return this.field_150149_b.getIcon(p_149691_1_, this.field_150151_M);
    }
    
    @Override
    public int func_149738_a(final World p_149738_1_) {
        return this.field_150149_b.func_149738_a(p_149738_1_);
    }
    
    @Override
    public AxisAlignedBB getSelectedBoundingBoxFromPool(final World p_149633_1_, final int p_149633_2_, final int p_149633_3_, final int p_149633_4_) {
        return this.field_150149_b.getSelectedBoundingBoxFromPool(p_149633_1_, p_149633_2_, p_149633_3_, p_149633_4_);
    }
    
    @Override
    public void velocityToAddToEntity(final World p_149640_1_, final int p_149640_2_, final int p_149640_3_, final int p_149640_4_, final Entity p_149640_5_, final Vec3 p_149640_6_) {
        this.field_150149_b.velocityToAddToEntity(p_149640_1_, p_149640_2_, p_149640_3_, p_149640_4_, p_149640_5_, p_149640_6_);
    }
    
    @Override
    public boolean isCollidable() {
        return this.field_150149_b.isCollidable();
    }
    
    @Override
    public boolean canCollideCheck(final int p_149678_1_, final boolean p_149678_2_) {
        return this.field_150149_b.canCollideCheck(p_149678_1_, p_149678_2_);
    }
    
    @Override
    public boolean canPlaceBlockAt(final World p_149742_1_, final int p_149742_2_, final int p_149742_3_, final int p_149742_4_) {
        return this.field_150149_b.canPlaceBlockAt(p_149742_1_, p_149742_2_, p_149742_3_, p_149742_4_);
    }
    
    @Override
    public void onBlockAdded(final World p_149726_1_, final int p_149726_2_, final int p_149726_3_, final int p_149726_4_) {
        this.onNeighborBlockChange(p_149726_1_, p_149726_2_, p_149726_3_, p_149726_4_, Blocks.air);
        this.field_150149_b.onBlockAdded(p_149726_1_, p_149726_2_, p_149726_3_, p_149726_4_);
    }
    
    @Override
    public void breakBlock(final World p_149749_1_, final int p_149749_2_, final int p_149749_3_, final int p_149749_4_, final Block p_149749_5_, final int p_149749_6_) {
        this.field_150149_b.breakBlock(p_149749_1_, p_149749_2_, p_149749_3_, p_149749_4_, p_149749_5_, p_149749_6_);
    }
    
    @Override
    public void onEntityWalking(final World p_149724_1_, final int p_149724_2_, final int p_149724_3_, final int p_149724_4_, final Entity p_149724_5_) {
        this.field_150149_b.onEntityWalking(p_149724_1_, p_149724_2_, p_149724_3_, p_149724_4_, p_149724_5_);
    }
    
    @Override
    public void updateTick(final World p_149674_1_, final int p_149674_2_, final int p_149674_3_, final int p_149674_4_, final Random p_149674_5_) {
        this.field_150149_b.updateTick(p_149674_1_, p_149674_2_, p_149674_3_, p_149674_4_, p_149674_5_);
    }
    
    @Override
    public boolean onBlockActivated(final World p_149727_1_, final int p_149727_2_, final int p_149727_3_, final int p_149727_4_, final EntityPlayer p_149727_5_, final int p_149727_6_, final float p_149727_7_, final float p_149727_8_, final float p_149727_9_) {
        return this.field_150149_b.onBlockActivated(p_149727_1_, p_149727_2_, p_149727_3_, p_149727_4_, p_149727_5_, 0, 0.0f, 0.0f, 0.0f);
    }
    
    @Override
    public void onBlockDestroyedByExplosion(final World p_149723_1_, final int p_149723_2_, final int p_149723_3_, final int p_149723_4_, final Explosion p_149723_5_) {
        this.field_150149_b.onBlockDestroyedByExplosion(p_149723_1_, p_149723_2_, p_149723_3_, p_149723_4_, p_149723_5_);
    }
    
    @Override
    public MapColor getMapColor(final int p_149728_1_) {
        return this.field_150149_b.getMapColor(this.field_150151_M);
    }
    
    @Override
    public void onBlockPlacedBy(final World p_149689_1_, final int p_149689_2_, final int p_149689_3_, final int p_149689_4_, final EntityLivingBase p_149689_5_, final ItemStack p_149689_6_) {
        final int var7 = MathHelper.floor_double(p_149689_5_.rotationYaw * 4.0f / 360.0f + 0.5) & 0x3;
        final int var8 = p_149689_1_.getBlockMetadata(p_149689_2_, p_149689_3_, p_149689_4_) & 0x4;
        if (var7 == 0) {
            p_149689_1_.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_, 0x2 | var8, 2);
        }
        if (var7 == 1) {
            p_149689_1_.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_, 0x1 | var8, 2);
        }
        if (var7 == 2) {
            p_149689_1_.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_, 0x3 | var8, 2);
        }
        if (var7 == 3) {
            p_149689_1_.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_, 0x0 | var8, 2);
        }
    }
    
    @Override
    public int onBlockPlaced(final World p_149660_1_, final int p_149660_2_, final int p_149660_3_, final int p_149660_4_, final int p_149660_5_, final float p_149660_6_, final float p_149660_7_, final float p_149660_8_, final int p_149660_9_) {
        return (p_149660_5_ != 0 && (p_149660_5_ == 1 || p_149660_7_ <= 0.5)) ? p_149660_9_ : (p_149660_9_ | 0x4);
    }
    
    @Override
    public MovingObjectPosition collisionRayTrace(final World p_149731_1_, final int p_149731_2_, final int p_149731_3_, final int p_149731_4_, final Vec3 p_149731_5_, final Vec3 p_149731_6_) {
        final MovingObjectPosition[] var7 = new MovingObjectPosition[8];
        final int var8 = p_149731_1_.getBlockMetadata(p_149731_2_, p_149731_3_, p_149731_4_);
        final int var9 = var8 & 0x3;
        final boolean var10 = (var8 & 0x4) == 0x4;
        final int[] var11 = BlockStairs.field_150150_a[var9 + (var10 ? 4 : 0)];
        this.field_150152_N = true;
        for (int var12 = 0; var12 < 8; ++var12) {
            this.field_150153_O = var12;
            final int[] var13 = var11;
            for (int var14 = var11.length, var15 = 0; var15 < var14; ++var15) {
                final int var16 = var13[var15];
                if (var16 == var12) {}
            }
            var7[var12] = super.collisionRayTrace(p_149731_1_, p_149731_2_, p_149731_3_, p_149731_4_, p_149731_5_, p_149731_6_);
        }
        final int[] var17 = var11;
        for (int var18 = var11.length, var14 = 0; var14 < var18; ++var14) {
            final int var15 = var17[var14];
            var7[var15] = null;
        }
        MovingObjectPosition var19 = null;
        double var20 = 0.0;
        final MovingObjectPosition[] var21 = var7;
        final int var16 = var7.length;
        for (final MovingObjectPosition var23 : var21) {
            if (var23 != null) {
                final double var24 = var23.hitVec.squareDistanceTo(p_149731_6_);
                if (var24 > var20) {
                    var19 = var23;
                    var20 = var24;
                }
            }
        }
        return var19;
    }
    
    @Override
    public void registerBlockIcons(final IIconRegister p_149651_1_) {
    }
    
    static {
        field_150150_a = new int[][] { { 2, 6 }, { 3, 7 }, { 2, 3 }, { 6, 7 }, { 0, 4 }, { 1, 5 }, { 0, 1 }, { 4, 5 } };
    }
}
