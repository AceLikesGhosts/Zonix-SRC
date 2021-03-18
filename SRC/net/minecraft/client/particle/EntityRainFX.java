package net.minecraft.client.particle;

import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.block.*;
import net.minecraft.block.material.*;

public class EntityRainFX extends EntityFX
{
    private static final String __OBFID = "CL_00000934";
    
    public EntityRainFX(final World p_i1235_1_, final double p_i1235_2_, final double p_i1235_4_, final double p_i1235_6_) {
        super(p_i1235_1_, p_i1235_2_, p_i1235_4_, p_i1235_6_, 0.0, 0.0, 0.0);
        this.motionX *= 0.30000001192092896;
        this.motionY = (float)Math.random() * 0.2f + 0.1f;
        this.motionZ *= 0.30000001192092896;
        this.particleRed = 1.0f;
        this.particleGreen = 1.0f;
        this.particleBlue = 1.0f;
        this.setParticleTextureIndex(19 + this.rand.nextInt(4));
        this.setSize(0.01f, 0.01f);
        this.particleGravity = 0.06f;
        this.particleMaxAge = (int)(8.0 / (Math.random() * 0.8 + 0.2));
    }
    
    @Override
    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.motionY -= this.particleGravity;
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        this.motionX *= 0.9800000190734863;
        this.motionY *= 0.9800000190734863;
        this.motionZ *= 0.9800000190734863;
        if (this.particleMaxAge-- <= 0) {
            this.setDead();
        }
        if (this.onGround) {
            if (Math.random() < 0.5) {
                this.setDead();
            }
            this.motionX *= 0.699999988079071;
            this.motionZ *= 0.699999988079071;
        }
        final Material var1 = this.worldObj.getBlock(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ)).getMaterial();
        if (var1.isLiquid() || var1.isSolid()) {
            final double var2 = MathHelper.floor_double(this.posY) + 1 - BlockLiquid.func_149801_b(this.worldObj.getBlockMetadata(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ)));
            if (this.posY < var2) {
                this.setDead();
            }
        }
    }
}
