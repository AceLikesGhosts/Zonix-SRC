package net.minecraft.entity.passive;

import net.minecraft.world.*;
import net.minecraft.inventory.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.ai.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.entity.item.*;
import net.minecraft.nbt.*;
import net.minecraft.block.*;
import java.util.*;
import net.minecraft.entity.*;
import net.minecraft.item.crafting.*;

public class EntitySheep extends EntityAnimal
{
    private final InventoryCrafting field_90016_e;
    public static final float[][] fleeceColorTable;
    private int sheepTimer;
    private EntityAIEatGrass field_146087_bs;
    private static final String __OBFID = "CL_00001648";
    
    public EntitySheep(final World p_i1691_1_) {
        super(p_i1691_1_);
        this.field_90016_e = new InventoryCrafting(new Container() {
            private static final String __OBFID = "CL_00001649";
            
            @Override
            public boolean canInteractWith(final EntityPlayer p_75145_1_) {
                return false;
            }
        }, 2, 1);
        this.field_146087_bs = new EntityAIEatGrass(this);
        this.setSize(0.9f, 1.3f);
        this.getNavigator().setAvoidsWater(true);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIPanic(this, 1.25));
        this.tasks.addTask(2, new EntityAIMate(this, 1.0));
        this.tasks.addTask(3, new EntityAITempt(this, 1.1, Items.wheat, false));
        this.tasks.addTask(4, new EntityAIFollowParent(this, 1.1));
        this.tasks.addTask(5, this.field_146087_bs);
        this.tasks.addTask(6, new EntityAIWander(this, 1.0));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0f));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.field_90016_e.setInventorySlotContents(0, new ItemStack(Items.dye, 1, 0));
        this.field_90016_e.setInventorySlotContents(1, new ItemStack(Items.dye, 1, 0));
    }
    
    @Override
    protected boolean isAIEnabled() {
        return true;
    }
    
    @Override
    protected void updateAITasks() {
        this.sheepTimer = this.field_146087_bs.func_151499_f();
        super.updateAITasks();
    }
    
    @Override
    public void onLivingUpdate() {
        if (this.worldObj.isClient) {
            this.sheepTimer = Math.max(0, this.sheepTimer - 1);
        }
        super.onLivingUpdate();
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(8.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.23000000417232513);
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(16, new Byte((byte)0));
    }
    
    @Override
    protected void dropFewItems(final boolean p_70628_1_, final int p_70628_2_) {
        if (!this.getSheared()) {
            this.entityDropItem(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, this.getFleeceColor()), 0.0f);
        }
    }
    
    @Override
    protected Item func_146068_u() {
        return Item.getItemFromBlock(Blocks.wool);
    }
    
    @Override
    public void handleHealthUpdate(final byte p_70103_1_) {
        if (p_70103_1_ == 10) {
            this.sheepTimer = 40;
        }
        else {
            super.handleHealthUpdate(p_70103_1_);
        }
    }
    
    public float func_70894_j(final float p_70894_1_) {
        return (this.sheepTimer <= 0) ? 0.0f : ((this.sheepTimer >= 4 && this.sheepTimer <= 36) ? 1.0f : ((this.sheepTimer < 4) ? ((this.sheepTimer - p_70894_1_) / 4.0f) : (-(this.sheepTimer - 40 - p_70894_1_) / 4.0f)));
    }
    
    public float func_70890_k(final float p_70890_1_) {
        if (this.sheepTimer > 4 && this.sheepTimer <= 36) {
            final float var2 = (this.sheepTimer - 4 - p_70890_1_) / 32.0f;
            return 0.62831855f + 0.2199115f * MathHelper.sin(var2 * 28.7f);
        }
        return (this.sheepTimer > 0) ? 0.62831855f : (this.rotationPitch / 57.295776f);
    }
    
    @Override
    public boolean interact(final EntityPlayer p_70085_1_) {
        final ItemStack var2 = p_70085_1_.inventory.getCurrentItem();
        if (var2 != null && var2.getItem() == Items.shears && !this.getSheared() && !this.isChild()) {
            if (!this.worldObj.isClient) {
                this.setSheared(true);
                for (int var3 = 1 + this.rand.nextInt(3), var4 = 0; var4 < var3; ++var4) {
                    final EntityItem entityDropItem;
                    final EntityItem var5 = entityDropItem = this.entityDropItem(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, this.getFleeceColor()), 1.0f);
                    entityDropItem.motionY += this.rand.nextFloat() * 0.05f;
                    final EntityItem entityItem = var5;
                    entityItem.motionX += (this.rand.nextFloat() - this.rand.nextFloat()) * 0.1f;
                    final EntityItem entityItem2 = var5;
                    entityItem2.motionZ += (this.rand.nextFloat() - this.rand.nextFloat()) * 0.1f;
                }
            }
            var2.damageItem(1, p_70085_1_);
            this.playSound("mob.sheep.shear", 1.0f, 1.0f);
        }
        return super.interact(p_70085_1_);
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound p_70014_1_) {
        super.writeEntityToNBT(p_70014_1_);
        p_70014_1_.setBoolean("Sheared", this.getSheared());
        p_70014_1_.setByte("Color", (byte)this.getFleeceColor());
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound p_70037_1_) {
        super.readEntityFromNBT(p_70037_1_);
        this.setSheared(p_70037_1_.getBoolean("Sheared"));
        this.setFleeceColor(p_70037_1_.getByte("Color"));
    }
    
    @Override
    protected String getLivingSound() {
        return "mob.sheep.say";
    }
    
    @Override
    protected String getHurtSound() {
        return "mob.sheep.say";
    }
    
    @Override
    protected String getDeathSound() {
        return "mob.sheep.say";
    }
    
    @Override
    protected void func_145780_a(final int p_145780_1_, final int p_145780_2_, final int p_145780_3_, final Block p_145780_4_) {
        this.playSound("mob.sheep.step", 0.15f, 1.0f);
    }
    
    public int getFleeceColor() {
        return this.dataWatcher.getWatchableObjectByte(16) & 0xF;
    }
    
    public void setFleeceColor(final int p_70891_1_) {
        final byte var2 = this.dataWatcher.getWatchableObjectByte(16);
        this.dataWatcher.updateObject(16, (byte)((var2 & 0xF0) | (p_70891_1_ & 0xF)));
    }
    
    public boolean getSheared() {
        return (this.dataWatcher.getWatchableObjectByte(16) & 0x10) != 0x0;
    }
    
    public void setSheared(final boolean p_70893_1_) {
        final byte var2 = this.dataWatcher.getWatchableObjectByte(16);
        if (p_70893_1_) {
            this.dataWatcher.updateObject(16, (byte)(var2 | 0x10));
        }
        else {
            this.dataWatcher.updateObject(16, (byte)(var2 & 0xFFFFFFEF));
        }
    }
    
    public static int getRandomFleeceColor(final Random p_70895_0_) {
        final int var1 = p_70895_0_.nextInt(100);
        return (var1 < 5) ? 15 : ((var1 < 10) ? 7 : ((var1 < 15) ? 8 : ((var1 < 18) ? 12 : ((p_70895_0_.nextInt(500) == 0) ? 6 : 0))));
    }
    
    @Override
    public EntitySheep createChild(final EntityAgeable p_90011_1_) {
        final EntitySheep var2 = (EntitySheep)p_90011_1_;
        final EntitySheep var3 = new EntitySheep(this.worldObj);
        final int var4 = this.func_90014_a(this, var2);
        var3.setFleeceColor(15 - var4);
        return var3;
    }
    
    @Override
    public void eatGrassBonus() {
        this.setSheared(false);
        if (this.isChild()) {
            this.addGrowth(60);
        }
    }
    
    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData p_110161_1_) {
        p_110161_1_ = super.onSpawnWithEgg(p_110161_1_);
        this.setFleeceColor(getRandomFleeceColor(this.worldObj.rand));
        return p_110161_1_;
    }
    
    private int func_90014_a(final EntityAnimal p_90014_1_, final EntityAnimal p_90014_2_) {
        final int var3 = this.func_90013_b(p_90014_1_);
        final int var4 = this.func_90013_b(p_90014_2_);
        this.field_90016_e.getStackInSlot(0).setItemDamage(var3);
        this.field_90016_e.getStackInSlot(1).setItemDamage(var4);
        final ItemStack var5 = CraftingManager.getInstance().findMatchingRecipe(this.field_90016_e, ((EntitySheep)p_90014_1_).worldObj);
        int var6;
        if (var5 != null && var5.getItem() == Items.dye) {
            var6 = var5.getItemDamage();
        }
        else {
            var6 = (this.worldObj.rand.nextBoolean() ? var3 : var4);
        }
        return var6;
    }
    
    private int func_90013_b(final EntityAnimal p_90013_1_) {
        return 15 - ((EntitySheep)p_90013_1_).getFleeceColor();
    }
    
    static {
        fleeceColorTable = new float[][] { { 1.0f, 1.0f, 1.0f }, { 0.85f, 0.5f, 0.2f }, { 0.7f, 0.3f, 0.85f }, { 0.4f, 0.6f, 0.85f }, { 0.9f, 0.9f, 0.2f }, { 0.5f, 0.8f, 0.1f }, { 0.95f, 0.5f, 0.65f }, { 0.3f, 0.3f, 0.3f }, { 0.6f, 0.6f, 0.6f }, { 0.3f, 0.5f, 0.6f }, { 0.5f, 0.25f, 0.7f }, { 0.2f, 0.3f, 0.7f }, { 0.4f, 0.3f, 0.2f }, { 0.4f, 0.5f, 0.2f }, { 0.6f, 0.2f, 0.2f }, { 0.1f, 0.1f, 0.1f } };
    }
}
