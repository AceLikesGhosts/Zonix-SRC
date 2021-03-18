package net.minecraft.world.gen.structure;

import java.util.*;
import net.minecraft.util.*;
import net.minecraft.nbt.*;
import net.minecraft.block.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.tileentity.*;
import net.minecraft.world.*;

public class StructureStrongholdPieces
{
    private static final PieceWeight[] pieceWeightArray;
    private static List structurePieceList;
    private static Class strongComponentType;
    static int totalWeight;
    private static final Stones strongholdStones;
    private static final String __OBFID = "CL_00000483";
    
    public static void func_143046_a() {
        MapGenStructureIO.func_143031_a(ChestCorridor.class, "SHCC");
        MapGenStructureIO.func_143031_a(Corridor.class, "SHFC");
        MapGenStructureIO.func_143031_a(Crossing.class, "SH5C");
        MapGenStructureIO.func_143031_a(LeftTurn.class, "SHLT");
        MapGenStructureIO.func_143031_a(Library.class, "SHLi");
        MapGenStructureIO.func_143031_a(PortalRoom.class, "SHPR");
        MapGenStructureIO.func_143031_a(Prison.class, "SHPH");
        MapGenStructureIO.func_143031_a(RightTurn.class, "SHRT");
        MapGenStructureIO.func_143031_a(RoomCrossing.class, "SHRC");
        MapGenStructureIO.func_143031_a(Stairs.class, "SHSD");
        MapGenStructureIO.func_143031_a(Stairs2.class, "SHStart");
        MapGenStructureIO.func_143031_a(Straight.class, "SHS");
        MapGenStructureIO.func_143031_a(StairsStraight.class, "SHSSD");
    }
    
    public static void prepareStructurePieces() {
        StructureStrongholdPieces.structurePieceList = new ArrayList();
        for (final PieceWeight var4 : StructureStrongholdPieces.pieceWeightArray) {
            var4.instancesSpawned = 0;
            StructureStrongholdPieces.structurePieceList.add(var4);
        }
        StructureStrongholdPieces.strongComponentType = null;
    }
    
    private static boolean canAddStructurePieces() {
        boolean var0 = false;
        StructureStrongholdPieces.totalWeight = 0;
        for (final PieceWeight var3 : StructureStrongholdPieces.structurePieceList) {
            if (var3.instancesLimit > 0 && var3.instancesSpawned < var3.instancesLimit) {
                var0 = true;
            }
            StructureStrongholdPieces.totalWeight += var3.pieceWeight;
        }
        return var0;
    }
    
    private static Stronghold getStrongholdComponentFromWeightedPiece(final Class p_75200_0_, final List p_75200_1_, final Random p_75200_2_, final int p_75200_3_, final int p_75200_4_, final int p_75200_5_, final int p_75200_6_, final int p_75200_7_) {
        Object var8 = null;
        if (p_75200_0_ == Straight.class) {
            var8 = Straight.findValidPlacement(p_75200_1_, p_75200_2_, p_75200_3_, p_75200_4_, p_75200_5_, p_75200_6_, p_75200_7_);
        }
        else if (p_75200_0_ == Prison.class) {
            var8 = Prison.findValidPlacement(p_75200_1_, p_75200_2_, p_75200_3_, p_75200_4_, p_75200_5_, p_75200_6_, p_75200_7_);
        }
        else if (p_75200_0_ == LeftTurn.class) {
            var8 = LeftTurn.findValidPlacement(p_75200_1_, p_75200_2_, p_75200_3_, p_75200_4_, p_75200_5_, p_75200_6_, p_75200_7_);
        }
        else if (p_75200_0_ == RightTurn.class) {
            var8 = LeftTurn.findValidPlacement(p_75200_1_, p_75200_2_, p_75200_3_, p_75200_4_, p_75200_5_, p_75200_6_, p_75200_7_);
        }
        else if (p_75200_0_ == RoomCrossing.class) {
            var8 = RoomCrossing.findValidPlacement(p_75200_1_, p_75200_2_, p_75200_3_, p_75200_4_, p_75200_5_, p_75200_6_, p_75200_7_);
        }
        else if (p_75200_0_ == StairsStraight.class) {
            var8 = StairsStraight.findValidPlacement(p_75200_1_, p_75200_2_, p_75200_3_, p_75200_4_, p_75200_5_, p_75200_6_, p_75200_7_);
        }
        else if (p_75200_0_ == Stairs.class) {
            var8 = Stairs.getStrongholdStairsComponent(p_75200_1_, p_75200_2_, p_75200_3_, p_75200_4_, p_75200_5_, p_75200_6_, p_75200_7_);
        }
        else if (p_75200_0_ == Crossing.class) {
            var8 = Crossing.findValidPlacement(p_75200_1_, p_75200_2_, p_75200_3_, p_75200_4_, p_75200_5_, p_75200_6_, p_75200_7_);
        }
        else if (p_75200_0_ == ChestCorridor.class) {
            var8 = ChestCorridor.findValidPlacement(p_75200_1_, p_75200_2_, p_75200_3_, p_75200_4_, p_75200_5_, p_75200_6_, p_75200_7_);
        }
        else if (p_75200_0_ == Library.class) {
            var8 = Library.findValidPlacement(p_75200_1_, p_75200_2_, p_75200_3_, p_75200_4_, p_75200_5_, p_75200_6_, p_75200_7_);
        }
        else if (p_75200_0_ == PortalRoom.class) {
            var8 = PortalRoom.findValidPlacement(p_75200_1_, p_75200_2_, p_75200_3_, p_75200_4_, p_75200_5_, p_75200_6_, p_75200_7_);
        }
        return (Stronghold)var8;
    }
    
    private static Stronghold getNextComponent(final Stairs2 p_75201_0_, final List p_75201_1_, final Random p_75201_2_, final int p_75201_3_, final int p_75201_4_, final int p_75201_5_, final int p_75201_6_, final int p_75201_7_) {
        if (!canAddStructurePieces()) {
            return null;
        }
        if (StructureStrongholdPieces.strongComponentType != null) {
            final Stronghold var8 = getStrongholdComponentFromWeightedPiece(StructureStrongholdPieces.strongComponentType, p_75201_1_, p_75201_2_, p_75201_3_, p_75201_4_, p_75201_5_, p_75201_6_, p_75201_7_);
            StructureStrongholdPieces.strongComponentType = null;
            if (var8 != null) {
                return var8;
            }
        }
        int var9 = 0;
        while (var9 < 5) {
            ++var9;
            int var10 = p_75201_2_.nextInt(StructureStrongholdPieces.totalWeight);
            for (final PieceWeight var12 : StructureStrongholdPieces.structurePieceList) {
                var10 -= var12.pieceWeight;
                if (var10 < 0) {
                    if (!var12.canSpawnMoreStructuresOfType(p_75201_7_)) {
                        break;
                    }
                    if (var12 == p_75201_0_.strongholdPieceWeight) {
                        break;
                    }
                    final Stronghold var13 = getStrongholdComponentFromWeightedPiece(var12.pieceClass, p_75201_1_, p_75201_2_, p_75201_3_, p_75201_4_, p_75201_5_, p_75201_6_, p_75201_7_);
                    if (var13 != null) {
                        final PieceWeight pieceWeight = var12;
                        ++pieceWeight.instancesSpawned;
                        p_75201_0_.strongholdPieceWeight = var12;
                        if (!var12.canSpawnMoreStructures()) {
                            StructureStrongholdPieces.structurePieceList.remove(var12);
                        }
                        return var13;
                    }
                    continue;
                }
            }
        }
        final StructureBoundingBox var14 = Corridor.func_74992_a(p_75201_1_, p_75201_2_, p_75201_3_, p_75201_4_, p_75201_5_, p_75201_6_);
        if (var14 != null && var14.minY > 1) {
            return new Corridor(p_75201_7_, p_75201_2_, var14, p_75201_6_);
        }
        return null;
    }
    
    private static StructureComponent getNextValidComponent(final Stairs2 p_75196_0_, final List p_75196_1_, final Random p_75196_2_, final int p_75196_3_, final int p_75196_4_, final int p_75196_5_, final int p_75196_6_, final int p_75196_7_) {
        if (p_75196_7_ > 50) {
            return null;
        }
        if (Math.abs(p_75196_3_ - p_75196_0_.getBoundingBox().minX) <= 112 && Math.abs(p_75196_5_ - p_75196_0_.getBoundingBox().minZ) <= 112) {
            final Stronghold var8 = getNextComponent(p_75196_0_, p_75196_1_, p_75196_2_, p_75196_3_, p_75196_4_, p_75196_5_, p_75196_6_, p_75196_7_ + 1);
            if (var8 != null) {
                p_75196_1_.add(var8);
                p_75196_0_.field_75026_c.add(var8);
            }
            return var8;
        }
        return null;
    }
    
    static {
        pieceWeightArray = new PieceWeight[] { new PieceWeight(Straight.class, 40, 0), new PieceWeight(Prison.class, 5, 5), new PieceWeight(LeftTurn.class, 20, 0), new PieceWeight(RightTurn.class, 20, 0), new PieceWeight(RoomCrossing.class, 10, 6), new PieceWeight(StairsStraight.class, 5, 5), new PieceWeight(Stairs.class, 5, 5), new PieceWeight(Crossing.class, 5, 4), new PieceWeight(ChestCorridor.class, 5, 4), new PieceWeight(Library.class, 10, 2) {
                private static final String __OBFID = "CL_00000484";
                
                @Override
                public boolean canSpawnMoreStructuresOfType(final int p_75189_1_) {
                    return super.canSpawnMoreStructuresOfType(p_75189_1_) && p_75189_1_ > 4;
                }
            }, new PieceWeight(PortalRoom.class, 20, 1) {
                private static final String __OBFID = "CL_00000485";
                
                @Override
                public boolean canSpawnMoreStructuresOfType(final int p_75189_1_) {
                    return super.canSpawnMoreStructuresOfType(p_75189_1_) && p_75189_1_ > 5;
                }
            } };
        strongholdStones = new Stones(null);
    }
    
    public static class ChestCorridor extends Stronghold
    {
        private static final WeightedRandomChestContent[] strongholdChestContents;
        private boolean hasMadeChest;
        private static final String __OBFID = "CL_00000487";
        
        public ChestCorridor() {
        }
        
        public ChestCorridor(final int p_i2071_1_, final Random p_i2071_2_, final StructureBoundingBox p_i2071_3_, final int p_i2071_4_) {
            super(p_i2071_1_);
            this.coordBaseMode = p_i2071_4_;
            this.field_143013_d = this.getRandomDoor(p_i2071_2_);
            this.boundingBox = p_i2071_3_;
        }
        
        @Override
        protected void func_143012_a(final NBTTagCompound p_143012_1_) {
            super.func_143012_a(p_143012_1_);
            p_143012_1_.setBoolean("Chest", this.hasMadeChest);
        }
        
        @Override
        protected void func_143011_b(final NBTTagCompound p_143011_1_) {
            super.func_143011_b(p_143011_1_);
            this.hasMadeChest = p_143011_1_.getBoolean("Chest");
        }
        
        @Override
        public void buildComponent(final StructureComponent p_74861_1_, final List p_74861_2_, final Random p_74861_3_) {
            this.getNextComponentNormal((Stairs2)p_74861_1_, p_74861_2_, p_74861_3_, 1, 1);
        }
        
        public static ChestCorridor findValidPlacement(final List p_75000_0_, final Random p_75000_1_, final int p_75000_2_, final int p_75000_3_, final int p_75000_4_, final int p_75000_5_, final int p_75000_6_) {
            final StructureBoundingBox var7 = StructureBoundingBox.getComponentToAddBoundingBox(p_75000_2_, p_75000_3_, p_75000_4_, -1, -1, 0, 5, 5, 7, p_75000_5_);
            return (Stronghold.canStrongholdGoDeeper(var7) && StructureComponent.findIntersecting(p_75000_0_, var7) == null) ? new ChestCorridor(p_75000_6_, p_75000_1_, var7, p_75000_5_) : null;
        }
        
