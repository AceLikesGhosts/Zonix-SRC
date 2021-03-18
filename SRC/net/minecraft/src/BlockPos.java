package net.minecraft.src;

public class BlockPos
{
    private final int x;
    private final int y;
    private final int z;
    
    public BlockPos(final int x, final int y, final int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public int getX() {
        return this.x;
    }
    
    public int getY() {
        return this.y;
    }
    
    public int getZ() {
        return this.z;
    }
    
    public BlockPos add(final BlockPos pos) {
        return new BlockPos(this.x + pos.x, this.y + pos.y, this.z + pos.z);
    }
    
    public BlockPos sub(final BlockPos pos) {
        return new BlockPos(this.x - pos.x, this.y - pos.y, this.z - pos.z);
    }
    
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof BlockPos)) {
            return false;
        }
        final BlockPos blockPos = (BlockPos)obj;
        return this.getX() == blockPos.getX() && this.getY() == blockPos.getY() && this.getZ() == blockPos.getZ();
    }
    
    @Override
    public int hashCode() {
        return (this.getY() + this.getZ() * 31) * 31 + this.getX();
    }
    
    @Override
    public String toString() {
        return "" + this.x + ", " + this.y + ", " + this.z;
    }
}
