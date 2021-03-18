package net.minecraft.client.particle;

import net.minecraft.entity.*;
import net.minecraft.world.*;
import net.minecraft.client.renderer.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.entity.*;

public class EntityPickupFX extends EntityFX
{
    private Entity entityToPickUp;
    private Entity entityPickingUp;
    private int age;
    private int maxAge;
    private float yOffs;
    private static final String __OBFID = "CL_00000930";
    
    public EntityPickupFX(final World p_i1233_1_, final Entity p_i1233_2_, final Entity p_i1233_3_, final float p_i1233_4_) {
        super(p_i1233_1_, p_i1233_2_.posX, p_i1233_2_.posY, p_i1233_2_.posZ, p_i1233_2_.motionX, p_i1233_2_.motionY, p_i1233_2_.motionZ);
        this.entityToPickUp = p_i1233_2_;
        this.entityPickingUp = p_i1233_3_;
        this.maxAge = 3;
        this.yOffs = p_i1233_4_;
    }
    
    @Override
    public void renderParticle(final Tessellator p_70539_1_, final float p_70539_2_, final float p_70539_3_, final float p_70539_4_, final float p_70539_5_, final float p_70539_6_, final float p_70539_7_) {
        float var8 = (this.age + p_70539_2_) / this.maxAge;
        var8 *= var8;
        final double var9 = this.entityToPickUp.posX;
        final double var10 = this.entityToPickUp.posY;
        final double var11 = this.entityToPickUp.posZ;
        final double var12 = this.entityPickingUp.lastTickPosX + (this.entityPickingUp.posX - this.entityPickingUp.lastTickPosX) * p_70539_2_;
        final double var13 = this.entityPickingUp.lastTickPosY + (this.entityPickingUp.posY - this.entityPickingUp.lastTickPosY) * p_70539_2_ + this.yOffs;
        final double var14 = this.entityPickingUp.lastTickPosZ + (this.entityPickingUp.posZ - this.entityPickingUp.lastTickPosZ) * p_70539_2_;
        double var15 = var9 + (var12 - var9) * var8;
        double var16 = var10 + (var13 - var10) * var8;
        double var17 = var11 + (var14 - var11) * var8;
        final int var18 = this.getBrightnessForRender(p_70539_2_);
        final int var19 = var18 % 65536;
        final int var20 = var18 / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, var19 / 1.0f, var20 / 1.0f);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        var15 -= EntityPickupFX.interpPosX;
        var16 -= EntityPickupFX.interpPosY;
        var17 -= EntityPickupFX.interpPosZ;
        RenderManager.instance.func_147940_a(this.entityToPickUp, (float)var15, (float)var16, (float)var17, this.entityToPickUp.rotationYaw, p_70539_2_);
    }
    
    @Override
    public void onUpdate() {
        ++this.age;
        if (this.age == this.maxAge) {
            this.setDead();
        }
    }
    
    @Override
    public int getFXLayer() {
        return 3;
    }
}