        @Override
        public boolean addComponentParts(final World p_74875_1_, final Random p_74875_2_, final StructureBoundingBox p_74875_3_) {
            if (this.isLiquidInStructureBoundingBox(p_74875_1_, p_74875_3_)) {
                return false;
            }
            this.fillWithRandomizedBlocks(p_74875_1_, p_74875_3_, 0, 0, 0, 4, 4, 6, true, p_74875_2_, StructureStrongholdPieces.strongholdStones);
            this.placeDoor(p_74875_1_, p_74875_2_, p_74875_3_, this.field_143013_d, 1, 1, 0);
            this.placeDoor(p_74875_1_, p_74875_2_, p_74875_3_, Door.OPENING, 1, 1, 6);
            this.func_151549_a(p_74875_1_, p_74875_3_, 3, 1, 2, 3, 1, 4, Blocks.stonebrick, Blocks.stonebrick, false);
            this.func_151550_a(p_74875_1_, Blocks.stone_slab, 5, 3, 1, 1, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.stone_slab, 5, 3, 1, 5, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.stone_slab, 5, 3, 2, 2, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.stone_slab, 5, 3, 2, 4, p_74875_3_);
            for (int var4 = 2; var4 <= 4; ++var4) {
                this.func_151550_a(p_74875_1_, Blocks.stone_slab, 5, 2, 1, var4, p_74875_3_);
            }
            if (!this.hasMadeChest) {
                final int var4 = this.getYWithOffset(2);
                final int var5 = this.getXWithOffset(3, 3);
                final int var6 = this.getZWithOffset(3, 3);
                if (p_74875_3_.isVecInside(var5, var4, var6)) {
                    this.hasMadeChest = true;
                    this.generateStructureChestContents(p_74875_1_, p_74875_3_, p_74875_2_, 3, 2, 3, WeightedRandomChestContent.func_92080_a(ChestCorridor.strongholdChestContents, Items.enchanted_book.func_92114_b(p_74875_2_)), 2 + p_74875_2_.nextInt(2));
                }
            }
            return true;
        }
        
        static {
            strongholdChestContents = new WeightedRandomChestContent[] { new WeightedRandomChestContent(Items.ender_pearl, 0, 1, 1, 10), new WeightedRandomChestContent(Items.diamond, 0, 1, 3, 3), new WeightedRandomChestContent(Items.iron_ingot, 0, 1, 5, 10), new WeightedRandomChestContent(Items.gold_ingot, 0, 1, 3, 5), new WeightedRandomChestContent(Items.redstone, 0, 4, 9, 5), new WeightedRandomChestContent(Items.bread, 0, 1, 3, 15), new WeightedRandomChestContent(Items.apple, 0, 1, 3, 15), new WeightedRandomChestContent(Items.iron_pickaxe, 0, 1, 1, 5), new WeightedRandomChestContent(Items.iron_sword, 0, 1, 1, 5), new WeightedRandomChestContent(Items.iron_chestplate, 0, 1, 1, 5), new WeightedRandomChestContent(Items.iron_helmet, 0, 1, 1, 5), new WeightedRandomChestContent(Items.iron_leggings, 0, 1, 1, 5), new WeightedRandomChestContent(Items.iron_boots, 0, 1, 1, 5), new WeightedRandomChestContent(Items.golden_apple, 0, 1, 1, 1), new WeightedRandomChestContent(Items.saddle, 0, 1, 1, 1), new WeightedRandomChestContent(Items.iron_horse_armor, 0, 1, 1, 1), new WeightedRandomChestContent(Items.golden_horse_armor, 0, 1, 1, 1), new WeightedRandomChestContent(Items.diamond_horse_armor, 0, 1, 1, 1) };
        }
    }
    
    public static class Corridor extends Stronghold
    {
        private int field_74993_a;
        private static final String __OBFID = "CL_00000488";
        
        public Corridor() {
        }
        
        public Corridor(final int p_i2072_1_, final Random p_i2072_2_, final StructureBoundingBox p_i2072_3_, final int p_i2072_4_) {
            super(p_i2072_1_);
            this.coordBaseMode = p_i2072_4_;
            this.boundingBox = p_i2072_3_;
            this.field_74993_a = ((p_i2072_4_ != 2 && p_i2072_4_ != 0) ? p_i2072_3_.getXSize() : p_i2072_3_.getZSize());
        }
        
        @Override
        protected void func_143012_a(final NBTTagCompound p_143012_1_) {
            super.func_143012_a(p_143012_1_);
            p_143012_1_.setInteger("Steps", this.field_74993_a);
        }
        
        @Override
        protected void func_143011_b(final NBTTagCompound p_143011_1_) {
            super.func_143011_b(p_143011_1_);
            this.field_74993_a = p_143011_1_.getInteger("Steps");
        }
        
        public static StructureBoundingBox func_74992_a(final List p_74992_0_, final Random p_74992_1_, final int p_74992_2_, final int p_74992_3_, final int p_74992_4_, final int p_74992_5_) {
            final boolean var6 = true;
            StructureBoundingBox var7 = StructureBoundingBox.getComponentToAddBoundingBox(p_74992_2_, p_74992_3_, p_74992_4_, -1, -1, 0, 5, 5, 4, p_74992_5_);
            final StructureComponent var8 = StructureComponent.findIntersecting(p_74992_0_, var7);
            if (var8 == null) {
                return null;
            }
            if (var8.getBoundingBox().minY == var7.minY) {
                for (int var9 = 3; var9 >= 1; --var9) {
                    var7 = StructureBoundingBox.getComponentToAddBoundingBox(p_74992_2_, p_74992_3_, p_74992_4_, -1, -1, 0, 5, 5, var9 - 1, p_74992_5_);
                    if (!var8.getBoundingBox().intersectsWith(var7)) {
                        return StructureBoundingBox.getComponentToAddBoundingBox(p_74992_2_, p_74992_3_, p_74992_4_, -1, -1, 0, 5, 5, var9, p_74992_5_);
                    }
                }
            }
            return null;
        }
        
        @Override
        public boolean addComponentParts(final World p_74875_1_, final Random p_74875_2_, final StructureBoundingBox p_74875_3_) {
            if (this.isLiquidInStructureBoundingBox(p_74875_1_, p_74875_3_)) {
                return false;
            }
            for (int var4 = 0; var4 < this.field_74993_a; ++var4) {
                this.func_151550_a(p_74875_1_, Blocks.stonebrick, 0, 0, 0, var4, p_74875_3_);
                this.func_151550_a(p_74875_1_, Blocks.stonebrick, 0, 1, 0, var4, p_74875_3_);
                this.func_151550_a(p_74875_1_, Blocks.stonebrick, 0, 2, 0, var4, p_74875_3_);
                this.func_151550_a(p_74875_1_, Blocks.stonebrick, 0, 3, 0, var4, p_74875_3_);
                this.func_151550_a(p_74875_1_, Blocks.stonebrick, 0, 4, 0, var4, p_74875_3_);
                for (int var5 = 1; var5 <= 3; ++var5) {
                    this.func_151550_a(p_74875_1_, Blocks.stonebrick, 0, 0, var5, var4, p_74875_3_);
                    this.func_151550_a(p_74875_1_, Blocks.air, 0, 1, var5, var4, p_74875_3_);
                    this.func_151550_a(p_74875_1_, Blocks.air, 0, 2, var5, var4, p_74875_3_);
                    this.func_151550_a(p_74875_1_, Blocks.air, 0, 3, var5, var4, p_74875_3_);
                    this.func_151550_a(p_74875_1_, Blocks.stonebrick, 0, 4, var5, var4, p_74875_3_);
                }
                this.func_151550_a(p_74875_1_, Blocks.stonebrick, 0, 0, 4, var4, p_74875_3_);
                this.func_151550_a(p_74875_1_, Blocks.stonebrick, 0, 1, 4, var4, p_74875_3_);
                this.func_151550_a(p_74875_1_, Blocks.stonebrick, 0, 2, 4, var4, p_74875_3_);
                this.func_151550_a(p_74875_1_, Blocks.stonebrick, 0, 3, 4, var4, p_74875_3_);
                this.func_151550_a(p_74875_1_, Blocks.stonebrick, 0, 4, 4, var4, p_74875_3_);
            }
            return true;
        }
    }
    
    public static class Crossing extends Stronghold
    {
        private boolean field_74996_b;
        private boolean field_74997_c;
        private boolean field_74995_d;
        private boolean field_74999_h;
        private static final String __OBFID = "CL_00000489";
        
        public Crossing() {
        }
        
        public Crossing(final int p_i2073_1_, final Random p_i2073_2_, final StructureBoundingBox p_i2073_3_, final int p_i2073_4_) {
            super(p_i2073_1_);
            this.coordBaseMode = p_i2073_4_;
            this.field_143013_d = this.getRandomDoor(p_i2073_2_);
            this.boundingBox = p_i2073_3_;
            this.field_74996_b = p_i2073_2_.nextBoolean();
            this.field_74997_c = p_i2073_2_.nextBoolean();
            this.field_74995_d = p_i2073_2_.nextBoolean();
            this.field_74999_h = (p_i2073_2_.nextInt(3) > 0);
        }
        
        @Override
        protected void func_143012_a(final NBTTagCompound p_143012_1_) {
            super.func_143012_a(p_143012_1_);
            p_143012_1_.setBoolean("leftLow", this.field_74996_b);
            p_143012_1_.setBoolean("leftHigh", this.field_74997_c);
            p_143012_1_.setBoolean("rightLow", this.field_74995_d);
            p_143012_1_.setBoolean("rightHigh", this.field_74999_h);
        }
        
        @Override
        protected void func_143011_b(final NBTTagCompound p_143011_1_) {
            super.func_143011_b(p_143011_1_);
            this.field_74996_b = p_143011_1_.getBoolean("leftLow");
            this.field_74997_c = p_143011_1_.getBoolean("leftHigh");
            this.field_74995_d = p_143011_1_.getBoolean("rightLow");
            this.field_74999_h = p_143011_1_.getBoolean("rightHigh");
        }
        
        @Override
        public void buildComponent(final StructureComponent p_74861_1_, final List p_74861_2_, final Random p_74861_3_) {
            int var4 = 3;
            int var5 = 5;
            if (this.coordBaseMode == 1 || this.coordBaseMode == 2) {
                var4 = 8 - var4;
                var5 = 8 - var5;
            }
            this.getNextComponentNormal((Stairs2)p_74861_1_, p_74861_2_, p_74861_3_, 5, 1);
            if (this.field_74996_b) {
                this.getNextComponentX((Stairs2)p_74861_1_, p_74861_2_, p_74861_3_, var4, 1);
            }
            if (this.field_74997_c) {
                this.getNextComponentX((Stairs2)p_74861_1_, p_74861_2_, p_74861_3_, var5, 7);
            }
            if (this.field_74995_d) {
                this.getNextComponentZ((Stairs2)p_74861_1_, p_74861_2_, p_74861_3_, var4, 1);
            }
            if (this.field_74999_h) {
                this.getNextComponentZ((Stairs2)p_74861_1_, p_74861_2_, p_74861_3_, var5, 7);
            }
        }
        
        public static Crossing findValidPlacement(final List p_74994_0_, final Random p_74994_1_, final int p_74994_2_, final int p_74994_3_, final int p_74994_4_, final int p_74994_5_, final int p_74994_6_) {
            final StructureBoundingBox var7 = StructureBoundingBox.getComponentToAddBoundingBox(p_74994_2_, p_74994_3_, p_74994_4_, -4, -3, 0, 10, 9, 11, p_74994_5_);
            return (Stronghold.canStrongholdGoDeeper(var7) && StructureComponent.findIntersecting(p_74994_0_, var7) == null) ? new Crossing(p_74994_6_, p_74994_1_, var7, p_74994_5_) : null;
        }
        
