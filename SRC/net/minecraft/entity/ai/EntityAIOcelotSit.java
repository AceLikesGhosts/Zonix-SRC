package net.minecraft.entity.ai;

import net.minecraft.entity.passive.*;
import net.minecraft.world.*;
import net.minecraft.init.*;
import net.minecraft.tileentity.*;
import net.minecraft.block.*;

public class EntityAIOcelotSit extends EntityAIBase
{
    private final EntityOcelot field_151493_a;
    private final double field_151491_b;
    private int field_151492_c;
    private int field_151489_d;
    private int field_151490_e;
    private int field_151487_f;
    private int field_151488_g;
    private int field_151494_h;
    private static final String __OBFID = "CL_00001601";
    
    public EntityAIOcelotSit(final EntityOcelot p_i45315_1_, final double p_i45315_2_) {
        this.field_151493_a = p_i45315_1_;
        this.field_151491_b = p_i45315_2_;
        this.setMutexBits(5);
    }
    
    @Override
    public boolean shouldExecute() {
        return this.field_151493_a.isTamed() && !this.field_151493_a.isSitting() && this.field_151493_a.getRNG().nextDouble() <= 0.006500000134110451 && this.func_151485_f();
    }
    
    @Override
    public boolean continueExecuting() {
        return this.field_151492_c <= this.field_151490_e && this.field_151489_d <= 60 && this.func_151486_a(this.field_151493_a.worldObj, this.field_151487_f, this.field_151488_g, this.field_151494_h);
    }
    
    @Override
    public void startExecuting() {
        this.field_151493_a.getNavigator().tryMoveToXYZ((float)this.field_151487_f + 0.5, this.field_151488_g + 1, (float)this.field_151494_h + 0.5, this.field_151491_b);
        this.field_151492_c = 0;
        this.field_151489_d = 0;
        this.field_151490_e = this.field_151493_a.getRNG().nextInt(this.field_151493_a.getRNG().nextInt(1200) + 1200) + 1200;
        this.field_151493_a.func_70907_r().setSitting(false);
    }
    
    @Override
    public void resetTask() {
        this.field_151493_a.setSitting(false);
    }
    
    @Override
    public void updateTask() {
        ++this.field_151492_c;
        this.field_151493_a.func_70907_r().setSitting(false);
        if (this.field_151493_a.getDistanceSq(this.field_151487_f, this.field_151488_g + 1, this.field_151494_h) > 1.0) {
            this.field_151493_a.setSitting(false);
            this.field_151493_a.getNavigator().tryMoveToXYZ((float)this.field_151487_f + 0.5, this.field_151488_g + 1, (float)this.field_151494_h + 0.5, this.field_151491_b);
            ++this.field_151489_d;
        }
        else if (!this.field_151493_a.isSitting()) {
            this.field_151493_a.setSitting(true);
        }
        else {
            --this.field_151489_d;
        }
    }
    
    private boolean func_151485_f() {
        final int var1 = (int)this.field_151493_a.posY;
        double var2 = 2.147483647E9;
        for (int var3 = (int)this.field_151493_a.posX - 8; var3 < this.field_151493_a.posX + 8.0; ++var3) {
            for (int var4 = (int)this.field_151493_a.posZ - 8; var4 < this.field_151493_a.posZ + 8.0; ++var4) {
                if (this.func_151486_a(this.field_151493_a.worldObj, var3, var1, var4) && this.field_151493_a.worldObj.isAirBlock(var3, var1 + 1, var4)) {
                    final double var5 = this.field_151493_a.getDistanceSq(var3, var1, var4);
                    if (var5 < var2) {
                        this.field_151487_f = var3;
                        this.field_151488_g = var1;
                        this.field_151494_h = var4;
                        var2 = var5;
                    }
                }
            }
        }
        return var2 < 2.147483647E9;
    }
    
    private boolean func_151486_a(final World p_151486_1_, final int p_151486_2_, final int p_151486_3_, final int p_151486_4_) {
        final Block var5 = p_151486_1_.getBlock(p_151486_2_, p_151486_3_, p_151486_4_);
        final int var6 = p_151486_1_.getBlockMetadata(p_151486_2_, p_151486_3_, p_151486_4_);
        if (var5 == Blocks.chest) {
            final TileEntityChest var7 = (TileEntityChest)p_151486_1_.getTileEntity(p_151486_2_, p_151486_3_, p_151486_4_);
            if (var7.field_145987_o < 1) {
                return true;
            }
        }
        else {
            if (var5 == Blocks.lit_furnace) {
                return true;
            }
            if (var5 == Blocks.bed && !BlockBed.func_149975_b(var6)) {
                return true;
            }
        }
        return false;
    }
}
