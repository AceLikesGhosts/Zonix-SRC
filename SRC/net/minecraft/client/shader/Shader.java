package net.minecraft.client.shader;

import javax.vecmath.*;
import net.minecraft.client.resources.*;
import com.google.common.collect.*;
import net.minecraft.client.util.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.*;
import java.util.*;

public class Shader
{
    private final ShaderManager manager;
    public final Framebuffer framebufferIn;
    public final Framebuffer framebufferOut;
    private final List listAuxFramebuffers;
    private final List listAuxNames;
    private final List listAuxWidths;
    private final List listAuxHeights;
    private Matrix4f projectionMatrix;
    private static final String __OBFID = "CL_00001042";
    
    public Shader(final IResourceManager p_i45089_1_, final String p_i45089_2_, final Framebuffer p_i45089_3_, final Framebuffer p_i45089_4_) throws JsonException {
        this.listAuxFramebuffers = Lists.newArrayList();
        this.listAuxNames = Lists.newArrayList();
        this.listAuxWidths = Lists.newArrayList();
        this.listAuxHeights = Lists.newArrayList();
        this.manager = new ShaderManager(p_i45089_1_, p_i45089_2_);
        this.framebufferIn = p_i45089_3_;
        this.framebufferOut = p_i45089_4_;
    }
    
    public void deleteShader() {
        this.manager.func_147988_a();
    }
    
    public void addAuxFramebuffer(final String p_148041_1_, final Object p_148041_2_, final int p_148041_3_, final int p_148041_4_) {
        this.listAuxNames.add(this.listAuxNames.size(), p_148041_1_);
        this.listAuxFramebuffers.add(this.listAuxFramebuffers.size(), p_148041_2_);
        this.listAuxWidths.add(this.listAuxWidths.size(), p_148041_3_);
        this.listAuxHeights.add(this.listAuxHeights.size(), p_148041_4_);
    }
    
    private void preLoadShader() {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glDisable(3042);
        GL11.glDisable(2929);
        GL11.glDisable(3008);
        GL11.glDisable(2912);
        GL11.glDisable(2896);
        GL11.glDisable(2903);
        GL11.glEnable(3553);
        GL11.glBindTexture(3553, 0);
    }
    
    public void setProjectionMatrix(final Matrix4f p_148045_1_) {
        this.projectionMatrix = p_148045_1_;
    }
    
    public void loadShader(final float p_148042_1_) {
        this.preLoadShader();
        this.framebufferIn.unbindFramebuffer();
        final float var2 = (float)this.framebufferOut.framebufferTextureWidth;
        final float var3 = (float)this.framebufferOut.framebufferTextureHeight;
        GL11.glViewport(0, 0, (int)var2, (int)var3);
        this.manager.func_147992_a("DiffuseSampler", this.framebufferIn);
        for (int var4 = 0; var4 < this.listAuxFramebuffers.size(); ++var4) {
            this.manager.func_147992_a(this.listAuxNames.get(var4), this.listAuxFramebuffers.get(var4));
            this.manager.func_147984_b("AuxSize" + var4).func_148087_a(this.listAuxWidths.get(var4), this.listAuxHeights.get(var4));
        }
        this.manager.func_147984_b("ProjMat").func_148088_a(this.projectionMatrix);
        this.manager.func_147984_b("InSize").func_148087_a((float)this.framebufferIn.framebufferTextureWidth, (float)this.framebufferIn.framebufferTextureHeight);
        this.manager.func_147984_b("OutSize").func_148087_a(var2, var3);
        this.manager.func_147984_b("Time").func_148090_a(p_148042_1_);
        final Minecraft var5 = Minecraft.getMinecraft();
        this.manager.func_147984_b("ScreenSize").func_148087_a((float)var5.displayWidth, (float)var5.displayHeight);
        this.manager.func_147995_c();
        this.framebufferOut.framebufferClear();
        this.framebufferOut.bindFramebuffer(false);
        GL11.glDepthMask(false);
        GL11.glColorMask(true, true, true, false);
        final Tessellator var6 = Tessellator.instance;
        var6.startDrawingQuads();
        var6.setColorOpaque_I(-1);
        var6.addVertex(0.0, var3, 500.0);
        var6.addVertex(var2, var3, 500.0);
        var6.addVertex(var2, 0.0, 500.0);
        var6.addVertex(0.0, 0.0, 500.0);
        var6.draw();
        GL11.glDepthMask(true);
        GL11.glColorMask(true, true, true, true);
        this.manager.func_147993_b();
        this.framebufferOut.unbindFramebuffer();
        this.framebufferIn.unbindFramebufferTexture();
        for (final Object var8 : this.listAuxFramebuffers) {
            if (var8 instanceof Framebuffer) {
                ((Framebuffer)var8).unbindFramebufferTexture();
            }
        }
    }
    
    public ShaderManager getShaderManager() {
        return this.manager;
    }
}
