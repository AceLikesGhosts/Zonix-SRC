package us.zonix.client.module.impl;

import us.zonix.client.module.modules.*;
import com.thevoxelbox.voxelmap.*;

public final class ZansMinimap extends AbstractModule
{
    private final VoxelMap voxelMap;
    
    public ZansMinimap() {
        super("Minimap");
        this.voxelMap = new VoxelMap(true, true);
    }
    
    @Override
    public void renderReal() {
        if (this.mc.currentScreen == null) {
            this.voxelMap.onTickInGame(this.mc);
        }
    }
}
