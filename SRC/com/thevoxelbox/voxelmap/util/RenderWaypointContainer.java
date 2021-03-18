package com.thevoxelbox.voxelmap.util;

import com.thevoxelbox.voxelmap.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.entity.*;
import java.util.*;
import net.minecraft.client.renderer.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.settings.*;
import net.minecraft.client.gui.*;

public class RenderWaypointContainer extends Render
{
    MapSettingsManager options;
    
    public RenderWaypointContainer(final MapSettingsManager options) {
        this.options = null;
        this.options = options;
    }
    
    @Override
    public ResourceLocation getEntityTexture(final Entity par1Entity) {
        return new ResourceLocation("", "");
    }
    
    public void doRenderWaypoints(final EntityWaypointContainer par1EntityWaypointContainer, final double baseX, final double baseY, final double baseZ, final float par8, final float par9) {
        for (final Waypoint pt : par1EntityWaypointContainer.wayPts) {
            if (pt.isActive()) {
                final int x = pt.getX();
                final int z = pt.getZ();
                final int y = pt.getY();
                final double distance = Math.sqrt(pt.getDistanceSqToEntity(this.renderManager.livingPlayer));
                if (this.options.showBeacons && par1EntityWaypointContainer.worldObj.getChunkFromBlockCoords(x, z).isChunkLoaded) {
                    final double bottomOfWorld = 0.0 - RenderManager.renderPosY;
                    this.renderBeam(pt, x - RenderManager.renderPosX, bottomOfWorld, z - RenderManager.renderPosZ, 64.0f, distance);
                }
                if (!this.options.showWaypoints || this.options.game.gameSettings.hideGUI) {
                    continue;
                }
                final String label = pt.name;
                this.renderLabel(pt, label, baseX + x, baseY + y + 1.0, baseZ + z, 64, distance);
            }
        }
    }
    