        @Override
        public boolean addComponentParts(final World p_74875_1_, final Random p_74875_2_, final StructureBoundingBox p_74875_3_) {
            if (this.isLiquidInStructureBoundingBox(p_74875_1_, p_74875_3_)) {
                return false;
            }
            this.fillWithRandomizedBlocks(p_74875_1_, p_74875_3_, 0, 0, 0, 9, 8, 10, true, p_74875_2_, StructureStrongholdPieces.strongholdStones);
            this.placeDoor(p_74875_1_, p_74875_2_, p_74875_3_, this.field_143013_d, 4, 3, 0);
            if (this.field_74996_b) {
                this.func_151549_a(p_74875_1_, p_74875_3_, 0, 3, 1, 0, 5, 3, Blocks.air, Blocks.air, false);
            }
            if (this.field_74995_d) {
                this.func_151549_a(p_74875_1_, p_74875_3_, 9, 3, 1, 9, 5, 3, Blocks.air, Blocks.air, false);
            }
            if (this.field_74997_c) {
                this.func_151549_a(p_74875_1_, p_74875_3_, 0, 5, 7, 0, 7, 9, Blocks.air, Blocks.air, false);
            }
            if (this.field_74999_h) {
                this.func_151549_a(p_74875_1_, p_74875_3_, 9, 5, 7, 9, 7, 9, Blocks.air, Blocks.air, false);
            }
            this.func_151549_a(p_74875_1_, p_74875_3_, 5, 1, 10, 7, 3, 10, Blocks.air, Blocks.air, false);
            this.fillWithRandomizedBlocks(p_74875_1_, p_74875_3_, 1, 2, 1, 8, 2, 6, false, p_74875_2_, StructureStrongholdPieces.strongholdStones);
            this.fillWithRandomizedBlocks(p_74875_1_, p_74875_3_, 4, 1, 5, 4, 4, 9, false, p_74875_2_, StructureStrongholdPieces.strongholdStones);
            this.fillWithRandomizedBlocks(p_74875_1_, p_74875_3_, 8, 1, 5, 8, 4, 9, false, p_74875_2_, StructureStrongholdPieces.strongholdStones);
            this.fillWithRandomizedBlocks(p_74875_1_, p_74875_3_, 1, 4, 7, 3, 4, 9, false, p_74875_2_, StructureStrongholdPieces.strongholdStones);
            this.fillWithRandomizedBlocks(p_74875_1_, p_74875_3_, 1, 3, 5, 3, 3, 6, false, p_74875_2_, StructureStrongholdPieces.strongholdStones);
            this.func_151549_a(p_74875_1_, p_74875_3_, 1, 3, 4, 3, 3, 4, Blocks.stone_slab, Blocks.stone_slab, false);
            this.func_151549_a(p_74875_1_, p_74875_3_, 1, 4, 6, 3, 4, 6, Blocks.stone_slab, Blocks.stone_slab, false);
            this.fillWithRandomizedBlocks(p_74875_1_, p_74875_3_, 5, 1, 7, 7, 1, 8, false, p_74875_2_, StructureStrongholdPieces.strongholdStones);
            this.func_151549_a(p_74875_1_, p_74875_3_, 5, 1, 9, 7, 1, 9, Blocks.stone_slab, Blocks.stone_slab, false);
            this.func_151549_a(p_74875_1_, p_74875_3_, 5, 2, 7, 7, 2, 7, Blocks.stone_slab, Blocks.stone_slab, false);
            this.func_151549_a(p_74875_1_, p_74875_3_, 4, 5, 7, 4, 5, 9, Blocks.stone_slab, Blocks.stone_slab, false);
            this.func_151549_a(p_74875_1_, p_74875_3_, 8, 5, 7, 8, 5, 9, Blocks.stone_slab, Blocks.stone_slab, false);
            this.func_151549_a(p_74875_1_, p_74875_3_, 5, 5, 7, 7, 5, 9, Blocks.double_stone_slab, Blocks.double_stone_slab, false);
            this.func_151550_a(p_74875_1_, Blocks.torch, 0, 6, 5, 6, p_74875_3_);
            return true;
        }
    }
    
    public static class LeftTurn extends Stronghold
    {
        private static final String __OBFID = "CL_00000490";
        
        public LeftTurn() {
        }
        
        public LeftTurn(final int p_i2074_1_, final Random p_i2074_2_, final StructureBoundingBox p_i2074_3_, final int p_i2074_4_) {
            super(p_i2074_1_);
            this.coordBaseMode = p_i2074_4_;
            this.field_143013_d = this.getRandomDoor(p_i2074_2_);
            this.boundingBox = p_i2074_3_;
        }
        
        @Override
        public void buildComponent(final StructureComponent p_74861_1_, final List p_74861_2_, final Random p_74861_3_) {
            if (this.coordBaseMode != 2 && this.coordBaseMode != 3) {
                this.getNextComponentZ((Stairs2)p_74861_1_, p_74861_2_, p_74861_3_, 1, 1);
            }
            else {
                this.getNextComponentX((Stairs2)p_74861_1_, p_74861_2_, p_74861_3_, 1, 1);
            }
        }
        
        public static LeftTurn findValidPlacement(final List p_75010_0_, final Random p_75010_1_, final int p_75010_2_, final int p_75010_3_, final int p_75010_4_, final int p_75010_5_, final int p_75010_6_) {
            final StructureBoundingBox var7 = StructureBoundingBox.getComponentToAddBoundingBox(p_75010_2_, p_75010_3_, p_75010_4_, -1, -1, 0, 5, 5, 5, p_75010_5_);
            return (Stronghold.canStrongholdGoDeeper(var7) && StructureComponent.findIntersecting(p_75010_0_, var7) == null) ? new LeftTurn(p_75010_6_, p_75010_1_, var7, p_75010_5_) : null;
        }
        
        @Override
        public boolean addComponentParts(final World p_74875_1_, final Random p_74875_2_, final StructureBoundingBox p_74875_3_) {
            if (this.isLiquidInStructureBoundingBox(p_74875_1_, p_74875_3_)) {
                return false;
            }
            this.fillWithRandomizedBlocks(p_74875_1_, p_74875_3_, 0, 0, 0, 4, 4, 4, true, p_74875_2_, StructureStrongholdPieces.strongholdStones);
            this.placeDoor(p_74875_1_, p_74875_2_, p_74875_3_, this.field_143013_d, 1, 1, 0);
            if (this.coordBaseMode != 2 && this.coordBaseMode != 3) {
                this.func_151549_a(p_74875_1_, p_74875_3_, 4, 1, 1, 4, 3, 3, Blocks.air, Blocks.air, false);
            }
            else {
                this.func_151549_a(p_74875_1_, p_74875_3_, 0, 1, 1, 0, 3, 3, Blocks.air, Blocks.air, false);
            }
            return true;
        }
    }
    
    public static class Library extends Stronghold
    {
        private static final WeightedRandomChestContent[] strongholdLibraryChestContents;
        private boolean isLargeRoom;
        private static final String __OBFID = "CL_00000491";
        
        public Library() {
        }
        
        public Library(final int p_i2075_1_, final Random p_i2075_2_, final StructureBoundingBox p_i2075_3_, final int p_i2075_4_) {
            super(p_i2075_1_);
            this.coordBaseMode = p_i2075_4_;
            this.field_143013_d = this.getRandomDoor(p_i2075_2_);
            this.boundingBox = p_i2075_3_;
            this.isLargeRoom = (p_i2075_3_.getYSize() > 6);
        }
        
        @Override
        protected void func_143012_a(final NBTTagCompound p_143012_1_) {
            super.func_143012_a(p_143012_1_);
            p_143012_1_.setBoolean("Tall", this.isLargeRoom);
        }
        
        @Override
        protected void func_143011_b(final NBTTagCompound p_143011_1_) {
            super.func_143011_b(p_143011_1_);
            this.isLargeRoom = p_143011_1_.getBoolean("Tall");
        }
        
        public static Library findValidPlacement(final List p_75006_0_, final Random p_75006_1_, final int p_75006_2_, final int p_75006_3_, final int p_75006_4_, final int p_75006_5_, final int p_75006_6_) {
            StructureBoundingBox var7 = StructureBoundingBox.getComponentToAddBoundingBox(p_75006_2_, p_75006_3_, p_75006_4_, -4, -1, 0, 14, 11, 15, p_75006_5_);
            if (!Stronghold.canStrongholdGoDeeper(var7) || StructureComponent.findIntersecting(p_75006_0_, var7) != null) {
                var7 = StructureBoundingBox.getComponentToAddBoundingBox(p_75006_2_, p_75006_3_, p_75006_4_, -4, -1, 0, 14, 6, 15, p_75006_5_);
                if (!Stronghold.canStrongholdGoDeeper(var7) || StructureComponent.findIntersecting(p_75006_0_, var7) != null) {
                    return null;
                }
            }
            return new Library(p_75006_6_, p_75006_1_, var7, p_75006_5_);
        }
        
