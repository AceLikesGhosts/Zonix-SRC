package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.*;
import org.lwjgl.opengl.*;
import net.minecraft.item.*;
import net.minecraft.block.*;
import net.minecraft.client.renderer.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import java.util.*;
import com.mojang.authlib.*;
import net.minecraft.client.renderer.tileentity.*;
import net.minecraft.nbt.*;
import net.minecraft.entity.*;
import com.google.common.collect.*;

public class RenderBiped extends RenderLiving
{
    protected ModelBiped modelBipedMain;
    protected float field_77070_b;
    protected ModelBiped field_82423_g;
    protected ModelBiped field_82425_h;
    private static final Map field_110859_k;
    private static final String[] bipedArmorFilenamePrefix;
    private static final String __OBFID = "CL_00001001";
    
    public RenderBiped(final ModelBiped p_i1257_1_, final float p_i1257_2_) {
        this(p_i1257_1_, p_i1257_2_, 1.0f);
    }
    
    public RenderBiped(final ModelBiped p_i1258_1_, final float p_i1258_2_, final float p_i1258_3_) {
        super(p_i1258_1_, p_i1258_2_);
        this.modelBipedMain = p_i1258_1_;
        this.field_77070_b = p_i1258_3_;
        this.func_82421_b();
    }
    
    protected void func_82421_b() {
        this.field_82423_g = new ModelBiped(1.0f);
        this.field_82425_h = new ModelBiped(0.5f);
    }
    
    public static ResourceLocation func_110857_a(final ItemArmor p_110857_0_, final int p_110857_1_) {
        return func_110858_a(p_110857_0_, p_110857_1_, null);
    }
    
    public static ResourceLocation func_110858_a(final ItemArmor p_110858_0_, final int p_110858_1_, final String p_110858_2_) {
        final String var3 = String.format("textures/models/armor/%s_layer_%d%s.png", RenderBiped.bipedArmorFilenamePrefix[p_110858_0_.renderIndex], (p_110858_1_ == 2) ? 2 : 1, (p_110858_2_ == null) ? "" : String.format("_%s", p_110858_2_));
        ResourceLocation var4 = RenderBiped.field_110859_k.get(var3);
        if (var4 == null) {
            var4 = new ResourceLocation(var3);
            RenderBiped.field_110859_k.put(var3, var4);
        }
        return var4;
    }
    
    public int shouldRenderPass(final EntityLiving p_77032_1_, final int p_77032_2_, final float p_77032_3_) {
        final ItemStack var4 = p_77032_1_.func_130225_q(3 - p_77032_2_);
        if (var4 != null) {
            final Item var5 = var4.getItem();
            if (var5 instanceof ItemArmor) {
                final ItemArmor var6 = (ItemArmor)var5;
                this.bindTexture(func_110857_a(var6, p_77032_2_));
                final ModelBiped var7 = (p_77032_2_ == 2) ? this.field_82425_h : this.field_82423_g;
                var7.bipedHead.showModel = (p_77032_2_ == 0);
                var7.bipedHeadwear.showModel = (p_77032_2_ == 0);
                var7.bipedBody.showModel = (p_77032_2_ == 1 || p_77032_2_ == 2);
                var7.bipedRightArm.showModel = (p_77032_2_ == 1);
                var7.bipedLeftArm.showModel = (p_77032_2_ == 1);
                var7.bipedRightLeg.showModel = (p_77032_2_ == 2 || p_77032_2_ == 3);
                var7.bipedLeftLeg.showModel = (p_77032_2_ == 2 || p_77032_2_ == 3);
                this.setRenderPassModel(var7);
                var7.onGround = this.mainModel.onGround;
                var7.isRiding = this.mainModel.isRiding;
                var7.isChild = this.mainModel.isChild;
                if (var6.getArmorMaterial() == ItemArmor.ArmorMaterial.CLOTH) {
                    final int var8 = var6.getColor(var4);
                    final float var9 = (var8 >> 16 & 0xFF) / 255.0f;
                    final float var10 = (var8 >> 8 & 0xFF) / 255.0f;
                    final float var11 = (var8 & 0xFF) / 255.0f;
                    GL11.glColor3f(var9, var10, var11);
                    if (var4.isItemEnchanted()) {
                        return 31;
                    }
                    return 16;
                }
                else {
                    GL11.glColor3f(1.0f, 1.0f, 1.0f);
                    if (var4.isItemEnchanted()) {
                        return 15;
                    }
                    return 1;
                }
            }
        }
        return -1;
    }
    
    protected void func_82408_c(final EntityLiving p_82408_1_, final int p_82408_2_, final float p_82408_3_) {
        final ItemStack var4 = p_82408_1_.func_130225_q(3 - p_82408_2_);
        if (var4 != null) {
            final Item var5 = var4.getItem();
            if (var5 instanceof ItemArmor) {
                this.bindTexture(func_110858_a((ItemArmor)var5, p_82408_2_, "overlay"));
                final float var6 = 1.0f;
                GL11.glColor3f(1.0f, 1.0f, 1.0f);
            }
        }
    }
    
