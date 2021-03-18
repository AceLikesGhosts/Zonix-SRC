package net.minecraft.client.resources;

import net.minecraft.client.settings.*;
import com.google.common.collect.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import java.util.*;
import net.minecraft.client.resources.data.*;
import java.awt.image.*;
import net.minecraft.client.renderer.texture.*;
import java.io.*;
import org.apache.commons.io.*;
import net.minecraft.util.*;

public class ResourcePackRepository
{
    protected static final FileFilter resourcePackFilter;
    private final File dirResourcepacks;
    public final IResourcePack rprDefaultResourcePack;
    private final File field_148534_e;
    public final IMetadataSerializer rprMetadataSerializer;
    private IResourcePack field_148532_f;
    private boolean field_148533_g;
    private List repositoryEntriesAll;
    private List repositoryEntries;
    private static final String __OBFID = "CL_00001087";
    
    public ResourcePackRepository(final File p_i45101_1_, final File p_i45101_2_, final IResourcePack p_i45101_3_, final IMetadataSerializer p_i45101_4_, final GameSettings p_i45101_5_) {
        this.repositoryEntriesAll = Lists.newArrayList();
        this.repositoryEntries = Lists.newArrayList();
        this.dirResourcepacks = p_i45101_1_;
        this.field_148534_e = p_i45101_2_;
        this.rprDefaultResourcePack = p_i45101_3_;
        this.rprMetadataSerializer = p_i45101_4_;
        this.fixDirResourcepacks();
        this.updateRepositoryEntriesAll();
        for (final String var7 : p_i45101_5_.resourcePacks) {
            for (final Entry var9 : this.repositoryEntriesAll) {
                if (var9.getResourcePackName().equals(var7)) {
                    this.repositoryEntries.add(var9);
                    break;
                }
            }
        }
    }
    
    private void fixDirResourcepacks() {
        if (!this.dirResourcepacks.isDirectory()) {
            this.dirResourcepacks.delete();
            this.dirResourcepacks.mkdirs();
        }
    }
    
    private List getResourcePackFiles() {
        return this.dirResourcepacks.isDirectory() ? Arrays.asList(this.dirResourcepacks.listFiles(ResourcePackRepository.resourcePackFilter)) : Collections.emptyList();
    }
    
    public void updateRepositoryEntriesAll() {
        final ArrayList var1 = Lists.newArrayList();
        for (final File var3 : this.getResourcePackFiles()) {
            final Entry var4 = new Entry(var3, null);
            if (!this.repositoryEntriesAll.contains(var4)) {
                try {
                    var4.updateResourcePack();
                    var1.add(var4);
                }
                catch (Exception var7) {
                    var1.remove(var4);
                }
            }
            else {
                final int var5 = this.repositoryEntriesAll.indexOf(var4);
                if (var5 <= -1 || var5 >= this.repositoryEntriesAll.size()) {
                    continue;
                }
                var1.add(this.repositoryEntriesAll.get(var5));
            }
        }
        this.repositoryEntriesAll.removeAll(var1);
        for (final Entry var6 : this.repositoryEntriesAll) {
            var6.closeResourcePack();
        }
        this.repositoryEntriesAll = var1;
    }
    
    public List getRepositoryEntriesAll() {
        return (List)ImmutableList.copyOf((Collection)this.repositoryEntriesAll);
    }
    
    public List getRepositoryEntries() {
        return (List)ImmutableList.copyOf((Collection)this.repositoryEntries);
    }
    
    public void func_148527_a(final List p_148527_1_) {
        this.repositoryEntries.clear();
        this.repositoryEntries.addAll(p_148527_1_);
    }
    
    public File getDirResourcepacks() {
        return this.dirResourcepacks;
    }
    
    public void func_148526_a(final String p_148526_1_) {
        String var2 = p_148526_1_.substring(p_148526_1_.lastIndexOf("/") + 1);
        if (var2.contains("?")) {
            var2 = var2.substring(0, var2.indexOf("?"));
        }
        if (var2.endsWith(".zip")) {
            final File var3 = new File(this.field_148534_e, var2.replaceAll("\\W", ""));
            this.func_148529_f();
            this.func_148528_a(p_148526_1_, var3);
        }
    }
    
