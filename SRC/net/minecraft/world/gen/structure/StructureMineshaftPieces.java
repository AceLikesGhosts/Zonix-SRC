package net.minecraft.world.gen.structure;

import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraft.block.material.*;
import net.minecraft.entity.item.*;
import net.minecraft.inventory.*;
import net.minecraft.entity.*;
import net.minecraft.tileentity.*;
import net.minecraft.block.*;
import java.util.*;
import net.minecraft.nbt.*;

public class StructureMineshaftPieces
{
    private static final WeightedRandomChestContent[] mineshaftChestContents;
    private static final String __OBFID = "CL_00000444";
    
    public static void func_143048_a() {
        MapGenStructureIO.func_143031_a(Corridor.class, "MSCorridor");
        MapGenStructureIO.func_143031_a(Cross.class, "MSCrossing");
        MapGenStructureIO.func_143031_a(Room.class, "MSRoom");
        MapGenStructureIO.func_143031_a(Stairs.class, "MSStairs");
    }
    
    private static StructureComponent getRandomComponent(final List p_78815_0_, final Random p_78815_1_, final int p_78815_2_, final int p_78815_3_, final int p_78815_4_, final int p_78815_5_, final int p_78815_6_) {
        final int var7 = p_78815_1_.nextInt(100);
        if (var7 >= 80) {
            final StructureBoundingBox var8 = Cross.findValidPlacement(p_78815_0_, p_78815_1_, p_78815_2_, p_78815_3_, p_78815_4_, p_78815_5_);
            if (var8 != null) {
                return new Cross(p_78815_6_, p_78815_1_, var8, p_78815_5_);
            }
        }
        else if (var7 >= 70) {
            final StructureBoundingBox var8 = Stairs.findValidPlacement(p_78815_0_, p_78815_1_, p_78815_2_, p_78815_3_, p_78815_4_, p_78815_5_);
            if (var8 != null) {
                return new Stairs(p_78815_6_, p_78815_1_, var8, p_78815_5_);
            }
        }
        else {
            final StructureBoundingBox var8 = Corridor.findValidPlacement(p_78815_0_, p_78815_1_, p_78815_2_, p_78815_3_, p_78815_4_, p_78815_5_);
            if (var8 != null) {
                return new Corridor(p_78815_6_, p_78815_1_, var8, p_78815_5_);
            }
        }
        return null;
    }
    
    private static StructureComponent getNextMineShaftComponent(final StructureComponent p_78817_0_, final List p_78817_1_, final Random p_78817_2_, final int p_78817_3_, final int p_78817_4_, final int p_78817_5_, final int p_78817_6_, final int p_78817_7_) {
        if (p_78817_7_ > 8) {
            return null;
        }
        if (Math.abs(p_78817_3_ - p_78817_0_.getBoundingBox().minX) <= 80 && Math.abs(p_78817_5_ - p_78817_0_.getBoundingBox().minZ) <= 80) {
            final StructureComponent var8 = getRandomComponent(p_78817_1_, p_78817_2_, p_78817_3_, p_78817_4_, p_78817_5_, p_78817_6_, p_78817_7_ + 1);
            if (var8 != null) {
                p_78817_1_.add(var8);
                var8.buildComponent(p_78817_0_, p_78817_1_, p_78817_2_);
            }
            return var8;
        }
        return null;
    }
    
    static {
        mineshaftChestContents = new WeightedRandomChestContent[] { new WeightedRandomChestContent(Items.iron_ingot, 0, 1, 5, 10), new WeightedRandomChestContent(Items.gold_ingot, 0, 1, 3, 5), new WeightedRandomChestContent(Items.redstone, 0, 4, 9, 5), new WeightedRandomChestContent(Items.dye, 4, 4, 9, 5), new WeightedRandomChestContent(Items.diamond, 0, 1, 2, 3), new WeightedRandomChestContent(Items.coal, 0, 3, 8, 10), new WeightedRandomChestContent(Items.bread, 0, 1, 3, 15), new WeightedRandomChestContent(Items.iron_pickaxe, 0, 1, 1, 1), new WeightedRandomChestContent(Item.getItemFromBlock(Blocks.rail), 0, 4, 8, 1), new WeightedRandomChestContent(Items.melon_seeds, 0, 2, 4, 10), new WeightedRandomChestContent(Items.pumpkin_seeds, 0, 2, 4, 10), new WeightedRandomChestContent(Items.saddle, 0, 1, 1, 3), new WeightedRandomChestContent(Items.iron_horse_armor, 0, 1, 1, 1) };
    }
    
    public static class Corridor extends StructureComponent
    {
        private boolean hasRails;
        private boolean hasSpiders;
        private boolean spawnerPlaced;
        private int sectionCount;
        private static final String __OBFID = "CL_00000445";
        
        public Corridor() {
        }
        
        @Override
        protected void func_143012_a(final NBTTagCompound p_143012_1_) {
            p_143012_1_.setBoolean("hr", this.hasRails);
            p_143012_1_.setBoolean("sc", this.hasSpiders);
            p_143012_1_.setBoolean("hps", this.spawnerPlaced);
            p_143012_1_.setInteger("Num", this.sectionCount);
        }
        
