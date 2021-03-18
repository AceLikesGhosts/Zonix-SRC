package net.minecraft.client.renderer;

import net.minecraft.client.*;
import us.zonix.client.module.impl.*;
import net.minecraft.init.*;
import net.minecraft.tileentity.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.block.material.*;
import net.minecraft.block.*;
import net.minecraft.src.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.tileentity.*;
import net.minecraft.client.renderer.texture.*;

public class RenderBlocks
{
    public IBlockAccess blockAccess;
    public IIcon overrideBlockTexture;
    public boolean flipTexture;
    public boolean field_152631_f;
    public boolean renderAllFaces;
    public static boolean fancyGrass;
    public static boolean cfgGrassFix;
    public boolean useInventoryTint;
    public boolean renderFromInside;
    public double renderMinX;
    public double renderMaxX;
    public double renderMinY;
    public double renderMaxY;
    public double renderMinZ;
    public double renderMaxZ;
    public boolean lockBlockBounds;
    public boolean partialRenderBounds;
    public final Minecraft minecraftRB;
    public int uvRotateEast;
    public int uvRotateWest;
    public int uvRotateSouth;
    public int uvRotateNorth;
    public int uvRotateTop;
    public int uvRotateBottom;
    public boolean enableAO;
    public float aoLightValueScratchXYZNNN;
    public float aoLightValueScratchXYNN;
    public float aoLightValueScratchXYZNNP;
    public float aoLightValueScratchYZNN;
    public float aoLightValueScratchYZNP;
    public float aoLightValueScratchXYZPNN;
    public float aoLightValueScratchXYPN;
    public float aoLightValueScratchXYZPNP;
    public float aoLightValueScratchXYZNPN;
    public float aoLightValueScratchXYNP;
    public float aoLightValueScratchXYZNPP;
    public float aoLightValueScratchYZPN;
    public float aoLightValueScratchXYZPPN;
    public float aoLightValueScratchXYPP;
    public float aoLightValueScratchYZPP;
    public float aoLightValueScratchXYZPPP;
    public float aoLightValueScratchXZNN;
    public float aoLightValueScratchXZPN;
    public float aoLightValueScratchXZNP;
    public float aoLightValueScratchXZPP;
    public int aoBrightnessXYZNNN;
    public int aoBrightnessXYNN;
    public int aoBrightnessXYZNNP;
    public int aoBrightnessYZNN;
    public int aoBrightnessYZNP;
    public int aoBrightnessXYZPNN;
    public int aoBrightnessXYPN;
    public int aoBrightnessXYZPNP;
    public int aoBrightnessXYZNPN;
    public int aoBrightnessXYNP;
    public int aoBrightnessXYZNPP;
    public int aoBrightnessYZPN;
    public int aoBrightnessXYZPPN;
    public int aoBrightnessXYPP;
    public int aoBrightnessYZPP;
    public int aoBrightnessXYZPPP;
    public int aoBrightnessXZNN;
    public int aoBrightnessXZPN;
    public int aoBrightnessXZNP;
    public int aoBrightnessXZPP;
    public int brightnessTopLeft;
    public int brightnessBottomLeft;
    public int brightnessBottomRight;
    public int brightnessTopRight;
    public float colorRedTopLeft;
    public float colorRedBottomLeft;
    public float colorRedBottomRight;
    public float colorRedTopRight;
    public float colorGreenTopLeft;
    public float colorGreenBottomLeft;
    public float colorGreenBottomRight;
    public float colorGreenTopRight;
    public float colorBlueTopLeft;
    public float colorBlueBottomLeft;
    public float colorBlueBottomRight;
    public float colorBlueTopRight;
    public boolean aoLightValuesCalculated;
    public float aoLightValueOpaque;
    public boolean betterSnowEnabled;
    private static RenderBlocks instance;
    
    public RenderBlocks(final IBlockAccess par1IBlockAccess) {
        this.useInventoryTint = true;
        this.renderFromInside = false;
        this.aoLightValueOpaque = 0.2f;
        this.betterSnowEnabled = true;
        this.blockAccess = par1IBlockAccess;
        this.field_152631_f = false;
        this.flipTexture = false;
        this.minecraftRB = Minecraft.getMinecraft();
        this.aoLightValueOpaque = 1.0f - Config.getAmbientOcclusionLevel() * 0.8f;
    }
    
    public RenderBlocks() {
        this.useInventoryTint = true;
        this.renderFromInside = false;
        this.aoLightValueOpaque = 0.2f;
        this.betterSnowEnabled = true;
        this.minecraftRB = Minecraft.getMinecraft();
    }
    
    public void setOverrideBlockTexture(final IIcon p_147757_1_) {
        this.overrideBlockTexture = p_147757_1_;
    }
    
    public void clearOverrideBlockTexture() {
        this.overrideBlockTexture = null;
    }
    
    public boolean hasOverrideBlockTexture() {
        return this.overrideBlockTexture != null;
    }
    
    public void setRenderFromInside(final boolean p_147786_1_) {
        this.renderFromInside = p_147786_1_;
    }
    
    public void setRenderAllFaces(final boolean p_147753_1_) {
        this.renderAllFaces = p_147753_1_;
    }
    
    public void setRenderBounds(final double p_147782_1_, final double p_147782_3_, final double p_147782_5_, final double p_147782_7_, final double p_147782_9_, final double p_147782_11_) {
        if (!this.lockBlockBounds) {
            this.renderMinX = p_147782_1_;
            this.renderMaxX = p_147782_7_;
            this.renderMinY = p_147782_3_;
            this.renderMaxY = p_147782_9_;
            this.renderMinZ = p_147782_5_;
            this.renderMaxZ = p_147782_11_;
            this.partialRenderBounds = (this.minecraftRB.gameSettings.ambientOcclusion >= 2 && (this.renderMinX > 0.0 || this.renderMaxX < 1.0 || this.renderMinY > 0.0 || this.renderMaxY < 1.0 || this.renderMinZ > 0.0 || this.renderMaxZ < 1.0));
        }
    }
    
    public void setRenderBoundsFromBlock(final Block p_147775_1_) {
        if (!this.lockBlockBounds) {
            this.renderMinX = p_147775_1_.getBlockBoundsMinX();
            this.renderMaxX = p_147775_1_.getBlockBoundsMaxX();
            this.renderMinY = p_147775_1_.getBlockBoundsMinY();
            this.renderMaxY = p_147775_1_.getBlockBoundsMaxY();
            this.renderMinZ = p_147775_1_.getBlockBoundsMinZ();
            this.renderMaxZ = p_147775_1_.getBlockBoundsMaxZ();
            this.partialRenderBounds = (this.minecraftRB.gameSettings.ambientOcclusion >= 2 && (this.renderMinX > 0.0 || this.renderMaxX < 1.0 || this.renderMinY > 0.0 || this.renderMaxY < 1.0 || this.renderMinZ > 0.0 || this.renderMaxZ < 1.0));
        }
    }
    
    public void overrideBlockBounds(final double p_147770_1_, final double p_147770_3_, final double p_147770_5_, final double p_147770_7_, final double p_147770_9_, final double p_147770_11_) {
        this.renderMinX = p_147770_1_;
        this.renderMaxX = p_147770_7_;
        this.renderMinY = p_147770_3_;
        this.renderMaxY = p_147770_9_;
        this.renderMinZ = p_147770_5_;
        this.renderMaxZ = p_147770_11_;
        this.lockBlockBounds = true;
        this.partialRenderBounds = (this.minecraftRB.gameSettings.ambientOcclusion >= 2 && (this.renderMinX > 0.0 || this.renderMaxX < 1.0 || this.renderMinY > 0.0 || this.renderMaxY < 1.0 || this.renderMinZ > 0.0 || this.renderMaxZ < 1.0));
    }
    
    public void unlockBlockBounds() {
        this.lockBlockBounds = false;
    }
    
    public void renderBlockUsingTexture(final Block p_147792_1_, final int p_147792_2_, final int p_147792_3_, final int p_147792_4_, final IIcon p_147792_5_) {
        this.setOverrideBlockTexture(p_147792_5_);
        this.renderBlockByRenderType(p_147792_1_, p_147792_2_, p_147792_3_, p_147792_4_);
        this.clearOverrideBlockTexture();
    }
    
    public void renderBlockAllFaces(final Block p_147769_1_, final int p_147769_2_, final int p_147769_3_, final int p_147769_4_) {
        this.renderAllFaces = true;
        this.renderBlockByRenderType(p_147769_1_, p_147769_2_, p_147769_3_, p_147769_4_);
        this.renderAllFaces = false;
    }
    
    public boolean renderBlockByRenderType(final Block par1Block, final int par2, final int par3, final int par4) {
        final int i = par1Block.getRenderType();
        if (i == -1) {
            return false;
        }
        if ((par1Block instanceof BlockGlass || par1Block instanceof BlockStainedGlass) && FPSBoost.CLEAR_GLASS.getValue()) {
            return true;
        }
        par1Block.setBlockBoundsBasedOnState(this.blockAccess, par2, par3, par4);
        if (Config.isBetterSnow() && par1Block == Blocks.standing_sign && this.hasSnowNeighbours(par2, par3, par4)) {
            this.renderSnow(par2, par3, par4, Blocks.snow_layer.getBlockBoundsMaxY());
        }
        this.setRenderBoundsFromBlock(par1Block);
        switch (i) {
            case 0: {
                return this.renderStandardBlock(par1Block, par2, par3, par4);
            }
            case 1: {
                return this.renderCrossedSquares(par1Block, par2, par3, par4);
            }
            case 2: {
                return this.renderBlockTorch(par1Block, par2, par3, par4);
            }
            case 3: {
                return this.renderBlockFire((BlockFire)par1Block, par2, par3, par4);
            }
            case 4: {
                return this.renderBlockFluids(par1Block, par2, par3, par4);
            }
            case 5: {
                return this.renderBlockRedstoneWire(par1Block, par2, par3, par4);
            }
            case 6: {
                return this.renderBlockCrops(par1Block, par2, par3, par4);
            }
            case 7: {
                return this.renderBlockDoor(par1Block, par2, par3, par4);
            }
            case 8: {
                return this.renderBlockLadder(par1Block, par2, par3, par4);
            }
            case 9: {
                return this.renderBlockMinecartTrack((BlockRailBase)par1Block, par2, par3, par4);
            }
            case 10: {
                return this.renderBlockStairs((BlockStairs)par1Block, par2, par3, par4);
            }
            case 11: {
                return this.renderBlockFence((BlockFence)par1Block, par2, par3, par4);
            }
            case 12: {
                return this.renderBlockLever(par1Block, par2, par3, par4);
            }
            case 13: {
                return this.renderBlockCactus(par1Block, par2, par3, par4);
            }
            case 14: {
                return this.renderBlockBed(par1Block, par2, par3, par4);
            }
            case 15: {
                return this.renderBlockRepeater((BlockRedstoneRepeater)par1Block, par2, par3, par4);
            }
            case 16: {
                return this.renderPistonBase(par1Block, par2, par3, par4, false);
            }
            case 17: {
                return this.renderPistonExtension(par1Block, par2, par3, par4, true);
            }
            case 18: {
                return this.renderBlockPane((BlockPane)par1Block, par2, par3, par4);
            }
            case 19: {
                return this.renderBlockStem(par1Block, par2, par3, par4);
            }
            case 20: {
                return this.renderBlockVine(par1Block, par2, par3, par4);
            }
            case 21: {
                return this.renderBlockFenceGate((BlockFenceGate)par1Block, par2, par3, par4);
            }
            default: {
                return false;
            }
            case 23: {
                return this.renderBlockLilyPad(par1Block, par2, par3, par4);
            }
            case 24: {
                return this.renderBlockCauldron((BlockCauldron)par1Block, par2, par3, par4);
            }
            case 25: {
                return this.renderBlockBrewingStand((BlockBrewingStand)par1Block, par2, par3, par4);
            }
            case 26: {
                return this.renderBlockEndPortalFrame((BlockEndPortalFrame)par1Block, par2, par3, par4);
            }
            case 27: {
                return this.renderBlockDragonEgg((BlockDragonEgg)par1Block, par2, par3, par4);
            }
            case 28: {
                return this.renderBlockCocoa((BlockCocoa)par1Block, par2, par3, par4);
            }
            case 29: {
                return this.renderBlockTripWireSource(par1Block, par2, par3, par4);
            }
            case 30: {
                return this.renderBlockTripWire(par1Block, par2, par3, par4);
            }
            case 31: {
                return this.renderBlockLog(par1Block, par2, par3, par4);
            }
            case 32: {
                return this.renderBlockWall((BlockWall)par1Block, par2, par3, par4);
            }
            case 33: {
                return this.renderBlockFlowerpot((BlockFlowerPot)par1Block, par2, par3, par4);
            }
            case 34: {
                return this.renderBlockBeacon((BlockBeacon)par1Block, par2, par3, par4);
            }
            case 35: {
                return this.renderBlockAnvil((BlockAnvil)par1Block, par2, par3, par4);
            }
            case 36: {
                return this.renderBlockRedstoneDiode((BlockRedstoneDiode)par1Block, par2, par3, par4);
            }
            case 37: {
                return this.renderBlockRedstoneComparator((BlockRedstoneComparator)par1Block, par2, par3, par4);
            }
            case 38: {
                return this.renderBlockHopper((BlockHopper)par1Block, par2, par3, par4);
            }
            case 39: {
                return this.renderBlockQuartz(par1Block, par2, par3, par4);
            }
            case 40: {
                return this.renderBlockDoublePlant((BlockDoublePlant)par1Block, par2, par3, par4);
            }
            case 41: {
                return this.renderBlockStainedGlassPane(par1Block, par2, par3, par4);
            }
        }
    }
    
    public boolean renderBlockEndPortalFrame(final BlockEndPortalFrame p_147743_1_, final int p_147743_2_, final int p_147743_3_, final int p_147743_4_) {
        final int var5 = this.blockAccess.getBlockMetadata(p_147743_2_, p_147743_3_, p_147743_4_);
        final int var6 = var5 & 0x3;
        if (var6 == 0) {
            this.uvRotateTop = 3;
        }
        else if (var6 == 3) {
            this.uvRotateTop = 1;
        }
        else if (var6 == 1) {
            this.uvRotateTop = 2;
        }
        if (!BlockEndPortalFrame.func_150020_b(var5)) {
            this.setRenderBounds(0.0, 0.0, 0.0, 1.0, 0.8125, 1.0);
            this.renderStandardBlock(p_147743_1_, p_147743_2_, p_147743_3_, p_147743_4_);
            this.uvRotateTop = 0;
            return true;
        }
        this.renderAllFaces = true;
        this.setRenderBounds(0.0, 0.0, 0.0, 1.0, 0.8125, 1.0);
        this.renderStandardBlock(p_147743_1_, p_147743_2_, p_147743_3_, p_147743_4_);
        this.setOverrideBlockTexture(p_147743_1_.func_150021_e());
        this.setRenderBounds(0.25, 0.8125, 0.25, 0.75, 1.0, 0.75);
        this.renderStandardBlock(p_147743_1_, p_147743_2_, p_147743_3_, p_147743_4_);
        this.renderAllFaces = false;
        this.clearOverrideBlockTexture();
        this.uvRotateTop = 0;
        return true;
    }
    
    public boolean renderBlockBed(final Block p_147773_1_, final int p_147773_2_, final int p_147773_3_, final int p_147773_4_) {
        final Tessellator var5 = Tessellator.instance;
        final int var6 = this.blockAccess.getBlockMetadata(p_147773_2_, p_147773_3_, p_147773_4_);
        final int var7 = BlockDirectional.func_149895_l(var6);
        final boolean var8 = BlockBed.func_149975_b(var6);
        final float var9 = 0.5f;
        final float var10 = 1.0f;
        final float var11 = 0.8f;
        final float var12 = 0.6f;
        final int var13 = p_147773_1_.getBlockBrightness(this.blockAccess, p_147773_2_, p_147773_3_, p_147773_4_);
        var5.setBrightness(var13);
        var5.setColorOpaque_F(var9, var9, var9);
        IIcon var14 = this.getBlockIcon(p_147773_1_, this.blockAccess, p_147773_2_, p_147773_3_, p_147773_4_, 0);
        if (this.overrideBlockTexture != null) {
            var14 = this.overrideBlockTexture;
        }
        double var15 = var14.getMinU();
        double var16 = var14.getMaxU();
        double var17 = var14.getMinV();
        double var18 = var14.getMaxV();
        double var19 = p_147773_2_ + this.renderMinX;
        double var20 = p_147773_2_ + this.renderMaxX;
        double var21 = p_147773_3_ + this.renderMinY + 0.1875;
        double var22 = p_147773_4_ + this.renderMinZ;
        double var23 = p_147773_4_ + this.renderMaxZ;
        var5.addVertexWithUV(var19, var21, var23, var15, var18);
        var5.addVertexWithUV(var19, var21, var22, var15, var17);
        var5.addVertexWithUV(var20, var21, var22, var16, var17);
        var5.addVertexWithUV(var20, var21, var23, var16, var18);
        var5.setBrightness(p_147773_1_.getBlockBrightness(this.blockAccess, p_147773_2_, p_147773_3_ + 1, p_147773_4_));
        var5.setColorOpaque_F(var10, var10, var10);
        var14 = this.getBlockIcon(p_147773_1_, this.blockAccess, p_147773_2_, p_147773_3_, p_147773_4_, 1);
        if (this.overrideBlockTexture != null) {
            var14 = this.overrideBlockTexture;
        }
        var15 = var14.getMinU();
        var16 = var14.getMaxU();
        var17 = var14.getMinV();
        var18 = var14.getMaxV();
        var19 = var15;
        var20 = var16;
        var21 = var17;
        var22 = var17;
        var23 = var15;
        double var24 = var16;
        double var25 = var18;
        double var26 = var18;
        if (var7 == 0) {
            var20 = var15;
            var21 = var18;
            var23 = var16;
            var26 = var17;
        }
        else if (var7 == 2) {
            var19 = var16;
            var22 = var18;
            var24 = var15;
            var25 = var17;
        }
        else if (var7 == 3) {
            var19 = var16;
            var22 = var18;
            var24 = var15;
            var25 = var17;
            var20 = var15;
            var21 = var18;
            var23 = var16;
            var26 = var17;
        }
        final double var27 = p_147773_2_ + this.renderMinX;
        final double var28 = p_147773_2_ + this.renderMaxX;
        final double var29 = p_147773_3_ + this.renderMaxY;
        final double var30 = p_147773_4_ + this.renderMinZ;
        final double var31 = p_147773_4_ + this.renderMaxZ;
        var5.addVertexWithUV(var28, var29, var31, var23, var25);
        var5.addVertexWithUV(var28, var29, var30, var19, var21);
        var5.addVertexWithUV(var27, var29, var30, var20, var22);
        var5.addVertexWithUV(var27, var29, var31, var24, var26);
        int var32 = Direction.directionToFacing[var7];
        if (var8) {
            var32 = Direction.directionToFacing[Direction.rotateOpposite[var7]];
        }
        byte var33 = 4;
        switch (var7) {
            case 0: {
                var33 = 5;
                break;
            }
            case 1: {
                var33 = 3;
                break;
            }
            case 3: {
                var33 = 2;
                break;
            }
        }
        if (var32 != 2 && (this.renderAllFaces || p_147773_1_.shouldSideBeRendered(this.blockAccess, p_147773_2_, p_147773_3_, p_147773_4_ - 1, 2))) {
            var5.setBrightness((this.renderMinZ > 0.0) ? var13 : p_147773_1_.getBlockBrightness(this.blockAccess, p_147773_2_, p_147773_3_, p_147773_4_ - 1));
            var5.setColorOpaque_F(var11, var11, var11);
            this.flipTexture = (var33 == 2);
            this.renderFaceZNeg(p_147773_1_, p_147773_2_, p_147773_3_, p_147773_4_, this.getBlockIcon(p_147773_1_, this.blockAccess, p_147773_2_, p_147773_3_, p_147773_4_, 2));
        }
        if (var32 != 3 && (this.renderAllFaces || p_147773_1_.shouldSideBeRendered(this.blockAccess, p_147773_2_, p_147773_3_, p_147773_4_ + 1, 3))) {
            var5.setBrightness((this.renderMaxZ < 1.0) ? var13 : p_147773_1_.getBlockBrightness(this.blockAccess, p_147773_2_, p_147773_3_, p_147773_4_ + 1));
            var5.setColorOpaque_F(var11, var11, var11);
            this.flipTexture = (var33 == 3);
            this.renderFaceZPos(p_147773_1_, p_147773_2_, p_147773_3_, p_147773_4_, this.getBlockIcon(p_147773_1_, this.blockAccess, p_147773_2_, p_147773_3_, p_147773_4_, 3));
        }
        if (var32 != 4 && (this.renderAllFaces || p_147773_1_.shouldSideBeRendered(this.blockAccess, p_147773_2_ - 1, p_147773_3_, p_147773_4_, 4))) {
            var5.setBrightness((this.renderMinZ > 0.0) ? var13 : p_147773_1_.getBlockBrightness(this.blockAccess, p_147773_2_ - 1, p_147773_3_, p_147773_4_));
            var5.setColorOpaque_F(var12, var12, var12);
            this.flipTexture = (var33 == 4);
            this.renderFaceXNeg(p_147773_1_, p_147773_2_, p_147773_3_, p_147773_4_, this.getBlockIcon(p_147773_1_, this.blockAccess, p_147773_2_, p_147773_3_, p_147773_4_, 4));
        }
        if (var32 != 5 && (this.renderAllFaces || p_147773_1_.shouldSideBeRendered(this.blockAccess, p_147773_2_ + 1, p_147773_3_, p_147773_4_, 5))) {
            var5.setBrightness((this.renderMaxZ < 1.0) ? var13 : p_147773_1_.getBlockBrightness(this.blockAccess, p_147773_2_ + 1, p_147773_3_, p_147773_4_));
            var5.setColorOpaque_F(var12, var12, var12);
            this.flipTexture = (var33 == 5);
            this.renderFaceXPos(p_147773_1_, p_147773_2_, p_147773_3_, p_147773_4_, this.getBlockIcon(p_147773_1_, this.blockAccess, p_147773_2_, p_147773_3_, p_147773_4_, 5));
        }
        this.flipTexture = false;
        return true;
    }
    
    public boolean renderBlockBrewingStand(final BlockBrewingStand p_147741_1_, final int p_147741_2_, final int p_147741_3_, final int p_147741_4_) {
        this.setRenderBounds(0.4375, 0.0, 0.4375, 0.5625, 0.875, 0.5625);
        this.renderStandardBlock(p_147741_1_, p_147741_2_, p_147741_3_, p_147741_4_);
        this.setOverrideBlockTexture(p_147741_1_.func_149959_e());
        this.renderAllFaces = true;
        this.setRenderBounds(0.5625, 0.0, 0.3125, 0.9375, 0.125, 0.6875);
        this.renderStandardBlock(p_147741_1_, p_147741_2_, p_147741_3_, p_147741_4_);
        this.setRenderBounds(0.125, 0.0, 0.0625, 0.5, 0.125, 0.4375);
        this.renderStandardBlock(p_147741_1_, p_147741_2_, p_147741_3_, p_147741_4_);
        this.setRenderBounds(0.125, 0.0, 0.5625, 0.5, 0.125, 0.9375);
        this.renderStandardBlock(p_147741_1_, p_147741_2_, p_147741_3_, p_147741_4_);
        this.renderAllFaces = false;
        this.clearOverrideBlockTexture();
        final Tessellator var5 = Tessellator.instance;
        var5.setBrightness(p_147741_1_.getBlockBrightness(this.blockAccess, p_147741_2_, p_147741_3_, p_147741_4_));
        final int var6 = p_147741_1_.colorMultiplier(this.blockAccess, p_147741_2_, p_147741_3_, p_147741_4_);
        float var7 = (var6 >> 16 & 0xFF) / 255.0f;
        float var8 = (var6 >> 8 & 0xFF) / 255.0f;
        float var9 = (var6 & 0xFF) / 255.0f;
        if (EntityRenderer.anaglyphEnable) {
            final float var10 = (var7 * 30.0f + var8 * 59.0f + var9 * 11.0f) / 100.0f;
            final float var11 = (var7 * 30.0f + var8 * 70.0f) / 100.0f;
            final float var12 = (var7 * 30.0f + var9 * 70.0f) / 100.0f;
            var7 = var10;
            var8 = var11;
            var9 = var12;
        }
        var5.setColorOpaque_F(var7, var8, var9);
        IIcon var13 = this.getBlockIconFromSideAndMetadata(p_147741_1_, 0, 0);
        if (this.hasOverrideBlockTexture()) {
            var13 = this.overrideBlockTexture;
        }
        if (Config.isConnectedTextures() && this.overrideBlockTexture == null) {
            var13 = ConnectedTextures.getConnectedTexture(this.blockAccess, p_147741_1_, p_147741_2_, p_147741_3_, p_147741_4_, -1, var13);
        }
        final double var14 = var13.getMinV();
        final double var15 = var13.getMaxV();
        final int var16 = this.blockAccess.getBlockMetadata(p_147741_2_, p_147741_3_, p_147741_4_);
        for (int var17 = 0; var17 < 3; ++var17) {
            final double var18 = var17 * 3.141592653589793 * 2.0 / 3.0 + 1.5707963267948966;
            final double var19 = var13.getInterpolatedU(8.0);
            double var20 = var13.getMaxU();
            if ((var16 & 1 << var17) != 0x0) {
                var20 = var13.getMinU();
            }
            final double var21 = p_147741_2_ + 0.5;
            final double var22 = p_147741_2_ + 0.5 + Math.sin(var18) * 8.0 / 16.0;
            final double var23 = p_147741_4_ + 0.5;
            final double var24 = p_147741_4_ + 0.5 + Math.cos(var18) * 8.0 / 16.0;
            var5.addVertexWithUV(var21, p_147741_3_ + 1, var23, var19, var14);
            var5.addVertexWithUV(var21, p_147741_3_ + 0, var23, var19, var15);
            var5.addVertexWithUV(var22, p_147741_3_ + 0, var24, var20, var15);
            var5.addVertexWithUV(var22, p_147741_3_ + 1, var24, var20, var14);
            var5.addVertexWithUV(var22, p_147741_3_ + 1, var24, var20, var14);
            var5.addVertexWithUV(var22, p_147741_3_ + 0, var24, var20, var15);
            var5.addVertexWithUV(var21, p_147741_3_ + 0, var23, var19, var15);
            var5.addVertexWithUV(var21, p_147741_3_ + 1, var23, var19, var14);
        }
        p_147741_1_.setBlockBoundsForItemRender();
        return true;
    }
    
    public boolean renderBlockCauldron(final BlockCauldron p_147785_1_, final int p_147785_2_, final int p_147785_3_, final int p_147785_4_) {
        this.renderStandardBlock(p_147785_1_, p_147785_2_, p_147785_3_, p_147785_4_);
        final Tessellator var5 = Tessellator.instance;
        var5.setBrightness(p_147785_1_.getBlockBrightness(this.blockAccess, p_147785_2_, p_147785_3_, p_147785_4_));
        final int var6 = p_147785_1_.colorMultiplier(this.blockAccess, p_147785_2_, p_147785_3_, p_147785_4_);
        float var7 = (var6 >> 16 & 0xFF) / 255.0f;
        float var8 = (var6 >> 8 & 0xFF) / 255.0f;
        float var9 = (var6 & 0xFF) / 255.0f;
        if (EntityRenderer.anaglyphEnable) {
            final float var10 = (var7 * 30.0f + var8 * 59.0f + var9 * 11.0f) / 100.0f;
            final float var11 = (var7 * 30.0f + var8 * 70.0f) / 100.0f;
            final float var12 = (var7 * 30.0f + var9 * 70.0f) / 100.0f;
            var7 = var10;
            var8 = var11;
            var9 = var12;
        }
        var5.setColorOpaque_F(var7, var8, var9);
        final IIcon var13 = p_147785_1_.getBlockTextureFromSide(2);
        final float var11 = 0.125f;
        this.renderFaceXPos(p_147785_1_, p_147785_2_ - 1.0f + var11, p_147785_3_, p_147785_4_, var13);
        this.renderFaceXNeg(p_147785_1_, p_147785_2_ + 1.0f - var11, p_147785_3_, p_147785_4_, var13);
        this.renderFaceZPos(p_147785_1_, p_147785_2_, p_147785_3_, p_147785_4_ - 1.0f + var11, var13);
        this.renderFaceZNeg(p_147785_1_, p_147785_2_, p_147785_3_, p_147785_4_ + 1.0f - var11, var13);
        final IIcon var14 = BlockCauldron.func_150026_e("inner");
        this.renderFaceYPos(p_147785_1_, p_147785_2_, p_147785_3_ - 1.0f + 0.25f, p_147785_4_, var14);
        this.renderFaceYNeg(p_147785_1_, p_147785_2_, p_147785_3_ + 1.0f - 0.75f, p_147785_4_, var14);
        final int var15 = this.blockAccess.getBlockMetadata(p_147785_2_, p_147785_3_, p_147785_4_);
        if (var15 > 0) {
            final IIcon var16 = BlockLiquid.func_149803_e("water_still");
            final int wc = CustomColorizer.getFluidColor(Blocks.water, this.blockAccess, p_147785_2_, p_147785_3_, p_147785_4_);
            final float wr = (wc >> 16 & 0xFF) / 255.0f;
            final float wg = (wc >> 8 & 0xFF) / 255.0f;
            final float wb = (wc & 0xFF) / 255.0f;
            var5.setColorOpaque_F(wr, wg, wb);
            this.renderFaceYPos(p_147785_1_, p_147785_2_, p_147785_3_ - 1.0f + BlockCauldron.func_150025_c(var15), p_147785_4_, var16);
        }
        return true;
    }
    
    public boolean renderBlockFlowerpot(final BlockFlowerPot p_147752_1_, final int p_147752_2_, final int p_147752_3_, final int p_147752_4_) {
        this.renderStandardBlock(p_147752_1_, p_147752_2_, p_147752_3_, p_147752_4_);
        final Tessellator var5 = Tessellator.instance;
        var5.setBrightness(p_147752_1_.getBlockBrightness(this.blockAccess, p_147752_2_, p_147752_3_, p_147752_4_));
        int var6 = p_147752_1_.colorMultiplier(this.blockAccess, p_147752_2_, p_147752_3_, p_147752_4_);
        final IIcon var7 = this.getBlockIconFromSide(p_147752_1_, 0);
        float var8 = (var6 >> 16 & 0xFF) / 255.0f;
        float var9 = (var6 >> 8 & 0xFF) / 255.0f;
        float var10 = (var6 & 0xFF) / 255.0f;
        if (EntityRenderer.anaglyphEnable) {
            final float var11 = (var8 * 30.0f + var9 * 59.0f + var10 * 11.0f) / 100.0f;
            final float var12 = (var8 * 30.0f + var9 * 70.0f) / 100.0f;
            final float var13 = (var8 * 30.0f + var10 * 70.0f) / 100.0f;
            var8 = var11;
            var9 = var12;
            var10 = var13;
        }
        var5.setColorOpaque_F(var8, var9, var10);
        final float var11 = 0.1865f;
        this.renderFaceXPos(p_147752_1_, p_147752_2_ - 0.5f + var11, p_147752_3_, p_147752_4_, var7);
        this.renderFaceXNeg(p_147752_1_, p_147752_2_ + 0.5f - var11, p_147752_3_, p_147752_4_, var7);
        this.renderFaceZPos(p_147752_1_, p_147752_2_, p_147752_3_, p_147752_4_ - 0.5f + var11, var7);
        this.renderFaceZNeg(p_147752_1_, p_147752_2_, p_147752_3_, p_147752_4_ + 0.5f - var11, var7);
        this.renderFaceYPos(p_147752_1_, p_147752_2_, p_147752_3_ - 0.5f + var11 + 0.1875f, p_147752_4_, this.getBlockIcon(Blocks.dirt));
        final TileEntity var14 = this.blockAccess.getTileEntity(p_147752_2_, p_147752_3_, p_147752_4_);
        if (var14 != null && var14 instanceof TileEntityFlowerPot) {
            final Item var15 = ((TileEntityFlowerPot)var14).func_145965_a();
            final int var16 = ((TileEntityFlowerPot)var14).func_145966_b();
            if (var15 instanceof ItemBlock) {
                final Block var17 = Block.getBlockFromItem(var15);
                final int var18 = var17.getRenderType();
                final float var19 = 0.0f;
                final float var20 = 4.0f;
                final float var21 = 0.0f;
                var5.addTranslation(var19 / 16.0f, var20 / 16.0f, var21 / 16.0f);
                var6 = var17.colorMultiplier(this.blockAccess, p_147752_2_, p_147752_3_, p_147752_4_);
                if (var6 != 16777215) {
                    var8 = (var6 >> 16 & 0xFF) / 255.0f;
                    var9 = (var6 >> 8 & 0xFF) / 255.0f;
                    var10 = (var6 & 0xFF) / 255.0f;
                    var5.setColorOpaque_F(var8, var9, var10);
                }
                if (var18 == 1) {
                    this.drawCrossedSquares(this.getBlockIconFromSideAndMetadata(var17, 0, var16), p_147752_2_, p_147752_3_, p_147752_4_, 0.75f);
                }
                else if (var18 == 13) {
                    this.renderAllFaces = true;
                    final float var22 = 0.125f;
                    this.setRenderBounds(0.5f - var22, 0.0, 0.5f - var22, 0.5f + var22, 0.25, 0.5f + var22);
                    this.renderStandardBlock(var17, p_147752_2_, p_147752_3_, p_147752_4_);
                    this.setRenderBounds(0.5f - var22, 0.25, 0.5f - var22, 0.5f + var22, 0.5, 0.5f + var22);
                    this.renderStandardBlock(var17, p_147752_2_, p_147752_3_, p_147752_4_);
                    this.setRenderBounds(0.5f - var22, 0.5, 0.5f - var22, 0.5f + var22, 0.75, 0.5f + var22);
                    this.renderStandardBlock(var17, p_147752_2_, p_147752_3_, p_147752_4_);
                    this.renderAllFaces = false;
                    this.setRenderBounds(0.0, 0.0, 0.0, 1.0, 1.0, 1.0);
                }
                var5.addTranslation(-var19 / 16.0f, -var20 / 16.0f, -var21 / 16.0f);
            }
        }
        if (Config.isBetterSnow() && this.hasSnowNeighbours(p_147752_2_, p_147752_3_, p_147752_4_)) {
            this.renderSnow(p_147752_2_, p_147752_3_, p_147752_4_, Blocks.snow_layer.getBlockBoundsMaxY());
        }
        return true;
    }
    
    public boolean renderBlockAnvil(final BlockAnvil p_147725_1_, final int p_147725_2_, final int p_147725_3_, final int p_147725_4_) {
        return this.renderBlockAnvilMetadata(p_147725_1_, p_147725_2_, p_147725_3_, p_147725_4_, this.blockAccess.getBlockMetadata(p_147725_2_, p_147725_3_, p_147725_4_));
    }
    
    public boolean renderBlockAnvilMetadata(final BlockAnvil p_147780_1_, final int p_147780_2_, final int p_147780_3_, final int p_147780_4_, final int p_147780_5_) {
        final Tessellator var6 = Tessellator.instance;
        var6.setBrightness(p_147780_1_.getBlockBrightness(this.blockAccess, p_147780_2_, p_147780_3_, p_147780_4_));
        final int var7 = p_147780_1_.colorMultiplier(this.blockAccess, p_147780_2_, p_147780_3_, p_147780_4_);
        float var8 = (var7 >> 16 & 0xFF) / 255.0f;
        float var9 = (var7 >> 8 & 0xFF) / 255.0f;
        float var10 = (var7 & 0xFF) / 255.0f;
        if (EntityRenderer.anaglyphEnable) {
            final float var11 = (var8 * 30.0f + var9 * 59.0f + var10 * 11.0f) / 100.0f;
            final float var12 = (var8 * 30.0f + var9 * 70.0f) / 100.0f;
            final float var13 = (var8 * 30.0f + var10 * 70.0f) / 100.0f;
            var8 = var11;
            var9 = var12;
            var10 = var13;
        }
        var6.setColorOpaque_F(var8, var9, var10);
        return this.renderBlockAnvilOrient(p_147780_1_, p_147780_2_, p_147780_3_, p_147780_4_, p_147780_5_, false);
    }
    
    public boolean renderBlockAnvilOrient(final BlockAnvil p_147728_1_, final int p_147728_2_, final int p_147728_3_, final int p_147728_4_, final int p_147728_5_, final boolean p_147728_6_) {
        final int var7 = p_147728_6_ ? 0 : (p_147728_5_ & 0x3);
        boolean var8 = false;
        float var9 = 0.0f;
        switch (var7) {
            case 0: {
                this.uvRotateSouth = 2;
                this.uvRotateNorth = 1;
                this.uvRotateTop = 3;
                this.uvRotateBottom = 3;
                break;
            }
            case 1: {
                this.uvRotateEast = 1;
                this.uvRotateWest = 2;
                this.uvRotateTop = 2;
                this.uvRotateBottom = 1;
                var8 = true;
                break;
            }
            case 2: {
                this.uvRotateSouth = 1;
                this.uvRotateNorth = 2;
                break;
            }
            case 3: {
                this.uvRotateEast = 2;
                this.uvRotateWest = 1;
                this.uvRotateTop = 1;
                this.uvRotateBottom = 2;
                var8 = true;
                break;
            }
        }
        var9 = this.renderBlockAnvilRotate(p_147728_1_, p_147728_2_, p_147728_3_, p_147728_4_, 0, var9, 0.75f, 0.25f, 0.75f, var8, p_147728_6_, p_147728_5_);
        var9 = this.renderBlockAnvilRotate(p_147728_1_, p_147728_2_, p_147728_3_, p_147728_4_, 1, var9, 0.5f, 0.0625f, 0.625f, var8, p_147728_6_, p_147728_5_);
        var9 = this.renderBlockAnvilRotate(p_147728_1_, p_147728_2_, p_147728_3_, p_147728_4_, 2, var9, 0.25f, 0.3125f, 0.5f, var8, p_147728_6_, p_147728_5_);
        this.renderBlockAnvilRotate(p_147728_1_, p_147728_2_, p_147728_3_, p_147728_4_, 3, var9, 0.625f, 0.375f, 1.0f, var8, p_147728_6_, p_147728_5_);
        this.setRenderBounds(0.0, 0.0, 0.0, 1.0, 1.0, 1.0);
        this.uvRotateEast = 0;
        this.uvRotateWest = 0;
        this.uvRotateSouth = 0;
        this.uvRotateNorth = 0;
        this.uvRotateTop = 0;
        this.uvRotateBottom = 0;
        return true;
    }
    
    public float renderBlockAnvilRotate(final BlockAnvil p_147737_1_, final int p_147737_2_, final int p_147737_3_, final int p_147737_4_, final int p_147737_5_, final float p_147737_6_, float p_147737_7_, final float p_147737_8_, float p_147737_9_, final boolean p_147737_10_, final boolean p_147737_11_, final int p_147737_12_) {
        if (p_147737_10_) {
            final float var14 = p_147737_7_;
            p_147737_7_ = p_147737_9_;
            p_147737_9_ = var14;
        }
        p_147737_7_ /= 2.0f;
        p_147737_9_ /= 2.0f;
        p_147737_1_.field_149833_b = p_147737_5_;
        this.setRenderBounds(0.5f - p_147737_7_, p_147737_6_, 0.5f - p_147737_9_, 0.5f + p_147737_7_, p_147737_6_ + p_147737_8_, 0.5f + p_147737_9_);
        if (p_147737_11_) {
            final Tessellator var15 = Tessellator.instance;
            var15.startDrawingQuads();
            var15.setNormal(0.0f, -1.0f, 0.0f);
            this.renderFaceYNeg(p_147737_1_, 0.0, 0.0, 0.0, this.getBlockIconFromSideAndMetadata(p_147737_1_, 0, p_147737_12_));
            var15.draw();
            var15.startDrawingQuads();
            var15.setNormal(0.0f, 1.0f, 0.0f);
            this.renderFaceYPos(p_147737_1_, 0.0, 0.0, 0.0, this.getBlockIconFromSideAndMetadata(p_147737_1_, 1, p_147737_12_));
            var15.draw();
            var15.startDrawingQuads();
            var15.setNormal(0.0f, 0.0f, -1.0f);
            this.renderFaceZNeg(p_147737_1_, 0.0, 0.0, 0.0, this.getBlockIconFromSideAndMetadata(p_147737_1_, 2, p_147737_12_));
            var15.draw();
            var15.startDrawingQuads();
            var15.setNormal(0.0f, 0.0f, 1.0f);
            this.renderFaceZPos(p_147737_1_, 0.0, 0.0, 0.0, this.getBlockIconFromSideAndMetadata(p_147737_1_, 3, p_147737_12_));
            var15.draw();
            var15.startDrawingQuads();
            var15.setNormal(-1.0f, 0.0f, 0.0f);
            this.renderFaceXNeg(p_147737_1_, 0.0, 0.0, 0.0, this.getBlockIconFromSideAndMetadata(p_147737_1_, 4, p_147737_12_));
            var15.draw();
            var15.startDrawingQuads();
            var15.setNormal(1.0f, 0.0f, 0.0f);
            this.renderFaceXPos(p_147737_1_, 0.0, 0.0, 0.0, this.getBlockIconFromSideAndMetadata(p_147737_1_, 5, p_147737_12_));
            var15.draw();
        }
        else {
            this.renderStandardBlock(p_147737_1_, p_147737_2_, p_147737_3_, p_147737_4_);
        }
        return p_147737_6_ + p_147737_8_;
    }
    
    public boolean renderBlockTorch(final Block p_147791_1_, final int p_147791_2_, final int p_147791_3_, final int p_147791_4_) {
        final int var5 = this.blockAccess.getBlockMetadata(p_147791_2_, p_147791_3_, p_147791_4_);
        final Tessellator var6 = Tessellator.instance;
        var6.setBrightness(p_147791_1_.getBlockBrightness(this.blockAccess, p_147791_2_, p_147791_3_, p_147791_4_));
        var6.setColorOpaque_F(1.0f, 1.0f, 1.0f);
        final double var7 = 0.4000000059604645;
        final double var8 = 0.5 - var7;
        final double var9 = 0.20000000298023224;
        if (var5 == 1) {
            this.renderTorchAtAngle(p_147791_1_, p_147791_2_ - var8, p_147791_3_ + var9, p_147791_4_, -var7, 0.0, 0);
        }
        else if (var5 == 2) {
            this.renderTorchAtAngle(p_147791_1_, p_147791_2_ + var8, p_147791_3_ + var9, p_147791_4_, var7, 0.0, 0);
        }
        else if (var5 == 3) {
            this.renderTorchAtAngle(p_147791_1_, p_147791_2_, p_147791_3_ + var9, p_147791_4_ - var8, 0.0, -var7, 0);
        }
        else if (var5 == 4) {
            this.renderTorchAtAngle(p_147791_1_, p_147791_2_, p_147791_3_ + var9, p_147791_4_ + var8, 0.0, var7, 0);
        }
        else {
            this.renderTorchAtAngle(p_147791_1_, p_147791_2_, p_147791_3_, p_147791_4_, 0.0, 0.0, 0);
            if (p_147791_1_ != Blocks.torch && Config.isBetterSnow() && this.hasSnowNeighbours(p_147791_2_, p_147791_3_, p_147791_4_)) {
                this.renderSnow(p_147791_2_, p_147791_3_, p_147791_4_, Blocks.snow_layer.getBlockBoundsMaxY());
            }
        }
        return true;
    }
    
    public boolean renderBlockRepeater(final BlockRedstoneRepeater p_147759_1_, final int p_147759_2_, final int p_147759_3_, final int p_147759_4_) {
        final int var5 = this.blockAccess.getBlockMetadata(p_147759_2_, p_147759_3_, p_147759_4_);
        final int var6 = var5 & 0x3;
        final int var7 = (var5 & 0xC) >> 2;
        final Tessellator var8 = Tessellator.instance;
        var8.setBrightness(p_147759_1_.getBlockBrightness(this.blockAccess, p_147759_2_, p_147759_3_, p_147759_4_));
        var8.setColorOpaque_F(1.0f, 1.0f, 1.0f);
        final double var9 = -0.1875;
        final boolean var10 = p_147759_1_.func_149910_g(this.blockAccess, p_147759_2_, p_147759_3_, p_147759_4_, var5);
        double var11 = 0.0;
        double var12 = 0.0;
        double var13 = 0.0;
        double var14 = 0.0;
        switch (var6) {
            case 0: {
                var14 = -0.3125;
                var12 = BlockRedstoneRepeater.field_149973_b[var7];
                break;
            }
            case 1: {
                var13 = 0.3125;
                var11 = -BlockRedstoneRepeater.field_149973_b[var7];
                break;
            }
            case 2: {
                var14 = 0.3125;
                var12 = -BlockRedstoneRepeater.field_149973_b[var7];
                break;
            }
            case 3: {
                var13 = -0.3125;
                var11 = BlockRedstoneRepeater.field_149973_b[var7];
                break;
            }
        }
        if (!var10) {
            this.renderTorchAtAngle(p_147759_1_, p_147759_2_ + var11, p_147759_3_ + var9, p_147759_4_ + var12, 0.0, 0.0, 0);
        }
        else {
            final IIcon var15 = this.getBlockIcon(Blocks.bedrock);
            this.setOverrideBlockTexture(var15);
            float var16 = 2.0f;
            float var17 = 14.0f;
            float var18 = 7.0f;
            float var19 = 9.0f;
            switch (var6) {
                case 1:
                case 3: {
                    var16 = 7.0f;
                    var17 = 9.0f;
                    var18 = 2.0f;
                    var19 = 14.0f;
                    break;
                }
            }
            this.setRenderBounds(var16 / 16.0f + (float)var11, 0.125, var18 / 16.0f + (float)var12, var17 / 16.0f + (float)var11, 0.25, var19 / 16.0f + (float)var12);
            final double var20 = var15.getInterpolatedU(var16);
            final double var21 = var15.getInterpolatedV(var18);
            final double var22 = var15.getInterpolatedU(var17);
            final double var23 = var15.getInterpolatedV(var19);
            var8.addVertexWithUV(p_147759_2_ + var16 / 16.0f + var11, p_147759_3_ + 0.25f, p_147759_4_ + var18 / 16.0f + var12, var20, var21);
            var8.addVertexWithUV(p_147759_2_ + var16 / 16.0f + var11, p_147759_3_ + 0.25f, p_147759_4_ + var19 / 16.0f + var12, var20, var23);
            var8.addVertexWithUV(p_147759_2_ + var17 / 16.0f + var11, p_147759_3_ + 0.25f, p_147759_4_ + var19 / 16.0f + var12, var22, var23);
            var8.addVertexWithUV(p_147759_2_ + var17 / 16.0f + var11, p_147759_3_ + 0.25f, p_147759_4_ + var18 / 16.0f + var12, var22, var21);
            this.renderStandardBlock(p_147759_1_, p_147759_2_, p_147759_3_, p_147759_4_);
            this.setRenderBounds(0.0, 0.0, 0.0, 1.0, 0.125, 1.0);
            this.clearOverrideBlockTexture();
        }
        var8.setBrightness(p_147759_1_.getBlockBrightness(this.blockAccess, p_147759_2_, p_147759_3_, p_147759_4_));
        var8.setColorOpaque_F(1.0f, 1.0f, 1.0f);
        this.renderTorchAtAngle(p_147759_1_, p_147759_2_ + var13, p_147759_3_ + var9, p_147759_4_ + var14, 0.0, 0.0, 0);
        this.renderBlockRedstoneDiode(p_147759_1_, p_147759_2_, p_147759_3_, p_147759_4_);
        return true;
    }
    
    public boolean renderBlockRedstoneComparator(final BlockRedstoneComparator p_147781_1_, final int p_147781_2_, final int p_147781_3_, final int p_147781_4_) {
        final Tessellator var5 = Tessellator.instance;
        var5.setBrightness(p_147781_1_.getBlockBrightness(this.blockAccess, p_147781_2_, p_147781_3_, p_147781_4_));
        var5.setColorOpaque_F(1.0f, 1.0f, 1.0f);
        final int var6 = this.blockAccess.getBlockMetadata(p_147781_2_, p_147781_3_, p_147781_4_);
        final int var7 = var6 & 0x3;
        double var8 = 0.0;
        double var9 = -0.1875;
        double var10 = 0.0;
        double var11 = 0.0;
        double var12 = 0.0;
        IIcon var13;
        if (p_147781_1_.func_149969_d(var6)) {
            var13 = Blocks.redstone_torch.getBlockTextureFromSide(0);
        }
        else {
            var9 -= 0.1875;
            var13 = Blocks.unlit_redstone_torch.getBlockTextureFromSide(0);
        }
        switch (var7) {
            case 0: {
                var10 = -0.3125;
                var12 = 1.0;
                break;
            }
            case 1: {
                var8 = 0.3125;
                var11 = -1.0;
                break;
            }
            case 2: {
                var10 = 0.3125;
                var12 = -1.0;
                break;
            }
            case 3: {
                var8 = -0.3125;
                var11 = 1.0;
                break;
            }
        }
        this.renderTorchAtAngle(p_147781_1_, p_147781_2_ + 0.25 * var11 + 0.1875 * var12, p_147781_3_ - 0.1875f, p_147781_4_ + 0.25 * var12 + 0.1875 * var11, 0.0, 0.0, var6);
        this.renderTorchAtAngle(p_147781_1_, p_147781_2_ + 0.25 * var11 + -0.1875 * var12, p_147781_3_ - 0.1875f, p_147781_4_ + 0.25 * var12 + -0.1875 * var11, 0.0, 0.0, var6);
        this.setOverrideBlockTexture(var13);
        this.renderTorchAtAngle(p_147781_1_, p_147781_2_ + var8, p_147781_3_ + var9, p_147781_4_ + var10, 0.0, 0.0, var6);
        this.clearOverrideBlockTexture();
        this.renderBlockRedstoneDiodeMetadata(p_147781_1_, p_147781_2_, p_147781_3_, p_147781_4_, var7);
        return true;
    }
    
    public boolean renderBlockRedstoneDiode(final BlockRedstoneDiode p_147748_1_, final int p_147748_2_, final int p_147748_3_, final int p_147748_4_) {
        final Tessellator var5 = Tessellator.instance;
        this.renderBlockRedstoneDiodeMetadata(p_147748_1_, p_147748_2_, p_147748_3_, p_147748_4_, this.blockAccess.getBlockMetadata(p_147748_2_, p_147748_3_, p_147748_4_) & 0x3);
        return true;
    }
    
    public void renderBlockRedstoneDiodeMetadata(final BlockRedstoneDiode p_147732_1_, final int p_147732_2_, final int p_147732_3_, final int p_147732_4_, final int p_147732_5_) {
        this.renderStandardBlock(p_147732_1_, p_147732_2_, p_147732_3_, p_147732_4_);
        final Tessellator var6 = Tessellator.instance;
        var6.setBrightness(p_147732_1_.getBlockBrightness(this.blockAccess, p_147732_2_, p_147732_3_, p_147732_4_));
        var6.setColorOpaque_F(1.0f, 1.0f, 1.0f);
        final int var7 = this.blockAccess.getBlockMetadata(p_147732_2_, p_147732_3_, p_147732_4_);
        final IIcon var8 = this.getBlockIconFromSideAndMetadata(p_147732_1_, 1, var7);
        final double var9 = var8.getMinU();
        final double var10 = var8.getMaxU();
        final double var11 = var8.getMinV();
        final double var12 = var8.getMaxV();
        final double var13 = 0.125;
        double var14 = p_147732_2_ + 1;
        double var15 = p_147732_2_ + 1;
        double var16 = p_147732_2_ + 0;
        double var17 = p_147732_2_ + 0;
        double var18 = p_147732_4_ + 0;
        double var19 = p_147732_4_ + 1;
        double var20 = p_147732_4_ + 1;
        double var21 = p_147732_4_ + 0;
        final double var22 = p_147732_3_ + var13;
        if (p_147732_5_ == 2) {
            var15 = (var14 = p_147732_2_ + 0);
            var17 = (var16 = p_147732_2_ + 1);
            var21 = (var18 = p_147732_4_ + 1);
            var20 = (var19 = p_147732_4_ + 0);
        }
        else if (p_147732_5_ == 3) {
            var17 = (var14 = p_147732_2_ + 0);
            var16 = (var15 = p_147732_2_ + 1);
            var19 = (var18 = p_147732_4_ + 0);
            var21 = (var20 = p_147732_4_ + 1);
        }
        else if (p_147732_5_ == 1) {
            var17 = (var14 = p_147732_2_ + 1);
            var16 = (var15 = p_147732_2_ + 0);
            var19 = (var18 = p_147732_4_ + 1);
            var21 = (var20 = p_147732_4_ + 0);
        }
        var6.addVertexWithUV(var17, var22, var21, var9, var11);
        var6.addVertexWithUV(var16, var22, var20, var9, var12);
        var6.addVertexWithUV(var15, var22, var19, var10, var12);
        var6.addVertexWithUV(var14, var22, var18, var10, var11);
    }
    
    public void renderPistonBaseAllFaces(final Block p_147804_1_, final int p_147804_2_, final int p_147804_3_, final int p_147804_4_) {
        this.renderPistonBase(p_147804_1_, p_147804_2_, p_147804_3_, p_147804_4_, this.renderAllFaces = true);
        this.renderAllFaces = false;
    }
    
    public boolean renderPistonBase(final Block p_147731_1_, final int p_147731_2_, final int p_147731_3_, final int p_147731_4_, final boolean p_147731_5_) {
        final int var6 = this.blockAccess.getBlockMetadata(p_147731_2_, p_147731_3_, p_147731_4_);
        final boolean var7 = p_147731_5_ || (var6 & 0x8) != 0x0;
        final int var8 = BlockPistonBase.func_150076_b(var6);
        final float var9 = 0.25f;
        if (var7) {
            switch (var8) {
                case 0: {
                    this.uvRotateEast = 3;
                    this.uvRotateWest = 3;
                    this.uvRotateSouth = 3;
                    this.uvRotateNorth = 3;
                    this.setRenderBounds(0.0, 0.25, 0.0, 1.0, 1.0, 1.0);
                    break;
                }
                case 1: {
                    this.setRenderBounds(0.0, 0.0, 0.0, 1.0, 0.75, 1.0);
                    break;
                }
                case 2: {
                    this.uvRotateSouth = 1;
                    this.uvRotateNorth = 2;
                    this.setRenderBounds(0.0, 0.0, 0.25, 1.0, 1.0, 1.0);
                    break;
                }
                case 3: {
                    this.uvRotateSouth = 2;
                    this.uvRotateNorth = 1;
                    this.uvRotateTop = 3;
                    this.uvRotateBottom = 3;
                    this.setRenderBounds(0.0, 0.0, 0.0, 1.0, 1.0, 0.75);
                    break;
                }
                case 4: {
                    this.uvRotateEast = 1;
                    this.uvRotateWest = 2;
                    this.uvRotateTop = 2;
                    this.uvRotateBottom = 1;
                    this.setRenderBounds(0.25, 0.0, 0.0, 1.0, 1.0, 1.0);
                    break;
                }
                case 5: {
                    this.uvRotateEast = 2;
                    this.uvRotateWest = 1;
                    this.uvRotateTop = 1;
                    this.uvRotateBottom = 2;
                    this.setRenderBounds(0.0, 0.0, 0.0, 0.75, 1.0, 1.0);
                    break;
                }
            }
            ((BlockPistonBase)p_147731_1_).func_150070_b((float)this.renderMinX, (float)this.renderMinY, (float)this.renderMinZ, (float)this.renderMaxX, (float)this.renderMaxY, (float)this.renderMaxZ);
            this.renderStandardBlock(p_147731_1_, p_147731_2_, p_147731_3_, p_147731_4_);
            this.uvRotateEast = 0;
            this.uvRotateWest = 0;
            this.uvRotateSouth = 0;
            this.uvRotateNorth = 0;
            this.uvRotateTop = 0;
            this.uvRotateBottom = 0;
            this.setRenderBounds(0.0, 0.0, 0.0, 1.0, 1.0, 1.0);
            ((BlockPistonBase)p_147731_1_).func_150070_b((float)this.renderMinX, (float)this.renderMinY, (float)this.renderMinZ, (float)this.renderMaxX, (float)this.renderMaxY, (float)this.renderMaxZ);
        }
        else {
            switch (var8) {
                case 0: {
                    this.uvRotateEast = 3;
                    this.uvRotateWest = 3;
                    this.uvRotateSouth = 3;
                    this.uvRotateNorth = 3;
                    break;
                }
                case 2: {
                    this.uvRotateSouth = 1;
                    this.uvRotateNorth = 2;
                    break;
                }
                case 3: {
                    this.uvRotateSouth = 2;
                    this.uvRotateNorth = 1;
                    this.uvRotateTop = 3;
                    this.uvRotateBottom = 3;
                    break;
                }
                case 4: {
                    this.uvRotateEast = 1;
                    this.uvRotateWest = 2;
                    this.uvRotateTop = 2;
                    this.uvRotateBottom = 1;
                    break;
                }
                case 5: {
                    this.uvRotateEast = 2;
                    this.uvRotateWest = 1;
                    this.uvRotateTop = 1;
                    this.uvRotateBottom = 2;
                    break;
                }
            }
            this.renderStandardBlock(p_147731_1_, p_147731_2_, p_147731_3_, p_147731_4_);
            this.uvRotateEast = 0;
            this.uvRotateWest = 0;
            this.uvRotateSouth = 0;
            this.uvRotateNorth = 0;
            this.uvRotateTop = 0;
            this.uvRotateBottom = 0;
        }
        return true;
    }
    
    public void renderPistonRodUD(final double p_147763_1_, final double p_147763_3_, final double p_147763_5_, final double p_147763_7_, final double p_147763_9_, final double p_147763_11_, final float p_147763_13_, final double p_147763_14_) {
        IIcon var16 = BlockPistonBase.func_150074_e("piston_side");
        if (this.hasOverrideBlockTexture()) {
            var16 = this.overrideBlockTexture;
        }
        final Tessellator var17 = Tessellator.instance;
        final double var18 = var16.getMinU();
        final double var19 = var16.getMinV();
        final double var20 = var16.getInterpolatedU(p_147763_14_);
        final double var21 = var16.getInterpolatedV(4.0);
        var17.setColorOpaque_F(p_147763_13_, p_147763_13_, p_147763_13_);
        var17.addVertexWithUV(p_147763_1_, p_147763_7_, p_147763_9_, var20, var19);
        var17.addVertexWithUV(p_147763_1_, p_147763_5_, p_147763_9_, var18, var19);
        var17.addVertexWithUV(p_147763_3_, p_147763_5_, p_147763_11_, var18, var21);
        var17.addVertexWithUV(p_147763_3_, p_147763_7_, p_147763_11_, var20, var21);
    }
    
    public void renderPistonRodSN(final double p_147789_1_, final double p_147789_3_, final double p_147789_5_, final double p_147789_7_, final double p_147789_9_, final double p_147789_11_, final float p_147789_13_, final double p_147789_14_) {
        IIcon var16 = BlockPistonBase.func_150074_e("piston_side");
        if (this.hasOverrideBlockTexture()) {
            var16 = this.overrideBlockTexture;
        }
        final Tessellator var17 = Tessellator.instance;
        final double var18 = var16.getMinU();
        final double var19 = var16.getMinV();
        final double var20 = var16.getInterpolatedU(p_147789_14_);
        final double var21 = var16.getInterpolatedV(4.0);
        var17.setColorOpaque_F(p_147789_13_, p_147789_13_, p_147789_13_);
        var17.addVertexWithUV(p_147789_1_, p_147789_5_, p_147789_11_, var20, var19);
        var17.addVertexWithUV(p_147789_1_, p_147789_5_, p_147789_9_, var18, var19);
        var17.addVertexWithUV(p_147789_3_, p_147789_7_, p_147789_9_, var18, var21);
        var17.addVertexWithUV(p_147789_3_, p_147789_7_, p_147789_11_, var20, var21);
    }
    
    public void renderPistonRodEW(final double p_147738_1_, final double p_147738_3_, final double p_147738_5_, final double p_147738_7_, final double p_147738_9_, final double p_147738_11_, final float p_147738_13_, final double p_147738_14_) {
        IIcon var16 = BlockPistonBase.func_150074_e("piston_side");
        if (this.hasOverrideBlockTexture()) {
            var16 = this.overrideBlockTexture;
        }
        final Tessellator var17 = Tessellator.instance;
        final double var18 = var16.getMinU();
        final double var19 = var16.getMinV();
        final double var20 = var16.getInterpolatedU(p_147738_14_);
        final double var21 = var16.getInterpolatedV(4.0);
        var17.setColorOpaque_F(p_147738_13_, p_147738_13_, p_147738_13_);
        var17.addVertexWithUV(p_147738_3_, p_147738_5_, p_147738_9_, var20, var19);
        var17.addVertexWithUV(p_147738_1_, p_147738_5_, p_147738_9_, var18, var19);
        var17.addVertexWithUV(p_147738_1_, p_147738_7_, p_147738_11_, var18, var21);
        var17.addVertexWithUV(p_147738_3_, p_147738_7_, p_147738_11_, var20, var21);
    }
    
    public void renderPistonExtensionAllFaces(final Block p_147750_1_, final int p_147750_2_, final int p_147750_3_, final int p_147750_4_, final boolean p_147750_5_) {
        this.renderAllFaces = true;
        this.renderPistonExtension(p_147750_1_, p_147750_2_, p_147750_3_, p_147750_4_, p_147750_5_);
        this.renderAllFaces = false;
    }
    
    public boolean renderPistonExtension(final Block p_147809_1_, final int p_147809_2_, final int p_147809_3_, final int p_147809_4_, final boolean p_147809_5_) {
        final int var6 = this.blockAccess.getBlockMetadata(p_147809_2_, p_147809_3_, p_147809_4_);
        final int var7 = BlockPistonExtension.func_150085_b(var6);
        final float var8 = 0.25f;
        final float var9 = 0.375f;
        final float var10 = 0.625f;
        final float var11 = p_147809_5_ ? 1.0f : 0.5f;
        final double var12 = p_147809_5_ ? 16.0 : 8.0;
        switch (var7) {
            case 0: {
                this.uvRotateEast = 3;
                this.uvRotateWest = 3;
                this.uvRotateSouth = 3;
                this.uvRotateNorth = 3;
                this.setRenderBounds(0.0, 0.0, 0.0, 1.0, 0.25, 1.0);
                this.renderStandardBlock(p_147809_1_, p_147809_2_, p_147809_3_, p_147809_4_);
                this.renderPistonRodUD(p_147809_2_ + 0.375f, p_147809_2_ + 0.625f, p_147809_3_ + 0.25f, p_147809_3_ + 0.25f + var11, p_147809_4_ + 0.625f, p_147809_4_ + 0.625f, 0.8f, var12);
                this.renderPistonRodUD(p_147809_2_ + 0.625f, p_147809_2_ + 0.375f, p_147809_3_ + 0.25f, p_147809_3_ + 0.25f + var11, p_147809_4_ + 0.375f, p_147809_4_ + 0.375f, 0.8f, var12);
                this.renderPistonRodUD(p_147809_2_ + 0.375f, p_147809_2_ + 0.375f, p_147809_3_ + 0.25f, p_147809_3_ + 0.25f + var11, p_147809_4_ + 0.375f, p_147809_4_ + 0.625f, 0.6f, var12);
                this.renderPistonRodUD(p_147809_2_ + 0.625f, p_147809_2_ + 0.625f, p_147809_3_ + 0.25f, p_147809_3_ + 0.25f + var11, p_147809_4_ + 0.625f, p_147809_4_ + 0.375f, 0.6f, var12);
                break;
            }
            case 1: {
                this.setRenderBounds(0.0, 0.75, 0.0, 1.0, 1.0, 1.0);
                this.renderStandardBlock(p_147809_1_, p_147809_2_, p_147809_3_, p_147809_4_);
                this.renderPistonRodUD(p_147809_2_ + 0.375f, p_147809_2_ + 0.625f, p_147809_3_ - 0.25f + 1.0f - var11, p_147809_3_ - 0.25f + 1.0f, p_147809_4_ + 0.625f, p_147809_4_ + 0.625f, 0.8f, var12);
                this.renderPistonRodUD(p_147809_2_ + 0.625f, p_147809_2_ + 0.375f, p_147809_3_ - 0.25f + 1.0f - var11, p_147809_3_ - 0.25f + 1.0f, p_147809_4_ + 0.375f, p_147809_4_ + 0.375f, 0.8f, var12);
                this.renderPistonRodUD(p_147809_2_ + 0.375f, p_147809_2_ + 0.375f, p_147809_3_ - 0.25f + 1.0f - var11, p_147809_3_ - 0.25f + 1.0f, p_147809_4_ + 0.375f, p_147809_4_ + 0.625f, 0.6f, var12);
                this.renderPistonRodUD(p_147809_2_ + 0.625f, p_147809_2_ + 0.625f, p_147809_3_ - 0.25f + 1.0f - var11, p_147809_3_ - 0.25f + 1.0f, p_147809_4_ + 0.625f, p_147809_4_ + 0.375f, 0.6f, var12);
                break;
            }
            case 2: {
                this.uvRotateSouth = 1;
                this.uvRotateNorth = 2;
                this.setRenderBounds(0.0, 0.0, 0.0, 1.0, 1.0, 0.25);
                this.renderStandardBlock(p_147809_1_, p_147809_2_, p_147809_3_, p_147809_4_);
                this.renderPistonRodSN(p_147809_2_ + 0.375f, p_147809_2_ + 0.375f, p_147809_3_ + 0.625f, p_147809_3_ + 0.375f, p_147809_4_ + 0.25f, p_147809_4_ + 0.25f + var11, 0.6f, var12);
                this.renderPistonRodSN(p_147809_2_ + 0.625f, p_147809_2_ + 0.625f, p_147809_3_ + 0.375f, p_147809_3_ + 0.625f, p_147809_4_ + 0.25f, p_147809_4_ + 0.25f + var11, 0.6f, var12);
                this.renderPistonRodSN(p_147809_2_ + 0.375f, p_147809_2_ + 0.625f, p_147809_3_ + 0.375f, p_147809_3_ + 0.375f, p_147809_4_ + 0.25f, p_147809_4_ + 0.25f + var11, 0.5f, var12);
                this.renderPistonRodSN(p_147809_2_ + 0.625f, p_147809_2_ + 0.375f, p_147809_3_ + 0.625f, p_147809_3_ + 0.625f, p_147809_4_ + 0.25f, p_147809_4_ + 0.25f + var11, 1.0f, var12);
                break;
            }
            case 3: {
                this.uvRotateSouth = 2;
                this.uvRotateNorth = 1;
                this.uvRotateTop = 3;
                this.uvRotateBottom = 3;
                this.setRenderBounds(0.0, 0.0, 0.75, 1.0, 1.0, 1.0);
                this.renderStandardBlock(p_147809_1_, p_147809_2_, p_147809_3_, p_147809_4_);
                this.renderPistonRodSN(p_147809_2_ + 0.375f, p_147809_2_ + 0.375f, p_147809_3_ + 0.625f, p_147809_3_ + 0.375f, p_147809_4_ - 0.25f + 1.0f - var11, p_147809_4_ - 0.25f + 1.0f, 0.6f, var12);
                this.renderPistonRodSN(p_147809_2_ + 0.625f, p_147809_2_ + 0.625f, p_147809_3_ + 0.375f, p_147809_3_ + 0.625f, p_147809_4_ - 0.25f + 1.0f - var11, p_147809_4_ - 0.25f + 1.0f, 0.6f, var12);
                this.renderPistonRodSN(p_147809_2_ + 0.375f, p_147809_2_ + 0.625f, p_147809_3_ + 0.375f, p_147809_3_ + 0.375f, p_147809_4_ - 0.25f + 1.0f - var11, p_147809_4_ - 0.25f + 1.0f, 0.5f, var12);
                this.renderPistonRodSN(p_147809_2_ + 0.625f, p_147809_2_ + 0.375f, p_147809_3_ + 0.625f, p_147809_3_ + 0.625f, p_147809_4_ - 0.25f + 1.0f - var11, p_147809_4_ - 0.25f + 1.0f, 1.0f, var12);
                break;
            }
            case 4: {
                this.uvRotateEast = 1;
                this.uvRotateWest = 2;
                this.uvRotateTop = 2;
                this.uvRotateBottom = 1;
                this.setRenderBounds(0.0, 0.0, 0.0, 0.25, 1.0, 1.0);
                this.renderStandardBlock(p_147809_1_, p_147809_2_, p_147809_3_, p_147809_4_);
                this.renderPistonRodEW(p_147809_2_ + 0.25f, p_147809_2_ + 0.25f + var11, p_147809_3_ + 0.375f, p_147809_3_ + 0.375f, p_147809_4_ + 0.625f, p_147809_4_ + 0.375f, 0.5f, var12);
                this.renderPistonRodEW(p_147809_2_ + 0.25f, p_147809_2_ + 0.25f + var11, p_147809_3_ + 0.625f, p_147809_3_ + 0.625f, p_147809_4_ + 0.375f, p_147809_4_ + 0.625f, 1.0f, var12);
                this.renderPistonRodEW(p_147809_2_ + 0.25f, p_147809_2_ + 0.25f + var11, p_147809_3_ + 0.375f, p_147809_3_ + 0.625f, p_147809_4_ + 0.375f, p_147809_4_ + 0.375f, 0.6f, var12);
                this.renderPistonRodEW(p_147809_2_ + 0.25f, p_147809_2_ + 0.25f + var11, p_147809_3_ + 0.625f, p_147809_3_ + 0.375f, p_147809_4_ + 0.625f, p_147809_4_ + 0.625f, 0.6f, var12);
                break;
            }
            case 5: {
                this.uvRotateEast = 2;
                this.uvRotateWest = 1;
                this.uvRotateTop = 1;
                this.uvRotateBottom = 2;
                this.setRenderBounds(0.75, 0.0, 0.0, 1.0, 1.0, 1.0);
                this.renderStandardBlock(p_147809_1_, p_147809_2_, p_147809_3_, p_147809_4_);
                this.renderPistonRodEW(p_147809_2_ - 0.25f + 1.0f - var11, p_147809_2_ - 0.25f + 1.0f, p_147809_3_ + 0.375f, p_147809_3_ + 0.375f, p_147809_4_ + 0.625f, p_147809_4_ + 0.375f, 0.5f, var12);
                this.renderPistonRodEW(p_147809_2_ - 0.25f + 1.0f - var11, p_147809_2_ - 0.25f + 1.0f, p_147809_3_ + 0.625f, p_147809_3_ + 0.625f, p_147809_4_ + 0.375f, p_147809_4_ + 0.625f, 1.0f, var12);
                this.renderPistonRodEW(p_147809_2_ - 0.25f + 1.0f - var11, p_147809_2_ - 0.25f + 1.0f, p_147809_3_ + 0.375f, p_147809_3_ + 0.625f, p_147809_4_ + 0.375f, p_147809_4_ + 0.375f, 0.6f, var12);
                this.renderPistonRodEW(p_147809_2_ - 0.25f + 1.0f - var11, p_147809_2_ - 0.25f + 1.0f, p_147809_3_ + 0.625f, p_147809_3_ + 0.375f, p_147809_4_ + 0.625f, p_147809_4_ + 0.625f, 0.6f, var12);
                break;
            }
        }
        this.uvRotateEast = 0;
        this.uvRotateWest = 0;
        this.uvRotateSouth = 0;
        this.uvRotateNorth = 0;
        this.uvRotateTop = 0;
        this.uvRotateBottom = 0;
        this.setRenderBounds(0.0, 0.0, 0.0, 1.0, 1.0, 1.0);
        return true;
    }
    
    public boolean renderBlockLever(final Block p_147790_1_, final int p_147790_2_, final int p_147790_3_, final int p_147790_4_) {
        final int var5 = this.blockAccess.getBlockMetadata(p_147790_2_, p_147790_3_, p_147790_4_);
        final int var6 = var5 & 0x7;
        final boolean var7 = (var5 & 0x8) > 0;
        final Tessellator var8 = Tessellator.instance;
        final boolean var9 = this.hasOverrideBlockTexture();
        if (!var9) {
            this.setOverrideBlockTexture(this.getBlockIcon(Blocks.cobblestone));
        }
        final float var10 = 0.25f;
        final float var11 = 0.1875f;
        final float var12 = 0.1875f;
        if (var6 == 5) {
            this.setRenderBounds(0.5f - var11, 0.0, 0.5f - var10, 0.5f + var11, var12, 0.5f + var10);
        }
        else if (var6 == 6) {
            this.setRenderBounds(0.5f - var10, 0.0, 0.5f - var11, 0.5f + var10, var12, 0.5f + var11);
        }
        else if (var6 == 4) {
            this.setRenderBounds(0.5f - var11, 0.5f - var10, 1.0f - var12, 0.5f + var11, 0.5f + var10, 1.0);
        }
        else if (var6 == 3) {
            this.setRenderBounds(0.5f - var11, 0.5f - var10, 0.0, 0.5f + var11, 0.5f + var10, var12);
        }
        else if (var6 == 2) {
            this.setRenderBounds(1.0f - var12, 0.5f - var10, 0.5f - var11, 1.0, 0.5f + var10, 0.5f + var11);
        }
        else if (var6 == 1) {
            this.setRenderBounds(0.0, 0.5f - var10, 0.5f - var11, var12, 0.5f + var10, 0.5f + var11);
        }
        else if (var6 == 0) {
            this.setRenderBounds(0.5f - var10, 1.0f - var12, 0.5f - var11, 0.5f + var10, 1.0, 0.5f + var11);
        }
        else if (var6 == 7) {
            this.setRenderBounds(0.5f - var11, 1.0f - var12, 0.5f - var10, 0.5f + var11, 1.0, 0.5f + var10);
        }
        this.renderStandardBlock(p_147790_1_, p_147790_2_, p_147790_3_, p_147790_4_);
        if (!var9) {
            this.clearOverrideBlockTexture();
        }
        var8.setBrightness(p_147790_1_.getBlockBrightness(this.blockAccess, p_147790_2_, p_147790_3_, p_147790_4_));
        var8.setColorOpaque_F(1.0f, 1.0f, 1.0f);
        IIcon var13 = this.getBlockIconFromSide(p_147790_1_, 0);
        if (this.hasOverrideBlockTexture()) {
            var13 = this.overrideBlockTexture;
        }
        double var14 = var13.getMinU();
        double var15 = var13.getMinV();
        double var16 = var13.getMaxU();
        double var17 = var13.getMaxV();
        final Vec3[] var18 = new Vec3[8];
        final float var19 = 0.0625f;
        final float var20 = 0.0625f;
        final float var21 = 0.625f;
        var18[0] = Vec3.createVectorHelper(-var19, 0.0, -var20);
        var18[1] = Vec3.createVectorHelper(var19, 0.0, -var20);
        var18[2] = Vec3.createVectorHelper(var19, 0.0, var20);
        var18[3] = Vec3.createVectorHelper(-var19, 0.0, var20);
        var18[4] = Vec3.createVectorHelper(-var19, var21, -var20);
        var18[5] = Vec3.createVectorHelper(var19, var21, -var20);
        var18[6] = Vec3.createVectorHelper(var19, var21, var20);
        var18[7] = Vec3.createVectorHelper(-var19, var21, var20);
        for (int var22 = 0; var22 < 8; ++var22) {
            if (var7) {
                final Vec3 vec3 = var18[var22];
                vec3.zCoord -= 0.0625;
                var18[var22].rotateAroundX(0.69813174f);
            }
            else {
                final Vec3 vec4 = var18[var22];
                vec4.zCoord += 0.0625;
                var18[var22].rotateAroundX(-0.69813174f);
            }
            if (var6 == 0 || var6 == 7) {
                var18[var22].rotateAroundZ(3.1415927f);
            }
            if (var6 == 6 || var6 == 0) {
                var18[var22].rotateAroundY(1.5707964f);
            }
            if (var6 > 0 && var6 < 5) {
                final Vec3 vec5 = var18[var22];
                vec5.yCoord -= 0.375;
                var18[var22].rotateAroundX(1.5707964f);
                if (var6 == 4) {
                    var18[var22].rotateAroundY(0.0f);
                }
                if (var6 == 3) {
                    var18[var22].rotateAroundY(3.1415927f);
                }
                if (var6 == 2) {
                    var18[var22].rotateAroundY(1.5707964f);
                }
                if (var6 == 1) {
                    var18[var22].rotateAroundY(-1.5707964f);
                }
                final Vec3 vec6 = var18[var22];
                vec6.xCoord += p_147790_2_ + 0.5;
                final Vec3 vec7 = var18[var22];
                vec7.yCoord += p_147790_3_ + 0.5f;
                final Vec3 vec8 = var18[var22];
                vec8.zCoord += p_147790_4_ + 0.5;
            }
            else if (var6 != 0 && var6 != 7) {
                final Vec3 vec9 = var18[var22];
                vec9.xCoord += p_147790_2_ + 0.5;
                final Vec3 vec10 = var18[var22];
                vec10.yCoord += p_147790_3_ + 0.125f;
                final Vec3 vec11 = var18[var22];
                vec11.zCoord += p_147790_4_ + 0.5;
            }
            else {
                final Vec3 vec12 = var18[var22];
                vec12.xCoord += p_147790_2_ + 0.5;
                final Vec3 vec13 = var18[var22];
                vec13.yCoord += p_147790_3_ + 0.875f;
                final Vec3 vec14 = var18[var22];
                vec14.zCoord += p_147790_4_ + 0.5;
            }
        }
        Vec3 var23 = null;
        Vec3 var24 = null;
        Vec3 var25 = null;
        Vec3 var26 = null;
        for (int var27 = 0; var27 < 6; ++var27) {
            if (var27 == 0) {
                var14 = var13.getInterpolatedU(7.0);
                var15 = var13.getInterpolatedV(6.0);
                var16 = var13.getInterpolatedU(9.0);
                var17 = var13.getInterpolatedV(8.0);
            }
            else if (var27 == 2) {
                var14 = var13.getInterpolatedU(7.0);
                var15 = var13.getInterpolatedV(6.0);
                var16 = var13.getInterpolatedU(9.0);
                var17 = var13.getMaxV();
            }
            if (var27 == 0) {
                var23 = var18[0];
                var24 = var18[1];
                var25 = var18[2];
                var26 = var18[3];
            }
            else if (var27 == 1) {
                var23 = var18[7];
                var24 = var18[6];
                var25 = var18[5];
                var26 = var18[4];
            }
            else if (var27 == 2) {
                var23 = var18[1];
                var24 = var18[0];
                var25 = var18[4];
                var26 = var18[5];
            }
            else if (var27 == 3) {
                var23 = var18[2];
                var24 = var18[1];
                var25 = var18[5];
                var26 = var18[6];
            }
            else if (var27 == 4) {
                var23 = var18[3];
                var24 = var18[2];
                var25 = var18[6];
                var26 = var18[7];
            }
            else if (var27 == 5) {
                var23 = var18[0];
                var24 = var18[3];
                var25 = var18[7];
                var26 = var18[4];
            }
            var8.addVertexWithUV(var23.xCoord, var23.yCoord, var23.zCoord, var14, var17);
            var8.addVertexWithUV(var24.xCoord, var24.yCoord, var24.zCoord, var16, var17);
            var8.addVertexWithUV(var25.xCoord, var25.yCoord, var25.zCoord, var16, var15);
            var8.addVertexWithUV(var26.xCoord, var26.yCoord, var26.zCoord, var14, var15);
        }
        if (Config.isBetterSnow() && this.hasSnowNeighbours(p_147790_2_, p_147790_3_, p_147790_4_)) {
            this.renderSnow(p_147790_2_, p_147790_3_, p_147790_4_, Blocks.snow_layer.getBlockBoundsMaxY());
        }
        return true;
    }
    
    public boolean renderBlockTripWireSource(final Block p_147723_1_, final int p_147723_2_, final int p_147723_3_, final int p_147723_4_) {
        final Tessellator var5 = Tessellator.instance;
        final int var6 = this.blockAccess.getBlockMetadata(p_147723_2_, p_147723_3_, p_147723_4_);
        final int var7 = var6 & 0x3;
        final boolean var8 = (var6 & 0x4) == 0x4;
        final boolean var9 = (var6 & 0x8) == 0x8;
        final boolean var10 = !World.doesBlockHaveSolidTopSurface(this.blockAccess, p_147723_2_, p_147723_3_ - 1, p_147723_4_);
        final boolean var11 = this.hasOverrideBlockTexture();
        if (!var11) {
            this.setOverrideBlockTexture(this.getBlockIcon(Blocks.planks));
        }
        final float var12 = 0.25f;
        final float var13 = 0.125f;
        final float var14 = 0.125f;
        final float var15 = 0.3f - var12;
        final float var16 = 0.3f + var12;
        if (var7 == 2) {
            this.setRenderBounds(0.5f - var13, var15, 1.0f - var14, 0.5f + var13, var16, 1.0);
        }
        else if (var7 == 0) {
            this.setRenderBounds(0.5f - var13, var15, 0.0, 0.5f + var13, var16, var14);
        }
        else if (var7 == 1) {
            this.setRenderBounds(1.0f - var14, var15, 0.5f - var13, 1.0, var16, 0.5f + var13);
        }
        else if (var7 == 3) {
            this.setRenderBounds(0.0, var15, 0.5f - var13, var14, var16, 0.5f + var13);
        }
        this.renderStandardBlock(p_147723_1_, p_147723_2_, p_147723_3_, p_147723_4_);
        if (!var11) {
            this.clearOverrideBlockTexture();
        }
        var5.setBrightness(p_147723_1_.getBlockBrightness(this.blockAccess, p_147723_2_, p_147723_3_, p_147723_4_));
        var5.setColorOpaque_F(1.0f, 1.0f, 1.0f);
        IIcon var17 = this.getBlockIconFromSide(p_147723_1_, 0);
        if (this.hasOverrideBlockTexture()) {
            var17 = this.overrideBlockTexture;
        }
        double var18 = var17.getMinU();
        double var19 = var17.getMinV();
        double var20 = var17.getMaxU();
        double var21 = var17.getMaxV();
        final Vec3[] var22 = new Vec3[8];
        final float var23 = 0.046875f;
        final float var24 = 0.046875f;
        final float var25 = 0.3125f;
        var22[0] = Vec3.createVectorHelper(-var23, 0.0, -var24);
        var22[1] = Vec3.createVectorHelper(var23, 0.0, -var24);
        var22[2] = Vec3.createVectorHelper(var23, 0.0, var24);
        var22[3] = Vec3.createVectorHelper(-var23, 0.0, var24);
        var22[4] = Vec3.createVectorHelper(-var23, var25, -var24);
        var22[5] = Vec3.createVectorHelper(var23, var25, -var24);
        var22[6] = Vec3.createVectorHelper(var23, var25, var24);
        var22[7] = Vec3.createVectorHelper(-var23, var25, var24);
        for (int var26 = 0; var26 < 8; ++var26) {
            final Vec3 vec3 = var22[var26];
            vec3.zCoord += 0.0625;
            if (var9) {
                var22[var26].rotateAroundX(0.5235988f);
                final Vec3 vec4 = var22[var26];
                vec4.yCoord -= 0.4375;
            }
            else if (var8) {
                var22[var26].rotateAroundX(0.08726647f);
                final Vec3 vec5 = var22[var26];
                vec5.yCoord -= 0.4375;
            }
            else {
                var22[var26].rotateAroundX(-0.69813174f);
                final Vec3 vec6 = var22[var26];
                vec6.yCoord -= 0.375;
            }
            var22[var26].rotateAroundX(1.5707964f);
            if (var7 == 2) {
                var22[var26].rotateAroundY(0.0f);
            }
            if (var7 == 0) {
                var22[var26].rotateAroundY(3.1415927f);
            }
            if (var7 == 1) {
                var22[var26].rotateAroundY(1.5707964f);
            }
            if (var7 == 3) {
                var22[var26].rotateAroundY(-1.5707964f);
            }
            final Vec3 vec7 = var22[var26];
            vec7.xCoord += p_147723_2_ + 0.5;
            final Vec3 vec8 = var22[var26];
            vec8.yCoord += p_147723_3_ + 0.3125f;
            final Vec3 vec9 = var22[var26];
            vec9.zCoord += p_147723_4_ + 0.5;
        }
        Vec3 var27 = null;
        Vec3 var28 = null;
        Vec3 var29 = null;
        Vec3 var30 = null;
        final byte var31 = 7;
        final byte var32 = 9;
        final byte var33 = 9;
        final byte var34 = 16;
        for (int var35 = 0; var35 < 6; ++var35) {
            if (var35 == 0) {
                var27 = var22[0];
                var28 = var22[1];
                var29 = var22[2];
                var30 = var22[3];
                var18 = var17.getInterpolatedU(var31);
                var19 = var17.getInterpolatedV(var33);
                var20 = var17.getInterpolatedU(var32);
                var21 = var17.getInterpolatedV(var33 + 2);
            }
            else if (var35 == 1) {
                var27 = var22[7];
                var28 = var22[6];
                var29 = var22[5];
                var30 = var22[4];
            }
            else if (var35 == 2) {
                var27 = var22[1];
                var28 = var22[0];
                var29 = var22[4];
                var30 = var22[5];
                var18 = var17.getInterpolatedU(var31);
                var19 = var17.getInterpolatedV(var33);
                var20 = var17.getInterpolatedU(var32);
                var21 = var17.getInterpolatedV(var34);
            }
            else if (var35 == 3) {
                var27 = var22[2];
                var28 = var22[1];
                var29 = var22[5];
                var30 = var22[6];
            }
            else if (var35 == 4) {
                var27 = var22[3];
                var28 = var22[2];
                var29 = var22[6];
                var30 = var22[7];
            }
            else if (var35 == 5) {
                var27 = var22[0];
                var28 = var22[3];
                var29 = var22[7];
                var30 = var22[4];
            }
            var5.addVertexWithUV(var27.xCoord, var27.yCoord, var27.zCoord, var18, var21);
            var5.addVertexWithUV(var28.xCoord, var28.yCoord, var28.zCoord, var20, var21);
            var5.addVertexWithUV(var29.xCoord, var29.yCoord, var29.zCoord, var20, var19);
            var5.addVertexWithUV(var30.xCoord, var30.yCoord, var30.zCoord, var18, var19);
        }
        final float var36 = 0.09375f;
        final float var37 = 0.09375f;
        final float var38 = 0.03125f;
        var22[0] = Vec3.createVectorHelper(-var36, 0.0, -var37);
        var22[1] = Vec3.createVectorHelper(var36, 0.0, -var37);
        var22[2] = Vec3.createVectorHelper(var36, 0.0, var37);
        var22[3] = Vec3.createVectorHelper(-var36, 0.0, var37);
        var22[4] = Vec3.createVectorHelper(-var36, var38, -var37);
        var22[5] = Vec3.createVectorHelper(var36, var38, -var37);
        var22[6] = Vec3.createVectorHelper(var36, var38, var37);
        var22[7] = Vec3.createVectorHelper(-var36, var38, var37);
        for (int var39 = 0; var39 < 8; ++var39) {
            final Vec3 vec10 = var22[var39];
            vec10.zCoord += 0.21875;
            if (var9) {
                final Vec3 vec11 = var22[var39];
                vec11.yCoord -= 0.09375;
                final Vec3 vec12 = var22[var39];
                vec12.zCoord -= 0.1625;
                var22[var39].rotateAroundX(0.0f);
            }
            else if (var8) {
                final Vec3 vec13 = var22[var39];
                vec13.yCoord += 0.015625;
                final Vec3 vec14 = var22[var39];
                vec14.zCoord -= 0.171875;
                var22[var39].rotateAroundX(0.17453294f);
            }
            else {
                var22[var39].rotateAroundX(0.87266463f);
            }
            if (var7 == 2) {
                var22[var39].rotateAroundY(0.0f);
            }
            if (var7 == 0) {
                var22[var39].rotateAroundY(3.1415927f);
            }
            if (var7 == 1) {
                var22[var39].rotateAroundY(1.5707964f);
            }
            if (var7 == 3) {
                var22[var39].rotateAroundY(-1.5707964f);
            }
            final Vec3 vec15 = var22[var39];
            vec15.xCoord += p_147723_2_ + 0.5;
            final Vec3 vec16 = var22[var39];
            vec16.yCoord += p_147723_3_ + 0.3125f;
            final Vec3 vec17 = var22[var39];
            vec17.zCoord += p_147723_4_ + 0.5;
        }
        final byte var40 = 5;
        final byte var41 = 11;
        final byte var42 = 3;
        final byte var43 = 9;
        for (int var44 = 0; var44 < 6; ++var44) {
            if (var44 == 0) {
                var27 = var22[0];
                var28 = var22[1];
                var29 = var22[2];
                var30 = var22[3];
                var18 = var17.getInterpolatedU(var40);
                var19 = var17.getInterpolatedV(var42);
                var20 = var17.getInterpolatedU(var41);
                var21 = var17.getInterpolatedV(var43);
            }
            else if (var44 == 1) {
                var27 = var22[7];
                var28 = var22[6];
                var29 = var22[5];
                var30 = var22[4];
            }
            else if (var44 == 2) {
                var27 = var22[1];
                var28 = var22[0];
                var29 = var22[4];
                var30 = var22[5];
                var18 = var17.getInterpolatedU(var40);
                var19 = var17.getInterpolatedV(var42);
                var20 = var17.getInterpolatedU(var41);
                var21 = var17.getInterpolatedV(var42 + 2);
            }
            else if (var44 == 3) {
                var27 = var22[2];
                var28 = var22[1];
                var29 = var22[5];
                var30 = var22[6];
            }
            else if (var44 == 4) {
                var27 = var22[3];
                var28 = var22[2];
                var29 = var22[6];
                var30 = var22[7];
            }
            else if (var44 == 5) {
                var27 = var22[0];
                var28 = var22[3];
                var29 = var22[7];
                var30 = var22[4];
            }
            var5.addVertexWithUV(var27.xCoord, var27.yCoord, var27.zCoord, var18, var21);
            var5.addVertexWithUV(var28.xCoord, var28.yCoord, var28.zCoord, var20, var21);
            var5.addVertexWithUV(var29.xCoord, var29.yCoord, var29.zCoord, var20, var19);
            var5.addVertexWithUV(var30.xCoord, var30.yCoord, var30.zCoord, var18, var19);
        }
        if (var8) {
            final double var45 = var22[0].yCoord;
            final float var46 = 0.03125f;
            final float var47 = 0.5f - var46 / 2.0f;
            final float var48 = var47 + var46;
            final double var49 = var17.getMinU();
            final double var50 = var17.getInterpolatedV(var8 ? 2.0 : 0.0);
            final double var51 = var17.getMaxU();
            final double var52 = var17.getInterpolatedV(var8 ? 4.0 : 2.0);
            final double var53 = (var10 ? 3.5f : 1.5f) / 16.0;
            var5.setColorOpaque_F(0.75f, 0.75f, 0.75f);
            if (var7 == 2) {
                var5.addVertexWithUV(p_147723_2_ + var47, p_147723_3_ + var53, p_147723_4_ + 0.25, var49, var50);
                var5.addVertexWithUV(p_147723_2_ + var48, p_147723_3_ + var53, p_147723_4_ + 0.25, var49, var52);
                var5.addVertexWithUV(p_147723_2_ + var48, p_147723_3_ + var53, p_147723_4_, var51, var52);
                var5.addVertexWithUV(p_147723_2_ + var47, p_147723_3_ + var53, p_147723_4_, var51, var50);
                var5.addVertexWithUV(p_147723_2_ + var47, var45, p_147723_4_ + 0.5, var49, var50);
                var5.addVertexWithUV(p_147723_2_ + var48, var45, p_147723_4_ + 0.5, var49, var52);
                var5.addVertexWithUV(p_147723_2_ + var48, p_147723_3_ + var53, p_147723_4_ + 0.25, var51, var52);
                var5.addVertexWithUV(p_147723_2_ + var47, p_147723_3_ + var53, p_147723_4_ + 0.25, var51, var50);
            }
            else if (var7 == 0) {
                var5.addVertexWithUV(p_147723_2_ + var47, p_147723_3_ + var53, p_147723_4_ + 0.75, var49, var50);
                var5.addVertexWithUV(p_147723_2_ + var48, p_147723_3_ + var53, p_147723_4_ + 0.75, var49, var52);
                var5.addVertexWithUV(p_147723_2_ + var48, var45, p_147723_4_ + 0.5, var51, var52);
                var5.addVertexWithUV(p_147723_2_ + var47, var45, p_147723_4_ + 0.5, var51, var50);
                var5.addVertexWithUV(p_147723_2_ + var47, p_147723_3_ + var53, p_147723_4_ + 1, var49, var50);
                var5.addVertexWithUV(p_147723_2_ + var48, p_147723_3_ + var53, p_147723_4_ + 1, var49, var52);
                var5.addVertexWithUV(p_147723_2_ + var48, p_147723_3_ + var53, p_147723_4_ + 0.75, var51, var52);
                var5.addVertexWithUV(p_147723_2_ + var47, p_147723_3_ + var53, p_147723_4_ + 0.75, var51, var50);
            }
            else if (var7 == 1) {
                var5.addVertexWithUV(p_147723_2_, p_147723_3_ + var53, p_147723_4_ + var48, var49, var52);
                var5.addVertexWithUV(p_147723_2_ + 0.25, p_147723_3_ + var53, p_147723_4_ + var48, var51, var52);
                var5.addVertexWithUV(p_147723_2_ + 0.25, p_147723_3_ + var53, p_147723_4_ + var47, var51, var50);
                var5.addVertexWithUV(p_147723_2_, p_147723_3_ + var53, p_147723_4_ + var47, var49, var50);
                var5.addVertexWithUV(p_147723_2_ + 0.25, p_147723_3_ + var53, p_147723_4_ + var48, var49, var52);
                var5.addVertexWithUV(p_147723_2_ + 0.5, var45, p_147723_4_ + var48, var51, var52);
                var5.addVertexWithUV(p_147723_2_ + 0.5, var45, p_147723_4_ + var47, var51, var50);
                var5.addVertexWithUV(p_147723_2_ + 0.25, p_147723_3_ + var53, p_147723_4_ + var47, var49, var50);
            }
            else {
                var5.addVertexWithUV(p_147723_2_ + 0.5, var45, p_147723_4_ + var48, var49, var52);
                var5.addVertexWithUV(p_147723_2_ + 0.75, p_147723_3_ + var53, p_147723_4_ + var48, var51, var52);
                var5.addVertexWithUV(p_147723_2_ + 0.75, p_147723_3_ + var53, p_147723_4_ + var47, var51, var50);
                var5.addVertexWithUV(p_147723_2_ + 0.5, var45, p_147723_4_ + var47, var49, var50);
                var5.addVertexWithUV(p_147723_2_ + 0.75, p_147723_3_ + var53, p_147723_4_ + var48, var49, var52);
                var5.addVertexWithUV(p_147723_2_ + 1, p_147723_3_ + var53, p_147723_4_ + var48, var51, var52);
                var5.addVertexWithUV(p_147723_2_ + 1, p_147723_3_ + var53, p_147723_4_ + var47, var51, var50);
                var5.addVertexWithUV(p_147723_2_ + 0.75, p_147723_3_ + var53, p_147723_4_ + var47, var49, var50);
            }
        }
        return true;
    }
    
    public boolean renderBlockTripWire(final Block p_147756_1_, final int p_147756_2_, final int p_147756_3_, final int p_147756_4_) {
        final Tessellator var5 = Tessellator.instance;
        IIcon var6 = this.getBlockIconFromSide(p_147756_1_, 0);
        final int var7 = this.blockAccess.getBlockMetadata(p_147756_2_, p_147756_3_, p_147756_4_);
        final boolean var8 = (var7 & 0x4) == 0x4;
        final boolean var9 = (var7 & 0x2) == 0x2;
        if (this.hasOverrideBlockTexture()) {
            var6 = this.overrideBlockTexture;
        }
        var5.setBrightness(p_147756_1_.getBlockBrightness(this.blockAccess, p_147756_2_, p_147756_3_, p_147756_4_));
        var5.setColorOpaque_F(1.0f, 1.0f, 1.0f);
        final double var10 = var6.getMinU();
        final double var11 = var6.getInterpolatedV(var8 ? 2.0 : 0.0);
        final double var12 = var6.getMaxU();
        final double var13 = var6.getInterpolatedV(var8 ? 4.0 : 2.0);
        final double var14 = (var9 ? 3.5f : 1.5f) / 16.0;
        final boolean var15 = BlockTripWire.func_150139_a(this.blockAccess, p_147756_2_, p_147756_3_, p_147756_4_, var7, 1);
        final boolean var16 = BlockTripWire.func_150139_a(this.blockAccess, p_147756_2_, p_147756_3_, p_147756_4_, var7, 3);
        boolean var17 = BlockTripWire.func_150139_a(this.blockAccess, p_147756_2_, p_147756_3_, p_147756_4_, var7, 2);
        boolean var18 = BlockTripWire.func_150139_a(this.blockAccess, p_147756_2_, p_147756_3_, p_147756_4_, var7, 0);
        final float var19 = 0.03125f;
        final float var20 = 0.5f - var19 / 2.0f;
        final float var21 = var20 + var19;
        if (!var17 && !var16 && !var18 && !var15) {
            var17 = true;
            var18 = true;
        }
        if (var17) {
            var5.addVertexWithUV(p_147756_2_ + var20, p_147756_3_ + var14, p_147756_4_ + 0.25, var10, var11);
            var5.addVertexWithUV(p_147756_2_ + var21, p_147756_3_ + var14, p_147756_4_ + 0.25, var10, var13);
            var5.addVertexWithUV(p_147756_2_ + var21, p_147756_3_ + var14, p_147756_4_, var12, var13);
            var5.addVertexWithUV(p_147756_2_ + var20, p_147756_3_ + var14, p_147756_4_, var12, var11);
            var5.addVertexWithUV(p_147756_2_ + var20, p_147756_3_ + var14, p_147756_4_, var12, var11);
            var5.addVertexWithUV(p_147756_2_ + var21, p_147756_3_ + var14, p_147756_4_, var12, var13);
            var5.addVertexWithUV(p_147756_2_ + var21, p_147756_3_ + var14, p_147756_4_ + 0.25, var10, var13);
            var5.addVertexWithUV(p_147756_2_ + var20, p_147756_3_ + var14, p_147756_4_ + 0.25, var10, var11);
        }
        if (var17 || (var18 && !var16 && !var15)) {
            var5.addVertexWithUV(p_147756_2_ + var20, p_147756_3_ + var14, p_147756_4_ + 0.5, var10, var11);
            var5.addVertexWithUV(p_147756_2_ + var21, p_147756_3_ + var14, p_147756_4_ + 0.5, var10, var13);
            var5.addVertexWithUV(p_147756_2_ + var21, p_147756_3_ + var14, p_147756_4_ + 0.25, var12, var13);
            var5.addVertexWithUV(p_147756_2_ + var20, p_147756_3_ + var14, p_147756_4_ + 0.25, var12, var11);
            var5.addVertexWithUV(p_147756_2_ + var20, p_147756_3_ + var14, p_147756_4_ + 0.25, var12, var11);
            var5.addVertexWithUV(p_147756_2_ + var21, p_147756_3_ + var14, p_147756_4_ + 0.25, var12, var13);
            var5.addVertexWithUV(p_147756_2_ + var21, p_147756_3_ + var14, p_147756_4_ + 0.5, var10, var13);
            var5.addVertexWithUV(p_147756_2_ + var20, p_147756_3_ + var14, p_147756_4_ + 0.5, var10, var11);
        }
        if (var18 || (var17 && !var16 && !var15)) {
            var5.addVertexWithUV(p_147756_2_ + var20, p_147756_3_ + var14, p_147756_4_ + 0.75, var10, var11);
            var5.addVertexWithUV(p_147756_2_ + var21, p_147756_3_ + var14, p_147756_4_ + 0.75, var10, var13);
            var5.addVertexWithUV(p_147756_2_ + var21, p_147756_3_ + var14, p_147756_4_ + 0.5, var12, var13);
            var5.addVertexWithUV(p_147756_2_ + var20, p_147756_3_ + var14, p_147756_4_ + 0.5, var12, var11);
            var5.addVertexWithUV(p_147756_2_ + var20, p_147756_3_ + var14, p_147756_4_ + 0.5, var12, var11);
            var5.addVertexWithUV(p_147756_2_ + var21, p_147756_3_ + var14, p_147756_4_ + 0.5, var12, var13);
            var5.addVertexWithUV(p_147756_2_ + var21, p_147756_3_ + var14, p_147756_4_ + 0.75, var10, var13);
            var5.addVertexWithUV(p_147756_2_ + var20, p_147756_3_ + var14, p_147756_4_ + 0.75, var10, var11);
        }
        if (var18) {
            var5.addVertexWithUV(p_147756_2_ + var20, p_147756_3_ + var14, p_147756_4_ + 1, var10, var11);
            var5.addVertexWithUV(p_147756_2_ + var21, p_147756_3_ + var14, p_147756_4_ + 1, var10, var13);
            var5.addVertexWithUV(p_147756_2_ + var21, p_147756_3_ + var14, p_147756_4_ + 0.75, var12, var13);
            var5.addVertexWithUV(p_147756_2_ + var20, p_147756_3_ + var14, p_147756_4_ + 0.75, var12, var11);
            var5.addVertexWithUV(p_147756_2_ + var20, p_147756_3_ + var14, p_147756_4_ + 0.75, var12, var11);
            var5.addVertexWithUV(p_147756_2_ + var21, p_147756_3_ + var14, p_147756_4_ + 0.75, var12, var13);
            var5.addVertexWithUV(p_147756_2_ + var21, p_147756_3_ + var14, p_147756_4_ + 1, var10, var13);
            var5.addVertexWithUV(p_147756_2_ + var20, p_147756_3_ + var14, p_147756_4_ + 1, var10, var11);
        }
        if (var15) {
            var5.addVertexWithUV(p_147756_2_, p_147756_3_ + var14, p_147756_4_ + var21, var10, var13);
            var5.addVertexWithUV(p_147756_2_ + 0.25, p_147756_3_ + var14, p_147756_4_ + var21, var12, var13);
            var5.addVertexWithUV(p_147756_2_ + 0.25, p_147756_3_ + var14, p_147756_4_ + var20, var12, var11);
            var5.addVertexWithUV(p_147756_2_, p_147756_3_ + var14, p_147756_4_ + var20, var10, var11);
            var5.addVertexWithUV(p_147756_2_, p_147756_3_ + var14, p_147756_4_ + var20, var10, var11);
            var5.addVertexWithUV(p_147756_2_ + 0.25, p_147756_3_ + var14, p_147756_4_ + var20, var12, var11);
            var5.addVertexWithUV(p_147756_2_ + 0.25, p_147756_3_ + var14, p_147756_4_ + var21, var12, var13);
            var5.addVertexWithUV(p_147756_2_, p_147756_3_ + var14, p_147756_4_ + var21, var10, var13);
        }
        if (var15 || (var16 && !var17 && !var18)) {
            var5.addVertexWithUV(p_147756_2_ + 0.25, p_147756_3_ + var14, p_147756_4_ + var21, var10, var13);
            var5.addVertexWithUV(p_147756_2_ + 0.5, p_147756_3_ + var14, p_147756_4_ + var21, var12, var13);
            var5.addVertexWithUV(p_147756_2_ + 0.5, p_147756_3_ + var14, p_147756_4_ + var20, var12, var11);
            var5.addVertexWithUV(p_147756_2_ + 0.25, p_147756_3_ + var14, p_147756_4_ + var20, var10, var11);
            var5.addVertexWithUV(p_147756_2_ + 0.25, p_147756_3_ + var14, p_147756_4_ + var20, var10, var11);
            var5.addVertexWithUV(p_147756_2_ + 0.5, p_147756_3_ + var14, p_147756_4_ + var20, var12, var11);
            var5.addVertexWithUV(p_147756_2_ + 0.5, p_147756_3_ + var14, p_147756_4_ + var21, var12, var13);
            var5.addVertexWithUV(p_147756_2_ + 0.25, p_147756_3_ + var14, p_147756_4_ + var21, var10, var13);
        }
        if (var16 || (var15 && !var17 && !var18)) {
            var5.addVertexWithUV(p_147756_2_ + 0.5, p_147756_3_ + var14, p_147756_4_ + var21, var10, var13);
            var5.addVertexWithUV(p_147756_2_ + 0.75, p_147756_3_ + var14, p_147756_4_ + var21, var12, var13);
            var5.addVertexWithUV(p_147756_2_ + 0.75, p_147756_3_ + var14, p_147756_4_ + var20, var12, var11);
            var5.addVertexWithUV(p_147756_2_ + 0.5, p_147756_3_ + var14, p_147756_4_ + var20, var10, var11);
            var5.addVertexWithUV(p_147756_2_ + 0.5, p_147756_3_ + var14, p_147756_4_ + var20, var10, var11);
            var5.addVertexWithUV(p_147756_2_ + 0.75, p_147756_3_ + var14, p_147756_4_ + var20, var12, var11);
            var5.addVertexWithUV(p_147756_2_ + 0.75, p_147756_3_ + var14, p_147756_4_ + var21, var12, var13);
            var5.addVertexWithUV(p_147756_2_ + 0.5, p_147756_3_ + var14, p_147756_4_ + var21, var10, var13);
        }
        if (var16) {
            var5.addVertexWithUV(p_147756_2_ + 0.75, p_147756_3_ + var14, p_147756_4_ + var21, var10, var13);
            var5.addVertexWithUV(p_147756_2_ + 1, p_147756_3_ + var14, p_147756_4_ + var21, var12, var13);
            var5.addVertexWithUV(p_147756_2_ + 1, p_147756_3_ + var14, p_147756_4_ + var20, var12, var11);
            var5.addVertexWithUV(p_147756_2_ + 0.75, p_147756_3_ + var14, p_147756_4_ + var20, var10, var11);
            var5.addVertexWithUV(p_147756_2_ + 0.75, p_147756_3_ + var14, p_147756_4_ + var20, var10, var11);
            var5.addVertexWithUV(p_147756_2_ + 1, p_147756_3_ + var14, p_147756_4_ + var20, var12, var11);
            var5.addVertexWithUV(p_147756_2_ + 1, p_147756_3_ + var14, p_147756_4_ + var21, var12, var13);
            var5.addVertexWithUV(p_147756_2_ + 0.75, p_147756_3_ + var14, p_147756_4_ + var21, var10, var13);
        }
        return true;
    }
    
    public boolean renderBlockFire(final BlockFire p_147801_1_, final int p_147801_2_, int p_147801_3_, final int p_147801_4_) {
        final Tessellator var5 = Tessellator.instance;
        final IIcon var6 = p_147801_1_.func_149840_c(0);
        final IIcon var7 = p_147801_1_.func_149840_c(1);
        IIcon var8 = var6;
        if (this.hasOverrideBlockTexture()) {
            var8 = this.overrideBlockTexture;
        }
        var5.setColorOpaque_F(1.0f, 1.0f, 1.0f);
        var5.setBrightness(p_147801_1_.getBlockBrightness(this.blockAccess, p_147801_2_, p_147801_3_, p_147801_4_));
        double var9 = var8.getMinU();
        double var10 = var8.getMinV();
        double var11 = var8.getMaxU();
        double var12 = var8.getMaxV();
        float var13 = 1.4f;
        if (!World.doesBlockHaveSolidTopSurface(this.blockAccess, p_147801_2_, p_147801_3_ - 1, p_147801_4_) && !Blocks.fire.func_149844_e(this.blockAccess, p_147801_2_, p_147801_3_ - 1, p_147801_4_)) {
            final float var14 = 0.2f;
            final float var15 = 0.0625f;
            if ((p_147801_2_ + p_147801_3_ + p_147801_4_ & 0x1) == 0x1) {
                var9 = var7.getMinU();
                var10 = var7.getMinV();
                var11 = var7.getMaxU();
                var12 = var7.getMaxV();
            }
            if ((p_147801_2_ / 2 + p_147801_3_ / 2 + p_147801_4_ / 2 & 0x1) == 0x1) {
                final double var16 = var11;
                var11 = var9;
                var9 = var16;
            }
            if (Blocks.fire.func_149844_e(this.blockAccess, p_147801_2_ - 1, p_147801_3_, p_147801_4_)) {
                var5.addVertexWithUV(p_147801_2_ + var14, p_147801_3_ + var13 + var15, p_147801_4_ + 1, var11, var10);
                var5.addVertexWithUV(p_147801_2_ + 0, p_147801_3_ + 0 + var15, p_147801_4_ + 1, var11, var12);
                var5.addVertexWithUV(p_147801_2_ + 0, p_147801_3_ + 0 + var15, p_147801_4_ + 0, var9, var12);
                var5.addVertexWithUV(p_147801_2_ + var14, p_147801_3_ + var13 + var15, p_147801_4_ + 0, var9, var10);
                var5.addVertexWithUV(p_147801_2_ + var14, p_147801_3_ + var13 + var15, p_147801_4_ + 0, var9, var10);
                var5.addVertexWithUV(p_147801_2_ + 0, p_147801_3_ + 0 + var15, p_147801_4_ + 0, var9, var12);
                var5.addVertexWithUV(p_147801_2_ + 0, p_147801_3_ + 0 + var15, p_147801_4_ + 1, var11, var12);
                var5.addVertexWithUV(p_147801_2_ + var14, p_147801_3_ + var13 + var15, p_147801_4_ + 1, var11, var10);
            }
            if (Blocks.fire.func_149844_e(this.blockAccess, p_147801_2_ + 1, p_147801_3_, p_147801_4_)) {
                var5.addVertexWithUV(p_147801_2_ + 1 - var14, p_147801_3_ + var13 + var15, p_147801_4_ + 0, var9, var10);
                var5.addVertexWithUV(p_147801_2_ + 1 - 0, p_147801_3_ + 0 + var15, p_147801_4_ + 0, var9, var12);
                var5.addVertexWithUV(p_147801_2_ + 1 - 0, p_147801_3_ + 0 + var15, p_147801_4_ + 1, var11, var12);
                var5.addVertexWithUV(p_147801_2_ + 1 - var14, p_147801_3_ + var13 + var15, p_147801_4_ + 1, var11, var10);
                var5.addVertexWithUV(p_147801_2_ + 1 - var14, p_147801_3_ + var13 + var15, p_147801_4_ + 1, var11, var10);
                var5.addVertexWithUV(p_147801_2_ + 1 - 0, p_147801_3_ + 0 + var15, p_147801_4_ + 1, var11, var12);
                var5.addVertexWithUV(p_147801_2_ + 1 - 0, p_147801_3_ + 0 + var15, p_147801_4_ + 0, var9, var12);
                var5.addVertexWithUV(p_147801_2_ + 1 - var14, p_147801_3_ + var13 + var15, p_147801_4_ + 0, var9, var10);
            }
            if (Blocks.fire.func_149844_e(this.blockAccess, p_147801_2_, p_147801_3_, p_147801_4_ - 1)) {
                var5.addVertexWithUV(p_147801_2_ + 0, p_147801_3_ + var13 + var15, p_147801_4_ + var14, var11, var10);
                var5.addVertexWithUV(p_147801_2_ + 0, p_147801_3_ + 0 + var15, p_147801_4_ + 0, var11, var12);
                var5.addVertexWithUV(p_147801_2_ + 1, p_147801_3_ + 0 + var15, p_147801_4_ + 0, var9, var12);
                var5.addVertexWithUV(p_147801_2_ + 1, p_147801_3_ + var13 + var15, p_147801_4_ + var14, var9, var10);
                var5.addVertexWithUV(p_147801_2_ + 1, p_147801_3_ + var13 + var15, p_147801_4_ + var14, var9, var10);
                var5.addVertexWithUV(p_147801_2_ + 1, p_147801_3_ + 0 + var15, p_147801_4_ + 0, var9, var12);
                var5.addVertexWithUV(p_147801_2_ + 0, p_147801_3_ + 0 + var15, p_147801_4_ + 0, var11, var12);
                var5.addVertexWithUV(p_147801_2_ + 0, p_147801_3_ + var13 + var15, p_147801_4_ + var14, var11, var10);
            }
            if (Blocks.fire.func_149844_e(this.blockAccess, p_147801_2_, p_147801_3_, p_147801_4_ + 1)) {
                var5.addVertexWithUV(p_147801_2_ + 1, p_147801_3_ + var13 + var15, p_147801_4_ + 1 - var14, var9, var10);
                var5.addVertexWithUV(p_147801_2_ + 1, p_147801_3_ + 0 + var15, p_147801_4_ + 1 - 0, var9, var12);
                var5.addVertexWithUV(p_147801_2_ + 0, p_147801_3_ + 0 + var15, p_147801_4_ + 1 - 0, var11, var12);
                var5.addVertexWithUV(p_147801_2_ + 0, p_147801_3_ + var13 + var15, p_147801_4_ + 1 - var14, var11, var10);
                var5.addVertexWithUV(p_147801_2_ + 0, p_147801_3_ + var13 + var15, p_147801_4_ + 1 - var14, var11, var10);
                var5.addVertexWithUV(p_147801_2_ + 0, p_147801_3_ + 0 + var15, p_147801_4_ + 1 - 0, var11, var12);
                var5.addVertexWithUV(p_147801_2_ + 1, p_147801_3_ + 0 + var15, p_147801_4_ + 1 - 0, var9, var12);
                var5.addVertexWithUV(p_147801_2_ + 1, p_147801_3_ + var13 + var15, p_147801_4_ + 1 - var14, var9, var10);
            }
            if (Blocks.fire.func_149844_e(this.blockAccess, p_147801_2_, p_147801_3_ + 1, p_147801_4_)) {
                final double var16 = p_147801_2_ + 0.5 + 0.5;
                final double var17 = p_147801_2_ + 0.5 - 0.5;
                final double var18 = p_147801_4_ + 0.5 + 0.5;
                final double var19 = p_147801_4_ + 0.5 - 0.5;
                final double var20 = p_147801_2_ + 0.5 - 0.5;
                final double var21 = p_147801_2_ + 0.5 + 0.5;
                final double var22 = p_147801_4_ + 0.5 - 0.5;
                final double var23 = p_147801_4_ + 0.5 + 0.5;
                var9 = var6.getMinU();
                var10 = var6.getMinV();
                var11 = var6.getMaxU();
                var12 = var6.getMaxV();
                ++p_147801_3_;
                var13 = -0.2f;
                if ((p_147801_2_ + p_147801_3_ + p_147801_4_ & 0x1) == 0x0) {
                    var5.addVertexWithUV(var20, p_147801_3_ + var13, p_147801_4_ + 0, var11, var10);
                    var5.addVertexWithUV(var16, p_147801_3_ + 0, p_147801_4_ + 0, var11, var12);
                    var5.addVertexWithUV(var16, p_147801_3_ + 0, p_147801_4_ + 1, var9, var12);
                    var5.addVertexWithUV(var20, p_147801_3_ + var13, p_147801_4_ + 1, var9, var10);
                    var9 = var7.getMinU();
                    var10 = var7.getMinV();
                    var11 = var7.getMaxU();
                    var12 = var7.getMaxV();
                    var5.addVertexWithUV(var21, p_147801_3_ + var13, p_147801_4_ + 1, var11, var10);
                    var5.addVertexWithUV(var17, p_147801_3_ + 0, p_147801_4_ + 1, var11, var12);
                    var5.addVertexWithUV(var17, p_147801_3_ + 0, p_147801_4_ + 0, var9, var12);
                    var5.addVertexWithUV(var21, p_147801_3_ + var13, p_147801_4_ + 0, var9, var10);
                }
                else {
                    var5.addVertexWithUV(p_147801_2_ + 0, p_147801_3_ + var13, var23, var11, var10);
                    var5.addVertexWithUV(p_147801_2_ + 0, p_147801_3_ + 0, var19, var11, var12);
                    var5.addVertexWithUV(p_147801_2_ + 1, p_147801_3_ + 0, var19, var9, var12);
                    var5.addVertexWithUV(p_147801_2_ + 1, p_147801_3_ + var13, var23, var9, var10);
                    var9 = var7.getMinU();
                    var10 = var7.getMinV();
                    var11 = var7.getMaxU();
                    var12 = var7.getMaxV();
                    var5.addVertexWithUV(p_147801_2_ + 1, p_147801_3_ + var13, var22, var11, var10);
                    var5.addVertexWithUV(p_147801_2_ + 1, p_147801_3_ + 0, var18, var11, var12);
                    var5.addVertexWithUV(p_147801_2_ + 0, p_147801_3_ + 0, var18, var9, var12);
                    var5.addVertexWithUV(p_147801_2_ + 0, p_147801_3_ + var13, var22, var9, var10);
                }
            }
        }
        else {
            double var24 = p_147801_2_ + 0.5 + 0.2;
            double var16 = p_147801_2_ + 0.5 - 0.2;
            double var17 = p_147801_4_ + 0.5 + 0.2;
            double var18 = p_147801_4_ + 0.5 - 0.2;
            double var19 = p_147801_2_ + 0.5 - 0.3;
            double var20 = p_147801_2_ + 0.5 + 0.3;
            double var21 = p_147801_4_ + 0.5 - 0.3;
            double var22 = p_147801_4_ + 0.5 + 0.3;
            var5.addVertexWithUV(var19, p_147801_3_ + var13, p_147801_4_ + 1, var11, var10);
            var5.addVertexWithUV(var24, p_147801_3_ + 0, p_147801_4_ + 1, var11, var12);
            var5.addVertexWithUV(var24, p_147801_3_ + 0, p_147801_4_ + 0, var9, var12);
            var5.addVertexWithUV(var19, p_147801_3_ + var13, p_147801_4_ + 0, var9, var10);
            var5.addVertexWithUV(var20, p_147801_3_ + var13, p_147801_4_ + 0, var11, var10);
            var5.addVertexWithUV(var16, p_147801_3_ + 0, p_147801_4_ + 0, var11, var12);
            var5.addVertexWithUV(var16, p_147801_3_ + 0, p_147801_4_ + 1, var9, var12);
            var5.addVertexWithUV(var20, p_147801_3_ + var13, p_147801_4_ + 1, var9, var10);
            var9 = var7.getMinU();
            var10 = var7.getMinV();
            var11 = var7.getMaxU();
            var12 = var7.getMaxV();
            var5.addVertexWithUV(p_147801_2_ + 1, p_147801_3_ + var13, var22, var11, var10);
            var5.addVertexWithUV(p_147801_2_ + 1, p_147801_3_ + 0, var18, var11, var12);
            var5.addVertexWithUV(p_147801_2_ + 0, p_147801_3_ + 0, var18, var9, var12);
            var5.addVertexWithUV(p_147801_2_ + 0, p_147801_3_ + var13, var22, var9, var10);
            var5.addVertexWithUV(p_147801_2_ + 0, p_147801_3_ + var13, var21, var11, var10);
            var5.addVertexWithUV(p_147801_2_ + 0, p_147801_3_ + 0, var17, var11, var12);
            var5.addVertexWithUV(p_147801_2_ + 1, p_147801_3_ + 0, var17, var9, var12);
            var5.addVertexWithUV(p_147801_2_ + 1, p_147801_3_ + var13, var21, var9, var10);
            var24 = p_147801_2_ + 0.5 - 0.5;
            var16 = p_147801_2_ + 0.5 + 0.5;
            var17 = p_147801_4_ + 0.5 - 0.5;
            var18 = p_147801_4_ + 0.5 + 0.5;
            var19 = p_147801_2_ + 0.5 - 0.4;
            var20 = p_147801_2_ + 0.5 + 0.4;
            var21 = p_147801_4_ + 0.5 - 0.4;
            var22 = p_147801_4_ + 0.5 + 0.4;
            var5.addVertexWithUV(var19, p_147801_3_ + var13, p_147801_4_ + 0, var9, var10);
            var5.addVertexWithUV(var24, p_147801_3_ + 0, p_147801_4_ + 0, var9, var12);
            var5.addVertexWithUV(var24, p_147801_3_ + 0, p_147801_4_ + 1, var11, var12);
            var5.addVertexWithUV(var19, p_147801_3_ + var13, p_147801_4_ + 1, var11, var10);
            var5.addVertexWithUV(var20, p_147801_3_ + var13, p_147801_4_ + 1, var9, var10);
            var5.addVertexWithUV(var16, p_147801_3_ + 0, p_147801_4_ + 1, var9, var12);
            var5.addVertexWithUV(var16, p_147801_3_ + 0, p_147801_4_ + 0, var11, var12);
            var5.addVertexWithUV(var20, p_147801_3_ + var13, p_147801_4_ + 0, var11, var10);
            var9 = var6.getMinU();
            var10 = var6.getMinV();
            var11 = var6.getMaxU();
            var12 = var6.getMaxV();
            var5.addVertexWithUV(p_147801_2_ + 0, p_147801_3_ + var13, var22, var9, var10);
            var5.addVertexWithUV(p_147801_2_ + 0, p_147801_3_ + 0, var18, var9, var12);
            var5.addVertexWithUV(p_147801_2_ + 1, p_147801_3_ + 0, var18, var11, var12);
            var5.addVertexWithUV(p_147801_2_ + 1, p_147801_3_ + var13, var22, var11, var10);
            var5.addVertexWithUV(p_147801_2_ + 1, p_147801_3_ + var13, var21, var9, var10);
            var5.addVertexWithUV(p_147801_2_ + 1, p_147801_3_ + 0, var17, var9, var12);
            var5.addVertexWithUV(p_147801_2_ + 0, p_147801_3_ + 0, var17, var11, var12);
            var5.addVertexWithUV(p_147801_2_ + 0, p_147801_3_ + var13, var21, var11, var10);
        }
        return true;
    }
    
    public boolean renderBlockRedstoneWire(final Block p_147788_1_, final int p_147788_2_, final int p_147788_3_, final int p_147788_4_) {
        final Tessellator var5 = Tessellator.instance;
        final int var6 = this.blockAccess.getBlockMetadata(p_147788_2_, p_147788_3_, p_147788_4_);
        final IIcon var7 = BlockRedstoneWire.func_150173_e("cross");
        final IIcon var8 = BlockRedstoneWire.func_150173_e("line");
        final IIcon var9 = BlockRedstoneWire.func_150173_e("cross_overlay");
        final IIcon var10 = BlockRedstoneWire.func_150173_e("line_overlay");
        var5.setBrightness(p_147788_1_.getBlockBrightness(this.blockAccess, p_147788_2_, p_147788_3_, p_147788_4_));
        final float var11 = var6 / 15.0f;
        float var12 = var11 * 0.6f + 0.4f;
        if (var6 == 0) {
            var12 = 0.3f;
        }
        float var13 = var11 * var11 * 0.7f - 0.5f;
        float var14 = var11 * var11 * 0.6f - 0.7f;
        if (var13 < 0.0f) {
            var13 = 0.0f;
        }
        if (var14 < 0.0f) {
            var14 = 0.0f;
        }
        var5.setColorOpaque_F(var12, var13, var14);
        final double var15 = 0.015625;
        final double var16 = 0.015625;
        boolean var17 = BlockRedstoneWire.func_150174_f(this.blockAccess, p_147788_2_ - 1, p_147788_3_, p_147788_4_, 1) || (!this.blockAccess.getBlock(p_147788_2_ - 1, p_147788_3_, p_147788_4_).isBlockNormalCube() && BlockRedstoneWire.func_150174_f(this.blockAccess, p_147788_2_ - 1, p_147788_3_ - 1, p_147788_4_, -1));
        boolean var18 = BlockRedstoneWire.func_150174_f(this.blockAccess, p_147788_2_ + 1, p_147788_3_, p_147788_4_, 3) || (!this.blockAccess.getBlock(p_147788_2_ + 1, p_147788_3_, p_147788_4_).isBlockNormalCube() && BlockRedstoneWire.func_150174_f(this.blockAccess, p_147788_2_ + 1, p_147788_3_ - 1, p_147788_4_, -1));
        boolean var19 = BlockRedstoneWire.func_150174_f(this.blockAccess, p_147788_2_, p_147788_3_, p_147788_4_ - 1, 2) || (!this.blockAccess.getBlock(p_147788_2_, p_147788_3_, p_147788_4_ - 1).isBlockNormalCube() && BlockRedstoneWire.func_150174_f(this.blockAccess, p_147788_2_, p_147788_3_ - 1, p_147788_4_ - 1, -1));
        boolean var20 = BlockRedstoneWire.func_150174_f(this.blockAccess, p_147788_2_, p_147788_3_, p_147788_4_ + 1, 0) || (!this.blockAccess.getBlock(p_147788_2_, p_147788_3_, p_147788_4_ + 1).isBlockNormalCube() && BlockRedstoneWire.func_150174_f(this.blockAccess, p_147788_2_, p_147788_3_ - 1, p_147788_4_ + 1, -1));
        if (!this.blockAccess.getBlock(p_147788_2_, p_147788_3_ + 1, p_147788_4_).isBlockNormalCube()) {
            if (this.blockAccess.getBlock(p_147788_2_ - 1, p_147788_3_, p_147788_4_).isBlockNormalCube() && BlockRedstoneWire.func_150174_f(this.blockAccess, p_147788_2_ - 1, p_147788_3_ + 1, p_147788_4_, -1)) {
                var17 = true;
            }
            if (this.blockAccess.getBlock(p_147788_2_ + 1, p_147788_3_, p_147788_4_).isBlockNormalCube() && BlockRedstoneWire.func_150174_f(this.blockAccess, p_147788_2_ + 1, p_147788_3_ + 1, p_147788_4_, -1)) {
                var18 = true;
            }
            if (this.blockAccess.getBlock(p_147788_2_, p_147788_3_, p_147788_4_ - 1).isBlockNormalCube() && BlockRedstoneWire.func_150174_f(this.blockAccess, p_147788_2_, p_147788_3_ + 1, p_147788_4_ - 1, -1)) {
                var19 = true;
            }
            if (this.blockAccess.getBlock(p_147788_2_, p_147788_3_, p_147788_4_ + 1).isBlockNormalCube() && BlockRedstoneWire.func_150174_f(this.blockAccess, p_147788_2_, p_147788_3_ + 1, p_147788_4_ + 1, -1)) {
                var20 = true;
            }
        }
        float var21 = (float)(p_147788_2_ + 0);
        float var22 = (float)(p_147788_2_ + 1);
        float var23 = (float)(p_147788_4_ + 0);
        float var24 = (float)(p_147788_4_ + 1);
        int var25 = 0;
        if ((var17 || var18) && !var19 && !var20) {
            var25 = 1;
        }
        if ((var19 || var20) && !var18 && !var17) {
            var25 = 2;
        }
        if (var25 == 0) {
            int var26 = 0;
            int var27 = 0;
            int var28 = 16;
            int var29 = 16;
            final boolean var30 = true;
            if (!var17) {
                var21 += 0.3125f;
            }
            if (!var17) {
                var26 += 5;
            }
            if (!var18) {
                var22 -= 0.3125f;
            }
            if (!var18) {
                var28 -= 5;
            }
            if (!var19) {
                var23 += 0.3125f;
            }
            if (!var19) {
                var27 += 5;
            }
            if (!var20) {
                var24 -= 0.3125f;
            }
            if (!var20) {
                var29 -= 5;
            }
            var5.addVertexWithUV(var22, p_147788_3_ + 0.015625, var24, var7.getInterpolatedU(var28), var7.getInterpolatedV(var29));
            var5.addVertexWithUV(var22, p_147788_3_ + 0.015625, var23, var7.getInterpolatedU(var28), var7.getInterpolatedV(var27));
            var5.addVertexWithUV(var21, p_147788_3_ + 0.015625, var23, var7.getInterpolatedU(var26), var7.getInterpolatedV(var27));
            var5.addVertexWithUV(var21, p_147788_3_ + 0.015625, var24, var7.getInterpolatedU(var26), var7.getInterpolatedV(var29));
            var5.setColorOpaque_F(1.0f, 1.0f, 1.0f);
            var5.addVertexWithUV(var22, p_147788_3_ + 0.015625, var24, var9.getInterpolatedU(var28), var9.getInterpolatedV(var29));
            var5.addVertexWithUV(var22, p_147788_3_ + 0.015625, var23, var9.getInterpolatedU(var28), var9.getInterpolatedV(var27));
            var5.addVertexWithUV(var21, p_147788_3_ + 0.015625, var23, var9.getInterpolatedU(var26), var9.getInterpolatedV(var27));
            var5.addVertexWithUV(var21, p_147788_3_ + 0.015625, var24, var9.getInterpolatedU(var26), var9.getInterpolatedV(var29));
        }
        else if (var25 == 1) {
            var5.addVertexWithUV(var22, p_147788_3_ + 0.015625, var24, var8.getMaxU(), var8.getMaxV());
            var5.addVertexWithUV(var22, p_147788_3_ + 0.015625, var23, var8.getMaxU(), var8.getMinV());
            var5.addVertexWithUV(var21, p_147788_3_ + 0.015625, var23, var8.getMinU(), var8.getMinV());
            var5.addVertexWithUV(var21, p_147788_3_ + 0.015625, var24, var8.getMinU(), var8.getMaxV());
            var5.setColorOpaque_F(1.0f, 1.0f, 1.0f);
            var5.addVertexWithUV(var22, p_147788_3_ + 0.015625, var24, var10.getMaxU(), var10.getMaxV());
            var5.addVertexWithUV(var22, p_147788_3_ + 0.015625, var23, var10.getMaxU(), var10.getMinV());
            var5.addVertexWithUV(var21, p_147788_3_ + 0.015625, var23, var10.getMinU(), var10.getMinV());
            var5.addVertexWithUV(var21, p_147788_3_ + 0.015625, var24, var10.getMinU(), var10.getMaxV());
        }
        else {
            var5.addVertexWithUV(var22, p_147788_3_ + 0.015625, var24, var8.getMaxU(), var8.getMaxV());
            var5.addVertexWithUV(var22, p_147788_3_ + 0.015625, var23, var8.getMinU(), var8.getMaxV());
            var5.addVertexWithUV(var21, p_147788_3_ + 0.015625, var23, var8.getMinU(), var8.getMinV());
            var5.addVertexWithUV(var21, p_147788_3_ + 0.015625, var24, var8.getMaxU(), var8.getMinV());
            var5.setColorOpaque_F(1.0f, 1.0f, 1.0f);
            var5.addVertexWithUV(var22, p_147788_3_ + 0.015625, var24, var10.getMaxU(), var10.getMaxV());
            var5.addVertexWithUV(var22, p_147788_3_ + 0.015625, var23, var10.getMinU(), var10.getMaxV());
            var5.addVertexWithUV(var21, p_147788_3_ + 0.015625, var23, var10.getMinU(), var10.getMinV());
            var5.addVertexWithUV(var21, p_147788_3_ + 0.015625, var24, var10.getMaxU(), var10.getMinV());
        }
        if (!this.blockAccess.getBlock(p_147788_2_, p_147788_3_ + 1, p_147788_4_).isBlockNormalCube()) {
            final float var31 = 0.021875f;
            if (this.blockAccess.getBlock(p_147788_2_ - 1, p_147788_3_, p_147788_4_).isBlockNormalCube() && this.blockAccess.getBlock(p_147788_2_ - 1, p_147788_3_ + 1, p_147788_4_) == Blocks.redstone_wire) {
                var5.setColorOpaque_F(var12, var13, var14);
                var5.addVertexWithUV(p_147788_2_ + 0.015625, p_147788_3_ + 1 + 0.021875f, p_147788_4_ + 1, var8.getMaxU(), var8.getMinV());
                var5.addVertexWithUV(p_147788_2_ + 0.015625, p_147788_3_ + 0, p_147788_4_ + 1, var8.getMinU(), var8.getMinV());
                var5.addVertexWithUV(p_147788_2_ + 0.015625, p_147788_3_ + 0, p_147788_4_ + 0, var8.getMinU(), var8.getMaxV());
                var5.addVertexWithUV(p_147788_2_ + 0.015625, p_147788_3_ + 1 + 0.021875f, p_147788_4_ + 0, var8.getMaxU(), var8.getMaxV());
                var5.setColorOpaque_F(1.0f, 1.0f, 1.0f);
                var5.addVertexWithUV(p_147788_2_ + 0.015625, p_147788_3_ + 1 + 0.021875f, p_147788_4_ + 1, var10.getMaxU(), var10.getMinV());
                var5.addVertexWithUV(p_147788_2_ + 0.015625, p_147788_3_ + 0, p_147788_4_ + 1, var10.getMinU(), var10.getMinV());
                var5.addVertexWithUV(p_147788_2_ + 0.015625, p_147788_3_ + 0, p_147788_4_ + 0, var10.getMinU(), var10.getMaxV());
                var5.addVertexWithUV(p_147788_2_ + 0.015625, p_147788_3_ + 1 + 0.021875f, p_147788_4_ + 0, var10.getMaxU(), var10.getMaxV());
            }
            if (this.blockAccess.getBlock(p_147788_2_ + 1, p_147788_3_, p_147788_4_).isBlockNormalCube() && this.blockAccess.getBlock(p_147788_2_ + 1, p_147788_3_ + 1, p_147788_4_) == Blocks.redstone_wire) {
                var5.setColorOpaque_F(var12, var13, var14);
                var5.addVertexWithUV(p_147788_2_ + 1 - 0.015625, p_147788_3_ + 0, p_147788_4_ + 1, var8.getMinU(), var8.getMaxV());
                var5.addVertexWithUV(p_147788_2_ + 1 - 0.015625, p_147788_3_ + 1 + 0.021875f, p_147788_4_ + 1, var8.getMaxU(), var8.getMaxV());
                var5.addVertexWithUV(p_147788_2_ + 1 - 0.015625, p_147788_3_ + 1 + 0.021875f, p_147788_4_ + 0, var8.getMaxU(), var8.getMinV());
                var5.addVertexWithUV(p_147788_2_ + 1 - 0.015625, p_147788_3_ + 0, p_147788_4_ + 0, var8.getMinU(), var8.getMinV());
                var5.setColorOpaque_F(1.0f, 1.0f, 1.0f);
                var5.addVertexWithUV(p_147788_2_ + 1 - 0.015625, p_147788_3_ + 0, p_147788_4_ + 1, var10.getMinU(), var10.getMaxV());
                var5.addVertexWithUV(p_147788_2_ + 1 - 0.015625, p_147788_3_ + 1 + 0.021875f, p_147788_4_ + 1, var10.getMaxU(), var10.getMaxV());
                var5.addVertexWithUV(p_147788_2_ + 1 - 0.015625, p_147788_3_ + 1 + 0.021875f, p_147788_4_ + 0, var10.getMaxU(), var10.getMinV());
                var5.addVertexWithUV(p_147788_2_ + 1 - 0.015625, p_147788_3_ + 0, p_147788_4_ + 0, var10.getMinU(), var10.getMinV());
            }
            if (this.blockAccess.getBlock(p_147788_2_, p_147788_3_, p_147788_4_ - 1).isBlockNormalCube() && this.blockAccess.getBlock(p_147788_2_, p_147788_3_ + 1, p_147788_4_ - 1) == Blocks.redstone_wire) {
                var5.setColorOpaque_F(var12, var13, var14);
                var5.addVertexWithUV(p_147788_2_ + 1, p_147788_3_ + 0, p_147788_4_ + 0.015625, var8.getMinU(), var8.getMaxV());
                var5.addVertexWithUV(p_147788_2_ + 1, p_147788_3_ + 1 + 0.021875f, p_147788_4_ + 0.015625, var8.getMaxU(), var8.getMaxV());
                var5.addVertexWithUV(p_147788_2_ + 0, p_147788_3_ + 1 + 0.021875f, p_147788_4_ + 0.015625, var8.getMaxU(), var8.getMinV());
                var5.addVertexWithUV(p_147788_2_ + 0, p_147788_3_ + 0, p_147788_4_ + 0.015625, var8.getMinU(), var8.getMinV());
                var5.setColorOpaque_F(1.0f, 1.0f, 1.0f);
                var5.addVertexWithUV(p_147788_2_ + 1, p_147788_3_ + 0, p_147788_4_ + 0.015625, var10.getMinU(), var10.getMaxV());
                var5.addVertexWithUV(p_147788_2_ + 1, p_147788_3_ + 1 + 0.021875f, p_147788_4_ + 0.015625, var10.getMaxU(), var10.getMaxV());
                var5.addVertexWithUV(p_147788_2_ + 0, p_147788_3_ + 1 + 0.021875f, p_147788_4_ + 0.015625, var10.getMaxU(), var10.getMinV());
                var5.addVertexWithUV(p_147788_2_ + 0, p_147788_3_ + 0, p_147788_4_ + 0.015625, var10.getMinU(), var10.getMinV());
            }
            if (this.blockAccess.getBlock(p_147788_2_, p_147788_3_, p_147788_4_ + 1).isBlockNormalCube() && this.blockAccess.getBlock(p_147788_2_, p_147788_3_ + 1, p_147788_4_ + 1) == Blocks.redstone_wire) {
                var5.setColorOpaque_F(var12, var13, var14);
                var5.addVertexWithUV(p_147788_2_ + 1, p_147788_3_ + 1 + 0.021875f, p_147788_4_ + 1 - 0.015625, var8.getMaxU(), var8.getMinV());
                var5.addVertexWithUV(p_147788_2_ + 1, p_147788_3_ + 0, p_147788_4_ + 1 - 0.015625, var8.getMinU(), var8.getMinV());
                var5.addVertexWithUV(p_147788_2_ + 0, p_147788_3_ + 0, p_147788_4_ + 1 - 0.015625, var8.getMinU(), var8.getMaxV());
                var5.addVertexWithUV(p_147788_2_ + 0, p_147788_3_ + 1 + 0.021875f, p_147788_4_ + 1 - 0.015625, var8.getMaxU(), var8.getMaxV());
                var5.setColorOpaque_F(1.0f, 1.0f, 1.0f);
                var5.addVertexWithUV(p_147788_2_ + 1, p_147788_3_ + 1 + 0.021875f, p_147788_4_ + 1 - 0.015625, var10.getMaxU(), var10.getMinV());
                var5.addVertexWithUV(p_147788_2_ + 1, p_147788_3_ + 0, p_147788_4_ + 1 - 0.015625, var10.getMinU(), var10.getMinV());
                var5.addVertexWithUV(p_147788_2_ + 0, p_147788_3_ + 0, p_147788_4_ + 1 - 0.015625, var10.getMinU(), var10.getMaxV());
                var5.addVertexWithUV(p_147788_2_ + 0, p_147788_3_ + 1 + 0.021875f, p_147788_4_ + 1 - 0.015625, var10.getMaxU(), var10.getMaxV());
            }
        }
        return true;
    }
    
    public boolean renderBlockMinecartTrack(final BlockRailBase p_147766_1_, final int p_147766_2_, final int p_147766_3_, final int p_147766_4_) {
        final Tessellator var5 = Tessellator.instance;
        int var6 = this.blockAccess.getBlockMetadata(p_147766_2_, p_147766_3_, p_147766_4_);
        IIcon var7 = this.getBlockIconFromSideAndMetadata(p_147766_1_, 0, var6);
        if (this.hasOverrideBlockTexture()) {
            var7 = this.overrideBlockTexture;
        }
        if (Config.isConnectedTextures() && this.overrideBlockTexture == null) {
            var7 = ConnectedTextures.getConnectedTexture(this.blockAccess, p_147766_1_, p_147766_2_, p_147766_3_, p_147766_4_, 1, var7);
        }
        if (p_147766_1_.func_150050_e()) {
            var6 &= 0x7;
        }
        var5.setBrightness(p_147766_1_.getBlockBrightness(this.blockAccess, p_147766_2_, p_147766_3_, p_147766_4_));
        var5.setColorOpaque_F(1.0f, 1.0f, 1.0f);
        final double var8 = var7.getMinU();
        final double var9 = var7.getMinV();
        final double var10 = var7.getMaxU();
        final double var11 = var7.getMaxV();
        final double var12 = 0.0625;
        double var13 = p_147766_2_ + 1;
        double var14 = p_147766_2_ + 1;
        double var15 = p_147766_2_ + 0;
        double var16 = p_147766_2_ + 0;
        double var17 = p_147766_4_ + 0;
        double var18 = p_147766_4_ + 1;
        double var19 = p_147766_4_ + 1;
        double var20 = p_147766_4_ + 0;
        double var21 = p_147766_3_ + var12;
        double var22 = p_147766_3_ + var12;
        double var23 = p_147766_3_ + var12;
        double var24 = p_147766_3_ + var12;
        if (var6 != 1 && var6 != 2 && var6 != 3 && var6 != 7) {
            if (var6 == 8) {
                var14 = (var13 = p_147766_2_ + 0);
                var16 = (var15 = p_147766_2_ + 1);
                var20 = (var17 = p_147766_4_ + 1);
                var19 = (var18 = p_147766_4_ + 0);
            }
            else if (var6 == 9) {
                var16 = (var13 = p_147766_2_ + 0);
                var15 = (var14 = p_147766_2_ + 1);
                var18 = (var17 = p_147766_4_ + 0);
                var20 = (var19 = p_147766_4_ + 1);
            }
        }
        else {
            var16 = (var13 = p_147766_2_ + 1);
            var15 = (var14 = p_147766_2_ + 0);
            var18 = (var17 = p_147766_4_ + 1);
            var20 = (var19 = p_147766_4_ + 0);
        }
        if (var6 != 2 && var6 != 4) {
            if (var6 == 3 || var6 == 5) {
                ++var22;
                ++var23;
            }
        }
        else {
            ++var21;
            ++var24;
        }
        var5.addVertexWithUV(var13, var21, var17, var10, var9);
        var5.addVertexWithUV(var14, var22, var18, var10, var11);
        var5.addVertexWithUV(var15, var23, var19, var8, var11);
        var5.addVertexWithUV(var16, var24, var20, var8, var9);
        var5.addVertexWithUV(var16, var24, var20, var8, var9);
        var5.addVertexWithUV(var15, var23, var19, var8, var11);
        var5.addVertexWithUV(var14, var22, var18, var10, var11);
        var5.addVertexWithUV(var13, var21, var17, var10, var9);
        if (Config.isBetterSnow() && this.hasSnowNeighbours(p_147766_2_, p_147766_3_, p_147766_4_)) {
            this.renderSnow(p_147766_2_, p_147766_3_, p_147766_4_, 0.05);
        }
        return true;
    }
    
    public boolean renderBlockLadder(final Block p_147794_1_, final int p_147794_2_, final int p_147794_3_, final int p_147794_4_) {
        final Tessellator var5 = Tessellator.instance;
        IIcon var6 = this.getBlockIconFromSide(p_147794_1_, 0);
        if (this.hasOverrideBlockTexture()) {
            var6 = this.overrideBlockTexture;
        }
        final int var7 = this.blockAccess.getBlockMetadata(p_147794_2_, p_147794_3_, p_147794_4_);
        if (Config.isConnectedTextures() && this.overrideBlockTexture == null) {
            var6 = ConnectedTextures.getConnectedTexture(this.blockAccess, p_147794_1_, p_147794_2_, p_147794_3_, p_147794_4_, var7, var6);
        }
        var5.setBrightness(p_147794_1_.getBlockBrightness(this.blockAccess, p_147794_2_, p_147794_3_, p_147794_4_));
        var5.setColorOpaque_F(1.0f, 1.0f, 1.0f);
        final double var8 = var6.getMinU();
        final double var9 = var6.getMinV();
        final double var10 = var6.getMaxU();
        final double var11 = var6.getMaxV();
        final double var12 = 0.0;
        final double var13 = 0.05000000074505806;
        if (var7 == 5) {
            var5.addVertexWithUV(p_147794_2_ + var13, p_147794_3_ + 1 + var12, p_147794_4_ + 1 + var12, var8, var9);
            var5.addVertexWithUV(p_147794_2_ + var13, p_147794_3_ + 0 - var12, p_147794_4_ + 1 + var12, var8, var11);
            var5.addVertexWithUV(p_147794_2_ + var13, p_147794_3_ + 0 - var12, p_147794_4_ + 0 - var12, var10, var11);
            var5.addVertexWithUV(p_147794_2_ + var13, p_147794_3_ + 1 + var12, p_147794_4_ + 0 - var12, var10, var9);
        }
        if (var7 == 4) {
            var5.addVertexWithUV(p_147794_2_ + 1 - var13, p_147794_3_ + 0 - var12, p_147794_4_ + 1 + var12, var10, var11);
            var5.addVertexWithUV(p_147794_2_ + 1 - var13, p_147794_3_ + 1 + var12, p_147794_4_ + 1 + var12, var10, var9);
            var5.addVertexWithUV(p_147794_2_ + 1 - var13, p_147794_3_ + 1 + var12, p_147794_4_ + 0 - var12, var8, var9);
            var5.addVertexWithUV(p_147794_2_ + 1 - var13, p_147794_3_ + 0 - var12, p_147794_4_ + 0 - var12, var8, var11);
        }
        if (var7 == 3) {
            var5.addVertexWithUV(p_147794_2_ + 1 + var12, p_147794_3_ + 0 - var12, p_147794_4_ + var13, var10, var11);
            var5.addVertexWithUV(p_147794_2_ + 1 + var12, p_147794_3_ + 1 + var12, p_147794_4_ + var13, var10, var9);
            var5.addVertexWithUV(p_147794_2_ + 0 - var12, p_147794_3_ + 1 + var12, p_147794_4_ + var13, var8, var9);
            var5.addVertexWithUV(p_147794_2_ + 0 - var12, p_147794_3_ + 0 - var12, p_147794_4_ + var13, var8, var11);
        }
        if (var7 == 2) {
            var5.addVertexWithUV(p_147794_2_ + 1 + var12, p_147794_3_ + 1 + var12, p_147794_4_ + 1 - var13, var8, var9);
            var5.addVertexWithUV(p_147794_2_ + 1 + var12, p_147794_3_ + 0 - var12, p_147794_4_ + 1 - var13, var8, var11);
            var5.addVertexWithUV(p_147794_2_ + 0 - var12, p_147794_3_ + 0 - var12, p_147794_4_ + 1 - var13, var10, var11);
            var5.addVertexWithUV(p_147794_2_ + 0 - var12, p_147794_3_ + 1 + var12, p_147794_4_ + 1 - var13, var10, var9);
        }
        return true;
    }
    
    public boolean renderBlockVine(final Block p_147726_1_, final int p_147726_2_, final int p_147726_3_, final int p_147726_4_) {
        final Tessellator var5 = Tessellator.instance;
        IIcon var6 = this.getBlockIconFromSide(p_147726_1_, 0);
        if (this.hasOverrideBlockTexture()) {
            var6 = this.overrideBlockTexture;
        }
        final int var7 = this.blockAccess.getBlockMetadata(p_147726_2_, p_147726_3_, p_147726_4_);
        if (Config.isConnectedTextures() && this.overrideBlockTexture == null) {
            byte var8 = 0;
            if ((var7 & 0x1) != 0x0) {
                var8 = 2;
            }
            else if ((var7 & 0x2) != 0x0) {
                var8 = 5;
            }
            else if ((var7 & 0x4) != 0x0) {
                var8 = 3;
            }
            else if ((var7 & 0x8) != 0x0) {
                var8 = 4;
            }
            var6 = ConnectedTextures.getConnectedTexture(this.blockAccess, p_147726_1_, p_147726_2_, p_147726_3_, p_147726_4_, var8, var6);
        }
        var5.setBrightness(p_147726_1_.getBlockBrightness(this.blockAccess, p_147726_2_, p_147726_3_, p_147726_4_));
        final int var9 = CustomColorizer.getColorMultiplier(p_147726_1_, this.blockAccess, p_147726_2_, p_147726_3_, p_147726_4_);
        final float var10 = (var9 >> 16 & 0xFF) / 255.0f;
        final float var11 = (var9 >> 8 & 0xFF) / 255.0f;
        final float var12 = (var9 & 0xFF) / 255.0f;
        var5.setColorOpaque_F(var10, var11, var12);
        final double var13 = var6.getMinU();
        final double var14 = var6.getMinV();
        final double var15 = var6.getMaxU();
        final double var16 = var6.getMaxV();
        final double var17 = 0.05000000074505806;
        if ((var7 & 0x2) != 0x0) {
            var5.addVertexWithUV(p_147726_2_ + var17, p_147726_3_ + 1, p_147726_4_ + 1, var13, var14);
            var5.addVertexWithUV(p_147726_2_ + var17, p_147726_3_ + 0, p_147726_4_ + 1, var13, var16);
            var5.addVertexWithUV(p_147726_2_ + var17, p_147726_3_ + 0, p_147726_4_ + 0, var15, var16);
            var5.addVertexWithUV(p_147726_2_ + var17, p_147726_3_ + 1, p_147726_4_ + 0, var15, var14);
            var5.addVertexWithUV(p_147726_2_ + var17, p_147726_3_ + 1, p_147726_4_ + 0, var15, var14);
            var5.addVertexWithUV(p_147726_2_ + var17, p_147726_3_ + 0, p_147726_4_ + 0, var15, var16);
            var5.addVertexWithUV(p_147726_2_ + var17, p_147726_3_ + 0, p_147726_4_ + 1, var13, var16);
            var5.addVertexWithUV(p_147726_2_ + var17, p_147726_3_ + 1, p_147726_4_ + 1, var13, var14);
        }
        if ((var7 & 0x8) != 0x0) {
            var5.addVertexWithUV(p_147726_2_ + 1 - var17, p_147726_3_ + 0, p_147726_4_ + 1, var15, var16);
            var5.addVertexWithUV(p_147726_2_ + 1 - var17, p_147726_3_ + 1, p_147726_4_ + 1, var15, var14);
            var5.addVertexWithUV(p_147726_2_ + 1 - var17, p_147726_3_ + 1, p_147726_4_ + 0, var13, var14);
            var5.addVertexWithUV(p_147726_2_ + 1 - var17, p_147726_3_ + 0, p_147726_4_ + 0, var13, var16);
            var5.addVertexWithUV(p_147726_2_ + 1 - var17, p_147726_3_ + 0, p_147726_4_ + 0, var13, var16);
            var5.addVertexWithUV(p_147726_2_ + 1 - var17, p_147726_3_ + 1, p_147726_4_ + 0, var13, var14);
            var5.addVertexWithUV(p_147726_2_ + 1 - var17, p_147726_3_ + 1, p_147726_4_ + 1, var15, var14);
            var5.addVertexWithUV(p_147726_2_ + 1 - var17, p_147726_3_ + 0, p_147726_4_ + 1, var15, var16);
        }
        if ((var7 & 0x4) != 0x0) {
            var5.addVertexWithUV(p_147726_2_ + 1, p_147726_3_ + 0, p_147726_4_ + var17, var15, var16);
            var5.addVertexWithUV(p_147726_2_ + 1, p_147726_3_ + 1, p_147726_4_ + var17, var15, var14);
            var5.addVertexWithUV(p_147726_2_ + 0, p_147726_3_ + 1, p_147726_4_ + var17, var13, var14);
            var5.addVertexWithUV(p_147726_2_ + 0, p_147726_3_ + 0, p_147726_4_ + var17, var13, var16);
            var5.addVertexWithUV(p_147726_2_ + 0, p_147726_3_ + 0, p_147726_4_ + var17, var13, var16);
            var5.addVertexWithUV(p_147726_2_ + 0, p_147726_3_ + 1, p_147726_4_ + var17, var13, var14);
            var5.addVertexWithUV(p_147726_2_ + 1, p_147726_3_ + 1, p_147726_4_ + var17, var15, var14);
            var5.addVertexWithUV(p_147726_2_ + 1, p_147726_3_ + 0, p_147726_4_ + var17, var15, var16);
        }
        if ((var7 & 0x1) != 0x0) {
            var5.addVertexWithUV(p_147726_2_ + 1, p_147726_3_ + 1, p_147726_4_ + 1 - var17, var13, var14);
            var5.addVertexWithUV(p_147726_2_ + 1, p_147726_3_ + 0, p_147726_4_ + 1 - var17, var13, var16);
            var5.addVertexWithUV(p_147726_2_ + 0, p_147726_3_ + 0, p_147726_4_ + 1 - var17, var15, var16);
            var5.addVertexWithUV(p_147726_2_ + 0, p_147726_3_ + 1, p_147726_4_ + 1 - var17, var15, var14);
            var5.addVertexWithUV(p_147726_2_ + 0, p_147726_3_ + 1, p_147726_4_ + 1 - var17, var15, var14);
            var5.addVertexWithUV(p_147726_2_ + 0, p_147726_3_ + 0, p_147726_4_ + 1 - var17, var15, var16);
            var5.addVertexWithUV(p_147726_2_ + 1, p_147726_3_ + 0, p_147726_4_ + 1 - var17, var13, var16);
            var5.addVertexWithUV(p_147726_2_ + 1, p_147726_3_ + 1, p_147726_4_ + 1 - var17, var13, var14);
        }
        if (this.blockAccess.getBlock(p_147726_2_, p_147726_3_ + 1, p_147726_4_).isBlockNormalCube()) {
            var5.addVertexWithUV(p_147726_2_ + 1, p_147726_3_ + 1 - var17, p_147726_4_ + 0, var13, var14);
            var5.addVertexWithUV(p_147726_2_ + 1, p_147726_3_ + 1 - var17, p_147726_4_ + 1, var13, var16);
            var5.addVertexWithUV(p_147726_2_ + 0, p_147726_3_ + 1 - var17, p_147726_4_ + 1, var15, var16);
            var5.addVertexWithUV(p_147726_2_ + 0, p_147726_3_ + 1 - var17, p_147726_4_ + 0, var15, var14);
        }
        return true;
    }
    
    public boolean renderBlockStainedGlassPane(final Block block, final int x, final int y, final int z) {
        final int var5 = this.blockAccess.getHeight();
        final Tessellator var6 = Tessellator.instance;
        var6.setBrightness(block.getBlockBrightness(this.blockAccess, x, y, z));
        final int var7 = block.colorMultiplier(this.blockAccess, x, y, z);
        float var8 = (var7 >> 16 & 0xFF) / 255.0f;
        float var9 = (var7 >> 8 & 0xFF) / 255.0f;
        float var10 = (var7 & 0xFF) / 255.0f;
        if (EntityRenderer.anaglyphEnable) {
            final float isStainedGlass = (var8 * 30.0f + var9 * 59.0f + var10 * 11.0f) / 100.0f;
            final float iconGlass = (var8 * 30.0f + var9 * 70.0f) / 100.0f;
            final float iconGlassPaneTop = (var8 * 30.0f + var10 * 70.0f) / 100.0f;
            var8 = isStainedGlass;
            var9 = iconGlass;
            var10 = iconGlassPaneTop;
        }
        var6.setColorOpaque_F(var8, var9, var10);
        final boolean isStainedGlass2 = block instanceof BlockStainedGlassPane;
        int metadata = 0;
        IIcon iconGlass2;
        IIcon iconGlassPaneTop2;
        if (this.hasOverrideBlockTexture()) {
            iconGlass2 = this.overrideBlockTexture;
            iconGlassPaneTop2 = this.overrideBlockTexture;
        }
        else {
            metadata = this.blockAccess.getBlockMetadata(x, y, z);
            iconGlass2 = this.getBlockIconFromSideAndMetadata(block, 0, metadata);
            iconGlassPaneTop2 = (isStainedGlass2 ? ((BlockStainedGlassPane)block).func_150104_b(metadata) : ((BlockPane)block).func_150097_e());
        }
        IIcon iconZ = iconGlass2;
        boolean drawTop = true;
        boolean drawBottom = true;
        if (Config.isConnectedTextures() && this.overrideBlockTexture == null) {
            final IIcon gMinU = ConnectedTextures.getConnectedTexture(this.blockAccess, block, x, y, z, 4, iconGlass2);
            final IIcon iz = ConnectedTextures.getConnectedTexture(this.blockAccess, block, x, y, z, 3, iconGlass2);
            if (gMinU != iconGlass2 || iz != iconGlass2) {
                final BlockPane gHalf7U = (BlockPane)block;
                drawTop = (this.blockAccess.getBlock(x, y + 1, z) != block || this.blockAccess.getBlockMetadata(x, y + 1, z) != metadata);
                drawBottom = (this.blockAccess.getBlock(x, y - 1, z) != block || this.blockAccess.getBlockMetadata(x, y - 1, z) != metadata);
            }
            iconGlass2 = gMinU;
            iconZ = iz;
        }
        final double gMinU2 = iconGlass2.getMinU();
        final double gHalf7U2 = iconGlass2.getInterpolatedU(7.0);
        final double gHalf9U = iconGlass2.getInterpolatedU(9.0);
        final double gMaxU = iconGlass2.getMaxU();
        final double gMinV = iconGlass2.getMinV();
        final double gMaxV = iconGlass2.getMaxV();
        final double gMinUz = iconZ.getMinU();
        final double gHalf7Uz = iconZ.getInterpolatedU(7.0);
        final double gHalf9Uz = iconZ.getInterpolatedU(9.0);
        final double gMaxUz = iconZ.getMaxU();
        final double gMinVz = iconZ.getMinV();
        final double gMaxVz = iconZ.getMaxV();
        final double var11 = iconGlassPaneTop2.getInterpolatedU(7.0);
        final double var12 = iconGlassPaneTop2.getInterpolatedU(9.0);
        final double var13 = iconGlassPaneTop2.getMinV();
        final double var14 = iconGlassPaneTop2.getMaxV();
        final double var15 = iconGlassPaneTop2.getInterpolatedV(7.0);
        final double var16 = iconGlassPaneTop2.getInterpolatedV(9.0);
        final double x2 = x;
        final double x3 = x + 1;
        final double z2 = z;
        final double z3 = z + 1;
        final double xHalfMin = x + 0.5 - 0.0625;
        final double xHalfMax = x + 0.5 + 0.0625;
        final double zHalfMin = z + 0.5 - 0.0625;
        final double zHalfMax = z + 0.5 + 0.0625;
        final boolean connZNeg = isStainedGlass2 ? ((BlockStainedGlassPane)block).func_150098_a(this.blockAccess.getBlock(x, y, z - 1)) : ((BlockPane)block).func_150098_a(this.blockAccess.getBlock(x, y, z - 1));
        final boolean connZPos = isStainedGlass2 ? ((BlockStainedGlassPane)block).func_150098_a(this.blockAccess.getBlock(x, y, z + 1)) : ((BlockPane)block).func_150098_a(this.blockAccess.getBlock(x, y, z + 1));
        final boolean connXNeg = isStainedGlass2 ? ((BlockStainedGlassPane)block).func_150098_a(this.blockAccess.getBlock(x - 1, y, z)) : ((BlockPane)block).func_150098_a(this.blockAccess.getBlock(x - 1, y, z));
        final boolean connXPos = isStainedGlass2 ? ((BlockStainedGlassPane)block).func_150098_a(this.blockAccess.getBlock(x + 1, y, z)) : ((BlockPane)block).func_150098_a(this.blockAccess.getBlock(x + 1, y, z));
        final double var17 = 0.001;
        final double var18 = 0.999;
        final double var19 = 0.001;
        final boolean disconnected = !connZNeg && !connZPos && !connXNeg && !connXPos;
        double yTop = y + 0.999;
        double yBottom = y + 0.001;
        if (!drawTop) {
            yTop = y + 1;
        }
        if (!drawBottom) {
            yBottom = y;
        }
        if (!connXNeg && !disconnected) {
            if (!connZNeg && !connZPos) {
                var6.addVertexWithUV(xHalfMin, yTop, zHalfMin, gHalf7U2, gMinV);
                var6.addVertexWithUV(xHalfMin, yBottom, zHalfMin, gHalf7U2, gMaxV);
                var6.addVertexWithUV(xHalfMin, yBottom, zHalfMax, gHalf9U, gMaxV);
                var6.addVertexWithUV(xHalfMin, yTop, zHalfMax, gHalf9U, gMinV);
            }
        }
        else if (connXNeg && connXPos) {
            if (!connZNeg) {
                var6.addVertexWithUV(x3, yTop, zHalfMin, gMaxUz, gMinVz);
                var6.addVertexWithUV(x3, yBottom, zHalfMin, gMaxUz, gMaxVz);
                var6.addVertexWithUV(x2, yBottom, zHalfMin, gMinUz, gMaxVz);
                var6.addVertexWithUV(x2, yTop, zHalfMin, gMinUz, gMinVz);
            }
            else {
                var6.addVertexWithUV(xHalfMin, yTop, zHalfMin, gHalf7Uz, gMinVz);
                var6.addVertexWithUV(xHalfMin, yBottom, zHalfMin, gHalf7Uz, gMaxVz);
                var6.addVertexWithUV(x2, yBottom, zHalfMin, gMinUz, gMaxVz);
                var6.addVertexWithUV(x2, yTop, zHalfMin, gMinUz, gMinVz);
                var6.addVertexWithUV(x3, yTop, zHalfMin, gMaxUz, gMinVz);
                var6.addVertexWithUV(x3, yBottom, zHalfMin, gMaxUz, gMaxVz);
                var6.addVertexWithUV(xHalfMax, yBottom, zHalfMin, gHalf9Uz, gMaxVz);
                var6.addVertexWithUV(xHalfMax, yTop, zHalfMin, gHalf9Uz, gMinVz);
            }
            if (!connZPos) {
                var6.addVertexWithUV(x2, yTop, zHalfMax, gMinUz, gMinVz);
                var6.addVertexWithUV(x2, yBottom, zHalfMax, gMinUz, gMaxVz);
                var6.addVertexWithUV(x3, yBottom, zHalfMax, gMaxUz, gMaxVz);
                var6.addVertexWithUV(x3, yTop, zHalfMax, gMaxUz, gMinVz);
            }
            else {
                var6.addVertexWithUV(x2, yTop, zHalfMax, gMinUz, gMinVz);
                var6.addVertexWithUV(x2, yBottom, zHalfMax, gMinUz, gMaxVz);
                var6.addVertexWithUV(xHalfMin, yBottom, zHalfMax, gHalf7Uz, gMaxVz);
                var6.addVertexWithUV(xHalfMin, yTop, zHalfMax, gHalf7Uz, gMinVz);
                var6.addVertexWithUV(xHalfMax, yTop, zHalfMax, gHalf9Uz, gMinVz);
                var6.addVertexWithUV(xHalfMax, yBottom, zHalfMax, gHalf9Uz, gMaxVz);
                var6.addVertexWithUV(x3, yBottom, zHalfMax, gMaxUz, gMaxVz);
                var6.addVertexWithUV(x3, yTop, zHalfMax, gMaxUz, gMinVz);
            }
            if (drawTop) {
                var6.addVertexWithUV(x2, yTop, zHalfMax, var12, var13);
                var6.addVertexWithUV(x3, yTop, zHalfMax, var12, var14);
                var6.addVertexWithUV(x3, yTop, zHalfMin, var11, var14);
                var6.addVertexWithUV(x2, yTop, zHalfMin, var11, var13);
            }
            if (drawBottom) {
                var6.addVertexWithUV(x3, yBottom, zHalfMax, var11, var14);
                var6.addVertexWithUV(x2, yBottom, zHalfMax, var11, var13);
                var6.addVertexWithUV(x2, yBottom, zHalfMin, var12, var13);
                var6.addVertexWithUV(x3, yBottom, zHalfMin, var12, var14);
            }
        }
        else {
            if (!connZNeg && !disconnected) {
                var6.addVertexWithUV(xHalfMax, yTop, zHalfMin, gHalf9Uz, gMinVz);
                var6.addVertexWithUV(xHalfMax, yBottom, zHalfMin, gHalf9Uz, gMaxVz);
                var6.addVertexWithUV(x2, yBottom, zHalfMin, gMinUz, gMaxVz);
                var6.addVertexWithUV(x2, yTop, zHalfMin, gMinUz, gMinVz);
            }
            else {
                var6.addVertexWithUV(xHalfMin, yTop, zHalfMin, gHalf7Uz, gMinVz);
                var6.addVertexWithUV(xHalfMin, yBottom, zHalfMin, gHalf7Uz, gMaxVz);
                var6.addVertexWithUV(x2, yBottom, zHalfMin, gMinUz, gMaxVz);
                var6.addVertexWithUV(x2, yTop, zHalfMin, gMinUz, gMinVz);
            }
            if (!connZPos && !disconnected) {
                var6.addVertexWithUV(x2, yTop, zHalfMax, gMinUz, gMinVz);
                var6.addVertexWithUV(x2, yBottom, zHalfMax, gMinUz, gMaxVz);
                var6.addVertexWithUV(xHalfMax, yBottom, zHalfMax, gHalf9Uz, gMaxVz);
                var6.addVertexWithUV(xHalfMax, yTop, zHalfMax, gHalf9Uz, gMinVz);
            }
            else {
                var6.addVertexWithUV(x2, yTop, zHalfMax, gMinUz, gMinVz);
                var6.addVertexWithUV(x2, yBottom, zHalfMax, gMinUz, gMaxVz);
                var6.addVertexWithUV(xHalfMin, yBottom, zHalfMax, gHalf7Uz, gMaxVz);
                var6.addVertexWithUV(xHalfMin, yTop, zHalfMax, gHalf7Uz, gMinVz);
            }
            if (drawTop) {
                var6.addVertexWithUV(x2, yTop, zHalfMax, var12, var13);
                var6.addVertexWithUV(xHalfMin, yTop, zHalfMax, var12, var15);
                var6.addVertexWithUV(xHalfMin, yTop, zHalfMin, var11, var15);
                var6.addVertexWithUV(x2, yTop, zHalfMin, var11, var13);
            }
            if (drawBottom) {
                var6.addVertexWithUV(xHalfMin, yBottom, zHalfMax, var11, var15);
                var6.addVertexWithUV(x2, yBottom, zHalfMax, var11, var13);
                var6.addVertexWithUV(x2, yBottom, zHalfMin, var12, var13);
                var6.addVertexWithUV(xHalfMin, yBottom, zHalfMin, var12, var15);
            }
        }
        if ((connXPos || disconnected) && !connXNeg) {
            if (!connZPos && !disconnected) {
                var6.addVertexWithUV(xHalfMin, yTop, zHalfMax, gHalf7Uz, gMinVz);
                var6.addVertexWithUV(xHalfMin, yBottom, zHalfMax, gHalf7Uz, gMaxVz);
                var6.addVertexWithUV(x3, yBottom, zHalfMax, gMaxUz, gMaxVz);
                var6.addVertexWithUV(x3, yTop, zHalfMax, gMaxUz, gMinVz);
            }
            else {
                var6.addVertexWithUV(xHalfMax, yTop, zHalfMax, gHalf9Uz, gMinVz);
                var6.addVertexWithUV(xHalfMax, yBottom, zHalfMax, gHalf9Uz, gMaxVz);
                var6.addVertexWithUV(x3, yBottom, zHalfMax, gMaxUz, gMaxVz);
                var6.addVertexWithUV(x3, yTop, zHalfMax, gMaxUz, gMinVz);
            }
            if (!connZNeg && !disconnected) {
                var6.addVertexWithUV(x3, yTop, zHalfMin, gMaxUz, gMinVz);
                var6.addVertexWithUV(x3, yBottom, zHalfMin, gMaxUz, gMaxVz);
                var6.addVertexWithUV(xHalfMin, yBottom, zHalfMin, gHalf7Uz, gMaxVz);
                var6.addVertexWithUV(xHalfMin, yTop, zHalfMin, gHalf7Uz, gMinVz);
            }
            else {
                var6.addVertexWithUV(x3, yTop, zHalfMin, gMaxUz, gMinVz);
                var6.addVertexWithUV(x3, yBottom, zHalfMin, gMaxUz, gMaxVz);
                var6.addVertexWithUV(xHalfMax, yBottom, zHalfMin, gHalf9Uz, gMaxVz);
                var6.addVertexWithUV(xHalfMax, yTop, zHalfMin, gHalf9Uz, gMinVz);
            }
            if (drawTop) {
                var6.addVertexWithUV(xHalfMax, yTop, zHalfMax, var12, var16);
                var6.addVertexWithUV(x3, yTop, zHalfMax, var12, var13);
                var6.addVertexWithUV(x3, yTop, zHalfMin, var11, var13);
                var6.addVertexWithUV(xHalfMax, yTop, zHalfMin, var11, var16);
            }
            if (drawBottom) {
                var6.addVertexWithUV(x3, yBottom, zHalfMax, var11, var14);
                var6.addVertexWithUV(xHalfMax, yBottom, zHalfMax, var11, var16);
                var6.addVertexWithUV(xHalfMax, yBottom, zHalfMin, var12, var16);
                var6.addVertexWithUV(x3, yBottom, zHalfMin, var12, var14);
            }
        }
        else if (!connXPos && !connZNeg && !connZPos) {
            var6.addVertexWithUV(xHalfMax, yTop, zHalfMax, gHalf7U2, gMinV);
            var6.addVertexWithUV(xHalfMax, yBottom, zHalfMax, gHalf7U2, gMaxV);
            var6.addVertexWithUV(xHalfMax, yBottom, zHalfMin, gHalf9U, gMaxV);
            var6.addVertexWithUV(xHalfMax, yTop, zHalfMin, gHalf9U, gMinV);
        }
        if (!connZNeg && !disconnected) {
            if (!connXPos && !connXNeg) {
                var6.addVertexWithUV(xHalfMax, yTop, zHalfMin, gHalf9Uz, gMinVz);
                var6.addVertexWithUV(xHalfMax, yBottom, zHalfMin, gHalf9Uz, gMaxVz);
                var6.addVertexWithUV(xHalfMin, yBottom, zHalfMin, gHalf7Uz, gMaxVz);
                var6.addVertexWithUV(xHalfMin, yTop, zHalfMin, gHalf7Uz, gMinVz);
            }
        }
        else if (connZNeg && connZPos) {
            if (!connXNeg) {
                var6.addVertexWithUV(xHalfMin, yTop, z2, gMinU2, gMinV);
                var6.addVertexWithUV(xHalfMin, yBottom, z2, gMinU2, gMaxV);
                var6.addVertexWithUV(xHalfMin, yBottom, z3, gMaxU, gMaxV);
                var6.addVertexWithUV(xHalfMin, yTop, z3, gMaxU, gMinV);
            }
            else {
                var6.addVertexWithUV(xHalfMin, yTop, z2, gMinU2, gMinV);
                var6.addVertexWithUV(xHalfMin, yBottom, z2, gMinU2, gMaxV);
                var6.addVertexWithUV(xHalfMin, yBottom, zHalfMin, gHalf7U2, gMaxV);
                var6.addVertexWithUV(xHalfMin, yTop, zHalfMin, gHalf7U2, gMinV);
                var6.addVertexWithUV(xHalfMin, yTop, zHalfMax, gHalf9U, gMinV);
                var6.addVertexWithUV(xHalfMin, yBottom, zHalfMax, gHalf9U, gMaxV);
                var6.addVertexWithUV(xHalfMin, yBottom, z3, gMaxU, gMaxV);
                var6.addVertexWithUV(xHalfMin, yTop, z3, gMaxU, gMinV);
            }
            if (!connXPos) {
                var6.addVertexWithUV(xHalfMax, yTop, z3, gMaxU, gMinV);
                var6.addVertexWithUV(xHalfMax, yBottom, z3, gMaxU, gMaxV);
                var6.addVertexWithUV(xHalfMax, yBottom, z2, gMinU2, gMaxV);
                var6.addVertexWithUV(xHalfMax, yTop, z2, gMinU2, gMinV);
            }
            else {
                var6.addVertexWithUV(xHalfMax, yTop, zHalfMin, gHalf7U2, gMinV);
                var6.addVertexWithUV(xHalfMax, yBottom, zHalfMin, gHalf7U2, gMaxV);
                var6.addVertexWithUV(xHalfMax, yBottom, z2, gMinU2, gMaxV);
                var6.addVertexWithUV(xHalfMax, yTop, z2, gMinU2, gMinV);
                var6.addVertexWithUV(xHalfMax, yTop, z3, gMaxU, gMinV);
                var6.addVertexWithUV(xHalfMax, yBottom, z3, gMaxU, gMaxV);
                var6.addVertexWithUV(xHalfMax, yBottom, zHalfMax, gHalf9U, gMaxV);
                var6.addVertexWithUV(xHalfMax, yTop, zHalfMax, gHalf9U, gMinV);
            }
            if (drawTop) {
                var6.addVertexWithUV(xHalfMax, yTop, z2, var12, var13);
                var6.addVertexWithUV(xHalfMin, yTop, z2, var11, var13);
                var6.addVertexWithUV(xHalfMin, yTop, z3, var11, var14);
                var6.addVertexWithUV(xHalfMax, yTop, z3, var12, var14);
            }
            if (drawBottom) {
                var6.addVertexWithUV(xHalfMin, yBottom, z2, var11, var13);
                var6.addVertexWithUV(xHalfMax, yBottom, z2, var12, var13);
                var6.addVertexWithUV(xHalfMax, yBottom, z3, var12, var14);
                var6.addVertexWithUV(xHalfMin, yBottom, z3, var11, var14);
            }
        }
        else {
            if (!connXNeg && !disconnected) {
                var6.addVertexWithUV(xHalfMin, yTop, z2, gMinU2, gMinV);
                var6.addVertexWithUV(xHalfMin, yBottom, z2, gMinU2, gMaxV);
                var6.addVertexWithUV(xHalfMin, yBottom, zHalfMax, gHalf9U, gMaxV);
                var6.addVertexWithUV(xHalfMin, yTop, zHalfMax, gHalf9U, gMinV);
            }
            else {
                var6.addVertexWithUV(xHalfMin, yTop, z2, gMinU2, gMinV);
                var6.addVertexWithUV(xHalfMin, yBottom, z2, gMinU2, gMaxV);
                var6.addVertexWithUV(xHalfMin, yBottom, zHalfMin, gHalf7U2, gMaxV);
                var6.addVertexWithUV(xHalfMin, yTop, zHalfMin, gHalf7U2, gMinV);
            }
            if (!connXPos && !disconnected) {
                var6.addVertexWithUV(xHalfMax, yTop, zHalfMax, gHalf9U, gMinV);
                var6.addVertexWithUV(xHalfMax, yBottom, zHalfMax, gHalf9U, gMaxV);
                var6.addVertexWithUV(xHalfMax, yBottom, z2, gMinU2, gMaxV);
                var6.addVertexWithUV(xHalfMax, yTop, z2, gMinU2, gMinV);
            }
            else {
                var6.addVertexWithUV(xHalfMax, yTop, zHalfMin, gHalf7U2, gMinV);
                var6.addVertexWithUV(xHalfMax, yBottom, zHalfMin, gHalf7U2, gMaxV);
                var6.addVertexWithUV(xHalfMax, yBottom, z2, gMinU2, gMaxV);
                var6.addVertexWithUV(xHalfMax, yTop, z2, gMinU2, gMinV);
            }
            if (drawTop) {
                var6.addVertexWithUV(xHalfMax, yTop, z2, var12, var13);
                var6.addVertexWithUV(xHalfMin, yTop, z2, var11, var13);
                var6.addVertexWithUV(xHalfMin, yTop, zHalfMin, var11, var15);
                var6.addVertexWithUV(xHalfMax, yTop, zHalfMin, var12, var15);
            }
            if (drawBottom) {
                var6.addVertexWithUV(xHalfMin, yBottom, z2, var11, var13);
                var6.addVertexWithUV(xHalfMax, yBottom, z2, var12, var13);
                var6.addVertexWithUV(xHalfMax, yBottom, zHalfMin, var12, var15);
                var6.addVertexWithUV(xHalfMin, yBottom, zHalfMin, var11, var15);
            }
        }
        if ((connZPos || disconnected) && !connZNeg) {
            if (!connXNeg && !disconnected) {
                var6.addVertexWithUV(xHalfMin, yTop, zHalfMin, gHalf7U2, gMinV);
                var6.addVertexWithUV(xHalfMin, yBottom, zHalfMin, gHalf7U2, gMaxV);
                var6.addVertexWithUV(xHalfMin, yBottom, z3, gMaxU, gMaxV);
                var6.addVertexWithUV(xHalfMin, yTop, z3, gMaxU, gMinV);
            }
            else {
                var6.addVertexWithUV(xHalfMin, yTop, zHalfMax, gHalf9U, gMinV);
                var6.addVertexWithUV(xHalfMin, yBottom, zHalfMax, gHalf9U, gMaxV);
                var6.addVertexWithUV(xHalfMin, yBottom, z3, gMaxU, gMaxV);
                var6.addVertexWithUV(xHalfMin, yTop, z3, gMaxU, gMinV);
            }
            if (!connXPos && !disconnected) {
                var6.addVertexWithUV(xHalfMax, yTop, z3, gMaxU, gMinV);
                var6.addVertexWithUV(xHalfMax, yBottom, z3, gMaxU, gMaxV);
                var6.addVertexWithUV(xHalfMax, yBottom, zHalfMin, gHalf7U2, gMaxV);
                var6.addVertexWithUV(xHalfMax, yTop, zHalfMin, gHalf7U2, gMinV);
            }
            else {
                var6.addVertexWithUV(xHalfMax, yTop, z3, gMaxU, gMinV);
                var6.addVertexWithUV(xHalfMax, yBottom, z3, gMaxU, gMaxV);
                var6.addVertexWithUV(xHalfMax, yBottom, zHalfMax, gHalf9U, gMaxV);
                var6.addVertexWithUV(xHalfMax, yTop, zHalfMax, gHalf9U, gMinV);
            }
            if (drawTop) {
                var6.addVertexWithUV(xHalfMax, yTop, zHalfMax, var12, var16);
                var6.addVertexWithUV(xHalfMin, yTop, zHalfMax, var11, var16);
                var6.addVertexWithUV(xHalfMin, yTop, z3, var11, var14);
                var6.addVertexWithUV(xHalfMax, yTop, z3, var12, var14);
            }
            if (drawBottom) {
                var6.addVertexWithUV(xHalfMin, yBottom, zHalfMax, var11, var16);
                var6.addVertexWithUV(xHalfMax, yBottom, zHalfMax, var12, var16);
                var6.addVertexWithUV(xHalfMax, yBottom, z3, var12, var14);
                var6.addVertexWithUV(xHalfMin, yBottom, z3, var11, var14);
            }
        }
        else if (!connZPos && !connXPos && !connXNeg) {
            var6.addVertexWithUV(xHalfMin, yTop, zHalfMax, gHalf7Uz, gMinVz);
            var6.addVertexWithUV(xHalfMin, yBottom, zHalfMax, gHalf7Uz, gMaxVz);
            var6.addVertexWithUV(xHalfMax, yBottom, zHalfMax, gHalf9Uz, gMaxVz);
            var6.addVertexWithUV(xHalfMax, yTop, zHalfMax, gHalf9Uz, gMinVz);
        }
        if (drawTop) {
            var6.addVertexWithUV(xHalfMax, yTop, zHalfMin, var12, var15);
            var6.addVertexWithUV(xHalfMin, yTop, zHalfMin, var11, var15);
            var6.addVertexWithUV(xHalfMin, yTop, zHalfMax, var11, var16);
            var6.addVertexWithUV(xHalfMax, yTop, zHalfMax, var12, var16);
        }
        if (drawBottom) {
            var6.addVertexWithUV(xHalfMin, yBottom, zHalfMin, var11, var15);
            var6.addVertexWithUV(xHalfMax, yBottom, zHalfMin, var12, var15);
            var6.addVertexWithUV(xHalfMax, yBottom, zHalfMax, var12, var16);
            var6.addVertexWithUV(xHalfMin, yBottom, zHalfMax, var11, var16);
        }
        if (disconnected) {
            var6.addVertexWithUV(x2, yTop, zHalfMin, gHalf7U2, gMinV);
            var6.addVertexWithUV(x2, yBottom, zHalfMin, gHalf7U2, gMaxV);
            var6.addVertexWithUV(x2, yBottom, zHalfMax, gHalf9U, gMaxV);
            var6.addVertexWithUV(x2, yTop, zHalfMax, gHalf9U, gMinV);
            var6.addVertexWithUV(x3, yTop, zHalfMax, gHalf7U2, gMinV);
            var6.addVertexWithUV(x3, yBottom, zHalfMax, gHalf7U2, gMaxV);
            var6.addVertexWithUV(x3, yBottom, zHalfMin, gHalf9U, gMaxV);
            var6.addVertexWithUV(x3, yTop, zHalfMin, gHalf9U, gMinV);
            var6.addVertexWithUV(xHalfMax, yTop, z2, gHalf9Uz, gMinVz);
            var6.addVertexWithUV(xHalfMax, yBottom, z2, gHalf9Uz, gMaxVz);
            var6.addVertexWithUV(xHalfMin, yBottom, z2, gHalf7Uz, gMaxVz);
            var6.addVertexWithUV(xHalfMin, yTop, z2, gHalf7Uz, gMinVz);
            var6.addVertexWithUV(xHalfMin, yTop, z3, gHalf7Uz, gMinVz);
            var6.addVertexWithUV(xHalfMin, yBottom, z3, gHalf7Uz, gMaxVz);
            var6.addVertexWithUV(xHalfMax, yBottom, z3, gHalf9Uz, gMaxVz);
            var6.addVertexWithUV(xHalfMax, yTop, z3, gHalf9Uz, gMinVz);
        }
        return true;
    }
    
    public boolean renderBlockPane(final BlockPane p_147767_1_, final int p_147767_2_, final int p_147767_3_, final int p_147767_4_) {
        final int var5 = this.blockAccess.getHeight();
        final Tessellator var6 = Tessellator.instance;
        var6.setBrightness(p_147767_1_.getBlockBrightness(this.blockAccess, p_147767_2_, p_147767_3_, p_147767_4_));
        final int var7 = p_147767_1_.colorMultiplier(this.blockAccess, p_147767_2_, p_147767_3_, p_147767_4_);
        float var8 = (var7 >> 16 & 0xFF) / 255.0f;
        float var9 = (var7 >> 8 & 0xFF) / 255.0f;
        float var10 = (var7 & 0xFF) / 255.0f;
        if (EntityRenderer.anaglyphEnable) {
            final float var11 = (var8 * 30.0f + var9 * 59.0f + var10 * 11.0f) / 100.0f;
            final float var12 = (var8 * 30.0f + var9 * 70.0f) / 100.0f;
            final float kr = (var8 * 30.0f + var10 * 70.0f) / 100.0f;
            var8 = var11;
            var9 = var12;
            var10 = kr;
        }
        var6.setColorOpaque_F(var8, var9, var10);
        IIcon var13;
        IIcon var14;
        if (this.hasOverrideBlockTexture()) {
            var13 = this.overrideBlockTexture;
            var14 = this.overrideBlockTexture;
        }
        else {
            final int kr2 = this.blockAccess.getBlockMetadata(p_147767_2_, p_147767_3_, p_147767_4_);
            var13 = this.getBlockIconFromSideAndMetadata(p_147767_1_, 0, kr2);
            var14 = p_147767_1_.func_150097_e();
        }
        IIcon kr3 = var13;
        IIcon kz = var13;
        IIcon kzr = var13;
        if (Config.isConnectedTextures() && this.overrideBlockTexture == null) {
            var13 = ConnectedTextures.getConnectedTexture(this.blockAccess, p_147767_1_, p_147767_2_, p_147767_3_, p_147767_4_, 2, var13);
            kr3 = ConnectedTextures.getConnectedTexture(this.blockAccess, p_147767_1_, p_147767_2_, p_147767_3_, p_147767_4_, 3, kr3);
            kz = ConnectedTextures.getConnectedTexture(this.blockAccess, p_147767_1_, p_147767_2_, p_147767_3_, p_147767_4_, 4, kz);
            kzr = ConnectedTextures.getConnectedTexture(this.blockAccess, p_147767_1_, p_147767_2_, p_147767_3_, p_147767_4_, 5, kzr);
        }
        final double var15 = var13.getMinU();
        final double var16 = var13.getInterpolatedU(8.0);
        final double var17 = var13.getMaxU();
        final double var18 = var13.getMinV();
        final double var19 = var13.getMaxV();
        final double dr = kr3.getMinU();
        final double d1r = kr3.getInterpolatedU(8.0);
        final double d2r = kr3.getMaxU();
        final double d3r = kr3.getMinV();
        final double d4r = kr3.getMaxV();
        final double dz = kz.getMinU();
        final double d1z = kz.getInterpolatedU(8.0);
        final double d2z = kz.getMaxU();
        final double d3z = kz.getMinV();
        final double d4z = kz.getMaxV();
        final double dzr = kzr.getMinU();
        final double d1zr = kzr.getInterpolatedU(8.0);
        final double d2zr = kzr.getMaxU();
        final double d3zr = kzr.getMinV();
        final double d4zr = kzr.getMaxV();
        final double var20 = var14.getInterpolatedU(7.0);
        final double var21 = var14.getInterpolatedU(9.0);
        final double var22 = var14.getMinV();
        final double var23 = var14.getInterpolatedV(8.0);
        final double var24 = var14.getMaxV();
        final double var25 = p_147767_2_;
        final double var26 = p_147767_2_ + 0.5;
        final double var27 = p_147767_2_ + 1;
        final double var28 = p_147767_4_;
        final double var29 = p_147767_4_ + 0.5;
        final double var30 = p_147767_4_ + 1;
        final double var31 = p_147767_2_ + 0.5 - 0.0625;
        final double var32 = p_147767_2_ + 0.5 + 0.0625;
        final double var33 = p_147767_4_ + 0.5 - 0.0625;
        final double var34 = p_147767_4_ + 0.5 + 0.0625;
        final boolean var35 = p_147767_1_.func_150098_a(this.blockAccess.getBlock(p_147767_2_, p_147767_3_, p_147767_4_ - 1));
        final boolean var36 = p_147767_1_.func_150098_a(this.blockAccess.getBlock(p_147767_2_, p_147767_3_, p_147767_4_ + 1));
        final boolean var37 = p_147767_1_.func_150098_a(this.blockAccess.getBlock(p_147767_2_ - 1, p_147767_3_, p_147767_4_));
        final boolean var38 = p_147767_1_.func_150098_a(this.blockAccess.getBlock(p_147767_2_ + 1, p_147767_3_, p_147767_4_));
        final boolean var39 = p_147767_1_.shouldSideBeRendered(this.blockAccess, p_147767_2_, p_147767_3_ + 1, p_147767_4_, 1);
        final boolean var40 = p_147767_1_.shouldSideBeRendered(this.blockAccess, p_147767_2_, p_147767_3_ - 1, p_147767_4_, 0);
        final double var41 = 0.01;
        final double var42 = 0.005;
        if ((!var37 || !var38) && (var37 || var38 || var35 || var36)) {
            if (var37 && !var38) {
                var6.addVertexWithUV(var25, p_147767_3_ + 1, var29, var15, var18);
                var6.addVertexWithUV(var25, p_147767_3_ + 0, var29, var15, var19);
                var6.addVertexWithUV(var26, p_147767_3_ + 0, var29, var16, var19);
                var6.addVertexWithUV(var26, p_147767_3_ + 1, var29, var16, var18);
                var6.addVertexWithUV(var26, p_147767_3_ + 1, var29, d1r, d3r);
                var6.addVertexWithUV(var26, p_147767_3_ + 0, var29, d1r, d4r);
                var6.addVertexWithUV(var25, p_147767_3_ + 0, var29, d2r, d4r);
                var6.addVertexWithUV(var25, p_147767_3_ + 1, var29, d2r, d3r);
                if (!var36 && !var35) {
                    var6.addVertexWithUV(var26, p_147767_3_ + 1, var34, var20, var22);
                    var6.addVertexWithUV(var26, p_147767_3_ + 0, var34, var20, var24);
                    var6.addVertexWithUV(var26, p_147767_3_ + 0, var33, var21, var24);
                    var6.addVertexWithUV(var26, p_147767_3_ + 1, var33, var21, var22);
                    var6.addVertexWithUV(var26, p_147767_3_ + 1, var33, var20, var22);
                    var6.addVertexWithUV(var26, p_147767_3_ + 0, var33, var20, var24);
                    var6.addVertexWithUV(var26, p_147767_3_ + 0, var34, var21, var24);
                    var6.addVertexWithUV(var26, p_147767_3_ + 1, var34, var21, var22);
                }
                if (var39 || (p_147767_3_ < var5 - 1 && this.blockAccess.isAirBlock(p_147767_2_ - 1, p_147767_3_ + 1, p_147767_4_))) {
                    var6.addVertexWithUV(var25, p_147767_3_ + 1 + 0.01, var34, var21, var23);
                    var6.addVertexWithUV(var26, p_147767_3_ + 1 + 0.01, var34, var21, var24);
                    var6.addVertexWithUV(var26, p_147767_3_ + 1 + 0.01, var33, var20, var24);
                    var6.addVertexWithUV(var25, p_147767_3_ + 1 + 0.01, var33, var20, var23);
                    var6.addVertexWithUV(var26, p_147767_3_ + 1 + 0.01, var34, var21, var23);
                    var6.addVertexWithUV(var25, p_147767_3_ + 1 + 0.01, var34, var21, var24);
                    var6.addVertexWithUV(var25, p_147767_3_ + 1 + 0.01, var33, var20, var24);
                    var6.addVertexWithUV(var26, p_147767_3_ + 1 + 0.01, var33, var20, var23);
                }
                if (var40 || (p_147767_3_ > 1 && this.blockAccess.isAirBlock(p_147767_2_ - 1, p_147767_3_ - 1, p_147767_4_))) {
                    var6.addVertexWithUV(var25, p_147767_3_ - 0.01, var34, var21, var23);
                    var6.addVertexWithUV(var26, p_147767_3_ - 0.01, var34, var21, var24);
                    var6.addVertexWithUV(var26, p_147767_3_ - 0.01, var33, var20, var24);
                    var6.addVertexWithUV(var25, p_147767_3_ - 0.01, var33, var20, var23);
                    var6.addVertexWithUV(var26, p_147767_3_ - 0.01, var34, var21, var23);
                    var6.addVertexWithUV(var25, p_147767_3_ - 0.01, var34, var21, var24);
                    var6.addVertexWithUV(var25, p_147767_3_ - 0.01, var33, var20, var24);
                    var6.addVertexWithUV(var26, p_147767_3_ - 0.01, var33, var20, var23);
                }
            }
            else if (!var37 && var38) {
                var6.addVertexWithUV(var26, p_147767_3_ + 1, var29, var16, var18);
                var6.addVertexWithUV(var26, p_147767_3_ + 0, var29, var16, var19);
                var6.addVertexWithUV(var27, p_147767_3_ + 0, var29, var17, var19);
                var6.addVertexWithUV(var27, p_147767_3_ + 1, var29, var17, var18);
                var6.addVertexWithUV(var27, p_147767_3_ + 1, var29, dr, d3r);
                var6.addVertexWithUV(var27, p_147767_3_ + 0, var29, dr, d4r);
                var6.addVertexWithUV(var26, p_147767_3_ + 0, var29, d1r, d4r);
                var6.addVertexWithUV(var26, p_147767_3_ + 1, var29, d1r, d3r);
                if (!var36 && !var35) {
                    var6.addVertexWithUV(var26, p_147767_3_ + 1, var33, var20, var22);
                    var6.addVertexWithUV(var26, p_147767_3_ + 0, var33, var20, var24);
                    var6.addVertexWithUV(var26, p_147767_3_ + 0, var34, var21, var24);
                    var6.addVertexWithUV(var26, p_147767_3_ + 1, var34, var21, var22);
                    var6.addVertexWithUV(var26, p_147767_3_ + 1, var34, var20, var22);
                    var6.addVertexWithUV(var26, p_147767_3_ + 0, var34, var20, var24);
                    var6.addVertexWithUV(var26, p_147767_3_ + 0, var33, var21, var24);
                    var6.addVertexWithUV(var26, p_147767_3_ + 1, var33, var21, var22);
                }
                if (var39 || (p_147767_3_ < var5 - 1 && this.blockAccess.isAirBlock(p_147767_2_ + 1, p_147767_3_ + 1, p_147767_4_))) {
                    var6.addVertexWithUV(var26, p_147767_3_ + 1 + 0.01, var34, var21, var22);
                    var6.addVertexWithUV(var27, p_147767_3_ + 1 + 0.01, var34, var21, var23);
                    var6.addVertexWithUV(var27, p_147767_3_ + 1 + 0.01, var33, var20, var23);
                    var6.addVertexWithUV(var26, p_147767_3_ + 1 + 0.01, var33, var20, var22);
                    var6.addVertexWithUV(var27, p_147767_3_ + 1 + 0.01, var34, var21, var22);
                    var6.addVertexWithUV(var26, p_147767_3_ + 1 + 0.01, var34, var21, var23);
                    var6.addVertexWithUV(var26, p_147767_3_ + 1 + 0.01, var33, var20, var23);
                    var6.addVertexWithUV(var27, p_147767_3_ + 1 + 0.01, var33, var20, var22);
                }
                if (var40 || (p_147767_3_ > 1 && this.blockAccess.isAirBlock(p_147767_2_ + 1, p_147767_3_ - 1, p_147767_4_))) {
                    var6.addVertexWithUV(var26, p_147767_3_ - 0.01, var34, var21, var22);
                    var6.addVertexWithUV(var27, p_147767_3_ - 0.01, var34, var21, var23);
                    var6.addVertexWithUV(var27, p_147767_3_ - 0.01, var33, var20, var23);
                    var6.addVertexWithUV(var26, p_147767_3_ - 0.01, var33, var20, var22);
                    var6.addVertexWithUV(var27, p_147767_3_ - 0.01, var34, var21, var22);
                    var6.addVertexWithUV(var26, p_147767_3_ - 0.01, var34, var21, var23);
                    var6.addVertexWithUV(var26, p_147767_3_ - 0.01, var33, var20, var23);
                    var6.addVertexWithUV(var27, p_147767_3_ - 0.01, var33, var20, var22);
                }
            }
        }
        else {
            var6.addVertexWithUV(var25, p_147767_3_ + 1, var29, var15, var18);
            var6.addVertexWithUV(var25, p_147767_3_ + 0, var29, var15, var19);
            var6.addVertexWithUV(var27, p_147767_3_ + 0, var29, var17, var19);
            var6.addVertexWithUV(var27, p_147767_3_ + 1, var29, var17, var18);
            var6.addVertexWithUV(var27, p_147767_3_ + 1, var29, dr, d3r);
            var6.addVertexWithUV(var27, p_147767_3_ + 0, var29, dr, d4r);
            var6.addVertexWithUV(var25, p_147767_3_ + 0, var29, d2r, d4r);
            var6.addVertexWithUV(var25, p_147767_3_ + 1, var29, d2r, d3r);
            if (var39) {
                var6.addVertexWithUV(var25, p_147767_3_ + 1 + 0.01, var34, var21, var24);
                var6.addVertexWithUV(var27, p_147767_3_ + 1 + 0.01, var34, var21, var22);
                var6.addVertexWithUV(var27, p_147767_3_ + 1 + 0.01, var33, var20, var22);
                var6.addVertexWithUV(var25, p_147767_3_ + 1 + 0.01, var33, var20, var24);
                var6.addVertexWithUV(var27, p_147767_3_ + 1 + 0.01, var34, var21, var24);
                var6.addVertexWithUV(var25, p_147767_3_ + 1 + 0.01, var34, var21, var22);
                var6.addVertexWithUV(var25, p_147767_3_ + 1 + 0.01, var33, var20, var22);
                var6.addVertexWithUV(var27, p_147767_3_ + 1 + 0.01, var33, var20, var24);
            }
            else {
                if (p_147767_3_ < var5 - 1 && this.blockAccess.isAirBlock(p_147767_2_ - 1, p_147767_3_ + 1, p_147767_4_)) {
                    var6.addVertexWithUV(var25, p_147767_3_ + 1 + 0.01, var34, var21, var23);
                    var6.addVertexWithUV(var26, p_147767_3_ + 1 + 0.01, var34, var21, var24);
                    var6.addVertexWithUV(var26, p_147767_3_ + 1 + 0.01, var33, var20, var24);
                    var6.addVertexWithUV(var25, p_147767_3_ + 1 + 0.01, var33, var20, var23);
                    var6.addVertexWithUV(var26, p_147767_3_ + 1 + 0.01, var34, var21, var23);
                    var6.addVertexWithUV(var25, p_147767_3_ + 1 + 0.01, var34, var21, var24);
                    var6.addVertexWithUV(var25, p_147767_3_ + 1 + 0.01, var33, var20, var24);
                    var6.addVertexWithUV(var26, p_147767_3_ + 1 + 0.01, var33, var20, var23);
                }
                if (p_147767_3_ < var5 - 1 && this.blockAccess.isAirBlock(p_147767_2_ + 1, p_147767_3_ + 1, p_147767_4_)) {
                    var6.addVertexWithUV(var26, p_147767_3_ + 1 + 0.01, var34, var21, var22);
                    var6.addVertexWithUV(var27, p_147767_3_ + 1 + 0.01, var34, var21, var23);
                    var6.addVertexWithUV(var27, p_147767_3_ + 1 + 0.01, var33, var20, var23);
                    var6.addVertexWithUV(var26, p_147767_3_ + 1 + 0.01, var33, var20, var22);
                    var6.addVertexWithUV(var27, p_147767_3_ + 1 + 0.01, var34, var21, var22);
                    var6.addVertexWithUV(var26, p_147767_3_ + 1 + 0.01, var34, var21, var23);
                    var6.addVertexWithUV(var26, p_147767_3_ + 1 + 0.01, var33, var20, var23);
                    var6.addVertexWithUV(var27, p_147767_3_ + 1 + 0.01, var33, var20, var22);
                }
            }
            if (var40) {
                var6.addVertexWithUV(var25, p_147767_3_ - 0.01, var34, var21, var24);
                var6.addVertexWithUV(var27, p_147767_3_ - 0.01, var34, var21, var22);
                var6.addVertexWithUV(var27, p_147767_3_ - 0.01, var33, var20, var22);
                var6.addVertexWithUV(var25, p_147767_3_ - 0.01, var33, var20, var24);
                var6.addVertexWithUV(var27, p_147767_3_ - 0.01, var34, var21, var24);
                var6.addVertexWithUV(var25, p_147767_3_ - 0.01, var34, var21, var22);
                var6.addVertexWithUV(var25, p_147767_3_ - 0.01, var33, var20, var22);
                var6.addVertexWithUV(var27, p_147767_3_ - 0.01, var33, var20, var24);
            }
            else {
                if (p_147767_3_ > 1 && this.blockAccess.isAirBlock(p_147767_2_ - 1, p_147767_3_ - 1, p_147767_4_)) {
                    var6.addVertexWithUV(var25, p_147767_3_ - 0.01, var34, var21, var23);
                    var6.addVertexWithUV(var26, p_147767_3_ - 0.01, var34, var21, var24);
                    var6.addVertexWithUV(var26, p_147767_3_ - 0.01, var33, var20, var24);
                    var6.addVertexWithUV(var25, p_147767_3_ - 0.01, var33, var20, var23);
                    var6.addVertexWithUV(var26, p_147767_3_ - 0.01, var34, var21, var23);
                    var6.addVertexWithUV(var25, p_147767_3_ - 0.01, var34, var21, var24);
                    var6.addVertexWithUV(var25, p_147767_3_ - 0.01, var33, var20, var24);
                    var6.addVertexWithUV(var26, p_147767_3_ - 0.01, var33, var20, var23);
                }
                if (p_147767_3_ > 1 && this.blockAccess.isAirBlock(p_147767_2_ + 1, p_147767_3_ - 1, p_147767_4_)) {
                    var6.addVertexWithUV(var26, p_147767_3_ - 0.01, var34, var21, var22);
                    var6.addVertexWithUV(var27, p_147767_3_ - 0.01, var34, var21, var23);
                    var6.addVertexWithUV(var27, p_147767_3_ - 0.01, var33, var20, var23);
                    var6.addVertexWithUV(var26, p_147767_3_ - 0.01, var33, var20, var22);
                    var6.addVertexWithUV(var27, p_147767_3_ - 0.01, var34, var21, var22);
                    var6.addVertexWithUV(var26, p_147767_3_ - 0.01, var34, var21, var23);
                    var6.addVertexWithUV(var26, p_147767_3_ - 0.01, var33, var20, var23);
                    var6.addVertexWithUV(var27, p_147767_3_ - 0.01, var33, var20, var22);
                }
            }
        }
        if ((!var35 || !var36) && (var37 || var38 || var35 || var36)) {
            if (var35 && !var36) {
                var6.addVertexWithUV(var26, p_147767_3_ + 1, var28, dz, d3z);
                var6.addVertexWithUV(var26, p_147767_3_ + 0, var28, dz, d4z);
                var6.addVertexWithUV(var26, p_147767_3_ + 0, var29, d1z, d4z);
                var6.addVertexWithUV(var26, p_147767_3_ + 1, var29, d1z, d3z);
                var6.addVertexWithUV(var26, p_147767_3_ + 1, var29, d1zr, d3zr);
                var6.addVertexWithUV(var26, p_147767_3_ + 0, var29, d1zr, d4zr);
                var6.addVertexWithUV(var26, p_147767_3_ + 0, var28, d2zr, d4zr);
                var6.addVertexWithUV(var26, p_147767_3_ + 1, var28, d2zr, d3zr);
                if (!var38 && !var37) {
                    var6.addVertexWithUV(var31, p_147767_3_ + 1, var29, var20, var22);
                    var6.addVertexWithUV(var31, p_147767_3_ + 0, var29, var20, var24);
                    var6.addVertexWithUV(var32, p_147767_3_ + 0, var29, var21, var24);
                    var6.addVertexWithUV(var32, p_147767_3_ + 1, var29, var21, var22);
                    var6.addVertexWithUV(var32, p_147767_3_ + 1, var29, var20, var22);
                    var6.addVertexWithUV(var32, p_147767_3_ + 0, var29, var20, var24);
                    var6.addVertexWithUV(var31, p_147767_3_ + 0, var29, var21, var24);
                    var6.addVertexWithUV(var31, p_147767_3_ + 1, var29, var21, var22);
                }
                if (var39 || (p_147767_3_ < var5 - 1 && this.blockAccess.isAirBlock(p_147767_2_, p_147767_3_ + 1, p_147767_4_ - 1))) {
                    var6.addVertexWithUV(var31, p_147767_3_ + 1 + 0.005, var28, var21, var22);
                    var6.addVertexWithUV(var31, p_147767_3_ + 1 + 0.005, var29, var21, var23);
                    var6.addVertexWithUV(var32, p_147767_3_ + 1 + 0.005, var29, var20, var23);
                    var6.addVertexWithUV(var32, p_147767_3_ + 1 + 0.005, var28, var20, var22);
                    var6.addVertexWithUV(var31, p_147767_3_ + 1 + 0.005, var29, var21, var22);
                    var6.addVertexWithUV(var31, p_147767_3_ + 1 + 0.005, var28, var21, var23);
                    var6.addVertexWithUV(var32, p_147767_3_ + 1 + 0.005, var28, var20, var23);
                    var6.addVertexWithUV(var32, p_147767_3_ + 1 + 0.005, var29, var20, var22);
                }
                if (var40 || (p_147767_3_ > 1 && this.blockAccess.isAirBlock(p_147767_2_, p_147767_3_ - 1, p_147767_4_ - 1))) {
                    var6.addVertexWithUV(var31, p_147767_3_ - 0.005, var28, var21, var22);
                    var6.addVertexWithUV(var31, p_147767_3_ - 0.005, var29, var21, var23);
                    var6.addVertexWithUV(var32, p_147767_3_ - 0.005, var29, var20, var23);
                    var6.addVertexWithUV(var32, p_147767_3_ - 0.005, var28, var20, var22);
                    var6.addVertexWithUV(var31, p_147767_3_ - 0.005, var29, var21, var22);
                    var6.addVertexWithUV(var31, p_147767_3_ - 0.005, var28, var21, var23);
                    var6.addVertexWithUV(var32, p_147767_3_ - 0.005, var28, var20, var23);
                    var6.addVertexWithUV(var32, p_147767_3_ - 0.005, var29, var20, var22);
                }
            }
            else if (!var35 && var36) {
                var6.addVertexWithUV(var26, p_147767_3_ + 1, var29, d1z, d3z);
                var6.addVertexWithUV(var26, p_147767_3_ + 0, var29, d1z, d4z);
                var6.addVertexWithUV(var26, p_147767_3_ + 0, var30, d2z, d4z);
                var6.addVertexWithUV(var26, p_147767_3_ + 1, var30, d2z, d3z);
                var6.addVertexWithUV(var26, p_147767_3_ + 1, var30, dzr, d3zr);
                var6.addVertexWithUV(var26, p_147767_3_ + 0, var30, dzr, d4zr);
                var6.addVertexWithUV(var26, p_147767_3_ + 0, var29, d1zr, d4zr);
                var6.addVertexWithUV(var26, p_147767_3_ + 1, var29, d1zr, d3zr);
                if (!var38 && !var37) {
                    var6.addVertexWithUV(var32, p_147767_3_ + 1, var29, var20, var22);
                    var6.addVertexWithUV(var32, p_147767_3_ + 0, var29, var20, var24);
                    var6.addVertexWithUV(var31, p_147767_3_ + 0, var29, var21, var24);
                    var6.addVertexWithUV(var31, p_147767_3_ + 1, var29, var21, var22);
                    var6.addVertexWithUV(var31, p_147767_3_ + 1, var29, var20, var22);
                    var6.addVertexWithUV(var31, p_147767_3_ + 0, var29, var20, var24);
                    var6.addVertexWithUV(var32, p_147767_3_ + 0, var29, var21, var24);
                    var6.addVertexWithUV(var32, p_147767_3_ + 1, var29, var21, var22);
                }
                if (var39 || (p_147767_3_ < var5 - 1 && this.blockAccess.isAirBlock(p_147767_2_, p_147767_3_ + 1, p_147767_4_ + 1))) {
                    var6.addVertexWithUV(var31, p_147767_3_ + 1 + 0.005, var29, var20, var23);
                    var6.addVertexWithUV(var31, p_147767_3_ + 1 + 0.005, var30, var20, var24);
                    var6.addVertexWithUV(var32, p_147767_3_ + 1 + 0.005, var30, var21, var24);
                    var6.addVertexWithUV(var32, p_147767_3_ + 1 + 0.005, var29, var21, var23);
                    var6.addVertexWithUV(var31, p_147767_3_ + 1 + 0.005, var30, var20, var23);
                    var6.addVertexWithUV(var31, p_147767_3_ + 1 + 0.005, var29, var20, var24);
                    var6.addVertexWithUV(var32, p_147767_3_ + 1 + 0.005, var29, var21, var24);
                    var6.addVertexWithUV(var32, p_147767_3_ + 1 + 0.005, var30, var21, var23);
                }
                if (var40 || (p_147767_3_ > 1 && this.blockAccess.isAirBlock(p_147767_2_, p_147767_3_ - 1, p_147767_4_ + 1))) {
                    var6.addVertexWithUV(var31, p_147767_3_ - 0.005, var29, var20, var23);
                    var6.addVertexWithUV(var31, p_147767_3_ - 0.005, var30, var20, var24);
                    var6.addVertexWithUV(var32, p_147767_3_ - 0.005, var30, var21, var24);
                    var6.addVertexWithUV(var32, p_147767_3_ - 0.005, var29, var21, var23);
                    var6.addVertexWithUV(var31, p_147767_3_ - 0.005, var30, var20, var23);
                    var6.addVertexWithUV(var31, p_147767_3_ - 0.005, var29, var20, var24);
                    var6.addVertexWithUV(var32, p_147767_3_ - 0.005, var29, var21, var24);
                    var6.addVertexWithUV(var32, p_147767_3_ - 0.005, var30, var21, var23);
                }
            }
        }
        else {
            var6.addVertexWithUV(var26, p_147767_3_ + 1, var30, dzr, d3zr);
            var6.addVertexWithUV(var26, p_147767_3_ + 0, var30, dzr, d4zr);
            var6.addVertexWithUV(var26, p_147767_3_ + 0, var28, d2zr, d4zr);
            var6.addVertexWithUV(var26, p_147767_3_ + 1, var28, d2zr, d3zr);
            var6.addVertexWithUV(var26, p_147767_3_ + 1, var28, dz, d3z);
            var6.addVertexWithUV(var26, p_147767_3_ + 0, var28, dz, d4z);
            var6.addVertexWithUV(var26, p_147767_3_ + 0, var30, d2z, d4z);
            var6.addVertexWithUV(var26, p_147767_3_ + 1, var30, d2z, d3z);
            if (var39) {
                var6.addVertexWithUV(var32, p_147767_3_ + 1 + 0.005, var30, var21, var24);
                var6.addVertexWithUV(var32, p_147767_3_ + 1 + 0.005, var28, var21, var22);
                var6.addVertexWithUV(var31, p_147767_3_ + 1 + 0.005, var28, var20, var22);
                var6.addVertexWithUV(var31, p_147767_3_ + 1 + 0.005, var30, var20, var24);
                var6.addVertexWithUV(var32, p_147767_3_ + 1 + 0.005, var28, var21, var24);
                var6.addVertexWithUV(var32, p_147767_3_ + 1 + 0.005, var30, var21, var22);
                var6.addVertexWithUV(var31, p_147767_3_ + 1 + 0.005, var30, var20, var22);
                var6.addVertexWithUV(var31, p_147767_3_ + 1 + 0.005, var28, var20, var24);
            }
            else {
                if (p_147767_3_ < var5 - 1 && this.blockAccess.isAirBlock(p_147767_2_, p_147767_3_ + 1, p_147767_4_ - 1)) {
                    var6.addVertexWithUV(var31, p_147767_3_ + 1 + 0.005, var28, var21, var22);
                    var6.addVertexWithUV(var31, p_147767_3_ + 1 + 0.005, var29, var21, var23);
                    var6.addVertexWithUV(var32, p_147767_3_ + 1 + 0.005, var29, var20, var23);
                    var6.addVertexWithUV(var32, p_147767_3_ + 1 + 0.005, var28, var20, var22);
                    var6.addVertexWithUV(var31, p_147767_3_ + 1 + 0.005, var29, var21, var22);
                    var6.addVertexWithUV(var31, p_147767_3_ + 1 + 0.005, var28, var21, var23);
                    var6.addVertexWithUV(var32, p_147767_3_ + 1 + 0.005, var28, var20, var23);
                    var6.addVertexWithUV(var32, p_147767_3_ + 1 + 0.005, var29, var20, var22);
                }
                if (p_147767_3_ < var5 - 1 && this.blockAccess.isAirBlock(p_147767_2_, p_147767_3_ + 1, p_147767_4_ + 1)) {
                    var6.addVertexWithUV(var31, p_147767_3_ + 1 + 0.005, var29, var20, var23);
                    var6.addVertexWithUV(var31, p_147767_3_ + 1 + 0.005, var30, var20, var24);
                    var6.addVertexWithUV(var32, p_147767_3_ + 1 + 0.005, var30, var21, var24);
                    var6.addVertexWithUV(var32, p_147767_3_ + 1 + 0.005, var29, var21, var23);
                    var6.addVertexWithUV(var31, p_147767_3_ + 1 + 0.005, var30, var20, var23);
                    var6.addVertexWithUV(var31, p_147767_3_ + 1 + 0.005, var29, var20, var24);
                    var6.addVertexWithUV(var32, p_147767_3_ + 1 + 0.005, var29, var21, var24);
                    var6.addVertexWithUV(var32, p_147767_3_ + 1 + 0.005, var30, var21, var23);
                }
            }
            if (var40) {
                var6.addVertexWithUV(var32, p_147767_3_ - 0.005, var30, var21, var24);
                var6.addVertexWithUV(var32, p_147767_3_ - 0.005, var28, var21, var22);
                var6.addVertexWithUV(var31, p_147767_3_ - 0.005, var28, var20, var22);
                var6.addVertexWithUV(var31, p_147767_3_ - 0.005, var30, var20, var24);
                var6.addVertexWithUV(var32, p_147767_3_ - 0.005, var28, var21, var24);
                var6.addVertexWithUV(var32, p_147767_3_ - 0.005, var30, var21, var22);
                var6.addVertexWithUV(var31, p_147767_3_ - 0.005, var30, var20, var22);
                var6.addVertexWithUV(var31, p_147767_3_ - 0.005, var28, var20, var24);
            }
            else {
                if (p_147767_3_ > 1 && this.blockAccess.isAirBlock(p_147767_2_, p_147767_3_ - 1, p_147767_4_ - 1)) {
                    var6.addVertexWithUV(var31, p_147767_3_ - 0.005, var28, var21, var22);
                    var6.addVertexWithUV(var31, p_147767_3_ - 0.005, var29, var21, var23);
                    var6.addVertexWithUV(var32, p_147767_3_ - 0.005, var29, var20, var23);
                    var6.addVertexWithUV(var32, p_147767_3_ - 0.005, var28, var20, var22);
                    var6.addVertexWithUV(var31, p_147767_3_ - 0.005, var29, var21, var22);
                    var6.addVertexWithUV(var31, p_147767_3_ - 0.005, var28, var21, var23);
                    var6.addVertexWithUV(var32, p_147767_3_ - 0.005, var28, var20, var23);
                    var6.addVertexWithUV(var32, p_147767_3_ - 0.005, var29, var20, var22);
                }
                if (p_147767_3_ > 1 && this.blockAccess.isAirBlock(p_147767_2_, p_147767_3_ - 1, p_147767_4_ + 1)) {
                    var6.addVertexWithUV(var31, p_147767_3_ - 0.005, var29, var20, var23);
                    var6.addVertexWithUV(var31, p_147767_3_ - 0.005, var30, var20, var24);
                    var6.addVertexWithUV(var32, p_147767_3_ - 0.005, var30, var21, var24);
                    var6.addVertexWithUV(var32, p_147767_3_ - 0.005, var29, var21, var23);
                    var6.addVertexWithUV(var31, p_147767_3_ - 0.005, var30, var20, var23);
                    var6.addVertexWithUV(var31, p_147767_3_ - 0.005, var29, var20, var24);
                    var6.addVertexWithUV(var32, p_147767_3_ - 0.005, var29, var21, var24);
                    var6.addVertexWithUV(var32, p_147767_3_ - 0.005, var30, var21, var23);
                }
            }
        }
        if (Config.isBetterSnow() && this.hasSnowNeighbours(p_147767_2_, p_147767_3_, p_147767_4_)) {
            this.renderSnow(p_147767_2_, p_147767_3_, p_147767_4_, Blocks.snow_layer.getBlockBoundsMaxY());
        }
        return true;
    }
    
    public boolean renderCrossedSquares(final Block p_147746_1_, final int p_147746_2_, final int p_147746_3_, final int p_147746_4_) {
        final Tessellator var5 = Tessellator.instance;
        var5.setBrightness(p_147746_1_.getBlockBrightness(this.blockAccess, p_147746_2_, p_147746_3_, p_147746_4_));
        final int var6 = CustomColorizer.getColorMultiplier(p_147746_1_, this.blockAccess, p_147746_2_, p_147746_3_, p_147746_4_);
        float var7 = (var6 >> 16 & 0xFF) / 255.0f;
        float var8 = (var6 >> 8 & 0xFF) / 255.0f;
        float var9 = (var6 & 0xFF) / 255.0f;
        if (EntityRenderer.anaglyphEnable) {
            final float var10 = (var7 * 30.0f + var8 * 59.0f + var9 * 11.0f) / 100.0f;
            final float var11 = (var7 * 30.0f + var8 * 70.0f) / 100.0f;
            final float var12 = (var7 * 30.0f + var9 * 70.0f) / 100.0f;
            var7 = var10;
            var8 = var11;
            var9 = var12;
        }
        var5.setColorOpaque_F(var7, var8, var9);
        double var13 = p_147746_2_;
        double var14 = p_147746_3_;
        double var15 = p_147746_4_;
        if (p_147746_1_ == Blocks.tallgrass) {
            long var16 = (long)(p_147746_2_ * 3129871) ^ p_147746_4_ * 116129781L ^ (long)p_147746_3_;
            var16 = var16 * var16 * 42317861L + var16 * 11L;
            var13 += ((var16 >> 16 & 0xFL) / 15.0f - 0.5) * 0.5;
            var14 += ((var16 >> 20 & 0xFL) / 15.0f - 1.0) * 0.2;
            var15 += ((var16 >> 24 & 0xFL) / 15.0f - 0.5) * 0.5;
        }
        else if (p_147746_1_ == Blocks.red_flower || p_147746_1_ == Blocks.yellow_flower) {
            long var16 = (long)(p_147746_2_ * 3129871) ^ p_147746_4_ * 116129781L ^ (long)p_147746_3_;
            var16 = var16 * var16 * 42317861L + var16 * 11L;
            var13 += ((var16 >> 16 & 0xFL) / 15.0f - 0.5) * 0.3;
            var15 += ((var16 >> 24 & 0xFL) / 15.0f - 0.5) * 0.3;
        }
        IIcon var17 = this.getBlockIconFromSideAndMetadata(p_147746_1_, 0, this.blockAccess.getBlockMetadata(p_147746_2_, p_147746_3_, p_147746_4_));
        if (Config.isConnectedTextures() && this.overrideBlockTexture == null) {
            var17 = ConnectedTextures.getConnectedTexture(this.blockAccess, p_147746_1_, p_147746_2_, p_147746_3_, p_147746_4_, 2, var17);
        }
        this.drawCrossedSquares(var17, var13, var14, var15, 1.0f);
        if (Config.isBetterSnow() && this.hasSnowNeighbours(p_147746_2_, p_147746_3_, p_147746_4_)) {
            this.renderSnow(p_147746_2_, p_147746_3_, p_147746_4_, Blocks.snow_layer.getBlockBoundsMaxY());
        }
        return true;
    }
    
    public boolean renderBlockDoublePlant(final BlockDoublePlant p_147774_1_, final int p_147774_2_, final int p_147774_3_, final int p_147774_4_) {
        final Tessellator var5 = Tessellator.instance;
        var5.setBrightness(p_147774_1_.getBlockBrightness(this.blockAccess, p_147774_2_, p_147774_3_, p_147774_4_));
        final int var6 = CustomColorizer.getColorMultiplier(p_147774_1_, this.blockAccess, p_147774_2_, p_147774_3_, p_147774_4_);
        float var7 = (var6 >> 16 & 0xFF) / 255.0f;
        float var8 = (var6 >> 8 & 0xFF) / 255.0f;
        float var9 = (var6 & 0xFF) / 255.0f;
        if (EntityRenderer.anaglyphEnable) {
            final float var10 = (var7 * 30.0f + var8 * 59.0f + var9 * 11.0f) / 100.0f;
            final float var11 = (var7 * 30.0f + var8 * 70.0f) / 100.0f;
            final float var12 = (var7 * 30.0f + var9 * 70.0f) / 100.0f;
            var7 = var10;
            var8 = var11;
            var9 = var12;
        }
        var5.setColorOpaque_F(var7, var8, var9);
        long var13 = (long)(p_147774_2_ * 3129871) ^ p_147774_4_ * 116129781L;
        var13 = var13 * var13 * 42317861L + var13 * 11L;
        double var14 = p_147774_2_;
        final double var15 = p_147774_3_;
        double var16 = p_147774_4_;
        var14 += ((var13 >> 16 & 0xFL) / 15.0f - 0.5) * 0.3;
        var16 += ((var13 >> 24 & 0xFL) / 15.0f - 0.5) * 0.3;
        final int var17 = this.blockAccess.getBlockMetadata(p_147774_2_, p_147774_3_, p_147774_4_);
        final boolean var18 = false;
        final boolean var19 = BlockDoublePlant.func_149887_c(var17);
        int var20;
        if (var19) {
            if (this.blockAccess.getBlock(p_147774_2_, p_147774_3_ - 1, p_147774_4_) != p_147774_1_) {
                return false;
            }
            var20 = BlockDoublePlant.func_149890_d(this.blockAccess.getBlockMetadata(p_147774_2_, p_147774_3_ - 1, p_147774_4_));
        }
        else {
            var20 = BlockDoublePlant.func_149890_d(var17);
        }
        final IIcon var21 = p_147774_1_.func_149888_a(var19, var20);
        this.drawCrossedSquares(var21, var14, var15, var16, 1.0f);
        if (var19 && var20 == 0) {
            final IIcon var22 = p_147774_1_.field_149891_b[0];
            final double var23 = Math.cos(var13 * 0.8) * 3.141592653589793 * 0.1;
            final double var24 = Math.cos(var23);
            final double var25 = Math.sin(var23);
            double var26 = var22.getMinU();
            double var27 = var22.getMinV();
            double var28 = var22.getMaxU();
            double var29 = var22.getMaxV();
            final double var30 = 0.3;
            final double var31 = -0.05;
            final double var32 = 0.5 + 0.3 * var24 - 0.5 * var25;
            final double var33 = 0.5 + 0.5 * var24 + 0.3 * var25;
            final double var34 = 0.5 + 0.3 * var24 + 0.5 * var25;
            final double var35 = 0.5 + -0.5 * var24 + 0.3 * var25;
            final double var36 = 0.5 + -0.05 * var24 + 0.5 * var25;
            final double var37 = 0.5 + -0.5 * var24 + -0.05 * var25;
            final double var38 = 0.5 + -0.05 * var24 - 0.5 * var25;
            final double var39 = 0.5 + 0.5 * var24 + -0.05 * var25;
            var5.addVertexWithUV(var14 + var36, var15 + 1.0, var16 + var37, var26, var29);
            var5.addVertexWithUV(var14 + var38, var15 + 1.0, var16 + var39, var28, var29);
            var5.addVertexWithUV(var14 + var32, var15 + 0.0, var16 + var33, var28, var27);
            var5.addVertexWithUV(var14 + var34, var15 + 0.0, var16 + var35, var26, var27);
            final IIcon var40 = p_147774_1_.field_149891_b[1];
            var26 = var40.getMinU();
            var27 = var40.getMinV();
            var28 = var40.getMaxU();
            var29 = var40.getMaxV();
            var5.addVertexWithUV(var14 + var38, var15 + 1.0, var16 + var39, var26, var29);
            var5.addVertexWithUV(var14 + var36, var15 + 1.0, var16 + var37, var28, var29);
            var5.addVertexWithUV(var14 + var34, var15 + 0.0, var16 + var35, var28, var27);
            var5.addVertexWithUV(var14 + var32, var15 + 0.0, var16 + var33, var26, var27);
        }
        if (Config.isBetterSnow() && this.hasSnowNeighbours(p_147774_2_, p_147774_3_, p_147774_4_)) {
            this.renderSnow(p_147774_2_, p_147774_3_, p_147774_4_, Blocks.snow_layer.getBlockBoundsMaxY());
        }
        return true;
    }
    
    public boolean renderBlockStem(final Block p_147724_1_, final int p_147724_2_, final int p_147724_3_, final int p_147724_4_) {
        final BlockStem var5 = (BlockStem)p_147724_1_;
        final Tessellator var6 = Tessellator.instance;
        var6.setBrightness(var5.getBlockBrightness(this.blockAccess, p_147724_2_, p_147724_3_, p_147724_4_));
        final int var7 = CustomColorizer.getStemColorMultiplier(var5, this.blockAccess, p_147724_2_, p_147724_3_, p_147724_4_);
        float var8 = (var7 >> 16 & 0xFF) / 255.0f;
        float var9 = (var7 >> 8 & 0xFF) / 255.0f;
        float var10 = (var7 & 0xFF) / 255.0f;
        if (EntityRenderer.anaglyphEnable) {
            final float var11 = (var8 * 30.0f + var9 * 59.0f + var10 * 11.0f) / 100.0f;
            final float var12 = (var8 * 30.0f + var9 * 70.0f) / 100.0f;
            final float var13 = (var8 * 30.0f + var10 * 70.0f) / 100.0f;
            var8 = var11;
            var9 = var12;
            var10 = var13;
        }
        var6.setColorOpaque_F(var8, var9, var10);
        var5.setBlockBoundsBasedOnState(this.blockAccess, p_147724_2_, p_147724_3_, p_147724_4_);
        final int var14 = var5.func_149873_e(this.blockAccess, p_147724_2_, p_147724_3_, p_147724_4_);
        if (var14 < 0) {
            this.renderBlockStemSmall(var5, this.blockAccess.getBlockMetadata(p_147724_2_, p_147724_3_, p_147724_4_), this.renderMaxY, p_147724_2_, p_147724_3_ - 0.0625f, p_147724_4_);
        }
        else {
            this.renderBlockStemSmall(var5, this.blockAccess.getBlockMetadata(p_147724_2_, p_147724_3_, p_147724_4_), 0.5, p_147724_2_, p_147724_3_ - 0.0625f, p_147724_4_);
            this.renderBlockStemBig(var5, this.blockAccess.getBlockMetadata(p_147724_2_, p_147724_3_, p_147724_4_), var14, this.renderMaxY, p_147724_2_, p_147724_3_ - 0.0625f, p_147724_4_);
        }
        return true;
    }
    
    public boolean renderBlockCrops(final Block p_147796_1_, final int p_147796_2_, final int p_147796_3_, final int p_147796_4_) {
        final Tessellator var5 = Tessellator.instance;
        var5.setBrightness(p_147796_1_.getBlockBrightness(this.blockAccess, p_147796_2_, p_147796_3_, p_147796_4_));
        var5.setColorOpaque_F(1.0f, 1.0f, 1.0f);
        this.renderBlockCropsImpl(p_147796_1_, this.blockAccess.getBlockMetadata(p_147796_2_, p_147796_3_, p_147796_4_), p_147796_2_, p_147796_3_ - 0.0625f, p_147796_4_);
        return true;
    }
    
    public void renderTorchAtAngle(final Block p_147747_1_, double p_147747_2_, final double p_147747_4_, double p_147747_6_, final double p_147747_8_, final double p_147747_10_, final int p_147747_12_) {
        final Tessellator var13 = Tessellator.instance;
        IIcon var14 = this.getBlockIconFromSideAndMetadata(p_147747_1_, 0, p_147747_12_);
        if (this.hasOverrideBlockTexture()) {
            var14 = this.overrideBlockTexture;
        }
        final double var15 = var14.getMinU();
        final double var16 = var14.getMinV();
        final double var17 = var14.getMaxU();
        final double var18 = var14.getMaxV();
        final double var19 = var14.getInterpolatedU(7.0);
        final double var20 = var14.getInterpolatedV(6.0);
        final double var21 = var14.getInterpolatedU(9.0);
        final double var22 = var14.getInterpolatedV(8.0);
        final double var23 = var14.getInterpolatedU(7.0);
        final double var24 = var14.getInterpolatedV(13.0);
        final double var25 = var14.getInterpolatedU(9.0);
        final double var26 = var14.getInterpolatedV(15.0);
        p_147747_2_ += 0.5;
        p_147747_6_ += 0.5;
        final double var27 = p_147747_2_ - 0.5;
        final double var28 = p_147747_2_ + 0.5;
        final double var29 = p_147747_6_ - 0.5;
        final double var30 = p_147747_6_ + 0.5;
        final double var31 = 0.0625;
        final double var32 = 0.625;
        var13.addVertexWithUV(p_147747_2_ + p_147747_8_ * (1.0 - var32) - var31, p_147747_4_ + var32, p_147747_6_ + p_147747_10_ * (1.0 - var32) - var31, var19, var20);
        var13.addVertexWithUV(p_147747_2_ + p_147747_8_ * (1.0 - var32) - var31, p_147747_4_ + var32, p_147747_6_ + p_147747_10_ * (1.0 - var32) + var31, var19, var22);
        var13.addVertexWithUV(p_147747_2_ + p_147747_8_ * (1.0 - var32) + var31, p_147747_4_ + var32, p_147747_6_ + p_147747_10_ * (1.0 - var32) + var31, var21, var22);
        var13.addVertexWithUV(p_147747_2_ + p_147747_8_ * (1.0 - var32) + var31, p_147747_4_ + var32, p_147747_6_ + p_147747_10_ * (1.0 - var32) - var31, var21, var20);
        var13.addVertexWithUV(p_147747_2_ + var31 + p_147747_8_, p_147747_4_, p_147747_6_ - var31 + p_147747_10_, var25, var24);
        var13.addVertexWithUV(p_147747_2_ + var31 + p_147747_8_, p_147747_4_, p_147747_6_ + var31 + p_147747_10_, var25, var26);
        var13.addVertexWithUV(p_147747_2_ - var31 + p_147747_8_, p_147747_4_, p_147747_6_ + var31 + p_147747_10_, var23, var26);
        var13.addVertexWithUV(p_147747_2_ - var31 + p_147747_8_, p_147747_4_, p_147747_6_ - var31 + p_147747_10_, var23, var24);
        var13.addVertexWithUV(p_147747_2_ - var31, p_147747_4_ + 1.0, var29, var15, var16);
        var13.addVertexWithUV(p_147747_2_ - var31 + p_147747_8_, p_147747_4_ + 0.0, var29 + p_147747_10_, var15, var18);
        var13.addVertexWithUV(p_147747_2_ - var31 + p_147747_8_, p_147747_4_ + 0.0, var30 + p_147747_10_, var17, var18);
        var13.addVertexWithUV(p_147747_2_ - var31, p_147747_4_ + 1.0, var30, var17, var16);
        var13.addVertexWithUV(p_147747_2_ + var31, p_147747_4_ + 1.0, var30, var15, var16);
        var13.addVertexWithUV(p_147747_2_ + p_147747_8_ + var31, p_147747_4_ + 0.0, var30 + p_147747_10_, var15, var18);
        var13.addVertexWithUV(p_147747_2_ + p_147747_8_ + var31, p_147747_4_ + 0.0, var29 + p_147747_10_, var17, var18);
        var13.addVertexWithUV(p_147747_2_ + var31, p_147747_4_ + 1.0, var29, var17, var16);
        var13.addVertexWithUV(var27, p_147747_4_ + 1.0, p_147747_6_ + var31, var15, var16);
        var13.addVertexWithUV(var27 + p_147747_8_, p_147747_4_ + 0.0, p_147747_6_ + var31 + p_147747_10_, var15, var18);
        var13.addVertexWithUV(var28 + p_147747_8_, p_147747_4_ + 0.0, p_147747_6_ + var31 + p_147747_10_, var17, var18);
        var13.addVertexWithUV(var28, p_147747_4_ + 1.0, p_147747_6_ + var31, var17, var16);
        var13.addVertexWithUV(var28, p_147747_4_ + 1.0, p_147747_6_ - var31, var15, var16);
        var13.addVertexWithUV(var28 + p_147747_8_, p_147747_4_ + 0.0, p_147747_6_ - var31 + p_147747_10_, var15, var18);
        var13.addVertexWithUV(var27 + p_147747_8_, p_147747_4_ + 0.0, p_147747_6_ - var31 + p_147747_10_, var17, var18);
        var13.addVertexWithUV(var27, p_147747_4_ + 1.0, p_147747_6_ - var31, var17, var16);
    }
    
    public void drawCrossedSquares(IIcon p_147765_1_, final double p_147765_2_, final double p_147765_4_, final double p_147765_6_, final float p_147765_8_) {
        final Tessellator var9 = Tessellator.instance;
        if (this.hasOverrideBlockTexture()) {
            p_147765_1_ = this.overrideBlockTexture;
        }
        final double var10 = p_147765_1_.getMinU();
        final double var11 = p_147765_1_.getMinV();
        final double var12 = p_147765_1_.getMaxU();
        final double var13 = p_147765_1_.getMaxV();
        final double var14 = 0.45 * p_147765_8_;
        final double var15 = p_147765_2_ + 0.5 - var14;
        final double var16 = p_147765_2_ + 0.5 + var14;
        final double var17 = p_147765_6_ + 0.5 - var14;
        final double var18 = p_147765_6_ + 0.5 + var14;
        var9.addVertexWithUV(var15, p_147765_4_ + p_147765_8_, var17, var10, var11);
        var9.addVertexWithUV(var15, p_147765_4_ + 0.0, var17, var10, var13);
        var9.addVertexWithUV(var16, p_147765_4_ + 0.0, var18, var12, var13);
        var9.addVertexWithUV(var16, p_147765_4_ + p_147765_8_, var18, var12, var11);
        var9.addVertexWithUV(var16, p_147765_4_ + p_147765_8_, var18, var10, var11);
        var9.addVertexWithUV(var16, p_147765_4_ + 0.0, var18, var10, var13);
        var9.addVertexWithUV(var15, p_147765_4_ + 0.0, var17, var12, var13);
        var9.addVertexWithUV(var15, p_147765_4_ + p_147765_8_, var17, var12, var11);
        var9.addVertexWithUV(var15, p_147765_4_ + p_147765_8_, var18, var10, var11);
        var9.addVertexWithUV(var15, p_147765_4_ + 0.0, var18, var10, var13);
        var9.addVertexWithUV(var16, p_147765_4_ + 0.0, var17, var12, var13);
        var9.addVertexWithUV(var16, p_147765_4_ + p_147765_8_, var17, var12, var11);
        var9.addVertexWithUV(var16, p_147765_4_ + p_147765_8_, var17, var10, var11);
        var9.addVertexWithUV(var16, p_147765_4_ + 0.0, var17, var10, var13);
        var9.addVertexWithUV(var15, p_147765_4_ + 0.0, var18, var12, var13);
        var9.addVertexWithUV(var15, p_147765_4_ + p_147765_8_, var18, var12, var11);
    }
    
    public void renderBlockStemSmall(final Block p_147730_1_, final int p_147730_2_, final double p_147730_3_, final double p_147730_5_, final double p_147730_7_, final double p_147730_9_) {
        final Tessellator var11 = Tessellator.instance;
        IIcon var12 = this.getBlockIconFromSideAndMetadata(p_147730_1_, 0, p_147730_2_);
        if (this.hasOverrideBlockTexture()) {
            var12 = this.overrideBlockTexture;
        }
        final double var13 = var12.getMinU();
        final double var14 = var12.getMinV();
        final double var15 = var12.getMaxU();
        final double var16 = var12.getInterpolatedV(p_147730_3_ * 16.0);
        final double var17 = p_147730_5_ + 0.5 - 0.44999998807907104;
        final double var18 = p_147730_5_ + 0.5 + 0.44999998807907104;
        final double var19 = p_147730_9_ + 0.5 - 0.44999998807907104;
        final double var20 = p_147730_9_ + 0.5 + 0.44999998807907104;
        var11.addVertexWithUV(var17, p_147730_7_ + p_147730_3_, var19, var13, var14);
        var11.addVertexWithUV(var17, p_147730_7_ + 0.0, var19, var13, var16);
        var11.addVertexWithUV(var18, p_147730_7_ + 0.0, var20, var15, var16);
        var11.addVertexWithUV(var18, p_147730_7_ + p_147730_3_, var20, var15, var14);
        var11.addVertexWithUV(var18, p_147730_7_ + p_147730_3_, var20, var15, var14);
        var11.addVertexWithUV(var18, p_147730_7_ + 0.0, var20, var15, var16);
        var11.addVertexWithUV(var17, p_147730_7_ + 0.0, var19, var13, var16);
        var11.addVertexWithUV(var17, p_147730_7_ + p_147730_3_, var19, var13, var14);
        var11.addVertexWithUV(var17, p_147730_7_ + p_147730_3_, var20, var13, var14);
        var11.addVertexWithUV(var17, p_147730_7_ + 0.0, var20, var13, var16);
        var11.addVertexWithUV(var18, p_147730_7_ + 0.0, var19, var15, var16);
        var11.addVertexWithUV(var18, p_147730_7_ + p_147730_3_, var19, var15, var14);
        var11.addVertexWithUV(var18, p_147730_7_ + p_147730_3_, var19, var15, var14);
        var11.addVertexWithUV(var18, p_147730_7_ + 0.0, var19, var15, var16);
        var11.addVertexWithUV(var17, p_147730_7_ + 0.0, var20, var13, var16);
        var11.addVertexWithUV(var17, p_147730_7_ + p_147730_3_, var20, var13, var14);
    }
    
    public boolean renderBlockLilyPad(final Block p_147783_1_, final int p_147783_2_, final int p_147783_3_, final int p_147783_4_) {
        final Tessellator var5 = Tessellator.instance;
        IIcon var6 = this.getBlockIconFromSide(p_147783_1_, 1);
        if (this.hasOverrideBlockTexture()) {
            var6 = this.overrideBlockTexture;
        }
        if (Config.isConnectedTextures() && this.overrideBlockTexture == null) {
            var6 = ConnectedTextures.getConnectedTexture(this.blockAccess, p_147783_1_, p_147783_2_, p_147783_3_, p_147783_4_, 1, var6);
        }
        final float var7 = 0.015625f;
        final double var8 = var6.getMinU();
        final double var9 = var6.getMinV();
        final double var10 = var6.getMaxU();
        final double var11 = var6.getMaxV();
        long var12 = (long)(p_147783_2_ * 3129871) ^ p_147783_4_ * 116129781L ^ (long)p_147783_3_;
        var12 = var12 * var12 * 42317861L + var12 * 11L;
        final int var13 = (int)(var12 >> 16 & 0x3L);
        var5.setBrightness(p_147783_1_.getBlockBrightness(this.blockAccess, p_147783_2_, p_147783_3_, p_147783_4_));
        final float var14 = p_147783_2_ + 0.5f;
        final float var15 = p_147783_4_ + 0.5f;
        final float var16 = (var13 & 0x1) * 0.5f * (1 - var13 / 2 % 2 * 2);
        final float var17 = (var13 + 1 & 0x1) * 0.5f * (1 - (var13 + 1) / 2 % 2 * 2);
        final int col = CustomColorizer.getLilypadColor();
        var5.setColorOpaque_I(col);
        var5.addVertexWithUV(var14 + var16 - var17, p_147783_3_ + var7, var15 + var16 + var17, var8, var9);
        var5.addVertexWithUV(var14 + var16 + var17, p_147783_3_ + var7, var15 - var16 + var17, var10, var9);
        var5.addVertexWithUV(var14 - var16 + var17, p_147783_3_ + var7, var15 - var16 - var17, var10, var11);
        var5.addVertexWithUV(var14 - var16 - var17, p_147783_3_ + var7, var15 + var16 - var17, var8, var11);
        var5.setColorOpaque_I((col & 0xFEFEFE) >> 1);
        var5.addVertexWithUV(var14 - var16 - var17, p_147783_3_ + var7, var15 + var16 - var17, var8, var11);
        var5.addVertexWithUV(var14 - var16 + var17, p_147783_3_ + var7, var15 - var16 - var17, var10, var11);
        var5.addVertexWithUV(var14 + var16 + var17, p_147783_3_ + var7, var15 - var16 + var17, var10, var9);
        var5.addVertexWithUV(var14 + var16 - var17, p_147783_3_ + var7, var15 + var16 + var17, var8, var9);
        return true;
    }
    
    public void renderBlockStemBig(final BlockStem p_147740_1_, final int p_147740_2_, final int p_147740_3_, final double p_147740_4_, final double p_147740_6_, final double p_147740_8_, final double p_147740_10_) {
        final Tessellator var12 = Tessellator.instance;
        IIcon var13 = p_147740_1_.func_149872_i();
        if (this.hasOverrideBlockTexture()) {
            var13 = this.overrideBlockTexture;
        }
        double var14 = var13.getMinU();
        final double var15 = var13.getMinV();
        double var16 = var13.getMaxU();
        final double var17 = var13.getMaxV();
        final double var18 = p_147740_6_ + 0.5 - 0.5;
        final double var19 = p_147740_6_ + 0.5 + 0.5;
        final double var20 = p_147740_10_ + 0.5 - 0.5;
        final double var21 = p_147740_10_ + 0.5 + 0.5;
        final double var22 = p_147740_6_ + 0.5;
        final double var23 = p_147740_10_ + 0.5;
        if ((p_147740_3_ + 1) / 2 % 2 == 1) {
            final double var24 = var16;
            var16 = var14;
            var14 = var24;
        }
        if (p_147740_3_ < 2) {
            var12.addVertexWithUV(var18, p_147740_8_ + p_147740_4_, var23, var14, var15);
            var12.addVertexWithUV(var18, p_147740_8_ + 0.0, var23, var14, var17);
            var12.addVertexWithUV(var19, p_147740_8_ + 0.0, var23, var16, var17);
            var12.addVertexWithUV(var19, p_147740_8_ + p_147740_4_, var23, var16, var15);
            var12.addVertexWithUV(var19, p_147740_8_ + p_147740_4_, var23, var16, var15);
            var12.addVertexWithUV(var19, p_147740_8_ + 0.0, var23, var16, var17);
            var12.addVertexWithUV(var18, p_147740_8_ + 0.0, var23, var14, var17);
            var12.addVertexWithUV(var18, p_147740_8_ + p_147740_4_, var23, var14, var15);
        }
        else {
            var12.addVertexWithUV(var22, p_147740_8_ + p_147740_4_, var21, var14, var15);
            var12.addVertexWithUV(var22, p_147740_8_ + 0.0, var21, var14, var17);
            var12.addVertexWithUV(var22, p_147740_8_ + 0.0, var20, var16, var17);
            var12.addVertexWithUV(var22, p_147740_8_ + p_147740_4_, var20, var16, var15);
            var12.addVertexWithUV(var22, p_147740_8_ + p_147740_4_, var20, var16, var15);
            var12.addVertexWithUV(var22, p_147740_8_ + 0.0, var20, var16, var17);
            var12.addVertexWithUV(var22, p_147740_8_ + 0.0, var21, var14, var17);
            var12.addVertexWithUV(var22, p_147740_8_ + p_147740_4_, var21, var14, var15);
        }
    }
    
    public void renderBlockCropsImpl(final Block p_147795_1_, final int p_147795_2_, final double p_147795_3_, final double p_147795_5_, final double p_147795_7_) {
        final Tessellator var9 = Tessellator.instance;
        IIcon var10 = this.getBlockIconFromSideAndMetadata(p_147795_1_, 0, p_147795_2_);
        if (this.hasOverrideBlockTexture()) {
            var10 = this.overrideBlockTexture;
        }
        final double var11 = var10.getMinU();
        final double var12 = var10.getMinV();
        final double var13 = var10.getMaxU();
        final double var14 = var10.getMaxV();
        double var15 = p_147795_3_ + 0.5 - 0.25;
        double var16 = p_147795_3_ + 0.5 + 0.25;
        double var17 = p_147795_7_ + 0.5 - 0.5;
        double var18 = p_147795_7_ + 0.5 + 0.5;
        var9.addVertexWithUV(var15, p_147795_5_ + 1.0, var17, var11, var12);
        var9.addVertexWithUV(var15, p_147795_5_ + 0.0, var17, var11, var14);
        var9.addVertexWithUV(var15, p_147795_5_ + 0.0, var18, var13, var14);
        var9.addVertexWithUV(var15, p_147795_5_ + 1.0, var18, var13, var12);
        var9.addVertexWithUV(var15, p_147795_5_ + 1.0, var18, var11, var12);
        var9.addVertexWithUV(var15, p_147795_5_ + 0.0, var18, var11, var14);
        var9.addVertexWithUV(var15, p_147795_5_ + 0.0, var17, var13, var14);
        var9.addVertexWithUV(var15, p_147795_5_ + 1.0, var17, var13, var12);
        var9.addVertexWithUV(var16, p_147795_5_ + 1.0, var18, var11, var12);
        var9.addVertexWithUV(var16, p_147795_5_ + 0.0, var18, var11, var14);
        var9.addVertexWithUV(var16, p_147795_5_ + 0.0, var17, var13, var14);
        var9.addVertexWithUV(var16, p_147795_5_ + 1.0, var17, var13, var12);
        var9.addVertexWithUV(var16, p_147795_5_ + 1.0, var17, var11, var12);
        var9.addVertexWithUV(var16, p_147795_5_ + 0.0, var17, var11, var14);
        var9.addVertexWithUV(var16, p_147795_5_ + 0.0, var18, var13, var14);
        var9.addVertexWithUV(var16, p_147795_5_ + 1.0, var18, var13, var12);
        var15 = p_147795_3_ + 0.5 - 0.5;
        var16 = p_147795_3_ + 0.5 + 0.5;
        var17 = p_147795_7_ + 0.5 - 0.25;
        var18 = p_147795_7_ + 0.5 + 0.25;
        var9.addVertexWithUV(var15, p_147795_5_ + 1.0, var17, var11, var12);
        var9.addVertexWithUV(var15, p_147795_5_ + 0.0, var17, var11, var14);
        var9.addVertexWithUV(var16, p_147795_5_ + 0.0, var17, var13, var14);
        var9.addVertexWithUV(var16, p_147795_5_ + 1.0, var17, var13, var12);
        var9.addVertexWithUV(var16, p_147795_5_ + 1.0, var17, var11, var12);
        var9.addVertexWithUV(var16, p_147795_5_ + 0.0, var17, var11, var14);
        var9.addVertexWithUV(var15, p_147795_5_ + 0.0, var17, var13, var14);
        var9.addVertexWithUV(var15, p_147795_5_ + 1.0, var17, var13, var12);
        var9.addVertexWithUV(var16, p_147795_5_ + 1.0, var18, var11, var12);
        var9.addVertexWithUV(var16, p_147795_5_ + 0.0, var18, var11, var14);
        var9.addVertexWithUV(var15, p_147795_5_ + 0.0, var18, var13, var14);
        var9.addVertexWithUV(var15, p_147795_5_ + 1.0, var18, var13, var12);
        var9.addVertexWithUV(var15, p_147795_5_ + 1.0, var18, var11, var12);
        var9.addVertexWithUV(var15, p_147795_5_ + 0.0, var18, var11, var14);
        var9.addVertexWithUV(var16, p_147795_5_ + 0.0, var18, var13, var14);
        var9.addVertexWithUV(var16, p_147795_5_ + 1.0, var18, var13, var12);
    }
    
    public boolean renderBlockFluids(final Block p_147721_1_, final int p_147721_2_, final int p_147721_3_, final int p_147721_4_) {
        final Tessellator var5 = Tessellator.instance;
        final int var6 = CustomColorizer.getFluidColor(p_147721_1_, this.blockAccess, p_147721_2_, p_147721_3_, p_147721_4_);
        final float var7 = (var6 >> 16 & 0xFF) / 255.0f;
        final float var8 = (var6 >> 8 & 0xFF) / 255.0f;
        final float var9 = (var6 & 0xFF) / 255.0f;
        final boolean var10 = p_147721_1_.shouldSideBeRendered(this.blockAccess, p_147721_2_, p_147721_3_ + 1, p_147721_4_, 1);
        final boolean var11 = p_147721_1_.shouldSideBeRendered(this.blockAccess, p_147721_2_, p_147721_3_ - 1, p_147721_4_, 0);
        final boolean[] var12 = { p_147721_1_.shouldSideBeRendered(this.blockAccess, p_147721_2_, p_147721_3_, p_147721_4_ - 1, 2), p_147721_1_.shouldSideBeRendered(this.blockAccess, p_147721_2_, p_147721_3_, p_147721_4_ + 1, 3), p_147721_1_.shouldSideBeRendered(this.blockAccess, p_147721_2_ - 1, p_147721_3_, p_147721_4_, 4), p_147721_1_.shouldSideBeRendered(this.blockAccess, p_147721_2_ + 1, p_147721_3_, p_147721_4_, 5) };
        if (!var10 && !var11 && !var12[0] && !var12[1] && !var12[2] && !var12[3]) {
            return false;
        }
        boolean var13 = false;
        final float var14 = 0.5f;
        final float var15 = 1.0f;
        final float var16 = 0.8f;
        final float var17 = 0.6f;
        final double var18 = 0.0;
        final double var19 = 1.0;
        final Material var20 = p_147721_1_.getMaterial();
        final int var21 = this.blockAccess.getBlockMetadata(p_147721_2_, p_147721_3_, p_147721_4_);
        double var22 = this.getFluidHeight(p_147721_2_, p_147721_3_, p_147721_4_, var20);
        double var23 = this.getFluidHeight(p_147721_2_, p_147721_3_, p_147721_4_ + 1, var20);
        double var24 = this.getFluidHeight(p_147721_2_ + 1, p_147721_3_, p_147721_4_ + 1, var20);
        double var25 = this.getFluidHeight(p_147721_2_ + 1, p_147721_3_, p_147721_4_, var20);
        final double var26 = 0.0010000000474974513;
        if (this.renderAllFaces || var10) {
            var13 = true;
            IIcon var27 = this.getBlockIconFromSideAndMetadata(p_147721_1_, 1, var21);
            final float var28 = (float)BlockLiquid.func_149802_a(this.blockAccess, p_147721_2_, p_147721_3_, p_147721_4_, var20);
            if (var28 > -999.0f) {
                var27 = this.getBlockIconFromSideAndMetadata(p_147721_1_, 2, var21);
            }
            var22 -= var26;
            var23 -= var26;
            var24 -= var26;
            var25 -= var26;
            double var29;
            double var30;
            double var31;
            double var32;
            double var33;
            double var34;
            double var35;
            double var36;
            if (var28 < -999.0f) {
                var29 = var27.getInterpolatedU(0.0);
                var30 = var27.getInterpolatedV(0.0);
                var31 = var29;
                var32 = var27.getInterpolatedV(16.0);
                var33 = var27.getInterpolatedU(16.0);
                var34 = var32;
                var35 = var33;
                var36 = var30;
            }
            else {
                final float var37 = MathHelper.sin(var28) * 0.25f;
                final float var38 = MathHelper.cos(var28) * 0.25f;
                final float var39 = 8.0f;
                var29 = var27.getInterpolatedU(8.0f + (-var38 - var37) * 16.0f);
                var30 = var27.getInterpolatedV(8.0f + (-var38 + var37) * 16.0f);
                var31 = var27.getInterpolatedU(8.0f + (-var38 + var37) * 16.0f);
                var32 = var27.getInterpolatedV(8.0f + (var38 + var37) * 16.0f);
                var33 = var27.getInterpolatedU(8.0f + (var38 + var37) * 16.0f);
                var34 = var27.getInterpolatedV(8.0f + (var38 - var37) * 16.0f);
                var35 = var27.getInterpolatedU(8.0f + (var38 - var37) * 16.0f);
                var36 = var27.getInterpolatedV(8.0f + (-var38 - var37) * 16.0f);
            }
            var5.setBrightness(p_147721_1_.getBlockBrightness(this.blockAccess, p_147721_2_, p_147721_3_, p_147721_4_));
            var5.setColorOpaque_F(var15 * var7, var15 * var8, var15 * var9);
            final double var40 = 3.90625E-5;
            var5.addVertexWithUV(p_147721_2_ + 0, p_147721_3_ + var22, p_147721_4_ + 0, var29 + var40, var30 + var40);
            var5.addVertexWithUV(p_147721_2_ + 0, p_147721_3_ + var23, p_147721_4_ + 1, var31 + var40, var32 - var40);
            var5.addVertexWithUV(p_147721_2_ + 1, p_147721_3_ + var24, p_147721_4_ + 1, var33 - var40, var34 - var40);
            var5.addVertexWithUV(p_147721_2_ + 1, p_147721_3_ + var25, p_147721_4_ + 0, var35 - var40, var36 + var40);
            var5.addVertexWithUV(p_147721_2_ + 0, p_147721_3_ + var22, p_147721_4_ + 0, var29 + var40, var30 + var40);
            var5.addVertexWithUV(p_147721_2_ + 1, p_147721_3_ + var25, p_147721_4_ + 0, var35 - var40, var36 + var40);
            var5.addVertexWithUV(p_147721_2_ + 1, p_147721_3_ + var24, p_147721_4_ + 1, var33 - var40, var34 - var40);
            var5.addVertexWithUV(p_147721_2_ + 0, p_147721_3_ + var23, p_147721_4_ + 1, var31 + var40, var32 - var40);
        }
        if (this.renderAllFaces || var11) {
            var5.setBrightness(p_147721_1_.getBlockBrightness(this.blockAccess, p_147721_2_, p_147721_3_ - 1, p_147721_4_));
            var5.setColorOpaque_F(var14 * var7, var14 * var8, var14 * var9);
            this.renderFaceYNeg(p_147721_1_, p_147721_2_, p_147721_3_ + var26, p_147721_4_, this.getBlockIconFromSide(p_147721_1_, 0));
            var13 = true;
        }
        for (int var41 = 0; var41 < 4; ++var41) {
            int var42 = p_147721_2_;
            int var43 = p_147721_4_;
            if (var41 == 0) {
                var43 = p_147721_4_ - 1;
            }
            if (var41 == 1) {
                ++var43;
            }
            if (var41 == 2) {
                var42 = p_147721_2_ - 1;
            }
            if (var41 == 3) {
                ++var42;
            }
            final IIcon var44 = this.getBlockIconFromSideAndMetadata(p_147721_1_, var41 + 2, var21);
            if (this.renderAllFaces || var12[var41]) {
                double var30;
                double var31;
                double var32;
                double var33;
                double var34;
                double var35;
                if (var41 == 0) {
                    var31 = var22;
                    var33 = var25;
                    var35 = p_147721_2_;
                    var32 = p_147721_2_ + 1;
                    var30 = p_147721_4_ + var26;
                    var34 = p_147721_4_ + var26;
                }
                else if (var41 == 1) {
                    var31 = var24;
                    var33 = var23;
                    var35 = p_147721_2_ + 1;
                    var32 = p_147721_2_;
                    var30 = p_147721_4_ + 1 - var26;
                    var34 = p_147721_4_ + 1 - var26;
                }
                else if (var41 == 2) {
                    var31 = var23;
                    var33 = var22;
                    var35 = p_147721_2_ + var26;
                    var32 = p_147721_2_ + var26;
                    var30 = p_147721_4_ + 1;
                    var34 = p_147721_4_;
                }
                else {
                    var31 = var25;
                    var33 = var24;
                    var35 = p_147721_2_ + 1 - var26;
                    var32 = p_147721_2_ + 1 - var26;
                    var30 = p_147721_4_;
                    var34 = p_147721_4_ + 1;
                }
                var13 = true;
                final float var45 = var44.getInterpolatedU(0.0);
                final float var37 = var44.getInterpolatedU(8.0);
                final float var38 = var44.getInterpolatedV((1.0 - var31) * 16.0 * 0.5);
                final float var39 = var44.getInterpolatedV((1.0 - var33) * 16.0 * 0.5);
                final float var46 = var44.getInterpolatedV(8.0);
                var5.setBrightness(p_147721_1_.getBlockBrightness(this.blockAccess, var42, p_147721_3_, var43));
                float var47 = 1.0f;
                var47 *= ((var41 < 2) ? var16 : var17);
                var5.setColorOpaque_F(var15 * var47 * var7, var15 * var47 * var8, var15 * var47 * var9);
                var5.addVertexWithUV(var35, p_147721_3_ + var31, var30, var45, var38);
                var5.addVertexWithUV(var32, p_147721_3_ + var33, var34, var37, var39);
                var5.addVertexWithUV(var32, p_147721_3_ + 0, var34, var37, var46);
                var5.addVertexWithUV(var35, p_147721_3_ + 0, var30, var45, var46);
                var5.addVertexWithUV(var35, p_147721_3_ + 0, var30, var45, var46);
                var5.addVertexWithUV(var32, p_147721_3_ + 0, var34, var37, var46);
                var5.addVertexWithUV(var32, p_147721_3_ + var33, var34, var37, var39);
                var5.addVertexWithUV(var35, p_147721_3_ + var31, var30, var45, var38);
            }
        }
        this.renderMinY = var18;
        this.renderMaxY = var19;
        return var13;
    }
    
    public float getFluidHeight(final int p_147729_1_, final int p_147729_2_, final int p_147729_3_, final Material p_147729_4_) {
        int var5 = 0;
        float var6 = 0.0f;
        for (int var7 = 0; var7 < 4; ++var7) {
            final int var8 = p_147729_1_ - (var7 & 0x1);
            final int var9 = p_147729_3_ - (var7 >> 1 & 0x1);
            if (this.blockAccess.getBlock(var8, p_147729_2_ + 1, var9).getMaterial() == p_147729_4_) {
                return 1.0f;
            }
            final Material var10 = this.blockAccess.getBlock(var8, p_147729_2_, var9).getMaterial();
            if (var10 == p_147729_4_) {
                final int var11 = this.blockAccess.getBlockMetadata(var8, p_147729_2_, var9);
                if (var11 >= 8 || var11 == 0) {
                    var6 += BlockLiquid.func_149801_b(var11) * 10.0f;
                    var5 += 10;
                }
                var6 += BlockLiquid.func_149801_b(var11);
                ++var5;
            }
            else if (!var10.isSolid()) {
                ++var6;
                ++var5;
            }
        }
        return 1.0f - var6 / var5;
    }
    
    public void renderBlockSandFalling(final Block p_147749_1_, final World p_147749_2_, final int p_147749_3_, final int p_147749_4_, final int p_147749_5_, final int p_147749_6_) {
        final float var7 = 0.5f;
        final float var8 = 1.0f;
        final float var9 = 0.8f;
        final float var10 = 0.6f;
        final Tessellator var11 = Tessellator.instance;
        var11.startDrawingQuads();
        var11.setBrightness(p_147749_1_.getBlockBrightness(p_147749_2_, p_147749_3_, p_147749_4_, p_147749_5_));
        var11.setColorOpaque_F(var7, var7, var7);
        this.renderFaceYNeg(p_147749_1_, -0.5, -0.5, -0.5, this.getBlockIconFromSideAndMetadata(p_147749_1_, 0, p_147749_6_));
        var11.setColorOpaque_F(var8, var8, var8);
        this.renderFaceYPos(p_147749_1_, -0.5, -0.5, -0.5, this.getBlockIconFromSideAndMetadata(p_147749_1_, 1, p_147749_6_));
        var11.setColorOpaque_F(var9, var9, var9);
        this.renderFaceZNeg(p_147749_1_, -0.5, -0.5, -0.5, this.getBlockIconFromSideAndMetadata(p_147749_1_, 2, p_147749_6_));
        var11.setColorOpaque_F(var9, var9, var9);
        this.renderFaceZPos(p_147749_1_, -0.5, -0.5, -0.5, this.getBlockIconFromSideAndMetadata(p_147749_1_, 3, p_147749_6_));
        var11.setColorOpaque_F(var10, var10, var10);
        this.renderFaceXNeg(p_147749_1_, -0.5, -0.5, -0.5, this.getBlockIconFromSideAndMetadata(p_147749_1_, 4, p_147749_6_));
        var11.setColorOpaque_F(var10, var10, var10);
        this.renderFaceXPos(p_147749_1_, -0.5, -0.5, -0.5, this.getBlockIconFromSideAndMetadata(p_147749_1_, 5, p_147749_6_));
        var11.draw();
    }
    
    public boolean renderStandardBlock(final Block p_147784_1_, final int p_147784_2_, final int p_147784_3_, final int p_147784_4_) {
        final int var5 = CustomColorizer.getColorMultiplier(p_147784_1_, this.blockAccess, p_147784_2_, p_147784_3_, p_147784_4_);
        float var6 = (var5 >> 16 & 0xFF) / 255.0f;
        float var7 = (var5 >> 8 & 0xFF) / 255.0f;
        float var8 = (var5 & 0xFF) / 255.0f;
        if (EntityRenderer.anaglyphEnable) {
            final float var9 = (var6 * 30.0f + var7 * 59.0f + var8 * 11.0f) / 100.0f;
            final float var10 = (var6 * 30.0f + var7 * 70.0f) / 100.0f;
            final float var11 = (var6 * 30.0f + var8 * 70.0f) / 100.0f;
            var6 = var9;
            var7 = var10;
            var8 = var11;
        }
        return (Minecraft.isAmbientOcclusionEnabled() && p_147784_1_.getLightValue() == 0) ? (this.partialRenderBounds ? this.renderStandardBlockWithAmbientOcclusionPartial(p_147784_1_, p_147784_2_, p_147784_3_, p_147784_4_, var6, var7, var8) : this.renderStandardBlockWithAmbientOcclusion(p_147784_1_, p_147784_2_, p_147784_3_, p_147784_4_, var6, var7, var8)) : this.renderStandardBlockWithColorMultiplier(p_147784_1_, p_147784_2_, p_147784_3_, p_147784_4_, var6, var7, var8);
    }
    
    public boolean renderBlockLog(final Block p_147742_1_, final int p_147742_2_, final int p_147742_3_, final int p_147742_4_) {
        final int var5 = this.blockAccess.getBlockMetadata(p_147742_2_, p_147742_3_, p_147742_4_);
        final int var6 = var5 & 0xC;
        if (var6 == 4) {
            this.uvRotateEast = 2;
            this.uvRotateWest = 1;
            this.uvRotateTop = 1;
            this.uvRotateBottom = 2;
        }
        else if (var6 == 8) {
            this.uvRotateSouth = 2;
            this.uvRotateNorth = 1;
            this.uvRotateTop = 3;
            this.uvRotateBottom = 3;
        }
        final boolean var7 = this.renderStandardBlock(p_147742_1_, p_147742_2_, p_147742_3_, p_147742_4_);
        this.uvRotateSouth = 0;
        this.uvRotateEast = 0;
        this.uvRotateWest = 0;
        this.uvRotateNorth = 0;
        this.uvRotateTop = 0;
        this.uvRotateBottom = 0;
        return var7;
    }
    
    public boolean renderBlockQuartz(final Block p_147779_1_, final int p_147779_2_, final int p_147779_3_, final int p_147779_4_) {
        final int var5 = this.blockAccess.getBlockMetadata(p_147779_2_, p_147779_3_, p_147779_4_);
        if (var5 == 3) {
            this.uvRotateEast = 2;
            this.uvRotateWest = 1;
            this.uvRotateTop = 1;
            this.uvRotateBottom = 2;
        }
        else if (var5 == 4) {
            this.uvRotateSouth = 2;
            this.uvRotateNorth = 1;
            this.uvRotateTop = 3;
            this.uvRotateBottom = 3;
        }
        final boolean var6 = this.renderStandardBlock(p_147779_1_, p_147779_2_, p_147779_3_, p_147779_4_);
        this.uvRotateSouth = 0;
        this.uvRotateEast = 0;
        this.uvRotateWest = 0;
        this.uvRotateNorth = 0;
        this.uvRotateTop = 0;
        this.uvRotateBottom = 0;
        return var6;
    }
    
    public boolean renderStandardBlockWithAmbientOcclusion(final Block p_147751_1_, int p_147751_2_, int p_147751_3_, int p_147751_4_, final float p_147751_5_, final float p_147751_6_, final float p_147751_7_) {
        this.enableAO = true;
        final boolean defaultTexture = Tessellator.instance.defaultTexture;
        final boolean betterGrass = Config.isBetterGrass() && defaultTexture;
        final boolean simpleAO = p_147751_1_ == Blocks.glass;
        boolean var8 = false;
        float var9 = 0.0f;
        float var10 = 0.0f;
        float var11 = 0.0f;
        float var12 = 0.0f;
        boolean var13 = true;
        int var14 = -1;
        final Tessellator var15 = Tessellator.instance;
        var15.setBrightness(983055);
        if (p_147751_1_ == Blocks.grass) {
            var13 = false;
        }
        else if (this.hasOverrideBlockTexture()) {
            var13 = false;
        }
        if (this.renderAllFaces || p_147751_1_.shouldSideBeRendered(this.blockAccess, p_147751_2_, p_147751_3_ - 1, p_147751_4_, 0)) {
            if (this.renderMinY <= 0.0) {
                --p_147751_3_;
            }
            this.aoBrightnessXYNN = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_ - 1, p_147751_3_, p_147751_4_);
            this.aoBrightnessYZNN = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_, p_147751_3_, p_147751_4_ - 1);
            this.aoBrightnessYZNP = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_, p_147751_3_, p_147751_4_ + 1);
            this.aoBrightnessXYPN = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_ + 1, p_147751_3_, p_147751_4_);
            this.aoLightValueScratchXYNN = this.getAmbientOcclusionLightValue(p_147751_2_ - 1, p_147751_3_, p_147751_4_);
            this.aoLightValueScratchYZNN = this.getAmbientOcclusionLightValue(p_147751_2_, p_147751_3_, p_147751_4_ - 1);
            this.aoLightValueScratchYZNP = this.getAmbientOcclusionLightValue(p_147751_2_, p_147751_3_, p_147751_4_ + 1);
            this.aoLightValueScratchXYPN = this.getAmbientOcclusionLightValue(p_147751_2_ + 1, p_147751_3_, p_147751_4_);
            final boolean var16 = this.blockAccess.getBlock(p_147751_2_ + 1, p_147751_3_ - 1, p_147751_4_).getCanBlockGrass();
            final boolean var17 = this.blockAccess.getBlock(p_147751_2_ - 1, p_147751_3_ - 1, p_147751_4_).getCanBlockGrass();
            final boolean var18 = this.blockAccess.getBlock(p_147751_2_, p_147751_3_ - 1, p_147751_4_ + 1).getCanBlockGrass();
            final boolean var19 = this.blockAccess.getBlock(p_147751_2_, p_147751_3_ - 1, p_147751_4_ - 1).getCanBlockGrass();
            if (!var19 && !var17) {
                this.aoLightValueScratchXYZNNN = this.aoLightValueScratchXYNN;
                this.aoBrightnessXYZNNN = this.aoBrightnessXYNN;
            }
            else {
                this.aoLightValueScratchXYZNNN = this.getAmbientOcclusionLightValue(p_147751_2_ - 1, p_147751_3_, p_147751_4_ - 1);
                this.aoBrightnessXYZNNN = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_ - 1, p_147751_3_, p_147751_4_ - 1);
            }
            if (!var18 && !var17) {
                this.aoLightValueScratchXYZNNP = this.aoLightValueScratchXYNN;
                this.aoBrightnessXYZNNP = this.aoBrightnessXYNN;
            }
            else {
                this.aoLightValueScratchXYZNNP = this.getAmbientOcclusionLightValue(p_147751_2_ - 1, p_147751_3_, p_147751_4_ + 1);
                this.aoBrightnessXYZNNP = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_ - 1, p_147751_3_, p_147751_4_ + 1);
            }
            if (!var19 && !var16) {
                this.aoLightValueScratchXYZPNN = this.aoLightValueScratchXYPN;
                this.aoBrightnessXYZPNN = this.aoBrightnessXYPN;
            }
            else {
                this.aoLightValueScratchXYZPNN = this.getAmbientOcclusionLightValue(p_147751_2_ + 1, p_147751_3_, p_147751_4_ - 1);
                this.aoBrightnessXYZPNN = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_ + 1, p_147751_3_, p_147751_4_ - 1);
            }
            if (!var18 && !var16) {
                this.aoLightValueScratchXYZPNP = this.aoLightValueScratchXYPN;
                this.aoBrightnessXYZPNP = this.aoBrightnessXYPN;
            }
            else {
                this.aoLightValueScratchXYZPNP = this.getAmbientOcclusionLightValue(p_147751_2_ + 1, p_147751_3_, p_147751_4_ + 1);
                this.aoBrightnessXYZPNP = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_ + 1, p_147751_3_, p_147751_4_ + 1);
            }
            if (this.renderMinY <= 0.0) {
                ++p_147751_3_;
            }
            if (var14 < 0) {
                var14 = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_, p_147751_3_, p_147751_4_);
            }
            int var20 = var14;
            if (this.renderMinY <= 0.0 || !this.blockAccess.getBlock(p_147751_2_, p_147751_3_ - 1, p_147751_4_).isOpaqueCube()) {
                var20 = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_, p_147751_3_ - 1, p_147751_4_);
            }
            final float var21 = this.getAmbientOcclusionLightValue(p_147751_2_, p_147751_3_ - 1, p_147751_4_);
            var9 = (this.aoLightValueScratchXYZNNP + this.aoLightValueScratchXYNN + this.aoLightValueScratchYZNP + var21) / 4.0f;
            var12 = (this.aoLightValueScratchYZNP + var21 + this.aoLightValueScratchXYZPNP + this.aoLightValueScratchXYPN) / 4.0f;
            var11 = (var21 + this.aoLightValueScratchYZNN + this.aoLightValueScratchXYPN + this.aoLightValueScratchXYZPNN) / 4.0f;
            var10 = (this.aoLightValueScratchXYNN + this.aoLightValueScratchXYZNNN + var21 + this.aoLightValueScratchYZNN) / 4.0f;
            this.brightnessTopLeft = this.getAoBrightness(this.aoBrightnessXYZNNP, this.aoBrightnessXYNN, this.aoBrightnessYZNP, var20);
            this.brightnessTopRight = this.getAoBrightness(this.aoBrightnessYZNP, this.aoBrightnessXYZPNP, this.aoBrightnessXYPN, var20);
            this.brightnessBottomRight = this.getAoBrightness(this.aoBrightnessYZNN, this.aoBrightnessXYPN, this.aoBrightnessXYZPNN, var20);
            this.brightnessBottomLeft = this.getAoBrightness(this.aoBrightnessXYNN, this.aoBrightnessXYZNNN, this.aoBrightnessYZNN, var20);
            if (simpleAO) {
                var10 = var21;
                var11 = var21;
                var12 = var21;
                var9 = var21;
                final int n = var20;
                this.brightnessBottomLeft = n;
                this.brightnessBottomRight = n;
                this.brightnessTopRight = n;
                this.brightnessTopLeft = n;
            }
            if (var13) {
                final float n2 = p_147751_5_ * 0.5f;
                this.colorRedTopRight = n2;
                this.colorRedBottomRight = n2;
                this.colorRedBottomLeft = n2;
                this.colorRedTopLeft = n2;
                final float n3 = p_147751_6_ * 0.5f;
                this.colorGreenTopRight = n3;
                this.colorGreenBottomRight = n3;
                this.colorGreenBottomLeft = n3;
                this.colorGreenTopLeft = n3;
                final float n4 = p_147751_7_ * 0.5f;
                this.colorBlueTopRight = n4;
                this.colorBlueBottomRight = n4;
                this.colorBlueBottomLeft = n4;
                this.colorBlueTopLeft = n4;
            }
            else {
                final float n5 = 0.5f;
                this.colorRedTopRight = n5;
                this.colorRedBottomRight = n5;
                this.colorRedBottomLeft = n5;
                this.colorRedTopLeft = n5;
                final float n6 = 0.5f;
                this.colorGreenTopRight = n6;
                this.colorGreenBottomRight = n6;
                this.colorGreenBottomLeft = n6;
                this.colorGreenTopLeft = n6;
                final float n7 = 0.5f;
                this.colorBlueTopRight = n7;
                this.colorBlueBottomRight = n7;
                this.colorBlueBottomLeft = n7;
                this.colorBlueTopLeft = n7;
            }
            this.colorRedTopLeft *= var9;
            this.colorGreenTopLeft *= var9;
            this.colorBlueTopLeft *= var9;
            this.colorRedBottomLeft *= var10;
            this.colorGreenBottomLeft *= var10;
            this.colorBlueBottomLeft *= var10;
            this.colorRedBottomRight *= var11;
            this.colorGreenBottomRight *= var11;
            this.colorBlueBottomRight *= var11;
            this.colorRedTopRight *= var12;
            this.colorGreenTopRight *= var12;
            this.colorBlueTopRight *= var12;
            this.renderFaceYNeg(p_147751_1_, p_147751_2_, p_147751_3_, p_147751_4_, this.getBlockIcon(p_147751_1_, this.blockAccess, p_147751_2_, p_147751_3_, p_147751_4_, 0));
            var8 = true;
        }
        if (this.renderAllFaces || p_147751_1_.shouldSideBeRendered(this.blockAccess, p_147751_2_, p_147751_3_ + 1, p_147751_4_, 1)) {
            if (this.renderMaxY >= 1.0) {
                ++p_147751_3_;
            }
            this.aoBrightnessXYNP = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_ - 1, p_147751_3_, p_147751_4_);
            this.aoBrightnessXYPP = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_ + 1, p_147751_3_, p_147751_4_);
            this.aoBrightnessYZPN = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_, p_147751_3_, p_147751_4_ - 1);
            this.aoBrightnessYZPP = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_, p_147751_3_, p_147751_4_ + 1);
            this.aoLightValueScratchXYNP = this.getAmbientOcclusionLightValue(p_147751_2_ - 1, p_147751_3_, p_147751_4_);
            this.aoLightValueScratchXYPP = this.getAmbientOcclusionLightValue(p_147751_2_ + 1, p_147751_3_, p_147751_4_);
            this.aoLightValueScratchYZPN = this.getAmbientOcclusionLightValue(p_147751_2_, p_147751_3_, p_147751_4_ - 1);
            this.aoLightValueScratchYZPP = this.getAmbientOcclusionLightValue(p_147751_2_, p_147751_3_, p_147751_4_ + 1);
            final boolean var16 = this.blockAccess.getBlock(p_147751_2_ + 1, p_147751_3_ + 1, p_147751_4_).getCanBlockGrass();
            final boolean var17 = this.blockAccess.getBlock(p_147751_2_ - 1, p_147751_3_ + 1, p_147751_4_).getCanBlockGrass();
            final boolean var18 = this.blockAccess.getBlock(p_147751_2_, p_147751_3_ + 1, p_147751_4_ + 1).getCanBlockGrass();
            final boolean var19 = this.blockAccess.getBlock(p_147751_2_, p_147751_3_ + 1, p_147751_4_ - 1).getCanBlockGrass();
            if (!var19 && !var17) {
                this.aoLightValueScratchXYZNPN = this.aoLightValueScratchXYNP;
                this.aoBrightnessXYZNPN = this.aoBrightnessXYNP;
            }
            else {
                this.aoLightValueScratchXYZNPN = this.getAmbientOcclusionLightValue(p_147751_2_ - 1, p_147751_3_, p_147751_4_ - 1);
                this.aoBrightnessXYZNPN = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_ - 1, p_147751_3_, p_147751_4_ - 1);
            }
            if (!var19 && !var16) {
                this.aoLightValueScratchXYZPPN = this.aoLightValueScratchXYPP;
                this.aoBrightnessXYZPPN = this.aoBrightnessXYPP;
            }
            else {
                this.aoLightValueScratchXYZPPN = this.getAmbientOcclusionLightValue(p_147751_2_ + 1, p_147751_3_, p_147751_4_ - 1);
                this.aoBrightnessXYZPPN = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_ + 1, p_147751_3_, p_147751_4_ - 1);
            }
            if (!var18 && !var17) {
                this.aoLightValueScratchXYZNPP = this.aoLightValueScratchXYNP;
                this.aoBrightnessXYZNPP = this.aoBrightnessXYNP;
            }
            else {
                this.aoLightValueScratchXYZNPP = this.getAmbientOcclusionLightValue(p_147751_2_ - 1, p_147751_3_, p_147751_4_ + 1);
                this.aoBrightnessXYZNPP = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_ - 1, p_147751_3_, p_147751_4_ + 1);
            }
            if (!var18 && !var16) {
                this.aoLightValueScratchXYZPPP = this.aoLightValueScratchXYPP;
                this.aoBrightnessXYZPPP = this.aoBrightnessXYPP;
            }
            else {
                this.aoLightValueScratchXYZPPP = this.getAmbientOcclusionLightValue(p_147751_2_ + 1, p_147751_3_, p_147751_4_ + 1);
                this.aoBrightnessXYZPPP = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_ + 1, p_147751_3_, p_147751_4_ + 1);
            }
            if (this.renderMaxY >= 1.0) {
                --p_147751_3_;
            }
            if (var14 < 0) {
                var14 = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_, p_147751_3_, p_147751_4_);
            }
            int var20 = var14;
            if (this.renderMaxY >= 1.0 || !this.blockAccess.getBlock(p_147751_2_, p_147751_3_ + 1, p_147751_4_).isOpaqueCube()) {
                var20 = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_, p_147751_3_ + 1, p_147751_4_);
            }
            final float var21 = this.getAmbientOcclusionLightValue(p_147751_2_, p_147751_3_ + 1, p_147751_4_);
            var12 = (this.aoLightValueScratchXYZNPP + this.aoLightValueScratchXYNP + this.aoLightValueScratchYZPP + var21) / 4.0f;
            var9 = (this.aoLightValueScratchYZPP + var21 + this.aoLightValueScratchXYZPPP + this.aoLightValueScratchXYPP) / 4.0f;
            var10 = (var21 + this.aoLightValueScratchYZPN + this.aoLightValueScratchXYPP + this.aoLightValueScratchXYZPPN) / 4.0f;
            var11 = (this.aoLightValueScratchXYNP + this.aoLightValueScratchXYZNPN + var21 + this.aoLightValueScratchYZPN) / 4.0f;
            this.brightnessTopRight = this.getAoBrightness(this.aoBrightnessXYZNPP, this.aoBrightnessXYNP, this.aoBrightnessYZPP, var20);
            this.brightnessTopLeft = this.getAoBrightness(this.aoBrightnessYZPP, this.aoBrightnessXYZPPP, this.aoBrightnessXYPP, var20);
            this.brightnessBottomLeft = this.getAoBrightness(this.aoBrightnessYZPN, this.aoBrightnessXYPP, this.aoBrightnessXYZPPN, var20);
            this.brightnessBottomRight = this.getAoBrightness(this.aoBrightnessXYNP, this.aoBrightnessXYZNPN, this.aoBrightnessYZPN, var20);
            if (simpleAO) {
                var10 = var21;
                var11 = var21;
                var12 = var21;
                var9 = var21;
                final int n8 = var20;
                this.brightnessBottomLeft = n8;
                this.brightnessBottomRight = n8;
                this.brightnessTopRight = n8;
                this.brightnessTopLeft = n8;
            }
            this.colorRedTopRight = p_147751_5_;
            this.colorRedBottomRight = p_147751_5_;
            this.colorRedBottomLeft = p_147751_5_;
            this.colorRedTopLeft = p_147751_5_;
            this.colorGreenTopRight = p_147751_6_;
            this.colorGreenBottomRight = p_147751_6_;
            this.colorGreenBottomLeft = p_147751_6_;
            this.colorGreenTopLeft = p_147751_6_;
            this.colorBlueTopRight = p_147751_7_;
            this.colorBlueBottomRight = p_147751_7_;
            this.colorBlueBottomLeft = p_147751_7_;
            this.colorBlueTopLeft = p_147751_7_;
            this.colorRedTopLeft *= var9;
            this.colorGreenTopLeft *= var9;
            this.colorBlueTopLeft *= var9;
            this.colorRedBottomLeft *= var10;
            this.colorGreenBottomLeft *= var10;
            this.colorBlueBottomLeft *= var10;
            this.colorRedBottomRight *= var11;
            this.colorGreenBottomRight *= var11;
            this.colorBlueBottomRight *= var11;
            this.colorRedTopRight *= var12;
            this.colorGreenTopRight *= var12;
            this.colorBlueTopRight *= var12;
            this.renderFaceYPos(p_147751_1_, p_147751_2_, p_147751_3_, p_147751_4_, this.getBlockIcon(p_147751_1_, this.blockAccess, p_147751_2_, p_147751_3_, p_147751_4_, 1));
            var8 = true;
        }
        if (this.renderAllFaces || p_147751_1_.shouldSideBeRendered(this.blockAccess, p_147751_2_, p_147751_3_, p_147751_4_ - 1, 2)) {
            if (this.renderMinZ <= 0.0) {
                --p_147751_4_;
            }
            this.aoLightValueScratchXZNN = this.getAmbientOcclusionLightValue(p_147751_2_ - 1, p_147751_3_, p_147751_4_);
            this.aoLightValueScratchYZNN = this.getAmbientOcclusionLightValue(p_147751_2_, p_147751_3_ - 1, p_147751_4_);
            this.aoLightValueScratchYZPN = this.getAmbientOcclusionLightValue(p_147751_2_, p_147751_3_ + 1, p_147751_4_);
            this.aoLightValueScratchXZPN = this.getAmbientOcclusionLightValue(p_147751_2_ + 1, p_147751_3_, p_147751_4_);
            this.aoBrightnessXZNN = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_ - 1, p_147751_3_, p_147751_4_);
            this.aoBrightnessYZNN = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_, p_147751_3_ - 1, p_147751_4_);
            this.aoBrightnessYZPN = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_, p_147751_3_ + 1, p_147751_4_);
            this.aoBrightnessXZPN = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_ + 1, p_147751_3_, p_147751_4_);
            final boolean var16 = this.blockAccess.getBlock(p_147751_2_ + 1, p_147751_3_, p_147751_4_ - 1).getCanBlockGrass();
            final boolean var17 = this.blockAccess.getBlock(p_147751_2_ - 1, p_147751_3_, p_147751_4_ - 1).getCanBlockGrass();
            final boolean var18 = this.blockAccess.getBlock(p_147751_2_, p_147751_3_ + 1, p_147751_4_ - 1).getCanBlockGrass();
            final boolean var19 = this.blockAccess.getBlock(p_147751_2_, p_147751_3_ - 1, p_147751_4_ - 1).getCanBlockGrass();
            if (!var17 && !var19) {
                this.aoLightValueScratchXYZNNN = this.aoLightValueScratchXZNN;
                this.aoBrightnessXYZNNN = this.aoBrightnessXZNN;
            }
            else {
                this.aoLightValueScratchXYZNNN = this.getAmbientOcclusionLightValue(p_147751_2_ - 1, p_147751_3_ - 1, p_147751_4_);
                this.aoBrightnessXYZNNN = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_ - 1, p_147751_3_ - 1, p_147751_4_);
            }
            if (!var17 && !var18) {
                this.aoLightValueScratchXYZNPN = this.aoLightValueScratchXZNN;
                this.aoBrightnessXYZNPN = this.aoBrightnessXZNN;
            }
            else {
                this.aoLightValueScratchXYZNPN = this.getAmbientOcclusionLightValue(p_147751_2_ - 1, p_147751_3_ + 1, p_147751_4_);
                this.aoBrightnessXYZNPN = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_ - 1, p_147751_3_ + 1, p_147751_4_);
            }
            if (!var16 && !var19) {
                this.aoLightValueScratchXYZPNN = this.aoLightValueScratchXZPN;
                this.aoBrightnessXYZPNN = this.aoBrightnessXZPN;
            }
            else {
                this.aoLightValueScratchXYZPNN = this.getAmbientOcclusionLightValue(p_147751_2_ + 1, p_147751_3_ - 1, p_147751_4_);
                this.aoBrightnessXYZPNN = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_ + 1, p_147751_3_ - 1, p_147751_4_);
            }
            if (!var16 && !var18) {
                this.aoLightValueScratchXYZPPN = this.aoLightValueScratchXZPN;
                this.aoBrightnessXYZPPN = this.aoBrightnessXZPN;
            }
            else {
                this.aoLightValueScratchXYZPPN = this.getAmbientOcclusionLightValue(p_147751_2_ + 1, p_147751_3_ + 1, p_147751_4_);
                this.aoBrightnessXYZPPN = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_ + 1, p_147751_3_ + 1, p_147751_4_);
            }
            if (this.renderMinZ <= 0.0) {
                ++p_147751_4_;
            }
            if (var14 < 0) {
                var14 = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_, p_147751_3_, p_147751_4_);
            }
            int var20 = var14;
            if (this.renderMinZ <= 0.0 || !this.blockAccess.getBlock(p_147751_2_, p_147751_3_, p_147751_4_ - 1).isOpaqueCube()) {
                var20 = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_, p_147751_3_, p_147751_4_ - 1);
            }
            final float var21 = this.getAmbientOcclusionLightValue(p_147751_2_, p_147751_3_, p_147751_4_ - 1);
            var9 = (this.aoLightValueScratchXZNN + this.aoLightValueScratchXYZNPN + var21 + this.aoLightValueScratchYZPN) / 4.0f;
            var10 = (var21 + this.aoLightValueScratchYZPN + this.aoLightValueScratchXZPN + this.aoLightValueScratchXYZPPN) / 4.0f;
            var11 = (this.aoLightValueScratchYZNN + var21 + this.aoLightValueScratchXYZPNN + this.aoLightValueScratchXZPN) / 4.0f;
            var12 = (this.aoLightValueScratchXYZNNN + this.aoLightValueScratchXZNN + this.aoLightValueScratchYZNN + var21) / 4.0f;
            this.brightnessTopLeft = this.getAoBrightness(this.aoBrightnessXZNN, this.aoBrightnessXYZNPN, this.aoBrightnessYZPN, var20);
            this.brightnessBottomLeft = this.getAoBrightness(this.aoBrightnessYZPN, this.aoBrightnessXZPN, this.aoBrightnessXYZPPN, var20);
            this.brightnessBottomRight = this.getAoBrightness(this.aoBrightnessYZNN, this.aoBrightnessXYZPNN, this.aoBrightnessXZPN, var20);
            this.brightnessTopRight = this.getAoBrightness(this.aoBrightnessXYZNNN, this.aoBrightnessXZNN, this.aoBrightnessYZNN, var20);
            if (simpleAO) {
                var10 = var21;
                var11 = var21;
                var12 = var21;
                var9 = var21;
                final int n9 = var20;
                this.brightnessBottomLeft = n9;
                this.brightnessBottomRight = n9;
                this.brightnessTopRight = n9;
                this.brightnessTopLeft = n9;
            }
            if (var13) {
                final float n10 = p_147751_5_ * 0.8f;
                this.colorRedTopRight = n10;
                this.colorRedBottomRight = n10;
                this.colorRedBottomLeft = n10;
                this.colorRedTopLeft = n10;
                final float n11 = p_147751_6_ * 0.8f;
                this.colorGreenTopRight = n11;
                this.colorGreenBottomRight = n11;
                this.colorGreenBottomLeft = n11;
                this.colorGreenTopLeft = n11;
                final float n12 = p_147751_7_ * 0.8f;
                this.colorBlueTopRight = n12;
                this.colorBlueBottomRight = n12;
                this.colorBlueBottomLeft = n12;
                this.colorBlueTopLeft = n12;
            }
            else {
                final float n13 = 0.8f;
                this.colorRedTopRight = n13;
                this.colorRedBottomRight = n13;
                this.colorRedBottomLeft = n13;
                this.colorRedTopLeft = n13;
                final float n14 = 0.8f;
                this.colorGreenTopRight = n14;
                this.colorGreenBottomRight = n14;
                this.colorGreenBottomLeft = n14;
                this.colorGreenTopLeft = n14;
                final float n15 = 0.8f;
                this.colorBlueTopRight = n15;
                this.colorBlueBottomRight = n15;
                this.colorBlueBottomLeft = n15;
                this.colorBlueTopLeft = n15;
            }
            this.colorRedTopLeft *= var9;
            this.colorGreenTopLeft *= var9;
            this.colorBlueTopLeft *= var9;
            this.colorRedBottomLeft *= var10;
            this.colorGreenBottomLeft *= var10;
            this.colorBlueBottomLeft *= var10;
            this.colorRedBottomRight *= var11;
            this.colorGreenBottomRight *= var11;
            this.colorBlueBottomRight *= var11;
            this.colorRedTopRight *= var12;
            this.colorGreenTopRight *= var12;
            this.colorBlueTopRight *= var12;
            IIcon var22 = this.getBlockIcon(p_147751_1_, this.blockAccess, p_147751_2_, p_147751_3_, p_147751_4_, 2);
            if (betterGrass) {
                var22 = this.fixAoSideGrassTexture(var22, p_147751_2_, p_147751_3_, p_147751_4_, 2, p_147751_5_, p_147751_6_, p_147751_7_);
            }
            this.renderFaceZNeg(p_147751_1_, p_147751_2_, p_147751_3_, p_147751_4_, var22);
            if (defaultTexture && RenderBlocks.fancyGrass && var22 == TextureUtils.iconGrassSide && !this.hasOverrideBlockTexture()) {
                this.colorRedTopLeft *= p_147751_5_;
                this.colorRedBottomLeft *= p_147751_5_;
                this.colorRedBottomRight *= p_147751_5_;
                this.colorRedTopRight *= p_147751_5_;
                this.colorGreenTopLeft *= p_147751_6_;
                this.colorGreenBottomLeft *= p_147751_6_;
                this.colorGreenBottomRight *= p_147751_6_;
                this.colorGreenTopRight *= p_147751_6_;
                this.colorBlueTopLeft *= p_147751_7_;
                this.colorBlueBottomLeft *= p_147751_7_;
                this.colorBlueBottomRight *= p_147751_7_;
                this.colorBlueTopRight *= p_147751_7_;
                this.renderFaceZNeg(p_147751_1_, p_147751_2_, p_147751_3_, p_147751_4_, BlockGrass.func_149990_e());
            }
            var8 = true;
        }
        if (this.renderAllFaces || p_147751_1_.shouldSideBeRendered(this.blockAccess, p_147751_2_, p_147751_3_, p_147751_4_ + 1, 3)) {
            if (this.renderMaxZ >= 1.0) {
                ++p_147751_4_;
            }
            this.aoLightValueScratchXZNP = this.getAmbientOcclusionLightValue(p_147751_2_ - 1, p_147751_3_, p_147751_4_);
            this.aoLightValueScratchXZPP = this.getAmbientOcclusionLightValue(p_147751_2_ + 1, p_147751_3_, p_147751_4_);
            this.aoLightValueScratchYZNP = this.getAmbientOcclusionLightValue(p_147751_2_, p_147751_3_ - 1, p_147751_4_);
            this.aoLightValueScratchYZPP = this.getAmbientOcclusionLightValue(p_147751_2_, p_147751_3_ + 1, p_147751_4_);
            this.aoBrightnessXZNP = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_ - 1, p_147751_3_, p_147751_4_);
            this.aoBrightnessXZPP = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_ + 1, p_147751_3_, p_147751_4_);
            this.aoBrightnessYZNP = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_, p_147751_3_ - 1, p_147751_4_);
            this.aoBrightnessYZPP = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_, p_147751_3_ + 1, p_147751_4_);
            final boolean var16 = this.blockAccess.getBlock(p_147751_2_ + 1, p_147751_3_, p_147751_4_ + 1).getCanBlockGrass();
            final boolean var17 = this.blockAccess.getBlock(p_147751_2_ - 1, p_147751_3_, p_147751_4_ + 1).getCanBlockGrass();
            final boolean var18 = this.blockAccess.getBlock(p_147751_2_, p_147751_3_ + 1, p_147751_4_ + 1).getCanBlockGrass();
            final boolean var19 = this.blockAccess.getBlock(p_147751_2_, p_147751_3_ - 1, p_147751_4_ + 1).getCanBlockGrass();
            if (!var17 && !var19) {
                this.aoLightValueScratchXYZNNP = this.aoLightValueScratchXZNP;
                this.aoBrightnessXYZNNP = this.aoBrightnessXZNP;
            }
            else {
                this.aoLightValueScratchXYZNNP = this.getAmbientOcclusionLightValue(p_147751_2_ - 1, p_147751_3_ - 1, p_147751_4_);
                this.aoBrightnessXYZNNP = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_ - 1, p_147751_3_ - 1, p_147751_4_);
            }
            if (!var17 && !var18) {
                this.aoLightValueScratchXYZNPP = this.aoLightValueScratchXZNP;
                this.aoBrightnessXYZNPP = this.aoBrightnessXZNP;
            }
            else {
                this.aoLightValueScratchXYZNPP = this.getAmbientOcclusionLightValue(p_147751_2_ - 1, p_147751_3_ + 1, p_147751_4_);
                this.aoBrightnessXYZNPP = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_ - 1, p_147751_3_ + 1, p_147751_4_);
            }
            if (!var16 && !var19) {
                this.aoLightValueScratchXYZPNP = this.aoLightValueScratchXZPP;
                this.aoBrightnessXYZPNP = this.aoBrightnessXZPP;
            }
            else {
                this.aoLightValueScratchXYZPNP = this.getAmbientOcclusionLightValue(p_147751_2_ + 1, p_147751_3_ - 1, p_147751_4_);
                this.aoBrightnessXYZPNP = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_ + 1, p_147751_3_ - 1, p_147751_4_);
            }
            if (!var16 && !var18) {
                this.aoLightValueScratchXYZPPP = this.aoLightValueScratchXZPP;
                this.aoBrightnessXYZPPP = this.aoBrightnessXZPP;
            }
            else {
                this.aoLightValueScratchXYZPPP = this.getAmbientOcclusionLightValue(p_147751_2_ + 1, p_147751_3_ + 1, p_147751_4_);
                this.aoBrightnessXYZPPP = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_ + 1, p_147751_3_ + 1, p_147751_4_);
            }
            if (this.renderMaxZ >= 1.0) {
                --p_147751_4_;
            }
            if (var14 < 0) {
                var14 = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_, p_147751_3_, p_147751_4_);
            }
            int var20 = var14;
            if (this.renderMaxZ >= 1.0 || !this.blockAccess.getBlock(p_147751_2_, p_147751_3_, p_147751_4_ + 1).isOpaqueCube()) {
                var20 = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_, p_147751_3_, p_147751_4_ + 1);
            }
            final float var21 = this.getAmbientOcclusionLightValue(p_147751_2_, p_147751_3_, p_147751_4_ + 1);
            var9 = (this.aoLightValueScratchXZNP + this.aoLightValueScratchXYZNPP + var21 + this.aoLightValueScratchYZPP) / 4.0f;
            var12 = (var21 + this.aoLightValueScratchYZPP + this.aoLightValueScratchXZPP + this.aoLightValueScratchXYZPPP) / 4.0f;
            var11 = (this.aoLightValueScratchYZNP + var21 + this.aoLightValueScratchXYZPNP + this.aoLightValueScratchXZPP) / 4.0f;
            var10 = (this.aoLightValueScratchXYZNNP + this.aoLightValueScratchXZNP + this.aoLightValueScratchYZNP + var21) / 4.0f;
            this.brightnessTopLeft = this.getAoBrightness(this.aoBrightnessXZNP, this.aoBrightnessXYZNPP, this.aoBrightnessYZPP, var20);
            this.brightnessTopRight = this.getAoBrightness(this.aoBrightnessYZPP, this.aoBrightnessXZPP, this.aoBrightnessXYZPPP, var20);
            this.brightnessBottomRight = this.getAoBrightness(this.aoBrightnessYZNP, this.aoBrightnessXYZPNP, this.aoBrightnessXZPP, var20);
            this.brightnessBottomLeft = this.getAoBrightness(this.aoBrightnessXYZNNP, this.aoBrightnessXZNP, this.aoBrightnessYZNP, var20);
            if (simpleAO) {
                var10 = var21;
                var11 = var21;
                var12 = var21;
                var9 = var21;
                final int n16 = var20;
                this.brightnessBottomLeft = n16;
                this.brightnessBottomRight = n16;
                this.brightnessTopRight = n16;
                this.brightnessTopLeft = n16;
            }
            if (var13) {
                final float n17 = p_147751_5_ * 0.8f;
                this.colorRedTopRight = n17;
                this.colorRedBottomRight = n17;
                this.colorRedBottomLeft = n17;
                this.colorRedTopLeft = n17;
                final float n18 = p_147751_6_ * 0.8f;
                this.colorGreenTopRight = n18;
                this.colorGreenBottomRight = n18;
                this.colorGreenBottomLeft = n18;
                this.colorGreenTopLeft = n18;
                final float n19 = p_147751_7_ * 0.8f;
                this.colorBlueTopRight = n19;
                this.colorBlueBottomRight = n19;
                this.colorBlueBottomLeft = n19;
                this.colorBlueTopLeft = n19;
            }
            else {
                final float n20 = 0.8f;
                this.colorRedTopRight = n20;
                this.colorRedBottomRight = n20;
                this.colorRedBottomLeft = n20;
                this.colorRedTopLeft = n20;
                final float n21 = 0.8f;
                this.colorGreenTopRight = n21;
                this.colorGreenBottomRight = n21;
                this.colorGreenBottomLeft = n21;
                this.colorGreenTopLeft = n21;
                final float n22 = 0.8f;
                this.colorBlueTopRight = n22;
                this.colorBlueBottomRight = n22;
                this.colorBlueBottomLeft = n22;
                this.colorBlueTopLeft = n22;
            }
            this.colorRedTopLeft *= var9;
            this.colorGreenTopLeft *= var9;
            this.colorBlueTopLeft *= var9;
            this.colorRedBottomLeft *= var10;
            this.colorGreenBottomLeft *= var10;
            this.colorBlueBottomLeft *= var10;
            this.colorRedBottomRight *= var11;
            this.colorGreenBottomRight *= var11;
            this.colorBlueBottomRight *= var11;
            this.colorRedTopRight *= var12;
            this.colorGreenTopRight *= var12;
            this.colorBlueTopRight *= var12;
            IIcon var22 = this.getBlockIcon(p_147751_1_, this.blockAccess, p_147751_2_, p_147751_3_, p_147751_4_, 3);
            if (betterGrass) {
                var22 = this.fixAoSideGrassTexture(var22, p_147751_2_, p_147751_3_, p_147751_4_, 3, p_147751_5_, p_147751_6_, p_147751_7_);
            }
            this.renderFaceZPos(p_147751_1_, p_147751_2_, p_147751_3_, p_147751_4_, var22);
            if (defaultTexture && RenderBlocks.fancyGrass && var22 == TextureUtils.iconGrassSide && !this.hasOverrideBlockTexture()) {
                this.colorRedTopLeft *= p_147751_5_;
                this.colorRedBottomLeft *= p_147751_5_;
                this.colorRedBottomRight *= p_147751_5_;
                this.colorRedTopRight *= p_147751_5_;
                this.colorGreenTopLeft *= p_147751_6_;
                this.colorGreenBottomLeft *= p_147751_6_;
                this.colorGreenBottomRight *= p_147751_6_;
                this.colorGreenTopRight *= p_147751_6_;
                this.colorBlueTopLeft *= p_147751_7_;
                this.colorBlueBottomLeft *= p_147751_7_;
                this.colorBlueBottomRight *= p_147751_7_;
                this.colorBlueTopRight *= p_147751_7_;
                this.renderFaceZPos(p_147751_1_, p_147751_2_, p_147751_3_, p_147751_4_, BlockGrass.func_149990_e());
            }
            var8 = true;
        }
        if (this.renderAllFaces || p_147751_1_.shouldSideBeRendered(this.blockAccess, p_147751_2_ - 1, p_147751_3_, p_147751_4_, 4)) {
            if (this.renderMinX <= 0.0) {
                --p_147751_2_;
            }
            this.aoLightValueScratchXYNN = this.getAmbientOcclusionLightValue(p_147751_2_, p_147751_3_ - 1, p_147751_4_);
            this.aoLightValueScratchXZNN = this.getAmbientOcclusionLightValue(p_147751_2_, p_147751_3_, p_147751_4_ - 1);
            this.aoLightValueScratchXZNP = this.getAmbientOcclusionLightValue(p_147751_2_, p_147751_3_, p_147751_4_ + 1);
            this.aoLightValueScratchXYNP = this.getAmbientOcclusionLightValue(p_147751_2_, p_147751_3_ + 1, p_147751_4_);
            this.aoBrightnessXYNN = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_, p_147751_3_ - 1, p_147751_4_);
            this.aoBrightnessXZNN = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_, p_147751_3_, p_147751_4_ - 1);
            this.aoBrightnessXZNP = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_, p_147751_3_, p_147751_4_ + 1);
            this.aoBrightnessXYNP = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_, p_147751_3_ + 1, p_147751_4_);
            final boolean var16 = this.blockAccess.getBlock(p_147751_2_ - 1, p_147751_3_ + 1, p_147751_4_).getCanBlockGrass();
            final boolean var17 = this.blockAccess.getBlock(p_147751_2_ - 1, p_147751_3_ - 1, p_147751_4_).getCanBlockGrass();
            final boolean var18 = this.blockAccess.getBlock(p_147751_2_ - 1, p_147751_3_, p_147751_4_ - 1).getCanBlockGrass();
            final boolean var19 = this.blockAccess.getBlock(p_147751_2_ - 1, p_147751_3_, p_147751_4_ + 1).getCanBlockGrass();
            if (!var18 && !var17) {
                this.aoLightValueScratchXYZNNN = this.aoLightValueScratchXZNN;
                this.aoBrightnessXYZNNN = this.aoBrightnessXZNN;
            }
            else {
                this.aoLightValueScratchXYZNNN = this.getAmbientOcclusionLightValue(p_147751_2_, p_147751_3_ - 1, p_147751_4_ - 1);
                this.aoBrightnessXYZNNN = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_, p_147751_3_ - 1, p_147751_4_ - 1);
            }
            if (!var19 && !var17) {
                this.aoLightValueScratchXYZNNP = this.aoLightValueScratchXZNP;
                this.aoBrightnessXYZNNP = this.aoBrightnessXZNP;
            }
            else {
                this.aoLightValueScratchXYZNNP = this.getAmbientOcclusionLightValue(p_147751_2_, p_147751_3_ - 1, p_147751_4_ + 1);
                this.aoBrightnessXYZNNP = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_, p_147751_3_ - 1, p_147751_4_ + 1);
            }
            if (!var18 && !var16) {
                this.aoLightValueScratchXYZNPN = this.aoLightValueScratchXZNN;
                this.aoBrightnessXYZNPN = this.aoBrightnessXZNN;
            }
            else {
                this.aoLightValueScratchXYZNPN = this.getAmbientOcclusionLightValue(p_147751_2_, p_147751_3_ + 1, p_147751_4_ - 1);
                this.aoBrightnessXYZNPN = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_, p_147751_3_ + 1, p_147751_4_ - 1);
            }
            if (!var19 && !var16) {
                this.aoLightValueScratchXYZNPP = this.aoLightValueScratchXZNP;
                this.aoBrightnessXYZNPP = this.aoBrightnessXZNP;
            }
            else {
                this.aoLightValueScratchXYZNPP = this.getAmbientOcclusionLightValue(p_147751_2_, p_147751_3_ + 1, p_147751_4_ + 1);
                this.aoBrightnessXYZNPP = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_, p_147751_3_ + 1, p_147751_4_ + 1);
            }
            if (this.renderMinX <= 0.0) {
                ++p_147751_2_;
            }
            if (var14 < 0) {
                var14 = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_, p_147751_3_, p_147751_4_);
            }
            int var20 = var14;
            if (this.renderMinX <= 0.0 || !this.blockAccess.getBlock(p_147751_2_ - 1, p_147751_3_, p_147751_4_).isOpaqueCube()) {
                var20 = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_ - 1, p_147751_3_, p_147751_4_);
            }
            final float var21 = this.getAmbientOcclusionLightValue(p_147751_2_ - 1, p_147751_3_, p_147751_4_);
            var12 = (this.aoLightValueScratchXYNN + this.aoLightValueScratchXYZNNP + var21 + this.aoLightValueScratchXZNP) / 4.0f;
            var9 = (var21 + this.aoLightValueScratchXZNP + this.aoLightValueScratchXYNP + this.aoLightValueScratchXYZNPP) / 4.0f;
            var10 = (this.aoLightValueScratchXZNN + var21 + this.aoLightValueScratchXYZNPN + this.aoLightValueScratchXYNP) / 4.0f;
            var11 = (this.aoLightValueScratchXYZNNN + this.aoLightValueScratchXYNN + this.aoLightValueScratchXZNN + var21) / 4.0f;
            this.brightnessTopRight = this.getAoBrightness(this.aoBrightnessXYNN, this.aoBrightnessXYZNNP, this.aoBrightnessXZNP, var20);
            this.brightnessTopLeft = this.getAoBrightness(this.aoBrightnessXZNP, this.aoBrightnessXYNP, this.aoBrightnessXYZNPP, var20);
            this.brightnessBottomLeft = this.getAoBrightness(this.aoBrightnessXZNN, this.aoBrightnessXYZNPN, this.aoBrightnessXYNP, var20);
            this.brightnessBottomRight = this.getAoBrightness(this.aoBrightnessXYZNNN, this.aoBrightnessXYNN, this.aoBrightnessXZNN, var20);
            if (simpleAO) {
                var10 = var21;
                var11 = var21;
                var12 = var21;
                var9 = var21;
                final int n23 = var20;
                this.brightnessBottomLeft = n23;
                this.brightnessBottomRight = n23;
                this.brightnessTopRight = n23;
                this.brightnessTopLeft = n23;
            }
            if (var13) {
                final float n24 = p_147751_5_ * 0.6f;
                this.colorRedTopRight = n24;
                this.colorRedBottomRight = n24;
                this.colorRedBottomLeft = n24;
                this.colorRedTopLeft = n24;
                final float n25 = p_147751_6_ * 0.6f;
                this.colorGreenTopRight = n25;
                this.colorGreenBottomRight = n25;
                this.colorGreenBottomLeft = n25;
                this.colorGreenTopLeft = n25;
                final float n26 = p_147751_7_ * 0.6f;
                this.colorBlueTopRight = n26;
                this.colorBlueBottomRight = n26;
                this.colorBlueBottomLeft = n26;
                this.colorBlueTopLeft = n26;
            }
            else {
                final float n27 = 0.6f;
                this.colorRedTopRight = n27;
                this.colorRedBottomRight = n27;
                this.colorRedBottomLeft = n27;
                this.colorRedTopLeft = n27;
                final float n28 = 0.6f;
                this.colorGreenTopRight = n28;
                this.colorGreenBottomRight = n28;
                this.colorGreenBottomLeft = n28;
                this.colorGreenTopLeft = n28;
                final float n29 = 0.6f;
                this.colorBlueTopRight = n29;
                this.colorBlueBottomRight = n29;
                this.colorBlueBottomLeft = n29;
                this.colorBlueTopLeft = n29;
            }
            this.colorRedTopLeft *= var9;
            this.colorGreenTopLeft *= var9;
            this.colorBlueTopLeft *= var9;
            this.colorRedBottomLeft *= var10;
            this.colorGreenBottomLeft *= var10;
            this.colorBlueBottomLeft *= var10;
            this.colorRedBottomRight *= var11;
            this.colorGreenBottomRight *= var11;
            this.colorBlueBottomRight *= var11;
            this.colorRedTopRight *= var12;
            this.colorGreenTopRight *= var12;
            this.colorBlueTopRight *= var12;
            IIcon var22 = this.getBlockIcon(p_147751_1_, this.blockAccess, p_147751_2_, p_147751_3_, p_147751_4_, 4);
            if (betterGrass) {
                var22 = this.fixAoSideGrassTexture(var22, p_147751_2_, p_147751_3_, p_147751_4_, 4, p_147751_5_, p_147751_6_, p_147751_7_);
            }
            this.renderFaceXNeg(p_147751_1_, p_147751_2_, p_147751_3_, p_147751_4_, var22);
            if (defaultTexture && RenderBlocks.fancyGrass && var22 == TextureUtils.iconGrassSide && !this.hasOverrideBlockTexture()) {
                this.colorRedTopLeft *= p_147751_5_;
                this.colorRedBottomLeft *= p_147751_5_;
                this.colorRedBottomRight *= p_147751_5_;
                this.colorRedTopRight *= p_147751_5_;
                this.colorGreenTopLeft *= p_147751_6_;
                this.colorGreenBottomLeft *= p_147751_6_;
                this.colorGreenBottomRight *= p_147751_6_;
                this.colorGreenTopRight *= p_147751_6_;
                this.colorBlueTopLeft *= p_147751_7_;
                this.colorBlueBottomLeft *= p_147751_7_;
                this.colorBlueBottomRight *= p_147751_7_;
                this.colorBlueTopRight *= p_147751_7_;
                this.renderFaceXNeg(p_147751_1_, p_147751_2_, p_147751_3_, p_147751_4_, BlockGrass.func_149990_e());
            }
            var8 = true;
        }
        if (this.renderAllFaces || p_147751_1_.shouldSideBeRendered(this.blockAccess, p_147751_2_ + 1, p_147751_3_, p_147751_4_, 5)) {
            if (this.renderMaxX >= 1.0) {
                ++p_147751_2_;
            }
            this.aoLightValueScratchXYPN = this.getAmbientOcclusionLightValue(p_147751_2_, p_147751_3_ - 1, p_147751_4_);
            this.aoLightValueScratchXZPN = this.getAmbientOcclusionLightValue(p_147751_2_, p_147751_3_, p_147751_4_ - 1);
            this.aoLightValueScratchXZPP = this.getAmbientOcclusionLightValue(p_147751_2_, p_147751_3_, p_147751_4_ + 1);
            this.aoLightValueScratchXYPP = this.getAmbientOcclusionLightValue(p_147751_2_, p_147751_3_ + 1, p_147751_4_);
            this.aoBrightnessXYPN = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_, p_147751_3_ - 1, p_147751_4_);
            this.aoBrightnessXZPN = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_, p_147751_3_, p_147751_4_ - 1);
            this.aoBrightnessXZPP = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_, p_147751_3_, p_147751_4_ + 1);
            this.aoBrightnessXYPP = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_, p_147751_3_ + 1, p_147751_4_);
            final boolean var16 = this.blockAccess.getBlock(p_147751_2_ + 1, p_147751_3_ + 1, p_147751_4_).getCanBlockGrass();
            final boolean var17 = this.blockAccess.getBlock(p_147751_2_ + 1, p_147751_3_ - 1, p_147751_4_).getCanBlockGrass();
            final boolean var18 = this.blockAccess.getBlock(p_147751_2_ + 1, p_147751_3_, p_147751_4_ + 1).getCanBlockGrass();
            final boolean var19 = this.blockAccess.getBlock(p_147751_2_ + 1, p_147751_3_, p_147751_4_ - 1).getCanBlockGrass();
            if (!var17 && !var19) {
                this.aoLightValueScratchXYZPNN = this.aoLightValueScratchXZPN;
                this.aoBrightnessXYZPNN = this.aoBrightnessXZPN;
            }
            else {
                this.aoLightValueScratchXYZPNN = this.getAmbientOcclusionLightValue(p_147751_2_, p_147751_3_ - 1, p_147751_4_ - 1);
                this.aoBrightnessXYZPNN = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_, p_147751_3_ - 1, p_147751_4_ - 1);
            }
            if (!var17 && !var18) {
                this.aoLightValueScratchXYZPNP = this.aoLightValueScratchXZPP;
                this.aoBrightnessXYZPNP = this.aoBrightnessXZPP;
            }
            else {
                this.aoLightValueScratchXYZPNP = this.getAmbientOcclusionLightValue(p_147751_2_, p_147751_3_ - 1, p_147751_4_ + 1);
                this.aoBrightnessXYZPNP = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_, p_147751_3_ - 1, p_147751_4_ + 1);
            }
            if (!var16 && !var19) {
                this.aoLightValueScratchXYZPPN = this.aoLightValueScratchXZPN;
                this.aoBrightnessXYZPPN = this.aoBrightnessXZPN;
            }
            else {
                this.aoLightValueScratchXYZPPN = this.getAmbientOcclusionLightValue(p_147751_2_, p_147751_3_ + 1, p_147751_4_ - 1);
                this.aoBrightnessXYZPPN = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_, p_147751_3_ + 1, p_147751_4_ - 1);
            }
            if (!var16 && !var18) {
                this.aoLightValueScratchXYZPPP = this.aoLightValueScratchXZPP;
                this.aoBrightnessXYZPPP = this.aoBrightnessXZPP;
            }
            else {
                this.aoLightValueScratchXYZPPP = this.getAmbientOcclusionLightValue(p_147751_2_, p_147751_3_ + 1, p_147751_4_ + 1);
                this.aoBrightnessXYZPPP = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_, p_147751_3_ + 1, p_147751_4_ + 1);
            }
            if (this.renderMaxX >= 1.0) {
                --p_147751_2_;
            }
            if (var14 < 0) {
                var14 = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_, p_147751_3_, p_147751_4_);
            }
            int var20 = var14;
            if (this.renderMaxX >= 1.0 || !this.blockAccess.getBlock(p_147751_2_ + 1, p_147751_3_, p_147751_4_).isOpaqueCube()) {
                var20 = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_ + 1, p_147751_3_, p_147751_4_);
            }
            final float var21 = this.getAmbientOcclusionLightValue(p_147751_2_ + 1, p_147751_3_, p_147751_4_);
            var9 = (this.aoLightValueScratchXYPN + this.aoLightValueScratchXYZPNP + var21 + this.aoLightValueScratchXZPP) / 4.0f;
            var10 = (this.aoLightValueScratchXYZPNN + this.aoLightValueScratchXYPN + this.aoLightValueScratchXZPN + var21) / 4.0f;
            var11 = (this.aoLightValueScratchXZPN + var21 + this.aoLightValueScratchXYZPPN + this.aoLightValueScratchXYPP) / 4.0f;
            var12 = (var21 + this.aoLightValueScratchXZPP + this.aoLightValueScratchXYPP + this.aoLightValueScratchXYZPPP) / 4.0f;
            this.brightnessTopLeft = this.getAoBrightness(this.aoBrightnessXYPN, this.aoBrightnessXYZPNP, this.aoBrightnessXZPP, var20);
            this.brightnessTopRight = this.getAoBrightness(this.aoBrightnessXZPP, this.aoBrightnessXYPP, this.aoBrightnessXYZPPP, var20);
            this.brightnessBottomRight = this.getAoBrightness(this.aoBrightnessXZPN, this.aoBrightnessXYZPPN, this.aoBrightnessXYPP, var20);
            this.brightnessBottomLeft = this.getAoBrightness(this.aoBrightnessXYZPNN, this.aoBrightnessXYPN, this.aoBrightnessXZPN, var20);
            if (simpleAO) {
                var10 = var21;
                var11 = var21;
                var12 = var21;
                var9 = var21;
                final int n30 = var20;
                this.brightnessBottomLeft = n30;
                this.brightnessBottomRight = n30;
                this.brightnessTopRight = n30;
                this.brightnessTopLeft = n30;
            }
            if (var13) {
                final float n31 = p_147751_5_ * 0.6f;
                this.colorRedTopRight = n31;
                this.colorRedBottomRight = n31;
                this.colorRedBottomLeft = n31;
                this.colorRedTopLeft = n31;
                final float n32 = p_147751_6_ * 0.6f;
                this.colorGreenTopRight = n32;
                this.colorGreenBottomRight = n32;
                this.colorGreenBottomLeft = n32;
                this.colorGreenTopLeft = n32;
                final float n33 = p_147751_7_ * 0.6f;
                this.colorBlueTopRight = n33;
                this.colorBlueBottomRight = n33;
                this.colorBlueBottomLeft = n33;
                this.colorBlueTopLeft = n33;
            }
            else {
                final float n34 = 0.6f;
                this.colorRedTopRight = n34;
                this.colorRedBottomRight = n34;
                this.colorRedBottomLeft = n34;
                this.colorRedTopLeft = n34;
                final float n35 = 0.6f;
                this.colorGreenTopRight = n35;
                this.colorGreenBottomRight = n35;
                this.colorGreenBottomLeft = n35;
                this.colorGreenTopLeft = n35;
                final float n36 = 0.6f;
                this.colorBlueTopRight = n36;
                this.colorBlueBottomRight = n36;
                this.colorBlueBottomLeft = n36;
                this.colorBlueTopLeft = n36;
            }
            this.colorRedTopLeft *= var9;
            this.colorGreenTopLeft *= var9;
            this.colorBlueTopLeft *= var9;
            this.colorRedBottomLeft *= var10;
            this.colorGreenBottomLeft *= var10;
            this.colorBlueBottomLeft *= var10;
            this.colorRedBottomRight *= var11;
            this.colorGreenBottomRight *= var11;
            this.colorBlueBottomRight *= var11;
            this.colorRedTopRight *= var12;
            this.colorGreenTopRight *= var12;
            this.colorBlueTopRight *= var12;
            IIcon var22 = this.getBlockIcon(p_147751_1_, this.blockAccess, p_147751_2_, p_147751_3_, p_147751_4_, 5);
            if (betterGrass) {
                var22 = this.fixAoSideGrassTexture(var22, p_147751_2_, p_147751_3_, p_147751_4_, 5, p_147751_5_, p_147751_6_, p_147751_7_);
            }
            this.renderFaceXPos(p_147751_1_, p_147751_2_, p_147751_3_, p_147751_4_, var22);
            if (defaultTexture && RenderBlocks.fancyGrass && var22 == TextureUtils.iconGrassSide && !this.hasOverrideBlockTexture()) {
                this.colorRedTopLeft *= p_147751_5_;
                this.colorRedBottomLeft *= p_147751_5_;
                this.colorRedBottomRight *= p_147751_5_;
                this.colorRedTopRight *= p_147751_5_;
                this.colorGreenTopLeft *= p_147751_6_;
                this.colorGreenBottomLeft *= p_147751_6_;
                this.colorGreenBottomRight *= p_147751_6_;
                this.colorGreenTopRight *= p_147751_6_;
                this.colorBlueTopLeft *= p_147751_7_;
                this.colorBlueBottomLeft *= p_147751_7_;
                this.colorBlueBottomRight *= p_147751_7_;
                this.colorBlueTopRight *= p_147751_7_;
                this.renderFaceXPos(p_147751_1_, p_147751_2_, p_147751_3_, p_147751_4_, BlockGrass.func_149990_e());
            }
            var8 = true;
        }
        this.enableAO = false;
        return var8;
    }
    
    public boolean renderStandardBlockWithAmbientOcclusionPartial(final Block p_147808_1_, int p_147808_2_, int p_147808_3_, int p_147808_4_, final float p_147808_5_, final float p_147808_6_, final float p_147808_7_) {
        this.enableAO = true;
        boolean var8 = false;
        float var9 = 0.0f;
        float var10 = 0.0f;
        float var11 = 0.0f;
        float var12 = 0.0f;
        boolean var13 = true;
        final int var14 = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_, p_147808_3_, p_147808_4_);
        final Tessellator var15 = Tessellator.instance;
        var15.setBrightness(983055);
        if (p_147808_1_ == Blocks.grass) {
            var13 = false;
        }
        else if (this.hasOverrideBlockTexture()) {
            var13 = false;
        }
        if (this.renderAllFaces || p_147808_1_.shouldSideBeRendered(this.blockAccess, p_147808_2_, p_147808_3_ - 1, p_147808_4_, 0)) {
            if (this.renderMinY <= 0.0) {
                --p_147808_3_;
            }
            this.aoBrightnessXYNN = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_ - 1, p_147808_3_, p_147808_4_);
            this.aoBrightnessYZNN = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_, p_147808_3_, p_147808_4_ - 1);
            this.aoBrightnessYZNP = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_, p_147808_3_, p_147808_4_ + 1);
            this.aoBrightnessXYPN = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_ + 1, p_147808_3_, p_147808_4_);
            this.aoLightValueScratchXYNN = this.getAmbientOcclusionLightValue(p_147808_2_ - 1, p_147808_3_, p_147808_4_);
            this.aoLightValueScratchYZNN = this.getAmbientOcclusionLightValue(p_147808_2_, p_147808_3_, p_147808_4_ - 1);
            this.aoLightValueScratchYZNP = this.getAmbientOcclusionLightValue(p_147808_2_, p_147808_3_, p_147808_4_ + 1);
            this.aoLightValueScratchXYPN = this.getAmbientOcclusionLightValue(p_147808_2_ + 1, p_147808_3_, p_147808_4_);
            final boolean var16 = this.blockAccess.getBlock(p_147808_2_ + 1, p_147808_3_ - 1, p_147808_4_).getCanBlockGrass();
            final boolean var17 = this.blockAccess.getBlock(p_147808_2_ - 1, p_147808_3_ - 1, p_147808_4_).getCanBlockGrass();
            final boolean var18 = this.blockAccess.getBlock(p_147808_2_, p_147808_3_ - 1, p_147808_4_ + 1).getCanBlockGrass();
            final boolean var19 = this.blockAccess.getBlock(p_147808_2_, p_147808_3_ - 1, p_147808_4_ - 1).getCanBlockGrass();
            if (!var19 && !var17) {
                this.aoLightValueScratchXYZNNN = this.aoLightValueScratchXYNN;
                this.aoBrightnessXYZNNN = this.aoBrightnessXYNN;
            }
            else {
                this.aoLightValueScratchXYZNNN = this.getAmbientOcclusionLightValue(p_147808_2_ - 1, p_147808_3_, p_147808_4_ - 1);
                this.aoBrightnessXYZNNN = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_ - 1, p_147808_3_, p_147808_4_ - 1);
            }
            if (!var18 && !var17) {
                this.aoLightValueScratchXYZNNP = this.aoLightValueScratchXYNN;
                this.aoBrightnessXYZNNP = this.aoBrightnessXYNN;
            }
            else {
                this.aoLightValueScratchXYZNNP = this.getAmbientOcclusionLightValue(p_147808_2_ - 1, p_147808_3_, p_147808_4_ + 1);
                this.aoBrightnessXYZNNP = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_ - 1, p_147808_3_, p_147808_4_ + 1);
            }
            if (!var19 && !var16) {
                this.aoLightValueScratchXYZPNN = this.aoLightValueScratchXYPN;
                this.aoBrightnessXYZPNN = this.aoBrightnessXYPN;
            }
            else {
                this.aoLightValueScratchXYZPNN = this.getAmbientOcclusionLightValue(p_147808_2_ + 1, p_147808_3_, p_147808_4_ - 1);
                this.aoBrightnessXYZPNN = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_ + 1, p_147808_3_, p_147808_4_ - 1);
            }
            if (!var18 && !var16) {
                this.aoLightValueScratchXYZPNP = this.aoLightValueScratchXYPN;
                this.aoBrightnessXYZPNP = this.aoBrightnessXYPN;
            }
            else {
                this.aoLightValueScratchXYZPNP = this.getAmbientOcclusionLightValue(p_147808_2_ + 1, p_147808_3_, p_147808_4_ + 1);
                this.aoBrightnessXYZPNP = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_ + 1, p_147808_3_, p_147808_4_ + 1);
            }
            if (this.renderMinY <= 0.0) {
                ++p_147808_3_;
            }
            int var20 = var14;
            if (this.renderMinY <= 0.0 || !this.blockAccess.getBlock(p_147808_2_, p_147808_3_ - 1, p_147808_4_).isOpaqueCube()) {
                var20 = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_, p_147808_3_ - 1, p_147808_4_);
            }
            final float var21 = this.getAmbientOcclusionLightValue(p_147808_2_, p_147808_3_ - 1, p_147808_4_);
            var9 = (this.aoLightValueScratchXYZNNP + this.aoLightValueScratchXYNN + this.aoLightValueScratchYZNP + var21) / 4.0f;
            var12 = (this.aoLightValueScratchYZNP + var21 + this.aoLightValueScratchXYZPNP + this.aoLightValueScratchXYPN) / 4.0f;
            var11 = (var21 + this.aoLightValueScratchYZNN + this.aoLightValueScratchXYPN + this.aoLightValueScratchXYZPNN) / 4.0f;
            var10 = (this.aoLightValueScratchXYNN + this.aoLightValueScratchXYZNNN + var21 + this.aoLightValueScratchYZNN) / 4.0f;
            this.brightnessTopLeft = this.getAoBrightness(this.aoBrightnessXYZNNP, this.aoBrightnessXYNN, this.aoBrightnessYZNP, var20);
            this.brightnessTopRight = this.getAoBrightness(this.aoBrightnessYZNP, this.aoBrightnessXYZPNP, this.aoBrightnessXYPN, var20);
            this.brightnessBottomRight = this.getAoBrightness(this.aoBrightnessYZNN, this.aoBrightnessXYPN, this.aoBrightnessXYZPNN, var20);
            this.brightnessBottomLeft = this.getAoBrightness(this.aoBrightnessXYNN, this.aoBrightnessXYZNNN, this.aoBrightnessYZNN, var20);
            if (var13) {
                final float n = p_147808_5_ * 0.5f;
                this.colorRedTopRight = n;
                this.colorRedBottomRight = n;
                this.colorRedBottomLeft = n;
                this.colorRedTopLeft = n;
                final float n2 = p_147808_6_ * 0.5f;
                this.colorGreenTopRight = n2;
                this.colorGreenBottomRight = n2;
                this.colorGreenBottomLeft = n2;
                this.colorGreenTopLeft = n2;
                final float n3 = p_147808_7_ * 0.5f;
                this.colorBlueTopRight = n3;
                this.colorBlueBottomRight = n3;
                this.colorBlueBottomLeft = n3;
                this.colorBlueTopLeft = n3;
            }
            else {
                final float n4 = 0.5f;
                this.colorRedTopRight = n4;
                this.colorRedBottomRight = n4;
                this.colorRedBottomLeft = n4;
                this.colorRedTopLeft = n4;
                final float n5 = 0.5f;
                this.colorGreenTopRight = n5;
                this.colorGreenBottomRight = n5;
                this.colorGreenBottomLeft = n5;
                this.colorGreenTopLeft = n5;
                final float n6 = 0.5f;
                this.colorBlueTopRight = n6;
                this.colorBlueBottomRight = n6;
                this.colorBlueBottomLeft = n6;
                this.colorBlueTopLeft = n6;
            }
            this.colorRedTopLeft *= var9;
            this.colorGreenTopLeft *= var9;
            this.colorBlueTopLeft *= var9;
            this.colorRedBottomLeft *= var10;
            this.colorGreenBottomLeft *= var10;
            this.colorBlueBottomLeft *= var10;
            this.colorRedBottomRight *= var11;
            this.colorGreenBottomRight *= var11;
            this.colorBlueBottomRight *= var11;
            this.colorRedTopRight *= var12;
            this.colorGreenTopRight *= var12;
            this.colorBlueTopRight *= var12;
            this.renderFaceYNeg(p_147808_1_, p_147808_2_, p_147808_3_, p_147808_4_, this.getBlockIcon(p_147808_1_, this.blockAccess, p_147808_2_, p_147808_3_, p_147808_4_, 0));
            var8 = true;
        }
        if (this.renderAllFaces || p_147808_1_.shouldSideBeRendered(this.blockAccess, p_147808_2_, p_147808_3_ + 1, p_147808_4_, 1)) {
            if (this.renderMaxY >= 1.0) {
                ++p_147808_3_;
            }
            this.aoBrightnessXYNP = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_ - 1, p_147808_3_, p_147808_4_);
            this.aoBrightnessXYPP = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_ + 1, p_147808_3_, p_147808_4_);
            this.aoBrightnessYZPN = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_, p_147808_3_, p_147808_4_ - 1);
            this.aoBrightnessYZPP = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_, p_147808_3_, p_147808_4_ + 1);
            this.aoLightValueScratchXYNP = this.getAmbientOcclusionLightValue(p_147808_2_ - 1, p_147808_3_, p_147808_4_);
            this.aoLightValueScratchXYPP = this.getAmbientOcclusionLightValue(p_147808_2_ + 1, p_147808_3_, p_147808_4_);
            this.aoLightValueScratchYZPN = this.getAmbientOcclusionLightValue(p_147808_2_, p_147808_3_, p_147808_4_ - 1);
            this.aoLightValueScratchYZPP = this.getAmbientOcclusionLightValue(p_147808_2_, p_147808_3_, p_147808_4_ + 1);
            final boolean var16 = this.blockAccess.getBlock(p_147808_2_ + 1, p_147808_3_ + 1, p_147808_4_).getCanBlockGrass();
            final boolean var17 = this.blockAccess.getBlock(p_147808_2_ - 1, p_147808_3_ + 1, p_147808_4_).getCanBlockGrass();
            final boolean var18 = this.blockAccess.getBlock(p_147808_2_, p_147808_3_ + 1, p_147808_4_ + 1).getCanBlockGrass();
            final boolean var19 = this.blockAccess.getBlock(p_147808_2_, p_147808_3_ + 1, p_147808_4_ - 1).getCanBlockGrass();
            if (!var19 && !var17) {
                this.aoLightValueScratchXYZNPN = this.aoLightValueScratchXYNP;
                this.aoBrightnessXYZNPN = this.aoBrightnessXYNP;
            }
            else {
                this.aoLightValueScratchXYZNPN = this.getAmbientOcclusionLightValue(p_147808_2_ - 1, p_147808_3_, p_147808_4_ - 1);
                this.aoBrightnessXYZNPN = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_ - 1, p_147808_3_, p_147808_4_ - 1);
            }
            if (!var19 && !var16) {
                this.aoLightValueScratchXYZPPN = this.aoLightValueScratchXYPP;
                this.aoBrightnessXYZPPN = this.aoBrightnessXYPP;
            }
            else {
                this.aoLightValueScratchXYZPPN = this.getAmbientOcclusionLightValue(p_147808_2_ + 1, p_147808_3_, p_147808_4_ - 1);
                this.aoBrightnessXYZPPN = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_ + 1, p_147808_3_, p_147808_4_ - 1);
            }
            if (!var18 && !var17) {
                this.aoLightValueScratchXYZNPP = this.aoLightValueScratchXYNP;
                this.aoBrightnessXYZNPP = this.aoBrightnessXYNP;
            }
            else {
                this.aoLightValueScratchXYZNPP = this.getAmbientOcclusionLightValue(p_147808_2_ - 1, p_147808_3_, p_147808_4_ + 1);
                this.aoBrightnessXYZNPP = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_ - 1, p_147808_3_, p_147808_4_ + 1);
            }
            if (!var18 && !var16) {
                this.aoLightValueScratchXYZPPP = this.aoLightValueScratchXYPP;
                this.aoBrightnessXYZPPP = this.aoBrightnessXYPP;
            }
            else {
                this.aoLightValueScratchXYZPPP = this.getAmbientOcclusionLightValue(p_147808_2_ + 1, p_147808_3_, p_147808_4_ + 1);
                this.aoBrightnessXYZPPP = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_ + 1, p_147808_3_, p_147808_4_ + 1);
            }
            if (this.renderMaxY >= 1.0) {
                --p_147808_3_;
            }
            int var20 = var14;
            if (this.renderMaxY >= 1.0 || !this.blockAccess.getBlock(p_147808_2_, p_147808_3_ + 1, p_147808_4_).isOpaqueCube()) {
                var20 = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_, p_147808_3_ + 1, p_147808_4_);
            }
            final float var21 = this.getAmbientOcclusionLightValue(p_147808_2_, p_147808_3_ + 1, p_147808_4_);
            var12 = (this.aoLightValueScratchXYZNPP + this.aoLightValueScratchXYNP + this.aoLightValueScratchYZPP + var21) / 4.0f;
            var9 = (this.aoLightValueScratchYZPP + var21 + this.aoLightValueScratchXYZPPP + this.aoLightValueScratchXYPP) / 4.0f;
            var10 = (var21 + this.aoLightValueScratchYZPN + this.aoLightValueScratchXYPP + this.aoLightValueScratchXYZPPN) / 4.0f;
            var11 = (this.aoLightValueScratchXYNP + this.aoLightValueScratchXYZNPN + var21 + this.aoLightValueScratchYZPN) / 4.0f;
            this.brightnessTopRight = this.getAoBrightness(this.aoBrightnessXYZNPP, this.aoBrightnessXYNP, this.aoBrightnessYZPP, var20);
            this.brightnessTopLeft = this.getAoBrightness(this.aoBrightnessYZPP, this.aoBrightnessXYZPPP, this.aoBrightnessXYPP, var20);
            this.brightnessBottomLeft = this.getAoBrightness(this.aoBrightnessYZPN, this.aoBrightnessXYPP, this.aoBrightnessXYZPPN, var20);
            this.brightnessBottomRight = this.getAoBrightness(this.aoBrightnessXYNP, this.aoBrightnessXYZNPN, this.aoBrightnessYZPN, var20);
            this.colorRedTopRight = p_147808_5_;
            this.colorRedBottomRight = p_147808_5_;
            this.colorRedBottomLeft = p_147808_5_;
            this.colorRedTopLeft = p_147808_5_;
            this.colorGreenTopRight = p_147808_6_;
            this.colorGreenBottomRight = p_147808_6_;
            this.colorGreenBottomLeft = p_147808_6_;
            this.colorGreenTopLeft = p_147808_6_;
            this.colorBlueTopRight = p_147808_7_;
            this.colorBlueBottomRight = p_147808_7_;
            this.colorBlueBottomLeft = p_147808_7_;
            this.colorBlueTopLeft = p_147808_7_;
            this.colorRedTopLeft *= var9;
            this.colorGreenTopLeft *= var9;
            this.colorBlueTopLeft *= var9;
            this.colorRedBottomLeft *= var10;
            this.colorGreenBottomLeft *= var10;
            this.colorBlueBottomLeft *= var10;
            this.colorRedBottomRight *= var11;
            this.colorGreenBottomRight *= var11;
            this.colorBlueBottomRight *= var11;
            this.colorRedTopRight *= var12;
            this.colorGreenTopRight *= var12;
            this.colorBlueTopRight *= var12;
            this.renderFaceYPos(p_147808_1_, p_147808_2_, p_147808_3_, p_147808_4_, this.getBlockIcon(p_147808_1_, this.blockAccess, p_147808_2_, p_147808_3_, p_147808_4_, 1));
            var8 = true;
        }
        if (this.renderAllFaces || p_147808_1_.shouldSideBeRendered(this.blockAccess, p_147808_2_, p_147808_3_, p_147808_4_ - 1, 2)) {
            if (this.renderMinZ <= 0.0) {
                --p_147808_4_;
            }
            this.aoLightValueScratchXZNN = this.getAmbientOcclusionLightValue(p_147808_2_ - 1, p_147808_3_, p_147808_4_);
            this.aoLightValueScratchYZNN = this.getAmbientOcclusionLightValue(p_147808_2_, p_147808_3_ - 1, p_147808_4_);
            this.aoLightValueScratchYZPN = this.getAmbientOcclusionLightValue(p_147808_2_, p_147808_3_ + 1, p_147808_4_);
            this.aoLightValueScratchXZPN = this.getAmbientOcclusionLightValue(p_147808_2_ + 1, p_147808_3_, p_147808_4_);
            this.aoBrightnessXZNN = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_ - 1, p_147808_3_, p_147808_4_);
            this.aoBrightnessYZNN = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_, p_147808_3_ - 1, p_147808_4_);
            this.aoBrightnessYZPN = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_, p_147808_3_ + 1, p_147808_4_);
            this.aoBrightnessXZPN = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_ + 1, p_147808_3_, p_147808_4_);
            final boolean var16 = this.blockAccess.getBlock(p_147808_2_ + 1, p_147808_3_, p_147808_4_ - 1).getCanBlockGrass();
            final boolean var17 = this.blockAccess.getBlock(p_147808_2_ - 1, p_147808_3_, p_147808_4_ - 1).getCanBlockGrass();
            final boolean var18 = this.blockAccess.getBlock(p_147808_2_, p_147808_3_ + 1, p_147808_4_ - 1).getCanBlockGrass();
            final boolean var19 = this.blockAccess.getBlock(p_147808_2_, p_147808_3_ - 1, p_147808_4_ - 1).getCanBlockGrass();
            if (!var17 && !var19) {
                this.aoLightValueScratchXYZNNN = this.aoLightValueScratchXZNN;
                this.aoBrightnessXYZNNN = this.aoBrightnessXZNN;
            }
            else {
                this.aoLightValueScratchXYZNNN = this.getAmbientOcclusionLightValue(p_147808_2_ - 1, p_147808_3_ - 1, p_147808_4_);
                this.aoBrightnessXYZNNN = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_ - 1, p_147808_3_ - 1, p_147808_4_);
            }
            if (!var17 && !var18) {
                this.aoLightValueScratchXYZNPN = this.aoLightValueScratchXZNN;
                this.aoBrightnessXYZNPN = this.aoBrightnessXZNN;
            }
            else {
                this.aoLightValueScratchXYZNPN = this.getAmbientOcclusionLightValue(p_147808_2_ - 1, p_147808_3_ + 1, p_147808_4_);
                this.aoBrightnessXYZNPN = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_ - 1, p_147808_3_ + 1, p_147808_4_);
            }
            if (!var16 && !var19) {
                this.aoLightValueScratchXYZPNN = this.aoLightValueScratchXZPN;
                this.aoBrightnessXYZPNN = this.aoBrightnessXZPN;
            }
            else {
                this.aoLightValueScratchXYZPNN = this.getAmbientOcclusionLightValue(p_147808_2_ + 1, p_147808_3_ - 1, p_147808_4_);
                this.aoBrightnessXYZPNN = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_ + 1, p_147808_3_ - 1, p_147808_4_);
            }
            if (!var16 && !var18) {
                this.aoLightValueScratchXYZPPN = this.aoLightValueScratchXZPN;
                this.aoBrightnessXYZPPN = this.aoBrightnessXZPN;
            }
            else {
                this.aoLightValueScratchXYZPPN = this.getAmbientOcclusionLightValue(p_147808_2_ + 1, p_147808_3_ + 1, p_147808_4_);
                this.aoBrightnessXYZPPN = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_ + 1, p_147808_3_ + 1, p_147808_4_);
            }
            if (this.renderMinZ <= 0.0) {
                ++p_147808_4_;
            }
            int var20 = var14;
            if (this.renderMinZ <= 0.0 || !this.blockAccess.getBlock(p_147808_2_, p_147808_3_, p_147808_4_ - 1).isOpaqueCube()) {
                var20 = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_, p_147808_3_, p_147808_4_ - 1);
            }
            final float var21 = this.getAmbientOcclusionLightValue(p_147808_2_, p_147808_3_, p_147808_4_ - 1);
            final float var22 = (this.aoLightValueScratchXZNN + this.aoLightValueScratchXYZNPN + var21 + this.aoLightValueScratchYZPN) / 4.0f;
            final float var23 = (var21 + this.aoLightValueScratchYZPN + this.aoLightValueScratchXZPN + this.aoLightValueScratchXYZPPN) / 4.0f;
            final float var24 = (this.aoLightValueScratchYZNN + var21 + this.aoLightValueScratchXYZPNN + this.aoLightValueScratchXZPN) / 4.0f;
            final float var25 = (this.aoLightValueScratchXYZNNN + this.aoLightValueScratchXZNN + this.aoLightValueScratchYZNN + var21) / 4.0f;
            var9 = (float)(var22 * this.renderMaxY * (1.0 - this.renderMinX) + var23 * this.renderMaxY * this.renderMinX + var24 * (1.0 - this.renderMaxY) * this.renderMinX + var25 * (1.0 - this.renderMaxY) * (1.0 - this.renderMinX));
            var10 = (float)(var22 * this.renderMaxY * (1.0 - this.renderMaxX) + var23 * this.renderMaxY * this.renderMaxX + var24 * (1.0 - this.renderMaxY) * this.renderMaxX + var25 * (1.0 - this.renderMaxY) * (1.0 - this.renderMaxX));
            var11 = (float)(var22 * this.renderMinY * (1.0 - this.renderMaxX) + var23 * this.renderMinY * this.renderMaxX + var24 * (1.0 - this.renderMinY) * this.renderMaxX + var25 * (1.0 - this.renderMinY) * (1.0 - this.renderMaxX));
            var12 = (float)(var22 * this.renderMinY * (1.0 - this.renderMinX) + var23 * this.renderMinY * this.renderMinX + var24 * (1.0 - this.renderMinY) * this.renderMinX + var25 * (1.0 - this.renderMinY) * (1.0 - this.renderMinX));
            final int var26 = this.getAoBrightness(this.aoBrightnessXZNN, this.aoBrightnessXYZNPN, this.aoBrightnessYZPN, var20);
            final int var27 = this.getAoBrightness(this.aoBrightnessYZPN, this.aoBrightnessXZPN, this.aoBrightnessXYZPPN, var20);
            final int var28 = this.getAoBrightness(this.aoBrightnessYZNN, this.aoBrightnessXYZPNN, this.aoBrightnessXZPN, var20);
            final int var29 = this.getAoBrightness(this.aoBrightnessXYZNNN, this.aoBrightnessXZNN, this.aoBrightnessYZNN, var20);
            this.brightnessTopLeft = this.mixAoBrightness(var26, var27, var28, var29, this.renderMaxY * (1.0 - this.renderMinX), this.renderMaxY * this.renderMinX, (1.0 - this.renderMaxY) * this.renderMinX, (1.0 - this.renderMaxY) * (1.0 - this.renderMinX));
            this.brightnessBottomLeft = this.mixAoBrightness(var26, var27, var28, var29, this.renderMaxY * (1.0 - this.renderMaxX), this.renderMaxY * this.renderMaxX, (1.0 - this.renderMaxY) * this.renderMaxX, (1.0 - this.renderMaxY) * (1.0 - this.renderMaxX));
            this.brightnessBottomRight = this.mixAoBrightness(var26, var27, var28, var29, this.renderMinY * (1.0 - this.renderMaxX), this.renderMinY * this.renderMaxX, (1.0 - this.renderMinY) * this.renderMaxX, (1.0 - this.renderMinY) * (1.0 - this.renderMaxX));
            this.brightnessTopRight = this.mixAoBrightness(var26, var27, var28, var29, this.renderMinY * (1.0 - this.renderMinX), this.renderMinY * this.renderMinX, (1.0 - this.renderMinY) * this.renderMinX, (1.0 - this.renderMinY) * (1.0 - this.renderMinX));
            if (var13) {
                final float n7 = p_147808_5_ * 0.8f;
                this.colorRedTopRight = n7;
                this.colorRedBottomRight = n7;
                this.colorRedBottomLeft = n7;
                this.colorRedTopLeft = n7;
                final float n8 = p_147808_6_ * 0.8f;
                this.colorGreenTopRight = n8;
                this.colorGreenBottomRight = n8;
                this.colorGreenBottomLeft = n8;
                this.colorGreenTopLeft = n8;
                final float n9 = p_147808_7_ * 0.8f;
                this.colorBlueTopRight = n9;
                this.colorBlueBottomRight = n9;
                this.colorBlueBottomLeft = n9;
                this.colorBlueTopLeft = n9;
            }
            else {
                final float n10 = 0.8f;
                this.colorRedTopRight = n10;
                this.colorRedBottomRight = n10;
                this.colorRedBottomLeft = n10;
                this.colorRedTopLeft = n10;
                final float n11 = 0.8f;
                this.colorGreenTopRight = n11;
                this.colorGreenBottomRight = n11;
                this.colorGreenBottomLeft = n11;
                this.colorGreenTopLeft = n11;
                final float n12 = 0.8f;
                this.colorBlueTopRight = n12;
                this.colorBlueBottomRight = n12;
                this.colorBlueBottomLeft = n12;
                this.colorBlueTopLeft = n12;
            }
            this.colorRedTopLeft *= var9;
            this.colorGreenTopLeft *= var9;
            this.colorBlueTopLeft *= var9;
            this.colorRedBottomLeft *= var10;
            this.colorGreenBottomLeft *= var10;
            this.colorBlueBottomLeft *= var10;
            this.colorRedBottomRight *= var11;
            this.colorGreenBottomRight *= var11;
            this.colorBlueBottomRight *= var11;
            this.colorRedTopRight *= var12;
            this.colorGreenTopRight *= var12;
            this.colorBlueTopRight *= var12;
            final IIcon var30 = this.getBlockIcon(p_147808_1_, this.blockAccess, p_147808_2_, p_147808_3_, p_147808_4_, 2);
            this.renderFaceZNeg(p_147808_1_, p_147808_2_, p_147808_3_, p_147808_4_, var30);
            if (RenderBlocks.fancyGrass && var30.getIconName().equals("grass_side") && !this.hasOverrideBlockTexture()) {
                this.colorRedTopLeft *= p_147808_5_;
                this.colorRedBottomLeft *= p_147808_5_;
                this.colorRedBottomRight *= p_147808_5_;
                this.colorRedTopRight *= p_147808_5_;
                this.colorGreenTopLeft *= p_147808_6_;
                this.colorGreenBottomLeft *= p_147808_6_;
                this.colorGreenBottomRight *= p_147808_6_;
                this.colorGreenTopRight *= p_147808_6_;
                this.colorBlueTopLeft *= p_147808_7_;
                this.colorBlueBottomLeft *= p_147808_7_;
                this.colorBlueBottomRight *= p_147808_7_;
                this.colorBlueTopRight *= p_147808_7_;
                this.renderFaceZNeg(p_147808_1_, p_147808_2_, p_147808_3_, p_147808_4_, BlockGrass.func_149990_e());
            }
            var8 = true;
        }
        if (this.renderAllFaces || p_147808_1_.shouldSideBeRendered(this.blockAccess, p_147808_2_, p_147808_3_, p_147808_4_ + 1, 3)) {
            if (this.renderMaxZ >= 1.0) {
                ++p_147808_4_;
            }
            this.aoLightValueScratchXZNP = this.getAmbientOcclusionLightValue(p_147808_2_ - 1, p_147808_3_, p_147808_4_);
            this.aoLightValueScratchXZPP = this.getAmbientOcclusionLightValue(p_147808_2_ + 1, p_147808_3_, p_147808_4_);
            this.aoLightValueScratchYZNP = this.getAmbientOcclusionLightValue(p_147808_2_, p_147808_3_ - 1, p_147808_4_);
            this.aoLightValueScratchYZPP = this.getAmbientOcclusionLightValue(p_147808_2_, p_147808_3_ + 1, p_147808_4_);
            this.aoBrightnessXZNP = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_ - 1, p_147808_3_, p_147808_4_);
            this.aoBrightnessXZPP = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_ + 1, p_147808_3_, p_147808_4_);
            this.aoBrightnessYZNP = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_, p_147808_3_ - 1, p_147808_4_);
            this.aoBrightnessYZPP = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_, p_147808_3_ + 1, p_147808_4_);
            final boolean var16 = this.blockAccess.getBlock(p_147808_2_ + 1, p_147808_3_, p_147808_4_ + 1).getCanBlockGrass();
            final boolean var17 = this.blockAccess.getBlock(p_147808_2_ - 1, p_147808_3_, p_147808_4_ + 1).getCanBlockGrass();
            final boolean var18 = this.blockAccess.getBlock(p_147808_2_, p_147808_3_ + 1, p_147808_4_ + 1).getCanBlockGrass();
            final boolean var19 = this.blockAccess.getBlock(p_147808_2_, p_147808_3_ - 1, p_147808_4_ + 1).getCanBlockGrass();
            if (!var17 && !var19) {
                this.aoLightValueScratchXYZNNP = this.aoLightValueScratchXZNP;
                this.aoBrightnessXYZNNP = this.aoBrightnessXZNP;
            }
            else {
                this.aoLightValueScratchXYZNNP = this.getAmbientOcclusionLightValue(p_147808_2_ - 1, p_147808_3_ - 1, p_147808_4_);
                this.aoBrightnessXYZNNP = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_ - 1, p_147808_3_ - 1, p_147808_4_);
            }
            if (!var17 && !var18) {
                this.aoLightValueScratchXYZNPP = this.aoLightValueScratchXZNP;
                this.aoBrightnessXYZNPP = this.aoBrightnessXZNP;
            }
            else {
                this.aoLightValueScratchXYZNPP = this.getAmbientOcclusionLightValue(p_147808_2_ - 1, p_147808_3_ + 1, p_147808_4_);
                this.aoBrightnessXYZNPP = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_ - 1, p_147808_3_ + 1, p_147808_4_);
            }
            if (!var16 && !var19) {
                this.aoLightValueScratchXYZPNP = this.aoLightValueScratchXZPP;
                this.aoBrightnessXYZPNP = this.aoBrightnessXZPP;
            }
            else {
                this.aoLightValueScratchXYZPNP = this.getAmbientOcclusionLightValue(p_147808_2_ + 1, p_147808_3_ - 1, p_147808_4_);
                this.aoBrightnessXYZPNP = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_ + 1, p_147808_3_ - 1, p_147808_4_);
            }
            if (!var16 && !var18) {
                this.aoLightValueScratchXYZPPP = this.aoLightValueScratchXZPP;
                this.aoBrightnessXYZPPP = this.aoBrightnessXZPP;
            }
            else {
                this.aoLightValueScratchXYZPPP = this.getAmbientOcclusionLightValue(p_147808_2_ + 1, p_147808_3_ + 1, p_147808_4_);
                this.aoBrightnessXYZPPP = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_ + 1, p_147808_3_ + 1, p_147808_4_);
            }
            if (this.renderMaxZ >= 1.0) {
                --p_147808_4_;
            }
            int var20 = var14;
            if (this.renderMaxZ >= 1.0 || !this.blockAccess.getBlock(p_147808_2_, p_147808_3_, p_147808_4_ + 1).isOpaqueCube()) {
                var20 = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_, p_147808_3_, p_147808_4_ + 1);
            }
            final float var21 = this.getAmbientOcclusionLightValue(p_147808_2_, p_147808_3_, p_147808_4_ + 1);
            final float var22 = (this.aoLightValueScratchXZNP + this.aoLightValueScratchXYZNPP + var21 + this.aoLightValueScratchYZPP) / 4.0f;
            final float var23 = (var21 + this.aoLightValueScratchYZPP + this.aoLightValueScratchXZPP + this.aoLightValueScratchXYZPPP) / 4.0f;
            final float var24 = (this.aoLightValueScratchYZNP + var21 + this.aoLightValueScratchXYZPNP + this.aoLightValueScratchXZPP) / 4.0f;
            final float var25 = (this.aoLightValueScratchXYZNNP + this.aoLightValueScratchXZNP + this.aoLightValueScratchYZNP + var21) / 4.0f;
            var9 = (float)(var22 * this.renderMaxY * (1.0 - this.renderMinX) + var23 * this.renderMaxY * this.renderMinX + var24 * (1.0 - this.renderMaxY) * this.renderMinX + var25 * (1.0 - this.renderMaxY) * (1.0 - this.renderMinX));
            var10 = (float)(var22 * this.renderMinY * (1.0 - this.renderMinX) + var23 * this.renderMinY * this.renderMinX + var24 * (1.0 - this.renderMinY) * this.renderMinX + var25 * (1.0 - this.renderMinY) * (1.0 - this.renderMinX));
            var11 = (float)(var22 * this.renderMinY * (1.0 - this.renderMaxX) + var23 * this.renderMinY * this.renderMaxX + var24 * (1.0 - this.renderMinY) * this.renderMaxX + var25 * (1.0 - this.renderMinY) * (1.0 - this.renderMaxX));
            var12 = (float)(var22 * this.renderMaxY * (1.0 - this.renderMaxX) + var23 * this.renderMaxY * this.renderMaxX + var24 * (1.0 - this.renderMaxY) * this.renderMaxX + var25 * (1.0 - this.renderMaxY) * (1.0 - this.renderMaxX));
            final int var26 = this.getAoBrightness(this.aoBrightnessXZNP, this.aoBrightnessXYZNPP, this.aoBrightnessYZPP, var20);
            final int var27 = this.getAoBrightness(this.aoBrightnessYZPP, this.aoBrightnessXZPP, this.aoBrightnessXYZPPP, var20);
            final int var28 = this.getAoBrightness(this.aoBrightnessYZNP, this.aoBrightnessXYZPNP, this.aoBrightnessXZPP, var20);
            final int var29 = this.getAoBrightness(this.aoBrightnessXYZNNP, this.aoBrightnessXZNP, this.aoBrightnessYZNP, var20);
            this.brightnessTopLeft = this.mixAoBrightness(var26, var29, var28, var27, this.renderMaxY * (1.0 - this.renderMinX), (1.0 - this.renderMaxY) * (1.0 - this.renderMinX), (1.0 - this.renderMaxY) * this.renderMinX, this.renderMaxY * this.renderMinX);
            this.brightnessBottomLeft = this.mixAoBrightness(var26, var29, var28, var27, this.renderMinY * (1.0 - this.renderMinX), (1.0 - this.renderMinY) * (1.0 - this.renderMinX), (1.0 - this.renderMinY) * this.renderMinX, this.renderMinY * this.renderMinX);
            this.brightnessBottomRight = this.mixAoBrightness(var26, var29, var28, var27, this.renderMinY * (1.0 - this.renderMaxX), (1.0 - this.renderMinY) * (1.0 - this.renderMaxX), (1.0 - this.renderMinY) * this.renderMaxX, this.renderMinY * this.renderMaxX);
            this.brightnessTopRight = this.mixAoBrightness(var26, var29, var28, var27, this.renderMaxY * (1.0 - this.renderMaxX), (1.0 - this.renderMaxY) * (1.0 - this.renderMaxX), (1.0 - this.renderMaxY) * this.renderMaxX, this.renderMaxY * this.renderMaxX);
            if (var13) {
                final float n13 = p_147808_5_ * 0.8f;
                this.colorRedTopRight = n13;
                this.colorRedBottomRight = n13;
                this.colorRedBottomLeft = n13;
                this.colorRedTopLeft = n13;
                final float n14 = p_147808_6_ * 0.8f;
                this.colorGreenTopRight = n14;
                this.colorGreenBottomRight = n14;
                this.colorGreenBottomLeft = n14;
                this.colorGreenTopLeft = n14;
                final float n15 = p_147808_7_ * 0.8f;
                this.colorBlueTopRight = n15;
                this.colorBlueBottomRight = n15;
                this.colorBlueBottomLeft = n15;
                this.colorBlueTopLeft = n15;
            }
            else {
                final float n16 = 0.8f;
                this.colorRedTopRight = n16;
                this.colorRedBottomRight = n16;
                this.colorRedBottomLeft = n16;
                this.colorRedTopLeft = n16;
                final float n17 = 0.8f;
                this.colorGreenTopRight = n17;
                this.colorGreenBottomRight = n17;
                this.colorGreenBottomLeft = n17;
                this.colorGreenTopLeft = n17;
                final float n18 = 0.8f;
                this.colorBlueTopRight = n18;
                this.colorBlueBottomRight = n18;
                this.colorBlueBottomLeft = n18;
                this.colorBlueTopLeft = n18;
            }
            this.colorRedTopLeft *= var9;
            this.colorGreenTopLeft *= var9;
            this.colorBlueTopLeft *= var9;
            this.colorRedBottomLeft *= var10;
            this.colorGreenBottomLeft *= var10;
            this.colorBlueBottomLeft *= var10;
            this.colorRedBottomRight *= var11;
            this.colorGreenBottomRight *= var11;
            this.colorBlueBottomRight *= var11;
            this.colorRedTopRight *= var12;
            this.colorGreenTopRight *= var12;
            this.colorBlueTopRight *= var12;
            final IIcon var30 = this.getBlockIcon(p_147808_1_, this.blockAccess, p_147808_2_, p_147808_3_, p_147808_4_, 3);
            this.renderFaceZPos(p_147808_1_, p_147808_2_, p_147808_3_, p_147808_4_, var30);
            if (RenderBlocks.fancyGrass && var30.getIconName().equals("grass_side") && !this.hasOverrideBlockTexture()) {
                this.colorRedTopLeft *= p_147808_5_;
                this.colorRedBottomLeft *= p_147808_5_;
                this.colorRedBottomRight *= p_147808_5_;
                this.colorRedTopRight *= p_147808_5_;
                this.colorGreenTopLeft *= p_147808_6_;
                this.colorGreenBottomLeft *= p_147808_6_;
                this.colorGreenBottomRight *= p_147808_6_;
                this.colorGreenTopRight *= p_147808_6_;
                this.colorBlueTopLeft *= p_147808_7_;
                this.colorBlueBottomLeft *= p_147808_7_;
                this.colorBlueBottomRight *= p_147808_7_;
                this.colorBlueTopRight *= p_147808_7_;
                this.renderFaceZPos(p_147808_1_, p_147808_2_, p_147808_3_, p_147808_4_, BlockGrass.func_149990_e());
            }
            var8 = true;
        }
        if (this.renderAllFaces || p_147808_1_.shouldSideBeRendered(this.blockAccess, p_147808_2_ - 1, p_147808_3_, p_147808_4_, 4)) {
            if (this.renderMinX <= 0.0) {
                --p_147808_2_;
            }
            this.aoLightValueScratchXYNN = this.getAmbientOcclusionLightValue(p_147808_2_, p_147808_3_ - 1, p_147808_4_);
            this.aoLightValueScratchXZNN = this.getAmbientOcclusionLightValue(p_147808_2_, p_147808_3_, p_147808_4_ - 1);
            this.aoLightValueScratchXZNP = this.getAmbientOcclusionLightValue(p_147808_2_, p_147808_3_, p_147808_4_ + 1);
            this.aoLightValueScratchXYNP = this.getAmbientOcclusionLightValue(p_147808_2_, p_147808_3_ + 1, p_147808_4_);
            this.aoBrightnessXYNN = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_, p_147808_3_ - 1, p_147808_4_);
            this.aoBrightnessXZNN = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_, p_147808_3_, p_147808_4_ - 1);
            this.aoBrightnessXZNP = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_, p_147808_3_, p_147808_4_ + 1);
            this.aoBrightnessXYNP = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_, p_147808_3_ + 1, p_147808_4_);
            final boolean var16 = this.blockAccess.getBlock(p_147808_2_ - 1, p_147808_3_ + 1, p_147808_4_).getCanBlockGrass();
            final boolean var17 = this.blockAccess.getBlock(p_147808_2_ - 1, p_147808_3_ - 1, p_147808_4_).getCanBlockGrass();
            final boolean var18 = this.blockAccess.getBlock(p_147808_2_ - 1, p_147808_3_, p_147808_4_ - 1).getCanBlockGrass();
            final boolean var19 = this.blockAccess.getBlock(p_147808_2_ - 1, p_147808_3_, p_147808_4_ + 1).getCanBlockGrass();
            if (!var18 && !var17) {
                this.aoLightValueScratchXYZNNN = this.aoLightValueScratchXZNN;
                this.aoBrightnessXYZNNN = this.aoBrightnessXZNN;
            }
            else {
                this.aoLightValueScratchXYZNNN = this.getAmbientOcclusionLightValue(p_147808_2_, p_147808_3_ - 1, p_147808_4_ - 1);
                this.aoBrightnessXYZNNN = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_, p_147808_3_ - 1, p_147808_4_ - 1);
            }
            if (!var19 && !var17) {
                this.aoLightValueScratchXYZNNP = this.aoLightValueScratchXZNP;
                this.aoBrightnessXYZNNP = this.aoBrightnessXZNP;
            }
            else {
                this.aoLightValueScratchXYZNNP = this.getAmbientOcclusionLightValue(p_147808_2_, p_147808_3_ - 1, p_147808_4_ + 1);
                this.aoBrightnessXYZNNP = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_, p_147808_3_ - 1, p_147808_4_ + 1);
            }
            if (!var18 && !var16) {
                this.aoLightValueScratchXYZNPN = this.aoLightValueScratchXZNN;
                this.aoBrightnessXYZNPN = this.aoBrightnessXZNN;
            }
            else {
                this.aoLightValueScratchXYZNPN = this.getAmbientOcclusionLightValue(p_147808_2_, p_147808_3_ + 1, p_147808_4_ - 1);
                this.aoBrightnessXYZNPN = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_, p_147808_3_ + 1, p_147808_4_ - 1);
            }
            if (!var19 && !var16) {
                this.aoLightValueScratchXYZNPP = this.aoLightValueScratchXZNP;
                this.aoBrightnessXYZNPP = this.aoBrightnessXZNP;
            }
            else {
                this.aoLightValueScratchXYZNPP = this.getAmbientOcclusionLightValue(p_147808_2_, p_147808_3_ + 1, p_147808_4_ + 1);
                this.aoBrightnessXYZNPP = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_, p_147808_3_ + 1, p_147808_4_ + 1);
            }
            if (this.renderMinX <= 0.0) {
                ++p_147808_2_;
            }
            int var20 = var14;
            if (this.renderMinX <= 0.0 || !this.blockAccess.getBlock(p_147808_2_ - 1, p_147808_3_, p_147808_4_).isOpaqueCube()) {
                var20 = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_ - 1, p_147808_3_, p_147808_4_);
            }
            final float var21 = this.getAmbientOcclusionLightValue(p_147808_2_ - 1, p_147808_3_, p_147808_4_);
            final float var22 = (this.aoLightValueScratchXYNN + this.aoLightValueScratchXYZNNP + var21 + this.aoLightValueScratchXZNP) / 4.0f;
            final float var23 = (var21 + this.aoLightValueScratchXZNP + this.aoLightValueScratchXYNP + this.aoLightValueScratchXYZNPP) / 4.0f;
            final float var24 = (this.aoLightValueScratchXZNN + var21 + this.aoLightValueScratchXYZNPN + this.aoLightValueScratchXYNP) / 4.0f;
            final float var25 = (this.aoLightValueScratchXYZNNN + this.aoLightValueScratchXYNN + this.aoLightValueScratchXZNN + var21) / 4.0f;
            var9 = (float)(var23 * this.renderMaxY * this.renderMaxZ + var24 * this.renderMaxY * (1.0 - this.renderMaxZ) + var25 * (1.0 - this.renderMaxY) * (1.0 - this.renderMaxZ) + var22 * (1.0 - this.renderMaxY) * this.renderMaxZ);
            var10 = (float)(var23 * this.renderMaxY * this.renderMinZ + var24 * this.renderMaxY * (1.0 - this.renderMinZ) + var25 * (1.0 - this.renderMaxY) * (1.0 - this.renderMinZ) + var22 * (1.0 - this.renderMaxY) * this.renderMinZ);
            var11 = (float)(var23 * this.renderMinY * this.renderMinZ + var24 * this.renderMinY * (1.0 - this.renderMinZ) + var25 * (1.0 - this.renderMinY) * (1.0 - this.renderMinZ) + var22 * (1.0 - this.renderMinY) * this.renderMinZ);
            var12 = (float)(var23 * this.renderMinY * this.renderMaxZ + var24 * this.renderMinY * (1.0 - this.renderMaxZ) + var25 * (1.0 - this.renderMinY) * (1.0 - this.renderMaxZ) + var22 * (1.0 - this.renderMinY) * this.renderMaxZ);
            final int var26 = this.getAoBrightness(this.aoBrightnessXYNN, this.aoBrightnessXYZNNP, this.aoBrightnessXZNP, var20);
            final int var27 = this.getAoBrightness(this.aoBrightnessXZNP, this.aoBrightnessXYNP, this.aoBrightnessXYZNPP, var20);
            final int var28 = this.getAoBrightness(this.aoBrightnessXZNN, this.aoBrightnessXYZNPN, this.aoBrightnessXYNP, var20);
            final int var29 = this.getAoBrightness(this.aoBrightnessXYZNNN, this.aoBrightnessXYNN, this.aoBrightnessXZNN, var20);
            this.brightnessTopLeft = this.mixAoBrightness(var27, var28, var29, var26, this.renderMaxY * this.renderMaxZ, this.renderMaxY * (1.0 - this.renderMaxZ), (1.0 - this.renderMaxY) * (1.0 - this.renderMaxZ), (1.0 - this.renderMaxY) * this.renderMaxZ);
            this.brightnessBottomLeft = this.mixAoBrightness(var27, var28, var29, var26, this.renderMaxY * this.renderMinZ, this.renderMaxY * (1.0 - this.renderMinZ), (1.0 - this.renderMaxY) * (1.0 - this.renderMinZ), (1.0 - this.renderMaxY) * this.renderMinZ);
            this.brightnessBottomRight = this.mixAoBrightness(var27, var28, var29, var26, this.renderMinY * this.renderMinZ, this.renderMinY * (1.0 - this.renderMinZ), (1.0 - this.renderMinY) * (1.0 - this.renderMinZ), (1.0 - this.renderMinY) * this.renderMinZ);
            this.brightnessTopRight = this.mixAoBrightness(var27, var28, var29, var26, this.renderMinY * this.renderMaxZ, this.renderMinY * (1.0 - this.renderMaxZ), (1.0 - this.renderMinY) * (1.0 - this.renderMaxZ), (1.0 - this.renderMinY) * this.renderMaxZ);
            if (var13) {
                final float n19 = p_147808_5_ * 0.6f;
                this.colorRedTopRight = n19;
                this.colorRedBottomRight = n19;
                this.colorRedBottomLeft = n19;
                this.colorRedTopLeft = n19;
                final float n20 = p_147808_6_ * 0.6f;
                this.colorGreenTopRight = n20;
                this.colorGreenBottomRight = n20;
                this.colorGreenBottomLeft = n20;
                this.colorGreenTopLeft = n20;
                final float n21 = p_147808_7_ * 0.6f;
                this.colorBlueTopRight = n21;
                this.colorBlueBottomRight = n21;
                this.colorBlueBottomLeft = n21;
                this.colorBlueTopLeft = n21;
            }
            else {
                final float n22 = 0.6f;
                this.colorRedTopRight = n22;
                this.colorRedBottomRight = n22;
                this.colorRedBottomLeft = n22;
                this.colorRedTopLeft = n22;
                final float n23 = 0.6f;
                this.colorGreenTopRight = n23;
                this.colorGreenBottomRight = n23;
                this.colorGreenBottomLeft = n23;
                this.colorGreenTopLeft = n23;
                final float n24 = 0.6f;
                this.colorBlueTopRight = n24;
                this.colorBlueBottomRight = n24;
                this.colorBlueBottomLeft = n24;
                this.colorBlueTopLeft = n24;
            }
            this.colorRedTopLeft *= var9;
            this.colorGreenTopLeft *= var9;
            this.colorBlueTopLeft *= var9;
            this.colorRedBottomLeft *= var10;
            this.colorGreenBottomLeft *= var10;
            this.colorBlueBottomLeft *= var10;
            this.colorRedBottomRight *= var11;
            this.colorGreenBottomRight *= var11;
            this.colorBlueBottomRight *= var11;
            this.colorRedTopRight *= var12;
            this.colorGreenTopRight *= var12;
            this.colorBlueTopRight *= var12;
            final IIcon var30 = this.getBlockIcon(p_147808_1_, this.blockAccess, p_147808_2_, p_147808_3_, p_147808_4_, 4);
            this.renderFaceXNeg(p_147808_1_, p_147808_2_, p_147808_3_, p_147808_4_, var30);
            if (RenderBlocks.fancyGrass && var30.getIconName().equals("grass_side") && !this.hasOverrideBlockTexture()) {
                this.colorRedTopLeft *= p_147808_5_;
                this.colorRedBottomLeft *= p_147808_5_;
                this.colorRedBottomRight *= p_147808_5_;
                this.colorRedTopRight *= p_147808_5_;
                this.colorGreenTopLeft *= p_147808_6_;
                this.colorGreenBottomLeft *= p_147808_6_;
                this.colorGreenBottomRight *= p_147808_6_;
                this.colorGreenTopRight *= p_147808_6_;
                this.colorBlueTopLeft *= p_147808_7_;
                this.colorBlueBottomLeft *= p_147808_7_;
                this.colorBlueBottomRight *= p_147808_7_;
                this.colorBlueTopRight *= p_147808_7_;
                this.renderFaceXNeg(p_147808_1_, p_147808_2_, p_147808_3_, p_147808_4_, BlockGrass.func_149990_e());
            }
            var8 = true;
        }
        if (this.renderAllFaces || p_147808_1_.shouldSideBeRendered(this.blockAccess, p_147808_2_ + 1, p_147808_3_, p_147808_4_, 5)) {
            if (this.renderMaxX >= 1.0) {
                ++p_147808_2_;
            }
            this.aoLightValueScratchXYPN = this.getAmbientOcclusionLightValue(p_147808_2_, p_147808_3_ - 1, p_147808_4_);
            this.aoLightValueScratchXZPN = this.getAmbientOcclusionLightValue(p_147808_2_, p_147808_3_, p_147808_4_ - 1);
            this.aoLightValueScratchXZPP = this.getAmbientOcclusionLightValue(p_147808_2_, p_147808_3_, p_147808_4_ + 1);
            this.aoLightValueScratchXYPP = this.getAmbientOcclusionLightValue(p_147808_2_, p_147808_3_ + 1, p_147808_4_);
            this.aoBrightnessXYPN = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_, p_147808_3_ - 1, p_147808_4_);
            this.aoBrightnessXZPN = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_, p_147808_3_, p_147808_4_ - 1);
            this.aoBrightnessXZPP = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_, p_147808_3_, p_147808_4_ + 1);
            this.aoBrightnessXYPP = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_, p_147808_3_ + 1, p_147808_4_);
            final boolean var16 = this.blockAccess.getBlock(p_147808_2_ + 1, p_147808_3_ + 1, p_147808_4_).getCanBlockGrass();
            final boolean var17 = this.blockAccess.getBlock(p_147808_2_ + 1, p_147808_3_ - 1, p_147808_4_).getCanBlockGrass();
            final boolean var18 = this.blockAccess.getBlock(p_147808_2_ + 1, p_147808_3_, p_147808_4_ + 1).getCanBlockGrass();
            final boolean var19 = this.blockAccess.getBlock(p_147808_2_ + 1, p_147808_3_, p_147808_4_ - 1).getCanBlockGrass();
            if (!var17 && !var19) {
                this.aoLightValueScratchXYZPNN = this.aoLightValueScratchXZPN;
                this.aoBrightnessXYZPNN = this.aoBrightnessXZPN;
            }
            else {
                this.aoLightValueScratchXYZPNN = this.getAmbientOcclusionLightValue(p_147808_2_, p_147808_3_ - 1, p_147808_4_ - 1);
                this.aoBrightnessXYZPNN = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_, p_147808_3_ - 1, p_147808_4_ - 1);
            }
            if (!var17 && !var18) {
                this.aoLightValueScratchXYZPNP = this.aoLightValueScratchXZPP;
                this.aoBrightnessXYZPNP = this.aoBrightnessXZPP;
            }
            else {
                this.aoLightValueScratchXYZPNP = this.getAmbientOcclusionLightValue(p_147808_2_, p_147808_3_ - 1, p_147808_4_ + 1);
                this.aoBrightnessXYZPNP = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_, p_147808_3_ - 1, p_147808_4_ + 1);
            }
            if (!var16 && !var19) {
                this.aoLightValueScratchXYZPPN = this.aoLightValueScratchXZPN;
                this.aoBrightnessXYZPPN = this.aoBrightnessXZPN;
            }
            else {
                this.aoLightValueScratchXYZPPN = this.getAmbientOcclusionLightValue(p_147808_2_, p_147808_3_ + 1, p_147808_4_ - 1);
                this.aoBrightnessXYZPPN = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_, p_147808_3_ + 1, p_147808_4_ - 1);
            }
            if (!var16 && !var18) {
                this.aoLightValueScratchXYZPPP = this.aoLightValueScratchXZPP;
                this.aoBrightnessXYZPPP = this.aoBrightnessXZPP;
            }
            else {
                this.aoLightValueScratchXYZPPP = this.getAmbientOcclusionLightValue(p_147808_2_, p_147808_3_ + 1, p_147808_4_ + 1);
                this.aoBrightnessXYZPPP = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_, p_147808_3_ + 1, p_147808_4_ + 1);
            }
            if (this.renderMaxX >= 1.0) {
                --p_147808_2_;
            }
            int var20 = var14;
            if (this.renderMaxX >= 1.0 || !this.blockAccess.getBlock(p_147808_2_ + 1, p_147808_3_, p_147808_4_).isOpaqueCube()) {
                var20 = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_ + 1, p_147808_3_, p_147808_4_);
            }
            final float var21 = this.getAmbientOcclusionLightValue(p_147808_2_ + 1, p_147808_3_, p_147808_4_);
            final float var22 = (this.aoLightValueScratchXYPN + this.aoLightValueScratchXYZPNP + var21 + this.aoLightValueScratchXZPP) / 4.0f;
            final float var23 = (this.aoLightValueScratchXYZPNN + this.aoLightValueScratchXYPN + this.aoLightValueScratchXZPN + var21) / 4.0f;
            final float var24 = (this.aoLightValueScratchXZPN + var21 + this.aoLightValueScratchXYZPPN + this.aoLightValueScratchXYPP) / 4.0f;
            final float var25 = (var21 + this.aoLightValueScratchXZPP + this.aoLightValueScratchXYPP + this.aoLightValueScratchXYZPPP) / 4.0f;
            var9 = (float)(var22 * (1.0 - this.renderMinY) * this.renderMaxZ + var23 * (1.0 - this.renderMinY) * (1.0 - this.renderMaxZ) + var24 * this.renderMinY * (1.0 - this.renderMaxZ) + var25 * this.renderMinY * this.renderMaxZ);
            var10 = (float)(var22 * (1.0 - this.renderMinY) * this.renderMinZ + var23 * (1.0 - this.renderMinY) * (1.0 - this.renderMinZ) + var24 * this.renderMinY * (1.0 - this.renderMinZ) + var25 * this.renderMinY * this.renderMinZ);
            var11 = (float)(var22 * (1.0 - this.renderMaxY) * this.renderMinZ + var23 * (1.0 - this.renderMaxY) * (1.0 - this.renderMinZ) + var24 * this.renderMaxY * (1.0 - this.renderMinZ) + var25 * this.renderMaxY * this.renderMinZ);
            var12 = (float)(var22 * (1.0 - this.renderMaxY) * this.renderMaxZ + var23 * (1.0 - this.renderMaxY) * (1.0 - this.renderMaxZ) + var24 * this.renderMaxY * (1.0 - this.renderMaxZ) + var25 * this.renderMaxY * this.renderMaxZ);
            final int var26 = this.getAoBrightness(this.aoBrightnessXYPN, this.aoBrightnessXYZPNP, this.aoBrightnessXZPP, var20);
            final int var27 = this.getAoBrightness(this.aoBrightnessXZPP, this.aoBrightnessXYPP, this.aoBrightnessXYZPPP, var20);
            final int var28 = this.getAoBrightness(this.aoBrightnessXZPN, this.aoBrightnessXYZPPN, this.aoBrightnessXYPP, var20);
            final int var29 = this.getAoBrightness(this.aoBrightnessXYZPNN, this.aoBrightnessXYPN, this.aoBrightnessXZPN, var20);
            this.brightnessTopLeft = this.mixAoBrightness(var26, var29, var28, var27, (1.0 - this.renderMinY) * this.renderMaxZ, (1.0 - this.renderMinY) * (1.0 - this.renderMaxZ), this.renderMinY * (1.0 - this.renderMaxZ), this.renderMinY * this.renderMaxZ);
            this.brightnessBottomLeft = this.mixAoBrightness(var26, var29, var28, var27, (1.0 - this.renderMinY) * this.renderMinZ, (1.0 - this.renderMinY) * (1.0 - this.renderMinZ), this.renderMinY * (1.0 - this.renderMinZ), this.renderMinY * this.renderMinZ);
            this.brightnessBottomRight = this.mixAoBrightness(var26, var29, var28, var27, (1.0 - this.renderMaxY) * this.renderMinZ, (1.0 - this.renderMaxY) * (1.0 - this.renderMinZ), this.renderMaxY * (1.0 - this.renderMinZ), this.renderMaxY * this.renderMinZ);
            this.brightnessTopRight = this.mixAoBrightness(var26, var29, var28, var27, (1.0 - this.renderMaxY) * this.renderMaxZ, (1.0 - this.renderMaxY) * (1.0 - this.renderMaxZ), this.renderMaxY * (1.0 - this.renderMaxZ), this.renderMaxY * this.renderMaxZ);
            if (var13) {
                final float n25 = p_147808_5_ * 0.6f;
                this.colorRedTopRight = n25;
                this.colorRedBottomRight = n25;
                this.colorRedBottomLeft = n25;
                this.colorRedTopLeft = n25;
                final float n26 = p_147808_6_ * 0.6f;
                this.colorGreenTopRight = n26;
                this.colorGreenBottomRight = n26;
                this.colorGreenBottomLeft = n26;
                this.colorGreenTopLeft = n26;
                final float n27 = p_147808_7_ * 0.6f;
                this.colorBlueTopRight = n27;
                this.colorBlueBottomRight = n27;
                this.colorBlueBottomLeft = n27;
                this.colorBlueTopLeft = n27;
            }
            else {
                final float n28 = 0.6f;
                this.colorRedTopRight = n28;
                this.colorRedBottomRight = n28;
                this.colorRedBottomLeft = n28;
                this.colorRedTopLeft = n28;
                final float n29 = 0.6f;
                this.colorGreenTopRight = n29;
                this.colorGreenBottomRight = n29;
                this.colorGreenBottomLeft = n29;
                this.colorGreenTopLeft = n29;
                final float n30 = 0.6f;
                this.colorBlueTopRight = n30;
                this.colorBlueBottomRight = n30;
                this.colorBlueBottomLeft = n30;
                this.colorBlueTopLeft = n30;
            }
            this.colorRedTopLeft *= var9;
            this.colorGreenTopLeft *= var9;
            this.colorBlueTopLeft *= var9;
            this.colorRedBottomLeft *= var10;
            this.colorGreenBottomLeft *= var10;
            this.colorBlueBottomLeft *= var10;
            this.colorRedBottomRight *= var11;
            this.colorGreenBottomRight *= var11;
            this.colorBlueBottomRight *= var11;
            this.colorRedTopRight *= var12;
            this.colorGreenTopRight *= var12;
            this.colorBlueTopRight *= var12;
            final IIcon var30 = this.getBlockIcon(p_147808_1_, this.blockAccess, p_147808_2_, p_147808_3_, p_147808_4_, 5);
            this.renderFaceXPos(p_147808_1_, p_147808_2_, p_147808_3_, p_147808_4_, var30);
            if (RenderBlocks.fancyGrass && var30.getIconName().equals("grass_side") && !this.hasOverrideBlockTexture()) {
                this.colorRedTopLeft *= p_147808_5_;
                this.colorRedBottomLeft *= p_147808_5_;
                this.colorRedBottomRight *= p_147808_5_;
                this.colorRedTopRight *= p_147808_5_;
                this.colorGreenTopLeft *= p_147808_6_;
                this.colorGreenBottomLeft *= p_147808_6_;
                this.colorGreenBottomRight *= p_147808_6_;
                this.colorGreenTopRight *= p_147808_6_;
                this.colorBlueTopLeft *= p_147808_7_;
                this.colorBlueBottomLeft *= p_147808_7_;
                this.colorBlueBottomRight *= p_147808_7_;
                this.colorBlueTopRight *= p_147808_7_;
                this.renderFaceXPos(p_147808_1_, p_147808_2_, p_147808_3_, p_147808_4_, BlockGrass.func_149990_e());
            }
            var8 = true;
        }
        this.enableAO = false;
        return var8;
    }
    
    public int getAoBrightness(int p_147778_1_, int p_147778_2_, int p_147778_3_, final int p_147778_4_) {
        if (p_147778_1_ == 0) {
            p_147778_1_ = p_147778_4_;
        }
        if (p_147778_2_ == 0) {
            p_147778_2_ = p_147778_4_;
        }
        if (p_147778_3_ == 0) {
            p_147778_3_ = p_147778_4_;
        }
        return p_147778_1_ + p_147778_2_ + p_147778_3_ + p_147778_4_ >> 2 & 0xFF00FF;
    }
    
    public int mixAoBrightness(final int p_147727_1_, final int p_147727_2_, final int p_147727_3_, final int p_147727_4_, final double p_147727_5_, final double p_147727_7_, final double p_147727_9_, final double p_147727_11_) {
        final int var13 = (int)((p_147727_1_ >> 16 & 0xFF) * p_147727_5_ + (p_147727_2_ >> 16 & 0xFF) * p_147727_7_ + (p_147727_3_ >> 16 & 0xFF) * p_147727_9_ + (p_147727_4_ >> 16 & 0xFF) * p_147727_11_) & 0xFF;
        final int var14 = (int)((p_147727_1_ & 0xFF) * p_147727_5_ + (p_147727_2_ & 0xFF) * p_147727_7_ + (p_147727_3_ & 0xFF) * p_147727_9_ + (p_147727_4_ & 0xFF) * p_147727_11_) & 0xFF;
        return var13 << 16 | var14;
    }
    
    public boolean renderStandardBlockWithColorMultiplier(final Block p_147736_1_, final int p_147736_2_, final int p_147736_3_, final int p_147736_4_, final float p_147736_5_, final float p_147736_6_, final float p_147736_7_) {
        this.enableAO = false;
        final boolean defaultTexture = Tessellator.instance.defaultTexture;
        final boolean betterGrass = Config.isBetterGrass() && defaultTexture;
        final Tessellator var8 = Tessellator.instance;
        boolean var9 = false;
        int var10 = -1;
        if (this.renderAllFaces || p_147736_1_.shouldSideBeRendered(this.blockAccess, p_147736_2_, p_147736_3_ - 1, p_147736_4_, 0)) {
            if (var10 < 0) {
                var10 = p_147736_1_.getBlockBrightness(this.blockAccess, p_147736_2_, p_147736_3_, p_147736_4_);
            }
            float var14;
            float var13;
            float var12;
            final float var11 = var12 = (var13 = (var14 = 0.5f));
            if (p_147736_1_ != Blocks.grass) {
                var14 = var11 * p_147736_5_;
                var13 = var11 * p_147736_6_;
                var12 = var11 * p_147736_7_;
            }
            var8.setBrightness((this.renderMinY > 0.0) ? var10 : p_147736_1_.getBlockBrightness(this.blockAccess, p_147736_2_, p_147736_3_ - 1, p_147736_4_));
            var8.setColorOpaque_F(var14, var13, var12);
            this.renderFaceYNeg(p_147736_1_, p_147736_2_, p_147736_3_, p_147736_4_, this.getBlockIcon(p_147736_1_, this.blockAccess, p_147736_2_, p_147736_3_, p_147736_4_, 0));
            var9 = true;
        }
        if (this.renderAllFaces || p_147736_1_.shouldSideBeRendered(this.blockAccess, p_147736_2_, p_147736_3_ + 1, p_147736_4_, 1)) {
            if (var10 < 0) {
                var10 = p_147736_1_.getBlockBrightness(this.blockAccess, p_147736_2_, p_147736_3_, p_147736_4_);
            }
            final float var11 = 1.0f;
            final float var14 = var11 * p_147736_5_;
            final float var13 = var11 * p_147736_6_;
            final float var12 = var11 * p_147736_7_;
            var8.setBrightness((this.renderMaxY < 1.0) ? var10 : p_147736_1_.getBlockBrightness(this.blockAccess, p_147736_2_, p_147736_3_ + 1, p_147736_4_));
            var8.setColorOpaque_F(var14, var13, var12);
            this.renderFaceYPos(p_147736_1_, p_147736_2_, p_147736_3_, p_147736_4_, this.getBlockIcon(p_147736_1_, this.blockAccess, p_147736_2_, p_147736_3_, p_147736_4_, 1));
            var9 = true;
        }
        if (this.renderAllFaces || p_147736_1_.shouldSideBeRendered(this.blockAccess, p_147736_2_, p_147736_3_, p_147736_4_ - 1, 2)) {
            if (var10 < 0) {
                var10 = p_147736_1_.getBlockBrightness(this.blockAccess, p_147736_2_, p_147736_3_, p_147736_4_);
            }
            float var13;
            float var12;
            float var15;
            final float var14 = var15 = (var12 = (var13 = 0.8f));
            if (p_147736_1_ != Blocks.grass) {
                var13 = var14 * p_147736_5_;
                var12 = var14 * p_147736_6_;
                var15 = var14 * p_147736_7_;
            }
            var8.setBrightness((this.renderMinZ > 0.0) ? var10 : p_147736_1_.getBlockBrightness(this.blockAccess, p_147736_2_, p_147736_3_, p_147736_4_ - 1));
            var8.setColorOpaque_F(var13, var12, var15);
            IIcon var16 = this.getBlockIcon(p_147736_1_, this.blockAccess, p_147736_2_, p_147736_3_, p_147736_4_, 2);
            if (betterGrass) {
                if (var16 == TextureUtils.iconGrassSide || var16 == TextureUtils.iconMyceliumSide) {
                    var16 = Config.getSideGrassTexture(this.blockAccess, p_147736_2_, p_147736_3_, p_147736_4_, 2, var16);
                    if (var16 == TextureUtils.iconGrassTop) {
                        var8.setColorOpaque_F(var13 * p_147736_5_, var12 * p_147736_6_, var15 * p_147736_7_);
                    }
                }
                if (var16 == TextureUtils.iconGrassSideSnowed) {
                    var16 = Config.getSideSnowGrassTexture(this.blockAccess, p_147736_2_, p_147736_3_, p_147736_4_, 2);
                }
            }
            this.renderFaceZNeg(p_147736_1_, p_147736_2_, p_147736_3_, p_147736_4_, var16);
            if (defaultTexture && RenderBlocks.fancyGrass && var16 == TextureUtils.iconGrassSide && !this.hasOverrideBlockTexture()) {
                var8.setColorOpaque_F(var13 * p_147736_5_, var12 * p_147736_6_, var15 * p_147736_7_);
                this.renderFaceZNeg(p_147736_1_, p_147736_2_, p_147736_3_, p_147736_4_, BlockGrass.func_149990_e());
            }
            var9 = true;
        }
        if (this.renderAllFaces || p_147736_1_.shouldSideBeRendered(this.blockAccess, p_147736_2_, p_147736_3_, p_147736_4_ + 1, 3)) {
            if (var10 < 0) {
                var10 = p_147736_1_.getBlockBrightness(this.blockAccess, p_147736_2_, p_147736_3_, p_147736_4_);
            }
            float var13;
            float var12;
            float var15;
            final float var14 = var15 = (var12 = (var13 = 0.8f));
            if (p_147736_1_ != Blocks.grass) {
                var13 = var14 * p_147736_5_;
                var12 = var14 * p_147736_6_;
                var15 = var14 * p_147736_7_;
            }
            var8.setBrightness((this.renderMaxZ < 1.0) ? var10 : p_147736_1_.getBlockBrightness(this.blockAccess, p_147736_2_, p_147736_3_, p_147736_4_ + 1));
            var8.setColorOpaque_F(var13, var12, var15);
            IIcon var16 = this.getBlockIcon(p_147736_1_, this.blockAccess, p_147736_2_, p_147736_3_, p_147736_4_, 3);
            if (betterGrass) {
                if (var16 == TextureUtils.iconGrassSide || var16 == TextureUtils.iconMyceliumSide) {
                    var16 = Config.getSideGrassTexture(this.blockAccess, p_147736_2_, p_147736_3_, p_147736_4_, 3, var16);
                    if (var16 == TextureUtils.iconGrassTop) {
                        var8.setColorOpaque_F(var13 * p_147736_5_, var12 * p_147736_6_, var15 * p_147736_7_);
                    }
                }
                if (var16 == TextureUtils.iconGrassSideSnowed) {
                    var16 = Config.getSideSnowGrassTexture(this.blockAccess, p_147736_2_, p_147736_3_, p_147736_4_, 3);
                }
            }
            this.renderFaceZPos(p_147736_1_, p_147736_2_, p_147736_3_, p_147736_4_, var16);
            if (defaultTexture && RenderBlocks.fancyGrass && var16 == TextureUtils.iconGrassSide && !this.hasOverrideBlockTexture()) {
                var8.setColorOpaque_F(var13 * p_147736_5_, var12 * p_147736_6_, var15 * p_147736_7_);
                this.renderFaceZPos(p_147736_1_, p_147736_2_, p_147736_3_, p_147736_4_, BlockGrass.func_149990_e());
            }
            var9 = true;
        }
        if (this.renderAllFaces || p_147736_1_.shouldSideBeRendered(this.blockAccess, p_147736_2_ - 1, p_147736_3_, p_147736_4_, 4)) {
            if (var10 < 0) {
                var10 = p_147736_1_.getBlockBrightness(this.blockAccess, p_147736_2_, p_147736_3_, p_147736_4_);
            }
            float var13;
            float var12;
            float var15;
            final float var14 = var15 = (var12 = (var13 = 0.6f));
            if (p_147736_1_ != Blocks.grass) {
                var13 = var14 * p_147736_5_;
                var12 = var14 * p_147736_6_;
                var15 = var14 * p_147736_7_;
            }
            var8.setBrightness((this.renderMinX > 0.0) ? var10 : p_147736_1_.getBlockBrightness(this.blockAccess, p_147736_2_ - 1, p_147736_3_, p_147736_4_));
            var8.setColorOpaque_F(var13, var12, var15);
            IIcon var16 = this.getBlockIcon(p_147736_1_, this.blockAccess, p_147736_2_, p_147736_3_, p_147736_4_, 4);
            if (betterGrass) {
                if (var16 == TextureUtils.iconGrassSide || var16 == TextureUtils.iconMyceliumSide) {
                    var16 = Config.getSideGrassTexture(this.blockAccess, p_147736_2_, p_147736_3_, p_147736_4_, 4, var16);
                    if (var16 == TextureUtils.iconGrassTop) {
                        var8.setColorOpaque_F(var13 * p_147736_5_, var12 * p_147736_6_, var15 * p_147736_7_);
                    }
                }
                if (var16 == TextureUtils.iconGrassSideSnowed) {
                    var16 = Config.getSideSnowGrassTexture(this.blockAccess, p_147736_2_, p_147736_3_, p_147736_4_, 4);
                }
            }
            this.renderFaceXNeg(p_147736_1_, p_147736_2_, p_147736_3_, p_147736_4_, var16);
            if (defaultTexture && RenderBlocks.fancyGrass && var16 == TextureUtils.iconGrassSide && !this.hasOverrideBlockTexture()) {
                var8.setColorOpaque_F(var13 * p_147736_5_, var12 * p_147736_6_, var15 * p_147736_7_);
                this.renderFaceXNeg(p_147736_1_, p_147736_2_, p_147736_3_, p_147736_4_, BlockGrass.func_149990_e());
            }
            var9 = true;
        }
        if (this.renderAllFaces || p_147736_1_.shouldSideBeRendered(this.blockAccess, p_147736_2_ + 1, p_147736_3_, p_147736_4_, 5)) {
            if (var10 < 0) {
                var10 = p_147736_1_.getBlockBrightness(this.blockAccess, p_147736_2_, p_147736_3_, p_147736_4_);
            }
            float var13;
            float var12;
            float var15;
            final float var14 = var15 = (var12 = (var13 = 0.6f));
            if (p_147736_1_ != Blocks.grass) {
                var13 = var14 * p_147736_5_;
                var12 = var14 * p_147736_6_;
                var15 = var14 * p_147736_7_;
            }
            var8.setBrightness((this.renderMaxX < 1.0) ? var10 : p_147736_1_.getBlockBrightness(this.blockAccess, p_147736_2_ + 1, p_147736_3_, p_147736_4_));
            var8.setColorOpaque_F(var13, var12, var15);
            IIcon var16 = this.getBlockIcon(p_147736_1_, this.blockAccess, p_147736_2_, p_147736_3_, p_147736_4_, 5);
            if (betterGrass) {
                if (var16 == TextureUtils.iconGrassSide || var16 == TextureUtils.iconMyceliumSide) {
                    var16 = Config.getSideGrassTexture(this.blockAccess, p_147736_2_, p_147736_3_, p_147736_4_, 5, var16);
                    if (var16 == TextureUtils.iconGrassTop) {
                        var8.setColorOpaque_F(var13 * p_147736_5_, var12 * p_147736_6_, var15 * p_147736_7_);
                    }
                }
                if (var16 == TextureUtils.iconGrassSideSnowed) {
                    var16 = Config.getSideSnowGrassTexture(this.blockAccess, p_147736_2_, p_147736_3_, p_147736_4_, 5);
                }
            }
            this.renderFaceXPos(p_147736_1_, p_147736_2_, p_147736_3_, p_147736_4_, var16);
            if (defaultTexture && RenderBlocks.fancyGrass && var16 == TextureUtils.iconGrassSide && !this.hasOverrideBlockTexture()) {
                var8.setColorOpaque_F(var13 * p_147736_5_, var12 * p_147736_6_, var15 * p_147736_7_);
                this.renderFaceXPos(p_147736_1_, p_147736_2_, p_147736_3_, p_147736_4_, BlockGrass.func_149990_e());
            }
            var9 = true;
        }
        return var9;
    }
    
    public boolean renderBlockCocoa(final BlockCocoa p_147772_1_, final int p_147772_2_, final int p_147772_3_, final int p_147772_4_) {
        final Tessellator var5 = Tessellator.instance;
        var5.setBrightness(p_147772_1_.getBlockBrightness(this.blockAccess, p_147772_2_, p_147772_3_, p_147772_4_));
        var5.setColorOpaque_F(1.0f, 1.0f, 1.0f);
        final int var6 = this.blockAccess.getBlockMetadata(p_147772_2_, p_147772_3_, p_147772_4_);
        final int var7 = BlockDirectional.func_149895_l(var6);
        final int var8 = BlockCocoa.func_149987_c(var6);
        final IIcon var9 = p_147772_1_.func_149988_b(var8);
        final int var10 = 4 + var8 * 2;
        final int var11 = 5 + var8 * 2;
        final double var12 = 15.0 - var10;
        final double var13 = 15.0;
        final double var14 = 4.0;
        final double var15 = 4.0 + var11;
        double var16 = var9.getInterpolatedU(var12);
        double var17 = var9.getInterpolatedU(var13);
        double var18 = var9.getInterpolatedV(var14);
        double var19 = var9.getInterpolatedV(var15);
        double var20 = 0.0;
        double var21 = 0.0;
        switch (var7) {
            case 0: {
                var20 = 8.0 - var10 / 2;
                var21 = 15.0 - var10;
                break;
            }
            case 1: {
                var20 = 1.0;
                var21 = 8.0 - var10 / 2;
                break;
            }
            case 2: {
                var20 = 8.0 - var10 / 2;
                var21 = 1.0;
                break;
            }
            case 3: {
                var20 = 15.0 - var10;
                var21 = 8.0 - var10 / 2;
                break;
            }
        }
        double var22 = p_147772_2_ + var20 / 16.0;
        double var23 = p_147772_2_ + (var20 + var10) / 16.0;
        double var24 = p_147772_3_ + (12.0 - var11) / 16.0;
        double var25 = p_147772_3_ + 0.75;
        double var26 = p_147772_4_ + var21 / 16.0;
        double var27 = p_147772_4_ + (var21 + var10) / 16.0;
        var5.addVertexWithUV(var22, var24, var26, var16, var19);
        var5.addVertexWithUV(var22, var24, var27, var17, var19);
        var5.addVertexWithUV(var22, var25, var27, var17, var18);
        var5.addVertexWithUV(var22, var25, var26, var16, var18);
        var5.addVertexWithUV(var23, var24, var27, var16, var19);
        var5.addVertexWithUV(var23, var24, var26, var17, var19);
        var5.addVertexWithUV(var23, var25, var26, var17, var18);
        var5.addVertexWithUV(var23, var25, var27, var16, var18);
        var5.addVertexWithUV(var23, var24, var26, var16, var19);
        var5.addVertexWithUV(var22, var24, var26, var17, var19);
        var5.addVertexWithUV(var22, var25, var26, var17, var18);
        var5.addVertexWithUV(var23, var25, var26, var16, var18);
        var5.addVertexWithUV(var22, var24, var27, var16, var19);
        var5.addVertexWithUV(var23, var24, var27, var17, var19);
        var5.addVertexWithUV(var23, var25, var27, var17, var18);
        var5.addVertexWithUV(var22, var25, var27, var16, var18);
        int var28 = var10;
        if (var8 >= 2) {
            var28 = var10 - 1;
        }
        var16 = var9.getMinU();
        var17 = var9.getInterpolatedU(var28);
        var18 = var9.getMinV();
        var19 = var9.getInterpolatedV(var28);
        var5.addVertexWithUV(var22, var25, var27, var16, var19);
        var5.addVertexWithUV(var23, var25, var27, var17, var19);
        var5.addVertexWithUV(var23, var25, var26, var17, var18);
        var5.addVertexWithUV(var22, var25, var26, var16, var18);
        var5.addVertexWithUV(var22, var24, var26, var16, var18);
        var5.addVertexWithUV(var23, var24, var26, var17, var18);
        var5.addVertexWithUV(var23, var24, var27, var17, var19);
        var5.addVertexWithUV(var22, var24, var27, var16, var19);
        var16 = var9.getInterpolatedU(12.0);
        var17 = var9.getMaxU();
        var18 = var9.getMinV();
        var19 = var9.getInterpolatedV(4.0);
        var20 = 8.0;
        var21 = 0.0;
        switch (var7) {
            case 0: {
                var20 = 8.0;
                var21 = 12.0;
                final double var29 = var16;
                var16 = var17;
                var17 = var29;
                break;
            }
            case 1: {
                var20 = 0.0;
                var21 = 8.0;
                break;
            }
            case 2: {
                var20 = 8.0;
                var21 = 0.0;
                break;
            }
            case 3: {
                var20 = 12.0;
                var21 = 8.0;
                final double var29 = var16;
                var16 = var17;
                var17 = var29;
                break;
            }
        }
        var22 = p_147772_2_ + var20 / 16.0;
        var23 = p_147772_2_ + (var20 + 4.0) / 16.0;
        var24 = p_147772_3_ + 0.75;
        var25 = p_147772_3_ + 1.0;
        var26 = p_147772_4_ + var21 / 16.0;
        var27 = p_147772_4_ + (var21 + 4.0) / 16.0;
        if (var7 != 2 && var7 != 0) {
            if (var7 == 1 || var7 == 3) {
                var5.addVertexWithUV(var23, var24, var26, var16, var19);
                var5.addVertexWithUV(var22, var24, var26, var17, var19);
                var5.addVertexWithUV(var22, var25, var26, var17, var18);
                var5.addVertexWithUV(var23, var25, var26, var16, var18);
                var5.addVertexWithUV(var22, var24, var26, var17, var19);
                var5.addVertexWithUV(var23, var24, var26, var16, var19);
                var5.addVertexWithUV(var23, var25, var26, var16, var18);
                var5.addVertexWithUV(var22, var25, var26, var17, var18);
            }
        }
        else {
            var5.addVertexWithUV(var22, var24, var26, var17, var19);
            var5.addVertexWithUV(var22, var24, var27, var16, var19);
            var5.addVertexWithUV(var22, var25, var27, var16, var18);
            var5.addVertexWithUV(var22, var25, var26, var17, var18);
            var5.addVertexWithUV(var22, var24, var27, var16, var19);
            var5.addVertexWithUV(var22, var24, var26, var17, var19);
            var5.addVertexWithUV(var22, var25, var26, var17, var18);
            var5.addVertexWithUV(var22, var25, var27, var16, var18);
        }
        return true;
    }
    
    public boolean renderBlockBeacon(final BlockBeacon p_147797_1_, final int p_147797_2_, final int p_147797_3_, final int p_147797_4_) {
        final float var5 = 0.1875f;
        this.setOverrideBlockTexture(this.getBlockIcon(Blocks.glass));
        this.setRenderBounds(0.0, 0.0, 0.0, 1.0, 1.0, 1.0);
        this.renderStandardBlock(p_147797_1_, p_147797_2_, p_147797_3_, p_147797_4_);
        this.renderAllFaces = true;
        this.setOverrideBlockTexture(this.getBlockIcon(Blocks.obsidian));
        this.setRenderBounds(0.125, 0.0062500000931322575, 0.125, 0.875, var5, 0.875);
        this.renderStandardBlock(p_147797_1_, p_147797_2_, p_147797_3_, p_147797_4_);
        IIcon iconBeacon = this.getBlockIcon(Blocks.beacon);
        if (Config.isConnectedTextures()) {
            iconBeacon = ConnectedTextures.getConnectedTexture(this.blockAccess, p_147797_1_, p_147797_2_, p_147797_3_, p_147797_4_, -1, iconBeacon);
        }
        this.setOverrideBlockTexture(iconBeacon);
        this.setRenderBounds(0.1875, var5, 0.1875, 0.8125, 0.875, 0.8125);
        this.renderStandardBlock(p_147797_1_, p_147797_2_, p_147797_3_, p_147797_4_);
        this.renderAllFaces = false;
        this.clearOverrideBlockTexture();
        return true;
    }
    
    public boolean renderBlockCactus(final Block p_147755_1_, final int p_147755_2_, final int p_147755_3_, final int p_147755_4_) {
        final int var5 = p_147755_1_.colorMultiplier(this.blockAccess, p_147755_2_, p_147755_3_, p_147755_4_);
        float var6 = (var5 >> 16 & 0xFF) / 255.0f;
        float var7 = (var5 >> 8 & 0xFF) / 255.0f;
        float var8 = (var5 & 0xFF) / 255.0f;
        if (EntityRenderer.anaglyphEnable) {
            final float var9 = (var6 * 30.0f + var7 * 59.0f + var8 * 11.0f) / 100.0f;
            final float var10 = (var6 * 30.0f + var7 * 70.0f) / 100.0f;
            final float var11 = (var6 * 30.0f + var8 * 70.0f) / 100.0f;
            var6 = var9;
            var7 = var10;
            var8 = var11;
        }
        return this.renderBlockCactusImpl(p_147755_1_, p_147755_2_, p_147755_3_, p_147755_4_, var6, var7, var8);
    }
    
    public boolean renderBlockCactusImpl(final Block p_147754_1_, final int p_147754_2_, final int p_147754_3_, final int p_147754_4_, final float p_147754_5_, final float p_147754_6_, final float p_147754_7_) {
        final Tessellator var8 = Tessellator.instance;
        final boolean var9 = false;
        final float var10 = 0.5f;
        final float var11 = 1.0f;
        final float var12 = 0.8f;
        final float var13 = 0.6f;
        final float var14 = var10 * p_147754_5_;
        final float var15 = var11 * p_147754_5_;
        final float var16 = var12 * p_147754_5_;
        final float var17 = var13 * p_147754_5_;
        final float var18 = var10 * p_147754_6_;
        final float var19 = var11 * p_147754_6_;
        final float var20 = var12 * p_147754_6_;
        final float var21 = var13 * p_147754_6_;
        final float var22 = var10 * p_147754_7_;
        final float var23 = var11 * p_147754_7_;
        final float var24 = var12 * p_147754_7_;
        final float var25 = var13 * p_147754_7_;
        final float var26 = 0.0625f;
        final int var27 = p_147754_1_.getBlockBrightness(this.blockAccess, p_147754_2_, p_147754_3_, p_147754_4_);
        if (this.renderAllFaces || p_147754_1_.shouldSideBeRendered(this.blockAccess, p_147754_2_, p_147754_3_ - 1, p_147754_4_, 0)) {
            var8.setBrightness((this.renderMinY > 0.0) ? var27 : p_147754_1_.getBlockBrightness(this.blockAccess, p_147754_2_, p_147754_3_ - 1, p_147754_4_));
            var8.setColorOpaque_F(var14, var18, var22);
            this.renderFaceYNeg(p_147754_1_, p_147754_2_, p_147754_3_, p_147754_4_, this.getBlockIcon(p_147754_1_, this.blockAccess, p_147754_2_, p_147754_3_, p_147754_4_, 0));
        }
        if (this.renderAllFaces || p_147754_1_.shouldSideBeRendered(this.blockAccess, p_147754_2_, p_147754_3_ + 1, p_147754_4_, 1)) {
            var8.setBrightness((this.renderMaxY < 1.0) ? var27 : p_147754_1_.getBlockBrightness(this.blockAccess, p_147754_2_, p_147754_3_ + 1, p_147754_4_));
            var8.setColorOpaque_F(var15, var19, var23);
            this.renderFaceYPos(p_147754_1_, p_147754_2_, p_147754_3_, p_147754_4_, this.getBlockIcon(p_147754_1_, this.blockAccess, p_147754_2_, p_147754_3_, p_147754_4_, 1));
        }
        var8.setBrightness(var27);
        var8.setColorOpaque_F(var16, var20, var24);
        var8.addTranslation(0.0f, 0.0f, var26);
        this.renderFaceZNeg(p_147754_1_, p_147754_2_, p_147754_3_, p_147754_4_, this.getBlockIcon(p_147754_1_, this.blockAccess, p_147754_2_, p_147754_3_, p_147754_4_, 2));
        var8.addTranslation(0.0f, 0.0f, -var26);
        var8.addTranslation(0.0f, 0.0f, -var26);
        this.renderFaceZPos(p_147754_1_, p_147754_2_, p_147754_3_, p_147754_4_, this.getBlockIcon(p_147754_1_, this.blockAccess, p_147754_2_, p_147754_3_, p_147754_4_, 3));
        var8.addTranslation(0.0f, 0.0f, var26);
        var8.setColorOpaque_F(var17, var21, var25);
        var8.addTranslation(var26, 0.0f, 0.0f);
        this.renderFaceXNeg(p_147754_1_, p_147754_2_, p_147754_3_, p_147754_4_, this.getBlockIcon(p_147754_1_, this.blockAccess, p_147754_2_, p_147754_3_, p_147754_4_, 4));
        var8.addTranslation(-var26, 0.0f, 0.0f);
        var8.addTranslation(-var26, 0.0f, 0.0f);
        this.renderFaceXPos(p_147754_1_, p_147754_2_, p_147754_3_, p_147754_4_, this.getBlockIcon(p_147754_1_, this.blockAccess, p_147754_2_, p_147754_3_, p_147754_4_, 5));
        var8.addTranslation(var26, 0.0f, 0.0f);
        return true;
    }
    
    public boolean renderBlockFence(final BlockFence p_147735_1_, final int p_147735_2_, final int p_147735_3_, final int p_147735_4_) {
        boolean var5 = false;
        float var6 = 0.375f;
        float var7 = 0.625f;
        this.setRenderBounds(var6, 0.0, var6, var7, 1.0, var7);
        this.renderStandardBlock(p_147735_1_, p_147735_2_, p_147735_3_, p_147735_4_);
        var5 = true;
        boolean var8 = false;
        boolean var9 = false;
        if (p_147735_1_.func_149826_e(this.blockAccess, p_147735_2_ - 1, p_147735_3_, p_147735_4_) || p_147735_1_.func_149826_e(this.blockAccess, p_147735_2_ + 1, p_147735_3_, p_147735_4_)) {
            var8 = true;
        }
        if (p_147735_1_.func_149826_e(this.blockAccess, p_147735_2_, p_147735_3_, p_147735_4_ - 1) || p_147735_1_.func_149826_e(this.blockAccess, p_147735_2_, p_147735_3_, p_147735_4_ + 1)) {
            var9 = true;
        }
        final boolean var10 = p_147735_1_.func_149826_e(this.blockAccess, p_147735_2_ - 1, p_147735_3_, p_147735_4_);
        final boolean var11 = p_147735_1_.func_149826_e(this.blockAccess, p_147735_2_ + 1, p_147735_3_, p_147735_4_);
        final boolean var12 = p_147735_1_.func_149826_e(this.blockAccess, p_147735_2_, p_147735_3_, p_147735_4_ - 1);
        final boolean var13 = p_147735_1_.func_149826_e(this.blockAccess, p_147735_2_, p_147735_3_, p_147735_4_ + 1);
        if (!var8 && !var9) {
            var8 = true;
        }
        var6 = 0.4375f;
        var7 = 0.5625f;
        float var14 = 0.75f;
        float var15 = 0.9375f;
        final float var16 = var10 ? 0.0f : var6;
        final float var17 = var11 ? 1.0f : var7;
        final float var18 = var12 ? 0.0f : var6;
        final float var19 = var13 ? 1.0f : var7;
        this.field_152631_f = true;
        if (var8) {
            this.setRenderBounds(var16, var14, var6, var17, var15, var7);
            this.renderStandardBlock(p_147735_1_, p_147735_2_, p_147735_3_, p_147735_4_);
            var5 = true;
        }
        if (var9) {
            this.setRenderBounds(var6, var14, var18, var7, var15, var19);
            this.renderStandardBlock(p_147735_1_, p_147735_2_, p_147735_3_, p_147735_4_);
            var5 = true;
        }
        var14 = 0.375f;
        var15 = 0.5625f;
        if (var8) {
            this.setRenderBounds(var16, var14, var6, var17, var15, var7);
            this.renderStandardBlock(p_147735_1_, p_147735_2_, p_147735_3_, p_147735_4_);
            var5 = true;
        }
        if (var9) {
            this.setRenderBounds(var6, var14, var18, var7, var15, var19);
            this.renderStandardBlock(p_147735_1_, p_147735_2_, p_147735_3_, p_147735_4_);
            var5 = true;
        }
        this.field_152631_f = false;
        p_147735_1_.setBlockBoundsBasedOnState(this.blockAccess, p_147735_2_, p_147735_3_, p_147735_4_);
        if (Config.isBetterSnow() && this.hasSnowNeighbours(p_147735_2_, p_147735_3_, p_147735_4_)) {
            this.renderSnow(p_147735_2_, p_147735_3_, p_147735_4_, Blocks.snow_layer.getBlockBoundsMaxY());
        }
        return var5;
    }
    
    public boolean renderBlockWall(final BlockWall p_147807_1_, final int p_147807_2_, final int p_147807_3_, final int p_147807_4_) {
        final boolean var5 = p_147807_1_.func_150091_e(this.blockAccess, p_147807_2_ - 1, p_147807_3_, p_147807_4_);
        final boolean var6 = p_147807_1_.func_150091_e(this.blockAccess, p_147807_2_ + 1, p_147807_3_, p_147807_4_);
        final boolean var7 = p_147807_1_.func_150091_e(this.blockAccess, p_147807_2_, p_147807_3_, p_147807_4_ - 1);
        final boolean var8 = p_147807_1_.func_150091_e(this.blockAccess, p_147807_2_, p_147807_3_, p_147807_4_ + 1);
        final boolean var9 = var7 && var8 && !var5 && !var6;
        final boolean var10 = !var7 && !var8 && var5 && var6;
        final boolean var11 = this.blockAccess.isAirBlock(p_147807_2_, p_147807_3_ + 1, p_147807_4_);
        if ((var9 || var10) && var11) {
            if (var9) {
                this.setRenderBounds(0.3125, 0.0, 0.0, 0.6875, 0.8125, 1.0);
                this.renderStandardBlock(p_147807_1_, p_147807_2_, p_147807_3_, p_147807_4_);
            }
            else {
                this.setRenderBounds(0.0, 0.0, 0.3125, 1.0, 0.8125, 0.6875);
                this.renderStandardBlock(p_147807_1_, p_147807_2_, p_147807_3_, p_147807_4_);
            }
        }
        else {
            this.setRenderBounds(0.25, 0.0, 0.25, 0.75, 1.0, 0.75);
            this.renderStandardBlock(p_147807_1_, p_147807_2_, p_147807_3_, p_147807_4_);
            if (var5) {
                this.setRenderBounds(0.0, 0.0, 0.3125, 0.25, 0.8125, 0.6875);
                this.renderStandardBlock(p_147807_1_, p_147807_2_, p_147807_3_, p_147807_4_);
            }
            if (var6) {
                this.setRenderBounds(0.75, 0.0, 0.3125, 1.0, 0.8125, 0.6875);
                this.renderStandardBlock(p_147807_1_, p_147807_2_, p_147807_3_, p_147807_4_);
            }
            if (var7) {
                this.setRenderBounds(0.3125, 0.0, 0.0, 0.6875, 0.8125, 0.25);
                this.renderStandardBlock(p_147807_1_, p_147807_2_, p_147807_3_, p_147807_4_);
            }
            if (var8) {
                this.setRenderBounds(0.3125, 0.0, 0.75, 0.6875, 0.8125, 1.0);
                this.renderStandardBlock(p_147807_1_, p_147807_2_, p_147807_3_, p_147807_4_);
            }
        }
        p_147807_1_.setBlockBoundsBasedOnState(this.blockAccess, p_147807_2_, p_147807_3_, p_147807_4_);
        if (Config.isBetterSnow() && this.hasSnowNeighbours(p_147807_2_, p_147807_3_, p_147807_4_)) {
            this.renderSnow(p_147807_2_, p_147807_3_, p_147807_4_, Blocks.snow_layer.getBlockBoundsMaxY());
        }
        return true;
    }
    
    public boolean renderBlockDragonEgg(final BlockDragonEgg p_147802_1_, final int p_147802_2_, final int p_147802_3_, final int p_147802_4_) {
        boolean var5 = false;
        int var6 = 0;
        for (int var7 = 0; var7 < 8; ++var7) {
            byte var8 = 0;
            byte var9 = 1;
            if (var7 == 0) {
                var8 = 2;
            }
            if (var7 == 1) {
                var8 = 3;
            }
            if (var7 == 2) {
                var8 = 4;
            }
            if (var7 == 3) {
                var8 = 5;
                var9 = 2;
            }
            if (var7 == 4) {
                var8 = 6;
                var9 = 3;
            }
            if (var7 == 5) {
                var8 = 7;
                var9 = 5;
            }
            if (var7 == 6) {
                var8 = 6;
                var9 = 2;
            }
            if (var7 == 7) {
                var8 = 3;
            }
            final float var10 = var8 / 16.0f;
            final float var11 = 1.0f - var6 / 16.0f;
            final float var12 = 1.0f - (var6 + var9) / 16.0f;
            var6 += var9;
            this.setRenderBounds(0.5f - var10, var12, 0.5f - var10, 0.5f + var10, var11, 0.5f + var10);
            this.renderStandardBlock(p_147802_1_, p_147802_2_, p_147802_3_, p_147802_4_);
        }
        var5 = true;
        this.setRenderBounds(0.0, 0.0, 0.0, 1.0, 1.0, 1.0);
        return var5;
    }
    
    public boolean renderBlockFenceGate(final BlockFenceGate p_147776_1_, final int p_147776_2_, final int p_147776_3_, final int p_147776_4_) {
        final boolean var5 = true;
        final int var6 = this.blockAccess.getBlockMetadata(p_147776_2_, p_147776_3_, p_147776_4_);
        final boolean var7 = BlockFenceGate.isFenceGateOpen(var6);
        final int var8 = BlockDirectional.func_149895_l(var6);
        float var9 = 0.375f;
        float var10 = 0.5625f;
        float var11 = 0.75f;
        float var12 = 0.9375f;
        float var13 = 0.3125f;
        float var14 = 1.0f;
        if (((var8 == 2 || var8 == 0) && this.blockAccess.getBlock(p_147776_2_ - 1, p_147776_3_, p_147776_4_) == Blocks.cobblestone_wall && this.blockAccess.getBlock(p_147776_2_ + 1, p_147776_3_, p_147776_4_) == Blocks.cobblestone_wall) || ((var8 == 3 || var8 == 1) && this.blockAccess.getBlock(p_147776_2_, p_147776_3_, p_147776_4_ - 1) == Blocks.cobblestone_wall && this.blockAccess.getBlock(p_147776_2_, p_147776_3_, p_147776_4_ + 1) == Blocks.cobblestone_wall)) {
            var9 -= 0.1875f;
            var10 -= 0.1875f;
            var11 -= 0.1875f;
            var12 -= 0.1875f;
            var13 -= 0.1875f;
            var14 -= 0.1875f;
        }
        this.renderAllFaces = true;
        if (var8 != 3 && var8 != 1) {
            float var15 = 0.0f;
            float var16 = 0.125f;
            final float var17 = 0.4375f;
            final float var18 = 0.5625f;
            this.setRenderBounds(var15, var13, var17, var16, var14, var18);
            this.renderStandardBlock(p_147776_1_, p_147776_2_, p_147776_3_, p_147776_4_);
            var15 = 0.875f;
            var16 = 1.0f;
            this.setRenderBounds(var15, var13, var17, var16, var14, var18);
            this.renderStandardBlock(p_147776_1_, p_147776_2_, p_147776_3_, p_147776_4_);
        }
        else {
            this.uvRotateTop = 1;
            final float var15 = 0.4375f;
            final float var16 = 0.5625f;
            float var17 = 0.0f;
            float var18 = 0.125f;
            this.setRenderBounds(var15, var13, var17, var16, var14, var18);
            this.renderStandardBlock(p_147776_1_, p_147776_2_, p_147776_3_, p_147776_4_);
            var17 = 0.875f;
            var18 = 1.0f;
            this.setRenderBounds(var15, var13, var17, var16, var14, var18);
            this.renderStandardBlock(p_147776_1_, p_147776_2_, p_147776_3_, p_147776_4_);
            this.uvRotateTop = 0;
        }
        if (var7) {
            if (var8 == 2 || var8 == 0) {
                this.uvRotateTop = 1;
            }
            if (var8 == 3) {
                final float var15 = 0.0f;
                final float var16 = 0.125f;
                final float var17 = 0.875f;
                final float var18 = 1.0f;
                final float var19 = 0.5625f;
                final float var20 = 0.8125f;
                final float var21 = 0.9375f;
                this.setRenderBounds(0.8125, var9, 0.0, 0.9375, var12, 0.125);
                this.renderStandardBlock(p_147776_1_, p_147776_2_, p_147776_3_, p_147776_4_);
                this.setRenderBounds(0.8125, var9, 0.875, 0.9375, var12, 1.0);
                this.renderStandardBlock(p_147776_1_, p_147776_2_, p_147776_3_, p_147776_4_);
                this.setRenderBounds(0.5625, var9, 0.0, 0.8125, var10, 0.125);
                this.renderStandardBlock(p_147776_1_, p_147776_2_, p_147776_3_, p_147776_4_);
                this.setRenderBounds(0.5625, var9, 0.875, 0.8125, var10, 1.0);
                this.renderStandardBlock(p_147776_1_, p_147776_2_, p_147776_3_, p_147776_4_);
                this.setRenderBounds(0.5625, var11, 0.0, 0.8125, var12, 0.125);
                this.renderStandardBlock(p_147776_1_, p_147776_2_, p_147776_3_, p_147776_4_);
                this.setRenderBounds(0.5625, var11, 0.875, 0.8125, var12, 1.0);
                this.renderStandardBlock(p_147776_1_, p_147776_2_, p_147776_3_, p_147776_4_);
            }
            else if (var8 == 1) {
                final float var15 = 0.0f;
                final float var16 = 0.125f;
                final float var17 = 0.875f;
                final float var18 = 1.0f;
                final float var19 = 0.0625f;
                final float var20 = 0.1875f;
                final float var21 = 0.4375f;
                this.setRenderBounds(0.0625, var9, 0.0, 0.1875, var12, 0.125);
                this.renderStandardBlock(p_147776_1_, p_147776_2_, p_147776_3_, p_147776_4_);
                this.setRenderBounds(0.0625, var9, 0.875, 0.1875, var12, 1.0);
                this.renderStandardBlock(p_147776_1_, p_147776_2_, p_147776_3_, p_147776_4_);
                this.setRenderBounds(0.1875, var9, 0.0, 0.4375, var10, 0.125);
                this.renderStandardBlock(p_147776_1_, p_147776_2_, p_147776_3_, p_147776_4_);
                this.setRenderBounds(0.1875, var9, 0.875, 0.4375, var10, 1.0);
                this.renderStandardBlock(p_147776_1_, p_147776_2_, p_147776_3_, p_147776_4_);
                this.setRenderBounds(0.1875, var11, 0.0, 0.4375, var12, 0.125);
                this.renderStandardBlock(p_147776_1_, p_147776_2_, p_147776_3_, p_147776_4_);
                this.setRenderBounds(0.1875, var11, 0.875, 0.4375, var12, 1.0);
                this.renderStandardBlock(p_147776_1_, p_147776_2_, p_147776_3_, p_147776_4_);
            }
            else if (var8 == 0) {
                final float var15 = 0.0f;
                final float var16 = 0.125f;
                final float var17 = 0.875f;
                final float var18 = 1.0f;
                final float var19 = 0.5625f;
                final float var20 = 0.8125f;
                final float var21 = 0.9375f;
                this.setRenderBounds(0.0, var9, 0.8125, 0.125, var12, 0.9375);
                this.renderStandardBlock(p_147776_1_, p_147776_2_, p_147776_3_, p_147776_4_);
                this.setRenderBounds(0.875, var9, 0.8125, 1.0, var12, 0.9375);
                this.renderStandardBlock(p_147776_1_, p_147776_2_, p_147776_3_, p_147776_4_);
                this.setRenderBounds(0.0, var9, 0.5625, 0.125, var10, 0.8125);
                this.renderStandardBlock(p_147776_1_, p_147776_2_, p_147776_3_, p_147776_4_);
                this.setRenderBounds(0.875, var9, 0.5625, 1.0, var10, 0.8125);
                this.renderStandardBlock(p_147776_1_, p_147776_2_, p_147776_3_, p_147776_4_);
                this.setRenderBounds(0.0, var11, 0.5625, 0.125, var12, 0.8125);
                this.renderStandardBlock(p_147776_1_, p_147776_2_, p_147776_3_, p_147776_4_);
                this.setRenderBounds(0.875, var11, 0.5625, 1.0, var12, 0.8125);
                this.renderStandardBlock(p_147776_1_, p_147776_2_, p_147776_3_, p_147776_4_);
            }
            else if (var8 == 2) {
                final float var15 = 0.0f;
                final float var16 = 0.125f;
                final float var17 = 0.875f;
                final float var18 = 1.0f;
                final float var19 = 0.0625f;
                final float var20 = 0.1875f;
                final float var21 = 0.4375f;
                this.setRenderBounds(0.0, var9, 0.0625, 0.125, var12, 0.1875);
                this.renderStandardBlock(p_147776_1_, p_147776_2_, p_147776_3_, p_147776_4_);
                this.setRenderBounds(0.875, var9, 0.0625, 1.0, var12, 0.1875);
                this.renderStandardBlock(p_147776_1_, p_147776_2_, p_147776_3_, p_147776_4_);
                this.setRenderBounds(0.0, var9, 0.1875, 0.125, var10, 0.4375);
                this.renderStandardBlock(p_147776_1_, p_147776_2_, p_147776_3_, p_147776_4_);
                this.setRenderBounds(0.875, var9, 0.1875, 1.0, var10, 0.4375);
                this.renderStandardBlock(p_147776_1_, p_147776_2_, p_147776_3_, p_147776_4_);
                this.setRenderBounds(0.0, var11, 0.1875, 0.125, var12, 0.4375);
                this.renderStandardBlock(p_147776_1_, p_147776_2_, p_147776_3_, p_147776_4_);
                this.setRenderBounds(0.875, var11, 0.1875, 1.0, var12, 0.4375);
                this.renderStandardBlock(p_147776_1_, p_147776_2_, p_147776_3_, p_147776_4_);
            }
        }
        else if (var8 != 3 && var8 != 1) {
            float var15 = 0.375f;
            float var16 = 0.5f;
            final float var17 = 0.4375f;
            final float var18 = 0.5625f;
            this.setRenderBounds(var15, var9, var17, var16, var12, var18);
            this.renderStandardBlock(p_147776_1_, p_147776_2_, p_147776_3_, p_147776_4_);
            var15 = 0.5f;
            var16 = 0.625f;
            this.setRenderBounds(var15, var9, var17, var16, var12, var18);
            this.renderStandardBlock(p_147776_1_, p_147776_2_, p_147776_3_, p_147776_4_);
            var15 = 0.625f;
            var16 = 0.875f;
            this.setRenderBounds(var15, var9, var17, var16, var10, var18);
            this.renderStandardBlock(p_147776_1_, p_147776_2_, p_147776_3_, p_147776_4_);
            this.setRenderBounds(var15, var11, var17, var16, var12, var18);
            this.renderStandardBlock(p_147776_1_, p_147776_2_, p_147776_3_, p_147776_4_);
            var15 = 0.125f;
            var16 = 0.375f;
            this.setRenderBounds(var15, var9, var17, var16, var10, var18);
            this.renderStandardBlock(p_147776_1_, p_147776_2_, p_147776_3_, p_147776_4_);
            this.setRenderBounds(var15, var11, var17, var16, var12, var18);
            this.renderStandardBlock(p_147776_1_, p_147776_2_, p_147776_3_, p_147776_4_);
        }
        else {
            this.uvRotateTop = 1;
            final float var15 = 0.4375f;
            final float var16 = 0.5625f;
            float var17 = 0.375f;
            float var18 = 0.5f;
            this.setRenderBounds(var15, var9, var17, var16, var12, var18);
            this.renderStandardBlock(p_147776_1_, p_147776_2_, p_147776_3_, p_147776_4_);
            var17 = 0.5f;
            var18 = 0.625f;
            this.setRenderBounds(var15, var9, var17, var16, var12, var18);
            this.renderStandardBlock(p_147776_1_, p_147776_2_, p_147776_3_, p_147776_4_);
            var17 = 0.625f;
            var18 = 0.875f;
            this.setRenderBounds(var15, var9, var17, var16, var10, var18);
            this.renderStandardBlock(p_147776_1_, p_147776_2_, p_147776_3_, p_147776_4_);
            this.setRenderBounds(var15, var11, var17, var16, var12, var18);
            this.renderStandardBlock(p_147776_1_, p_147776_2_, p_147776_3_, p_147776_4_);
            var17 = 0.125f;
            var18 = 0.375f;
            this.setRenderBounds(var15, var9, var17, var16, var10, var18);
            this.renderStandardBlock(p_147776_1_, p_147776_2_, p_147776_3_, p_147776_4_);
            this.setRenderBounds(var15, var11, var17, var16, var12, var18);
            this.renderStandardBlock(p_147776_1_, p_147776_2_, p_147776_3_, p_147776_4_);
        }
        if (Config.isBetterSnow() && this.hasSnowNeighbours(p_147776_2_, p_147776_3_, p_147776_4_)) {
            this.renderSnow(p_147776_2_, p_147776_3_, p_147776_4_, Blocks.snow_layer.getBlockBoundsMaxY());
        }
        this.renderAllFaces = false;
        this.uvRotateTop = 0;
        this.setRenderBounds(0.0, 0.0, 0.0, 1.0, 1.0, 1.0);
        return var5;
    }
    
    public boolean renderBlockHopper(final BlockHopper p_147803_1_, final int p_147803_2_, final int p_147803_3_, final int p_147803_4_) {
        final Tessellator var5 = Tessellator.instance;
        var5.setBrightness(p_147803_1_.getBlockBrightness(this.blockAccess, p_147803_2_, p_147803_3_, p_147803_4_));
        final int var6 = p_147803_1_.colorMultiplier(this.blockAccess, p_147803_2_, p_147803_3_, p_147803_4_);
        float var7 = (var6 >> 16 & 0xFF) / 255.0f;
        float var8 = (var6 >> 8 & 0xFF) / 255.0f;
        float var9 = (var6 & 0xFF) / 255.0f;
        if (EntityRenderer.anaglyphEnable) {
            final float var10 = (var7 * 30.0f + var8 * 59.0f + var9 * 11.0f) / 100.0f;
            final float var11 = (var7 * 30.0f + var8 * 70.0f) / 100.0f;
            final float var12 = (var7 * 30.0f + var9 * 70.0f) / 100.0f;
            var7 = var10;
            var8 = var11;
            var9 = var12;
        }
        var5.setColorOpaque_F(var7, var8, var9);
        return this.renderBlockHopperMetadata(p_147803_1_, p_147803_2_, p_147803_3_, p_147803_4_, this.blockAccess.getBlockMetadata(p_147803_2_, p_147803_3_, p_147803_4_), false);
    }
    
    public boolean renderBlockHopperMetadata(final BlockHopper p_147799_1_, final int p_147799_2_, final int p_147799_3_, final int p_147799_4_, final int p_147799_5_, final boolean p_147799_6_) {
        final Tessellator var7 = Tessellator.instance;
        final int var8 = BlockHopper.func_149918_b(p_147799_5_);
        final double var9 = 0.625;
        this.setRenderBounds(0.0, var9, 0.0, 1.0, 1.0, 1.0);
        if (p_147799_6_) {
            var7.startDrawingQuads();
            var7.setNormal(0.0f, -1.0f, 0.0f);
            this.renderFaceYNeg(p_147799_1_, 0.0, 0.0, 0.0, this.getBlockIconFromSideAndMetadata(p_147799_1_, 0, p_147799_5_));
            var7.draw();
            var7.startDrawingQuads();
            var7.setNormal(0.0f, 1.0f, 0.0f);
            this.renderFaceYPos(p_147799_1_, 0.0, 0.0, 0.0, this.getBlockIconFromSideAndMetadata(p_147799_1_, 1, p_147799_5_));
            var7.draw();
            var7.startDrawingQuads();
            var7.setNormal(0.0f, 0.0f, -1.0f);
            this.renderFaceZNeg(p_147799_1_, 0.0, 0.0, 0.0, this.getBlockIconFromSideAndMetadata(p_147799_1_, 2, p_147799_5_));
            var7.draw();
            var7.startDrawingQuads();
            var7.setNormal(0.0f, 0.0f, 1.0f);
            this.renderFaceZPos(p_147799_1_, 0.0, 0.0, 0.0, this.getBlockIconFromSideAndMetadata(p_147799_1_, 3, p_147799_5_));
            var7.draw();
            var7.startDrawingQuads();
            var7.setNormal(-1.0f, 0.0f, 0.0f);
            this.renderFaceXNeg(p_147799_1_, 0.0, 0.0, 0.0, this.getBlockIconFromSideAndMetadata(p_147799_1_, 4, p_147799_5_));
            var7.draw();
            var7.startDrawingQuads();
            var7.setNormal(1.0f, 0.0f, 0.0f);
            this.renderFaceXPos(p_147799_1_, 0.0, 0.0, 0.0, this.getBlockIconFromSideAndMetadata(p_147799_1_, 5, p_147799_5_));
            var7.draw();
        }
        else {
            this.renderStandardBlock(p_147799_1_, p_147799_2_, p_147799_3_, p_147799_4_);
        }
        if (!p_147799_6_) {
            var7.setBrightness(p_147799_1_.getBlockBrightness(this.blockAccess, p_147799_2_, p_147799_3_, p_147799_4_));
            final int var10 = p_147799_1_.colorMultiplier(this.blockAccess, p_147799_2_, p_147799_3_, p_147799_4_);
            float var11 = (var10 >> 16 & 0xFF) / 255.0f;
            float var12 = (var10 >> 8 & 0xFF) / 255.0f;
            float var13 = (var10 & 0xFF) / 255.0f;
            if (EntityRenderer.anaglyphEnable) {
                final float var14 = (var11 * 30.0f + var12 * 59.0f + var13 * 11.0f) / 100.0f;
                final float var15 = (var11 * 30.0f + var12 * 70.0f) / 100.0f;
                final float var16 = (var11 * 30.0f + var13 * 70.0f) / 100.0f;
                var11 = var14;
                var12 = var15;
                var13 = var16;
            }
            var7.setColorOpaque_F(var11, var12, var13);
        }
        final IIcon var17 = BlockHopper.func_149916_e("hopper_outside");
        final IIcon var18 = BlockHopper.func_149916_e("hopper_inside");
        float var12 = 0.125f;
        if (p_147799_6_) {
            var7.startDrawingQuads();
            var7.setNormal(1.0f, 0.0f, 0.0f);
            this.renderFaceXPos(p_147799_1_, -1.0f + var12, 0.0, 0.0, var17);
            var7.draw();
            var7.startDrawingQuads();
            var7.setNormal(-1.0f, 0.0f, 0.0f);
            this.renderFaceXNeg(p_147799_1_, 1.0f - var12, 0.0, 0.0, var17);
            var7.draw();
            var7.startDrawingQuads();
            var7.setNormal(0.0f, 0.0f, 1.0f);
            this.renderFaceZPos(p_147799_1_, 0.0, 0.0, -1.0f + var12, var17);
            var7.draw();
            var7.startDrawingQuads();
            var7.setNormal(0.0f, 0.0f, -1.0f);
            this.renderFaceZNeg(p_147799_1_, 0.0, 0.0, 1.0f - var12, var17);
            var7.draw();
            var7.startDrawingQuads();
            var7.setNormal(0.0f, 1.0f, 0.0f);
            this.renderFaceYPos(p_147799_1_, 0.0, -1.0 + var9, 0.0, var18);
            var7.draw();
        }
        else {
            this.renderFaceXPos(p_147799_1_, p_147799_2_ - 1.0f + var12, p_147799_3_, p_147799_4_, var17);
            this.renderFaceXNeg(p_147799_1_, p_147799_2_ + 1.0f - var12, p_147799_3_, p_147799_4_, var17);
            this.renderFaceZPos(p_147799_1_, p_147799_2_, p_147799_3_, p_147799_4_ - 1.0f + var12, var17);
            this.renderFaceZNeg(p_147799_1_, p_147799_2_, p_147799_3_, p_147799_4_ + 1.0f - var12, var17);
            this.renderFaceYPos(p_147799_1_, p_147799_2_, p_147799_3_ - 1.0f + var9, p_147799_4_, var18);
        }
        this.setOverrideBlockTexture(var17);
        final double var19 = 0.25;
        final double var20 = 0.25;
        this.setRenderBounds(var19, var20, var19, 1.0 - var19, var9 - 0.002, 1.0 - var19);
        if (p_147799_6_) {
            var7.startDrawingQuads();
            var7.setNormal(1.0f, 0.0f, 0.0f);
            this.renderFaceXPos(p_147799_1_, 0.0, 0.0, 0.0, var17);
            var7.draw();
            var7.startDrawingQuads();
            var7.setNormal(-1.0f, 0.0f, 0.0f);
            this.renderFaceXNeg(p_147799_1_, 0.0, 0.0, 0.0, var17);
            var7.draw();
            var7.startDrawingQuads();
            var7.setNormal(0.0f, 0.0f, 1.0f);
            this.renderFaceZPos(p_147799_1_, 0.0, 0.0, 0.0, var17);
            var7.draw();
            var7.startDrawingQuads();
            var7.setNormal(0.0f, 0.0f, -1.0f);
            this.renderFaceZNeg(p_147799_1_, 0.0, 0.0, 0.0, var17);
            var7.draw();
            var7.startDrawingQuads();
            var7.setNormal(0.0f, 1.0f, 0.0f);
            this.renderFaceYPos(p_147799_1_, 0.0, 0.0, 0.0, var17);
            var7.draw();
            var7.startDrawingQuads();
            var7.setNormal(0.0f, -1.0f, 0.0f);
            this.renderFaceYNeg(p_147799_1_, 0.0, 0.0, 0.0, var17);
            var7.draw();
        }
        else {
            this.renderStandardBlock(p_147799_1_, p_147799_2_, p_147799_3_, p_147799_4_);
        }
        if (!p_147799_6_) {
            final double var21 = 0.375;
            final double var22 = 0.25;
            this.setOverrideBlockTexture(var17);
            if (var8 == 0) {
                this.setRenderBounds(var21, 0.0, var21, 1.0 - var21, 0.25, 1.0 - var21);
                this.renderStandardBlock(p_147799_1_, p_147799_2_, p_147799_3_, p_147799_4_);
            }
            if (var8 == 2) {
                this.setRenderBounds(var21, var20, 0.0, 1.0 - var21, var20 + var22, var19);
                this.renderStandardBlock(p_147799_1_, p_147799_2_, p_147799_3_, p_147799_4_);
            }
            if (var8 == 3) {
                this.setRenderBounds(var21, var20, 1.0 - var19, 1.0 - var21, var20 + var22, 1.0);
                this.renderStandardBlock(p_147799_1_, p_147799_2_, p_147799_3_, p_147799_4_);
            }
            if (var8 == 4) {
                this.setRenderBounds(0.0, var20, var21, var19, var20 + var22, 1.0 - var21);
                this.renderStandardBlock(p_147799_1_, p_147799_2_, p_147799_3_, p_147799_4_);
            }
            if (var8 == 5) {
                this.setRenderBounds(1.0 - var19, var20, var21, 1.0, var20 + var22, 1.0 - var21);
                this.renderStandardBlock(p_147799_1_, p_147799_2_, p_147799_3_, p_147799_4_);
            }
        }
        this.clearOverrideBlockTexture();
        return true;
    }
    
    public boolean renderBlockStairs(final BlockStairs p_147722_1_, final int p_147722_2_, final int p_147722_3_, final int p_147722_4_) {
        p_147722_1_.func_150147_e(this.blockAccess, p_147722_2_, p_147722_3_, p_147722_4_);
        this.setRenderBoundsFromBlock(p_147722_1_);
        this.renderStandardBlock(p_147722_1_, p_147722_2_, p_147722_3_, p_147722_4_);
        this.field_152631_f = true;
        final boolean var5 = p_147722_1_.func_150145_f(this.blockAccess, p_147722_2_, p_147722_3_, p_147722_4_);
        this.setRenderBoundsFromBlock(p_147722_1_);
        this.renderStandardBlock(p_147722_1_, p_147722_2_, p_147722_3_, p_147722_4_);
        if (var5 && p_147722_1_.func_150144_g(this.blockAccess, p_147722_2_, p_147722_3_, p_147722_4_)) {
            this.setRenderBoundsFromBlock(p_147722_1_);
            this.renderStandardBlock(p_147722_1_, p_147722_2_, p_147722_3_, p_147722_4_);
        }
        this.field_152631_f = false;
        return true;
    }
    
    public boolean renderBlockDoor(final Block p_147760_1_, final int p_147760_2_, final int p_147760_3_, final int p_147760_4_) {
        final Tessellator var5 = Tessellator.instance;
        final int var6 = this.blockAccess.getBlockMetadata(p_147760_2_, p_147760_3_, p_147760_4_);
        if ((var6 & 0x8) != 0x0) {
            if (this.blockAccess.getBlock(p_147760_2_, p_147760_3_ - 1, p_147760_4_) != p_147760_1_) {
                return false;
            }
        }
        else if (this.blockAccess.getBlock(p_147760_2_, p_147760_3_ + 1, p_147760_4_) != p_147760_1_) {
            return false;
        }
        boolean var7 = false;
        final float var8 = 0.5f;
        final float var9 = 1.0f;
        final float var10 = 0.8f;
        final float var11 = 0.6f;
        final int var12 = p_147760_1_.getBlockBrightness(this.blockAccess, p_147760_2_, p_147760_3_, p_147760_4_);
        var5.setBrightness((this.renderMinY > 0.0) ? var12 : p_147760_1_.getBlockBrightness(this.blockAccess, p_147760_2_, p_147760_3_ - 1, p_147760_4_));
        var5.setColorOpaque_F(var8, var8, var8);
        this.renderFaceYNeg(p_147760_1_, p_147760_2_, p_147760_3_, p_147760_4_, this.getBlockIcon(p_147760_1_, this.blockAccess, p_147760_2_, p_147760_3_, p_147760_4_, 0));
        var7 = true;
        var5.setBrightness((this.renderMaxY < 1.0) ? var12 : p_147760_1_.getBlockBrightness(this.blockAccess, p_147760_2_, p_147760_3_ + 1, p_147760_4_));
        var5.setColorOpaque_F(var9, var9, var9);
        this.renderFaceYPos(p_147760_1_, p_147760_2_, p_147760_3_, p_147760_4_, this.getBlockIcon(p_147760_1_, this.blockAccess, p_147760_2_, p_147760_3_, p_147760_4_, 1));
        var7 = true;
        var5.setBrightness((this.renderMinZ > 0.0) ? var12 : p_147760_1_.getBlockBrightness(this.blockAccess, p_147760_2_, p_147760_3_, p_147760_4_ - 1));
        var5.setColorOpaque_F(var10, var10, var10);
        IIcon var13 = this.getBlockIcon(p_147760_1_, this.blockAccess, p_147760_2_, p_147760_3_, p_147760_4_, 2);
        this.renderFaceZNeg(p_147760_1_, p_147760_2_, p_147760_3_, p_147760_4_, var13);
        var7 = true;
        this.flipTexture = false;
        var5.setBrightness((this.renderMaxZ < 1.0) ? var12 : p_147760_1_.getBlockBrightness(this.blockAccess, p_147760_2_, p_147760_3_, p_147760_4_ + 1));
        var5.setColorOpaque_F(var10, var10, var10);
        var13 = this.getBlockIcon(p_147760_1_, this.blockAccess, p_147760_2_, p_147760_3_, p_147760_4_, 3);
        this.renderFaceZPos(p_147760_1_, p_147760_2_, p_147760_3_, p_147760_4_, var13);
        var7 = true;
        this.flipTexture = false;
        var5.setBrightness((this.renderMinX > 0.0) ? var12 : p_147760_1_.getBlockBrightness(this.blockAccess, p_147760_2_ - 1, p_147760_3_, p_147760_4_));
        var5.setColorOpaque_F(var11, var11, var11);
        var13 = this.getBlockIcon(p_147760_1_, this.blockAccess, p_147760_2_, p_147760_3_, p_147760_4_, 4);
        this.renderFaceXNeg(p_147760_1_, p_147760_2_, p_147760_3_, p_147760_4_, var13);
        var7 = true;
        this.flipTexture = false;
        var5.setBrightness((this.renderMaxX < 1.0) ? var12 : p_147760_1_.getBlockBrightness(this.blockAccess, p_147760_2_ + 1, p_147760_3_, p_147760_4_));
        var5.setColorOpaque_F(var11, var11, var11);
        var13 = this.getBlockIcon(p_147760_1_, this.blockAccess, p_147760_2_, p_147760_3_, p_147760_4_, 5);
        this.renderFaceXPos(p_147760_1_, p_147760_2_, p_147760_3_, p_147760_4_, var13);
        var7 = true;
        this.flipTexture = false;
        return var7;
    }
    
    public void renderFaceYNeg(final Block p_147768_1_, final double p_147768_2_, final double p_147768_4_, final double p_147768_6_, IIcon p_147768_8_) {
        final Tessellator var9 = Tessellator.instance;
        if (this.hasOverrideBlockTexture()) {
            p_147768_8_ = this.overrideBlockTexture;
        }
        if (Config.isConnectedTextures() && this.overrideBlockTexture == null) {
            p_147768_8_ = ConnectedTextures.getConnectedTexture(this.blockAccess, p_147768_1_, (int)p_147768_2_, (int)p_147768_4_, (int)p_147768_6_, 0, p_147768_8_);
        }
        boolean uvRotateSet = false;
        if (Config.isNaturalTextures() && this.overrideBlockTexture == null && this.uvRotateBottom == 0) {
            final NaturalProperties var10 = NaturalTextures.getNaturalProperties(p_147768_8_);
            if (var10 != null) {
                final int rand = Config.getRandom((int)p_147768_2_, (int)p_147768_4_, (int)p_147768_6_, 0);
                if (var10.rotation > 1) {
                    this.uvRotateBottom = (rand & 0x3);
                }
                if (var10.rotation == 2) {
                    this.uvRotateBottom = this.uvRotateBottom / 2 * 3;
                }
                if (var10.flip) {
                    this.flipTexture = ((rand & 0x4) != 0x0);
                }
                uvRotateSet = true;
            }
        }
        double var11 = p_147768_8_.getInterpolatedU(this.renderMinX * 16.0);
        double var12 = p_147768_8_.getInterpolatedU(this.renderMaxX * 16.0);
        double var13 = p_147768_8_.getInterpolatedV(this.renderMinZ * 16.0);
        double var14 = p_147768_8_.getInterpolatedV(this.renderMaxZ * 16.0);
        if (this.renderMinX < 0.0 || this.renderMaxX > 1.0) {
            var11 = p_147768_8_.getMinU();
            var12 = p_147768_8_.getMaxU();
        }
        if (this.renderMinZ < 0.0 || this.renderMaxZ > 1.0) {
            var13 = p_147768_8_.getMinV();
            var14 = p_147768_8_.getMaxV();
        }
        if (this.flipTexture) {
            final double var15 = var11;
            var11 = var12;
            var12 = var15;
        }
        double var15 = var12;
        double var16 = var11;
        double var17 = var13;
        double var18 = var14;
        if (this.uvRotateBottom == 2) {
            var11 = p_147768_8_.getInterpolatedU(this.renderMinZ * 16.0);
            var13 = p_147768_8_.getInterpolatedV(16.0 - this.renderMaxX * 16.0);
            var12 = p_147768_8_.getInterpolatedU(this.renderMaxZ * 16.0);
            var14 = p_147768_8_.getInterpolatedV(16.0 - this.renderMinX * 16.0);
            if (this.flipTexture) {
                final double var19 = var11;
                var11 = var12;
                var12 = var19;
            }
            var17 = var13;
            var18 = var14;
            var15 = var11;
            var16 = var12;
            var13 = var14;
            var14 = var17;
        }
        else if (this.uvRotateBottom == 1) {
            var11 = p_147768_8_.getInterpolatedU(16.0 - this.renderMaxZ * 16.0);
            var13 = p_147768_8_.getInterpolatedV(this.renderMinX * 16.0);
            var12 = p_147768_8_.getInterpolatedU(16.0 - this.renderMinZ * 16.0);
            var14 = p_147768_8_.getInterpolatedV(this.renderMaxX * 16.0);
            if (this.flipTexture) {
                final double var19 = var11;
                var11 = var12;
                var12 = var19;
            }
            var15 = var12;
            var16 = var11;
            var11 = var12;
            var12 = var16;
            var17 = var14;
            var18 = var13;
        }
        else if (this.uvRotateBottom == 3) {
            var11 = p_147768_8_.getInterpolatedU(16.0 - this.renderMinX * 16.0);
            var12 = p_147768_8_.getInterpolatedU(16.0 - this.renderMaxX * 16.0);
            var13 = p_147768_8_.getInterpolatedV(16.0 - this.renderMinZ * 16.0);
            var14 = p_147768_8_.getInterpolatedV(16.0 - this.renderMaxZ * 16.0);
            if (this.flipTexture) {
                final double var19 = var11;
                var11 = var12;
                var12 = var19;
            }
            var15 = var12;
            var16 = var11;
            var17 = var13;
            var18 = var14;
        }
        if (uvRotateSet) {
            this.uvRotateBottom = 0;
            this.flipTexture = false;
        }
        double var19 = p_147768_2_ + this.renderMinX;
        double var20 = p_147768_2_ + this.renderMaxX;
        final double var21 = p_147768_4_ + this.renderMinY;
        final double var22 = p_147768_6_ + this.renderMinZ;
        final double var23 = p_147768_6_ + this.renderMaxZ;
        if (this.renderFromInside) {
            var19 = p_147768_2_ + this.renderMaxX;
            var20 = p_147768_2_ + this.renderMinX;
        }
        if (this.enableAO) {
            var9.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
            var9.setBrightness(this.brightnessTopLeft);
            var9.addVertexWithUV(var19, var21, var23, var16, var18);
            var9.setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft);
            var9.setBrightness(this.brightnessBottomLeft);
            var9.addVertexWithUV(var19, var21, var22, var11, var13);
            var9.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight);
            var9.setBrightness(this.brightnessBottomRight);
            var9.addVertexWithUV(var20, var21, var22, var15, var17);
            var9.setColorOpaque_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight);
            var9.setBrightness(this.brightnessTopRight);
            var9.addVertexWithUV(var20, var21, var23, var12, var14);
        }
        else {
            var9.addVertexWithUV(var19, var21, var23, var16, var18);
            var9.addVertexWithUV(var19, var21, var22, var11, var13);
            var9.addVertexWithUV(var20, var21, var22, var15, var17);
            var9.addVertexWithUV(var20, var21, var23, var12, var14);
        }
    }
    
    public void renderFaceYPos(final Block p_147806_1_, final double p_147806_2_, final double p_147806_4_, final double p_147806_6_, IIcon p_147806_8_) {
        final Tessellator var9 = Tessellator.instance;
        if (this.hasOverrideBlockTexture()) {
            p_147806_8_ = this.overrideBlockTexture;
        }
        if (Config.isConnectedTextures() && this.overrideBlockTexture == null) {
            p_147806_8_ = ConnectedTextures.getConnectedTexture(this.blockAccess, p_147806_1_, (int)p_147806_2_, (int)p_147806_4_, (int)p_147806_6_, 1, p_147806_8_);
        }
        boolean uvRotateSet = false;
        if (Config.isNaturalTextures() && this.overrideBlockTexture == null && this.uvRotateTop == 0) {
            final NaturalProperties var10 = NaturalTextures.getNaturalProperties(p_147806_8_);
            if (var10 != null) {
                final int rand = Config.getRandom((int)p_147806_2_, (int)p_147806_4_, (int)p_147806_6_, 1);
                if (var10.rotation > 1) {
                    this.uvRotateTop = (rand & 0x3);
                }
                if (var10.rotation == 2) {
                    this.uvRotateTop = this.uvRotateTop / 2 * 3;
                }
                if (var10.flip) {
                    this.flipTexture = ((rand & 0x4) != 0x0);
                }
                uvRotateSet = true;
            }
        }
        double var11 = p_147806_8_.getInterpolatedU(this.renderMinX * 16.0);
        double var12 = p_147806_8_.getInterpolatedU(this.renderMaxX * 16.0);
        double var13 = p_147806_8_.getInterpolatedV(this.renderMinZ * 16.0);
        double var14 = p_147806_8_.getInterpolatedV(this.renderMaxZ * 16.0);
        if (this.flipTexture) {
            final double var15 = var11;
            var11 = var12;
            var12 = var15;
        }
        if (this.renderMinX < 0.0 || this.renderMaxX > 1.0) {
            var11 = p_147806_8_.getMinU();
            var12 = p_147806_8_.getMaxU();
        }
        if (this.renderMinZ < 0.0 || this.renderMaxZ > 1.0) {
            var13 = p_147806_8_.getMinV();
            var14 = p_147806_8_.getMaxV();
        }
        double var15 = var12;
        double var16 = var11;
        double var17 = var13;
        double var18 = var14;
        if (this.uvRotateTop == 1) {
            var11 = p_147806_8_.getInterpolatedU(this.renderMinZ * 16.0);
            var13 = p_147806_8_.getInterpolatedV(16.0 - this.renderMaxX * 16.0);
            var12 = p_147806_8_.getInterpolatedU(this.renderMaxZ * 16.0);
            var14 = p_147806_8_.getInterpolatedV(16.0 - this.renderMinX * 16.0);
            if (this.flipTexture) {
                final double var19 = var11;
                var11 = var12;
                var12 = var19;
            }
            var17 = var13;
            var18 = var14;
            var15 = var11;
            var16 = var12;
            var13 = var14;
            var14 = var17;
        }
        else if (this.uvRotateTop == 2) {
            var11 = p_147806_8_.getInterpolatedU(16.0 - this.renderMaxZ * 16.0);
            var13 = p_147806_8_.getInterpolatedV(this.renderMinX * 16.0);
            var12 = p_147806_8_.getInterpolatedU(16.0 - this.renderMinZ * 16.0);
            var14 = p_147806_8_.getInterpolatedV(this.renderMaxX * 16.0);
            if (this.flipTexture) {
                final double var19 = var11;
                var11 = var12;
                var12 = var19;
            }
            var15 = var12;
            var16 = var11;
            var11 = var12;
            var12 = var16;
            var17 = var14;
            var18 = var13;
        }
        else if (this.uvRotateTop == 3) {
            var11 = p_147806_8_.getInterpolatedU(16.0 - this.renderMinX * 16.0);
            var12 = p_147806_8_.getInterpolatedU(16.0 - this.renderMaxX * 16.0);
            var13 = p_147806_8_.getInterpolatedV(16.0 - this.renderMinZ * 16.0);
            var14 = p_147806_8_.getInterpolatedV(16.0 - this.renderMaxZ * 16.0);
            if (this.flipTexture) {
                final double var19 = var11;
                var11 = var12;
                var12 = var19;
            }
            var15 = var12;
            var16 = var11;
            var17 = var13;
            var18 = var14;
        }
        if (uvRotateSet) {
            this.uvRotateTop = 0;
            this.flipTexture = false;
        }
        double var19 = p_147806_2_ + this.renderMinX;
        double var20 = p_147806_2_ + this.renderMaxX;
        final double var21 = p_147806_4_ + this.renderMaxY;
        final double var22 = p_147806_6_ + this.renderMinZ;
        final double var23 = p_147806_6_ + this.renderMaxZ;
        if (this.renderFromInside) {
            var19 = p_147806_2_ + this.renderMaxX;
            var20 = p_147806_2_ + this.renderMinX;
        }
        if (this.enableAO) {
            var9.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
            var9.setBrightness(this.brightnessTopLeft);
            var9.addVertexWithUV(var20, var21, var23, var12, var14);
            var9.setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft);
            var9.setBrightness(this.brightnessBottomLeft);
            var9.addVertexWithUV(var20, var21, var22, var15, var17);
            var9.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight);
            var9.setBrightness(this.brightnessBottomRight);
            var9.addVertexWithUV(var19, var21, var22, var11, var13);
            var9.setColorOpaque_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight);
            var9.setBrightness(this.brightnessTopRight);
            var9.addVertexWithUV(var19, var21, var23, var16, var18);
        }
        else {
            var9.addVertexWithUV(var20, var21, var23, var12, var14);
            var9.addVertexWithUV(var20, var21, var22, var15, var17);
            var9.addVertexWithUV(var19, var21, var22, var11, var13);
            var9.addVertexWithUV(var19, var21, var23, var16, var18);
        }
    }
    
    public void renderFaceZNeg(final Block p_147761_1_, final double p_147761_2_, final double p_147761_4_, final double p_147761_6_, IIcon p_147761_8_) {
        final Tessellator var9 = Tessellator.instance;
        if (this.hasOverrideBlockTexture()) {
            p_147761_8_ = this.overrideBlockTexture;
        }
        if (Config.isConnectedTextures() && this.overrideBlockTexture == null) {
            p_147761_8_ = ConnectedTextures.getConnectedTexture(this.blockAccess, p_147761_1_, (int)p_147761_2_, (int)p_147761_4_, (int)p_147761_6_, 2, p_147761_8_);
        }
        boolean uvRotateSet = false;
        if (Config.isNaturalTextures() && this.overrideBlockTexture == null && this.uvRotateEast == 0) {
            final NaturalProperties var10 = NaturalTextures.getNaturalProperties(p_147761_8_);
            if (var10 != null) {
                final int rand = Config.getRandom((int)p_147761_2_, (int)p_147761_4_, (int)p_147761_6_, 2);
                if (var10.rotation > 1) {
                    this.uvRotateEast = (rand & 0x3);
                }
                if (var10.rotation == 2) {
                    this.uvRotateEast = this.uvRotateEast / 2 * 3;
                }
                if (var10.flip) {
                    this.flipTexture = ((rand & 0x4) != 0x0);
                }
                uvRotateSet = true;
            }
        }
        double var11 = p_147761_8_.getInterpolatedU(this.renderMinX * 16.0);
        double var12 = p_147761_8_.getInterpolatedU(this.renderMaxX * 16.0);
        if (this.field_152631_f) {
            var12 = p_147761_8_.getInterpolatedU((1.0 - this.renderMinX) * 16.0);
            var11 = p_147761_8_.getInterpolatedU((1.0 - this.renderMaxX) * 16.0);
        }
        double var13 = p_147761_8_.getInterpolatedV(16.0 - this.renderMaxY * 16.0);
        double var14 = p_147761_8_.getInterpolatedV(16.0 - this.renderMinY * 16.0);
        if (this.flipTexture) {
            final double var15 = var11;
            var11 = var12;
            var12 = var15;
        }
        if (this.renderMinX < 0.0 || this.renderMaxX > 1.0) {
            var11 = p_147761_8_.getMinU();
            var12 = p_147761_8_.getMaxU();
        }
        if (this.renderMinY < 0.0 || this.renderMaxY > 1.0) {
            var13 = p_147761_8_.getMinV();
            var14 = p_147761_8_.getMaxV();
        }
        double var15 = var12;
        double var16 = var11;
        double var17 = var13;
        double var18 = var14;
        if (this.uvRotateEast == 2) {
            var11 = p_147761_8_.getInterpolatedU(this.renderMinY * 16.0);
            var12 = p_147761_8_.getInterpolatedU(this.renderMaxY * 16.0);
            var13 = p_147761_8_.getInterpolatedV(16.0 - this.renderMinX * 16.0);
            var14 = p_147761_8_.getInterpolatedV(16.0 - this.renderMaxX * 16.0);
            if (this.flipTexture) {
                final double var19 = var11;
                var11 = var12;
                var12 = var19;
            }
            var17 = var13;
            var18 = var14;
            var15 = var11;
            var16 = var12;
            var13 = var14;
            var14 = var17;
        }
        else if (this.uvRotateEast == 1) {
            var11 = p_147761_8_.getInterpolatedU(16.0 - this.renderMaxY * 16.0);
            var12 = p_147761_8_.getInterpolatedU(16.0 - this.renderMinY * 16.0);
            var13 = p_147761_8_.getInterpolatedV(this.renderMaxX * 16.0);
            var14 = p_147761_8_.getInterpolatedV(this.renderMinX * 16.0);
            if (this.flipTexture) {
                final double var19 = var11;
                var11 = var12;
                var12 = var19;
            }
            var15 = var12;
            var16 = var11;
            var11 = var12;
            var12 = var16;
            var17 = var14;
            var18 = var13;
        }
        else if (this.uvRotateEast == 3) {
            var11 = p_147761_8_.getInterpolatedU(16.0 - this.renderMinX * 16.0);
            var12 = p_147761_8_.getInterpolatedU(16.0 - this.renderMaxX * 16.0);
            var13 = p_147761_8_.getInterpolatedV(this.renderMaxY * 16.0);
            var14 = p_147761_8_.getInterpolatedV(this.renderMinY * 16.0);
            if (this.flipTexture) {
                final double var19 = var11;
                var11 = var12;
                var12 = var19;
            }
            var15 = var12;
            var16 = var11;
            var17 = var13;
            var18 = var14;
        }
        if (uvRotateSet) {
            this.uvRotateEast = 0;
            this.flipTexture = false;
        }
        double var19 = p_147761_2_ + this.renderMinX;
        double var20 = p_147761_2_ + this.renderMaxX;
        final double var21 = p_147761_4_ + this.renderMinY;
        final double var22 = p_147761_4_ + this.renderMaxY;
        final double var23 = p_147761_6_ + this.renderMinZ;
        if (this.renderFromInside) {
            var19 = p_147761_2_ + this.renderMaxX;
            var20 = p_147761_2_ + this.renderMinX;
        }
        if (this.enableAO) {
            var9.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
            var9.setBrightness(this.brightnessTopLeft);
            var9.addVertexWithUV(var19, var22, var23, var15, var17);
            var9.setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft);
            var9.setBrightness(this.brightnessBottomLeft);
            var9.addVertexWithUV(var20, var22, var23, var11, var13);
            var9.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight);
            var9.setBrightness(this.brightnessBottomRight);
            var9.addVertexWithUV(var20, var21, var23, var16, var18);
            var9.setColorOpaque_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight);
            var9.setBrightness(this.brightnessTopRight);
            var9.addVertexWithUV(var19, var21, var23, var12, var14);
        }
        else {
            var9.addVertexWithUV(var19, var22, var23, var15, var17);
            var9.addVertexWithUV(var20, var22, var23, var11, var13);
            var9.addVertexWithUV(var20, var21, var23, var16, var18);
            var9.addVertexWithUV(var19, var21, var23, var12, var14);
        }
    }
    
    public void renderFaceZPos(final Block p_147734_1_, final double p_147734_2_, final double p_147734_4_, final double p_147734_6_, IIcon p_147734_8_) {
        final Tessellator var9 = Tessellator.instance;
        if (this.hasOverrideBlockTexture()) {
            p_147734_8_ = this.overrideBlockTexture;
        }
        if (Config.isConnectedTextures() && this.overrideBlockTexture == null) {
            p_147734_8_ = ConnectedTextures.getConnectedTexture(this.blockAccess, p_147734_1_, (int)p_147734_2_, (int)p_147734_4_, (int)p_147734_6_, 3, p_147734_8_);
        }
        boolean uvRotateSet = false;
        if (Config.isNaturalTextures() && this.overrideBlockTexture == null && this.uvRotateWest == 0) {
            final NaturalProperties var10 = NaturalTextures.getNaturalProperties(p_147734_8_);
            if (var10 != null) {
                final int rand = Config.getRandom((int)p_147734_2_, (int)p_147734_4_, (int)p_147734_6_, 3);
                if (var10.rotation > 1) {
                    this.uvRotateWest = (rand & 0x3);
                }
                if (var10.rotation == 2) {
                    this.uvRotateWest = this.uvRotateWest / 2 * 3;
                }
                if (var10.flip) {
                    this.flipTexture = ((rand & 0x4) != 0x0);
                }
                uvRotateSet = true;
            }
        }
        double var11 = p_147734_8_.getInterpolatedU(this.renderMinX * 16.0);
        double var12 = p_147734_8_.getInterpolatedU(this.renderMaxX * 16.0);
        double var13 = p_147734_8_.getInterpolatedV(16.0 - this.renderMaxY * 16.0);
        double var14 = p_147734_8_.getInterpolatedV(16.0 - this.renderMinY * 16.0);
        if (this.flipTexture) {
            final double var15 = var11;
            var11 = var12;
            var12 = var15;
        }
        if (this.renderMinX < 0.0 || this.renderMaxX > 1.0) {
            var11 = p_147734_8_.getMinU();
            var12 = p_147734_8_.getMaxU();
        }
        if (this.renderMinY < 0.0 || this.renderMaxY > 1.0) {
            var13 = p_147734_8_.getMinV();
            var14 = p_147734_8_.getMaxV();
        }
        double var15 = var12;
        double var16 = var11;
        double var17 = var13;
        double var18 = var14;
        if (this.uvRotateWest == 1) {
            var11 = p_147734_8_.getInterpolatedU(this.renderMinY * 16.0);
            var14 = p_147734_8_.getInterpolatedV(16.0 - this.renderMinX * 16.0);
            var12 = p_147734_8_.getInterpolatedU(this.renderMaxY * 16.0);
            var13 = p_147734_8_.getInterpolatedV(16.0 - this.renderMaxX * 16.0);
            if (this.flipTexture) {
                final double var19 = var11;
                var11 = var12;
                var12 = var19;
            }
            var17 = var13;
            var18 = var14;
            var15 = var11;
            var16 = var12;
            var13 = var14;
            var14 = var17;
        }
        else if (this.uvRotateWest == 2) {
            var11 = p_147734_8_.getInterpolatedU(16.0 - this.renderMaxY * 16.0);
            var13 = p_147734_8_.getInterpolatedV(this.renderMinX * 16.0);
            var12 = p_147734_8_.getInterpolatedU(16.0 - this.renderMinY * 16.0);
            var14 = p_147734_8_.getInterpolatedV(this.renderMaxX * 16.0);
            if (this.flipTexture) {
                final double var19 = var11;
                var11 = var12;
                var12 = var19;
            }
            var15 = var12;
            var16 = var11;
            var11 = var12;
            var12 = var16;
            var17 = var14;
            var18 = var13;
        }
        else if (this.uvRotateWest == 3) {
            var11 = p_147734_8_.getInterpolatedU(16.0 - this.renderMinX * 16.0);
            var12 = p_147734_8_.getInterpolatedU(16.0 - this.renderMaxX * 16.0);
            var13 = p_147734_8_.getInterpolatedV(this.renderMaxY * 16.0);
            var14 = p_147734_8_.getInterpolatedV(this.renderMinY * 16.0);
            if (this.flipTexture) {
                final double var19 = var11;
                var11 = var12;
                var12 = var19;
            }
            var15 = var12;
            var16 = var11;
            var17 = var13;
            var18 = var14;
        }
        if (uvRotateSet) {
            this.uvRotateWest = 0;
            this.flipTexture = false;
        }
        double var19 = p_147734_2_ + this.renderMinX;
        double var20 = p_147734_2_ + this.renderMaxX;
        final double var21 = p_147734_4_ + this.renderMinY;
        final double var22 = p_147734_4_ + this.renderMaxY;
        final double var23 = p_147734_6_ + this.renderMaxZ;
        if (this.renderFromInside) {
            var19 = p_147734_2_ + this.renderMaxX;
            var20 = p_147734_2_ + this.renderMinX;
        }
        if (this.enableAO) {
            var9.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
            var9.setBrightness(this.brightnessTopLeft);
            var9.addVertexWithUV(var19, var22, var23, var11, var13);
            var9.setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft);
            var9.setBrightness(this.brightnessBottomLeft);
            var9.addVertexWithUV(var19, var21, var23, var16, var18);
            var9.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight);
            var9.setBrightness(this.brightnessBottomRight);
            var9.addVertexWithUV(var20, var21, var23, var12, var14);
            var9.setColorOpaque_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight);
            var9.setBrightness(this.brightnessTopRight);
            var9.addVertexWithUV(var20, var22, var23, var15, var17);
        }
        else {
            var9.addVertexWithUV(var19, var22, var23, var11, var13);
            var9.addVertexWithUV(var19, var21, var23, var16, var18);
            var9.addVertexWithUV(var20, var21, var23, var12, var14);
            var9.addVertexWithUV(var20, var22, var23, var15, var17);
        }
    }
    
    public void renderFaceXNeg(final Block p_147798_1_, final double p_147798_2_, final double p_147798_4_, final double p_147798_6_, IIcon p_147798_8_) {
        final Tessellator var9 = Tessellator.instance;
        if (this.hasOverrideBlockTexture()) {
            p_147798_8_ = this.overrideBlockTexture;
        }
        if (Config.isConnectedTextures() && this.overrideBlockTexture == null) {
            p_147798_8_ = ConnectedTextures.getConnectedTexture(this.blockAccess, p_147798_1_, (int)p_147798_2_, (int)p_147798_4_, (int)p_147798_6_, 4, p_147798_8_);
        }
        boolean uvRotateSet = false;
        if (Config.isNaturalTextures() && this.overrideBlockTexture == null && this.uvRotateNorth == 0) {
            final NaturalProperties var10 = NaturalTextures.getNaturalProperties(p_147798_8_);
            if (var10 != null) {
                final int rand = Config.getRandom((int)p_147798_2_, (int)p_147798_4_, (int)p_147798_6_, 4);
                if (var10.rotation > 1) {
                    this.uvRotateNorth = (rand & 0x3);
                }
                if (var10.rotation == 2) {
                    this.uvRotateNorth = this.uvRotateNorth / 2 * 3;
                }
                if (var10.flip) {
                    this.flipTexture = ((rand & 0x4) != 0x0);
                }
                uvRotateSet = true;
            }
        }
        double var11 = p_147798_8_.getInterpolatedU(this.renderMinZ * 16.0);
        double var12 = p_147798_8_.getInterpolatedU(this.renderMaxZ * 16.0);
        double var13 = p_147798_8_.getInterpolatedV(16.0 - this.renderMaxY * 16.0);
        double var14 = p_147798_8_.getInterpolatedV(16.0 - this.renderMinY * 16.0);
        if (this.flipTexture) {
            final double var15 = var11;
            var11 = var12;
            var12 = var15;
        }
        if (this.renderMinZ < 0.0 || this.renderMaxZ > 1.0) {
            var11 = p_147798_8_.getMinU();
            var12 = p_147798_8_.getMaxU();
        }
        if (this.renderMinY < 0.0 || this.renderMaxY > 1.0) {
            var13 = p_147798_8_.getMinV();
            var14 = p_147798_8_.getMaxV();
        }
        double var15 = var12;
        double var16 = var11;
        double var17 = var13;
        double var18 = var14;
        if (this.uvRotateNorth == 1) {
            var11 = p_147798_8_.getInterpolatedU(this.renderMinY * 16.0);
            var13 = p_147798_8_.getInterpolatedV(16.0 - this.renderMaxZ * 16.0);
            var12 = p_147798_8_.getInterpolatedU(this.renderMaxY * 16.0);
            var14 = p_147798_8_.getInterpolatedV(16.0 - this.renderMinZ * 16.0);
            if (this.flipTexture) {
                final double var19 = var11;
                var11 = var12;
                var12 = var19;
            }
            var17 = var13;
            var18 = var14;
            var15 = var11;
            var16 = var12;
            var13 = var14;
            var14 = var17;
        }
        else if (this.uvRotateNorth == 2) {
            var11 = p_147798_8_.getInterpolatedU(16.0 - this.renderMaxY * 16.0);
            var13 = p_147798_8_.getInterpolatedV(this.renderMinZ * 16.0);
            var12 = p_147798_8_.getInterpolatedU(16.0 - this.renderMinY * 16.0);
            var14 = p_147798_8_.getInterpolatedV(this.renderMaxZ * 16.0);
            if (this.flipTexture) {
                final double var19 = var11;
                var11 = var12;
                var12 = var19;
            }
            var15 = var12;
            var16 = var11;
            var11 = var12;
            var12 = var16;
            var17 = var14;
            var18 = var13;
        }
        else if (this.uvRotateNorth == 3) {
            var11 = p_147798_8_.getInterpolatedU(16.0 - this.renderMinZ * 16.0);
            var12 = p_147798_8_.getInterpolatedU(16.0 - this.renderMaxZ * 16.0);
            var13 = p_147798_8_.getInterpolatedV(this.renderMaxY * 16.0);
            var14 = p_147798_8_.getInterpolatedV(this.renderMinY * 16.0);
            if (this.flipTexture) {
                final double var19 = var11;
                var11 = var12;
                var12 = var19;
            }
            var15 = var12;
            var16 = var11;
            var17 = var13;
            var18 = var14;
        }
        if (uvRotateSet) {
            this.uvRotateNorth = 0;
            this.flipTexture = false;
        }
        final double var19 = p_147798_2_ + this.renderMinX;
        final double var20 = p_147798_4_ + this.renderMinY;
        final double var21 = p_147798_4_ + this.renderMaxY;
        double var22 = p_147798_6_ + this.renderMinZ;
        double var23 = p_147798_6_ + this.renderMaxZ;
        if (this.renderFromInside) {
            var22 = p_147798_6_ + this.renderMaxZ;
            var23 = p_147798_6_ + this.renderMinZ;
        }
        if (this.enableAO) {
            var9.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
            var9.setBrightness(this.brightnessTopLeft);
            var9.addVertexWithUV(var19, var21, var23, var15, var17);
            var9.setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft);
            var9.setBrightness(this.brightnessBottomLeft);
            var9.addVertexWithUV(var19, var21, var22, var11, var13);
            var9.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight);
            var9.setBrightness(this.brightnessBottomRight);
            var9.addVertexWithUV(var19, var20, var22, var16, var18);
            var9.setColorOpaque_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight);
            var9.setBrightness(this.brightnessTopRight);
            var9.addVertexWithUV(var19, var20, var23, var12, var14);
        }
        else {
            var9.addVertexWithUV(var19, var21, var23, var15, var17);
            var9.addVertexWithUV(var19, var21, var22, var11, var13);
            var9.addVertexWithUV(var19, var20, var22, var16, var18);
            var9.addVertexWithUV(var19, var20, var23, var12, var14);
        }
    }
    
    public void renderFaceXPos(final Block p_147764_1_, final double p_147764_2_, final double p_147764_4_, final double p_147764_6_, IIcon p_147764_8_) {
        final Tessellator var9 = Tessellator.instance;
        if (this.hasOverrideBlockTexture()) {
            p_147764_8_ = this.overrideBlockTexture;
        }
        if (Config.isConnectedTextures() && this.overrideBlockTexture == null) {
            p_147764_8_ = ConnectedTextures.getConnectedTexture(this.blockAccess, p_147764_1_, (int)p_147764_2_, (int)p_147764_4_, (int)p_147764_6_, 5, p_147764_8_);
        }
        boolean uvRotateSet = false;
        if (Config.isNaturalTextures() && this.overrideBlockTexture == null && this.uvRotateSouth == 0) {
            final NaturalProperties var10 = NaturalTextures.getNaturalProperties(p_147764_8_);
            if (var10 != null) {
                final int rand = Config.getRandom((int)p_147764_2_, (int)p_147764_4_, (int)p_147764_6_, 5);
                if (var10.rotation > 1) {
                    this.uvRotateSouth = (rand & 0x3);
                }
                if (var10.rotation == 2) {
                    this.uvRotateSouth = this.uvRotateSouth / 2 * 3;
                }
                if (var10.flip) {
                    this.flipTexture = ((rand & 0x4) != 0x0);
                }
                uvRotateSet = true;
            }
        }
        double var11 = p_147764_8_.getInterpolatedU(this.renderMinZ * 16.0);
        double var12 = p_147764_8_.getInterpolatedU(this.renderMaxZ * 16.0);
        if (this.field_152631_f) {
            var12 = p_147764_8_.getInterpolatedU((1.0 - this.renderMinZ) * 16.0);
            var11 = p_147764_8_.getInterpolatedU((1.0 - this.renderMaxZ) * 16.0);
        }
        double var13 = p_147764_8_.getInterpolatedV(16.0 - this.renderMaxY * 16.0);
        double var14 = p_147764_8_.getInterpolatedV(16.0 - this.renderMinY * 16.0);
        if (this.flipTexture) {
            final double var15 = var11;
            var11 = var12;
            var12 = var15;
        }
        if (this.renderMinZ < 0.0 || this.renderMaxZ > 1.0) {
            var11 = p_147764_8_.getMinU();
            var12 = p_147764_8_.getMaxU();
        }
        if (this.renderMinY < 0.0 || this.renderMaxY > 1.0) {
            var13 = p_147764_8_.getMinV();
            var14 = p_147764_8_.getMaxV();
        }
        double var15 = var12;
        double var16 = var11;
        double var17 = var13;
        double var18 = var14;
        if (this.uvRotateSouth == 2) {
            var11 = p_147764_8_.getInterpolatedU(this.renderMinY * 16.0);
            var13 = p_147764_8_.getInterpolatedV(16.0 - this.renderMinZ * 16.0);
            var12 = p_147764_8_.getInterpolatedU(this.renderMaxY * 16.0);
            var14 = p_147764_8_.getInterpolatedV(16.0 - this.renderMaxZ * 16.0);
            if (this.flipTexture) {
                final double var19 = var11;
                var11 = var12;
                var12 = var19;
            }
            var17 = var13;
            var18 = var14;
            var15 = var11;
            var16 = var12;
            var13 = var14;
            var14 = var17;
        }
        else if (this.uvRotateSouth == 1) {
            var11 = p_147764_8_.getInterpolatedU(16.0 - this.renderMaxY * 16.0);
            var13 = p_147764_8_.getInterpolatedV(this.renderMaxZ * 16.0);
            var12 = p_147764_8_.getInterpolatedU(16.0 - this.renderMinY * 16.0);
            var14 = p_147764_8_.getInterpolatedV(this.renderMinZ * 16.0);
            if (this.flipTexture) {
                final double var19 = var11;
                var11 = var12;
                var12 = var19;
            }
            var15 = var12;
            var16 = var11;
            var11 = var12;
            var12 = var16;
            var17 = var14;
            var18 = var13;
        }
        else if (this.uvRotateSouth == 3) {
            var11 = p_147764_8_.getInterpolatedU(16.0 - this.renderMinZ * 16.0);
            var12 = p_147764_8_.getInterpolatedU(16.0 - this.renderMaxZ * 16.0);
            var13 = p_147764_8_.getInterpolatedV(this.renderMaxY * 16.0);
            var14 = p_147764_8_.getInterpolatedV(this.renderMinY * 16.0);
            if (this.flipTexture) {
                final double var19 = var11;
                var11 = var12;
                var12 = var19;
            }
            var15 = var12;
            var16 = var11;
            var17 = var13;
            var18 = var14;
        }
        if (uvRotateSet) {
            this.uvRotateSouth = 0;
            this.flipTexture = false;
        }
        final double var19 = p_147764_2_ + this.renderMaxX;
        final double var20 = p_147764_4_ + this.renderMinY;
        final double var21 = p_147764_4_ + this.renderMaxY;
        double var22 = p_147764_6_ + this.renderMinZ;
        double var23 = p_147764_6_ + this.renderMaxZ;
        if (this.renderFromInside) {
            var22 = p_147764_6_ + this.renderMaxZ;
            var23 = p_147764_6_ + this.renderMinZ;
        }
        if (this.enableAO) {
            var9.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
            var9.setBrightness(this.brightnessTopLeft);
            var9.addVertexWithUV(var19, var20, var23, var16, var18);
            var9.setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft);
            var9.setBrightness(this.brightnessBottomLeft);
            var9.addVertexWithUV(var19, var20, var22, var12, var14);
            var9.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight);
            var9.setBrightness(this.brightnessBottomRight);
            var9.addVertexWithUV(var19, var21, var22, var15, var17);
            var9.setColorOpaque_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight);
            var9.setBrightness(this.brightnessTopRight);
            var9.addVertexWithUV(var19, var21, var23, var11, var13);
        }
        else {
            var9.addVertexWithUV(var19, var20, var23, var16, var18);
            var9.addVertexWithUV(var19, var20, var22, var12, var14);
            var9.addVertexWithUV(var19, var21, var22, var15, var17);
            var9.addVertexWithUV(var19, var21, var23, var11, var13);
        }
    }
    
    public void renderBlockAsItem(final Block p_147800_1_, int p_147800_2_, final float p_147800_3_) {
        final Tessellator var4 = Tessellator.instance;
        final boolean var5 = p_147800_1_ == Blocks.grass;
        if (p_147800_1_ == Blocks.dispenser || p_147800_1_ == Blocks.dropper || p_147800_1_ == Blocks.furnace) {
            p_147800_2_ = 3;
        }
        if (this.useInventoryTint) {
            int var6 = p_147800_1_.getRenderColor(p_147800_2_);
            if (var5) {
                var6 = 16777215;
            }
            final float var7 = (var6 >> 16 & 0xFF) / 255.0f;
            final float var8 = (var6 >> 8 & 0xFF) / 255.0f;
            final float var9 = (var6 & 0xFF) / 255.0f;
            GL11.glColor4f(var7 * p_147800_3_, var8 * p_147800_3_, var9 * p_147800_3_, 1.0f);
        }
        int var6 = p_147800_1_.getRenderType();
        this.setRenderBoundsFromBlock(p_147800_1_);
        if (var6 != 0 && var6 != 31 && var6 != 39 && var6 != 16 && var6 != 26) {
            if (var6 == 1) {
                var4.startDrawingQuads();
                var4.setNormal(0.0f, -1.0f, 0.0f);
                final IIcon var10 = this.getBlockIconFromSideAndMetadata(p_147800_1_, 0, p_147800_2_);
                this.drawCrossedSquares(var10, -0.5, -0.5, -0.5, 1.0f);
                var4.draw();
            }
            else if (var6 == 19) {
                var4.startDrawingQuads();
                var4.setNormal(0.0f, -1.0f, 0.0f);
                p_147800_1_.setBlockBoundsForItemRender();
                this.renderBlockStemSmall(p_147800_1_, p_147800_2_, this.renderMaxY, -0.5, -0.5, -0.5);
                var4.draw();
            }
            else if (var6 == 23) {
                var4.startDrawingQuads();
                var4.setNormal(0.0f, -1.0f, 0.0f);
                p_147800_1_.setBlockBoundsForItemRender();
                var4.draw();
            }
            else if (var6 == 13) {
                p_147800_1_.setBlockBoundsForItemRender();
                GL11.glTranslatef(-0.5f, -0.5f, -0.5f);
                final float var7 = 0.0625f;
                var4.startDrawingQuads();
                var4.setNormal(0.0f, -1.0f, 0.0f);
                this.renderFaceYNeg(p_147800_1_, 0.0, 0.0, 0.0, this.getBlockIconFromSide(p_147800_1_, 0));
                var4.draw();
                var4.startDrawingQuads();
                var4.setNormal(0.0f, 1.0f, 0.0f);
                this.renderFaceYPos(p_147800_1_, 0.0, 0.0, 0.0, this.getBlockIconFromSide(p_147800_1_, 1));
                var4.draw();
                var4.startDrawingQuads();
                var4.setNormal(0.0f, 0.0f, -1.0f);
                var4.addTranslation(0.0f, 0.0f, var7);
                this.renderFaceZNeg(p_147800_1_, 0.0, 0.0, 0.0, this.getBlockIconFromSide(p_147800_1_, 2));
                var4.addTranslation(0.0f, 0.0f, -var7);
                var4.draw();
                var4.startDrawingQuads();
                var4.setNormal(0.0f, 0.0f, 1.0f);
                var4.addTranslation(0.0f, 0.0f, -var7);
                this.renderFaceZPos(p_147800_1_, 0.0, 0.0, 0.0, this.getBlockIconFromSide(p_147800_1_, 3));
                var4.addTranslation(0.0f, 0.0f, var7);
                var4.draw();
                var4.startDrawingQuads();
                var4.setNormal(-1.0f, 0.0f, 0.0f);
                var4.addTranslation(var7, 0.0f, 0.0f);
                this.renderFaceXNeg(p_147800_1_, 0.0, 0.0, 0.0, this.getBlockIconFromSide(p_147800_1_, 4));
                var4.addTranslation(-var7, 0.0f, 0.0f);
                var4.draw();
                var4.startDrawingQuads();
                var4.setNormal(1.0f, 0.0f, 0.0f);
                var4.addTranslation(-var7, 0.0f, 0.0f);
                this.renderFaceXPos(p_147800_1_, 0.0, 0.0, 0.0, this.getBlockIconFromSide(p_147800_1_, 5));
                var4.addTranslation(var7, 0.0f, 0.0f);
                var4.draw();
                GL11.glTranslatef(0.5f, 0.5f, 0.5f);
            }
            else if (var6 == 22) {
                GL11.glRotatef(90.0f, 0.0f, 1.0f, 0.0f);
                GL11.glTranslatef(-0.5f, -0.5f, -0.5f);
                TileEntityRendererChestHelper.instance.func_147715_a(p_147800_1_, p_147800_2_, p_147800_3_);
                GL11.glEnable(32826);
            }
            else if (var6 == 6) {
                var4.startDrawingQuads();
                var4.setNormal(0.0f, -1.0f, 0.0f);
                this.renderBlockCropsImpl(p_147800_1_, p_147800_2_, -0.5, -0.5, -0.5);
                var4.draw();
            }
            else if (var6 == 2) {
                var4.startDrawingQuads();
                var4.setNormal(0.0f, -1.0f, 0.0f);
                this.renderTorchAtAngle(p_147800_1_, -0.5, -0.5, -0.5, 0.0, 0.0, 0);
                var4.draw();
            }
            else if (var6 == 10) {
                for (int var11 = 0; var11 < 2; ++var11) {
                    if (var11 == 0) {
                        this.setRenderBounds(0.0, 0.0, 0.0, 1.0, 1.0, 0.5);
                    }
                    if (var11 == 1) {
                        this.setRenderBounds(0.0, 0.0, 0.5, 1.0, 0.5, 1.0);
                    }
                    GL11.glTranslatef(-0.5f, -0.5f, -0.5f);
                    var4.startDrawingQuads();
                    var4.setNormal(0.0f, -1.0f, 0.0f);
                    this.renderFaceYNeg(p_147800_1_, 0.0, 0.0, 0.0, this.getBlockIconFromSide(p_147800_1_, 0));
                    var4.draw();
                    var4.startDrawingQuads();
                    var4.setNormal(0.0f, 1.0f, 0.0f);
                    this.renderFaceYPos(p_147800_1_, 0.0, 0.0, 0.0, this.getBlockIconFromSide(p_147800_1_, 1));
                    var4.draw();
                    var4.startDrawingQuads();
                    var4.setNormal(0.0f, 0.0f, -1.0f);
                    this.renderFaceZNeg(p_147800_1_, 0.0, 0.0, 0.0, this.getBlockIconFromSide(p_147800_1_, 2));
                    var4.draw();
                    var4.startDrawingQuads();
                    var4.setNormal(0.0f, 0.0f, 1.0f);
                    this.renderFaceZPos(p_147800_1_, 0.0, 0.0, 0.0, this.getBlockIconFromSide(p_147800_1_, 3));
                    var4.draw();
                    var4.startDrawingQuads();
                    var4.setNormal(-1.0f, 0.0f, 0.0f);
                    this.renderFaceXNeg(p_147800_1_, 0.0, 0.0, 0.0, this.getBlockIconFromSide(p_147800_1_, 4));
                    var4.draw();
                    var4.startDrawingQuads();
                    var4.setNormal(1.0f, 0.0f, 0.0f);
                    this.renderFaceXPos(p_147800_1_, 0.0, 0.0, 0.0, this.getBlockIconFromSide(p_147800_1_, 5));
                    var4.draw();
                    GL11.glTranslatef(0.5f, 0.5f, 0.5f);
                }
            }
            else if (var6 == 27) {
                int var11 = 0;
                GL11.glTranslatef(-0.5f, -0.5f, -0.5f);
                var4.startDrawingQuads();
                for (int var12 = 0; var12 < 8; ++var12) {
                    byte var13 = 0;
                    byte var14 = 1;
                    if (var12 == 0) {
                        var13 = 2;
                    }
                    if (var12 == 1) {
                        var13 = 3;
                    }
                    if (var12 == 2) {
                        var13 = 4;
                    }
                    if (var12 == 3) {
                        var13 = 5;
                        var14 = 2;
                    }
                    if (var12 == 4) {
                        var13 = 6;
                        var14 = 3;
                    }
                    if (var12 == 5) {
                        var13 = 7;
                        var14 = 5;
                    }
                    if (var12 == 6) {
                        var13 = 6;
                        var14 = 2;
                    }
                    if (var12 == 7) {
                        var13 = 3;
                    }
                    final float var15 = var13 / 16.0f;
                    final float var16 = 1.0f - var11 / 16.0f;
                    final float var17 = 1.0f - (var11 + var14) / 16.0f;
                    var11 += var14;
                    this.setRenderBounds(0.5f - var15, var17, 0.5f - var15, 0.5f + var15, var16, 0.5f + var15);
                    var4.setNormal(0.0f, -1.0f, 0.0f);
                    this.renderFaceYNeg(p_147800_1_, 0.0, 0.0, 0.0, this.getBlockIconFromSide(p_147800_1_, 0));
                    var4.setNormal(0.0f, 1.0f, 0.0f);
                    this.renderFaceYPos(p_147800_1_, 0.0, 0.0, 0.0, this.getBlockIconFromSide(p_147800_1_, 1));
                    var4.setNormal(0.0f, 0.0f, -1.0f);
                    this.renderFaceZNeg(p_147800_1_, 0.0, 0.0, 0.0, this.getBlockIconFromSide(p_147800_1_, 2));
                    var4.setNormal(0.0f, 0.0f, 1.0f);
                    this.renderFaceZPos(p_147800_1_, 0.0, 0.0, 0.0, this.getBlockIconFromSide(p_147800_1_, 3));
                    var4.setNormal(-1.0f, 0.0f, 0.0f);
                    this.renderFaceXNeg(p_147800_1_, 0.0, 0.0, 0.0, this.getBlockIconFromSide(p_147800_1_, 4));
                    var4.setNormal(1.0f, 0.0f, 0.0f);
                    this.renderFaceXPos(p_147800_1_, 0.0, 0.0, 0.0, this.getBlockIconFromSide(p_147800_1_, 5));
                }
                var4.draw();
                GL11.glTranslatef(0.5f, 0.5f, 0.5f);
                this.setRenderBounds(0.0, 0.0, 0.0, 1.0, 1.0, 1.0);
            }
            else if (var6 == 11) {
                for (int var11 = 0; var11 < 4; ++var11) {
                    float var8 = 0.125f;
                    if (var11 == 0) {
                        this.setRenderBounds(0.5f - var8, 0.0, 0.0, 0.5f + var8, 1.0, var8 * 2.0f);
                    }
                    if (var11 == 1) {
                        this.setRenderBounds(0.5f - var8, 0.0, 1.0f - var8 * 2.0f, 0.5f + var8, 1.0, 1.0);
                    }
                    var8 = 0.0625f;
                    if (var11 == 2) {
                        this.setRenderBounds(0.5f - var8, 1.0f - var8 * 3.0f, -var8 * 2.0f, 0.5f + var8, 1.0f - var8, 1.0f + var8 * 2.0f);
                    }
                    if (var11 == 3) {
                        this.setRenderBounds(0.5f - var8, 0.5f - var8 * 3.0f, -var8 * 2.0f, 0.5f + var8, 0.5f - var8, 1.0f + var8 * 2.0f);
                    }
                    GL11.glTranslatef(-0.5f, -0.5f, -0.5f);
                    var4.startDrawingQuads();
                    var4.setNormal(0.0f, -1.0f, 0.0f);
                    this.renderFaceYNeg(p_147800_1_, 0.0, 0.0, 0.0, this.getBlockIconFromSide(p_147800_1_, 0));
                    var4.draw();
                    var4.startDrawingQuads();
                    var4.setNormal(0.0f, 1.0f, 0.0f);
                    this.renderFaceYPos(p_147800_1_, 0.0, 0.0, 0.0, this.getBlockIconFromSide(p_147800_1_, 1));
                    var4.draw();
                    var4.startDrawingQuads();
                    var4.setNormal(0.0f, 0.0f, -1.0f);
                    this.renderFaceZNeg(p_147800_1_, 0.0, 0.0, 0.0, this.getBlockIconFromSide(p_147800_1_, 2));
                    var4.draw();
                    var4.startDrawingQuads();
                    var4.setNormal(0.0f, 0.0f, 1.0f);
                    this.renderFaceZPos(p_147800_1_, 0.0, 0.0, 0.0, this.getBlockIconFromSide(p_147800_1_, 3));
                    var4.draw();
                    var4.startDrawingQuads();
                    var4.setNormal(-1.0f, 0.0f, 0.0f);
                    this.renderFaceXNeg(p_147800_1_, 0.0, 0.0, 0.0, this.getBlockIconFromSide(p_147800_1_, 4));
                    var4.draw();
                    var4.startDrawingQuads();
                    var4.setNormal(1.0f, 0.0f, 0.0f);
                    this.renderFaceXPos(p_147800_1_, 0.0, 0.0, 0.0, this.getBlockIconFromSide(p_147800_1_, 5));
                    var4.draw();
                    GL11.glTranslatef(0.5f, 0.5f, 0.5f);
                }
                this.setRenderBounds(0.0, 0.0, 0.0, 1.0, 1.0, 1.0);
            }
            else if (var6 == 21) {
                for (int var11 = 0; var11 < 3; ++var11) {
                    float var8 = 0.0625f;
                    if (var11 == 0) {
                        this.setRenderBounds(0.5f - var8, 0.30000001192092896, 0.0, 0.5f + var8, 1.0, var8 * 2.0f);
                    }
                    if (var11 == 1) {
                        this.setRenderBounds(0.5f - var8, 0.30000001192092896, 1.0f - var8 * 2.0f, 0.5f + var8, 1.0, 1.0);
                    }
                    var8 = 0.0625f;
                    if (var11 == 2) {
                        this.setRenderBounds(0.5f - var8, 0.5, 0.0, 0.5f + var8, 1.0f - var8, 1.0);
                    }
                    GL11.glTranslatef(-0.5f, -0.5f, -0.5f);
                    var4.startDrawingQuads();
                    var4.setNormal(0.0f, -1.0f, 0.0f);
                    this.renderFaceYNeg(p_147800_1_, 0.0, 0.0, 0.0, this.getBlockIconFromSide(p_147800_1_, 0));
                    var4.draw();
                    var4.startDrawingQuads();
                    var4.setNormal(0.0f, 1.0f, 0.0f);
                    this.renderFaceYPos(p_147800_1_, 0.0, 0.0, 0.0, this.getBlockIconFromSide(p_147800_1_, 1));
                    var4.draw();
                    var4.startDrawingQuads();
                    var4.setNormal(0.0f, 0.0f, -1.0f);
                    this.renderFaceZNeg(p_147800_1_, 0.0, 0.0, 0.0, this.getBlockIconFromSide(p_147800_1_, 2));
                    var4.draw();
                    var4.startDrawingQuads();
                    var4.setNormal(0.0f, 0.0f, 1.0f);
                    this.renderFaceZPos(p_147800_1_, 0.0, 0.0, 0.0, this.getBlockIconFromSide(p_147800_1_, 3));
                    var4.draw();
                    var4.startDrawingQuads();
                    var4.setNormal(-1.0f, 0.0f, 0.0f);
                    this.renderFaceXNeg(p_147800_1_, 0.0, 0.0, 0.0, this.getBlockIconFromSide(p_147800_1_, 4));
                    var4.draw();
                    var4.startDrawingQuads();
                    var4.setNormal(1.0f, 0.0f, 0.0f);
                    this.renderFaceXPos(p_147800_1_, 0.0, 0.0, 0.0, this.getBlockIconFromSide(p_147800_1_, 5));
                    var4.draw();
                    GL11.glTranslatef(0.5f, 0.5f, 0.5f);
                }
            }
            else if (var6 == 32) {
                for (int var11 = 0; var11 < 2; ++var11) {
                    if (var11 == 0) {
                        this.setRenderBounds(0.0, 0.0, 0.3125, 1.0, 0.8125, 0.6875);
                    }
                    if (var11 == 1) {
                        this.setRenderBounds(0.25, 0.0, 0.25, 0.75, 1.0, 0.75);
                    }
                    GL11.glTranslatef(-0.5f, -0.5f, -0.5f);
                    var4.startDrawingQuads();
                    var4.setNormal(0.0f, -1.0f, 0.0f);
                    this.renderFaceYNeg(p_147800_1_, 0.0, 0.0, 0.0, this.getBlockIconFromSideAndMetadata(p_147800_1_, 0, p_147800_2_));
                    var4.draw();
                    var4.startDrawingQuads();
                    var4.setNormal(0.0f, 1.0f, 0.0f);
                    this.renderFaceYPos(p_147800_1_, 0.0, 0.0, 0.0, this.getBlockIconFromSideAndMetadata(p_147800_1_, 1, p_147800_2_));
                    var4.draw();
                    var4.startDrawingQuads();
                    var4.setNormal(0.0f, 0.0f, -1.0f);
                    this.renderFaceZNeg(p_147800_1_, 0.0, 0.0, 0.0, this.getBlockIconFromSideAndMetadata(p_147800_1_, 2, p_147800_2_));
                    var4.draw();
                    var4.startDrawingQuads();
                    var4.setNormal(0.0f, 0.0f, 1.0f);
                    this.renderFaceZPos(p_147800_1_, 0.0, 0.0, 0.0, this.getBlockIconFromSideAndMetadata(p_147800_1_, 3, p_147800_2_));
                    var4.draw();
                    var4.startDrawingQuads();
                    var4.setNormal(-1.0f, 0.0f, 0.0f);
                    this.renderFaceXNeg(p_147800_1_, 0.0, 0.0, 0.0, this.getBlockIconFromSideAndMetadata(p_147800_1_, 4, p_147800_2_));
                    var4.draw();
                    var4.startDrawingQuads();
                    var4.setNormal(1.0f, 0.0f, 0.0f);
                    this.renderFaceXPos(p_147800_1_, 0.0, 0.0, 0.0, this.getBlockIconFromSideAndMetadata(p_147800_1_, 5, p_147800_2_));
                    var4.draw();
                    GL11.glTranslatef(0.5f, 0.5f, 0.5f);
                }
                this.setRenderBounds(0.0, 0.0, 0.0, 1.0, 1.0, 1.0);
            }
            else if (var6 == 35) {
                GL11.glTranslatef(-0.5f, -0.5f, -0.5f);
                this.renderBlockAnvilOrient((BlockAnvil)p_147800_1_, 0, 0, 0, p_147800_2_ << 2, true);
                GL11.glTranslatef(0.5f, 0.5f, 0.5f);
            }
            else if (var6 == 34) {
                for (int var11 = 0; var11 < 3; ++var11) {
                    if (var11 == 0) {
                        this.setRenderBounds(0.125, 0.0, 0.125, 0.875, 0.1875, 0.875);
                        this.setOverrideBlockTexture(this.getBlockIcon(Blocks.obsidian));
                    }
                    else if (var11 == 1) {
                        this.setRenderBounds(0.1875, 0.1875, 0.1875, 0.8125, 0.875, 0.8125);
                        this.setOverrideBlockTexture(this.getBlockIcon(Blocks.beacon));
                    }
                    else if (var11 == 2) {
                        this.setRenderBounds(0.0, 0.0, 0.0, 1.0, 1.0, 1.0);
                        this.setOverrideBlockTexture(this.getBlockIcon(Blocks.glass));
                    }
                    GL11.glTranslatef(-0.5f, -0.5f, -0.5f);
                    var4.startDrawingQuads();
                    var4.setNormal(0.0f, -1.0f, 0.0f);
                    this.renderFaceYNeg(p_147800_1_, 0.0, 0.0, 0.0, this.getBlockIconFromSideAndMetadata(p_147800_1_, 0, p_147800_2_));
                    var4.draw();
                    var4.startDrawingQuads();
                    var4.setNormal(0.0f, 1.0f, 0.0f);
                    this.renderFaceYPos(p_147800_1_, 0.0, 0.0, 0.0, this.getBlockIconFromSideAndMetadata(p_147800_1_, 1, p_147800_2_));
                    var4.draw();
                    var4.startDrawingQuads();
                    var4.setNormal(0.0f, 0.0f, -1.0f);
                    this.renderFaceZNeg(p_147800_1_, 0.0, 0.0, 0.0, this.getBlockIconFromSideAndMetadata(p_147800_1_, 2, p_147800_2_));
                    var4.draw();
                    var4.startDrawingQuads();
                    var4.setNormal(0.0f, 0.0f, 1.0f);
                    this.renderFaceZPos(p_147800_1_, 0.0, 0.0, 0.0, this.getBlockIconFromSideAndMetadata(p_147800_1_, 3, p_147800_2_));
                    var4.draw();
                    var4.startDrawingQuads();
                    var4.setNormal(-1.0f, 0.0f, 0.0f);
                    this.renderFaceXNeg(p_147800_1_, 0.0, 0.0, 0.0, this.getBlockIconFromSideAndMetadata(p_147800_1_, 4, p_147800_2_));
                    var4.draw();
                    var4.startDrawingQuads();
                    var4.setNormal(1.0f, 0.0f, 0.0f);
                    this.renderFaceXPos(p_147800_1_, 0.0, 0.0, 0.0, this.getBlockIconFromSideAndMetadata(p_147800_1_, 5, p_147800_2_));
                    var4.draw();
                    GL11.glTranslatef(0.5f, 0.5f, 0.5f);
                }
                this.setRenderBounds(0.0, 0.0, 0.0, 1.0, 1.0, 1.0);
                this.clearOverrideBlockTexture();
            }
            else if (var6 == 38) {
                GL11.glTranslatef(-0.5f, -0.5f, -0.5f);
                this.renderBlockHopperMetadata((BlockHopper)p_147800_1_, 0, 0, 0, 0, true);
                GL11.glTranslatef(0.5f, 0.5f, 0.5f);
            }
        }
        else {
            if (var6 == 16) {
                p_147800_2_ = 1;
            }
            p_147800_1_.setBlockBoundsForItemRender();
            this.setRenderBoundsFromBlock(p_147800_1_);
            GL11.glRotatef(90.0f, 0.0f, 1.0f, 0.0f);
            GL11.glTranslatef(-0.5f, -0.5f, -0.5f);
            var4.startDrawingQuads();
            var4.setNormal(0.0f, -1.0f, 0.0f);
            this.renderFaceYNeg(p_147800_1_, 0.0, 0.0, 0.0, this.getBlockIconFromSideAndMetadata(p_147800_1_, 0, p_147800_2_));
            var4.draw();
            if (var5 && this.useInventoryTint) {
                final int var11 = p_147800_1_.getRenderColor(p_147800_2_);
                final float var8 = (var11 >> 16 & 0xFF) / 255.0f;
                final float var9 = (var11 >> 8 & 0xFF) / 255.0f;
                final float var18 = (var11 & 0xFF) / 255.0f;
                GL11.glColor4f(var8 * p_147800_3_, var9 * p_147800_3_, var18 * p_147800_3_, 1.0f);
            }
            var4.startDrawingQuads();
            var4.setNormal(0.0f, 1.0f, 0.0f);
            this.renderFaceYPos(p_147800_1_, 0.0, 0.0, 0.0, this.getBlockIconFromSideAndMetadata(p_147800_1_, 1, p_147800_2_));
            var4.draw();
            if (var5 && this.useInventoryTint) {
                GL11.glColor4f(p_147800_3_, p_147800_3_, p_147800_3_, 1.0f);
            }
            var4.startDrawingQuads();
            var4.setNormal(0.0f, 0.0f, -1.0f);
            this.renderFaceZNeg(p_147800_1_, 0.0, 0.0, 0.0, this.getBlockIconFromSideAndMetadata(p_147800_1_, 2, p_147800_2_));
            var4.draw();
            var4.startDrawingQuads();
            var4.setNormal(0.0f, 0.0f, 1.0f);
            this.renderFaceZPos(p_147800_1_, 0.0, 0.0, 0.0, this.getBlockIconFromSideAndMetadata(p_147800_1_, 3, p_147800_2_));
            var4.draw();
            var4.startDrawingQuads();
            var4.setNormal(-1.0f, 0.0f, 0.0f);
            this.renderFaceXNeg(p_147800_1_, 0.0, 0.0, 0.0, this.getBlockIconFromSideAndMetadata(p_147800_1_, 4, p_147800_2_));
            var4.draw();
            var4.startDrawingQuads();
            var4.setNormal(1.0f, 0.0f, 0.0f);
            this.renderFaceXPos(p_147800_1_, 0.0, 0.0, 0.0, this.getBlockIconFromSideAndMetadata(p_147800_1_, 5, p_147800_2_));
            var4.draw();
            GL11.glTranslatef(0.5f, 0.5f, 0.5f);
        }
    }
    
    public static boolean renderItemIn3d(final int par0) {
        switch (par0) {
            case -1: {
                return false;
            }
            case 0:
            case 10:
            case 11:
            case 13:
            case 16:
            case 21:
            case 22:
            case 26:
            case 27:
            case 31:
            case 32:
            case 34:
            case 35:
            case 39: {
                return true;
            }
            default: {
                return false;
            }
        }
    }
    
    public IIcon getBlockIcon(final Block p_147793_1_, final IBlockAccess p_147793_2_, final int p_147793_3_, final int p_147793_4_, final int p_147793_5_, final int p_147793_6_) {
        return this.getIconSafe(p_147793_1_.getIcon(p_147793_2_, p_147793_3_, p_147793_4_, p_147793_5_, p_147793_6_));
    }
    
    public IIcon getBlockIconFromSideAndMetadata(final Block p_147787_1_, final int p_147787_2_, final int p_147787_3_) {
        return this.getIconSafe(p_147787_1_.getIcon(p_147787_2_, p_147787_3_));
    }
    
    public IIcon getBlockIconFromSide(final Block p_147777_1_, final int p_147777_2_) {
        return this.getIconSafe(p_147777_1_.getBlockTextureFromSide(p_147777_2_));
    }
    
    public IIcon getBlockIcon(final Block p_147745_1_) {
        return this.getIconSafe(p_147745_1_.getBlockTextureFromSide(1));
    }
    
    public IIcon getIconSafe(IIcon p_147758_1_) {
        if (p_147758_1_ == null) {
            p_147758_1_ = ((TextureMap)Minecraft.getMinecraft().getTextureManager().getTexture(TextureMap.locationBlocksTexture)).getAtlasSprite("missingno");
        }
        return p_147758_1_;
    }
    
    private float getAmbientOcclusionLightValue(final int i, final int j, final int k) {
        final Block block = this.blockAccess.getBlock(i, j, k);
        return block.isBlockNormalCube() ? this.aoLightValueOpaque : 1.0f;
    }
    
    private IIcon fixAoSideGrassTexture(IIcon tex, final int x, final int y, final int z, final int side, final float f, final float f1, final float f2) {
        if (tex == TextureUtils.iconGrassSide || tex == TextureUtils.iconMyceliumSide) {
            tex = Config.getSideGrassTexture(this.blockAccess, x, y, z, side, tex);
            if (tex == TextureUtils.iconGrassTop) {
                this.colorRedTopLeft *= f;
                this.colorRedBottomLeft *= f;
                this.colorRedBottomRight *= f;
                this.colorRedTopRight *= f;
                this.colorGreenTopLeft *= f1;
                this.colorGreenBottomLeft *= f1;
                this.colorGreenBottomRight *= f1;
                this.colorGreenTopRight *= f1;
                this.colorBlueTopLeft *= f2;
                this.colorBlueBottomLeft *= f2;
                this.colorBlueBottomRight *= f2;
                this.colorBlueTopRight *= f2;
            }
        }
        if (tex == TextureUtils.iconGrassSideSnowed) {
            tex = Config.getSideSnowGrassTexture(this.blockAccess, x, y, z, side);
        }
        return tex;
    }
    
    private boolean hasSnowNeighbours(final int x, final int y, final int z) {
        final Block blockSnow = Blocks.snow_layer;
        return (this.blockAccess.getBlock(x - 1, y, z) == blockSnow || this.blockAccess.getBlock(x + 1, y, z) == blockSnow || this.blockAccess.getBlock(x, y, z - 1) == blockSnow || this.blockAccess.getBlock(x, y, z + 1) == blockSnow) && this.blockAccess.getBlock(x, y - 1, z).isOpaqueCube();
    }
    
    private void renderSnow(final int x, final int y, final int z, final double maxY) {
        if (this.betterSnowEnabled) {
            this.setRenderBoundsFromBlock(Blocks.snow_layer);
            this.renderMaxY = maxY;
            this.renderStandardBlock(Blocks.snow_layer, x, y, z);
        }
    }
    
    public static RenderBlocks getInstance() {
        if (RenderBlocks.instance == null) {
            RenderBlocks.instance = new RenderBlocks();
        }
        return RenderBlocks.instance;
    }
    
    static {
        RenderBlocks.fancyGrass = true;
        RenderBlocks.cfgGrassFix = true;
    }
}
