package us.zonix.client.gui;

import us.zonix.client.gui.component.*;
import us.zonix.client.gui.component.impl.menu.*;
import java.util.*;
import net.minecraft.client.gui.*;
import us.zonix.client.util.*;
import us.zonix.client.*;
import us.zonix.client.module.*;
import org.lwjgl.opengl.*;
import us.zonix.client.module.impl.*;
import java.awt.*;
import java.beans.*;

public final class ModScreen extends GuiScreen
{
    public static final int NORMAL_COLOR;
    public static final int HOVER_COLOR;
    private final Set<IComponent> components;
    private DragMod dragging;
    private boolean open;
    
    public ModScreen() {
        this.components = new HashSet<IComponent>();
    }
    
    private void addButtons() {
        this.components.add(new MenuComponent());
    }
    
    @Override
    public void initGui() {
        this.open = true;
        final Iterator<IComponent> iterator;
        IComponent component;
        new Thread(() -> {
            while (this.open) {
                this.components.iterator();
                while (iterator.hasNext()) {
                    component = iterator.next();
                    component.tick();
                }
                try {
                    Thread.sleep(10L);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return;
        }).start();
        this.mc.entityRenderer.setBlur(true);
        if (this.components.isEmpty()) {
            this.addButtons();
        }
        for (final IComponent component2 : this.components) {
            component2.onOpen();
        }
    }
    
    @Override
    public void onGuiClosed() {
        this.open = false;
        this.mc.entityRenderer.setBlur(false);
    }
    
    @Override
    protected void mouseMovedOrUp(final int mouseX, final int mouseY, final int button) {
        this.dragging = null;
        this.components.forEach(IComponent::onMouseRelease);
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        final ScaledResolution resolution = new ScaledResolution(this.mc);
        if (this.dragging != null) {
            final float pageWidth = (float)resolution.getScaledWidth();
            final float pageHeight = (float)resolution.getScaledHeight();
            RenderUtil.drawRect(0.0f, 2.0f, pageWidth, 3.0f, -287162561);
            RenderUtil.drawRect(2.0f, 0.0f, 3.0f, pageHeight, -287162561);
            RenderUtil.drawRect(pageWidth - 2.0f, 0.0f, pageWidth - 3.0f, pageHeight, -287162561);
            RenderUtil.drawRect(0.0f, pageHeight - 2.0f, pageWidth, pageHeight - 3.0f, -287162561);
            RenderUtil.drawRect(0.0f, pageHeight / 2.0f - 0.5f, pageWidth, pageHeight / 2.0f + 0.5f, -287162561);
            RenderUtil.drawRect(pageWidth / 2.0f - 0.5f, 0.0f, pageWidth / 2.0f + 0.5f, pageHeight, -287162561);
        }
        for (final IModule module : Client.getInstance().getModuleManager().getEnabledModules()) {
            module.renderPreview();
            GL11.glPushMatrix();
            final boolean mouseOver = this.isMouseOver(module, mouseX, mouseY);
            final int borderColor = mouseOver ? ModScreen.HOVER_COLOR : ModScreen.NORMAL_COLOR;
            RenderUtil.drawBorderedRect(module.getX(), module.getY(), module.getX() + module.getWidth(), module.getY() + module.getHeight(), 1.0f, borderColor, 452984831);
            GL11.glPopMatrix();
        }
        if (this.dragging != null) {
            float setX = (float)(mouseX - this.dragging.x);
            float setY = (float)(mouseY - this.dragging.y);
            if (mouseX - this.dragging.module.getX() != this.dragging.x || mouseY - this.dragging.module.getY() != this.dragging.y) {
                this.dragging.moved = true;
            }
            final int height = this.dragging.module.getHeight();
            final int width = this.dragging.module.getWidth();
            if (setX < 2.0f) {
                setX = 2.0f;
            }
            else if (setX + width > resolution.getScaledWidth() - 2) {
                setX = (float)(resolution.getScaledWidth() - width - 2);
            }
            if (setY < 2.0f) {
                setY = 2.0f;
            }
            else if (setY + height > resolution.getScaledHeight() - 2) {
                setY = (float)(resolution.getScaledHeight() - height - 2);
            }
            this.dragging.module.setX(setX);
            this.dragging.module.setY(setY);
            for (final IModule module2 : Client.getInstance().getModuleManager().getEnabledModules()) {
                if (module2 == this.dragging.module) {
                    continue;
                }
                if (module2.getWidth() == 0) {
                    continue;
                }
                if (module2.getHeight() == 0) {
                    continue;
                }
                this.snapToModule(module2);
            }
            this.snapToGuideLines(resolution);
        }
        if (this.dragging == null) {
            for (final IComponent component : this.components) {
                component.draw(mouseX, mouseY);
            }
        }
    }
    
    @Override
    public void handleMouseInput() {
        super.handleMouseInput();
        if (this.dragging == null) {
            this.components.forEach(IComponent::onMouseEvent);
        }
    }
    
    @Override
    protected void keyTyped(final char c, final int key) {
        super.keyTyped(c, key);
        this.components.forEach(iComponent -> iComponent.onKeyPress(key, c));
    }
    
    @Override
    protected void mouseClicked(final int mouseX, final int mouseY, final int button) {
        if (this.dragging == null) {
            this.components.forEach(component -> {
                if (component instanceof MenuComponent) {
                    component.onClick(mouseX, mouseY, button);
                    return;
                }
                else {
                    if (mouseX > component.getX() && mouseX < component.getX() + component.getWidth() && mouseY > component.getY() && mouseY < component.getY() + component.getHeight()) {
                        component.onClick(mouseX, mouseY, button);
                    }
                    return;
                }
            });
        }
        for (final IModule module : Client.getInstance().getModuleManager().getEnabledModules()) {
            if (module instanceof ZansMinimap) {
                continue;
            }
            if (!this.isMouseOver(module, mouseX, mouseY)) {
                continue;
            }
            if (button != 1) {
                final int x = (int)(mouseX - module.getX());
                final int y = (int)(mouseY - module.getY());
                this.dragging = new DragMod(module, x, y);
                break;
            }
            if (module.getSettingMap().size() > 1) {
                final MenuComponent menuComponent = (MenuComponent)this.components.toArray(new IComponent[0])[0];
                if (menuComponent.getMenuType() == MenuComponent.EnumMenuType.MODS) {
                    menuComponent.setScrollAmount(0);
                    menuComponent.setSwitchTime(0);
                }
                menuComponent.setMenuType(MenuComponent.EnumMenuType.MOD);
                menuComponent.setEditing(module);
                break;
            }
            break;
        }
    }
    
    private boolean isMouseOver(final IModule module, final int mouseX, final int mouseY) {
        final float minX = module.getX();
        final float minY = module.getY();
        final float maxX = minX + module.getWidth();
        final float maxY = minY + module.getHeight();
        return mouseX > minX && mouseY > minY && mouseX < maxX && mouseY < maxY;
    }
    
    private void snapToModule(final IModule module) {
        final IModule dragging = this.dragging.module;
        final float minToMinX = module.getX() - dragging.getX();
        final float maxToMaxX = module.getX() + module.getWidth() - (dragging.getX() + dragging.getWidth());
        final float maxToMinX = module.getX() + module.getWidth() - dragging.getX();
        final float minToMaxX = module.getX() - (dragging.getX() + dragging.getWidth());
        final float minToMinY = module.getY() - dragging.getY();
        final float maxToMaxY = module.getY() + module.getHeight() - (dragging.getY() + dragging.getHeight());
        final float maxToMinY = module.getY() + module.getHeight() - dragging.getY();
        final float minToMaxY = module.getY() - (dragging.getY() + dragging.getHeight());
        boolean xSnap = false;
        boolean ySnap = false;
        if (minToMinX >= -2.0f && minToMinX <= 2.0f) {
            dragging.setX(dragging.getX() + minToMinX);
            xSnap = true;
        }
        if (maxToMaxX >= -2.0f && maxToMaxX <= 2.0f && !xSnap) {
            dragging.setX(dragging.getX() + maxToMaxX);
            xSnap = true;
        }
        if (minToMaxX >= -2.0f && minToMaxX <= 2.0f && !xSnap) {
            dragging.setX(dragging.getX() + minToMaxX);
            xSnap = true;
        }
        if (maxToMinX >= -2.0f && maxToMinX <= 2.0f && !xSnap) {
            dragging.setX(dragging.getX() + maxToMinX);
        }
        if (minToMinY >= -2.0f && minToMinY <= 2.0f) {
            dragging.setY(dragging.getY() + minToMinY);
            ySnap = true;
        }
        if (maxToMaxY >= -2.0f && maxToMaxY <= 2.0f && !ySnap) {
            dragging.setY(dragging.getY() + maxToMaxY);
            ySnap = true;
        }
        if (minToMaxY >= -2.0f && minToMaxY <= 2.0f && !ySnap) {
            dragging.setY(dragging.getY() + minToMaxY);
            ySnap = true;
        }
        if (maxToMinY >= -2.0f && maxToMinY <= 2.0f && !ySnap) {
            dragging.setY(dragging.getY() + maxToMinY);
        }
    }
    
    private void snapToGuideLines(final ScaledResolution resolution) {
        final IModule dragging = this.dragging.module;
        final float height = (float)(resolution.getScaledHeight() / 2);
        final float width = (float)(resolution.getScaledWidth() / 2);
        final float draggingMinX = dragging.getX();
        final float draggingMaxX = draggingMinX + this.dragging.module.getWidth();
        final float draggingHalfX = draggingMinX + this.dragging.module.getWidth() / 2;
        final float draggingMinY = dragging.getY();
        final float draggingMaxY = draggingMinY + this.dragging.module.getHeight();
        final float draggingHalfY = draggingMinY + this.dragging.module.getHeight() / 2;
        if (this.checkBounds(draggingMinX, width)) {
            dragging.setX(width);
        }
        if (this.checkBounds(draggingMinY, height)) {
            dragging.setY(height);
        }
        if (this.checkBounds(draggingMaxX, width)) {
            dragging.setX(width - dragging.getWidth());
        }
        if (this.checkBounds(draggingMaxY, height)) {
            dragging.setY(height - dragging.getHeight());
        }
        if (this.checkBounds(draggingHalfX, width)) {
            dragging.setX(width - dragging.getWidth() / 2);
        }
        if (this.checkBounds(draggingHalfY, height)) {
            dragging.setY(height - dragging.getHeight() / 2);
        }
    }
    
    private boolean checkBounds(final float f1, final float f2) {
        return f1 >= f2 - 2.0f && f1 <= f2 + 2.0f;
    }
    
    static {
        NORMAL_COLOR = new Color(169, 169, 169, 200).getRGB();
        HOVER_COLOR = new Color(255, 0, 117, 200).getRGB();
    }
    
    private final class DragMod
    {
        private final IModule module;
        private boolean moved;
        private int x;
        private int y;
        
        @ConstructorProperties({ "module", "x", "y" })
        public DragMod(final IModule module, final int x, final int y) {
            this.module = module;
            this.x = x;
            this.y = y;
        }
    }
}
