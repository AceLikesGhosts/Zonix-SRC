package net.minecraft.client.gui;

import com.google.common.collect.*;
import net.minecraft.util.*;
import java.net.*;
import org.lwjgl.*;
import java.util.*;
import java.io.*;
import net.minecraft.client.resources.*;
import org.apache.logging.log4j.*;

public class GuiScreenResourcePacks extends GuiScreen
{
    private static final Logger logger;
    private GuiScreen field_146965_f;
    private List field_146966_g;
    private List field_146969_h;
    private GuiResourcePackAvailable field_146970_i;
    private GuiResourcePackSelected field_146967_r;
    private static final String __OBFID = "CL_00000820";
    private boolean loaded;
    
    public GuiScreenResourcePacks(final GuiScreen p_i45050_1_) {
        this.field_146965_f = p_i45050_1_;
    }
    
    @Override
    public void initGui() {
        this.buttonList.add(new GuiOptionButton(2, this.width / 2 - 154, this.height - 48, I18n.format("resourcePack.openFolder", new Object[0])));
        this.buttonList.add(new GuiOptionButton(1, this.width / 2 + 4, this.height - 48, I18n.format("gui.done", new Object[0])));
        this.field_146966_g = new ArrayList();
        this.field_146969_h = new ArrayList();
        final ResourcePackRepository var1;
        final ArrayList var2;
        final Iterator var3;
        ResourcePackRepository.Entry var4;
        final Iterator var5;
        ResourcePackRepository.Entry var6;
        new Thread(() -> {
            var1 = this.mc.getResourcePackRepository();
            var1.updateRepositoryEntriesAll();
            var2 = Lists.newArrayList((Iterable)var1.getRepositoryEntriesAll());
            var2.removeAll(var1.getRepositoryEntries());
            var3 = var2.iterator();
            while (var3.hasNext()) {
                var4 = var3.next();
                this.field_146966_g.add(new ResourcePackListEntryFound(this, var4));
            }
            var5 = Lists.reverse(var1.getRepositoryEntries()).iterator();
            while (var5.hasNext()) {
                var6 = var5.next();
                this.field_146969_h.add(new ResourcePackListEntryFound(this, var6));
            }
            this.loaded = true;
            return;
        }).start();
        this.field_146970_i = new GuiResourcePackAvailable(this.mc, 200, this.height, this.field_146966_g);
        this.field_146967_r = new GuiResourcePackSelected(this.mc, 200, this.height, this.field_146969_h);
        this.field_146970_i.func_148140_g(this.width / 2 - 4 - 200);
        this.field_146970_i.func_148134_d(7, 8);
        this.field_146967_r.func_148140_g(this.width / 2 + 4);
        this.field_146967_r.func_148134_d(7, 8);
    }
    
    public boolean func_146961_a(final ResourcePackListEntry p_146961_1_) {
        return this.field_146969_h.contains(p_146961_1_);
    }
    
    public List func_146962_b(final ResourcePackListEntry p_146962_1_) {
        return this.func_146961_a(p_146962_1_) ? this.field_146969_h : this.field_146966_g;
    }
    
    public List func_146964_g() {
        return this.field_146966_g;
    }
    
    public List func_146963_h() {
        return this.field_146969_h;
    }
    
    @Override
    protected void actionPerformed(final GuiButton p_146284_1_) {
        if (p_146284_1_.enabled) {
            if (p_146284_1_.id == 2) {
                final File var2 = this.mc.getResourcePackRepository().getDirResourcepacks();
                final String var3 = var2.getAbsolutePath();
                Label_0135: {
                    if (Util.getOSType() == Util.EnumOS.OSX) {
                        try {
                            GuiScreenResourcePacks.logger.info(var3);
                            Runtime.getRuntime().exec(new String[] { "/usr/bin/open", var3 });
                            return;
                        }
                        catch (IOException var4) {
                            GuiScreenResourcePacks.logger.error("Couldn't open file", (Throwable)var4);
                            break Label_0135;
                        }
                    }
                    if (Util.getOSType() == Util.EnumOS.WINDOWS) {
                        final String var5 = String.format("cmd.exe /C start \"Open file\" \"%s\"", var3);
                        try {
                            Runtime.getRuntime().exec(var5);
                            return;
                        }
                        catch (IOException var6) {
                            GuiScreenResourcePacks.logger.error("Couldn't open file", (Throwable)var6);
                        }
                    }
                }
                boolean var7 = false;
                try {
                    final Class var8 = Class.forName("java.awt.Desktop");
                    final Object var9 = var8.getMethod("getDesktop", (Class[])new Class[0]).invoke(null, new Object[0]);
                    var8.getMethod("browse", URI.class).invoke(var9, var2.toURI());
                }
                catch (Throwable var10) {
                    GuiScreenResourcePacks.logger.error("Couldn't open link", var10);
                    var7 = true;
                }
                if (var7) {
                    GuiScreenResourcePacks.logger.info("Opening via system class!");
                    Sys.openURL("file://" + var3);
                }
            }
            else if (p_146284_1_.id == 1) {
                final ArrayList var11 = Lists.newArrayList();
                for (final ResourcePackListEntry var13 : this.field_146969_h) {
                    if (var13 instanceof ResourcePackListEntryFound) {
                        var11.add(((ResourcePackListEntryFound)var13).func_148318_i());
                    }
                }
                Collections.reverse(var11);
                this.mc.getResourcePackRepository().func_148527_a(var11);
                this.mc.gameSettings.resourcePacks.clear();
                for (final ResourcePackRepository.Entry var14 : var11) {
                    this.mc.gameSettings.resourcePacks.add(var14.getResourcePackName());
                }
                this.mc.gameSettings.saveOptions();
                this.mc.refreshResources();
                this.mc.displayGuiScreen(this.field_146965_f);
            }
        }
    }
    
    @Override
    protected void mouseClicked(final int p_73864_1_, final int p_73864_2_, final int p_73864_3_) {
        super.mouseClicked(p_73864_1_, p_73864_2_, p_73864_3_);
        if (this.field_146970_i != null) {
            this.field_146970_i.func_148179_a(p_73864_1_, p_73864_2_, p_73864_3_);
            this.field_146967_r.func_148179_a(p_73864_1_, p_73864_2_, p_73864_3_);
        }
    }
    
    @Override
    protected void mouseMovedOrUp(final int p_146286_1_, final int p_146286_2_, final int p_146286_3_) {
        super.mouseMovedOrUp(p_146286_1_, p_146286_2_, p_146286_3_);
    }
    
    @Override
    public void drawScreen(final int p_73863_1_, final int p_73863_2_, final float p_73863_3_) {
        if (this.loaded) {
            this.field_146969_h.add(new ResourcePackListEntryDefault(this));
            this.loaded = false;
        }
        this.func_146278_c(0);
        if (this.field_146970_i == null) {
            super.drawScreen(p_73863_1_, p_73863_2_, p_73863_3_);
            return;
        }
        this.field_146970_i.func_148128_a(p_73863_1_, p_73863_2_, p_73863_3_);
        this.field_146967_r.func_148128_a(p_73863_1_, p_73863_2_, p_73863_3_);
        this.drawCenteredString(this.fontRendererObj, I18n.format("resourcePack.title", new Object[0]), this.width / 2, 16, 16777215);
        this.drawCenteredString(this.fontRendererObj, I18n.format("resourcePack.folderInfo", new Object[0]), this.width / 2 - 77, this.height - 26, 8421504);
        super.drawScreen(p_73863_1_, p_73863_2_, p_73863_3_);
    }
    
    static {
        logger = LogManager.getLogger();
    }
}
