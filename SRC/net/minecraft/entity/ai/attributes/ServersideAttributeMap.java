package net.minecraft.entity.ai.attributes;

import com.google.common.collect.*;
import net.minecraft.server.management.*;
import java.util.*;

public class ServersideAttributeMap extends BaseAttributeMap
{
    private final Set attributeInstanceSet;
    protected final Map descriptionToAttributeInstanceMap;
    private static final String __OBFID = "CL_00001569";
    
    public ServersideAttributeMap() {
        this.attributeInstanceSet = Sets.newHashSet();
        this.descriptionToAttributeInstanceMap = new LowerStringMap();
    }
    
    @Override
    public ModifiableAttributeInstance getAttributeInstance(final IAttribute p_111151_1_) {
        return (ModifiableAttributeInstance)super.getAttributeInstance(p_111151_1_);
    }
    
    @Override
    public ModifiableAttributeInstance getAttributeInstanceByName(final String p_111152_1_) {
        IAttributeInstance var2 = super.getAttributeInstanceByName(p_111152_1_);
        if (var2 == null) {
            var2 = this.descriptionToAttributeInstanceMap.get(p_111152_1_);
        }
        return (ModifiableAttributeInstance)var2;
    }
    
    @Override
    public IAttributeInstance registerAttribute(final IAttribute p_111150_1_) {
        if (this.attributesByName.containsKey(p_111150_1_.getAttributeUnlocalizedName())) {
            throw new IllegalArgumentException("Attribute is already registered!");
        }
        final ModifiableAttributeInstance var2 = new ModifiableAttributeInstance(this, p_111150_1_);
        this.attributesByName.put(p_111150_1_.getAttributeUnlocalizedName(), var2);
        if (p_111150_1_ instanceof RangedAttribute && ((RangedAttribute)p_111150_1_).getDescription() != null) {
            this.descriptionToAttributeInstanceMap.put(((RangedAttribute)p_111150_1_).getDescription(), var2);
        }
        this.attributes.put(p_111150_1_, var2);
        return var2;
    }
    
    @Override
    public void addAttributeInstance(final ModifiableAttributeInstance p_111149_1_) {
        if (p_111149_1_.getAttribute().getShouldWatch()) {
            this.attributeInstanceSet.add(p_111149_1_);
        }
    }
    
    public Set getAttributeInstanceSet() {
        return this.attributeInstanceSet;
    }
    
    public Collection getWatchedAttributes() {
        final HashSet var1 = Sets.newHashSet();
        for (final IAttributeInstance var3 : this.getAllAttributes()) {
            if (var3.getAttribute().getShouldWatch()) {
                var1.add(var3);
            }
        }
        return var1;
    }
}