        @Override
        protected void func_143011_b(final NBTTagCompound p_143011_1_) {
            this.hasRails = p_143011_1_.getBoolean("hr");
            this.hasSpiders = p_143011_1_.getBoolean("sc");
            this.spawnerPlaced = p_143011_1_.getBoolean("hps");
            this.sectionCount = p_143011_1_.getInteger("Num");
        }
        
        public Corridor(final int p_i2035_1_, final Random p_i2035_2_, final StructureBoundingBox p_i2035_3_, final int p_i2035_4_) {
            super(p_i2035_1_);
            this.coordBaseMode = p_i2035_4_;
            this.boundingBox = p_i2035_3_;
            this.hasRails = (p_i2035_2_.nextInt(3) == 0);
            this.hasSpiders = (!this.hasRails && p_i2035_2_.nextInt(23) == 0);
            if (this.coordBaseMode != 2 && this.coordBaseMode != 0) {
                this.sectionCount = p_i2035_3_.getXSize() / 5;
            }
            else {
                this.sectionCount = p_i2035_3_.getZSize() / 5;
            }
        }
        
        public static StructureBoundingBox findValidPlacement(final List p_74954_0_, final Random p_74954_1_, final int p_74954_2_, final int p_74954_3_, final int p_74954_4_, final int p_74954_5_) {
            final StructureBoundingBox var6 = new StructureBoundingBox(p_74954_2_, p_74954_3_, p_74954_4_, p_74954_2_, p_74954_3_ + 2, p_74954_4_);
            int var7;
            for (var7 = p_74954_1_.nextInt(3) + 2; var7 > 0; --var7) {
                final int var8 = var7 * 5;
                switch (p_74954_5_) {
                    case 0: {
                        var6.maxX = p_74954_2_ + 2;
                        var6.maxZ = p_74954_4_ + (var8 - 1);
                        break;
                    }
                    case 1: {
                        var6.minX = p_74954_2_ - (var8 - 1);
                        var6.maxZ = p_74954_4_ + 2;
                        break;
                    }
                    case 2: {
                        var6.maxX = p_74954_2_ + 2;
                        var6.minZ = p_74954_4_ - (var8 - 1);
                        break;
                    }
                    case 3: {
                        var6.maxX = p_74954_2_ + (var8 - 1);
                        var6.maxZ = p_74954_4_ + 2;
                        break;
                    }
                }
                if (StructureComponent.findIntersecting(p_74954_0_, var6) == null) {
                    break;
                }
            }
            return (var7 > 0) ? var6 : null;
        }
        
