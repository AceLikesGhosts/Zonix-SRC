package net.minecraft.item;

import net.minecraft.block.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;

public class ItemSlab extends ItemBlock
{
    private final boolean field_150948_b;
    private final BlockSlab field_150949_c;
    private final BlockSlab field_150947_d;
    private static final String __OBFID = "CL_00000071";
    
    public ItemSlab(final Block p_i45355_1_, final BlockSlab p_i45355_2_, final BlockSlab p_i45355_3_, final boolean p_i45355_4_) {
        super(p_i45355_1_);
        this.field_150949_c = p_i45355_2_;
        this.field_150947_d = p_i45355_3_;
        this.field_150948_b = p_i45355_4_;
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }
    
    @Override
    public IIcon getIconFromDamage(final int p_77617_1_) {
        return Block.getBlockFromItem(this).getIcon(2, p_77617_1_);
    }
    
    @Override
    public int getMetadata(final int p_77647_1_) {
        return p_77647_1_;
    }
    
    @Override
    public String getUnlocalizedName(final ItemStack p_77667_1_) {
        return this.field_150949_c.func_150002_b(p_77667_1_.getItemDamage());
    }
    
    @Override
    public boolean onItemUse(final ItemStack p_77648_1_, final EntityPlayer p_77648_2_, final World p_77648_3_, final int p_77648_4_, final int p_77648_5_, final int p_77648_6_, final int p_77648_7_, final float p_77648_8_, final float p_77648_9_, final float p_77648_10_) {
        if (this.field_150948_b) {
            return super.onItemUse(p_77648_1_, p_77648_2_, p_77648_3_, p_77648_4_, p_77648_5_, p_77648_6_, p_77648_7_, p_77648_8_, p_77648_9_, p_77648_10_);
        }
        if (p_77648_1_.stackSize == 0) {
            return false;
        }
        if (!p_77648_2_.canPlayerEdit(p_77648_4_, p_77648_5_, p_77648_6_, p_77648_7_, p_77648_1_)) {
            return false;
        }
        final Block var11 = p_77648_3_.getBlock(p_77648_4_, p_77648_5_, p_77648_6_);
        final int var12 = p_77648_3_.getBlockMetadata(p_77648_4_, p_77648_5_, p_77648_6_);
        final int var13 = var12 & 0x7;
        final boolean var14 = (var12 & 0x8) != 0x0;
        if (((p_77648_7_ == 1 && !var14) || (p_77648_7_ == 0 && var14)) && var11 == this.field_150949_c && var13 == p_77648_1_.getItemDamage()) {
            if (p_77648_3_.checkNoEntityCollision(this.field_150947_d.getCollisionBoundingBoxFromPool(p_77648_3_, p_77648_4_, p_77648_5_, p_77648_6_)) && p_77648_3_.setBlock(p_77648_4_, p_77648_5_, p_77648_6_, this.field_150947_d, var13, 3)) {
                p_77648_3_.playSoundEffect(p_77648_4_ + 0.5f, p_77648_5_ + 0.5f, p_77648_6_ + 0.5f, this.field_150947_d.stepSound.func_150496_b(), (this.field_150947_d.stepSound.func_150497_c() + 1.0f) / 2.0f, this.field_150947_d.stepSound.func_150494_d() * 0.8f);
                --p_77648_1_.stackSize;
            }
            return true;
        }
        return this.func_150946_a(p_77648_1_, p_77648_2_, p_77648_3_, p_77648_4_, p_77648_5_, p_77648_6_, p_77648_7_) || super.onItemUse(p_77648_1_, p_77648_2_, p_77648_3_, p_77648_4_, p_77648_5_, p_77648_6_, p_77648_7_, p_77648_8_, p_77648_9_, p_77648_10_);
    }
    
    @Override
    public boolean func_150936_a(final World p_150936_1_, int p_150936_2_, int p_150936_3_, int p_150936_4_, final int p_150936_5_, final EntityPlayer p_150936_6_, final ItemStack p_150936_7_) {
        final int var8 = p_150936_2_;
        final int var9 = p_150936_3_;
        final int var10 = p_150936_4_;
        final Block var11 = p_150936_1_.getBlock(p_150936_2_, p_150936_3_, p_150936_4_);
        final int var12 = p_150936_1_.getBlockMetadata(p_150936_2_, p_150936_3_, p_150936_4_);
        int var13 = var12 & 0x7;
        final boolean var14 = (var12 & 0x8) != 0x0;
        if (((p_150936_5_ == 1 && !var14) || (p_150936_5_ == 0 && var14)) && var11 == this.field_150949_c && var13 == p_150936_7_.getItemDamage()) {
            return true;
        }
        if (p_150936_5_ == 0) {
            --p_150936_3_;
        }
        if (p_150936_5_ == 1) {
            ++p_150936_3_;
        }
        if (p_150936_5_ == 2) {
            --p_150936_4_;
        }
        if (p_150936_5_ == 3) {
            ++p_150936_4_;
        }
        if (p_150936_5_ == 4) {
            --p_150936_2_;
        }
        if (p_150936_5_ == 5) {
            ++p_150936_2_;
        }
        final Block var15 = p_150936_1_.getBlock(p_150936_2_, p_150936_3_, p_150936_4_);
        final int var16 = p_150936_1_.getBlockMetadata(p_150936_2_, p_150936_3_, p_150936_4_);
        var13 = (var16 & 0x7);
        return (var15 == this.field_150949_c && var13 == p_150936_7_.getItemDamage()) || super.func_150936_a(p_150936_1_, var8, var9, var10, p_150936_5_, p_150936_6_, p_150936_7_);
    }
    
    private boolean func_150946_a(final ItemStack p_150946_1_, final EntityPlayer p_150946_2_, final World p_150946_3_, int p_150946_4_, int p_150946_5_, int p_150946_6_, final int p_150946_7_) {
        if (p_150946_7_ == 0) {
            --p_150946_5_;
        }
        if (p_150946_7_ == 1) {
            ++p_150946_5_;
        }
        if (p_150946_7_ == 2) {
            --p_150946_6_;
        }
        if (p_150946_7_ == 3) {
            ++p_150946_6_;
        }
        if (p_150946_7_ == 4) {
            --p_150946_4_;
        }
        if (p_150946_7_ == 5) {
            ++p_150946_4_;
        }
        final Block var8 = p_150946_3_.getBlock(p_150946_4_, p_150946_5_, p_150946_6_);
        final int var9 = p_150946_3_.getBlockMetadata(p_150946_4_, p_150946_5_, p_150946_6_);
        final int var10 = var9 & 0x7;
        if (var8 == this.field_150949_c && var10 == p_150946_1_.getItemDamage()) {
            if (p_150946_3_.checkNoEntityCollision(this.field_150947_d.getCollisionBoundingBoxFromPool(p_150946_3_, p_150946_4_, p_150946_5_, p_150946_6_)) && p_150946_3_.setBlock(p_150946_4_, p_150946_5_, p_150946_6_, this.field_150947_d, var10, 3)) {
                p_150946_3_.playSoundEffect(p_150946_4_ + 0.5f, p_150946_5_ + 0.5f, p_150946_6_ + 0.5f, this.field_150947_d.stepSound.func_150496_b(), (this.field_150947_d.stepSound.func_150497_c() + 1.0f) / 2.0f, this.field_150947_d.stepSound.func_150494_d() * 0.8f);
                --p_150946_1_.stackSize;
            }
            return true;
        }
        return false;
    }
}
