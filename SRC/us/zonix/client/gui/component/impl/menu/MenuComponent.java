package us.zonix.client.gui.component.impl.menu;

import us.zonix.client.gui.component.*;
import net.minecraft.util.*;
import us.zonix.client.module.*;
import us.zonix.client.*;
import us.zonix.client.setting.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import us.zonix.client.setting.impl.*;
import org.lwjgl.input.*;
import java.util.*;
import us.zonix.client.util.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.*;
import java.awt.*;

public final class MenuComponent implements IComponent
{
    private static final ResourceLocation ARROW_RIGHT;
    private static final ResourceLocation ARROW_LEFT;
    private static final ResourceLocation TOGGLE_OFF;
    private static final ResourceLocation TOGGLE_ON;
    private static final ResourceLocation SETTINGS;
    private EnumMenuType menuType;
    private FloatSetting draggingSetting;
    private IModule editing;
    private int switchTime;
    private int scrollAmount;
    private boolean hiding;
    private int hidingTicks;
    private int width;
    private int height;
    private int x;
    private int y;
    
    public MenuComponent() {
        this.menuType = EnumMenuType.MODS;
        this.switchTime = 450;
    }
    
    @Override
    public void setPosition(final int x, final int y) {
        this.x = x;
        this.y = y;
    }
    
    @Override
    public void onOpen() {
    }
    
    @Override
    public void tick() {
        ++this.hidingTicks;
        if (this.switchTime <= 450.0f && this.switchTime >= 0) {
            if (this.menuType == EnumMenuType.MODS) {
                this.switchTime -= 25;
            }
            else {
                this.switchTime += 25;
            }
        }
    }
    
    @Override
    public void onMouseRelease() {
        this.draggingSetting = null;
        ColorSetting colorSetting;
        Client.getInstance().getModuleManager().getModules().forEach(module -> module.getSettingMap().values().forEach(setting -> {
            if (setting instanceof ColorSetting) {
                colorSetting = setting;
                colorSetting.setDraggingAlpha((boolean)(0 != 0));
                colorSetting.setDraggingHue((boolean)(0 != 0));
                colorSetting.setDraggingAll((boolean)(0 != 0));
            }
        }));
    }
    
    @Override
    public void onKeyPress(final int code, final char c) {
        if (this.menuType == EnumMenuType.MOD) {
            final List<ISetting> sortedSettings = this.editing.getSortedSettings();
            for (int i = 0; i < sortedSettings.size(); ++i) {
                final ISetting setting = sortedSettings.get(i);
                if (setting instanceof TextSetting && ((TextSetting)setting).isEditing()) {
                    final String value = setting.getValue().toString();
                    if (code == 14) {
                        if (!value.isEmpty()) {
                            ((TextSetting)setting).setValue(value.substring(0, value.length() - 1));
                        }
                    }
                    else if (code == 28) {
                        ((TextSetting)setting).setEditing(false);
                    }
                    else if (code == 15) {
                        ((TextSetting)setting).setEditing(false);
                        if (i < sortedSettings.size() - 1) {
                            final ISetting next = sortedSettings.get(i + 1);
                            if (next instanceof TextSetting) {
                                ((TextSetting)next).setEditing(true);
                            }
                            break;
                        }
                        break;
                    }
                    else if (Character.isLetterOrDigit(c) || " []()<>.&%+-_,'".contains(String.valueOf(c))) {
                        ((TextSetting)setting).setValue(value + c);
                    }
                }
            }
        }
    }
    