        @Override
        public boolean addComponentParts(final World p_74875_1_, final Random p_74875_2_, final StructureBoundingBox p_74875_3_) {
            if (this.isLiquidInStructureBoundingBox(p_74875_1_, p_74875_3_)) {
                return false;
            }
            byte var4 = 11;
            if (!this.isLargeRoom) {
                var4 = 6;
            }
            this.fillWithRandomizedBlocks(p_74875_1_, p_74875_3_, 0, 0, 0, 13, var4 - 1, 14, true, p_74875_2_, StructureStrongholdPieces.strongholdStones);
            this.placeDoor(p_74875_1_, p_74875_2_, p_74875_3_, this.field_143013_d, 4, 1, 0);
            this.func_151551_a(p_74875_1_, p_74875_3_, p_74875_2_, 0.07f, 2, 1, 1, 11, 4, 13, Blocks.web, Blocks.web, false);
            final boolean var5 = true;
            final boolean var6 = true;
            for (int var7 = 1; var7 <= 13; ++var7) {
                if ((var7 - 1) % 4 == 0) {
                    this.func_151549_a(p_74875_1_, p_74875_3_, 1, 1, var7, 1, 4, var7, Blocks.planks, Blocks.planks, false);
                    this.func_151549_a(p_74875_1_, p_74875_3_, 12, 1, var7, 12, 4, var7, Blocks.planks, Blocks.planks, false);
                    this.func_151550_a(p_74875_1_, Blocks.torch, 0, 2, 3, var7, p_74875_3_);
                    this.func_151550_a(p_74875_1_, Blocks.torch, 0, 11, 3, var7, p_74875_3_);
                    if (this.isLargeRoom) {
                        this.func_151549_a(p_74875_1_, p_74875_3_, 1, 6, var7, 1, 9, var7, Blocks.planks, Blocks.planks, false);
                        this.func_151549_a(p_74875_1_, p_74875_3_, 12, 6, var7, 12, 9, var7, Blocks.planks, Blocks.planks, false);
                    }
                }
                else {
                    this.func_151549_a(p_74875_1_, p_74875_3_, 1, 1, var7, 1, 4, var7, Blocks.bookshelf, Blocks.bookshelf, false);
                    this.func_151549_a(p_74875_1_, p_74875_3_, 12, 1, var7, 12, 4, var7, Blocks.bookshelf, Blocks.bookshelf, false);
                    if (this.isLargeRoom) {
                        this.func_151549_a(p_74875_1_, p_74875_3_, 1, 6, var7, 1, 9, var7, Blocks.bookshelf, Blocks.bookshelf, false);
                        this.func_151549_a(p_74875_1_, p_74875_3_, 12, 6, var7, 12, 9, var7, Blocks.bookshelf, Blocks.bookshelf, false);
                    }
                }
            }
            for (int var7 = 3; var7 < 12; var7 += 2) {
                this.func_151549_a(p_74875_1_, p_74875_3_, 3, 1, var7, 4, 3, var7, Blocks.bookshelf, Blocks.bookshelf, false);
                this.func_151549_a(p_74875_1_, p_74875_3_, 6, 1, var7, 7, 3, var7, Blocks.bookshelf, Blocks.bookshelf, false);
                this.func_151549_a(p_74875_1_, p_74875_3_, 9, 1, var7, 10, 3, var7, Blocks.bookshelf, Blocks.bookshelf, false);
            }
            if (this.isLargeRoom) {
                this.func_151549_a(p_74875_1_, p_74875_3_, 1, 5, 1, 3, 5, 13, Blocks.planks, Blocks.planks, false);
                this.func_151549_a(p_74875_1_, p_74875_3_, 10, 5, 1, 12, 5, 13, Blocks.planks, Blocks.planks, false);
                this.func_151549_a(p_74875_1_, p_74875_3_, 4, 5, 1, 9, 5, 2, Blocks.planks, Blocks.planks, false);
                this.func_151549_a(p_74875_1_, p_74875_3_, 4, 5, 12, 9, 5, 13, Blocks.planks, Blocks.planks, false);
                this.func_151550_a(p_74875_1_, Blocks.planks, 0, 9, 5, 11, p_74875_3_);
                this.func_151550_a(p_74875_1_, Blocks.planks, 0, 8, 5, 11, p_74875_3_);
                this.func_151550_a(p_74875_1_, Blocks.planks, 0, 9, 5, 10, p_74875_3_);
                this.func_151549_a(p_74875_1_, p_74875_3_, 3, 6, 2, 3, 6, 12, Blocks.fence, Blocks.fence, false);
                this.func_151549_a(p_74875_1_, p_74875_3_, 10, 6, 2, 10, 6, 10, Blocks.fence, Blocks.fence, false);
                this.func_151549_a(p_74875_1_, p_74875_3_, 4, 6, 2, 9, 6, 2, Blocks.fence, Blocks.fence, false);
                this.func_151549_a(p_74875_1_, p_74875_3_, 4, 6, 12, 8, 6, 12, Blocks.fence, Blocks.fence, false);
                this.func_151550_a(p_74875_1_, Blocks.fence, 0, 9, 6, 11, p_74875_3_);
                this.func_151550_a(p_74875_1_, Blocks.fence, 0, 8, 6, 11, p_74875_3_);
                this.func_151550_a(p_74875_1_, Blocks.fence, 0, 9, 6, 10, p_74875_3_);
                final int var7 = this.func_151555_a(Blocks.ladder, 3);
                this.func_151550_a(p_74875_1_, Blocks.ladder, var7, 10, 1, 13, p_74875_3_);
                this.func_151550_a(p_74875_1_, Blocks.ladder, var7, 10, 2, 13, p_74875_3_);
                this.func_151550_a(p_74875_1_, Blocks.ladder, var7, 10, 3, 13, p_74875_3_);
                this.func_151550_a(p_74875_1_, Blocks.ladder, var7, 10, 4, 13, p_74875_3_);
                this.func_151550_a(p_74875_1_, Blocks.ladder, var7, 10, 5, 13, p_74875_3_);
                this.func_151550_a(p_74875_1_, Blocks.ladder, var7, 10, 6, 13, p_74875_3_);
                this.func_151550_a(p_74875_1_, Blocks.ladder, var7, 10, 7, 13, p_74875_3_);
                final byte var8 = 7;
                final byte var9 = 7;
                this.func_151550_a(p_74875_1_, Blocks.fence, 0, var8 - 1, 9, var9, p_74875_3_);
                this.func_151550_a(p_74875_1_, Blocks.fence, 0, var8, 9, var9, p_74875_3_);
                this.func_151550_a(p_74875_1_, Blocks.fence, 0, var8 - 1, 8, var9, p_74875_3_);
                this.func_151550_a(p_74875_1_, Blocks.fence, 0, var8, 8, var9, p_74875_3_);
                this.func_151550_a(p_74875_1_, Blocks.fence, 0, var8 - 1, 7, var9, p_74875_3_);
                this.func_151550_a(p_74875_1_, Blocks.fence, 0, var8, 7, var9, p_74875_3_);
                this.func_151550_a(p_74875_1_, Blocks.fence, 0, var8 - 2, 7, var9, p_74875_3_);
                this.func_151550_a(p_74875_1_, Blocks.fence, 0, var8 + 1, 7, var9, p_74875_3_);
                this.func_151550_a(p_74875_1_, Blocks.fence, 0, var8 - 1, 7, var9 - 1, p_74875_3_);
                this.func_151550_a(p_74875_1_, Blocks.fence, 0, var8 - 1, 7, var9 + 1, p_74875_3_);
                this.func_151550_a(p_74875_1_, Blocks.fence, 0, var8, 7, var9 - 1, p_74875_3_);
                this.func_151550_a(p_74875_1_, Blocks.fence, 0, var8, 7, var9 + 1, p_74875_3_);
                this.func_151550_a(p_74875_1_, Blocks.torch, 0, var8 - 2, 8, var9, p_74875_3_);
                this.func_151550_a(p_74875_1_, Blocks.torch, 0, var8 + 1, 8, var9, p_74875_3_);
                this.func_151550_a(p_74875_1_, Blocks.torch, 0, var8 - 1, 8, var9 - 1, p_74875_3_);
                this.func_151550_a(p_74875_1_, Blocks.torch, 0, var8 - 1, 8, var9 + 1, p_74875_3_);
                this.func_151550_a(p_74875_1_, Blocks.torch, 0, var8, 8, var9 - 1, p_74875_3_);
                this.func_151550_a(p_74875_1_, Blocks.torch, 0, var8, 8, var9 + 1, p_74875_3_);
            }
            this.generateStructureChestContents(p_74875_1_, p_74875_3_, p_74875_2_, 3, 3, 5, WeightedRandomChestContent.func_92080_a(Library.strongholdLibraryChestContents, Items.enchanted_book.func_92112_a(p_74875_2_, 1, 5, 2)), 1 + p_74875_2_.nextInt(4));
            if (this.isLargeRoom) {
                this.func_151550_a(p_74875_1_, Blocks.air, 0, 12, 9, 1, p_74875_3_);
                this.generateStructureChestContents(p_74875_1_, p_74875_3_, p_74875_2_, 12, 8, 1, WeightedRandomChestContent.func_92080_a(Library.strongholdLibraryChestContents, Items.enchanted_book.func_92112_a(p_74875_2_, 1, 5, 2)), 1 + p_74875_2_.nextInt(4));
            }
            return true;
        }
        
        static {
            strongholdLibraryChestContents = new WeightedRandomChestContent[] { new WeightedRandomChestContent(Items.book, 0, 1, 3, 20), new WeightedRandomChestContent(Items.paper, 0, 2, 7, 20), new WeightedRandomChestContent(Items.map, 0, 1, 1, 1), new WeightedRandomChestContent(Items.compass, 0, 1, 1, 1) };
        }
    }
    
    static class PieceWeight
    {
        public Class pieceClass;
        public final int pieceWeight;
        public int instancesSpawned;
        public int instancesLimit;
        private static final String __OBFID = "CL_00000492";
        
        public PieceWeight(final Class p_i2076_1_, final int p_i2076_2_, final int p_i2076_3_) {
            this.pieceClass = p_i2076_1_;
            this.pieceWeight = p_i2076_2_;
            this.instancesLimit = p_i2076_3_;
        }
        
        public boolean canSpawnMoreStructuresOfType(final int p_75189_1_) {
            return this.instancesLimit == 0 || this.instancesSpawned < this.instancesLimit;
        }
        
        public boolean canSpawnMoreStructures() {
            return this.instancesLimit == 0 || this.instancesSpawned < this.instancesLimit;
        }
    }
    
    public static class PortalRoom extends Stronghold
    {
        private boolean hasSpawner;
        private static final String __OBFID = "CL_00000493";
        
        public PortalRoom() {
        }
        
        public PortalRoom(final int p_i2077_1_, final Random p_i2077_2_, final StructureBoundingBox p_i2077_3_, final int p_i2077_4_) {
            super(p_i2077_1_);
            this.coordBaseMode = p_i2077_4_;
            this.boundingBox = p_i2077_3_;
        }
        
        @Override
        protected void func_143012_a(final NBTTagCompound p_143012_1_) {
            super.func_143012_a(p_143012_1_);
            p_143012_1_.setBoolean("Mob", this.hasSpawner);
        }
        
        @Override
        protected void func_143011_b(final NBTTagCompound p_143011_1_) {
            super.func_143011_b(p_143011_1_);
            this.hasSpawner = p_143011_1_.getBoolean("Mob");
        }
        
        @Override
        public void buildComponent(final StructureComponent p_74861_1_, final List p_74861_2_, final Random p_74861_3_) {
            if (p_74861_1_ != null) {
                ((Stairs2)p_74861_1_).strongholdPortalRoom = this;
            }
        }
        
        public static PortalRoom findValidPlacement(final List p_75004_0_, final Random p_75004_1_, final int p_75004_2_, final int p_75004_3_, final int p_75004_4_, final int p_75004_5_, final int p_75004_6_) {
            final StructureBoundingBox var7 = StructureBoundingBox.getComponentToAddBoundingBox(p_75004_2_, p_75004_3_, p_75004_4_, -4, -1, 0, 11, 8, 16, p_75004_5_);
            return (Stronghold.canStrongholdGoDeeper(var7) && StructureComponent.findIntersecting(p_75004_0_, var7) == null) ? new PortalRoom(p_75004_6_, p_75004_1_, var7, p_75004_5_) : null;
        }
        
