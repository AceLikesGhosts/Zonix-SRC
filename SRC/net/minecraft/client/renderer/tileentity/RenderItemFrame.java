package net.minecraft.client.renderer.tileentity;

import net.minecraft.client.*;
import org.lwjgl.opengl.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import net.minecraft.entity.item.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.world.storage.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.gui.*;

public class RenderItemFrame extends Render
{
    private static final ResourceLocation mapBackgroundTextures;
    private final RenderBlocks field_147916_f;
    private final Minecraft field_147917_g;
    private IIcon field_94147_f;
    private static final String __OBFID = "CL_00001002";
    
    public RenderItemFrame() {
        this.field_147916_f = new RenderBlocks();
        this.field_147917_g = Minecraft.getMinecraft();
    }
    
    @Override
    public void updateIcons(final IIconRegister p_94143_1_) {
        this.field_94147_f = p_94143_1_.registerIcon("itemframe_background");
    }
    
    public void doRender(final EntityItemFrame p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        GL11.glPushMatrix();
        final double var10 = p_76986_1_.posX - p_76986_2_ - 0.5;
        final double var11 = p_76986_1_.posY - p_76986_4_ - 0.5;
        final double var12 = p_76986_1_.posZ - p_76986_6_ - 0.5;
        final int var13 = p_76986_1_.field_146063_b + Direction.offsetX[p_76986_1_.hangingDirection];
        final int var14 = p_76986_1_.field_146064_c;
        final int var15 = p_76986_1_.field_146062_d + Direction.offsetZ[p_76986_1_.hangingDirection];
        GL11.glTranslated(var13 - var10, var14 - var11, var15 - var12);
        if (p_76986_1_.getDisplayedItem() != null && p_76986_1_.getDisplayedItem().getItem() == Items.filled_map) {
            this.func_147915_b(p_76986_1_);
        }
        else {
            this.renderFrameItemAsBlock(p_76986_1_);
        }
        this.func_82402_b(p_76986_1_);
        GL11.glPopMatrix();
        this.func_147914_a(p_76986_1_, p_76986_2_ + Direction.offsetX[p_76986_1_.hangingDirection] * 0.3f, p_76986_4_ - 0.25, p_76986_6_ + Direction.offsetZ[p_76986_1_.hangingDirection] * 0.3f);
    }
    
    public ResourceLocation getEntityTexture(final EntityItemFrame p_110775_1_) {
        return null;
    }
    
    private void func_147915_b(final EntityItemFrame p_147915_1_) {
        GL11.glPushMatrix();
        GL11.glRotatef(p_147915_1_.rotationYaw, 0.0f, 1.0f, 0.0f);
        this.renderManager.renderEngine.bindTexture(TextureMap.locationBlocksTexture);
        final Block var2 = Blocks.planks;
        final float var3 = 0.0625f;
        final float var4 = 1.0f;
        final float var5 = var4 / 2.0f;
        GL11.glPushMatrix();
        this.field_147916_f.overrideBlockBounds(0.0, 0.5f - var5 + 0.0625f, 0.5f - var5 + 0.0625f, var3, 0.5f + var5 - 0.0625f, 0.5f + var5 - 0.0625f);
        this.field_147916_f.setOverrideBlockTexture(this.field_94147_f);
        this.field_147916_f.renderBlockAsItem(var2, 0, 1.0f);
        this.field_147916_f.clearOverrideBlockTexture();
        this.field_147916_f.unlockBlockBounds();
        GL11.glPopMatrix();
        this.field_147916_f.setOverrideBlockTexture(Blocks.planks.getIcon(1, 2));
        GL11.glPushMatrix();
        this.field_147916_f.overrideBlockBounds(0.0, 0.5f - var5, 0.5f - var5, var3 + 1.0E-4f, var3 + 0.5f - var5, 0.5f + var5);
        this.field_147916_f.renderBlockAsItem(var2, 0, 1.0f);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        this.field_147916_f.overrideBlockBounds(0.0, 0.5f + var5 - var3, 0.5f - var5, var3 + 1.0E-4f, 0.5f + var5, 0.5f + var5);
        this.field_147916_f.renderBlockAsItem(var2, 0, 1.0f);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        this.field_147916_f.overrideBlockBounds(0.0, 0.5f - var5, 0.5f - var5, var3, 0.5f + var5, var3 + 0.5f - var5);
        this.field_147916_f.renderBlockAsItem(var2, 0, 1.0f);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        this.field_147916_f.overrideBlockBounds(0.0, 0.5f - var5, 0.5f + var5 - var3, var3, 0.5f + var5, 0.5f + var5);
        this.field_147916_f.renderBlockAsItem(var2, 0, 1.0f);
        GL11.glPopMatrix();
        this.field_147916_f.unlockBlockBounds();
        this.field_147916_f.clearOverrideBlockTexture();
        GL11.glPopMatrix();
    }
    
