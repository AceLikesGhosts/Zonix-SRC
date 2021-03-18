package com.thevoxelbox.voxelmap.util;

import java.util.*;
import com.google.common.collect.*;
import net.minecraft.client.*;
import net.minecraft.client.resources.*;
import net.minecraft.util.*;
import java.io.*;
import net.minecraft.client.resources.data.*;
import java.awt.image.*;

public class AddonResourcePack implements IResourcePack
{
    public static Set defaultResourceDomains;
    private AddonDefaultResourcePack defaultResourcePack;
    private FileResourcePack fileResourcePack;
    private FolderResourcePack folderResourcePack;
    private String domain;
    
    public AddonResourcePack(final String domain) {
        this.domain = domain;
        AddonResourcePack.defaultResourceDomains = (Set)ImmutableSet.of((Object)domain);
        final File fileAssets = (File)ReflectionUtils.getPrivateFieldValueByType(Minecraft.getMinecraft(), Minecraft.class, File.class, 2);
        this.defaultResourcePack = new AddonDefaultResourcePack(new ResourceIndex(fileAssets, domain).func_152782_a(), domain);
        this.fileResourcePack = new FileResourcePack(Minecraft.getMinecraft().mcDataDir);
        this.folderResourcePack = new FolderResourcePack(Minecraft.getMinecraft().mcDataDir);
    }
    
    @Override
    public Set getResourceDomains() {
        return AddonResourcePack.defaultResourceDomains;
    }
    
    @Override
    public InputStream getInputStream(final ResourceLocation var1) throws IOException {
        try {
            return this.defaultResourcePack.getInputStream(var1);
        }
        catch (IOException e) {
            try {
                return this.fileResourcePack.getInputStream(var1);
            }
            catch (IOException iooe) {
                try {
                    return this.folderResourcePack.getInputStream(var1);
                }
                catch (IOException ioe) {
                    throw ioe;
                }
            }
        }
    }
    
    @Override
    public boolean resourceExists(final ResourceLocation var1) {
        return this.defaultResourcePack.resourceExists(var1) || this.fileResourcePack.resourceExists(var1) || this.folderResourcePack.resourceExists(var1);
    }
    
    @Override
    public IMetadataSection getPackMetadata(final IMetadataSerializer var1, final String var2) throws IOException {
        return null;
    }
    
    @Override
    public BufferedImage getPackImage() throws IOException {
        return null;
    }
    
    @Override
    public String getPackName() {
        return this.domain + "ResourcePack";
    }
    
    static {
        AddonResourcePack.defaultResourceDomains = null;
    }
}
