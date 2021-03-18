package net.minecraft.entity.item;

import net.minecraft.entity.*;
import net.minecraft.world.*;
import net.minecraft.block.material.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.block.*;
import net.minecraft.tileentity.*;
import net.minecraft.nbt.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.crash.*;

public class EntityFallingBlock extends Entity
{
    private Block field_145811_e;
    public int field_145814_a;
    public int field_145812_b;
    public boolean field_145813_c;
    private boolean field_145808_f;
    private boolean field_145809_g;
    private int field_145815_h;
    private float field_145816_i;
    public NBTTagCompound field_145810_d;
    private static final String __OBFID = "CL_00001668";
    
    public EntityFallingBlock(final World p_i1706_1_) {
        super(p_i1706_1_);
        this.field_145813_c = true;
        this.field_145815_h = 40;
        this.field_145816_i = 2.0f;
    }
    
    public EntityFallingBlock(final World p_i45318_1_, final double p_i45318_2_, final double p_i45318_4_, final double p_i45318_6_, final Block p_i45318_8_) {
        this(p_i45318_1_, p_i45318_2_, p_i45318_4_, p_i45318_6_, p_i45318_8_, 0);
    }
    
    public EntityFallingBlock(final World p_i45319_1_, final double p_i45319_2_, final double p_i45319_4_, final double p_i45319_6_, final Block p_i45319_8_, final int p_i45319_9_) {
        super(p_i45319_1_);
        this.field_145813_c = true;
        this.field_145815_h = 40;
        this.field_145816_i = 2.0f;
        this.field_145811_e = p_i45319_8_;
        this.field_145814_a = p_i45319_9_;
        this.preventEntitySpawning = true;
        this.setSize(0.98f, 0.98f);
        this.yOffset = this.height / 2.0f;
        this.setPosition(p_i45319_2_, p_i45319_4_, p_i45319_6_);
        this.motionX = 0.0;
        this.motionY = 0.0;
        this.motionZ = 0.0;
        this.prevPosX = p_i45319_2_;
        this.prevPosY = p_i45319_4_;
        this.prevPosZ = p_i45319_6_;
    }
    
    @Override
    protected boolean canTriggerWalking() {
        return false;
    }
    
    @Override
    protected void entityInit() {
    }
    
    @Override
    public boolean canBeCollidedWith() {
        return !this.isDead;
    }
    
    @Override
    public void onUpdate() {
        if (this.field_145811_e.getMaterial() == Material.air) {
            this.setDead();
        }
        else {
            this.prevPosX = this.posX;
            this.prevPosY = this.posY;
            this.prevPosZ = this.posZ;
            ++this.field_145812_b;
            this.motionY -= 0.03999999910593033;
            this.moveEntity(this.motionX, this.motionY, this.motionZ);
            this.motionX *= 0.9800000190734863;
            this.motionY *= 0.9800000190734863;
            this.motionZ *= 0.9800000190734863;
            if (!this.worldObj.isClient) {
                final int var1 = MathHelper.floor_double(this.posX);
                final int var2 = MathHelper.floor_double(this.posY);
                final int var3 = MathHelper.floor_double(this.posZ);
                if (this.field_145812_b == 1) {
                    if (this.worldObj.getBlock(var1, var2, var3) != this.field_145811_e) {
                        this.setDead();
                        return;
                    }
                    this.worldObj.setBlockToAir(var1, var2, var3);
                }
                if (this.onGround) {
                    this.motionX *= 0.699999988079071;
                    this.motionZ *= 0.699999988079071;
                    this.motionY *= -0.5;
                    if (this.worldObj.getBlock(var1, var2, var3) != Blocks.piston_extension) {
                        this.setDead();
                        if (!this.field_145808_f && this.worldObj.canPlaceEntityOnSide(this.field_145811_e, var1, var2, var3, true, 1, null, null) && !BlockFalling.func_149831_e(this.worldObj, var1, var2 - 1, var3) && this.worldObj.setBlock(var1, var2, var3, this.field_145811_e, this.field_145814_a, 3)) {
                            if (this.field_145811_e instanceof BlockFalling) {
                                ((BlockFalling)this.field_145811_e).func_149828_a(this.worldObj, var1, var2, var3, this.field_145814_a);
                            }
                            if (this.field_145810_d != null && this.field_145811_e instanceof ITileEntityProvider) {
                                final TileEntity var4 = this.worldObj.getTileEntity(var1, var2, var3);
                                if (var4 != null) {
                                    final NBTTagCompound var5 = new NBTTagCompound();
                                    var4.writeToNBT(var5);
                                    for (final String var7 : this.field_145810_d.func_150296_c()) {
                                        final NBTBase var8 = this.field_145810_d.getTag(var7);
                                        if (!var7.equals("x") && !var7.equals("y") && !var7.equals("z")) {
                                            var5.setTag(var7, var8.copy());
                                        }
                                    }
                                    var4.readFromNBT(var5);
                                    var4.onInventoryChanged();
                                }
                            }
                        }
                        else if (this.field_145813_c && !this.field_145808_f) {
                            this.entityDropItem(new ItemStack(this.field_145811_e, 1, this.field_145811_e.damageDropped(this.field_145814_a)), 0.0f);
                        }
                    }
                }
                else if ((this.field_145812_b > 100 && !this.worldObj.isClient && (var2 < 1 || var2 > 256)) || this.field_145812_b > 600) {
                    if (this.field_145813_c) {
                        this.entityDropItem(new ItemStack(this.field_145811_e, 1, this.field_145811_e.damageDropped(this.field_145814_a)), 0.0f);
                    }
                    this.setDead();
                }
            }
        }
    }
    
