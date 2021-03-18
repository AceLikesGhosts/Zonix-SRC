package net.minecraft.client.gui;

import net.minecraft.client.settings.*;
import net.minecraft.client.resources.*;
import net.minecraft.src.*;

public class GuiVideoSettings extends GuiScreen
{
    private GuiScreen field_146498_f;
    protected String field_146500_a;
    private GameSettings field_146499_g;
    private boolean is64bit;
    private static GameSettings.Options[] field_146502_i;
    private int lastMouseX;
    private int lastMouseY;
    private long mouseStillTime;
    
    public GuiVideoSettings(final GuiScreen par1GuiScreen, final GameSettings par2GameSettings) {
        this.field_146500_a = "Video Settings";
        this.lastMouseX = 0;
        this.lastMouseY = 0;
        this.mouseStillTime = 0L;
        this.field_146498_f = par1GuiScreen;
        this.field_146499_g = par2GameSettings;
    }
    
    @Override
    public void initGui() {
        this.field_146500_a = I18n.format("options.videoTitle", new Object[0]);
        this.buttonList.clear();
        this.is64bit = false;
        final String[] var2;
        final String[] var1 = var2 = new String[] { "sun.arch.data.model", "com.ibm.vm.bitmode", "os.arch" };
        for (int var3 = var1.length, var4 = 0; var4 < var3; ++var4) {
            final String var5 = var2[var4];
            final String var6 = System.getProperty(var5);
            if (var6 != null && var6.contains("64")) {
                this.is64bit = true;
                break;
            }
        }
        final boolean var7 = false;
        final boolean var8 = !this.is64bit;
        final GameSettings.Options[] var9 = GuiVideoSettings.field_146502_i;
        final int var10 = var9.length;
        final boolean var11 = false;
        int var12;
        for (var12 = 0; var12 < var10; ++var12) {
            final GameSettings.Options y = var9[var12];
            if (y != null) {
                final int x = this.width / 2 - 155 + var12 % 2 * 160;
                final int y2 = this.height / 6 + 25 * (var12 / 2) - 10;
                if (y.getEnumFloat()) {
                    this.buttonList.add(new GuiOptionSlider(y.returnEnumOrdinal(), x, y2, y));
                }
                else {
                    this.buttonList.add(new GuiOptionButton(y.returnEnumOrdinal(), x, y2, y, this.field_146499_g.getKeyBinding(y)));
                }
            }
        }
        int var13 = this.height / 6 + 25 * (var12 / 2) - 10;
        int x = this.width / 2 - 155 + 160;
        this.buttonList.add(new GuiOptionButton(202, x, var13, "Quality..."));
        var13 += 25;
        x = this.width / 2 - 155;
        this.buttonList.add(new GuiOptionButton(201, x, var13, "Details..."));
        x = this.width / 2 - 155 + 160;
        this.buttonList.add(new GuiOptionButton(211, x, var13, "Animations..."));
        var13 += 25;
        x = this.width / 2 - 155;
        this.buttonList.add(new GuiOptionButton(222, x, var13, "Other..."));
        this.buttonList.add(new GuiButton(200, this.width / 2 - 100, var13 + 25, I18n.format("gui.done", new Object[0])));
    }
    
    @Override
    protected void actionPerformed(final GuiButton par1GuiButton) {
        if (par1GuiButton.enabled) {
            final int var2 = this.field_146499_g.guiScale;
            if (par1GuiButton.id < 200 && par1GuiButton instanceof GuiOptionButton) {
                this.field_146499_g.setOptionValue(((GuiOptionButton)par1GuiButton).func_146136_c(), 1);
                par1GuiButton.displayString = this.field_146499_g.getKeyBinding(GameSettings.Options.getEnumOptions(par1GuiButton.id));
            }
            if (par1GuiButton.id == 200) {
                this.mc.gameSettings.saveOptions();
                this.mc.displayGuiScreen(this.field_146498_f);
            }
            if (this.field_146499_g.guiScale != var2) {
                final ScaledResolution scr = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
                final int var3 = scr.getScaledWidth();
                final int var4 = scr.getScaledHeight();
                this.setWorldAndResolution(this.mc, var3, var4);
            }
            if (par1GuiButton.id == 201) {
                this.mc.gameSettings.saveOptions();
                final GuiDetailSettingsOF scr2 = new GuiDetailSettingsOF(this, this.field_146499_g);
                this.mc.displayGuiScreen(scr2);
            }
            if (par1GuiButton.id == 202) {
                this.mc.gameSettings.saveOptions();
                final GuiQualitySettingsOF scr3 = new GuiQualitySettingsOF(this, this.field_146499_g);
                this.mc.displayGuiScreen(scr3);
            }
            if (par1GuiButton.id == 211) {
                this.mc.gameSettings.saveOptions();
                final GuiAnimationSettingsOF scr4 = new GuiAnimationSettingsOF(this, this.field_146499_g);
                this.mc.displayGuiScreen(scr4);
            }
            if (par1GuiButton.id == 222) {
                this.mc.gameSettings.saveOptions();
                final GuiOtherSettingsOF scr5 = new GuiOtherSettingsOF(this, this.field_146499_g);
                this.mc.displayGuiScreen(scr5);
            }
            if (par1GuiButton.id == GameSettings.Options.AO_LEVEL.ordinal()) {
                return;
            }
        }
    }
    
