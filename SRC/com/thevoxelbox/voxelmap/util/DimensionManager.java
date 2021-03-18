package com.thevoxelbox.voxelmap.util;

import com.thevoxelbox.voxelmap.interfaces.*;
import net.minecraft.world.*;
import java.util.*;
import net.minecraft.client.*;

public class DimensionManager implements IDimensionManager
{
    public ArrayList<Dimension> dimensions;
    IVoxelMap master;
    
    public DimensionManager(final IVoxelMap master) {
        this.master = master;
        this.dimensions = new ArrayList<Dimension>();
    }
    
    @Override
    public ArrayList<Dimension> getDimensions() {
        return this.dimensions;
    }
    
    @Override
    public void populateDimensions() {
        this.dimensions.clear();
        for (int t = -1; t <= 1; ++t) {
            String name = "notLoaded";
            WorldProvider provider = null;
            try {
                provider = WorldProvider.getProviderForDimension(t);
            }
            catch (Exception e) {
                provider = null;
            }
            if (provider != null) {
                try {
                    name = provider.getDimensionName();
                }
                catch (Exception e) {
                    name = "failedToLoad";
                }
                final Dimension dim = new Dimension(name, t);
                this.dimensions.add(dim);
            }
        }
        for (final Waypoint pt : this.master.getWaypointManager().getWaypoints()) {
            for (final Integer t2 : pt.dimensions) {
                if (this.getDimensionByID(t2) == null) {
                    String name2 = "notLoaded";
                    WorldProvider provider2 = null;
                    try {
                        provider2 = WorldProvider.getProviderForDimension(t2);
                    }
                    catch (Exception e2) {
                        provider2 = null;
                    }
                    if (provider2 == null) {
                        continue;
                    }
                    try {
                        name2 = provider2.getDimensionName();
                    }
                    catch (Exception e2) {
                        name2 = "failedToLoad";
                    }
                    final Dimension dim2 = new Dimension(name2, t2);
                    this.dimensions.add(dim2);
                }
            }
        }
        Collections.sort(this.dimensions, new Comparator<Dimension>() {
            @Override
            public int compare(final Dimension dim1, final Dimension dim2) {
                return dim1.ID - dim2.ID;
            }
        });
    }
    
    @Override
    public void enteredDimension(final int ID) {
        Dimension dim = this.getDimensionByID(ID);
        if (dim == null) {
            dim = new Dimension("notLoaded", ID);
            this.dimensions.add(dim);
            Collections.sort(this.dimensions, new Comparator<Dimension>() {
                @Override
                public int compare(final Dimension dim1, final Dimension dim2) {
                    return dim1.ID - dim2.ID;
                }
            });
        }
        if (!dim.name.equals("notLoaded")) {
            if (!dim.name.equals("failedToLoad")) {
                return;
            }
        }
        try {
            dim.name = Minecraft.getMinecraft().theWorld.provider.getDimensionName();
        }
        catch (Exception e) {
            dim.name = "dimension " + ID + "(" + Minecraft.getMinecraft().theWorld.provider.getClass().getSimpleName() + ")";
        }
    }
    
    @Override
    public Dimension getDimensionByID(final int ID) {
        for (final Dimension dim : this.dimensions) {
            if (dim.ID == ID) {
                return dim;
            }
        }
        return null;
    }
    
    public Dimension getDimensionByName(final String name) {
        for (final Dimension dim : this.dimensions) {
            if (dim.name.equals(name)) {
                return dim;
            }
        }
        return null;
    }
}
