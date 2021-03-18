package net.minecraft.util;

public interface IIcon
{
    int getIconWidth();
    
    int getIconHeight();
    
    float getMinU();
    
    float getMaxU();
    
    float getInterpolatedU(final double p0);
    
    float getMinV();
    
    float getMaxV();
    
    float getInterpolatedV(final double p0);
    
    String getIconName();
}