        @Override
        public void buildComponent(final StructureComponent p_74861_1_, final List p_74861_2_, final Random p_74861_3_) {
            final int var4 = this.getComponentType();
            final int var5 = p_74861_3_.nextInt(4);
            switch (this.coordBaseMode) {
                case 0: {
                    if (var5 <= 1) {
                        getNextMineShaftComponent(p_74861_1_, p_74861_2_, p_74861_3_, this.boundingBox.minX, this.boundingBox.minY - 1 + p_74861_3_.nextInt(3), this.boundingBox.maxZ + 1, this.coordBaseMode, var4);
                        break;
                    }
                    if (var5 == 2) {
                        getNextMineShaftComponent(p_74861_1_, p_74861_2_, p_74861_3_, this.boundingBox.minX - 1, this.boundingBox.minY - 1 + p_74861_3_.nextInt(3), this.boundingBox.maxZ - 3, 1, var4);
                        break;
                    }
                    getNextMineShaftComponent(p_74861_1_, p_74861_2_, p_74861_3_, this.boundingBox.maxX + 1, this.boundingBox.minY - 1 + p_74861_3_.nextInt(3), this.boundingBox.maxZ - 3, 3, var4);
                    break;
                }
                case 1: {
                    if (var5 <= 1) {
                        getNextMineShaftComponent(p_74861_1_, p_74861_2_, p_74861_3_, this.boundingBox.minX - 1, this.boundingBox.minY - 1 + p_74861_3_.nextInt(3), this.boundingBox.minZ, this.coordBaseMode, var4);
                        break;
                    }
                    if (var5 == 2) {
                        getNextMineShaftComponent(p_74861_1_, p_74861_2_, p_74861_3_, this.boundingBox.minX, this.boundingBox.minY - 1 + p_74861_3_.nextInt(3), this.boundingBox.minZ - 1, 2, var4);
                        break;
                    }
                    getNextMineShaftComponent(p_74861_1_, p_74861_2_, p_74861_3_, this.boundingBox.minX, this.boundingBox.minY - 1 + p_74861_3_.nextInt(3), this.boundingBox.maxZ + 1, 0, var4);
                    break;
                }
                case 2: {
                    if (var5 <= 1) {
                        getNextMineShaftComponent(p_74861_1_, p_74861_2_, p_74861_3_, this.boundingBox.minX, this.boundingBox.minY - 1 + p_74861_3_.nextInt(3), this.boundingBox.minZ - 1, this.coordBaseMode, var4);
                        break;
                    }
                    if (var5 == 2) {
                        getNextMineShaftComponent(p_74861_1_, p_74861_2_, p_74861_3_, this.boundingBox.minX - 1, this.boundingBox.minY - 1 + p_74861_3_.nextInt(3), this.boundingBox.minZ, 1, var4);
                        break;
                    }
                    getNextMineShaftComponent(p_74861_1_, p_74861_2_, p_74861_3_, this.boundingBox.maxX + 1, this.boundingBox.minY - 1 + p_74861_3_.nextInt(3), this.boundingBox.minZ, 3, var4);
                    break;
                }
                case 3: {
                    if (var5 <= 1) {
                        getNextMineShaftComponent(p_74861_1_, p_74861_2_, p_74861_3_, this.boundingBox.maxX + 1, this.boundingBox.minY - 1 + p_74861_3_.nextInt(3), this.boundingBox.minZ, this.coordBaseMode, var4);
                        break;
                    }
                    if (var5 == 2) {
                        getNextMineShaftComponent(p_74861_1_, p_74861_2_, p_74861_3_, this.boundingBox.maxX - 3, this.boundingBox.minY - 1 + p_74861_3_.nextInt(3), this.boundingBox.minZ - 1, 2, var4);
                        break;
                    }
                    getNextMineShaftComponent(p_74861_1_, p_74861_2_, p_74861_3_, this.boundingBox.maxX - 3, this.boundingBox.minY - 1 + p_74861_3_.nextInt(3), this.boundingBox.maxZ + 1, 0, var4);
                    break;
                }
            }
            if (var4 < 8) {
                if (this.coordBaseMode != 2 && this.coordBaseMode != 0) {
                    for (int var6 = this.boundingBox.minX + 3; var6 + 3 <= this.boundingBox.maxX; var6 += 5) {
                        final int var7 = p_74861_3_.nextInt(5);
                        if (var7 == 0) {
                            getNextMineShaftComponent(p_74861_1_, p_74861_2_, p_74861_3_, var6, this.boundingBox.minY, this.boundingBox.minZ - 1, 2, var4 + 1);
                        }
                        else if (var7 == 1) {
                            getNextMineShaftComponent(p_74861_1_, p_74861_2_, p_74861_3_, var6, this.boundingBox.minY, this.boundingBox.maxZ + 1, 0, var4 + 1);
                        }
                    }
                }
                else {
                    for (int var6 = this.boundingBox.minZ + 3; var6 + 3 <= this.boundingBox.maxZ; var6 += 5) {
                        final int var7 = p_74861_3_.nextInt(5);
                        if (var7 == 0) {
                            getNextMineShaftComponent(p_74861_1_, p_74861_2_, p_74861_3_, this.boundingBox.minX - 1, this.boundingBox.minY, var6, 1, var4 + 1);
                        }
                        else if (var7 == 1) {
                            getNextMineShaftComponent(p_74861_1_, p_74861_2_, p_74861_3_, this.boundingBox.maxX + 1, this.boundingBox.minY, var6, 3, var4 + 1);
                        }
                    }
                }
            }
        }
        
        @Override
        protected boolean generateStructureChestContents(final World p_74879_1_, final StructureBoundingBox p_74879_2_, final Random p_74879_3_, final int p_74879_4_, final int p_74879_5_, final int p_74879_6_, final WeightedRandomChestContent[] p_74879_7_, final int p_74879_8_) {
            final int var9 = this.getXWithOffset(p_74879_4_, p_74879_6_);
            final int var10 = this.getYWithOffset(p_74879_5_);
            final int var11 = this.getZWithOffset(p_74879_4_, p_74879_6_);
            if (p_74879_2_.isVecInside(var9, var10, var11) && p_74879_1_.getBlock(var9, var10, var11).getMaterial() == Material.air) {
                final int var12 = p_74879_3_.nextBoolean() ? 1 : 0;
                p_74879_1_.setBlock(var9, var10, var11, Blocks.rail, this.func_151555_a(Blocks.rail, var12), 2);
                final EntityMinecartChest var13 = new EntityMinecartChest(p_74879_1_, var9 + 0.5f, var10 + 0.5f, var11 + 0.5f);
                WeightedRandomChestContent.generateChestContents(p_74879_3_, p_74879_7_, var13, p_74879_8_);
                p_74879_1_.spawnEntityInWorld(var13);
                return true;
            }
            return false;
        }
        
