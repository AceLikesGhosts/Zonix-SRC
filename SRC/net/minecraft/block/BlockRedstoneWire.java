package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.world.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.texture.*;

public class BlockRedstoneWire extends Block
{
    private boolean field_150181_a;
    private Set field_150179_b;
    private IIcon field_150182_M;
    private IIcon field_150183_N;
    private IIcon field_150184_O;
    private IIcon field_150180_P;
    private static final String __OBFID = "CL_00000295";
    
    public BlockRedstoneWire() {
        super(Material.circuits);
        this.field_150181_a = true;
        this.field_150179_b = new HashSet();
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.0625f, 1.0f);
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(final World p_149668_1_, final int p_149668_2_, final int p_149668_3_, final int p_149668_4_) {
        return null;
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
        return 5;
    }
    
    @Override
    public int colorMultiplier(final IBlockAccess p_149720_1_, final int p_149720_2_, final int p_149720_3_, final int p_149720_4_) {
        return 8388608;
    }
    
    @Override
    public boolean canPlaceBlockAt(final World p_149742_1_, final int p_149742_2_, final int p_149742_3_, final int p_149742_4_) {
        return World.doesBlockHaveSolidTopSurface(p_149742_1_, p_149742_2_, p_149742_3_ - 1, p_149742_4_) || p_149742_1_.getBlock(p_149742_2_, p_149742_3_ - 1, p_149742_4_) == Blocks.glowstone;
    }
    
    private void func_150177_e(final World p_150177_1_, final int p_150177_2_, final int p_150177_3_, final int p_150177_4_) {
        this.func_150175_a(p_150177_1_, p_150177_2_, p_150177_3_, p_150177_4_, p_150177_2_, p_150177_3_, p_150177_4_);
        final ArrayList var5 = new ArrayList(this.field_150179_b);
        this.field_150179_b.clear();
        for (int var6 = 0; var6 < var5.size(); ++var6) {
            final ChunkPosition var7 = var5.get(var6);
            p_150177_1_.notifyBlocksOfNeighborChange(var7.field_151329_a, var7.field_151327_b, var7.field_151328_c, this);
        }
    }
    
    private void func_150175_a(final World p_150175_1_, final int p_150175_2_, final int p_150175_3_, final int p_150175_4_, final int p_150175_5_, final int p_150175_6_, final int p_150175_7_) {
        final int var8 = p_150175_1_.getBlockMetadata(p_150175_2_, p_150175_3_, p_150175_4_);
        final byte var9 = 0;
        int var10 = this.func_150178_a(p_150175_1_, p_150175_5_, p_150175_6_, p_150175_7_, var9);
        this.field_150181_a = false;
        final int var11 = p_150175_1_.getStrongestIndirectPower(p_150175_2_, p_150175_3_, p_150175_4_);
        this.field_150181_a = true;
        if (var11 > 0 && var11 > var10 - 1) {
            var10 = var11;
        }
        int var12 = 0;
        for (int var13 = 0; var13 < 4; ++var13) {
            int var14 = p_150175_2_;
            int var15 = p_150175_4_;
            if (var13 == 0) {
                var14 = p_150175_2_ - 1;
            }
            if (var13 == 1) {
                ++var14;
            }
            if (var13 == 2) {
                var15 = p_150175_4_ - 1;
            }
            if (var13 == 3) {
                ++var15;
            }
            if (var14 != p_150175_5_ || var15 != p_150175_7_) {
                var12 = this.func_150178_a(p_150175_1_, var14, p_150175_3_, var15, var12);
            }
            if (p_150175_1_.getBlock(var14, p_150175_3_, var15).isNormalCube() && !p_150175_1_.getBlock(p_150175_2_, p_150175_3_ + 1, p_150175_4_).isNormalCube()) {
                if ((var14 != p_150175_5_ || var15 != p_150175_7_) && p_150175_3_ >= p_150175_6_) {
                    var12 = this.func_150178_a(p_150175_1_, var14, p_150175_3_ + 1, var15, var12);
                }
            }
            else if (!p_150175_1_.getBlock(var14, p_150175_3_, var15).isNormalCube() && (var14 != p_150175_5_ || var15 != p_150175_7_) && p_150175_3_ <= p_150175_6_) {
                var12 = this.func_150178_a(p_150175_1_, var14, p_150175_3_ - 1, var15, var12);
            }
        }
        if (var12 > var10) {
            var10 = var12 - 1;
        }
        else if (var10 > 0) {
            --var10;
        }
        else {
            var10 = 0;
        }
        if (var11 > var10 - 1) {
            var10 = var11;
        }
        if (var8 != var10) {
            p_150175_1_.setBlockMetadataWithNotify(p_150175_2_, p_150175_3_, p_150175_4_, var10, 2);
            this.field_150179_b.add(new ChunkPosition(p_150175_2_, p_150175_3_, p_150175_4_));
            this.field_150179_b.add(new ChunkPosition(p_150175_2_ - 1, p_150175_3_, p_150175_4_));
            this.field_150179_b.add(new ChunkPosition(p_150175_2_ + 1, p_150175_3_, p_150175_4_));
            this.field_150179_b.add(new ChunkPosition(p_150175_2_, p_150175_3_ - 1, p_150175_4_));
            this.field_150179_b.add(new ChunkPosition(p_150175_2_, p_150175_3_ + 1, p_150175_4_));
            this.field_150179_b.add(new ChunkPosition(p_150175_2_, p_150175_3_, p_150175_4_ - 1));
            this.field_150179_b.add(new ChunkPosition(p_150175_2_, p_150175_3_, p_150175_4_ + 1));
        }
    }
    
