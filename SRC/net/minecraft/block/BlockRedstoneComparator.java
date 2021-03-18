package net.minecraft.block;

import java.util.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.tileentity.*;

public class BlockRedstoneComparator extends BlockRedstoneDiode implements ITileEntityProvider
{
    private static final String __OBFID = "CL_00000220";
    
    public BlockRedstoneComparator(final boolean p_i45399_1_) {
        super(p_i45399_1_);
        this.isBlockContainer = true;
    }
    
    @Override
    public Item getItemDropped(final int p_149650_1_, final Random p_149650_2_, final int p_149650_3_) {
        return Items.comparator;
    }
    
    @Override
    public Item getItem(final World p_149694_1_, final int p_149694_2_, final int p_149694_3_, final int p_149694_4_) {
        return Items.comparator;
    }
    
    @Override
    protected int func_149901_b(final int p_149901_1_) {
        return 2;
    }
    
    @Override
    protected BlockRedstoneDiode func_149906_e() {
        return Blocks.powered_comparator;
    }
    
    @Override
    protected BlockRedstoneDiode func_149898_i() {
        return Blocks.unpowered_comparator;
    }
    
    @Override
    public int getRenderType() {
        return 37;
    }
    
    @Override
    public IIcon getIcon(final int p_149691_1_, final int p_149691_2_) {
        final boolean var3 = this.field_149914_a || (p_149691_2_ & 0x8) != 0x0;
        return (p_149691_1_ == 0) ? (var3 ? Blocks.redstone_torch.getBlockTextureFromSide(p_149691_1_) : Blocks.unlit_redstone_torch.getBlockTextureFromSide(p_149691_1_)) : ((p_149691_1_ == 1) ? (var3 ? Blocks.powered_comparator.blockIcon : this.blockIcon) : Blocks.double_stone_slab.getBlockTextureFromSide(1));
    }
    
    @Override
    protected boolean func_149905_c(final int p_149905_1_) {
        return this.field_149914_a || (p_149905_1_ & 0x8) != 0x0;
    }
    
    @Override
    protected int func_149904_f(final IBlockAccess p_149904_1_, final int p_149904_2_, final int p_149904_3_, final int p_149904_4_, final int p_149904_5_) {
        return this.func_149971_e(p_149904_1_, p_149904_2_, p_149904_3_, p_149904_4_).func_145996_a();
    }
    
    private int func_149970_j(final World p_149970_1_, final int p_149970_2_, final int p_149970_3_, final int p_149970_4_, final int p_149970_5_) {
        return this.func_149969_d(p_149970_5_) ? Math.max(this.func_149903_h(p_149970_1_, p_149970_2_, p_149970_3_, p_149970_4_, p_149970_5_) - this.func_149902_h(p_149970_1_, p_149970_2_, p_149970_3_, p_149970_4_, p_149970_5_), 0) : this.func_149903_h(p_149970_1_, p_149970_2_, p_149970_3_, p_149970_4_, p_149970_5_);
    }
    
    public boolean func_149969_d(final int p_149969_1_) {
        return (p_149969_1_ & 0x4) == 0x4;
    }
    
    @Override
    protected boolean func_149900_a(final World p_149900_1_, final int p_149900_2_, final int p_149900_3_, final int p_149900_4_, final int p_149900_5_) {
        final int var6 = this.func_149903_h(p_149900_1_, p_149900_2_, p_149900_3_, p_149900_4_, p_149900_5_);
        if (var6 >= 15) {
            return true;
        }
        if (var6 == 0) {
            return false;
        }
        final int var7 = this.func_149902_h(p_149900_1_, p_149900_2_, p_149900_3_, p_149900_4_, p_149900_5_);
        return var7 == 0 || var6 >= var7;
    }
    
