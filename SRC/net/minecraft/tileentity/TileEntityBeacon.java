package net.minecraft.tileentity;

import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.potion.*;
import java.util.*;
import net.minecraft.stats.*;
import net.minecraft.block.*;
import net.minecraft.network.*;
import net.minecraft.nbt.*;
import net.minecraft.network.play.server.*;
import net.minecraft.init.*;

public class TileEntityBeacon extends TileEntity implements IInventory
{
    public static final Potion[][] field_146009_a;
    private long field_146016_i;
    private float field_146014_j;
    private boolean field_146015_k;
    private int field_146012_l;
    private int field_146013_m;
    private int field_146010_n;
    private ItemStack field_146011_o;
    private String field_146008_p;
    private static final String __OBFID = "CL_00000339";
    
    public TileEntityBeacon() {
        this.field_146012_l = -1;
    }
    
    @Override
    public void updateEntity() {
        if (this.worldObj.getTotalWorldTime() % 80L == 0L) {
            this.func_146003_y();
            this.func_146000_x();
        }
    }
    
    private void func_146000_x() {
        if (this.field_146015_k && this.field_146012_l > 0 && !this.worldObj.isClient && this.field_146013_m > 0) {
            final double var1 = this.field_146012_l * 10 + 10;
            byte var2 = 0;
            if (this.field_146012_l >= 4 && this.field_146013_m == this.field_146010_n) {
                var2 = 1;
            }
            final AxisAlignedBB var3 = AxisAlignedBB.getBoundingBox(this.field_145851_c, this.field_145848_d, this.field_145849_e, this.field_145851_c + 1, this.field_145848_d + 1, this.field_145849_e + 1).expand(var1, var1, var1);
            var3.maxY = this.worldObj.getHeight();
            final List var4 = this.worldObj.getEntitiesWithinAABB(EntityPlayer.class, var3);
            for (final EntityPlayer var6 : var4) {
                var6.addPotionEffect(new PotionEffect(this.field_146013_m, 180, var2, true));
            }
            if (this.field_146012_l >= 4 && this.field_146013_m != this.field_146010_n && this.field_146010_n > 0) {
                for (final EntityPlayer var6 : var4) {
                    var6.addPotionEffect(new PotionEffect(this.field_146010_n, 180, 0, true));
                }
            }
        }
    }
    
