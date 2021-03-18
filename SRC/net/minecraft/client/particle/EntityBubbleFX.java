package net.minecraft.client.particle;

import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.block.material.*;

public class EntityBubbleFX extends EntityFX
{
    private static final String __OBFID = "CL_00000898";
    
    public EntityBubbleFX(final World p_i1198_1_, final double p_i1198_2_, final double p_i1198_4_, final double p_i1198_6_, final double p_i1198_8_, final double p_i1198_10_, final double p_i1198_12_) {
        super(p_i1198_1_, p_i1198_2_, p_i1198_4_, p_i1198_6_, p_i1198_8_, p_i1198_10_, p_i1198_12_);
        this.particleRed = 1.0f;
        this.particleGreen = 1.0f;
        this.particleBlue = 1.0f;
        this.setParticleTextureIndex(32);
        this.setSize(0.02f, 0.02f);
        this.particleScale *= this.rand.nextFloat() * 0.6f + 0.2f;
        this.motionX = p_i1198_8_ * 0.20000000298023224 + (float)(Math.random() * 2.0 - 1.0) * 0.02f;
        this.motionY = p_i1198_10_ * 0.20000000298023224 + (float)(Math.random() * 2.0 - 1.0) * 0.02f;
        this.motionZ = p_i1198_12_ * 0.20000000298023224 + (float)(Math.random() * 2.0 - 1.0) * 0.02f;
        this.particleMaxAge = (int)(8.0 / (Math.random() * 0.8 + 0.2));
    }
    
    @Override
    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.motionY += 0.002;
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        this.motionX *= 0.8500000238418579;
        this.motionY *= 0.8500000238418579;
        this.motionZ *= 0.8500000238418579;
        if (this.worldObj.getBlock(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ)).getMaterial() != Material.water) {
            this.setDead();
        }
        if (this.particleMaxAge-- <= 0) {
            this.setDead();
        }
    }
}
