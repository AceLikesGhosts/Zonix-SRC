package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.renderer.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.util.*;

public class BlockDoor extends Block
{
    private IIcon[] field_150017_a;
    private IIcon[] field_150016_b;
    private static final String __OBFID = "CL_00000230";
    
    protected BlockDoor(final Material p_i45402_1_) {
        super(p_i45402_1_);
        final float var2 = 0.5f;
        final float var3 = 1.0f;
        this.setBlockBounds(0.5f - var2, 0.0f, 0.5f - var2, 0.5f + var2, var3, 0.5f + var2);
    }
    
    @Override
    public IIcon getIcon(final int p_149691_1_, final int p_149691_2_) {
        return this.field_150016_b[0];
    }
    
    @Override
    public IIcon getIcon(final IBlockAccess p_149673_1_, final int p_149673_2_, final int p_149673_3_, final int p_149673_4_, final int p_149673_5_) {
        if (p_149673_5_ != 1 && p_149673_5_ != 0) {
            final int var6 = this.func_150012_g(p_149673_1_, p_149673_2_, p_149673_3_, p_149673_4_);
            final int var7 = var6 & 0x3;
            final boolean var8 = (var6 & 0x4) != 0x0;
            boolean var9 = false;
            final boolean var10 = (var6 & 0x8) != 0x0;
            if (var8) {
                if (var7 == 0 && p_149673_5_ == 2) {
                    var9 = !var9;
                }
                else if (var7 == 1 && p_149673_5_ == 5) {
                    var9 = !var9;
                }
                else if (var7 == 2 && p_149673_5_ == 3) {
                    var9 = !var9;
                }
                else if (var7 == 3 && p_149673_5_ == 4) {
                    var9 = !var9;
                }
            }
            else {
                if (var7 == 0 && p_149673_5_ == 5) {
                    var9 = !var9;
                }
                else if (var7 == 1 && p_149673_5_ == 3) {
                    var9 = !var9;
                }
                else if (var7 == 2 && p_149673_5_ == 4) {
                    var9 = !var9;
                }
                else if (var7 == 3 && p_149673_5_ == 2) {
                    var9 = !var9;
                }
                if ((var6 & 0x10) != 0x0) {
                    var9 = !var9;
                }
            }
            return var10 ? this.field_150017_a[var9] : this.field_150016_b[var9];
        }
        return this.field_150016_b[0];
    }
    
    @Override
    public void registerBlockIcons(final IIconRegister p_149651_1_) {
        this.field_150017_a = new IIcon[2];
        this.field_150016_b = new IIcon[2];
        this.field_150017_a[0] = p_149651_1_.registerIcon(this.getTextureName() + "_upper");
        this.field_150016_b[0] = p_149651_1_.registerIcon(this.getTextureName() + "_lower");
        this.field_150017_a[1] = new IconFlipped(this.field_150017_a[0], true, false);
        this.field_150016_b[1] = new IconFlipped(this.field_150016_b[0], true, false);
    }
    
    @Override
    public boolean isOpaqueCube() {
        return false;
    }
    
