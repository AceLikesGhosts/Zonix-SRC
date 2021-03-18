package net.minecraft.block;

import net.minecraft.dispenser.*;
import net.minecraft.world.*;
import net.minecraft.tileentity.*;

public class BlockSourceImpl implements IBlockSource
{
    private final World worldObj;
    private final int xPos;
    private final int yPos;
    private final int zPos;
    private static final String __OBFID = "CL_00001194";
    
    public BlockSourceImpl(final World p_i1365_1_, final int p_i1365_2_, final int p_i1365_3_, final int p_i1365_4_) {
        this.worldObj = p_i1365_1_;
        this.xPos = p_i1365_2_;
        this.yPos = p_i1365_3_;
        this.zPos = p_i1365_4_;
    }
    
    @Override
    public World getWorld() {
        return this.worldObj;
    }
    
    @Override
    public double getX() {
        return this.xPos + 0.5;
    }
    
    @Override
    public double getY() {
        return this.yPos + 0.5;
    }
    
    @Override
    public double getZ() {
        return this.zPos + 0.5;
    }
    
    @Override
    public int getXInt() {
        return this.xPos;
    }
    
    @Override
    public int getYInt() {
        return this.yPos;
    }
    
    @Override
    public int getZInt() {
        return this.zPos;
    }
    
    @Override
    public int getBlockMetadata() {
        return this.worldObj.getBlockMetadata(this.xPos, this.yPos, this.zPos);
    }
    
    @Override
    public TileEntity getBlockTileEntity() {
        return this.worldObj.getTileEntity(this.xPos, this.yPos, this.zPos);
    }
}
