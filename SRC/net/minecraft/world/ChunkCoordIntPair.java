package net.minecraft.world;

public class ChunkCoordIntPair
{
    public final int chunkXPos;
    public final int chunkZPos;
    private static final String __OBFID = "CL_00000133";
    
    public ChunkCoordIntPair(final int p_i1947_1_, final int p_i1947_2_) {
        this.chunkXPos = p_i1947_1_;
        this.chunkZPos = p_i1947_2_;
    }
    
    public static long chunkXZ2Int(final int p_77272_0_, final int p_77272_1_) {
        return ((long)p_77272_0_ & 0xFFFFFFFFL) | ((long)p_77272_1_ & 0xFFFFFFFFL) << 32;
    }
    
    @Override
    public int hashCode() {
        final int var1 = 1664525 * this.chunkXPos + 1013904223;
        final int var2 = 1664525 * (this.chunkZPos ^ 0xDEADBEEF) + 1013904223;
        return var1 ^ var2;
    }
    
    @Override
    public boolean equals(final Object p_equals_1_) {
        if (this == p_equals_1_) {
            return true;
        }
        if (!(p_equals_1_ instanceof ChunkCoordIntPair)) {
            return false;
        }
        final ChunkCoordIntPair var2 = (ChunkCoordIntPair)p_equals_1_;
        return this.chunkXPos == var2.chunkXPos && this.chunkZPos == var2.chunkZPos;
    }
    
    public int getCenterXPos() {
        return (this.chunkXPos << 4) + 8;
    }
    
    public int getCenterZPosition() {
        return (this.chunkZPos << 4) + 8;
    }
    
    public ChunkPosition func_151349_a(final int p_151349_1_) {
        return new ChunkPosition(this.getCenterXPos(), p_151349_1_, this.getCenterZPosition());
    }
    
    @Override
    public String toString() {
        return "[" + this.chunkXPos + ", " + this.chunkZPos + "]";
    }
}