    private void renderFrameItemAsBlock(final EntityItemFrame p_82403_1_) {
        GL11.glPushMatrix();
        GL11.glRotatef(p_82403_1_.rotationYaw, 0.0f, 1.0f, 0.0f);
        this.renderManager.renderEngine.bindTexture(TextureMap.locationBlocksTexture);
        final Block var2 = Blocks.planks;
        final float var3 = 0.0625f;
        final float var4 = 0.75f;
        final float var5 = var4 / 2.0f;
        GL11.glPushMatrix();
        this.field_147916_f.overrideBlockBounds(0.0, 0.5f - var5 + 0.0625f, 0.5f - var5 + 0.0625f, var3 * 0.5f, 0.5f + var5 - 0.0625f, 0.5f + var5 - 0.0625f);
        this.field_147916_f.setOverrideBlockTexture(this.field_94147_f);
        this.field_147916_f.renderBlockAsItem(var2, 0, 1.0f);
        this.field_147916_f.clearOverrideBlockTexture();
        this.field_147916_f.unlockBlockBounds();
        GL11.glPopMatrix();
        this.field_147916_f.setOverrideBlockTexture(Blocks.planks.getIcon(1, 2));
        GL11.glPushMatrix();
        this.field_147916_f.overrideBlockBounds(0.0, 0.5f - var5, 0.5f - var5, var3 + 1.0E-4f, var3 + 0.5f - var5, 0.5f + var5);
        this.field_147916_f.renderBlockAsItem(var2, 0, 1.0f);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        this.field_147916_f.overrideBlockBounds(0.0, 0.5f + var5 - var3, 0.5f - var5, var3 + 1.0E-4f, 0.5f + var5, 0.5f + var5);
        this.field_147916_f.renderBlockAsItem(var2, 0, 1.0f);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        this.field_147916_f.overrideBlockBounds(0.0, 0.5f - var5, 0.5f - var5, var3, 0.5f + var5, var3 + 0.5f - var5);
        this.field_147916_f.renderBlockAsItem(var2, 0, 1.0f);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        this.field_147916_f.overrideBlockBounds(0.0, 0.5f - var5, 0.5f + var5 - var3, var3, 0.5f + var5, 0.5f + var5);
        this.field_147916_f.renderBlockAsItem(var2, 0, 1.0f);
        GL11.glPopMatrix();
        this.field_147916_f.unlockBlockBounds();
        this.field_147916_f.clearOverrideBlockTexture();
        GL11.glPopMatrix();
    }
    
    private void func_82402_b(final EntityItemFrame p_82402_1_) {
        final ItemStack var2 = p_82402_1_.getDisplayedItem();
        if (var2 != null) {
            final EntityItem var3 = new EntityItem(p_82402_1_.worldObj, 0.0, 0.0, 0.0, var2);
            final Item var4 = var3.getEntityItem().getItem();
            var3.getEntityItem().stackSize = 1;
            var3.hoverStart = 0.0f;
            GL11.glPushMatrix();
            GL11.glTranslatef(-0.453125f * Direction.offsetX[p_82402_1_.hangingDirection], -0.18f, -0.453125f * Direction.offsetZ[p_82402_1_.hangingDirection]);
            GL11.glRotatef(180.0f + p_82402_1_.rotationYaw, 0.0f, 1.0f, 0.0f);
            GL11.glRotatef((float)(-90 * p_82402_1_.getRotation()), 0.0f, 0.0f, 1.0f);
            switch (p_82402_1_.getRotation()) {
                case 1: {
                    GL11.glTranslatef(-0.16f, -0.16f, 0.0f);
                    break;
                }
                case 2: {
                    GL11.glTranslatef(0.0f, -0.32f, 0.0f);
                    break;
                }
                case 3: {
                    GL11.glTranslatef(0.16f, -0.16f, 0.0f);
                    break;
                }
            }
            if (var4 == Items.filled_map) {
                this.renderManager.renderEngine.bindTexture(RenderItemFrame.mapBackgroundTextures);
                final Tessellator var5 = Tessellator.instance;
                GL11.glRotatef(180.0f, 0.0f, 1.0f, 0.0f);
                GL11.glRotatef(180.0f, 0.0f, 0.0f, 1.0f);
                final float var6 = 0.0078125f;
                GL11.glScalef(var6, var6, var6);
                switch (p_82402_1_.getRotation()) {
                    case 0: {
                        GL11.glTranslatef(-64.0f, -87.0f, -1.5f);
                        break;
                    }
                    case 1: {
                        GL11.glTranslatef(-66.5f, -84.5f, -1.5f);
                        break;
                    }
                    case 2: {
                        GL11.glTranslatef(-64.0f, -82.0f, -1.5f);
                        break;
                    }
                    case 3: {
                        GL11.glTranslatef(-61.5f, -84.5f, -1.5f);
                        break;
                    }
                }
                GL11.glNormal3f(0.0f, 0.0f, -1.0f);
                final MapData var7 = Items.filled_map.getMapData(var3.getEntityItem(), p_82402_1_.worldObj);
                GL11.glTranslatef(0.0f, 0.0f, -1.0f);
                if (var7 != null) {
                    this.field_147917_g.entityRenderer.getMapItemRenderer().func_148250_a(var7, true);
                }
            }
            else {
                if (var4 == Items.compass) {
                    final TextureManager var8 = Minecraft.getMinecraft().getTextureManager();
                    var8.bindTexture(TextureMap.locationItemsTexture);
                    final TextureAtlasSprite var9 = ((TextureMap)var8.getTexture(TextureMap.locationItemsTexture)).getAtlasSprite(Items.compass.getIconIndex(var3.getEntityItem()).getIconName());
                    if (var9 instanceof TextureCompass) {
                        final TextureCompass var10 = (TextureCompass)var9;
                        final double var11 = var10.currentAngle;
                        final double var12 = var10.angleDelta;
                        var10.currentAngle = 0.0;
                        var10.angleDelta = 0.0;
                        var10.updateCompass(p_82402_1_.worldObj, p_82402_1_.posX, p_82402_1_.posZ, MathHelper.wrapAngleTo180_float((float)(180 + p_82402_1_.hangingDirection * 90)), false, true);
                        var10.currentAngle = var11;
                        var10.angleDelta = var12;
                    }
                }
                RenderItem.renderInFrame = true;
                RenderManager.instance.func_147940_a(var3, 0.0, 0.0, 0.0, 0.0f, 0.0f);
                RenderItem.renderInFrame = false;
                if (var4 == Items.compass) {
                    final TextureAtlasSprite var13 = ((TextureMap)Minecraft.getMinecraft().getTextureManager().getTexture(TextureMap.locationItemsTexture)).getAtlasSprite(Items.compass.getIconIndex(var3.getEntityItem()).getIconName());
                    if (var13.getFrameCount() > 0) {
                        var13.updateAnimation();
                    }
                }
            }
            GL11.glPopMatrix();
        }
    }
    
