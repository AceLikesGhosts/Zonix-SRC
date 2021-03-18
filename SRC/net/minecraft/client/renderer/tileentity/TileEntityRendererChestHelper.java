package net.minecraft.client.renderer.tileentity;

import net.minecraft.block.*;
import net.minecraft.init.*;
import net.minecraft.tileentity.*;

public class TileEntityRendererChestHelper
{
    public static TileEntityRendererChestHelper instance;
    private TileEntityChest field_147717_b;
    private TileEntityChest field_147718_c;
    private TileEntityEnderChest field_147716_d;
    private static final String __OBFID = "CL_00000946";
    
    public TileEntityRendererChestHelper() {
        this.field_147717_b = new TileEntityChest(0);
        this.field_147718_c = new TileEntityChest(1);
        this.field_147716_d = new TileEntityEnderChest();
    }
    
    public void func_147715_a(final Block p_147715_1_, final int p_147715_2_, final float p_147715_3_) {
        if (p_147715_1_ == Blocks.ender_chest) {
            TileEntityRendererDispatcher.instance.func_147549_a(this.field_147716_d, 0.0, 0.0, 0.0, 0.0f);
        }
        else if (p_147715_1_ == Blocks.trapped_chest) {
            TileEntityRendererDispatcher.instance.func_147549_a(this.field_147718_c, 0.0, 0.0, 0.0, 0.0f);
        }
        else {
            TileEntityRendererDispatcher.instance.func_147549_a(this.field_147717_b, 0.0, 0.0, 0.0, 0.0f);
        }
    }
    
    static {
        TileEntityRendererChestHelper.instance = new TileEntityRendererChestHelper();
    }
}
