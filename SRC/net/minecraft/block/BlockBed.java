package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.biome.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.world.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.init.*;

public class BlockBed extends BlockDirectional
{
    public static final int[][] field_149981_a;
    private IIcon[] field_149980_b;
    private IIcon[] field_149982_M;
    private IIcon[] field_149983_N;
    private static final String __OBFID = "CL_00000198";
    
    public BlockBed() {
        super(Material.cloth);
        this.func_149978_e();
    }
    
    @Override
    public boolean onBlockActivated(final World p_149727_1_, int p_149727_2_, final int p_149727_3_, int p_149727_4_, final EntityPlayer p_149727_5_, final int p_149727_6_, final float p_149727_7_, final float p_149727_8_, final float p_149727_9_) {
        if (p_149727_1_.isClient) {
            return true;
        }
        int var10 = p_149727_1_.getBlockMetadata(p_149727_2_, p_149727_3_, p_149727_4_);
        if (!func_149975_b(var10)) {
            final int var11 = BlockDirectional.func_149895_l(var10);
            p_149727_2_ += BlockBed.field_149981_a[var11][0];
            p_149727_4_ += BlockBed.field_149981_a[var11][1];
            if (p_149727_1_.getBlock(p_149727_2_, p_149727_3_, p_149727_4_) != this) {
                return true;
            }
            var10 = p_149727_1_.getBlockMetadata(p_149727_2_, p_149727_3_, p_149727_4_);
        }
        if (!p_149727_1_.provider.canRespawnHere() || p_149727_1_.getBiomeGenForCoords(p_149727_2_, p_149727_4_) == BiomeGenBase.hell) {
            double var12 = p_149727_2_ + 0.5;
            double var13 = p_149727_3_ + 0.5;
            double var14 = p_149727_4_ + 0.5;
            p_149727_1_.setBlockToAir(p_149727_2_, p_149727_3_, p_149727_4_);
            final int var15 = BlockDirectional.func_149895_l(var10);
            p_149727_2_ += BlockBed.field_149981_a[var15][0];
            p_149727_4_ += BlockBed.field_149981_a[var15][1];
            if (p_149727_1_.getBlock(p_149727_2_, p_149727_3_, p_149727_4_) == this) {
                p_149727_1_.setBlockToAir(p_149727_2_, p_149727_3_, p_149727_4_);
                var12 = (var12 + p_149727_2_ + 0.5) / 2.0;
                var13 = (var13 + p_149727_3_ + 0.5) / 2.0;
                var14 = (var14 + p_149727_4_ + 0.5) / 2.0;
            }
            p_149727_1_.newExplosion(null, p_149727_2_ + 0.5f, p_149727_3_ + 0.5f, p_149727_4_ + 0.5f, 5.0f, true, true);
            return true;
        }
        if (func_149976_c(var10)) {
            EntityPlayer var16 = null;
            for (final EntityPlayer var18 : p_149727_1_.playerEntities) {
                if (var18.isPlayerSleeping()) {
                    final ChunkCoordinates var19 = var18.playerLocation;
                    if (var19.posX != p_149727_2_ || var19.posY != p_149727_3_ || var19.posZ != p_149727_4_) {
                        continue;
                    }
                    var16 = var18;
                }
            }
            if (var16 != null) {
                p_149727_5_.addChatComponentMessage(new ChatComponentTranslation("tile.bed.occupied", new Object[0]));
                return true;
            }
            func_149979_a(p_149727_1_, p_149727_2_, p_149727_3_, p_149727_4_, false);
        }
        final EntityPlayer.EnumStatus var20 = p_149727_5_.sleepInBedAt(p_149727_2_, p_149727_3_, p_149727_4_);
        if (var20 == EntityPlayer.EnumStatus.OK) {
            func_149979_a(p_149727_1_, p_149727_2_, p_149727_3_, p_149727_4_, true);
            return true;
        }
        if (var20 == EntityPlayer.EnumStatus.NOT_POSSIBLE_NOW) {
            p_149727_5_.addChatComponentMessage(new ChatComponentTranslation("tile.bed.noSleep", new Object[0]));
        }
        else if (var20 == EntityPlayer.EnumStatus.NOT_SAFE) {
            p_149727_5_.addChatComponentMessage(new ChatComponentTranslation("tile.bed.notSafe", new Object[0]));
        }
        return true;
    }
    
    @Override
    public IIcon getIcon(final int p_149691_1_, final int p_149691_2_) {
        if (p_149691_1_ == 0) {
            return Blocks.planks.getBlockTextureFromSide(p_149691_1_);
        }
        final int var3 = BlockDirectional.func_149895_l(p_149691_2_);
        final int var4 = Direction.bedDirection[var3][p_149691_1_];
        final int var5 = func_149975_b(p_149691_2_) ? 1 : 0;
        return ((var5 != 1 || var4 != 2) && (var5 != 0 || var4 != 3)) ? ((var4 != 5 && var4 != 4) ? this.field_149983_N[var5] : this.field_149982_M[var5]) : this.field_149980_b[var5];
    }
    
    @Override
    public void registerBlockIcons(final IIconRegister p_149651_1_) {
        this.field_149983_N = new IIcon[] { p_149651_1_.registerIcon(this.getTextureName() + "_feet_top"), p_149651_1_.registerIcon(this.getTextureName() + "_head_top") };
        this.field_149980_b = new IIcon[] { p_149651_1_.registerIcon(this.getTextureName() + "_feet_end"), p_149651_1_.registerIcon(this.getTextureName() + "_head_end") };
        this.field_149982_M = new IIcon[] { p_149651_1_.registerIcon(this.getTextureName() + "_feet_side"), p_149651_1_.registerIcon(this.getTextureName() + "_head_side") };
    }
    
