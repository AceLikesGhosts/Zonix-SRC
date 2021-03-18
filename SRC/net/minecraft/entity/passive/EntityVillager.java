package net.minecraft.entity.passive;

import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.entity.ai.*;
import net.minecraft.village.*;
import net.minecraft.potion.*;
import net.minecraft.nbt.*;
import net.minecraft.entity.monster.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.enchantment.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import java.util.*;

public class EntityVillager extends EntityAgeable implements IMerchant, INpc
{
    private int randomTickDivider;
    private boolean isMating;
    private boolean isPlaying;
    Village villageObj;
    private EntityPlayer buyingPlayer;
    private MerchantRecipeList buyingList;
    private int timeUntilReset;
    private boolean needsInitilization;
    private int wealth;
    private String lastBuyingPlayer;
    private boolean isLookingForHome;
    private float field_82191_bN;
    private static final Map villagersSellingList;
    private static final Map blacksmithSellingList;
    private static final String __OBFID = "CL_00001707";
    
    public EntityVillager(final World p_i1747_1_) {
        this(p_i1747_1_, 0);
    }
    
    public EntityVillager(final World p_i1748_1_, final int p_i1748_2_) {
        super(p_i1748_1_);
        this.setProfession(p_i1748_2_);
        this.setSize(0.6f, 1.8f);
        this.getNavigator().setBreakDoors(true);
        this.getNavigator().setAvoidsWater(true);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIAvoidEntity(this, EntityZombie.class, 8.0f, 0.6, 0.6));
        this.tasks.addTask(1, new EntityAITradePlayer(this));
        this.tasks.addTask(1, new EntityAILookAtTradePlayer(this));
        this.tasks.addTask(2, new EntityAIMoveIndoors(this));
        this.tasks.addTask(3, new EntityAIRestrictOpenDoor(this));
        this.tasks.addTask(4, new EntityAIOpenDoor(this, true));
        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 0.6));
        this.tasks.addTask(6, new EntityAIVillagerMate(this));
        this.tasks.addTask(7, new EntityAIFollowGolem(this));
        this.tasks.addTask(8, new EntityAIPlay(this, 0.32));
        this.tasks.addTask(9, new EntityAIWatchClosest2(this, EntityPlayer.class, 3.0f, 1.0f));
        this.tasks.addTask(9, new EntityAIWatchClosest2(this, EntityVillager.class, 5.0f, 0.02f));
        this.tasks.addTask(9, new EntityAIWander(this, 0.6));
        this.tasks.addTask(10, new EntityAIWatchClosest(this, EntityLiving.class, 8.0f));
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.5);
    }
    
    public boolean isAIEnabled() {
        return true;
    }
    
    @Override
    protected void updateAITick() {
        final int randomTickDivider = this.randomTickDivider - 1;
        this.randomTickDivider = randomTickDivider;
        if (randomTickDivider <= 0) {
            this.worldObj.villageCollectionObj.addVillagerPosition(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ));
            this.randomTickDivider = 70 + this.rand.nextInt(50);
            this.villageObj = this.worldObj.villageCollectionObj.findNearestVillage(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ), 32);
            if (this.villageObj == null) {
                this.detachHome();
            }
            else {
                final ChunkCoordinates var1 = this.villageObj.getCenter();
                this.setHomeArea(var1.posX, var1.posY, var1.posZ, (int)(this.villageObj.getVillageRadius() * 0.6f));
                if (this.isLookingForHome) {
                    this.isLookingForHome = false;
                    this.villageObj.setDefaultPlayerReputation(5);
                }
            }
        }
        if (!this.isTrading() && this.timeUntilReset > 0) {
            --this.timeUntilReset;
            if (this.timeUntilReset <= 0) {
                if (this.needsInitilization) {
                    if (this.buyingList.size() > 1) {
                        for (final MerchantRecipe var3 : this.buyingList) {
                            if (var3.isRecipeDisabled()) {
                                var3.func_82783_a(this.rand.nextInt(6) + this.rand.nextInt(6) + 2);
                            }
                        }
                    }
                    this.addDefaultEquipmentAndRecipies(1);
                    this.needsInitilization = false;
                    if (this.villageObj != null && this.lastBuyingPlayer != null) {
                        this.worldObj.setEntityState(this, (byte)14);
                        this.villageObj.setReputationForPlayer(this.lastBuyingPlayer, 1);
                    }
                }
                this.addPotionEffect(new PotionEffect(Potion.regeneration.id, 200, 0));
            }
        }
        super.updateAITick();
    }
    
    @Override
    public boolean interact(final EntityPlayer p_70085_1_) {
        final ItemStack var2 = p_70085_1_.inventory.getCurrentItem();
        final boolean var3 = var2 != null && var2.getItem() == Items.spawn_egg;
        if (!var3 && this.isEntityAlive() && !this.isTrading() && !this.isChild()) {
            if (!this.worldObj.isClient) {
                this.setCustomer(p_70085_1_);
                p_70085_1_.displayGUIMerchant(this, this.getCustomNameTag());
            }
            return true;
        }
        return super.interact(p_70085_1_);
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(16, 0);
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound p_70014_1_) {
        super.writeEntityToNBT(p_70014_1_);
        p_70014_1_.setInteger("Profession", this.getProfession());
        p_70014_1_.setInteger("Riches", this.wealth);
        if (this.buyingList != null) {
            p_70014_1_.setTag("Offers", this.buyingList.getRecipiesAsTags());
        }
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound p_70037_1_) {
        super.readEntityFromNBT(p_70037_1_);
        this.setProfession(p_70037_1_.getInteger("Profession"));
        this.wealth = p_70037_1_.getInteger("Riches");
        if (p_70037_1_.func_150297_b("Offers", 10)) {
            final NBTTagCompound var2 = p_70037_1_.getCompoundTag("Offers");
            this.buyingList = new MerchantRecipeList(var2);
        }
    }
    
    @Override
    protected boolean canDespawn() {
        return false;
    }
    
    @Override
    protected String getLivingSound() {
        return this.isTrading() ? "mob.villager.haggle" : "mob.villager.idle";
    }
    
    @Override
    protected String getHurtSound() {
        return "mob.villager.hit";
    }
    
    @Override
    protected String getDeathSound() {
        return "mob.villager.death";
    }
    
    public void setProfession(final int p_70938_1_) {
        this.dataWatcher.updateObject(16, p_70938_1_);
    }
    
    public int getProfession() {
        return this.dataWatcher.getWatchableObjectInt(16);
    }
    
    public boolean isMating() {
        return this.isMating;
    }
    
    public void setMating(final boolean p_70947_1_) {
        this.isMating = p_70947_1_;
    }
    
    public void setPlaying(final boolean p_70939_1_) {
        this.isPlaying = p_70939_1_;
    }
    
    public boolean isPlaying() {
        return this.isPlaying;
    }
    
    @Override
    public void setRevengeTarget(final EntityLivingBase p_70604_1_) {
        super.setRevengeTarget(p_70604_1_);
        if (this.villageObj != null && p_70604_1_ != null) {
            this.villageObj.addOrRenewAgressor(p_70604_1_);
            if (p_70604_1_ instanceof EntityPlayer) {
                byte var2 = -1;
                if (this.isChild()) {
                    var2 = -3;
                }
                this.villageObj.setReputationForPlayer(p_70604_1_.getCommandSenderName(), var2);
                if (this.isEntityAlive()) {
                    this.worldObj.setEntityState(this, (byte)13);
                }
            }
        }
    }
    
    @Override
    public void onDeath(final DamageSource p_70645_1_) {
        if (this.villageObj != null) {
            final Entity var2 = p_70645_1_.getEntity();
            if (var2 != null) {
                if (var2 instanceof EntityPlayer) {
                    this.villageObj.setReputationForPlayer(var2.getCommandSenderName(), -2);
                }
                else if (var2 instanceof IMob) {
                    this.villageObj.endMatingSeason();
                }
            }
            else if (var2 == null) {
                final EntityPlayer var3 = this.worldObj.getClosestPlayerToEntity(this, 16.0);
                if (var3 != null) {
                    this.villageObj.endMatingSeason();
                }
            }
        }
        super.onDeath(p_70645_1_);
    }
    
    @Override
    public void setCustomer(final EntityPlayer p_70932_1_) {
        this.buyingPlayer = p_70932_1_;
    }
    
    @Override
    public EntityPlayer getCustomer() {
        return this.buyingPlayer;
    }
    
    public boolean isTrading() {
        return this.buyingPlayer != null;
    }
    
    @Override
    public void useRecipe(final MerchantRecipe p_70933_1_) {
        p_70933_1_.incrementToolUses();
        this.livingSoundTime = -this.getTalkInterval();
        this.playSound("mob.villager.yes", this.getSoundVolume(), this.getSoundPitch());
        if (p_70933_1_.hasSameIDsAs(this.buyingList.get(this.buyingList.size() - 1))) {
            this.timeUntilReset = 40;
            this.needsInitilization = true;
            if (this.buyingPlayer != null) {
                this.lastBuyingPlayer = this.buyingPlayer.getCommandSenderName();
            }
            else {
                this.lastBuyingPlayer = null;
            }
        }
        if (p_70933_1_.getItemToBuy().getItem() == Items.emerald) {
            this.wealth += p_70933_1_.getItemToBuy().stackSize;
        }
    }
    
    @Override
    public void func_110297_a_(final ItemStack p_110297_1_) {
        if (!this.worldObj.isClient && this.livingSoundTime > -this.getTalkInterval() + 20) {
            this.livingSoundTime = -this.getTalkInterval();
            if (p_110297_1_ != null) {
                this.playSound("mob.villager.yes", this.getSoundVolume(), this.getSoundPitch());
            }
            else {
                this.playSound("mob.villager.no", this.getSoundVolume(), this.getSoundPitch());
            }
        }
    }
    
    @Override
    public MerchantRecipeList getRecipes(final EntityPlayer p_70934_1_) {
        if (this.buyingList == null) {
            this.addDefaultEquipmentAndRecipies(1);
        }
        return this.buyingList;
    }
    
    private float adjustProbability(final float p_82188_1_) {
        final float var2 = p_82188_1_ + this.field_82191_bN;
        return (var2 > 0.9f) ? (0.9f - (var2 - 0.9f)) : var2;
    }
    
    private void addDefaultEquipmentAndRecipies(final int p_70950_1_) {
        if (this.buyingList != null) {
            this.field_82191_bN = MathHelper.sqrt_float((float)this.buyingList.size()) * 0.2f;
        }
        else {
            this.field_82191_bN = 0.0f;
        }
        final MerchantRecipeList var2 = new MerchantRecipeList();
        switch (this.getProfession()) {
            case 0: {
                func_146091_a(var2, Items.wheat, this.rand, this.adjustProbability(0.9f));
                func_146091_a(var2, Item.getItemFromBlock(Blocks.wool), this.rand, this.adjustProbability(0.5f));
                func_146091_a(var2, Items.chicken, this.rand, this.adjustProbability(0.5f));
                func_146091_a(var2, Items.cooked_fished, this.rand, this.adjustProbability(0.4f));
                func_146089_b(var2, Items.bread, this.rand, this.adjustProbability(0.9f));
                func_146089_b(var2, Items.melon, this.rand, this.adjustProbability(0.3f));
                func_146089_b(var2, Items.apple, this.rand, this.adjustProbability(0.3f));
                func_146089_b(var2, Items.cookie, this.rand, this.adjustProbability(0.3f));
                func_146089_b(var2, Items.shears, this.rand, this.adjustProbability(0.3f));
                func_146089_b(var2, Items.flint_and_steel, this.rand, this.adjustProbability(0.3f));
                func_146089_b(var2, Items.cooked_chicken, this.rand, this.adjustProbability(0.3f));
                func_146089_b(var2, Items.arrow, this.rand, this.adjustProbability(0.5f));
                if (this.rand.nextFloat() < this.adjustProbability(0.5f)) {
                    var2.add(new MerchantRecipe(new ItemStack(Blocks.gravel, 10), new ItemStack(Items.emerald), new ItemStack(Items.flint, 4 + this.rand.nextInt(2), 0)));
                    break;
                }
                break;
            }
            case 1: {
                func_146091_a(var2, Items.paper, this.rand, this.adjustProbability(0.8f));
                func_146091_a(var2, Items.book, this.rand, this.adjustProbability(0.8f));
                func_146091_a(var2, Items.written_book, this.rand, this.adjustProbability(0.3f));
                func_146089_b(var2, Item.getItemFromBlock(Blocks.bookshelf), this.rand, this.adjustProbability(0.8f));
                func_146089_b(var2, Item.getItemFromBlock(Blocks.glass), this.rand, this.adjustProbability(0.2f));
                func_146089_b(var2, Items.compass, this.rand, this.adjustProbability(0.2f));
                func_146089_b(var2, Items.clock, this.rand, this.adjustProbability(0.2f));
                if (this.rand.nextFloat() < this.adjustProbability(0.07f)) {
                    final Enchantment var3 = Enchantment.enchantmentsBookList[this.rand.nextInt(Enchantment.enchantmentsBookList.length)];
                    final int var4 = MathHelper.getRandomIntegerInRange(this.rand, var3.getMinLevel(), var3.getMaxLevel());
                    final ItemStack var5 = Items.enchanted_book.getEnchantedItemStack(new EnchantmentData(var3, var4));
                    final int var6 = 2 + this.rand.nextInt(5 + var4 * 10) + 3 * var4;
                    var2.add(new MerchantRecipe(new ItemStack(Items.book), new ItemStack(Items.emerald, var6), var5));
                    break;
                }
                break;
            }
            case 2: {
                func_146089_b(var2, Items.ender_eye, this.rand, this.adjustProbability(0.3f));
                func_146089_b(var2, Items.experience_bottle, this.rand, this.adjustProbability(0.2f));
                func_146089_b(var2, Items.redstone, this.rand, this.adjustProbability(0.4f));
                func_146089_b(var2, Item.getItemFromBlock(Blocks.glowstone), this.rand, this.adjustProbability(0.3f));
                final Item[] var8;
                final Item[] var7 = var8 = new Item[] { Items.iron_sword, Items.diamond_sword, Items.iron_chestplate, Items.diamond_chestplate, Items.iron_axe, Items.diamond_axe, Items.iron_pickaxe, Items.diamond_pickaxe };
                for (int var9 = var7.length, var6 = 0; var6 < var9; ++var6) {
                    final Item var10 = var8[var6];
                    if (this.rand.nextFloat() < this.adjustProbability(0.05f)) {
                        var2.add(new MerchantRecipe(new ItemStack(var10, 1, 0), new ItemStack(Items.emerald, 2 + this.rand.nextInt(3), 0), EnchantmentHelper.addRandomEnchantment(this.rand, new ItemStack(var10, 1, 0), 5 + this.rand.nextInt(15))));
                    }
                }
                break;
            }
            case 3: {
                func_146091_a(var2, Items.coal, this.rand, this.adjustProbability(0.7f));
                func_146091_a(var2, Items.iron_ingot, this.rand, this.adjustProbability(0.5f));
                func_146091_a(var2, Items.gold_ingot, this.rand, this.adjustProbability(0.5f));
                func_146091_a(var2, Items.diamond, this.rand, this.adjustProbability(0.5f));
                func_146089_b(var2, Items.iron_sword, this.rand, this.adjustProbability(0.5f));
                func_146089_b(var2, Items.diamond_sword, this.rand, this.adjustProbability(0.5f));
                func_146089_b(var2, Items.iron_axe, this.rand, this.adjustProbability(0.3f));
                func_146089_b(var2, Items.diamond_axe, this.rand, this.adjustProbability(0.3f));
                func_146089_b(var2, Items.iron_pickaxe, this.rand, this.adjustProbability(0.5f));
                func_146089_b(var2, Items.diamond_pickaxe, this.rand, this.adjustProbability(0.5f));
                func_146089_b(var2, Items.iron_shovel, this.rand, this.adjustProbability(0.2f));
                func_146089_b(var2, Items.diamond_shovel, this.rand, this.adjustProbability(0.2f));
                func_146089_b(var2, Items.iron_hoe, this.rand, this.adjustProbability(0.2f));
                func_146089_b(var2, Items.diamond_hoe, this.rand, this.adjustProbability(0.2f));
                func_146089_b(var2, Items.iron_boots, this.rand, this.adjustProbability(0.2f));
                func_146089_b(var2, Items.diamond_boots, this.rand, this.adjustProbability(0.2f));
                func_146089_b(var2, Items.iron_helmet, this.rand, this.adjustProbability(0.2f));
                func_146089_b(var2, Items.diamond_helmet, this.rand, this.adjustProbability(0.2f));
                func_146089_b(var2, Items.iron_chestplate, this.rand, this.adjustProbability(0.2f));
                func_146089_b(var2, Items.diamond_chestplate, this.rand, this.adjustProbability(0.2f));
                func_146089_b(var2, Items.iron_leggings, this.rand, this.adjustProbability(0.2f));
                func_146089_b(var2, Items.diamond_leggings, this.rand, this.adjustProbability(0.2f));
                func_146089_b(var2, Items.chainmail_boots, this.rand, this.adjustProbability(0.1f));
                func_146089_b(var2, Items.chainmail_helmet, this.rand, this.adjustProbability(0.1f));
                func_146089_b(var2, Items.chainmail_chestplate, this.rand, this.adjustProbability(0.1f));
                func_146089_b(var2, Items.chainmail_leggings, this.rand, this.adjustProbability(0.1f));
                break;
            }
            case 4: {
                func_146091_a(var2, Items.coal, this.rand, this.adjustProbability(0.7f));
                func_146091_a(var2, Items.porkchop, this.rand, this.adjustProbability(0.5f));
                func_146091_a(var2, Items.beef, this.rand, this.adjustProbability(0.5f));
                func_146089_b(var2, Items.saddle, this.rand, this.adjustProbability(0.1f));
                func_146089_b(var2, Items.leather_chestplate, this.rand, this.adjustProbability(0.3f));
                func_146089_b(var2, Items.leather_boots, this.rand, this.adjustProbability(0.3f));
                func_146089_b(var2, Items.leather_helmet, this.rand, this.adjustProbability(0.3f));
                func_146089_b(var2, Items.leather_leggings, this.rand, this.adjustProbability(0.3f));
                func_146089_b(var2, Items.cooked_porkchop, this.rand, this.adjustProbability(0.3f));
                func_146089_b(var2, Items.cooked_beef, this.rand, this.adjustProbability(0.3f));
                break;
            }
        }
        if (var2.isEmpty()) {
            func_146091_a(var2, Items.gold_ingot, this.rand, 1.0f);
        }
        Collections.shuffle(var2);
        if (this.buyingList == null) {
            this.buyingList = new MerchantRecipeList();
        }
        for (int var11 = 0; var11 < p_70950_1_ && var11 < var2.size(); ++var11) {
            this.buyingList.addToListWithCheck(var2.get(var11));
        }
    }
    
    @Override
    public void setRecipes(final MerchantRecipeList p_70930_1_) {
    }
    
    private static void func_146091_a(final MerchantRecipeList p_146091_0_, final Item p_146091_1_, final Random p_146091_2_, final float p_146091_3_) {
        if (p_146091_2_.nextFloat() < p_146091_3_) {
            p_146091_0_.add(new MerchantRecipe(func_146088_a(p_146091_1_, p_146091_2_), Items.emerald));
        }
    }
    
    private static ItemStack func_146088_a(final Item p_146088_0_, final Random p_146088_1_) {
        return new ItemStack(p_146088_0_, func_146092_b(p_146088_0_, p_146088_1_), 0);
    }
    
    private static int func_146092_b(final Item p_146092_0_, final Random p_146092_1_) {
        final Tuple var2 = EntityVillager.villagersSellingList.get(p_146092_0_);
        return (int)((var2 == null) ? 1 : (((int)var2.getFirst() >= (int)var2.getSecond()) ? var2.getFirst() : ((int)var2.getFirst() + p_146092_1_.nextInt((int)var2.getSecond() - (int)var2.getFirst()))));
    }
    
    private static void func_146089_b(final MerchantRecipeList p_146089_0_, final Item p_146089_1_, final Random p_146089_2_, final float p_146089_3_) {
        if (p_146089_2_.nextFloat() < p_146089_3_) {
            final int var4 = func_146090_c(p_146089_1_, p_146089_2_);
            ItemStack var5;
            ItemStack var6;
            if (var4 < 0) {
                var5 = new ItemStack(Items.emerald, 1, 0);
                var6 = new ItemStack(p_146089_1_, -var4, 0);
            }
            else {
                var5 = new ItemStack(Items.emerald, var4, 0);
                var6 = new ItemStack(p_146089_1_, 1, 0);
            }
            p_146089_0_.add(new MerchantRecipe(var5, var6));
        }
    }
    
    private static int func_146090_c(final Item p_146090_0_, final Random p_146090_1_) {
        final Tuple var2 = EntityVillager.blacksmithSellingList.get(p_146090_0_);
        return (int)((var2 == null) ? 1 : (((int)var2.getFirst() >= (int)var2.getSecond()) ? var2.getFirst() : ((int)var2.getFirst() + p_146090_1_.nextInt((int)var2.getSecond() - (int)var2.getFirst()))));
    }
    
    @Override
    public void handleHealthUpdate(final byte p_70103_1_) {
        if (p_70103_1_ == 12) {
            this.generateRandomParticles("heart");
        }
        else if (p_70103_1_ == 13) {
            this.generateRandomParticles("angryVillager");
        }
        else if (p_70103_1_ == 14) {
            this.generateRandomParticles("happyVillager");
        }
        else {
            super.handleHealthUpdate(p_70103_1_);
        }
    }
    
    private void generateRandomParticles(final String p_70942_1_) {
        for (int var2 = 0; var2 < 5; ++var2) {
            final double var3 = this.rand.nextGaussian() * 0.02;
            final double var4 = this.rand.nextGaussian() * 0.02;
            final double var5 = this.rand.nextGaussian() * 0.02;
            this.worldObj.spawnParticle(p_70942_1_, this.posX + this.rand.nextFloat() * this.width * 2.0f - this.width, this.posY + 1.0 + this.rand.nextFloat() * this.height, this.posZ + this.rand.nextFloat() * this.width * 2.0f - this.width, var3, var4, var5);
        }
    }
    
    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData p_110161_1_) {
        p_110161_1_ = super.onSpawnWithEgg(p_110161_1_);
        this.setProfession(this.worldObj.rand.nextInt(5));
        return p_110161_1_;
    }
    
    public void setLookingForHome() {
        this.isLookingForHome = true;
    }
    
    @Override
    public EntityVillager createChild(final EntityAgeable p_90011_1_) {
        final EntityVillager var2 = new EntityVillager(this.worldObj);
        var2.onSpawnWithEgg(null);
        return var2;
    }
    
    @Override
    public boolean allowLeashing() {
        return false;
    }
    
    static {
        villagersSellingList = new HashMap();
        blacksmithSellingList = new HashMap();
        EntityVillager.villagersSellingList.put(Items.coal, new Tuple(16, 24));
        EntityVillager.villagersSellingList.put(Items.iron_ingot, new Tuple(8, 10));
        EntityVillager.villagersSellingList.put(Items.gold_ingot, new Tuple(8, 10));
        EntityVillager.villagersSellingList.put(Items.diamond, new Tuple(4, 6));
        EntityVillager.villagersSellingList.put(Items.paper, new Tuple(24, 36));
        EntityVillager.villagersSellingList.put(Items.book, new Tuple(11, 13));
        EntityVillager.villagersSellingList.put(Items.written_book, new Tuple(1, 1));
        EntityVillager.villagersSellingList.put(Items.ender_pearl, new Tuple(3, 4));
        EntityVillager.villagersSellingList.put(Items.ender_eye, new Tuple(2, 3));
        EntityVillager.villagersSellingList.put(Items.porkchop, new Tuple(14, 18));
        EntityVillager.villagersSellingList.put(Items.beef, new Tuple(14, 18));
        EntityVillager.villagersSellingList.put(Items.chicken, new Tuple(14, 18));
        EntityVillager.villagersSellingList.put(Items.cooked_fished, new Tuple(9, 13));
        EntityVillager.villagersSellingList.put(Items.wheat_seeds, new Tuple(34, 48));
        EntityVillager.villagersSellingList.put(Items.melon_seeds, new Tuple(30, 38));
        EntityVillager.villagersSellingList.put(Items.pumpkin_seeds, new Tuple(30, 38));
        EntityVillager.villagersSellingList.put(Items.wheat, new Tuple(18, 22));
        EntityVillager.villagersSellingList.put(Item.getItemFromBlock(Blocks.wool), new Tuple(14, 22));
        EntityVillager.villagersSellingList.put(Items.rotten_flesh, new Tuple(36, 64));
        EntityVillager.blacksmithSellingList.put(Items.flint_and_steel, new Tuple(3, 4));
        EntityVillager.blacksmithSellingList.put(Items.shears, new Tuple(3, 4));
        EntityVillager.blacksmithSellingList.put(Items.iron_sword, new Tuple(7, 11));
        EntityVillager.blacksmithSellingList.put(Items.diamond_sword, new Tuple(12, 14));
        EntityVillager.blacksmithSellingList.put(Items.iron_axe, new Tuple(6, 8));
        EntityVillager.blacksmithSellingList.put(Items.diamond_axe, new Tuple(9, 12));
        EntityVillager.blacksmithSellingList.put(Items.iron_pickaxe, new Tuple(7, 9));
        EntityVillager.blacksmithSellingList.put(Items.diamond_pickaxe, new Tuple(10, 12));
        EntityVillager.blacksmithSellingList.put(Items.iron_shovel, new Tuple(4, 6));
        EntityVillager.blacksmithSellingList.put(Items.diamond_shovel, new Tuple(7, 8));
        EntityVillager.blacksmithSellingList.put(Items.iron_hoe, new Tuple(4, 6));
        EntityVillager.blacksmithSellingList.put(Items.diamond_hoe, new Tuple(7, 8));
        EntityVillager.blacksmithSellingList.put(Items.iron_boots, new Tuple(4, 6));
        EntityVillager.blacksmithSellingList.put(Items.diamond_boots, new Tuple(7, 8));
        EntityVillager.blacksmithSellingList.put(Items.iron_helmet, new Tuple(4, 6));
        EntityVillager.blacksmithSellingList.put(Items.diamond_helmet, new Tuple(7, 8));
        EntityVillager.blacksmithSellingList.put(Items.iron_chestplate, new Tuple(10, 14));
        EntityVillager.blacksmithSellingList.put(Items.diamond_chestplate, new Tuple(16, 19));
        EntityVillager.blacksmithSellingList.put(Items.iron_leggings, new Tuple(8, 10));
        EntityVillager.blacksmithSellingList.put(Items.diamond_leggings, new Tuple(11, 14));
        EntityVillager.blacksmithSellingList.put(Items.chainmail_boots, new Tuple(5, 7));
        EntityVillager.blacksmithSellingList.put(Items.chainmail_helmet, new Tuple(5, 7));
        EntityVillager.blacksmithSellingList.put(Items.chainmail_chestplate, new Tuple(11, 15));
        EntityVillager.blacksmithSellingList.put(Items.chainmail_leggings, new Tuple(9, 11));
        EntityVillager.blacksmithSellingList.put(Items.bread, new Tuple(-4, -2));
        EntityVillager.blacksmithSellingList.put(Items.melon, new Tuple(-8, -4));
        EntityVillager.blacksmithSellingList.put(Items.apple, new Tuple(-8, -4));
        EntityVillager.blacksmithSellingList.put(Items.cookie, new Tuple(-10, -7));
        EntityVillager.blacksmithSellingList.put(Item.getItemFromBlock(Blocks.glass), new Tuple(-5, -3));
        EntityVillager.blacksmithSellingList.put(Item.getItemFromBlock(Blocks.bookshelf), new Tuple(3, 4));
        EntityVillager.blacksmithSellingList.put(Items.leather_chestplate, new Tuple(4, 5));
        EntityVillager.blacksmithSellingList.put(Items.leather_boots, new Tuple(2, 4));
        EntityVillager.blacksmithSellingList.put(Items.leather_helmet, new Tuple(2, 4));
        EntityVillager.blacksmithSellingList.put(Items.leather_leggings, new Tuple(2, 4));
        EntityVillager.blacksmithSellingList.put(Items.saddle, new Tuple(6, 8));
        EntityVillager.blacksmithSellingList.put(Items.experience_bottle, new Tuple(-4, -1));
        EntityVillager.blacksmithSellingList.put(Items.redstone, new Tuple(-4, -1));
        EntityVillager.blacksmithSellingList.put(Items.compass, new Tuple(10, 12));
        EntityVillager.blacksmithSellingList.put(Items.clock, new Tuple(10, 12));
        EntityVillager.blacksmithSellingList.put(Item.getItemFromBlock(Blocks.glowstone), new Tuple(-3, -1));
        EntityVillager.blacksmithSellingList.put(Items.cooked_porkchop, new Tuple(-7, -5));
        EntityVillager.blacksmithSellingList.put(Items.cooked_beef, new Tuple(-7, -5));
        EntityVillager.blacksmithSellingList.put(Items.cooked_chicken, new Tuple(-8, -6));
        EntityVillager.blacksmithSellingList.put(Items.ender_eye, new Tuple(7, 11));
        EntityVillager.blacksmithSellingList.put(Items.arrow, new Tuple(-12, -8));
    }
}
