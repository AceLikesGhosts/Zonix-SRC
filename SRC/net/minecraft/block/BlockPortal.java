package net.minecraft.block;

import net.minecraft.block.material.*;
import java.util.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.util.*;

public class BlockPortal extends BlockBreakable
{
    public static final int[][] field_150001_a;
    private static final String __OBFID = "CL_00000284";
    
    public BlockPortal() {
        super("portal", Material.Portal, false);
        this.setTickRandomly(true);
    }
    
    @Override
    public void updateTick(final World p_149674_1_, final int p_149674_2_, final int p_149674_3_, final int p_149674_4_, final Random p_149674_5_) {
        super.updateTick(p_149674_1_, p_149674_2_, p_149674_3_, p_149674_4_, p_149674_5_);
        if (p_149674_1_.provider.isSurfaceWorld() && p_149674_1_.getGameRules().getGameRuleBooleanValue("doMobSpawning") && p_149674_5_.nextInt(2000) < p_149674_1_.difficultySetting.getDifficultyId()) {
            int var6;
            for (var6 = p_149674_3_; !World.doesBlockHaveSolidTopSurface(p_149674_1_, p_149674_2_, var6, p_149674_4_) && var6 > 0; --var6) {}
            if (var6 > 0 && !p_149674_1_.getBlock(p_149674_2_, var6 + 1, p_149674_4_).isNormalCube()) {
                final Entity var7 = ItemMonsterPlacer.spawnCreature(p_149674_1_, 57, p_149674_2_ + 0.5, var6 + 1.1, p_149674_4_ + 0.5);
                if (var7 != null) {
                    var7.timeUntilPortal = var7.getPortalCooldown();
                }
            }
        }
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(final World p_149668_1_, final int p_149668_2_, final int p_149668_3_, final int p_149668_4_) {
        return null;
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess p_149719_1_, final int p_149719_2_, final int p_149719_3_, final int p_149719_4_) {
        int var5 = func_149999_b(p_149719_1_.getBlockMetadata(p_149719_2_, p_149719_3_, p_149719_4_));
        if (var5 == 0) {
            if (p_149719_1_.getBlock(p_149719_2_ - 1, p_149719_3_, p_149719_4_) != this && p_149719_1_.getBlock(p_149719_2_ + 1, p_149719_3_, p_149719_4_) != this) {
                var5 = 2;
            }
            else {
                var5 = 1;
            }
            if (p_149719_1_ instanceof World && !((World)p_149719_1_).isClient) {
                ((World)p_149719_1_).setBlockMetadataWithNotify(p_149719_2_, p_149719_3_, p_149719_4_, var5, 2);
            }
        }
        float var6 = 0.125f;
        float var7 = 0.125f;
        if (var5 == 1) {
            var6 = 0.5f;
        }
        if (var5 == 2) {
            var7 = 0.5f;
        }
        this.setBlockBounds(0.5f - var6, 0.0f, 0.5f - var7, 0.5f + var6, 1.0f, 0.5f + var7);
    }
    
    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }
    
    public boolean func_150000_e(final World p_150000_1_, final int p_150000_2_, final int p_150000_3_, final int p_150000_4_) {
        final Size var5 = new Size(p_150000_1_, p_150000_2_, p_150000_3_, p_150000_4_, 1);
        final Size var6 = new Size(p_150000_1_, p_150000_2_, p_150000_3_, p_150000_4_, 2);
        if (var5.func_150860_b() && var5.field_150864_e == 0) {
            var5.func_150859_c();
            return true;
        }
        if (var6.func_150860_b() && var6.field_150864_e == 0) {
            var6.func_150859_c();
            return true;
        }
        return false;
    }
    
