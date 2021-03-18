package net.minecraft.client.renderer.texture;

public abstract class AbstractTexture implements ITextureObject
{
    protected int glTextureId;
    private static final String __OBFID = "CL_00001047";
    
    public AbstractTexture() {
        this.glTextureId = -1;
    }
    
    @Override
    public int getGlTextureId() {
        if (this.glTextureId == -1) {
            this.glTextureId = TextureUtil.glGenTextures();
        }
        return this.glTextureId;
    }
    
    public void func_147631_c() {
        if (this.glTextureId != -1) {
            TextureUtil.deleteTexture(this.glTextureId);
            this.glTextureId = -1;
        }
    }
}