    private void func_150172_m(final World p_150172_1_, final int p_150172_2_, final int p_150172_3_, final int p_150172_4_) {
        if (p_150172_1_.getBlock(p_150172_2_, p_150172_3_, p_150172_4_) == this) {
            p_150172_1_.notifyBlocksOfNeighborChange(p_150172_2_, p_150172_3_, p_150172_4_, this);
            p_150172_1_.notifyBlocksOfNeighborChange(p_150172_2_ - 1, p_150172_3_, p_150172_4_, this);
            p_150172_1_.notifyBlocksOfNeighborChange(p_150172_2_ + 1, p_150172_3_, p_150172_4_, this);
            p_150172_1_.notifyBlocksOfNeighborChange(p_150172_2_, p_150172_3_, p_150172_4_ - 1, this);
            p_150172_1_.notifyBlocksOfNeighborChange(p_150172_2_, p_150172_3_, p_150172_4_ + 1, this);
            p_150172_1_.notifyBlocksOfNeighborChange(p_150172_2_, p_150172_3_ - 1, p_150172_4_, this);
            p_150172_1_.notifyBlocksOfNeighborChange(p_150172_2_, p_150172_3_ + 1, p_150172_4_, this);
        }
    }
    
    @Override
    public void onBlockAdded(final World p_149726_1_, final int p_149726_2_, final int p_149726_3_, final int p_149726_4_) {
        super.onBlockAdded(p_149726_1_, p_149726_2_, p_149726_3_, p_149726_4_);
        if (!p_149726_1_.isClient) {
            this.func_150177_e(p_149726_1_, p_149726_2_, p_149726_3_, p_149726_4_);
            p_149726_1_.notifyBlocksOfNeighborChange(p_149726_2_, p_149726_3_ + 1, p_149726_4_, this);
            p_149726_1_.notifyBlocksOfNeighborChange(p_149726_2_, p_149726_3_ - 1, p_149726_4_, this);
            this.func_150172_m(p_149726_1_, p_149726_2_ - 1, p_149726_3_, p_149726_4_);
            this.func_150172_m(p_149726_1_, p_149726_2_ + 1, p_149726_3_, p_149726_4_);
            this.func_150172_m(p_149726_1_, p_149726_2_, p_149726_3_, p_149726_4_ - 1);
            this.func_150172_m(p_149726_1_, p_149726_2_, p_149726_3_, p_149726_4_ + 1);
            if (p_149726_1_.getBlock(p_149726_2_ - 1, p_149726_3_, p_149726_4_).isNormalCube()) {
                this.func_150172_m(p_149726_1_, p_149726_2_ - 1, p_149726_3_ + 1, p_149726_4_);
            }
            else {
                this.func_150172_m(p_149726_1_, p_149726_2_ - 1, p_149726_3_ - 1, p_149726_4_);
            }
            if (p_149726_1_.getBlock(p_149726_2_ + 1, p_149726_3_, p_149726_4_).isNormalCube()) {
                this.func_150172_m(p_149726_1_, p_149726_2_ + 1, p_149726_3_ + 1, p_149726_4_);
            }
            else {
                this.func_150172_m(p_149726_1_, p_149726_2_ + 1, p_149726_3_ - 1, p_149726_4_);
            }
            if (p_149726_1_.getBlock(p_149726_2_, p_149726_3_, p_149726_4_ - 1).isNormalCube()) {
                this.func_150172_m(p_149726_1_, p_149726_2_, p_149726_3_ + 1, p_149726_4_ - 1);
            }
            else {
                this.func_150172_m(p_149726_1_, p_149726_2_, p_149726_3_ - 1, p_149726_4_ - 1);
            }
            if (p_149726_1_.getBlock(p_149726_2_, p_149726_3_, p_149726_4_ + 1).isNormalCube()) {
                this.func_150172_m(p_149726_1_, p_149726_2_, p_149726_3_ + 1, p_149726_4_ + 1);
            }
            else {
                this.func_150172_m(p_149726_1_, p_149726_2_, p_149726_3_ - 1, p_149726_4_ + 1);
            }
        }
    }
    
