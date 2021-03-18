package net.minecraft.client.particle;

import net.minecraft.world.*;
import net.minecraft.client.renderer.*;

public class EntityHugeExplodeFX extends EntityFX
{
    private int timeSinceStart;
    private int maximumTime;
    private static final String __OBFID = "CL_00000911";
    
    public EntityHugeExplodeFX(final World p_i1214_1_, final double p_i1214_2_, final double p_i1214_4_, final double p_i1214_6_, final double p_i1214_8_, final double p_i1214_10_, final double p_i1214_12_) {
        super(p_i1214_1_, p_i1214_2_, p_i1214_4_, p_i1214_6_, 0.0, 0.0, 0.0);
        this.maximumTime = 8;
    }
    
    @Override
    public void renderParticle(final Tessellator p_70539_1_, final float p_70539_2_, final float p_70539_3_, final float p_70539_4_, final float p_70539_5_, final float p_70539_6_, final float p_70539_7_) {
    }
    
    @Override
    public void onUpdate() {
        for (int var1 = 0; var1 < 6; ++var1) {
            final double var2 = this.posX + (this.rand.nextDouble() - this.rand.nextDouble()) * 4.0;
            final double var3 = this.posY + (this.rand.nextDouble() - this.rand.nextDouble()) * 4.0;
            final double var4 = this.posZ + (this.rand.nextDouble() - this.rand.nextDouble()) * 4.0;
            this.worldObj.spawnParticle("largeexplode", var2, var3, var4, this.timeSinceStart / (float)this.maximumTime, 0.0, 0.0);
        }
        ++this.timeSinceStart;
        if (this.timeSinceStart == this.maximumTime) {
            this.setDead();
        }
    }
    
    @Override
    public int getFXLayer() {
        return 1;
    }
}
