package us.zonix.client.gui.component;

public interface IComponent
{
    int getX();
    
    int getY();
    
    void setPosition(final int p0, final int p1);
    
    int getWidth();
    
    int getHeight();
    
    void setWidth(final int p0);
    
    void setHeight(final int p0);
    
    void onOpen();
    
    void tick();
    
    void draw(final int p0, final int p1);
    
    void onClick(final int p0, final int p1, final int p2);
    
    default void onMouseEvent() {
    }
    
    default void onMouseRelease() {
    }
    
    default void onKeyPress(final int code, final char c) {
    }
}
