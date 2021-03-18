package net.minecraft.world.gen.structure;

import java.util.*;
import net.minecraft.world.*;
import net.minecraft.block.material.*;
import net.minecraft.block.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.world.biome.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.*;

public class StructureVillagePieces
{
    private static final String __OBFID = "CL_00000516";
    
    public static void func_143016_a() {
        MapGenStructureIO.func_143031_a(House1.class, "ViBH");
        MapGenStructureIO.func_143031_a(Field1.class, "ViDF");
        MapGenStructureIO.func_143031_a(Field2.class, "ViF");
        MapGenStructureIO.func_143031_a(Torch.class, "ViL");
        MapGenStructureIO.func_143031_a(Hall.class, "ViPH");
        MapGenStructureIO.func_143031_a(House4Garden.class, "ViSH");
        MapGenStructureIO.func_143031_a(WoodHut.class, "ViSmH");
        MapGenStructureIO.func_143031_a(Church.class, "ViST");
        MapGenStructureIO.func_143031_a(House2.class, "ViS");
        MapGenStructureIO.func_143031_a(Start.class, "ViStart");
        MapGenStructureIO.func_143031_a(Path.class, "ViSR");
        MapGenStructureIO.func_143031_a(House3.class, "ViTRH");
        MapGenStructureIO.func_143031_a(Well.class, "ViW");
    }
    
    public static List getStructureVillageWeightedPieceList(final Random p_75084_0_, final int p_75084_1_) {
        final ArrayList var2 = new ArrayList();
        var2.add(new PieceWeight(House4Garden.class, 4, MathHelper.getRandomIntegerInRange(p_75084_0_, 2 + p_75084_1_, 4 + p_75084_1_ * 2)));
        var2.add(new PieceWeight(Church.class, 20, MathHelper.getRandomIntegerInRange(p_75084_0_, 0 + p_75084_1_, 1 + p_75084_1_)));
        var2.add(new PieceWeight(House1.class, 20, MathHelper.getRandomIntegerInRange(p_75084_0_, 0 + p_75084_1_, 2 + p_75084_1_)));
        var2.add(new PieceWeight(WoodHut.class, 3, MathHelper.getRandomIntegerInRange(p_75084_0_, 2 + p_75084_1_, 5 + p_75084_1_ * 3)));
        var2.add(new PieceWeight(Hall.class, 15, MathHelper.getRandomIntegerInRange(p_75084_0_, 0 + p_75084_1_, 2 + p_75084_1_)));
        var2.add(new PieceWeight(Field1.class, 3, MathHelper.getRandomIntegerInRange(p_75084_0_, 1 + p_75084_1_, 4 + p_75084_1_)));
        var2.add(new PieceWeight(Field2.class, 3, MathHelper.getRandomIntegerInRange(p_75084_0_, 2 + p_75084_1_, 4 + p_75084_1_ * 2)));
        var2.add(new PieceWeight(House2.class, 15, MathHelper.getRandomIntegerInRange(p_75084_0_, 0, 1 + p_75084_1_)));
        var2.add(new PieceWeight(House3.class, 8, MathHelper.getRandomIntegerInRange(p_75084_0_, 0 + p_75084_1_, 3 + p_75084_1_ * 2)));
        final Iterator var3 = var2.iterator();
        while (var3.hasNext()) {
            if (var3.next().villagePiecesLimit == 0) {
                var3.remove();
            }
        }
        return var2;
    }
    
    private static int func_75079_a(final List p_75079_0_) {
        boolean var1 = false;
        int var2 = 0;
        for (final PieceWeight var4 : p_75079_0_) {
            if (var4.villagePiecesLimit > 0 && var4.villagePiecesSpawned < var4.villagePiecesLimit) {
                var1 = true;
            }
            var2 += var4.villagePieceWeight;
        }
        return var1 ? var2 : -1;
    }
    
    private static Village func_75083_a(final Start p_75083_0_, final PieceWeight p_75083_1_, final List p_75083_2_, final Random p_75083_3_, final int p_75083_4_, final int p_75083_5_, final int p_75083_6_, final int p_75083_7_, final int p_75083_8_) {
        final Class var9 = p_75083_1_.villagePieceClass;
        Object var10 = null;
        if (var9 == House4Garden.class) {
            var10 = House4Garden.func_74912_a(p_75083_0_, p_75083_2_, p_75083_3_, p_75083_4_, p_75083_5_, p_75083_6_, p_75083_7_, p_75083_8_);
        }
        else if (var9 == Church.class) {
            var10 = Church.func_74919_a(p_75083_0_, p_75083_2_, p_75083_3_, p_75083_4_, p_75083_5_, p_75083_6_, p_75083_7_, p_75083_8_);
        }
        else if (var9 == House1.class) {
            var10 = House1.func_74898_a(p_75083_0_, p_75083_2_, p_75083_3_, p_75083_4_, p_75083_5_, p_75083_6_, p_75083_7_, p_75083_8_);
        }
        else if (var9 == WoodHut.class) {
            var10 = WoodHut.func_74908_a(p_75083_0_, p_75083_2_, p_75083_3_, p_75083_4_, p_75083_5_, p_75083_6_, p_75083_7_, p_75083_8_);
        }
        else if (var9 == Hall.class) {
            var10 = Hall.func_74906_a(p_75083_0_, p_75083_2_, p_75083_3_, p_75083_4_, p_75083_5_, p_75083_6_, p_75083_7_, p_75083_8_);
        }
        else if (var9 == Field1.class) {
            var10 = Field1.func_74900_a(p_75083_0_, p_75083_2_, p_75083_3_, p_75083_4_, p_75083_5_, p_75083_6_, p_75083_7_, p_75083_8_);
        }
        else if (var9 == Field2.class) {
            var10 = Field2.func_74902_a(p_75083_0_, p_75083_2_, p_75083_3_, p_75083_4_, p_75083_5_, p_75083_6_, p_75083_7_, p_75083_8_);
        }
        else if (var9 == House2.class) {
            var10 = House2.func_74915_a(p_75083_0_, p_75083_2_, p_75083_3_, p_75083_4_, p_75083_5_, p_75083_6_, p_75083_7_, p_75083_8_);
        }
        else if (var9 == House3.class) {
            var10 = House3.func_74921_a(p_75083_0_, p_75083_2_, p_75083_3_, p_75083_4_, p_75083_5_, p_75083_6_, p_75083_7_, p_75083_8_);
        }
        return (Village)var10;
    }
    
    private static Village getNextVillageComponent(final Start p_75081_0_, final List p_75081_1_, final Random p_75081_2_, final int p_75081_3_, final int p_75081_4_, final int p_75081_5_, final int p_75081_6_, final int p_75081_7_) {
        final int var8 = func_75079_a(p_75081_0_.structureVillageWeightedPieceList);
        if (var8 <= 0) {
            return null;
        }
        int var9 = 0;
        while (var9 < 5) {
            ++var9;
            int var10 = p_75081_2_.nextInt(var8);
            for (final PieceWeight var12 : p_75081_0_.structureVillageWeightedPieceList) {
                var10 -= var12.villagePieceWeight;
                if (var10 < 0) {
                    if (!var12.canSpawnMoreVillagePiecesOfType(p_75081_7_)) {
                        break;
                    }
                    if (var12 == p_75081_0_.structVillagePieceWeight && p_75081_0_.structureVillageWeightedPieceList.size() > 1) {
                        break;
                    }
                    final Village var13 = func_75083_a(p_75081_0_, var12, p_75081_1_, p_75081_2_, p_75081_3_, p_75081_4_, p_75081_5_, p_75081_6_, p_75081_7_);
                    if (var13 != null) {
                        final PieceWeight pieceWeight = var12;
                        ++pieceWeight.villagePiecesSpawned;
                        p_75081_0_.structVillagePieceWeight = var12;
                        if (!var12.canSpawnMoreVillagePieces()) {
                            p_75081_0_.structureVillageWeightedPieceList.remove(var12);
                        }
                        return var13;
                    }
                    continue;
                }
            }
        }
        final StructureBoundingBox var14 = Torch.func_74904_a(p_75081_0_, p_75081_1_, p_75081_2_, p_75081_3_, p_75081_4_, p_75081_5_, p_75081_6_);
        if (var14 != null) {
            return new Torch(p_75081_0_, p_75081_7_, p_75081_2_, var14, p_75081_6_);
        }
        return null;
    }
    
    private static StructureComponent getNextVillageStructureComponent(final Start p_75077_0_, final List p_75077_1_, final Random p_75077_2_, final int p_75077_3_, final int p_75077_4_, final int p_75077_5_, final int p_75077_6_, final int p_75077_7_) {
        if (p_75077_7_ > 50) {
            return null;
        }
        if (Math.abs(p_75077_3_ - p_75077_0_.getBoundingBox().minX) <= 112 && Math.abs(p_75077_5_ - p_75077_0_.getBoundingBox().minZ) <= 112) {
            final Village var8 = getNextVillageComponent(p_75077_0_, p_75077_1_, p_75077_2_, p_75077_3_, p_75077_4_, p_75077_5_, p_75077_6_, p_75077_7_ + 1);
            if (var8 != null) {
                final int var9 = (var8.boundingBox.minX + var8.boundingBox.maxX) / 2;
                final int var10 = (var8.boundingBox.minZ + var8.boundingBox.maxZ) / 2;
                final int var11 = var8.boundingBox.maxX - var8.boundingBox.minX;
                final int var12 = var8.boundingBox.maxZ - var8.boundingBox.minZ;
                final int var13 = (var11 > var12) ? var11 : var12;
                if (p_75077_0_.getWorldChunkManager().areBiomesViable(var9, var10, var13 / 2 + 4, MapGenVillage.villageSpawnBiomes)) {
                    p_75077_1_.add(var8);
                    p_75077_0_.field_74932_i.add(var8);
                    return var8;
                }
            }
            return null;
        }
        return null;
    }
    
    private static StructureComponent getNextComponentVillagePath(final Start p_75080_0_, final List p_75080_1_, final Random p_75080_2_, final int p_75080_3_, final int p_75080_4_, final int p_75080_5_, final int p_75080_6_, final int p_75080_7_) {
        if (p_75080_7_ > 3 + p_75080_0_.terrainType) {
            return null;
        }
        if (Math.abs(p_75080_3_ - p_75080_0_.getBoundingBox().minX) <= 112 && Math.abs(p_75080_5_ - p_75080_0_.getBoundingBox().minZ) <= 112) {
            final StructureBoundingBox var8 = Path.func_74933_a(p_75080_0_, p_75080_1_, p_75080_2_, p_75080_3_, p_75080_4_, p_75080_5_, p_75080_6_);
            if (var8 != null && var8.minY > 10) {
                final Path var9 = new Path(p_75080_0_, p_75080_7_, p_75080_2_, var8, p_75080_6_);
                final int var10 = (var9.boundingBox.minX + var9.boundingBox.maxX) / 2;
                final int var11 = (var9.boundingBox.minZ + var9.boundingBox.maxZ) / 2;
                final int var12 = var9.boundingBox.maxX - var9.boundingBox.minX;
                final int var13 = var9.boundingBox.maxZ - var9.boundingBox.minZ;
                final int var14 = (var12 > var13) ? var12 : var13;
                if (p_75080_0_.getWorldChunkManager().areBiomesViable(var10, var11, var14 / 2 + 4, MapGenVillage.villageSpawnBiomes)) {
                    p_75080_1_.add(var9);
                    p_75080_0_.field_74930_j.add(var9);
                    return var9;
                }
            }
            return null;
        }
        return null;
    }
    
    public static class Church extends Village
    {
        private static final String __OBFID = "CL_00000525";
        
        public Church() {
        }
        
        public Church(final Start p_i2102_1_, final int p_i2102_2_, final Random p_i2102_3_, final StructureBoundingBox p_i2102_4_, final int p_i2102_5_) {
            super(p_i2102_1_, p_i2102_2_);
            this.coordBaseMode = p_i2102_5_;
            this.boundingBox = p_i2102_4_;
        }
        
        public static Church func_74919_a(final Start p_74919_0_, final List p_74919_1_, final Random p_74919_2_, final int p_74919_3_, final int p_74919_4_, final int p_74919_5_, final int p_74919_6_, final int p_74919_7_) {
            final StructureBoundingBox var8 = StructureBoundingBox.getComponentToAddBoundingBox(p_74919_3_, p_74919_4_, p_74919_5_, 0, 0, 0, 5, 12, 9, p_74919_6_);
            return (Village.canVillageGoDeeper(var8) && StructureComponent.findIntersecting(p_74919_1_, var8) == null) ? new Church(p_74919_0_, p_74919_7_, p_74919_2_, var8, p_74919_6_) : null;
        }
        