    @Override
    public boolean getBlocksMovement(final IBlockAccess p_149655_1_, final int p_149655_2_, final int p_149655_3_, final int p_149655_4_) {
        final int var5 = this.func_150012_g(p_149655_1_, p_149655_2_, p_149655_3_, p_149655_4_);
        return (var5 & 0x4) != 0x0;
    }
    
    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }
    
    @Override
    public int getRenderType() {
        return 7;
    }
    
    @Override
    public AxisAlignedBB getSelectedBoundingBoxFromPool(final World p_149633_1_, final int p_149633_2_, final int p_149633_3_, final int p_149633_4_) {
        this.setBlockBoundsBasedOnState(p_149633_1_, p_149633_2_, p_149633_3_, p_149633_4_);
        return super.getSelectedBoundingBoxFromPool(p_149633_1_, p_149633_2_, p_149633_3_, p_149633_4_);
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(final World p_149668_1_, final int p_149668_2_, final int p_149668_3_, final int p_149668_4_) {
        this.setBlockBoundsBasedOnState(p_149668_1_, p_149668_2_, p_149668_3_, p_149668_4_);
        return super.getCollisionBoundingBoxFromPool(p_149668_1_, p_149668_2_, p_149668_3_, p_149668_4_);
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess p_149719_1_, final int p_149719_2_, final int p_149719_3_, final int p_149719_4_) {
        this.func_150011_b(this.func_150012_g(p_149719_1_, p_149719_2_, p_149719_3_, p_149719_4_));
    }
    
    public int func_150013_e(final IBlockAccess p_150013_1_, final int p_150013_2_, final int p_150013_3_, final int p_150013_4_) {
        return this.func_150012_g(p_150013_1_, p_150013_2_, p_150013_3_, p_150013_4_) & 0x3;
    }
    
    public boolean func_150015_f(final IBlockAccess p_150015_1_, final int p_150015_2_, final int p_150015_3_, final int p_150015_4_) {
        return (this.func_150012_g(p_150015_1_, p_150015_2_, p_150015_3_, p_150015_4_) & 0x4) != 0x0;
    }
    
    private void func_150011_b(final int p_150011_1_) {
        final float var2 = 0.1875f;
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 2.0f, 1.0f);
        final int var3 = p_150011_1_ & 0x3;
        final boolean var4 = (p_150011_1_ & 0x4) != 0x0;
        final boolean var5 = (p_150011_1_ & 0x10) != 0x0;
        if (var3 == 0) {
            if (var4) {
                if (!var5) {
                    this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, var2);
                }
                else {
                    this.setBlockBounds(0.0f, 0.0f, 1.0f - var2, 1.0f, 1.0f, 1.0f);
                }
            }
            else {
                this.setBlockBounds(0.0f, 0.0f, 0.0f, var2, 1.0f, 1.0f);
            }
        }
        else if (var3 == 1) {
            if (var4) {
                if (!var5) {
                    this.setBlockBounds(1.0f - var2, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
                }
                else {
                    this.setBlockBounds(0.0f, 0.0f, 0.0f, var2, 1.0f, 1.0f);
                }
            }
            else {
                this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, var2);
            }
        }
        else if (var3 == 2) {
            if (var4) {
                if (!var5) {
                    this.setBlockBounds(0.0f, 0.0f, 1.0f - var2, 1.0f, 1.0f, 1.0f);
                }
                else {
                    this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, var2);
                }
            }
            else {
                this.setBlockBounds(1.0f - var2, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
            }
        }
        else if (var3 == 3) {
            if (var4) {
                if (!var5) {
                    this.setBlockBounds(0.0f, 0.0f, 0.0f, var2, 1.0f, 1.0f);
                }
                else {
                    this.setBlockBounds(1.0f - var2, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
                }
            }
            else {
                this.setBlockBounds(0.0f, 0.0f, 1.0f - var2, 1.0f, 1.0f, 1.0f);
            }
        }
    }
    
    @Override
    public void onBlockClicked(final World p_149699_1_, final int p_149699_2_, final int p_149699_3_, final int p_149699_4_, final EntityPlayer p_149699_5_) {
    }
    
    @Override
    public boolean onBlockActivated(final World p_149727_1_, final int p_149727_2_, final int p_149727_3_, final int p_149727_4_, final EntityPlayer p_149727_5_, final int p_149727_6_, final float p_149727_7_, final float p_149727_8_, final float p_149727_9_) {
        if (this.blockMaterial == Material.iron) {
            return true;
        }
        final int var10 = this.func_150012_g(p_149727_1_, p_149727_2_, p_149727_3_, p_149727_4_);
        int var11 = var10 & 0x7;
        var11 ^= 0x4;
        if ((var10 & 0x8) == 0x0) {
            p_149727_1_.setBlockMetadataWithNotify(p_149727_2_, p_149727_3_, p_149727_4_, var11, 2);
            p_149727_1_.markBlockRangeForRenderUpdate(p_149727_2_, p_149727_3_, p_149727_4_, p_149727_2_, p_149727_3_, p_149727_4_);
        }
        else {
            p_149727_1_.setBlockMetadataWithNotify(p_149727_2_, p_149727_3_ - 1, p_149727_4_, var11, 2);
            p_149727_1_.markBlockRangeForRenderUpdate(p_149727_2_, p_149727_3_ - 1, p_149727_4_, p_149727_2_, p_149727_3_, p_149727_4_);
        }
        p_149727_1_.playAuxSFXAtEntity(p_149727_5_, 1003, p_149727_2_, p_149727_3_, p_149727_4_, 0);
        return true;
    }
    
    public void func_150014_a(final World p_150014_1_, final int p_150014_2_, final int p_150014_3_, final int p_150014_4_, final boolean p_150014_5_) {
        final int var6 = this.func_150012_g(p_150014_1_, p_150014_2_, p_150014_3_, p_150014_4_);
        final boolean var7 = (var6 & 0x4) != 0x0;
        if (var7 != p_150014_5_) {
            int var8 = var6 & 0x7;
            var8 ^= 0x4;
            if ((var6 & 0x8) == 0x0) {
                p_150014_1_.setBlockMetadataWithNotify(p_150014_2_, p_150014_3_, p_150014_4_, var8, 2);
                p_150014_1_.markBlockRangeForRenderUpdate(p_150014_2_, p_150014_3_, p_150014_4_, p_150014_2_, p_150014_3_, p_150014_4_);
            }
            else {
                p_150014_1_.setBlockMetadataWithNotify(p_150014_2_, p_150014_3_ - 1, p_150014_4_, var8, 2);
                p_150014_1_.markBlockRangeForRenderUpdate(p_150014_2_, p_150014_3_ - 1, p_150014_4_, p_150014_2_, p_150014_3_, p_150014_4_);
            }
            p_150014_1_.playAuxSFXAtEntity(null, 1003, p_150014_2_, p_150014_3_, p_150014_4_, 0);
        }
    }
    
    @Override
    public void onNeighborBlockChange(final World p_149695_1_, final int p_149695_2_, final int p_149695_3_, final int p_149695_4_, final Block p_149695_5_) {
        final int var6 = p_149695_1_.getBlockMetadata(p_149695_2_, p_149695_3_, p_149695_4_);
        if ((var6 & 0x8) == 0x0) {
            boolean var7 = false;
            if (p_149695_1_.getBlock(p_149695_2_, p_149695_3_ + 1, p_149695_4_) != this) {
                p_149695_1_.setBlockToAir(p_149695_2_, p_149695_3_, p_149695_4_);
                var7 = true;
            }
            if (!World.doesBlockHaveSolidTopSurface(p_149695_1_, p_149695_2_, p_149695_3_ - 1, p_149695_4_)) {
                p_149695_1_.setBlockToAir(p_149695_2_, p_149695_3_, p_149695_4_);
                var7 = true;
                if (p_149695_1_.getBlock(p_149695_2_, p_149695_3_ + 1, p_149695_4_) == this) {
                    p_149695_1_.setBlockToAir(p_149695_2_, p_149695_3_ + 1, p_149695_4_);
                }
            }
            if (var7) {
                if (!p_149695_1_.isClient) {
                    this.dropBlockAsItem(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_, var6, 0);
                }
            }
            else {
                final boolean var8 = p_149695_1_.isBlockIndirectlyGettingPowered(p_149695_2_, p_149695_3_, p_149695_4_) || p_149695_1_.isBlockIndirectlyGettingPowered(p_149695_2_, p_149695_3_ + 1, p_149695_4_);
                if ((var8 || p_149695_5_.canProvidePower()) && p_149695_5_ != this) {
                    this.func_150014_a(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_, var8);
                }
            }
        }
        else {
            if (p_149695_1_.getBlock(p_149695_2_, p_149695_3_ - 1, p_149695_4_) != this) {
                p_149695_1_.setBlockToAir(p_149695_2_, p_149695_3_, p_149695_4_);
            }
            if (p_149695_5_ != this) {
                this.onNeighborBlockChange(p_149695_1_, p_149695_2_, p_149695_3_ - 1, p_149695_4_, p_149695_5_);
            }
        }
    }
    
    @Override
    public Item getItemDropped(final int p_149650_1_, final Random p_149650_2_, final int p_149650_3_) {
        return ((p_149650_1_ & 0x8) != 0x0) ? null : ((this.blockMaterial == Material.iron) ? Items.iron_door : Items.wooden_door);
    }
    
    @Override
    public MovingObjectPosition collisionRayTrace(final World p_149731_1_, final int p_149731_2_, final int p_149731_3_, final int p_149731_4_, final Vec3 p_149731_5_, final Vec3 p_149731_6_) {
        this.setBlockBoundsBasedOnState(p_149731_1_, p_149731_2_, p_149731_3_, p_149731_4_);
        return super.collisionRayTrace(p_149731_1_, p_149731_2_, p_149731_3_, p_149731_4_, p_149731_5_, p_149731_6_);
    }
    
    @Override
    public boolean canPlaceBlockAt(final World p_149742_1_, final int p_149742_2_, final int p_149742_3_, final int p_149742_4_) {
        return p_149742_3_ < 255 && (World.doesBlockHaveSolidTopSurface(p_149742_1_, p_149742_2_, p_149742_3_ - 1, p_149742_4_) && super.canPlaceBlockAt(p_149742_1_, p_149742_2_, p_149742_3_, p_149742_4_) && super.canPlaceBlockAt(p_149742_1_, p_149742_2_, p_149742_3_ + 1, p_149742_4_));
    }
    
    @Override
    public int getMobilityFlag() {
        return 1;
    }
    
    public int func_150012_g(final IBlockAccess p_150012_1_, final int p_150012_2_, final int p_150012_3_, final int p_150012_4_) {
        final int var5 = p_150012_1_.getBlockMetadata(p_150012_2_, p_150012_3_, p_150012_4_);
        final boolean var6 = (var5 & 0x8) != 0x0;
        int var7;
        int var8;
        if (var6) {
            var7 = p_150012_1_.getBlockMetadata(p_150012_2_, p_150012_3_ - 1, p_150012_4_);
            var8 = var5;
        }
        else {
            var7 = var5;
            var8 = p_150012_1_.getBlockMetadata(p_150012_2_, p_150012_3_ + 1, p_150012_4_);
        }
        final boolean var9 = (var8 & 0x1) != 0x0;
        return (var7 & 0x7) | (var6 ? 8 : 0) | (var9 ? 16 : 0);
    }
    
    @Override
    public Item getItem(final World p_149694_1_, final int p_149694_2_, final int p_149694_3_, final int p_149694_4_) {
        return (this.blockMaterial == Material.iron) ? Items.iron_door : Items.wooden_door;
    }
    
    @Override
    public void onBlockHarvested(final World p_149681_1_, final int p_149681_2_, final int p_149681_3_, final int p_149681_4_, final int p_149681_5_, final EntityPlayer p_149681_6_) {
        if (p_149681_6_.capabilities.isCreativeMode && (p_149681_5_ & 0x8) != 0x0 && p_149681_1_.getBlock(p_149681_2_, p_149681_3_ - 1, p_149681_4_) == this) {
            p_149681_1_.setBlockToAir(p_149681_2_, p_149681_3_ - 1, p_149681_4_);
        }
    }
}
