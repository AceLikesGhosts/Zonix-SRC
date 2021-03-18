package net.minecraft.util;

public enum EnumFacing
{
    DOWN(0, 1, 0, -1, 0), 
    UP(1, 0, 0, 1, 0), 
    NORTH(2, 3, 0, 0, -1), 
    SOUTH(3, 2, 0, 0, 1), 
    EAST(4, 5, -1, 0, 0), 
    WEST(5, 4, 1, 0, 0);
    
    private final int order_a;
    private final int order_b;
    private final int frontOffsetX;
    private final int frontOffsetY;
    private final int frontOffsetZ;
    private static final EnumFacing[] faceList;
    private static final String __OBFID = "CL_00001201";
    
    private EnumFacing(final int p_i1367_3_, final int p_i1367_4_, final int p_i1367_5_, final int p_i1367_6_, final int p_i1367_7_) {
        this.order_a = p_i1367_3_;
        this.order_b = p_i1367_4_;
        this.frontOffsetX = p_i1367_5_;
        this.frontOffsetY = p_i1367_6_;
        this.frontOffsetZ = p_i1367_7_;
    }
    
    public int getFrontOffsetX() {
        return this.frontOffsetX;
    }
    
    public int getFrontOffsetY() {
        return this.frontOffsetY;
    }
    
    public int getFrontOffsetZ() {
        return this.frontOffsetZ;
    }
    
    public static EnumFacing getFront(final int p_82600_0_) {
        return EnumFacing.faceList[p_82600_0_ % EnumFacing.faceList.length];
    }
    
    static {
        faceList = new EnumFacing[6];
        for (final EnumFacing var4 : values()) {
            EnumFacing.faceList[var4.order_a] = var4;
        }
    }
}