        @Override
        public boolean addComponentParts(final World p_74875_1_, final Random p_74875_2_, final StructureBoundingBox p_74875_3_) {
            if (this.field_143015_k < 0) {
                this.field_143015_k = this.getAverageGroundLevel(p_74875_1_, p_74875_3_);
                if (this.field_143015_k < 0) {
                    return true;
                }
                this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 12 - 1, 0);
            }
            this.func_151549_a(p_74875_1_, p_74875_3_, 1, 1, 1, 3, 3, 7, Blocks.air, Blocks.air, false);
            this.func_151549_a(p_74875_1_, p_74875_3_, 1, 5, 1, 3, 9, 3, Blocks.air, Blocks.air, false);
            this.func_151549_a(p_74875_1_, p_74875_3_, 1, 0, 0, 3, 0, 8, Blocks.cobblestone, Blocks.cobblestone, false);
            this.func_151549_a(p_74875_1_, p_74875_3_, 1, 1, 0, 3, 10, 0, Blocks.cobblestone, Blocks.cobblestone, false);
            this.func_151549_a(p_74875_1_, p_74875_3_, 0, 1, 1, 0, 10, 3, Blocks.cobblestone, Blocks.cobblestone, false);
            this.func_151549_a(p_74875_1_, p_74875_3_, 4, 1, 1, 4, 10, 3, Blocks.cobblestone, Blocks.cobblestone, false);
            this.func_151549_a(p_74875_1_, p_74875_3_, 0, 0, 4, 0, 4, 7, Blocks.cobblestone, Blocks.cobblestone, false);
            this.func_151549_a(p_74875_1_, p_74875_3_, 4, 0, 4, 4, 4, 7, Blocks.cobblestone, Blocks.cobblestone, false);
            this.func_151549_a(p_74875_1_, p_74875_3_, 1, 1, 8, 3, 4, 8, Blocks.cobblestone, Blocks.cobblestone, false);
            this.func_151549_a(p_74875_1_, p_74875_3_, 1, 5, 4, 3, 10, 4, Blocks.cobblestone, Blocks.cobblestone, false);
            this.func_151549_a(p_74875_1_, p_74875_3_, 1, 5, 5, 3, 5, 7, Blocks.cobblestone, Blocks.cobblestone, false);
            this.func_151549_a(p_74875_1_, p_74875_3_, 0, 9, 0, 4, 9, 4, Blocks.cobblestone, Blocks.cobblestone, false);
            this.func_151549_a(p_74875_1_, p_74875_3_, 0, 4, 0, 4, 4, 4, Blocks.cobblestone, Blocks.cobblestone, false);
            this.func_151550_a(p_74875_1_, Blocks.cobblestone, 0, 0, 11, 2, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.cobblestone, 0, 4, 11, 2, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.cobblestone, 0, 2, 11, 0, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.cobblestone, 0, 2, 11, 4, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.cobblestone, 0, 1, 1, 6, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.cobblestone, 0, 1, 1, 7, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.cobblestone, 0, 2, 1, 7, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.cobblestone, 0, 3, 1, 6, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.cobblestone, 0, 3, 1, 7, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.stone_stairs, this.func_151555_a(Blocks.stone_stairs, 3), 1, 1, 5, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.stone_stairs, this.func_151555_a(Blocks.stone_stairs, 3), 2, 1, 6, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.stone_stairs, this.func_151555_a(Blocks.stone_stairs, 3), 3, 1, 5, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.stone_stairs, this.func_151555_a(Blocks.stone_stairs, 1), 1, 2, 7, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.stone_stairs, this.func_151555_a(Blocks.stone_stairs, 0), 3, 2, 7, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.glass_pane, 0, 0, 2, 2, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.glass_pane, 0, 0, 3, 2, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.glass_pane, 0, 4, 2, 2, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.glass_pane, 0, 4, 3, 2, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.glass_pane, 0, 0, 6, 2, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.glass_pane, 0, 0, 7, 2, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.glass_pane, 0, 4, 6, 2, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.glass_pane, 0, 4, 7, 2, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.glass_pane, 0, 2, 6, 0, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.glass_pane, 0, 2, 7, 0, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.glass_pane, 0, 2, 6, 4, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.glass_pane, 0, 2, 7, 4, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.glass_pane, 0, 0, 3, 6, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.glass_pane, 0, 4, 3, 6, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.glass_pane, 0, 2, 3, 8, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.torch, 0, 2, 4, 7, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.torch, 0, 1, 4, 6, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.torch, 0, 3, 4, 6, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.torch, 0, 2, 4, 5, p_74875_3_);
            final int var4 = this.func_151555_a(Blocks.ladder, 4);
            for (int var5 = 1; var5 <= 9; ++var5) {
                this.func_151550_a(p_74875_1_, Blocks.ladder, var4, 3, var5, 3, p_74875_3_);
            }
            this.func_151550_a(p_74875_1_, Blocks.air, 0, 2, 1, 0, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.air, 0, 2, 2, 0, p_74875_3_);
            this.placeDoorAtCurrentPosition(p_74875_1_, p_74875_3_, p_74875_2_, 2, 1, 0, this.func_151555_a(Blocks.wooden_door, 1));
            if (this.func_151548_a(p_74875_1_, 2, 0, -1, p_74875_3_).getMaterial() == Material.air && this.func_151548_a(p_74875_1_, 2, -1, -1, p_74875_3_).getMaterial() != Material.air) {
                this.func_151550_a(p_74875_1_, Blocks.stone_stairs, this.func_151555_a(Blocks.stone_stairs, 3), 2, 0, -1, p_74875_3_);
            }
            for (int var5 = 0; var5 < 9; ++var5) {
                for (int var6 = 0; var6 < 5; ++var6) {
                    this.clearCurrentPositionBlocksUpwards(p_74875_1_, var6, 12, var5, p_74875_3_);
                    this.func_151554_b(p_74875_1_, Blocks.cobblestone, 0, var6, -1, var5, p_74875_3_);
                }
            }
            this.spawnVillagers(p_74875_1_, p_74875_3_, 2, 1, 2, 1);
            return true;
        }
        
