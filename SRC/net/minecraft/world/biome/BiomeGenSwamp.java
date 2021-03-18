package net.minecraft.world.biome;

import net.minecraft.entity.monster.*;
import java.util.*;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.*;
import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.init.*;

public class BiomeGenSwamp extends BiomeGenBase
{
    private static final String __OBFID = "CL_00000185";
    
    protected BiomeGenSwamp(final int p_i1988_1_) {
        super(p_i1988_1_);
        this.theBiomeDecorator.treesPerChunk = 2;
        this.theBiomeDecorator.flowersPerChunk = 1;
        this.theBiomeDecorator.deadBushPerChunk = 1;
        this.theBiomeDecorator.mushroomsPerChunk = 8;
        this.theBiomeDecorator.reedsPerChunk = 10;
        this.theBiomeDecorator.clayPerChunk = 1;
        this.theBiomeDecorator.waterlilyPerChunk = 4;
        this.theBiomeDecorator.sandPerChunk2 = 0;
        this.theBiomeDecorator.sandPerChunk = 0;
        this.theBiomeDecorator.grassPerChunk = 5;
        this.waterColorMultiplier = 14745518;
        this.spawnableMonsterList.add(new SpawnListEntry(EntitySlime.class, 1, 1, 1));
    }
    
    @Override
    public WorldGenAbstractTree func_150567_a(final Random p_150567_1_) {
        return this.worldGeneratorSwamp;
    }
    
    @Override
    public int getBiomeGrassColor(final int p_150558_1_, final int p_150558_2_, final int p_150558_3_) {
        final double var4 = BiomeGenSwamp.field_150606_ad.func_151601_a(p_150558_1_ * 0.0225, p_150558_3_ * 0.0225);
        return (var4 < -0.1) ? 5011004 : 6975545;
    }
    
    @Override
    public int getBiomeFoliageColor(final int p_150571_1_, final int p_150571_2_, final int p_150571_3_) {
        return 6975545;
    }
    
    @Override
    public String func_150572_a(final Random p_150572_1_, final int p_150572_2_, final int p_150572_3_, final int p_150572_4_) {
        return BlockFlower.field_149859_a[1];
    }
    
    @Override
    public void func_150573_a(final World p_150573_1_, final Random p_150573_2_, final Block[] p_150573_3_, final byte[] p_150573_4_, final int p_150573_5_, final int p_150573_6_, final double p_150573_7_) {
        final double var9 = BiomeGenSwamp.field_150606_ad.func_151601_a(p_150573_5_ * 0.25, p_150573_6_ * 0.25);
        if (var9 > 0.0) {
            final int var10 = p_150573_5_ & 0xF;
            final int var11 = p_150573_6_ & 0xF;
            final int var12 = p_150573_3_.length / 256;
            int var13 = 255;
            while (var13 >= 0) {
                final int var14 = (var11 * 16 + var10) * var12 + var13;
                if (p_150573_3_[var14] == null || p_150573_3_[var14].getMaterial() != Material.air) {
                    if (var13 != 62 || p_150573_3_[var14] == Blocks.water) {
                        break;
                    }
                    p_150573_3_[var14] = Blocks.water;
                    if (var9 < 0.12) {
                        p_150573_3_[var14 + 1] = Blocks.waterlily;
                        break;
                    }
                    break;
                }
                else {
                    --var13;
                }
            }
        }
        this.func_150560_b(p_150573_1_, p_150573_2_, p_150573_3_, p_150573_4_, p_150573_5_, p_150573_6_, p_150573_7_);
    }
}