        @Override
        public boolean addComponentParts(final World p_74875_1_, final Random p_74875_2_, final StructureBoundingBox p_74875_3_) {
            if (this.isLiquidInStructureBoundingBox(p_74875_1_, p_74875_3_)) {
                return false;
            }
            final boolean var4 = false;
            final boolean var5 = true;
            final boolean var6 = false;
            final boolean var7 = true;
            final int var8 = this.sectionCount * 5 - 1;
            this.func_151549_a(p_74875_1_, p_74875_3_, 0, 0, 0, 2, 1, var8, Blocks.air, Blocks.air, false);
            this.func_151551_a(p_74875_1_, p_74875_3_, p_74875_2_, 0.8f, 0, 2, 0, 2, 2, var8, Blocks.air, Blocks.air, false);
            if (this.hasSpiders) {
                this.func_151551_a(p_74875_1_, p_74875_3_, p_74875_2_, 0.6f, 0, 0, 0, 2, 1, var8, Blocks.web, Blocks.air, false);
            }
            for (int var9 = 0; var9 < this.sectionCount; ++var9) {
                final int var10 = 2 + var9 * 5;
                this.func_151549_a(p_74875_1_, p_74875_3_, 0, 0, var10, 0, 1, var10, Blocks.fence, Blocks.air, false);
                this.func_151549_a(p_74875_1_, p_74875_3_, 2, 0, var10, 2, 1, var10, Blocks.fence, Blocks.air, false);
                if (p_74875_2_.nextInt(4) == 0) {
                    this.func_151549_a(p_74875_1_, p_74875_3_, 0, 2, var10, 0, 2, var10, Blocks.planks, Blocks.air, false);
                    this.func_151549_a(p_74875_1_, p_74875_3_, 2, 2, var10, 2, 2, var10, Blocks.planks, Blocks.air, false);
                }
                else {
                    this.func_151549_a(p_74875_1_, p_74875_3_, 0, 2, var10, 2, 2, var10, Blocks.planks, Blocks.air, false);
                }
                this.func_151552_a(p_74875_1_, p_74875_3_, p_74875_2_, 0.1f, 0, 2, var10 - 1, Blocks.web, 0);
                this.func_151552_a(p_74875_1_, p_74875_3_, p_74875_2_, 0.1f, 2, 2, var10 - 1, Blocks.web, 0);
                this.func_151552_a(p_74875_1_, p_74875_3_, p_74875_2_, 0.1f, 0, 2, var10 + 1, Blocks.web, 0);
                this.func_151552_a(p_74875_1_, p_74875_3_, p_74875_2_, 0.1f, 2, 2, var10 + 1, Blocks.web, 0);
                this.func_151552_a(p_74875_1_, p_74875_3_, p_74875_2_, 0.05f, 0, 2, var10 - 2, Blocks.web, 0);
                this.func_151552_a(p_74875_1_, p_74875_3_, p_74875_2_, 0.05f, 2, 2, var10 - 2, Blocks.web, 0);
                this.func_151552_a(p_74875_1_, p_74875_3_, p_74875_2_, 0.05f, 0, 2, var10 + 2, Blocks.web, 0);
                this.func_151552_a(p_74875_1_, p_74875_3_, p_74875_2_, 0.05f, 2, 2, var10 + 2, Blocks.web, 0);
                this.func_151552_a(p_74875_1_, p_74875_3_, p_74875_2_, 0.05f, 1, 2, var10 - 1, Blocks.torch, 0);
                this.func_151552_a(p_74875_1_, p_74875_3_, p_74875_2_, 0.05f, 1, 2, var10 + 1, Blocks.torch, 0);
                if (p_74875_2_.nextInt(100) == 0) {
                    this.generateStructureChestContents(p_74875_1_, p_74875_3_, p_74875_2_, 2, 0, var10 - 1, WeightedRandomChestContent.func_92080_a(StructureMineshaftPieces.mineshaftChestContents, Items.enchanted_book.func_92114_b(p_74875_2_)), 3 + p_74875_2_.nextInt(4));
                }
                if (p_74875_2_.nextInt(100) == 0) {
                    this.generateStructureChestContents(p_74875_1_, p_74875_3_, p_74875_2_, 0, 0, var10 + 1, WeightedRandomChestContent.func_92080_a(StructureMineshaftPieces.mineshaftChestContents, Items.enchanted_book.func_92114_b(p_74875_2_)), 3 + p_74875_2_.nextInt(4));
                }
                if (this.hasSpiders && !this.spawnerPlaced) {
                    final int var11 = this.getYWithOffset(0);
                    int var12 = var10 - 1 + p_74875_2_.nextInt(3);
                    final int var13 = this.getXWithOffset(1, var12);
                    var12 = this.getZWithOffset(1, var12);
                    if (p_74875_3_.isVecInside(var13, var11, var12)) {
                        this.spawnerPlaced = true;
                        p_74875_1_.setBlock(var13, var11, var12, Blocks.mob_spawner, 0, 2);
                        final TileEntityMobSpawner var14 = (TileEntityMobSpawner)p_74875_1_.getTileEntity(var13, var11, var12);
                        if (var14 != null) {
                            var14.func_145881_a().setMobID("CaveSpider");
                        }
                    }
                }
            }
            for (int var9 = 0; var9 <= 2; ++var9) {
                for (int var10 = 0; var10 <= var8; ++var10) {
                    final byte var15 = -1;
                    final Block var16 = this.func_151548_a(p_74875_1_, var9, var15, var10, p_74875_3_);
                    if (var16.getMaterial() == Material.air) {
                        final byte var17 = -1;
                        this.func_151550_a(p_74875_1_, Blocks.planks, 0, var9, var17, var10, p_74875_3_);
                    }
                }
            }
            if (this.hasRails) {
                for (int var9 = 0; var9 <= var8; ++var9) {
                    final Block var18 = this.func_151548_a(p_74875_1_, 1, -1, var9, p_74875_3_);
                    if (var18.getMaterial() != Material.air && var18.func_149730_j()) {
                        this.func_151552_a(p_74875_1_, p_74875_3_, p_74875_2_, 0.7f, 1, 0, var9, Blocks.rail, this.func_151555_a(Blocks.rail, 0));
                    }
                }
            }
            return true;
        }
    }
    
