package net.minecraft.client.entity;

import net.minecraft.client.*;
import net.minecraft.world.*;
import us.zonix.client.*;
import us.zonix.client.module.*;
import net.minecraft.client.audio.*;
import net.minecraft.potion.*;
import java.util.*;
import net.minecraft.init.*;
import net.minecraft.entity.ai.attributes.*;
import net.minecraft.command.server.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.inventory.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.passive.*;
import net.minecraft.tileentity.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.client.gui.*;
import net.minecraft.entity.*;
import net.minecraft.client.particle.*;
import net.minecraft.util.*;

public class EntityPlayerSP extends AbstractClientPlayer
{
    public MovementInput movementInput;
    protected Minecraft mc;
    protected int sprintToggleTimer;
    public int sprintingTicksLeft;
    public float renderArmYaw;
    public float renderArmPitch;
    public float prevRenderArmYaw;
    public float prevRenderArmPitch;
    private int horseJumpPowerCounter;
    private float horseJumpPower;
    private MouseFilter field_71162_ch;
    private MouseFilter field_71160_ci;
    private MouseFilter field_71161_cj;
    public float timeInPortal;
    public float prevTimeInPortal;
    private static final String __OBFID = "CL_00000938";
    
    public EntityPlayerSP(final Minecraft p_i1238_1_, final World p_i1238_2_, final Session p_i1238_3_, final int p_i1238_4_) {
        super(p_i1238_2_, p_i1238_3_.func_148256_e());
        this.field_71162_ch = new MouseFilter();
        this.field_71160_ci = new MouseFilter();
        this.field_71161_cj = new MouseFilter();
        this.mc = p_i1238_1_;
        this.dimension = p_i1238_4_;
        Client.getInstance().getCapeManager().onSpawn(this.getUniqueID());
    }
    
    public void updateEntityActionState() {
        super.updateEntityActionState();
        this.moveStrafing = this.movementInput.moveStrafe;
        this.moveForward = this.movementInput.moveForward;
        this.isJumping = this.movementInput.jump;
        this.prevRenderArmYaw = this.renderArmYaw;
        this.prevRenderArmPitch = this.renderArmPitch;
        this.renderArmPitch += (float)((this.rotationPitch - this.renderArmPitch) * 0.5);
        this.renderArmYaw += (float)((this.rotationYaw - this.renderArmYaw) * 0.5);
    }
    
