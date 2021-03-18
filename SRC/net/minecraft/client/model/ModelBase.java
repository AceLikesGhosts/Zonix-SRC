package net.minecraft.client.model;

import net.minecraft.entity.*;
import java.util.*;

public abstract class ModelBase
{
    public float onGround;
    public boolean isRiding;
    public List boxList;
    public boolean isChild;
    private Map modelTextureMap;
    public int textureWidth;
    public int textureHeight;
    private static final String __OBFID = "CL_00000845";
    
    public ModelBase() {
        this.boxList = new ArrayList();
        this.isChild = true;
        this.modelTextureMap = new HashMap();
        this.textureWidth = 64;
        this.textureHeight = 32;
    }
    
    public void render(final Entity p_78088_1_, final float p_78088_2_, final float p_78088_3_, final float p_78088_4_, final float p_78088_5_, final float p_78088_6_, final float p_78088_7_) {
    }
    
    public void setRotationAngles(final float p_78087_1_, final float p_78087_2_, final float p_78087_3_, final float p_78087_4_, final float p_78087_5_, final float p_78087_6_, final Entity p_78087_7_) {
    }
    
    public void setLivingAnimations(final EntityLivingBase p_78086_1_, final float p_78086_2_, final float p_78086_3_, final float p_78086_4_) {
    }
    
    public ModelRenderer getRandomModelBox(final Random p_85181_1_) {
        return this.boxList.get(p_85181_1_.nextInt(this.boxList.size()));
    }
    
    protected void setTextureOffset(final String p_78085_1_, final int p_78085_2_, final int p_78085_3_) {
        this.modelTextureMap.put(p_78085_1_, new TextureOffset(p_78085_2_, p_78085_3_));
    }
    
    public TextureOffset getTextureOffset(final String p_78084_1_) {
        return this.modelTextureMap.get(p_78084_1_);
    }
}
