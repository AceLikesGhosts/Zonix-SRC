package net.minecraft.client.gui;

import net.minecraft.util.*;
import com.google.common.collect.*;
import net.minecraft.world.storage.*;
import java.util.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.block.material.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.*;

public class MapItemRenderer
{
    private static final ResourceLocation field_148253_a;
    private final TextureManager field_148251_b;
    private final Map field_148252_c;
    private static final String __OBFID = "CL_00000663";
    
    public MapItemRenderer(final TextureManager p_i45009_1_) {
        this.field_148252_c = Maps.newHashMap();
        this.field_148251_b = p_i45009_1_;
    }
    
    public void func_148246_a(final MapData p_148246_1_) {
        this.func_148248_b(p_148246_1_).func_148236_a();
    }
    
    public void func_148250_a(final MapData p_148250_1_, final boolean p_148250_2_) {
        this.func_148248_b(p_148250_1_).func_148237_a(p_148250_2_);
    }
    
    private Instance func_148248_b(final MapData p_148248_1_) {
        Instance var2 = this.field_148252_c.get(p_148248_1_.mapName);
        if (var2 == null) {
            var2 = new Instance(p_148248_1_, null);
            this.field_148252_c.put(p_148248_1_.mapName, var2);
        }
        return var2;
    }
    
    public void func_148249_a() {
        for (final Instance var2 : this.field_148252_c.values()) {
            this.field_148251_b.func_147645_c(var2.field_148240_d);
        }
        this.field_148252_c.clear();
    }
    
    static {
        field_148253_a = new ResourceLocation("textures/map/map_icons.png");
    }
    
    class Instance
    {
        private final MapData field_148242_b;
        private final DynamicTexture field_148243_c;
        private final ResourceLocation field_148240_d;
        private final int[] field_148241_e;
        private static final String __OBFID = "CL_00000665";
        
        private Instance(final MapData p_i45007_2_) {
            this.field_148242_b = p_i45007_2_;
            this.field_148243_c = new DynamicTexture(128, 128);
            this.field_148241_e = this.field_148243_c.getTextureData();
            this.field_148240_d = MapItemRenderer.this.field_148251_b.getDynamicTextureLocation("map/" + p_i45007_2_.mapName, this.field_148243_c);
            for (int var3 = 0; var3 < this.field_148241_e.length; ++var3) {
                this.field_148241_e[var3] = 0;
            }
        }
        
        private void func_148236_a() {
            for (int var1 = 0; var1 < 16384; ++var1) {
                final int var2 = this.field_148242_b.colors[var1] & 0xFF;
                if (var2 / 4 == 0) {
                    this.field_148241_e[var1] = (var1 + var1 / 128 & 0x1) * 8 + 16 << 24;
                }
                else {
                    this.field_148241_e[var1] = MapColor.mapColorArray[var2 / 4].func_151643_b(var2 & 0x3);
                }
            }
            this.field_148243_c.updateDynamicTexture();
        }
        
        private void func_148237_a(final boolean p_148237_1_) {
            final byte var2 = 0;
            final byte var3 = 0;
            final Tessellator var4 = Tessellator.instance;
            final float var5 = 0.0f;
            MapItemRenderer.this.field_148251_b.bindTexture(this.field_148240_d);
            GL11.glEnable(3042);
            OpenGlHelper.glBlendFunc(1, 771, 0, 1);
            GL11.glDisable(3008);
            var4.startDrawingQuads();
            var4.addVertexWithUV(var2 + 0 + var5, var3 + 128 - var5, -0.009999999776482582, 0.0, 1.0);
            var4.addVertexWithUV(var2 + 128 - var5, var3 + 128 - var5, -0.009999999776482582, 1.0, 1.0);
            var4.addVertexWithUV(var2 + 128 - var5, var3 + 0 + var5, -0.009999999776482582, 1.0, 0.0);
            var4.addVertexWithUV(var2 + 0 + var5, var3 + 0 + var5, -0.009999999776482582, 0.0, 0.0);
            var4.draw();
            GL11.glEnable(3008);
            GL11.glDisable(3042);
            MapItemRenderer.this.field_148251_b.bindTexture(MapItemRenderer.field_148253_a);
            int var6 = 0;
            for (final MapData.MapCoord var8 : this.field_148242_b.playersVisibleOnMap.values()) {
                if (!p_148237_1_ || var8.iconSize == 1) {
                    GL11.glPushMatrix();
                    GL11.glTranslatef(var2 + var8.centerX / 2.0f + 64.0f, var3 + var8.centerZ / 2.0f + 64.0f, -0.02f);
                    GL11.glRotatef(var8.iconRotation * 360 / 16.0f, 0.0f, 0.0f, 1.0f);
                    GL11.glScalef(4.0f, 4.0f, 3.0f);
                    GL11.glTranslatef(-0.125f, 0.125f, 0.0f);
                    final float var9 = (var8.iconSize % 4 + 0) / 4.0f;
                    final float var10 = (var8.iconSize / 4 + 0) / 4.0f;
                    final float var11 = (var8.iconSize % 4 + 1) / 4.0f;
                    final float var12 = (var8.iconSize / 4 + 1) / 4.0f;
                    var4.startDrawingQuads();
                    var4.addVertexWithUV(-1.0, 1.0, var6 * 0.001f, var9, var10);
                    var4.addVertexWithUV(1.0, 1.0, var6 * 0.001f, var11, var10);
                    var4.addVertexWithUV(1.0, -1.0, var6 * 0.001f, var11, var12);
                    var4.addVertexWithUV(-1.0, -1.0, var6 * 0.001f, var9, var12);
                    var4.draw();
                    GL11.glPopMatrix();
                    ++var6;
                }
            }
            GL11.glPushMatrix();
            GL11.glTranslatef(0.0f, 0.0f, -0.04f);
            GL11.glScalef(1.0f, 1.0f, 1.0f);
            GL11.glPopMatrix();
        }
        
        Instance(final MapItemRenderer this$0, final MapData p_i45008_2_, final Object p_i45008_3_) {
            this(this$0, p_i45008_2_);
        }
    }
}