    @Override
    public void onNeighborBlockChange(final World p_149695_1_, final int p_149695_2_, final int p_149695_3_, final int p_149695_4_, final Block p_149695_5_) {
        final int var6 = func_149999_b(p_149695_1_.getBlockMetadata(p_149695_2_, p_149695_3_, p_149695_4_));
        final Size var7 = new Size(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_, 1);
        final Size var8 = new Size(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_, 2);
        if (var6 == 1 && (!var7.func_150860_b() || var7.field_150864_e < var7.field_150868_h * var7.field_150862_g)) {
            p_149695_1_.setBlock(p_149695_2_, p_149695_3_, p_149695_4_, Blocks.air);
        }
        else if (var6 == 2 && (!var8.func_150860_b() || var8.field_150864_e < var8.field_150868_h * var8.field_150862_g)) {
            p_149695_1_.setBlock(p_149695_2_, p_149695_3_, p_149695_4_, Blocks.air);
        }
        else if (var6 == 0 && !var7.func_150860_b() && !var8.func_150860_b()) {
            p_149695_1_.setBlock(p_149695_2_, p_149695_3_, p_149695_4_, Blocks.air);
        }
    }
    
    @Override
    public boolean shouldSideBeRendered(final IBlockAccess p_149646_1_, final int p_149646_2_, final int p_149646_3_, final int p_149646_4_, final int p_149646_5_) {
        int var6 = 0;
        if (p_149646_1_.getBlock(p_149646_2_, p_149646_3_, p_149646_4_) == this) {
            var6 = func_149999_b(p_149646_1_.getBlockMetadata(p_149646_2_, p_149646_3_, p_149646_4_));
            if (var6 == 0) {
                return false;
            }
            if (var6 == 2 && p_149646_5_ != 5 && p_149646_5_ != 4) {
                return false;
            }
            if (var6 == 1 && p_149646_5_ != 3 && p_149646_5_ != 2) {
                return false;
            }
        }
        final boolean var7 = p_149646_1_.getBlock(p_149646_2_ - 1, p_149646_3_, p_149646_4_) == this && p_149646_1_.getBlock(p_149646_2_ - 2, p_149646_3_, p_149646_4_) != this;
        final boolean var8 = p_149646_1_.getBlock(p_149646_2_ + 1, p_149646_3_, p_149646_4_) == this && p_149646_1_.getBlock(p_149646_2_ + 2, p_149646_3_, p_149646_4_) != this;
        final boolean var9 = p_149646_1_.getBlock(p_149646_2_, p_149646_3_, p_149646_4_ - 1) == this && p_149646_1_.getBlock(p_149646_2_, p_149646_3_, p_149646_4_ - 2) != this;
        final boolean var10 = p_149646_1_.getBlock(p_149646_2_, p_149646_3_, p_149646_4_ + 1) == this && p_149646_1_.getBlock(p_149646_2_, p_149646_3_, p_149646_4_ + 2) != this;
        final boolean var11 = var7 || var8 || var6 == 1;
        final boolean var12 = var9 || var10 || var6 == 2;
        return (var11 && p_149646_5_ == 4) || (var11 && p_149646_5_ == 5) || (var12 && p_149646_5_ == 2) || (var12 && p_149646_5_ == 3);
    }
    
    @Override
    public int quantityDropped(final Random p_149745_1_) {
        return 0;
    }
    
    @Override
    public int getRenderBlockPass() {
        return 1;
    }
    
    @Override
    public void onEntityCollidedWithBlock(final World p_149670_1_, final int p_149670_2_, final int p_149670_3_, final int p_149670_4_, final Entity p_149670_5_) {
        if (p_149670_5_.ridingEntity == null && p_149670_5_.riddenByEntity == null) {
            p_149670_5_.setInPortal();
        }
    }
    
