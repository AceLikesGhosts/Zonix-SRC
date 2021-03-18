package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import net.minecraft.init.*;
import net.minecraft.entity.monster.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.texture.*;

public class BlockPumpkin extends BlockDirectional
{
    private boolean field_149985_a;
    private IIcon field_149984_b;
    private IIcon field_149986_M;
    private static final String __OBFID = "CL_00000291";
    
    protected BlockPumpkin(final boolean p_i45419_1_) {
        super(Material.field_151572_C);
        this.setTickRandomly(true);
        this.field_149985_a = p_i45419_1_;
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
    
    @Override
    public IIcon getIcon(final int p_149691_1_, final int p_149691_2_) {
        return (p_149691_1_ == 1) ? this.field_149984_b : ((p_149691_1_ == 0) ? this.field_149984_b : ((p_149691_2_ == 2 && p_149691_1_ == 2) ? this.field_149986_M : ((p_149691_2_ == 3 && p_149691_1_ == 5) ? this.field_149986_M : ((p_149691_2_ == 0 && p_149691_1_ == 3) ? this.field_149986_M : ((p_149691_2_ == 1 && p_149691_1_ == 4) ? this.field_149986_M : this.blockIcon)))));
    }
    
    @Override
    public void onBlockAdded(final World p_149726_1_, final int p_149726_2_, final int p_149726_3_, final int p_149726_4_) {
        super.onBlockAdded(p_149726_1_, p_149726_2_, p_149726_3_, p_149726_4_);
        if (p_149726_1_.getBlock(p_149726_2_, p_149726_3_ - 1, p_149726_4_) == Blocks.snow && p_149726_1_.getBlock(p_149726_2_, p_149726_3_ - 2, p_149726_4_) == Blocks.snow) {
            if (!p_149726_1_.isClient) {
                p_149726_1_.setBlock(p_149726_2_, p_149726_3_, p_149726_4_, Block.getBlockById(0), 0, 2);
                p_149726_1_.setBlock(p_149726_2_, p_149726_3_ - 1, p_149726_4_, Block.getBlockById(0), 0, 2);
                p_149726_1_.setBlock(p_149726_2_, p_149726_3_ - 2, p_149726_4_, Block.getBlockById(0), 0, 2);
                final EntitySnowman var9 = new EntitySnowman(p_149726_1_);
                var9.setLocationAndAngles(p_149726_2_ + 0.5, p_149726_3_ - 1.95, p_149726_4_ + 0.5, 0.0f, 0.0f);
                p_149726_1_.spawnEntityInWorld(var9);
                p_149726_1_.notifyBlockChange(p_149726_2_, p_149726_3_, p_149726_4_, Block.getBlockById(0));
                p_149726_1_.notifyBlockChange(p_149726_2_, p_149726_3_ - 1, p_149726_4_, Block.getBlockById(0));
                p_149726_1_.notifyBlockChange(p_149726_2_, p_149726_3_ - 2, p_149726_4_, Block.getBlockById(0));
            }
            for (int var10 = 0; var10 < 120; ++var10) {
                p_149726_1_.spawnParticle("snowshovel", p_149726_2_ + p_149726_1_.rand.nextDouble(), p_149726_3_ - 2 + p_149726_1_.rand.nextDouble() * 2.5, p_149726_4_ + p_149726_1_.rand.nextDouble(), 0.0, 0.0, 0.0);
            }
        }
        else if (p_149726_1_.getBlock(p_149726_2_, p_149726_3_ - 1, p_149726_4_) == Blocks.iron_block && p_149726_1_.getBlock(p_149726_2_, p_149726_3_ - 2, p_149726_4_) == Blocks.iron_block) {
            final boolean var11 = p_149726_1_.getBlock(p_149726_2_ - 1, p_149726_3_ - 1, p_149726_4_) == Blocks.iron_block && p_149726_1_.getBlock(p_149726_2_ + 1, p_149726_3_ - 1, p_149726_4_) == Blocks.iron_block;
            final boolean var12 = p_149726_1_.getBlock(p_149726_2_, p_149726_3_ - 1, p_149726_4_ - 1) == Blocks.iron_block && p_149726_1_.getBlock(p_149726_2_, p_149726_3_ - 1, p_149726_4_ + 1) == Blocks.iron_block;
            if (var11 || var12) {
                p_149726_1_.setBlock(p_149726_2_, p_149726_3_, p_149726_4_, Block.getBlockById(0), 0, 2);
                p_149726_1_.setBlock(p_149726_2_, p_149726_3_ - 1, p_149726_4_, Block.getBlockById(0), 0, 2);
                p_149726_1_.setBlock(p_149726_2_, p_149726_3_ - 2, p_149726_4_, Block.getBlockById(0), 0, 2);
                if (var11) {
                    p_149726_1_.setBlock(p_149726_2_ - 1, p_149726_3_ - 1, p_149726_4_, Block.getBlockById(0), 0, 2);
                    p_149726_1_.setBlock(p_149726_2_ + 1, p_149726_3_ - 1, p_149726_4_, Block.getBlockById(0), 0, 2);
                }
                else {
                    p_149726_1_.setBlock(p_149726_2_, p_149726_3_ - 1, p_149726_4_ - 1, Block.getBlockById(0), 0, 2);
                    p_149726_1_.setBlock(p_149726_2_, p_149726_3_ - 1, p_149726_4_ + 1, Block.getBlockById(0), 0, 2);
                }
                final EntityIronGolem var13 = new EntityIronGolem(p_149726_1_);
                var13.setPlayerCreated(true);
                var13.setLocationAndAngles(p_149726_2_ + 0.5, p_149726_3_ - 1.95, p_149726_4_ + 0.5, 0.0f, 0.0f);
                p_149726_1_.spawnEntityInWorld(var13);
                for (int var14 = 0; var14 < 120; ++var14) {
                    p_149726_1_.spawnParticle("snowballpoof", p_149726_2_ + p_149726_1_.rand.nextDouble(), p_149726_3_ - 2 + p_149726_1_.rand.nextDouble() * 3.9, p_149726_4_ + p_149726_1_.rand.nextDouble(), 0.0, 0.0, 0.0);
                }
                p_149726_1_.notifyBlockChange(p_149726_2_, p_149726_3_, p_149726_4_, Block.getBlockById(0));
                p_149726_1_.notifyBlockChange(p_149726_2_, p_149726_3_ - 1, p_149726_4_, Block.getBlockById(0));
                p_149726_1_.notifyBlockChange(p_149726_2_, p_149726_3_ - 2, p_149726_4_, Block.getBlockById(0));
                if (var11) {
                    p_149726_1_.notifyBlockChange(p_149726_2_ - 1, p_149726_3_ - 1, p_149726_4_, Block.getBlockById(0));
                    p_149726_1_.notifyBlockChange(p_149726_2_ + 1, p_149726_3_ - 1, p_149726_4_, Block.getBlockById(0));
                }
                else {
                    p_149726_1_.notifyBlockChange(p_149726_2_, p_149726_3_ - 1, p_149726_4_ - 1, Block.getBlockById(0));
                    p_149726_1_.notifyBlockChange(p_149726_2_, p_149726_3_ - 1, p_149726_4_ + 1, Block.getBlockById(0));
                }
            }
        }
    }
    
    @Override
    public boolean canPlaceBlockAt(final World p_149742_1_, final int p_149742_2_, final int p_149742_3_, final int p_149742_4_) {
        return p_149742_1_.getBlock(p_149742_2_, p_149742_3_, p_149742_4_).blockMaterial.isReplaceable() && World.doesBlockHaveSolidTopSurface(p_149742_1_, p_149742_2_, p_149742_3_ - 1, p_149742_4_);
    }
    
    @Override
    public void onBlockPlacedBy(final World p_149689_1_, final int p_149689_2_, final int p_149689_3_, final int p_149689_4_, final EntityLivingBase p_149689_5_, final ItemStack p_149689_6_) {
        final int var7 = MathHelper.floor_double(p_149689_5_.rotationYaw * 4.0f / 360.0f + 2.5) & 0x3;
        p_149689_1_.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_, var7, 2);
    }
    
    @Override
    public void registerBlockIcons(final IIconRegister p_149651_1_) {
        this.field_149986_M = p_149651_1_.registerIcon(this.getTextureName() + "_face_" + (this.field_149985_a ? "on" : "off"));
        this.field_149984_b = p_149651_1_.registerIcon(this.getTextureName() + "_top");
        this.blockIcon = p_149651_1_.registerIcon(this.getTextureName() + "_side");
    }
}