    @Override
    public void drawScreen(final int x, final int y, final float z) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, this.field_146500_a, this.width / 2, this.is64bit ? 20 : 5, 16777215);
        super.drawScreen(x, y, z);
        if (Math.abs(x - this.lastMouseX) <= 5 && Math.abs(y - this.lastMouseY) <= 5) {
            final short activateDelay = 700;
            if (System.currentTimeMillis() >= this.mouseStillTime + activateDelay) {
                final int x2 = this.width / 2 - 150;
                int y2 = this.height / 6 - 5;
                if (y <= y2 + 98) {
                    y2 += 105;
                }
                final int x3 = x2 + 150 + 150;
                final int y3 = y2 + 84 + 32;
                final GuiButton btn = this.getSelectedButton(x, y);
                if (btn != null) {
                    final String s = this.getButtonName(btn.displayString);
                    final String[] lines = this.getTooltipLines(s);
                    if (lines == null) {
                        return;
                    }
                    this.drawGradientRect(x2, y2, x3, y3, -536870912, -536870912);
                    for (int i = 0; i < lines.length; ++i) {
                        final String line = lines[i];
                        this.fontRendererObj.drawStringWithShadow(line, x2 + 5, y2 + 5 + i * 11, 14540253);
                    }
                }
            }
        }
        else {
            this.lastMouseX = x;
            this.lastMouseY = y;
            this.mouseStillTime = System.currentTimeMillis();
        }
    }
    
    private String[] getTooltipLines(final String btnName) {
        return (String[])(btnName.equals("Graphics") ? new String[] { "Visual quality", "  Fast  - lower quality, faster", "  Fancy - higher quality, slower", "Changes the appearance of clouds, leaves, water,", "shadows and grass sides." } : (btnName.equals("Render Distance") ? new String[] { "Visible distance", "  2 Tiny - 32m (fastest)", "  4 Short - 64m (faster)", "  8 Normal - 128m", "  16 Far - 256m (slower)", "  32 Extreme - 512m (slowest!)", "The Extreme view distance is very resource demanding!", "Values over 16 Far are only effective in local worlds." } : (btnName.equals("Smooth Lighting") ? new String[] { "Smooth lighting", "  OFF - no smooth lighting (faster)", "  Minimum - simple smooth lighting (slower)", "  Maximum - complex smooth lighting (slowest)" } : (btnName.equals("Smooth Lighting Level") ? new String[] { "Smooth lighting level", "  OFF - no smooth lighting (faster)", "  1% - light smooth lighting (slower)", "  100% - dark smooth lighting (slower)" } : (btnName.equals("Max Framerate") ? new String[] { "Max framerate", "  VSync - limit to monitor framerate (60, 30, 20)", "  5-255 - variable", "  Unlimited - no limit (fastest)", "The framerate limit decreases the FPS even if", "the limit value is not reached." } : (btnName.equals("View Bobbing") ? new String[] { "More realistic movement.", "When using mipmaps set it to OFF for best results." } : (btnName.equals("GUI Scale") ? new String[] { "GUI Scale", "Smaller GUI might be faster" } : (btnName.equals("Server Textures") ? new String[] { "Server textures", "Use the resource pack recommended by the server" } : (btnName.equals("Advanced OpenGL") ? new String[] { "Detect and render only visible geometry", "  OFF - all geometry is rendered (slower)", "  Fast - only visible geometry is rendered (fastest)", "  Fancy - conservative, avoids visual artifacts (faster)", "The option is available only if it is supported by the ", "graphic card." } : (btnName.equals("Fog") ? new String[] { "Fog type", "  Fast - faster fog", "  Fancy - slower fog, looks better", "  OFF - no fog, fastest", "The fancy fog is available only if it is supported by the ", "graphic card." } : (btnName.equals("Fog Start") ? new String[] { "Fog start", "  0.2 - the fog starts near the player", "  0.8 - the fog starts far from the player", "This option usually does not affect the performance." } : (btnName.equals("Brightness") ? new String[] { "Increases the brightness of darker objects", "  OFF - standard brightness", "  100% - maximum brightness for darker objects", "This options does not change the brightness of ", "fully black objects" } : (btnName.equals("Chunk Loading") ? new String[] { "Chunk Loading", "  Default - unstable FPS when loading chunks", "  Smooth - stable FPS", "  Multi-Core - stable FPS, 3x faster world loading", "Smooth and Multi-Core remove the stuttering and ", "freezes caused by chunk loading.", "Multi-Core can speed up 3x the world loading and", "increase FPS by using a second CPU core.", "", "Currently you can only use 'Smooth' chunk loading." } : null)))))))))))));
    }
    
    private String getButtonName(final String displayString) {
        final int pos = displayString.indexOf(58);
        return (pos < 0) ? displayString : displayString.substring(0, pos);
    }
    
    private GuiButton getSelectedButton(final int i, final int j) {
        for (int k = 0; k < this.buttonList.size(); ++k) {
            final GuiButton btn = this.buttonList.get(k);
            final boolean flag = i >= btn.x && j >= btn.y && i < btn.x + btn.width && j < btn.y + btn.height;
            if (flag) {
                return btn;
            }
        }
        return null;
    }
    
    public static int getButtonWidth(final GuiButton btn) {
        return btn.width;
    }
    
    public static int getButtonHeight(final GuiButton btn) {
        return btn.height;
    }
    
    static {
        GuiVideoSettings.field_146502_i = new GameSettings.Options[] { GameSettings.Options.GRAPHICS, GameSettings.Options.RENDER_DISTANCE, GameSettings.Options.AMBIENT_OCCLUSION, GameSettings.Options.FRAMERATE_LIMIT, GameSettings.Options.AO_LEVEL, GameSettings.Options.VIEW_BOBBING, GameSettings.Options.GUI_SCALE, GameSettings.Options.ADVANCED_OPENGL, GameSettings.Options.GAMMA, GameSettings.Options.FOG_FANCY, GameSettings.Options.FOG_START };
    }
}
