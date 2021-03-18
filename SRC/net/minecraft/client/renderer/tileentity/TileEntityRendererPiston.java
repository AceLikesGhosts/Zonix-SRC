package net.minecraft.client.renderer.tileentity;

import net.minecraft.block.material.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.renderer.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import net.minecraft.world.*;
import net.minecraft.tileentity.*;

public class TileEntityRendererPiston extends TileEntitySpecialRenderer
{
    private RenderBlocks field_147516_b;
    private static final String __OBFID = "CL_00000969";
    
    public void renderTileEntityAt(final TileEntityPiston p_147500_1_, final double p_147500_2_, final double p_147500_4_, final double p_147500_6_, final float p_147500_8_) {
        final Block var9 = p_147500_1_.func_145861_a();
        if (var9.getMaterial() != Material.air && p_147500_1_.func_145860_a(p_147500_8_) < 1.0f) {
            final Tessellator var10 = Tessellator.instance;
            this.bindTexture(TextureMap.locationBlocksTexture);
            RenderHelper.disableStandardItemLighting();
            GL11.glBlendFunc(770, 771);
            GL11.glEnable(3042);
            GL11.glDisable(2884);
            if (Minecraft.isAmbientOcclusionEnabled()) {
                GL11.glShadeModel(7425);
            }
            else {
                GL11.glShadeModel(7424);
            }
            var10.startDrawingQuads();
            var10.setTranslation((float)p_147500_2_ - p_147500_1_.field_145851_c + p_147500_1_.func_145865_b(p_147500_8_), (float)p_147500_4_ - p_147500_1_.field_145848_d + p_147500_1_.func_145862_c(p_147500_8_), (float)p_147500_6_ - p_147500_1_.field_145849_e + p_147500_1_.func_145859_d(p_147500_8_));
            var10.setColorOpaque_F(1.0f, 1.0f, 1.0f);
            if (var9 == Blocks.piston_head && p_147500_1_.func_145860_a(p_147500_8_) < 0.5f) {
                this.field_147516_b.renderPistonExtensionAllFaces(var9, p_147500_1_.field_145851_c, p_147500_1_.field_145848_d, p_147500_1_.field_145849_e, false);
            }
            else if (p_147500_1_.func_145867_d() && !p_147500_1_.func_145868_b()) {
                Blocks.piston_head.func_150086_a(((BlockPistonBase)var9).func_150073_e());
                this.field_147516_b.renderPistonExtensionAllFaces(Blocks.piston_head, p_147500_1_.field_145851_c, p_147500_1_.field_145848_d, p_147500_1_.field_145849_e, p_147500_1_.func_145860_a(p_147500_8_) < 0.5f);
                Blocks.piston_head.func_150087_e();
                var10.setTranslation((float)p_147500_2_ - p_147500_1_.field_145851_c, (float)p_147500_4_ - p_147500_1_.field_145848_d, (float)p_147500_6_ - p_147500_1_.field_145849_e);
                this.field_147516_b.renderPistonBaseAllFaces(var9, p_147500_1_.field_145851_c, p_147500_1_.field_145848_d, p_147500_1_.field_145849_e);
            }
            else {
                this.field_147516_b.renderBlockAllFaces(var9, p_147500_1_.field_145851_c, p_147500_1_.field_145848_d, p_147500_1_.field_145849_e);
            }
            var10.setTranslation(0.0, 0.0, 0.0);
            var10.draw();
            RenderHelper.enableStandardItemLighting();
        }
    }
    
    @Override
    public void func_147496_a(final World p_147496_1_) {
        this.field_147516_b = new RenderBlocks(p_147496_1_);
    }
    
    @Override
    public void renderTileEntityAt(final TileEntity p_147500_1_, final double p_147500_2_, final double p_147500_4_, final double p_147500_6_, final float p_147500_8_) {
        this.renderTileEntityAt((TileEntityPiston)p_147500_1_, p_147500_2_, p_147500_4_, p_147500_6_, p_147500_8_);
    }
}
