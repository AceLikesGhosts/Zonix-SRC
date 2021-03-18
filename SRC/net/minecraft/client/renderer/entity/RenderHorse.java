package net.minecraft.client.renderer.entity;

import java.util.*;
import net.minecraft.util.*;
import net.minecraft.client.model.*;
import net.minecraft.entity.passive.*;
import org.lwjgl.opengl.*;
import net.minecraft.entity.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.texture.*;
import com.google.common.collect.*;

public class RenderHorse extends RenderLiving
{
    private static final Map field_110852_a;
    private static final ResourceLocation whiteHorseTextures;
    private static final ResourceLocation muleTextures;
    private static final ResourceLocation donkeyTextures;
    private static final ResourceLocation zombieHorseTextures;
    private static final ResourceLocation skeletonHorseTextures;
    private static final String __OBFID = "CL_00001000";
    
    public RenderHorse(final ModelBase p_i1256_1_, final float p_i1256_2_) {
        super(p_i1256_1_, p_i1256_2_);
    }
    
    public void preRenderCallback(final EntityHorse p_77041_1_, final float p_77041_2_) {
        float var3 = 1.0f;
        final int var4 = p_77041_1_.getHorseType();
        if (var4 == 1) {
            var3 *= 0.87f;
        }
        else if (var4 == 2) {
            var3 *= 0.92f;
        }
        GL11.glScalef(var3, var3, var3);
        super.preRenderCallback(p_77041_1_, p_77041_2_);
    }
    
    protected void renderModel(final EntityHorse p_77036_1_, final float p_77036_2_, final float p_77036_3_, final float p_77036_4_, final float p_77036_5_, final float p_77036_6_, final float p_77036_7_) {
        if (p_77036_1_.isInvisible()) {
            this.mainModel.setRotationAngles(p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, p_77036_7_, p_77036_1_);
        }
        else {
            this.bindEntityTexture(p_77036_1_);
            this.mainModel.render(p_77036_1_, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, p_77036_7_);
        }
    }
    
    public ResourceLocation getEntityTexture(final EntityHorse p_110775_1_) {
        if (p_110775_1_.func_110239_cn()) {
            return this.func_110848_b(p_110775_1_);
        }
        switch (p_110775_1_.getHorseType()) {
            default: {
                return RenderHorse.whiteHorseTextures;
            }
            case 1: {
                return RenderHorse.donkeyTextures;
            }
            case 2: {
                return RenderHorse.muleTextures;
            }
            case 3: {
                return RenderHorse.zombieHorseTextures;
            }
            case 4: {
                return RenderHorse.skeletonHorseTextures;
            }
        }
    }
    
    private ResourceLocation func_110848_b(final EntityHorse p_110848_1_) {
        final String var2 = p_110848_1_.getHorseTexture();
        ResourceLocation var3 = RenderHorse.field_110852_a.get(var2);
        if (var3 == null) {
            var3 = new ResourceLocation(var2);
            Minecraft.getMinecraft().getTextureManager().loadTexture(var3, new LayeredTexture(p_110848_1_.getVariantTexturePaths()));
            RenderHorse.field_110852_a.put(var2, var3);
        }
        return var3;
    }
    
    @Override
    public void preRenderCallback(final EntityLivingBase p_77041_1_, final float p_77041_2_) {
        this.preRenderCallback((EntityHorse)p_77041_1_, p_77041_2_);
    }
    
    @Override
    protected void renderModel(final EntityLivingBase p_77036_1_, final float p_77036_2_, final float p_77036_3_, final float p_77036_4_, final float p_77036_5_, final float p_77036_6_, final float p_77036_7_) {
        this.renderModel((EntityHorse)p_77036_1_, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, p_77036_7_);
    }
    
    @Override
    public ResourceLocation getEntityTexture(final Entity p_110775_1_) {
        return this.getEntityTexture((EntityHorse)p_110775_1_);
    }
    
    static {
        field_110852_a = Maps.newHashMap();
        whiteHorseTextures = new ResourceLocation("textures/entity/horse/horse_white.png");
        muleTextures = new ResourceLocation("textures/entity/horse/mule.png");
        donkeyTextures = new ResourceLocation("textures/entity/horse/donkey.png");
        zombieHorseTextures = new ResourceLocation("textures/entity/horse/horse_zombie.png");
        skeletonHorseTextures = new ResourceLocation("textures/entity/horse/horse_skeleton.png");
    }
}
