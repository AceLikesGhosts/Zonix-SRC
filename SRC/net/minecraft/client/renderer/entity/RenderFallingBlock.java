package net.minecraft.client.renderer.entity;

import net.minecraft.entity.item.*;
import org.lwjgl.opengl.*;
import net.minecraft.entity.*;
import net.minecraft.client.renderer.*;
import net.minecraft.world.*;
import net.minecraft.block.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.texture.*;

public class RenderFallingBlock extends Render
{
    private final RenderBlocks field_147920_a;
    private static final String __OBFID = "CL_00000994";
    
    public RenderFallingBlock() {
        this.field_147920_a = new RenderBlocks();
        this.shadowSize = 0.5f;
    }
    
    public void doRender(final EntityFallingBlock p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        final World var10 = p_76986_1_.func_145807_e();
        final Block var11 = p_76986_1_.func_145805_f();
        final int var12 = MathHelper.floor_double(p_76986_1_.posX);
        final int var13 = MathHelper.floor_double(p_76986_1_.posY);
        final int var14 = MathHelper.floor_double(p_76986_1_.posZ);
        if (var11 != null && var11 != var10.getBlock(var12, var13, var14)) {
            GL11.glPushMatrix();
            GL11.glTranslatef((float)p_76986_2_, (float)p_76986_4_, (float)p_76986_6_);
            this.bindEntityTexture(p_76986_1_);
            GL11.glDisable(2896);
            if (var11 instanceof BlockAnvil) {
                this.field_147920_a.blockAccess = var10;
                final Tessellator var15 = Tessellator.instance;
                var15.startDrawingQuads();
                var15.setTranslation(-var12 - 0.5f, -var13 - 0.5f, -var14 - 0.5f);
                this.field_147920_a.renderBlockAnvilMetadata((BlockAnvil)var11, var12, var13, var14, p_76986_1_.field_145814_a);
                var15.setTranslation(0.0, 0.0, 0.0);
                var15.draw();
            }
            else if (var11 instanceof BlockDragonEgg) {
                this.field_147920_a.blockAccess = var10;
                final Tessellator var15 = Tessellator.instance;
                var15.startDrawingQuads();
                var15.setTranslation(-var12 - 0.5f, -var13 - 0.5f, -var14 - 0.5f);
                this.field_147920_a.renderBlockDragonEgg((BlockDragonEgg)var11, var12, var13, var14);
                var15.setTranslation(0.0, 0.0, 0.0);
                var15.draw();
            }
            else {
                this.field_147920_a.setRenderBoundsFromBlock(var11);
                this.field_147920_a.renderBlockSandFalling(var11, var10, var12, var13, var14, p_76986_1_.field_145814_a);
            }
            GL11.glEnable(2896);
            GL11.glPopMatrix();
        }
    }
    
    public ResourceLocation getEntityTexture(final EntityFallingBlock p_110775_1_) {
        return TextureMap.locationBlocksTexture;
    }
    
    @Override
    public ResourceLocation getEntityTexture(final Entity p_110775_1_) {
        return this.getEntityTexture((EntityFallingBlock)p_110775_1_);
    }
    
    @Override
    public void doRender(final Entity p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        this.doRender((EntityFallingBlock)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
}
