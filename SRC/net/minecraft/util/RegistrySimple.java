package net.minecraft.util;

import com.google.common.collect.*;
import java.util.*;
import org.apache.logging.log4j.*;

public class RegistrySimple implements IRegistry
{
    private static final Logger logger;
    protected final Map registryObjects;
    private static final String __OBFID = "CL_00001210";
    
    public RegistrySimple() {
        this.registryObjects = this.createUnderlyingMap();
    }
    
    protected Map createUnderlyingMap() {
        return Maps.newHashMap();
    }
    
    @Override
    public Object getObject(final Object p_82594_1_) {
        return this.registryObjects.get(p_82594_1_);
    }
    
    @Override
    public void putObject(final Object p_82595_1_, final Object p_82595_2_) {
        if (this.registryObjects.containsKey(p_82595_1_)) {
            RegistrySimple.logger.debug("Adding duplicate key '" + p_82595_1_ + "' to registry");
        }
        this.registryObjects.put(p_82595_1_, p_82595_2_);
    }
    
    public Set getKeys() {
        return Collections.unmodifiableSet(this.registryObjects.keySet());
    }
    
    public boolean containsKey(final Object p_148741_1_) {
        return this.registryObjects.containsKey(p_148741_1_);
    }
    
    static {
        logger = LogManager.getLogger();
    }
}
