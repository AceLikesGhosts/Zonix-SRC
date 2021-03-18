package com.thevoxelbox.voxelmap.gui;

import net.minecraft.client.gui.*;
import com.thevoxelbox.voxelmap.*;
import java.text.*;
import java.util.*;
import net.minecraft.util.*;
import org.lwjgl.input.*;
import net.minecraft.client.renderer.*;
import org.lwjgl.opengl.*;
import com.thevoxelbox.voxelmap.util.*;

class GuiSlotMobs extends GuiSlot
{
    final GuiMobs parentGui;
    private ArrayList<String> mobNames;
    private RadarSettingsManager options;
    
    public GuiSlotMobs(final GuiMobs par1GuiMobs) {
        super(par1GuiMobs.options.game, par1GuiMobs.getWidth(), par1GuiMobs.getHeight(), 32, par1GuiMobs.getHeight() - 41 + 4, 18);
        this.parentGui = par1GuiMobs;
        this.options = this.parentGui.options;
        this.mobNames = new ArrayList<String>();
        for (final EnumMobs mob : EnumMobs.values()) {
            if (mob.isTopLevelUnit && ((mob.isHostile && this.options.showHostiles) || (mob.isNeutral && this.options.showNeutrals))) {
                this.mobNames.add(mob.name);
            }
        }
        for (final CustomMob mob2 : CustomMobsManager.mobs) {
            if ((mob2.isHostile && this.options.showHostiles) || (mob2.isNeutral && this.options.showNeutrals)) {
                this.mobNames.add(mob2.name);
            }
        }
        final Collator collator = I18nUtils.getLocaleAwareCollator();
        Collections.sort(this.mobNames, new Comparator<String>() {
            @Override
            public int compare(String name1, String name2) {
                name1 = getTranslatedName(name1);
                name2 = getTranslatedName(name2);
                return collator.compare(name1, name2);
            }
        });
    }
    
    private static String getTranslatedName(String name) {
        name = StatCollector.translateToLocal("entity." + name + ".name");
        name = name.replaceAll("^entity.", "").replaceAll(".name$", "");
        return name;
    }
    
    @Override
    protected int getSize() {
        return this.mobNames.size();
    }
    
    @Override
    protected void elementClicked(final int par1, final boolean par2, final int par3, final int par4) {
        this.parentGui.setSelectedMob(this.mobNames.get(par1));
        final int leftEdge = this.parentGui.getWidth() / 2 - 92 - 16;
        final byte padding = 3;
        final int width = 215;
        if (this.field_148150_g >= leftEdge + width - 16 - padding && this.field_148150_g <= leftEdge + width + padding) {
            this.parentGui.toggleMobVisibility();
        }
        else if (par2) {
            Mouse.next();
            this.parentGui.toggleMobVisibility();
        }
    }
    
    @Override
    protected boolean isSelected(final int par1) {
        return this.mobNames.get(par1).equals(this.parentGui.selectedMobName);
    }
    
    @Override
    protected int func_148138_e() {
        return this.getSize() * 18;
    }
    
    @Override
    protected void drawBackground() {
        this.parentGui.drawDefaultBackground();
    }
    
    @Override
    protected void drawSlot(final int par1, final int par2, final int par3, final int par4, final Tessellator par5Tessellator, final int par6, final int par7) {
        final String name = this.mobNames.get(par1);
        boolean isHostile = false;
        boolean isNeutral = false;
        boolean isEnabled = true;
        final EnumMobs mob = EnumMobs.getMobByName(name);
        if (mob != null) {
            isHostile = mob.isHostile;
            isNeutral = mob.isNeutral;
            isEnabled = mob.enabled;
        }
        else {
            final CustomMob customMob = CustomMobsManager.getCustomMobByName(name);
            if (customMob != null) {
                isHostile = customMob.isHostile;
                isNeutral = customMob.isNeutral;
                isEnabled = customMob.enabled;
            }
        }
        final int red = isHostile ? 255 : 0;
        final int green = isNeutral ? 255 : 0;
        final int color = -16777216 + (red << 16) + (green << 8) + 0;
        this.parentGui.drawCenteredString(this.parentGui.getFontRenderer(), getTranslatedName(name), this.parentGui.getWidth() / 2, par3 + 3, color);
        final byte padding = 3;
        if (this.field_148150_g >= par2 - padding && this.field_148162_h >= par3 && this.field_148150_g <= par2 + 215 + padding && this.field_148162_h <= par3 + this.field_148149_f) {
            String tooltip;
            if (this.field_148150_g >= par2 + 215 - 16 - padding && this.field_148150_g <= par2 + 215 + padding) {
                tooltip = (isEnabled ? I18nUtils.getString("options.minimap.mobs.disable") : I18nUtils.getString("options.minimap.mobs.enable"));
            }
            else {
                tooltip = (isEnabled ? I18nUtils.getString("options.minimap.mobs.enabled") : I18nUtils.getString("options.minimap.mobs.disabled"));
            }
            GuiMobs.setTooltip(this.parentGui, tooltip);
        }
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GLUtils.img("textures/gui/container/inventory.png");
        final int xOffset = isEnabled ? 72 : 90;
        final int yOffset = 216;
        this.parentGui.drawTexturedModalRect(par2 + 198, par3 - 2, xOffset, yOffset, 16, 16);
    }
}
