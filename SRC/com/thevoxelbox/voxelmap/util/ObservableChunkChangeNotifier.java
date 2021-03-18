package com.thevoxelbox.voxelmap.util;

import java.util.*;
import com.thevoxelbox.voxelmap.interfaces.*;
import net.minecraft.world.chunk.*;

public class ObservableChunkChangeNotifier extends Observable implements IObservableChunkChangeNotifier
{
    @Override
    public void chunkChanged(final Chunk chunk) {
        this.setChanged();
        this.notifyObservers(chunk);
    }
}
