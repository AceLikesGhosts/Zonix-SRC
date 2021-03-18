package net.minecraft.client.renderer.culling;

public class ClippingHelper
{
    public float[][] frustum;
    public float[] projectionMatrix;
    public float[] modelviewMatrix;
    public float[] clippingMatrix;
    private static final String __OBFID = "CL_00000977";
    
    public ClippingHelper() {
        this.frustum = new float[16][16];
        this.projectionMatrix = new float[16];
        this.modelviewMatrix = new float[16];
        this.clippingMatrix = new float[16];
    }
    
    public boolean isBoxInFrustum(final double p_78553_1_, final double p_78553_3_, final double p_78553_5_, final double p_78553_7_, final double p_78553_9_, final double p_78553_11_) {
        for (int var13 = 0; var13 < 6; ++var13) {
            if (this.frustum[var13][0] * p_78553_1_ + this.frustum[var13][1] * p_78553_3_ + this.frustum[var13][2] * p_78553_5_ + this.frustum[var13][3] <= 0.0 && this.frustum[var13][0] * p_78553_7_ + this.frustum[var13][1] * p_78553_3_ + this.frustum[var13][2] * p_78553_5_ + this.frustum[var13][3] <= 0.0 && this.frustum[var13][0] * p_78553_1_ + this.frustum[var13][1] * p_78553_9_ + this.frustum[var13][2] * p_78553_5_ + this.frustum[var13][3] <= 0.0 && this.frustum[var13][0] * p_78553_7_ + this.frustum[var13][1] * p_78553_9_ + this.frustum[var13][2] * p_78553_5_ + this.frustum[var13][3] <= 0.0 && this.frustum[var13][0] * p_78553_1_ + this.frustum[var13][1] * p_78553_3_ + this.frustum[var13][2] * p_78553_11_ + this.frustum[var13][3] <= 0.0 && this.frustum[var13][0] * p_78553_7_ + this.frustum[var13][1] * p_78553_3_ + this.frustum[var13][2] * p_78553_11_ + this.frustum[var13][3] <= 0.0 && this.frustum[var13][0] * p_78553_1_ + this.frustum[var13][1] * p_78553_9_ + this.frustum[var13][2] * p_78553_11_ + this.frustum[var13][3] <= 0.0 && this.frustum[var13][0] * p_78553_7_ + this.frustum[var13][1] * p_78553_9_ + this.frustum[var13][2] * p_78553_11_ + this.frustum[var13][3] <= 0.0) {
                return false;
            }
        }
        return true;
    }
    
    public boolean isBoxInFrustumFully(final double minX, final double minY, final double minZ, final double maxX, final double maxY, final double maxZ) {
        for (int i = 0; i < 6; ++i) {
            final float minXf = (float)minX;
            final float minYf = (float)minY;
            final float minZf = (float)minZ;
            final float maxXf = (float)maxX;
            final float maxYf = (float)maxY;
            final float maxZf = (float)maxZ;
            if (i < 4) {
                if (this.frustum[i][0] * minXf + this.frustum[i][1] * minYf + this.frustum[i][2] * minZf + this.frustum[i][3] <= 0.0f || this.frustum[i][0] * maxXf + this.frustum[i][1] * minYf + this.frustum[i][2] * minZf + this.frustum[i][3] <= 0.0f || this.frustum[i][0] * minXf + this.frustum[i][1] * maxYf + this.frustum[i][2] * minZf + this.frustum[i][3] <= 0.0f || this.frustum[i][0] * maxXf + this.frustum[i][1] * maxYf + this.frustum[i][2] * minZf + this.frustum[i][3] <= 0.0f || this.frustum[i][0] * minXf + this.frustum[i][1] * minYf + this.frustum[i][2] * maxZf + this.frustum[i][3] <= 0.0f || this.frustum[i][0] * maxXf + this.frustum[i][1] * minYf + this.frustum[i][2] * maxZf + this.frustum[i][3] <= 0.0f || this.frustum[i][0] * minXf + this.frustum[i][1] * maxYf + this.frustum[i][2] * maxZf + this.frustum[i][3] <= 0.0f || this.frustum[i][0] * maxXf + this.frustum[i][1] * maxYf + this.frustum[i][2] * maxZf + this.frustum[i][3] <= 0.0f) {
                    return false;
                }
            }
            else if (this.frustum[i][0] * minXf + this.frustum[i][1] * minYf + this.frustum[i][2] * minZf + this.frustum[i][3] <= 0.0f && this.frustum[i][0] * maxXf + this.frustum[i][1] * minYf + this.frustum[i][2] * minZf + this.frustum[i][3] <= 0.0f && this.frustum[i][0] * minXf + this.frustum[i][1] * maxYf + this.frustum[i][2] * minZf + this.frustum[i][3] <= 0.0f && this.frustum[i][0] * maxXf + this.frustum[i][1] * maxYf + this.frustum[i][2] * minZf + this.frustum[i][3] <= 0.0f && this.frustum[i][0] * minXf + this.frustum[i][1] * minYf + this.frustum[i][2] * maxZf + this.frustum[i][3] <= 0.0f && this.frustum[i][0] * maxXf + this.frustum[i][1] * minYf + this.frustum[i][2] * maxZf + this.frustum[i][3] <= 0.0f && this.frustum[i][0] * minXf + this.frustum[i][1] * maxYf + this.frustum[i][2] * maxZf + this.frustum[i][3] <= 0.0f && this.frustum[i][0] * maxXf + this.frustum[i][1] * maxYf + this.frustum[i][2] * maxZf + this.frustum[i][3] <= 0.0f) {
                return false;
            }
        }
        return true;
    }
}
