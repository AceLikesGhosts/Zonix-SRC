package net.minecraft.village;

public class VillageDoorInfo
{
    public final int posX;
    public final int posY;
    public final int posZ;
    public final int insideDirectionX;
    public final int insideDirectionZ;
    public int lastActivityTimestamp;
    public boolean isDetachedFromVillageFlag;
    private int doorOpeningRestrictionCounter;
    private static final String __OBFID = "CL_00001630";
    
    public VillageDoorInfo(final int p_i1673_1_, final int p_i1673_2_, final int p_i1673_3_, final int p_i1673_4_, final int p_i1673_5_, final int p_i1673_6_) {
        this.posX = p_i1673_1_;
        this.posY = p_i1673_2_;
        this.posZ = p_i1673_3_;
        this.insideDirectionX = p_i1673_4_;
        this.insideDirectionZ = p_i1673_5_;
        this.lastActivityTimestamp = p_i1673_6_;
    }
    
    public int getDistanceSquared(final int p_75474_1_, final int p_75474_2_, final int p_75474_3_) {
        final int var4 = p_75474_1_ - this.posX;
        final int var5 = p_75474_2_ - this.posY;
        final int var6 = p_75474_3_ - this.posZ;
        return var4 * var4 + var5 * var5 + var6 * var6;
    }
    
    public int getInsideDistanceSquare(final int p_75469_1_, final int p_75469_2_, final int p_75469_3_) {
        final int var4 = p_75469_1_ - this.posX - this.insideDirectionX;
        final int var5 = p_75469_2_ - this.posY;
        final int var6 = p_75469_3_ - this.posZ - this.insideDirectionZ;
        return var4 * var4 + var5 * var5 + var6 * var6;
    }
    
    public int getInsidePosX() {
        return this.posX + this.insideDirectionX;
    }
    
    public int getInsidePosY() {
        return this.posY;
    }
    
    public int getInsidePosZ() {
        return this.posZ + this.insideDirectionZ;
    }
    
    public boolean isInside(final int p_75467_1_, final int p_75467_2_) {
        final int var3 = p_75467_1_ - this.posX;
        final int var4 = p_75467_2_ - this.posZ;
        return var3 * this.insideDirectionX + var4 * this.insideDirectionZ >= 0;
    }
    
    public void resetDoorOpeningRestrictionCounter() {
        this.doorOpeningRestrictionCounter = 0;
    }
    
    public void incrementDoorOpeningRestrictionCounter() {
        ++this.doorOpeningRestrictionCounter;
    }
    
    public int getDoorOpeningRestrictionCounter() {
        return this.doorOpeningRestrictionCounter;
    }
}
