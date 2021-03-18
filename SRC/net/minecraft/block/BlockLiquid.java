package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.world.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import net.minecraft.init.*;
import net.minecraft.client.renderer.texture.*;

public abstract class BlockLiquid extends Block
{
    private IIcon[] field_149806_a;
    private static final String __OBFID = "CL_00000265";
    
    protected BlockLiquid(final Material p_i45413_1_) {
        super(p_i45413_1_);
        final float var2 = 0.0f;
        final float var3 = 0.0f;
        this.setBlockBounds(0.0f + var3, 0.0f + var2, 0.0f + var3, 1.0f + var3, 1.0f + var2, 1.0f + var3);
        this.setTickRandomly(true);
    }
    
    @Override
    public boolean getBlocksMovement(final IBlockAccess p_149655_1_, final int p_149655_2_, final int p_149655_3_, final int p_149655_4_) {
        return this.blockMaterial != Material.lava;
    }
    
    @Override
    public int getBlockColor() {
        return 16777215;
    }
    
    @Override
    public int colorMultiplier(final IBlockAccess p_149720_1_, final int p_149720_2_, final int p_149720_3_, final int p_149720_4_) {
        if (this.blockMaterial != Material.water) {
            return 16777215;
        }
        int var5 = 0;
        int var6 = 0;
        int var7 = 0;
        for (int var8 = -1; var8 <= 1; ++var8) {
            for (int var9 = -1; var9 <= 1; ++var9) {
                final int var10 = p_149720_1_.getBiomeGenForCoords(p_149720_2_ + var9, p_149720_4_ + var8).waterColorMultiplier;
                var5 += (var10 & 0xFF0000) >> 16;
                var6 += (var10 & 0xFF00) >> 8;
                var7 += (var10 & 0xFF);
            }
        }
        return (var5 / 9 & 0xFF) << 16 | (var6 / 9 & 0xFF) << 8 | (var7 / 9 & 0xFF);
    }
    
    public static float func_149801_b(int p_149801_0_) {
        if (p_149801_0_ >= 8) {
            p_149801_0_ = 0;
        }
        return (p_149801_0_ + 1) / 9.0f;
    }
    
    @Override
    public IIcon getIcon(final int p_149691_1_, final int p_149691_2_) {
        return (p_149691_1_ != 0 && p_149691_1_ != 1) ? this.field_149806_a[1] : this.field_149806_a[0];
    }
    
    protected int func_149804_e(final World p_149804_1_, final int p_149804_2_, final int p_149804_3_, final int p_149804_4_) {
        return (p_149804_1_.getBlock(p_149804_2_, p_149804_3_, p_149804_4_).getMaterial() == this.blockMaterial) ? p_149804_1_.getBlockMetadata(p_149804_2_, p_149804_3_, p_149804_4_) : -1;
    }
    
