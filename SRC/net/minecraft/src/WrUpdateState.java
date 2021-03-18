package net.minecraft.src;

import net.minecraft.client.renderer.*;
import java.util.*;

public class WrUpdateState
{
    public ChunkCacheOF chunkcache;
    public RenderBlocks renderblocks;
    public HashSet setOldEntityRenders;
    int viewEntityPosX;
    int viewEntityPosY;
    int viewEntityPosZ;
    public int renderPass;
    public int y;
    public boolean flag;
    public boolean hasRenderedBlocks;
    public boolean hasGlList;
    
    public WrUpdateState() {
        this.chunkcache = null;
        this.renderblocks = null;
        this.setOldEntityRenders = null;
        this.viewEntityPosX = 0;
        this.viewEntityPosY = 0;
        this.viewEntityPosZ = 0;
        this.renderPass = 0;
        this.y = 0;
        this.flag = false;
        this.hasRenderedBlocks = false;
        this.hasGlList = false;
    }
}
