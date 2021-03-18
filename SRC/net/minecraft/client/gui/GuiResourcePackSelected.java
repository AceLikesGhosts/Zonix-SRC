package net.minecraft.client.gui;

import net.minecraft.client.*;
import java.util.*;
import net.minecraft.client.resources.*;

public class GuiResourcePackSelected extends GuiResourcePackList
{
    private static final String __OBFID = "CL_00000827";
    
    public GuiResourcePackSelected(final Minecraft p_i45056_1_, final int p_i45056_2_, final int p_i45056_3_, final List p_i45056_4_) {
        super(p_i45056_1_, p_i45056_2_, p_i45056_3_, p_i45056_4_);
    }
    
    @Override
    protected String func_148202_k() {
        return I18n.format("resourcePack.selected.title", new Object[0]);
    }
}
