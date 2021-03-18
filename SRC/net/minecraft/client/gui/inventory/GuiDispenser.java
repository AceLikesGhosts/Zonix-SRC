package net.minecraft.client.gui.inventory;

import net.minecraft.util.*;
import net.minecraft.tileentity.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.client.resources.*;
import org.lwjgl.opengl.*;

public class GuiDispenser extends GuiContainer
{
    private static final ResourceLocation field_147088_v;
    public TileEntityDispenser field_147089_u;
    private static final String __OBFID = "CL_00000765";
    
    public GuiDispenser(final InventoryPlayer p_i46384_1_, final TileEntityDispenser p_i46384_2_) {
        super(new ContainerDispenser(p_i46384_1_, p_i46384_2_));
        this.field_147089_u = p_i46384_2_;
    }
    
    @Override
    protected void func_146979_b(final int p_146979_1_, final int p_146979_2_) {
        final String var3 = this.field_147089_u.isInventoryNameLocalized() ? this.field_147089_u.getInventoryName() : I18n.format(this.field_147089_u.getInventoryName(), new Object[0]);
        this.fontRendererObj.drawString(var3, this.field_146999_f / 2 - this.fontRendererObj.getStringWidth(var3) / 2, 6, 4210752);
        this.fontRendererObj.drawString(I18n.format("container.inventory", new Object[0]), 8, this.field_147000_g - 96 + 2, 4210752);
    }
    
    @Override
    protected void func_146976_a(final float p_146976_1_, final int p_146976_2_, final int p_146976_3_) {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.getTextureManager().bindTexture(GuiDispenser.field_147088_v);
        final int var4 = (this.width - this.field_146999_f) / 2;
        final int var5 = (this.height - this.field_147000_g) / 2;
        this.drawTexturedModalRect(var4, var5, 0, 0, this.field_146999_f, this.field_147000_g);
    }
    
    static {
        field_147088_v = new ResourceLocation("textures/gui/container/dispenser.png");
    }
}