    public static class Cross extends StructureComponent
    {
        private int corridorDirection;
        private boolean isMultipleFloors;
        private static final String __OBFID = "CL_00000446";
        
        public Cross() {
        }
        
        @Override
        protected void func_143012_a(final NBTTagCompound p_143012_1_) {
            p_143012_1_.setBoolean("tf", this.isMultipleFloors);
            p_143012_1_.setInteger("D", this.corridorDirection);
        }
        
        @Override
        protected void func_143011_b(final NBTTagCompound p_143011_1_) {
            this.isMultipleFloors = p_143011_1_.getBoolean("tf");
            this.corridorDirection = p_143011_1_.getInteger("D");
        }
        
        public Cross(final int p_i2036_1_, final Random p_i2036_2_, final StructureBoundingBox p_i2036_3_, final int p_i2036_4_) {
            super(p_i2036_1_);
            this.corridorDirection = p_i2036_4_;
            this.boundingBox = p_i2036_3_;
            this.isMultipleFloors = (p_i2036_3_.getYSize() > 3);
        }
        
        public static StructureBoundingBox findValidPlacement(final List p_74951_0_, final Random p_74951_1_, final int p_74951_2_, final int p_74951_3_, final int p_74951_4_, final int p_74951_5_) {
            final StructureBoundingBox var6 = new StructureBoundingBox(p_74951_2_, p_74951_3_, p_74951_4_, p_74951_2_, p_74951_3_ + 2, p_74951_4_);
            if (p_74951_1_.nextInt(4) == 0) {
                final StructureBoundingBox structureBoundingBox = var6;
                structureBoundingBox.maxY += 4;
            }
            switch (p_74951_5_) {
                case 0: {
                    var6.minX = p_74951_2_ - 1;
                    var6.maxX = p_74951_2_ + 3;
                    var6.maxZ = p_74951_4_ + 4;
                    break;
                }
                case 1: {
                    var6.minX = p_74951_2_ - 4;
                    var6.minZ = p_74951_4_ - 1;
                    var6.maxZ = p_74951_4_ + 3;
                    break;
                }
                case 2: {
                    var6.minX = p_74951_2_ - 1;
                    var6.maxX = p_74951_2_ + 3;
                    var6.minZ = p_74951_4_ - 4;
                    break;
                }
                case 3: {
                    var6.maxX = p_74951_2_ + 4;
                    var6.minZ = p_74951_4_ - 1;
                    var6.maxZ = p_74951_4_ + 3;
                    break;
                }
            }
            return (StructureComponent.findIntersecting(p_74951_0_, var6) != null) ? null : var6;
        }
        
        @Override
        public void buildComponent(final StructureComponent p_74861_1_, final List p_74861_2_, final Random p_74861_3_) {
            final int var4 = this.getComponentType();
            switch (this.corridorDirection) {
                case 0: {
                    getNextMineShaftComponent(p_74861_1_, p_74861_2_, p_74861_3_, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.maxZ + 1, 0, var4);
                    getNextMineShaftComponent(p_74861_1_, p_74861_2_, p_74861_3_, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.minZ + 1, 1, var4);
                    getNextMineShaftComponent(p_74861_1_, p_74861_2_, p_74861_3_, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.minZ + 1, 3, var4);
                    break;
                }
                case 1: {
                    getNextMineShaftComponent(p_74861_1_, p_74861_2_, p_74861_3_, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.minZ - 1, 2, var4);
                    getNextMineShaftComponent(p_74861_1_, p_74861_2_, p_74861_3_, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.maxZ + 1, 0, var4);
                    getNextMineShaftComponent(p_74861_1_, p_74861_2_, p_74861_3_, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.minZ + 1, 1, var4);
                    break;
                }
                case 2: {
                    getNextMineShaftComponent(p_74861_1_, p_74861_2_, p_74861_3_, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.minZ - 1, 2, var4);
                    getNextMineShaftComponent(p_74861_1_, p_74861_2_, p_74861_3_, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.minZ + 1, 1, var4);
                    getNextMineShaftComponent(p_74861_1_, p_74861_2_, p_74861_3_, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.minZ + 1, 3, var4);
                    break;
                }
                case 3: {
                    getNextMineShaftComponent(p_74861_1_, p_74861_2_, p_74861_3_, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.minZ - 1, 2, var4);
                    getNextMineShaftComponent(p_74861_1_, p_74861_2_, p_74861_3_, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.maxZ + 1, 0, var4);
                    getNextMineShaftComponent(p_74861_1_, p_74861_2_, p_74861_3_, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.minZ + 1, 3, var4);
                    break;
                }
            }
            if (this.isMultipleFloors) {
                if (p_74861_3_.nextBoolean()) {
                    getNextMineShaftComponent(p_74861_1_, p_74861_2_, p_74861_3_, this.boundingBox.minX + 1, this.boundingBox.minY + 3 + 1, this.boundingBox.minZ - 1, 2, var4);
                }
                if (p_74861_3_.nextBoolean()) {
                    getNextMineShaftComponent(p_74861_1_, p_74861_2_, p_74861_3_, this.boundingBox.minX - 1, this.boundingBox.minY + 3 + 1, this.boundingBox.minZ + 1, 1, var4);
                }
                if (p_74861_3_.nextBoolean()) {
                    getNextMineShaftComponent(p_74861_1_, p_74861_2_, p_74861_3_, this.boundingBox.maxX + 1, this.boundingBox.minY + 3 + 1, this.boundingBox.minZ + 1, 3, var4);
                }
                if (p_74861_3_.nextBoolean()) {
                    getNextMineShaftComponent(p_74861_1_, p_74861_2_, p_74861_3_, this.boundingBox.minX + 1, this.boundingBox.minY + 3 + 1, this.boundingBox.maxZ + 1, 0, var4);
                }
            }
        }
        
