package net.minecraft.client.model;

import java.util.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.*;
import net.minecraft.src.*;

public class ModelRenderer
{
    public float textureWidth;
    public float textureHeight;
    private int textureOffsetX;
    private int textureOffsetY;
    public float rotationPointX;
    public float rotationPointY;
    public float rotationPointZ;
    public float rotateAngleX;
    public float rotateAngleY;
    public float rotateAngleZ;
    private boolean compiled;
    private int displayList;
    public boolean mirror;
    public boolean showModel;
    public boolean isHidden;
    public List cubeList;
    public List childModels;
    public final String boxName;
    private ModelBase baseModel;
    public float offsetX;
    public float offsetY;
    public float offsetZ;
    public List spriteList;
    public boolean mirrorV;
    private static final String __OBFID = "CL_00000874";
    
    public ModelRenderer(final ModelBase p_i1172_1_, final String p_i1172_2_) {
        this.spriteList = new ArrayList();
        this.textureWidth = 64.0f;
        this.textureHeight = 32.0f;
        this.showModel = true;
        this.cubeList = new ArrayList();
        this.baseModel = p_i1172_1_;
        p_i1172_1_.boxList.add(this);
        this.boxName = p_i1172_2_;
        this.setTextureSize(p_i1172_1_.textureWidth, p_i1172_1_.textureHeight);
    }
    
    public ModelRenderer(final ModelBase p_i1173_1_) {
        this(p_i1173_1_, null);
    }
    
    public ModelRenderer(final ModelBase p_i46358_1_, final int p_i46358_2_, final int p_i46358_3_) {
        this(p_i46358_1_);
        this.setTextureOffset(p_i46358_2_, p_i46358_3_);
    }
    
    public void addChild(final ModelRenderer p_78792_1_) {
        if (this.childModels == null) {
            this.childModels = new ArrayList();
        }
        this.childModels.add(p_78792_1_);
    }
    
    public ModelRenderer setTextureOffset(final int p_78784_1_, final int p_78784_2_) {
        this.textureOffsetX = p_78784_1_;
        this.textureOffsetY = p_78784_2_;
        return this;
    }
    
    public ModelRenderer addBox(String p_78786_1_, final float p_78786_2_, final float p_78786_3_, final float p_78786_4_, final int p_78786_5_, final int p_78786_6_, final int p_78786_7_) {
        p_78786_1_ = this.boxName + "." + p_78786_1_;
        final TextureOffset var8 = this.baseModel.getTextureOffset(p_78786_1_);
        this.setTextureOffset(var8.textureOffsetX, var8.textureOffsetY);
        this.cubeList.add(new ModelBox(this, this.textureOffsetX, this.textureOffsetY, p_78786_2_, p_78786_3_, p_78786_4_, p_78786_5_, p_78786_6_, p_78786_7_, 0.0f).func_78244_a(p_78786_1_));
        return this;
    }
    
    public ModelRenderer addBox(final float p_78789_1_, final float p_78789_2_, final float p_78789_3_, final int p_78789_4_, final int p_78789_5_, final int p_78789_6_) {
        this.cubeList.add(new ModelBox(this, this.textureOffsetX, this.textureOffsetY, p_78789_1_, p_78789_2_, p_78789_3_, p_78789_4_, p_78789_5_, p_78789_6_, 0.0f));
        return this;
    }
    
    public void addBox(final float p_78790_1_, final float p_78790_2_, final float p_78790_3_, final int p_78790_4_, final int p_78790_5_, final int p_78790_6_, final float p_78790_7_) {
        this.cubeList.add(new ModelBox(this, this.textureOffsetX, this.textureOffsetY, p_78790_1_, p_78790_2_, p_78790_3_, p_78790_4_, p_78790_5_, p_78790_6_, p_78790_7_));
    }
    
    public void setRotationPoint(final float p_78793_1_, final float p_78793_2_, final float p_78793_3_) {
        this.rotationPointX = p_78793_1_;
        this.rotationPointY = p_78793_2_;
        this.rotationPointZ = p_78793_3_;
    }
    
