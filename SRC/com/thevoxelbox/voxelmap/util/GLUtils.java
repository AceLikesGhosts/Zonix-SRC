package com.thevoxelbox.voxelmap.util;

import net.minecraft.client.renderer.texture.*;
import org.lwjgl.*;
import java.nio.*;
import java.awt.image.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.*;
import org.lwjgl.opengl.*;

public class GLUtils
{
    private static final IntBuffer dataBuffer;
    public static TextureManager textureManager;
    public static int fboID;
    public static boolean fboEnabled;
    public static int fboTextureID;
    public static boolean hasAlphaBits;
    private static Tessellator tesselator;
    private static int previousFBOID;
    
    public static void setupFBO() {
        GLUtils.previousFBOID = GL11.glGetInteger(36006);
        GLUtils.fboID = EXTFramebufferObject.glGenFramebuffersEXT();
        GLUtils.fboTextureID = GL11.glGenTextures();
        final int width = 256;
        final int height = 256;
        EXTFramebufferObject.glBindFramebufferEXT(36160, GLUtils.fboID);
        final ByteBuffer byteBuffer = BufferUtils.createByteBuffer(4 * width * height);
        GL11.glBindTexture(3553, GLUtils.fboTextureID);
        GL11.glTexParameteri(3553, 10242, 10496);
        GL11.glTexParameteri(3553, 10243, 10496);
        GL11.glTexParameteri(3553, 10241, 9729);
        GL11.glTexParameteri(3553, 10240, 9729);
        GL11.glTexImage2D(3553, 0, 6408, width, height, 0, 6408, 5120, byteBuffer);
        EXTFramebufferObject.glFramebufferTexture2DEXT(36160, 36064, 3553, GLUtils.fboTextureID, 0);
        final int depthRenderBufferID = EXTFramebufferObject.glGenRenderbuffersEXT();
        EXTFramebufferObject.glBindRenderbufferEXT(36161, depthRenderBufferID);
        EXTFramebufferObject.glRenderbufferStorageEXT(36161, 33190, 256, 256);
        EXTFramebufferObject.glFramebufferRenderbufferEXT(36160, 36096, 36161, depthRenderBufferID);
        EXTFramebufferObject.glBindRenderbufferEXT(36161, 0);
        EXTFramebufferObject.glBindFramebufferEXT(36160, GLUtils.previousFBOID);
    }
    
    public static void bindFrameBuffer() {
        GLUtils.previousFBOID = GL11.glGetInteger(36006);
        EXTFramebufferObject.glBindFramebufferEXT(36160, GLUtils.fboID);
    }
    
    public static void unbindFrameBuffer() {
        EXTFramebufferObject.glBindFramebufferEXT(36160, GLUtils.previousFBOID);
    }
    
    public static void setMap(final int x, final int y) {
        setMap(x, (float)y, 128);
    }
    
    public static void setMap(final int x, final float y, final int imageSize) {
        final int scale = imageSize / 4;
        ldrawthree(x - scale, y + scale, 1.0, 0.0, 1.0);
        ldrawthree(x + scale, y + scale, 1.0, 1.0, 1.0);
        ldrawthree(x + scale, y - scale, 1.0, 1.0, 0.0);
        ldrawthree(x - scale, y - scale, 1.0, 0.0, 0.0);
    }
    
    public static int tex(final BufferedImage paramImg) {
        final int glid = GL11.glGenTextures();
        final int width = paramImg.getWidth();
        final int height = paramImg.getHeight();
        final int[] imageData = new int[width * height];
        paramImg.getRGB(0, 0, width, height, imageData, 0, width);
        GL11.glBindTexture(3553, glid);
        GLUtils.dataBuffer.clear();
        GLUtils.dataBuffer.put(imageData, 0, width * height);
        GLUtils.dataBuffer.position(0).limit(width * height);
        GL11.glTexImage2D(3553, 0, 6408, width, height, 0, 32993, 33639, GLUtils.dataBuffer);
        return glid;
    }
    
    public static void img(final String paramStr) {
        GLUtils.textureManager.bindTexture(new ResourceLocation(paramStr));
    }
    
    public static void img(final ResourceLocation paramResourceLocation) {
        GLUtils.textureManager.bindTexture(paramResourceLocation);
    }
    
    public static void disp(final int paramInt) {
        GL11.glBindTexture(3553, paramInt);
    }
    
    public static void drawPre() {
        GLUtils.tesselator.startDrawingQuads();
    }
    
    public static void drawPost() {
        GLUtils.tesselator.draw();
    }
    
    public static void glah(final int g) {
        GL11.glDeleteTextures(g);
    }
    
    public static void ldrawone(final int a, final int b, final double c, final double d, final double e) {
        GLUtils.tesselator.addVertexWithUV(a, b, c, d, e);
    }
    
    public static void ldrawtwo(final double a, final double b, final double c) {
        GLUtils.tesselator.addVertex(a, b, c);
    }
    
    public static void ldrawthree(final double a, final double b, final double c, final double d, final double e) {
        GLUtils.tesselator.addVertexWithUV(a, b, c, d, e);
    }
    
    static {
        dataBuffer = GLAllocation.createDirectIntBuffer(4194304);
        GLUtils.fboID = 0;
        GLUtils.fboEnabled = (GLContext.getCapabilities().GL_EXT_framebuffer_object && GLContext.getCapabilities().OpenGL14);
        GLUtils.fboTextureID = 0;
        GLUtils.hasAlphaBits = (GL11.glGetInteger(3413) > 0);
        GLUtils.tesselator = Tessellator.instance;
        GLUtils.previousFBOID = 0;
    }
}
