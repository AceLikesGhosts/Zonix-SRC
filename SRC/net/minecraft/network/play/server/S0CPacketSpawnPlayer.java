package net.minecraft.network.play.server;

import com.mojang.authlib.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import net.minecraft.item.*;
import com.mojang.authlib.properties.*;
import java.io.*;
import java.util.*;
import net.minecraft.network.play.*;
import net.minecraft.network.*;

public class S0CPacketSpawnPlayer extends Packet
{
    private int field_148957_a;
    private GameProfile field_148955_b;
    private int field_148956_c;
    private int field_148953_d;
    private int field_148954_e;
    private byte field_148951_f;
    private byte field_148952_g;
    private int field_148959_h;
    private DataWatcher field_148960_i;
    private List field_148958_j;
    private static final String __OBFID = "CL_00001281";
    
    public S0CPacketSpawnPlayer() {
    }
    
    public S0CPacketSpawnPlayer(final EntityPlayer p_i45171_1_) {
        this.field_148957_a = p_i45171_1_.getEntityId();
        this.field_148955_b = p_i45171_1_.getGameProfile();
        this.field_148956_c = MathHelper.floor_double(p_i45171_1_.posX * 32.0);
        this.field_148953_d = MathHelper.floor_double(p_i45171_1_.posY * 32.0);
        this.field_148954_e = MathHelper.floor_double(p_i45171_1_.posZ * 32.0);
        this.field_148951_f = (byte)(p_i45171_1_.rotationYaw * 256.0f / 360.0f);
        this.field_148952_g = (byte)(p_i45171_1_.rotationPitch * 256.0f / 360.0f);
        final ItemStack var2 = p_i45171_1_.inventory.getCurrentItem();
        this.field_148959_h = ((var2 == null) ? 0 : Item.getIdFromItem(var2.getItem()));
        this.field_148960_i = p_i45171_1_.getDataWatcher();
    }
    
    @Override
    public void readPacketData(final PacketBuffer p_148837_1_) throws IOException {
        this.field_148957_a = p_148837_1_.readVarIntFromBuffer();
        final UUID var2 = UUID.fromString(p_148837_1_.readStringFromBuffer(36));
        this.field_148955_b = new GameProfile(var2, p_148837_1_.readStringFromBuffer(16));
        for (int var3 = p_148837_1_.readVarIntFromBuffer(), var4 = 0; var4 < var3; ++var4) {
            final String var5 = p_148837_1_.readStringFromBuffer(32767);
            final String var6 = p_148837_1_.readStringFromBuffer(32767);
            final String var7 = p_148837_1_.readStringFromBuffer(32767);
            this.field_148955_b.getProperties().put((Object)var5, (Object)new Property(var5, var6, var7));
        }
        this.field_148956_c = p_148837_1_.readInt();
        this.field_148953_d = p_148837_1_.readInt();
        this.field_148954_e = p_148837_1_.readInt();
        this.field_148951_f = p_148837_1_.readByte();
        this.field_148952_g = p_148837_1_.readByte();
        this.field_148959_h = p_148837_1_.readShort();
        this.field_148958_j = DataWatcher.readWatchedListFromPacketBuffer(p_148837_1_);
    }
    
    @Override
    public void writePacketData(final PacketBuffer p_148840_1_) throws IOException {
        p_148840_1_.writeVarIntToBuffer(this.field_148957_a);
        final UUID var2 = this.field_148955_b.getId();
        p_148840_1_.writeStringToBuffer((var2 == null) ? "" : var2.toString());
        p_148840_1_.writeStringToBuffer(this.field_148955_b.getName());
        p_148840_1_.writeVarIntToBuffer(this.field_148955_b.getProperties().size());
        for (final Property var4 : this.field_148955_b.getProperties().values()) {
            p_148840_1_.writeStringToBuffer(var4.getName());
            p_148840_1_.writeStringToBuffer(var4.getValue());
            p_148840_1_.writeStringToBuffer(var4.getSignature());
        }
        p_148840_1_.writeInt(this.field_148956_c);
        p_148840_1_.writeInt(this.field_148953_d);
        p_148840_1_.writeInt(this.field_148954_e);
        p_148840_1_.writeByte(this.field_148951_f);
        p_148840_1_.writeByte(this.field_148952_g);
        p_148840_1_.writeShort(this.field_148959_h);
        this.field_148960_i.func_151509_a(p_148840_1_);
    }
    
    public void processPacket(final INetHandlerPlayClient p_148833_1_) {
        p_148833_1_.handleSpawnPlayer(this);
    }
    
    public List func_148944_c() {
        if (this.field_148958_j == null) {
            this.field_148958_j = this.field_148960_i.getAllWatched();
        }
        return this.field_148958_j;
    }
    
    @Override
    public String serialize() {
        return String.format("id=%d, gameProfile='%s', x=%.2f, y=%.2f, z=%.2f, carried=%d", this.field_148957_a, this.field_148955_b, this.field_148956_c / 32.0f, this.field_148953_d / 32.0f, this.field_148954_e / 32.0f, this.field_148959_h);
    }
    
    public int func_148943_d() {
        return this.field_148957_a;
    }
    
    public GameProfile func_148948_e() {
        return this.field_148955_b;
    }
    
    public int func_148942_f() {
        return this.field_148956_c;
    }
    
    public int func_148949_g() {
        return this.field_148953_d;
    }
    
    public int func_148946_h() {
        return this.field_148954_e;
    }
    
    public byte func_148941_i() {
        return this.field_148951_f;
    }
    
    public byte func_148945_j() {
        return this.field_148952_g;
    }
    
    public int func_148947_k() {
        return this.field_148959_h;
    }
    
    @Override
    public void processPacket(final INetHandler p_148833_1_) {
        this.processPacket((INetHandlerPlayClient)p_148833_1_);
    }
}
