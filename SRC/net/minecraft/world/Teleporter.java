package net.minecraft.world;

import net.minecraft.entity.*;
import net.minecraft.init.*;
import java.util.*;
import net.minecraft.util.*;

public class Teleporter
{
    private final WorldServer worldServerInstance;
    private final Random random;
    private final LongHashMap destinationCoordinateCache;
    private final List destinationCoordinateKeys;
    private static final String __OBFID = "CL_00000153";
    
    public Teleporter(final WorldServer p_i1963_1_) {
        this.destinationCoordinateCache = new LongHashMap();
        this.destinationCoordinateKeys = new ArrayList();
        this.worldServerInstance = p_i1963_1_;
        this.random = new Random(p_i1963_1_.getSeed());
    }
    
    public void placeInPortal(final Entity p_77185_1_, final double p_77185_2_, final double p_77185_4_, final double p_77185_6_, final float p_77185_8_) {
        if (this.worldServerInstance.provider.dimensionId != 1) {
            if (!this.placeInExistingPortal(p_77185_1_, p_77185_2_, p_77185_4_, p_77185_6_, p_77185_8_)) {
                this.makePortal(p_77185_1_);
                this.placeInExistingPortal(p_77185_1_, p_77185_2_, p_77185_4_, p_77185_6_, p_77185_8_);
            }
        }
        else {
            final int var9 = MathHelper.floor_double(p_77185_1_.posX);
            final int var10 = MathHelper.floor_double(p_77185_1_.posY) - 1;
            final int var11 = MathHelper.floor_double(p_77185_1_.posZ);
            final byte var12 = 1;
            final byte var13 = 0;
            for (int var14 = -2; var14 <= 2; ++var14) {
                for (int var15 = -2; var15 <= 2; ++var15) {
                    for (int var16 = -1; var16 < 3; ++var16) {
                        final int var17 = var9 + var15 * var12 + var14 * var13;
                        final int var18 = var10 + var16;
                        final int var19 = var11 + var15 * var13 - var14 * var12;
                        final boolean var20 = var16 < 0;
                        this.worldServerInstance.setBlock(var17, var18, var19, var20 ? Blocks.obsidian : Blocks.air);
                    }
                }
            }
            p_77185_1_.setLocationAndAngles(var9, var10, var11, p_77185_1_.rotationYaw, 0.0f);
            final double motionX = 0.0;
            p_77185_1_.motionZ = motionX;
            p_77185_1_.motionY = motionX;
            p_77185_1_.motionX = motionX;
        }
    }
    