        @Override
        public boolean addComponentParts(final World p_74875_1_, final Random p_74875_2_, final StructureBoundingBox p_74875_3_) {
            this.fillWithRandomizedBlocks(p_74875_1_, p_74875_3_, 0, 0, 0, 10, 7, 15, false, p_74875_2_, StructureStrongholdPieces.strongholdStones);
            this.placeDoor(p_74875_1_, p_74875_2_, p_74875_3_, Door.GRATES, 4, 1, 0);
            final byte var4 = 6;
            this.fillWithRandomizedBlocks(p_74875_1_, p_74875_3_, 1, var4, 1, 1, var4, 14, false, p_74875_2_, StructureStrongholdPieces.strongholdStones);
            this.fillWithRandomizedBlocks(p_74875_1_, p_74875_3_, 9, var4, 1, 9, var4, 14, false, p_74875_2_, StructureStrongholdPieces.strongholdStones);
            this.fillWithRandomizedBlocks(p_74875_1_, p_74875_3_, 2, var4, 1, 8, var4, 2, false, p_74875_2_, StructureStrongholdPieces.strongholdStones);
            this.fillWithRandomizedBlocks(p_74875_1_, p_74875_3_, 2, var4, 14, 8, var4, 14, false, p_74875_2_, StructureStrongholdPieces.strongholdStones);
            this.fillWithRandomizedBlocks(p_74875_1_, p_74875_3_, 1, 1, 1, 2, 1, 4, false, p_74875_2_, StructureStrongholdPieces.strongholdStones);
            this.fillWithRandomizedBlocks(p_74875_1_, p_74875_3_, 8, 1, 1, 9, 1, 4, false, p_74875_2_, StructureStrongholdPieces.strongholdStones);
            this.func_151549_a(p_74875_1_, p_74875_3_, 1, 1, 1, 1, 1, 3, Blocks.flowing_lava, Blocks.flowing_lava, false);
            this.func_151549_a(p_74875_1_, p_74875_3_, 9, 1, 1, 9, 1, 3, Blocks.flowing_lava, Blocks.flowing_lava, false);
            this.fillWithRandomizedBlocks(p_74875_1_, p_74875_3_, 3, 1, 8, 7, 1, 12, false, p_74875_2_, StructureStrongholdPieces.strongholdStones);
            this.func_151549_a(p_74875_1_, p_74875_3_, 4, 1, 9, 6, 1, 11, Blocks.flowing_lava, Blocks.flowing_lava, false);
            for (int var5 = 3; var5 < 14; var5 += 2) {
                this.func_151549_a(p_74875_1_, p_74875_3_, 0, 3, var5, 0, 4, var5, Blocks.iron_bars, Blocks.iron_bars, false);
                this.func_151549_a(p_74875_1_, p_74875_3_, 10, 3, var5, 10, 4, var5, Blocks.iron_bars, Blocks.iron_bars, false);
            }
            for (int var5 = 2; var5 < 9; var5 += 2) {
                this.func_151549_a(p_74875_1_, p_74875_3_, var5, 3, 15, var5, 4, 15, Blocks.iron_bars, Blocks.iron_bars, false);
            }
            int var5 = this.func_151555_a(Blocks.stone_brick_stairs, 3);
            this.fillWithRandomizedBlocks(p_74875_1_, p_74875_3_, 4, 1, 5, 6, 1, 7, false, p_74875_2_, StructureStrongholdPieces.strongholdStones);
            this.fillWithRandomizedBlocks(p_74875_1_, p_74875_3_, 4, 2, 6, 6, 2, 7, false, p_74875_2_, StructureStrongholdPieces.strongholdStones);
            this.fillWithRandomizedBlocks(p_74875_1_, p_74875_3_, 4, 3, 7, 6, 3, 7, false, p_74875_2_, StructureStrongholdPieces.strongholdStones);
            for (int var6 = 4; var6 <= 6; ++var6) {
                this.func_151550_a(p_74875_1_, Blocks.stone_brick_stairs, var5, var6, 1, 4, p_74875_3_);
                this.func_151550_a(p_74875_1_, Blocks.stone_brick_stairs, var5, var6, 2, 5, p_74875_3_);
                this.func_151550_a(p_74875_1_, Blocks.stone_brick_stairs, var5, var6, 3, 6, p_74875_3_);
            }
            byte var7 = 2;
            byte var8 = 0;
            byte var9 = 3;
            byte var10 = 1;
            switch (this.coordBaseMode) {
                case 0: {
                    var7 = 0;
                    var8 = 2;
                    break;
                }
                case 1: {
                    var7 = 1;
                    var8 = 3;
                    var9 = 0;
                    var10 = 2;
                    break;
                }
                case 3: {
                    var7 = 3;
                    var8 = 1;
                    var9 = 0;
                    var10 = 2;
                    break;
                }
            }
            this.func_151550_a(p_74875_1_, Blocks.end_portal_frame, var7 + ((p_74875_2_.nextFloat() > 0.9f) ? 4 : 0), 4, 3, 8, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.end_portal_frame, var7 + ((p_74875_2_.nextFloat() > 0.9f) ? 4 : 0), 5, 3, 8, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.end_portal_frame, var7 + ((p_74875_2_.nextFloat() > 0.9f) ? 4 : 0), 6, 3, 8, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.end_portal_frame, var8 + ((p_74875_2_.nextFloat() > 0.9f) ? 4 : 0), 4, 3, 12, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.end_portal_frame, var8 + ((p_74875_2_.nextFloat() > 0.9f) ? 4 : 0), 5, 3, 12, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.end_portal_frame, var8 + ((p_74875_2_.nextFloat() > 0.9f) ? 4 : 0), 6, 3, 12, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.end_portal_frame, var9 + ((p_74875_2_.nextFloat() > 0.9f) ? 4 : 0), 3, 3, 9, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.end_portal_frame, var9 + ((p_74875_2_.nextFloat() > 0.9f) ? 4 : 0), 3, 3, 10, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.end_portal_frame, var9 + ((p_74875_2_.nextFloat() > 0.9f) ? 4 : 0), 3, 3, 11, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.end_portal_frame, var10 + ((p_74875_2_.nextFloat() > 0.9f) ? 4 : 0), 7, 3, 9, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.end_portal_frame, var10 + ((p_74875_2_.nextFloat() > 0.9f) ? 4 : 0), 7, 3, 10, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.end_portal_frame, var10 + ((p_74875_2_.nextFloat() > 0.9f) ? 4 : 0), 7, 3, 11, p_74875_3_);
            if (!this.hasSpawner) {
                final int var11 = this.getYWithOffset(3);
                final int var12 = this.getXWithOffset(5, 6);
                final int var13 = this.getZWithOffset(5, 6);
                if (p_74875_3_.isVecInside(var12, var11, var13)) {
                    this.hasSpawner = true;
                    p_74875_1_.setBlock(var12, var11, var13, Blocks.mob_spawner, 0, 2);
                    final TileEntityMobSpawner var14 = (TileEntityMobSpawner)p_74875_1_.getTileEntity(var12, var11, var13);
                    if (var14 != null) {
                        var14.func_145881_a().setMobID("Silverfish");
                    }
                }
            }
            return true;
        }
    }
    
    public static class Prison extends Stronghold
    {
        private static final String __OBFID = "CL_00000494";
        
        public Prison() {
        }
        
        public Prison(final int p_i2078_1_, final Random p_i2078_2_, final StructureBoundingBox p_i2078_3_, final int p_i2078_4_) {
            super(p_i2078_1_);
            this.coordBaseMode = p_i2078_4_;
            this.field_143013_d = this.getRandomDoor(p_i2078_2_);
            this.boundingBox = p_i2078_3_;
        }
        
        @Override
        public void buildComponent(final StructureComponent p_74861_1_, final List p_74861_2_, final Random p_74861_3_) {
            this.getNextComponentNormal((Stairs2)p_74861_1_, p_74861_2_, p_74861_3_, 1, 1);
        }
        
        public static Prison findValidPlacement(final List p_75016_0_, final Random p_75016_1_, final int p_75016_2_, final int p_75016_3_, final int p_75016_4_, final int p_75016_5_, final int p_75016_6_) {
            final StructureBoundingBox var7 = StructureBoundingBox.getComponentToAddBoundingBox(p_75016_2_, p_75016_3_, p_75016_4_, -1, -1, 0, 9, 5, 11, p_75016_5_);
            return (Stronghold.canStrongholdGoDeeper(var7) && StructureComponent.findIntersecting(p_75016_0_, var7) == null) ? new Prison(p_75016_6_, p_75016_1_, var7, p_75016_5_) : null;
        }
        
        @Override
        public boolean addComponentParts(final World p_74875_1_, final Random p_74875_2_, final StructureBoundingBox p_74875_3_) {
            if (this.isLiquidInStructureBoundingBox(p_74875_1_, p_74875_3_)) {
                return false;
            }
            this.fillWithRandomizedBlocks(p_74875_1_, p_74875_3_, 0, 0, 0, 8, 4, 10, true, p_74875_2_, StructureStrongholdPieces.strongholdStones);
            this.placeDoor(p_74875_1_, p_74875_2_, p_74875_3_, this.field_143013_d, 1, 1, 0);
            this.func_151549_a(p_74875_1_, p_74875_3_, 1, 1, 10, 3, 3, 10, Blocks.air, Blocks.air, false);
            this.fillWithRandomizedBlocks(p_74875_1_, p_74875_3_, 4, 1, 1, 4, 3, 1, false, p_74875_2_, StructureStrongholdPieces.strongholdStones);
            this.fillWithRandomizedBlocks(p_74875_1_, p_74875_3_, 4, 1, 3, 4, 3, 3, false, p_74875_2_, StructureStrongholdPieces.strongholdStones);
            this.fillWithRandomizedBlocks(p_74875_1_, p_74875_3_, 4, 1, 7, 4, 3, 7, false, p_74875_2_, StructureStrongholdPieces.strongholdStones);
            this.fillWithRandomizedBlocks(p_74875_1_, p_74875_3_, 4, 1, 9, 4, 3, 9, false, p_74875_2_, StructureStrongholdPieces.strongholdStones);
            this.func_151549_a(p_74875_1_, p_74875_3_, 4, 1, 4, 4, 3, 6, Blocks.iron_bars, Blocks.iron_bars, false);
            this.func_151549_a(p_74875_1_, p_74875_3_, 5, 1, 5, 7, 3, 5, Blocks.iron_bars, Blocks.iron_bars, false);
            this.func_151550_a(p_74875_1_, Blocks.iron_bars, 0, 4, 3, 2, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.iron_bars, 0, 4, 3, 8, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.iron_door, this.func_151555_a(Blocks.iron_door, 3), 4, 1, 2, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.iron_door, this.func_151555_a(Blocks.iron_door, 3) + 8, 4, 2, 2, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.iron_door, this.func_151555_a(Blocks.iron_door, 3), 4, 1, 8, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.iron_door, this.func_151555_a(Blocks.iron_door, 3) + 8, 4, 2, 8, p_74875_3_);
            return true;
        }
    }
    
    public static class RightTurn extends LeftTurn
    {
        private static final String __OBFID = "CL_00000495";
        
        @Override
        public void buildComponent(final StructureComponent p_74861_1_, final List p_74861_2_, final Random p_74861_3_) {
            if (this.coordBaseMode != 2 && this.coordBaseMode != 3) {
                this.getNextComponentX((Stairs2)p_74861_1_, p_74861_2_, p_74861_3_, 1, 1);
            }
            else {
                this.getNextComponentZ((Stairs2)p_74861_1_, p_74861_2_, p_74861_3_, 1, 1);
            }
        }
        
        @Override
        public boolean addComponentParts(final World p_74875_1_, final Random p_74875_2_, final StructureBoundingBox p_74875_3_) {
            if (this.isLiquidInStructureBoundingBox(p_74875_1_, p_74875_3_)) {
                return false;
            }
            this.fillWithRandomizedBlocks(p_74875_1_, p_74875_3_, 0, 0, 0, 4, 4, 4, true, p_74875_2_, StructureStrongholdPieces.strongholdStones);
            this.placeDoor(p_74875_1_, p_74875_2_, p_74875_3_, this.field_143013_d, 1, 1, 0);
            if (this.coordBaseMode != 2 && this.coordBaseMode != 3) {
                this.func_151549_a(p_74875_1_, p_74875_3_, 0, 1, 1, 0, 3, 3, Blocks.air, Blocks.air, false);
            }
            else {
                this.func_151549_a(p_74875_1_, p_74875_3_, 4, 1, 1, 4, 3, 3, Blocks.air, Blocks.air, false);
            }
            return true;
        }
    }
    
    public static class RoomCrossing extends Stronghold
    {
        private static final WeightedRandomChestContent[] strongholdRoomCrossingChestContents;
        protected int roomType;
        private static final String __OBFID = "CL_00000496";
        
        public RoomCrossing() {
        }
        
        public RoomCrossing(final int p_i2079_1_, final Random p_i2079_2_, final StructureBoundingBox p_i2079_3_, final int p_i2079_4_) {
            super(p_i2079_1_);
            this.coordBaseMode = p_i2079_4_;
            this.field_143013_d = this.getRandomDoor(p_i2079_2_);
            this.boundingBox = p_i2079_3_;
            this.roomType = p_i2079_2_.nextInt(5);
        }
        
        @Override
        protected void func_143012_a(final NBTTagCompound p_143012_1_) {
            super.func_143012_a(p_143012_1_);
            p_143012_1_.setInteger("Type", this.roomType);
        }
        
        @Override
        protected void func_143011_b(final NBTTagCompound p_143011_1_) {
            super.func_143011_b(p_143011_1_);
            this.roomType = p_143011_1_.getInteger("Type");
        }
        
        @Override
        public void buildComponent(final StructureComponent p_74861_1_, final List p_74861_2_, final Random p_74861_3_) {
            this.getNextComponentNormal((Stairs2)p_74861_1_, p_74861_2_, p_74861_3_, 4, 1);
            this.getNextComponentX((Stairs2)p_74861_1_, p_74861_2_, p_74861_3_, 1, 4);
            this.getNextComponentZ((Stairs2)p_74861_1_, p_74861_2_, p_74861_3_, 1, 4);
        }
        
