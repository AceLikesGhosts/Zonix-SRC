package net.minecraft.tileentity;

import net.minecraft.entity.player.*;
import net.minecraft.nbt.*;
import net.minecraft.network.*;
import net.minecraft.network.play.server.*;

public class TileEntitySign extends TileEntity
{
    public String[] field_145915_a;
    public int field_145918_i;
    private boolean field_145916_j;
    private EntityPlayer field_145917_k;
    private static final String __OBFID = "CL_00000363";
    
    public TileEntitySign() {
        this.field_145915_a = new String[] { "", "", "", "" };
        this.field_145918_i = -1;
        this.field_145916_j = true;
    }
    
    @Override
    public void writeToNBT(final NBTTagCompound p_145841_1_) {
        super.writeToNBT(p_145841_1_);
        p_145841_1_.setString("Text1", this.field_145915_a[0]);
        p_145841_1_.setString("Text2", this.field_145915_a[1]);
        p_145841_1_.setString("Text3", this.field_145915_a[2]);
        p_145841_1_.setString("Text4", this.field_145915_a[3]);
    }
    
    @Override
    public void readFromNBT(final NBTTagCompound p_145839_1_) {
        this.field_145916_j = false;
        super.readFromNBT(p_145839_1_);
        for (int var2 = 0; var2 < 4; ++var2) {
            this.field_145915_a[var2] = p_145839_1_.getString("Text" + (var2 + 1));
            if (this.field_145915_a[var2].length() > 15) {
                this.field_145915_a[var2] = this.field_145915_a[var2].substring(0, 15);
            }
        }
    }
    
    @Override
    public Packet getDescriptionPacket() {
        final String[] var1 = new String[4];
        System.arraycopy(this.field_145915_a, 0, var1, 0, 4);
        return new S33PacketUpdateSign(this.field_145851_c, this.field_145848_d, this.field_145849_e, var1);
    }
    
    public boolean func_145914_a() {
        return this.field_145916_j;
    }
    
    public void func_145913_a(final boolean p_145913_1_) {
        if (!(this.field_145916_j = p_145913_1_)) {
            this.field_145917_k = null;
        }
    }
    
    public void func_145912_a(final EntityPlayer p_145912_1_) {
        this.field_145917_k = p_145912_1_;
    }
    
    public EntityPlayer func_145911_b() {
        return this.field_145917_k;
    }
}
