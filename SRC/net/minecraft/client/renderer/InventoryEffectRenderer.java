package net.minecraft.client.renderer;

import net.minecraft.client.gui.inventory.*;
import net.minecraft.inventory.*;
import us.zonix.client.module.impl.*;
import us.zonix.client.*;
import org.lwjgl.opengl.*;
import net.minecraft.potion.*;
import net.minecraft.client.resources.*;
import java.util.*;

public abstract class InventoryEffectRenderer extends GuiContainer
{
    private boolean field_147045_u;
    private static final String __OBFID = "CL_00000755";
    
    public InventoryEffectRenderer(final Container p_i1089_1_) {
        super(p_i1089_1_);
    }
    
    @Override
    public void initGui() {
        super.initGui();
        if (Client.getInstance().getModuleManager().getModule(PotionEffects.class).isEnabled() && !PotionEffects.SHOW_IN_INVENTORY.getValue()) {
            return;
        }
        if (!this.mc.thePlayer.getActivePotionEffects().isEmpty()) {
            this.field_147045_u = true;
        }
    }
    
    @Override
    public void drawScreen(final int p_73863_1_, final int p_73863_2_, final float p_73863_3_) {
        super.drawScreen(p_73863_1_, p_73863_2_, p_73863_3_);
        if (this.field_147045_u) {
            this.func_147044_g();
        }
    }
    
    private void func_147044_g() {
        if (Client.getInstance().getModuleManager().getModule(PotionEffects.class).isEnabled() && !PotionEffects.SHOW_IN_INVENTORY.getValue()) {
            return;
        }
        final int var1 = this.field_147003_i - 124;
        int var2 = this.field_147009_r;
        final boolean var3 = true;
        final Collection var4 = this.mc.thePlayer.getActivePotionEffects();
        if (!var4.isEmpty()) {
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            GL11.glDisable(2896);
            int var5 = 33;
            if (var4.size() > 5) {
                var5 = 132 / (var4.size() - 1);
            }
            for (final PotionEffect var7 : this.mc.thePlayer.getActivePotionEffects()) {
                final Potion var8 = Potion.potionTypes[var7.getPotionID()];
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                this.mc.getTextureManager().bindTexture(InventoryEffectRenderer.field_147001_a);
                this.drawTexturedModalRect(var1, var2, 0, 166, 140, 32);
                if (var8.hasStatusIcon()) {
                    final int var9 = var8.getStatusIconIndex();
                    this.drawTexturedModalRect(var1 + 6, var2 + 7, 0 + var9 % 8 * 18, 198 + var9 / 8 * 18, 18, 18);
                }
                String var10 = I18n.format(var8.getName(), new Object[0]);
                if (var7.getAmplifier() == 1) {
                    var10 = var10 + " " + I18n.format("enchantment.level.2", new Object[0]);
                }
                else if (var7.getAmplifier() == 2) {
                    var10 = var10 + " " + I18n.format("enchantment.level.3", new Object[0]);
                }
                else if (var7.getAmplifier() == 3) {
                    var10 = var10 + " " + I18n.format("enchantment.level.4", new Object[0]);
                }
                this.fontRendererObj.drawStringWithShadow(var10, var1 + 10 + 18, var2 + 6, 16777215);
                final String var11 = Potion.getDurationString(var7);
                this.fontRendererObj.drawStringWithShadow(var11, var1 + 10 + 18, var2 + 6 + 10, 8355711);
                var2 += var5;
            }
        }
    }
}
