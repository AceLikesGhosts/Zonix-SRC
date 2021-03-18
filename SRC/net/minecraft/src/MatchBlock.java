package net.minecraft.src;

public class MatchBlock
{
    private int blockId;
    private int[] metadatas;
    
    public MatchBlock(final int blockId) {
        this.blockId = -1;
        this.metadatas = null;
        this.blockId = blockId;
    }
    
    public MatchBlock(final int blockId, final int metadata) {
        this.blockId = -1;
        this.metadatas = null;
        this.blockId = blockId;
        if (metadata >= 0 && metadata <= 15) {
            this.metadatas = new int[] { metadata };
        }
    }
    
    public MatchBlock(final int blockId, final int[] metadatas) {
        this.blockId = -1;
        this.metadatas = null;
        this.blockId = blockId;
        this.metadatas = metadatas;
    }
    
    public int getBlockId() {
        return this.blockId;
    }
    
    public int[] getMetadatas() {
        return this.metadatas;
    }
    
    public boolean matches(final int id, final int metadata) {
        if (id != this.blockId) {
            return false;
        }
        if (this.metadatas != null) {
            boolean matchMetadata = false;
            for (int i = 0; i < this.metadatas.length; ++i) {
                final int md = this.metadatas[i];
                if (md == metadata) {
                    matchMetadata = true;
                    break;
                }
            }
            if (!matchMetadata) {
                return false;
            }
        }
        return true;
    }
    
    public void addMetadata(final int metadata) {
        if (this.metadatas != null && metadata >= 0 && metadata <= 15) {
            for (int i = 0; i < this.metadatas.length; ++i) {
                if (this.metadatas[i] == metadata) {
                    return;
                }
            }
            this.metadatas = Config.addIntToArray(this.metadatas, metadata);
        }
    }
    
    @Override
    public String toString() {
        return "" + this.blockId + ":" + Config.arrayToString(this.metadatas);
    }
}
