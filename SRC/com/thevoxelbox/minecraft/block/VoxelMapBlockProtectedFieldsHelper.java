package com.thevoxelbox.minecraft.block;

import net.minecraft.block.*;

public class VoxelMapBlockProtectedFieldsHelper
{
    public static void setLightOpacity(final Block block, final int opacity) {
        block.setLightOpacity(opacity);
    }
}
