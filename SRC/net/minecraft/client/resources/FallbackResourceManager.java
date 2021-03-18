package net.minecraft.client.resources;

import net.minecraft.client.resources.data.*;
import net.minecraft.util.*;
import java.io.*;
import com.google.common.collect.*;
import java.util.*;

public class FallbackResourceManager implements IResourceManager
{
    protected final List resourcePacks;
    private final IMetadataSerializer frmMetadataSerializer;
    private static final String __OBFID = "CL_00001074";
    
    public FallbackResourceManager(final IMetadataSerializer p_i1289_1_) {
        this.resourcePacks = new ArrayList();
        this.frmMetadataSerializer = p_i1289_1_;
    }
    
    public void addResourcePack(final IResourcePack p_110538_1_) {
        this.resourcePacks.add(p_110538_1_);
    }
    
    @Override
    public Set getResourceDomains() {
        return null;
    }
    
    @Override
    public IResource getResource(final ResourceLocation p_110536_1_) throws IOException {
        IResourcePack var2 = null;
        final ResourceLocation var3 = getLocationMcmeta(p_110536_1_);
        for (int var4 = this.resourcePacks.size() - 1; var4 >= 0; --var4) {
            final IResourcePack var5 = this.resourcePacks.get(var4);
            if (var2 == null && var5.resourceExists(var3)) {
                var2 = var5;
            }
            if (var5.resourceExists(p_110536_1_)) {
                InputStream var6 = null;
                if (var2 != null) {
                    var6 = var2.getInputStream(var3);
                }
                return new SimpleResource(p_110536_1_, var5.getInputStream(p_110536_1_), var6, this.frmMetadataSerializer);
            }
        }
        throw new FileNotFoundException(p_110536_1_.toString());
    }
    
    @Override
    public List getAllResources(final ResourceLocation p_135056_1_) throws IOException {
        final ArrayList var2 = Lists.newArrayList();
        final ResourceLocation var3 = getLocationMcmeta(p_135056_1_);
        for (final IResourcePack var5 : this.resourcePacks) {
            if (var5.resourceExists(p_135056_1_)) {
                final InputStream var6 = var5.resourceExists(var3) ? var5.getInputStream(var3) : null;
                var2.add(new SimpleResource(p_135056_1_, var5.getInputStream(p_135056_1_), var6, this.frmMetadataSerializer));
            }
        }
        if (var2.isEmpty()) {
            throw new FileNotFoundException(p_135056_1_.toString());
        }
        return var2;
    }
    
    static ResourceLocation getLocationMcmeta(final ResourceLocation p_110537_0_) {
        return new ResourceLocation(p_110537_0_.getResourceDomain(), p_110537_0_.getResourcePath() + ".mcmeta");
    }
}
