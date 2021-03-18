package net.minecraft.client.shader;

import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.texture.*;
import java.nio.*;
import net.minecraft.client.renderer.*;

public class Framebuffer
{
    public int framebufferTextureWidth;
    public int framebufferTextureHeight;
    public int framebufferWidth;
    public int framebufferHeight;
    public boolean useDepth;
    public int framebufferObject;
    public int framebufferTexture;
    public int depthBuffer;
    public float[] framebufferColor;
    public int framebufferFilter;
    private static final String __OBFID = "CL_00000959";
    
    public Framebuffer(final int p_i45078_1_, final int p_i45078_2_, final boolean p_i45078_3_) {
        this.useDepth = p_i45078_3_;
        this.framebufferObject = -1;
        this.framebufferTexture = -1;
        this.depthBuffer = -1;
        (this.framebufferColor = new float[4])[0] = 1.0f;
        this.framebufferColor[1] = 1.0f;
        this.framebufferColor[2] = 1.0f;
        this.framebufferColor[3] = 0.0f;
        this.createBindFramebuffer(p_i45078_1_, p_i45078_2_);
    }
    
    public void createBindFramebuffer(final int p_147613_1_, final int p_147613_2_) {
        if (!OpenGlHelper.isFramebufferEnabled()) {
            this.framebufferWidth = p_147613_1_;
            this.framebufferHeight = p_147613_2_;
        }
        else {
            GL11.glEnable(2929);
            if (this.framebufferObject >= 0) {
                this.deleteFramebuffer();
            }
            this.createFramebuffer(p_147613_1_, p_147613_2_);
            this.checkFramebufferComplete();
            OpenGlHelper.func_153171_g(OpenGlHelper.field_153198_e, 0);
        }
    }
    
    public void deleteFramebuffer() {
        if (OpenGlHelper.isFramebufferEnabled()) {
            this.unbindFramebufferTexture();
            this.unbindFramebuffer();
            if (this.depthBuffer > -1) {
                OpenGlHelper.func_153184_g(this.depthBuffer);
                this.depthBuffer = -1;
            }
            if (this.framebufferTexture > -1) {
                TextureUtil.deleteTexture(this.framebufferTexture);
                this.framebufferTexture = -1;
            }
            if (this.framebufferObject > -1) {
                OpenGlHelper.func_153171_g(OpenGlHelper.field_153198_e, 0);
                OpenGlHelper.func_153174_h(this.framebufferObject);
                this.framebufferObject = -1;
            }
        }
    }
    
    public void createFramebuffer(final int p_147605_1_, final int p_147605_2_) {
        this.framebufferWidth = p_147605_1_;
        this.framebufferHeight = p_147605_2_;
        this.framebufferTextureWidth = p_147605_1_;
        this.framebufferTextureHeight = p_147605_2_;
        if (!OpenGlHelper.isFramebufferEnabled()) {
            this.framebufferClear();
        }
        else {
            this.framebufferObject = OpenGlHelper.func_153165_e();
            this.framebufferTexture = TextureUtil.glGenTextures();
            if (this.useDepth) {
                this.depthBuffer = OpenGlHelper.func_153185_f();
            }
            this.setFramebufferFilter(9728);
            GL11.glBindTexture(3553, this.framebufferTexture);
            GL11.glTexImage2D(3553, 0, 32856, this.framebufferTextureWidth, this.framebufferTextureHeight, 0, 6408, 5121, (ByteBuffer)null);
            OpenGlHelper.func_153171_g(OpenGlHelper.field_153198_e, this.framebufferObject);
            OpenGlHelper.func_153188_a(OpenGlHelper.field_153198_e, OpenGlHelper.field_153200_g, 3553, this.framebufferTexture, 0);
            if (this.useDepth) {
                OpenGlHelper.func_153176_h(OpenGlHelper.field_153199_f, this.depthBuffer);
                OpenGlHelper.func_153186_a(OpenGlHelper.field_153199_f, 33190, this.framebufferTextureWidth, this.framebufferTextureHeight);
                OpenGlHelper.func_153190_b(OpenGlHelper.field_153198_e, OpenGlHelper.field_153201_h, OpenGlHelper.field_153199_f, this.depthBuffer);
            }
            this.framebufferClear();
            this.unbindFramebufferTexture();
        }
    }
    
    public void setFramebufferFilter(final int p_147607_1_) {
        if (OpenGlHelper.isFramebufferEnabled()) {
            this.framebufferFilter = p_147607_1_;
            GL11.glBindTexture(3553, this.framebufferTexture);
            GL11.glTexParameterf(3553, 10241, (float)p_147607_1_);
            GL11.glTexParameterf(3553, 10240, (float)p_147607_1_);
            GL11.glTexParameterf(3553, 10242, 10496.0f);
            GL11.glTexParameterf(3553, 10243, 10496.0f);
            GL11.glBindTexture(3553, 0);
        }
    }
    