    @Override
    public void doRender(final EntityLiving p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        GL11.glColor3f(1.0f, 1.0f, 1.0f);
        final ItemStack var10 = p_76986_1_.getHeldItem();
        this.func_82420_a(p_76986_1_, var10);
        double var11 = p_76986_4_ - p_76986_1_.yOffset;
        if (p_76986_1_.isSneaking()) {
            var11 -= 0.125;
        }
        super.doRender(p_76986_1_, p_76986_2_, var11, p_76986_6_, p_76986_8_, p_76986_9_);
        final ModelBiped field_82423_g = this.field_82423_g;
        final ModelBiped field_82425_h = this.field_82425_h;
        final ModelBiped modelBipedMain = this.modelBipedMain;
        final boolean aimedBow = false;
        modelBipedMain.aimedBow = aimedBow;
        field_82425_h.aimedBow = aimedBow;
        field_82423_g.aimedBow = aimedBow;
        final ModelBiped field_82423_g2 = this.field_82423_g;
        final ModelBiped field_82425_h2 = this.field_82425_h;
        final ModelBiped modelBipedMain2 = this.modelBipedMain;
        final boolean isSneak = false;
        modelBipedMain2.isSneak = isSneak;
        field_82425_h2.isSneak = isSneak;
        field_82423_g2.isSneak = isSneak;
        final ModelBiped field_82423_g3 = this.field_82423_g;
        final ModelBiped field_82425_h3 = this.field_82425_h;
        final ModelBiped modelBipedMain3 = this.modelBipedMain;
        final int heldItemRight = 0;
        modelBipedMain3.heldItemRight = heldItemRight;
        field_82425_h3.heldItemRight = heldItemRight;
        field_82423_g3.heldItemRight = heldItemRight;
    }
    
    public ResourceLocation getEntityTexture(final EntityLiving p_110775_1_) {
        return null;
    }
    
    protected void func_82420_a(final EntityLiving p_82420_1_, final ItemStack p_82420_2_) {
        final ModelBiped field_82423_g = this.field_82423_g;
        final ModelBiped field_82425_h = this.field_82425_h;
        final ModelBiped modelBipedMain = this.modelBipedMain;
        final int heldItemRight;
        final int n = heldItemRight = ((p_82420_2_ != null) ? 1 : 0);
        modelBipedMain.heldItemRight = n;
        field_82425_h.heldItemRight = n;
        field_82423_g.heldItemRight = heldItemRight;
        final ModelBiped field_82423_g2 = this.field_82423_g;
        final ModelBiped field_82425_h2 = this.field_82425_h;
        final ModelBiped modelBipedMain2 = this.modelBipedMain;
        final boolean sneaking = p_82420_1_.isSneaking();
        modelBipedMain2.isSneak = sneaking;
        field_82425_h2.isSneak = sneaking;
        field_82423_g2.isSneak = sneaking;
    }
    
