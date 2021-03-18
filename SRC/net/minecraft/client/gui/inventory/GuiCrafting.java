package net.minecraft.client.gui.inventory;

import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.inventory.*;
import net.minecraft.client.resources.*;
import org.lwjgl.opengl.*;

public class GuiCrafting extends GuiContainer
{
    private static final ResourceLocation field_147019_u;
    private static final String __OBFID = "CL_00000750";
    
    public GuiCrafting(final InventoryPlayer p_i46397_1_, final World p_i46397_2_, final int p_i46397_3_, final int p_i46397_4_, final int p_i46397_5_) {
        super(new ContainerWorkbench(p_i46397_1_, p_i46397_2_, p_i46397_3_, p_i46397_4_, p_i46397_5_));
    }
    
    @Override
    protected void func_146979_b(final int p_146979_1_, final int p_146979_2_) {
        this.fontRendererObj.drawString(I18n.format("container.crafting", new Object[0]), 28, 6, 4210752);
        this.fontRendererObj.drawString(I18n.format("container.inventory", new Object[0]), 8, this.field_147000_g - 96 + 2, 4210752);
    }
    
    @Override
    protected void func_146976_a(final float p_146976_1_, final int p_146976_2_, final int p_146976_3_) {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.getTextureManager().bindTexture(GuiCrafting.field_147019_u);
        final int var4 = (this.width - this.field_146999_f) / 2;
        final int var5 = (this.height - this.field_147000_g) / 2;
        this.drawTexturedModalRect(var4, var5, 0, 0, this.field_146999_f, this.field_147000_g);
    }
    
    static {
        field_147019_u = new ResourceLocation("textures/gui/container/crafting_table.png");
    }
}
