package net.minecraft.src;

import net.minecraft.entity.*;
import net.minecraft.client.renderer.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.block.*;
import java.util.*;

public class DynamicLight
{
    private Entity entity;
    private double offsetY;
    private double lastPosX;
    private double lastPosY;
    private double lastPosZ;
    private int lastLightLevel;
    private boolean underwater;
    private long timeCheckMs;
    private Set<BlockPos> setLitChunkPos;
    
    public DynamicLight(final Entity entity) {
        this.entity = null;
        this.offsetY = 0.0;
        this.lastPosX = -2.147483648E9;
        this.lastPosY = -2.147483648E9;
        this.lastPosZ = -2.147483648E9;
        this.lastLightLevel = 0;
        this.underwater = false;
        this.timeCheckMs = 0L;
        this.setLitChunkPos = new HashSet<BlockPos>();
        this.entity = entity;
        this.offsetY = entity.getEyeHeight();
    }
    
    public void update(final RenderGlobal renderGlobal) {
        if (Config.isDynamicLightsFast()) {
            final long posX = System.currentTimeMillis();
            if (posX < this.timeCheckMs + 500L) {
                return;
            }
            this.timeCheckMs = posX;
        }
        final double posX2 = this.entity.posX - 0.5;
        final double posY = this.entity.posY - 0.5 + this.offsetY;
        final double posZ = this.entity.posZ - 0.5;
        final int lightLevel = DynamicLights.getLightLevel(this.entity);
        final double dx = posX2 - this.lastPosX;
        final double dy = posY - this.lastPosY;
        final double dz = posZ - this.lastPosZ;
        final double delta = 0.1;
        if (Math.abs(dx) > delta || Math.abs(dy) > delta || Math.abs(dz) > delta || this.lastLightLevel != lightLevel) {
            this.lastPosX = posX2;
            this.lastPosY = posY;
            this.lastPosZ = posZ;
            this.lastLightLevel = lightLevel;
            this.underwater = false;
            final WorldClient world = renderGlobal.theWorld;
            if (world != null) {
                final Block setNewPos = world.getBlock(MathHelper.floor_double(posX2), MathHelper.floor_double(posY), MathHelper.floor_double(posZ));
                this.underwater = (setNewPos == Blocks.water);
            }
            final HashSet setNewPos2 = new HashSet();
            if (lightLevel > 0) {
                EnumFacing dirX = ((MathHelper.floor_double(posX2) & 0xF) >= 8) ? EnumFacing.EAST : EnumFacing.WEST;
                final EnumFacing dirY = ((MathHelper.floor_double(posY) & 0xF) >= 8) ? EnumFacing.UP : EnumFacing.DOWN;
                final EnumFacing dirZ = ((MathHelper.floor_double(posZ) & 0xF) >= 8) ? EnumFacing.SOUTH : EnumFacing.NORTH;
                dirX = this.getOpposite(dirX);
                final BlockPos pos = new BlockPos(MathHelper.floor_double(posX2), MathHelper.floor_double(posY), MathHelper.floor_double(posZ));
                final BlockPos chunkView = new BlockPos(MathHelper.bucketInt(pos.getX(), 16) * 16, MathHelper.bucketInt(pos.getY(), 16) * 16, MathHelper.bucketInt(pos.getZ(), 16) * 16);
                final BlockPos chunkX = getRenderChunk(chunkView, dirX);
                final BlockPos chunkZ = getRenderChunk(chunkView, dirZ);
                final BlockPos chunkXZ = getRenderChunk(chunkX, dirZ);
                final BlockPos chunkY = getRenderChunk(chunkView, dirY);
                final BlockPos chunkYX = getRenderChunk(chunkY, dirX);
                final BlockPos chunkYZ = getRenderChunk(chunkY, dirZ);
                final BlockPos chunkYXZ = getRenderChunk(chunkYX, dirZ);
                this.updateChunkLight(renderGlobal, chunkView, this.setLitChunkPos, setNewPos2);
                this.updateChunkLight(renderGlobal, chunkX, this.setLitChunkPos, setNewPos2);
                this.updateChunkLight(renderGlobal, chunkZ, this.setLitChunkPos, setNewPos2);
                this.updateChunkLight(renderGlobal, chunkXZ, this.setLitChunkPos, setNewPos2);
                this.updateChunkLight(renderGlobal, chunkY, this.setLitChunkPos, setNewPos2);
                this.updateChunkLight(renderGlobal, chunkYX, this.setLitChunkPos, setNewPos2);
                this.updateChunkLight(renderGlobal, chunkYZ, this.setLitChunkPos, setNewPos2);
                this.updateChunkLight(renderGlobal, chunkYXZ, this.setLitChunkPos, setNewPos2);
            }
            this.updateLitChunks(renderGlobal);
            this.setLitChunkPos = (Set<BlockPos>)setNewPos2;
        }
    }
    
