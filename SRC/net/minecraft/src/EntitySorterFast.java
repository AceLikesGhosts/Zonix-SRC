package net.minecraft.src;

import java.util.*;
import net.minecraft.entity.*;
import net.minecraft.client.renderer.*;

public class EntitySorterFast implements Comparator
{
    private Entity entity;
    
    public EntitySorterFast(final Entity par1Entity) {
        this.entity = par1Entity;
    }
    
    public void prepareToSort(final WorldRenderer[] renderers, final int countWorldRenderers) {
        for (final WorldRenderer wr : renderers) {
            wr.updateDistanceToEntitySquared(this.entity);
        }
    }
    
    public int compare(final WorldRenderer par1WorldRenderer, final WorldRenderer par2WorldRenderer) {
        final float dist1 = par1WorldRenderer.sortDistanceToEntitySquared;
        final float dist2 = par2WorldRenderer.sortDistanceToEntitySquared;
        return (dist1 == dist2) ? 0 : ((dist1 > dist2) ? 1 : -1);
    }
    
    @Override
    public int compare(final Object par1Obj, final Object par2Obj) {
        return this.compare((WorldRenderer)par1Obj, (WorldRenderer)par2Obj);
    }
}
