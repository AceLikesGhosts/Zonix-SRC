package net.minecraft.client.renderer.tileentity;

import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.world.*;
import net.minecraft.client.gui.*;

public abstract class TileEntitySpecialRenderer
{
    protected TileEntityRendererDispatcher field_147501_a;
    private static final String __OBFID = "CL_00000964";
    
    public abstract void renderTileEntityAt(final TileEntity p0, final double p1, final double p2, final double p3, final float p4);
    
    protected void bindTexture(final ResourceLocation p_147499_1_) {
        final TextureManager var2 = this.field_147501_a.field_147553_e;
        if (var2 != null) {
            var2.bindTexture(p_147499_1_);
        }
    }
    
    public void func_147497_a(final TileEntityRendererDispatcher p_147497_1_) {
        this.field_147501_a = p_147497_1_;
    }
    
    public void func_147496_a(final World p_147496_1_) {
    }
    
    public FontRenderer func_147498_b() {
        return this.field_147501_a.func_147548_a();
    }
}
