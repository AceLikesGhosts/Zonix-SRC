package net.minecraft.block;

import net.minecraft.block.material.*;
import java.util.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.item.*;

public class BlockDragonEgg extends Block
{
    private static final String __OBFID = "CL_00000232";
    
    public BlockDragonEgg() {
        super(Material.dragonEgg);
        this.setBlockBounds(0.0625f, 0.0f, 0.0625f, 0.9375f, 1.0f, 0.9375f);
    }
    
    @Override
    public void onBlockAdded(final World p_149726_1_, final int p_149726_2_, final int p_149726_3_, final int p_149726_4_) {
        p_149726_1_.scheduleBlockUpdate(p_149726_2_, p_149726_3_, p_149726_4_, this, this.func_149738_a(p_149726_1_));
    }
    
    @Override
    public void onNeighborBlockChange(final World p_149695_1_, final int p_149695_2_, final int p_149695_3_, final int p_149695_4_, final Block p_149695_5_) {
        p_149695_1_.scheduleBlockUpdate(p_149695_2_, p_149695_3_, p_149695_4_, this, this.func_149738_a(p_149695_1_));
    }
    
    @Override
    public void updateTick(final World p_149674_1_, final int p_149674_2_, final int p_149674_3_, final int p_149674_4_, final Random p_149674_5_) {
        this.func_150018_e(p_149674_1_, p_149674_2_, p_149674_3_, p_149674_4_);
    }
    
    private void func_150018_e(final World p_150018_1_, final int p_150018_2_, int p_150018_3_, final int p_150018_4_) {
        if (BlockFalling.func_149831_e(p_150018_1_, p_150018_2_, p_150018_3_ - 1, p_150018_4_) && p_150018_3_ >= 0) {
            final byte var5 = 32;
            if (!BlockFalling.field_149832_M && p_150018_1_.checkChunksExist(p_150018_2_ - var5, p_150018_3_ - var5, p_150018_4_ - var5, p_150018_2_ + var5, p_150018_3_ + var5, p_150018_4_ + var5)) {
                final EntityFallingBlock var6 = new EntityFallingBlock(p_150018_1_, p_150018_2_ + 0.5f, p_150018_3_ + 0.5f, p_150018_4_ + 0.5f, this);
                p_150018_1_.spawnEntityInWorld(var6);
            }
            else {
                p_150018_1_.setBlockToAir(p_150018_2_, p_150018_3_, p_150018_4_);
                while (BlockFalling.func_149831_e(p_150018_1_, p_150018_2_, p_150018_3_ - 1, p_150018_4_) && p_150018_3_ > 0) {
                    --p_150018_3_;
                }
                if (p_150018_3_ > 0) {
                    p_150018_1_.setBlock(p_150018_2_, p_150018_3_, p_150018_4_, this, 0, 2);
                }
            }
        }
    }
    
    @Override
    public boolean onBlockActivated(final World p_149727_1_, final int p_149727_2_, final int p_149727_3_, final int p_149727_4_, final EntityPlayer p_149727_5_, final int p_149727_6_, final float p_149727_7_, final float p_149727_8_, final float p_149727_9_) {
        this.func_150019_m(p_149727_1_, p_149727_2_, p_149727_3_, p_149727_4_);
        return true;
    }
    
    @Override
    public void onBlockClicked(final World p_149699_1_, final int p_149699_2_, final int p_149699_3_, final int p_149699_4_, final EntityPlayer p_149699_5_) {
        this.func_150019_m(p_149699_1_, p_149699_2_, p_149699_3_, p_149699_4_);
    }
    
    private void func_150019_m(final World p_150019_1_, final int p_150019_2_, final int p_150019_3_, final int p_150019_4_) {
        if (p_150019_1_.getBlock(p_150019_2_, p_150019_3_, p_150019_4_) == this) {
            for (int var5 = 0; var5 < 1000; ++var5) {
                final int var6 = p_150019_2_ + p_150019_1_.rand.nextInt(16) - p_150019_1_.rand.nextInt(16);
                final int var7 = p_150019_3_ + p_150019_1_.rand.nextInt(8) - p_150019_1_.rand.nextInt(8);
                final int var8 = p_150019_4_ + p_150019_1_.rand.nextInt(16) - p_150019_1_.rand.nextInt(16);
                if (p_150019_1_.getBlock(var6, var7, var8).blockMaterial == Material.air) {
                    if (!p_150019_1_.isClient) {
                        p_150019_1_.setBlock(var6, var7, var8, this, p_150019_1_.getBlockMetadata(p_150019_2_, p_150019_3_, p_150019_4_), 2);
                        p_150019_1_.setBlockToAir(p_150019_2_, p_150019_3_, p_150019_4_);
                    }
                    else {
                        final short var9 = 128;
                        for (int var10 = 0; var10 < var9; ++var10) {
                            final double var11 = p_150019_1_.rand.nextDouble();
                            final float var12 = (p_150019_1_.rand.nextFloat() - 0.5f) * 0.2f;
                            final float var13 = (p_150019_1_.rand.nextFloat() - 0.5f) * 0.2f;
                            final float var14 = (p_150019_1_.rand.nextFloat() - 0.5f) * 0.2f;
                            final double var15 = var6 + (p_150019_2_ - var6) * var11 + (p_150019_1_.rand.nextDouble() - 0.5) * 1.0 + 0.5;
                            final double var16 = var7 + (p_150019_3_ - var7) * var11 + p_150019_1_.rand.nextDouble() * 1.0 - 0.5;
                            final double var17 = var8 + (p_150019_4_ - var8) * var11 + (p_150019_1_.rand.nextDouble() - 0.5) * 1.0 + 0.5;
                            p_150019_1_.spawnParticle("portal", var15, var16, var17, var12, var13, var14);
                        }
                    }
                    return;
                }
            }
        }
    }
    
    @Override
    public int func_149738_a(final World p_149738_1_) {
        return 5;
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
    public boolean shouldSideBeRendered(final IBlockAccess p_149646_1_, final int p_149646_2_, final int p_149646_3_, final int p_149646_4_, final int p_149646_5_) {
        return true;
    }
    
    @Override
    public int getRenderType() {
        return 27;
    }
    
    @Override
    public Item getItem(final World p_149694_1_, final int p_149694_2_, final int p_149694_3_, final int p_149694_4_) {
        return Item.getItemById(0);
    }
}
