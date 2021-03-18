package net.minecraft.potion;

public class PotionHealth extends Potion
{
    private static final String __OBFID = "CL_00001527";
    
    public PotionHealth(final int p_i1572_1_, final boolean p_i1572_2_, final int p_i1572_3_) {
        super(p_i1572_1_, p_i1572_2_, p_i1572_3_);
    }
    
    @Override
    public boolean isInstant() {
        return true;
    }
    
    @Override
    public boolean isReady(final int p_76397_1_, final int p_76397_2_) {
        return p_76397_1_ >= 1;
    }
}
