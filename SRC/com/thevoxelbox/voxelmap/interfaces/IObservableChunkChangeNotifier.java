package com.thevoxelbox.voxelmap.interfaces;

import net.minecraft.world.chunk.*;
import java.util.*;

public interface IObservableChunkChangeNotifier
{
    void chunkChanged(final Chunk p0);
    
    void addObserver(final Observer p0);
}
