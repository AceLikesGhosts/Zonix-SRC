package net.minecraft.client.particle;

import net.minecraft.entity.*;
import net.minecraft.world.*;
import net.minecraft.client.renderer.*;

public class EntityCrit2FX extends EntityFX
{
    private Entity theEntity;
    private int currentLife;
    private int maximumLife;
    private String particleName;
    private static final String __OBFID = "CL_00000899";
    
    public EntityCrit2FX(final World p_i1199_1_, final Entity p_i1199_2_) {
        this(p_i1199_1_, p_i1199_2_, "crit");
    }
    
    public EntityCrit2FX(final World p_i1200_1_, final Entity p_i1200_2_, final String p_i1200_3_) {
        super(p_i1200_1_, p_i1200_2_.posX, p_i1200_2_.boundingBox.minY + p_i1200_2_.height / 2.0f, p_i1200_2_.posZ, p_i1200_2_.motionX, p_i1200_2_.motionY, p_i1200_2_.motionZ);
        this.theEntity = p_i1200_2_;
        this.maximumLife = 3;
        this.particleName = p_i1200_3_;
        this.onUpdate();
    }
    
    @Override
    public void renderParticle(final Tessellator p_70539_1_, final float p_70539_2_, final float p_70539_3_, final float p_70539_4_, final float p_70539_5_, final float p_70539_6_, final float p_70539_7_) {
    }
    
    @Override
    public void onUpdate() {
        for (int var1 = 0; var1 < 16; ++var1) {
            final double var2 = this.rand.nextFloat() * 2.0f - 1.0f;
            final double var3 = this.rand.nextFloat() * 2.0f - 1.0f;
            final double var4 = this.rand.nextFloat() * 2.0f - 1.0f;
            if (var2 * var2 + var3 * var3 + var4 * var4 <= 1.0) {
                final double var5 = this.theEntity.posX + var2 * this.theEntity.width / 4.0;
                final double var6 = this.theEntity.boundingBox.minY + this.theEntity.height / 2.0f + var3 * this.theEntity.height / 4.0;
                final double var7 = this.theEntity.posZ + var4 * this.theEntity.width / 4.0;
                this.worldObj.spawnParticle(this.particleName, var5, var6, var7, var2, var3 + 0.2, var4);
            }
        }
        ++this.currentLife;
        if (this.currentLife >= this.maximumLife) {
            this.setDead();
        }
    }
    
    @Override
    public int getFXLayer() {
        return 3;
    }
}