    @Override
    public void breakBlock(final World p_149749_1_, final int p_149749_2_, final int p_149749_3_, final int p_149749_4_, final Block p_149749_5_, final int p_149749_6_) {
        super.breakBlock(p_149749_1_, p_149749_2_, p_149749_3_, p_149749_4_, p_149749_5_, p_149749_6_);
        if (!p_149749_1_.isClient) {
            p_149749_1_.notifyBlocksOfNeighborChange(p_149749_2_, p_149749_3_ + 1, p_149749_4_, this);
            p_149749_1_.notifyBlocksOfNeighborChange(p_149749_2_, p_149749_3_ - 1, p_149749_4_, this);
            p_149749_1_.notifyBlocksOfNeighborChange(p_149749_2_ + 1, p_149749_3_, p_149749_4_, this);
            p_149749_1_.notifyBlocksOfNeighborChange(p_149749_2_ - 1, p_149749_3_, p_149749_4_, this);
            p_149749_1_.notifyBlocksOfNeighborChange(p_149749_2_, p_149749_3_, p_149749_4_ + 1, this);
            p_149749_1_.notifyBlocksOfNeighborChange(p_149749_2_, p_149749_3_, p_149749_4_ - 1, this);
            this.func_150177_e(p_149749_1_, p_149749_2_, p_149749_3_, p_149749_4_);
            this.func_150172_m(p_149749_1_, p_149749_2_ - 1, p_149749_3_, p_149749_4_);
            this.func_150172_m(p_149749_1_, p_149749_2_ + 1, p_149749_3_, p_149749_4_);
            this.func_150172_m(p_149749_1_, p_149749_2_, p_149749_3_, p_149749_4_ - 1);
            this.func_150172_m(p_149749_1_, p_149749_2_, p_149749_3_, p_149749_4_ + 1);
            if (p_149749_1_.getBlock(p_149749_2_ - 1, p_149749_3_, p_149749_4_).isNormalCube()) {
                this.func_150172_m(p_149749_1_, p_149749_2_ - 1, p_149749_3_ + 1, p_149749_4_);
            }
            else {
                this.func_150172_m(p_149749_1_, p_149749_2_ - 1, p_149749_3_ - 1, p_149749_4_);
            }
            if (p_149749_1_.getBlock(p_149749_2_ + 1, p_149749_3_, p_149749_4_).isNormalCube()) {
                this.func_150172_m(p_149749_1_, p_149749_2_ + 1, p_149749_3_ + 1, p_149749_4_);
            }
            else {
                this.func_150172_m(p_149749_1_, p_149749_2_ + 1, p_149749_3_ - 1, p_149749_4_);
            }
            if (p_149749_1_.getBlock(p_149749_2_, p_149749_3_, p_149749_4_ - 1).isNormalCube()) {
                this.func_150172_m(p_149749_1_, p_149749_2_, p_149749_3_ + 1, p_149749_4_ - 1);
            }
            else {
                this.func_150172_m(p_149749_1_, p_149749_2_, p_149749_3_ - 1, p_149749_4_ - 1);
            }
            if (p_149749_1_.getBlock(p_149749_2_, p_149749_3_, p_149749_4_ + 1).isNormalCube()) {
                this.func_150172_m(p_149749_1_, p_149749_2_, p_149749_3_ + 1, p_149749_4_ + 1);
            }
            else {
                this.func_150172_m(p_149749_1_, p_149749_2_, p_149749_3_ - 1, p_149749_4_ + 1);
            }
        }
    }
    
    private int func_150178_a(final World p_150178_1_, final int p_150178_2_, final int p_150178_3_, final int p_150178_4_, final int p_150178_5_) {
        if (p_150178_1_.getBlock(p_150178_2_, p_150178_3_, p_150178_4_) != this) {
            return p_150178_5_;
        }
        final int var6 = p_150178_1_.getBlockMetadata(p_150178_2_, p_150178_3_, p_150178_4_);
        return (var6 > p_150178_5_) ? var6 : p_150178_5_;
    }
    