    @Override
    protected void fall(final float p_70069_1_) {
        if (this.field_145809_g) {
            final int var2 = MathHelper.ceiling_float_int(p_70069_1_ - 1.0f);
            if (var2 > 0) {
                final ArrayList var3 = new ArrayList(this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox));
                final boolean var4 = this.field_145811_e == Blocks.anvil;
                final DamageSource var5 = var4 ? DamageSource.anvil : DamageSource.fallingBlock;
                for (final Entity var7 : var3) {
                    var7.attackEntityFrom(var5, (float)Math.min(MathHelper.floor_float(var2 * this.field_145816_i), this.field_145815_h));
                }
                if (var4 && this.rand.nextFloat() < 0.05000000074505806 + var2 * 0.05) {
                    int var8 = this.field_145814_a >> 2;
                    final int var9 = this.field_145814_a & 0x3;
                    if (++var8 > 2) {
                        this.field_145808_f = true;
                    }
                    else {
                        this.field_145814_a = (var9 | var8 << 2);
                    }
                }
            }
        }
    }
    
    @Override
    protected void writeEntityToNBT(final NBTTagCompound p_70014_1_) {
        p_70014_1_.setByte("Tile", (byte)Block.getIdFromBlock(this.field_145811_e));
        p_70014_1_.setInteger("TileID", Block.getIdFromBlock(this.field_145811_e));
        p_70014_1_.setByte("Data", (byte)this.field_145814_a);
        p_70014_1_.setByte("Time", (byte)this.field_145812_b);
        p_70014_1_.setBoolean("DropItem", this.field_145813_c);
        p_70014_1_.setBoolean("HurtEntities", this.field_145809_g);
        p_70014_1_.setFloat("FallHurtAmount", this.field_145816_i);
        p_70014_1_.setInteger("FallHurtMax", this.field_145815_h);
        if (this.field_145810_d != null) {
            p_70014_1_.setTag("TileEntityData", this.field_145810_d);
        }
    }
    
    @Override
    protected void readEntityFromNBT(final NBTTagCompound p_70037_1_) {
        if (p_70037_1_.func_150297_b("TileID", 99)) {
            this.field_145811_e = Block.getBlockById(p_70037_1_.getInteger("TileID"));
        }
        else {
            this.field_145811_e = Block.getBlockById(p_70037_1_.getByte("Tile") & 0xFF);
        }
        this.field_145814_a = (p_70037_1_.getByte("Data") & 0xFF);
        this.field_145812_b = (p_70037_1_.getByte("Time") & 0xFF);
        if (p_70037_1_.func_150297_b("HurtEntities", 99)) {
            this.field_145809_g = p_70037_1_.getBoolean("HurtEntities");
            this.field_145816_i = p_70037_1_.getFloat("FallHurtAmount");
            this.field_145815_h = p_70037_1_.getInteger("FallHurtMax");
        }
        else if (this.field_145811_e == Blocks.anvil) {
            this.field_145809_g = true;
        }
        if (p_70037_1_.func_150297_b("DropItem", 99)) {
            this.field_145813_c = p_70037_1_.getBoolean("DropItem");
        }
        if (p_70037_1_.func_150297_b("TileEntityData", 10)) {
            this.field_145810_d = p_70037_1_.getCompoundTag("TileEntityData");
        }
        if (this.field_145811_e.getMaterial() == Material.air) {
            this.field_145811_e = Blocks.sand;
        }
    }
    
    @Override
    public float getShadowSize() {
        return 0.0f;
    }
    
    public World func_145807_e() {
        return this.worldObj;
    }
    
    public void func_145806_a(final boolean p_145806_1_) {
        this.field_145809_g = p_145806_1_;
    }
    
    @Override
    public boolean canRenderOnFire() {
        return false;
    }
    
    @Override
    public void addEntityCrashInfo(final CrashReportCategory p_85029_1_) {
        super.addEntityCrashInfo(p_85029_1_);
        p_85029_1_.addCrashSection("Immitating block ID", Block.getIdFromBlock(this.field_145811_e));
        p_85029_1_.addCrashSection("Immitating block data", this.field_145814_a);
    }
    
    public Block func_145805_f() {
        return this.field_145811_e;
    }
}
