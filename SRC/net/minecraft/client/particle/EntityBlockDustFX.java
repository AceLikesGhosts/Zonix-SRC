package net.minecraft.client.particle;

import net.minecraft.world.*;
import net.minecraft.block.*;

public class EntityBlockDustFX extends EntityDiggingFX
{
    private static final String __OBFID = "CL_00000931";
    
    public EntityBlockDustFX(final World p_i45072_1_, final double p_i45072_2_, final double p_i45072_4_, final double p_i45072_6_, final double p_i45072_8_, final double p_i45072_10_, final double p_i45072_12_, final Block p_i45072_14_, final int p_i45072_15_) {
        super(p_i45072_1_, p_i45072_2_, p_i45072_4_, p_i45072_6_, p_i45072_8_, p_i45072_10_, p_i45072_12_, p_i45072_14_, p_i45072_15_);
        this.motionX = p_i45072_8_;
        this.motionY = p_i45072_10_;
        this.motionZ = p_i45072_12_;
    }
}
