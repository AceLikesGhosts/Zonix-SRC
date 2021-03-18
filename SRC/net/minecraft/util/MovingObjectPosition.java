package net.minecraft.util;

import net.minecraft.entity.*;

public class MovingObjectPosition
{
    public MovingObjectType typeOfHit;
    public int blockX;
    public int blockY;
    public int blockZ;
    public int sideHit;
    public Vec3 hitVec;
    public Entity entityHit;
    private static final String __OBFID = "CL_00000610";
    
    public MovingObjectPosition(final int p_i2303_1_, final int p_i2303_2_, final int p_i2303_3_, final int p_i2303_4_, final Vec3 p_i2303_5_) {
        this(p_i2303_1_, p_i2303_2_, p_i2303_3_, p_i2303_4_, p_i2303_5_, true);
    }
    
    public MovingObjectPosition(final int p_i45481_1_, final int p_i45481_2_, final int p_i45481_3_, final int p_i45481_4_, final Vec3 p_i45481_5_, final boolean p_i45481_6_) {
        this.typeOfHit = (p_i45481_6_ ? MovingObjectType.BLOCK : MovingObjectType.MISS);
        this.blockX = p_i45481_1_;
        this.blockY = p_i45481_2_;
        this.blockZ = p_i45481_3_;
        this.sideHit = p_i45481_4_;
        this.hitVec = Vec3.createVectorHelper(p_i45481_5_.xCoord, p_i45481_5_.yCoord, p_i45481_5_.zCoord);
    }
    
    public MovingObjectPosition(final Entity p_i2304_1_) {
        this(p_i2304_1_, Vec3.createVectorHelper(p_i2304_1_.posX, p_i2304_1_.posY, p_i2304_1_.posZ));
    }
    
    public MovingObjectPosition(final Entity p_i45482_1_, final Vec3 p_i45482_2_) {
        this.typeOfHit = MovingObjectType.ENTITY;
        this.entityHit = p_i45482_1_;
        this.hitVec = p_i45482_2_;
    }
    
    @Override
    public String toString() {
        return "HitResult{type=" + this.typeOfHit + ", x=" + this.blockX + ", y=" + this.blockY + ", z=" + this.blockZ + ", f=" + this.sideHit + ", pos=" + this.hitVec + ", entity=" + this.entityHit + '}';
    }
    
    public enum MovingObjectType
    {
        MISS("MISS", 0), 
        BLOCK("BLOCK", 1), 
        ENTITY("ENTITY", 2);
        
        private static final MovingObjectType[] $VALUES;
        private static final String __OBFID = "CL_00000611";
        
        private MovingObjectType(final String p_i2302_1_, final int p_i2302_2_) {
        }
        
        static {
            $VALUES = new MovingObjectType[] { MovingObjectType.MISS, MovingObjectType.BLOCK, MovingObjectType.ENTITY };
        }
    }
}