    public void checkFramebufferComplete() {
        final int var1 = OpenGlHelper.func_153167_i(OpenGlHelper.field_153198_e);
        if (var1 == OpenGlHelper.field_153202_i) {
            return;
        }
        if (var1 == OpenGlHelper.field_153203_j) {
            throw new RuntimeException("GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT");
        }
        if (var1 == OpenGlHelper.field_153204_k) {
            throw new RuntimeException("GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT");
        }
        if (var1 == OpenGlHelper.field_153205_l) {
            throw new RuntimeException("GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER");
        }
        if (var1 == OpenGlHelper.field_153206_m) {
            throw new RuntimeException("GL_FRAMEBUFFER_INCOMPLETE_READ_BUFFER");
        }
        throw new RuntimeException("glCheckFramebufferStatus returned unknown status:" + var1);
    }
    
    public void bindFramebufferTexture() {
        if (OpenGlHelper.isFramebufferEnabled()) {
            GL11.glBindTexture(3553, this.framebufferTexture);
        }
    }
    
    public void unbindFramebufferTexture() {
        if (OpenGlHelper.isFramebufferEnabled()) {
            GL11.glBindTexture(3553, 0);
        }
    }
    
    public void bindFramebuffer(final boolean p_147610_1_) {
        if (OpenGlHelper.isFramebufferEnabled()) {
            OpenGlHelper.func_153171_g(OpenGlHelper.field_153198_e, this.framebufferObject);
            if (p_147610_1_) {
                GL11.glViewport(0, 0, this.framebufferWidth, this.framebufferHeight);
            }
        }
    }
    
    public void unbindFramebuffer() {
        if (OpenGlHelper.isFramebufferEnabled()) {
            OpenGlHelper.func_153171_g(OpenGlHelper.field_153198_e, 0);
        }
    }
    
    public void setFramebufferColor(final float p_147604_1_, final float p_147604_2_, final float p_147604_3_, final float p_147604_4_) {
        this.framebufferColor[0] = p_147604_1_;
        this.framebufferColor[1] = p_147604_2_;
        this.framebufferColor[2] = p_147604_3_;
        this.framebufferColor[3] = p_147604_4_;
    }
    
    public void framebufferRender(final int p_147615_1_, final int p_147615_2_) {
        if (OpenGlHelper.isFramebufferEnabled()) {
            GL11.glColorMask(true, true, true, false);
            GL11.glDisable(2929);
            GL11.glDepthMask(false);
            GL11.glMatrixMode(5889);
            GL11.glLoadIdentity();
            GL11.glOrtho(0.0, (double)p_147615_1_, (double)p_147615_2_, 0.0, 1000.0, 3000.0);
            GL11.glMatrixMode(5888);
            GL11.glLoadIdentity();
            GL11.glTranslatef(0.0f, 0.0f, -2000.0f);
            GL11.glViewport(0, 0, p_147615_1_, p_147615_2_);
            GL11.glEnable(3553);
            GL11.glDisable(2896);
            GL11.glDisable(3008);
            GL11.glDisable(3042);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            GL11.glEnable(2903);
            this.bindFramebufferTexture();
            final float var3 = (float)p_147615_1_;
            final float var4 = (float)p_147615_2_;
            final float var5 = this.framebufferWidth / (float)this.framebufferTextureWidth;
            final float var6 = this.framebufferHeight / (float)this.framebufferTextureHeight;
            final Tessellator var7 = Tessellator.instance;
            var7.startDrawingQuads();
            var7.setColorOpaque_I(-1);
            var7.addVertexWithUV(0.0, var4, 0.0, 0.0, 0.0);
            var7.addVertexWithUV(var3, var4, 0.0, var5, 0.0);
            var7.addVertexWithUV(var3, 0.0, 0.0, var5, var6);
            var7.addVertexWithUV(0.0, 0.0, 0.0, 0.0, var6);
            var7.draw();
            this.unbindFramebufferTexture();
            GL11.glDepthMask(true);
            GL11.glColorMask(true, true, true, true);
        }
    }
    
    public void framebufferClear() {
        this.bindFramebuffer(true);
        GL11.glClearColor(this.framebufferColor[0], this.framebufferColor[1], this.framebufferColor[2], this.framebufferColor[3]);
        int var1 = 16384;
        if (this.useDepth) {
            GL11.glClearDepth(1.0);
            var1 |= 0x100;
        }
        GL11.glClear(var1);
        this.unbindFramebuffer();
    }
}