        @Override
        public boolean addComponentParts(final World p_74875_1_, final Random p_74875_2_, final StructureBoundingBox p_74875_3_) {
            if (this.isLiquidInStructureBoundingBox(p_74875_1_, p_74875_3_)) {
                return false;
            }
            if (this.isMultipleFloors) {
                this.func_151549_a(p_74875_1_, p_74875_3_, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.minZ, this.boundingBox.maxX - 1, this.boundingBox.minY + 3 - 1, this.boundingBox.maxZ, Blocks.air, Blocks.air, false);
                this.func_151549_a(p_74875_1_, p_74875_3_, this.boundingBox.minX, this.boundingBox.minY, this.boundingBox.minZ + 1, this.boundingBox.maxX, this.boundingBox.minY + 3 - 1, this.boundingBox.maxZ - 1, Blocks.air, Blocks.air, false);
                this.func_151549_a(p_74875_1_, p_74875_3_, this.boundingBox.minX + 1, this.boundingBox.maxY - 2, this.boundingBox.minZ, this.boundingBox.maxX - 1, this.boundingBox.maxY, this.boundingBox.maxZ, Blocks.air, Blocks.air, false);
                this.func_151549_a(p_74875_1_, p_74875_3_, this.boundingBox.minX, this.boundingBox.maxY - 2, this.boundingBox.minZ + 1, this.boundingBox.maxX, this.boundingBox.maxY, this.boundingBox.maxZ - 1, Blocks.air, Blocks.air, false);
                this.func_151549_a(p_74875_1_, p_74875_3_, this.boundingBox.minX + 1, this.boundingBox.minY + 3, this.boundingBox.minZ + 1, this.boundingBox.maxX - 1, this.boundingBox.minY + 3, this.boundingBox.maxZ - 1, Blocks.air, Blocks.air, false);
            }
            else {
                this.func_151549_a(p_74875_1_, p_74875_3_, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.minZ, this.boundingBox.maxX - 1, this.boundingBox.maxY, this.boundingBox.maxZ, Blocks.air, Blocks.air, false);
                this.func_151549_a(p_74875_1_, p_74875_3_, this.boundingBox.minX, this.boundingBox.minY, this.boundingBox.minZ + 1, this.boundingBox.maxX, this.boundingBox.maxY, this.boundingBox.maxZ - 1, Blocks.air, Blocks.air, false);
            }
            this.func_151549_a(p_74875_1_, p_74875_3_, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.minZ + 1, this.boundingBox.minX + 1, this.boundingBox.maxY, this.boundingBox.minZ + 1, Blocks.planks, Blocks.air, false);
            this.func_151549_a(p_74875_1_, p_74875_3_, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.maxZ - 1, this.boundingBox.minX + 1, this.boundingBox.maxY, this.boundingBox.maxZ - 1, Blocks.planks, Blocks.air, false);
            this.func_151549_a(p_74875_1_, p_74875_3_, this.boundingBox.maxX - 1, this.boundingBox.minY, this.boundingBox.minZ + 1, this.boundingBox.maxX - 1, this.boundingBox.maxY, this.boundingBox.minZ + 1, Blocks.planks, Blocks.air, false);
            this.func_151549_a(p_74875_1_, p_74875_3_, this.boundingBox.maxX - 1, this.boundingBox.minY, this.boundingBox.maxZ - 1, this.boundingBox.maxX - 1, this.boundingBox.maxY, this.boundingBox.maxZ - 1, Blocks.planks, Blocks.air, false);
            for (int var4 = this.boundingBox.minX; var4 <= this.boundingBox.maxX; ++var4) {
                for (int var5 = this.boundingBox.minZ; var5 <= this.boundingBox.maxZ; ++var5) {
                    if (this.func_151548_a(p_74875_1_, var4, this.boundingBox.minY - 1, var5, p_74875_3_).getMaterial() == Material.air) {
                        this.func_151550_a(p_74875_1_, Blocks.planks, 0, var4, this.boundingBox.minY - 1, var5, p_74875_3_);
                    }
                }
            }
            return true;
        }
    }
    
    public static class Room extends StructureComponent
    {
        private List roomsLinkedToTheRoom;
        private static final String __OBFID = "CL_00000447";
        
        public Room() {
            this.roomsLinkedToTheRoom = new LinkedList();
        }
        