    protected void renderEquippedItems(final EntityLiving p_77029_1_, final float p_77029_2_) {
        GL11.glColor3f(1.0f, 1.0f, 1.0f);
        super.renderEquippedItems(p_77029_1_, p_77029_2_);
        final ItemStack var3 = p_77029_1_.getHeldItem();
        final ItemStack var4 = p_77029_1_.func_130225_q(3);
        if (var4 != null) {
            GL11.glPushMatrix();
            this.modelBipedMain.bipedHead.postRender(0.0625f);
            final Item var5 = var4.getItem();
            if (var5 instanceof ItemBlock) {
                if (RenderBlocks.renderItemIn3d(Block.getBlockFromItem(var5).getRenderType())) {
                    final float var6 = 0.625f;
                    GL11.glTranslatef(0.0f, -0.25f, 0.0f);
                    GL11.glRotatef(90.0f, 0.0f, 1.0f, 0.0f);
                    GL11.glScalef(var6, -var6, -var6);
                }
                this.renderManager.itemRenderer.renderItem(p_77029_1_, var4, 0);
            }
            else if (var5 == Items.skull) {
                final float var6 = 1.0625f;
                GL11.glScalef(var6, -var6, -var6);
                GameProfile var7 = null;
                if (var4.hasTagCompound()) {
                    final NBTTagCompound var8 = var4.getTagCompound();
                    if (var8.func_150297_b("SkullOwner", 10)) {
                        var7 = NBTUtil.func_152459_a(var8.getCompoundTag("SkullOwner"));
                    }
                    else if (var8.func_150297_b("SkullOwner", 8) && !StringUtils.isNullOrEmpty(var8.getString("SkullOwner"))) {
                        var7 = new GameProfile((UUID)null, var8.getString("SkullOwner"));
                    }
                }
                TileEntitySkullRenderer.field_147536_b.func_152674_a(-0.5f, 0.0f, -0.5f, 1, 180.0f, var4.getItemDamage(), var7);
            }
            GL11.glPopMatrix();
        }
        if (var3 != null && var3.getItem() != null) {
            final Item var5 = var3.getItem();
            GL11.glPushMatrix();
            if (this.mainModel.isChild) {
                final float var6 = 0.5f;
                GL11.glTranslatef(0.0f, 0.625f, 0.0f);
                GL11.glRotatef(-20.0f, -1.0f, 0.0f, 0.0f);
                GL11.glScalef(var6, var6, var6);
            }
            this.modelBipedMain.bipedRightArm.postRender(0.0625f);
            GL11.glTranslatef(-0.0625f, 0.4375f, 0.0625f);
            if (var5 instanceof ItemBlock && RenderBlocks.renderItemIn3d(Block.getBlockFromItem(var5).getRenderType())) {
                float var6 = 0.5f;
                GL11.glTranslatef(0.0f, 0.1875f, -0.3125f);
                var6 *= 0.75f;
                GL11.glRotatef(20.0f, 1.0f, 0.0f, 0.0f);
                GL11.glRotatef(45.0f, 0.0f, 1.0f, 0.0f);
                GL11.glScalef(-var6, -var6, var6);
            }
            else if (var5 == Items.bow) {
                final float var6 = 0.625f;
                GL11.glTranslatef(0.0f, 0.125f, 0.3125f);
                GL11.glRotatef(-20.0f, 0.0f, 1.0f, 0.0f);
                GL11.glScalef(var6, -var6, var6);
                GL11.glRotatef(-100.0f, 1.0f, 0.0f, 0.0f);
                GL11.glRotatef(45.0f, 0.0f, 1.0f, 0.0f);
            }
            else if (var5.isFull3D()) {
                final float var6 = 0.625f;
                if (var5.shouldRotateAroundWhenRendering()) {
                    GL11.glRotatef(180.0f, 0.0f, 0.0f, 1.0f);
                    GL11.glTranslatef(0.0f, -0.125f, 0.0f);
                }
                this.func_82422_c();
                GL11.glScalef(var6, -var6, var6);
                GL11.glRotatef(-100.0f, 1.0f, 0.0f, 0.0f);
                GL11.glRotatef(45.0f, 0.0f, 1.0f, 0.0f);
            }
            else {
                final float var6 = 0.375f;
                GL11.glTranslatef(0.25f, 0.1875f, -0.1875f);
                GL11.glScalef(var6, var6, var6);
                GL11.glRotatef(60.0f, 0.0f, 0.0f, 1.0f);
                GL11.glRotatef(-90.0f, 1.0f, 0.0f, 0.0f);
                GL11.glRotatef(20.0f, 0.0f, 0.0f, 1.0f);
            }
            if (var3.getItem().requiresMultipleRenderPasses()) {
                for (int var9 = 0; var9 <= 1; ++var9) {
                    final int var10 = var3.getItem().getColorFromItemStack(var3, var9);
                    final float var11 = (var10 >> 16 & 0xFF) / 255.0f;
                    final float var12 = (var10 >> 8 & 0xFF) / 255.0f;
                    final float var13 = (var10 & 0xFF) / 255.0f;
                    GL11.glColor4f(var11, var12, var13, 1.0f);
                    this.renderManager.itemRenderer.renderItem(p_77029_1_, var3, var9);
                }
            }
            else {
                final int var9 = var3.getItem().getColorFromItemStack(var3, 0);
                final float var14 = (var9 >> 16 & 0xFF) / 255.0f;
                final float var11 = (var9 >> 8 & 0xFF) / 255.0f;
                final float var12 = (var9 & 0xFF) / 255.0f;
                GL11.glColor4f(var14, var11, var12, 1.0f);
                this.renderManager.itemRenderer.renderItem(p_77029_1_, var3, 0);
            }
            GL11.glPopMatrix();
        }
    }
    
    protected void func_82422_c() {
        GL11.glTranslatef(0.0f, 0.1875f, 0.0f);
    }
    
    @Override
    protected void func_82408_c(final EntityLivingBase p_82408_1_, final int p_82408_2_, final float p_82408_3_) {
        this.func_82408_c((EntityLiving)p_82408_1_, p_82408_2_, p_82408_3_);
    }
    
    @Override
    public int shouldRenderPass(final EntityLivingBase p_77032_1_, final int p_77032_2_, final float p_77032_3_) {
        return this.shouldRenderPass((EntityLiving)p_77032_1_, p_77032_2_, p_77032_3_);
    }
    
    @Override
    protected void renderEquippedItems(final EntityLivingBase p_77029_1_, final float p_77029_2_) {
        this.renderEquippedItems((EntityLiving)p_77029_1_, p_77029_2_);
    }
    
    @Override
    public void doRender(final EntityLivingBase p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        this.doRender((EntityLiving)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
    
    @Override
    public ResourceLocation getEntityTexture(final Entity p_110775_1_) {
        return this.getEntityTexture((EntityLiving)p_110775_1_);
    }
    
    @Override
    public void doRender(final Entity p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        this.doRender((EntityLiving)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
    
    static {
        field_110859_k = Maps.newHashMap();
        bipedArmorFilenamePrefix = new String[] { "leather", "chainmail", "iron", "diamond", "gold" };
    }
}
