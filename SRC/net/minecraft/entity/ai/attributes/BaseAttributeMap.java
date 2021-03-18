package net.minecraft.entity.ai.attributes;

import net.minecraft.server.management.*;
import com.google.common.collect.*;
import java.util.*;

public abstract class BaseAttributeMap
{
    protected final Map attributes;
    protected final Map attributesByName;
    private static final String __OBFID = "CL_00001566";
    
    public BaseAttributeMap() {
        this.attributes = new HashMap();
        this.attributesByName = new LowerStringMap();
    }
    
    public IAttributeInstance getAttributeInstance(final IAttribute p_111151_1_) {
        return this.attributes.get(p_111151_1_);
    }
    
    public IAttributeInstance getAttributeInstanceByName(final String p_111152_1_) {
        return this.attributesByName.get(p_111152_1_);
    }
    
    public abstract IAttributeInstance registerAttribute(final IAttribute p0);
    
    public Collection getAllAttributes() {
        return this.attributesByName.values();
    }
    
    public void addAttributeInstance(final ModifiableAttributeInstance p_111149_1_) {
    }
    
    public void removeAttributeModifiers(final Multimap p_111148_1_) {
        for (final Map.Entry var3 : p_111148_1_.entries()) {
            final IAttributeInstance var4 = this.getAttributeInstanceByName(var3.getKey());
            if (var4 != null) {
                var4.removeModifier(var3.getValue());
            }
        }
    }
    
    public void applyAttributeModifiers(final Multimap p_111147_1_) {
        for (final Map.Entry var3 : p_111147_1_.entries()) {
            final IAttributeInstance var4 = this.getAttributeInstanceByName(var3.getKey());
            if (var4 != null) {
                var4.removeModifier(var3.getValue());
                var4.applyModifier(var3.getValue());
            }
        }
    }
}
