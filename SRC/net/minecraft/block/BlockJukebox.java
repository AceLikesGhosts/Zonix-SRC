package net.minecraft.block;

import net.minecraft.util.*;
import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.*;
import net.minecraft.tileentity.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.nbt.*;

public class BlockJukebox extends BlockContainer
{
    private IIcon field_149927_a;
    private static final String __OBFID = "CL_00000260";
    
    protected BlockJukebox() {
        super(Material.wood);
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }
    
    @Override
    public IIcon getIcon(final int p_149691_1_, final int p_149691_2_) {
        return (p_149691_1_ == 1) ? this.field_149927_a : this.blockIcon;
    }
    
    @Override
    public boolean onBlockActivated(final World p_149727_1_, final int p_149727_2_, final int p_149727_3_, final int p_149727_4_, final EntityPlayer p_149727_5_, final int p_149727_6_, final float p_149727_7_, final float p_149727_8_, final float p_149727_9_) {
        if (p_149727_1_.getBlockMetadata(p_149727_2_, p_149727_3_, p_149727_4_) == 0) {
            return false;
        }
        this.func_149925_e(p_149727_1_, p_149727_2_, p_149727_3_, p_149727_4_);
        return true;
    }
    
    public void func_149926_b(final World p_149926_1_, final int p_149926_2_, final int p_149926_3_, final int p_149926_4_, final ItemStack p_149926_5_) {
        if (!p_149926_1_.isClient) {
            final TileEntityJukebox var6 = (TileEntityJukebox)p_149926_1_.getTileEntity(p_149926_2_, p_149926_3_, p_149926_4_);
            if (var6 != null) {
                var6.func_145857_a(p_149926_5_.copy());
                p_149926_1_.setBlockMetadataWithNotify(p_149926_2_, p_149926_3_, p_149926_4_, 1, 2);
            }
        }
    }
    
    public void func_149925_e(final World p_149925_1_, final int p_149925_2_, final int p_149925_3_, final int p_149925_4_) {
        if (!p_149925_1_.isClient) {
            final TileEntityJukebox var5 = (TileEntityJukebox)p_149925_1_.getTileEntity(p_149925_2_, p_149925_3_, p_149925_4_);
            if (var5 != null) {
                final ItemStack var6 = var5.func_145856_a();
                if (var6 != null) {
                    p_149925_1_.playAuxSFX(1005, p_149925_2_, p_149925_3_, p_149925_4_, 0);
                    p_149925_1_.playRecord(null, p_149925_2_, p_149925_3_, p_149925_4_);
                    var5.func_145857_a(null);
                    p_149925_1_.setBlockMetadataWithNotify(p_149925_2_, p_149925_3_, p_149925_4_, 0, 2);
                    final float var7 = 0.7f;
                    final double var8 = p_149925_1_.rand.nextFloat() * var7 + (1.0f - var7) * 0.5;
                    final double var9 = p_149925_1_.rand.nextFloat() * var7 + (1.0f - var7) * 0.2 + 0.6;
                    final double var10 = p_149925_1_.rand.nextFloat() * var7 + (1.0f - var7) * 0.5;
                    final ItemStack var11 = var6.copy();
                    final EntityItem var12 = new EntityItem(p_149925_1_, p_149925_2_ + var8, p_149925_3_ + var9, p_149925_4_ + var10, var11);
                    var12.delayBeforeCanPickup = 10;
                    p_149925_1_.spawnEntityInWorld(var12);
                }
            }
        }
    }
    
    @Override
    public void breakBlock(final World p_149749_1_, final int p_149749_2_, final int p_149749_3_, final int p_149749_4_, final Block p_149749_5_, final int p_149749_6_) {
        this.func_149925_e(p_149749_1_, p_149749_2_, p_149749_3_, p_149749_4_);
        super.breakBlock(p_149749_1_, p_149749_2_, p_149749_3_, p_149749_4_, p_149749_5_, p_149749_6_);
    }
    
    @Override
    public void dropBlockAsItemWithChance(final World p_149690_1_, final int p_149690_2_, final int p_149690_3_, final int p_149690_4_, final int p_149690_5_, final float p_149690_6_, final int p_149690_7_) {
        if (!p_149690_1_.isClient) {
            super.dropBlockAsItemWithChance(p_149690_1_, p_149690_2_, p_149690_3_, p_149690_4_, p_149690_5_, p_149690_6_, 0);
        }
    }
    
    @Override
    public TileEntity createNewTileEntity(final World p_149915_1_, final int p_149915_2_) {
        return new TileEntityJukebox();
    }
    
    @Override
    public void registerBlockIcons(final IIconRegister p_149651_1_) {
        this.blockIcon = p_149651_1_.registerIcon(this.getTextureName() + "_side");
        this.field_149927_a = p_149651_1_.registerIcon(this.getTextureName() + "_top");
    }
    
    @Override
    public boolean hasComparatorInputOverride() {
        return true;
    }
    
    @Override
    public int getComparatorInputOverride(final World p_149736_1_, final int p_149736_2_, final int p_149736_3_, final int p_149736_4_, final int p_149736_5_) {
        final ItemStack var6 = ((TileEntityJukebox)p_149736_1_.getTileEntity(p_149736_2_, p_149736_3_, p_149736_4_)).func_145856_a();
        return (var6 == null) ? 0 : (Item.getIdFromItem(var6.getItem()) + 1 - Item.getIdFromItem(Items.record_13));
    }
    
    public static class TileEntityJukebox extends TileEntity
    {
        private ItemStack field_145858_a;
        private static final String __OBFID = "CL_00000261";
        
        @Override
        public void readFromNBT(final NBTTagCompound p_145839_1_) {
            super.readFromNBT(p_145839_1_);
            if (p_145839_1_.func_150297_b("RecordItem", 10)) {
                this.func_145857_a(ItemStack.loadItemStackFromNBT(p_145839_1_.getCompoundTag("RecordItem")));
            }
            else if (p_145839_1_.getInteger("Record") > 0) {
                this.func_145857_a(new ItemStack(Item.getItemById(p_145839_1_.getInteger("Record")), 1, 0));
            }
        }
        
        @Override
        public void writeToNBT(final NBTTagCompound p_145841_1_) {
            super.writeToNBT(p_145841_1_);
            if (this.func_145856_a() != null) {
                p_145841_1_.setTag("RecordItem", this.func_145856_a().writeToNBT(new NBTTagCompound()));
                p_145841_1_.setInteger("Record", Item.getIdFromItem(this.func_145856_a().getItem()));
            }
        }
        
        public ItemStack func_145856_a() {
            return this.field_145858_a;
        }
        
        public void func_145857_a(final ItemStack p_145857_1_) {
            this.field_145858_a = p_145857_1_;
            this.onInventoryChanged();
        }
    }
}