    public void renderBeam(final Waypoint par1EntityWaypoint, final double baseX, final double baseY, final double baseZ, final float par8, final double distance) {
        final Tessellator tesselator = Tessellator.instance;
        GL11.glDisable(3553);
        GL11.glDisable(2896);
        GL11.glDisable(2912);
        GL11.glDepthMask(false);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 1);
        final int height = 256;
        final float brightness = 0.06f;
        final double topWidthFactor = 1.05;
        final double bottomWidthFactor = 1.05;
        final float r = par1EntityWaypoint.red;
        final float b = par1EntityWaypoint.blue;
        final float g = par1EntityWaypoint.green;
        float alphaMultiplier = (distance < 30.0) ? ((float)distance / 30.0f) : 1.0f;
        if (alphaMultiplier < 0.2f) {
            alphaMultiplier = 0.2f;
        }
        for (int width = 0; width < 4; ++width) {
            tesselator.startDrawing(5);
            tesselator.setColorRGBA_F(r * brightness, g * brightness, b * brightness, 0.8f * alphaMultiplier);
            double var32 = 0.1 + width * 0.2;
            var32 *= topWidthFactor;
            double var33 = 0.1 + width * 0.2;
            var33 *= bottomWidthFactor;
            for (int side = 0; side < 5; ++side) {
                double vertX2 = baseX + 0.5 - var32;
                double vertZ2 = baseZ + 0.5 - var32;
                if (side == 1 || side == 2) {
                    vertX2 += var32 * 2.0;
                }
                if (side == 2 || side == 3) {
                    vertZ2 += var32 * 2.0;
                }
                double vertX3 = baseX + 0.5 - var33;
                double vertZ3 = baseZ + 0.5 - var33;
                if (side == 1 || side == 2) {
                    vertX3 += var33 * 2.0;
                }
                if (side == 2 || side == 3) {
                    vertZ3 += var33 * 2.0;
                }
                tesselator.addVertex(vertX3, baseY + 0.0, vertZ3);
                tesselator.addVertex(vertX2, baseY + height, vertZ2);
            }
            tesselator.draw();
        }
        GL11.glDisable(3042);
        GL11.glEnable(2912);
        GL11.glEnable(2896);
        GL11.glEnable(3553);
        GL11.glDepthMask(true);
    }
    
    protected void renderLabel(final Waypoint par1EntityWaypoint, String par2Str, double par3, double par5, double par7, final int par9, final double distance) {
        GL11.glAlphaFunc(516, 0.1f);
        if (distance <= this.options.maxWaypointDisplayDistance || this.options.maxWaypointDisplayDistance < 0) {
            par2Str = par2Str + " (" + (int)distance + "m)";
            final double maxDistance = this.options.game.gameSettings.getOptionFloatValue(GameSettings.Options.RENDER_DISTANCE) * 16.0f * 0.75;
            double adjustedDistance = distance;
            if (distance > maxDistance) {
                par3 = par3 / distance * maxDistance;
                par5 = par5 / distance * maxDistance;
                par7 = par7 / distance * maxDistance;
                adjustedDistance = maxDistance;
            }
            final FontRenderer fontRenderer = this.getFontRendererFromRenderManager();
            final float var14 = ((float)adjustedDistance * 0.1f + 1.0f) * 0.0266f;
            GL11.glPushMatrix();
            GL11.glTranslatef((float)par3 + 0.5f, (float)par5 + 1.3f, (float)par7 + 0.5f);
            GL11.glNormal3f(0.0f, 1.0f, 0.0f);
            GL11.glRotatef(-this.renderManager.playerViewY, 0.0f, 1.0f, 0.0f);
            GL11.glRotatef(this.renderManager.playerViewX, 1.0f, 0.0f, 0.0f);
            GL11.glScalef(-var14, -var14, var14);
            GL11.glDisable(2896);
            GL11.glDisable(2912);
            GL11.glDepthMask(false);
            GL11.glDisable(2929);
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            final Tessellator var15 = Tessellator.instance;
            final byte var16 = 0;
            GL11.glDisable(3553);
            final int var17 = fontRenderer.getStringWidth(par2Str) / 2;
            GL11.glEnable(2929);
            if (distance < maxDistance) {
                GL11.glDepthMask(true);
            }
            float alphaMultiplier = (distance < 30.0) ? ((float)distance / 30.0f) : 1.0f;
            if (alphaMultiplier < 0.2f) {
                alphaMultiplier = 0.2f;
            }
            GL11.glEnable(32823);
            GL11.glPolygonOffset(1.0f, 3.0f);
            var15.startDrawingQuads();
            var15.setColorRGBA_F(par1EntityWaypoint.red, par1EntityWaypoint.green, par1EntityWaypoint.blue, 0.6f * alphaMultiplier);
            var15.addVertex(-var17 - 2, -2 + var16, 0.0);
            var15.addVertex(-var17 - 2, 9 + var16, 0.0);
            var15.addVertex(var17 + 2, 9 + var16, 0.0);
            var15.addVertex(var17 + 2, -2 + var16, 0.0);
            var15.draw();
            GL11.glPolygonOffset(1.0f, 1.0f);
            var15.startDrawingQuads();
            var15.setColorRGBA_F(0.0f, 0.0f, 0.0f, 0.15f);
            var15.addVertex(-var17 - 1, -1 + var16, 0.0);
            var15.addVertex(-var17 - 1, 8 + var16, 0.0);
            var15.addVertex(var17 + 1, 8 + var16, 0.0);
            var15.addVertex(var17 + 1, -1 + var16, 0.0);
            var15.draw();
            GL11.glDisable(2929);
            GL11.glDepthMask(false);
            GL11.glPolygonOffset(1.0f, 7.0f);
            var15.startDrawingQuads();
            var15.setColorRGBA_F(par1EntityWaypoint.red, par1EntityWaypoint.green, par1EntityWaypoint.blue, 0.15f * alphaMultiplier);
            var15.addVertex(-var17 - 2, -2 + var16, 0.0);
            var15.addVertex(-var17 - 2, 9 + var16, 0.0);
            var15.addVertex(var17 + 2, 9 + var16, 0.0);
            var15.addVertex(var17 + 2, -2 + var16, 0.0);
            var15.draw();
            GL11.glPolygonOffset(1.0f, 5.0f);
            var15.startDrawingQuads();
            var15.setColorRGBA_F(0.0f, 0.0f, 0.0f, 0.15f * alphaMultiplier);
            var15.addVertex(-var17 - 1, -1 + var16, 0.0);
            var15.addVertex(-var17 - 1, 8 + var16, 0.0);
            var15.addVertex(var17 + 1, 8 + var16, 0.0);
            var15.addVertex(var17 + 1, -1 + var16, 0.0);
            var15.draw();
            GL11.glDisable(32823);
            GL11.glEnable(3553);
            final int font = 13421772;
            final int fontwhite = 16777215;
            fontRenderer.drawString(par2Str, -fontRenderer.getStringWidth(par2Str) / 2, var16, font | (int)(255.0f * alphaMultiplier) << 24);
            GL11.glEnable(2929);
            fontRenderer.drawString(par2Str, -fontRenderer.getStringWidth(par2Str) / 2, var16, fontwhite | (int)(255.0f * alphaMultiplier) << 24);
            GL11.glEnable(2929);
            GL11.glDepthMask(true);
            GL11.glEnable(2912);
            GL11.glEnable(2896);
            GL11.glDisable(3042);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            GL11.glPopMatrix();
        }
    }
    
    @Override
    public void doRender(final Entity par1Entity, final double par2, final double par4, final double par6, final float par8, final float par9) {
        this.doRenderWaypoints((EntityWaypointContainer)par1Entity, par2, par4, par6, par8, par9);
    }
}
