package net.minecraft.entity.player;

import net.minecraft.server.*;
import com.mojang.authlib.*;
import net.minecraft.nbt.*;
import net.minecraft.world.*;
import net.minecraft.world.chunk.*;
import net.minecraft.crash.*;
import net.minecraft.world.biome.*;
import com.google.common.collect.*;
import java.util.*;
import net.minecraft.stats.*;
import net.minecraft.scoreboard.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.entity.item.*;
import net.minecraft.tileentity.*;
import net.minecraft.entity.*;
import net.minecraft.network.*;
import io.netty.buffer.*;
import java.io.*;
import net.minecraft.village.*;
import net.minecraft.entity.passive.*;
import net.minecraft.inventory.*;
import net.minecraft.util.*;
import net.minecraft.item.*;
import net.minecraft.potion.*;
import net.minecraft.network.play.server.*;
import net.minecraft.server.management.*;
import net.minecraft.network.play.client.*;
import org.apache.commons.io.*;
import org.apache.logging.log4j.*;

public class EntityPlayerMP extends EntityPlayer implements ICrafting
{
    private static final Logger logger;
    private String translator;
    public NetHandlerPlayServer playerNetServerHandler;
    public final MinecraftServer mcServer;
    public final ItemInWorldManager theItemInWorldManager;
    public double managedPosX;
    public double managedPosZ;
    public final List loadedChunks;
    private final List destroyedItemsNetCache;
    private final StatisticsFile field_147103_bO;
    private float field_130068_bO;
    private float lastHealth;
    private int lastFoodLevel;
    private boolean wasHungry;
    private int lastExperience;
    private int field_147101_bU;
    private EnumChatVisibility chatVisibility;
    private boolean chatColours;
    private long field_143005_bX;
    private int currentWindowId;
    public boolean isChangingQuantityOnly;
    public int ping;
    public boolean playerConqueredTheEnd;
    private static final String __OBFID = "CL_00001440";
    
    public EntityPlayerMP(final MinecraftServer p_i45285_1_, final WorldServer p_i45285_2_, final GameProfile p_i45285_3_, final ItemInWorldManager p_i45285_4_) {
        super(p_i45285_2_, p_i45285_3_);
        this.translator = "en_US";
        this.loadedChunks = new LinkedList();
        this.destroyedItemsNetCache = new LinkedList();
        this.field_130068_bO = Float.MIN_VALUE;
        this.lastHealth = -1.0E8f;
        this.lastFoodLevel = -99999999;
        this.wasHungry = true;
        this.lastExperience = -99999999;
        this.field_147101_bU = 60;
        this.chatColours = true;
        this.field_143005_bX = System.currentTimeMillis();
        p_i45285_4_.thisPlayerMP = this;
        this.theItemInWorldManager = p_i45285_4_;
        final ChunkCoordinates var5 = p_i45285_2_.getSpawnPoint();
        int var6 = var5.posX;
        int var7 = var5.posZ;
        int var8 = var5.posY;
        if (!p_i45285_2_.provider.hasNoSky && p_i45285_2_.getWorldInfo().getGameType() != WorldSettings.GameType.ADVENTURE) {
            final int var9 = Math.max(5, p_i45285_1_.getSpawnProtectionSize() - 6);
            var6 += this.rand.nextInt(var9 * 2) - var9;
            var7 += this.rand.nextInt(var9 * 2) - var9;
            var8 = p_i45285_2_.getTopSolidOrLiquidBlock(var6, var7);
        }
        this.mcServer = p_i45285_1_;
        this.field_147103_bO = p_i45285_1_.getConfigurationManager().func_152602_a(this);
        this.stepHeight = 0.0f;
        this.yOffset = 0.0f;
        this.setLocationAndAngles(var6 + 0.5, var8, var7 + 0.5, 0.0f, 0.0f);
        while (!p_i45285_2_.getCollidingBoundingBoxes(this, this.boundingBox).isEmpty()) {
            this.setPosition(this.posX, this.posY + 1.0, this.posZ);
        }
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound p_70037_1_) {
        super.readEntityFromNBT(p_70037_1_);
        if (p_70037_1_.func_150297_b("playerGameType", 99)) {
            if (MinecraftServer.getServer().getForceGamemode()) {
                this.theItemInWorldManager.setGameType(MinecraftServer.getServer().getGameType());
            }
            else {
                this.theItemInWorldManager.setGameType(WorldSettings.GameType.getByID(p_70037_1_.getInteger("playerGameType")));
            }
        }
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound p_70014_1_) {
        super.writeEntityToNBT(p_70014_1_);
        p_70014_1_.setInteger("playerGameType", this.theItemInWorldManager.getGameType().getID());
    }
    
