package net.minecraft.block;

import net.minecraft.util.*;
import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import net.minecraft.world.*;
import java.util.*;
import net.minecraft.init.*;
import net.minecraft.tileentity.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.client.renderer.texture.*;

public class BlockEnchantmentTable extends BlockContainer
{
    private IIcon field_149950_a;
    private IIcon field_149949_b;
    private static final String __OBFID = "CL_00000235";
    
    protected BlockEnchantmentTable() {
        super(Material.rock);
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.75f, 1.0f);
        this.setLightOpacity(0);
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }
    
    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }
    
    @Override
    public void randomDisplayTick(final World p_149734_1_, final int p_149734_2_, final int p_149734_3_, final int p_149734_4_, final Random p_149734_5_) {
        super.randomDisplayTick(p_149734_1_, p_149734_2_, p_149734_3_, p_149734_4_, p_149734_5_);
        for (int var6 = p_149734_2_ - 2; var6 <= p_149734_2_ + 2; ++var6) {
            for (int var7 = p_149734_4_ - 2; var7 <= p_149734_4_ + 2; ++var7) {
                if (var6 > p_149734_2_ - 2 && var6 < p_149734_2_ + 2 && var7 == p_149734_4_ - 1) {
                    var7 = p_149734_4_ + 2;
                }
                if (p_149734_5_.nextInt(16) == 0) {
                    for (int var8 = p_149734_3_; var8 <= p_149734_3_ + 1; ++var8) {
                        if (p_149734_1_.getBlock(var6, var8, var7) == Blocks.bookshelf) {
                            if (!p_149734_1_.isAirBlock((var6 - p_149734_2_) / 2 + p_149734_2_, var8, (var7 - p_149734_4_) / 2 + p_149734_4_)) {
                                break;
                            }
                            p_149734_1_.spawnParticle("enchantmenttable", p_149734_2_ + 0.5, p_149734_3_ + 2.0, p_149734_4_ + 0.5, var6 - p_149734_2_ + p_149734_5_.nextFloat() - 0.5, var8 - p_149734_3_ - p_149734_5_.nextFloat() - 1.0f, var7 - p_149734_4_ + p_149734_5_.nextFloat() - 0.5);
                        }
                    }
                }
            }
        }
    }
    
    @Override
    public boolean isOpaqueCube() {
        return false;
    }
    
    @Override
    public IIcon getIcon(final int p_149691_1_, final int p_149691_2_) {
        return (p_149691_1_ == 0) ? this.field_149949_b : ((p_149691_1_ == 1) ? this.field_149950_a : this.blockIcon);
    }
    
    @Override
    public TileEntity createNewTileEntity(final World p_149915_1_, final int p_149915_2_) {
        return new TileEntityEnchantmentTable();
    }
    
    @Override
    public boolean onBlockActivated(final World p_149727_1_, final int p_149727_2_, final int p_149727_3_, final int p_149727_4_, final EntityPlayer p_149727_5_, final int p_149727_6_, final float p_149727_7_, final float p_149727_8_, final float p_149727_9_) {
        if (p_149727_1_.isClient) {
            return true;
        }
        final TileEntityEnchantmentTable var10 = (TileEntityEnchantmentTable)p_149727_1_.getTileEntity(p_149727_2_, p_149727_3_, p_149727_4_);
        p_149727_5_.displayGUIEnchantment(p_149727_2_, p_149727_3_, p_149727_4_, var10.func_145921_b() ? var10.func_145919_a() : null);
        return true;
    }
    
    @Override
    public void onBlockPlacedBy(final World p_149689_1_, final int p_149689_2_, final int p_149689_3_, final int p_149689_4_, final EntityLivingBase p_149689_5_, final ItemStack p_149689_6_) {
        super.onBlockPlacedBy(p_149689_1_, p_149689_2_, p_149689_3_, p_149689_4_, p_149689_5_, p_149689_6_);
        if (p_149689_6_.hasDisplayName()) {
            ((TileEntityEnchantmentTable)p_149689_1_.getTileEntity(p_149689_2_, p_149689_3_, p_149689_4_)).func_145920_a(p_149689_6_.getDisplayName());
        }
    }
    
    @Override
    public void registerBlockIcons(final IIconRegister p_149651_1_) {
        this.blockIcon = p_149651_1_.registerIcon(this.getTextureName() + "_side");
        this.field_149950_a = p_149651_1_.registerIcon(this.getTextureName() + "_top");
        this.field_149949_b = p_149651_1_.registerIcon(this.getTextureName() + "_bottom");
    }
}