    @Override
    public void randomDisplayTick(final World p_149734_1_, final int p_149734_2_, final int p_149734_3_, final int p_149734_4_, final Random p_149734_5_) {
        if (p_149734_5_.nextInt(100) == 0) {
            p_149734_1_.playSound(p_149734_2_ + 0.5, p_149734_3_ + 0.5, p_149734_4_ + 0.5, "portal.portal", 0.5f, p_149734_5_.nextFloat() * 0.4f + 0.8f, false);
        }
        for (int var6 = 0; var6 < 4; ++var6) {
            double var7 = p_149734_2_ + p_149734_5_.nextFloat();
            final double var8 = p_149734_3_ + p_149734_5_.nextFloat();
            double var9 = p_149734_4_ + p_149734_5_.nextFloat();
            double var10 = 0.0;
            double var11 = 0.0;
            double var12 = 0.0;
            final int var13 = p_149734_5_.nextInt(2) * 2 - 1;
            var10 = (p_149734_5_.nextFloat() - 0.5) * 0.5;
            var11 = (p_149734_5_.nextFloat() - 0.5) * 0.5;
            var12 = (p_149734_5_.nextFloat() - 0.5) * 0.5;
            if (p_149734_1_.getBlock(p_149734_2_ - 1, p_149734_3_, p_149734_4_) != this && p_149734_1_.getBlock(p_149734_2_ + 1, p_149734_3_, p_149734_4_) != this) {
                var7 = p_149734_2_ + 0.5 + 0.25 * var13;
                var10 = p_149734_5_.nextFloat() * 2.0f * var13;
            }
            else {
                var9 = p_149734_4_ + 0.5 + 0.25 * var13;
                var12 = p_149734_5_.nextFloat() * 2.0f * var13;
            }
            p_149734_1_.spawnParticle("portal", var7, var8, var9, var10, var11, var12);
        }
    }
    
    @Override
    public Item getItem(final World p_149694_1_, final int p_149694_2_, final int p_149694_3_, final int p_149694_4_) {
        return Item.getItemById(0);
    }
    
    public static int func_149999_b(final int p_149999_0_) {
        return p_149999_0_ & 0x3;
    }
    
    static {
        field_150001_a = new int[][] { new int[0], { 3, 1 }, { 2, 0 } };
    }
    
    public static class Size
    {
        private final World field_150867_a;
        private final int field_150865_b;
        private final int field_150866_c;
        private final int field_150863_d;
        private int field_150864_e;
        private ChunkCoordinates field_150861_f;
        private int field_150862_g;
        private int field_150868_h;
        private static final String __OBFID = "CL_00000285";
        
        public Size(final World p_i45415_1_, final int p_i45415_2_, int p_i45415_3_, final int p_i45415_4_, final int p_i45415_5_) {
            this.field_150864_e = 0;
            this.field_150867_a = p_i45415_1_;
            this.field_150865_b = p_i45415_5_;
            this.field_150863_d = BlockPortal.field_150001_a[p_i45415_5_][0];
            this.field_150866_c = BlockPortal.field_150001_a[p_i45415_5_][1];
            for (int var6 = p_i45415_3_; p_i45415_3_ > var6 - 21 && p_i45415_3_ > 0 && this.func_150857_a(p_i45415_1_.getBlock(p_i45415_2_, p_i45415_3_ - 1, p_i45415_4_)); --p_i45415_3_) {}
            final int var7 = this.func_150853_a(p_i45415_2_, p_i45415_3_, p_i45415_4_, this.field_150863_d) - 1;
            if (var7 >= 0) {
                this.field_150861_f = new ChunkCoordinates(p_i45415_2_ + var7 * Direction.offsetX[this.field_150863_d], p_i45415_3_, p_i45415_4_ + var7 * Direction.offsetZ[this.field_150863_d]);
                this.field_150868_h = this.func_150853_a(this.field_150861_f.posX, this.field_150861_f.posY, this.field_150861_f.posZ, this.field_150866_c);
                if (this.field_150868_h < 2 || this.field_150868_h > 21) {
                    this.field_150861_f = null;
                    this.field_150868_h = 0;
                }
            }
            if (this.field_150861_f != null) {
                this.field_150862_g = this.func_150858_a();
            }
        }
        
        protected int func_150853_a(final int p_150853_1_, final int p_150853_2_, final int p_150853_3_, final int p_150853_4_) {
            final int var6 = Direction.offsetX[p_150853_4_];
            final int var7 = Direction.offsetZ[p_150853_4_];
            int var8;
            for (var8 = 0; var8 < 22; ++var8) {
                final Block var9 = this.field_150867_a.getBlock(p_150853_1_ + var6 * var8, p_150853_2_, p_150853_3_ + var7 * var8);
                if (!this.func_150857_a(var9)) {
                    break;
                }
                final Block var10 = this.field_150867_a.getBlock(p_150853_1_ + var6 * var8, p_150853_2_ - 1, p_150853_3_ + var7 * var8);
                if (var10 != Blocks.obsidian) {
                    break;
                }
            }
            final Block var9 = this.field_150867_a.getBlock(p_150853_1_ + var6 * var8, p_150853_2_, p_150853_3_ + var7 * var8);
            return (var9 == Blocks.obsidian) ? var8 : 0;
        }
        
