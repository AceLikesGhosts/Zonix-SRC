package net.minecraft.client.gui;

import net.minecraft.client.*;
import java.util.*;
import net.minecraft.client.resources.*;

public class GuiResourcePackAvailable extends GuiResourcePackList
{
    private static final String __OBFID = "CL_00000824";
    
    public GuiResourcePackAvailable(final Minecraft p_i45054_1_, final int p_i45054_2_, final int p_i45054_3_, final List p_i45054_4_) {
        super(p_i45054_1_, p_i45054_2_, p_i45054_3_, p_i45054_4_);
    }
    
    @Override
    protected String func_148202_k() {
        return I18n.format("resourcePack.available.title", new Object[0]);
    }
}
