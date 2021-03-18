package net.minecraft.world;

import net.minecraft.block.material.*;
import net.minecraft.util.*;
import net.minecraft.enchantment.*;
import net.minecraft.entity.player.*;
import net.minecraft.block.*;
import net.minecraft.init.*;
import java.util.*;
import net.minecraft.entity.*;
import net.minecraft.entity.item.*;

public class Explosion
{
    public boolean isFlaming;
    public boolean isSmoking;
    private int field_77289_h;
    private Random explosionRNG;
    private World worldObj;
    public double explosionX;
    public double explosionY;
    public double explosionZ;
    public Entity exploder;
    public float explosionSize;
    public List affectedBlockPositions;
    private Map field_77288_k;
    private static final String __OBFID = "CL_00000134";
    
    public Explosion(final World p_i1948_1_, final Entity p_i1948_2_, final double p_i1948_3_, final double p_i1948_5_, final double p_i1948_7_, final float p_i1948_9_) {
        this.isSmoking = true;
        this.field_77289_h = 16;
        this.explosionRNG = new Random();
        this.affectedBlockPositions = new ArrayList();
        this.field_77288_k = new HashMap();
        this.worldObj = p_i1948_1_;
        this.exploder = p_i1948_2_;
        this.explosionSize = p_i1948_9_;
        this.explosionX = p_i1948_3_;
        this.explosionY = p_i1948_5_;
        this.explosionZ = p_i1948_7_;
    }
    