        public static RoomCrossing findValidPlacement(final List p_75012_0_, final Random p_75012_1_, final int p_75012_2_, final int p_75012_3_, final int p_75012_4_, final int p_75012_5_, final int p_75012_6_) {
            final StructureBoundingBox var7 = StructureBoundingBox.getComponentToAddBoundingBox(p_75012_2_, p_75012_3_, p_75012_4_, -4, -1, 0, 11, 7, 11, p_75012_5_);
            return (Stronghold.canStrongholdGoDeeper(var7) && StructureComponent.findIntersecting(p_75012_0_, var7) == null) ? new RoomCrossing(p_75012_6_, p_75012_1_, var7, p_75012_5_) : null;
        }
        
        @Override
        public boolean addComponentParts(final World p_74875_1_, final Random p_74875_2_, final StructureBoundingBox p_74875_3_) {
            if (this.isLiquidInStructureBoundingBox(p_74875_1_, p_74875_3_)) {
                return false;
            }
            this.fillWithRandomizedBlocks(p_74875_1_, p_74875_3_, 0, 0, 0, 10, 6, 10, true, p_74875_2_, StructureStrongholdPieces.strongholdStones);
            this.placeDoor(p_74875_1_, p_74875_2_, p_74875_3_, this.field_143013_d, 4, 1, 0);
            this.func_151549_a(p_74875_1_, p_74875_3_, 4, 1, 10, 6, 3, 10, Blocks.air, Blocks.air, false);
            this.func_151549_a(p_74875_1_, p_74875_3_, 0, 1, 4, 0, 3, 6, Blocks.air, Blocks.air, false);
            this.func_151549_a(p_74875_1_, p_74875_3_, 10, 1, 4, 10, 3, 6, Blocks.air, Blocks.air, false);
            switch (this.roomType) {
                case 0: {
                    this.func_151550_a(p_74875_1_, Blocks.stonebrick, 0, 5, 1, 5, p_74875_3_);
                    this.func_151550_a(p_74875_1_, Blocks.stonebrick, 0, 5, 2, 5, p_74875_3_);
                    this.func_151550_a(p_74875_1_, Blocks.stonebrick, 0, 5, 3, 5, p_74875_3_);
                    this.func_151550_a(p_74875_1_, Blocks.torch, 0, 4, 3, 5, p_74875_3_);
                    this.func_151550_a(p_74875_1_, Blocks.torch, 0, 6, 3, 5, p_74875_3_);
                    this.func_151550_a(p_74875_1_, Blocks.torch, 0, 5, 3, 4, p_74875_3_);
                    this.func_151550_a(p_74875_1_, Blocks.torch, 0, 5, 3, 6, p_74875_3_);
                    this.func_151550_a(p_74875_1_, Blocks.stone_slab, 0, 4, 1, 4, p_74875_3_);
                    this.func_151550_a(p_74875_1_, Blocks.stone_slab, 0, 4, 1, 5, p_74875_3_);
                    this.func_151550_a(p_74875_1_, Blocks.stone_slab, 0, 4, 1, 6, p_74875_3_);
                    this.func_151550_a(p_74875_1_, Blocks.stone_slab, 0, 6, 1, 4, p_74875_3_);
                    this.func_151550_a(p_74875_1_, Blocks.stone_slab, 0, 6, 1, 5, p_74875_3_);
                    this.func_151550_a(p_74875_1_, Blocks.stone_slab, 0, 6, 1, 6, p_74875_3_);
                    this.func_151550_a(p_74875_1_, Blocks.stone_slab, 0, 5, 1, 4, p_74875_3_);
                    this.func_151550_a(p_74875_1_, Blocks.stone_slab, 0, 5, 1, 6, p_74875_3_);
                    break;
                }
                case 1: {
                    for (int var4 = 0; var4 < 5; ++var4) {
                        this.func_151550_a(p_74875_1_, Blocks.stonebrick, 0, 3, 1, 3 + var4, p_74875_3_);
                        this.func_151550_a(p_74875_1_, Blocks.stonebrick, 0, 7, 1, 3 + var4, p_74875_3_);
                        this.func_151550_a(p_74875_1_, Blocks.stonebrick, 0, 3 + var4, 1, 3, p_74875_3_);
                        this.func_151550_a(p_74875_1_, Blocks.stonebrick, 0, 3 + var4, 1, 7, p_74875_3_);
                    }
                    this.func_151550_a(p_74875_1_, Blocks.stonebrick, 0, 5, 1, 5, p_74875_3_);
                    this.func_151550_a(p_74875_1_, Blocks.stonebrick, 0, 5, 2, 5, p_74875_3_);
                    this.func_151550_a(p_74875_1_, Blocks.stonebrick, 0, 5, 3, 5, p_74875_3_);
                    this.func_151550_a(p_74875_1_, Blocks.flowing_water, 0, 5, 4, 5, p_74875_3_);
                    break;
                }
                case 2: {
                    for (int var4 = 1; var4 <= 9; ++var4) {
                        this.func_151550_a(p_74875_1_, Blocks.cobblestone, 0, 1, 3, var4, p_74875_3_);
                        this.func_151550_a(p_74875_1_, Blocks.cobblestone, 0, 9, 3, var4, p_74875_3_);
                    }
                    for (int var4 = 1; var4 <= 9; ++var4) {
                        this.func_151550_a(p_74875_1_, Blocks.cobblestone, 0, var4, 3, 1, p_74875_3_);
                        this.func_151550_a(p_74875_1_, Blocks.cobblestone, 0, var4, 3, 9, p_74875_3_);
                    }
                    this.func_151550_a(p_74875_1_, Blocks.cobblestone, 0, 5, 1, 4, p_74875_3_);
                    this.func_151550_a(p_74875_1_, Blocks.cobblestone, 0, 5, 1, 6, p_74875_3_);
                    this.func_151550_a(p_74875_1_, Blocks.cobblestone, 0, 5, 3, 4, p_74875_3_);
                    this.func_151550_a(p_74875_1_, Blocks.cobblestone, 0, 5, 3, 6, p_74875_3_);
                    this.func_151550_a(p_74875_1_, Blocks.cobblestone, 0, 4, 1, 5, p_74875_3_);
                    this.func_151550_a(p_74875_1_, Blocks.cobblestone, 0, 6, 1, 5, p_74875_3_);
                    this.func_151550_a(p_74875_1_, Blocks.cobblestone, 0, 4, 3, 5, p_74875_3_);
                    this.func_151550_a(p_74875_1_, Blocks.cobblestone, 0, 6, 3, 5, p_74875_3_);
                    for (int var4 = 1; var4 <= 3; ++var4) {
                        this.func_151550_a(p_74875_1_, Blocks.cobblestone, 0, 4, var4, 4, p_74875_3_);
                        this.func_151550_a(p_74875_1_, Blocks.cobblestone, 0, 6, var4, 4, p_74875_3_);
                        this.func_151550_a(p_74875_1_, Blocks.cobblestone, 0, 4, var4, 6, p_74875_3_);
                        this.func_151550_a(p_74875_1_, Blocks.cobblestone, 0, 6, var4, 6, p_74875_3_);
                    }
                    this.func_151550_a(p_74875_1_, Blocks.torch, 0, 5, 3, 5, p_74875_3_);
                    for (int var4 = 2; var4 <= 8; ++var4) {
                        this.func_151550_a(p_74875_1_, Blocks.planks, 0, 2, 3, var4, p_74875_3_);
                        this.func_151550_a(p_74875_1_, Blocks.planks, 0, 3, 3, var4, p_74875_3_);
                        if (var4 <= 3 || var4 >= 7) {
                            this.func_151550_a(p_74875_1_, Blocks.planks, 0, 4, 3, var4, p_74875_3_);
                            this.func_151550_a(p_74875_1_, Blocks.planks, 0, 5, 3, var4, p_74875_3_);
                            this.func_151550_a(p_74875_1_, Blocks.planks, 0, 6, 3, var4, p_74875_3_);
                        }
                        this.func_151550_a(p_74875_1_, Blocks.planks, 0, 7, 3, var4, p_74875_3_);
                        this.func_151550_a(p_74875_1_, Blocks.planks, 0, 8, 3, var4, p_74875_3_);
                    }
                    this.func_151550_a(p_74875_1_, Blocks.ladder, this.func_151555_a(Blocks.ladder, 4), 9, 1, 3, p_74875_3_);
                    this.func_151550_a(p_74875_1_, Blocks.ladder, this.func_151555_a(Blocks.ladder, 4), 9, 2, 3, p_74875_3_);
                    this.func_151550_a(p_74875_1_, Blocks.ladder, this.func_151555_a(Blocks.ladder, 4), 9, 3, 3, p_74875_3_);
                    this.generateStructureChestContents(p_74875_1_, p_74875_3_, p_74875_2_, 3, 4, 8, WeightedRandomChestContent.func_92080_a(RoomCrossing.strongholdRoomCrossingChestContents, Items.enchanted_book.func_92114_b(p_74875_2_)), 1 + p_74875_2_.nextInt(4));
                    break;
                }
            }
            return true;
        }
        
        static {
            strongholdRoomCrossingChestContents = new WeightedRandomChestContent[] { new WeightedRandomChestContent(Items.iron_ingot, 0, 1, 5, 10), new WeightedRandomChestContent(Items.gold_ingot, 0, 1, 3, 5), new WeightedRandomChestContent(Items.redstone, 0, 4, 9, 5), new WeightedRandomChestContent(Items.coal, 0, 3, 8, 10), new WeightedRandomChestContent(Items.bread, 0, 1, 3, 15), new WeightedRandomChestContent(Items.apple, 0, 1, 3, 15), new WeightedRandomChestContent(Items.iron_pickaxe, 0, 1, 1, 1) };
        }
    }
    
    public static class Stairs extends Stronghold
    {
        private boolean field_75024_a;
        private static final String __OBFID = "CL_00000498";
        
        public Stairs() {
        }
        
        public Stairs(final int p_i2081_1_, final Random p_i2081_2_, final int p_i2081_3_, final int p_i2081_4_) {
            super(p_i2081_1_);
            this.field_75024_a = true;
            this.coordBaseMode = p_i2081_2_.nextInt(4);
            this.field_143013_d = Door.OPENING;
            switch (this.coordBaseMode) {
                case 0:
                case 2: {
                    this.boundingBox = new StructureBoundingBox(p_i2081_3_, 64, p_i2081_4_, p_i2081_3_ + 5 - 1, 74, p_i2081_4_ + 5 - 1);
                    break;
                }
                default: {
                    this.boundingBox = new StructureBoundingBox(p_i2081_3_, 64, p_i2081_4_, p_i2081_3_ + 5 - 1, 74, p_i2081_4_ + 5 - 1);
                    break;
                }
            }
        }
        
        public Stairs(final int p_i2082_1_, final Random p_i2082_2_, final StructureBoundingBox p_i2082_3_, final int p_i2082_4_) {
            super(p_i2082_1_);
            this.field_75024_a = false;
            this.coordBaseMode = p_i2082_4_;
            this.field_143013_d = this.getRandomDoor(p_i2082_2_);
            this.boundingBox = p_i2082_3_;
        }
        
        @Override
        protected void func_143012_a(final NBTTagCompound p_143012_1_) {
            super.func_143012_a(p_143012_1_);
            p_143012_1_.setBoolean("Source", this.field_75024_a);
        }
        
        @Override
        protected void func_143011_b(final NBTTagCompound p_143011_1_) {
            super.func_143011_b(p_143011_1_);
            this.field_75024_a = p_143011_1_.getBoolean("Source");
        }
        
        @Override
        public void buildComponent(final StructureComponent p_74861_1_, final List p_74861_2_, final Random p_74861_3_) {
            if (this.field_75024_a) {
                StructureStrongholdPieces.strongComponentType = Crossing.class;
            }
            this.getNextComponentNormal((Stairs2)p_74861_1_, p_74861_2_, p_74861_3_, 1, 1);
        }
        
        public static Stairs getStrongholdStairsComponent(final List p_75022_0_, final Random p_75022_1_, final int p_75022_2_, final int p_75022_3_, final int p_75022_4_, final int p_75022_5_, final int p_75022_6_) {
            final StructureBoundingBox var7 = StructureBoundingBox.getComponentToAddBoundingBox(p_75022_2_, p_75022_3_, p_75022_4_, -1, -7, 0, 5, 11, 5, p_75022_5_);
            return (Stronghold.canStrongholdGoDeeper(var7) && StructureComponent.findIntersecting(p_75022_0_, var7) == null) ? new Stairs(p_75022_6_, p_75022_1_, var7, p_75022_5_) : null;
        }
        
