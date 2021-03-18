package net.minecraft.tileentity;

import net.minecraft.inventory.*;
import net.minecraft.world.*;

public interface IHopper extends IInventory
{
    World getWorldObj();
    
    double getXPos();
    
    double getYPos();
    
    double getZPos();
}
