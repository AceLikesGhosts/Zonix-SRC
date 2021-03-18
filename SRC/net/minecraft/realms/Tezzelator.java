package net.minecraft.realms;

import net.minecraft.client.renderer.*;
import net.minecraft.client.shader.*;

public class Tezzelator
{
    public static Tessellator t;
    public static final Tezzelator instance;
    private static final String __OBFID = "CL_00001855";
    
    public int end() {
        return Tezzelator.t.draw();
    }
    
    public void vertex(final double p_vertex_1_, final double p_vertex_3_, final double p_vertex_5_) {
        Tezzelator.t.addVertex(p_vertex_1_, p_vertex_3_, p_vertex_5_);
    }
    
    public void color(final float p_color_1_, final float p_color_2_, final float p_color_3_, final float p_color_4_) {
        Tezzelator.t.setColorRGBA_F(p_color_1_, p_color_2_, p_color_3_, p_color_4_);
    }
    
    public void color(final int p_color_1_, final int p_color_2_, final int p_color_3_) {
        Tezzelator.t.setColorOpaque(p_color_1_, p_color_2_, p_color_3_);
    }
    
    public void tex2(final int p_tex2_1_) {
        Tezzelator.t.setBrightness(p_tex2_1_);
    }
    
    public void normal(final float p_normal_1_, final float p_normal_2_, final float p_normal_3_) {
        Tezzelator.t.setNormal(p_normal_1_, p_normal_2_, p_normal_3_);
    }
    
    public void noColor() {
        Tezzelator.t.disableColor();
    }
    
    public void color(final int p_color_1_) {
        Tezzelator.t.setColorOpaque_I(p_color_1_);
    }
    
    public void color(final float p_color_1_, final float p_color_2_, final float p_color_3_) {
        Tezzelator.t.setColorOpaque_F(p_color_1_, p_color_2_, p_color_3_);
    }
    
    public TesselatorVertexState sortQuads(final float p_sortQuads_1_, final float p_sortQuads_2_, final float p_sortQuads_3_) {
        return Tezzelator.t.getVertexState(p_sortQuads_1_, p_sortQuads_2_, p_sortQuads_3_);
    }
    
    public void restoreState(final TesselatorVertexState p_restoreState_1_) {
        Tezzelator.t.setVertexState(p_restoreState_1_);
    }
    
    public void begin(final int p_begin_1_) {
        Tezzelator.t.startDrawing(p_begin_1_);
    }
    
    public void begin() {
        Tezzelator.t.startDrawingQuads();
    }
    
    public void vertexUV(final double p_vertexUV_1_, final double p_vertexUV_3_, final double p_vertexUV_5_, final double p_vertexUV_7_, final double p_vertexUV_9_) {
        Tezzelator.t.addVertexWithUV(p_vertexUV_1_, p_vertexUV_3_, p_vertexUV_5_, p_vertexUV_7_, p_vertexUV_9_);
    }
    
    public void color(final int p_color_1_, final int p_color_2_) {
        Tezzelator.t.setColorRGBA_I(p_color_1_, p_color_2_);
    }
    
    public void offset(final double p_offset_1_, final double p_offset_3_, final double p_offset_5_) {
        Tezzelator.t.setTranslation(p_offset_1_, p_offset_3_, p_offset_5_);
    }
    
    public void color(final int p_color_1_, final int p_color_2_, final int p_color_3_, final int p_color_4_) {
        Tezzelator.t.setColorRGBA(p_color_1_, p_color_2_, p_color_3_, p_color_4_);
    }
    
    public void addOffset(final float p_addOffset_1_, final float p_addOffset_2_, final float p_addOffset_3_) {
        Tezzelator.t.addTranslation(p_addOffset_1_, p_addOffset_2_, p_addOffset_3_);
    }
    
    public void tex(final double p_tex_1_, final double p_tex_3_) {
        Tezzelator.t.setTextureUV(p_tex_1_, p_tex_3_);
    }
    
    public void color(final byte p_color_1_, final byte p_color_2_, final byte p_color_3_) {
        Tezzelator.t.func_154352_a(p_color_1_, p_color_2_, p_color_3_);
    }
    
    static {
        Tezzelator.t = Tessellator.instance;
        instance = new Tezzelator();
    }
}
