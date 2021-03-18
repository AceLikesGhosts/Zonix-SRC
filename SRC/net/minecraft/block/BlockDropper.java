package net.minecraft.block;

import net.minecraft.client.renderer.texture.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.tileentity.*;
import net.minecraft.dispenser.*;
import net.minecraft.inventory.*;

public class BlockDropper extends BlockDispenser
{
    private final IBehaviorDispenseItem field_149947_P;
    private static final String __OBFID = "CL_00000233";
    
    public BlockDropper() {
        this.field_149947_P = new BehaviorDefaultDispenseItem();
    }
    
    @Override
    public void registerBlockIcons(final IIconRegister p_149651_1_) {
        this.blockIcon = p_149651_1_.registerIcon("furnace_side");
        this.field_149944_M = p_149651_1_.registerIcon("furnace_top");
        this.field_149945_N = p_149651_1_.registerIcon(this.getTextureName() + "_front_horizontal");
        this.field_149946_O = p_149651_1_.registerIcon(this.getTextureName() + "_front_vertical");
    }
    
    @Override
    protected IBehaviorDispenseItem func_149940_a(final ItemStack p_149940_1_) {
        return this.field_149947_P;
    }
    
    @Override
    public TileEntity createNewTileEntity(final World p_149915_1_, final int p_149915_2_) {
        return new TileEntityDropper();
    }
    
    @Override
    protected void func_149941_e(final World p_149941_1_, final int p_149941_2_, final int p_149941_3_, final int p_149941_4_) {
        final BlockSourceImpl var5 = new BlockSourceImpl(p_149941_1_, p_149941_2_, p_149941_3_, p_149941_4_);
        final TileEntityDispenser var6 = (TileEntityDispenser)var5.getBlockTileEntity();
        if (var6 != null) {
            final int var7 = var6.func_146017_i();
            if (var7 < 0) {
                p_149941_1_.playAuxSFX(1001, p_149941_2_, p_149941_3_, p_149941_4_, 0);
            }
            else {
                final ItemStack var8 = var6.getStackInSlot(var7);
                final int var9 = p_149941_1_.getBlockMetadata(p_149941_2_, p_149941_3_, p_149941_4_) & 0x7;
                final IInventory var10 = TileEntityHopper.func_145893_b(p_149941_1_, p_149941_2_ + Facing.offsetsXForSide[var9], p_149941_3_ + Facing.offsetsYForSide[var9], p_149941_4_ + Facing.offsetsZForSide[var9]);
                ItemStack var11;
                if (var10 != null) {
                    var11 = TileEntityHopper.func_145889_a(var10, var8.copy().splitStack(1), Facing.oppositeSide[var9]);
                    if (var11 == null) {
                        final ItemStack copy;
                        var11 = (copy = var8.copy());
                        if (--copy.stackSize == 0) {
                            var11 = null;
                        }
                    }
                    else {
                        var11 = var8.copy();
                    }
                }
                else {
                    var11 = this.field_149947_P.dispense(var5, var8);
                    if (var11 != null && var11.stackSize == 0) {
                        var11 = null;
                    }
                }
                var6.setInventorySlotContents(var7, var11);
            }
        }
    }
}
