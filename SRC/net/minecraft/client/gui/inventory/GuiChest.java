package net.minecraft.client.gui.inventory;

import net.minecraft.util.*;
import net.minecraft.inventory.*;
import net.minecraft.client.resources.*;
import org.lwjgl.opengl.*;

public class GuiChest extends GuiContainer
{
    private static final ResourceLocation field_147017_u;
    private IInventory field_147016_v;
    private IInventory field_147015_w;
    private int field_147018_x;
    private static final String __OBFID = "CL_00000749";
    
    public GuiChest(final IInventory p_i46315_1_, final IInventory p_i46315_2_) {
        super(new ContainerChest(p_i46315_1_, p_i46315_2_));
        this.field_147016_v = p_i46315_1_;
        this.field_147015_w = p_i46315_2_;
        this.field_146291_p = false;
        final short var3 = 222;
        final int var4 = var3 - 108;
        this.field_147018_x = p_i46315_2_.getSizeInventory() / 9;
        this.field_147000_g = var4 + this.field_147018_x * 18;
    }
    
    @Override
    protected void func_146979_b(final int p_146979_1_, final int p_146979_2_) {
        this.fontRendererObj.drawString(this.field_147015_w.isInventoryNameLocalized() ? this.field_147015_w.getInventoryName() : I18n.format(this.field_147015_w.getInventoryName(), new Object[0]), 8, 6, 4210752);
        this.fontRendererObj.drawString(this.field_147016_v.isInventoryNameLocalized() ? this.field_147016_v.getInventoryName() : I18n.format(this.field_147016_v.getInventoryName(), new Object[0]), 8, this.field_147000_g - 96 + 2, 4210752);
    }
    
    @Override
    protected void func_146976_a(final float p_146976_1_, final int p_146976_2_, final int p_146976_3_) {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.getTextureManager().bindTexture(GuiChest.field_147017_u);
        final int var4 = (this.width - this.field_146999_f) / 2;
        final int var5 = (this.height - this.field_147000_g) / 2;
        this.drawTexturedModalRect(var4, var5, 0, 0, this.field_146999_f, this.field_147018_x * 18 + 17);
        this.drawTexturedModalRect(var4, var5 + this.field_147018_x * 18 + 17, 0, 126, this.field_146999_f, 96);
    }
    
    static {
        field_147017_u = new ResourceLocation("textures/gui/container/generic_54.png");
    }
}
