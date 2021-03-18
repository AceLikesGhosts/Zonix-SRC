package net.minecraft.server.integrated;

import net.minecraft.server.management.*;
import net.minecraft.nbt.*;
import net.minecraft.server.*;
import net.minecraft.entity.player.*;
import java.net.*;
import com.mojang.authlib.*;

public class IntegratedPlayerList extends ServerConfigurationManager
{
    private NBTTagCompound hostPlayerData;
    private static final String __OBFID = "CL_00001128";
    
    public IntegratedPlayerList(final IntegratedServer p_i1314_1_) {
        super(p_i1314_1_);
        this.func_152611_a(10);
    }
    
    @Override
    protected void writePlayerData(final EntityPlayerMP p_72391_1_) {
        if (p_72391_1_.getCommandSenderName().equals(this.getServerInstance().getServerOwner())) {
            p_72391_1_.writeToNBT(this.hostPlayerData = new NBTTagCompound());
        }
        super.writePlayerData(p_72391_1_);
    }
    
    @Override
    public String func_148542_a(final SocketAddress p_148542_1_, final GameProfile p_148542_2_) {
        return (p_148542_2_.getName().equalsIgnoreCase(this.getServerInstance().getServerOwner()) && this.func_152612_a(p_148542_2_.getName()) != null) ? "That name is already taken." : super.func_148542_a(p_148542_1_, p_148542_2_);
    }
    
    @Override
    public IntegratedServer getServerInstance() {
        return (IntegratedServer)super.getServerInstance();
    }
    
    @Override
    public NBTTagCompound getHostPlayerData() {
        return this.hostPlayerData;
    }
}