    @Override
    public void onClick(final int mouseX, final int mouseY, final int button) {
        final ScaledResolution resolution = new ScaledResolution(Minecraft.getMinecraft());
        switch (this.menuType) {
            case MOD: {
                final float boxWidth = 450.0f;
                final float boxHeight = 200.0f;
                float minX = resolution.getScaledWidth() / 2 - boxWidth / 2.0f;
                float minY = resolution.getScaledHeight() / 2 - boxHeight / 2.0f;
                final float nameWidth = (float)Client.getInstance().getLargeBoldFontRenderer().getStringWidth(this.editing.getName().toUpperCase());
                float x = minX + boxWidth / 2.0f - nameWidth / 2.0f - 25.0f;
                final float y = minY + 12.0f;
                if (mouseX >= x && mouseX <= x + 16.0f && mouseY >= y && mouseY <= y + 16.0f) {
                    this.menuType = EnumMenuType.MODS;
                    this.scrollAmount = 0;
                    this.switchTime = 450;
                    ColorSetting colorSetting;
                    this.editing.getSettingMap().values().forEach(setting -> {
                        if (setting instanceof ColorSetting) {
                            colorSetting = setting;
                            colorSetting.setPicking(false);
                        }
                        return;
                    });
                }
                final List<ISetting> settings = this.editing.getSortedSettings();
                final float currentMaxY = minY + settings.size() * 50.0f + (settings.size() - 1) * 10.0f;
                if (currentMaxY > minY + boxHeight) {
                    final float translate = this.scrollAmount / 10.0f;
                    minY += translate;
                }
                else {
                    this.scrollAmount = 0;
                }
                minX += 25.0f;
                boolean switchedSelector = false;
                boolean switchedBoolean = false;
                float startY = minY + 40.0f;
                for (int i = 0; i < settings.size(); ++i) {
                    final ISetting setting2 = settings.get(i);
                    if (!setting2.getName().equals("Enabled")) {
                        boolean down = true;
                        float itemStart = 120.0f;
                        if (setting2 instanceof FloatSetting) {
                            switchedSelector = (switchedBoolean = false);
                            if (mouseX >= minX + itemStart && mouseX <= minX + boxWidth - 50.0f && mouseY >= startY + 4.0f && mouseY <= startY + 12.5f) {
                                this.draggingSetting = (FloatSetting)setting2;
                                final float width = minX + boxWidth - 50.0f - (minX + itemStart);
                                final float percent = (mouseX - (minX + itemStart)) / width * this.draggingSetting.getMax();
                                this.draggingSetting.setValue(Float.valueOf(percent));
                            }
                        }
                        else if (setting2 instanceof LabelSetting) {
                            switchedSelector = (switchedBoolean = false);
                        }
                        else if (setting2 instanceof StringSetting) {
                            switchedBoolean = false;
                            x = minX;
                            if (!(switchedSelector = !switchedSelector)) {
                                x = minX + boxWidth - 100.0f - itemStart;
                            }
                            else if (i < settings.size() - 1 && settings.get(i + 1).getClass() == setting2.getClass()) {
                                down = false;
                            }
                            int index = ((StringSetting)setting2).getIndex();
                            if (mouseX >= x + itemStart - 25.0f && mouseX <= x + itemStart - 15.0f && mouseY >= startY + 3.0f && mouseY <= startY + 13.0f) {
                                --index;
                            }
                            if (mouseX >= x + itemStart + 45.0f && mouseX <= x + itemStart + 55.0f && mouseY >= startY + 3.0f && mouseY <= startY + 13.0f) {
                                ++index;
                            }
                            final int max = ((StringSetting)setting2).getOptions().length;
                            if (index < 0) {
                                index = max - 1;
                            }
                            else if (index >= max) {
                                index = 0;
                            }
                            ((StringSetting)setting2).setValue(index);
                        }
                        else if (setting2 instanceof BooleanSetting) {
                            switchedSelector = false;
                            x = minX;
                            if (!(switchedBoolean = !switchedBoolean)) {
                                x = minX + boxWidth - 100.0f - itemStart;
                            }
                            else if (i < settings.size() - 1 && settings.get(i + 1).getClass() == setting2.getClass()) {
                                down = false;
                            }
                            if (mouseX >= x + itemStart + 8.0f && mouseX <= x + itemStart + 23.0f && mouseY >= startY + 1.0f && mouseY <= startY + 16.0f) {
                                ((BooleanSetting)setting2).setValue(Boolean.valueOf(!setting2.getValue()));
                            }
                        }
                        else if (setting2 instanceof ColorSetting) {
                            switchedSelector = (switchedBoolean = false);
                            if (mouseX >= minX + itemStart + 10.0f && mouseX <= minX + itemStart + 20.0f && mouseY >= startY + 4.0f && mouseY <= startY + 14.0f) {
                                ((ColorSetting)setting2).setPicking(!((ColorSetting)setting2).isPicking());
                            }
                            if (!setting2.getName().equals("Background")) {
                                final float chromaX = minX + boxWidth - 100.0f - itemStart;
                                if (mouseX >= chromaX + itemStart + 10.0f && mouseX <= chromaX + itemStart + 20.0f && mouseY >= startY + 4.0f && mouseY <= startY + 14.0f) {
                                    ((ColorSetting)setting2).setChroma(!((ColorSetting)setting2).isChroma());
                                }
                            }
                            if (((ColorSetting)setting2).isPicking()) {
                                itemStart -= 45.0f;
                                startY += 15.0f;
                                final float spectrumX = minX + itemStart + 56.0f;
                                final float spectrumXEnd = minX + itemStart + 176.0f;
                                final float spectrumY = startY + 5.0f;
                                final float spectrumYEnd = startY + 119.0f;
                                final float spectrumWidth = spectrumXEnd - spectrumX;
                                final float spectrumHeight = spectrumYEnd - spectrumY;
                                if (mouseX >= spectrumX && mouseX <= spectrumXEnd && mouseY >= spectrumY && mouseY <= spectrumYEnd) {
                                    ((ColorSetting)setting2).setDraggingAll(true);
                                }
                                if (mouseX >= spectrumX + spectrumWidth + 4.0f && mouseX <= spectrumX + spectrumWidth + 14.0f && mouseY >= spectrumY - 1.0f && mouseY <= spectrumY + spectrumHeight + 1.0f) {
                                    ((ColorSetting)setting2).setDraggingHue(true);
                                }
                                if (mouseX >= spectrumX + spectrumWidth + 18.0f && mouseX <= spectrumX + spectrumWidth + 28.0f && mouseY >= spectrumY - 1.0f && mouseY <= spectrumY + spectrumHeight + 1.0f) {
                                    ((ColorSetting)setting2).setDraggingAlpha(true);
                                }
                                startY += 105.0f;
                            }
                        }
                        else if (setting2 instanceof TextSetting) {
                            if (mouseX >= minX + itemStart && mouseX <= minX + boxWidth - 50.0f && mouseY >= startY && mouseY <= startY + 15.0f) {
                                ((TextSetting)setting2).setEditing(true);
                            }
                            else {
                                ((TextSetting)setting2).setEditing(false);
                            }
                        }
                        if (down) {
                            startY += 25.0f;
                        }
                    }
                }
                break;
            }
            case MODS: {
                final float boxWidth = 450.0f;
                final float boxHeight = 210.0f;
                final float minX = resolution.getScaledWidth() / 2 - boxWidth / 2.0f;
                final float minY = resolution.getScaledHeight() / 2 - boxHeight / 2.0f;
                final List<IModule> modules = new ArrayList<IModule>(Client.getInstance().getModuleManager().getModules());
                modules.sort((m1, m2) -> m1.getName().compareToIgnoreCase(m2.getName()));
                int v = 0;
                int h = 0;
                for (final IModule module : modules) {
                    final float x2 = minX + 110.0f * h + 10.0f;
                    final float y2 = minY + 50.0f * v + 10.0f;
                    if (mouseX >= x2 + 32.5f && mouseX <= x2 + 46.5f && mouseY >= y2 + 20.0f && mouseY <= y2 + 34.0f) {
                        module.setEnabled(!module.isEnabled());
                        return;
                    }
                    if (mouseX >= x2 + 57.5f && mouseX <= x2 + 71.5f && mouseY >= y2 + 20.0f && mouseY <= y2 + 34.0f) {
                        if (module.getSettingMap().size() > 1) {
                            this.menuType = EnumMenuType.MOD;
                            this.scrollAmount = 0;
                            this.editing = module;
                            this.switchTime = 0;
                        }
                        return;
                    }
                    if (++h != 4) {
                        continue;
                    }
                    h = 0;
                    ++v;
                }
                break;
            }
        }
    }
    
