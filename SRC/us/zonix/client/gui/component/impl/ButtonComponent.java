package us.zonix.client.gui.component.impl;

import us.zonix.client.gui.component.*;
import java.beans.*;
import us.zonix.client.gui.*;
import us.zonix.client.util.*;

public abstract class ButtonComponent implements ILabelledComponent
{
    private String text;
    private int width;
    private int height;
    private int x;
    private int y;
    
    @ConstructorProperties({ "text", "width", "height", "x", "y" })
    public ButtonComponent(final String text, final int width, final int height, final int x, final int y) {
        this.text = text;
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
    }
    
    @Override
    public void setPosition(final int x, final int y) {
        this.x = x;
        this.y = y;
    }
    
    @Override
    public void draw(final int mouseX, final int mouseY) {
        final boolean hovering = mouseX > this.x && mouseX < this.x + this.width && mouseY > this.y && mouseY < this.y + this.height;
        RenderUtil.drawBorderedRoundedRect((float)this.x, (float)this.y, (float)(this.x + this.width), (float)(this.y + this.height), 1.0f, hovering ? ModScreen.HOVER_COLOR : ModScreen.NORMAL_COLOR, 452984831);
        RenderUtil.drawCenteredString(this.text, this.x + this.width / 2, this.y + this.height / 2, -16777216);
    }
    
    @Override
    public String getText() {
        return this.text;
    }
    
    @Override
    public int getWidth() {
        return this.width;
    }
    
    @Override
    public int getHeight() {
        return this.height;
    }
    
    @Override
    public int getX() {
        return this.x;
    }
    
    @Override
    public int getY() {
        return this.y;
    }
    
    @Override
    public void setText(final String text) {
        this.text = text;
    }
    
    @Override
    public void setWidth(final int width) {
        this.width = width;
    }
    
    @Override
    public void setHeight(final int height) {
        this.height = height;
    }
}