    @Override
    public void onNeighborBlockChange(final World p_149695_1_, final int p_149695_2_, final int p_149695_3_, final int p_149695_4_, final Block p_149695_5_) {
        if (!p_149695_1_.isClient) {
            final boolean var6 = this.canPlaceBlockAt(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_);
            if (var6) {
                this.func_150177_e(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_);
            }
            else {
                this.dropBlockAsItem(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_, 0, 0);
                p_149695_1_.setBlockToAir(p_149695_2_, p_149695_3_, p_149695_4_);
            }
            super.onNeighborBlockChange(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_, p_149695_5_);
        }
    }
    
    @Override
    public Item getItemDropped(final int p_149650_1_, final Random p_149650_2_, final int p_149650_3_) {
        return Items.redstone;
    }
    
    @Override
    public int isProvidingStrongPower(final IBlockAccess p_149748_1_, final int p_149748_2_, final int p_149748_3_, final int p_149748_4_, final int p_149748_5_) {
        return this.field_150181_a ? this.isProvidingWeakPower(p_149748_1_, p_149748_2_, p_149748_3_, p_149748_4_, p_149748_5_) : 0;
    }
    
    @Override
    public int isProvidingWeakPower(final IBlockAccess p_149709_1_, final int p_149709_2_, final int p_149709_3_, final int p_149709_4_, final int p_149709_5_) {
        if (!this.field_150181_a) {
            return 0;
        }
        final int var6 = p_149709_1_.getBlockMetadata(p_149709_2_, p_149709_3_, p_149709_4_);
        if (var6 == 0) {
            return 0;
        }
        if (p_149709_5_ == 1) {
            return var6;
        }
        boolean var7 = func_150176_g(p_149709_1_, p_149709_2_ - 1, p_149709_3_, p_149709_4_, 1) || (!p_149709_1_.getBlock(p_149709_2_ - 1, p_149709_3_, p_149709_4_).isNormalCube() && func_150176_g(p_149709_1_, p_149709_2_ - 1, p_149709_3_ - 1, p_149709_4_, -1));
        boolean var8 = func_150176_g(p_149709_1_, p_149709_2_ + 1, p_149709_3_, p_149709_4_, 3) || (!p_149709_1_.getBlock(p_149709_2_ + 1, p_149709_3_, p_149709_4_).isNormalCube() && func_150176_g(p_149709_1_, p_149709_2_ + 1, p_149709_3_ - 1, p_149709_4_, -1));
        boolean var9 = func_150176_g(p_149709_1_, p_149709_2_, p_149709_3_, p_149709_4_ - 1, 2) || (!p_149709_1_.getBlock(p_149709_2_, p_149709_3_, p_149709_4_ - 1).isNormalCube() && func_150176_g(p_149709_1_, p_149709_2_, p_149709_3_ - 1, p_149709_4_ - 1, -1));
        boolean var10 = func_150176_g(p_149709_1_, p_149709_2_, p_149709_3_, p_149709_4_ + 1, 0) || (!p_149709_1_.getBlock(p_149709_2_, p_149709_3_, p_149709_4_ + 1).isNormalCube() && func_150176_g(p_149709_1_, p_149709_2_, p_149709_3_ - 1, p_149709_4_ + 1, -1));
        if (!p_149709_1_.getBlock(p_149709_2_, p_149709_3_ + 1, p_149709_4_).isNormalCube()) {
            if (p_149709_1_.getBlock(p_149709_2_ - 1, p_149709_3_, p_149709_4_).isNormalCube() && func_150176_g(p_149709_1_, p_149709_2_ - 1, p_149709_3_ + 1, p_149709_4_, -1)) {
                var7 = true;
            }
            if (p_149709_1_.getBlock(p_149709_2_ + 1, p_149709_3_, p_149709_4_).isNormalCube() && func_150176_g(p_149709_1_, p_149709_2_ + 1, p_149709_3_ + 1, p_149709_4_, -1)) {
                var8 = true;
            }
            if (p_149709_1_.getBlock(p_149709_2_, p_149709_3_, p_149709_4_ - 1).isNormalCube() && func_150176_g(p_149709_1_, p_149709_2_, p_149709_3_ + 1, p_149709_4_ - 1, -1)) {
                var9 = true;
            }
            if (p_149709_1_.getBlock(p_149709_2_, p_149709_3_, p_149709_4_ + 1).isNormalCube() && func_150176_g(p_149709_1_, p_149709_2_, p_149709_3_ + 1, p_149709_4_ + 1, -1)) {
                var10 = true;
            }
        }
        return (!var9 && !var8 && !var7 && !var10 && p_149709_5_ >= 2 && p_149709_5_ <= 5) ? var6 : ((p_149709_5_ == 2 && var9 && !var7 && !var8) ? var6 : ((p_149709_5_ == 3 && var10 && !var7 && !var8) ? var6 : ((p_149709_5_ == 4 && var7 && !var9 && !var10) ? var6 : ((p_149709_5_ == 5 && var8 && !var9 && !var10) ? var6 : 0))));
    }
    
