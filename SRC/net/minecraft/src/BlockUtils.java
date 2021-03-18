package net.minecraft.src;

import net.minecraft.block.*;

public class BlockUtils
{
    private static boolean directAccessValid;
    
    public static void setLightOpacity(final Block block, final int opacity) {
        if (BlockUtils.directAccessValid) {
            try {
                block.setLightOpacity(opacity);
            }
            catch (IllegalAccessError var3) {
                BlockUtils.directAccessValid = false;
            }
        }
    }
    
    static {
        BlockUtils.directAccessValid = true;
    }
}