        public Room(final int p_i2037_1_, final Random p_i2037_2_, final int p_i2037_3_, final int p_i2037_4_) {
            super(p_i2037_1_);
            this.roomsLinkedToTheRoom = new LinkedList();
            this.boundingBox = new StructureBoundingBox(p_i2037_3_, 50, p_i2037_4_, p_i2037_3_ + 7 + p_i2037_2_.nextInt(6), 54 + p_i2037_2_.nextInt(6), p_i2037_4_ + 7 + p_i2037_2_.nextInt(6));
        }
        
        @Override
        public void buildComponent(final StructureComponent p_74861_1_, final List p_74861_2_, final Random p_74861_3_) {
            final int var4 = this.getComponentType();
            int var5 = this.boundingBox.getYSize() - 3 - 1;
            if (var5 <= 0) {
                var5 = 1;
            }
            for (int var6 = 0; var6 < this.boundingBox.getXSize(); var6 += 4) {
                var6 += p_74861_3_.nextInt(this.boundingBox.getXSize());
                if (var6 + 3 > this.boundingBox.getXSize()) {
                    break;
                }
                final StructureComponent var7 = getNextMineShaftComponent(p_74861_1_, p_74861_2_, p_74861_3_, this.boundingBox.minX + var6, this.boundingBox.minY + p_74861_3_.nextInt(var5) + 1, this.boundingBox.minZ - 1, 2, var4);
                if (var7 != null) {
                    final StructureBoundingBox var8 = var7.getBoundingBox();
                    this.roomsLinkedToTheRoom.add(new StructureBoundingBox(var8.minX, var8.minY, this.boundingBox.minZ, var8.maxX, var8.maxY, this.boundingBox.minZ + 1));
                }
            }
            for (int var6 = 0; var6 < this.boundingBox.getXSize(); var6 += 4) {
                var6 += p_74861_3_.nextInt(this.boundingBox.getXSize());
                if (var6 + 3 > this.boundingBox.getXSize()) {
                    break;
                }
                final StructureComponent var7 = getNextMineShaftComponent(p_74861_1_, p_74861_2_, p_74861_3_, this.boundingBox.minX + var6, this.boundingBox.minY + p_74861_3_.nextInt(var5) + 1, this.boundingBox.maxZ + 1, 0, var4);
                if (var7 != null) {
                    final StructureBoundingBox var8 = var7.getBoundingBox();
                    this.roomsLinkedToTheRoom.add(new StructureBoundingBox(var8.minX, var8.minY, this.boundingBox.maxZ - 1, var8.maxX, var8.maxY, this.boundingBox.maxZ));
                }
            }
            for (int var6 = 0; var6 < this.boundingBox.getZSize(); var6 += 4) {
                var6 += p_74861_3_.nextInt(this.boundingBox.getZSize());
                if (var6 + 3 > this.boundingBox.getZSize()) {
                    break;
                }
                final StructureComponent var7 = getNextMineShaftComponent(p_74861_1_, p_74861_2_, p_74861_3_, this.boundingBox.minX - 1, this.boundingBox.minY + p_74861_3_.nextInt(var5) + 1, this.boundingBox.minZ + var6, 1, var4);
                if (var7 != null) {
                    final StructureBoundingBox var8 = var7.getBoundingBox();
                    this.roomsLinkedToTheRoom.add(new StructureBoundingBox(this.boundingBox.minX, var8.minY, var8.minZ, this.boundingBox.minX + 1, var8.maxY, var8.maxZ));
                }
            }
            for (int var6 = 0; var6 < this.boundingBox.getZSize(); var6 += 4) {
                var6 += p_74861_3_.nextInt(this.boundingBox.getZSize());
                if (var6 + 3 > this.boundingBox.getZSize()) {
                    break;
                }
                final StructureComponent var7 = getNextMineShaftComponent(p_74861_1_, p_74861_2_, p_74861_3_, this.boundingBox.maxX + 1, this.boundingBox.minY + p_74861_3_.nextInt(var5) + 1, this.boundingBox.minZ + var6, 3, var4);
                if (var7 != null) {
                    final StructureBoundingBox var8 = var7.getBoundingBox();
                    this.roomsLinkedToTheRoom.add(new StructureBoundingBox(this.boundingBox.maxX - 1, var8.minY, var8.minZ, this.boundingBox.maxX, var8.maxY, var8.maxZ));
                }
            }
        }
        
        @Override
        public boolean addComponentParts(final World p_74875_1_, final Random p_74875_2_, final StructureBoundingBox p_74875_3_) {
            if (this.isLiquidInStructureBoundingBox(p_74875_1_, p_74875_3_)) {
                return false;
            }
            this.func_151549_a(p_74875_1_, p_74875_3_, this.boundingBox.minX, this.boundingBox.minY, this.boundingBox.minZ, this.boundingBox.maxX, this.boundingBox.minY, this.boundingBox.maxZ, Blocks.dirt, Blocks.air, true);
            this.func_151549_a(p_74875_1_, p_74875_3_, this.boundingBox.minX, this.boundingBox.minY + 1, this.boundingBox.minZ, this.boundingBox.maxX, Math.min(this.boundingBox.minY + 3, this.boundingBox.maxY), this.boundingBox.maxZ, Blocks.air, Blocks.air, false);
            for (final StructureBoundingBox var5 : this.roomsLinkedToTheRoom) {
                this.func_151549_a(p_74875_1_, p_74875_3_, var5.minX, var5.maxY - 2, var5.minZ, var5.maxX, var5.maxY, var5.maxZ, Blocks.air, Blocks.air, false);
            }
            this.func_151547_a(p_74875_1_, p_74875_3_, this.boundingBox.minX, this.boundingBox.minY + 4, this.boundingBox.minZ, this.boundingBox.maxX, this.boundingBox.maxY, this.boundingBox.maxZ, Blocks.air, false);
            return true;
        }
        