    @Override
    public void onMouseEvent() {
        final int scroll = Mouse.getEventDWheel();
        if (scroll == 0) {
            return;
        }
        final List<Float> sizes = new LinkedList<Float>();
        switch (this.menuType) {
            case MODS: {
                for (int mods = Client.getInstance().getModuleManager().getModules().size() % 4, i = 0; i < mods; ++i) {
                    sizes.add(50.0f * i + 10.0f);
                }
                break;
            }
            case MOD: {
                boolean switchBoolean = false;
                boolean switchString = false;
                final List<ISetting> settings = new LinkedList<ISetting>();
                final List<ISetting> sortedSettings = this.editing.getSortedSettings();
                for (int j = 0; j < sortedSettings.size(); ++j) {
                    final ISetting setting = sortedSettings.get(j);
                    if (setting instanceof BooleanSetting) {
                        switchString = false;
                        if ((switchBoolean = !switchBoolean) && j < settings.size() - 1 && settings.get(j + 1).getClass() == setting.getClass()) {
                            continue;
                        }
                    }
                    else if (setting instanceof StringSetting) {
                        switchBoolean = false;
                        if ((switchString = !switchString) && j < settings.size() - 1 && settings.get(j + 1).getClass() == setting.getClass()) {
                            continue;
                        }
                    }
                    settings.add(setting);
                    if (setting instanceof ColorSetting && ((ColorSetting)setting).isPicking()) {
                        sizes.add(145.0f);
                    }
                    else {
                        sizes.add(25.0f);
                    }
                }
                break;
            }
        }
        if (sizes.size() > 0) {
            this.scroll(sizes, scroll);
        }
    }
    
    private void scroll(final List<Float> sizes, final int scroll) {
        final int before = this.scrollAmount;
        this.scrollAmount += scroll;
        if (this.scrollAmount > 0) {
            this.scrollAmount = 0;
        }
        final ScaledResolution resolution = new ScaledResolution(Minecraft.getMinecraft());
        final float boxHeight = 50.0f;
        final float minY = resolution.getScaledHeight() / 2 - boxHeight / 2.0f + 40.0f;
        final float maxY = minY + boxHeight - 20.0f;
        final float translate = this.scrollAmount / 10.0f;
        float startY = 60.0f + translate;
        boolean move = false;
        for (final Float size : sizes) {
            if (startY + size >= maxY) {
                move = true;
                break;
            }
            startY += size;
        }
        if (!move) {
            this.scrollAmount = before;
        }
    }
    
