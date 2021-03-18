package net.minecraft.client.gui.inventory;

import net.minecraft.creativetab.*;
import net.minecraft.entity.player.*;
import net.minecraft.client.settings.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.resources.*;
import net.minecraft.inventory.*;
import org.lwjgl.input.*;
import net.minecraft.enchantment.*;
import java.util.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.achievement.*;
import net.minecraft.util.*;

public class GuiContainerCreative extends InventoryEffectRenderer
{
    private static final ResourceLocation field_147061_u;
    private static InventoryBasic field_147060_v;
    private static int field_147058_w;
    private float field_147067_x;
    private boolean field_147066_y;
    private boolean field_147065_z;
    private GuiTextField field_147062_A;
    private List field_147063_B;
    private Slot field_147064_C;
    private boolean field_147057_D;
    private CreativeCrafting field_147059_E;
    private static final String __OBFID = "CL_00000752";
    
    public GuiContainerCreative(final EntityPlayer p_i1088_1_) {
        super(new ContainerCreative(p_i1088_1_));
        p_i1088_1_.openContainer = this.field_147002_h;
        this.field_146291_p = true;
        this.field_147000_g = 136;
        this.field_146999_f = 195;
    }
    
    @Override
    public void updateScreen() {
        if (!this.mc.playerController.isInCreativeMode()) {
            this.mc.displayGuiScreen(new GuiInventory(this.mc.thePlayer));
        }
    }
    
