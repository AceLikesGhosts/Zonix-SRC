package us.zonix.client.module.impl;

import us.zonix.client.module.modules.*;
import net.minecraft.client.gui.*;
import us.zonix.client.setting.impl.*;
import net.minecraft.potion.*;
import net.minecraft.util.*;
import org.lwjgl.opengl.*;
import us.zonix.client.util.*;
import java.util.*;

public final class PotionEffects extends AbstractModule
{
    private static final ResourceLocation INVENTORY_RESOURCE;
    public static final BooleanSetting SHOW_IN_INVENTORY;
    private List<PotionEffect> potionEffects;
    
    public PotionEffects() {
        super("Potion Status");
        this.potionEffects = new ArrayList<PotionEffect>();
        this.x = 4.0f;
        this.y = (float)(new ScaledResolution(this.mc).getScaledHeight() / 2 - this.getHeight());
        this.addSetting(new LabelSetting("General Settings"));
        this.addSetting(new BooleanSetting("Show effect icon", true));
        this.addSetting(new BooleanSetting("Show effect name", true));
        this.addSetting(new BooleanSetting("Show duration", true));
        this.addSetting(PotionEffects.SHOW_IN_INVENTORY);
        this.addSetting(new LabelSetting("Color Settings"));
        this.addSetting(new ColorSetting("Effect Color", -65536));
        this.addSetting(new ColorSetting("Duration Color", -65536));
    }
    
    @Override
    public void renderReal() {
        if (this.mc.thePlayer == null) {
            return;
        }
        this.potionEffects.clear();
        final Collection<PotionEffect> potionEffects = (Collection<PotionEffect>)this.mc.thePlayer.getActivePotionEffects();
        this.render(potionEffects);
    }
    
    @Override
    public void renderPreview() {
        if (this.mc.thePlayer == null) {
            return;
        }
        if (!this.mc.thePlayer.getActivePotionEffects().isEmpty()) {
            this.renderReal();
            return;
        }
        if (this.potionEffects.isEmpty()) {
            this.potionEffects.add(new PotionEffect(Potion.damageBoost.getId(), 600, 1));
            this.potionEffects.add(new PotionEffect(Potion.moveSpeed.getId(), 1800, 1));
            this.potionEffects.add(new PotionEffect(Potion.fireResistance.getId(), 8400));
        }
        this.potionEffects.removeIf(potionEffect -> potionEffect.getDuration() <= 0);
        this.render(this.potionEffects);
    }
    
    private void render(final Collection<PotionEffect> potionEffects) {
        int height = 0;
        int maxWidth = 0;
        for (final PotionEffect effect : potionEffects) {
            if (this.getBooleanSetting("Show effect name").getValue()) {
                final String label = StatCollector.translateToLocal(effect.getEffectName()) + this.getAmplifierNumerals(effect.getAmplifier());
                final int width = this.mc.fontRenderer.getStringWidth(label) + 24;
                this.mc.fontRenderer.drawStringWithShadow(label, this.getX() + 22.0f, this.getY() + height + 2.0f, this.getColorSetting("Effect Color").getValue());
                if (width > maxWidth) {
                    maxWidth = width;
                }
            }
            if (this.getBooleanSetting("Show duration").getValue()) {
                final String duration = Potion.getDurationString(effect);
                final int width = this.mc.fontRenderer.getStringWidth(duration) + 24;
                this.mc.fontRenderer.drawStringWithShadow(duration, this.getX() + 22.0f, this.getY() + height + 12.0f, this.getColorSetting("Duration Color").getValue());
                if (width > maxWidth) {
                    maxWidth = width;
                }
            }
            if (this.getBooleanSetting("Show effect icon").getValue()) {
                final Potion potion = Potion.potionTypes[effect.getPotionID()];
                if (potion.hasStatusIcon()) {
                    GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                    this.mc.getTextureManager().bindTexture(PotionEffects.INVENTORY_RESOURCE);
                    final int index = potion.getStatusIconIndex();
                    RenderUtil.drawTexturedRect(this.getX(), this.getY() + height + 2.0f, index % 8 * 18, 198 + index / 8 * 18, 18, 18);
                }
            }
            height += 24;
        }
        this.setWidth(maxWidth);
        this.setHeight(height);
    }
    
    private String getAmplifierNumerals(final int amplifier) {
        switch (amplifier) {
            case 0: {
                return " I";
            }
            case 1: {
                return " II";
            }
            case 2: {
                return " III";
            }
            case 3: {
                return " IV";
            }
            case 4: {
                return " V";
            }
            case 5: {
                return " VI";
            }
            case 6: {
                return " VII";
            }
            case 7: {
                return " VIII";
            }
            case 8: {
                return " IX";
            }
            case 9: {
                return " X";
            }
            default: {
                if (amplifier < 1) {
                    return "";
                }
                return " " + amplifier + 1;
            }
        }
    }
    
    static {
        INVENTORY_RESOURCE = new ResourceLocation("textures/gui/container/inventory.png");
        SHOW_IN_INVENTORY = new BooleanSetting("Show in inventory", true);
    }
}
