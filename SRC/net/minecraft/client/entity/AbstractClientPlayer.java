package net.minecraft.client.entity;

import net.minecraft.entity.player.*;
import net.minecraft.client.resources.*;
import net.minecraft.world.*;
import com.mojang.authlib.*;
import net.minecraft.client.*;
import net.minecraft.util.*;
import java.io.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.texture.*;
import com.mojang.authlib.minecraft.*;

public abstract class AbstractClientPlayer extends EntityPlayer implements SkinManager.SkinAvailableCallback
{
    public static final ResourceLocation locationStevePng;
    private ResourceLocation locationOfCape;
    private ResourceLocation locationSkin;
    private ResourceLocation locationCape;
    private static final String __OBFID = "CL_00000935";
    private String nameNonControl;
    
    public AbstractClientPlayer(final World p_i45074_1_, final GameProfile p_i45074_2_) {
        super(p_i45074_1_, p_i45074_2_);
        final String var3 = this.getCommandSenderName();
        if (!var3.isEmpty()) {
            final SkinManager var4 = Minecraft.getMinecraft().func_152342_ad();
            var4.func_152790_a(p_i45074_2_, this, true);
        }
        this.nameNonControl = p_i45074_2_.getName();
        if (this.nameNonControl != null && !this.nameNonControl.isEmpty()) {
            this.nameNonControl = StringUtils.stripControlCodes(this.nameNonControl);
        }
    }
    
    public String getNameClear() {
        return this.nameNonControl;
    }
    
    public ResourceLocation getLocationOfCape() {
        return this.locationOfCape;
    }
    
    public void setLocationOfCape(final ResourceLocation locationOfCape) {
        this.locationOfCape = locationOfCape;
    }
    
    public boolean func_152122_n() {
        return this.locationCape != null;
    }
    
    public boolean func_152123_o() {
        return this.locationSkin != null;
    }
    
    public ResourceLocation getLocationSkin() {
        return (this.locationSkin == null) ? AbstractClientPlayer.locationStevePng : this.locationSkin;
    }
    
    public ResourceLocation getLocationCape() {
        return this.locationCape;
    }
    
    public static ThreadDownloadImageData getDownloadImageSkin(final ResourceLocation p_110304_0_, final String p_110304_1_) {
        final TextureManager var2 = Minecraft.getMinecraft().getTextureManager();
        Object var3 = var2.getTexture(p_110304_0_);
        if (var3 == null) {
            var3 = new ThreadDownloadImageData(null, String.format("http://skins.minecraft.net/MinecraftSkins/%s.png", StringUtils.stripControlCodes(p_110304_1_)), AbstractClientPlayer.locationStevePng, new ImageBufferDownload());
            var2.loadTexture(p_110304_0_, (ITextureObject)var3);
        }
        return (ThreadDownloadImageData)var3;
    }
    
    public static ResourceLocation getLocationSkin(final String p_110311_0_) {
        return new ResourceLocation("skins/" + StringUtils.stripControlCodes(p_110311_0_));
    }
    
    @Override
    public void func_152121_a(final MinecraftProfileTexture.Type p_152121_1_, final ResourceLocation p_152121_2_) {
        switch (SwitchType.field_152630_a[p_152121_1_.ordinal()]) {
            case 1: {
                this.locationSkin = p_152121_2_;
                break;
            }
            case 2: {
                this.locationCape = p_152121_2_;
                break;
            }
        }
    }
    
    static {
        locationStevePng = new ResourceLocation("textures/entity/steve.png");
    }
    
    static final class SwitchType
    {
        static final int[] field_152630_a;
        private static final String __OBFID = "CL_00001832";
        
        static {
            field_152630_a = new int[MinecraftProfileTexture.Type.values().length];
            try {
                SwitchType.field_152630_a[MinecraftProfileTexture.Type.SKIN.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                SwitchType.field_152630_a[MinecraftProfileTexture.Type.CAPE.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
        }
    }
}
