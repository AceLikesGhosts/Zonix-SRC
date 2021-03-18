package net.minecraft.client.renderer;

import java.util.*;
import net.minecraft.entity.*;

public class EntitySorter implements Comparator
{
    private double entityPosX;
    private double entityPosY;
    private double entityPosZ;
    private static final String __OBFID = "CL_00000944";
    
    public EntitySorter(final Entity p_i1242_1_) {
        this.entityPosX = -p_i1242_1_.posX;
        this.entityPosY = -p_i1242_1_.posY;
        this.entityPosZ = -p_i1242_1_.posZ;
    }
    
    public int compare(final WorldRenderer p_compare_1_, final WorldRenderer p_compare_2_) {
        final double var3 = p_compare_1_.posXPlus + this.entityPosX;
        final double var4 = p_compare_1_.posYPlus + this.entityPosY;
        final double var5 = p_compare_1_.posZPlus + this.entityPosZ;
        final double var6 = p_compare_2_.posXPlus + this.entityPosX;
        final double var7 = p_compare_2_.posYPlus + this.entityPosY;
        final double var8 = p_compare_2_.posZPlus + this.entityPosZ;
        return (int)((var3 * var3 + var4 * var4 + var5 * var5 - (var6 * var6 + var7 * var7 + var8 * var8)) * 1024.0);
    }
    
    @Override
    public int compare(final Object p_compare_1_, final Object p_compare_2_) {
        return this.compare((WorldRenderer)p_compare_1_, (WorldRenderer)p_compare_2_);
    }
}