    public void render(final float p_78785_1_) {
        if (!this.isHidden && this.showModel) {
            if (!this.compiled) {
                this.compileDisplayList(p_78785_1_);
            }
            GL11.glTranslatef(this.offsetX, this.offsetY, this.offsetZ);
            if (this.rotateAngleX == 0.0f && this.rotateAngleY == 0.0f && this.rotateAngleZ == 0.0f) {
                if (this.rotationPointX == 0.0f && this.rotationPointY == 0.0f && this.rotationPointZ == 0.0f) {
                    GL11.glCallList(this.displayList);
                    if (this.childModels != null) {
                        for (int var2 = 0; var2 < this.childModels.size(); ++var2) {
                            this.childModels.get(var2).render(p_78785_1_);
                        }
                    }
                }
                else {
                    GL11.glTranslatef(this.rotationPointX * p_78785_1_, this.rotationPointY * p_78785_1_, this.rotationPointZ * p_78785_1_);
                    GL11.glCallList(this.displayList);
                    if (this.childModels != null) {
                        for (int var2 = 0; var2 < this.childModels.size(); ++var2) {
                            this.childModels.get(var2).render(p_78785_1_);
                        }
                    }
                    GL11.glTranslatef(-this.rotationPointX * p_78785_1_, -this.rotationPointY * p_78785_1_, -this.rotationPointZ * p_78785_1_);
                }
            }
            else {
                GL11.glPushMatrix();
                GL11.glTranslatef(this.rotationPointX * p_78785_1_, this.rotationPointY * p_78785_1_, this.rotationPointZ * p_78785_1_);
                if (this.rotateAngleZ != 0.0f) {
                    GL11.glRotatef(this.rotateAngleZ * 57.295776f, 0.0f, 0.0f, 1.0f);
                }
                if (this.rotateAngleY != 0.0f) {
                    GL11.glRotatef(this.rotateAngleY * 57.295776f, 0.0f, 1.0f, 0.0f);
                }
                if (this.rotateAngleX != 0.0f) {
                    GL11.glRotatef(this.rotateAngleX * 57.295776f, 1.0f, 0.0f, 0.0f);
                }
                GL11.glCallList(this.displayList);
                if (this.childModels != null) {
                    for (int var2 = 0; var2 < this.childModels.size(); ++var2) {
                        this.childModels.get(var2).render(p_78785_1_);
                    }
                }
                GL11.glPopMatrix();
            }
            GL11.glTranslatef(-this.offsetX, -this.offsetY, -this.offsetZ);
        }
    }
    
    public void renderWithRotation(final float p_78791_1_) {
        if (!this.isHidden && this.showModel) {
            if (!this.compiled) {
                this.compileDisplayList(p_78791_1_);
            }
            GL11.glPushMatrix();
            GL11.glTranslatef(this.rotationPointX * p_78791_1_, this.rotationPointY * p_78791_1_, this.rotationPointZ * p_78791_1_);
            if (this.rotateAngleY != 0.0f) {
                GL11.glRotatef(this.rotateAngleY * 57.295776f, 0.0f, 1.0f, 0.0f);
            }
            if (this.rotateAngleX != 0.0f) {
                GL11.glRotatef(this.rotateAngleX * 57.295776f, 1.0f, 0.0f, 0.0f);
            }
            if (this.rotateAngleZ != 0.0f) {
                GL11.glRotatef(this.rotateAngleZ * 57.295776f, 0.0f, 0.0f, 1.0f);
            }
            GL11.glCallList(this.displayList);
            GL11.glPopMatrix();
        }
    }
    
    public void postRender(final float p_78794_1_) {
        if (!this.isHidden && this.showModel) {
            if (!this.compiled) {
                this.compileDisplayList(p_78794_1_);
            }
            if (this.rotateAngleX == 0.0f && this.rotateAngleY == 0.0f && this.rotateAngleZ == 0.0f) {
                if (this.rotationPointX != 0.0f || this.rotationPointY != 0.0f || this.rotationPointZ != 0.0f) {
                    GL11.glTranslatef(this.rotationPointX * p_78794_1_, this.rotationPointY * p_78794_1_, this.rotationPointZ * p_78794_1_);
                }
            }
            else {
                GL11.glTranslatef(this.rotationPointX * p_78794_1_, this.rotationPointY * p_78794_1_, this.rotationPointZ * p_78794_1_);
                if (this.rotateAngleZ != 0.0f) {
                    GL11.glRotatef(this.rotateAngleZ * 57.295776f, 0.0f, 0.0f, 1.0f);
                }
                if (this.rotateAngleY != 0.0f) {
                    GL11.glRotatef(this.rotateAngleY * 57.295776f, 0.0f, 1.0f, 0.0f);
                }
                if (this.rotateAngleX != 0.0f) {
                    GL11.glRotatef(this.rotateAngleX * 57.295776f, 1.0f, 0.0f, 0.0f);
                }
            }
        }
    }
    
    private void compileDisplayList(final float p_78788_1_) {
        GL11.glNewList(this.displayList = GLAllocation.generateDisplayLists(1), 4864);
        final Tessellator var2 = Tessellator.instance;
        for (int var3 = 0; var3 < this.cubeList.size(); ++var3) {
            this.cubeList.get(var3).render(var2, p_78788_1_);
        }
        for (int i = 0; i < this.spriteList.size(); ++i) {
            final ModelSprite sprite = this.spriteList.get(i);
            sprite.render(Tessellator.instance, p_78788_1_);
        }
        GL11.glEndList();
        this.compiled = true;
    }
    
    public ModelRenderer setTextureSize(final int p_78787_1_, final int p_78787_2_) {
        this.textureWidth = (float)p_78787_1_;
        this.textureHeight = (float)p_78787_2_;
        return this;
    }
    
    public void addSprite(final float posX, final float posY, final float posZ, final int sizeX, final int sizeY, final int sizeZ, final float sizeAdd) {
        this.spriteList.add(new ModelSprite(this, this.textureOffsetX, this.textureOffsetY, posX, posY, posZ, sizeX, sizeY, sizeZ, sizeAdd));
    }
}
