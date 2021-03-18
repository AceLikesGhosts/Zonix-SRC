package net.minecraft.client.gui.inventory;

import net.minecraft.util.*;
import net.minecraft.tileentity.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.client.resources.*;
import org.lwjgl.opengl.*;

public class GuiBrewingStand extends GuiContainer
{
    private static final ResourceLocation field_147014_u;
    private TileEntityBrewingStand field_147013_v;
    private static final String __OBFID = "CL_00000746";
    
    public GuiBrewingStand(final InventoryPlayer p_i1081_1_, final TileEntityBrewingStand p_i1081_2_) {
        super(new ContainerBrewingStand(p_i1081_1_, p_i1081_2_));
        this.field_147013_v = p_i1081_2_;
    }
    
    @Override
    protected void func_146979_b(final int p_146979_1_, final int p_146979_2_) {
        final String var3 = this.field_147013_v.isInventoryNameLocalized() ? this.field_147013_v.getInventoryName() : I18n.format(this.field_147013_v.getInventoryName(), new Object[0]);
        this.fontRendererObj.drawString(var3, this.field_146999_f / 2 - this.fontRendererObj.getStringWidth(var3) / 2, 6, 4210752);
        this.fontRendererObj.drawString(I18n.format("container.inventory", new Object[0]), 8, this.field_147000_g - 96 + 2, 4210752);
    }
    
    @Override
    protected void func_146976_a(final float p_146976_1_, final int p_146976_2_, final int p_146976_3_) {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.getTextureManager().bindTexture(GuiBrewingStand.field_147014_u);
        final int var4 = (this.width - this.field_146999_f) / 2;
        final int var5 = (this.height - this.field_147000_g) / 2;
        this.drawTexturedModalRect(var4, var5, 0, 0, this.field_146999_f, this.field_147000_g);
        final int var6 = this.field_147013_v.func_145935_i();
        if (var6 > 0) {
            int var7 = (int)(28.0f * (1.0f - var6 / 400.0f));
            if (var7 > 0) {
                this.drawTexturedModalRect(var4 + 97, var5 + 16, 176, 0, 9, var7);
            }
            final int var8 = var6 / 2 % 7;
            switch (var8) {
                case 0: {
                    var7 = 29;
                    break;
                }
                case 1: {
                    var7 = 24;
                    break;
                }
                case 2: {
                    var7 = 20;
                    break;
                }
                case 3: {
                    var7 = 16;
                    break;
                }
                case 4: {
                    var7 = 11;
                    break;
                }
                case 5: {
                    var7 = 6;
                    break;
                }
                case 6: {
                    var7 = 0;
                    break;
                }
            }
            if (var7 > 0) {
                this.drawTexturedModalRect(var4 + 65, var5 + 14 + 29 - var7, 185, 29 - var7, 12, var7);
            }
        }
    }
    
    static {
        field_147014_u = new ResourceLocation("textures/gui/container/brewing_stand.png");
    }
}
