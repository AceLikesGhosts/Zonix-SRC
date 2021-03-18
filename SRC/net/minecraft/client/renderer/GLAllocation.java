package net.minecraft.client.renderer;

import org.lwjgl.opengl.*;
import java.nio.*;
import java.util.*;

public class GLAllocation
{
    private static final Map mapDisplayLists;
    private static final List listDummy;
    private static final String __OBFID = "CL_00000630";
    
    public static synchronized int generateDisplayLists(final int p_74526_0_) {
        final int var1 = GL11.glGenLists(p_74526_0_);
        GLAllocation.mapDisplayLists.put(var1, p_74526_0_);
        return var1;
    }
    
    public static synchronized void deleteDisplayLists(final int p_74523_0_) {
        GL11.glDeleteLists(p_74523_0_, (int)GLAllocation.mapDisplayLists.remove(p_74523_0_));
    }
    
    public static synchronized void deleteTexturesAndDisplayLists() {
        for (final Map.Entry var2 : GLAllocation.mapDisplayLists.entrySet()) {
            GL11.glDeleteLists((int)var2.getKey(), (int)var2.getValue());
        }
        GLAllocation.mapDisplayLists.clear();
    }
    
    public static synchronized ByteBuffer createDirectByteBuffer(final int p_74524_0_) {
        return ByteBuffer.allocateDirect(p_74524_0_).order(ByteOrder.nativeOrder());
    }
    
    public static IntBuffer createDirectIntBuffer(final int p_74527_0_) {
        return createDirectByteBuffer(p_74527_0_ << 2).asIntBuffer();
    }
    
    public static FloatBuffer createDirectFloatBuffer(final int p_74529_0_) {
        return createDirectByteBuffer(p_74529_0_ << 2).asFloatBuffer();
    }
    
    static {
        mapDisplayLists = new HashMap();
        listDummy = new ArrayList();
    }
}