    @Override
    public void addExperienceLevel(final int p_82242_1_) {
        super.addExperienceLevel(p_82242_1_);
        this.lastExperience = -1;
    }
    
    public void addSelfToInternalCraftingInventory() {
        this.openContainer.addCraftingToCrafters(this);
    }
    
    @Override
    protected void resetHeight() {
        this.yOffset = 0.0f;
    }
    
    @Override
    public float getEyeHeight() {
        return 1.62f;
    }
    
    @Override
    public void onUpdate() {
        this.theItemInWorldManager.updateBlockRemoving();
        --this.field_147101_bU;
        if (this.hurtResistantTime > 0) {
            --this.hurtResistantTime;
        }
        this.openContainer.detectAndSendChanges();
        if (!this.worldObj.isClient && !this.openContainer.canInteractWith(this)) {
            this.closeScreen();
            this.openContainer = this.inventoryContainer;
        }
        while (!this.destroyedItemsNetCache.isEmpty()) {
            final int var1 = Math.min(this.destroyedItemsNetCache.size(), 127);
            final int[] var2 = new int[var1];
            final Iterator var3 = this.destroyedItemsNetCache.iterator();
            int var4 = 0;
            while (var3.hasNext() && var4 < var1) {
                var2[var4++] = var3.next();
                var3.remove();
            }
            this.playerNetServerHandler.sendPacket(new S13PacketDestroyEntities(var2));
        }
        if (!this.loadedChunks.isEmpty()) {
            final ArrayList var5 = new ArrayList();
            final Iterator var6 = this.loadedChunks.iterator();
            final ArrayList var7 = new ArrayList();
            while (var6.hasNext() && var5.size() < S26PacketMapChunkBulk.func_149258_c()) {
                final ChunkCoordIntPair var8 = var6.next();
                if (var8 != null) {
                    if (!this.worldObj.blockExists(var8.chunkXPos << 4, 0, var8.chunkZPos << 4)) {
                        continue;
                    }
                    final Chunk var9 = this.worldObj.getChunkFromChunkCoords(var8.chunkXPos, var8.chunkZPos);
                    if (!var9.func_150802_k()) {
                        continue;
                    }
                    var5.add(var9);
                    var7.addAll(((WorldServer)this.worldObj).func_147486_a(var8.chunkXPos * 16, 0, var8.chunkZPos * 16, var8.chunkXPos * 16 + 16, 256, var8.chunkZPos * 16 + 16));
                    var6.remove();
                }
                else {
                    var6.remove();
                }
            }
            if (!var5.isEmpty()) {
                this.playerNetServerHandler.sendPacket(new S26PacketMapChunkBulk(var5));
                for (final TileEntity var11 : var7) {
                    this.func_147097_b(var11);
                }
                for (final Chunk var9 : var5) {
                    this.getServerForPlayer().getEntityTracker().func_85172_a(this, var9);
                }
            }
        }
    }
    
    public void onUpdateEntity() {
        try {
            super.onUpdate();
            for (int var1 = 0; var1 < this.inventory.getSizeInventory(); ++var1) {
                final ItemStack var2 = this.inventory.getStackInSlot(var1);
                if (var2 != null && var2.getItem().isMap()) {
                    final Packet var3 = ((ItemMapBase)var2.getItem()).func_150911_c(var2, this.worldObj, this);
                    if (var3 != null) {
                        this.playerNetServerHandler.sendPacket(var3);
                    }
                }
            }
            if (this.getHealth() != this.lastHealth || this.lastFoodLevel != this.foodStats.getFoodLevel() || this.foodStats.getSaturationLevel() == 0.0f != this.wasHungry) {
                this.playerNetServerHandler.sendPacket(new S06PacketUpdateHealth(this.getHealth(), this.foodStats.getFoodLevel(), this.foodStats.getSaturationLevel()));
                this.lastHealth = this.getHealth();
                this.lastFoodLevel = this.foodStats.getFoodLevel();
                this.wasHungry = (this.foodStats.getSaturationLevel() == 0.0f);
            }
            if (this.getHealth() + this.getAbsorptionAmount() != this.field_130068_bO) {
                this.field_130068_bO = this.getHealth() + this.getAbsorptionAmount();
                final Collection var4 = this.getWorldScoreboard().func_96520_a(IScoreObjectiveCriteria.health);
                for (final ScoreObjective var6 : var4) {
                    this.getWorldScoreboard().func_96529_a(this.getCommandSenderName(), var6).func_96651_a(Arrays.asList(this));
                }
            }
            if (this.experienceTotal != this.lastExperience) {
                this.lastExperience = this.experienceTotal;
                this.playerNetServerHandler.sendPacket(new S1FPacketSetExperience(this.experience, this.experienceTotal, this.experienceLevel));
            }
            if (this.ticksExisted % 20 * 5 == 0 && !this.func_147099_x().hasAchievementUnlocked(AchievementList.field_150961_L)) {
                this.func_147098_j();
            }
        }
        catch (Throwable var8) {
            final CrashReport var7 = CrashReport.makeCrashReport(var8, "Ticking player");
            final CrashReportCategory var9 = var7.makeCategory("Player being ticked");
            this.addEntityCrashInfo(var9);
            throw new ReportedException(var7);
        }
    }
    