    public void doExplosionA() {
        final float var1 = this.explosionSize;
        final HashSet var2 = new HashSet();
        for (int var3 = 0; var3 < this.field_77289_h; ++var3) {
            for (int var4 = 0; var4 < this.field_77289_h; ++var4) {
                for (int var5 = 0; var5 < this.field_77289_h; ++var5) {
                    if (var3 == 0 || var3 == this.field_77289_h - 1 || var4 == 0 || var4 == this.field_77289_h - 1 || var5 == 0 || var5 == this.field_77289_h - 1) {
                        double var6 = var3 / (this.field_77289_h - 1.0f) * 2.0f - 1.0f;
                        double var7 = var4 / (this.field_77289_h - 1.0f) * 2.0f - 1.0f;
                        double var8 = var5 / (this.field_77289_h - 1.0f) * 2.0f - 1.0f;
                        final double var9 = Math.sqrt(var6 * var6 + var7 * var7 + var8 * var8);
                        var6 /= var9;
                        var7 /= var9;
                        var8 /= var9;
                        float var10 = this.explosionSize * (0.7f + this.worldObj.rand.nextFloat() * 0.6f);
                        double var11 = this.explosionX;
                        double var12 = this.explosionY;
                        double var13 = this.explosionZ;
                        for (float var14 = 0.3f; var10 > 0.0f; var10 -= var14 * 0.75f) {
                            final int var15 = MathHelper.floor_double(var11);
                            final int var16 = MathHelper.floor_double(var12);
                            final int var17 = MathHelper.floor_double(var13);
                            final Block var18 = this.worldObj.getBlock(var15, var16, var17);
                            if (var18.getMaterial() != Material.air) {
                                final float var19 = (this.exploder != null) ? this.exploder.func_145772_a(this, this.worldObj, var15, var16, var17, var18) : var18.getExplosionResistance(this.exploder);
                                var10 -= (var19 + 0.3f) * var14;
                            }
                            if (var10 > 0.0f && (this.exploder == null || this.exploder.func_145774_a(this, this.worldObj, var15, var16, var17, var18, var10))) {
                                var2.add(new ChunkPosition(var15, var16, var17));
                            }
                            var11 += var6 * var14;
                            var12 += var7 * var14;
                            var13 += var8 * var14;
                        }
                    }
                }
            }
        }
        this.affectedBlockPositions.addAll(var2);
        this.explosionSize *= 2.0f;
        int var3 = MathHelper.floor_double(this.explosionX - this.explosionSize - 1.0);
        int var4 = MathHelper.floor_double(this.explosionX + this.explosionSize + 1.0);
        int var5 = MathHelper.floor_double(this.explosionY - this.explosionSize - 1.0);
        final int var20 = MathHelper.floor_double(this.explosionY + this.explosionSize + 1.0);
        final int var21 = MathHelper.floor_double(this.explosionZ - this.explosionSize - 1.0);
        final int var22 = MathHelper.floor_double(this.explosionZ + this.explosionSize + 1.0);
        final List var23 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this.exploder, AxisAlignedBB.getBoundingBox(var3, var5, var21, var4, var20, var22));
        final Vec3 var24 = Vec3.createVectorHelper(this.explosionX, this.explosionY, this.explosionZ);
        for (int var25 = 0; var25 < var23.size(); ++var25) {
            final Entity var26 = var23.get(var25);
            final double var27 = var26.getDistance(this.explosionX, this.explosionY, this.explosionZ) / this.explosionSize;
            if (var27 <= 1.0) {
                double var11 = var26.posX - this.explosionX;
                double var12 = var26.posY + var26.getEyeHeight() - this.explosionY;
                double var13 = var26.posZ - this.explosionZ;
                final double var28 = MathHelper.sqrt_double(var11 * var11 + var12 * var12 + var13 * var13);
                if (var28 != 0.0) {
                    var11 /= var28;
                    var12 /= var28;
                    var13 /= var28;
                    final double var29 = this.worldObj.getBlockDensity(var24, var26.boundingBox);
                    final double var30 = (1.0 - var27) * var29;
                    var26.attackEntityFrom(DamageSource.setExplosionSource(this), (float)(int)((var30 * var30 + var30) / 2.0 * 8.0 * this.explosionSize + 1.0));
                    final double var31 = EnchantmentProtection.func_92092_a(var26, var30);
                    final Entity entity = var26;
                    entity.motionX += var11 * var31;
                    final Entity entity2 = var26;
                    entity2.motionY += var12 * var31;
                    final Entity entity3 = var26;
                    entity3.motionZ += var13 * var31;
                    if (var26 instanceof EntityPlayer) {
                        this.field_77288_k.put(var26, Vec3.createVectorHelper(var11 * var30, var12 * var30, var13 * var30));
                    }
                }
            }
        }
        this.explosionSize = var1;
    }
    
    public void doExplosionB(final boolean p_77279_1_) {
        this.worldObj.playSoundEffect(this.explosionX, this.explosionY, this.explosionZ, "random.explode", 4.0f, (1.0f + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.2f) * 0.7f);
        if (this.explosionSize >= 2.0f && this.isSmoking) {
            this.worldObj.spawnParticle("hugeexplosion", this.explosionX, this.explosionY, this.explosionZ, 1.0, 0.0, 0.0);
        }
        else {
            this.worldObj.spawnParticle("largeexplode", this.explosionX, this.explosionY, this.explosionZ, 1.0, 0.0, 0.0);
        }
        if (this.isSmoking) {
            for (final ChunkPosition var3 : this.affectedBlockPositions) {
                final int var4 = var3.field_151329_a;
                final int var5 = var3.field_151327_b;
                final int var6 = var3.field_151328_c;
                final Block var7 = this.worldObj.getBlock(var4, var5, var6);
                if (p_77279_1_) {
                    final double var8 = var4 + this.worldObj.rand.nextFloat();
                    final double var9 = var5 + this.worldObj.rand.nextFloat();
                    final double var10 = var6 + this.worldObj.rand.nextFloat();
                    double var11 = var8 - this.explosionX;
                    double var12 = var9 - this.explosionY;
                    double var13 = var10 - this.explosionZ;
                    final double var14 = MathHelper.sqrt_double(var11 * var11 + var12 * var12 + var13 * var13);
                    var11 /= var14;
                    var12 /= var14;
                    var13 /= var14;
                    double var15 = 0.5 / (var14 / this.explosionSize + 0.1);
                    var15 *= this.worldObj.rand.nextFloat() * this.worldObj.rand.nextFloat() + 0.3f;
                    var11 *= var15;
                    var12 *= var15;
                    var13 *= var15;
                    this.worldObj.spawnParticle("explode", (var8 + this.explosionX * 1.0) / 2.0, (var9 + this.explosionY * 1.0) / 2.0, (var10 + this.explosionZ * 1.0) / 2.0, var11, var12, var13);
                    this.worldObj.spawnParticle("smoke", var8, var9, var10, var11, var12, var13);
                }
                if (var7.getMaterial() != Material.air) {
                    if (var7.canDropFromExplosion(this)) {
                        var7.dropBlockAsItemWithChance(this.worldObj, var4, var5, var6, this.worldObj.getBlockMetadata(var4, var5, var6), 1.0f / this.explosionSize, 0);
                    }
                    this.worldObj.setBlock(var4, var5, var6, Blocks.air, 0, 3);
                    var7.onBlockDestroyedByExplosion(this.worldObj, var4, var5, var6, this);
                }
            }
        }
        if (this.isFlaming) {
            for (final ChunkPosition var3 : this.affectedBlockPositions) {
                final int var4 = var3.field_151329_a;
                final int var5 = var3.field_151327_b;
                final int var6 = var3.field_151328_c;
                final Block var7 = this.worldObj.getBlock(var4, var5, var6);
                final Block var16 = this.worldObj.getBlock(var4, var5 - 1, var6);
                if (var7.getMaterial() == Material.air && var16.func_149730_j() && this.explosionRNG.nextInt(3) == 0) {
                    this.worldObj.setBlock(var4, var5, var6, Blocks.fire);
                }
            }
        }
    }
    
    public Map func_77277_b() {
        return this.field_77288_k;
    }
    
    public EntityLivingBase getExplosivePlacedBy() {
        return (this.exploder == null) ? null : ((this.exploder instanceof EntityTNTPrimed) ? ((EntityTNTPrimed)this.exploder).getTntPlacedBy() : ((this.exploder instanceof EntityLivingBase) ? ((EntityLivingBase)this.exploder) : null));
    }
}
