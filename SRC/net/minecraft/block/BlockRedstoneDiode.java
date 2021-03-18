package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.world.*;
import java.util.*;
import net.minecraft.init.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.util.*;

public abstract class BlockRedstoneDiode extends BlockDirectional
{
    protected final boolean field_149914_a;
    private static final String __OBFID = "CL_00000226";
    
    protected BlockRedstoneDiode(final boolean p_i45400_1_) {
        super(Material.circuits);
        this.field_149914_a = p_i45400_1_;
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.125f, 1.0f);
    }
    
    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }
    
    @Override
    public boolean canPlaceBlockAt(final World p_149742_1_, final int p_149742_2_, final int p_149742_3_, final int p_149742_4_) {
        return World.doesBlockHaveSolidTopSurface(p_149742_1_, p_149742_2_, p_149742_3_ - 1, p_149742_4_) && super.canPlaceBlockAt(p_149742_1_, p_149742_2_, p_149742_3_, p_149742_4_);
    }
    
    @Override
    public boolean canBlockStay(final World p_149718_1_, final int p_149718_2_, final int p_149718_3_, final int p_149718_4_) {
        return World.doesBlockHaveSolidTopSurface(p_149718_1_, p_149718_2_, p_149718_3_ - 1, p_149718_4_) && super.canBlockStay(p_149718_1_, p_149718_2_, p_149718_3_, p_149718_4_);
    }
    
    @Override
    public void updateTick(final World p_149674_1_, final int p_149674_2_, final int p_149674_3_, final int p_149674_4_, final Random p_149674_5_) {
        final int var6 = p_149674_1_.getBlockMetadata(p_149674_2_, p_149674_3_, p_149674_4_);
        if (!this.func_149910_g(p_149674_1_, p_149674_2_, p_149674_3_, p_149674_4_, var6)) {
            final boolean var7 = this.func_149900_a(p_149674_1_, p_149674_2_, p_149674_3_, p_149674_4_, var6);
            if (this.field_149914_a && !var7) {
                p_149674_1_.setBlock(p_149674_2_, p_149674_3_, p_149674_4_, this.func_149898_i(), var6, 2);
            }
            else if (!this.field_149914_a) {
                p_149674_1_.setBlock(p_149674_2_, p_149674_3_, p_149674_4_, this.func_149906_e(), var6, 2);
                if (!var7) {
                    p_149674_1_.func_147454_a(p_149674_2_, p_149674_3_, p_149674_4_, this.func_149906_e(), this.func_149899_k(var6), -1);
                }
            }
        }
    }
    
    @Override
    public IIcon getIcon(final int p_149691_1_, final int p_149691_2_) {
        return (p_149691_1_ == 0) ? (this.field_149914_a ? Blocks.redstone_torch.getBlockTextureFromSide(p_149691_1_) : Blocks.unlit_redstone_torch.getBlockTextureFromSide(p_149691_1_)) : ((p_149691_1_ == 1) ? this.blockIcon : Blocks.double_stone_slab.getBlockTextureFromSide(1));
    }
    
    @Override
    public boolean shouldSideBeRendered(final IBlockAccess p_149646_1_, final int p_149646_2_, final int p_149646_3_, final int p_149646_4_, final int p_149646_5_) {
        return p_149646_5_ != 0 && p_149646_5_ != 1;
    }
    
    @Override
    public int getRenderType() {
        return 36;
    }
    
    protected boolean func_149905_c(final int p_149905_1_) {
        return this.field_149914_a;
    }
    
    @Override
    public int isProvidingStrongPower(final IBlockAccess p_149748_1_, final int p_149748_2_, final int p_149748_3_, final int p_149748_4_, final int p_149748_5_) {
        return this.isProvidingWeakPower(p_149748_1_, p_149748_2_, p_149748_3_, p_149748_4_, p_149748_5_);
    }
    
    @Override
    public int isProvidingWeakPower(final IBlockAccess p_149709_1_, final int p_149709_2_, final int p_149709_3_, final int p_149709_4_, final int p_149709_5_) {
        final int var6 = p_149709_1_.getBlockMetadata(p_149709_2_, p_149709_3_, p_149709_4_);
        if (!this.func_149905_c(var6)) {
            return 0;
        }
        final int var7 = BlockDirectional.func_149895_l(var6);
        return (var7 == 0 && p_149709_5_ == 3) ? this.func_149904_f(p_149709_1_, p_149709_2_, p_149709_3_, p_149709_4_, var6) : ((var7 == 1 && p_149709_5_ == 4) ? this.func_149904_f(p_149709_1_, p_149709_2_, p_149709_3_, p_149709_4_, var6) : ((var7 == 2 && p_149709_5_ == 2) ? this.func_149904_f(p_149709_1_, p_149709_2_, p_149709_3_, p_149709_4_, var6) : ((var7 == 3 && p_149709_5_ == 5) ? this.func_149904_f(p_149709_1_, p_149709_2_, p_149709_3_, p_149709_4_, var6) : 0)));
    }
    
    @Override
    public void onNeighborBlockChange(final World p_149695_1_, final int p_149695_2_, final int p_149695_3_, final int p_149695_4_, final Block p_149695_5_) {
        if (!this.canBlockStay(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_)) {
            this.dropBlockAsItem(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_, p_149695_1_.getBlockMetadata(p_149695_2_, p_149695_3_, p_149695_4_), 0);
            p_149695_1_.setBlockToAir(p_149695_2_, p_149695_3_, p_149695_4_);
            p_149695_1_.notifyBlocksOfNeighborChange(p_149695_2_ + 1, p_149695_3_, p_149695_4_, this);
            p_149695_1_.notifyBlocksOfNeighborChange(p_149695_2_ - 1, p_149695_3_, p_149695_4_, this);
            p_149695_1_.notifyBlocksOfNeighborChange(p_149695_2_, p_149695_3_, p_149695_4_ + 1, this);
            p_149695_1_.notifyBlocksOfNeighborChange(p_149695_2_, p_149695_3_, p_149695_4_ - 1, this);
            p_149695_1_.notifyBlocksOfNeighborChange(p_149695_2_, p_149695_3_ - 1, p_149695_4_, this);
            p_149695_1_.notifyBlocksOfNeighborChange(p_149695_2_, p_149695_3_ + 1, p_149695_4_, this);
        }
        else {
            this.func_149897_b(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_, p_149695_5_);
        }
    }
    
    protected void func_149897_b(final World p_149897_1_, final int p_149897_2_, final int p_149897_3_, final int p_149897_4_, final Block p_149897_5_) {
        final int var6 = p_149897_1_.getBlockMetadata(p_149897_2_, p_149897_3_, p_149897_4_);
        if (!this.func_149910_g(p_149897_1_, p_149897_2_, p_149897_3_, p_149897_4_, var6)) {
            final boolean var7 = this.func_149900_a(p_149897_1_, p_149897_2_, p_149897_3_, p_149897_4_, var6);
            if (((this.field_149914_a && !var7) || (!this.field_149914_a && var7)) && !p_149897_1_.func_147477_a(p_149897_2_, p_149897_3_, p_149897_4_, this)) {
                byte var8 = -1;
                if (this.func_149912_i(p_149897_1_, p_149897_2_, p_149897_3_, p_149897_4_, var6)) {
                    var8 = -3;
                }
                else if (this.field_149914_a) {
                    var8 = -2;
                }
                p_149897_1_.func_147454_a(p_149897_2_, p_149897_3_, p_149897_4_, this, this.func_149901_b(var6), var8);
            }
        }
    }
    
    public boolean func_149910_g(final IBlockAccess p_149910_1_, final int p_149910_2_, final int p_149910_3_, final int p_149910_4_, final int p_149910_5_) {
        return false;
    }
    
    protected boolean func_149900_a(final World p_149900_1_, final int p_149900_2_, final int p_149900_3_, final int p_149900_4_, final int p_149900_5_) {
        return this.func_149903_h(p_149900_1_, p_149900_2_, p_149900_3_, p_149900_4_, p_149900_5_) > 0;
    }
    
    protected int func_149903_h(final World p_149903_1_, final int p_149903_2_, final int p_149903_3_, final int p_149903_4_, final int p_149903_5_) {
        final int var6 = BlockDirectional.func_149895_l(p_149903_5_);
        final int var7 = p_149903_2_ + Direction.offsetX[var6];
        final int var8 = p_149903_4_ + Direction.offsetZ[var6];
        final int var9 = p_149903_1_.getIndirectPowerLevelTo(var7, p_149903_3_, var8, Direction.directionToFacing[var6]);
        return (var9 >= 15) ? var9 : Math.max(var9, (p_149903_1_.getBlock(var7, p_149903_3_, var8) == Blocks.redstone_wire) ? p_149903_1_.getBlockMetadata(var7, p_149903_3_, var8) : 0);
    }
    
    protected int func_149902_h(final IBlockAccess p_149902_1_, final int p_149902_2_, final int p_149902_3_, final int p_149902_4_, final int p_149902_5_) {
        final int var6 = BlockDirectional.func_149895_l(p_149902_5_);
        switch (var6) {
            case 0:
            case 2: {
                return Math.max(this.func_149913_i(p_149902_1_, p_149902_2_ - 1, p_149902_3_, p_149902_4_, 4), this.func_149913_i(p_149902_1_, p_149902_2_ + 1, p_149902_3_, p_149902_4_, 5));
            }
            case 1:
            case 3: {
                return Math.max(this.func_149913_i(p_149902_1_, p_149902_2_, p_149902_3_, p_149902_4_ + 1, 3), this.func_149913_i(p_149902_1_, p_149902_2_, p_149902_3_, p_149902_4_ - 1, 2));
            }
            default: {
                return 0;
            }
        }
    }
    
    protected int func_149913_i(final IBlockAccess p_149913_1_, final int p_149913_2_, final int p_149913_3_, final int p_149913_4_, final int p_149913_5_) {
        final Block var6 = p_149913_1_.getBlock(p_149913_2_, p_149913_3_, p_149913_4_);
        return this.func_149908_a(var6) ? ((var6 == Blocks.redstone_wire) ? p_149913_1_.getBlockMetadata(p_149913_2_, p_149913_3_, p_149913_4_) : p_149913_1_.isBlockProvidingPowerTo(p_149913_2_, p_149913_3_, p_149913_4_, p_149913_5_)) : 0;
    }
    
    @Override
    public boolean canProvidePower() {
        return true;
    }
    
    @Override
    public void onBlockPlacedBy(final World p_149689_1_, final int p_149689_2_, final int p_149689_3_, final int p_149689_4_, final EntityLivingBase p_149689_5_, final ItemStack p_149689_6_) {
        final int var7 = ((MathHelper.floor_double(p_149689_5_.rotationYaw * 4.0f / 360.0f + 0.5) & 0x3) + 2) % 4;
        p_149689_1_.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_, var7, 3);
        final boolean var8 = this.func_149900_a(p_149689_1_, p_149689_2_, p_149689_3_, p_149689_4_, var7);
        if (var8) {
            p_149689_1_.scheduleBlockUpdate(p_149689_2_, p_149689_3_, p_149689_4_, this, 1);
        }
    }
    
    @Override
    public void onBlockAdded(final World p_149726_1_, final int p_149726_2_, final int p_149726_3_, final int p_149726_4_) {
        this.func_149911_e(p_149726_1_, p_149726_2_, p_149726_3_, p_149726_4_);
    }
    
    protected void func_149911_e(final World p_149911_1_, final int p_149911_2_, final int p_149911_3_, final int p_149911_4_) {
        final int var5 = BlockDirectional.func_149895_l(p_149911_1_.getBlockMetadata(p_149911_2_, p_149911_3_, p_149911_4_));
        if (var5 == 1) {
            p_149911_1_.func_147460_e(p_149911_2_ + 1, p_149911_3_, p_149911_4_, this);
            p_149911_1_.func_147441_b(p_149911_2_ + 1, p_149911_3_, p_149911_4_, this, 4);
        }
        if (var5 == 3) {
            p_149911_1_.func_147460_e(p_149911_2_ - 1, p_149911_3_, p_149911_4_, this);
            p_149911_1_.func_147441_b(p_149911_2_ - 1, p_149911_3_, p_149911_4_, this, 5);
        }
        if (var5 == 2) {
            p_149911_1_.func_147460_e(p_149911_2_, p_149911_3_, p_149911_4_ + 1, this);
            p_149911_1_.func_147441_b(p_149911_2_, p_149911_3_, p_149911_4_ + 1, this, 2);
        }
        if (var5 == 0) {
            p_149911_1_.func_147460_e(p_149911_2_, p_149911_3_, p_149911_4_ - 1, this);
            p_149911_1_.func_147441_b(p_149911_2_, p_149911_3_, p_149911_4_ - 1, this, 3);
        }
    }
    
    @Override
    public void onBlockDestroyedByPlayer(final World p_149664_1_, final int p_149664_2_, final int p_149664_3_, final int p_149664_4_, final int p_149664_5_) {
        if (this.field_149914_a) {
            p_149664_1_.notifyBlocksOfNeighborChange(p_149664_2_ + 1, p_149664_3_, p_149664_4_, this);
            p_149664_1_.notifyBlocksOfNeighborChange(p_149664_2_ - 1, p_149664_3_, p_149664_4_, this);
            p_149664_1_.notifyBlocksOfNeighborChange(p_149664_2_, p_149664_3_, p_149664_4_ + 1, this);
            p_149664_1_.notifyBlocksOfNeighborChange(p_149664_2_, p_149664_3_, p_149664_4_ - 1, this);
            p_149664_1_.notifyBlocksOfNeighborChange(p_149664_2_, p_149664_3_ - 1, p_149664_4_, this);
            p_149664_1_.notifyBlocksOfNeighborChange(p_149664_2_, p_149664_3_ + 1, p_149664_4_, this);
        }
        super.onBlockDestroyedByPlayer(p_149664_1_, p_149664_2_, p_149664_3_, p_149664_4_, p_149664_5_);
    }
    
    @Override
    public boolean isOpaqueCube() {
        return false;
    }
    
    protected boolean func_149908_a(final Block p_149908_1_) {
        return p_149908_1_.canProvidePower();
    }
    
    protected int func_149904_f(final IBlockAccess p_149904_1_, final int p_149904_2_, final int p_149904_3_, final int p_149904_4_, final int p_149904_5_) {
        return 15;
    }
    
    public static boolean func_149909_d(final Block p_149909_0_) {
        return Blocks.unpowered_repeater.func_149907_e(p_149909_0_) || Blocks.unpowered_comparator.func_149907_e(p_149909_0_);
    }
    
    public boolean func_149907_e(final Block p_149907_1_) {
        return p_149907_1_ == this.func_149906_e() || p_149907_1_ == this.func_149898_i();
    }
    
    public boolean func_149912_i(final World p_149912_1_, final int p_149912_2_, final int p_149912_3_, final int p_149912_4_, final int p_149912_5_) {
        final int var6 = BlockDirectional.func_149895_l(p_149912_5_);
        if (func_149909_d(p_149912_1_.getBlock(p_149912_2_ - Direction.offsetX[var6], p_149912_3_, p_149912_4_ - Direction.offsetZ[var6]))) {
            final int var7 = p_149912_1_.getBlockMetadata(p_149912_2_ - Direction.offsetX[var6], p_149912_3_, p_149912_4_ - Direction.offsetZ[var6]);
            final int var8 = BlockDirectional.func_149895_l(var7);
            return var8 != var6;
        }
        return false;
    }
    
    protected int func_149899_k(final int p_149899_1_) {
        return this.func_149901_b(p_149899_1_);
    }
    
    protected abstract int func_149901_b(final int p0);
    
    protected abstract BlockRedstoneDiode func_149906_e();
    
    protected abstract BlockRedstoneDiode func_149898_i();
    
    @Override
    public boolean func_149667_c(final Block p_149667_1_) {
        return this.func_149907_e(p_149667_1_);
    }
}
