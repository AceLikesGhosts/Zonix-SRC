package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import net.minecraft.init.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.tileentity.*;
import net.minecraft.world.*;
import java.util.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;

public class BlockPistonBase extends Block
{
    private final boolean field_150082_a;
    private IIcon field_150081_b;
    private IIcon field_150083_M;
    private IIcon field_150084_N;
    private static final String __OBFID = "CL_00000366";
    
    public BlockPistonBase(final boolean p_i45443_1_) {
        super(Material.piston);
        this.field_150082_a = p_i45443_1_;
        this.setStepSound(BlockPistonBase.soundTypePiston);
        this.setHardness(0.5f);
        this.setCreativeTab(CreativeTabs.tabRedstone);
    }
    
    public IIcon func_150073_e() {
        return this.field_150084_N;
    }
    
    public void func_150070_b(final float p_150070_1_, final float p_150070_2_, final float p_150070_3_, final float p_150070_4_, final float p_150070_5_, final float p_150070_6_) {
        this.setBlockBounds(p_150070_1_, p_150070_2_, p_150070_3_, p_150070_4_, p_150070_5_, p_150070_6_);
    }
    
    @Override
    public IIcon getIcon(final int p_149691_1_, final int p_149691_2_) {
        final int var3 = func_150076_b(p_149691_2_);
        return (var3 > 5) ? this.field_150084_N : ((p_149691_1_ == var3) ? ((!func_150075_c(p_149691_2_) && this.field_149759_B <= 0.0 && this.field_149760_C <= 0.0 && this.field_149754_D <= 0.0 && this.field_149755_E >= 1.0 && this.field_149756_F >= 1.0 && this.field_149757_G >= 1.0) ? this.field_150084_N : this.field_150081_b) : ((p_149691_1_ == Facing.oppositeSide[var3]) ? this.field_150083_M : this.blockIcon));
    }
    
    public static IIcon func_150074_e(final String p_150074_0_) {
        return (p_150074_0_ == "piston_side") ? Blocks.piston.blockIcon : ((p_150074_0_ == "piston_top_normal") ? Blocks.piston.field_150084_N : ((p_150074_0_ == "piston_top_sticky") ? Blocks.sticky_piston.field_150084_N : ((p_150074_0_ == "piston_inner") ? Blocks.piston.field_150081_b : null)));
    }
    
    @Override
    public void registerBlockIcons(final IIconRegister p_149651_1_) {
        this.blockIcon = p_149651_1_.registerIcon("piston_side");
        this.field_150084_N = p_149651_1_.registerIcon(this.field_150082_a ? "piston_top_sticky" : "piston_top_normal");
        this.field_150081_b = p_149651_1_.registerIcon("piston_inner");
        this.field_150083_M = p_149651_1_.registerIcon("piston_bottom");
    }
    
    @Override
    public int getRenderType() {
        return 16;
    }
    
    @Override
    public boolean isOpaqueCube() {
        return false;
    }
    
    @Override
    public boolean onBlockActivated(final World p_149727_1_, final int p_149727_2_, final int p_149727_3_, final int p_149727_4_, final EntityPlayer p_149727_5_, final int p_149727_6_, final float p_149727_7_, final float p_149727_8_, final float p_149727_9_) {
        return false;
    }
    