    private void func_148528_a(final String p_148528_1_, final File p_148528_2_) {
        final HashMap var3 = Maps.newHashMap();
        final GuiScreenWorking var4 = new GuiScreenWorking();
        var3.put("X-Minecraft-Username", Minecraft.getMinecraft().getSession().getUsername());
        var3.put("X-Minecraft-UUID", Minecraft.getMinecraft().getSession().getPlayerID());
        var3.put("X-Minecraft-Version", "1.7.10");
        this.field_148533_g = true;
        Minecraft.getMinecraft().displayGuiScreen(var4);
        HttpUtil.func_151223_a(p_148528_2_, p_148528_1_, new HttpUtil.DownloadListener() {
            private static final String __OBFID = "CL_00001089";
            
            @Override
            public void func_148522_a(final File p_148522_1_) {
                if (ResourcePackRepository.this.field_148533_g) {
                    ResourcePackRepository.this.field_148533_g = false;
                    ResourcePackRepository.this.field_148532_f = new FileResourcePack(p_148522_1_);
                    Minecraft.getMinecraft().scheduleResourcesRefresh();
                }
            }
        }, var3, 52428800, var4, Minecraft.getMinecraft().getProxy());
    }
    
    public IResourcePack func_148530_e() {
        return this.field_148532_f;
    }
    
    public void func_148529_f() {
        this.field_148532_f = null;
        this.field_148533_g = false;
    }
    
    static {
        resourcePackFilter = new FileFilter() {
            private static final String __OBFID = "CL_00001088";
            
            @Override
            public boolean accept(final File p_accept_1_) {
                final boolean var2 = p_accept_1_.isFile() && p_accept_1_.getName().endsWith(".zip");
                final boolean var3 = p_accept_1_.isDirectory() && new File(p_accept_1_, "pack.mcmeta").isFile();
                return var2 || var3;
            }
        };
    }
    
    public class Entry
    {
        private final File resourcePackFile;
        private IResourcePack reResourcePack;
        private PackMetadataSection rePackMetadataSection;
        private BufferedImage texturePackIcon;
        private ResourceLocation locationTexturePackIcon;
        private static final String __OBFID = "CL_00001090";
        
        private Entry(final File p_i1295_2_) {
            this.resourcePackFile = p_i1295_2_;
        }
        
        public void updateResourcePack() throws IOException {
            this.reResourcePack = (this.resourcePackFile.isDirectory() ? new FolderResourcePack(this.resourcePackFile) : new FileResourcePack(this.resourcePackFile));
            this.rePackMetadataSection = (PackMetadataSection)this.reResourcePack.getPackMetadata(ResourcePackRepository.this.rprMetadataSerializer, "pack");
            try {
                this.texturePackIcon = this.reResourcePack.getPackImage();
            }
            catch (IOException ex) {}
            if (this.texturePackIcon == null) {
                this.texturePackIcon = ResourcePackRepository.this.rprDefaultResourcePack.getPackImage();
            }
            this.closeResourcePack();
        }
        
        public void bindTexturePackIcon(final TextureManager p_110518_1_) {
            if (this.locationTexturePackIcon == null) {
                this.locationTexturePackIcon = p_110518_1_.getDynamicTextureLocation("texturepackicon", new DynamicTexture(this.texturePackIcon));
            }
            p_110518_1_.bindTexture(this.locationTexturePackIcon);
        }
        
        public void closeResourcePack() {
            if (this.reResourcePack instanceof Closeable) {
                IOUtils.closeQuietly((Closeable)this.reResourcePack);
            }
        }
        
        public IResourcePack getResourcePack() {
            return this.reResourcePack;
        }
        
        public String getResourcePackName() {
            return this.reResourcePack.getPackName();
        }
        
        public String getTexturePackDescription() {
            return (this.rePackMetadataSection == null) ? (EnumChatFormatting.RED + "Invalid pack.mcmeta (or missing 'pack' section)") : this.rePackMetadataSection.func_152805_a().getFormattedText();
        }
        
        @Override
        public boolean equals(final Object p_equals_1_) {
            return this == p_equals_1_ || (p_equals_1_ instanceof Entry && this.toString().equals(p_equals_1_.toString()));
        }
        
        @Override
        public int hashCode() {
            return this.toString().hashCode();
        }
        
        @Override
        public String toString() {
            return String.format("%s:%s:%d", this.resourcePackFile.getName(), this.resourcePackFile.isDirectory() ? "folder" : "zip", this.resourcePackFile.lastModified());
        }
        
        Entry(final ResourcePackRepository this$0, final File p_i1296_2_, final Object p_i1296_3_) {
            this(this$0, p_i1296_2_);
        }
    }
}