    private void renderModMenu(final ScaledResolution resolution, final int mouseX, final int mouseY) {
        if (this.editing == null) {
            this.menuType = EnumMenuType.MODS;
            this.scrollAmount = 0;
            return;
        }
        final float boxWidth = 450.0f;
        final float boxHeight = 200.0f;
        float minX = resolution.getScaledWidth() / 2 - boxWidth / 2.0f;
        float minY = resolution.getScaledHeight() / 2 - boxHeight / 2.0f;
        RenderUtil.drawRoundedRect(minX, minY, minX + boxWidth, minY + boxHeight, 5.0, 1997935379);
        RenderUtil.drawCenteredString(Client.getInstance().getLargeBoldFontRenderer(), this.editing.getName().toUpperCase(), minX + boxWidth / 2.0f, minY + 20.0f, -1, false);
        final float nameWidth = (float)Client.getInstance().getLargeBoldFontRenderer().getStringWidth(this.editing.getName().toUpperCase());
        GL11.glPushMatrix();
        float alpha = 0.7f;
        final float x = minX + boxWidth / 2.0f - nameWidth / 2.0f - 25.0f;
        final float y = minY + 12.0f;
        if (mouseX >= x && mouseX <= x + 16.0f && mouseY >= y && mouseY <= y + 16.0f) {
            alpha = 1.0f;
        }
        GL11.glColor4f(1.0f, 1.0f, 1.0f, alpha);
        RenderUtil.drawSquareTexture(MenuComponent.ARROW_LEFT, 8.0f, minX + boxWidth / 2.0f - nameWidth / 2.0f - 25.0f, minY + 12.0f);
        GL11.glPopMatrix();
        RenderUtil.startScissorBox(minY + 40.0f, minY + boxHeight - 20.0f, minX, minX + boxWidth);
        final List<ISetting> settings = this.editing.getSortedSettings();
        GL11.glPushMatrix();
        final float currentMaxY = minY + settings.size() * 50.0f + (settings.size() - 1) * 10.0f;
        if (currentMaxY > minY + boxHeight) {
            final float translate = this.scrollAmount / 10.0f;
            minY += translate;
        }
        else {
            this.scrollAmount = 0;
        }
        minX += 25.0f;
        boolean switchedSelector = false;
        boolean switchedBoolean = false;
        float startY = minY + 40.0f;
        for (int i = 0; i < settings.size(); ++i) {
            final ISetting setting = settings.get(i);
            if (!setting.getName().equals("Enabled")) {
                boolean down = true;
                GL11.glPushMatrix();
                float itemStart = 120.0f;
                if (setting instanceof FloatSetting) {
                    switchedSelector = (switchedBoolean = false);
                    RenderUtil.drawString(Client.getInstance().getSmallFontRenderer(), setting.getName(), minX, startY + 6.0f, -855638017, false);
                    final float width = minX + boxWidth - 50.0f - (minX + itemStart);
                    final FloatSetting floatSetting = (FloatSetting)setting;
                    if (setting == this.draggingSetting) {
                        final float percent = (mouseX - (minX + itemStart)) / width * floatSetting.getMax();
                        floatSetting.setValue(Float.valueOf(percent));
                    }
                    final String value = String.format("%.1f", floatSetting.getValue());
                    RenderUtil.drawString(Client.getInstance().getSmallFontRenderer(), value, minX + itemStart - 5.0f - Client.getInstance().getSmallFontRenderer().getStringWidth(value), startY + 6.0f, -1, false);
                    final float percent2 = width / 100.0f * (floatSetting.getValue() / floatSetting.getMax() * 100.0f);
                    RenderUtil.drawRect(minX + itemStart, startY + 4.0f, minX + boxWidth - 50.0f, startY + 12.5f, -1456275456);
                    RenderUtil.drawRect(minX + itemStart, startY + 4.0f, minX + itemStart + percent2, startY + 12.5f, -1444007117);
                }
                else if (setting instanceof LabelSetting) {
                    switchedSelector = (switchedBoolean = false);
                    RenderUtil.drawString(Client.getInstance().getSmallFontRenderer(), setting.getName(), minX - 5.0f, startY + 6.0f, -855638017, false);
                    RenderUtil.drawRect(minX - 5.0f, startY + 14.5f, minX + boxWidth - 45.0f, startY + 15.5f, -1999909941);
                }
                else if (setting instanceof StringSetting) {
                    switchedBoolean = false;
                    float x2 = minX;
                    if (!(switchedSelector = !switchedSelector)) {
                        x2 = minX + boxWidth - 100.0f - itemStart;
                    }
                    else if (i < settings.size() - 1 && settings.get(i + 1).getClass() == setting.getClass()) {
                        down = false;
                    }
                    RenderUtil.drawString(Client.getInstance().getSmallFontRenderer(), setting.getName(), x2, startY + 6.0f, -855638017, false);
                    RenderUtil.drawCenteredString(Client.getInstance().getSmallFontRenderer(), setting.getValue(), x2 + itemStart + 15.0f, startY + 6.0f + Client.getInstance().getSmallFontRenderer().getHeight() / 2, -855638017, false);
                    GL11.glPushMatrix();
                    if (mouseX >= x2 + itemStart + 45.0f && mouseX <= x2 + itemStart + 55.0f && mouseY >= startY + 3.0f && mouseY <= startY + 13.0f) {
                        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                    }
                    else {
                        GL11.glColor4f(1.0f, 1.0f, 1.0f, 0.6f);
                    }
                    RenderUtil.drawSquareTexture(MenuComponent.ARROW_RIGHT, 5.0f, x2 + itemStart + 45.0f, startY + 3.5f);
                    if (mouseX >= x2 + itemStart - 25.0f && mouseX <= x2 + itemStart - 15.0f && mouseY >= startY + 3.0f && mouseY <= startY + 13.0f) {
                        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                    }
                    else {
                        GL11.glColor4f(1.0f, 1.0f, 1.0f, 0.6f);
                    }
                    RenderUtil.drawSquareTexture(MenuComponent.ARROW_LEFT, 5.0f, x2 + itemStart - 25.0f, startY + 3.5f);
                    GL11.glPopMatrix();
                }
                else if (setting instanceof BooleanSetting) {
                    switchedSelector = false;
                    float x2 = minX;
                    if (!(switchedBoolean = !switchedBoolean)) {
                        x2 = minX + boxWidth - 100.0f - itemStart;
                    }
                    else if (i < settings.size() - 1 && settings.get(i + 1).getClass() == setting.getClass()) {
                        down = false;
                    }
                    RenderUtil.drawString(Client.getInstance().getSmallFontRenderer(), setting.getName(), x2, startY + 6.0f, -855638017, false);
                    float alpha2 = 0.6f;
                    if (mouseX >= x2 + itemStart + 8.0f && mouseX <= x2 + itemStart + 23.0f && mouseY >= startY + 1.0f && mouseY <= startY + 16.0f) {
                        alpha2 = 0.8f;
                    }
                    GL11.glEnable(3042);
                    if (setting.getValue()) {
                        GL11.glColor4f(0.0f, 1.0f, 0.0f, alpha2);
                        RenderUtil.drawSquareTexture(MenuComponent.TOGGLE_ON, 7.5f, x2 + itemStart + 8.0f, startY + 1.0f);
                    }
                    else {
                        GL11.glColor4f(1.0f, 0.0f, 0.0f, alpha2);
                        RenderUtil.drawSquareTexture(MenuComponent.TOGGLE_OFF, 7.5f, x2 + itemStart + 8.0f, startY + 1.0f);
                    }
                    GL11.glDisable(3042);
                }
                else if (setting instanceof ColorSetting) {
                    switchedSelector = (switchedBoolean = false);
                    RenderUtil.drawString(Client.getInstance().getSmallFontRenderer(), setting.getName(), minX, startY + 7.0f, -855638017, false);
                    RenderUtil.drawBorderedRect(minX + itemStart + 10.0f, startY + 4.0f, minX + itemStart + 20.0f, startY + 14.0f, 1.0f, -1, setting.getValue());
                    if (!setting.getName().equals("Background")) {
                        final float chromaX = minX + boxWidth - 100.0f - itemStart;
                        RenderUtil.drawString(Client.getInstance().getSmallFontRenderer(), "Chroma", chromaX, startY + 7.0f, -855638017, false);
                        RenderUtil.drawBorderedRect(chromaX + itemStart + 10.0f, startY + 4.0f, chromaX + itemStart + 20.0f, startY + 14.0f, 1.0f, -1, ((ColorSetting)setting).isChroma() ? -16777216 : -1);
                    }
                    RenderUtil.drawString(Client.getInstance().getSmallFontRenderer(), "#" + Integer.toHexString(setting.getValue()).toUpperCase(), minX + itemStart + 25.0f, startY + 7.0f, -855638017, false);
                    if (((ColorSetting)setting).isPicking()) {
                        itemStart -= 45.0f;
                        startY += 15.0f;
                        final ColorSetting colorSetting = (ColorSetting)setting;
                        final float spectrumX = minX + itemStart + 56.0f;
                        final float spectrumXEnd = minX + itemStart + 176.0f;
                        final float spectrumY = startY + 5.0f;
                        final float spectrumYEnd = startY + 119.0f;
                        final float spectrumWidth = spectrumXEnd - spectrumX;
                        final float spectrumHeight = spectrumYEnd - spectrumY;
                        RenderUtil.drawRect(minX + itemStart + 55.0f, startY + 4.0f, minX + itemStart + 177.0f, startY + 120.0f, -822083584);
                        final Tessellator tess = Tessellator.instance;
                        GL11.glDisable(3553);
                        tess.startDrawingQuads();
                        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                        tess.addVertex(spectrumX, spectrumYEnd, 0.0);
                        tess.addVertex(spectrumXEnd, spectrumYEnd, 0.0);
                        tess.addVertex(spectrumXEnd, spectrumY, 0.0);
                        tess.addVertex(spectrumX, spectrumY, 0.0);
                        tess.draw();
                        int[] colorPos = null;
                        for (int x3 = 0; x3 < spectrumWidth; ++x3) {
                            for (int y2 = 0; y2 < spectrumHeight; ++y2) {
                                final float saturation = x3 / spectrumWidth;
                                final float brightness = 1.0f - y2 / spectrumHeight;
                                final int colorAtXY = (int)colorSetting.getAlpha() << 24 | Color.HSBtoRGB(colorSetting.getHue(), saturation, brightness);
                                final boolean mouseXOver = mouseX >= spectrumX + x3 && mouseX <= spectrumX + x3 + 1.0f;
                                final boolean mouseYOver = mouseY <= spectrumY + y2 + 1.0f && mouseY > spectrumY + y2;
                                final boolean overPosition = mouseXOver && mouseYOver;
                                final boolean xTooSmall = x3 == 0 && mouseX < spectrumX && mouseYOver;
                                final boolean yTooSmall = y2 == 0 && mouseY < spectrumY && mouseXOver;
                                final boolean xTooBig = x3 == spectrumWidth - 1.0f && mouseX > spectrumX + spectrumWidth && mouseYOver;
                                final boolean yTooBig = y2 == spectrumHeight - 1.0f && mouseY > spectrumY + spectrumHeight && mouseXOver;
                                if (colorSetting.isDraggingAll() && (overPosition || xTooSmall || yTooSmall || xTooBig || yTooBig)) {
                                    colorSetting.setValue(Integer.valueOf(colorAtXY));
                                    colorSetting.setPickerLocation(new int[] { x3, y2 });
                                }
                                if (colorSetting.getPickerLocation() != null) {
                                    colorPos = colorSetting.getPickerLocation();
                                }
                                else if (colorAtXY == setting.getValue()) {
                                    colorPos = new int[] { x3, y2 };
                                }
                                tess.startDrawingQuads();
                                GL11.glColor4f((colorAtXY >> 16 & 0xFF) / 255.0f, (colorAtXY >> 8 & 0xFF) / 255.0f, (colorAtXY & 0xFF) / 255.0f, 1.0f);
                                tess.addVertex(spectrumX + x3, spectrumY + y2 + 1.0f, 0.0);
                                tess.addVertex(spectrumX + x3 + 1.0f, spectrumY + y2 + 1.0f, 0.0);
                                tess.addVertex(spectrumX + x3 + 1.0f, spectrumY + y2, 0.0);
                                tess.addVertex(spectrumX + x3, spectrumY + y2, 0.0);
                                tess.draw();
                            }
                        }
                        if (colorPos != null) {
                            GL11.glPushMatrix();
                            GL11.glColor4f(0.0f, 0.0f, 0.0f, 0.75f);
                            RenderUtil.drawCircle(spectrumX + colorPos[0] + 1.115f, spectrumY + colorPos[1] + 1.115f, 4.0);
                            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                            RenderUtil.drawCircle(spectrumX + colorPos[0] + 1.115f, spectrumY + colorPos[1] + 1.115f, 2.700000047683716);
                            GL11.glPopMatrix();
                        }
                        RenderUtil.drawRect(spectrumX + spectrumWidth + 4.0f, spectrumY - 1.0f, spectrumX + spectrumWidth + 14.0f, spectrumY + 1.0f + spectrumHeight, -822083584);
                        for (int j = 0; j < spectrumHeight; ++j) {
                            final int RGB = Color.HSBtoRGB(j / spectrumHeight, 1.0f, 1.0f);
                            RenderUtil.drawRect(spectrumX + spectrumWidth + 5.0f, spectrumY + j, spectrumX + spectrumWidth + 13.0f, spectrumY + j + 1.0f, RGB);
                            if (colorSetting.isDraggingHue() && mouseY >= spectrumY + j && mouseY <= spectrumY + j + 1.0f) {
                                final int c = colorSetting.getValue();
                                final float[] hsb = Color.RGBtoHSB(c >> 16 & 0xFF, c >> 8 & 0xFF, c & 0xFF, null);
                                colorSetting.setValue(Integer.valueOf(Color.HSBtoRGB(colorSetting.getHue(), hsb[1], hsb[2])));
                                colorSetting.setHue(j / spectrumHeight);
                            }
                        }
                        final float startHueY = -1.0f + spectrumHeight * ((ColorSetting)setting).getHue();
                        RenderUtil.drawRect(spectrumX + spectrumWidth + 4.0f, spectrumY + startHueY, spectrumX + spectrumWidth + 14.0f, spectrumY + startHueY + 3.0f, -822083584);
                        RenderUtil.drawRect(spectrumX + spectrumWidth + 4.0f, spectrumY + startHueY + 1.0f, spectrumX + spectrumWidth + 14.0f, spectrumY + startHueY + 2.0f, -805306369);
                        RenderUtil.drawRect(spectrumX + spectrumWidth + 18.0f, spectrumY - 1.0f, spectrumX + spectrumWidth + 28.0f, spectrumY + 1.0f + spectrumHeight, -822083584);
                        boolean left = true;
                        for (int k = 2; k < spectrumHeight; k += 4) {
                            if (!left) {
                                RenderUtil.drawRect(spectrumX + spectrumWidth + 19.0f, spectrumY + k, spectrumX + spectrumWidth + 23.0f, spectrumY + k + 4.0f, -1);
                                RenderUtil.drawRect(spectrumX + spectrumWidth + 23.0f, spectrumY + k, spectrumX + spectrumWidth + 27.0f, spectrumY + k + 4.0f, -7303024);
                                if (k < spectrumHeight - 4.0f) {
                                    RenderUtil.drawRect(spectrumX + spectrumWidth + 19.0f, spectrumY + k + 4.0f, spectrumX + spectrumWidth + 23.0f, spectrumY + k + 8.0f, -7303024);
                                    RenderUtil.drawRect(spectrumX + spectrumWidth + 23.0f, spectrumY + k + 4.0f, spectrumX + spectrumWidth + 27.0f, spectrumY + k + 8.0f, -1);
                                }
                            }
                            left = !left;
                        }
                        for (int k = 0; k < spectrumHeight; ++k) {
                            final int c2 = setting.getValue();
                            final int rgb = new Color(c2 >> 16 & 0xFF, c2 >> 8 & 0xFF, c2 & 0xFF, Math.round(255.0f - k / spectrumHeight * 255.0f)).getRGB();
                            if (colorSetting.isDraggingAlpha() && mouseY >= spectrumY + k && mouseY <= spectrumY + k + 1.0f) {
                                colorSetting.setAlpha(k / spectrumHeight);
                                colorSetting.setValue(Integer.valueOf(rgb));
                            }
                            RenderUtil.drawRect(spectrumX + spectrumWidth + 19.0f, spectrumY + k, spectrumX + spectrumWidth + 27.0f, spectrumY + k + 1.0f, rgb);
                        }
                        final float startAlphaY = -1.0f + spectrumHeight * ((ColorSetting)setting).getAlpha();
                        RenderUtil.drawRect(spectrumX + spectrumWidth + 18.0f, spectrumY + startAlphaY, spectrumX + spectrumWidth + 28.0f, spectrumY + startAlphaY + 3.0f, -822083584);
                        RenderUtil.drawRect(spectrumX + spectrumWidth + 18.0f, spectrumY + startAlphaY + 1.0f, spectrumX + spectrumWidth + 28.0f, spectrumY + startAlphaY + 2.0f, -805306369);
                        startY += 105.0f;
                    }
                }
                else if (setting instanceof TextSetting) {
                    switchedSelector = (switchedBoolean = false);
                    RenderUtil.drawString(Client.getInstance().getSmallFontRenderer(), setting.getName(), minX, startY + 6.0f, -855638017, false);
                    RenderUtil.drawRect(minX + itemStart, startY, minX + boxWidth - 50.0f, startY + 15.0f, -1724697805);
                    String value2 = setting.getValue();
                    if (((TextSetting)setting).isEditing()) {
                        if (((TextSetting)setting).getValueFlipTime() + 250L < System.currentTimeMillis()) {
                            ((TextSetting)setting).setValued(!((TextSetting)setting).isValued());
                            ((TextSetting)setting).setValueFlipTime(System.currentTimeMillis());
                        }
                        if (((TextSetting)setting).isValued()) {
                            value2 += "_";
                        }
                    }
                    RenderUtil.drawString(Client.getInstance().getRegularFontRenderer(), value2, minX + itemStart + 2.5f, startY + 4.0f, -905969665, false);
                }
                GL11.glPopMatrix();
                if (down) {
                    startY += 25.0f;
                }
            }
        }
        GL11.glPopMatrix();
        RenderUtil.endScissorBox();
    }
    
