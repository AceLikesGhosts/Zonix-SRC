package net.minecraft.client.renderer;

import java.nio.*;
import org.lwjgl.opengl.*;

public class RenderList
{
    public int renderChunkX;
    public int renderChunkY;
    public int renderChunkZ;
    private double cameraX;
    private double cameraY;
    private double cameraZ;
    private IntBuffer glLists;
    private boolean valid;
    private boolean bufferFlipped;
    private static final String __OBFID = "CL_00000957";
    
    public RenderList() {
        this.glLists = GLAllocation.createDirectIntBuffer(65536);
    }
    
    public void setupRenderList(final int p_78422_1_, final int p_78422_2_, final int p_78422_3_, final double p_78422_4_, final double p_78422_6_, final double p_78422_8_) {
        this.valid = true;
        this.glLists.clear();
        this.renderChunkX = p_78422_1_;
        this.renderChunkY = p_78422_2_;
        this.renderChunkZ = p_78422_3_;
        this.cameraX = p_78422_4_;
        this.cameraY = p_78422_6_;
        this.cameraZ = p_78422_8_;
    }
    
    public boolean rendersChunk(final int p_78418_1_, final int p_78418_2_, final int p_78418_3_) {
        return this.valid && (p_78418_1_ == this.renderChunkX && p_78418_2_ == this.renderChunkY && p_78418_3_ == this.renderChunkZ);
    }
    
    public void addGLRenderList(final int p_78420_1_) {
        this.glLists.put(p_78420_1_);
        if (this.glLists.remaining() == 0) {
            this.callLists();
        }
    }
    
    public void callLists() {
        if (this.valid) {
            if (!this.bufferFlipped) {
                this.glLists.flip();
                this.bufferFlipped = true;
            }
            if (this.glLists.remaining() > 0) {
                GL11.glPushMatrix();
                GL11.glTranslatef((float)(this.renderChunkX - this.cameraX), (float)(this.renderChunkY - this.cameraY), (float)(this.renderChunkZ - this.cameraZ));
                GL11.glCallLists(this.glLists);
                GL11.glPopMatrix();
            }
        }
    }
    
    public void resetList() {
        this.valid = false;
        this.bufferFlipped = false;
    }
}
