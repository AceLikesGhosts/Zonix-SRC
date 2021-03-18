package us.zonix.client.util;

import net.minecraft.item.*;
import net.minecraft.client.*;
import us.zonix.client.module.impl.*;
import net.minecraft.util.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.*;
import us.zonix.client.*;
import us.zonix.client.util.font.*;

public class HUDElement
{
    public final ItemStack itemStack;
    public final int iconW;
    public final int iconH;
    public final int padW;
    private int elementW;
    private int elementH;
    private String itemName;
    private int itemNameW;
    private String itemDamage;
    private int itemDamageW;
    private final boolean isArmor;
    private Minecraft mc;
    private int color;
    
    public HUDElement(final ItemStack itemStack, final int iconW, final int iconH, final int padW, final boolean isArmor) {
        this.itemName = "";
        this.itemDamage = "";
        this.mc = Minecraft.getMinecraft();
        this.itemStack = itemStack;
        this.iconW = iconW;
        this.iconH = iconH;
        this.padW = padW;
        this.isArmor = isArmor;
        this.initSize();
    }
    
    public int width() {
        return this.elementW;
    }
    
    public int height() {
        return this.elementH;
    }
    
    private void initSize() {
        this.elementH = (ArmorStatus.ITEM_NAME.getValue() ? Math.max(Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT * 2, this.iconH) : Math.max(this.mc.fontRenderer.FONT_HEIGHT, this.iconH));
        if (this.itemStack != null) {
            if (((this.isArmor && ArmorStatus.ARMOR_DAMAGE.getValue()) || (!this.isArmor && ArmorStatus.ITEM_DAMAGE.getValue())) && this.itemStack.isItemStackDamageable()) {
                final int maxDamage = this.itemStack.getMaxDamage() + 1;
                final int damage = maxDamage - this.itemStack.getItemDamageForDisplay();
                this.color = ArmorStatus.getColorCode(ArmorStatus.DAMAGE_THRESHOLD_TYPE.getValue().equalsIgnoreCase("percent") ? (damage * 100 / maxDamage) : damage);
                if (ArmorStatus.DAMAGE_DISPLAY_TYPE.getValue().equalsIgnoreCase("value")) {
                    this.itemDamage = damage + (ArmorStatus.MAX_DAMAGE.getValue() ? ("/" + maxDamage) : "");
                }
                else if (ArmorStatus.DAMAGE_DISPLAY_TYPE.getValue().equalsIgnoreCase("percent")) {
                    this.itemDamage = damage * 100 / maxDamage + "%";
                }
            }
            this.itemDamageW = this.mc.fontRenderer.getStringWidth(StringUtils.stripCtrl(this.itemDamage));
            this.elementW = this.padW + this.iconW + this.padW + this.itemDamageW;
            if (ArmorStatus.ITEM_NAME.getValue()) {
                this.itemName = this.itemStack.getDisplayName();
                this.elementW = this.padW + this.iconW + this.padW + Math.max(this.mc.fontRenderer.getStringWidth(StringUtils.stripCtrl(this.itemName)), this.itemDamageW);
            }
            this.itemNameW = this.mc.fontRenderer.getStringWidth(StringUtils.stripCtrl(this.itemName));
        }
    }
    
    public void renderToHud(final float x, final float y) {
        GL11.glPushMatrix();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glEnable(32826);
        RenderHelper.enableStandardItemLighting();
        RenderHelper.enableGUIStandardItemLighting();
        ArmorStatus.ITEM_RENDERER.zLevel = -10.0f;
        ArmorStatus.ITEM_RENDERER.renderItemAndEffectIntoGUI(this.mc.fontRenderer, this.mc.getTextureManager(), this.itemStack, (int)x, (int)y);
        HUDUtils.renderItemOverlayIntoGUI(this.mc.fontRenderer, this.itemStack, (int)x, (int)y, ArmorStatus.DAMAGE_OVERLAY.getValue(), ArmorStatus.ITEM_COUNT.getValue());
        RenderHelper.disableStandardItemLighting();
        GL11.glDisable(32826);
        GL11.glDisable(3042);
        final ZFontRenderer fontRenderer = Client.getInstance().getRegularFontRenderer();
        fontRenderer.drawString(this.itemName + "§r", x + this.iconW + this.padW, y, this.color);
        fontRenderer.drawString(this.itemDamage + "§r", x + this.iconW + this.padW, y + (ArmorStatus.ITEM_NAME.getValue() ? (this.elementH / 2) : (this.elementH / 4)), this.color);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glPopMatrix();
    }
}