    @Override
    public void onLivingUpdate() {
        for (final IModule module : Client.getInstance().getModuleManager().getEnabledModules()) {
            module.onPrePlayerUpdate();
        }
        if (this.sprintingTicksLeft > 0) {
            --this.sprintingTicksLeft;
            if (this.sprintingTicksLeft == 0) {
                this.setSprinting(false);
            }
        }
        if (this.sprintToggleTimer > 0) {
            --this.sprintToggleTimer;
        }
        if (this.mc.playerController.enableEverythingIsScrewedUpMode()) {
            final double n = 0.5;
            this.posZ = n;
            this.posX = n;
            this.posX = 0.0;
            this.posZ = 0.0;
            this.rotationYaw = this.ticksExisted / 12.0f;
            this.rotationPitch = 10.0f;
            this.posY = 68.5;
        }
        else {
            this.prevTimeInPortal = this.timeInPortal;
            if (this.inPortal) {
                if (this.mc.currentScreen != null) {
                    this.mc.displayGuiScreen(null);
                }
                if (this.timeInPortal == 0.0f) {
                    this.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("portal.trigger"), this.rand.nextFloat() * 0.4f + 0.8f));
                }
                this.timeInPortal += 0.0125f;
                if (this.timeInPortal >= 1.0f) {
                    this.timeInPortal = 1.0f;
                }
                this.inPortal = false;
            }
            else if (this.isPotionActive(Potion.confusion) && this.getActivePotionEffect(Potion.confusion).getDuration() > 60) {
                this.timeInPortal += 0.006666667f;
                if (this.timeInPortal > 1.0f) {
                    this.timeInPortal = 1.0f;
                }
            }
            else {
                if (this.timeInPortal > 0.0f) {
                    this.timeInPortal -= 0.05f;
                }
                if (this.timeInPortal < 0.0f) {
                    this.timeInPortal = 0.0f;
                }
            }
            if (this.timeUntilPortal > 0) {
                --this.timeUntilPortal;
            }
            final boolean var1 = this.movementInput.jump;
            final float var2 = 0.8f;
            final boolean var3 = this.movementInput.moveForward >= var2;
            this.movementInput.updatePlayerMoveState();
            if (this.isUsingItem() && !this.isRiding()) {
                final MovementInput movementInput = this.movementInput;
                movementInput.moveStrafe *= 0.2f;
                final MovementInput movementInput2 = this.movementInput;
                movementInput2.moveForward *= 0.2f;
                this.sprintToggleTimer = 0;
            }
            if (this.movementInput.sneak && this.ySize < 0.2f) {
                this.ySize = 0.2f;
            }
            this.func_145771_j(this.posX - this.width * 0.35, this.boundingBox.minY + 0.5, this.posZ + this.width * 0.35);
            this.func_145771_j(this.posX - this.width * 0.35, this.boundingBox.minY + 0.5, this.posZ - this.width * 0.35);
            this.func_145771_j(this.posX + this.width * 0.35, this.boundingBox.minY + 0.5, this.posZ - this.width * 0.35);
            this.func_145771_j(this.posX + this.width * 0.35, this.boundingBox.minY + 0.5, this.posZ + this.width * 0.35);
            final boolean var4 = this.getFoodStats().getFoodLevel() > 6.0f || this.capabilities.allowFlying;
            if (this.onGround && !var3 && this.movementInput.moveForward >= var2 && !this.isSprinting() && var4 && !this.isUsingItem() && !this.isPotionActive(Potion.blindness)) {
                if (this.sprintToggleTimer <= 0 && !this.mc.gameSettings.keyBindSprint.getIsKeyPressed()) {
                    this.sprintToggleTimer = 7;
                }
                else {
                    this.setSprinting(true);
                }
            }
            if (!this.isSprinting() && this.movementInput.moveForward >= var2 && var4 && !this.isUsingItem() && !this.isPotionActive(Potion.blindness) && this.mc.gameSettings.keyBindSprint.getIsKeyPressed()) {
                this.setSprinting(true);
            }
            if (this.isSprinting() && (this.movementInput.moveForward < var2 || this.isCollidedHorizontally || !var4)) {
                this.setSprinting(false);
            }
            if (this.capabilities.allowFlying && !var1 && this.movementInput.jump) {
                if (this.flyToggleTimer == 0) {
                    this.flyToggleTimer = 7;
                }
                else {
                    this.capabilities.isFlying = !this.capabilities.isFlying;
                    this.sendPlayerAbilities();
                    this.flyToggleTimer = 0;
                }
            }
            if (this.capabilities.isFlying) {
                if (this.movementInput.sneak) {
                    this.motionY -= 0.15;
                }
                if (this.movementInput.jump) {
                    this.motionY += 0.15;
                }
            }
            if (this.isRidingHorse()) {
                if (this.horseJumpPowerCounter < 0) {
                    ++this.horseJumpPowerCounter;
                    if (this.horseJumpPowerCounter == 0) {
                        this.horseJumpPower = 0.0f;
                    }
                }
                if (var1 && !this.movementInput.jump) {
                    this.horseJumpPowerCounter = -10;
                    this.func_110318_g();
                }
                else if (!var1 && this.movementInput.jump) {
                    this.horseJumpPowerCounter = 0;
                    this.horseJumpPower = 0.0f;
                }
                else if (var1) {
                    ++this.horseJumpPowerCounter;
                    if (this.horseJumpPowerCounter < 10) {
                        this.horseJumpPower = this.horseJumpPowerCounter * 0.1f;
                    }
                    else {
                        this.horseJumpPower = 0.8f + 2.0f / (this.horseJumpPowerCounter - 9) * 0.1f;
                    }
                }
            }
            else {
                this.horseJumpPower = 0.0f;
            }
            super.onLivingUpdate();
            if (this.onGround && this.capabilities.isFlying) {
                this.capabilities.isFlying = false;
                this.sendPlayerAbilities();
            }
        }
        for (final IModule module : Client.getInstance().getModuleManager().getEnabledModules()) {
            module.onPostPlayerUpdate();
        }
    }
    
    public float getFOVMultiplier() {
        float var1 = 1.0f;
        if (this.capabilities.isFlying) {
            var1 *= 1.1f;
        }
        final IAttributeInstance var2 = this.getEntityAttribute(SharedMonsterAttributes.movementSpeed);
        var1 *= (float)((var2.getAttributeValue() / this.capabilities.getWalkSpeed() + 1.0) / 2.0);
        if (this.capabilities.getWalkSpeed() == 0.0f || Float.isNaN(var1) || Float.isInfinite(var1)) {
            var1 = 1.0f;
        }
        if (this.isUsingItem() && this.getItemInUse().getItem() == Items.bow) {
            final int var3 = this.getItemInUseDuration();
            float var4 = var3 / 20.0f;
            if (var4 > 1.0f) {
                var4 = 1.0f;
            }
            else {
                var4 *= var4;
            }
            var1 *= 1.0f - var4 * 0.15f;
        }
        return var1;
    }
    
    public void closeScreen() {
        super.closeScreen();
        this.mc.displayGuiScreen(null);
    }
    
    @Override
    public void func_146100_a(final TileEntity p_146100_1_) {
        if (p_146100_1_ instanceof TileEntitySign) {
            this.mc.displayGuiScreen(new GuiEditSign((TileEntitySign)p_146100_1_));
        }
        else if (p_146100_1_ instanceof TileEntityCommandBlock) {
            this.mc.displayGuiScreen(new GuiCommandBlock(((TileEntityCommandBlock)p_146100_1_).func_145993_a()));
        }
    }
    
    @Override
    public void func_146095_a(final CommandBlockLogic p_146095_1_) {
        this.mc.displayGuiScreen(new GuiCommandBlock(p_146095_1_));
    }
    
    @Override
    public void displayGUIBook(final ItemStack p_71048_1_) {
        final Item var2 = p_71048_1_.getItem();
        if (var2 == Items.written_book) {
            this.mc.displayGuiScreen(new GuiScreenBook(this, p_71048_1_, false));
        }
        else if (var2 == Items.writable_book) {
            this.mc.displayGuiScreen(new GuiScreenBook(this, p_71048_1_, true));
        }
    }
    
    @Override
    public void displayGUIChest(final IInventory p_71007_1_) {
        this.mc.displayGuiScreen(new GuiChest(this.inventory, p_71007_1_));
    }
    
    @Override
    public void func_146093_a(final TileEntityHopper p_146093_1_) {
        this.mc.displayGuiScreen(new GuiHopper(this.inventory, p_146093_1_));
    }
    
    @Override
    public void displayGUIHopperMinecart(final EntityMinecartHopper p_96125_1_) {
        this.mc.displayGuiScreen(new GuiHopper(this.inventory, p_96125_1_));
    }
    
    @Override
    public void displayGUIHorse(final EntityHorse p_110298_1_, final IInventory p_110298_2_) {
        this.mc.displayGuiScreen(new GuiScreenHorseInventory(this.inventory, p_110298_2_, p_110298_1_));
    }
    
    @Override
    public void displayGUIWorkbench(final int p_71058_1_, final int p_71058_2_, final int p_71058_3_) {
        this.mc.displayGuiScreen(new GuiCrafting(this.inventory, this.worldObj, p_71058_1_, p_71058_2_, p_71058_3_));
    }
    
    @Override
    public void displayGUIEnchantment(final int p_71002_1_, final int p_71002_2_, final int p_71002_3_, final String p_71002_4_) {
        this.mc.displayGuiScreen(new GuiEnchantment(this.inventory, this.worldObj, p_71002_1_, p_71002_2_, p_71002_3_, p_71002_4_));
    }
    
    @Override
    public void displayGUIAnvil(final int p_82244_1_, final int p_82244_2_, final int p_82244_3_) {
        this.mc.displayGuiScreen(new GuiRepair(this.inventory, this.worldObj, p_82244_1_, p_82244_2_, p_82244_3_));
    }
    
    @Override
    public void func_146101_a(final TileEntityFurnace p_146101_1_) {
        this.mc.displayGuiScreen(new GuiFurnace(this.inventory, p_146101_1_));
    }
    
    @Override
    public void func_146098_a(final TileEntityBrewingStand p_146098_1_) {
        this.mc.displayGuiScreen(new GuiBrewingStand(this.inventory, p_146098_1_));
    }
    
    @Override
    public void func_146104_a(final TileEntityBeacon p_146104_1_) {
        this.mc.displayGuiScreen(new GuiBeacon(this.inventory, p_146104_1_));
    }
    
    @Override
    public void func_146102_a(final TileEntityDispenser p_146102_1_) {
        this.mc.displayGuiScreen(new GuiDispenser(this.inventory, p_146102_1_));
    }
    
    @Override
    public void displayGUIMerchant(final IMerchant p_71030_1_, final String p_71030_2_) {
        this.mc.displayGuiScreen(new GuiMerchant(this.inventory, p_71030_1_, this.worldObj, p_71030_2_));
    }
    
    @Override
    public void onCriticalHit(final Entity p_71009_1_) {
        this.mc.effectRenderer.addEffect(new EntityCrit2FX(this.mc.theWorld, p_71009_1_));
    }
    
    @Override
    public void onEnchantmentCritical(final Entity p_71047_1_) {
        final EntityCrit2FX var2 = new EntityCrit2FX(this.mc.theWorld, p_71047_1_, "magicCrit");
        this.mc.effectRenderer.addEffect(var2);
    }
    
    @Override
    public void onItemPickup(final Entity p_71001_1_, final int p_71001_2_) {
        this.mc.effectRenderer.addEffect(new EntityPickupFX(this.mc.theWorld, p_71001_1_, this, -0.5f));
    }
    
    @Override
    public boolean isSneaking() {
        return this.movementInput.sneak && !this.sleeping;
    }
    
    public void setPlayerSPHealth(final float p_71150_1_) {
        final float var2 = this.getHealth() - p_71150_1_;
        if (var2 <= 0.0f) {
            this.setHealth(p_71150_1_);
            if (var2 < 0.0f) {
                this.hurtResistantTime = this.maxHurtResistantTime / 2;
            }
        }
        else {
            this.lastDamage = var2;
            this.setHealth(this.getHealth());
            this.hurtResistantTime = this.maxHurtResistantTime;
            this.damageEntity(DamageSource.generic, var2);
            final int n = 10;
            this.maxHurtTime = n;
            this.hurtTime = n;
        }
    }
    
    @Override
    public void addChatComponentMessage(final IChatComponent p_146105_1_) {
        this.mc.ingameGUI.getChatGUI().func_146227_a(p_146105_1_);
    }
    
    private boolean isBlockTranslucent(final int p_71153_1_, final int p_71153_2_, final int p_71153_3_) {
        return this.worldObj.getBlock(p_71153_1_, p_71153_2_, p_71153_3_).isNormalCube();
    }
    
    @Override
    protected boolean func_145771_j(final double p_145771_1_, final double p_145771_3_, final double p_145771_5_) {
        final int var7 = MathHelper.floor_double(p_145771_1_);
        final int var8 = MathHelper.floor_double(p_145771_3_);
        final int var9 = MathHelper.floor_double(p_145771_5_);
        final double var10 = p_145771_1_ - var7;
        final double var11 = p_145771_5_ - var9;
        if (this.isBlockTranslucent(var7, var8, var9) || this.isBlockTranslucent(var7, var8 + 1, var9)) {
            final boolean var12 = !this.isBlockTranslucent(var7 - 1, var8, var9) && !this.isBlockTranslucent(var7 - 1, var8 + 1, var9);
            final boolean var13 = !this.isBlockTranslucent(var7 + 1, var8, var9) && !this.isBlockTranslucent(var7 + 1, var8 + 1, var9);
            final boolean var14 = !this.isBlockTranslucent(var7, var8, var9 - 1) && !this.isBlockTranslucent(var7, var8 + 1, var9 - 1);
            final boolean var15 = !this.isBlockTranslucent(var7, var8, var9 + 1) && !this.isBlockTranslucent(var7, var8 + 1, var9 + 1);
            byte var16 = -1;
            double var17 = 9999.0;
            if (var12 && var10 < var17) {
                var17 = var10;
                var16 = 0;
            }
            if (var13 && 1.0 - var10 < var17) {
                var17 = 1.0 - var10;
                var16 = 1;
            }
            if (var14 && var11 < var17) {
                var17 = var11;
                var16 = 4;
            }
            if (var15 && 1.0 - var11 < var17) {
                var17 = 1.0 - var11;
                var16 = 5;
            }
            final float var18 = 0.1f;
            if (var16 == 0) {
                this.motionX = -var18;
            }
            if (var16 == 1) {
                this.motionX = var18;
            }
            if (var16 == 4) {
                this.motionZ = -var18;
            }
            if (var16 == 5) {
                this.motionZ = var18;
            }
        }
        return false;
    }
    
    @Override
    public void setSprinting(final boolean p_70031_1_) {
        super.setSprinting(p_70031_1_);
        this.sprintingTicksLeft = (p_70031_1_ ? 600 : 0);
    }
    
    public void setXPStats(final float p_71152_1_, final int p_71152_2_, final int p_71152_3_) {
        this.experience = p_71152_1_;
        this.experienceTotal = p_71152_2_;
        this.experienceLevel = p_71152_3_;
    }
    
    @Override
    public void addChatMessage(final IChatComponent p_145747_1_) {
        this.mc.ingameGUI.getChatGUI().func_146227_a(p_145747_1_);
    }
    
    @Override
    public boolean canCommandSenderUseCommand(final int p_70003_1_, final String p_70003_2_) {
        return p_70003_1_ <= 0;
    }
    
    @Override
    public ChunkCoordinates getPlayerCoordinates() {
        return new ChunkCoordinates(MathHelper.floor_double(this.posX + 0.5), MathHelper.floor_double(this.posY + 0.5), MathHelper.floor_double(this.posZ + 0.5));
    }
    
    @Override
    public void playSound(final String p_85030_1_, final float p_85030_2_, final float p_85030_3_) {
        this.worldObj.playSound(this.posX, this.posY - this.yOffset, this.posZ, p_85030_1_, p_85030_2_, p_85030_3_, false);
    }
    
    @Override
    public boolean isClientWorld() {
        return true;
    }
    
    public boolean isRidingHorse() {
        return this.ridingEntity != null && this.ridingEntity instanceof EntityHorse;
    }
    
    public float getHorseJumpPower() {
        return this.horseJumpPower;
    }
    
    protected void func_110318_g() {
    }
}