    protected void func_147098_j() {
        final BiomeGenBase var1 = this.worldObj.getBiomeGenForCoords(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posZ));
        if (var1 != null) {
            final String var2 = var1.biomeName;
            JsonSerializableSet var3 = (JsonSerializableSet)this.func_147099_x().func_150870_b(AchievementList.field_150961_L);
            if (var3 == null) {
                var3 = (JsonSerializableSet)this.func_147099_x().func_150872_a(AchievementList.field_150961_L, new JsonSerializableSet());
            }
            var3.add((Object)var2);
            if (this.func_147099_x().canUnlockAchievement(AchievementList.field_150961_L) && var3.size() == BiomeGenBase.field_150597_n.size()) {
                final HashSet var4 = Sets.newHashSet((Iterable)BiomeGenBase.field_150597_n);
                for (final String var6 : var3) {
                    final Iterator var7 = var4.iterator();
                    while (var7.hasNext()) {
                        final BiomeGenBase var8 = var7.next();
                        if (var8.biomeName.equals(var6)) {
                            var7.remove();
                        }
                    }
                    if (var4.isEmpty()) {
                        break;
                    }
                }
                if (var4.isEmpty()) {
                    this.triggerAchievement(AchievementList.field_150961_L);
                }
            }
        }
    }
    
    @Override
    public void onDeath(final DamageSource p_70645_1_) {
        this.mcServer.getConfigurationManager().func_148539_a(this.func_110142_aN().func_151521_b());
        if (!this.worldObj.getGameRules().getGameRuleBooleanValue("keepInventory")) {
            this.inventory.dropAllItems();
        }
        final Collection var2 = this.worldObj.getScoreboard().func_96520_a(IScoreObjectiveCriteria.deathCount);
        for (final ScoreObjective var4 : var2) {
            final Score var5 = this.getWorldScoreboard().func_96529_a(this.getCommandSenderName(), var4);
            var5.func_96648_a();
        }
        final EntityLivingBase var6 = this.func_94060_bK();
        if (var6 != null) {
            final int var7 = EntityList.getEntityID(var6);
            final EntityList.EntityEggInfo var8 = EntityList.entityEggs.get(var7);
            if (var8 != null) {
                this.addStat(var8.field_151513_e, 1);
            }
            var6.addToPlayerScore(this, this.scoreValue);
        }
        this.addStat(StatList.deathsStat, 1);
        this.func_110142_aN().func_94549_h();
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource p_70097_1_, final float p_70097_2_) {
        if (this.isEntityInvulnerable()) {
            return false;
        }
        final boolean var3 = this.mcServer.isDedicatedServer() && this.mcServer.isPVPEnabled() && "fall".equals(p_70097_1_.damageType);
        if (!var3 && this.field_147101_bU > 0 && p_70097_1_ != DamageSource.outOfWorld) {
            return false;
        }
        if (p_70097_1_ instanceof EntityDamageSource) {
            final Entity var4 = p_70097_1_.getEntity();
            if (var4 instanceof EntityPlayer && !this.canAttackPlayer((EntityPlayer)var4)) {
                return false;
            }
            if (var4 instanceof EntityArrow) {
                final EntityArrow var5 = (EntityArrow)var4;
                if (var5.shootingEntity instanceof EntityPlayer && !this.canAttackPlayer((EntityPlayer)var5.shootingEntity)) {
                    return false;
                }
            }
        }
        return super.attackEntityFrom(p_70097_1_, p_70097_2_);
    }
    
    @Override
    public boolean canAttackPlayer(final EntityPlayer p_96122_1_) {
        return this.mcServer.isPVPEnabled() && super.canAttackPlayer(p_96122_1_);
    }
    
    @Override
    public void travelToDimension(int p_71027_1_) {
        if (this.dimension == 1 && p_71027_1_ == 1) {
            this.triggerAchievement(AchievementList.theEnd2);
            this.worldObj.removeEntity(this);
            this.playerConqueredTheEnd = true;
            this.playerNetServerHandler.sendPacket(new S2BPacketChangeGameState(4, 0.0f));
        }
        else {
            if (this.dimension == 0 && p_71027_1_ == 1) {
                this.triggerAchievement(AchievementList.theEnd);
                final ChunkCoordinates var2 = this.mcServer.worldServerForDimension(p_71027_1_).getEntrancePortalLocation();
                if (var2 != null) {
                    this.playerNetServerHandler.setPlayerLocation(var2.posX, var2.posY, var2.posZ, 0.0f, 0.0f);
                }
                p_71027_1_ = 1;
            }
            else {
                this.triggerAchievement(AchievementList.portal);
            }
            this.mcServer.getConfigurationManager().transferPlayerToDimension(this, p_71027_1_);
            this.lastExperience = -1;
            this.lastHealth = -1.0f;
            this.lastFoodLevel = -1;
        }
    }
    
    private void func_147097_b(final TileEntity p_147097_1_) {
        if (p_147097_1_ != null) {
            final Packet var2 = p_147097_1_.getDescriptionPacket();
            if (var2 != null) {
                this.playerNetServerHandler.sendPacket(var2);
            }
        }
    }
    
    @Override
    public void onItemPickup(final Entity p_71001_1_, final int p_71001_2_) {
        super.onItemPickup(p_71001_1_, p_71001_2_);
        this.openContainer.detectAndSendChanges();
    }
    
    @Override
    public EnumStatus sleepInBedAt(final int p_71018_1_, final int p_71018_2_, final int p_71018_3_) {
        final EnumStatus var4 = super.sleepInBedAt(p_71018_1_, p_71018_2_, p_71018_3_);
        if (var4 == EnumStatus.OK) {
            final S0APacketUseBed var5 = new S0APacketUseBed(this, p_71018_1_, p_71018_2_, p_71018_3_);
            this.getServerForPlayer().getEntityTracker().func_151247_a(this, var5);
            this.playerNetServerHandler.setPlayerLocation(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
            this.playerNetServerHandler.sendPacket(var5);
        }
        return var4;
    }
    
    @Override
    public void wakeUpPlayer(final boolean p_70999_1_, final boolean p_70999_2_, final boolean p_70999_3_) {
        if (this.isPlayerSleeping()) {
            this.getServerForPlayer().getEntityTracker().func_151248_b(this, new S0BPacketAnimation(this, 2));
        }
        super.wakeUpPlayer(p_70999_1_, p_70999_2_, p_70999_3_);
        if (this.playerNetServerHandler != null) {
            this.playerNetServerHandler.setPlayerLocation(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
        }
    }
    
    @Override
    public void mountEntity(final Entity p_70078_1_) {
        super.mountEntity(p_70078_1_);
        this.playerNetServerHandler.sendPacket(new S1BPacketEntityAttach(0, this, this.ridingEntity));
        this.playerNetServerHandler.setPlayerLocation(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
    }
    
    @Override
    protected void updateFallState(final double p_70064_1_, final boolean p_70064_3_) {
    }
    
    public void handleFalling(final double p_71122_1_, final boolean p_71122_3_) {
        super.updateFallState(p_71122_1_, p_71122_3_);
    }
    
    @Override
    public void func_146100_a(final TileEntity p_146100_1_) {
        if (p_146100_1_ instanceof TileEntitySign) {
            ((TileEntitySign)p_146100_1_).func_145912_a(this);
            this.playerNetServerHandler.sendPacket(new S36PacketSignEditorOpen(p_146100_1_.field_145851_c, p_146100_1_.field_145848_d, p_146100_1_.field_145849_e));
        }
    }
    
    private void getNextWindowId() {
        this.currentWindowId = this.currentWindowId % 100 + 1;
    }
    
    @Override
    public void displayGUIWorkbench(final int p_71058_1_, final int p_71058_2_, final int p_71058_3_) {
        this.getNextWindowId();
        this.playerNetServerHandler.sendPacket(new S2DPacketOpenWindow(this.currentWindowId, 1, "Crafting", 9, true));
        this.openContainer = new ContainerWorkbench(this.inventory, this.worldObj, p_71058_1_, p_71058_2_, p_71058_3_);
        this.openContainer.windowId = this.currentWindowId;
        this.openContainer.addCraftingToCrafters(this);
    }
    
    @Override
    public void displayGUIEnchantment(final int p_71002_1_, final int p_71002_2_, final int p_71002_3_, final String p_71002_4_) {
        this.getNextWindowId();
        this.playerNetServerHandler.sendPacket(new S2DPacketOpenWindow(this.currentWindowId, 4, (p_71002_4_ == null) ? "" : p_71002_4_, 9, p_71002_4_ != null));
        this.openContainer = new ContainerEnchantment(this.inventory, this.worldObj, p_71002_1_, p_71002_2_, p_71002_3_);
        this.openContainer.windowId = this.currentWindowId;
        this.openContainer.addCraftingToCrafters(this);
    }
    
    @Override
    public void displayGUIAnvil(final int p_82244_1_, final int p_82244_2_, final int p_82244_3_) {
        this.getNextWindowId();
        this.playerNetServerHandler.sendPacket(new S2DPacketOpenWindow(this.currentWindowId, 8, "Repairing", 9, true));
        this.openContainer = new ContainerRepair(this.inventory, this.worldObj, p_82244_1_, p_82244_2_, p_82244_3_, this);
        this.openContainer.windowId = this.currentWindowId;
        this.openContainer.addCraftingToCrafters(this);
    }
    
    @Override
    public void displayGUIChest(final IInventory p_71007_1_) {
        if (this.openContainer != this.inventoryContainer) {
            this.closeScreen();
        }
        this.getNextWindowId();
        this.playerNetServerHandler.sendPacket(new S2DPacketOpenWindow(this.currentWindowId, 0, p_71007_1_.getInventoryName(), p_71007_1_.getSizeInventory(), p_71007_1_.isInventoryNameLocalized()));
        this.openContainer = new ContainerChest(this.inventory, p_71007_1_);
        this.openContainer.windowId = this.currentWindowId;
        this.openContainer.addCraftingToCrafters(this);
    }
    
    @Override
    public void func_146093_a(final TileEntityHopper p_146093_1_) {
        this.getNextWindowId();
        this.playerNetServerHandler.sendPacket(new S2DPacketOpenWindow(this.currentWindowId, 9, p_146093_1_.getInventoryName(), p_146093_1_.getSizeInventory(), p_146093_1_.isInventoryNameLocalized()));
        this.openContainer = new ContainerHopper(this.inventory, p_146093_1_);
        this.openContainer.windowId = this.currentWindowId;
        this.openContainer.addCraftingToCrafters(this);
    }
    
    @Override
    public void displayGUIHopperMinecart(final EntityMinecartHopper p_96125_1_) {
        this.getNextWindowId();
        this.playerNetServerHandler.sendPacket(new S2DPacketOpenWindow(this.currentWindowId, 9, p_96125_1_.getInventoryName(), p_96125_1_.getSizeInventory(), p_96125_1_.isInventoryNameLocalized()));
        this.openContainer = new ContainerHopper(this.inventory, p_96125_1_);
        this.openContainer.windowId = this.currentWindowId;
        this.openContainer.addCraftingToCrafters(this);
    }
    
    @Override
    public void func_146101_a(final TileEntityFurnace p_146101_1_) {
        this.getNextWindowId();
        this.playerNetServerHandler.sendPacket(new S2DPacketOpenWindow(this.currentWindowId, 2, p_146101_1_.getInventoryName(), p_146101_1_.getSizeInventory(), p_146101_1_.isInventoryNameLocalized()));
        this.openContainer = new ContainerFurnace(this.inventory, p_146101_1_);
        this.openContainer.windowId = this.currentWindowId;
        this.openContainer.addCraftingToCrafters(this);
    }
    
    @Override
    public void func_146102_a(final TileEntityDispenser p_146102_1_) {
        this.getNextWindowId();
        this.playerNetServerHandler.sendPacket(new S2DPacketOpenWindow(this.currentWindowId, (p_146102_1_ instanceof TileEntityDropper) ? 10 : 3, p_146102_1_.getInventoryName(), p_146102_1_.getSizeInventory(), p_146102_1_.isInventoryNameLocalized()));
        this.openContainer = new ContainerDispenser(this.inventory, p_146102_1_);
        this.openContainer.windowId = this.currentWindowId;
        this.openContainer.addCraftingToCrafters(this);
    }
    
    @Override
    public void func_146098_a(final TileEntityBrewingStand p_146098_1_) {
        this.getNextWindowId();
        this.playerNetServerHandler.sendPacket(new S2DPacketOpenWindow(this.currentWindowId, 5, p_146098_1_.getInventoryName(), p_146098_1_.getSizeInventory(), p_146098_1_.isInventoryNameLocalized()));
        this.openContainer = new ContainerBrewingStand(this.inventory, p_146098_1_);
        this.openContainer.windowId = this.currentWindowId;
        this.openContainer.addCraftingToCrafters(this);
    }
    
    @Override
    public void func_146104_a(final TileEntityBeacon p_146104_1_) {
        this.getNextWindowId();
        this.playerNetServerHandler.sendPacket(new S2DPacketOpenWindow(this.currentWindowId, 7, p_146104_1_.getInventoryName(), p_146104_1_.getSizeInventory(), p_146104_1_.isInventoryNameLocalized()));
        this.openContainer = new ContainerBeacon(this.inventory, p_146104_1_);
        this.openContainer.windowId = this.currentWindowId;
        this.openContainer.addCraftingToCrafters(this);
    }
    
    @Override
    public void displayGUIMerchant(final IMerchant p_71030_1_, final String p_71030_2_) {
        this.getNextWindowId();
        this.openContainer = new ContainerMerchant(this.inventory, p_71030_1_, this.worldObj);
        this.openContainer.windowId = this.currentWindowId;
        this.openContainer.addCraftingToCrafters(this);
        final InventoryMerchant var3 = ((ContainerMerchant)this.openContainer).getMerchantInventory();
        this.playerNetServerHandler.sendPacket(new S2DPacketOpenWindow(this.currentWindowId, 6, (p_71030_2_ == null) ? "" : p_71030_2_, var3.getSizeInventory(), p_71030_2_ != null));
        final MerchantRecipeList var4 = p_71030_1_.getRecipes(this);
        if (var4 != null) {
            final PacketBuffer var5 = new PacketBuffer(Unpooled.buffer());
            try {
                var5.writeInt(this.currentWindowId);
                var4.func_151391_a(var5);
                this.playerNetServerHandler.sendPacket(new S3FPacketCustomPayload("MC|TrList", var5));
            }
            catch (IOException var6) {
                EntityPlayerMP.logger.error("Couldn't send trade list", (Throwable)var6);
            }
            finally {
                var5.release();
            }
        }
    }
    
    @Override
    public void displayGUIHorse(final EntityHorse p_110298_1_, final IInventory p_110298_2_) {
        if (this.openContainer != this.inventoryContainer) {
            this.closeScreen();
        }
        this.getNextWindowId();
        this.playerNetServerHandler.sendPacket(new S2DPacketOpenWindow(this.currentWindowId, 11, p_110298_2_.getInventoryName(), p_110298_2_.getSizeInventory(), p_110298_2_.isInventoryNameLocalized(), p_110298_1_.getEntityId()));
        this.openContainer = new ContainerHorseInventory(this.inventory, p_110298_2_, p_110298_1_);
        this.openContainer.windowId = this.currentWindowId;
        this.openContainer.addCraftingToCrafters(this);
    }
    
    @Override
    public void sendSlotContents(final Container p_71111_1_, final int p_71111_2_, final ItemStack p_71111_3_) {
        if (!(p_71111_1_.getSlot(p_71111_2_) instanceof SlotCrafting) && !this.isChangingQuantityOnly) {
            this.playerNetServerHandler.sendPacket(new S2FPacketSetSlot(p_71111_1_.windowId, p_71111_2_, p_71111_3_));
        }
    }
    
    public void sendContainerToPlayer(final Container p_71120_1_) {
        this.sendContainerAndContentsToPlayer(p_71120_1_, p_71120_1_.getInventory());
    }
    
    @Override
    public void sendContainerAndContentsToPlayer(final Container p_71110_1_, final List p_71110_2_) {
        this.playerNetServerHandler.sendPacket(new S30PacketWindowItems(p_71110_1_.windowId, p_71110_2_));
        this.playerNetServerHandler.sendPacket(new S2FPacketSetSlot(-1, -1, this.inventory.getItemStack()));
    }
    
    @Override
    public void sendProgressBarUpdate(final Container p_71112_1_, final int p_71112_2_, final int p_71112_3_) {
        this.playerNetServerHandler.sendPacket(new S31PacketWindowProperty(p_71112_1_.windowId, p_71112_2_, p_71112_3_));
    }
    
    public void closeScreen() {
        this.playerNetServerHandler.sendPacket(new S2EPacketCloseWindow(this.openContainer.windowId));
        this.closeContainer();
    }
    
    public void updateHeldItem() {
        if (!this.isChangingQuantityOnly) {
            this.playerNetServerHandler.sendPacket(new S2FPacketSetSlot(-1, -1, this.inventory.getItemStack()));
        }
    }
    
    public void closeContainer() {
        this.openContainer.onContainerClosed(this);
        this.openContainer = this.inventoryContainer;
    }
    
    public void setEntityActionState(final float p_110430_1_, final float p_110430_2_, final boolean p_110430_3_, final boolean p_110430_4_) {
        if (this.ridingEntity != null) {
            if (p_110430_1_ >= -1.0f && p_110430_1_ <= 1.0f) {
                this.moveStrafing = p_110430_1_;
            }
            if (p_110430_2_ >= -1.0f && p_110430_2_ <= 1.0f) {
                this.moveForward = p_110430_2_;
            }
            this.isJumping = p_110430_3_;
            this.setSneaking(p_110430_4_);
        }
    }
    
    @Override
    public void addStat(final StatBase p_71064_1_, final int p_71064_2_) {
        if (p_71064_1_ != null) {
            this.field_147103_bO.func_150871_b(this, p_71064_1_, p_71064_2_);
            for (final ScoreObjective var4 : this.getWorldScoreboard().func_96520_a(p_71064_1_.func_150952_k())) {
                this.getWorldScoreboard().func_96529_a(this.getCommandSenderName(), var4).func_96648_a();
            }
            if (this.field_147103_bO.func_150879_e()) {
                this.field_147103_bO.func_150876_a(this);
            }
        }
    }
    
    public void mountEntityAndWakeUp() {
        if (this.riddenByEntity != null) {
            this.riddenByEntity.mountEntity(this);
        }
        if (this.sleeping) {
            this.wakeUpPlayer(true, false, false);
        }
    }
    
    public void setPlayerHealthUpdated() {
        this.lastHealth = -1.0E8f;
    }
    
    @Override
    public void addChatComponentMessage(final IChatComponent p_146105_1_) {
        this.playerNetServerHandler.sendPacket(new S02PacketChat(p_146105_1_));
    }
    
    @Override
    protected void onItemUseFinish() {
        this.playerNetServerHandler.sendPacket(new S19PacketEntityStatus(this, (byte)9));
        super.onItemUseFinish();
    }
    
    @Override
    public void setItemInUse(final ItemStack p_71008_1_, final int p_71008_2_) {
        super.setItemInUse(p_71008_1_, p_71008_2_);
        if (p_71008_1_ != null && p_71008_1_.getItem() != null && p_71008_1_.getItem().getItemUseAction(p_71008_1_) == EnumAction.eat) {
            this.getServerForPlayer().getEntityTracker().func_151248_b(this, new S0BPacketAnimation(this, 3));
        }
    }
    
    @Override
    public void clonePlayer(final EntityPlayer p_71049_1_, final boolean p_71049_2_) {
        super.clonePlayer(p_71049_1_, p_71049_2_);
        this.lastExperience = -1;
        this.lastHealth = -1.0f;
        this.lastFoodLevel = -1;
        this.destroyedItemsNetCache.addAll(((EntityPlayerMP)p_71049_1_).destroyedItemsNetCache);
    }
    
    @Override
    protected void onNewPotionEffect(final PotionEffect p_70670_1_) {
        super.onNewPotionEffect(p_70670_1_);
        this.playerNetServerHandler.sendPacket(new S1DPacketEntityEffect(this.getEntityId(), p_70670_1_));
    }
    
    @Override
    protected void onChangedPotionEffect(final PotionEffect p_70695_1_, final boolean p_70695_2_) {
        super.onChangedPotionEffect(p_70695_1_, p_70695_2_);
        this.playerNetServerHandler.sendPacket(new S1DPacketEntityEffect(this.getEntityId(), p_70695_1_));
    }
    
    @Override
    protected void onFinishedPotionEffect(final PotionEffect p_70688_1_) {
        super.onFinishedPotionEffect(p_70688_1_);
        this.playerNetServerHandler.sendPacket(new S1EPacketRemoveEntityEffect(this.getEntityId(), p_70688_1_));
    }
    
    @Override
    public void setPositionAndUpdate(final double p_70634_1_, final double p_70634_3_, final double p_70634_5_) {
        this.playerNetServerHandler.setPlayerLocation(p_70634_1_, p_70634_3_, p_70634_5_, this.rotationYaw, this.rotationPitch);
    }
    
    @Override
    public void onCriticalHit(final Entity p_71009_1_) {
        this.getServerForPlayer().getEntityTracker().func_151248_b(this, new S0BPacketAnimation(p_71009_1_, 4));
    }
    
    @Override
    public void onEnchantmentCritical(final Entity p_71047_1_) {
        this.getServerForPlayer().getEntityTracker().func_151248_b(this, new S0BPacketAnimation(p_71047_1_, 5));
    }
    
    @Override
    public void sendPlayerAbilities() {
        if (this.playerNetServerHandler != null) {
            this.playerNetServerHandler.sendPacket(new S39PacketPlayerAbilities(this.capabilities));
        }
    }
    
    public WorldServer getServerForPlayer() {
        return (WorldServer)this.worldObj;
    }
    
    @Override
    public void setGameType(final WorldSettings.GameType p_71033_1_) {
        this.theItemInWorldManager.setGameType(p_71033_1_);
        this.playerNetServerHandler.sendPacket(new S2BPacketChangeGameState(3, (float)p_71033_1_.getID()));
    }
    
    @Override
    public void addChatMessage(final IChatComponent p_145747_1_) {
        this.playerNetServerHandler.sendPacket(new S02PacketChat(p_145747_1_));
    }
    
    @Override
    public boolean canCommandSenderUseCommand(final int p_70003_1_, final String p_70003_2_) {
        if ("seed".equals(p_70003_2_) && !this.mcServer.isDedicatedServer()) {
            return true;
        }
        if ("tell".equals(p_70003_2_) || "help".equals(p_70003_2_) || "me".equals(p_70003_2_)) {
            return true;
        }
        if (this.mcServer.getConfigurationManager().func_152596_g(this.getGameProfile())) {
            final UserListOpsEntry var3 = (UserListOpsEntry)this.mcServer.getConfigurationManager().func_152603_m().func_152683_b(this.getGameProfile());
            return (var3 != null) ? (var3.func_152644_a() >= p_70003_1_) : (this.mcServer.func_110455_j() >= p_70003_1_);
        }
        return false;
    }
    
    public String getPlayerIP() {
        String var1 = this.playerNetServerHandler.netManager.getSocketAddress().toString();
        var1 = var1.substring(var1.indexOf("/") + 1);
        var1 = var1.substring(0, var1.indexOf(":"));
        return var1;
    }
    
    public void func_147100_a(final C15PacketClientSettings p_147100_1_) {
        this.translator = p_147100_1_.func_149524_c();
        final int var2 = 256 >> p_147100_1_.func_149521_d();
        if (var2 <= 3 || var2 < 20) {}
        this.chatVisibility = p_147100_1_.func_149523_e();
        this.chatColours = p_147100_1_.func_149520_f();
        if (this.mcServer.isSinglePlayer() && this.mcServer.getServerOwner().equals(this.getCommandSenderName())) {
            this.mcServer.func_147139_a(p_147100_1_.func_149518_g());
        }
        this.setHideCape(1, !p_147100_1_.func_149519_h());
    }
    
    public EnumChatVisibility func_147096_v() {
        return this.chatVisibility;
    }
    
    public void func_147095_a(final String p_147095_1_) {
        this.playerNetServerHandler.sendPacket(new S3FPacketCustomPayload("MC|RPack", p_147095_1_.getBytes(Charsets.UTF_8)));
    }
    
    @Override
    public ChunkCoordinates getPlayerCoordinates() {
        return new ChunkCoordinates(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY + 0.5), MathHelper.floor_double(this.posZ));
    }
    
    public void func_143004_u() {
        this.field_143005_bX = MinecraftServer.getSystemTimeMillis();
    }
    
    public StatisticsFile func_147099_x() {
        return this.field_147103_bO;
    }
    
    public void func_152339_d(final Entity p_152339_1_) {
        if (p_152339_1_ instanceof EntityPlayer) {
            this.playerNetServerHandler.sendPacket(new S13PacketDestroyEntities(new int[] { p_152339_1_.getEntityId() }));
        }
        else {
            this.destroyedItemsNetCache.add(p_152339_1_.getEntityId());
        }
    }
    
    public long func_154331_x() {
        return this.field_143005_bX;
    }
    
    static {
        logger = LogManager.getLogger();
    }
}
