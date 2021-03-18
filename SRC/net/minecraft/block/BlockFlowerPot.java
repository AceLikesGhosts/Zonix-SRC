package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.world.*;
import java.util.*;
import net.minecraft.tileentity.*;

public class BlockFlowerPot extends BlockContainer
{
    private static final String __OBFID = "CL_00000247";
    
    public BlockFlowerPot() {
        super(Material.circuits);
        this.setBlockBoundsForItemRender();
    }
    
    @Override
    public void setBlockBoundsForItemRender() {
        final float var1 = 0.375f;
        final float var2 = var1 / 2.0f;
        this.setBlockBounds(0.5f - var2, 0.0f, 0.5f - var2, 0.5f + var2, var1, 0.5f + var2);
    }
    
    @Override
    public boolean isOpaqueCube() {
        return false;
    }
    
    @Override
    public int getRenderType() {
        return 33;
    }
    
    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }
    
    @Override
    public boolean onBlockActivated(final World p_149727_1_, final int p_149727_2_, final int p_149727_3_, final int p_149727_4_, final EntityPlayer p_149727_5_, final int p_149727_6_, final float p_149727_7_, final float p_149727_8_, final float p_149727_9_) {
        final ItemStack var10 = p_149727_5_.inventory.getCurrentItem();
        if (var10 == null || !(var10.getItem() instanceof ItemBlock)) {
            return false;
        }
        final TileEntityFlowerPot var11 = this.func_149929_e(p_149727_1_, p_149727_2_, p_149727_3_, p_149727_4_);
        if (var11 == null) {
            return false;
        }
        if (var11.func_145965_a() != null) {
            return false;
        }
        final Block var12 = Block.getBlockFromItem(var10.getItem());
        if (!this.func_149928_a(var12, var10.getItemDamage())) {
            return false;
        }
        var11.func_145964_a(var10.getItem(), var10.getItemDamage());
        var11.onInventoryChanged();
        if (!p_149727_1_.setBlockMetadataWithNotify(p_149727_2_, p_149727_3_, p_149727_4_, var10.getItemDamage(), 2)) {
            p_149727_1_.func_147471_g(p_149727_2_, p_149727_3_, p_149727_4_);
        }
        if (!p_149727_5_.capabilities.isCreativeMode) {
            final ItemStack itemStack = var10;
            if (--itemStack.stackSize <= 0) {
                p_149727_5_.inventory.setInventorySlotContents(p_149727_5_.inventory.currentItem, null);
            }
        }
        return true;
    }
    
    private boolean func_149928_a(final Block p_149928_1_, final int p_149928_2_) {
        return p_149928_1_ == Blocks.yellow_flower || p_149928_1_ == Blocks.red_flower || p_149928_1_ == Blocks.cactus || p_149928_1_ == Blocks.brown_mushroom || p_149928_1_ == Blocks.red_mushroom || p_149928_1_ == Blocks.sapling || p_149928_1_ == Blocks.deadbush || (p_149928_1_ == Blocks.tallgrass && p_149928_2_ == 2);
    }
    
    @Override
    public Item getItem(final World p_149694_1_, final int p_149694_2_, final int p_149694_3_, final int p_149694_4_) {
        final TileEntityFlowerPot var5 = this.func_149929_e(p_149694_1_, p_149694_2_, p_149694_3_, p_149694_4_);
        return (var5 != null && var5.func_145965_a() != null) ? var5.func_145965_a() : Items.flower_pot;
    }
    
    @Override
    public int getDamageValue(final World p_149643_1_, final int p_149643_2_, final int p_149643_3_, final int p_149643_4_) {
        final TileEntityFlowerPot var5 = this.func_149929_e(p_149643_1_, p_149643_2_, p_149643_3_, p_149643_4_);
        return (var5 != null && var5.func_145965_a() != null) ? var5.func_145966_b() : 0;
    }
    
    @Override
    public boolean isFlowerPot() {
        return true;
    }
    
    @Override
    public boolean canPlaceBlockAt(final World p_149742_1_, final int p_149742_2_, final int p_149742_3_, final int p_149742_4_) {
        return super.canPlaceBlockAt(p_149742_1_, p_149742_2_, p_149742_3_, p_149742_4_) && World.doesBlockHaveSolidTopSurface(p_149742_1_, p_149742_2_, p_149742_3_ - 1, p_149742_4_);
    }
    
    @Override
    public void onNeighborBlockChange(final World p_149695_1_, final int p_149695_2_, final int p_149695_3_, final int p_149695_4_, final Block p_149695_5_) {
        if (!World.doesBlockHaveSolidTopSurface(p_149695_1_, p_149695_2_, p_149695_3_ - 1, p_149695_4_)) {
            this.dropBlockAsItem(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_, p_149695_1_.getBlockMetadata(p_149695_2_, p_149695_3_, p_149695_4_), 0);
            p_149695_1_.setBlockToAir(p_149695_2_, p_149695_3_, p_149695_4_);
        }
    }
    
    @Override
    public void breakBlock(final World p_149749_1_, final int p_149749_2_, final int p_149749_3_, final int p_149749_4_, final Block p_149749_5_, final int p_149749_6_) {
        final TileEntityFlowerPot var7 = this.func_149929_e(p_149749_1_, p_149749_2_, p_149749_3_, p_149749_4_);
        if (var7 != null && var7.func_145965_a() != null) {
            this.dropBlockAsItem_do(p_149749_1_, p_149749_2_, p_149749_3_, p_149749_4_, new ItemStack(var7.func_145965_a(), 1, var7.func_145966_b()));
        }
        super.breakBlock(p_149749_1_, p_149749_2_, p_149749_3_, p_149749_4_, p_149749_5_, p_149749_6_);
    }
    
    @Override
    public void onBlockHarvested(final World p_149681_1_, final int p_149681_2_, final int p_149681_3_, final int p_149681_4_, final int p_149681_5_, final EntityPlayer p_149681_6_) {
        super.onBlockHarvested(p_149681_1_, p_149681_2_, p_149681_3_, p_149681_4_, p_149681_5_, p_149681_6_);
        if (p_149681_6_.capabilities.isCreativeMode) {
            final TileEntityFlowerPot var7 = this.func_149929_e(p_149681_1_, p_149681_2_, p_149681_3_, p_149681_4_);
            if (var7 != null) {
                var7.func_145964_a(Item.getItemById(0), 0);
            }
        }
    }
    
    @Override
    public Item getItemDropped(final int p_149650_1_, final Random p_149650_2_, final int p_149650_3_) {
        return Items.flower_pot;
    }
    
    private TileEntityFlowerPot func_149929_e(final World p_149929_1_, final int p_149929_2_, final int p_149929_3_, final int p_149929_4_) {
        final TileEntity var5 = p_149929_1_.getTileEntity(p_149929_2_, p_149929_3_, p_149929_4_);
        return (var5 != null && var5 instanceof TileEntityFlowerPot) ? ((TileEntityFlowerPot)var5) : null;
    }
    
    @Override
    public TileEntity createNewTileEntity(final World p_149915_1_, final int p_149915_2_) {
        Object var3 = null;
        byte var4 = 0;
        switch (p_149915_2_) {
            case 1: {
                var3 = Blocks.red_flower;
                var4 = 0;
                break;
            }
            case 2: {
                var3 = Blocks.yellow_flower;
                break;
            }
            case 3: {
                var3 = Blocks.sapling;
                var4 = 0;
                break;
            }
            case 4: {
                var3 = Blocks.sapling;
                var4 = 1;
                break;
            }
            case 5: {
                var3 = Blocks.sapling;
                var4 = 2;
                break;
            }
            case 6: {
                var3 = Blocks.sapling;
                var4 = 3;
                break;
            }
            case 7: {
                var3 = Blocks.red_mushroom;
                break;
            }
            case 8: {
                var3 = Blocks.brown_mushroom;
                break;
            }
            case 9: {
                var3 = Blocks.cactus;
                break;
            }
            case 10: {
                var3 = Blocks.deadbush;
                break;
            }
            case 11: {
                var3 = Blocks.tallgrass;
                var4 = 2;
                break;
            }
            case 12: {
                var3 = Blocks.sapling;
                var4 = 4;
                break;
            }
            case 13: {
                var3 = Blocks.sapling;
                var4 = 5;
                break;
            }
        }
        return new TileEntityFlowerPot(Item.getItemFromBlock((Block)var3), var4);
    }
}