    public boolean placeInExistingPortal(final Entity p_77184_1_, final double p_77184_2_, final double p_77184_4_, final double p_77184_6_, final float p_77184_8_) {
        final short var9 = 128;
        double var10 = -1.0;
        int var11 = 0;
        int var12 = 0;
        int var13 = 0;
        final int var14 = MathHelper.floor_double(p_77184_1_.posX);
        final int var15 = MathHelper.floor_double(p_77184_1_.posZ);
        final long var16 = ChunkCoordIntPair.chunkXZ2Int(var14, var15);
        boolean var17 = true;
        if (this.destinationCoordinateCache.containsItem(var16)) {
            final PortalPosition var18 = (PortalPosition)this.destinationCoordinateCache.getValueByKey(var16);
            var10 = 0.0;
            var11 = var18.posX;
            var12 = var18.posY;
            var13 = var18.posZ;
            var18.lastUpdateTime = this.worldServerInstance.getTotalWorldTime();
            var17 = false;
        }
        else {
            for (int var19 = var14 - var9; var19 <= var14 + var9; ++var19) {
                final double var20 = var19 + 0.5 - p_77184_1_.posX;
                for (int var21 = var15 - var9; var21 <= var15 + var9; ++var21) {
                    final double var22 = var21 + 0.5 - p_77184_1_.posZ;
                    for (int var23 = this.worldServerInstance.getActualHeight() - 1; var23 >= 0; --var23) {
                        if (this.worldServerInstance.getBlock(var19, var23, var21) == Blocks.portal) {
                            while (this.worldServerInstance.getBlock(var19, var23 - 1, var21) == Blocks.portal) {
                                --var23;
                            }
                            final double var24 = var23 + 0.5 - p_77184_1_.posY;
                            final double var25 = var20 * var20 + var24 * var24 + var22 * var22;
                            if (var10 < 0.0 || var25 < var10) {
                                var10 = var25;
                                var11 = var19;
                                var12 = var23;
                                var13 = var21;
                            }
                        }
                    }
                }
            }
        }
        if (var10 >= 0.0) {
            if (var17) {
                this.destinationCoordinateCache.add(var16, new PortalPosition(var11, var12, var13, this.worldServerInstance.getTotalWorldTime()));
                this.destinationCoordinateKeys.add(var16);
            }
            double var26 = var11 + 0.5;
            final double var27 = var12 + 0.5;
            double var24 = var13 + 0.5;
            int var28 = -1;
            if (this.worldServerInstance.getBlock(var11 - 1, var12, var13) == Blocks.portal) {
                var28 = 2;
            }
            if (this.worldServerInstance.getBlock(var11 + 1, var12, var13) == Blocks.portal) {
                var28 = 0;
            }
            if (this.worldServerInstance.getBlock(var11, var12, var13 - 1) == Blocks.portal) {
                var28 = 3;
            }
            if (this.worldServerInstance.getBlock(var11, var12, var13 + 1) == Blocks.portal) {
                var28 = 1;
            }
            final int var29 = p_77184_1_.getTeleportDirection();
            if (var28 > -1) {
                int var30 = Direction.rotateLeft[var28];
                int var31 = Direction.offsetX[var28];
                int var32 = Direction.offsetZ[var28];
                int var33 = Direction.offsetX[var30];
                int var34 = Direction.offsetZ[var30];
                boolean var35 = !this.worldServerInstance.isAirBlock(var11 + var31 + var33, var12, var13 + var32 + var34) || !this.worldServerInstance.isAirBlock(var11 + var31 + var33, var12 + 1, var13 + var32 + var34);
                boolean var36 = !this.worldServerInstance.isAirBlock(var11 + var31, var12, var13 + var32) || !this.worldServerInstance.isAirBlock(var11 + var31, var12 + 1, var13 + var32);
                if (var35 && var36) {
                    var28 = Direction.rotateOpposite[var28];
                    var30 = Direction.rotateOpposite[var30];
                    var31 = Direction.offsetX[var28];
                    var32 = Direction.offsetZ[var28];
                    var33 = Direction.offsetX[var30];
                    var34 = Direction.offsetZ[var30];
                    final int var19 = var11 - var33;
                    var26 -= var33;
                    final int var37 = var13 - var34;
                    var24 -= var34;
                    var35 = (!this.worldServerInstance.isAirBlock(var19 + var31 + var33, var12, var37 + var32 + var34) || !this.worldServerInstance.isAirBlock(var19 + var31 + var33, var12 + 1, var37 + var32 + var34));
                    var36 = (!this.worldServerInstance.isAirBlock(var19 + var31, var12, var37 + var32) || !this.worldServerInstance.isAirBlock(var19 + var31, var12 + 1, var37 + var32));
                }
                float var38 = 0.5f;
                float var39 = 0.5f;
                if (!var35 && var36) {
                    var38 = 1.0f;
                }
                else if (var35 && !var36) {
                    var38 = 0.0f;
                }
                else if (var35 && var36) {
                    var39 = 0.0f;
                }
                var26 += var33 * var38 + var39 * var31;
                var24 += var34 * var38 + var39 * var32;
                float var40 = 0.0f;
                float var41 = 0.0f;
                float var42 = 0.0f;
                float var43 = 0.0f;
                if (var28 == var29) {
                    var40 = 1.0f;
                    var41 = 1.0f;
                }
                else if (var28 == Direction.rotateOpposite[var29]) {
                    var40 = -1.0f;
                    var41 = -1.0f;
                }
                else if (var28 == Direction.rotateRight[var29]) {
                    var42 = 1.0f;
                    var43 = -1.0f;
                }
                else {
                    var42 = -1.0f;
                    var43 = 1.0f;
                }
                final double var44 = p_77184_1_.motionX;
                final double var45 = p_77184_1_.motionZ;
                p_77184_1_.motionX = var44 * var40 + var45 * var43;
                p_77184_1_.motionZ = var44 * var42 + var45 * var41;
                p_77184_1_.rotationYaw = p_77184_8_ - var29 * 90 + var28 * 90;
            }
            else {
                final double motionX = 0.0;
                p_77184_1_.motionZ = motionX;
                p_77184_1_.motionY = motionX;
                p_77184_1_.motionX = motionX;
            }
            p_77184_1_.setLocationAndAngles(var26, var27, var24, p_77184_1_.rotationYaw, p_77184_1_.rotationPitch);
            return true;
        }
        return false;
    }
    
