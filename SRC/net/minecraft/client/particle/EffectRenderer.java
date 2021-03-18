package net.minecraft.client.particle;

import net.minecraft.world.*;
import java.util.*;
import java.util.concurrent.*;
import net.minecraft.crash.*;
import net.minecraft.entity.*;
import net.minecraft.client.renderer.texture.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.*;
import net.minecraft.block.*;
import net.minecraft.block.material.*;

public class EffectRenderer
{
    private static final ResourceLocation particleTextures;
    protected World worldObj;
    private List[] fxLayers;
    private TextureManager renderer;
    private Random rand;
    private static final String __OBFID = "CL_00000915";
    
    public EffectRenderer(final World p_i1220_1_, final TextureManager p_i1220_2_) {
        this.fxLayers = new List[4];
        this.rand = new Random();
        if (p_i1220_1_ != null) {
            this.worldObj = p_i1220_1_;
        }
        this.renderer = p_i1220_2_;
        for (int var3 = 0; var3 < 4; ++var3) {
            this.fxLayers[var3] = new ArrayList();
        }
    }
    
    public void addEffect(final EntityFX p_78873_1_) {
        final int var2 = p_78873_1_.getFXLayer();
        if (this.fxLayers[var2].size() >= 4000) {
            this.fxLayers[var2].remove(0);
        }
        this.fxLayers[var2].add(p_78873_1_);
    }
    
    public void updateEffects() {
        for (int var11 = 0; var11 < 4; ++var11) {
            for (int var12 = var11, var13 = 0; var13 < this.fxLayers[var12].size(); ++var13) {
                final EntityFX var14 = this.fxLayers[var12].get(var13);
                try {
                    var14.onUpdate();
                }
                catch (Throwable var16) {
                    final CrashReport var15 = CrashReport.makeCrashReport(var16, "Ticking Particle");
                    final CrashReportCategory var17 = var15.makeCategory("Particle being ticked");
                    var17.addCrashSectionCallable("Particle", new Callable() {
                        private static final String __OBFID = "CL_00000916";
                        
                        @Override
                        public String call() {
                            return var14.toString();
                        }
                    });
                    var17.addCrashSectionCallable("Particle Type", new Callable() {
                        private static final String __OBFID = "CL_00000917";
                        
                        @Override
                        public String call() {
                            return (var12 == 0) ? "MISC_TEXTURE" : ((var12 == 1) ? "TERRAIN_TEXTURE" : ((var12 == 2) ? "ITEM_TEXTURE" : ((var12 == 3) ? "ENTITY_PARTICLE_TEXTURE" : ("Unknown - " + var12))));
                        }
                    });
                    throw new ReportedException(var15);
                }
                if (var14.isDead) {
                    this.fxLayers[var12].remove(var13--);
                }
            }
        }
    }
    
    public void renderParticles(final Entity p_78874_1_, final float p_78874_2_) {
        final float var3 = ActiveRenderInfo.rotationX;
        final float var4 = ActiveRenderInfo.rotationZ;
        final float var5 = ActiveRenderInfo.rotationYZ;
        final float var6 = ActiveRenderInfo.rotationXY;
        final float var7 = ActiveRenderInfo.rotationXZ;
        EntityFX.interpPosX = p_78874_1_.lastTickPosX + (p_78874_1_.posX - p_78874_1_.lastTickPosX) * p_78874_2_;
        EntityFX.interpPosY = p_78874_1_.lastTickPosY + (p_78874_1_.posY - p_78874_1_.lastTickPosY) * p_78874_2_;
        EntityFX.interpPosZ = p_78874_1_.lastTickPosZ + (p_78874_1_.posZ - p_78874_1_.lastTickPosZ) * p_78874_2_;
        for (int var8 = 0; var8 < 3; ++var8) {
            final int var9 = var8;
            if (!this.fxLayers[var9].isEmpty()) {
                switch (var9) {
                    default: {
                        this.renderer.bindTexture(EffectRenderer.particleTextures);
                        break;
                    }
                    case 1: {
                        this.renderer.bindTexture(TextureMap.locationBlocksTexture);
                        break;
                    }
                    case 2: {
                        this.renderer.bindTexture(TextureMap.locationItemsTexture);
                        break;
                    }
                }
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                GL11.glDepthMask(false);
                GL11.glEnable(3042);
                GL11.glBlendFunc(770, 771);
                GL11.glAlphaFunc(516, 0.003921569f);
                final Tessellator var10 = Tessellator.instance;
                var10.startDrawingQuads();
                for (int var11 = 0; var11 < this.fxLayers[var9].size(); ++var11) {
                    final EntityFX var12 = this.fxLayers[var9].get(var11);
                    var10.setBrightness(var12.getBrightnessForRender(p_78874_2_));
                    try {
                        var12.renderParticle(var10, p_78874_2_, var3, var7, var4, var5, var6);
                    }
                    catch (Throwable var14) {
                        final CrashReport var13 = CrashReport.makeCrashReport(var14, "Rendering Particle");
                        final CrashReportCategory var15 = var13.makeCategory("Particle being rendered");
                        var15.addCrashSectionCallable("Particle", new Callable() {
                            private static final String __OBFID = "CL_00000918";
                            
                            @Override
                            public String call() {
                                return var12.toString();
                            }
                        });
                        var15.addCrashSectionCallable("Particle Type", new Callable() {
                            private static final String __OBFID = "CL_00000919";
                            
                            @Override
                            public String call() {
                                return (var9 == 0) ? "MISC_TEXTURE" : ((var9 == 1) ? "TERRAIN_TEXTURE" : ((var9 == 2) ? "ITEM_TEXTURE" : ((var9 == 3) ? "ENTITY_PARTICLE_TEXTURE" : ("Unknown - " + var9))));
                            }
                        });
                        throw new ReportedException(var13);
                    }
                }
                var10.draw();
                GL11.glDisable(3042);
                GL11.glDepthMask(true);
                GL11.glAlphaFunc(516, 0.1f);
            }
        }
    }
    
