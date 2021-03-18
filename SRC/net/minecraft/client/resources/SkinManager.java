package net.minecraft.client.resources;

import net.minecraft.util.*;
import java.io.*;
import com.google.common.cache.*;
import com.mojang.authlib.*;
import net.minecraft.client.*;
import java.awt.image.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.texture.*;
import com.google.common.collect.*;
import com.mojang.authlib.minecraft.*;
import java.util.*;
import java.util.concurrent.*;

public class SkinManager
{
    public static final ResourceLocation field_152793_a;
    private static final ExecutorService field_152794_b;
    private final TextureManager field_152795_c;
    private final File field_152796_d;
    private final MinecraftSessionService field_152797_e;
    private final LoadingCache field_152798_f;
    private static final String __OBFID = "CL_00001830";
    
    public SkinManager(final TextureManager p_i1044_1_, final File p_i1044_2_, final MinecraftSessionService p_i1044_3_) {
        this.field_152795_c = p_i1044_1_;
        this.field_152796_d = p_i1044_2_;
        this.field_152797_e = p_i1044_3_;
        this.field_152798_f = CacheBuilder.newBuilder().expireAfterAccess(15L, TimeUnit.SECONDS).build((CacheLoader)new CacheLoader() {
            private static final String __OBFID = "CL_00001829";
            
            public Map func_152786_a(final GameProfile p_152786_1_) {
                return Minecraft.getMinecraft().func_152347_ac().getTextures(p_152786_1_, false);
            }
            
            public Object load(final Object p_load_1_) {
                return this.func_152786_a((GameProfile)p_load_1_);
            }
        });
    }
    
    public ResourceLocation func_152792_a(final MinecraftProfileTexture p_152792_1_, final MinecraftProfileTexture.Type p_152792_2_) {
        return this.func_152789_a(p_152792_1_, p_152792_2_, null);
    }
    
    public ResourceLocation func_152789_a(final MinecraftProfileTexture p_152789_1_, final MinecraftProfileTexture.Type p_152789_2_, final SkinAvailableCallback p_152789_3_) {
        final ResourceLocation var4 = new ResourceLocation("skins/" + p_152789_1_.getHash());
        final ITextureObject var5 = this.field_152795_c.getTexture(var4);
        if (var5 != null) {
            if (p_152789_3_ != null) {
                p_152789_3_.func_152121_a(p_152789_2_, var4);
            }
        }
        else {
            final File var6 = new File(this.field_152796_d, p_152789_1_.getHash().substring(0, 2));
            final File var7 = new File(var6, p_152789_1_.getHash());
            final ImageBufferDownload var8 = (p_152789_2_ == MinecraftProfileTexture.Type.SKIN) ? new ImageBufferDownload() : null;
            final ThreadDownloadImageData var9 = new ThreadDownloadImageData(var7, p_152789_1_.getUrl(), SkinManager.field_152793_a, new IImageBuffer() {
                private static final String __OBFID = "CL_00001828";
                
                @Override
                public BufferedImage parseUserSkin(BufferedImage p_78432_1_) {
                    if (var8 != null) {
                        p_78432_1_ = var8.parseUserSkin(p_78432_1_);
                    }
                    return p_78432_1_;
                }
                
                @Override
                public void func_152634_a() {
                    if (var8 != null) {
                        var8.func_152634_a();
                    }
                    if (p_152789_3_ != null) {
                        p_152789_3_.func_152121_a(p_152789_2_, var4);
                    }
                }
            });
            this.field_152795_c.loadTexture(var4, var9);
        }
        return var4;
    }
    
    public void func_152790_a(final GameProfile p_152790_1_, final SkinAvailableCallback p_152790_2_, final boolean p_152790_3_) {
        SkinManager.field_152794_b.submit(new Runnable() {
            private static final String __OBFID = "CL_00001827";
            
            @Override
            public void run() {
                final HashMap var1 = Maps.newHashMap();
                try {
                    var1.putAll(SkinManager.this.field_152797_e.getTextures(p_152790_1_, p_152790_3_));
                }
                catch (InsecureTextureException ex) {}
                if (var1.isEmpty() && p_152790_1_.getId().equals(Minecraft.getMinecraft().getSession().func_148256_e().getId())) {
                    var1.putAll(SkinManager.this.field_152797_e.getTextures(SkinManager.this.field_152797_e.fillProfileProperties(p_152790_1_, false), false));
                }
                Minecraft.getMinecraft().func_152344_a(new Runnable() {
                    private static final String __OBFID = "CL_00001826";
                    
                    @Override
                    public void run() {
                        if (var1.containsKey(MinecraftProfileTexture.Type.SKIN)) {
                            SkinManager.this.func_152789_a(var1.get(MinecraftProfileTexture.Type.SKIN), MinecraftProfileTexture.Type.SKIN, p_152790_2_);
                        }
                        if (var1.containsKey(MinecraftProfileTexture.Type.CAPE)) {
                            SkinManager.this.func_152789_a(var1.get(MinecraftProfileTexture.Type.CAPE), MinecraftProfileTexture.Type.CAPE, p_152790_2_);
                        }
                    }
                });
            }
        });
    }
    
    public Map func_152788_a(final GameProfile p_152788_1_) {
        return (Map)this.field_152798_f.getUnchecked((Object)p_152788_1_);
    }
    
    static {
        field_152793_a = new ResourceLocation("textures/entity/steve.png");
        field_152794_b = new ThreadPoolExecutor(0, 2, 1L, TimeUnit.MINUTES, new LinkedBlockingQueue<Runnable>());
    }
    
    public interface SkinAvailableCallback
    {
        void func_152121_a(final MinecraftProfileTexture.Type p0, final ResourceLocation p1);
    }
}
