package net.minecraft.src;

import net.minecraft.client.renderer.*;

public class WrDisplayListBlock
{
    private int start;
    private int used;
    private int end;
    public static final int BLOCK_LENGTH = 16384;
    
    public WrDisplayListBlock() {
        this.start = -1;
        this.used = -1;
        this.end = -1;
        this.start = GLAllocation.generateDisplayLists(16384);
        this.used = this.start;
        this.end = this.start + 16384;
    }
    
    public boolean canAllocate(final int len) {
        return this.used + len < this.end;
    }
    
    public int allocate(final int len) {
        if (!this.canAllocate(len)) {
            return -1;
        }
        final int allocated = this.used;
        this.used += len;
        return allocated;
    }
    
    public void reset() {
        this.used = this.start;
    }
    
    public void deleteDisplayLists() {
        GLAllocation.deleteDisplayLists(this.start);
    }
    
    public int getStart() {
        return this.start;
    }
    
    public int getUsed() {
        return this.used;
    }
    
    public int getEnd() {
        return this.end;
    }
}
