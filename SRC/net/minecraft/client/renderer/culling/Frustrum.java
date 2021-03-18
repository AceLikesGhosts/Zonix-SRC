package net.minecraft.client.renderer.culling;

import net.minecraft.util.*;

public class Frustrum implements ICamera
{
    private ClippingHelper clippingHelper;
    private double xPosition;
    private double yPosition;
    private double zPosition;
    private static final String __OBFID = "CL_00000976";
    
    public Frustrum() {
        this.clippingHelper = ClippingHelperImpl.getInstance();
    }
    
    @Override
    public void setPosition(final double p_78547_1_, final double p_78547_3_, final double p_78547_5_) {
        this.xPosition = p_78547_1_;
        this.yPosition = p_78547_3_;
        this.zPosition = p_78547_5_;
    }
    
    public boolean isBoxInFrustum(final double p_78548_1_, final double p_78548_3_, final double p_78548_5_, final double p_78548_7_, final double p_78548_9_, final double p_78548_11_) {
        return this.clippingHelper.isBoxInFrustum(p_78548_1_ - this.xPosition, p_78548_3_ - this.yPosition, p_78548_5_ - this.zPosition, p_78548_7_ - this.xPosition, p_78548_9_ - this.yPosition, p_78548_11_ - this.zPosition);
    }
    
    public boolean isBoxInFrustumFully(final double minX, final double minY, final double minZ, final double maxX, final double maxY, final double maxZ) {
        return this.clippingHelper.isBoxInFrustumFully(minX - this.xPosition, minY - this.yPosition, minZ - this.zPosition, maxX - this.xPosition, maxY - this.yPosition, maxZ - this.zPosition);
    }
    
    @Override
    public boolean isBoundingBoxInFrustum(final AxisAlignedBB p_78546_1_) {
        return this.isBoxInFrustum(p_78546_1_.minX, p_78546_1_.minY, p_78546_1_.minZ, p_78546_1_.maxX, p_78546_1_.maxY, p_78546_1_.maxZ);
    }
    
    @Override
    public boolean isBoundingBoxInFrustumFully(final AxisAlignedBB p_78546_1_) {
        return this.isBoxInFrustumFully(p_78546_1_.minX, p_78546_1_.minY, p_78546_1_.minZ, p_78546_1_.maxX, p_78546_1_.maxY, p_78546_1_.maxZ);
    }
}
