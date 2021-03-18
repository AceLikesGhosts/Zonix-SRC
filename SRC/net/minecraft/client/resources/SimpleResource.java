package net.minecraft.client.resources;

import java.util.*;
import net.minecraft.util.*;
import com.google.common.collect.*;
import net.minecraft.client.resources.data.*;
import java.io.*;
import com.google.gson.*;
import org.apache.commons.io.*;

public class SimpleResource implements IResource
{
    private final Map mapMetadataSections;
    private final ResourceLocation srResourceLocation;
    private final InputStream resourceInputStream;
    private final InputStream mcmetaInputStream;
    private final IMetadataSerializer srMetadataSerializer;
    private boolean mcmetaJsonChecked;
    private JsonObject mcmetaJson;
    private static final String __OBFID = "CL_00001093";
    
    public SimpleResource(final ResourceLocation p_i1300_1_, final InputStream p_i1300_2_, final InputStream p_i1300_3_, final IMetadataSerializer p_i1300_4_) {
        this.mapMetadataSections = Maps.newHashMap();
        this.srResourceLocation = p_i1300_1_;
        this.resourceInputStream = p_i1300_2_;
        this.mcmetaInputStream = p_i1300_3_;
        this.srMetadataSerializer = p_i1300_4_;
    }
    
    @Override
    public InputStream getInputStream() {
        return this.resourceInputStream;
    }
    
    @Override
    public boolean hasMetadata() {
        return this.mcmetaInputStream != null;
    }
    
    @Override
    public IMetadataSection getMetadata(final String p_110526_1_) {
        if (!this.hasMetadata()) {
            return null;
        }
        if (this.mcmetaJson == null && !this.mcmetaJsonChecked) {
            this.mcmetaJsonChecked = true;
            BufferedReader var2 = null;
            try {
                var2 = new BufferedReader(new InputStreamReader(this.mcmetaInputStream));
                this.mcmetaJson = new JsonParser().parse((Reader)var2).getAsJsonObject();
            }
            finally {
                IOUtils.closeQuietly((Reader)var2);
            }
        }
        IMetadataSection var3 = this.mapMetadataSections.get(p_110526_1_);
        if (var3 == null) {
            var3 = this.srMetadataSerializer.parseMetadataSection(p_110526_1_, this.mcmetaJson);
        }
        return var3;
    }
    
    @Override
    public boolean equals(final Object p_equals_1_) {
        if (this == p_equals_1_) {
            return true;
        }
        if (p_equals_1_ instanceof SimpleResource) {
            final SimpleResource var2 = (SimpleResource)p_equals_1_;
            return (this.srResourceLocation != null) ? this.srResourceLocation.equals(var2.srResourceLocation) : (var2.srResourceLocation == null);
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        return (this.srResourceLocation == null) ? 0 : this.srResourceLocation.hashCode();
    }
}