    @Override
    protected int func_149903_h(final World p_149903_1_, final int p_149903_2_, final int p_149903_3_, final int p_149903_4_, final int p_149903_5_) {
        int var6 = super.func_149903_h(p_149903_1_, p_149903_2_, p_149903_3_, p_149903_4_, p_149903_5_);
        final int var7 = BlockDirectional.func_149895_l(p_149903_5_);
        int var8 = p_149903_2_ + Direction.offsetX[var7];
        int var9 = p_149903_4_ + Direction.offsetZ[var7];
        Block var10 = p_149903_1_.getBlock(var8, p_149903_3_, var9);
        if (var10.hasComparatorInputOverride()) {
            var6 = var10.getComparatorInputOverride(p_149903_1_, var8, p_149903_3_, var9, Direction.rotateOpposite[var7]);
        }
        else if (var6 < 15 && var10.isNormalCube()) {
            var8 += Direction.offsetX[var7];
            var9 += Direction.offsetZ[var7];
            var10 = p_149903_1_.getBlock(var8, p_149903_3_, var9);
            if (var10.hasComparatorInputOverride()) {
                var6 = var10.getComparatorInputOverride(p_149903_1_, var8, p_149903_3_, var9, Direction.rotateOpposite[var7]);
            }
        }
        return var6;
    }
    
    public TileEntityComparator func_149971_e(final IBlockAccess p_149971_1_, final int p_149971_2_, final int p_149971_3_, final int p_149971_4_) {
        return (TileEntityComparator)p_149971_1_.getTileEntity(p_149971_2_, p_149971_3_, p_149971_4_);
    }
    
    @Override
    public boolean onBlockActivated(final World p_149727_1_, final int p_149727_2_, final int p_149727_3_, final int p_149727_4_, final EntityPlayer p_149727_5_, final int p_149727_6_, final float p_149727_7_, final float p_149727_8_, final float p_149727_9_) {
        final int var10 = p_149727_1_.getBlockMetadata(p_149727_2_, p_149727_3_, p_149727_4_);
        final boolean var11 = this.field_149914_a | (var10 & 0x8) != 0x0;
        final boolean var12 = !this.func_149969_d(var10);
        int var13 = var12 ? 4 : 0;
        var13 |= (var11 ? 8 : 0);
        p_149727_1_.playSoundEffect(p_149727_2_ + 0.5, p_149727_3_ + 0.5, p_149727_4_ + 0.5, "random.click", 0.3f, var12 ? 0.55f : 0.5f);
        p_149727_1_.setBlockMetadataWithNotify(p_149727_2_, p_149727_3_, p_149727_4_, var13 | (var10 & 0x3), 2);
        this.func_149972_c(p_149727_1_, p_149727_2_, p_149727_3_, p_149727_4_, p_149727_1_.rand);
        return true;
    }
    
    @Override
    protected void func_149897_b(final World p_149897_1_, final int p_149897_2_, final int p_149897_3_, final int p_149897_4_, final Block p_149897_5_) {
        if (!p_149897_1_.func_147477_a(p_149897_2_, p_149897_3_, p_149897_4_, this)) {
            final int var6 = p_149897_1_.getBlockMetadata(p_149897_2_, p_149897_3_, p_149897_4_);
            final int var7 = this.func_149970_j(p_149897_1_, p_149897_2_, p_149897_3_, p_149897_4_, var6);
            final int var8 = this.func_149971_e(p_149897_1_, p_149897_2_, p_149897_3_, p_149897_4_).func_145996_a();
            if (var7 != var8 || this.func_149905_c(var6) != this.func_149900_a(p_149897_1_, p_149897_2_, p_149897_3_, p_149897_4_, var6)) {
                if (this.func_149912_i(p_149897_1_, p_149897_2_, p_149897_3_, p_149897_4_, var6)) {
                    p_149897_1_.func_147454_a(p_149897_2_, p_149897_3_, p_149897_4_, this, this.func_149901_b(0), -1);
                }
                else {
                    p_149897_1_.func_147454_a(p_149897_2_, p_149897_3_, p_149897_4_, this, this.func_149901_b(0), 0);
                }
            }
        }
    }
    
