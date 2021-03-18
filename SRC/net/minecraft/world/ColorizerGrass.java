package net.minecraft.world;

public class ColorizerGrass
{
    private static int[] grassBuffer;
    private static final String __OBFID = "CL_00000138";
    
    public static void setGrassBiomeColorizer(final int[] p_77479_0_) {
        ColorizerGrass.grassBuffer = p_77479_0_;
    }
    
    public static int getGrassColor(final double p_77480_0_, double p_77480_2_) {
        p_77480_2_ *= p_77480_0_;
        final int var4 = (int)((1.0 - p_77480_0_) * 255.0);
        final int var5 = (int)((1.0 - p_77480_2_) * 255.0);
        return ColorizerGrass.grassBuffer[var5 << 8 | var4];
    }
    
    static {
        ColorizerGrass.grassBuffer = new int[65536];
    }
}
