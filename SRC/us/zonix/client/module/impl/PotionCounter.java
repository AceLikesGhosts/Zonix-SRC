package us.zonix.client.module.impl;

import us.zonix.client.module.modules.*;
import us.zonix.client.setting.impl.*;
import net.minecraft.item.*;
import us.zonix.client.util.*;

public final class PotionCounter extends AbstractModule
{
    private static final StringSetting DISPLAY_TYPE;
    private static final BooleanSetting DRAW_BACKGROUND;
    private static final ColorSetting FOREGROUND;
    private static final ColorSetting BACKGROUND;
    private int pots;
    
    public PotionCounter() {
        super("Pot Counter");
        this.setEnabled(false);
        this.addSetting(new LabelSetting("General Settings"));
        this.addSetting(PotionCounter.DRAW_BACKGROUND);
        this.addSetting(PotionCounter.DISPLAY_TYPE);
        this.addSetting(new LabelSetting("Color Settings"));
        this.addSetting(PotionCounter.FOREGROUND);
        this.addSetting(PotionCounter.BACKGROUND);
    }
    
    @Override
    public void onPostPlayerUpdate() {
        this.pots = 0;
        for (int i = 0; i < this.mc.thePlayer.inventory.mainInventory.length; ++i) {
            final ItemStack itemStack = this.mc.thePlayer.inventory.mainInventory[i];
            if (itemStack != null && itemStack.getUnlocalizedName().equals("item.potion") && (itemStack.getItemDamage() == 16421 || itemStack.getItemDamage() == 16453)) {
                this.pots += itemStack.stackSize;
            }
        }
    }
    
    @Override
    public void renderReal() {
        String text = this.pots + " Potions";
        if (PotionCounter.DISPLAY_TYPE.getIndex() == 1) {
            text = "Potions: " + this.pots;
        }
        else if (this.pots == 1) {
            text = "1 Potion";
        }
        float width = (float)(this.mc.fontRenderer.getStringWidth(text) + 4);
        if (width < 70.0f) {
            width = 70.0f;
        }
        this.setWidth((int)width);
        this.setHeight(13);
        if (PotionCounter.DRAW_BACKGROUND.getValue()) {
            RenderUtil.drawRect(this.getX(), this.getY(), this.getX() + this.getWidth(), this.getY() + this.getHeight(), PotionCounter.BACKGROUND.getValue());
        }
        this.mc.fontRenderer.drawString(text, this.getX() + this.getWidth() / 2 - this.mc.fontRenderer.getStringWidth(text) / 2, this.y + 3.0f, PotionCounter.FOREGROUND.getValue());
    }
    
    static {
        DISPLAY_TYPE = new StringSetting("Display Type", new String[] { "0 Potions", "Potion: 0" });
        DRAW_BACKGROUND = new BooleanSetting("Draw background", true);
        FOREGROUND = new ColorSetting("Foreground", -65536);
        BACKGROUND = new ColorSetting("Background", 1862270976);
    }
}
