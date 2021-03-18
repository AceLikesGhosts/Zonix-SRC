package net.minecraft.client.renderer.entity;

import net.minecraft.util.*;
import net.minecraft.client.model.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.*;

public class RenderZombie extends RenderBiped
{
    private static final ResourceLocation zombiePigmanTextures;
    private static final ResourceLocation zombieTextures;
    private static final ResourceLocation zombieVillagerTextures;
    private ModelBiped field_82434_o;
    private ModelZombieVillager zombieVillagerModel;
    protected ModelBiped field_82437_k;
    protected ModelBiped field_82435_l;
    protected ModelBiped field_82436_m;
    protected ModelBiped field_82433_n;
    private int field_82431_q;
    private static final String __OBFID = "CL_00001037";
    
    public RenderZombie() {
        super(new ModelZombie(), 0.5f, 1.0f);
        this.field_82431_q = 1;
        this.field_82434_o = this.modelBipedMain;
        this.zombieVillagerModel = new ModelZombieVillager();
    }
    
    @Override
    protected void func_82421_b() {
        this.field_82423_g = new ModelZombie(1.0f, true);
        this.field_82425_h = new ModelZombie(0.5f, true);
        this.field_82437_k = this.field_82423_g;
        this.field_82435_l = this.field_82425_h;
        this.field_82436_m = new ModelZombieVillager(1.0f, 0.0f, true);
        this.field_82433_n = new ModelZombieVillager(0.5f, 0.0f, true);
    }
    
    public int shouldRenderPass(final EntityZombie p_77032_1_, final int p_77032_2_, final float p_77032_3_) {
        this.func_82427_a(p_77032_1_);
        return super.shouldRenderPass(p_77032_1_, p_77032_2_, p_77032_3_);
    }
    
    public void doRender(final EntityZombie p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        this.func_82427_a(p_76986_1_);
        super.doRender(p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
    
    public ResourceLocation getEntityTexture(final EntityZombie p_110775_1_) {
        return (p_110775_1_ instanceof EntityPigZombie) ? RenderZombie.zombiePigmanTextures : (p_110775_1_.isVillager() ? RenderZombie.zombieVillagerTextures : RenderZombie.zombieTextures);
    }
    
    protected void renderEquippedItems(final EntityZombie p_77029_1_, final float p_77029_2_) {
        this.func_82427_a(p_77029_1_);
        super.renderEquippedItems(p_77029_1_, p_77029_2_);
    }
    
    private void func_82427_a(final EntityZombie p_82427_1_) {
        if (p_82427_1_.isVillager()) {
            if (this.field_82431_q != this.zombieVillagerModel.func_82897_a()) {
                this.zombieVillagerModel = new ModelZombieVillager();
                this.field_82431_q = this.zombieVillagerModel.func_82897_a();
                this.field_82436_m = new ModelZombieVillager(1.0f, 0.0f, true);
                this.field_82433_n = new ModelZombieVillager(0.5f, 0.0f, true);
            }
            this.mainModel = this.zombieVillagerModel;
            this.field_82423_g = this.field_82436_m;
            this.field_82425_h = this.field_82433_n;
        }
        else {
            this.mainModel = this.field_82434_o;
            this.field_82423_g = this.field_82437_k;
            this.field_82425_h = this.field_82435_l;
        }
        this.modelBipedMain = (ModelBiped)this.mainModel;
    }
    
    protected void rotateCorpse(final EntityZombie p_77043_1_, final float p_77043_2_, float p_77043_3_, final float p_77043_4_) {
        if (p_77043_1_.isConverting()) {
            p_77043_3_ += (float)(Math.cos(p_77043_1_.ticksExisted * 3.25) * 3.141592653589793 * 0.25);
        }
        super.rotateCorpse(p_77043_1_, p_77043_2_, p_77043_3_, p_77043_4_);
    }
    
    @Override
    protected void renderEquippedItems(final EntityLiving p_77029_1_, final float p_77029_2_) {
        this.renderEquippedItems((EntityZombie)p_77029_1_, p_77029_2_);
    }
    
    @Override
    public ResourceLocation getEntityTexture(final EntityLiving p_110775_1_) {
        return this.getEntityTexture((EntityZombie)p_110775_1_);
    }
    
    @Override
    public void doRender(final EntityLiving p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        this.doRender((EntityZombie)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
    
    @Override
    public int shouldRenderPass(final EntityLiving p_77032_1_, final int p_77032_2_, final float p_77032_3_) {
        return this.shouldRenderPass((EntityZombie)p_77032_1_, p_77032_2_, p_77032_3_);
    }
    
    @Override
    public int shouldRenderPass(final EntityLivingBase p_77032_1_, final int p_77032_2_, final float p_77032_3_) {
        return this.shouldRenderPass((EntityZombie)p_77032_1_, p_77032_2_, p_77032_3_);
    }
    
    @Override
    protected void renderEquippedItems(final EntityLivingBase p_77029_1_, final float p_77029_2_) {
        this.renderEquippedItems((EntityZombie)p_77029_1_, p_77029_2_);
    }
    
    @Override
    protected void rotateCorpse(final EntityLivingBase p_77043_1_, final float p_77043_2_, final float p_77043_3_, final float p_77043_4_) {
        this.rotateCorpse((EntityZombie)p_77043_1_, p_77043_2_, p_77043_3_, p_77043_4_);
    }
    
    @Override
    public void doRender(final EntityLivingBase p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        this.doRender((EntityZombie)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
    
    @Override
    public ResourceLocation getEntityTexture(final Entity p_110775_1_) {
        return this.getEntityTexture((EntityZombie)p_110775_1_);
    }
    
    @Override
    public void doRender(final Entity p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        this.doRender((EntityZombie)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
    
    static {
        zombiePigmanTextures = new ResourceLocation("textures/entity/zombie_pigman.png");
        zombieTextures = new ResourceLocation("textures/entity/zombie/zombie.png");
        zombieVillagerTextures = new ResourceLocation("textures/entity/zombie/zombie_villager.png");
    }
}
