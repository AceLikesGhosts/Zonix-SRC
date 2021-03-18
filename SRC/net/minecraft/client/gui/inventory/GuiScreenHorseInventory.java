package net.minecraft.client.gui.inventory;

import net.minecraft.util.*;
import net.minecraft.entity.passive.*;
import net.minecraft.inventory.*;
import net.minecraft.client.resources.*;
import org.lwjgl.opengl.*;
import net.minecraft.entity.*;

public class GuiScreenHorseInventory extends GuiContainer
{
    private static final ResourceLocation field_147031_u;
    private IInventory field_147030_v;
    private IInventory field_147029_w;
    private EntityHorse field_147034_x;
    private float field_147033_y;
    private float field_147032_z;
    private static final String __OBFID = "CL_00000760";
    
    public GuiScreenHorseInventory(final IInventory p_i1093_1_, final IInventory p_i1093_2_, final EntityHorse p_i1093_3_) {
        super(new ContainerHorseInventory(p_i1093_1_, p_i1093_2_, p_i1093_3_));
        this.field_147030_v = p_i1093_1_;
        this.field_147029_w = p_i1093_2_;
        this.field_147034_x = p_i1093_3_;
        this.field_146291_p = false;
    }
    
    @Override
    protected void func_146979_b(final int p_146979_1_, final int p_146979_2_) {
        this.fontRendererObj.drawString(this.field_147029_w.isInventoryNameLocalized() ? this.field_147029_w.getInventoryName() : I18n.format(this.field_147029_w.getInventoryName(), new Object[0]), 8, 6, 4210752);
        this.fontRendererObj.drawString(this.field_147030_v.isInventoryNameLocalized() ? this.field_147030_v.getInventoryName() : I18n.format(this.field_147030_v.getInventoryName(), new Object[0]), 8, this.field_147000_g - 96 + 2, 4210752);
    }
    
    @Override
    protected void func_146976_a(final float p_146976_1_, final int p_146976_2_, final int p_146976_3_) {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.getTextureManager().bindTexture(GuiScreenHorseInventory.field_147031_u);
        final int var4 = (this.width - this.field_146999_f) / 2;
        final int var5 = (this.height - this.field_147000_g) / 2;
        this.drawTexturedModalRect(var4, var5, 0, 0, this.field_146999_f, this.field_147000_g);
        if (this.field_147034_x.isChested()) {
            this.drawTexturedModalRect(var4 + 79, var5 + 17, 0, this.field_147000_g, 90, 54);
        }
        if (this.field_147034_x.func_110259_cr()) {
            this.drawTexturedModalRect(var4 + 7, var5 + 35, 0, this.field_147000_g + 54, 18, 18);
        }
        GuiInventory.func_147046_a(var4 + 51, var5 + 60, 17, var4 + 51 - this.field_147033_y, var5 + 75 - 50 - this.field_147032_z, this.field_147034_x);
    }
    
    @Override
    public void drawScreen(final int p_73863_1_, final int p_73863_2_, final float p_73863_3_) {
        this.field_147033_y = (float)p_73863_1_;
        this.field_147032_z = (float)p_73863_2_;
        super.drawScreen(p_73863_1_, p_73863_2_, p_73863_3_);
    }
    
    static {
        field_147031_u = new ResourceLocation("textures/gui/container/horse.png");
    }
}