    protected void func_147914_a(final EntityItemFrame p_147914_1_, final double p_147914_2_, final double p_147914_4_, final double p_147914_6_) {
        if (Minecraft.isGuiEnabled() && p_147914_1_.getDisplayedItem() != null && p_147914_1_.getDisplayedItem().hasDisplayName() && this.renderManager.field_147941_i == p_147914_1_) {
            final float var8 = 1.6f;
            final float var9 = 0.016666668f * var8;
            final double var10 = p_147914_1_.getDistanceSqToEntity(this.renderManager.livingPlayer);
            final float var11 = p_147914_1_.isSneaking() ? 32.0f : 64.0f;
            if (var10 < var11 * var11) {
                final String var12 = p_147914_1_.getDisplayedItem().getDisplayName();
                if (p_147914_1_.isSneaking()) {
                    final FontRenderer var13 = this.getFontRendererFromRenderManager();
                    GL11.glPushMatrix();
                    GL11.glTranslatef((float)p_147914_2_ + 0.0f, (float)p_147914_4_ + p_147914_1_.height + 0.5f, (float)p_147914_6_);
                    GL11.glNormal3f(0.0f, 1.0f, 0.0f);
                    GL11.glRotatef(-this.renderManager.playerViewY, 0.0f, 1.0f, 0.0f);
                    GL11.glRotatef(this.renderManager.playerViewX, 1.0f, 0.0f, 0.0f);
                    GL11.glScalef(-var9, -var9, var9);
                    GL11.glDisable(2896);
                    GL11.glTranslatef(0.0f, 0.25f / var9, 0.0f);
                    GL11.glDepthMask(false);
                    GL11.glEnable(3042);
                    GL11.glBlendFunc(770, 771);
                    final Tessellator var14 = Tessellator.instance;
                    GL11.glDisable(3553);
                    var14.startDrawingQuads();
                    final int var15 = var13.getStringWidth(var12) / 2;
                    var14.setColorRGBA_F(0.0f, 0.0f, 0.0f, 0.25f);
                    var14.addVertex(-var15 - 1, -1.0, 0.0);
                    var14.addVertex(-var15 - 1, 8.0, 0.0);
                    var14.addVertex(var15 + 1, 8.0, 0.0);
                    var14.addVertex(var15 + 1, -1.0, 0.0);
                    var14.draw();
                    GL11.glEnable(3553);
                    GL11.glDepthMask(true);
                    var13.drawString(var12, -var13.getStringWidth(var12) / 2, 0, 553648127);
                    GL11.glEnable(2896);
                    GL11.glDisable(3042);
                    GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                    GL11.glPopMatrix();
                }
                else {
                    this.func_147906_a(p_147914_1_, var12, p_147914_2_, p_147914_4_, p_147914_6_, 64);
                }
            }
        }
    }
    
    @Override
    public ResourceLocation getEntityTexture(final Entity p_110775_1_) {
        return this.getEntityTexture((EntityItemFrame)p_110775_1_);
    }
    
    @Override
    public void doRender(final Entity p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        this.doRender((EntityItemFrame)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
    
    static {
        mapBackgroundTextures = new ResourceLocation("textures/map/map_background.png");
    }
}