        @Override
        protected void func_143012_a(final NBTTagCompound p_143012_1_) {
            final NBTTagList var2 = new NBTTagList();
            for (final StructureBoundingBox var4 : this.roomsLinkedToTheRoom) {
                var2.appendTag(var4.func_151535_h());
            }
            p_143012_1_.setTag("Entrances", var2);
        }
        
        @Override
        protected void func_143011_b(final NBTTagCompound p_143011_1_) {
            final NBTTagList var2 = p_143011_1_.getTagList("Entrances", 11);
            for (int var3 = 0; var3 < var2.tagCount(); ++var3) {
                this.roomsLinkedToTheRoom.add(new StructureBoundingBox(var2.func_150306_c(var3)));
            }
        }
    }
    
    public static class Stairs extends StructureComponent
    {
        private static final String __OBFID = "CL_00000449";
        
        public Stairs() {
        }
        
        public Stairs(final int p_i2038_1_, final Random p_i2038_2_, final StructureBoundingBox p_i2038_3_, final int p_i2038_4_) {
            super(p_i2038_1_);
            this.coordBaseMode = p_i2038_4_;
            this.boundingBox = p_i2038_3_;
        }
        
        @Override
        protected void func_143012_a(final NBTTagCompound p_143012_1_) {
        }
        
        @Override
        protected void func_143011_b(final NBTTagCompound p_143011_1_) {
        }
        
        public static StructureBoundingBox findValidPlacement(final List p_74950_0_, final Random p_74950_1_, final int p_74950_2_, final int p_74950_3_, final int p_74950_4_, final int p_74950_5_) {
            final StructureBoundingBox var6 = new StructureBoundingBox(p_74950_2_, p_74950_3_ - 5, p_74950_4_, p_74950_2_, p_74950_3_ + 2, p_74950_4_);
            switch (p_74950_5_) {
                case 0: {
                    var6.maxX = p_74950_2_ + 2;
                    var6.maxZ = p_74950_4_ + 8;
                    break;
                }
                case 1: {
                    var6.minX = p_74950_2_ - 8;
                    var6.maxZ = p_74950_4_ + 2;
                    break;
                }
                case 2: {
                    var6.maxX = p_74950_2_ + 2;
                    var6.minZ = p_74950_4_ - 8;
                    break;
                }
                case 3: {
                    var6.maxX = p_74950_2_ + 8;
                    var6.maxZ = p_74950_4_ + 2;
                    break;
                }
            }
            return (StructureComponent.findIntersecting(p_74950_0_, var6) != null) ? null : var6;
        }
        
        @Override
        public void buildComponent(final StructureComponent p_74861_1_, final List p_74861_2_, final Random p_74861_3_) {
            final int var4 = this.getComponentType();
            switch (this.coordBaseMode) {
                case 0: {
                    getNextMineShaftComponent(p_74861_1_, p_74861_2_, p_74861_3_, this.boundingBox.minX, this.boundingBox.minY, this.boundingBox.maxZ + 1, 0, var4);
                    break;
                }
                case 1: {
                    getNextMineShaftComponent(p_74861_1_, p_74861_2_, p_74861_3_, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.minZ, 1, var4);
                    break;
                }
                case 2: {
                    getNextMineShaftComponent(p_74861_1_, p_74861_2_, p_74861_3_, this.boundingBox.minX, this.boundingBox.minY, this.boundingBox.minZ - 1, 2, var4);
                    break;
                }
                case 3: {
                    getNextMineShaftComponent(p_74861_1_, p_74861_2_, p_74861_3_, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.minZ, 3, var4);
                    break;
                }
            }
        }
        
        @Override
        public boolean addComponentParts(final World p_74875_1_, final Random p_74875_2_, final StructureBoundingBox p_74875_3_) {
            if (this.isLiquidInStructureBoundingBox(p_74875_1_, p_74875_3_)) {
                return false;
            }
            this.func_151549_a(p_74875_1_, p_74875_3_, 0, 5, 0, 2, 7, 1, Blocks.air, Blocks.air, false);
            this.func_151549_a(p_74875_1_, p_74875_3_, 0, 0, 7, 2, 2, 8, Blocks.air, Blocks.air, false);
            for (int var4 = 0; var4 < 5; ++var4) {
                this.func_151549_a(p_74875_1_, p_74875_3_, 0, 5 - var4 - ((var4 < 4) ? 1 : 0), 2 + var4, 2, 7 - var4, 2 + var4, Blocks.air, Blocks.air, false);
            }
            return true;
        }
    }
}