    @Override
    public void onBlockPlacedBy(final World p_149689_1_, final int p_149689_2_, final int p_149689_3_, final int p_149689_4_, final EntityLivingBase p_149689_5_, final ItemStack p_149689_6_) {
        final int var7 = func_150071_a(p_149689_1_, p_149689_2_, p_149689_3_, p_149689_4_, p_149689_5_);
        p_149689_1_.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_, var7, 2);
        if (!p_149689_1_.isClient) {
            this.func_150078_e(p_149689_1_, p_149689_2_, p_149689_3_, p_149689_4_);
        }
    }
    
    @Override
    public void onNeighborBlockChange(final World p_149695_1_, final int p_149695_2_, final int p_149695_3_, final int p_149695_4_, final Block p_149695_5_) {
        if (!p_149695_1_.isClient) {
            this.func_150078_e(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_);
        }
    }
    
    @Override
    public void onBlockAdded(final World p_149726_1_, final int p_149726_2_, final int p_149726_3_, final int p_149726_4_) {
        if (!p_149726_1_.isClient && p_149726_1_.getTileEntity(p_149726_2_, p_149726_3_, p_149726_4_) == null) {
            this.func_150078_e(p_149726_1_, p_149726_2_, p_149726_3_, p_149726_4_);
        }
    }
    
    private void func_150078_e(final World p_150078_1_, final int p_150078_2_, final int p_150078_3_, final int p_150078_4_) {
        final int var5 = p_150078_1_.getBlockMetadata(p_150078_2_, p_150078_3_, p_150078_4_);
        final int var6 = func_150076_b(var5);
        if (var6 != 7) {
            final boolean var7 = this.func_150072_a(p_150078_1_, p_150078_2_, p_150078_3_, p_150078_4_, var6);
            if (var7 && !func_150075_c(var5)) {
                if (func_150077_h(p_150078_1_, p_150078_2_, p_150078_3_, p_150078_4_, var6)) {
                    p_150078_1_.func_147452_c(p_150078_2_, p_150078_3_, p_150078_4_, this, 0, var6);
                }
            }
            else if (!var7 && func_150075_c(var5)) {
                p_150078_1_.setBlockMetadataWithNotify(p_150078_2_, p_150078_3_, p_150078_4_, var6, 2);
                p_150078_1_.func_147452_c(p_150078_2_, p_150078_3_, p_150078_4_, this, 1, var6);
            }
        }
    }
    
    private boolean func_150072_a(final World p_150072_1_, final int p_150072_2_, final int p_150072_3_, final int p_150072_4_, final int p_150072_5_) {
        return (p_150072_5_ != 0 && p_150072_1_.getIndirectPowerOutput(p_150072_2_, p_150072_3_ - 1, p_150072_4_, 0)) || (p_150072_5_ != 1 && p_150072_1_.getIndirectPowerOutput(p_150072_2_, p_150072_3_ + 1, p_150072_4_, 1)) || (p_150072_5_ != 2 && p_150072_1_.getIndirectPowerOutput(p_150072_2_, p_150072_3_, p_150072_4_ - 1, 2)) || (p_150072_5_ != 3 && p_150072_1_.getIndirectPowerOutput(p_150072_2_, p_150072_3_, p_150072_4_ + 1, 3)) || (p_150072_5_ != 5 && p_150072_1_.getIndirectPowerOutput(p_150072_2_ + 1, p_150072_3_, p_150072_4_, 5)) || (p_150072_5_ != 4 && p_150072_1_.getIndirectPowerOutput(p_150072_2_ - 1, p_150072_3_, p_150072_4_, 4)) || p_150072_1_.getIndirectPowerOutput(p_150072_2_, p_150072_3_, p_150072_4_, 0) || p_150072_1_.getIndirectPowerOutput(p_150072_2_, p_150072_3_ + 2, p_150072_4_, 1) || p_150072_1_.getIndirectPowerOutput(p_150072_2_, p_150072_3_ + 1, p_150072_4_ - 1, 2) || p_150072_1_.getIndirectPowerOutput(p_150072_2_, p_150072_3_ + 1, p_150072_4_ + 1, 3) || p_150072_1_.getIndirectPowerOutput(p_150072_2_ - 1, p_150072_3_ + 1, p_150072_4_, 4) || p_150072_1_.getIndirectPowerOutput(p_150072_2_ + 1, p_150072_3_ + 1, p_150072_4_, 5);
    }
    
    @Override
    public boolean onBlockEventReceived(final World p_149696_1_, int p_149696_2_, int p_149696_3_, int p_149696_4_, final int p_149696_5_, final int p_149696_6_) {
        if (!p_149696_1_.isClient) {
            final boolean var7 = this.func_150072_a(p_149696_1_, p_149696_2_, p_149696_3_, p_149696_4_, p_149696_6_);
            if (var7 && p_149696_5_ == 1) {
                p_149696_1_.setBlockMetadataWithNotify(p_149696_2_, p_149696_3_, p_149696_4_, p_149696_6_ | 0x8, 2);
                return false;
            }
            if (!var7 && p_149696_5_ == 0) {
                return false;
            }
        }
        if (p_149696_5_ == 0) {
            if (!this.func_150079_i(p_149696_1_, p_149696_2_, p_149696_3_, p_149696_4_, p_149696_6_)) {
                return false;
            }
            p_149696_1_.setBlockMetadataWithNotify(p_149696_2_, p_149696_3_, p_149696_4_, p_149696_6_ | 0x8, 2);
            p_149696_1_.playSoundEffect(p_149696_2_ + 0.5, p_149696_3_ + 0.5, p_149696_4_ + 0.5, "tile.piston.out", 0.5f, p_149696_1_.rand.nextFloat() * 0.25f + 0.6f);
        }
        else if (p_149696_5_ == 1) {
            final TileEntity var8 = p_149696_1_.getTileEntity(p_149696_2_ + Facing.offsetsXForSide[p_149696_6_], p_149696_3_ + Facing.offsetsYForSide[p_149696_6_], p_149696_4_ + Facing.offsetsZForSide[p_149696_6_]);
            if (var8 instanceof TileEntityPiston) {
                ((TileEntityPiston)var8).func_145866_f();
            }
            p_149696_1_.setBlock(p_149696_2_, p_149696_3_, p_149696_4_, Blocks.piston_extension, p_149696_6_, 3);
            p_149696_1_.setTileEntity(p_149696_2_, p_149696_3_, p_149696_4_, BlockPistonMoving.func_149962_a(this, p_149696_6_, p_149696_6_, false, true));
            if (this.field_150082_a) {
                final int var9 = p_149696_2_ + Facing.offsetsXForSide[p_149696_6_] * 2;
                final int var10 = p_149696_3_ + Facing.offsetsYForSide[p_149696_6_] * 2;
                final int var11 = p_149696_4_ + Facing.offsetsZForSide[p_149696_6_] * 2;
                Block var12 = p_149696_1_.getBlock(var9, var10, var11);
                int var13 = p_149696_1_.getBlockMetadata(var9, var10, var11);
                boolean var14 = false;
                if (var12 == Blocks.piston_extension) {
                    final TileEntity var15 = p_149696_1_.getTileEntity(var9, var10, var11);
                    if (var15 instanceof TileEntityPiston) {
                        final TileEntityPiston var16 = (TileEntityPiston)var15;
                        if (var16.func_145864_c() == p_149696_6_ && var16.func_145868_b()) {
                            var16.func_145866_f();
                            var12 = var16.func_145861_a();
                            var13 = var16.getBlockMetadata();
                            var14 = true;
                        }
                    }
                }
                if (!var14 && var12.getMaterial() != Material.air && func_150080_a(var12, p_149696_1_, var9, var10, var11, false) && (var12.getMobilityFlag() == 0 || var12 == Blocks.piston || var12 == Blocks.sticky_piston)) {
                    p_149696_2_ += Facing.offsetsXForSide[p_149696_6_];
                    p_149696_3_ += Facing.offsetsYForSide[p_149696_6_];
                    p_149696_4_ += Facing.offsetsZForSide[p_149696_6_];
                    p_149696_1_.setBlock(p_149696_2_, p_149696_3_, p_149696_4_, Blocks.piston_extension, var13, 3);
                    p_149696_1_.setTileEntity(p_149696_2_, p_149696_3_, p_149696_4_, BlockPistonMoving.func_149962_a(var12, var13, p_149696_6_, false, false));
                    p_149696_1_.setBlockToAir(var9, var10, var11);
                }
                else if (!var14) {
                    p_149696_1_.setBlockToAir(p_149696_2_ + Facing.offsetsXForSide[p_149696_6_], p_149696_3_ + Facing.offsetsYForSide[p_149696_6_], p_149696_4_ + Facing.offsetsZForSide[p_149696_6_]);
                }
            }
            else {
                p_149696_1_.setBlockToAir(p_149696_2_ + Facing.offsetsXForSide[p_149696_6_], p_149696_3_ + Facing.offsetsYForSide[p_149696_6_], p_149696_4_ + Facing.offsetsZForSide[p_149696_6_]);
            }
            p_149696_1_.playSoundEffect(p_149696_2_ + 0.5, p_149696_3_ + 0.5, p_149696_4_ + 0.5, "tile.piston.in", 0.5f, p_149696_1_.rand.nextFloat() * 0.15f + 0.6f);
        }
        return true;
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess p_149719_1_, final int p_149719_2_, final int p_149719_3_, final int p_149719_4_) {
        final int var5 = p_149719_1_.getBlockMetadata(p_149719_2_, p_149719_3_, p_149719_4_);
        if (func_150075_c(var5)) {
            final float var6 = 0.25f;
            switch (func_150076_b(var5)) {
                case 0: {
                    this.setBlockBounds(0.0f, 0.25f, 0.0f, 1.0f, 1.0f, 1.0f);
                    break;
                }
                case 1: {
                    this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.75f, 1.0f);
                    break;
                }
                case 2: {
                    this.setBlockBounds(0.0f, 0.0f, 0.25f, 1.0f, 1.0f, 1.0f);
                    break;
                }
                case 3: {
                    this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.75f);
                    break;
                }
                case 4: {
                    this.setBlockBounds(0.25f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
                    break;
                }
                case 5: {
                    this.setBlockBounds(0.0f, 0.0f, 0.0f, 0.75f, 1.0f, 1.0f);
                    break;
                }
            }
        }
        else {
            this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        }
    }
    
    @Override
    public void setBlockBoundsForItemRender() {
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
    }
    
    @Override
    public void addCollisionBoxesToList(final World p_149743_1_, final int p_149743_2_, final int p_149743_3_, final int p_149743_4_, final AxisAlignedBB p_149743_5_, final List p_149743_6_, final Entity p_149743_7_) {
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_);
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(final World p_149668_1_, final int p_149668_2_, final int p_149668_3_, final int p_149668_4_) {
        this.setBlockBoundsBasedOnState(p_149668_1_, p_149668_2_, p_149668_3_, p_149668_4_);
        return super.getCollisionBoundingBoxFromPool(p_149668_1_, p_149668_2_, p_149668_3_, p_149668_4_);
    }
    
    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }
    
    public static int func_150076_b(final int p_150076_0_) {
        return p_150076_0_ & 0x7;
    }
    
    public static boolean func_150075_c(final int p_150075_0_) {
        return (p_150075_0_ & 0x8) != 0x0;
    }
    
    public static int func_150071_a(final World p_150071_0_, final int p_150071_1_, final int p_150071_2_, final int p_150071_3_, final EntityLivingBase p_150071_4_) {
        if (MathHelper.abs((float)p_150071_4_.posX - p_150071_1_) < 2.0f && MathHelper.abs((float)p_150071_4_.posZ - p_150071_3_) < 2.0f) {
            final double var5 = p_150071_4_.posY + 1.82 - p_150071_4_.yOffset;
            if (var5 - p_150071_2_ > 2.0) {
                return 1;
            }
            if (p_150071_2_ - var5 > 0.0) {
                return 0;
            }
        }
        final int var6 = MathHelper.floor_double(p_150071_4_.rotationYaw * 4.0f / 360.0f + 0.5) & 0x3;
        return (var6 == 0) ? 2 : ((var6 == 1) ? 5 : ((var6 == 2) ? 3 : ((var6 == 3) ? 4 : 0)));
    }
    
    private static boolean func_150080_a(final Block p_150080_0_, final World p_150080_1_, final int p_150080_2_, final int p_150080_3_, final int p_150080_4_, final boolean p_150080_5_) {
        if (p_150080_0_ == Blocks.obsidian) {
            return false;
        }
        if (p_150080_0_ != Blocks.piston && p_150080_0_ != Blocks.sticky_piston) {
            if (p_150080_0_.getBlockHardness(p_150080_1_, p_150080_2_, p_150080_3_, p_150080_4_) == -1.0f) {
                return false;
            }
            if (p_150080_0_.getMobilityFlag() == 2) {
                return false;
            }
            if (p_150080_0_.getMobilityFlag() == 1) {
                return p_150080_5_;
            }
        }
        else if (func_150075_c(p_150080_1_.getBlockMetadata(p_150080_2_, p_150080_3_, p_150080_4_))) {
            return false;
        }
        return !(p_150080_0_ instanceof ITileEntityProvider);
    }
    
    private static boolean func_150077_h(final World p_150077_0_, final int p_150077_1_, final int p_150077_2_, final int p_150077_3_, final int p_150077_4_) {
        int var5 = p_150077_1_ + Facing.offsetsXForSide[p_150077_4_];
        int var6 = p_150077_2_ + Facing.offsetsYForSide[p_150077_4_];
        int var7 = p_150077_3_ + Facing.offsetsZForSide[p_150077_4_];
        for (int var8 = 0; var8 < 13; ++var8) {
            if (var6 <= 0 || var6 >= 255) {
                return false;
            }
            final Block var9 = p_150077_0_.getBlock(var5, var6, var7);
            if (var9.getMaterial() == Material.air) {
                break;
            }
            if (!func_150080_a(var9, p_150077_0_, var5, var6, var7, true)) {
                return false;
            }
            if (var9.getMobilityFlag() == 1) {
                break;
            }
            if (var8 == 12) {
                return false;
            }
            var5 += Facing.offsetsXForSide[p_150077_4_];
            var6 += Facing.offsetsYForSide[p_150077_4_];
            var7 += Facing.offsetsZForSide[p_150077_4_];
        }
        return true;
    }
    
    private boolean func_150079_i(final World p_150079_1_, final int p_150079_2_, final int p_150079_3_, final int p_150079_4_, final int p_150079_5_) {
        int var6 = p_150079_2_ + Facing.offsetsXForSide[p_150079_5_];
        int var7 = p_150079_3_ + Facing.offsetsYForSide[p_150079_5_];
        int var8 = p_150079_4_ + Facing.offsetsZForSide[p_150079_5_];
        for (int var9 = 0; var9 < 13; ++var9) {
            if (var7 <= 0 || var7 >= 255) {
                return false;
            }
            final Block var10 = p_150079_1_.getBlock(var6, var7, var8);
            if (var10.getMaterial() == Material.air) {
                break;
            }
            if (!func_150080_a(var10, p_150079_1_, var6, var7, var8, true)) {
                return false;
            }
            if (var10.getMobilityFlag() == 1) {
                var10.dropBlockAsItem(p_150079_1_, var6, var7, var8, p_150079_1_.getBlockMetadata(var6, var7, var8), 0);
                p_150079_1_.setBlockToAir(var6, var7, var8);
                break;
            }
            if (var9 == 12) {
                return false;
            }
            var6 += Facing.offsetsXForSide[p_150079_5_];
            var7 += Facing.offsetsYForSide[p_150079_5_];
            var8 += Facing.offsetsZForSide[p_150079_5_];
        }
        int var9 = var6;
        final int var11 = var7;
        final int var12 = var8;
        int var13 = 0;
        final Block[] var14 = new Block[13];
        while (var6 != p_150079_2_ || var7 != p_150079_3_ || var8 != p_150079_4_) {
            final int var15 = var6 - Facing.offsetsXForSide[p_150079_5_];
            final int var16 = var7 - Facing.offsetsYForSide[p_150079_5_];
            final int var17 = var8 - Facing.offsetsZForSide[p_150079_5_];
            final Block var18 = p_150079_1_.getBlock(var15, var16, var17);
            final int var19 = p_150079_1_.getBlockMetadata(var15, var16, var17);
            if (var18 == this && var15 == p_150079_2_ && var16 == p_150079_3_ && var17 == p_150079_4_) {
                p_150079_1_.setBlock(var6, var7, var8, Blocks.piston_extension, p_150079_5_ | (this.field_150082_a ? 8 : 0), 4);
                p_150079_1_.setTileEntity(var6, var7, var8, BlockPistonMoving.func_149962_a(Blocks.piston_head, p_150079_5_ | (this.field_150082_a ? 8 : 0), p_150079_5_, true, false));
            }
            else {
                p_150079_1_.setBlock(var6, var7, var8, Blocks.piston_extension, var19, 4);
                p_150079_1_.setTileEntity(var6, var7, var8, BlockPistonMoving.func_149962_a(var18, var19, p_150079_5_, true, false));
            }
            var14[var13++] = var18;
            var6 = var15;
            var7 = var16;
            var8 = var17;
        }
        var6 = var9;
        var7 = var11;
        var8 = var12;
        var13 = 0;
        while (var6 != p_150079_2_ || var7 != p_150079_3_ || var8 != p_150079_4_) {
            final int var15 = var6 - Facing.offsetsXForSide[p_150079_5_];
            final int var16 = var7 - Facing.offsetsYForSide[p_150079_5_];
            final int var17 = var8 - Facing.offsetsZForSide[p_150079_5_];
            p_150079_1_.notifyBlocksOfNeighborChange(var15, var16, var17, var14[var13++]);
            var6 = var15;
            var7 = var16;
            var8 = var17;
        }
        return true;
    }
}