        @Override
        public boolean addComponentParts(final World p_74875_1_, final Random p_74875_2_, final StructureBoundingBox p_74875_3_) {
            if (this.isLiquidInStructureBoundingBox(p_74875_1_, p_74875_3_)) {
                return false;
            }
            this.fillWithRandomizedBlocks(p_74875_1_, p_74875_3_, 0, 0, 0, 4, 10, 4, true, p_74875_2_, StructureStrongholdPieces.strongholdStones);
            this.placeDoor(p_74875_1_, p_74875_2_, p_74875_3_, this.field_143013_d, 1, 7, 0);
            this.placeDoor(p_74875_1_, p_74875_2_, p_74875_3_, Door.OPENING, 1, 1, 4);
            this.func_151550_a(p_74875_1_, Blocks.stonebrick, 0, 2, 6, 1, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.stonebrick, 0, 1, 5, 1, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.stone_slab, 0, 1, 6, 1, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.stonebrick, 0, 1, 5, 2, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.stonebrick, 0, 1, 4, 3, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.stone_slab, 0, 1, 5, 3, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.stonebrick, 0, 2, 4, 3, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.stonebrick, 0, 3, 3, 3, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.stone_slab, 0, 3, 4, 3, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.stonebrick, 0, 3, 3, 2, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.stonebrick, 0, 3, 2, 1, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.stone_slab, 0, 3, 3, 1, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.stonebrick, 0, 2, 2, 1, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.stonebrick, 0, 1, 1, 1, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.stone_slab, 0, 1, 2, 1, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.stonebrick, 0, 1, 1, 2, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.stone_slab, 0, 1, 1, 3, p_74875_3_);
            return true;
        }
    }
    
    public static class Stairs2 extends Stairs
    {
        public PieceWeight strongholdPieceWeight;
        public PortalRoom strongholdPortalRoom;
        public List field_75026_c;
        private static final String __OBFID = "CL_00000499";
        
        public Stairs2() {
            this.field_75026_c = new ArrayList();
        }
        
        public Stairs2(final int p_i2083_1_, final Random p_i2083_2_, final int p_i2083_3_, final int p_i2083_4_) {
            super(0, p_i2083_2_, p_i2083_3_, p_i2083_4_);
            this.field_75026_c = new ArrayList();
        }
        
        @Override
        public ChunkPosition func_151553_a() {
            return (this.strongholdPortalRoom != null) ? this.strongholdPortalRoom.func_151553_a() : super.func_151553_a();
        }
    }
    
    public static class StairsStraight extends Stronghold
    {
        private static final String __OBFID = "CL_00000501";
        
        public StairsStraight() {
        }
        
        public StairsStraight(final int p_i2085_1_, final Random p_i2085_2_, final StructureBoundingBox p_i2085_3_, final int p_i2085_4_) {
            super(p_i2085_1_);
            this.coordBaseMode = p_i2085_4_;
            this.field_143013_d = this.getRandomDoor(p_i2085_2_);
            this.boundingBox = p_i2085_3_;
        }
        
        @Override
        public void buildComponent(final StructureComponent p_74861_1_, final List p_74861_2_, final Random p_74861_3_) {
            this.getNextComponentNormal((Stairs2)p_74861_1_, p_74861_2_, p_74861_3_, 1, 1);
        }
        
        public static StairsStraight findValidPlacement(final List p_75028_0_, final Random p_75028_1_, final int p_75028_2_, final int p_75028_3_, final int p_75028_4_, final int p_75028_5_, final int p_75028_6_) {
            final StructureBoundingBox var7 = StructureBoundingBox.getComponentToAddBoundingBox(p_75028_2_, p_75028_3_, p_75028_4_, -1, -7, 0, 5, 11, 8, p_75028_5_);
            return (Stronghold.canStrongholdGoDeeper(var7) && StructureComponent.findIntersecting(p_75028_0_, var7) == null) ? new StairsStraight(p_75028_6_, p_75028_1_, var7, p_75028_5_) : null;
        }
        
        @Override
        public boolean addComponentParts(final World p_74875_1_, final Random p_74875_2_, final StructureBoundingBox p_74875_3_) {
            if (this.isLiquidInStructureBoundingBox(p_74875_1_, p_74875_3_)) {
                return false;
            }
            this.fillWithRandomizedBlocks(p_74875_1_, p_74875_3_, 0, 0, 0, 4, 10, 7, true, p_74875_2_, StructureStrongholdPieces.strongholdStones);
            this.placeDoor(p_74875_1_, p_74875_2_, p_74875_3_, this.field_143013_d, 1, 7, 0);
            this.placeDoor(p_74875_1_, p_74875_2_, p_74875_3_, Door.OPENING, 1, 1, 7);
            final int var4 = this.func_151555_a(Blocks.stone_stairs, 2);
            for (int var5 = 0; var5 < 6; ++var5) {
                this.func_151550_a(p_74875_1_, Blocks.stone_stairs, var4, 1, 6 - var5, 1 + var5, p_74875_3_);
                this.func_151550_a(p_74875_1_, Blocks.stone_stairs, var4, 2, 6 - var5, 1 + var5, p_74875_3_);
                this.func_151550_a(p_74875_1_, Blocks.stone_stairs, var4, 3, 6 - var5, 1 + var5, p_74875_3_);
                if (var5 < 5) {
                    this.func_151550_a(p_74875_1_, Blocks.stonebrick, 0, 1, 5 - var5, 1 + var5, p_74875_3_);
                    this.func_151550_a(p_74875_1_, Blocks.stonebrick, 0, 2, 5 - var5, 1 + var5, p_74875_3_);
                    this.func_151550_a(p_74875_1_, Blocks.stonebrick, 0, 3, 5 - var5, 1 + var5, p_74875_3_);
                }
            }
            return true;
        }
    }
    
    static class Stones extends StructureComponent.BlockSelector
    {
        private static final String __OBFID = "CL_00000497";
        
        private Stones() {
        }
        
        @Override
        public void selectBlocks(final Random p_75062_1_, final int p_75062_2_, final int p_75062_3_, final int p_75062_4_, final boolean p_75062_5_) {
            if (p_75062_5_) {
                this.field_151562_a = Blocks.stonebrick;
                final float var6 = p_75062_1_.nextFloat();
                if (var6 < 0.2f) {
                    this.selectedBlockMetaData = 2;
                }
                else if (var6 < 0.5f) {
                    this.selectedBlockMetaData = 1;
                }
                else if (var6 < 0.55f) {
                    this.field_151562_a = Blocks.monster_egg;
                    this.selectedBlockMetaData = 2;
                }
                else {
                    this.selectedBlockMetaData = 0;
                }
            }
            else {
                this.field_151562_a = Blocks.air;
                this.selectedBlockMetaData = 0;
            }
        }
        
        Stones(final Object p_i2080_1_) {
            this();
        }
    }
    
    public static class Straight extends Stronghold
    {
        private boolean expandsX;
        private boolean expandsZ;
        private static final String __OBFID = "CL_00000500";
        
        public Straight() {
        }
        
        public Straight(final int p_i2084_1_, final Random p_i2084_2_, final StructureBoundingBox p_i2084_3_, final int p_i2084_4_) {
            super(p_i2084_1_);
            this.coordBaseMode = p_i2084_4_;
            this.field_143013_d = this.getRandomDoor(p_i2084_2_);
            this.boundingBox = p_i2084_3_;
            this.expandsX = (p_i2084_2_.nextInt(2) == 0);
            this.expandsZ = (p_i2084_2_.nextInt(2) == 0);
        }
        
        @Override
        protected void func_143012_a(final NBTTagCompound p_143012_1_) {
            super.func_143012_a(p_143012_1_);
            p_143012_1_.setBoolean("Left", this.expandsX);
            p_143012_1_.setBoolean("Right", this.expandsZ);
        }
        
        @Override
        protected void func_143011_b(final NBTTagCompound p_143011_1_) {
            super.func_143011_b(p_143011_1_);
            this.expandsX = p_143011_1_.getBoolean("Left");
            this.expandsZ = p_143011_1_.getBoolean("Right");
        }
        
        @Override
        public void buildComponent(final StructureComponent p_74861_1_, final List p_74861_2_, final Random p_74861_3_) {
            this.getNextComponentNormal((Stairs2)p_74861_1_, p_74861_2_, p_74861_3_, 1, 1);
            if (this.expandsX) {
                this.getNextComponentX((Stairs2)p_74861_1_, p_74861_2_, p_74861_3_, 1, 2);
            }
            if (this.expandsZ) {
                this.getNextComponentZ((Stairs2)p_74861_1_, p_74861_2_, p_74861_3_, 1, 2);
            }
        }
        
        public static Straight findValidPlacement(final List p_75018_0_, final Random p_75018_1_, final int p_75018_2_, final int p_75018_3_, final int p_75018_4_, final int p_75018_5_, final int p_75018_6_) {
            final StructureBoundingBox var7 = StructureBoundingBox.getComponentToAddBoundingBox(p_75018_2_, p_75018_3_, p_75018_4_, -1, -1, 0, 5, 5, 7, p_75018_5_);
            return (Stronghold.canStrongholdGoDeeper(var7) && StructureComponent.findIntersecting(p_75018_0_, var7) == null) ? new Straight(p_75018_6_, p_75018_1_, var7, p_75018_5_) : null;
        }
        
        @Override
        public boolean addComponentParts(final World p_74875_1_, final Random p_74875_2_, final StructureBoundingBox p_74875_3_) {
            if (this.isLiquidInStructureBoundingBox(p_74875_1_, p_74875_3_)) {
                return false;
            }
            this.fillWithRandomizedBlocks(p_74875_1_, p_74875_3_, 0, 0, 0, 4, 4, 6, true, p_74875_2_, StructureStrongholdPieces.strongholdStones);
            this.placeDoor(p_74875_1_, p_74875_2_, p_74875_3_, this.field_143013_d, 1, 1, 0);
            this.placeDoor(p_74875_1_, p_74875_2_, p_74875_3_, Door.OPENING, 1, 1, 6);
            this.func_151552_a(p_74875_1_, p_74875_3_, p_74875_2_, 0.1f, 1, 2, 1, Blocks.torch, 0);
            this.func_151552_a(p_74875_1_, p_74875_3_, p_74875_2_, 0.1f, 3, 2, 1, Blocks.torch, 0);
            this.func_151552_a(p_74875_1_, p_74875_3_, p_74875_2_, 0.1f, 1, 2, 5, Blocks.torch, 0);
            this.func_151552_a(p_74875_1_, p_74875_3_, p_74875_2_, 0.1f, 3, 2, 5, Blocks.torch, 0);
            if (this.expandsX) {
                this.func_151549_a(p_74875_1_, p_74875_3_, 0, 1, 2, 0, 3, 4, Blocks.air, Blocks.air, false);
            }
            if (this.expandsZ) {
                this.func_151549_a(p_74875_1_, p_74875_3_, 4, 1, 2, 4, 3, 4, Blocks.air, Blocks.air, false);
            }
            return true;
        }
    }
    
    abstract static class Stronghold extends StructureComponent
    {
        protected Door field_143013_d;
        private static final String __OBFID = "CL_00000503";
        
        public Stronghold() {
            this.field_143013_d = Door.OPENING;
        }
        
        protected Stronghold(final int p_i2087_1_) {
            super(p_i2087_1_);
            this.field_143013_d = Door.OPENING;
        }
        
        @Override
        protected void func_143012_a(final NBTTagCompound p_143012_1_) {
            p_143012_1_.setString("EntryDoor", this.field_143013_d.name());
        }
        