    private void renderMainMenu(final ScaledResolution resolution, final int mouseX, final int mouseY) {
        final float boxWidth = 450.0f;
        final float boxHeight = 210.0f;
        final float minX = resolution.getScaledWidth() / 2 - boxWidth / 2.0f;
        final float minY = resolution.getScaledHeight() / 2 - boxHeight / 2.0f;
        RenderUtil.drawRoundedRect(minX, minY, minX + boxWidth, minY + boxHeight, 5.0, 1997935379);
        RenderUtil.startScissorBox(minY, minY + boxHeight, minX, minX + boxWidth);
        GL11.glPushMatrix();
        final List<IModule> modules = new ArrayList<IModule>(Client.getInstance().getModuleManager().getModules());
        modules.sort((m1, m2) -> m1.getName().compareToIgnoreCase(m2.getName()));
        int v = 0;
        int h = 0;
        for (final IModule module : modules) {
            final float x = minX + 110.0f * h + 10.0f;
            final float y = minY + 50.0f * v + 10.0f;
            RenderUtil.drawBorderedRoundedRect(x, y, x + 100.0f, y + 40.0f, 2.5f, 1.0f, -7788247, -13882324);
            RenderUtil.drawCenteredString(Client.getInstance().getRegularMediumBoldFontRenderer(), module.getName().toUpperCase(), x + 50.0f, y + 10.0f, -1);
            float alpha = 0.6f;
            if (mouseX >= x + 32.5f && mouseX <= x + 32.5f + 15.0f && mouseY >= y + 20.0f && mouseY <= y + 20.0f + 15.0f) {
                alpha = 0.8f;
            }
            GL11.glPushMatrix();
            GL11.glEnable(3042);
            if (module.isEnabled()) {
                GL11.glColor4f(0.0f, 1.0f, 0.0f, alpha);
                RenderUtil.drawSquareTexture(MenuComponent.TOGGLE_ON, 7.5f, x + 32.5f, y + 20.0f);
            }
            else {
                GL11.glColor4f(1.0f, 0.0f, 0.0f, alpha);
                RenderUtil.drawSquareTexture(MenuComponent.TOGGLE_OFF, 7.5f, x + 32.5f, y + 20.0f);
            }
            if (mouseX >= x + 57.5f && mouseX <= x + 71.5f && mouseY >= y + 20.0f && mouseY <= y + 34.0f) {
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 0.9f);
            }
            else {
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 0.6f);
            }
            RenderUtil.drawSquareTexture(MenuComponent.SETTINGS, 7.5f, x + 57.5f, y + 20.0f);
            GL11.glDisable(3042);
            GL11.glPopMatrix();
            if (++h == 4) {
                h = 0;
                ++v;
            }
        }
        GL11.glPopMatrix();
        RenderUtil.endScissorBox();
    }
    
    @Override
    public void draw(final int mouseX, final int mouseY) {
        final Minecraft mc = Minecraft.getMinecraft();
        final ScaledResolution resolution = new ScaledResolution(mc);
        GL11.glPushMatrix();
        final boolean wasHiding = this.hiding;
        this.hiding = (mouseY <= 20.0f);
        if (this.hiding) {
            if (!wasHiding) {
                this.hidingTicks = 0;
            }
            GL11.glTranslatef(0.0f, -this.hidingTicks * 2.0f, 0.0f);
        }
        RenderUtil.drawRect(0.0f, 0.0f, (float)resolution.getScaledWidth(), 23.333334f, -291356865);
        RenderUtil.drawString(Client.getInstance().getMediumBoldFontRenderer(), "ZONIX CLIENT", 10.0f, 6.6666665f, -1, true);
        RenderUtil.drawCenteredString(Client.getInstance().getMediumBoldFontRenderer(), "MOD SETTINGS", (float)(resolution.getScaledWidth() / 2), 11.666667f, -1, true);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        final float boxWidth = 450.0f;
        final float boxHeight = 220.0f;
        final float minX = resolution.getScaledWidth() / 2 - boxWidth / 2.0f;
        final float minY = resolution.getScaledHeight() / 2 - boxHeight / 2.0f;
        GL11.glPushMatrix();
        RenderUtil.startScissorBox(minY, minY + boxHeight, minX, minX + boxWidth);
        GL11.glTranslatef(-this.switchTime - 25.0f, 0.0f, 0.0f);
        this.renderMainMenu(resolution, mouseX, mouseY);
        RenderUtil.endScissorBox();
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        RenderUtil.startScissorBox(minY, minY + boxHeight, minX, minX + boxWidth);
        GL11.glTranslatef(450 - this.switchTime + 25.0f, 0.0f, 0.0f);
        this.renderModMenu(resolution, mouseX, mouseY);
        RenderUtil.endScissorBox();
        GL11.glPopMatrix();
        GL11.glPopMatrix();
    }
    
    private static String toHex(final Color color) {
        return String.format("#%02x%02x%02x%02x", color.getAlpha(), color.getRed(), color.getGreen(), color.getBlue());
    }
    
    public EnumMenuType getMenuType() {
        return this.menuType;
    }
    
    public void setMenuType(final EnumMenuType menuType) {
        this.menuType = menuType;
    }
    
    public void setDraggingSetting(final FloatSetting draggingSetting) {
        this.draggingSetting = draggingSetting;
    }
    
    public void setEditing(final IModule editing) {
        this.editing = editing;
    }
    
    public void setSwitchTime(final int switchTime) {
        this.switchTime = switchTime;
    }
    
    public void setScrollAmount(final int scrollAmount) {
        this.scrollAmount = scrollAmount;
    }
    
    @Override
    public int getWidth() {
        return this.width;
    }
    
    @Override
    public void setWidth(final int width) {
        this.width = width;
    }
    
    @Override
    public int getHeight() {
        return this.height;
    }
    
    @Override
    public void setHeight(final int height) {
        this.height = height;
    }
    
    @Override
    public int getX() {
        return this.x;
    }
    
    public void setX(final int x) {
        this.x = x;
    }
    
    @Override
    public int getY() {
        return this.y;
    }
    
    public void setY(final int y) {
        this.y = y;
    }
    
    static {
        ARROW_RIGHT = new ResourceLocation("icon/caret-right.png");
        ARROW_LEFT = new ResourceLocation("icon/caret-left.png");
        TOGGLE_OFF = new ResourceLocation("icon/toggle-off.png");
        TOGGLE_ON = new ResourceLocation("icon/toggle-on.png");
        SETTINGS = new ResourceLocation("icon/settings.png");
    }
    
    public enum EnumMenuType
    {
        MOD, 
        MODS;
    }
}