    @Override
    protected void func_146984_a(final Slot p_146984_1_, final int p_146984_2_, final int p_146984_3_, int p_146984_4_) {
        this.field_147057_D = true;
        final boolean var5 = p_146984_4_ == 1;
        p_146984_4_ = ((p_146984_2_ == -999 && p_146984_4_ == 0) ? 4 : p_146984_4_);
        if (p_146984_1_ == null && GuiContainerCreative.field_147058_w != CreativeTabs.tabInventory.getTabIndex() && p_146984_4_ != 5) {
            final InventoryPlayer var6 = this.mc.thePlayer.inventory;
            if (var6.getItemStack() != null) {
                if (p_146984_3_ == 0) {
                    this.mc.thePlayer.dropPlayerItemWithRandomChoice(var6.getItemStack(), true);
                    this.mc.playerController.sendPacketDropItem(var6.getItemStack());
                    var6.setItemStack(null);
                }
                if (p_146984_3_ == 1) {
                    final ItemStack var7 = var6.getItemStack().splitStack(1);
                    this.mc.thePlayer.dropPlayerItemWithRandomChoice(var7, true);
                    this.mc.playerController.sendPacketDropItem(var7);
                    if (var6.getItemStack().stackSize == 0) {
                        var6.setItemStack(null);
                    }
                }
            }
        }
        else if (p_146984_1_ == this.field_147064_C && var5) {
            for (int var8 = 0; var8 < this.mc.thePlayer.inventoryContainer.getInventory().size(); ++var8) {
                this.mc.playerController.sendSlotPacket(null, var8);
            }
        }
        else if (GuiContainerCreative.field_147058_w == CreativeTabs.tabInventory.getTabIndex()) {
            if (p_146984_1_ == this.field_147064_C) {
                this.mc.thePlayer.inventory.setItemStack(null);
            }
            else if (p_146984_4_ == 4 && p_146984_1_ != null && p_146984_1_.getHasStack()) {
                final ItemStack var9 = p_146984_1_.decrStackSize((p_146984_3_ == 0) ? 1 : p_146984_1_.getStack().getMaxStackSize());
                this.mc.thePlayer.dropPlayerItemWithRandomChoice(var9, true);
                this.mc.playerController.sendPacketDropItem(var9);
            }
            else if (p_146984_4_ == 4 && this.mc.thePlayer.inventory.getItemStack() != null) {
                this.mc.thePlayer.dropPlayerItemWithRandomChoice(this.mc.thePlayer.inventory.getItemStack(), true);
                this.mc.playerController.sendPacketDropItem(this.mc.thePlayer.inventory.getItemStack());
                this.mc.thePlayer.inventory.setItemStack(null);
            }
            else {
                this.mc.thePlayer.inventoryContainer.slotClick((p_146984_1_ == null) ? p_146984_2_ : ((CreativeSlot)p_146984_1_).field_148332_b.slotNumber, p_146984_3_, p_146984_4_, this.mc.thePlayer);
                this.mc.thePlayer.inventoryContainer.detectAndSendChanges();
            }
        }
        else if (p_146984_4_ != 5 && p_146984_1_.inventory == GuiContainerCreative.field_147060_v) {
            final InventoryPlayer var6 = this.mc.thePlayer.inventory;
            ItemStack var7 = var6.getItemStack();
            final ItemStack var10 = p_146984_1_.getStack();
            if (p_146984_4_ == 2) {
                if (var10 != null && p_146984_3_ >= 0 && p_146984_3_ < 9) {
                    final ItemStack var11 = var10.copy();
                    var11.stackSize = var11.getMaxStackSize();
                    this.mc.thePlayer.inventory.setInventorySlotContents(p_146984_3_, var11);
                    this.mc.thePlayer.inventoryContainer.detectAndSendChanges();
                }
                return;
            }
            if (p_146984_4_ == 3) {
                if (var6.getItemStack() == null && p_146984_1_.getHasStack()) {
                    final ItemStack var11 = p_146984_1_.getStack().copy();
                    var11.stackSize = var11.getMaxStackSize();
                    var6.setItemStack(var11);
                }
                return;
            }
            if (p_146984_4_ == 4) {
                if (var10 != null) {
                    final ItemStack var11 = var10.copy();
                    var11.stackSize = ((p_146984_3_ == 0) ? 1 : var11.getMaxStackSize());
                    this.mc.thePlayer.dropPlayerItemWithRandomChoice(var11, true);
                    this.mc.playerController.sendPacketDropItem(var11);
                }
                return;
            }
            if (var7 != null && var10 != null && var7.isItemEqual(var10)) {
                if (p_146984_3_ == 0) {
                    if (var5) {
                        var7.stackSize = var7.getMaxStackSize();
                    }
                    else if (var7.stackSize < var7.getMaxStackSize()) {
                        final ItemStack itemStack = var7;
                        ++itemStack.stackSize;
                    }
                }
                else if (var7.stackSize <= 1) {
                    var6.setItemStack(null);
                }
                else {
                    final ItemStack itemStack2 = var7;
                    --itemStack2.stackSize;
                }
            }
            else if (var10 != null && var7 == null) {
                var6.setItemStack(ItemStack.copyItemStack(var10));
                var7 = var6.getItemStack();
                if (var5) {
                    var7.stackSize = var7.getMaxStackSize();
                }
            }
            else {
                var6.setItemStack(null);
            }
        }
        else {
            this.field_147002_h.slotClick((p_146984_1_ == null) ? p_146984_2_ : p_146984_1_.slotNumber, p_146984_3_, p_146984_4_, this.mc.thePlayer);
            if (Container.func_94532_c(p_146984_3_) == 2) {
                for (int var8 = 0; var8 < 9; ++var8) {
                    this.mc.playerController.sendSlotPacket(this.field_147002_h.getSlot(45 + var8).getStack(), 36 + var8);
                }
            }
            else if (p_146984_1_ != null) {
                final ItemStack var9 = this.field_147002_h.getSlot(p_146984_1_.slotNumber).getStack();
                this.mc.playerController.sendSlotPacket(var9, p_146984_1_.slotNumber - this.field_147002_h.inventorySlots.size() + 9 + 36);
            }
        }
    }
    