        @Override
        protected int getVillagerType(final int p_74888_1_) {
            return 2;
        }
    }
    
    public static class Field1 extends Village
    {
        private Block cropTypeA;
        private Block cropTypeB;
        private Block cropTypeC;
        private Block cropTypeD;
        private static final String __OBFID = "CL_00000518";
        
        public Field1() {
        }
        
        public Field1(final Start p_i2095_1_, final int p_i2095_2_, final Random p_i2095_3_, final StructureBoundingBox p_i2095_4_, final int p_i2095_5_) {
            super(p_i2095_1_, p_i2095_2_);
            this.coordBaseMode = p_i2095_5_;
            this.boundingBox = p_i2095_4_;
            this.cropTypeA = this.func_151559_a(p_i2095_3_);
            this.cropTypeB = this.func_151559_a(p_i2095_3_);
            this.cropTypeC = this.func_151559_a(p_i2095_3_);
            this.cropTypeD = this.func_151559_a(p_i2095_3_);
        }
        
        @Override
        protected void func_143012_a(final NBTTagCompound p_143012_1_) {
            super.func_143012_a(p_143012_1_);
            p_143012_1_.setInteger("CA", Block.blockRegistry.getIDForObject(this.cropTypeA));
            p_143012_1_.setInteger("CB", Block.blockRegistry.getIDForObject(this.cropTypeB));
            p_143012_1_.setInteger("CC", Block.blockRegistry.getIDForObject(this.cropTypeC));
            p_143012_1_.setInteger("CD", Block.blockRegistry.getIDForObject(this.cropTypeD));
        }
        
        @Override
        protected void func_143011_b(final NBTTagCompound p_143011_1_) {
            super.func_143011_b(p_143011_1_);
            this.cropTypeA = Block.getBlockById(p_143011_1_.getInteger("CA"));
            this.cropTypeB = Block.getBlockById(p_143011_1_.getInteger("CB"));
            this.cropTypeC = Block.getBlockById(p_143011_1_.getInteger("CC"));
            this.cropTypeD = Block.getBlockById(p_143011_1_.getInteger("CD"));
        }
        
        private Block func_151559_a(final Random p_151559_1_) {
            switch (p_151559_1_.nextInt(5)) {
                case 0: {
                    return Blocks.carrots;
                }
                case 1: {
                    return Blocks.potatoes;
                }
                default: {
                    return Blocks.wheat;
                }
            }
        }
        
        public static Field1 func_74900_a(final Start p_74900_0_, final List p_74900_1_, final Random p_74900_2_, final int p_74900_3_, final int p_74900_4_, final int p_74900_5_, final int p_74900_6_, final int p_74900_7_) {
            final StructureBoundingBox var8 = StructureBoundingBox.getComponentToAddBoundingBox(p_74900_3_, p_74900_4_, p_74900_5_, 0, 0, 0, 13, 4, 9, p_74900_6_);
            return (Village.canVillageGoDeeper(var8) && StructureComponent.findIntersecting(p_74900_1_, var8) == null) ? new Field1(p_74900_0_, p_74900_7_, p_74900_2_, var8, p_74900_6_) : null;
        }
        
        @Override
        public boolean addComponentParts(final World p_74875_1_, final Random p_74875_2_, final StructureBoundingBox p_74875_3_) {
            if (this.field_143015_k < 0) {
                this.field_143015_k = this.getAverageGroundLevel(p_74875_1_, p_74875_3_);
                if (this.field_143015_k < 0) {
                    return true;
                }
                this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 4 - 1, 0);
            }
            this.func_151549_a(p_74875_1_, p_74875_3_, 0, 1, 0, 12, 4, 8, Blocks.air, Blocks.air, false);
            this.func_151549_a(p_74875_1_, p_74875_3_, 1, 0, 1, 2, 0, 7, Blocks.farmland, Blocks.farmland, false);
            this.func_151549_a(p_74875_1_, p_74875_3_, 4, 0, 1, 5, 0, 7, Blocks.farmland, Blocks.farmland, false);
            this.func_151549_a(p_74875_1_, p_74875_3_, 7, 0, 1, 8, 0, 7, Blocks.farmland, Blocks.farmland, false);
            this.func_151549_a(p_74875_1_, p_74875_3_, 10, 0, 1, 11, 0, 7, Blocks.farmland, Blocks.farmland, false);
            this.func_151549_a(p_74875_1_, p_74875_3_, 0, 0, 0, 0, 0, 8, Blocks.log, Blocks.log, false);
            this.func_151549_a(p_74875_1_, p_74875_3_, 6, 0, 0, 6, 0, 8, Blocks.log, Blocks.log, false);
            this.func_151549_a(p_74875_1_, p_74875_3_, 12, 0, 0, 12, 0, 8, Blocks.log, Blocks.log, false);
            this.func_151549_a(p_74875_1_, p_74875_3_, 1, 0, 0, 11, 0, 0, Blocks.log, Blocks.log, false);
            this.func_151549_a(p_74875_1_, p_74875_3_, 1, 0, 8, 11, 0, 8, Blocks.log, Blocks.log, false);
            this.func_151549_a(p_74875_1_, p_74875_3_, 3, 0, 1, 3, 0, 7, Blocks.water, Blocks.water, false);
            this.func_151549_a(p_74875_1_, p_74875_3_, 9, 0, 1, 9, 0, 7, Blocks.water, Blocks.water, false);
            for (int var4 = 1; var4 <= 7; ++var4) {
                this.func_151550_a(p_74875_1_, this.cropTypeA, MathHelper.getRandomIntegerInRange(p_74875_2_, 2, 7), 1, 1, var4, p_74875_3_);
                this.func_151550_a(p_74875_1_, this.cropTypeA, MathHelper.getRandomIntegerInRange(p_74875_2_, 2, 7), 2, 1, var4, p_74875_3_);
                this.func_151550_a(p_74875_1_, this.cropTypeB, MathHelper.getRandomIntegerInRange(p_74875_2_, 2, 7), 4, 1, var4, p_74875_3_);
                this.func_151550_a(p_74875_1_, this.cropTypeB, MathHelper.getRandomIntegerInRange(p_74875_2_, 2, 7), 5, 1, var4, p_74875_3_);
                this.func_151550_a(p_74875_1_, this.cropTypeC, MathHelper.getRandomIntegerInRange(p_74875_2_, 2, 7), 7, 1, var4, p_74875_3_);
                this.func_151550_a(p_74875_1_, this.cropTypeC, MathHelper.getRandomIntegerInRange(p_74875_2_, 2, 7), 8, 1, var4, p_74875_3_);
                this.func_151550_a(p_74875_1_, this.cropTypeD, MathHelper.getRandomIntegerInRange(p_74875_2_, 2, 7), 10, 1, var4, p_74875_3_);
                this.func_151550_a(p_74875_1_, this.cropTypeD, MathHelper.getRandomIntegerInRange(p_74875_2_, 2, 7), 11, 1, var4, p_74875_3_);
            }
            for (int var4 = 0; var4 < 9; ++var4) {
                for (int var5 = 0; var5 < 13; ++var5) {
                    this.clearCurrentPositionBlocksUpwards(p_74875_1_, var5, 4, var4, p_74875_3_);
                    this.func_151554_b(p_74875_1_, Blocks.dirt, 0, var5, -1, var4, p_74875_3_);
                }
            }
            return true;
        }
    }
    
    public static class Field2 extends Village
    {
        private Block cropTypeA;
        private Block cropTypeB;
        private static final String __OBFID = "CL_00000519";
        
        public Field2() {
        }
        
        public Field2(final Start p_i2096_1_, final int p_i2096_2_, final Random p_i2096_3_, final StructureBoundingBox p_i2096_4_, final int p_i2096_5_) {
            super(p_i2096_1_, p_i2096_2_);
            this.coordBaseMode = p_i2096_5_;
            this.boundingBox = p_i2096_4_;
            this.cropTypeA = this.func_151560_a(p_i2096_3_);
            this.cropTypeB = this.func_151560_a(p_i2096_3_);
        }
        
        @Override
        protected void func_143012_a(final NBTTagCompound p_143012_1_) {
            super.func_143012_a(p_143012_1_);
            p_143012_1_.setInteger("CA", Block.blockRegistry.getIDForObject(this.cropTypeA));
            p_143012_1_.setInteger("CB", Block.blockRegistry.getIDForObject(this.cropTypeB));
        }
        
        @Override
        protected void func_143011_b(final NBTTagCompound p_143011_1_) {
            super.func_143011_b(p_143011_1_);
            this.cropTypeA = Block.getBlockById(p_143011_1_.getInteger("CA"));
            this.cropTypeB = Block.getBlockById(p_143011_1_.getInteger("CB"));
        }
        
        private Block func_151560_a(final Random p_151560_1_) {
            switch (p_151560_1_.nextInt(5)) {
                case 0: {
                    return Blocks.carrots;
                }
                case 1: {
                    return Blocks.potatoes;
                }
                default: {
                    return Blocks.wheat;
                }
            }
        }
        
        public static Field2 func_74902_a(final Start p_74902_0_, final List p_74902_1_, final Random p_74902_2_, final int p_74902_3_, final int p_74902_4_, final int p_74902_5_, final int p_74902_6_, final int p_74902_7_) {
            final StructureBoundingBox var8 = StructureBoundingBox.getComponentToAddBoundingBox(p_74902_3_, p_74902_4_, p_74902_5_, 0, 0, 0, 7, 4, 9, p_74902_6_);
            return (Village.canVillageGoDeeper(var8) && StructureComponent.findIntersecting(p_74902_1_, var8) == null) ? new Field2(p_74902_0_, p_74902_7_, p_74902_2_, var8, p_74902_6_) : null;
        }
        
        @Override
        public boolean addComponentParts(final World p_74875_1_, final Random p_74875_2_, final StructureBoundingBox p_74875_3_) {
            if (this.field_143015_k < 0) {
                this.field_143015_k = this.getAverageGroundLevel(p_74875_1_, p_74875_3_);
                if (this.field_143015_k < 0) {
                    return true;
                }
                this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 4 - 1, 0);
            }
            this.func_151549_a(p_74875_1_, p_74875_3_, 0, 1, 0, 6, 4, 8, Blocks.air, Blocks.air, false);
            this.func_151549_a(p_74875_1_, p_74875_3_, 1, 0, 1, 2, 0, 7, Blocks.farmland, Blocks.farmland, false);
            this.func_151549_a(p_74875_1_, p_74875_3_, 4, 0, 1, 5, 0, 7, Blocks.farmland, Blocks.farmland, false);
            this.func_151549_a(p_74875_1_, p_74875_3_, 0, 0, 0, 0, 0, 8, Blocks.log, Blocks.log, false);
            this.func_151549_a(p_74875_1_, p_74875_3_, 6, 0, 0, 6, 0, 8, Blocks.log, Blocks.log, false);
            this.func_151549_a(p_74875_1_, p_74875_3_, 1, 0, 0, 5, 0, 0, Blocks.log, Blocks.log, false);
            this.func_151549_a(p_74875_1_, p_74875_3_, 1, 0, 8, 5, 0, 8, Blocks.log, Blocks.log, false);
            this.func_151549_a(p_74875_1_, p_74875_3_, 3, 0, 1, 3, 0, 7, Blocks.water, Blocks.water, false);
            for (int var4 = 1; var4 <= 7; ++var4) {
                this.func_151550_a(p_74875_1_, this.cropTypeA, MathHelper.getRandomIntegerInRange(p_74875_2_, 2, 7), 1, 1, var4, p_74875_3_);
                this.func_151550_a(p_74875_1_, this.cropTypeA, MathHelper.getRandomIntegerInRange(p_74875_2_, 2, 7), 2, 1, var4, p_74875_3_);
                this.func_151550_a(p_74875_1_, this.cropTypeB, MathHelper.getRandomIntegerInRange(p_74875_2_, 2, 7), 4, 1, var4, p_74875_3_);
                this.func_151550_a(p_74875_1_, this.cropTypeB, MathHelper.getRandomIntegerInRange(p_74875_2_, 2, 7), 5, 1, var4, p_74875_3_);
            }
            for (int var4 = 0; var4 < 9; ++var4) {
                for (int var5 = 0; var5 < 7; ++var5) {
                    this.clearCurrentPositionBlocksUpwards(p_74875_1_, var5, 4, var4, p_74875_3_);
                    this.func_151554_b(p_74875_1_, Blocks.dirt, 0, var5, -1, var4, p_74875_3_);
                }
            }
            return true;
        }
    }
    
    public static class Hall extends Village
    {
        private static final String __OBFID = "CL_00000522";
        
        public Hall() {
        }
        
        public Hall(final Start p_i2099_1_, final int p_i2099_2_, final Random p_i2099_3_, final StructureBoundingBox p_i2099_4_, final int p_i2099_5_) {
            super(p_i2099_1_, p_i2099_2_);
            this.coordBaseMode = p_i2099_5_;
            this.boundingBox = p_i2099_4_;
        }
        
        public static Hall func_74906_a(final Start p_74906_0_, final List p_74906_1_, final Random p_74906_2_, final int p_74906_3_, final int p_74906_4_, final int p_74906_5_, final int p_74906_6_, final int p_74906_7_) {
            final StructureBoundingBox var8 = StructureBoundingBox.getComponentToAddBoundingBox(p_74906_3_, p_74906_4_, p_74906_5_, 0, 0, 0, 9, 7, 11, p_74906_6_);
            return (Village.canVillageGoDeeper(var8) && StructureComponent.findIntersecting(p_74906_1_, var8) == null) ? new Hall(p_74906_0_, p_74906_7_, p_74906_2_, var8, p_74906_6_) : null;
        }
        
        @Override
        public boolean addComponentParts(final World p_74875_1_, final Random p_74875_2_, final StructureBoundingBox p_74875_3_) {
            if (this.field_143015_k < 0) {
                this.field_143015_k = this.getAverageGroundLevel(p_74875_1_, p_74875_3_);
                if (this.field_143015_k < 0) {
                    return true;
                }
                this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 7 - 1, 0);
            }
            this.func_151549_a(p_74875_1_, p_74875_3_, 1, 1, 1, 7, 4, 4, Blocks.air, Blocks.air, false);
            this.func_151549_a(p_74875_1_, p_74875_3_, 2, 1, 6, 8, 4, 10, Blocks.air, Blocks.air, false);
            this.func_151549_a(p_74875_1_, p_74875_3_, 2, 0, 6, 8, 0, 10, Blocks.dirt, Blocks.dirt, false);
            this.func_151550_a(p_74875_1_, Blocks.cobblestone, 0, 6, 0, 6, p_74875_3_);
            this.func_151549_a(p_74875_1_, p_74875_3_, 2, 1, 6, 2, 1, 10, Blocks.fence, Blocks.fence, false);
            this.func_151549_a(p_74875_1_, p_74875_3_, 8, 1, 6, 8, 1, 10, Blocks.fence, Blocks.fence, false);
            this.func_151549_a(p_74875_1_, p_74875_3_, 3, 1, 10, 7, 1, 10, Blocks.fence, Blocks.fence, false);
            this.func_151549_a(p_74875_1_, p_74875_3_, 1, 0, 1, 7, 0, 4, Blocks.planks, Blocks.planks, false);
            this.func_151549_a(p_74875_1_, p_74875_3_, 0, 0, 0, 0, 3, 5, Blocks.cobblestone, Blocks.cobblestone, false);
            this.func_151549_a(p_74875_1_, p_74875_3_, 8, 0, 0, 8, 3, 5, Blocks.cobblestone, Blocks.cobblestone, false);
            this.func_151549_a(p_74875_1_, p_74875_3_, 1, 0, 0, 7, 1, 0, Blocks.cobblestone, Blocks.cobblestone, false);
            this.func_151549_a(p_74875_1_, p_74875_3_, 1, 0, 5, 7, 1, 5, Blocks.cobblestone, Blocks.cobblestone, false);
            this.func_151549_a(p_74875_1_, p_74875_3_, 1, 2, 0, 7, 3, 0, Blocks.planks, Blocks.planks, false);
            this.func_151549_a(p_74875_1_, p_74875_3_, 1, 2, 5, 7, 3, 5, Blocks.planks, Blocks.planks, false);
            this.func_151549_a(p_74875_1_, p_74875_3_, 0, 4, 1, 8, 4, 1, Blocks.planks, Blocks.planks, false);
            this.func_151549_a(p_74875_1_, p_74875_3_, 0, 4, 4, 8, 4, 4, Blocks.planks, Blocks.planks, false);
            this.func_151549_a(p_74875_1_, p_74875_3_, 0, 5, 2, 8, 5, 3, Blocks.planks, Blocks.planks, false);
            this.func_151550_a(p_74875_1_, Blocks.planks, 0, 0, 4, 2, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.planks, 0, 0, 4, 3, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.planks, 0, 8, 4, 2, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.planks, 0, 8, 4, 3, p_74875_3_);
            final int var4 = this.func_151555_a(Blocks.oak_stairs, 3);
            final int var5 = this.func_151555_a(Blocks.oak_stairs, 2);
            for (int var6 = -1; var6 <= 2; ++var6) {
                for (int var7 = 0; var7 <= 8; ++var7) {
                    this.func_151550_a(p_74875_1_, Blocks.oak_stairs, var4, var7, 4 + var6, var6, p_74875_3_);
                    this.func_151550_a(p_74875_1_, Blocks.oak_stairs, var5, var7, 4 + var6, 5 - var6, p_74875_3_);
                }
            }
            this.func_151550_a(p_74875_1_, Blocks.log, 0, 0, 2, 1, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.log, 0, 0, 2, 4, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.log, 0, 8, 2, 1, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.log, 0, 8, 2, 4, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.glass_pane, 0, 0, 2, 2, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.glass_pane, 0, 0, 2, 3, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.glass_pane, 0, 8, 2, 2, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.glass_pane, 0, 8, 2, 3, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.glass_pane, 0, 2, 2, 5, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.glass_pane, 0, 3, 2, 5, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.glass_pane, 0, 5, 2, 0, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.glass_pane, 0, 6, 2, 5, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.fence, 0, 2, 1, 3, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.wooden_pressure_plate, 0, 2, 2, 3, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.planks, 0, 1, 1, 4, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.oak_stairs, this.func_151555_a(Blocks.oak_stairs, 3), 2, 1, 4, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.oak_stairs, this.func_151555_a(Blocks.oak_stairs, 1), 1, 1, 3, p_74875_3_);
            this.func_151549_a(p_74875_1_, p_74875_3_, 5, 0, 1, 7, 0, 3, Blocks.double_stone_slab, Blocks.double_stone_slab, false);
            this.func_151550_a(p_74875_1_, Blocks.double_stone_slab, 0, 6, 1, 1, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.double_stone_slab, 0, 6, 1, 2, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.air, 0, 2, 1, 0, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.air, 0, 2, 2, 0, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.torch, 0, 2, 3, 1, p_74875_3_);
            this.placeDoorAtCurrentPosition(p_74875_1_, p_74875_3_, p_74875_2_, 2, 1, 0, this.func_151555_a(Blocks.wooden_door, 1));
            if (this.func_151548_a(p_74875_1_, 2, 0, -1, p_74875_3_).getMaterial() == Material.air && this.func_151548_a(p_74875_1_, 2, -1, -1, p_74875_3_).getMaterial() != Material.air) {
                this.func_151550_a(p_74875_1_, Blocks.stone_stairs, this.func_151555_a(Blocks.stone_stairs, 3), 2, 0, -1, p_74875_3_);
            }
            this.func_151550_a(p_74875_1_, Blocks.air, 0, 6, 1, 5, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.air, 0, 6, 2, 5, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.torch, 0, 6, 3, 4, p_74875_3_);
            this.placeDoorAtCurrentPosition(p_74875_1_, p_74875_3_, p_74875_2_, 6, 1, 5, this.func_151555_a(Blocks.wooden_door, 1));
            for (int var6 = 0; var6 < 5; ++var6) {
                for (int var7 = 0; var7 < 9; ++var7) {
                    this.clearCurrentPositionBlocksUpwards(p_74875_1_, var7, 7, var6, p_74875_3_);
                    this.func_151554_b(p_74875_1_, Blocks.cobblestone, 0, var7, -1, var6, p_74875_3_);
                }
            }
            this.spawnVillagers(p_74875_1_, p_74875_3_, 4, 1, 2, 2);
            return true;
        }
        
        @Override
        protected int getVillagerType(final int p_74888_1_) {
            return (p_74888_1_ == 0) ? 4 : 0;
        }
    }
    
    public static class House1 extends Village
    {
        private static final String __OBFID = "CL_00000517";
        
        public House1() {
        }
        
        public House1(final Start p_i2094_1_, final int p_i2094_2_, final Random p_i2094_3_, final StructureBoundingBox p_i2094_4_, final int p_i2094_5_) {
            super(p_i2094_1_, p_i2094_2_);
            this.coordBaseMode = p_i2094_5_;
            this.boundingBox = p_i2094_4_;
        }
        
        public static House1 func_74898_a(final Start p_74898_0_, final List p_74898_1_, final Random p_74898_2_, final int p_74898_3_, final int p_74898_4_, final int p_74898_5_, final int p_74898_6_, final int p_74898_7_) {
            final StructureBoundingBox var8 = StructureBoundingBox.getComponentToAddBoundingBox(p_74898_3_, p_74898_4_, p_74898_5_, 0, 0, 0, 9, 9, 6, p_74898_6_);
            return (Village.canVillageGoDeeper(var8) && StructureComponent.findIntersecting(p_74898_1_, var8) == null) ? new House1(p_74898_0_, p_74898_7_, p_74898_2_, var8, p_74898_6_) : null;
        }
        
        @Override
        public boolean addComponentParts(final World p_74875_1_, final Random p_74875_2_, final StructureBoundingBox p_74875_3_) {
            if (this.field_143015_k < 0) {
                this.field_143015_k = this.getAverageGroundLevel(p_74875_1_, p_74875_3_);
                if (this.field_143015_k < 0) {
                    return true;
                }
                this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 9 - 1, 0);
            }
            this.func_151549_a(p_74875_1_, p_74875_3_, 1, 1, 1, 7, 5, 4, Blocks.air, Blocks.air, false);
            this.func_151549_a(p_74875_1_, p_74875_3_, 0, 0, 0, 8, 0, 5, Blocks.cobblestone, Blocks.cobblestone, false);
            this.func_151549_a(p_74875_1_, p_74875_3_, 0, 5, 0, 8, 5, 5, Blocks.cobblestone, Blocks.cobblestone, false);
            this.func_151549_a(p_74875_1_, p_74875_3_, 0, 6, 1, 8, 6, 4, Blocks.cobblestone, Blocks.cobblestone, false);
            this.func_151549_a(p_74875_1_, p_74875_3_, 0, 7, 2, 8, 7, 3, Blocks.cobblestone, Blocks.cobblestone, false);
            final int var4 = this.func_151555_a(Blocks.oak_stairs, 3);
            final int var5 = this.func_151555_a(Blocks.oak_stairs, 2);
            for (int var6 = -1; var6 <= 2; ++var6) {
                for (int var7 = 0; var7 <= 8; ++var7) {
                    this.func_151550_a(p_74875_1_, Blocks.oak_stairs, var4, var7, 6 + var6, var6, p_74875_3_);
                    this.func_151550_a(p_74875_1_, Blocks.oak_stairs, var5, var7, 6 + var6, 5 - var6, p_74875_3_);
                }
            }
            this.func_151549_a(p_74875_1_, p_74875_3_, 0, 1, 0, 0, 1, 5, Blocks.cobblestone, Blocks.cobblestone, false);
            this.func_151549_a(p_74875_1_, p_74875_3_, 1, 1, 5, 8, 1, 5, Blocks.cobblestone, Blocks.cobblestone, false);
            this.func_151549_a(p_74875_1_, p_74875_3_, 8, 1, 0, 8, 1, 4, Blocks.cobblestone, Blocks.cobblestone, false);
            this.func_151549_a(p_74875_1_, p_74875_3_, 2, 1, 0, 7, 1, 0, Blocks.cobblestone, Blocks.cobblestone, false);
            this.func_151549_a(p_74875_1_, p_74875_3_, 0, 2, 0, 0, 4, 0, Blocks.cobblestone, Blocks.cobblestone, false);
            this.func_151549_a(p_74875_1_, p_74875_3_, 0, 2, 5, 0, 4, 5, Blocks.cobblestone, Blocks.cobblestone, false);
            this.func_151549_a(p_74875_1_, p_74875_3_, 8, 2, 5, 8, 4, 5, Blocks.cobblestone, Blocks.cobblestone, false);
            this.func_151549_a(p_74875_1_, p_74875_3_, 8, 2, 0, 8, 4, 0, Blocks.cobblestone, Blocks.cobblestone, false);
            this.func_151549_a(p_74875_1_, p_74875_3_, 0, 2, 1, 0, 4, 4, Blocks.planks, Blocks.planks, false);
            this.func_151549_a(p_74875_1_, p_74875_3_, 1, 2, 5, 7, 4, 5, Blocks.planks, Blocks.planks, false);
            this.func_151549_a(p_74875_1_, p_74875_3_, 8, 2, 1, 8, 4, 4, Blocks.planks, Blocks.planks, false);
            this.func_151549_a(p_74875_1_, p_74875_3_, 1, 2, 0, 7, 4, 0, Blocks.planks, Blocks.planks, false);
            this.func_151550_a(p_74875_1_, Blocks.glass_pane, 0, 4, 2, 0, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.glass_pane, 0, 5, 2, 0, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.glass_pane, 0, 6, 2, 0, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.glass_pane, 0, 4, 3, 0, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.glass_pane, 0, 5, 3, 0, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.glass_pane, 0, 6, 3, 0, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.glass_pane, 0, 0, 2, 2, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.glass_pane, 0, 0, 2, 3, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.glass_pane, 0, 0, 3, 2, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.glass_pane, 0, 0, 3, 3, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.glass_pane, 0, 8, 2, 2, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.glass_pane, 0, 8, 2, 3, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.glass_pane, 0, 8, 3, 2, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.glass_pane, 0, 8, 3, 3, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.glass_pane, 0, 2, 2, 5, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.glass_pane, 0, 3, 2, 5, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.glass_pane, 0, 5, 2, 5, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.glass_pane, 0, 6, 2, 5, p_74875_3_);
            this.func_151549_a(p_74875_1_, p_74875_3_, 1, 4, 1, 7, 4, 1, Blocks.planks, Blocks.planks, false);
            this.func_151549_a(p_74875_1_, p_74875_3_, 1, 4, 4, 7, 4, 4, Blocks.planks, Blocks.planks, false);
            this.func_151549_a(p_74875_1_, p_74875_3_, 1, 3, 4, 7, 3, 4, Blocks.bookshelf, Blocks.bookshelf, false);
            this.func_151550_a(p_74875_1_, Blocks.planks, 0, 7, 1, 4, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.oak_stairs, this.func_151555_a(Blocks.oak_stairs, 0), 7, 1, 3, p_74875_3_);
            int var6 = this.func_151555_a(Blocks.oak_stairs, 3);
            this.func_151550_a(p_74875_1_, Blocks.oak_stairs, var6, 6, 1, 4, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.oak_stairs, var6, 5, 1, 4, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.oak_stairs, var6, 4, 1, 4, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.oak_stairs, var6, 3, 1, 4, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.fence, 0, 6, 1, 3, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.wooden_pressure_plate, 0, 6, 2, 3, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.fence, 0, 4, 1, 3, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.wooden_pressure_plate, 0, 4, 2, 3, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.crafting_table, 0, 7, 1, 1, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.air, 0, 1, 1, 0, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.air, 0, 1, 2, 0, p_74875_3_);
            this.placeDoorAtCurrentPosition(p_74875_1_, p_74875_3_, p_74875_2_, 1, 1, 0, this.func_151555_a(Blocks.wooden_door, 1));
            if (this.func_151548_a(p_74875_1_, 1, 0, -1, p_74875_3_).getMaterial() == Material.air && this.func_151548_a(p_74875_1_, 1, -1, -1, p_74875_3_).getMaterial() != Material.air) {
                this.func_151550_a(p_74875_1_, Blocks.stone_stairs, this.func_151555_a(Blocks.stone_stairs, 3), 1, 0, -1, p_74875_3_);
            }
            for (int var7 = 0; var7 < 6; ++var7) {
                for (int var8 = 0; var8 < 9; ++var8) {
                    this.clearCurrentPositionBlocksUpwards(p_74875_1_, var8, 9, var7, p_74875_3_);
                    this.func_151554_b(p_74875_1_, Blocks.cobblestone, 0, var8, -1, var7, p_74875_3_);
                }
            }
            this.spawnVillagers(p_74875_1_, p_74875_3_, 2, 1, 2, 1);
            return true;
        }
        
        @Override
        protected int getVillagerType(final int p_74888_1_) {
            return 1;
        }
    }
    
    public static class House2 extends Village
    {
        private static final WeightedRandomChestContent[] villageBlacksmithChestContents;
        private boolean hasMadeChest;
        private static final String __OBFID = "CL_00000526";
        
        public House2() {
        }
        
        public House2(final Start p_i2103_1_, final int p_i2103_2_, final Random p_i2103_3_, final StructureBoundingBox p_i2103_4_, final int p_i2103_5_) {
            super(p_i2103_1_, p_i2103_2_);
            this.coordBaseMode = p_i2103_5_;
            this.boundingBox = p_i2103_4_;
        }
        
        public static House2 func_74915_a(final Start p_74915_0_, final List p_74915_1_, final Random p_74915_2_, final int p_74915_3_, final int p_74915_4_, final int p_74915_5_, final int p_74915_6_, final int p_74915_7_) {
            final StructureBoundingBox var8 = StructureBoundingBox.getComponentToAddBoundingBox(p_74915_3_, p_74915_4_, p_74915_5_, 0, 0, 0, 10, 6, 7, p_74915_6_);
            return (Village.canVillageGoDeeper(var8) && StructureComponent.findIntersecting(p_74915_1_, var8) == null) ? new House2(p_74915_0_, p_74915_7_, p_74915_2_, var8, p_74915_6_) : null;
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
        public boolean addComponentParts(final World p_74875_1_, final Random p_74875_2_, final StructureBoundingBox p_74875_3_) {
            if (this.field_143015_k < 0) {
                this.field_143015_k = this.getAverageGroundLevel(p_74875_1_, p_74875_3_);
                if (this.field_143015_k < 0) {
                    return true;
                }
                this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 6 - 1, 0);
            }
            this.func_151549_a(p_74875_1_, p_74875_3_, 0, 1, 0, 9, 4, 6, Blocks.air, Blocks.air, false);
            this.func_151549_a(p_74875_1_, p_74875_3_, 0, 0, 0, 9, 0, 6, Blocks.cobblestone, Blocks.cobblestone, false);
            this.func_151549_a(p_74875_1_, p_74875_3_, 0, 4, 0, 9, 4, 6, Blocks.cobblestone, Blocks.cobblestone, false);
            this.func_151549_a(p_74875_1_, p_74875_3_, 0, 5, 0, 9, 5, 6, Blocks.stone_slab, Blocks.stone_slab, false);
            this.func_151549_a(p_74875_1_, p_74875_3_, 1, 5, 1, 8, 5, 5, Blocks.air, Blocks.air, false);
            this.func_151549_a(p_74875_1_, p_74875_3_, 1, 1, 0, 2, 3, 0, Blocks.planks, Blocks.planks, false);
            this.func_151549_a(p_74875_1_, p_74875_3_, 0, 1, 0, 0, 4, 0, Blocks.log, Blocks.log, false);
            this.func_151549_a(p_74875_1_, p_74875_3_, 3, 1, 0, 3, 4, 0, Blocks.log, Blocks.log, false);
            this.func_151549_a(p_74875_1_, p_74875_3_, 0, 1, 6, 0, 4, 6, Blocks.log, Blocks.log, false);
            this.func_151550_a(p_74875_1_, Blocks.planks, 0, 3, 3, 1, p_74875_3_);
            this.func_151549_a(p_74875_1_, p_74875_3_, 3, 1, 2, 3, 3, 2, Blocks.planks, Blocks.planks, false);
            this.func_151549_a(p_74875_1_, p_74875_3_, 4, 1, 3, 5, 3, 3, Blocks.planks, Blocks.planks, false);
            this.func_151549_a(p_74875_1_, p_74875_3_, 0, 1, 1, 0, 3, 5, Blocks.planks, Blocks.planks, false);
            this.func_151549_a(p_74875_1_, p_74875_3_, 1, 1, 6, 5, 3, 6, Blocks.planks, Blocks.planks, false);
            this.func_151549_a(p_74875_1_, p_74875_3_, 5, 1, 0, 5, 3, 0, Blocks.fence, Blocks.fence, false);
            this.func_151549_a(p_74875_1_, p_74875_3_, 9, 1, 0, 9, 3, 0, Blocks.fence, Blocks.fence, false);
            this.func_151549_a(p_74875_1_, p_74875_3_, 6, 1, 4, 9, 4, 6, Blocks.cobblestone, Blocks.cobblestone, false);
            this.func_151550_a(p_74875_1_, Blocks.flowing_lava, 0, 7, 1, 5, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.flowing_lava, 0, 8, 1, 5, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.iron_bars, 0, 9, 2, 5, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.iron_bars, 0, 9, 2, 4, p_74875_3_);
            this.func_151549_a(p_74875_1_, p_74875_3_, 7, 2, 4, 8, 2, 5, Blocks.air, Blocks.air, false);
            this.func_151550_a(p_74875_1_, Blocks.cobblestone, 0, 6, 1, 3, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.furnace, 0, 6, 2, 3, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.furnace, 0, 6, 3, 3, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.double_stone_slab, 0, 8, 1, 1, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.glass_pane, 0, 0, 2, 2, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.glass_pane, 0, 0, 2, 4, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.glass_pane, 0, 2, 2, 6, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.glass_pane, 0, 4, 2, 6, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.fence, 0, 2, 1, 4, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.wooden_pressure_plate, 0, 2, 2, 4, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.planks, 0, 1, 1, 5, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.oak_stairs, this.func_151555_a(Blocks.oak_stairs, 3), 2, 1, 5, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.oak_stairs, this.func_151555_a(Blocks.oak_stairs, 1), 1, 1, 4, p_74875_3_);
            if (!this.hasMadeChest) {
                final int var4 = this.getYWithOffset(1);
                final int var5 = this.getXWithOffset(5, 5);
                final int var6 = this.getZWithOffset(5, 5);
                if (p_74875_3_.isVecInside(var5, var4, var6)) {
                    this.hasMadeChest = true;
                    this.generateStructureChestContents(p_74875_1_, p_74875_3_, p_74875_2_, 5, 1, 5, House2.villageBlacksmithChestContents, 3 + p_74875_2_.nextInt(6));
                }
            }
            for (int var4 = 6; var4 <= 8; ++var4) {
                if (this.func_151548_a(p_74875_1_, var4, 0, -1, p_74875_3_).getMaterial() == Material.air && this.func_151548_a(p_74875_1_, var4, -1, -1, p_74875_3_).getMaterial() != Material.air) {
                    this.func_151550_a(p_74875_1_, Blocks.stone_stairs, this.func_151555_a(Blocks.stone_stairs, 3), var4, 0, -1, p_74875_3_);
                }
            }
            for (int var4 = 0; var4 < 7; ++var4) {
                for (int var5 = 0; var5 < 10; ++var5) {
                    this.clearCurrentPositionBlocksUpwards(p_74875_1_, var5, 6, var4, p_74875_3_);
                    this.func_151554_b(p_74875_1_, Blocks.cobblestone, 0, var5, -1, var4, p_74875_3_);
                }
            }
            this.spawnVillagers(p_74875_1_, p_74875_3_, 7, 1, 1, 1);
            return true;
        }
        
        @Override
        protected int getVillagerType(final int p_74888_1_) {
            return 3;
        }
        
        static {
            villageBlacksmithChestContents = new WeightedRandomChestContent[] { new WeightedRandomChestContent(Items.diamond, 0, 1, 3, 3), new WeightedRandomChestContent(Items.iron_ingot, 0, 1, 5, 10), new WeightedRandomChestContent(Items.gold_ingot, 0, 1, 3, 5), new WeightedRandomChestContent(Items.bread, 0, 1, 3, 15), new WeightedRandomChestContent(Items.apple, 0, 1, 3, 15), new WeightedRandomChestContent(Items.iron_pickaxe, 0, 1, 1, 5), new WeightedRandomChestContent(Items.iron_sword, 0, 1, 1, 5), new WeightedRandomChestContent(Items.iron_chestplate, 0, 1, 1, 5), new WeightedRandomChestContent(Items.iron_helmet, 0, 1, 1, 5), new WeightedRandomChestContent(Items.iron_leggings, 0, 1, 1, 5), new WeightedRandomChestContent(Items.iron_boots, 0, 1, 1, 5), new WeightedRandomChestContent(Item.getItemFromBlock(Blocks.obsidian), 0, 3, 7, 5), new WeightedRandomChestContent(Item.getItemFromBlock(Blocks.sapling), 0, 3, 7, 5), new WeightedRandomChestContent(Items.saddle, 0, 1, 1, 3), new WeightedRandomChestContent(Items.iron_horse_armor, 0, 1, 1, 1), new WeightedRandomChestContent(Items.golden_horse_armor, 0, 1, 1, 1), new WeightedRandomChestContent(Items.diamond_horse_armor, 0, 1, 1, 1) };
        }
    }
    
    public static class House3 extends Village
    {
        private static final String __OBFID = "CL_00000530";
        
        public House3() {
        }
        
        public House3(final Start p_i2106_1_, final int p_i2106_2_, final Random p_i2106_3_, final StructureBoundingBox p_i2106_4_, final int p_i2106_5_) {
            super(p_i2106_1_, p_i2106_2_);
            this.coordBaseMode = p_i2106_5_;
            this.boundingBox = p_i2106_4_;
        }
        
        public static House3 func_74921_a(final Start p_74921_0_, final List p_74921_1_, final Random p_74921_2_, final int p_74921_3_, final int p_74921_4_, final int p_74921_5_, final int p_74921_6_, final int p_74921_7_) {
            final StructureBoundingBox var8 = StructureBoundingBox.getComponentToAddBoundingBox(p_74921_3_, p_74921_4_, p_74921_5_, 0, 0, 0, 9, 7, 12, p_74921_6_);
            return (Village.canVillageGoDeeper(var8) && StructureComponent.findIntersecting(p_74921_1_, var8) == null) ? new House3(p_74921_0_, p_74921_7_, p_74921_2_, var8, p_74921_6_) : null;
        }
        
        @Override
        public boolean addComponentParts(final World p_74875_1_, final Random p_74875_2_, final StructureBoundingBox p_74875_3_) {
            if (this.field_143015_k < 0) {
                this.field_143015_k = this.getAverageGroundLevel(p_74875_1_, p_74875_3_);
                if (this.field_143015_k < 0) {
                    return true;
                }
                this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 7 - 1, 0);
            }
            this.func_151549_a(p_74875_1_, p_74875_3_, 1, 1, 1, 7, 4, 4, Blocks.air, Blocks.air, false);
            this.func_151549_a(p_74875_1_, p_74875_3_, 2, 1, 6, 8, 4, 10, Blocks.air, Blocks.air, false);
            this.func_151549_a(p_74875_1_, p_74875_3_, 2, 0, 5, 8, 0, 10, Blocks.planks, Blocks.planks, false);
            this.func_151549_a(p_74875_1_, p_74875_3_, 1, 0, 1, 7, 0, 4, Blocks.planks, Blocks.planks, false);
            this.func_151549_a(p_74875_1_, p_74875_3_, 0, 0, 0, 0, 3, 5, Blocks.cobblestone, Blocks.cobblestone, false);
            this.func_151549_a(p_74875_1_, p_74875_3_, 8, 0, 0, 8, 3, 10, Blocks.cobblestone, Blocks.cobblestone, false);
            this.func_151549_a(p_74875_1_, p_74875_3_, 1, 0, 0, 7, 2, 0, Blocks.cobblestone, Blocks.cobblestone, false);
            this.func_151549_a(p_74875_1_, p_74875_3_, 1, 0, 5, 2, 1, 5, Blocks.cobblestone, Blocks.cobblestone, false);
            this.func_151549_a(p_74875_1_, p_74875_3_, 2, 0, 6, 2, 3, 10, Blocks.cobblestone, Blocks.cobblestone, false);
            this.func_151549_a(p_74875_1_, p_74875_3_, 3, 0, 10, 7, 3, 10, Blocks.cobblestone, Blocks.cobblestone, false);
            this.func_151549_a(p_74875_1_, p_74875_3_, 1, 2, 0, 7, 3, 0, Blocks.planks, Blocks.planks, false);
            this.func_151549_a(p_74875_1_, p_74875_3_, 1, 2, 5, 2, 3, 5, Blocks.planks, Blocks.planks, false);
            this.func_151549_a(p_74875_1_, p_74875_3_, 0, 4, 1, 8, 4, 1, Blocks.planks, Blocks.planks, false);
            this.func_151549_a(p_74875_1_, p_74875_3_, 0, 4, 4, 3, 4, 4, Blocks.planks, Blocks.planks, false);
            this.func_151549_a(p_74875_1_, p_74875_3_, 0, 5, 2, 8, 5, 3, Blocks.planks, Blocks.planks, false);
            this.func_151550_a(p_74875_1_, Blocks.planks, 0, 0, 4, 2, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.planks, 0, 0, 4, 3, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.planks, 0, 8, 4, 2, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.planks, 0, 8, 4, 3, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.planks, 0, 8, 4, 4, p_74875_3_);
            final int var4 = this.func_151555_a(Blocks.oak_stairs, 3);
            final int var5 = this.func_151555_a(Blocks.oak_stairs, 2);
            for (int var6 = -1; var6 <= 2; ++var6) {
                for (int var7 = 0; var7 <= 8; ++var7) {
                    this.func_151550_a(p_74875_1_, Blocks.oak_stairs, var4, var7, 4 + var6, var6, p_74875_3_);
                    if ((var6 > -1 || var7 <= 1) && (var6 > 0 || var7 <= 3) && (var6 > 1 || var7 <= 4 || var7 >= 6)) {
                        this.func_151550_a(p_74875_1_, Blocks.oak_stairs, var5, var7, 4 + var6, 5 - var6, p_74875_3_);
                    }
                }
            }
            this.func_151549_a(p_74875_1_, p_74875_3_, 3, 4, 5, 3, 4, 10, Blocks.planks, Blocks.planks, false);
            this.func_151549_a(p_74875_1_, p_74875_3_, 7, 4, 2, 7, 4, 10, Blocks.planks, Blocks.planks, false);
            this.func_151549_a(p_74875_1_, p_74875_3_, 4, 5, 4, 4, 5, 10, Blocks.planks, Blocks.planks, false);
            this.func_151549_a(p_74875_1_, p_74875_3_, 6, 5, 4, 6, 5, 10, Blocks.planks, Blocks.planks, false);
            this.func_151549_a(p_74875_1_, p_74875_3_, 5, 6, 3, 5, 6, 10, Blocks.planks, Blocks.planks, false);
            int var6 = this.func_151555_a(Blocks.oak_stairs, 0);
            for (int var7 = 4; var7 >= 1; --var7) {
                this.func_151550_a(p_74875_1_, Blocks.planks, 0, var7, 2 + var7, 7 - var7, p_74875_3_);
                for (int var8 = 8 - var7; var8 <= 10; ++var8) {
                    this.func_151550_a(p_74875_1_, Blocks.oak_stairs, var6, var7, 2 + var7, var8, p_74875_3_);
                }
            }
            int var7 = this.func_151555_a(Blocks.oak_stairs, 1);
            this.func_151550_a(p_74875_1_, Blocks.planks, 0, 6, 6, 3, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.planks, 0, 7, 5, 4, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.oak_stairs, var7, 6, 6, 4, p_74875_3_);
            for (int var8 = 6; var8 <= 8; ++var8) {
                for (int var9 = 5; var9 <= 10; ++var9) {
                    this.func_151550_a(p_74875_1_, Blocks.oak_stairs, var7, var8, 12 - var8, var9, p_74875_3_);
                }
            }
            this.func_151550_a(p_74875_1_, Blocks.log, 0, 0, 2, 1, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.log, 0, 0, 2, 4, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.glass_pane, 0, 0, 2, 2, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.glass_pane, 0, 0, 2, 3, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.log, 0, 4, 2, 0, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.glass_pane, 0, 5, 2, 0, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.log, 0, 6, 2, 0, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.log, 0, 8, 2, 1, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.glass_pane, 0, 8, 2, 2, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.glass_pane, 0, 8, 2, 3, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.log, 0, 8, 2, 4, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.planks, 0, 8, 2, 5, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.log, 0, 8, 2, 6, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.glass_pane, 0, 8, 2, 7, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.glass_pane, 0, 8, 2, 8, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.log, 0, 8, 2, 9, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.log, 0, 2, 2, 6, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.glass_pane, 0, 2, 2, 7, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.glass_pane, 0, 2, 2, 8, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.log, 0, 2, 2, 9, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.log, 0, 4, 4, 10, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.glass_pane, 0, 5, 4, 10, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.log, 0, 6, 4, 10, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.planks, 0, 5, 5, 10, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.air, 0, 2, 1, 0, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.air, 0, 2, 2, 0, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.torch, 0, 2, 3, 1, p_74875_3_);
            this.placeDoorAtCurrentPosition(p_74875_1_, p_74875_3_, p_74875_2_, 2, 1, 0, this.func_151555_a(Blocks.wooden_door, 1));
            this.func_151549_a(p_74875_1_, p_74875_3_, 1, 0, -1, 3, 2, -1, Blocks.air, Blocks.air, false);
            if (this.func_151548_a(p_74875_1_, 2, 0, -1, p_74875_3_).getMaterial() == Material.air && this.func_151548_a(p_74875_1_, 2, -1, -1, p_74875_3_).getMaterial() != Material.air) {
                this.func_151550_a(p_74875_1_, Blocks.stone_stairs, this.func_151555_a(Blocks.stone_stairs, 3), 2, 0, -1, p_74875_3_);
            }
            for (int var8 = 0; var8 < 5; ++var8) {
                for (int var9 = 0; var9 < 9; ++var9) {
                    this.clearCurrentPositionBlocksUpwards(p_74875_1_, var9, 7, var8, p_74875_3_);
                    this.func_151554_b(p_74875_1_, Blocks.cobblestone, 0, var9, -1, var8, p_74875_3_);
                }
            }
            for (int var8 = 5; var8 < 11; ++var8) {
                for (int var9 = 2; var9 < 9; ++var9) {
                    this.clearCurrentPositionBlocksUpwards(p_74875_1_, var9, 7, var8, p_74875_3_);
                    this.func_151554_b(p_74875_1_, Blocks.cobblestone, 0, var9, -1, var8, p_74875_3_);
                }
            }
            this.spawnVillagers(p_74875_1_, p_74875_3_, 4, 1, 2, 2);
            return true;
        }
    }
    
    public static class House4Garden extends Village
    {
        private boolean isRoofAccessible;
        private static final String __OBFID = "CL_00000523";
        
        public House4Garden() {
        }
        
        public House4Garden(final Start p_i2100_1_, final int p_i2100_2_, final Random p_i2100_3_, final StructureBoundingBox p_i2100_4_, final int p_i2100_5_) {
            super(p_i2100_1_, p_i2100_2_);
            this.coordBaseMode = p_i2100_5_;
            this.boundingBox = p_i2100_4_;
            this.isRoofAccessible = p_i2100_3_.nextBoolean();
        }
        
        @Override
        protected void func_143012_a(final NBTTagCompound p_143012_1_) {
            super.func_143012_a(p_143012_1_);
            p_143012_1_.setBoolean("Terrace", this.isRoofAccessible);
        }
        
        @Override
        protected void func_143011_b(final NBTTagCompound p_143011_1_) {
            super.func_143011_b(p_143011_1_);
            this.isRoofAccessible = p_143011_1_.getBoolean("Terrace");
        }
        
        public static House4Garden func_74912_a(final Start p_74912_0_, final List p_74912_1_, final Random p_74912_2_, final int p_74912_3_, final int p_74912_4_, final int p_74912_5_, final int p_74912_6_, final int p_74912_7_) {
            final StructureBoundingBox var8 = StructureBoundingBox.getComponentToAddBoundingBox(p_74912_3_, p_74912_4_, p_74912_5_, 0, 0, 0, 5, 6, 5, p_74912_6_);
            return (StructureComponent.findIntersecting(p_74912_1_, var8) != null) ? null : new House4Garden(p_74912_0_, p_74912_7_, p_74912_2_, var8, p_74912_6_);
        }
        
        @Override
        public boolean addComponentParts(final World p_74875_1_, final Random p_74875_2_, final StructureBoundingBox p_74875_3_) {
            if (this.field_143015_k < 0) {
                this.field_143015_k = this.getAverageGroundLevel(p_74875_1_, p_74875_3_);
                if (this.field_143015_k < 0) {
                    return true;
                }
                this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 6 - 1, 0);
            }
            this.func_151549_a(p_74875_1_, p_74875_3_, 0, 0, 0, 4, 0, 4, Blocks.cobblestone, Blocks.cobblestone, false);
            this.func_151549_a(p_74875_1_, p_74875_3_, 0, 4, 0, 4, 4, 4, Blocks.log, Blocks.log, false);
            this.func_151549_a(p_74875_1_, p_74875_3_, 1, 4, 1, 3, 4, 3, Blocks.planks, Blocks.planks, false);
            this.func_151550_a(p_74875_1_, Blocks.cobblestone, 0, 0, 1, 0, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.cobblestone, 0, 0, 2, 0, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.cobblestone, 0, 0, 3, 0, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.cobblestone, 0, 4, 1, 0, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.cobblestone, 0, 4, 2, 0, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.cobblestone, 0, 4, 3, 0, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.cobblestone, 0, 0, 1, 4, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.cobblestone, 0, 0, 2, 4, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.cobblestone, 0, 0, 3, 4, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.cobblestone, 0, 4, 1, 4, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.cobblestone, 0, 4, 2, 4, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.cobblestone, 0, 4, 3, 4, p_74875_3_);
            this.func_151549_a(p_74875_1_, p_74875_3_, 0, 1, 1, 0, 3, 3, Blocks.planks, Blocks.planks, false);
            this.func_151549_a(p_74875_1_, p_74875_3_, 4, 1, 1, 4, 3, 3, Blocks.planks, Blocks.planks, false);
            this.func_151549_a(p_74875_1_, p_74875_3_, 1, 1, 4, 3, 3, 4, Blocks.planks, Blocks.planks, false);
            this.func_151550_a(p_74875_1_, Blocks.glass_pane, 0, 0, 2, 2, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.glass_pane, 0, 2, 2, 4, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.glass_pane, 0, 4, 2, 2, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.planks, 0, 1, 1, 0, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.planks, 0, 1, 2, 0, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.planks, 0, 1, 3, 0, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.planks, 0, 2, 3, 0, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.planks, 0, 3, 3, 0, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.planks, 0, 3, 2, 0, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.planks, 0, 3, 1, 0, p_74875_3_);
            if (this.func_151548_a(p_74875_1_, 2, 0, -1, p_74875_3_).getMaterial() == Material.air && this.func_151548_a(p_74875_1_, 2, -1, -1, p_74875_3_).getMaterial() != Material.air) {
                this.func_151550_a(p_74875_1_, Blocks.stone_stairs, this.func_151555_a(Blocks.stone_stairs, 3), 2, 0, -1, p_74875_3_);
            }
            this.func_151549_a(p_74875_1_, p_74875_3_, 1, 1, 1, 3, 3, 3, Blocks.air, Blocks.air, false);
            if (this.isRoofAccessible) {
                this.func_151550_a(p_74875_1_, Blocks.fence, 0, 0, 5, 0, p_74875_3_);
                this.func_151550_a(p_74875_1_, Blocks.fence, 0, 1, 5, 0, p_74875_3_);
                this.func_151550_a(p_74875_1_, Blocks.fence, 0, 2, 5, 0, p_74875_3_);
                this.func_151550_a(p_74875_1_, Blocks.fence, 0, 3, 5, 0, p_74875_3_);
                this.func_151550_a(p_74875_1_, Blocks.fence, 0, 4, 5, 0, p_74875_3_);
                this.func_151550_a(p_74875_1_, Blocks.fence, 0, 0, 5, 4, p_74875_3_);
                this.func_151550_a(p_74875_1_, Blocks.fence, 0, 1, 5, 4, p_74875_3_);
                this.func_151550_a(p_74875_1_, Blocks.fence, 0, 2, 5, 4, p_74875_3_);
                this.func_151550_a(p_74875_1_, Blocks.fence, 0, 3, 5, 4, p_74875_3_);
                this.func_151550_a(p_74875_1_, Blocks.fence, 0, 4, 5, 4, p_74875_3_);
                this.func_151550_a(p_74875_1_, Blocks.fence, 0, 4, 5, 1, p_74875_3_);
                this.func_151550_a(p_74875_1_, Blocks.fence, 0, 4, 5, 2, p_74875_3_);
                this.func_151550_a(p_74875_1_, Blocks.fence, 0, 4, 5, 3, p_74875_3_);
                this.func_151550_a(p_74875_1_, Blocks.fence, 0, 0, 5, 1, p_74875_3_);
                this.func_151550_a(p_74875_1_, Blocks.fence, 0, 0, 5, 2, p_74875_3_);
                this.func_151550_a(p_74875_1_, Blocks.fence, 0, 0, 5, 3, p_74875_3_);
            }
            if (this.isRoofAccessible) {
                final int var4 = this.func_151555_a(Blocks.ladder, 3);
                this.func_151550_a(p_74875_1_, Blocks.ladder, var4, 3, 1, 3, p_74875_3_);
                this.func_151550_a(p_74875_1_, Blocks.ladder, var4, 3, 2, 3, p_74875_3_);
                this.func_151550_a(p_74875_1_, Blocks.ladder, var4, 3, 3, 3, p_74875_3_);
                this.func_151550_a(p_74875_1_, Blocks.ladder, var4, 3, 4, 3, p_74875_3_);
            }
            this.func_151550_a(p_74875_1_, Blocks.torch, 0, 2, 3, 1, p_74875_3_);
            for (int var4 = 0; var4 < 5; ++var4) {
                for (int var5 = 0; var5 < 5; ++var5) {
                    this.clearCurrentPositionBlocksUpwards(p_74875_1_, var5, 6, var4, p_74875_3_);
                    this.func_151554_b(p_74875_1_, Blocks.cobblestone, 0, var5, -1, var4, p_74875_3_);
                }
            }
            this.spawnVillagers(p_74875_1_, p_74875_3_, 1, 1, 2, 1);
            return true;
        }
    }
    
    public static class Path extends Road
    {
        private int averageGroundLevel;
        private static final String __OBFID = "CL_00000528";
        
        public Path() {
        }
        
        public Path(final Start p_i2105_1_, final int p_i2105_2_, final Random p_i2105_3_, final StructureBoundingBox p_i2105_4_, final int p_i2105_5_) {
            super(p_i2105_1_, p_i2105_2_);
            this.coordBaseMode = p_i2105_5_;
            this.boundingBox = p_i2105_4_;
            this.averageGroundLevel = Math.max(p_i2105_4_.getXSize(), p_i2105_4_.getZSize());
        }
        
        @Override
        protected void func_143012_a(final NBTTagCompound p_143012_1_) {
            super.func_143012_a(p_143012_1_);
            p_143012_1_.setInteger("Length", this.averageGroundLevel);
        }
        
        @Override
        protected void func_143011_b(final NBTTagCompound p_143011_1_) {
            super.func_143011_b(p_143011_1_);
            this.averageGroundLevel = p_143011_1_.getInteger("Length");
        }
        
        @Override
        public void buildComponent(final StructureComponent p_74861_1_, final List p_74861_2_, final Random p_74861_3_) {
            boolean var4 = false;
            for (int var5 = p_74861_3_.nextInt(5); var5 < this.averageGroundLevel - 8; var5 += 2 + p_74861_3_.nextInt(5)) {
                final StructureComponent var6 = this.getNextComponentNN((Start)p_74861_1_, p_74861_2_, p_74861_3_, 0, var5);
                if (var6 != null) {
                    var5 += Math.max(var6.boundingBox.getXSize(), var6.boundingBox.getZSize());
                    var4 = true;
                }
            }
            for (int var5 = p_74861_3_.nextInt(5); var5 < this.averageGroundLevel - 8; var5 += 2 + p_74861_3_.nextInt(5)) {
                final StructureComponent var6 = this.getNextComponentPP((Start)p_74861_1_, p_74861_2_, p_74861_3_, 0, var5);
                if (var6 != null) {
                    var5 += Math.max(var6.boundingBox.getXSize(), var6.boundingBox.getZSize());
                    var4 = true;
                }
            }
            if (var4 && p_74861_3_.nextInt(3) > 0) {
                switch (this.coordBaseMode) {
                    case 0: {
                        getNextComponentVillagePath((Start)p_74861_1_, p_74861_2_, p_74861_3_, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.maxZ - 2, 1, this.getComponentType());
                        break;
                    }
                    case 1: {
                        getNextComponentVillagePath((Start)p_74861_1_, p_74861_2_, p_74861_3_, this.boundingBox.minX, this.boundingBox.minY, this.boundingBox.minZ - 1, 2, this.getComponentType());
                        break;
                    }
                    case 2: {
                        getNextComponentVillagePath((Start)p_74861_1_, p_74861_2_, p_74861_3_, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.minZ, 1, this.getComponentType());
                        break;
                    }
                    case 3: {
                        getNextComponentVillagePath((Start)p_74861_1_, p_74861_2_, p_74861_3_, this.boundingBox.maxX - 2, this.boundingBox.minY, this.boundingBox.minZ - 1, 2, this.getComponentType());
                        break;
                    }
                }
            }
            if (var4 && p_74861_3_.nextInt(3) > 0) {
                switch (this.coordBaseMode) {
                    case 0: {
                        getNextComponentVillagePath((Start)p_74861_1_, p_74861_2_, p_74861_3_, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.maxZ - 2, 3, this.getComponentType());
                        break;
                    }
                    case 1: {
                        getNextComponentVillagePath((Start)p_74861_1_, p_74861_2_, p_74861_3_, this.boundingBox.minX, this.boundingBox.minY, this.boundingBox.maxZ + 1, 0, this.getComponentType());
                        break;
                    }
                    case 2: {
                        getNextComponentVillagePath((Start)p_74861_1_, p_74861_2_, p_74861_3_, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.minZ, 3, this.getComponentType());
                        break;
                    }
                    case 3: {
                        getNextComponentVillagePath((Start)p_74861_1_, p_74861_2_, p_74861_3_, this.boundingBox.maxX - 2, this.boundingBox.minY, this.boundingBox.maxZ + 1, 0, this.getComponentType());
                        break;
                    }
                }
            }
        }
        
        public static StructureBoundingBox func_74933_a(final Start p_74933_0_, final List p_74933_1_, final Random p_74933_2_, final int p_74933_3_, final int p_74933_4_, final int p_74933_5_, final int p_74933_6_) {
            for (int var7 = 7 * MathHelper.getRandomIntegerInRange(p_74933_2_, 3, 5); var7 >= 7; var7 -= 7) {
                final StructureBoundingBox var8 = StructureBoundingBox.getComponentToAddBoundingBox(p_74933_3_, p_74933_4_, p_74933_5_, 0, 0, 0, 3, 3, var7, p_74933_6_);
                if (StructureComponent.findIntersecting(p_74933_1_, var8) == null) {
                    return var8;
                }
            }
            return null;
        }
        
        @Override
        public boolean addComponentParts(final World p_74875_1_, final Random p_74875_2_, final StructureBoundingBox p_74875_3_) {
            final Block var4 = this.func_151558_b(Blocks.gravel, 0);
            for (int var5 = this.boundingBox.minX; var5 <= this.boundingBox.maxX; ++var5) {
                for (int var6 = this.boundingBox.minZ; var6 <= this.boundingBox.maxZ; ++var6) {
                    if (p_74875_3_.isVecInside(var5, 64, var6)) {
                        final int var7 = p_74875_1_.getTopSolidOrLiquidBlock(var5, var6) - 1;
                        p_74875_1_.setBlock(var5, var7, var6, var4, 0, 2);
                    }
                }
            }
            return true;
        }
    }
    
    public static class PieceWeight
    {
        public Class villagePieceClass;
        public final int villagePieceWeight;
        public int villagePiecesSpawned;
        public int villagePiecesLimit;
        private static final String __OBFID = "CL_00000521";
        
        public PieceWeight(final Class p_i2098_1_, final int p_i2098_2_, final int p_i2098_3_) {
            this.villagePieceClass = p_i2098_1_;
            this.villagePieceWeight = p_i2098_2_;
            this.villagePiecesLimit = p_i2098_3_;
        }
        
        public boolean canSpawnMoreVillagePiecesOfType(final int p_75085_1_) {
            return this.villagePiecesLimit == 0 || this.villagePiecesSpawned < this.villagePiecesLimit;
        }
        
        public boolean canSpawnMoreVillagePieces() {
            return this.villagePiecesLimit == 0 || this.villagePiecesSpawned < this.villagePiecesLimit;
        }
    }
    
    public abstract static class Road extends Village
    {
        private static final String __OBFID = "CL_00000532";
        
        public Road() {
        }
        
        protected Road(final Start p_i2108_1_, final int p_i2108_2_) {
            super(p_i2108_1_, p_i2108_2_);
        }
    }
    
    public static class Start extends Well
    {
        public WorldChunkManager worldChunkMngr;
        public boolean inDesert;
        public int terrainType;
        public PieceWeight structVillagePieceWeight;
        public List structureVillageWeightedPieceList;
        public List field_74932_i;
        public List field_74930_j;
        private static final String __OBFID = "CL_00000527";
        
        public Start() {
            this.field_74932_i = new ArrayList();
            this.field_74930_j = new ArrayList();
        }
        
        public Start(final WorldChunkManager p_i2104_1_, final int p_i2104_2_, final Random p_i2104_3_, final int p_i2104_4_, final int p_i2104_5_, final List p_i2104_6_, final int p_i2104_7_) {
            super(null, 0, p_i2104_3_, p_i2104_4_, p_i2104_5_);
            this.field_74932_i = new ArrayList();
            this.field_74930_j = new ArrayList();
            this.worldChunkMngr = p_i2104_1_;
            this.structureVillageWeightedPieceList = p_i2104_6_;
            this.terrainType = p_i2104_7_;
            final BiomeGenBase var8 = p_i2104_1_.getBiomeGenAt(p_i2104_4_, p_i2104_5_);
            this.inDesert = (var8 == BiomeGenBase.desert || var8 == BiomeGenBase.desertHills);
        }
        
        public WorldChunkManager getWorldChunkManager() {
            return this.worldChunkMngr;
        }
    }
    
    public static class Torch extends Village
    {
        private static final String __OBFID = "CL_00000520";
        
        public Torch() {
        }
        
        public Torch(final Start p_i2097_1_, final int p_i2097_2_, final Random p_i2097_3_, final StructureBoundingBox p_i2097_4_, final int p_i2097_5_) {
            super(p_i2097_1_, p_i2097_2_);
            this.coordBaseMode = p_i2097_5_;
            this.boundingBox = p_i2097_4_;
        }
        
        public static StructureBoundingBox func_74904_a(final Start p_74904_0_, final List p_74904_1_, final Random p_74904_2_, final int p_74904_3_, final int p_74904_4_, final int p_74904_5_, final int p_74904_6_) {
            final StructureBoundingBox var7 = StructureBoundingBox.getComponentToAddBoundingBox(p_74904_3_, p_74904_4_, p_74904_5_, 0, 0, 0, 3, 4, 2, p_74904_6_);
            return (StructureComponent.findIntersecting(p_74904_1_, var7) != null) ? null : var7;
        }
        
        @Override
        public boolean addComponentParts(final World p_74875_1_, final Random p_74875_2_, final StructureBoundingBox p_74875_3_) {
            if (this.field_143015_k < 0) {
                this.field_143015_k = this.getAverageGroundLevel(p_74875_1_, p_74875_3_);
                if (this.field_143015_k < 0) {
                    return true;
                }
                this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 4 - 1, 0);
            }
            this.func_151549_a(p_74875_1_, p_74875_3_, 0, 0, 0, 2, 3, 1, Blocks.air, Blocks.air, false);
            this.func_151550_a(p_74875_1_, Blocks.fence, 0, 1, 0, 0, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.fence, 0, 1, 1, 0, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.fence, 0, 1, 2, 0, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.wool, 15, 1, 3, 0, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.torch, 0, 0, 3, 0, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.torch, 0, 1, 3, 1, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.torch, 0, 2, 3, 0, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.torch, 0, 1, 3, -1, p_74875_3_);
            return true;
        }
    }
    
    abstract static class Village extends StructureComponent
    {
        protected int field_143015_k;
        private int villagersSpawned;
        private boolean field_143014_b;
        private static final String __OBFID = "CL_00000531";
        
        public Village() {
            this.field_143015_k = -1;
        }
        
        protected Village(final Start p_i2107_1_, final int p_i2107_2_) {
            super(p_i2107_2_);
            this.field_143015_k = -1;
            if (p_i2107_1_ != null) {
                this.field_143014_b = p_i2107_1_.inDesert;
            }
        }
        
        @Override
        protected void func_143012_a(final NBTTagCompound p_143012_1_) {
            p_143012_1_.setInteger("HPos", this.field_143015_k);
            p_143012_1_.setInteger("VCount", this.villagersSpawned);
            p_143012_1_.setBoolean("Desert", this.field_143014_b);
        }
        
        @Override
        protected void func_143011_b(final NBTTagCompound p_143011_1_) {
            this.field_143015_k = p_143011_1_.getInteger("HPos");
            this.villagersSpawned = p_143011_1_.getInteger("VCount");
            this.field_143014_b = p_143011_1_.getBoolean("Desert");
        }
        
        protected StructureComponent getNextComponentNN(final Start p_74891_1_, final List p_74891_2_, final Random p_74891_3_, final int p_74891_4_, final int p_74891_5_) {
            switch (this.coordBaseMode) {
                case 0: {
                    return getNextVillageStructureComponent(p_74891_1_, p_74891_2_, p_74891_3_, this.boundingBox.minX - 1, this.boundingBox.minY + p_74891_4_, this.boundingBox.minZ + p_74891_5_, 1, this.getComponentType());
                }
                case 1: {
                    return getNextVillageStructureComponent(p_74891_1_, p_74891_2_, p_74891_3_, this.boundingBox.minX + p_74891_5_, this.boundingBox.minY + p_74891_4_, this.boundingBox.minZ - 1, 2, this.getComponentType());
                }
                case 2: {
                    return getNextVillageStructureComponent(p_74891_1_, p_74891_2_, p_74891_3_, this.boundingBox.minX - 1, this.boundingBox.minY + p_74891_4_, this.boundingBox.minZ + p_74891_5_, 1, this.getComponentType());
                }
                case 3: {
                    return getNextVillageStructureComponent(p_74891_1_, p_74891_2_, p_74891_3_, this.boundingBox.minX + p_74891_5_, this.boundingBox.minY + p_74891_4_, this.boundingBox.minZ - 1, 2, this.getComponentType());
                }
                default: {
                    return null;
                }
            }
        }
        
        protected StructureComponent getNextComponentPP(final Start p_74894_1_, final List p_74894_2_, final Random p_74894_3_, final int p_74894_4_, final int p_74894_5_) {
            switch (this.coordBaseMode) {
                case 0: {
                    return getNextVillageStructureComponent(p_74894_1_, p_74894_2_, p_74894_3_, this.boundingBox.maxX + 1, this.boundingBox.minY + p_74894_4_, this.boundingBox.minZ + p_74894_5_, 3, this.getComponentType());
                }
                case 1: {
                    return getNextVillageStructureComponent(p_74894_1_, p_74894_2_, p_74894_3_, this.boundingBox.minX + p_74894_5_, this.boundingBox.minY + p_74894_4_, this.boundingBox.maxZ + 1, 0, this.getComponentType());
                }
                case 2: {
                    return getNextVillageStructureComponent(p_74894_1_, p_74894_2_, p_74894_3_, this.boundingBox.maxX + 1, this.boundingBox.minY + p_74894_4_, this.boundingBox.minZ + p_74894_5_, 3, this.getComponentType());
                }
                case 3: {
                    return getNextVillageStructureComponent(p_74894_1_, p_74894_2_, p_74894_3_, this.boundingBox.minX + p_74894_5_, this.boundingBox.minY + p_74894_4_, this.boundingBox.maxZ + 1, 0, this.getComponentType());
                }
                default: {
                    return null;
                }
            }
        }
        
        protected int getAverageGroundLevel(final World p_74889_1_, final StructureBoundingBox p_74889_2_) {
            int var3 = 0;
            int var4 = 0;
            for (int var5 = this.boundingBox.minZ; var5 <= this.boundingBox.maxZ; ++var5) {
                for (int var6 = this.boundingBox.minX; var6 <= this.boundingBox.maxX; ++var6) {
                    if (p_74889_2_.isVecInside(var6, 64, var5)) {
                        var3 += Math.max(p_74889_1_.getTopSolidOrLiquidBlock(var6, var5), p_74889_1_.provider.getAverageGroundLevel());
                        ++var4;
                    }
                }
            }
            if (var4 == 0) {
                return -1;
            }
            return var3 / var4;
        }
        
        protected static boolean canVillageGoDeeper(final StructureBoundingBox p_74895_0_) {
            return p_74895_0_ != null && p_74895_0_.minY > 10;
        }
        
        protected void spawnVillagers(final World p_74893_1_, final StructureBoundingBox p_74893_2_, final int p_74893_3_, final int p_74893_4_, final int p_74893_5_, final int p_74893_6_) {
            if (this.villagersSpawned < p_74893_6_) {
                for (int var7 = this.villagersSpawned; var7 < p_74893_6_; ++var7) {
                    final int var8 = this.getXWithOffset(p_74893_3_ + var7, p_74893_5_);
                    final int var9 = this.getYWithOffset(p_74893_4_);
                    final int var10 = this.getZWithOffset(p_74893_3_ + var7, p_74893_5_);
                    if (!p_74893_2_.isVecInside(var8, var9, var10)) {
                        break;
                    }
                    ++this.villagersSpawned;
                    final EntityVillager var11 = new EntityVillager(p_74893_1_, this.getVillagerType(var7));
                    var11.setLocationAndAngles(var8 + 0.5, var9, var10 + 0.5, 0.0f, 0.0f);
                    p_74893_1_.spawnEntityInWorld(var11);
                }
            }
        }
        
        protected int getVillagerType(final int p_74888_1_) {
            return 0;
        }
        
        protected Block func_151558_b(final Block p_151558_1_, final int p_151558_2_) {
            if (this.field_143014_b) {
                if (p_151558_1_ == Blocks.log || p_151558_1_ == Blocks.log2) {
                    return Blocks.sandstone;
                }
                if (p_151558_1_ == Blocks.cobblestone) {
                    return Blocks.sandstone;
                }
                if (p_151558_1_ == Blocks.planks) {
                    return Blocks.sandstone;
                }
                if (p_151558_1_ == Blocks.oak_stairs) {
                    return Blocks.sandstone_stairs;
                }
                if (p_151558_1_ == Blocks.stone_stairs) {
                    return Blocks.sandstone_stairs;
                }
                if (p_151558_1_ == Blocks.gravel) {
                    return Blocks.sandstone;
                }
            }
            return p_151558_1_;
        }
        
        protected int func_151557_c(final Block p_151557_1_, final int p_151557_2_) {
            if (this.field_143014_b) {
                if (p_151557_1_ == Blocks.log || p_151557_1_ == Blocks.log2) {
                    return 0;
                }
                if (p_151557_1_ == Blocks.cobblestone) {
                    return 0;
                }
                if (p_151557_1_ == Blocks.planks) {
                    return 2;
                }
            }
            return p_151557_2_;
        }
        
        @Override
        protected void func_151550_a(final World p_151550_1_, final Block p_151550_2_, final int p_151550_3_, final int p_151550_4_, final int p_151550_5_, final int p_151550_6_, final StructureBoundingBox p_151550_7_) {
            final Block var8 = this.func_151558_b(p_151550_2_, p_151550_3_);
            final int var9 = this.func_151557_c(p_151550_2_, p_151550_3_);
            super.func_151550_a(p_151550_1_, var8, var9, p_151550_4_, p_151550_5_, p_151550_6_, p_151550_7_);
        }
        
        @Override
        protected void func_151549_a(final World p_151549_1_, final StructureBoundingBox p_151549_2_, final int p_151549_3_, final int p_151549_4_, final int p_151549_5_, final int p_151549_6_, final int p_151549_7_, final int p_151549_8_, final Block p_151549_9_, final Block p_151549_10_, final boolean p_151549_11_) {
            final Block var12 = this.func_151558_b(p_151549_9_, 0);
            final int var13 = this.func_151557_c(p_151549_9_, 0);
            final Block var14 = this.func_151558_b(p_151549_10_, 0);
            final int var15 = this.func_151557_c(p_151549_10_, 0);
            super.func_151556_a(p_151549_1_, p_151549_2_, p_151549_3_, p_151549_4_, p_151549_5_, p_151549_6_, p_151549_7_, p_151549_8_, var12, var13, var14, var15, p_151549_11_);
        }
        
        @Override
        protected void func_151554_b(final World p_151554_1_, final Block p_151554_2_, final int p_151554_3_, final int p_151554_4_, final int p_151554_5_, final int p_151554_6_, final StructureBoundingBox p_151554_7_) {
            final Block var8 = this.func_151558_b(p_151554_2_, p_151554_3_);
            final int var9 = this.func_151557_c(p_151554_2_, p_151554_3_);
            super.func_151554_b(p_151554_1_, var8, var9, p_151554_4_, p_151554_5_, p_151554_6_, p_151554_7_);
        }
    }
    
    public static class Well extends Village
    {
        private static final String __OBFID = "CL_00000533";
        
        public Well() {
        }
        
        public Well(final Start p_i2109_1_, final int p_i2109_2_, final Random p_i2109_3_, final int p_i2109_4_, final int p_i2109_5_) {
            super(p_i2109_1_, p_i2109_2_);
            switch (this.coordBaseMode = p_i2109_3_.nextInt(4)) {
                case 0:
                case 2: {
                    this.boundingBox = new StructureBoundingBox(p_i2109_4_, 64, p_i2109_5_, p_i2109_4_ + 6 - 1, 78, p_i2109_5_ + 6 - 1);
                    break;
                }
                default: {
                    this.boundingBox = new StructureBoundingBox(p_i2109_4_, 64, p_i2109_5_, p_i2109_4_ + 6 - 1, 78, p_i2109_5_ + 6 - 1);
                    break;
                }
            }
        }
        
        @Override
        public void buildComponent(final StructureComponent p_74861_1_, final List p_74861_2_, final Random p_74861_3_) {
            getNextComponentVillagePath((Start)p_74861_1_, p_74861_2_, p_74861_3_, this.boundingBox.minX - 1, this.boundingBox.maxY - 4, this.boundingBox.minZ + 1, 1, this.getComponentType());
            getNextComponentVillagePath((Start)p_74861_1_, p_74861_2_, p_74861_3_, this.boundingBox.maxX + 1, this.boundingBox.maxY - 4, this.boundingBox.minZ + 1, 3, this.getComponentType());
            getNextComponentVillagePath((Start)p_74861_1_, p_74861_2_, p_74861_3_, this.boundingBox.minX + 1, this.boundingBox.maxY - 4, this.boundingBox.minZ - 1, 2, this.getComponentType());
            getNextComponentVillagePath((Start)p_74861_1_, p_74861_2_, p_74861_3_, this.boundingBox.minX + 1, this.boundingBox.maxY - 4, this.boundingBox.maxZ + 1, 0, this.getComponentType());
        }
        
        @Override
        public boolean addComponentParts(final World p_74875_1_, final Random p_74875_2_, final StructureBoundingBox p_74875_3_) {
            if (this.field_143015_k < 0) {
                this.field_143015_k = this.getAverageGroundLevel(p_74875_1_, p_74875_3_);
                if (this.field_143015_k < 0) {
                    return true;
                }
                this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 3, 0);
            }
            this.func_151549_a(p_74875_1_, p_74875_3_, 1, 0, 1, 4, 12, 4, Blocks.cobblestone, Blocks.flowing_water, false);
            this.func_151550_a(p_74875_1_, Blocks.air, 0, 2, 12, 2, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.air, 0, 3, 12, 2, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.air, 0, 2, 12, 3, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.air, 0, 3, 12, 3, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.fence, 0, 1, 13, 1, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.fence, 0, 1, 14, 1, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.fence, 0, 4, 13, 1, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.fence, 0, 4, 14, 1, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.fence, 0, 1, 13, 4, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.fence, 0, 1, 14, 4, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.fence, 0, 4, 13, 4, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.fence, 0, 4, 14, 4, p_74875_3_);
            this.func_151549_a(p_74875_1_, p_74875_3_, 1, 15, 1, 4, 15, 4, Blocks.cobblestone, Blocks.cobblestone, false);
            for (int var4 = 0; var4 <= 5; ++var4) {
                for (int var5 = 0; var5 <= 5; ++var5) {
                    if (var5 == 0 || var5 == 5 || var4 == 0 || var4 == 5) {
                        this.func_151550_a(p_74875_1_, Blocks.gravel, 0, var5, 11, var4, p_74875_3_);
                        this.clearCurrentPositionBlocksUpwards(p_74875_1_, var5, 12, var4, p_74875_3_);
                    }
                }
            }
            return true;
        }
    }
    
    public static class WoodHut extends Village
    {
        private boolean isTallHouse;
        private int tablePosition;
        private static final String __OBFID = "CL_00000524";
        
        public WoodHut() {
        }
        
        public WoodHut(final Start p_i2101_1_, final int p_i2101_2_, final Random p_i2101_3_, final StructureBoundingBox p_i2101_4_, final int p_i2101_5_) {
            super(p_i2101_1_, p_i2101_2_);
            this.coordBaseMode = p_i2101_5_;
            this.boundingBox = p_i2101_4_;
            this.isTallHouse = p_i2101_3_.nextBoolean();
            this.tablePosition = p_i2101_3_.nextInt(3);
        }
        
        @Override
        protected void func_143012_a(final NBTTagCompound p_143012_1_) {
            super.func_143012_a(p_143012_1_);
            p_143012_1_.setInteger("T", this.tablePosition);
            p_143012_1_.setBoolean("C", this.isTallHouse);
        }
        
        @Override
        protected void func_143011_b(final NBTTagCompound p_143011_1_) {
            super.func_143011_b(p_143011_1_);
            this.tablePosition = p_143011_1_.getInteger("T");
            this.isTallHouse = p_143011_1_.getBoolean("C");
        }
        
        public static WoodHut func_74908_a(final Start p_74908_0_, final List p_74908_1_, final Random p_74908_2_, final int p_74908_3_, final int p_74908_4_, final int p_74908_5_, final int p_74908_6_, final int p_74908_7_) {
            final StructureBoundingBox var8 = StructureBoundingBox.getComponentToAddBoundingBox(p_74908_3_, p_74908_4_, p_74908_5_, 0, 0, 0, 4, 6, 5, p_74908_6_);
            return (Village.canVillageGoDeeper(var8) && StructureComponent.findIntersecting(p_74908_1_, var8) == null) ? new WoodHut(p_74908_0_, p_74908_7_, p_74908_2_, var8, p_74908_6_) : null;
        }
        
        @Override
        public boolean addComponentParts(final World p_74875_1_, final Random p_74875_2_, final StructureBoundingBox p_74875_3_) {
            if (this.field_143015_k < 0) {
                this.field_143015_k = this.getAverageGroundLevel(p_74875_1_, p_74875_3_);
                if (this.field_143015_k < 0) {
                    return true;
                }
                this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 6 - 1, 0);
            }
            this.func_151549_a(p_74875_1_, p_74875_3_, 1, 1, 1, 3, 5, 4, Blocks.air, Blocks.air, false);
            this.func_151549_a(p_74875_1_, p_74875_3_, 0, 0, 0, 3, 0, 4, Blocks.cobblestone, Blocks.cobblestone, false);
            this.func_151549_a(p_74875_1_, p_74875_3_, 1, 0, 1, 2, 0, 3, Blocks.dirt, Blocks.dirt, false);
            if (this.isTallHouse) {
                this.func_151549_a(p_74875_1_, p_74875_3_, 1, 4, 1, 2, 4, 3, Blocks.log, Blocks.log, false);
            }
            else {
                this.func_151549_a(p_74875_1_, p_74875_3_, 1, 5, 1, 2, 5, 3, Blocks.log, Blocks.log, false);
            }
            this.func_151550_a(p_74875_1_, Blocks.log, 0, 1, 4, 0, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.log, 0, 2, 4, 0, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.log, 0, 1, 4, 4, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.log, 0, 2, 4, 4, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.log, 0, 0, 4, 1, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.log, 0, 0, 4, 2, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.log, 0, 0, 4, 3, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.log, 0, 3, 4, 1, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.log, 0, 3, 4, 2, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.log, 0, 3, 4, 3, p_74875_3_);
            this.func_151549_a(p_74875_1_, p_74875_3_, 0, 1, 0, 0, 3, 0, Blocks.log, Blocks.log, false);
            this.func_151549_a(p_74875_1_, p_74875_3_, 3, 1, 0, 3, 3, 0, Blocks.log, Blocks.log, false);
            this.func_151549_a(p_74875_1_, p_74875_3_, 0, 1, 4, 0, 3, 4, Blocks.log, Blocks.log, false);
            this.func_151549_a(p_74875_1_, p_74875_3_, 3, 1, 4, 3, 3, 4, Blocks.log, Blocks.log, false);
            this.func_151549_a(p_74875_1_, p_74875_3_, 0, 1, 1, 0, 3, 3, Blocks.planks, Blocks.planks, false);
            this.func_151549_a(p_74875_1_, p_74875_3_, 3, 1, 1, 3, 3, 3, Blocks.planks, Blocks.planks, false);
            this.func_151549_a(p_74875_1_, p_74875_3_, 1, 1, 0, 2, 3, 0, Blocks.planks, Blocks.planks, false);
            this.func_151549_a(p_74875_1_, p_74875_3_, 1, 1, 4, 2, 3, 4, Blocks.planks, Blocks.planks, false);
            this.func_151550_a(p_74875_1_, Blocks.glass_pane, 0, 0, 2, 2, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.glass_pane, 0, 3, 2, 2, p_74875_3_);
            if (this.tablePosition > 0) {
                this.func_151550_a(p_74875_1_, Blocks.fence, 0, this.tablePosition, 1, 3, p_74875_3_);
                this.func_151550_a(p_74875_1_, Blocks.wooden_pressure_plate, 0, this.tablePosition, 2, 3, p_74875_3_);
            }
            this.func_151550_a(p_74875_1_, Blocks.air, 0, 1, 1, 0, p_74875_3_);
            this.func_151550_a(p_74875_1_, Blocks.air, 0, 1, 2, 0, p_74875_3_);
            this.placeDoorAtCurrentPosition(p_74875_1_, p_74875_3_, p_74875_2_, 1, 1, 0, this.func_151555_a(Blocks.wooden_door, 1));
            if (this.func_151548_a(p_74875_1_, 1, 0, -1, p_74875_3_).getMaterial() == Material.air && this.func_151548_a(p_74875_1_, 1, -1, -1, p_74875_3_).getMaterial() != Material.air) {
                this.func_151550_a(p_74875_1_, Blocks.stone_stairs, this.func_151555_a(Blocks.stone_stairs, 3), 1, 0, -1, p_74875_3_);
            }
            for (int var4 = 0; var4 < 5; ++var4) {
                for (int var5 = 0; var5 < 4; ++var5) {
                    this.clearCurrentPositionBlocksUpwards(p_74875_1_, var5, 6, var4, p_74875_3_);
                    this.func_151554_b(p_74875_1_, Blocks.cobblestone, 0, var5, -1, var4, p_74875_3_);
                }
            }
            this.spawnVillagers(p_74875_1_, p_74875_3_, 1, 1, 2, 1);
            return true;
        }
    }
}
