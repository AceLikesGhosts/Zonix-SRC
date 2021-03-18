package us.zonix.client.module.impl;

import us.zonix.client.module.modules.*;
import us.zonix.client.setting.impl.*;
import net.minecraft.client.renderer.entity.*;
import us.zonix.client.util.*;
import us.zonix.client.setting.*;
import net.minecraft.item.*;
import org.lwjgl.opengl.*;
import java.util.*;

public final class ArmorStatus extends AbstractModule
{
    private static final LabelSetting GENERAL_LABEL;
    public static final StringSetting DAMAGE_THRESHOLD_TYPE;
    public static final StringSetting DAMAGE_DISPLAY_TYPE;
    private static final StringSetting LIST_MODE;
    public static final BooleanSetting DAMAGE_OVERLAY;
    public static final BooleanSetting ARMOR_DAMAGE;
    public static final BooleanSetting ITEM_DAMAGE;
    public static final BooleanSetting MAX_DAMAGE;
    public static final BooleanSetting ITEM_COUNT;
    public static final BooleanSetting ITEM_NAME;
    public static final BooleanSetting HELD_ITEM;
    private static final LabelSetting COLOR_LABEL;
    private static final ColorSetting FULL_COLOR;
    private static final ColorSetting EIGHTY_COLOR;
    private static final ColorSetting SIXTY_COLOR;
    private static final ColorSetting FORTY_COLOR;
    private static final ColorSetting QUARTER_COLOR;
    private static final ColorSetting TEN_COLOR;
    public static final RenderItem ITEM_RENDERER;
    private final List<HUDElement> elements;
    private final List<ISetting> settings;
    
    public ArmorStatus() {
        super("Armor Status");
        this.elements = new ArrayList<HUDElement>();
        (this.settings = new LinkedList<ISetting>()).add(ArmorStatus.GENERAL_LABEL);
        this.settings.add(ArmorStatus.DAMAGE_OVERLAY);
        this.settings.add(ArmorStatus.ARMOR_DAMAGE);
        this.settings.add(ArmorStatus.ITEM_DAMAGE);
        this.settings.add(ArmorStatus.MAX_DAMAGE);
        this.settings.add(ArmorStatus.HELD_ITEM);
        this.settings.add(ArmorStatus.ITEM_COUNT);
        this.settings.add(ArmorStatus.ITEM_NAME);
        this.settings.add(ArmorStatus.DAMAGE_THRESHOLD_TYPE);
        this.settings.add(ArmorStatus.DAMAGE_DISPLAY_TYPE);
        this.settings.add(ArmorStatus.LIST_MODE);
        this.settings.add(ArmorStatus.COLOR_LABEL);
        this.settings.add(ArmorStatus.FULL_COLOR);
        this.settings.add(ArmorStatus.EIGHTY_COLOR);
        this.settings.add(ArmorStatus.SIXTY_COLOR);
        this.settings.add(ArmorStatus.FORTY_COLOR);
        this.settings.add(ArmorStatus.QUARTER_COLOR);
        this.settings.add(ArmorStatus.TEN_COLOR);
        this.addSetting(ArmorStatus.DAMAGE_THRESHOLD_TYPE);
        this.addSetting(ArmorStatus.DAMAGE_DISPLAY_TYPE);
        this.addSetting(ArmorStatus.LIST_MODE);
        this.addSetting(ArmorStatus.DAMAGE_OVERLAY);
        this.addSetting(ArmorStatus.ARMOR_DAMAGE);
        this.addSetting(ArmorStatus.ITEM_DAMAGE);
        this.addSetting(ArmorStatus.MAX_DAMAGE);
        this.addSetting(ArmorStatus.ITEM_COUNT);
        this.addSetting(ArmorStatus.ITEM_NAME);
        this.addSetting(ArmorStatus.FULL_COLOR);
        this.addSetting(ArmorStatus.EIGHTY_COLOR);
        this.addSetting(ArmorStatus.SIXTY_COLOR);
        this.addSetting(ArmorStatus.FORTY_COLOR);
        this.addSetting(ArmorStatus.QUARTER_COLOR);
        this.addSetting(ArmorStatus.TEN_COLOR);
    }
    
    @Override
    public List<ISetting> getSortedSettings() {
        return this.settings;
    }
    
    @Override
    public void renderPreview() {
        this.elements.clear();
        final ItemStack[] armor = { new ItemStack(Item.getItemById(310)), new ItemStack(Item.getItemById(311)), new ItemStack(Item.getItemById(312)), new ItemStack(Item.getItemById(313)) };
        for (int i = 0; i < 4; ++i) {
            ItemStack itemStack = this.mc.thePlayer.inventory.armorInventory[i];
            if (itemStack == null) {
                itemStack = armor[i];
            }
            this.elements.add(new HUDElement(itemStack, 16, 16, 2, true));
        }
        if (ArmorStatus.HELD_ITEM.getValue()) {
            if (this.mc.thePlayer.getCurrentEquippedItem() == null) {
                this.elements.add(new HUDElement(new ItemStack(Item.getItemById(276)), 16, 16, 2, false));
            }
            else {
                this.elements.add(new HUDElement(this.mc.thePlayer.getCurrentEquippedItem(), 16, 16, 2, false));
            }
        }
        GL11.glPushMatrix();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.displayArmorStatus();
        GL11.glPopMatrix();
    }
    
