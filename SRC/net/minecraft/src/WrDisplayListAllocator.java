package net.minecraft.src;

import java.util.*;

public class WrDisplayListAllocator
{
    private List<WrDisplayListBlock> listBlocks;
    private WrDisplayListBlock currentBlock;
    private int blockIndex;
    
    public WrDisplayListAllocator() {
        this.listBlocks = new ArrayList<WrDisplayListBlock>();
        this.currentBlock = null;
        this.blockIndex = -1;
    }
    
    public int allocateDisplayLists(final int len) {
        if (len > 0 && len <= 16384) {
            if (this.currentBlock == null || !this.currentBlock.canAllocate(len)) {
                if (this.blockIndex + 1 < this.listBlocks.size()) {
                    ++this.blockIndex;
                    this.currentBlock = this.listBlocks.get(this.blockIndex);
                }
                else {
                    this.currentBlock = new WrDisplayListBlock();
                    this.blockIndex = this.listBlocks.size();
                    this.listBlocks.add(this.currentBlock);
                }
                if (!this.currentBlock.canAllocate(len)) {
                    throw new IllegalArgumentException("Can not allocate: " + len);
                }
            }
            return this.currentBlock.allocate(len);
        }
        throw new IllegalArgumentException("Invalid display list length: " + len);
    }
    
    public void resetAllocatedLists() {
        this.currentBlock = null;
        this.blockIndex = -1;
        for (int i = 0; i < this.listBlocks.size(); ++i) {
            final WrDisplayListBlock block = this.listBlocks.get(i);
            block.reset();
        }
    }
    
    public void deleteDisplayLists() {
        for (int i = 0; i < this.listBlocks.size(); ++i) {
            final WrDisplayListBlock block = this.listBlocks.get(i);
            block.deleteDisplayLists();
        }
        this.listBlocks.clear();
        this.currentBlock = null;
        this.blockIndex = -1;
    }
}