        @Override
        protected void func_143011_b(final NBTTagCompound p_143011_1_) {
            this.field_143013_d = Door.valueOf(p_143011_1_.getString("EntryDoor"));
        }
        
        protected void placeDoor(final World p_74990_1_, final Random p_74990_2_, final StructureBoundingBox p_74990_3_, final Door p_74990_4_, final int p_74990_5_, final int p_74990_6_, final int p_74990_7_) {
            switch (SwitchDoor.doorEnum[p_74990_4_.ordinal()]) {
                default: {
                    this.func_151549_a(p_74990_1_, p_74990_3_, p_74990_5_, p_74990_6_, p_74990_7_, p_74990_5_ + 3 - 1, p_74990_6_ + 3 - 1, p_74990_7_, Blocks.air, Blocks.air, false);
                    break;
                }
                case 2: {
                    this.func_151550_a(p_74990_1_, Blocks.stonebrick, 0, p_74990_5_, p_74990_6_, p_74990_7_, p_74990_3_);
                    this.func_151550_a(p_74990_1_, Blocks.stonebrick, 0, p_74990_5_, p_74990_6_ + 1, p_74990_7_, p_74990_3_);
                    this.func_151550_a(p_74990_1_, Blocks.stonebrick, 0, p_74990_5_, p_74990_6_ + 2, p_74990_7_, p_74990_3_);
                    this.func_151550_a(p_74990_1_, Blocks.stonebrick, 0, p_74990_5_ + 1, p_74990_6_ + 2, p_74990_7_, p_74990_3_);
                    this.func_151550_a(p_74990_1_, Blocks.stonebrick, 0, p_74990_5_ + 2, p_74990_6_ + 2, p_74990_7_, p_74990_3_);
                    this.func_151550_a(p_74990_1_, Blocks.stonebrick, 0, p_74990_5_ + 2, p_74990_6_ + 1, p_74990_7_, p_74990_3_);
                    this.func_151550_a(p_74990_1_, Blocks.stonebrick, 0, p_74990_5_ + 2, p_74990_6_, p_74990_7_, p_74990_3_);
                    this.func_151550_a(p_74990_1_, Blocks.wooden_door, 0, p_74990_5_ + 1, p_74990_6_, p_74990_7_, p_74990_3_);
                    this.func_151550_a(p_74990_1_, Blocks.wooden_door, 8, p_74990_5_ + 1, p_74990_6_ + 1, p_74990_7_, p_74990_3_);
                    break;
                }
                case 3: {
                    this.func_151550_a(p_74990_1_, Blocks.air, 0, p_74990_5_ + 1, p_74990_6_, p_74990_7_, p_74990_3_);
                    this.func_151550_a(p_74990_1_, Blocks.air, 0, p_74990_5_ + 1, p_74990_6_ + 1, p_74990_7_, p_74990_3_);
                    this.func_151550_a(p_74990_1_, Blocks.iron_bars, 0, p_74990_5_, p_74990_6_, p_74990_7_, p_74990_3_);
                    this.func_151550_a(p_74990_1_, Blocks.iron_bars, 0, p_74990_5_, p_74990_6_ + 1, p_74990_7_, p_74990_3_);
                    this.func_151550_a(p_74990_1_, Blocks.iron_bars, 0, p_74990_5_, p_74990_6_ + 2, p_74990_7_, p_74990_3_);
                    this.func_151550_a(p_74990_1_, Blocks.iron_bars, 0, p_74990_5_ + 1, p_74990_6_ + 2, p_74990_7_, p_74990_3_);
                    this.func_151550_a(p_74990_1_, Blocks.iron_bars, 0, p_74990_5_ + 2, p_74990_6_ + 2, p_74990_7_, p_74990_3_);
                    this.func_151550_a(p_74990_1_, Blocks.iron_bars, 0, p_74990_5_ + 2, p_74990_6_ + 1, p_74990_7_, p_74990_3_);
                    this.func_151550_a(p_74990_1_, Blocks.iron_bars, 0, p_74990_5_ + 2, p_74990_6_, p_74990_7_, p_74990_3_);
                    break;
                }
                case 4: {
                    this.func_151550_a(p_74990_1_, Blocks.stonebrick, 0, p_74990_5_, p_74990_6_, p_74990_7_, p_74990_3_);
                    this.func_151550_a(p_74990_1_, Blocks.stonebrick, 0, p_74990_5_, p_74990_6_ + 1, p_74990_7_, p_74990_3_);
                    this.func_151550_a(p_74990_1_, Blocks.stonebrick, 0, p_74990_5_, p_74990_6_ + 2, p_74990_7_, p_74990_3_);
                    this.func_151550_a(p_74990_1_, Blocks.stonebrick, 0, p_74990_5_ + 1, p_74990_6_ + 2, p_74990_7_, p_74990_3_);
                    this.func_151550_a(p_74990_1_, Blocks.stonebrick, 0, p_74990_5_ + 2, p_74990_6_ + 2, p_74990_7_, p_74990_3_);
                    this.func_151550_a(p_74990_1_, Blocks.stonebrick, 0, p_74990_5_ + 2, p_74990_6_ + 1, p_74990_7_, p_74990_3_);
                    this.func_151550_a(p_74990_1_, Blocks.stonebrick, 0, p_74990_5_ + 2, p_74990_6_, p_74990_7_, p_74990_3_);
                    this.func_151550_a(p_74990_1_, Blocks.iron_door, 0, p_74990_5_ + 1, p_74990_6_, p_74990_7_, p_74990_3_);
                    this.func_151550_a(p_74990_1_, Blocks.iron_door, 8, p_74990_5_ + 1, p_74990_6_ + 1, p_74990_7_, p_74990_3_);
                    this.func_151550_a(p_74990_1_, Blocks.stone_button, this.func_151555_a(Blocks.stone_button, 4), p_74990_5_ + 2, p_74990_6_ + 1, p_74990_7_ + 1, p_74990_3_);
                    this.func_151550_a(p_74990_1_, Blocks.stone_button, this.func_151555_a(Blocks.stone_button, 3), p_74990_5_ + 2, p_74990_6_ + 1, p_74990_7_ - 1, p_74990_3_);
                    break;
                }
            }
        }
        
        protected Door getRandomDoor(final Random p_74988_1_) {
            final int var2 = p_74988_1_.nextInt(5);
            switch (var2) {
                default: {
                    return Door.OPENING;
                }
                case 2: {
                    return Door.WOOD_DOOR;
                }
                case 3: {
                    return Door.GRATES;
                }
                case 4: {
                    return Door.IRON_DOOR;
                }
            }
        }
        
        protected StructureComponent getNextComponentNormal(final Stairs2 p_74986_1_, final List p_74986_2_, final Random p_74986_3_, final int p_74986_4_, final int p_74986_5_) {
            switch (this.coordBaseMode) {
                case 0: {
                    return getNextValidComponent(p_74986_1_, p_74986_2_, p_74986_3_, this.boundingBox.minX + p_74986_4_, this.boundingBox.minY + p_74986_5_, this.boundingBox.maxZ + 1, this.coordBaseMode, this.getComponentType());
                }
                case 1: {
                    return getNextValidComponent(p_74986_1_, p_74986_2_, p_74986_3_, this.boundingBox.minX - 1, this.boundingBox.minY + p_74986_5_, this.boundingBox.minZ + p_74986_4_, this.coordBaseMode, this.getComponentType());
                }
                case 2: {
                    return getNextValidComponent(p_74986_1_, p_74986_2_, p_74986_3_, this.boundingBox.minX + p_74986_4_, this.boundingBox.minY + p_74986_5_, this.boundingBox.minZ - 1, this.coordBaseMode, this.getComponentType());
                }
                case 3: {
                    return getNextValidComponent(p_74986_1_, p_74986_2_, p_74986_3_, this.boundingBox.maxX + 1, this.boundingBox.minY + p_74986_5_, this.boundingBox.minZ + p_74986_4_, this.coordBaseMode, this.getComponentType());
                }
                default: {
                    return null;
                }
            }
        }
        
        protected StructureComponent getNextComponentX(final Stairs2 p_74989_1_, final List p_74989_2_, final Random p_74989_3_, final int p_74989_4_, final int p_74989_5_) {
            switch (this.coordBaseMode) {
                case 0: {
                    return getNextValidComponent(p_74989_1_, p_74989_2_, p_74989_3_, this.boundingBox.minX - 1, this.boundingBox.minY + p_74989_4_, this.boundingBox.minZ + p_74989_5_, 1, this.getComponentType());
                }
                case 1: {
                    return getNextValidComponent(p_74989_1_, p_74989_2_, p_74989_3_, this.boundingBox.minX + p_74989_5_, this.boundingBox.minY + p_74989_4_, this.boundingBox.minZ - 1, 2, this.getComponentType());
                }
                case 2: {
                    return getNextValidComponent(p_74989_1_, p_74989_2_, p_74989_3_, this.boundingBox.minX - 1, this.boundingBox.minY + p_74989_4_, this.boundingBox.minZ + p_74989_5_, 1, this.getComponentType());
                }
                case 3: {
                    return getNextValidComponent(p_74989_1_, p_74989_2_, p_74989_3_, this.boundingBox.minX + p_74989_5_, this.boundingBox.minY + p_74989_4_, this.boundingBox.minZ - 1, 2, this.getComponentType());
                }
                default: {
                    return null;
                }
            }
        }
        
        protected StructureComponent getNextComponentZ(final Stairs2 p_74987_1_, final List p_74987_2_, final Random p_74987_3_, final int p_74987_4_, final int p_74987_5_) {
            switch (this.coordBaseMode) {
                case 0: {
                    return getNextValidComponent(p_74987_1_, p_74987_2_, p_74987_3_, this.boundingBox.maxX + 1, this.boundingBox.minY + p_74987_4_, this.boundingBox.minZ + p_74987_5_, 3, this.getComponentType());
                }
                case 1: {
                    return getNextValidComponent(p_74987_1_, p_74987_2_, p_74987_3_, this.boundingBox.minX + p_74987_5_, this.boundingBox.minY + p_74987_4_, this.boundingBox.maxZ + 1, 0, this.getComponentType());
                }
                case 2: {
                    return getNextValidComponent(p_74987_1_, p_74987_2_, p_74987_3_, this.boundingBox.maxX + 1, this.boundingBox.minY + p_74987_4_, this.boundingBox.minZ + p_74987_5_, 3, this.getComponentType());
                }
                case 3: {
                    return getNextValidComponent(p_74987_1_, p_74987_2_, p_74987_3_, this.boundingBox.minX + p_74987_5_, this.boundingBox.minY + p_74987_4_, this.boundingBox.maxZ + 1, 0, this.getComponentType());
                }
                default: {
                    return null;
                }
            }
        }
        
        protected static boolean canStrongholdGoDeeper(final StructureBoundingBox p_74991_0_) {
            return p_74991_0_ != null && p_74991_0_.minY > 10;
        }
        
        public enum Door
        {
            OPENING("OPENING", 0), 
            WOOD_DOOR("WOOD_DOOR", 1), 
            GRATES("GRATES", 2), 
            IRON_DOOR("IRON_DOOR", 3);
            
            private static final Door[] $VALUES;
            private static final String __OBFID = "CL_00000504";
            
            private Door(final String p_i2086_1_, final int p_i2086_2_) {
            }
            
            static {
                $VALUES = new Door[] { Door.OPENING, Door.WOOD_DOOR, Door.GRATES, Door.IRON_DOOR };
            }
        }
    }
    
    static final class SwitchDoor
    {
        static final int[] doorEnum;
        private static final String __OBFID = "CL_00000486";
        
        static {
            doorEnum = new int[Stronghold.Door.values().length];
            try {
                SwitchDoor.doorEnum[Stronghold.Door.OPENING.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                SwitchDoor.doorEnum[Stronghold.Door.WOOD_DOOR.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                SwitchDoor.doorEnum[Stronghold.Door.GRATES.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            try {
                SwitchDoor.doorEnum[Stronghold.Door.IRON_DOOR.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
        }
    }
}