    protected int func_149798_e(final IBlockAccess p_149798_1_, final int p_149798_2_, final int p_149798_3_, final int p_149798_4_) {
        if (p_149798_1_.getBlock(p_149798_2_, p_149798_3_, p_149798_4_).getMaterial() != this.blockMaterial) {
            return -1;
        }
        int var5 = p_149798_1_.getBlockMetadata(p_149798_2_, p_149798_3_, p_149798_4_);
        if (var5 >= 8) {
            var5 = 0;
        }
        return var5;
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
    public boolean canCollideCheck(final int p_149678_1_, final boolean p_149678_2_) {
        return p_149678_2_ && p_149678_1_ == 0;
    }
    
    @Override
    public boolean isBlockSolid(final IBlockAccess p_149747_1_, final int p_149747_2_, final int p_149747_3_, final int p_149747_4_, final int p_149747_5_) {
        final Material var6 = p_149747_1_.getBlock(p_149747_2_, p_149747_3_, p_149747_4_).getMaterial();
        return var6 != this.blockMaterial && (p_149747_5_ == 1 || (var6 != Material.ice && super.isBlockSolid(p_149747_1_, p_149747_2_, p_149747_3_, p_149747_4_, p_149747_5_)));
    }
    
    @Override
    public boolean shouldSideBeRendered(final IBlockAccess p_149646_1_, final int p_149646_2_, final int p_149646_3_, final int p_149646_4_, final int p_149646_5_) {
        final Material var6 = p_149646_1_.getBlock(p_149646_2_, p_149646_3_, p_149646_4_).getMaterial();
        return var6 != this.blockMaterial && (p_149646_5_ == 1 || super.shouldSideBeRendered(p_149646_1_, p_149646_2_, p_149646_3_, p_149646_4_, p_149646_5_));
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(final World p_149668_1_, final int p_149668_2_, final int p_149668_3_, final int p_149668_4_) {
        return null;
    }
    
    @Override
    public int getRenderType() {
        return 4;
    }
    
    @Override
    public Item getItemDropped(final int p_149650_1_, final Random p_149650_2_, final int p_149650_3_) {
        return null;
    }
    
    @Override
    public int quantityDropped(final Random p_149745_1_) {
        return 0;
    }
    
    private Vec3 func_149800_f(final IBlockAccess p_149800_1_, final int p_149800_2_, final int p_149800_3_, final int p_149800_4_) {
        Vec3 var5 = Vec3.createVectorHelper(0.0, 0.0, 0.0);
        final int var6 = this.func_149798_e(p_149800_1_, p_149800_2_, p_149800_3_, p_149800_4_);
        for (int var7 = 0; var7 < 4; ++var7) {
            int var8 = p_149800_2_;
            int var9 = p_149800_4_;
            if (var7 == 0) {
                var8 = p_149800_2_ - 1;
            }
            if (var7 == 1) {
                var9 = p_149800_4_ - 1;
            }
            if (var7 == 2) {
                ++var8;
            }
            if (var7 == 3) {
                ++var9;
            }
            int var10 = this.func_149798_e(p_149800_1_, var8, p_149800_3_, var9);
            if (var10 < 0) {
                if (!p_149800_1_.getBlock(var8, p_149800_3_, var9).getMaterial().blocksMovement()) {
                    var10 = this.func_149798_e(p_149800_1_, var8, p_149800_3_ - 1, var9);
                    if (var10 >= 0) {
                        final int var11 = var10 - (var6 - 8);
                        var5 = var5.addVector((var8 - p_149800_2_) * var11, (p_149800_3_ - p_149800_3_) * var11, (var9 - p_149800_4_) * var11);
                    }
                }
            }
            else if (var10 >= 0) {
                final int var11 = var10 - var6;
                var5 = var5.addVector((var8 - p_149800_2_) * var11, (p_149800_3_ - p_149800_3_) * var11, (var9 - p_149800_4_) * var11);
            }
        }
        if (p_149800_1_.getBlockMetadata(p_149800_2_, p_149800_3_, p_149800_4_) >= 8) {
            boolean var12 = false;
            if (var12 || this.isBlockSolid(p_149800_1_, p_149800_2_, p_149800_3_, p_149800_4_ - 1, 2)) {
                var12 = true;
            }
            if (var12 || this.isBlockSolid(p_149800_1_, p_149800_2_, p_149800_3_, p_149800_4_ + 1, 3)) {
                var12 = true;
            }
            if (var12 || this.isBlockSolid(p_149800_1_, p_149800_2_ - 1, p_149800_3_, p_149800_4_, 4)) {
                var12 = true;
            }
            if (var12 || this.isBlockSolid(p_149800_1_, p_149800_2_ + 1, p_149800_3_, p_149800_4_, 5)) {
                var12 = true;
            }
            if (var12 || this.isBlockSolid(p_149800_1_, p_149800_2_, p_149800_3_ + 1, p_149800_4_ - 1, 2)) {
                var12 = true;
            }
            if (var12 || this.isBlockSolid(p_149800_1_, p_149800_2_, p_149800_3_ + 1, p_149800_4_ + 1, 3)) {
                var12 = true;
            }
            if (var12 || this.isBlockSolid(p_149800_1_, p_149800_2_ - 1, p_149800_3_ + 1, p_149800_4_, 4)) {
                var12 = true;
            }
            if (var12 || this.isBlockSolid(p_149800_1_, p_149800_2_ + 1, p_149800_3_ + 1, p_149800_4_, 5)) {
                var12 = true;
            }
            if (var12) {
                var5 = var5.normalize().addVector(0.0, -6.0, 0.0);
            }
        }
        var5 = var5.normalize();
        return var5;
    }
    
    @Override
    public void velocityToAddToEntity(final World p_149640_1_, final int p_149640_2_, final int p_149640_3_, final int p_149640_4_, final Entity p_149640_5_, final Vec3 p_149640_6_) {
        final Vec3 var7 = this.func_149800_f(p_149640_1_, p_149640_2_, p_149640_3_, p_149640_4_);
        p_149640_6_.xCoord += var7.xCoord;
        p_149640_6_.yCoord += var7.yCoord;
        p_149640_6_.zCoord += var7.zCoord;
    }
    
    @Override
    public int func_149738_a(final World p_149738_1_) {
        return (this.blockMaterial == Material.water) ? 5 : ((this.blockMaterial == Material.lava) ? (p_149738_1_.provider.hasNoSky ? 10 : 30) : 0);
    }
    
    @Override
    public int getBlockBrightness(final IBlockAccess p_149677_1_, final int p_149677_2_, final int p_149677_3_, final int p_149677_4_) {
        final int var5 = p_149677_1_.getLightBrightnessForSkyBlocks(p_149677_2_, p_149677_3_, p_149677_4_, 0);
        final int var6 = p_149677_1_.getLightBrightnessForSkyBlocks(p_149677_2_, p_149677_3_ + 1, p_149677_4_, 0);
        final int var7 = var5 & 0xFF;
        final int var8 = var6 & 0xFF;
        final int var9 = var5 >> 16 & 0xFF;
        final int var10 = var6 >> 16 & 0xFF;
        return ((var7 > var8) ? var7 : var8) | ((var9 > var10) ? var9 : var10) << 16;
    }
    
    @Override
    public int getRenderBlockPass() {
        return (this.blockMaterial == Material.water) ? 1 : 0;
    }
    
    @Override
    public void randomDisplayTick(final World p_149734_1_, final int p_149734_2_, final int p_149734_3_, final int p_149734_4_, final Random p_149734_5_) {
        if (this.blockMaterial == Material.water) {
            if (p_149734_5_.nextInt(10) == 0) {
                final int var6 = p_149734_1_.getBlockMetadata(p_149734_2_, p_149734_3_, p_149734_4_);
                if (var6 <= 0 || var6 >= 8) {
                    p_149734_1_.spawnParticle("suspended", p_149734_2_ + p_149734_5_.nextFloat(), p_149734_3_ + p_149734_5_.nextFloat(), p_149734_4_ + p_149734_5_.nextFloat(), 0.0, 0.0, 0.0);
                }
            }
            for (int var6 = 0; var6 < 0; ++var6) {
                final int var7 = p_149734_5_.nextInt(4);
                int var8 = p_149734_2_;
                int var9 = p_149734_4_;
                if (var7 == 0) {
                    var8 = p_149734_2_ - 1;
                }
                if (var7 == 1) {
                    ++var8;
                }
                if (var7 == 2) {
                    var9 = p_149734_4_ - 1;
                }
                if (var7 == 3) {
                    ++var9;
                }
                if (p_149734_1_.getBlock(var8, p_149734_3_, var9).getMaterial() == Material.air && (p_149734_1_.getBlock(var8, p_149734_3_ - 1, var9).getMaterial().blocksMovement() || p_149734_1_.getBlock(var8, p_149734_3_ - 1, var9).getMaterial().isLiquid())) {
                    final float var10 = 0.0625f;
                    double var11 = p_149734_2_ + p_149734_5_.nextFloat();
                    final double var12 = p_149734_3_ + p_149734_5_.nextFloat();
                    double var13 = p_149734_4_ + p_149734_5_.nextFloat();
                    if (var7 == 0) {
                        var11 = p_149734_2_ - var10;
                    }
                    if (var7 == 1) {
                        var11 = p_149734_2_ + 1 + var10;
                    }
                    if (var7 == 2) {
                        var13 = p_149734_4_ - var10;
                    }
                    if (var7 == 3) {
                        var13 = p_149734_4_ + 1 + var10;
                    }
                    double var14 = 0.0;
                    double var15 = 0.0;
                    if (var7 == 0) {
                        var14 = -var10;
                    }
                    if (var7 == 1) {
                        var14 = var10;
                    }
                    if (var7 == 2) {
                        var15 = -var10;
                    }
                    if (var7 == 3) {
                        var15 = var10;
                    }
                    p_149734_1_.spawnParticle("splash", var11, var12, var13, var14, 0.0, var15);
                }
            }
        }
        if (this.blockMaterial == Material.water && p_149734_5_.nextInt(64) == 0) {
            final int var6 = p_149734_1_.getBlockMetadata(p_149734_2_, p_149734_3_, p_149734_4_);
            if (var6 > 0 && var6 < 8) {
                p_149734_1_.playSound(p_149734_2_ + 0.5f, p_149734_3_ + 0.5f, p_149734_4_ + 0.5f, "liquid.water", p_149734_5_.nextFloat() * 0.25f + 0.75f, p_149734_5_.nextFloat() * 1.0f + 0.5f, false);
            }
        }
        if (this.blockMaterial == Material.lava && p_149734_1_.getBlock(p_149734_2_, p_149734_3_ + 1, p_149734_4_).getMaterial() == Material.air && !p_149734_1_.getBlock(p_149734_2_, p_149734_3_ + 1, p_149734_4_).isOpaqueCube()) {
            if (p_149734_5_.nextInt(100) == 0) {
                final double var16 = p_149734_2_ + p_149734_5_.nextFloat();
                final double var17 = p_149734_3_ + this.field_149756_F;
                final double var18 = p_149734_4_ + p_149734_5_.nextFloat();
                p_149734_1_.spawnParticle("lava", var16, var17, var18, 0.0, 0.0, 0.0);
                p_149734_1_.playSound(var16, var17, var18, "liquid.lavapop", 0.2f + p_149734_5_.nextFloat() * 0.2f, 0.9f + p_149734_5_.nextFloat() * 0.15f, false);
            }
            if (p_149734_5_.nextInt(200) == 0) {
                p_149734_1_.playSound(p_149734_2_, p_149734_3_, p_149734_4_, "liquid.lava", 0.2f + p_149734_5_.nextFloat() * 0.2f, 0.9f + p_149734_5_.nextFloat() * 0.15f, false);
            }
        }
        if (p_149734_5_.nextInt(10) == 0 && World.doesBlockHaveSolidTopSurface(p_149734_1_, p_149734_2_, p_149734_3_ - 1, p_149734_4_) && !p_149734_1_.getBlock(p_149734_2_, p_149734_3_ - 2, p_149734_4_).getMaterial().blocksMovement()) {
            final double var16 = p_149734_2_ + p_149734_5_.nextFloat();
            final double var17 = p_149734_3_ - 1.05;
            final double var18 = p_149734_4_ + p_149734_5_.nextFloat();
            if (this.blockMaterial == Material.water) {
                p_149734_1_.spawnParticle("dripWater", var16, var17, var18, 0.0, 0.0, 0.0);
            }
            else {
                p_149734_1_.spawnParticle("dripLava", var16, var17, var18, 0.0, 0.0, 0.0);
            }
        }
    }
    
    public static double func_149802_a(final IBlockAccess p_149802_0_, final int p_149802_1_, final int p_149802_2_, final int p_149802_3_, final Material p_149802_4_) {
        Vec3 var5 = null;
        if (p_149802_4_ == Material.water) {
            var5 = Blocks.flowing_water.func_149800_f(p_149802_0_, p_149802_1_, p_149802_2_, p_149802_3_);
        }
        if (p_149802_4_ == Material.lava) {
            var5 = Blocks.flowing_lava.func_149800_f(p_149802_0_, p_149802_1_, p_149802_2_, p_149802_3_);
        }
        return (var5.xCoord == 0.0 && var5.zCoord == 0.0) ? -1000.0 : (Math.atan2(var5.zCoord, var5.xCoord) - 1.5707963267948966);
    }
    
    @Override
    public void onBlockAdded(final World p_149726_1_, final int p_149726_2_, final int p_149726_3_, final int p_149726_4_) {
        this.func_149805_n(p_149726_1_, p_149726_2_, p_149726_3_, p_149726_4_);
    }
    
    @Override
    public void onNeighborBlockChange(final World p_149695_1_, final int p_149695_2_, final int p_149695_3_, final int p_149695_4_, final Block p_149695_5_) {
        this.func_149805_n(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_);
    }
    
    private void func_149805_n(final World p_149805_1_, final int p_149805_2_, final int p_149805_3_, final int p_149805_4_) {
        if (p_149805_1_.getBlock(p_149805_2_, p_149805_3_, p_149805_4_) == this && this.blockMaterial == Material.lava) {
            boolean var5 = false;
            if (var5 || p_149805_1_.getBlock(p_149805_2_, p_149805_3_, p_149805_4_ - 1).getMaterial() == Material.water) {
                var5 = true;
            }
            if (var5 || p_149805_1_.getBlock(p_149805_2_, p_149805_3_, p_149805_4_ + 1).getMaterial() == Material.water) {
                var5 = true;
            }
            if (var5 || p_149805_1_.getBlock(p_149805_2_ - 1, p_149805_3_, p_149805_4_).getMaterial() == Material.water) {
                var5 = true;
            }
            if (var5 || p_149805_1_.getBlock(p_149805_2_ + 1, p_149805_3_, p_149805_4_).getMaterial() == Material.water) {
                var5 = true;
            }
            if (var5 || p_149805_1_.getBlock(p_149805_2_, p_149805_3_ + 1, p_149805_4_).getMaterial() == Material.water) {
                var5 = true;
            }
            if (var5) {
                final int var6 = p_149805_1_.getBlockMetadata(p_149805_2_, p_149805_3_, p_149805_4_);
                if (var6 == 0) {
                    p_149805_1_.setBlock(p_149805_2_, p_149805_3_, p_149805_4_, Blocks.obsidian);
                }
                else if (var6 <= 4) {
                    p_149805_1_.setBlock(p_149805_2_, p_149805_3_, p_149805_4_, Blocks.cobblestone);
                }
                this.func_149799_m(p_149805_1_, p_149805_2_, p_149805_3_, p_149805_4_);
            }
        }
    }
    
    protected void func_149799_m(final World p_149799_1_, final int p_149799_2_, final int p_149799_3_, final int p_149799_4_) {
        p_149799_1_.playSoundEffect(p_149799_2_ + 0.5f, p_149799_3_ + 0.5f, p_149799_4_ + 0.5f, "random.fizz", 0.5f, 2.6f + (p_149799_1_.rand.nextFloat() - p_149799_1_.rand.nextFloat()) * 0.8f);
        for (int var5 = 0; var5 < 8; ++var5) {
            p_149799_1_.spawnParticle("largesmoke", p_149799_2_ + Math.random(), p_149799_3_ + 1.2, p_149799_4_ + Math.random(), 0.0, 0.0, 0.0);
        }
    }
    
    @Override
    public void registerBlockIcons(final IIconRegister p_149651_1_) {
        if (this.blockMaterial == Material.lava) {
            this.field_149806_a = new IIcon[] { p_149651_1_.registerIcon("lava_still"), p_149651_1_.registerIcon("lava_flow") };
        }
        else {
            this.field_149806_a = new IIcon[] { p_149651_1_.registerIcon("water_still"), p_149651_1_.registerIcon("water_flow") };
        }
    }
    
    public static IIcon func_149803_e(final String p_149803_0_) {
        return (p_149803_0_ == "water_still") ? Blocks.flowing_water.field_149806_a[0] : ((p_149803_0_ == "water_flow") ? Blocks.flowing_water.field_149806_a[1] : ((p_149803_0_ == "lava_still") ? Blocks.flowing_lava.field_149806_a[0] : ((p_149803_0_ == "lava_flow") ? Blocks.flowing_lava.field_149806_a[1] : null)));
    }
}
