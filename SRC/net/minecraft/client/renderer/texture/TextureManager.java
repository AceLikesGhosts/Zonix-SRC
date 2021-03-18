package net.minecraft.client.renderer.texture;

import net.minecraft.client.resources.*;
import com.google.common.collect.*;
import java.io.*;
import java.util.concurrent.*;
import net.minecraft.util.*;
import net.minecraft.crash.*;
import java.util.*;
import org.apache.logging.log4j.*;

public class TextureManager implements ITickable, IResourceManagerReloadListener
{
    private static final Logger logger;
    private final Map mapTextureObjects;
    private final Map mapResourceLocations;
    private final List listTickables;
    private final Map mapTextureCounters;
    private IResourceManager theResourceManager;
    private static final String __OBFID = "CL_00001064";
    
    public TextureManager(final IResourceManager p_i1284_1_) {
        this.mapTextureObjects = Maps.newHashMap();
        this.mapResourceLocations = Maps.newHashMap();
        this.listTickables = Lists.newArrayList();
        this.mapTextureCounters = Maps.newHashMap();
        this.theResourceManager = p_i1284_1_;
    }
    
    public void bindTexture(final ResourceLocation p_110577_1_) {
        Object var2 = this.mapTextureObjects.get(p_110577_1_);
        if (var2 == null) {
            var2 = new SimpleTexture(p_110577_1_);
            this.loadTexture(p_110577_1_, (ITextureObject)var2);
        }
        TextureUtil.bindTexture(((ITextureObject)var2).getGlTextureId());
    }
    
    public ResourceLocation getResourceLocation(final int p_130087_1_) {
        return this.mapResourceLocations.get(p_130087_1_);
    }
    
    public boolean loadTextureMap(final ResourceLocation p_130088_1_, final TextureMap p_130088_2_) {
        if (this.loadTickableTexture(p_130088_1_, p_130088_2_)) {
            this.mapResourceLocations.put(p_130088_2_.getTextureType(), p_130088_1_);
            return true;
        }
        return false;
    }
    
    public boolean loadTickableTexture(final ResourceLocation p_110580_1_, final ITickableTextureObject p_110580_2_) {
        if (this.loadTexture(p_110580_1_, p_110580_2_)) {
            this.listTickables.add(p_110580_2_);
            return true;
        }
        return false;
    }
    
    public boolean loadTexture(final ResourceLocation p_110579_1_, final ITextureObject p_110579_2_) {
        boolean var3 = true;
        ITextureObject p_110579_2_2 = p_110579_2_;
        try {
            p_110579_2_.loadTexture(this.theResourceManager);
        }
        catch (IOException var4) {
            TextureManager.logger.warn("Failed to load texture: " + p_110579_1_, (Throwable)var4);
            p_110579_2_2 = TextureUtil.missingTexture;
            this.mapTextureObjects.put(p_110579_1_, p_110579_2_2);
            var3 = false;
        }
        catch (Throwable var6) {
            final CrashReport var5 = CrashReport.makeCrashReport(var6, "Registering texture");
            final CrashReportCategory var7 = var5.makeCategory("Resource location being registered");
            var7.addCrashSection("Resource location", p_110579_1_);
            var7.addCrashSectionCallable("Texture object class", new Callable() {
                private static final String __OBFID = "CL_00001065";
                
                @Override
                public String call() {
                    return p_110579_2_.getClass().getName();
                }
            });
            throw new ReportedException(var5);
        }
        this.mapTextureObjects.put(p_110579_1_, p_110579_2_2);
        return var3;
    }
    
    public ITextureObject getTexture(final ResourceLocation p_110581_1_) {
        return this.mapTextureObjects.get(p_110581_1_);
    }
    
    public ResourceLocation getDynamicTextureLocation(final String p_110578_1_, final DynamicTexture p_110578_2_) {
        Integer var3 = this.mapTextureCounters.get(p_110578_1_);
        if (var3 == null) {
            var3 = 1;
        }
        else {
            ++var3;
        }
        this.mapTextureCounters.put(p_110578_1_, var3);
        final ResourceLocation var4 = new ResourceLocation(String.format("dynamic/%s_%d", p_110578_1_, var3));
        this.loadTexture(var4, p_110578_2_);
        return var4;
    }
    
    @Override
    public void tick() {
        for (final ITickable var2 : this.listTickables) {
            var2.tick();
        }
    }
    
    public void func_147645_c(final ResourceLocation p_147645_1_) {
        final ITextureObject var2 = this.getTexture(p_147645_1_);
        if (var2 != null) {
            TextureUtil.deleteTexture(var2.getGlTextureId());
        }
    }
    
    @Override
    public void onResourceManagerReload(final IResourceManager p_110549_1_) {
        for (final Map.Entry var3 : this.mapTextureObjects.entrySet()) {
            this.loadTexture(var3.getKey(), var3.getValue());
        }
    }
    
    static {
        logger = LogManager.getLogger();
    }
}