    @Override
    public void renderReal() {
        this.getHUDElements();
        if (!this.elements.isEmpty()) {
            GL11.glPushMatrix();
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            this.displayArmorStatus();
            GL11.glPopMatrix();
        }
    }
    
    private void getHUDElements() {
        this.elements.clear();
        for (int i = 3; i >= -1; --i) {
            ItemStack itemStack = null;
            if (i == -1 && ArmorStatus.HELD_ITEM.getValue()) {
                itemStack = this.mc.thePlayer.getCurrentEquippedItem();
            }
            else if (i != -1) {
                itemStack = this.mc.thePlayer.inventory.armorInventory[i];
            }
            if (itemStack != null) {
                this.elements.add(new HUDElement(itemStack, 16, 16, 2, i > -1));
            }
        }
    }
    
    public static int getColorCode(final int percentage) {
        if (percentage <= 10) {
            return ArmorStatus.TEN_COLOR.getValue();
        }
        if (percentage <= 25) {
            return ArmorStatus.QUARTER_COLOR.getValue();
        }
        if (percentage <= 40) {
            return ArmorStatus.FORTY_COLOR.getValue();
        }
        if (percentage <= 60) {
            return ArmorStatus.SIXTY_COLOR.getValue();
        }
        if (percentage <= 80) {
            return ArmorStatus.EIGHTY_COLOR.getValue();
        }
        return ArmorStatus.FULL_COLOR.getValue();
    }
    
    private void displayArmorStatus() {
        if (this.elements.size() > 0) {
            final int yOffset = ArmorStatus.ITEM_NAME.getValue() ? 18 : 16;
            if (ArmorStatus.LIST_MODE.getValue().equalsIgnoreCase("vertical")) {
                int yBase = 0;
                int heWidth = 0;
                for (final HUDElement e : this.elements) {
                    yBase += yOffset;
                    if (e.width() > heWidth) {
                        heWidth = e.width();
                    }
                }
                this.setHeight(yBase);
                this.setWidth(heWidth);
                for (final HUDElement e : this.elements) {
                    e.renderToHud(this.getX(), this.getY() - yBase + this.getHeight());
                    yBase -= yOffset;
                    if (e.width() > heWidth) {
                        heWidth = e.width();
                    }
                }
            }
            else if (ArmorStatus.LIST_MODE.getValue().equalsIgnoreCase("horizontal")) {
                final int yBase = 0;
                int prevX = 0;
                int heHeight = 0;
                for (final HUDElement e2 : this.elements) {
                    e2.renderToHud(this.getX() + prevX, this.getY() + yBase);
                    prevX += e2.width();
                    if (e2.height() > heHeight) {
                        heHeight += e2.height();
                    }
                }
                this.setHeight(heHeight);
                this.setWidth(prevX);
            }
        }
    }
    
    static {
        GENERAL_LABEL = new LabelSetting("General Settings");
        DAMAGE_THRESHOLD_TYPE = new StringSetting("Damage Type", new String[] { "value", "percent" });
        DAMAGE_DISPLAY_TYPE = new StringSetting("Damage Display Type", new String[] { "value", "percent", "none" });
        LIST_MODE = new StringSetting("List Mode", new String[] { "vertical", "horizontal" });
        DAMAGE_OVERLAY = new BooleanSetting("Damage Overlay", true);
        ARMOR_DAMAGE = new BooleanSetting("Armor Damage", true);
        ITEM_DAMAGE = new BooleanSetting("Item Damage", true);
        MAX_DAMAGE = new BooleanSetting("Max Damage", false);
        ITEM_COUNT = new BooleanSetting("Item Count", true);
        ITEM_NAME = new BooleanSetting("Item Name", false);
        HELD_ITEM = new BooleanSetting("Held item", true);
        COLOR_LABEL = new LabelSetting("Color Settings");
        FULL_COLOR = new ColorSetting("100% Color", -1);
        EIGHTY_COLOR = new ColorSetting("80% Color", -5592406);
        SIXTY_COLOR = new ColorSetting("60% Color", -171);
        FORTY_COLOR = new ColorSetting("40% Color", -22016);
        QUARTER_COLOR = new ColorSetting("25% Color", -43691);
        TEN_COLOR = new ColorSetting("10% Color", 11141120);
        ITEM_RENDERER = new RenderItem();
    }
}
