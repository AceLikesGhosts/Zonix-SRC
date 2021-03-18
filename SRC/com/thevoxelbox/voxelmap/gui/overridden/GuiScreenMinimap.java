package com.thevoxelbox.voxelmap.gui.overridden;

import com.thevoxelbox.voxelmap.interfaces.*;
import org.lwjgl.opengl.*;
import com.thevoxelbox.voxelmap.*;
import net.minecraft.client.*;
import java.util.*;
import net.minecraft.client.gui.*;

public class GuiScreenMinimap extends GuiScreen
{
    public void drawMap() {
        if (!AbstractVoxelMap.instance.getMapOptions().showUnderMenus) {
            AbstractVoxelMap.instance.getMap().drawMinimap(this.mc);
            GL11.glClear(256);
        }
    }
    
    @Override
    public void onGuiClosed() {
        MapSettingsManager.instance.saveAll();
    }
    
    public Minecraft getMinecraft() {
        return this.mc;
    }
    
    public int getWidth() {
        return this.width;
    }
    
    public int getHeight() {
        return this.height;
    }
    
    public List getButtonList() {
        return this.buttonList;
    }
    
    public FontRenderer getFontRenderer() {
        return this.fontRendererObj;
    }
}