    public void renderLitParticles(final Entity p_78872_1_, final float p_78872_2_) {
        final float var3 = 0.017453292f;
        final float var4 = MathHelper.cos(p_78872_1_.rotationYaw * 0.017453292f);
        final float var5 = MathHelper.sin(p_78872_1_.rotationYaw * 0.017453292f);
        final float var6 = -var5 * MathHelper.sin(p_78872_1_.rotationPitch * 0.017453292f);
        final float var7 = var4 * MathHelper.sin(p_78872_1_.rotationPitch * 0.017453292f);
        final float var8 = MathHelper.cos(p_78872_1_.rotationPitch * 0.017453292f);
        final byte var9 = 3;
        final List var10 = this.fxLayers[var9];
        if (!var10.isEmpty()) {
            final Tessellator var11 = Tessellator.instance;
            for (int var12 = 0; var12 < var10.size(); ++var12) {
                final EntityFX var13 = var10.get(var12);
                var11.setBrightness(var13.getBrightnessForRender(p_78872_2_));
                var13.renderParticle(var11, p_78872_2_, var4, var8, var5, var6, var7);
            }
        }
    }
    
    public void clearEffects(final World p_78870_1_) {
        this.worldObj = p_78870_1_;
        for (int var2 = 0; var2 < 4; ++var2) {
            this.fxLayers[var2].clear();
        }
    }
    
    public void func_147215_a(final int p_147215_1_, final int p_147215_2_, final int p_147215_3_, final Block p_147215_4_, final int p_147215_5_) {
        if (p_147215_4_.getMaterial() != Material.air) {
            final byte var6 = 4;
            for (int var7 = 0; var7 < var6; ++var7) {
                for (int var8 = 0; var8 < var6; ++var8) {
                    for (int var9 = 0; var9 < var6; ++var9) {
                        final double var10 = p_147215_1_ + (var7 + 0.5) / var6;
                        final double var11 = p_147215_2_ + (var8 + 0.5) / var6;
                        final double var12 = p_147215_3_ + (var9 + 0.5) / var6;
                        this.addEffect(new EntityDiggingFX(this.worldObj, var10, var11, var12, var10 - p_147215_1_ - 0.5, var11 - p_147215_2_ - 0.5, var12 - p_147215_3_ - 0.5, p_147215_4_, p_147215_5_).applyColourMultiplier(p_147215_1_, p_147215_2_, p_147215_3_));
                    }
                }
            }
        }
    }
    
    public void addBlockHitEffects(final int p_78867_1_, final int p_78867_2_, final int p_78867_3_, final int p_78867_4_) {
        final Block var5 = this.worldObj.getBlock(p_78867_1_, p_78867_2_, p_78867_3_);
        if (var5.getMaterial() != Material.air) {
            final float var6 = 0.1f;
            double var7 = p_78867_1_ + this.rand.nextDouble() * (var5.getBlockBoundsMaxX() - var5.getBlockBoundsMinX() - var6 * 2.0f) + var6 + var5.getBlockBoundsMinX();
            double var8 = p_78867_2_ + this.rand.nextDouble() * (var5.getBlockBoundsMaxY() - var5.getBlockBoundsMinY() - var6 * 2.0f) + var6 + var5.getBlockBoundsMinY();
            double var9 = p_78867_3_ + this.rand.nextDouble() * (var5.getBlockBoundsMaxZ() - var5.getBlockBoundsMinZ() - var6 * 2.0f) + var6 + var5.getBlockBoundsMinZ();
            if (p_78867_4_ == 0) {
                var8 = p_78867_2_ + var5.getBlockBoundsMinY() - var6;
            }
            if (p_78867_4_ == 1) {
                var8 = p_78867_2_ + var5.getBlockBoundsMaxY() + var6;
            }
            if (p_78867_4_ == 2) {
                var9 = p_78867_3_ + var5.getBlockBoundsMinZ() - var6;
            }
            if (p_78867_4_ == 3) {
                var9 = p_78867_3_ + var5.getBlockBoundsMaxZ() + var6;
            }
            if (p_78867_4_ == 4) {
                var7 = p_78867_1_ + var5.getBlockBoundsMinX() - var6;
            }
            if (p_78867_4_ == 5) {
                var7 = p_78867_1_ + var5.getBlockBoundsMaxX() + var6;
            }
            this.addEffect(new EntityDiggingFX(this.worldObj, var7, var8, var9, 0.0, 0.0, 0.0, var5, this.worldObj.getBlockMetadata(p_78867_1_, p_78867_2_, p_78867_3_)).applyColourMultiplier(p_78867_1_, p_78867_2_, p_78867_3_).multiplyVelocity(0.2f).multipleParticleScaleBy(0.6f));
        }
    }
    
    public String getStatistics() {
        return "" + (this.fxLayers[0].size() + this.fxLayers[1].size() + this.fxLayers[2].size());
    }
    
    static {
        particleTextures = new ResourceLocation("textures/particle/particles.png");
    }
}
