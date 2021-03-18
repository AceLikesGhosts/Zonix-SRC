package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.tileentity.*;
import net.minecraft.entity.player.*;
import net.minecraft.nbt.*;
import net.minecraft.world.*;
import net.minecraft.init.*;
import net.minecraft.entity.boss.*;
import net.minecraft.stats.*;
import net.minecraft.entity.*;
import java.util.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.util.*;
import net.minecraft.item.*;

public class BlockSkull extends BlockContainer
{
    private static final String __OBFID = "CL_00000307";
    
    protected BlockSkull() {
        super(Material.circuits);
        this.setBlockBounds(0.25f, 0.0f, 0.25f, 0.75f, 0.5f, 0.75f);
    }
    
    @Override
    public int getRenderType() {
        return -1;
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
    public void setBlockBoundsBasedOnState(final IBlockAccess p_149719_1_, final int p_149719_2_, final int p_149719_3_, final int p_149719_4_) {
        final int var5 = p_149719_1_.getBlockMetadata(p_149719_2_, p_149719_3_, p_149719_4_) & 0x7;
        switch (var5) {
            default: {
                this.setBlockBounds(0.25f, 0.0f, 0.25f, 0.75f, 0.5f, 0.75f);
                break;
            }
            case 2: {
                this.setBlockBounds(0.25f, 0.25f, 0.5f, 0.75f, 0.75f, 1.0f);
                break;
            }
            case 3: {
                this.setBlockBounds(0.25f, 0.25f, 0.0f, 0.75f, 0.75f, 0.5f);
                break;
            }
            case 4: {
                this.setBlockBounds(0.5f, 0.25f, 0.25f, 1.0f, 0.75f, 0.75f);
                break;
            }
            case 5: {
                this.setBlockBounds(0.0f, 0.25f, 0.25f, 0.5f, 0.75f, 0.75f);
                break;
            }
        }
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(final World p_149668_1_, final int p_149668_2_, final int p_149668_3_, final int p_149668_4_) {
        this.setBlockBoundsBasedOnState(p_149668_1_, p_149668_2_, p_149668_3_, p_149668_4_);
        return super.getCollisionBoundingBoxFromPool(p_149668_1_, p_149668_2_, p_149668_3_, p_149668_4_);
    }
    
    @Override
    public void onBlockPlacedBy(final World p_149689_1_, final int p_149689_2_, final int p_149689_3_, final int p_149689_4_, final EntityLivingBase p_149689_5_, final ItemStack p_149689_6_) {
        final int var7 = MathHelper.floor_double(p_149689_5_.rotationYaw * 4.0f / 360.0f + 2.5) & 0x3;
        p_149689_1_.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_, var7, 2);
    }
    
    @Override
    public TileEntity createNewTileEntity(final World p_149915_1_, final int p_149915_2_) {
        return new TileEntitySkull();
    }
    
    @Override
    public Item getItem(final World p_149694_1_, final int p_149694_2_, final int p_149694_3_, final int p_149694_4_) {
        return Items.skull;
    }
    
    @Override
    public int getDamageValue(final World p_149643_1_, final int p_149643_2_, final int p_149643_3_, final int p_149643_4_) {
        final TileEntity var5 = p_149643_1_.getTileEntity(p_149643_2_, p_149643_3_, p_149643_4_);
        return (var5 != null && var5 instanceof TileEntitySkull) ? ((TileEntitySkull)var5).func_145904_a() : super.getDamageValue(p_149643_1_, p_149643_2_, p_149643_3_, p_149643_4_);
    }
    
    @Override
    public int damageDropped(final int p_149692_1_) {
        return p_149692_1_;
    }
    
    @Override
    public void dropBlockAsItemWithChance(final World p_149690_1_, final int p_149690_2_, final int p_149690_3_, final int p_149690_4_, final int p_149690_5_, final float p_149690_6_, final int p_149690_7_) {
    }
    
    @Override
    public void onBlockHarvested(final World p_149681_1_, final int p_149681_2_, final int p_149681_3_, final int p_149681_4_, int p_149681_5_, final EntityPlayer p_149681_6_) {
        if (p_149681_6_.capabilities.isCreativeMode) {
            p_149681_5_ |= 0x8;
            p_149681_1_.setBlockMetadataWithNotify(p_149681_2_, p_149681_3_, p_149681_4_, p_149681_5_, 4);
        }
        super.onBlockHarvested(p_149681_1_, p_149681_2_, p_149681_3_, p_149681_4_, p_149681_5_, p_149681_6_);
    }
    
    @Override
    public void breakBlock(final World p_149749_1_, final int p_149749_2_, final int p_149749_3_, final int p_149749_4_, final Block p_149749_5_, final int p_149749_6_) {
        if (!p_149749_1_.isClient) {
            if ((p_149749_6_ & 0x8) == 0x0) {
                final ItemStack var7 = new ItemStack(Items.skull, 1, this.getDamageValue(p_149749_1_, p_149749_2_, p_149749_3_, p_149749_4_));
                final TileEntitySkull var8 = (TileEntitySkull)p_149749_1_.getTileEntity(p_149749_2_, p_149749_3_, p_149749_4_);
                if (var8.func_145904_a() == 3 && var8.func_152108_a() != null) {
                    var7.setTagCompound(new NBTTagCompound());
                    final NBTTagCompound var9 = new NBTTagCompound();
                    NBTUtil.func_152460_a(var9, var8.func_152108_a());
                    var7.getTagCompound().setTag("SkullOwner", var9);
                }
                this.dropBlockAsItem_do(p_149749_1_, p_149749_2_, p_149749_3_, p_149749_4_, var7);
            }
            super.breakBlock(p_149749_1_, p_149749_2_, p_149749_3_, p_149749_4_, p_149749_5_, p_149749_6_);
        }
    }
    
    @Override
    public Item getItemDropped(final int p_149650_1_, final Random p_149650_2_, final int p_149650_3_) {
        return Items.skull;
    }
    
    public void func_149965_a(final World p_149965_1_, final int p_149965_2_, final int p_149965_3_, final int p_149965_4_, final TileEntitySkull p_149965_5_) {
        if (p_149965_5_.func_145904_a() == 1 && p_149965_3_ >= 2 && p_149965_1_.difficultySetting != EnumDifficulty.PEACEFUL && !p_149965_1_.isClient) {
            for (int var6 = -2; var6 <= 0; ++var6) {
                if (p_149965_1_.getBlock(p_149965_2_, p_149965_3_ - 1, p_149965_4_ + var6) == Blocks.soul_sand && p_149965_1_.getBlock(p_149965_2_, p_149965_3_ - 1, p_149965_4_ + var6 + 1) == Blocks.soul_sand && p_149965_1_.getBlock(p_149965_2_, p_149965_3_ - 2, p_149965_4_ + var6 + 1) == Blocks.soul_sand && p_149965_1_.getBlock(p_149965_2_, p_149965_3_ - 1, p_149965_4_ + var6 + 2) == Blocks.soul_sand && this.func_149966_a(p_149965_1_, p_149965_2_, p_149965_3_, p_149965_4_ + var6, 1) && this.func_149966_a(p_149965_1_, p_149965_2_, p_149965_3_, p_149965_4_ + var6 + 1, 1) && this.func_149966_a(p_149965_1_, p_149965_2_, p_149965_3_, p_149965_4_ + var6 + 2, 1)) {
                    p_149965_1_.setBlockMetadataWithNotify(p_149965_2_, p_149965_3_, p_149965_4_ + var6, 8, 2);
                    p_149965_1_.setBlockMetadataWithNotify(p_149965_2_, p_149965_3_, p_149965_4_ + var6 + 1, 8, 2);
                    p_149965_1_.setBlockMetadataWithNotify(p_149965_2_, p_149965_3_, p_149965_4_ + var6 + 2, 8, 2);
                    p_149965_1_.setBlock(p_149965_2_, p_149965_3_, p_149965_4_ + var6, Block.getBlockById(0), 0, 2);
                    p_149965_1_.setBlock(p_149965_2_, p_149965_3_, p_149965_4_ + var6 + 1, Block.getBlockById(0), 0, 2);
                    p_149965_1_.setBlock(p_149965_2_, p_149965_3_, p_149965_4_ + var6 + 2, Block.getBlockById(0), 0, 2);
                    p_149965_1_.setBlock(p_149965_2_, p_149965_3_ - 1, p_149965_4_ + var6, Block.getBlockById(0), 0, 2);
                    p_149965_1_.setBlock(p_149965_2_, p_149965_3_ - 1, p_149965_4_ + var6 + 1, Block.getBlockById(0), 0, 2);
                    p_149965_1_.setBlock(p_149965_2_, p_149965_3_ - 1, p_149965_4_ + var6 + 2, Block.getBlockById(0), 0, 2);
                    p_149965_1_.setBlock(p_149965_2_, p_149965_3_ - 2, p_149965_4_ + var6 + 1, Block.getBlockById(0), 0, 2);
                    if (!p_149965_1_.isClient) {
                        final EntityWither var7 = new EntityWither(p_149965_1_);
                        var7.setLocationAndAngles(p_149965_2_ + 0.5, p_149965_3_ - 1.45, p_149965_4_ + var6 + 1.5, 90.0f, 0.0f);
                        var7.renderYawOffset = 90.0f;
                        var7.func_82206_m();
                        if (!p_149965_1_.isClient) {
                            for (final EntityPlayer var9 : p_149965_1_.getEntitiesWithinAABB(EntityPlayer.class, var7.boundingBox.expand(50.0, 50.0, 50.0))) {
                                var9.triggerAchievement(AchievementList.field_150963_I);
                            }
                        }
                        p_149965_1_.spawnEntityInWorld(var7);
                    }
                    for (int var10 = 0; var10 < 120; ++var10) {
                        p_149965_1_.spawnParticle("snowballpoof", p_149965_2_ + p_149965_1_.rand.nextDouble(), p_149965_3_ - 2 + p_149965_1_.rand.nextDouble() * 3.9, p_149965_4_ + var6 + 1 + p_149965_1_.rand.nextDouble(), 0.0, 0.0, 0.0);
                    }
                    p_149965_1_.notifyBlockChange(p_149965_2_, p_149965_3_, p_149965_4_ + var6, Block.getBlockById(0));
                    p_149965_1_.notifyBlockChange(p_149965_2_, p_149965_3_, p_149965_4_ + var6 + 1, Block.getBlockById(0));
                    p_149965_1_.notifyBlockChange(p_149965_2_, p_149965_3_, p_149965_4_ + var6 + 2, Block.getBlockById(0));
                    p_149965_1_.notifyBlockChange(p_149965_2_, p_149965_3_ - 1, p_149965_4_ + var6, Block.getBlockById(0));
                    p_149965_1_.notifyBlockChange(p_149965_2_, p_149965_3_ - 1, p_149965_4_ + var6 + 1, Block.getBlockById(0));
                    p_149965_1_.notifyBlockChange(p_149965_2_, p_149965_3_ - 1, p_149965_4_ + var6 + 2, Block.getBlockById(0));
                    p_149965_1_.notifyBlockChange(p_149965_2_, p_149965_3_ - 2, p_149965_4_ + var6 + 1, Block.getBlockById(0));
                    return;
                }
            }
            for (int var6 = -2; var6 <= 0; ++var6) {
                if (p_149965_1_.getBlock(p_149965_2_ + var6, p_149965_3_ - 1, p_149965_4_) == Blocks.soul_sand && p_149965_1_.getBlock(p_149965_2_ + var6 + 1, p_149965_3_ - 1, p_149965_4_) == Blocks.soul_sand && p_149965_1_.getBlock(p_149965_2_ + var6 + 1, p_149965_3_ - 2, p_149965_4_) == Blocks.soul_sand && p_149965_1_.getBlock(p_149965_2_ + var6 + 2, p_149965_3_ - 1, p_149965_4_) == Blocks.soul_sand && this.func_149966_a(p_149965_1_, p_149965_2_ + var6, p_149965_3_, p_149965_4_, 1) && this.func_149966_a(p_149965_1_, p_149965_2_ + var6 + 1, p_149965_3_, p_149965_4_, 1) && this.func_149966_a(p_149965_1_, p_149965_2_ + var6 + 2, p_149965_3_, p_149965_4_, 1)) {
                    p_149965_1_.setBlockMetadataWithNotify(p_149965_2_ + var6, p_149965_3_, p_149965_4_, 8, 2);
                    p_149965_1_.setBlockMetadataWithNotify(p_149965_2_ + var6 + 1, p_149965_3_, p_149965_4_, 8, 2);
                    p_149965_1_.setBlockMetadataWithNotify(p_149965_2_ + var6 + 2, p_149965_3_, p_149965_4_, 8, 2);
                    p_149965_1_.setBlock(p_149965_2_ + var6, p_149965_3_, p_149965_4_, Block.getBlockById(0), 0, 2);
                    p_149965_1_.setBlock(p_149965_2_ + var6 + 1, p_149965_3_, p_149965_4_, Block.getBlockById(0), 0, 2);
                    p_149965_1_.setBlock(p_149965_2_ + var6 + 2, p_149965_3_, p_149965_4_, Block.getBlockById(0), 0, 2);
                    p_149965_1_.setBlock(p_149965_2_ + var6, p_149965_3_ - 1, p_149965_4_, Block.getBlockById(0), 0, 2);
                    p_149965_1_.setBlock(p_149965_2_ + var6 + 1, p_149965_3_ - 1, p_149965_4_, Block.getBlockById(0), 0, 2);
                    p_149965_1_.setBlock(p_149965_2_ + var6 + 2, p_149965_3_ - 1, p_149965_4_, Block.getBlockById(0), 0, 2);
                    p_149965_1_.setBlock(p_149965_2_ + var6 + 1, p_149965_3_ - 2, p_149965_4_, Block.getBlockById(0), 0, 2);
                    if (!p_149965_1_.isClient) {
                        final EntityWither var7 = new EntityWither(p_149965_1_);
                        var7.setLocationAndAngles(p_149965_2_ + var6 + 1.5, p_149965_3_ - 1.45, p_149965_4_ + 0.5, 0.0f, 0.0f);
                        var7.func_82206_m();
                        if (!p_149965_1_.isClient) {
                            for (final EntityPlayer var9 : p_149965_1_.getEntitiesWithinAABB(EntityPlayer.class, var7.boundingBox.expand(50.0, 50.0, 50.0))) {
                                var9.triggerAchievement(AchievementList.field_150963_I);
                            }
                        }
                        p_149965_1_.spawnEntityInWorld(var7);
                    }
                    for (int var10 = 0; var10 < 120; ++var10) {
                        p_149965_1_.spawnParticle("snowballpoof", p_149965_2_ + var6 + 1 + p_149965_1_.rand.nextDouble(), p_149965_3_ - 2 + p_149965_1_.rand.nextDouble() * 3.9, p_149965_4_ + p_149965_1_.rand.nextDouble(), 0.0, 0.0, 0.0);
                    }
                    p_149965_1_.notifyBlockChange(p_149965_2_ + var6, p_149965_3_, p_149965_4_, Block.getBlockById(0));
                    p_149965_1_.notifyBlockChange(p_149965_2_ + var6 + 1, p_149965_3_, p_149965_4_, Block.getBlockById(0));
                    p_149965_1_.notifyBlockChange(p_149965_2_ + var6 + 2, p_149965_3_, p_149965_4_, Block.getBlockById(0));
                    p_149965_1_.notifyBlockChange(p_149965_2_ + var6, p_149965_3_ - 1, p_149965_4_, Block.getBlockById(0));
                    p_149965_1_.notifyBlockChange(p_149965_2_ + var6 + 1, p_149965_3_ - 1, p_149965_4_, Block.getBlockById(0));
                    p_149965_1_.notifyBlockChange(p_149965_2_ + var6 + 2, p_149965_3_ - 1, p_149965_4_, Block.getBlockById(0));
                    p_149965_1_.notifyBlockChange(p_149965_2_ + var6 + 1, p_149965_3_ - 2, p_149965_4_, Block.getBlockById(0));
                    return;
                }
            }
        }
    }
    
    private boolean func_149966_a(final World p_149966_1_, final int p_149966_2_, final int p_149966_3_, final int p_149966_4_, final int p_149966_5_) {
        if (p_149966_1_.getBlock(p_149966_2_, p_149966_3_, p_149966_4_) != this) {
            return false;
        }
        final TileEntity var6 = p_149966_1_.getTileEntity(p_149966_2_, p_149966_3_, p_149966_4_);
        return var6 != null && var6 instanceof TileEntitySkull && ((TileEntitySkull)var6).func_145904_a() == p_149966_5_;
    }
    
    @Override
    public void registerBlockIcons(final IIconRegister p_149651_1_) {
    }
    
    @Override
    public IIcon getIcon(final int p_149691_1_, final int p_149691_2_) {
        return Blocks.soul_sand.getBlockTextureFromSide(p_149691_1_);
    }
    
    @Override
    public String getItemIconName() {
        return this.getTextureName() + "_" + ItemSkull.field_94587_a[0];
    }
}
