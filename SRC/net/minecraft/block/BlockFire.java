package net.minecraft.block;

import net.minecraft.init.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.world.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.block.material.*;

public class BlockFire extends Block
{
    private int[] field_149849_a;
    private int[] field_149848_b;
    private IIcon[] field_149850_M;
    private static final String __OBFID = "CL_00000245";
    
    protected BlockFire() {
        super(Material.fire);
        this.field_149849_a = new int[256];
        this.field_149848_b = new int[256];
        this.setTickRandomly(true);
    }
    
    public static void func_149843_e() {
        Blocks.fire.func_149842_a(Block.getIdFromBlock(Blocks.planks), 5, 20);
        Blocks.fire.func_149842_a(Block.getIdFromBlock(Blocks.double_wooden_slab), 5, 20);
        Blocks.fire.func_149842_a(Block.getIdFromBlock(Blocks.wooden_slab), 5, 20);
        Blocks.fire.func_149842_a(Block.getIdFromBlock(Blocks.fence), 5, 20);
        Blocks.fire.func_149842_a(Block.getIdFromBlock(Blocks.oak_stairs), 5, 20);
        Blocks.fire.func_149842_a(Block.getIdFromBlock(Blocks.birch_stairs), 5, 20);
        Blocks.fire.func_149842_a(Block.getIdFromBlock(Blocks.spruce_stairs), 5, 20);
        Blocks.fire.func_149842_a(Block.getIdFromBlock(Blocks.jungle_stairs), 5, 20);
        Blocks.fire.func_149842_a(Block.getIdFromBlock(Blocks.log), 5, 5);
        Blocks.fire.func_149842_a(Block.getIdFromBlock(Blocks.log2), 5, 5);
        Blocks.fire.func_149842_a(Block.getIdFromBlock(Blocks.leaves), 30, 60);
        Blocks.fire.func_149842_a(Block.getIdFromBlock(Blocks.leaves2), 30, 60);
        Blocks.fire.func_149842_a(Block.getIdFromBlock(Blocks.bookshelf), 30, 20);
        Blocks.fire.func_149842_a(Block.getIdFromBlock(Blocks.tnt), 15, 100);
        Blocks.fire.func_149842_a(Block.getIdFromBlock(Blocks.tallgrass), 60, 100);
        Blocks.fire.func_149842_a(Block.getIdFromBlock(Blocks.double_plant), 60, 100);
        Blocks.fire.func_149842_a(Block.getIdFromBlock(Blocks.yellow_flower), 60, 100);
        Blocks.fire.func_149842_a(Block.getIdFromBlock(Blocks.red_flower), 60, 100);
        Blocks.fire.func_149842_a(Block.getIdFromBlock(Blocks.wool), 30, 60);
        Blocks.fire.func_149842_a(Block.getIdFromBlock(Blocks.vine), 15, 100);
        Blocks.fire.func_149842_a(Block.getIdFromBlock(Blocks.coal_block), 5, 5);
        Blocks.fire.func_149842_a(Block.getIdFromBlock(Blocks.hay_block), 60, 20);
        Blocks.fire.func_149842_a(Block.getIdFromBlock(Blocks.carpet), 60, 20);
    }
    
