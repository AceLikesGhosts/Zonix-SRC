package net.minecraft.block;

import net.minecraft.util.*;
import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import java.util.*;
import net.minecraft.world.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.init.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.client.renderer.texture.*;

public class BlockTNT extends Block
{
    private IIcon field_150116_a;
    private IIcon field_150115_b;
    private static final String __OBFID = "CL_00000324";
    
    public BlockTNT() {
        super(Material.tnt);
        this.setCreativeTab(CreativeTabs.tabRedstone);
    }
    
    @Override
    public IIcon getIcon(final int p_149691_1_, final int p_149691_2_) {
        return (p_149691_1_ == 0) ? this.field_150115_b : ((p_149691_1_ == 1) ? this.field_150116_a : this.blockIcon);
    }
    
    @Override
    public void onBlockAdded(final World p_149726_1_, final int p_149726_2_, final int p_149726_3_, final int p_149726_4_) {
        super.onBlockAdded(p_149726_1_, p_149726_2_, p_149726_3_, p_149726_4_);
        if (p_149726_1_.isBlockIndirectlyGettingPowered(p_149726_2_, p_149726_3_, p_149726_4_)) {
            this.onBlockDestroyedByPlayer(p_149726_1_, p_149726_2_, p_149726_3_, p_149726_4_, 1);
            p_149726_1_.setBlockToAir(p_149726_2_, p_149726_3_, p_149726_4_);
        }
    }
    
    @Override
    public void onNeighborBlockChange(final World p_149695_1_, final int p_149695_2_, final int p_149695_3_, final int p_149695_4_, final Block p_149695_5_) {
        if (p_149695_1_.isBlockIndirectlyGettingPowered(p_149695_2_, p_149695_3_, p_149695_4_)) {
            this.onBlockDestroyedByPlayer(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_, 1);
            p_149695_1_.setBlockToAir(p_149695_2_, p_149695_3_, p_149695_4_);
        }
    }
    
    @Override
    public int quantityDropped(final Random p_149745_1_) {
        return 1;
    }
    
    @Override
    public void onBlockDestroyedByExplosion(final World p_149723_1_, final int p_149723_2_, final int p_149723_3_, final int p_149723_4_, final Explosion p_149723_5_) {
        if (!p_149723_1_.isClient) {
            final EntityTNTPrimed var6 = new EntityTNTPrimed(p_149723_1_, p_149723_2_ + 0.5f, p_149723_3_ + 0.5f, p_149723_4_ + 0.5f, p_149723_5_.getExplosivePlacedBy());
            var6.fuse = p_149723_1_.rand.nextInt(var6.fuse / 4) + var6.fuse / 8;
            p_149723_1_.spawnEntityInWorld(var6);
        }
    }
    
    @Override
    public void onBlockDestroyedByPlayer(final World p_149664_1_, final int p_149664_2_, final int p_149664_3_, final int p_149664_4_, final int p_149664_5_) {
        this.func_150114_a(p_149664_1_, p_149664_2_, p_149664_3_, p_149664_4_, p_149664_5_, null);
    }
    
    public void func_150114_a(final World p_150114_1_, final int p_150114_2_, final int p_150114_3_, final int p_150114_4_, final int p_150114_5_, final EntityLivingBase p_150114_6_) {
        if (!p_150114_1_.isClient && (p_150114_5_ & 0x1) == 0x1) {
            final EntityTNTPrimed var7 = new EntityTNTPrimed(p_150114_1_, p_150114_2_ + 0.5f, p_150114_3_ + 0.5f, p_150114_4_ + 0.5f, p_150114_6_);
            p_150114_1_.spawnEntityInWorld(var7);
            p_150114_1_.playSoundAtEntity(var7, "game.tnt.primed", 1.0f, 1.0f);
        }
    }
    
    @Override
    public boolean onBlockActivated(final World p_149727_1_, final int p_149727_2_, final int p_149727_3_, final int p_149727_4_, final EntityPlayer p_149727_5_, final int p_149727_6_, final float p_149727_7_, final float p_149727_8_, final float p_149727_9_) {
        if (p_149727_5_.getCurrentEquippedItem() != null && p_149727_5_.getCurrentEquippedItem().getItem() == Items.flint_and_steel) {
            this.func_150114_a(p_149727_1_, p_149727_2_, p_149727_3_, p_149727_4_, 1, p_149727_5_);
            p_149727_1_.setBlockToAir(p_149727_2_, p_149727_3_, p_149727_4_);
            p_149727_5_.getCurrentEquippedItem().damageItem(1, p_149727_5_);
            return true;
        }
        return super.onBlockActivated(p_149727_1_, p_149727_2_, p_149727_3_, p_149727_4_, p_149727_5_, p_149727_6_, p_149727_7_, p_149727_8_, p_149727_9_);
    }
    
    @Override
    public void onEntityCollidedWithBlock(final World p_149670_1_, final int p_149670_2_, final int p_149670_3_, final int p_149670_4_, final Entity p_149670_5_) {
        if (p_149670_5_ instanceof EntityArrow && !p_149670_1_.isClient) {
            final EntityArrow var6 = (EntityArrow)p_149670_5_;
            if (var6.isBurning()) {
                this.func_150114_a(p_149670_1_, p_149670_2_, p_149670_3_, p_149670_4_, 1, (var6.shootingEntity instanceof EntityLivingBase) ? ((EntityLivingBase)var6.shootingEntity) : null);
                p_149670_1_.setBlockToAir(p_149670_2_, p_149670_3_, p_149670_4_);
            }
        }
    }
    
    @Override
    public boolean canDropFromExplosion(final Explosion p_149659_1_) {
        return false;
    }
    
    @Override
    public void registerBlockIcons(final IIconRegister p_149651_1_) {
        this.blockIcon = p_149651_1_.registerIcon(this.getTextureName() + "_side");
        this.field_150116_a = p_149651_1_.registerIcon(this.getTextureName() + "_top");
        this.field_150115_b = p_149651_1_.registerIcon(this.getTextureName() + "_bottom");
    }
}
