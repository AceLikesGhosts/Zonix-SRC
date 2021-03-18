package net.minecraft.src;

public class WrUpdateControl implements IWrUpdateControl
{
    private int renderPass;
    
    public WrUpdateControl() {
        this.renderPass = 0;
    }
    
    @Override
    public void resume() {
    }
    
    @Override
    public void pause() {
    }
    
    public void setRenderPass(final int renderPass) {
        this.renderPass = renderPass;
    }
}