    @Override
    public boolean canProvidePower() {
        return this.field_150181_a;
    }
    
    @Override
    public void randomDisplayTick(final World p_149734_1_, final int p_149734_2_, final int p_149734_3_, final int p_149734_4_, final Random p_149734_5_) {
        final int var6 = p_149734_1_.getBlockMetadata(p_149734_2_, p_149734_3_, p_149734_4_);
        if (var6 > 0) {
            final double var7 = p_149734_2_ + 0.5 + (p_149734_5_.nextFloat() - 0.5) * 0.2;
            final double var8 = p_149734_3_ + 0.0625f;
            final double var9 = p_149734_4_ + 0.5 + (p_149734_5_.nextFloat() - 0.5) * 0.2;
            final float var10 = var6 / 15.0f;
            float var11 = var10 * 0.6f + 0.4f;
            if (var6 == 0) {
                var11 = 0.0f;
            }
            float var12 = var10 * var10 * 0.7f - 0.5f;
            float var13 = var10 * var10 * 0.6f - 0.7f;
            if (var12 < 0.0f) {
                var12 = 0.0f;
            }
            if (var13 < 0.0f) {
                var13 = 0.0f;
            }
            p_149734_1_.spawnParticle("reddust", var7, var8, var9, var11, var12, var13);
        }
    }
    
    public static boolean func_150174_f(final IBlockAccess p_150174_0_, final int p_150174_1_, final int p_150174_2_, final int p_150174_3_, final int p_150174_4_) {
        final Block var5 = p_150174_0_.getBlock(p_150174_1_, p_150174_2_, p_150174_3_);
        if (var5 == Blocks.redstone_wire) {
            return true;
        }
        if (!Blocks.unpowered_repeater.func_149907_e(var5)) {
            return var5.canProvidePower() && p_150174_4_ != -1;
        }
        final int var6 = p_150174_0_.getBlockMetadata(p_150174_1_, p_150174_2_, p_150174_3_);
        return p_150174_4_ == (var6 & 0x3) || p_150174_4_ == Direction.rotateOpposite[var6 & 0x3];
    }
    
    public static boolean func_150176_g(final IBlockAccess p_150176_0_, final int p_150176_1_, final int p_150176_2_, final int p_150176_3_, final int p_150176_4_) {
        if (func_150174_f(p_150176_0_, p_150176_1_, p_150176_2_, p_150176_3_, p_150176_4_)) {
            return true;
        }
        if (p_150176_0_.getBlock(p_150176_1_, p_150176_2_, p_150176_3_) == Blocks.powered_repeater) {
            final int var5 = p_150176_0_.getBlockMetadata(p_150176_1_, p_150176_2_, p_150176_3_);
            return p_150176_4_ == (var5 & 0x3);
        }
        return false;
    }
    
    @Override
    public Item getItem(final World p_149694_1_, final int p_149694_2_, final int p_149694_3_, final int p_149694_4_) {
        return Items.redstone;
    }
    
    @Override
    public void registerBlockIcons(final IIconRegister p_149651_1_) {
        this.field_150182_M = p_149651_1_.registerIcon(this.getTextureName() + "_cross");
        this.field_150183_N = p_149651_1_.registerIcon(this.getTextureName() + "_line");
        this.field_150184_O = p_149651_1_.registerIcon(this.getTextureName() + "_cross_overlay");
        this.field_150180_P = p_149651_1_.registerIcon(this.getTextureName() + "_line_overlay");
        this.blockIcon = this.field_150182_M;
    }
    
    public static IIcon func_150173_e(final String p_150173_0_) {
        return p_150173_0_.equals("cross") ? Blocks.redstone_wire.field_150182_M : (p_150173_0_.equals("line") ? Blocks.redstone_wire.field_150183_N : (p_150173_0_.equals("cross_overlay") ? Blocks.redstone_wire.field_150184_O : (p_150173_0_.equals("line_overlay") ? Blocks.redstone_wire.field_150180_P : null)));
    }
}
