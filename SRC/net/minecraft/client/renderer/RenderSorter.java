package net.minecraft.client.renderer;

import java.util.*;
import net.minecraft.entity.*;

public class RenderSorter implements Comparator
{
    private EntityLivingBase baseEntity;
    private static final String __OBFID = "CL_00000943";
    
    public RenderSorter(final EntityLivingBase p_i1241_1_) {
        this.baseEntity = p_i1241_1_;
    }
    
    public int compare(final WorldRenderer p_compare_1_, final WorldRenderer p_compare_2_) {
        if (p_compare_1_.isInFrustum && !p_compare_2_.isInFrustum) {
            return 1;
        }
        if (p_compare_2_.isInFrustum && !p_compare_1_.isInFrustum) {
            return -1;
        }
        final double var3 = p_compare_1_.distanceToEntitySquared(this.baseEntity);
        final double var4 = p_compare_2_.distanceToEntitySquared(this.baseEntity);
        return (var3 < var4) ? 1 : ((var3 > var4) ? -1 : ((p_compare_1_.chunkIndex < p_compare_2_.chunkIndex) ? 1 : -1));
    }
    
    @Override
    public int compare(final Object p_compare_1_, final Object p_compare_2_) {
        return this.compare((WorldRenderer)p_compare_1_, (WorldRenderer)p_compare_2_);
    }
}
