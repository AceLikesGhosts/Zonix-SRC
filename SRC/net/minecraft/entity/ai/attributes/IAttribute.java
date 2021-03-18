package net.minecraft.entity.ai.attributes;

public interface IAttribute
{
    String getAttributeUnlocalizedName();
    
    double clampValue(final double p0);
    
    double getDefaultValue();
    
    boolean getShouldWatch();
}
