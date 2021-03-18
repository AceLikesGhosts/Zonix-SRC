package com.thevoxelbox.voxelmap.interfaces;

import java.util.*;
import com.thevoxelbox.voxelmap.util.*;

public interface IDimensionManager
{
    ArrayList<Dimension> getDimensions();
    
    Dimension getDimensionByID(final int p0);
    
    void enteredDimension(final int p0);
    
    void populateDimensions();
}