    @Override
    public int getRenderType() {
        return 14;
    }
    
    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }
    
    @Override
    public boolean isOpaqueCube() {
        return false;
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess p_149719_1_, final int p_149719_2_, final int p_149719_3_, final int p_149719_4_) {
        this.func_149978_e();
    }
    
    @Override
    public void onNeighborBlockChange(final World p_149695_1_, final int p_149695_2_, final int p_149695_3_, final int p_149695_4_, final Block p_149695_5_) {
        final int var6 = p_149695_1_.getBlockMetadata(p_149695_2_, p_149695_3_, p_149695_4_);
        final int var7 = BlockDirectional.func_149895_l(var6);
        if (func_149975_b(var6)) {
            if (p_149695_1_.getBlock(p_149695_2_ - BlockBed.field_149981_a[var7][0], p_149695_3_, p_149695_4_ - BlockBed.field_149981_a[var7][1]) != this) {
                p_149695_1_.setBlockToAir(p_149695_2_, p_149695_3_, p_149695_4_);
            }
        }
        else if (p_149695_1_.getBlock(p_149695_2_ + BlockBed.field_149981_a[var7][0], p_149695_3_, p_149695_4_ + BlockBed.field_149981_a[var7][1]) != this) {
            p_149695_1_.setBlockToAir(p_149695_2_, p_149695_3_, p_149695_4_);
            if (!p_149695_1_.isClient) {
                this.dropBlockAsItem(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_, var6, 0);
            }
        }
    }
    
    @Override
    public Item getItemDropped(final int p_149650_1_, final Random p_149650_2_, final int p_149650_3_) {
        return func_149975_b(p_149650_1_) ? Item.getItemById(0) : Items.bed;
    }
    
    private void func_149978_e() {
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.5625f, 1.0f);
    }
    
    public static boolean func_149975_b(final int p_149975_0_) {
        return (p_149975_0_ & 0x8) != 0x0;
    }
    
    public static boolean func_149976_c(final int p_149976_0_) {
        return (p_149976_0_ & 0x4) != 0x0;
    }
    
    public static void func_149979_a(final World p_149979_0_, final int p_149979_1_, final int p_149979_2_, final int p_149979_3_, final boolean p_149979_4_) {
        int var5 = p_149979_0_.getBlockMetadata(p_149979_1_, p_149979_2_, p_149979_3_);
        if (p_149979_4_) {
            var5 |= 0x4;
        }
        else {
            var5 &= 0xFFFFFFFB;
        }
        p_149979_0_.setBlockMetadataWithNotify(p_149979_1_, p_149979_2_, p_149979_3_, var5, 4);
    }
    
    public static ChunkCoordinates func_149977_a(final World p_149977_0_, final int p_149977_1_, final int p_149977_2_, final int p_149977_3_, int p_149977_4_) {
        final int var5 = p_149977_0_.getBlockMetadata(p_149977_1_, p_149977_2_, p_149977_3_);
        final int var6 = BlockDirectional.func_149895_l(var5);
        for (int var7 = 0; var7 <= 1; ++var7) {
            final int var8 = p_149977_1_ - BlockBed.field_149981_a[var6][0] * var7 - 1;
            final int var9 = p_149977_3_ - BlockBed.field_149981_a[var6][1] * var7 - 1;
            final int var10 = var8 + 2;
            final int var11 = var9 + 2;
            for (int var12 = var8; var12 <= var10; ++var12) {
                for (int var13 = var9; var13 <= var11; ++var13) {
                    if (World.doesBlockHaveSolidTopSurface(p_149977_0_, var12, p_149977_2_ - 1, var13) && !p_149977_0_.getBlock(var12, p_149977_2_, var13).getMaterial().isOpaque() && !p_149977_0_.getBlock(var12, p_149977_2_ + 1, var13).getMaterial().isOpaque()) {
                        if (p_149977_4_ <= 0) {
                            return new ChunkCoordinates(var12, p_149977_2_, var13);
                        }
                        --p_149977_4_;
                    }
                }
            }
        }
        return null;
    }
    
    @Override
    public void dropBlockAsItemWithChance(final World p_149690_1_, final int p_149690_2_, final int p_149690_3_, final int p_149690_4_, final int p_149690_5_, final float p_149690_6_, final int p_149690_7_) {
        if (!func_149975_b(p_149690_5_)) {
            super.dropBlockAsItemWithChance(p_149690_1_, p_149690_2_, p_149690_3_, p_149690_4_, p_149690_5_, p_149690_6_, 0);
        }
    }
    
    @Override
    public int getMobilityFlag() {
        return 1;
    }
    
    @Override
    public Item getItem(final World p_149694_1_, final int p_149694_2_, final int p_149694_3_, final int p_149694_4_) {
        return Items.bed;
    }
    
    @Override
    public void onBlockHarvested(final World p_149681_1_, int p_149681_2_, final int p_149681_3_, int p_149681_4_, final int p_149681_5_, final EntityPlayer p_149681_6_) {
        if (p_149681_6_.capabilities.isCreativeMode && func_149975_b(p_149681_5_)) {
            final int var7 = BlockDirectional.func_149895_l(p_149681_5_);
            p_149681_2_ -= BlockBed.field_149981_a[var7][0];
            p_149681_4_ -= BlockBed.field_149981_a[var7][1];
            if (p_149681_1_.getBlock(p_149681_2_, p_149681_3_, p_149681_4_) == this) {
                p_149681_1_.setBlockToAir(p_149681_2_, p_149681_3_, p_149681_4_);
            }
        }
    }
    
    static {
        field_149981_a = new int[][] { { 0, 1 }, { -1, 0 }, { 0, -1 }, { 1, 0 } };
    }
}
