package net.minecraft.client.shader;

public class TesselatorVertexState
{
    private int[] rawBuffer;
    private int rawBufferIndex;
    private int vertexCount;
    private boolean hasTexture;
    private boolean hasBrightness;
    private boolean hasNormals;
    private boolean hasColor;
    
    public TesselatorVertexState(final int[] p_i45079_1_, final int p_i45079_2_, final int p_i45079_3_, final boolean p_i45079_4_, final boolean p_i45079_5_, final boolean p_i45079_6_, final boolean p_i45079_7_) {
        this.rawBuffer = p_i45079_1_;
        this.rawBufferIndex = p_i45079_2_;
        this.vertexCount = p_i45079_3_;
        this.hasTexture = p_i45079_4_;
        this.hasBrightness = p_i45079_5_;
        this.hasNormals = p_i45079_6_;
        this.hasColor = p_i45079_7_;
    }
    
    public int[] getRawBuffer() {
        return this.rawBuffer;
    }
    
    public int getRawBufferIndex() {
        return this.rawBufferIndex;
    }
    
    public int getVertexCount() {
        return this.vertexCount;
    }
    
    public boolean getHasTexture() {
        return this.hasTexture;
    }
    
    public boolean getHasBrightness() {
        return this.hasBrightness;
    }
    
    public boolean getHasNormals() {
        return this.hasNormals;
    }
    
    public boolean getHasColor() {
        return this.hasColor;
    }
    
    public void addTessellatorVertexState(final TesselatorVertexState tsv) {
        if (tsv != null) {
            if (tsv.hasBrightness != this.hasBrightness || tsv.hasColor != this.hasColor || tsv.hasNormals != this.hasNormals || tsv.hasTexture != this.hasTexture) {
                throw new IllegalArgumentException("Incompatible vertex states");
            }
            final int newRawBufferIndex = this.rawBufferIndex + tsv.rawBufferIndex;
            final int[] newRawBuffer = new int[newRawBufferIndex];
            System.arraycopy(this.rawBuffer, 0, newRawBuffer, 0, this.rawBufferIndex);
            System.arraycopy(tsv.rawBuffer, 0, newRawBuffer, this.rawBufferIndex, tsv.rawBufferIndex);
            this.rawBuffer = newRawBuffer;
            this.rawBufferIndex = newRawBufferIndex;
            this.vertexCount += tsv.vertexCount;
        }
    }
}
