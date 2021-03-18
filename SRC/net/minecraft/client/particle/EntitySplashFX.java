package net.minecraft.client.particle;

import net.minecraft.world.*;

public class EntitySplashFX extends EntityRainFX
{
    private static final String __OBFID = "CL_00000927";
    
    public EntitySplashFX(final World p_i1230_1_, final double p_i1230_2_, final double p_i1230_4_, final double p_i1230_6_, final double p_i1230_8_, final double p_i1230_10_, final double p_i1230_12_) {
        super(p_i1230_1_, p_i1230_2_, p_i1230_4_, p_i1230_6_);
        this.particleGravity = 0.04f;
        this.nextTextureIndexX();
        if (p_i1230_10_ == 0.0 && (p_i1230_8_ != 0.0 || p_i1230_12_ != 0.0)) {
            this.motionX = p_i1230_8_;
            this.motionY = p_i1230_10_ + 0.1;
            this.motionZ = p_i1230_12_;
        }
    }
}