    @Override
    public void initGui() {
        if (this.mc.playerController.isInCreativeMode()) {
            super.initGui();
            this.buttonList.clear();
            Keyboard.enableRepeatEvents(true);
            (this.field_147062_A = new GuiTextField(this.fontRendererObj, this.field_147003_i + 82, this.field_147009_r + 6, 89, this.fontRendererObj.FONT_HEIGHT)).func_146203_f(15);
            this.field_147062_A.func_146185_a(false);
            this.field_147062_A.func_146189_e(false);
            this.field_147062_A.func_146193_g(16777215);
            final int var1 = GuiContainerCreative.field_147058_w;
            GuiContainerCreative.field_147058_w = -1;
            this.func_147050_b(CreativeTabs.creativeTabArray[var1]);
            this.field_147059_E = new CreativeCrafting(this.mc);
            this.mc.thePlayer.inventoryContainer.addCraftingToCrafters(this.field_147059_E);
        }
        else {
            this.mc.displayGuiScreen(new GuiInventory(this.mc.thePlayer));
        }
    }
    
    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
        if (this.mc.thePlayer != null && this.mc.thePlayer.inventory != null) {
            this.mc.thePlayer.inventoryContainer.removeCraftingFromCrafters(this.field_147059_E);
        }
        Keyboard.enableRepeatEvents(false);
    }
    
    @Override
    protected void keyTyped(final char p_73869_1_, final int p_73869_2_) {
        if (GuiContainerCreative.field_147058_w != CreativeTabs.tabAllSearch.getTabIndex()) {
            if (GameSettings.isKeyDown(this.mc.gameSettings.keyBindChat)) {
                this.func_147050_b(CreativeTabs.tabAllSearch);
            }
            else {
                super.keyTyped(p_73869_1_, p_73869_2_);
            }
        }
        else {
            if (this.field_147057_D) {
                this.field_147057_D = false;
                this.field_147062_A.setText("");
            }
            if (!this.func_146983_a(p_73869_2_)) {
                if (this.field_147062_A.textboxKeyTyped(p_73869_1_, p_73869_2_)) {
                    this.func_147053_i();
                }
                else {
                    super.keyTyped(p_73869_1_, p_73869_2_);
                }
            }
        }
    }
    
    private void func_147053_i() {
        final ContainerCreative var1 = (ContainerCreative)this.field_147002_h;
        var1.field_148330_a.clear();
        for (final Item var3 : Item.itemRegistry) {
            if (var3 != null && var3.getCreativeTab() != null) {
                var3.getSubItems(var3, null, var1.field_148330_a);
            }
        }
        for (final Enchantment var7 : Enchantment.enchantmentsList) {
            if (var7 != null && var7.type != null) {
                Items.enchanted_book.func_92113_a(var7, var1.field_148330_a);
            }
        }
        final Iterator var2 = var1.field_148330_a.iterator();
        final String var8 = this.field_147062_A.getText().toLowerCase();
        while (var2.hasNext()) {
            final ItemStack var9 = var2.next();
            boolean var10 = false;
            for (final String var12 : var9.getTooltip(this.mc.thePlayer, this.mc.gameSettings.advancedItemTooltips)) {
                if (!var12.toLowerCase().contains(var8)) {
                    continue;
                }
                var10 = true;
                break;
            }
            if (!var10) {
                var2.remove();
            }
        }
        var1.func_148329_a(this.field_147067_x = 0.0f);
    }
    
    @Override
    protected void func_146979_b(final int p_146979_1_, final int p_146979_2_) {
        final CreativeTabs var3 = CreativeTabs.creativeTabArray[GuiContainerCreative.field_147058_w];
        if (var3.drawInForegroundOfTab()) {
            GL11.glDisable(3042);
            this.fontRendererObj.drawString(I18n.format(var3.getTranslatedTabLabel(), new Object[0]), 8, 6, 4210752);
        }
    }
    
    @Override
    protected void mouseClicked(final int p_73864_1_, final int p_73864_2_, final int p_73864_3_) {
        if (p_73864_3_ == 0) {
            final int var4 = p_73864_1_ - this.field_147003_i;
            final int var5 = p_73864_2_ - this.field_147009_r;
            for (final CreativeTabs var9 : CreativeTabs.creativeTabArray) {
                if (this.func_147049_a(var9, var4, var5)) {
                    return;
                }
            }
        }
        super.mouseClicked(p_73864_1_, p_73864_2_, p_73864_3_);
    }
    
    @Override
    protected void mouseMovedOrUp(final int p_146286_1_, final int p_146286_2_, final int p_146286_3_) {
        if (p_146286_3_ == 0) {
            final int var4 = p_146286_1_ - this.field_147003_i;
            final int var5 = p_146286_2_ - this.field_147009_r;
            for (final CreativeTabs var9 : CreativeTabs.creativeTabArray) {
                if (this.func_147049_a(var9, var4, var5)) {
                    this.func_147050_b(var9);
                    return;
                }
            }
        }
        super.mouseMovedOrUp(p_146286_1_, p_146286_2_, p_146286_3_);
    }
    
    private boolean func_147055_p() {
        return GuiContainerCreative.field_147058_w != CreativeTabs.tabInventory.getTabIndex() && CreativeTabs.creativeTabArray[GuiContainerCreative.field_147058_w].shouldHidePlayerInventory() && ((ContainerCreative)this.field_147002_h).func_148328_e();
    }
    
    private void func_147050_b(final CreativeTabs p_147050_1_) {
        final int var2 = GuiContainerCreative.field_147058_w;
        GuiContainerCreative.field_147058_w = p_147050_1_.getTabIndex();
        final ContainerCreative var3 = (ContainerCreative)this.field_147002_h;
        this.field_147008_s.clear();
        var3.field_148330_a.clear();
        p_147050_1_.displayAllReleventItems(var3.field_148330_a);
        if (p_147050_1_ == CreativeTabs.tabInventory) {
            final Container var4 = this.mc.thePlayer.inventoryContainer;
            if (this.field_147063_B == null) {
                this.field_147063_B = var3.inventorySlots;
            }
            var3.inventorySlots = new ArrayList();
            for (int var5 = 0; var5 < var4.inventorySlots.size(); ++var5) {
                final CreativeSlot var6 = new CreativeSlot(var4.inventorySlots.get(var5), var5);
                var3.inventorySlots.add(var6);
                if (var5 >= 5 && var5 < 9) {
                    final int var7 = var5 - 5;
                    final int var8 = var7 / 2;
                    final int var9 = var7 % 2;
                    var6.xDisplayPosition = 9 + var8 * 54;
                    var6.yDisplayPosition = 6 + var9 * 27;
                }
                else if (var5 >= 0 && var5 < 5) {
                    var6.yDisplayPosition = -2000;
                    var6.xDisplayPosition = -2000;
                }
                else if (var5 < var4.inventorySlots.size()) {
                    final int var7 = var5 - 9;
                    final int var8 = var7 % 9;
                    final int var9 = var7 / 9;
                    var6.xDisplayPosition = 9 + var8 * 18;
                    if (var5 >= 36) {
                        var6.yDisplayPosition = 112;
                    }
                    else {
                        var6.yDisplayPosition = 54 + var9 * 18;
                    }
                }
            }
            this.field_147064_C = new Slot(GuiContainerCreative.field_147060_v, 0, 173, 112);
            var3.inventorySlots.add(this.field_147064_C);
        }
        else if (var2 == CreativeTabs.tabInventory.getTabIndex()) {
            var3.inventorySlots = this.field_147063_B;
            this.field_147063_B = null;
        }
        if (this.field_147062_A != null) {
            if (p_147050_1_ == CreativeTabs.tabAllSearch) {
                this.field_147062_A.func_146189_e(true);
                this.field_147062_A.func_146205_d(false);
                this.field_147062_A.setFocused(true);
                this.field_147062_A.setText("");
                this.func_147053_i();
            }
            else {
                this.field_147062_A.func_146189_e(false);
                this.field_147062_A.func_146205_d(true);
                this.field_147062_A.setFocused(false);
            }
        }
        var3.func_148329_a(this.field_147067_x = 0.0f);
    }
    
    @Override
    public void handleMouseInput() {
        super.handleMouseInput();
        int var1 = Mouse.getEventDWheel();
        if (var1 != 0 && this.func_147055_p()) {
            final int var2 = ((ContainerCreative)this.field_147002_h).field_148330_a.size() / 9 - 5 + 1;
            if (var1 > 0) {
                var1 = 1;
            }
            if (var1 < 0) {
                var1 = -1;
            }
            this.field_147067_x -= (float)(var1 / (double)var2);
            if (this.field_147067_x < 0.0f) {
                this.field_147067_x = 0.0f;
            }
            if (this.field_147067_x > 1.0f) {
                this.field_147067_x = 1.0f;
            }
            ((ContainerCreative)this.field_147002_h).func_148329_a(this.field_147067_x);
        }
    }
    
    @Override
    public void drawScreen(final int p_73863_1_, final int p_73863_2_, final float p_73863_3_) {
        final boolean var4 = Mouse.isButtonDown(0);
        final int var5 = this.field_147003_i;
        final int var6 = this.field_147009_r;
        final int var7 = var5 + 175;
        final int var8 = var6 + 18;
        final int var9 = var7 + 14;
        final int var10 = var8 + 112;
        if (!this.field_147065_z && var4 && p_73863_1_ >= var7 && p_73863_2_ >= var8 && p_73863_1_ < var9 && p_73863_2_ < var10) {
            this.field_147066_y = this.func_147055_p();
        }
        if (!var4) {
            this.field_147066_y = false;
        }
        this.field_147065_z = var4;
        if (this.field_147066_y) {
            this.field_147067_x = (p_73863_2_ - var8 - 7.5f) / (var10 - var8 - 15.0f);
            if (this.field_147067_x < 0.0f) {
                this.field_147067_x = 0.0f;
            }
            if (this.field_147067_x > 1.0f) {
                this.field_147067_x = 1.0f;
            }
            ((ContainerCreative)this.field_147002_h).func_148329_a(this.field_147067_x);
        }
        super.drawScreen(p_73863_1_, p_73863_2_, p_73863_3_);
        for (final CreativeTabs var14 : CreativeTabs.creativeTabArray) {
            if (this.func_147052_b(var14, p_73863_1_, p_73863_2_)) {
                break;
            }
        }
        if (this.field_147064_C != null && GuiContainerCreative.field_147058_w == CreativeTabs.tabInventory.getTabIndex() && this.func_146978_c(this.field_147064_C.xDisplayPosition, this.field_147064_C.yDisplayPosition, 16, 16, p_73863_1_, p_73863_2_)) {
            this.func_146279_a(I18n.format("inventory.binSlot", new Object[0]), p_73863_1_, p_73863_2_);
        }
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glDisable(2896);
    }
    
    @Override
    protected void func_146285_a(final ItemStack p_146285_1_, final int p_146285_2_, final int p_146285_3_) {
        if (GuiContainerCreative.field_147058_w == CreativeTabs.tabAllSearch.getTabIndex()) {
            final List var4 = p_146285_1_.getTooltip(this.mc.thePlayer, this.mc.gameSettings.advancedItemTooltips);
            CreativeTabs var5 = p_146285_1_.getItem().getCreativeTab();
            if (var5 == null && p_146285_1_.getItem() == Items.enchanted_book) {
                final Map var6 = EnchantmentHelper.getEnchantments(p_146285_1_);
                if (var6.size() == 1) {
                    final Enchantment var7 = Enchantment.enchantmentsList[var6.keySet().iterator().next()];
                    for (final CreativeTabs var11 : CreativeTabs.creativeTabArray) {
                        if (var11.func_111226_a(var7.type)) {
                            var5 = var11;
                            break;
                        }
                    }
                }
            }
            if (var5 != null) {
                var4.add(1, "" + EnumChatFormatting.BOLD + EnumChatFormatting.BLUE + I18n.format(var5.getTranslatedTabLabel(), new Object[0]));
            }
            for (int var12 = 0; var12 < var4.size(); ++var12) {
                if (var12 == 0) {
                    var4.set(var12, p_146285_1_.getRarity().rarityColor + var4.get(var12));
                }
                else {
                    var4.set(var12, EnumChatFormatting.GRAY + var4.get(var12));
                }
            }
            this.func_146283_a(var4, p_146285_2_, p_146285_3_);
        }
        else {
            super.func_146285_a(p_146285_1_, p_146285_2_, p_146285_3_);
        }
    }
    
    @Override
    protected void func_146976_a(final float p_146976_1_, final int p_146976_2_, final int p_146976_3_) {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        RenderHelper.enableGUIStandardItemLighting();
        final CreativeTabs var4 = CreativeTabs.creativeTabArray[GuiContainerCreative.field_147058_w];
        for (final CreativeTabs var8 : CreativeTabs.creativeTabArray) {
            this.mc.getTextureManager().bindTexture(GuiContainerCreative.field_147061_u);
            if (var8.getTabIndex() != GuiContainerCreative.field_147058_w) {
                this.func_147051_a(var8);
            }
        }
        this.mc.getTextureManager().bindTexture(new ResourceLocation("textures/gui/container/creative_inventory/tab_" + var4.getBackgroundImageName()));
        this.drawTexturedModalRect(this.field_147003_i, this.field_147009_r, 0, 0, this.field_146999_f, this.field_147000_g);
        this.field_147062_A.drawTextBox();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        final int var9 = this.field_147003_i + 175;
        final int var6 = this.field_147009_r + 18;
        final int var7 = var6 + 112;
        this.mc.getTextureManager().bindTexture(GuiContainerCreative.field_147061_u);
        if (var4.shouldHidePlayerInventory()) {
            this.drawTexturedModalRect(var9, var6 + (int)((var7 - var6 - 17) * this.field_147067_x), 232 + (this.func_147055_p() ? 0 : 12), 0, 12, 15);
        }
        this.func_147051_a(var4);
        if (var4 == CreativeTabs.tabInventory) {
            GuiInventory.func_147046_a(this.field_147003_i + 43, this.field_147009_r + 45, 20, (float)(this.field_147003_i + 43 - p_146976_2_), (float)(this.field_147009_r + 45 - 30 - p_146976_3_), this.mc.thePlayer);
        }
    }
    
    protected boolean func_147049_a(final CreativeTabs p_147049_1_, final int p_147049_2_, final int p_147049_3_) {
        final int var4 = p_147049_1_.getTabColumn();
        int var5 = 28 * var4;
        final byte var6 = 0;
        if (var4 == 5) {
            var5 = this.field_146999_f - 28 + 2;
        }
        else if (var4 > 0) {
            var5 += var4;
        }
        int var7;
        if (p_147049_1_.isTabInFirstRow()) {
            var7 = var6 - 32;
        }
        else {
            var7 = var6 + this.field_147000_g;
        }
        return p_147049_2_ >= var5 && p_147049_2_ <= var5 + 28 && p_147049_3_ >= var7 && p_147049_3_ <= var7 + 32;
    }
    
    protected boolean func_147052_b(final CreativeTabs p_147052_1_, final int p_147052_2_, final int p_147052_3_) {
        final int var4 = p_147052_1_.getTabColumn();
        int var5 = 28 * var4;
        final byte var6 = 0;
        if (var4 == 5) {
            var5 = this.field_146999_f - 28 + 2;
        }
        else if (var4 > 0) {
            var5 += var4;
        }
        int var7;
        if (p_147052_1_.isTabInFirstRow()) {
            var7 = var6 - 32;
        }
        else {
            var7 = var6 + this.field_147000_g;
        }
        if (this.func_146978_c(var5 + 3, var7 + 3, 23, 27, p_147052_2_, p_147052_3_)) {
            this.func_146279_a(I18n.format(p_147052_1_.getTranslatedTabLabel(), new Object[0]), p_147052_2_, p_147052_3_);
            return true;
        }
        return false;
    }
    
    protected void func_147051_a(final CreativeTabs p_147051_1_) {
        final boolean var2 = p_147051_1_.getTabIndex() == GuiContainerCreative.field_147058_w;
        final boolean var3 = p_147051_1_.isTabInFirstRow();
        final int var4 = p_147051_1_.getTabColumn();
        final int var5 = var4 * 28;
        int var6 = 0;
        int var7 = this.field_147003_i + 28 * var4;
        int var8 = this.field_147009_r;
        final byte var9 = 32;
        if (var2) {
            var6 += 32;
        }
        if (var4 == 5) {
            var7 = this.field_147003_i + this.field_146999_f - 28;
        }
        else if (var4 > 0) {
            var7 += var4;
        }
        if (var3) {
            var8 -= 28;
        }
        else {
            var6 += 64;
            var8 += this.field_147000_g - 4;
        }
        GL11.glDisable(2896);
        this.drawTexturedModalRect(var7, var8, var5, var6, 28, var9);
        this.zLevel = 100.0f;
        GuiContainerCreative.itemRender.zLevel = 100.0f;
        var7 += 6;
        var8 += 8 + (var3 ? 1 : -1);
        GL11.glEnable(2896);
        GL11.glEnable(32826);
        final ItemStack var10 = p_147051_1_.getIconItemStack();
        GuiContainerCreative.itemRender.renderItemAndEffectIntoGUI(this.fontRendererObj, this.mc.getTextureManager(), var10, var7, var8);
        GuiContainerCreative.itemRender.renderItemOverlayIntoGUI(this.fontRendererObj, this.mc.getTextureManager(), var10, var7, var8);
        GL11.glDisable(2896);
        GuiContainerCreative.itemRender.zLevel = 0.0f;
        this.zLevel = 0.0f;
    }
    
    @Override
    protected void actionPerformed(final GuiButton p_146284_1_) {
        if (p_146284_1_.id == 0) {
            this.mc.displayGuiScreen(new GuiAchievements(this, this.mc.thePlayer.func_146107_m()));
        }
        if (p_146284_1_.id == 1) {
            this.mc.displayGuiScreen(new GuiStats(this, this.mc.thePlayer.func_146107_m()));
        }
    }
    
    public int func_147056_g() {
        return GuiContainerCreative.field_147058_w;
    }
    
    static {
        field_147061_u = new ResourceLocation("textures/gui/container/creative_inventory/tabs.png");
        GuiContainerCreative.field_147060_v = new InventoryBasic("tmp", true, 45);
        GuiContainerCreative.field_147058_w = CreativeTabs.tabBlock.getTabIndex();
    }
    
    static class ContainerCreative extends Container
    {
        public List field_148330_a;
        private static final String __OBFID = "CL_00000753";
        
        public ContainerCreative(final EntityPlayer p_i1086_1_) {
            this.field_148330_a = new ArrayList();
            final InventoryPlayer var2 = p_i1086_1_.inventory;
            for (int var3 = 0; var3 < 5; ++var3) {
                for (int var4 = 0; var4 < 9; ++var4) {
                    this.addSlotToContainer(new Slot(GuiContainerCreative.field_147060_v, var3 * 9 + var4, 9 + var4 * 18, 18 + var3 * 18));
                }
            }
            for (int var3 = 0; var3 < 9; ++var3) {
                this.addSlotToContainer(new Slot(var2, var3, 9 + var3 * 18, 112));
            }
            this.func_148329_a(0.0f);
        }
        
        @Override
        public boolean canInteractWith(final EntityPlayer p_75145_1_) {
            return true;
        }
        
        public void func_148329_a(final float p_148329_1_) {
            final int var2 = this.field_148330_a.size() / 9 - 5 + 1;
            int var3 = (int)(p_148329_1_ * var2 + 0.5);
            if (var3 < 0) {
                var3 = 0;
            }
            for (int var4 = 0; var4 < 5; ++var4) {
                for (int var5 = 0; var5 < 9; ++var5) {
                    final int var6 = var5 + (var4 + var3) * 9;
                    if (var6 >= 0 && var6 < this.field_148330_a.size()) {
                        GuiContainerCreative.field_147060_v.setInventorySlotContents(var5 + var4 * 9, this.field_148330_a.get(var6));
                    }
                    else {
                        GuiContainerCreative.field_147060_v.setInventorySlotContents(var5 + var4 * 9, null);
                    }
                }
            }
        }
        
        public boolean func_148328_e() {
            return this.field_148330_a.size() > 45;
        }
        
        @Override
        protected void retrySlotClick(final int p_75133_1_, final int p_75133_2_, final boolean p_75133_3_, final EntityPlayer p_75133_4_) {
        }
        
        @Override
        public ItemStack transferStackInSlot(final EntityPlayer p_82846_1_, final int p_82846_2_) {
            if (p_82846_2_ >= this.inventorySlots.size() - 9 && p_82846_2_ < this.inventorySlots.size()) {
                final Slot var3 = this.inventorySlots.get(p_82846_2_);
                if (var3 != null && var3.getHasStack()) {
                    var3.putStack(null);
                }
            }
            return null;
        }
        
        @Override
        public boolean func_94530_a(final ItemStack p_94530_1_, final Slot p_94530_2_) {
            return p_94530_2_.yDisplayPosition > 90;
        }
        
        @Override
        public boolean canDragIntoSlot(final Slot p_94531_1_) {
            return p_94531_1_.inventory instanceof InventoryPlayer || (p_94531_1_.yDisplayPosition > 90 && p_94531_1_.xDisplayPosition <= 162);
        }
    }
    
    class CreativeSlot extends Slot
    {
        private final Slot field_148332_b;
        private static final String __OBFID = "CL_00000754";
        
        public CreativeSlot(final Slot p_i46313_2_, final int p_i46313_3_) {
            super(p_i46313_2_.inventory, p_i46313_3_, 0, 0);
            this.field_148332_b = p_i46313_2_;
        }
        
        @Override
        public void onPickupFromSlot(final EntityPlayer p_82870_1_, final ItemStack p_82870_2_) {
            this.field_148332_b.onPickupFromSlot(p_82870_1_, p_82870_2_);
        }
        
        @Override
        public boolean isItemValid(final ItemStack p_75214_1_) {
            return this.field_148332_b.isItemValid(p_75214_1_);
        }
        
        @Override
        public ItemStack getStack() {
            return this.field_148332_b.getStack();
        }
        
        @Override
        public boolean getHasStack() {
            return this.field_148332_b.getHasStack();
        }
        
        @Override
        public void putStack(final ItemStack p_75215_1_) {
            this.field_148332_b.putStack(p_75215_1_);
        }
        
        @Override
        public void onSlotChanged() {
            this.field_148332_b.onSlotChanged();
        }
        
        @Override
        public int getSlotStackLimit() {
            return this.field_148332_b.getSlotStackLimit();
        }
        
        @Override
        public IIcon getBackgroundIconIndex() {
            return this.field_148332_b.getBackgroundIconIndex();
        }
        
        @Override
        public ItemStack decrStackSize(final int p_75209_1_) {
            return this.field_148332_b.decrStackSize(p_75209_1_);
        }
        
        @Override
        public boolean isSlotInInventory(final IInventory p_75217_1_, final int p_75217_2_) {
            return this.field_148332_b.isSlotInInventory(p_75217_1_, p_75217_2_);
        }
    }
}
