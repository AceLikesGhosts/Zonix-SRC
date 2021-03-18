package net.minecraft.client.particle;

import net.minecraft.client.renderer.texture.*;
import net.minecraft.world.*;
import net.minecraft.client.renderer.*;
import org.lwjgl.opengl.*;
import net.minecraft.util.*;

public class EntityFootStepFX extends EntityFX
{
    private static final ResourceLocation field_110126_a;
    private int footstepAge;
    private int footstepMaxAge;
    private TextureManager currentFootSteps;
    private static final String __OBFID = "CL_00000908";
    
    public EntityFootStepFX(final TextureManager p_i1210_1_, final World p_i1210_2_, final double p_i1210_3_, final double p_i1210_5_, final double p_i1210_7_) {
        super(p_i1210_2_, p_i1210_3_, p_i1210_5_, p_i1210_7_, 0.0, 0.0, 0.0);
        this.currentFootSteps = p_i1210_1_;
        final double motionX = 0.0;
        this.motionZ = motionX;
        this.motionY = motionX;
        this.motionX = motionX;
        this.footstepMaxAge = 200;
    }
    
    @Override
    public void renderParticle(final Tessellator p_70539_1_, final float p_70539_2_, final float p_70539_3_, final float p_70539_4_, final float p_70539_5_, final float p_70539_6_, final float p_70539_7_) {
        float var8 = (this.footstepAge + p_70539_2_) / this.footstepMaxAge;
        var8 *= var8;
        float var9 = 2.0f - var8 * 2.0f;
        if (var9 > 1.0f) {
            var9 = 1.0f;
        }
        var9 *= 0.2f;
        GL11.glDisable(2896);
        final float var10 = 0.125f;
        final float var11 = (float)(this.posX - EntityFootStepFX.interpPosX);
        final float var12 = (float)(this.posY - EntityFootStepFX.interpPosY);
        final float var13 = (float)(this.posZ - EntityFootStepFX.interpPosZ);
        final float var14 = this.worldObj.getLightBrightness(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ));
        this.currentFootSteps.bindTexture(EntityFootStepFX.field_110126_a);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        p_70539_1_.startDrawingQuads();
        p_70539_1_.setColorRGBA_F(var14, var14, var14, var9);
        p_70539_1_.addVertexWithUV(var11 - var10, var12, var13 + var10, 0.0, 1.0);
        p_70539_1_.addVertexWithUV(var11 + var10, var12, var13 + var10, 1.0, 1.0);
        p_70539_1_.addVertexWithUV(var11 + var10, var12, var13 - var10, 1.0, 0.0);
        p_70539_1_.addVertexWithUV(var11 - var10, var12, var13 - var10, 0.0, 0.0);
        p_70539_1_.draw();
        GL11.glDisable(3042);
        GL11.glEnable(2896);
    }
    
    @Override
    public void onUpdate() {
        ++this.footstepAge;
        if (this.footstepAge == this.footstepMaxAge) {
            this.setDead();
        }
    }
    
    @Override
    public int getFXLayer() {
        return 3;
    }
    
    static {
        field_110126_a = new ResourceLocation("textures/particle/footprint.png");
    }
}