    private EnumFacing getOpposite(final EnumFacing facing) {
        switch (NamelessClass363811399.$SwitchMap$net$minecraft$util$EnumFacing[facing.ordinal()]) {
            case 1: {
                return EnumFacing.UP;
            }
            case 2: {
                return EnumFacing.DOWN;
            }
            case 3: {
                return EnumFacing.SOUTH;
            }
            case 4: {
                return EnumFacing.NORTH;
            }
            case 5: {
                return EnumFacing.WEST;
            }
            case 6: {
                return EnumFacing.EAST;
            }
            default: {
                return EnumFacing.DOWN;
            }
        }
    }
    
    private static BlockPos getRenderChunk(final BlockPos pos, final EnumFacing facing) {
        return new BlockPos(pos.getX() + facing.getFrontOffsetX() * 16, pos.getY() + facing.getFrontOffsetY() * 16, pos.getZ() + facing.getFrontOffsetZ() * 16);
    }
    
    private void updateChunkLight(final RenderGlobal renderGlobal, final BlockPos pos, final Set<BlockPos> setPrevPos, final Set<BlockPos> setNewPos) {
        if (pos != null) {
            renderGlobal.markBlockForUpdate(pos.getX() + 8, pos.getY() + 8, pos.getZ() + 8);
            if (setPrevPos != null) {
                setPrevPos.remove(pos);
            }
            if (setNewPos != null) {
                setNewPos.add(pos);
            }
        }
    }
    
    public void updateLitChunks(final RenderGlobal renderGlobal) {
        for (final BlockPos posOld : this.setLitChunkPos) {
            this.updateChunkLight(renderGlobal, posOld, null, null);
        }
    }
    
    public Entity getEntity() {
        return this.entity;
    }
    
    public double getLastPosX() {
        return this.lastPosX;
    }
    
    public double getLastPosY() {
        return this.lastPosY;
    }
    
    public double getLastPosZ() {
        return this.lastPosZ;
    }
    
    public int getLastLightLevel() {
        return this.lastLightLevel;
    }
    
    public boolean isUnderwater() {
        return this.underwater;
    }
    
    public double getOffsetY() {
        return this.offsetY;
    }
    
    @Override
    public String toString() {
        return "Entity: " + this.entity + ", offsetY: " + this.offsetY;
    }
    
    static class NamelessClass363811399
    {
        static final int[] $SwitchMap$net$minecraft$util$EnumFacing;
        
        static {
            $SwitchMap$net$minecraft$util$EnumFacing = new int[EnumFacing.values().length];
            try {
                NamelessClass363811399.$SwitchMap$net$minecraft$util$EnumFacing[EnumFacing.DOWN.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                NamelessClass363811399.$SwitchMap$net$minecraft$util$EnumFacing[EnumFacing.UP.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                NamelessClass363811399.$SwitchMap$net$minecraft$util$EnumFacing[EnumFacing.NORTH.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            try {
                NamelessClass363811399.$SwitchMap$net$minecraft$util$EnumFacing[EnumFacing.SOUTH.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
            try {
                NamelessClass363811399.$SwitchMap$net$minecraft$util$EnumFacing[EnumFacing.EAST.ordinal()] = 5;
            }
            catch (NoSuchFieldError noSuchFieldError5) {}
            try {
                NamelessClass363811399.$SwitchMap$net$minecraft$util$EnumFacing[EnumFacing.WEST.ordinal()] = 6;
            }
            catch (NoSuchFieldError noSuchFieldError6) {}
        }
    }
}