    public boolean makePortal(final Entity p_85188_1_) {
        final byte var2 = 16;
        double var3 = -1.0;
        final int var4 = MathHelper.floor_double(p_85188_1_.posX);
        final int var5 = MathHelper.floor_double(p_85188_1_.posY);
        final int var6 = MathHelper.floor_double(p_85188_1_.posZ);
        int var7 = var4;
        int var8 = var5;
        int var9 = var6;
        int var10 = 0;
        final int var11 = this.random.nextInt(4);
        for (int var12 = var4 - var2; var12 <= var4 + var2; ++var12) {
            final double var13 = var12 + 0.5 - p_85188_1_.posX;
            for (int var14 = var6 - var2; var14 <= var6 + var2; ++var14) {
                final double var15 = var14 + 0.5 - p_85188_1_.posZ;
            Label_0433:
                for (int var16 = this.worldServerInstance.getActualHeight() - 1; var16 >= 0; --var16) {
                    if (this.worldServerInstance.isAirBlock(var12, var16, var14)) {
                        while (var16 > 0 && this.worldServerInstance.isAirBlock(var12, var16 - 1, var14)) {
                            --var16;
                        }
                        for (int var17 = var11; var17 < var11 + 4; ++var17) {
                            int var18 = var17 % 2;
                            int var19 = 1 - var18;
                            if (var17 % 4 >= 2) {
                                var18 = -var18;
                                var19 = -var19;
                            }
                            for (int var20 = 0; var20 < 3; ++var20) {
                                for (int var21 = 0; var21 < 4; ++var21) {
                                    for (int var22 = -1; var22 < 4; ++var22) {
                                        final int var23 = var12 + (var21 - 1) * var18 + var20 * var19;
                                        final int var24 = var16 + var22;
                                        final int var25 = var14 + (var21 - 1) * var19 - var20 * var18;
                                        if (var22 < 0 && !this.worldServerInstance.getBlock(var23, var24, var25).getMaterial().isSolid()) {
                                            continue Label_0433;
                                        }
                                        if (var22 >= 0 && !this.worldServerInstance.isAirBlock(var23, var24, var25)) {
                                            continue Label_0433;
                                        }
                                    }
                                }
                            }
                            final double var26 = var16 + 0.5 - p_85188_1_.posY;
                            final double var27 = var13 * var13 + var26 * var26 + var15 * var15;
                            if (var3 < 0.0 || var27 < var3) {
                                var3 = var27;
                                var7 = var12;
                                var8 = var16;
                                var9 = var14;
                                var10 = var17 % 4;
                            }
                        }
                    }
                }
            }
        }
        if (var3 < 0.0) {
            for (int var12 = var4 - var2; var12 <= var4 + var2; ++var12) {
                final double var13 = var12 + 0.5 - p_85188_1_.posX;
                for (int var14 = var6 - var2; var14 <= var6 + var2; ++var14) {
                    final double var15 = var14 + 0.5 - p_85188_1_.posZ;
                Label_0786:
                    for (int var16 = this.worldServerInstance.getActualHeight() - 1; var16 >= 0; --var16) {
                        if (this.worldServerInstance.isAirBlock(var12, var16, var14)) {
                            while (var16 > 0 && this.worldServerInstance.isAirBlock(var12, var16 - 1, var14)) {
                                --var16;
                            }
                            for (int var17 = var11; var17 < var11 + 2; ++var17) {
                                final int var18 = var17 % 2;
                                final int var19 = 1 - var18;
                                for (int var20 = 0; var20 < 4; ++var20) {
                                    for (int var21 = -1; var21 < 4; ++var21) {
                                        final int var22 = var12 + (var20 - 1) * var18;
                                        final int var23 = var16 + var21;
                                        final int var24 = var14 + (var20 - 1) * var19;
                                        if (var21 < 0 && !this.worldServerInstance.getBlock(var22, var23, var24).getMaterial().isSolid()) {
                                            continue Label_0786;
                                        }
                                        if (var21 >= 0 && !this.worldServerInstance.isAirBlock(var22, var23, var24)) {
                                            continue Label_0786;
                                        }
                                    }
                                }
                                final double var26 = var16 + 0.5 - p_85188_1_.posY;
                                final double var27 = var13 * var13 + var26 * var26 + var15 * var15;
                                if (var3 < 0.0 || var27 < var3) {
                                    var3 = var27;
                                    var7 = var12;
                                    var8 = var16;
                                    var9 = var14;
                                    var10 = var17 % 2;
                                }
                            }
                        }
                    }
                }
            }
        }
        final int var28 = var7;
        int var29 = var8;
        int var14 = var9;
        int var30 = var10 % 2;
        int var31 = 1 - var30;
        if (var10 % 4 >= 2) {
            var30 = -var30;
            var31 = -var31;
        }
        if (var3 < 0.0) {
            if (var8 < 70) {
                var8 = 70;
            }
            if (var8 > this.worldServerInstance.getActualHeight() - 10) {
                var8 = this.worldServerInstance.getActualHeight() - 10;
            }
            var29 = var8;
            for (int var16 = -1; var16 <= 1; ++var16) {
                for (int var17 = 1; var17 < 3; ++var17) {
                    for (int var18 = -1; var18 < 3; ++var18) {
                        final int var19 = var28 + (var17 - 1) * var30 + var16 * var31;
                        final int var20 = var29 + var18;
                        final int var21 = var14 + (var17 - 1) * var31 - var16 * var30;
                        final boolean var32 = var18 < 0;
                        this.worldServerInstance.setBlock(var19, var20, var21, var32 ? Blocks.obsidian : Blocks.air);
                    }
                }
            }
        }
        for (int var16 = 0; var16 < 4; ++var16) {
            for (int var17 = 0; var17 < 4; ++var17) {
                for (int var18 = -1; var18 < 4; ++var18) {
                    final int var19 = var28 + (var17 - 1) * var30;
                    final int var20 = var29 + var18;
                    final int var21 = var14 + (var17 - 1) * var31;
                    final boolean var32 = var17 == 0 || var17 == 3 || var18 == -1 || var18 == 3;
                    this.worldServerInstance.setBlock(var19, var20, var21, var32 ? Blocks.obsidian : Blocks.portal, 0, 2);
                }
            }
            for (int var17 = 0; var17 < 4; ++var17) {
                for (int var18 = -1; var18 < 4; ++var18) {
                    final int var19 = var28 + (var17 - 1) * var30;
                    final int var20 = var29 + var18;
                    final int var21 = var14 + (var17 - 1) * var31;
                    this.worldServerInstance.notifyBlocksOfNeighborChange(var19, var20, var21, this.worldServerInstance.getBlock(var19, var20, var21));
                }
            }
        }
        return true;
    }
    
    public void removeStalePortalLocations(final long p_85189_1_) {
        if (p_85189_1_ % 100L == 0L) {
            final Iterator var3 = this.destinationCoordinateKeys.iterator();
            final long var4 = p_85189_1_ - 600L;
            while (var3.hasNext()) {
                final Long var5 = var3.next();
                final PortalPosition var6 = (PortalPosition)this.destinationCoordinateCache.getValueByKey(var5);
                if (var6 == null || var6.lastUpdateTime < var4) {
                    var3.remove();
                    this.destinationCoordinateCache.remove(var5);
                }
            }
        }
    }
    
    public class PortalPosition extends ChunkCoordinates
    {
        public long lastUpdateTime;
        private static final String __OBFID = "CL_00000154";
        
        public PortalPosition(final int p_i1962_2_, final int p_i1962_3_, final int p_i1962_4_, final long p_i1962_5_) {
            super(p_i1962_2_, p_i1962_3_, p_i1962_4_);
            this.lastUpdateTime = p_i1962_5_;
        }
    }
}