    private void func_149972_c(final World p_149972_1_, final int p_149972_2_, final int p_149972_3_, final int p_149972_4_, final Random p_149972_5_) {
        final int var6 = p_149972_1_.getBlockMetadata(p_149972_2_, p_149972_3_, p_149972_4_);
        final int var7 = this.func_149970_j(p_149972_1_, p_149972_2_, p_149972_3_, p_149972_4_, var6);
        final int var8 = this.func_149971_e(p_149972_1_, p_149972_2_, p_149972_3_, p_149972_4_).func_145996_a();
        this.func_149971_e(p_149972_1_, p_149972_2_, p_149972_3_, p_149972_4_).func_145995_a(var7);
        if (var8 != var7 || !this.func_149969_d(var6)) {
            final boolean var9 = this.func_149900_a(p_149972_1_, p_149972_2_, p_149972_3_, p_149972_4_, var6);
            final boolean var10 = this.field_149914_a || (var6 & 0x8) != 0x0;
            if (var10 && !var9) {
                p_149972_1_.setBlockMetadataWithNotify(p_149972_2_, p_149972_3_, p_149972_4_, var6 & 0xFFFFFFF7, 2);
            }
            else if (!var10 && var9) {
                p_149972_1_.setBlockMetadataWithNotify(p_149972_2_, p_149972_3_, p_149972_4_, var6 | 0x8, 2);
            }
            this.func_149911_e(p_149972_1_, p_149972_2_, p_149972_3_, p_149972_4_);
        }
    }
    
    @Override
    public void updateTick(final World p_149674_1_, final int p_149674_2_, final int p_149674_3_, final int p_149674_4_, final Random p_149674_5_) {
        if (this.field_149914_a) {
            final int var6 = p_149674_1_.getBlockMetadata(p_149674_2_, p_149674_3_, p_149674_4_);
            p_149674_1_.setBlock(p_149674_2_, p_149674_3_, p_149674_4_, this.func_149898_i(), var6 | 0x8, 4);
        }
        this.func_149972_c(p_149674_1_, p_149674_2_, p_149674_3_, p_149674_4_, p_149674_5_);
    }
    
    @Override
    public void onBlockAdded(final World p_149726_1_, final int p_149726_2_, final int p_149726_3_, final int p_149726_4_) {
        super.onBlockAdded(p_149726_1_, p_149726_2_, p_149726_3_, p_149726_4_);
        p_149726_1_.setTileEntity(p_149726_2_, p_149726_3_, p_149726_4_, this.createNewTileEntity(p_149726_1_, 0));
    }
    
    @Override
    public void breakBlock(final World p_149749_1_, final int p_149749_2_, final int p_149749_3_, final int p_149749_4_, final Block p_149749_5_, final int p_149749_6_) {
        super.breakBlock(p_149749_1_, p_149749_2_, p_149749_3_, p_149749_4_, p_149749_5_, p_149749_6_);
        p_149749_1_.removeTileEntity(p_149749_2_, p_149749_3_, p_149749_4_);
        this.func_149911_e(p_149749_1_, p_149749_2_, p_149749_3_, p_149749_4_);
    }
    
    @Override
    public boolean onBlockEventReceived(final World p_149696_1_, final int p_149696_2_, final int p_149696_3_, final int p_149696_4_, final int p_149696_5_, final int p_149696_6_) {
        super.onBlockEventReceived(p_149696_1_, p_149696_2_, p_149696_3_, p_149696_4_, p_149696_5_, p_149696_6_);
        final TileEntity var7 = p_149696_1_.getTileEntity(p_149696_2_, p_149696_3_, p_149696_4_);
        return var7 != null && var7.receiveClientEvent(p_149696_5_, p_149696_6_);
    }
    
    @Override
    public TileEntity createNewTileEntity(final World p_149915_1_, final int p_149915_2_) {
        return new TileEntityComparator();
    }
}
