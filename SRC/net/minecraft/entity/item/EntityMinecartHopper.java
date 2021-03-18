package net.minecraft.entity.item;

import net.minecraft.world.*;
import net.minecraft.block.*;
import net.minecraft.init.*;
import net.minecraft.entity.player.*;
import net.minecraft.tileentity.*;
import net.minecraft.command.*;
import net.minecraft.inventory.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;

public class EntityMinecartHopper extends EntityMinecartContainer implements IHopper
{
    private boolean isBlocked;
    private int transferTicker;
    private static final String __OBFID = "CL_00001676";
    
    public EntityMinecartHopper(final World p_i1720_1_) {
        super(p_i1720_1_);
        this.isBlocked = true;
        this.transferTicker = -1;
    }
    
    public EntityMinecartHopper(final World p_i1721_1_, final double p_i1721_2_, final double p_i1721_4_, final double p_i1721_6_) {
        super(p_i1721_1_, p_i1721_2_, p_i1721_4_, p_i1721_6_);
        this.isBlocked = true;
        this.transferTicker = -1;
    }
    
    @Override
    public int getMinecartType() {
        return 5;
    }
    
    @Override
    public Block func_145817_o() {
        return Blocks.hopper;
    }
    
    @Override
    public int getDefaultDisplayTileOffset() {
        return 1;
    }
    
    @Override
    public int getSizeInventory() {
        return 5;
    }
    
    @Override
    public boolean interactFirst(final EntityPlayer p_130002_1_) {
        if (!this.worldObj.isClient) {
            p_130002_1_.displayGUIHopperMinecart(this);
        }
        return true;
    }
    
    @Override
    public void onActivatorRailPass(final int p_96095_1_, final int p_96095_2_, final int p_96095_3_, final boolean p_96095_4_) {
        final boolean var5 = !p_96095_4_;
        if (var5 != this.getBlocked()) {
            this.setBlocked(var5);
        }
    }
    
    public boolean getBlocked() {
        return this.isBlocked;
    }
    
    public void setBlocked(final boolean p_96110_1_) {
        this.isBlocked = p_96110_1_;
    }
    
    @Override
    public World getWorldObj() {
        return this.worldObj;
    }
    
    @Override
    public double getXPos() {
        return this.posX;
    }
    
    @Override
    public double getYPos() {
        return this.posY;
    }
    
    @Override
    public double getZPos() {
        return this.posZ;
    }
    
    @Override
    public void onUpdate() {
        super.onUpdate();
        if (!this.worldObj.isClient && this.isEntityAlive() && this.getBlocked()) {
            --this.transferTicker;
            if (!this.canTransfer()) {
                this.setTransferTicker(0);
                if (this.func_96112_aD()) {
                    this.setTransferTicker(4);
                    this.onInventoryChanged();
                }
            }
        }
    }
    
    public boolean func_96112_aD() {
        if (TileEntityHopper.func_145891_a(this)) {
            return true;
        }
        final List var1 = this.worldObj.selectEntitiesWithinAABB(EntityItem.class, this.boundingBox.expand(0.25, 0.0, 0.25), IEntitySelector.selectAnything);
        if (var1.size() > 0) {
            TileEntityHopper.func_145898_a(this, var1.get(0));
        }
        return false;
    }
    
    @Override
    public void killMinecart(final DamageSource p_94095_1_) {
        super.killMinecart(p_94095_1_);
        this.func_145778_a(Item.getItemFromBlock(Blocks.hopper), 1, 0.0f);
    }
    
    @Override
    protected void writeEntityToNBT(final NBTTagCompound p_70014_1_) {
        super.writeEntityToNBT(p_70014_1_);
        p_70014_1_.setInteger("TransferCooldown", this.transferTicker);
    }
    
    @Override
    protected void readEntityFromNBT(final NBTTagCompound p_70037_1_) {
        super.readEntityFromNBT(p_70037_1_);
        this.transferTicker = p_70037_1_.getInteger("TransferCooldown");
    }
    
    public void setTransferTicker(final int p_98042_1_) {
        this.transferTicker = p_98042_1_;
    }
    
    public boolean canTransfer() {
        return this.transferTicker > 0;
    }
}
