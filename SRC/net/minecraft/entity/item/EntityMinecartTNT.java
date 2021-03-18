package net.minecraft.entity.item;

import net.minecraft.init.*;
import net.minecraft.util.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;
import net.minecraft.world.*;
import net.minecraft.block.*;
import net.minecraft.nbt.*;

public class EntityMinecartTNT extends EntityMinecart
{
    private int minecartTNTFuse;
    private static final String __OBFID = "CL_00001680";
    
    public EntityMinecartTNT(final World p_i1727_1_) {
        super(p_i1727_1_);
        this.minecartTNTFuse = -1;
    }
    
    public EntityMinecartTNT(final World p_i1728_1_, final double p_i1728_2_, final double p_i1728_4_, final double p_i1728_6_) {
        super(p_i1728_1_, p_i1728_2_, p_i1728_4_, p_i1728_6_);
        this.minecartTNTFuse = -1;
    }
    
    @Override
    public int getMinecartType() {
        return 3;
    }
    
    @Override
    public Block func_145817_o() {
        return Blocks.tnt;
    }
    
    @Override
    public void onUpdate() {
        super.onUpdate();
        if (this.minecartTNTFuse > 0) {
            --this.minecartTNTFuse;
            this.worldObj.spawnParticle("smoke", this.posX, this.posY + 0.5, this.posZ, 0.0, 0.0, 0.0);
        }
        else if (this.minecartTNTFuse == 0) {
            this.explodeCart(this.motionX * this.motionX + this.motionZ * this.motionZ);
        }
        if (this.isCollidedHorizontally) {
            final double var1 = this.motionX * this.motionX + this.motionZ * this.motionZ;
            if (var1 >= 0.009999999776482582) {
                this.explodeCart(var1);
            }
        }
    }
    
    @Override
    public void killMinecart(final DamageSource p_94095_1_) {
        super.killMinecart(p_94095_1_);
        final double var2 = this.motionX * this.motionX + this.motionZ * this.motionZ;
        if (!p_94095_1_.isExplosion()) {
            this.entityDropItem(new ItemStack(Blocks.tnt, 1), 0.0f);
        }
        if (p_94095_1_.isFireDamage() || p_94095_1_.isExplosion() || var2 >= 0.009999999776482582) {
            this.explodeCart(var2);
        }
    }
    
    protected void explodeCart(final double p_94103_1_) {
        if (!this.worldObj.isClient) {
            double var3 = Math.sqrt(p_94103_1_);
            if (var3 > 5.0) {
                var3 = 5.0;
            }
            this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, (float)(4.0 + this.rand.nextDouble() * 1.5 * var3), true);
            this.setDead();
        }
    }
    
    @Override
    protected void fall(final float p_70069_1_) {
        if (p_70069_1_ >= 3.0f) {
            final float var2 = p_70069_1_ / 10.0f;
            this.explodeCart(var2 * var2);
        }
        super.fall(p_70069_1_);
    }
    
    @Override
    public void onActivatorRailPass(final int p_96095_1_, final int p_96095_2_, final int p_96095_3_, final boolean p_96095_4_) {
        if (p_96095_4_ && this.minecartTNTFuse < 0) {
            this.ignite();
        }
    }
    
    @Override
    public void handleHealthUpdate(final byte p_70103_1_) {
        if (p_70103_1_ == 10) {
            this.ignite();
        }
        else {
            super.handleHealthUpdate(p_70103_1_);
        }
    }
    
    public void ignite() {
        this.minecartTNTFuse = 80;
        if (!this.worldObj.isClient) {
            this.worldObj.setEntityState(this, (byte)10);
            this.worldObj.playSoundAtEntity(this, "game.tnt.primed", 1.0f, 1.0f);
        }
    }
    
    public int func_94104_d() {
        return this.minecartTNTFuse;
    }
    
    public boolean isIgnited() {
        return this.minecartTNTFuse > -1;
    }
    
    @Override
    public float func_145772_a(final Explosion p_145772_1_, final World p_145772_2_, final int p_145772_3_, final int p_145772_4_, final int p_145772_5_, final Block p_145772_6_) {
        return (this.isIgnited() && (BlockRailBase.func_150051_a(p_145772_6_) || BlockRailBase.func_150049_b_(p_145772_2_, p_145772_3_, p_145772_4_ + 1, p_145772_5_))) ? 0.0f : super.func_145772_a(p_145772_1_, p_145772_2_, p_145772_3_, p_145772_4_, p_145772_5_, p_145772_6_);
    }
    
    @Override
    public boolean func_145774_a(final Explosion p_145774_1_, final World p_145774_2_, final int p_145774_3_, final int p_145774_4_, final int p_145774_5_, final Block p_145774_6_, final float p_145774_7_) {
        return (!this.isIgnited() || (!BlockRailBase.func_150051_a(p_145774_6_) && !BlockRailBase.func_150049_b_(p_145774_2_, p_145774_3_, p_145774_4_ + 1, p_145774_5_))) && super.func_145774_a(p_145774_1_, p_145774_2_, p_145774_3_, p_145774_4_, p_145774_5_, p_145774_6_, p_145774_7_);
    }
    
    @Override
    protected void readEntityFromNBT(final NBTTagCompound p_70037_1_) {
        super.readEntityFromNBT(p_70037_1_);
        if (p_70037_1_.func_150297_b("TNTFuse", 99)) {
            this.minecartTNTFuse = p_70037_1_.getInteger("TNTFuse");
        }
    }
    
    @Override
    protected void writeEntityToNBT(final NBTTagCompound p_70014_1_) {
        super.writeEntityToNBT(p_70014_1_);
        p_70014_1_.setInteger("TNTFuse", this.minecartTNTFuse);
    }
}
