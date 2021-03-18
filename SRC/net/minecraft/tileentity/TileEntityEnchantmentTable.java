package net.minecraft.tileentity;

import java.util.*;
import net.minecraft.nbt.*;
import net.minecraft.entity.player.*;

public class TileEntityEnchantmentTable extends TileEntity
{
    public int field_145926_a;
    public float field_145933_i;
    public float field_145931_j;
    public float field_145932_k;
    public float field_145929_l;
    public float field_145930_m;
    public float field_145927_n;
    public float field_145928_o;
    public float field_145925_p;
    public float field_145924_q;
    private static Random field_145923_r;
    private String field_145922_s;
    private static final String __OBFID = "CL_00000354";
    
    @Override
    public void writeToNBT(final NBTTagCompound p_145841_1_) {
        super.writeToNBT(p_145841_1_);
        if (this.func_145921_b()) {
            p_145841_1_.setString("CustomName", this.field_145922_s);
        }
    }
    
    @Override
    public void readFromNBT(final NBTTagCompound p_145839_1_) {
        super.readFromNBT(p_145839_1_);
        if (p_145839_1_.func_150297_b("CustomName", 8)) {
            this.field_145922_s = p_145839_1_.getString("CustomName");
        }
    }
    
    @Override
    public void updateEntity() {
        super.updateEntity();
        this.field_145927_n = this.field_145930_m;
        this.field_145925_p = this.field_145928_o;
        final EntityPlayer var1 = this.worldObj.getClosestPlayer(this.field_145851_c + 0.5f, this.field_145848_d + 0.5f, this.field_145849_e + 0.5f, 3.0);
        if (var1 != null) {
            final double var2 = var1.posX - (this.field_145851_c + 0.5f);
            final double var3 = var1.posZ - (this.field_145849_e + 0.5f);
            this.field_145924_q = (float)Math.atan2(var3, var2);
            this.field_145930_m += 0.1f;
            if (this.field_145930_m < 0.5f || TileEntityEnchantmentTable.field_145923_r.nextInt(40) == 0) {
                final float var4 = this.field_145932_k;
                do {
                    this.field_145932_k += TileEntityEnchantmentTable.field_145923_r.nextInt(4) - TileEntityEnchantmentTable.field_145923_r.nextInt(4);
                } while (var4 == this.field_145932_k);
            }
        }
        else {
            this.field_145924_q += 0.02f;
            this.field_145930_m -= 0.1f;
        }
        while (this.field_145928_o >= 3.1415927f) {
            this.field_145928_o -= 6.2831855f;
        }
        while (this.field_145928_o < -3.1415927f) {
            this.field_145928_o += 6.2831855f;
        }
        while (this.field_145924_q >= 3.1415927f) {
            this.field_145924_q -= 6.2831855f;
        }
        while (this.field_145924_q < -3.1415927f) {
            this.field_145924_q += 6.2831855f;
        }
        float var5;
        for (var5 = this.field_145924_q - this.field_145928_o; var5 >= 3.1415927f; var5 -= 6.2831855f) {}
        while (var5 < -3.1415927f) {
            var5 += 6.2831855f;
        }
        this.field_145928_o += var5 * 0.4f;
        if (this.field_145930_m < 0.0f) {
            this.field_145930_m = 0.0f;
        }
        if (this.field_145930_m > 1.0f) {
            this.field_145930_m = 1.0f;
        }
        ++this.field_145926_a;
        this.field_145931_j = this.field_145933_i;
        float var6 = (this.field_145932_k - this.field_145933_i) * 0.4f;
        final float var7 = 0.2f;
        if (var6 < -var7) {
            var6 = -var7;
        }
        if (var6 > var7) {
            var6 = var7;
        }
        this.field_145929_l += (var6 - this.field_145929_l) * 0.9f;
        this.field_145933_i += this.field_145929_l;
    }
    
    public String func_145919_a() {
        return this.func_145921_b() ? this.field_145922_s : "container.enchant";
    }
    
    public boolean func_145921_b() {
        return this.field_145922_s != null && this.field_145922_s.length() > 0;
    }
    
    public void func_145920_a(final String p_145920_1_) {
        this.field_145922_s = p_145920_1_;
    }
    
    static {
        TileEntityEnchantmentTable.field_145923_r = new Random();
    }
}
