package net.minecraft.server.management;

import java.util.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;

public class PlayerPositionComparator implements Comparator
{
    private final ChunkCoordinates theChunkCoordinates;
    private static final String __OBFID = "CL_00001422";
    
    public PlayerPositionComparator(final ChunkCoordinates p_i1499_1_) {
        this.theChunkCoordinates = p_i1499_1_;
    }
    
    public int compare(final EntityPlayerMP p_compare_1_, final EntityPlayerMP p_compare_2_) {
        final double var3 = p_compare_1_.getDistanceSq(this.theChunkCoordinates.posX, this.theChunkCoordinates.posY, this.theChunkCoordinates.posZ);
        final double var4 = p_compare_2_.getDistanceSq(this.theChunkCoordinates.posX, this.theChunkCoordinates.posY, this.theChunkCoordinates.posZ);
        return (var3 < var4) ? -1 : ((var3 > var4) ? 1 : 0);
    }
    
    @Override
    public int compare(final Object p_compare_1_, final Object p_compare_2_) {
        return this.compare((EntityPlayerMP)p_compare_1_, (EntityPlayerMP)p_compare_2_);
    }
}