    private void func_146003_y() {
        final int var1 = this.field_146012_l;
        if (!this.worldObj.canBlockSeeTheSky(this.field_145851_c, this.field_145848_d + 1, this.field_145849_e)) {
            this.field_146015_k = false;
            this.field_146012_l = 0;
        }
        else {
            this.field_146015_k = true;
            this.field_146012_l = 0;
            for (int var2 = 1; var2 <= 4; this.field_146012_l = var2++) {
                final int var3 = this.field_145848_d - var2;
                if (var3 < 0) {
                    break;
                }
                boolean var4 = true;
                for (int var5 = this.field_145851_c - var2; var5 <= this.field_145851_c + var2 && var4; ++var5) {
                    for (int var6 = this.field_145849_e - var2; var6 <= this.field_145849_e + var2; ++var6) {
                        final Block var7 = this.worldObj.getBlock(var5, var3, var6);
                        if (var7 != Blocks.emerald_block && var7 != Blocks.gold_block && var7 != Blocks.diamond_block && var7 != Blocks.iron_block) {
                            var4 = false;
                            break;
                        }
                    }
                }
                if (!var4) {
                    break;
                }
            }
            if (this.field_146012_l == 0) {
                this.field_146015_k = false;
            }
        }
        if (!this.worldObj.isClient && this.field_146012_l == 4 && var1 < this.field_146012_l) {
            for (final EntityPlayer var9 : this.worldObj.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getBoundingBox(this.field_145851_c, this.field_145848_d, this.field_145849_e, this.field_145851_c, this.field_145848_d - 4, this.field_145849_e).expand(10.0, 5.0, 10.0))) {
                var9.triggerAchievement(AchievementList.field_150965_K);
            }
        }
    }
    
    public float func_146002_i() {
        if (!this.field_146015_k) {
            return 0.0f;
        }
        final int var1 = (int)(this.worldObj.getTotalWorldTime() - this.field_146016_i);
        this.field_146016_i = this.worldObj.getTotalWorldTime();
        if (var1 > 1) {
            this.field_146014_j -= var1 / 40.0f;
            if (this.field_146014_j < 0.0f) {
                this.field_146014_j = 0.0f;
            }
        }
        this.field_146014_j += 0.025f;
        if (this.field_146014_j > 1.0f) {
            this.field_146014_j = 1.0f;
        }
        return this.field_146014_j;
    }
    
    public int func_146007_j() {
        return this.field_146013_m;
    }
    
    public int func_146006_k() {
        return this.field_146010_n;
    }
    
    public int func_145998_l() {
        return this.field_146012_l;
    }
    
    public void func_146005_c(final int p_146005_1_) {
        this.field_146012_l = p_146005_1_;
    }
    
    public void func_146001_d(final int p_146001_1_) {
        this.field_146013_m = 0;
        for (int var2 = 0; var2 < this.field_146012_l && var2 < 3; ++var2) {
            for (final Potion var6 : TileEntityBeacon.field_146009_a[var2]) {
                if (var6.id == p_146001_1_) {
                    this.field_146013_m = p_146001_1_;
                    return;
                }
            }
        }
    }
    
    public void func_146004_e(final int p_146004_1_) {
        this.field_146010_n = 0;
        if (this.field_146012_l >= 4) {
            for (int var2 = 0; var2 < 4; ++var2) {
                for (final Potion var6 : TileEntityBeacon.field_146009_a[var2]) {
                    if (var6.id == p_146004_1_) {
                        this.field_146010_n = p_146004_1_;
                        return;
                    }
                }
            }
        }
    }
    
    @Override
    public Packet getDescriptionPacket() {
        final NBTTagCompound var1 = new NBTTagCompound();
        this.writeToNBT(var1);
        return new S35PacketUpdateTileEntity(this.field_145851_c, this.field_145848_d, this.field_145849_e, 3, var1);
    }
    
    @Override
    public double getMaxRenderDistanceSquared() {
        return 65536.0;
    }
    
    @Override
    public void readFromNBT(final NBTTagCompound p_145839_1_) {
        super.readFromNBT(p_145839_1_);
        this.field_146013_m = p_145839_1_.getInteger("Primary");
        this.field_146010_n = p_145839_1_.getInteger("Secondary");
        this.field_146012_l = p_145839_1_.getInteger("Levels");
    }
    
    @Override
    public void writeToNBT(final NBTTagCompound p_145841_1_) {
        super.writeToNBT(p_145841_1_);
        p_145841_1_.setInteger("Primary", this.field_146013_m);
        p_145841_1_.setInteger("Secondary", this.field_146010_n);
        p_145841_1_.setInteger("Levels", this.field_146012_l);
    }
    
    @Override
    public int getSizeInventory() {
        return 1;
    }
    
    @Override
    public ItemStack getStackInSlot(final int p_70301_1_) {
        return (p_70301_1_ == 0) ? this.field_146011_o : null;
    }
    
    @Override
    public ItemStack decrStackSize(final int p_70298_1_, final int p_70298_2_) {
        if (p_70298_1_ != 0 || this.field_146011_o == null) {
            return null;
        }
        if (p_70298_2_ >= this.field_146011_o.stackSize) {
            final ItemStack var3 = this.field_146011_o;
            this.field_146011_o = null;
            return var3;
        }
        final ItemStack field_146011_o = this.field_146011_o;
        field_146011_o.stackSize -= p_70298_2_;
        return new ItemStack(this.field_146011_o.getItem(), p_70298_2_, this.field_146011_o.getItemDamage());
    }
    
    @Override
    public ItemStack getStackInSlotOnClosing(final int p_70304_1_) {
        if (p_70304_1_ == 0 && this.field_146011_o != null) {
            final ItemStack var2 = this.field_146011_o;
            this.field_146011_o = null;
            return var2;
        }
        return null;
    }
    
    @Override
    public void setInventorySlotContents(final int p_70299_1_, final ItemStack p_70299_2_) {
        if (p_70299_1_ == 0) {
            this.field_146011_o = p_70299_2_;
        }
    }
    
    @Override
    public String getInventoryName() {
        return this.isInventoryNameLocalized() ? this.field_146008_p : "container.beacon";
    }
    
    @Override
    public boolean isInventoryNameLocalized() {
        return this.field_146008_p != null && this.field_146008_p.length() > 0;
    }
    
    public void func_145999_a(final String p_145999_1_) {
        this.field_146008_p = p_145999_1_;
    }
    
    @Override
    public int getInventoryStackLimit() {
        return 1;
    }
    
    @Override
    public boolean isUseableByPlayer(final EntityPlayer p_70300_1_) {
        return this.worldObj.getTileEntity(this.field_145851_c, this.field_145848_d, this.field_145849_e) == this && p_70300_1_.getDistanceSq(this.field_145851_c + 0.5, this.field_145848_d + 0.5, this.field_145849_e + 0.5) <= 64.0;
    }
    
    @Override
    public void openInventory() {
    }
    
    @Override
    public void closeInventory() {
    }
    
    @Override
    public boolean isItemValidForSlot(final int p_94041_1_, final ItemStack p_94041_2_) {
        return p_94041_2_.getItem() == Items.emerald || p_94041_2_.getItem() == Items.diamond || p_94041_2_.getItem() == Items.gold_ingot || p_94041_2_.getItem() == Items.iron_ingot;
    }
    
    static {
        field_146009_a = new Potion[][] { { Potion.moveSpeed, Potion.digSpeed }, { Potion.resistance, Potion.jump }, { Potion.damageBoost }, { Potion.regeneration } };
    }
}
