package com.thevoxelbox.voxelmap.gui.overridden;

import net.minecraft.client.gui.*;

public class GuiOptionButtonMinimap extends GuiButton
{
    private final EnumOptionsMinimap enumOptions;
    
    public GuiOptionButtonMinimap(final int par1, final int par2, final int par3, final String par4Str) {
        this(par1, par2, par3, null, par4Str);
    }
    
    public GuiOptionButtonMinimap(final int par1, final int par2, final int par3, final int par4, final int par5, final String par6Str) {
        super(par1, par2, par3, par4, par5, par6Str);
        this.enumOptions = null;
    }
    
    public GuiOptionButtonMinimap(final int par1, final int par2, final int par3, final EnumOptionsMinimap par4EnumOptions, final String par5Str) {
        super(par1, par2, par3, 150, 20, par5Str);
        this.enumOptions = par4EnumOptions;
    }
    
    public EnumOptionsMinimap returnEnumOptions() {
        return this.enumOptions;
    }
}
