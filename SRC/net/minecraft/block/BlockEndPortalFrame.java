package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.init.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.world.*;
import java.util.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.util.*;

public class BlockEndPortalFrame extends Block
{
    private IIcon field_150023_a;
    private IIcon field_150022_b;
    private static final String __OBFID = "CL_00000237";
    
    public BlockEndPortalFrame() {
        super(Material.rock);
    }
    
    @Override
    public IIcon getIcon(final int p_149691_1_, final int p_149691_2_) {
        return (p_149691_1_ == 1) ? this.field_150023_a : ((p_149691_1_ == 0) ? Blocks.end_stone.getBlockTextureFromSide(p_149691_1_) : this.blockIcon);
    }
    
    @Override
    public void registerBlockIcons(final IIconRegister p_149651_1_) {
        this.blockIcon = p_149651_1_.registerIcon(this.getTextureName() + "_side");
        this.field_150023_a = p_149651_1_.registerIcon(this.getTextureName() + "_top");
        this.field_150022_b = p_149651_1_.registerIcon(this.getTextureName() + "_eye");
    }
    
    public IIcon func_150021_e() {
        return this.field_150022_b;
    }
    
    @Override
    public boolean isOpaqueCube() {
        return false;
    }
    
    @Override
    public int getRenderType() {
        return 26;
    }
    
    @Override
    public void setBlockBoundsForItemRender() {
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.8125f, 1.0f);
    }
    
    @Override
    public void addCollisionBoxesToList(final World p_149743_1_, final int p_149743_2_, final int p_149743_3_, final int p_149743_4_, final AxisAlignedBB p_149743_5_, final List p_149743_6_, final Entity p_149743_7_) {
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.8125f, 1.0f);
        super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_);
        final int var8 = p_149743_1_.getBlockMetadata(p_149743_2_, p_149743_3_, p_149743_4_);
        if (func_150020_b(var8)) {
            this.setBlockBounds(0.3125f, 0.8125f, 0.3125f, 0.6875f, 1.0f, 0.6875f);
            super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_);
        }
        this.setBlockBoundsForItemRender();
    }
    
    public static boolean func_150020_b(final int p_150020_0_) {
        return (p_150020_0_ & 0x4) != 0x0;
    }
    
    @Override
    public Item getItemDropped(final int p_149650_1_, final Random p_149650_2_, final int p_149650_3_) {
        return null;
    }
    
    @Override
    public void onBlockPlacedBy(final World p_149689_1_, final int p_149689_2_, final int p_149689_3_, final int p_149689_4_, final EntityLivingBase p_149689_5_, final ItemStack p_149689_6_) {
        final int var7 = ((MathHelper.floor_double(p_149689_5_.rotationYaw * 4.0f / 360.0f + 0.5) & 0x3) + 2) % 4;
        p_149689_1_.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_, var7, 2);
    }
    
    @Override
    public boolean hasComparatorInputOverride() {
        return true;
    }
    
    @Override
    public int getComparatorInputOverride(final World p_149736_1_, final int p_149736_2_, final int p_149736_3_, final int p_149736_4_, final int p_149736_5_) {
        final int var6 = p_149736_1_.getBlockMetadata(p_149736_2_, p_149736_3_, p_149736_4_);
        return func_150020_b(var6) ? 15 : 0;
    }
}