    public void func_149842_a(final int p_149842_1_, final int p_149842_2_, final int p_149842_3_) {
        this.field_149849_a[p_149842_1_] = p_149842_2_;
        this.field_149848_b[p_149842_1_] = p_149842_3_;
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(final World p_149668_1_, final int p_149668_2_, final int p_149668_3_, final int p_149668_4_) {
        return null;
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
    public int getRenderType() {
        return 3;
    }
    
    @Override
    public int quantityDropped(final Random p_149745_1_) {
        return 0;
    }
    
    @Override
    public int func_149738_a(final World p_149738_1_) {
        return 30;
    }
    
    @Override
    public void updateTick(final World p_149674_1_, final int p_149674_2_, final int p_149674_3_, final int p_149674_4_, final Random p_149674_5_) {
        if (p_149674_1_.getGameRules().getGameRuleBooleanValue("doFireTick")) {
            boolean var6 = p_149674_1_.getBlock(p_149674_2_, p_149674_3_ - 1, p_149674_4_) == Blocks.netherrack;
            if (p_149674_1_.provider instanceof WorldProviderEnd && p_149674_1_.getBlock(p_149674_2_, p_149674_3_ - 1, p_149674_4_) == Blocks.bedrock) {
                var6 = true;
            }
            if (!this.canPlaceBlockAt(p_149674_1_, p_149674_2_, p_149674_3_, p_149674_4_)) {
                p_149674_1_.setBlockToAir(p_149674_2_, p_149674_3_, p_149674_4_);
            }
            if (!var6 && p_149674_1_.isRaining() && (p_149674_1_.canLightningStrikeAt(p_149674_2_, p_149674_3_, p_149674_4_) || p_149674_1_.canLightningStrikeAt(p_149674_2_ - 1, p_149674_3_, p_149674_4_) || p_149674_1_.canLightningStrikeAt(p_149674_2_ + 1, p_149674_3_, p_149674_4_) || p_149674_1_.canLightningStrikeAt(p_149674_2_, p_149674_3_, p_149674_4_ - 1) || p_149674_1_.canLightningStrikeAt(p_149674_2_, p_149674_3_, p_149674_4_ + 1))) {
                p_149674_1_.setBlockToAir(p_149674_2_, p_149674_3_, p_149674_4_);
            }
            else {
                final int var7 = p_149674_1_.getBlockMetadata(p_149674_2_, p_149674_3_, p_149674_4_);
                if (var7 < 15) {
                    p_149674_1_.setBlockMetadataWithNotify(p_149674_2_, p_149674_3_, p_149674_4_, var7 + p_149674_5_.nextInt(3) / 2, 4);
                }
                p_149674_1_.scheduleBlockUpdate(p_149674_2_, p_149674_3_, p_149674_4_, this, this.func_149738_a(p_149674_1_) + p_149674_5_.nextInt(10));
                if (!var6 && !this.func_149847_e(p_149674_1_, p_149674_2_, p_149674_3_, p_149674_4_)) {
                    if (!World.doesBlockHaveSolidTopSurface(p_149674_1_, p_149674_2_, p_149674_3_ - 1, p_149674_4_) || var7 > 3) {
                        p_149674_1_.setBlockToAir(p_149674_2_, p_149674_3_, p_149674_4_);
                    }
                }
                else if (!var6 && !this.func_149844_e(p_149674_1_, p_149674_2_, p_149674_3_ - 1, p_149674_4_) && var7 == 15 && p_149674_5_.nextInt(4) == 0) {
                    p_149674_1_.setBlockToAir(p_149674_2_, p_149674_3_, p_149674_4_);
                }
                else {
                    final boolean var8 = p_149674_1_.isBlockHighHumidity(p_149674_2_, p_149674_3_, p_149674_4_);
                    byte var9 = 0;
                    if (var8) {
                        var9 = -50;
                    }
                    this.func_149841_a(p_149674_1_, p_149674_2_ + 1, p_149674_3_, p_149674_4_, 300 + var9, p_149674_5_, var7);
                    this.func_149841_a(p_149674_1_, p_149674_2_ - 1, p_149674_3_, p_149674_4_, 300 + var9, p_149674_5_, var7);
                    this.func_149841_a(p_149674_1_, p_149674_2_, p_149674_3_ - 1, p_149674_4_, 250 + var9, p_149674_5_, var7);
                    this.func_149841_a(p_149674_1_, p_149674_2_, p_149674_3_ + 1, p_149674_4_, 250 + var9, p_149674_5_, var7);
                    this.func_149841_a(p_149674_1_, p_149674_2_, p_149674_3_, p_149674_4_ - 1, 300 + var9, p_149674_5_, var7);
                    this.func_149841_a(p_149674_1_, p_149674_2_, p_149674_3_, p_149674_4_ + 1, 300 + var9, p_149674_5_, var7);
                    for (int var10 = p_149674_2_ - 1; var10 <= p_149674_2_ + 1; ++var10) {
                        for (int var11 = p_149674_4_ - 1; var11 <= p_149674_4_ + 1; ++var11) {
                            for (int var12 = p_149674_3_ - 1; var12 <= p_149674_3_ + 4; ++var12) {
                                if (var10 != p_149674_2_ || var12 != p_149674_3_ || var11 != p_149674_4_) {
                                    int var13 = 100;
                                    if (var12 > p_149674_3_ + 1) {
                                        var13 += (var12 - (p_149674_3_ + 1)) * 100;
                                    }
                                    final int var14 = this.func_149845_m(p_149674_1_, var10, var12, var11);
                                    if (var14 > 0) {
                                        int var15 = (var14 + 40 + p_149674_1_.difficultySetting.getDifficultyId() * 7) / (var7 + 30);
                                        if (var8) {
                                            var15 /= 2;
                                        }
                                        if (var15 > 0 && p_149674_5_.nextInt(var13) <= var15 && (!p_149674_1_.isRaining() || !p_149674_1_.canLightningStrikeAt(var10, var12, var11)) && !p_149674_1_.canLightningStrikeAt(var10 - 1, var12, p_149674_4_) && !p_149674_1_.canLightningStrikeAt(var10 + 1, var12, var11) && !p_149674_1_.canLightningStrikeAt(var10, var12, var11 - 1) && !p_149674_1_.canLightningStrikeAt(var10, var12, var11 + 1)) {
                                            int var16 = var7 + p_149674_5_.nextInt(5) / 4;
                                            if (var16 > 15) {
                                                var16 = 15;
                                            }
                                            p_149674_1_.setBlock(var10, var12, var11, this, var16, 3);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    
    @Override
    public boolean func_149698_L() {
        return false;
    }
    
    private void func_149841_a(final World p_149841_1_, final int p_149841_2_, final int p_149841_3_, final int p_149841_4_, final int p_149841_5_, final Random p_149841_6_, final int p_149841_7_) {
        final int var8 = this.field_149848_b[Block.getIdFromBlock(p_149841_1_.getBlock(p_149841_2_, p_149841_3_, p_149841_4_))];
        if (p_149841_6_.nextInt(p_149841_5_) < var8) {
            final boolean var9 = p_149841_1_.getBlock(p_149841_2_, p_149841_3_, p_149841_4_) == Blocks.tnt;
            if (p_149841_6_.nextInt(p_149841_7_ + 10) < 5 && !p_149841_1_.canLightningStrikeAt(p_149841_2_, p_149841_3_, p_149841_4_)) {
                int var10 = p_149841_7_ + p_149841_6_.nextInt(5) / 4;
                if (var10 > 15) {
                    var10 = 15;
                }
                p_149841_1_.setBlock(p_149841_2_, p_149841_3_, p_149841_4_, this, var10, 3);
            }
            else {
                p_149841_1_.setBlockToAir(p_149841_2_, p_149841_3_, p_149841_4_);
            }
            if (var9) {
                Blocks.tnt.onBlockDestroyedByPlayer(p_149841_1_, p_149841_2_, p_149841_3_, p_149841_4_, 1);
            }
        }
    }
    
    private boolean func_149847_e(final World p_149847_1_, final int p_149847_2_, final int p_149847_3_, final int p_149847_4_) {
        return this.func_149844_e(p_149847_1_, p_149847_2_ + 1, p_149847_3_, p_149847_4_) || this.func_149844_e(p_149847_1_, p_149847_2_ - 1, p_149847_3_, p_149847_4_) || this.func_149844_e(p_149847_1_, p_149847_2_, p_149847_3_ - 1, p_149847_4_) || this.func_149844_e(p_149847_1_, p_149847_2_, p_149847_3_ + 1, p_149847_4_) || this.func_149844_e(p_149847_1_, p_149847_2_, p_149847_3_, p_149847_4_ - 1) || this.func_149844_e(p_149847_1_, p_149847_2_, p_149847_3_, p_149847_4_ + 1);
    }
    
    private int func_149845_m(final World p_149845_1_, final int p_149845_2_, final int p_149845_3_, final int p_149845_4_) {
        final byte var5 = 0;
        if (!p_149845_1_.isAirBlock(p_149845_2_, p_149845_3_, p_149845_4_)) {
            return 0;
        }
        int var6 = this.func_149846_a(p_149845_1_, p_149845_2_ + 1, p_149845_3_, p_149845_4_, var5);
        var6 = this.func_149846_a(p_149845_1_, p_149845_2_ - 1, p_149845_3_, p_149845_4_, var6);
        var6 = this.func_149846_a(p_149845_1_, p_149845_2_, p_149845_3_ - 1, p_149845_4_, var6);
        var6 = this.func_149846_a(p_149845_1_, p_149845_2_, p_149845_3_ + 1, p_149845_4_, var6);
        var6 = this.func_149846_a(p_149845_1_, p_149845_2_, p_149845_3_, p_149845_4_ - 1, var6);
        var6 = this.func_149846_a(p_149845_1_, p_149845_2_, p_149845_3_, p_149845_4_ + 1, var6);
        return var6;
    }
    
    @Override
    public boolean isCollidable() {
        return false;
    }
    
    public boolean func_149844_e(final IBlockAccess p_149844_1_, final int p_149844_2_, final int p_149844_3_, final int p_149844_4_) {
        return this.field_149849_a[Block.getIdFromBlock(p_149844_1_.getBlock(p_149844_2_, p_149844_3_, p_149844_4_))] > 0;
    }
    
    public int func_149846_a(final World p_149846_1_, final int p_149846_2_, final int p_149846_3_, final int p_149846_4_, final int p_149846_5_) {
        final int var6 = this.field_149849_a[Block.getIdFromBlock(p_149846_1_.getBlock(p_149846_2_, p_149846_3_, p_149846_4_))];
        return (var6 > p_149846_5_) ? var6 : p_149846_5_;
    }
    
    @Override
    public boolean canPlaceBlockAt(final World p_149742_1_, final int p_149742_2_, final int p_149742_3_, final int p_149742_4_) {
        return World.doesBlockHaveSolidTopSurface(p_149742_1_, p_149742_2_, p_149742_3_ - 1, p_149742_4_) || this.func_149847_e(p_149742_1_, p_149742_2_, p_149742_3_, p_149742_4_);
    }
    
    @Override
    public void onNeighborBlockChange(final World p_149695_1_, final int p_149695_2_, final int p_149695_3_, final int p_149695_4_, final Block p_149695_5_) {
        if (!World.doesBlockHaveSolidTopSurface(p_149695_1_, p_149695_2_, p_149695_3_ - 1, p_149695_4_) && !this.func_149847_e(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_)) {
            p_149695_1_.setBlockToAir(p_149695_2_, p_149695_3_, p_149695_4_);
        }
    }
    
    @Override
    public void onBlockAdded(final World p_149726_1_, final int p_149726_2_, final int p_149726_3_, final int p_149726_4_) {
        if (p_149726_1_.provider.dimensionId > 0 || !Blocks.portal.func_150000_e(p_149726_1_, p_149726_2_, p_149726_3_, p_149726_4_)) {
            if (!World.doesBlockHaveSolidTopSurface(p_149726_1_, p_149726_2_, p_149726_3_ - 1, p_149726_4_) && !this.func_149847_e(p_149726_1_, p_149726_2_, p_149726_3_, p_149726_4_)) {
                p_149726_1_.setBlockToAir(p_149726_2_, p_149726_3_, p_149726_4_);
            }
            else {
                p_149726_1_.scheduleBlockUpdate(p_149726_2_, p_149726_3_, p_149726_4_, this, this.func_149738_a(p_149726_1_) + p_149726_1_.rand.nextInt(10));
            }
        }
    }
    
    @Override
    public void randomDisplayTick(final World p_149734_1_, final int p_149734_2_, final int p_149734_3_, final int p_149734_4_, final Random p_149734_5_) {
        if (p_149734_5_.nextInt(24) == 0) {
            p_149734_1_.playSound(p_149734_2_ + 0.5f, p_149734_3_ + 0.5f, p_149734_4_ + 0.5f, "fire.fire", 1.0f + p_149734_5_.nextFloat(), p_149734_5_.nextFloat() * 0.7f + 0.3f, false);
        }
        if (!World.doesBlockHaveSolidTopSurface(p_149734_1_, p_149734_2_, p_149734_3_ - 1, p_149734_4_) && !Blocks.fire.func_149844_e(p_149734_1_, p_149734_2_, p_149734_3_ - 1, p_149734_4_)) {
            if (Blocks.fire.func_149844_e(p_149734_1_, p_149734_2_ - 1, p_149734_3_, p_149734_4_)) {
                for (int var6 = 0; var6 < 2; ++var6) {
                    final float var7 = p_149734_2_ + p_149734_5_.nextFloat() * 0.1f;
                    final float var8 = p_149734_3_ + p_149734_5_.nextFloat();
                    final float var9 = p_149734_4_ + p_149734_5_.nextFloat();
                    p_149734_1_.spawnParticle("largesmoke", var7, var8, var9, 0.0, 0.0, 0.0);
                }
            }
            if (Blocks.fire.func_149844_e(p_149734_1_, p_149734_2_ + 1, p_149734_3_, p_149734_4_)) {
                for (int var6 = 0; var6 < 2; ++var6) {
                    final float var7 = p_149734_2_ + 1 - p_149734_5_.nextFloat() * 0.1f;
                    final float var8 = p_149734_3_ + p_149734_5_.nextFloat();
                    final float var9 = p_149734_4_ + p_149734_5_.nextFloat();
                    p_149734_1_.spawnParticle("largesmoke", var7, var8, var9, 0.0, 0.0, 0.0);
                }
            }
            if (Blocks.fire.func_149844_e(p_149734_1_, p_149734_2_, p_149734_3_, p_149734_4_ - 1)) {
                for (int var6 = 0; var6 < 2; ++var6) {
                    final float var7 = p_149734_2_ + p_149734_5_.nextFloat();
                    final float var8 = p_149734_3_ + p_149734_5_.nextFloat();
                    final float var9 = p_149734_4_ + p_149734_5_.nextFloat() * 0.1f;
                    p_149734_1_.spawnParticle("largesmoke", var7, var8, var9, 0.0, 0.0, 0.0);
                }
            }
            if (Blocks.fire.func_149844_e(p_149734_1_, p_149734_2_, p_149734_3_, p_149734_4_ + 1)) {
                for (int var6 = 0; var6 < 2; ++var6) {
                    final float var7 = p_149734_2_ + p_149734_5_.nextFloat();
                    final float var8 = p_149734_3_ + p_149734_5_.nextFloat();
                    final float var9 = p_149734_4_ + 1 - p_149734_5_.nextFloat() * 0.1f;
                    p_149734_1_.spawnParticle("largesmoke", var7, var8, var9, 0.0, 0.0, 0.0);
                }
            }
            if (Blocks.fire.func_149844_e(p_149734_1_, p_149734_2_, p_149734_3_ + 1, p_149734_4_)) {
                for (int var6 = 0; var6 < 2; ++var6) {
                    final float var7 = p_149734_2_ + p_149734_5_.nextFloat();
                    final float var8 = p_149734_3_ + 1 - p_149734_5_.nextFloat() * 0.1f;
                    final float var9 = p_149734_4_ + p_149734_5_.nextFloat();
                    p_149734_1_.spawnParticle("largesmoke", var7, var8, var9, 0.0, 0.0, 0.0);
                }
            }
        }
        else {
            for (int var6 = 0; var6 < 3; ++var6) {
                final float var7 = p_149734_2_ + p_149734_5_.nextFloat();
                final float var8 = p_149734_3_ + p_149734_5_.nextFloat() * 0.5f + 0.5f;
                final float var9 = p_149734_4_ + p_149734_5_.nextFloat();
                p_149734_1_.spawnParticle("largesmoke", var7, var8, var9, 0.0, 0.0, 0.0);
            }
        }
    }
    
    @Override
    public void registerBlockIcons(final IIconRegister p_149651_1_) {
        this.field_149850_M = new IIcon[] { p_149651_1_.registerIcon(this.getTextureName() + "_layer_0"), p_149651_1_.registerIcon(this.getTextureName() + "_layer_1") };
    }
    
    public IIcon func_149840_c(final int p_149840_1_) {
        return this.field_149850_M[p_149840_1_];
    }
    
    @Override
    public IIcon getIcon(final int p_149691_1_, final int p_149691_2_) {
        return this.field_149850_M[0];
    }
    
    @Override
    public MapColor getMapColor(final int p_149728_1_) {
        return MapColor.field_151656_f;
    }
}