        protected int func_150858_a() {
            this.field_150862_g = 0;
        Label_0272:
            while (this.field_150862_g < 21) {
                final int var1 = this.field_150861_f.posY + this.field_150862_g;
                for (int var2 = 0; var2 < this.field_150868_h; ++var2) {
                    final int var3 = this.field_150861_f.posX + var2 * Direction.offsetX[BlockPortal.field_150001_a[this.field_150865_b][1]];
                    final int var4 = this.field_150861_f.posZ + var2 * Direction.offsetZ[BlockPortal.field_150001_a[this.field_150865_b][1]];
                    Block var5 = this.field_150867_a.getBlock(var3, var1, var4);
                    if (!this.func_150857_a(var5)) {
                        break Label_0272;
                    }
                    if (var5 == Blocks.portal) {
                        ++this.field_150864_e;
                    }
                    if (var2 == 0) {
                        var5 = this.field_150867_a.getBlock(var3 + Direction.offsetX[BlockPortal.field_150001_a[this.field_150865_b][0]], var1, var4 + Direction.offsetZ[BlockPortal.field_150001_a[this.field_150865_b][0]]);
                        if (var5 != Blocks.obsidian) {
                            break Label_0272;
                        }
                    }
                    else if (var2 == this.field_150868_h - 1) {
                        var5 = this.field_150867_a.getBlock(var3 + Direction.offsetX[BlockPortal.field_150001_a[this.field_150865_b][1]], var1, var4 + Direction.offsetZ[BlockPortal.field_150001_a[this.field_150865_b][1]]);
                        if (var5 != Blocks.obsidian) {
                            break Label_0272;
                        }
                    }
                }
                ++this.field_150862_g;
            }
            for (int var1 = 0; var1 < this.field_150868_h; ++var1) {
                final int var2 = this.field_150861_f.posX + var1 * Direction.offsetX[BlockPortal.field_150001_a[this.field_150865_b][1]];
                final int var3 = this.field_150861_f.posY + this.field_150862_g;
                final int var4 = this.field_150861_f.posZ + var1 * Direction.offsetZ[BlockPortal.field_150001_a[this.field_150865_b][1]];
                if (this.field_150867_a.getBlock(var2, var3, var4) != Blocks.obsidian) {
                    this.field_150862_g = 0;
                    break;
                }
            }
            if (this.field_150862_g <= 21 && this.field_150862_g >= 3) {
                return this.field_150862_g;
            }
            this.field_150861_f = null;
            this.field_150868_h = 0;
            return this.field_150862_g = 0;
        }
        
        protected boolean func_150857_a(final Block p_150857_1_) {
            return p_150857_1_.blockMaterial == Material.air || p_150857_1_ == Blocks.fire || p_150857_1_ == Blocks.portal;
        }
        
        public boolean func_150860_b() {
            return this.field_150861_f != null && this.field_150868_h >= 2 && this.field_150868_h <= 21 && this.field_150862_g >= 3 && this.field_150862_g <= 21;
        }
        
        public void func_150859_c() {
            for (int var1 = 0; var1 < this.field_150868_h; ++var1) {
                final int var2 = this.field_150861_f.posX + Direction.offsetX[this.field_150866_c] * var1;
                final int var3 = this.field_150861_f.posZ + Direction.offsetZ[this.field_150866_c] * var1;
                for (int var4 = 0; var4 < this.field_150862_g; ++var4) {
                    final int var5 = this.field_150861_f.posY + var4;
                    this.field_150867_a.setBlock(var2, var5, var3, Blocks.portal, this.field_150865_b, 2);
                }
            }
        }
    }
}
